package com.gertec.G250.functions;

import java.awt.print.PrinterJob;
import gnu.io.NRSerialPort;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.MaskFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 *
 * @author tayna
 */
public class printCouponSAT {
    
    private static final byte[] RESTART = {0x1b, 0x40}; // limpa os dados e reset as configura��es da impressora
    private static String xmlNfKey;
    
    public static void printCouponSAT(String path, int type, Socket socket) throws IOException, Exception {
        sendBinBuffer.sendBinBuffer(RESTART, socket);
        printText.printText(printCouponSATFactory(path, type), socket);
        System.out.println(printCouponSATFactory(path, type));
    }

    public static void printCouponSAT(String path, int type, NRSerialPort serial) throws IOException, Exception {
        sendBinBuffer.sendBinBuffer(RESTART, serial);
        printText.printText(printCouponSATFactory(path, type), serial);
        
    }
    
    public static String printCouponSATFactory(String path, int type) throws ParseException {
        StringBuilder buffer = new StringBuilder();
        try {
            OutputStream port;
            File file = new File (path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();
            NodeList nList = document.getElementsByTagName("CFe");
            //for(int temp = 0; temp < nList.getLength(); temp++){
                Node nNode = nList.item(0);
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE){
                    Element eElement = (Element) nNode;
                    xmlNfKey = eElement.getElementsByTagName("infCFe").item(0).getAttributes().item(0).getTextContent().substring(3);
                    //cabe�alho do SAT
                    //buffer.append("printBITMAP.printBITMAP(\"C:/Users/tayna/OneDrive/Documentos/NetBeansProjects/G250_easyPrinter/GERTEC.jpg\")");
                    buffer.append("<ce><c>").append(eElement.getElementsByTagName("xFant").item(0).getTextContent()).append("</lf>");
                    buffer.append(eElement.getElementsByTagName("xNome").item(0).getTextContent()).append("</ne></lf>");
                    buffer.append(eElement.getElementsByTagName("xLgr").item(0).getTextContent());
                    buffer.append(", ").append(eElement.getElementsByTagName("nro").item(0).getTextContent());
                    buffer.append(", ").append(eElement.getElementsByTagName("xBairro").item(0).getTextContent());
                    buffer.append(", ").append(eElement.getElementsByTagName("xMun").item(0).getTextContent());
                    String cep = eElement.getElementsByTagName("CEP").item(0).getTextContent();
                    MaskFormatter mask = new MaskFormatter("#####-###");
                    mask.setValueContainsLiteralCharacters(false);
                    buffer.append(", CEP :").append(mask.valueToString(cep)).append("</lf>");
                    mask.setMask("##.###.###/####-## ");
                    buffer.append("CNPJ:").append(mask.valueToString(eElement.getElementsByTagName("CNPJ").item(1).getTextContent()));
                    mask.setMask("###.###.###.### ");
                    buffer.append(" IE:").append(mask.valueToString(eElement.getElementsByTagName("IE").item(0).getTextContent()));
                    buffer.append(" IM:").append(eElement.getElementsByTagName("IM").item(0).getTextContent()).append("</lf>");
                    buffer.append("----------------------------------------------------------------").append("</lf>");
                    
                    buffer.append("<ne>").append("Extrato No. ").append(eElement.getElementsByTagName("cNF").item(0).getTextContent()).append("</lf>");
                    buffer.append("CUPOM FISCAL ELETRÔNICO - SAT").append("</ce></ne></c></lf></lf>");
                    
                    switch (type) {
                        case 1:
                            buffer.append(satCFe(eElement));
                            break;
                        case 2:
                            buffer.append(satCancellation(eElement));
                            break;
                        case 3:
                            buffer.append(satCFeSimples(eElement));
                            break;
                        default:
                            throw new AssertionError();
                    }

                    
                }
            //}
             
           
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(printCouponSAT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(printCouponSAT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(printCouponSAT.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(printCouponSAT.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return buffer.toString();
    }
    
    public static String satCFe(Element eElement){
        StringBuilder buffer = new StringBuilder();
        //buffer.append(' ');
        try {
            MaskFormatter mask = new MaskFormatter("#####-###");
            mask.setValueContainsLiteralCharacters(false);
            //satFCe cuopon venda completo
            buffer.append("<c>").append("----------------------------------------------------------------").append("</lf>");
            mask.setMask("##.###.###/####-##");
            buffer.append("CPF/CNPJ do consumidor:").append(mask.valueToString(eElement.getElementsByTagName("CNPJ").item(0).getTextContent())).append("</lf>");
            buffer.append("Raz�o Social/Nome:<nome do consumidor></lf>");
            
            //Informa��es dos produtos
            //satFCe completo
            buffer.append("----------------------------------------------------------------").append("</lf>");
            buffer.append("# | COD | DESC | QTD | UN | VL UN R$ |  (VLTR R$ )* | VL ITEM R$").append("</ne></lf>");
            buffer.append("----------------------------------------------------------------").append("</lf>");
            NodeList produtos = eElement.getElementsByTagName("prod");
            for(int i = 0; i < produtos.getLength(); i++){
                buffer.append(eElement.getAttribute("det"));
                String n = ""+i+1;
                buffer.append(i+1).append(String.format("%"+(4-n.length())+"s",""));
                String cProd = produtos.item(i).getChildNodes().item(0).getTextContent();
                cProd = cProd.substring(cProd.length()-2, cProd.length());
                buffer.append(cProd).append(String.format("%"+(4-cProd.length())+"s",""));
                String prodName = produtos.item(i).getChildNodes().item(1).getTextContent();
                if (prodName.length() > 20){
                    buffer.append(prodName.substring(0, 20)).append(String.format("%3s",""));
                }else
                    buffer.append(prodName).append(String.format("%"+(23-prodName.length())+"s",""));
                String qCom = produtos.item(i).getChildNodes().item(5).getTextContent();
                buffer.append(qCom).append(" ");
                String uCom = produtos.item(i).getChildNodes().item(4).getTextContent();
                buffer.append(uCom).append(" X ");
                
                String vUnCom = produtos.item(i).getChildNodes().item(6).getTextContent();
                buffer.append(vUnCom).append(String.format("%"+(8-vUnCom.length())+"s",""));
                buffer.append("(").append(eElement.getElementsByTagName("vItem12741").item(0).getTextContent()).append(")");
                String vProd = produtos.item(i).getChildNodes().item(7).getTextContent();
                buffer.append(String.format("%8s",vProd)).append("</lf>");
                
                int remain = prodName.length() - 20;
                while (remain > 0) {
                    if (remain > 20) {
                        buffer.append(String.format("%8s","")).append(prodName.substring(prodName.length() - remain, prodName.length() - remain + 20)).append("</lf>");
                    }else
                        buffer.append(String.format("%8s","")).append(prodName.substring(prodName.length() - remain, prodName.length())).append("</lf></lf></lf>");
                    remain -= 20;
                }
            }
            buffer.append("________________________________________________________________</lf>");
            String vTotal = eElement.getElementsByTagName("vProd").item(0).getTextContent();
            buffer.append("Total bruto de itens:").append(String.format("%"+(64-21)+"s",vTotal)).append("</lf>");
            String desconto = eElement.getElementsByTagName("vDesc").item(0).getTextContent();
            buffer.append("Total de desconto/acréscimo sobre item:").append(String.format("%"+(64-39)+"s",desconto)).append("</lf>");
            //String desub = eElement.getElementsByTagName("vAcresSubtot").item(0).getTextContent();
            //buffer.append("Acréscimo sobre subtotal:").append(String.format("%"+(64-25)+"s",desub)).append("</lf>");
            //String discountSub = eElement.getElementsByTagName("vDescSubtot").item(0).getTextContent();
            //buffer.append("Desconto sobre subtotal:").append(String.format("%"+(64-24)+"s",discountSub)).append("</lf>");
            String vPago = eElement.getElementsByTagName("vCFe").item(0).getTextContent();
            buffer.append("<ne>").append("Total R$:").append(String.format("%"+(64-9)+"s",vPago)).append("</lf></ne>");
            buffer.append("________________________________________________________________</lf>");
            String cMP = eElement.getElementsByTagName("cMP").item(0).getTextContent();
            String typeOfPayment;
            switch (cMP) {
                case "01":
                    typeOfPayment = "Dinheiro";
                    break;
                case "02": 
                    typeOfPayment = "Cheque";
                    break;
                case "03": 
                    typeOfPayment = "Cart�o de Cr�dito";
                    break;
                case "04": 
                    typeOfPayment = "Cart�o de D�bito";
                    break;
                case "05": 
                    typeOfPayment = "Cr�dito Loja";
                    break;
                case "10": 
                    typeOfPayment = "Vale Alimentação";
                    break;
                case "11":
                    typeOfPayment = "Vale Refeição";
                    break;
                case "12":
                    typeOfPayment = "Vale Presente";
                    break;
                case "13":
                    typeOfPayment = "Vale Combustível";
                    break;
                case "14":
                    typeOfPayment = "Duplicata Mercantil";
                    break;
                case "15": 
                    typeOfPayment = "Boleto Bancario";
                    break;
                case "90": 
                    typeOfPayment = "Sem Pagamento";
                    break;
                case "99": 
                    typeOfPayment = "Outros";
                    break;
                default:
                {
                        return "Invalid option for TYPE OF PAYMENT.";
                }
            }
            
            buffer.append("FORMA DE PAGAMENTO").append(String.format("%"+(64-18)+"s", "VALOR PAGO")).append("</lf>");
            String vMP = eElement.getElementsByTagName("vMP").item(0).getTextContent();
            buffer.append(typeOfPayment).append(String.format("%"+(64-typeOfPayment.length())+"s", vMP)).append("</lf>");
            String vTroco = eElement.getElementsByTagName("vTroco").item(0).getTextContent();
            buffer.append("Troco R$").append(String.format("%"+(64-typeOfPayment.length())+"s", vTroco)).append("</lf>");
            buffer.append(eElement.getAttribute("obsFisco")).append("</lf>");
            buffer.append("<ce>").append(eElement.getElementsByTagName("xTexto").item(0).getTextContent()).append("</ce></lf>");
            buffer.append("----------------------------------------------------------------").append("</lf>");
            buffer.append("DADOS PARA ENTREGA").append("</lf>");
            String rua = eElement.getElementsByTagName("xLgr").item(0).getTextContent();
            String nro = eElement.getElementsByTagName("nro").item(0).getTextContent();
            String cpl = eElement.getElementsByTagName("xCpl").item(0).getTextContent();
            String bairro = eElement.getElementsByTagName("xBairro").item(0).getTextContent();
            String mun = eElement.getElementsByTagName("xMun").item(0).getTextContent();
            String cep = eElement.getElementsByTagName("CEP").item(0).getTextContent();
            mask.setMask("#####-###");
            buffer.append("Endereço: ");
            buffer.append(rua).append(", ").append(nro).append(" (").append(cpl).append(") ").append(bairro).append(" - ").append(mun).append(", ");
            buffer.append("CEP: ").append(mask.valueToString(cep)).append("</lf>");
            buffer.append("----------------------------------------------------------------").append("</lf>");
            buffer.append("OBSERVAÇÕES DO CONTRIBUINTE").append("</lf>");
            String infCpl = eElement.getElementsByTagName("infCpl").item(0).getTextContent().split(";")[0];
            buffer.append(infCpl).append(";</lf>");
            //buffer.append(eElement.getAttribute("vCFeLei12741")).append("</lf>");
            buffer.append("----------------------------------------------------------------").append("</lf>");
            mask.setMask("###.###.###");
            buffer.append("<ce><ne>").append("SAT No. ").append(mask.valueToString(eElement.getElementsByTagName("nserieSAT").item(0).getTextContent())).append("</ne></lf>");
            mask.setMask("####/##/##");
            buffer.append(mask.valueToString(eElement.getElementsByTagName("dEmi").item(0).getTextContent()));
            mask.setMask("##:##:##");
            buffer.append(" - ").append(mask.valueToString(eElement.getElementsByTagName("hEmi").item(0).getTextContent())).append("</lf>");
            mask.setMask("#### #### #### #### #### #### #### #### #### #### #### ");
            buffer.append(mask.valueToString(xmlNfKey)).append("</lf>");
            buffer.append("<cbar>code128c,2,100,1,").append(mask.valueToString(xmlNfKey)).append("</cbar></lf>");
            //buffer.append("Consulte o QR Code pelo aplicativo [nome_do_aplicativo], dispon�vel na AppStore(Apple) e PlayStore(Android)").append("</lf>");
            buffer.append("<qr>5,48,").append(buildQRCode(eElement)).append("</qr></lf>");
            buffer.append("Consulte o QR Code pelo aplicativo [nome_do_aplicativo], disponível na AppStore(Apple) e PlayStore(Android)").append("</lf></gui>");
        } catch (ParseException ex) {
            Logger.getLogger(printCouponSAT.class.getName()).log(Level.SEVERE, null, ex);
        }
        return buffer.toString();

    }
    
    public static String satCFeSimples(Element eElement){
        StringBuilder buffer = new StringBuilder();
        //buffer.append(' ');
        try {
            MaskFormatter mask = new MaskFormatter("#####-###");
            mask.setValueContainsLiteralCharacters(false);
            //Coupon satFCeSimple
                    
            buffer.append("<c>").append("----------------------------------------------------------------").append("</lf>");
            mask.setMask("##.###.###/####-##");
            buffer.append("CPF/CNPJ do consumidor:").append(mask.valueToString(eElement.getElementsByTagName("CNPJ").item(0).getTextContent())).append("</lf>");
            buffer.append("<ne>").append("Total R$: ").append(eElement.getElementsByTagName("vCFe").item(0).getTextContent()).append("</ne></lf>");
            buffer.append("----------------------------------------------------------------").append("</lf>");
            buffer.append(eElement.getElementsByTagName("infCpl").item(0).getTextContent()).append("</lf>");
            buffer.append(eElement.getAttribute("vCFeLei12741")).append("</lf>");
            buffer.append("----------------------------------------------------------------").append("</lf>");
            mask.setMask("###.###.###");
            buffer.append("<ce><ne>").append("SAT No. ").append(mask.valueToString(eElement.getElementsByTagName("nserieSAT").item(0).getTextContent())).append("</ne></lf>");
            mask.setMask("####/##/##");
            buffer.append(mask.valueToString(eElement.getElementsByTagName("dEmi").item(0).getTextContent()));
            mask.setMask("##:##:##");
            buffer.append(" - ").append(mask.valueToString(eElement.getElementsByTagName("hEmi").item(0).getTextContent())).append("</lf>");
            buffer.append(eElement.getAttribute("infCFe")).append("</lf>");
            //buffer.append("<qr>5,48,qrCode").append(eElement.getElementsByTagName("qrCode").item(0).getTextContent()).append("</qr></lf>");
            buffer.append("Consulte o QR Code pelo aplicativo [nome_do_aplicativo], disponível na AppStore(Apple) e PlayStore(Android)").append("</lf></gui>");
         } catch (ParseException ex) {
            Logger.getLogger(printCouponSAT.class.getName()).log(Level.SEVERE, null, ex);
        }
        return buffer.toString();

    }
    
    public static String satCancellation(Element eElement){
        StringBuilder buffer = new StringBuilder();
        //buffer.append(' ');
        try {
            MaskFormatter mask = new MaskFormatter();
            mask.setValueContainsLiteralCharacters(false);                    
            //Coupon Cancellation
                    
            buffer.append("<ce><ne>").append("CANCELAMENTO").append("</ce></ne></lf>");
            buffer.append("<c>").append("----------------------------------------------------------------").append("</lf>");
            buffer.append("DADOS DO CUPOM ELETR�NICO CANCELADO").append("</lf>");
            mask.setMask("##.###.###/####-##");
            buffer.append("CPF/CNPJ do consumidor:").append(mask.valueToString(eElement.getElementsByTagName("CNPJ").item(0).getTextContent())).append("</lf>");
            buffer.append("<ne>").append("Total R$: ").append(eElement.getElementsByTagName("vCFe").item(0).getTextContent()).append("</ne></lf>");
            mask.setMask("###.###.###");
            buffer.append("<ce><ne>").append("SAT No. ").append(mask.valueToString(eElement.getElementsByTagName("nserieSAT").item(0).getTextContent())).append("</ne></lf>");
            mask.setMask("####/##/##");
            buffer.append(mask.valueToString(eElement.getElementsByTagName("dEmi").item(0).getTextContent()));
            mask.setMask("##:##:##");
            buffer.append(" - ").append(mask.valueToString(eElement.getElementsByTagName("hEmi").item(0).getTextContent())).append("</lf>");
            mask.setMask("#### #### #### #### #### #### #### #### #### #### #### ");
            buffer.append(mask.valueToString(xmlNfKey)).append("</lf>");
            buffer.append("<qr>5,48,").append(buildQRCode(eElement)).append("</qr></c>");
            
            buffer.append("<c>").append("----------------------------------------------------------------").append("</lf>");
            buffer.append("DADOS DO CUPOM ELETRÔNICO CANCELADO").append("</lf>");
            
            buffer.append("<ce><ne>").append("SAT No. ").append(mask.valueToString(eElement.getElementsByTagName("nserieSAT").item(0).getTextContent())).append("</ne></lf>");
            mask.setMask("####/##/##");
            buffer.append(mask.valueToString(eElement.getElementsByTagName("dEmi").item(0).getTextContent()));
            mask.setMask("##:##:##");
            buffer.append(" - ").append(mask.valueToString(eElement.getElementsByTagName("hEmi").item(0).getTextContent())).append("</lf>");
            mask.setMask("#### #### #### #### #### #### #### #### #### #### #### ");
            buffer.append(mask.valueToString(xmlNfKey)).append("</lf>");
            buffer.append("<qr>5,48,").append(buildQRCode(eElement)).append("</qr></c></lf>");
            buffer.append("Consulte o QR Code pelo aplicativo [nome_do_aplicativo],disponível na AppStore(Apple) e PlayStore(Android)").
                    append("</gui>");
            //buffer.append("<cbar>code128,2,100,1,234565567").append("</cbar></lf></ce></gui>");
         } catch (ParseException ex) {
            Logger.getLogger(printCouponSAT.class.getName()).log(Level.SEVERE, null, ex);
        }
        return buffer.toString();

    }
    
    public static String buildQRCode(Element eElement) {
        StringBuilder buffer = new StringBuilder();
        String xmlNfKey = eElement.getElementsByTagName("infCFe").item(0).getAttributes().item(0).getTextContent().substring(3);
        System.out.println("xmlNfKey: "+xmlNfKey);
        buffer.append(xmlNfKey).append("|");
        buffer.append(eElement.getElementsByTagName("dEmi").item(0).getTextContent());
        buffer.append(eElement.getElementsByTagName("vCFe").item(0).getTextContent()).append("|");
        buffer.append(eElement.getElementsByTagName("CNPJ").item(0).getTextContent());
        return buffer.toString();
   }
}