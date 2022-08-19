/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gertec.G250.functions;

import gnu.io.NRSerialPort;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
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
 * @author adels
 */
public class printCouponNFCe {
    
    private static final byte[] RESTART = {0x1b, 0x40}; // limpa os dados e reset as configurações da impressora

    
    public static void printCouponNFCe(String path, int type, Socket socket) throws IOException, Exception {
        sendBinBuffer.sendBinBuffer(RESTART, socket);
        printText.printText(printCouponNFCeFactory(path, type), socket);
        System.out.println(printCouponNFCeFactory(path, type));
    }

    public static void printCouponNFCe(String path, int type, NRSerialPort serial) throws IOException, Exception {
        sendBinBuffer.sendBinBuffer(RESTART, serial);
        printText.printText(printCouponNFCeFactory(path, type), serial);
        //System.out.println(printCouponNFCeFactory(path, type));
    }
    
    public static String printCouponNFCeFactory(String path, int type) {
        StringBuilder buffer = new StringBuilder();
        String nfceType, nfceObs;
        switch (type) {
            case 1:
                nfceType = "Documento Auxiliar da Nota Fiscal de Consumidor Eletrônica";
                nfceObs = "Não permite aproveitamento de crédito do ICMS";
                break;
            case 2:
                nfceType = "EMITIDA EM CONTIGÊNCIA";
                nfceObs = "Pendente de autorização";
                break;
            case 3:
                nfceType = "CANCELAMENTO";
                nfceObs = "Pendente de autorização";
                break;
            default:
                throw new IllegalArgumentException("Tipo de Coupon inválido. Os valores válidos são 1 (NFCE)"
                    + ", 2 (Contigência) ou 3 (Cancelamento)");
        }
        try{
            File file = new File (path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();
            NodeList nList = document.getElementsByTagName("nfeProc");
            for(int temp = 0; temp < nList.getLength(); temp++){
                Node nNode = nList.item(temp);
                Element eElement = (Element) nNode;
                if (nNode.getNodeType() == Node.ELEMENT_NODE){
                    
                    //cabeçalho
                    String cnpj = eElement.getElementsByTagName("CNPJ").item(0).getTextContent();
                    MaskFormatter mask = new MaskFormatter("###.###.###/####-##");
                    mask.setValueContainsLiteralCharacters(false);
                    buffer.append("<ce><c>").append("CNPJ:").append(mask.valueToString(cnpj)).append("</lf>");
                    buffer.append("<ne>").append(eElement.getElementsByTagName("xNome").item(0).getTextContent()).append("</ne></lf>");
                    buffer.append(eElement.getElementsByTagName("xLgr").item(0).getTextContent());
                    buffer.append(", ").append(eElement.getElementsByTagName("nro").item(0).getTextContent());
                    buffer.append(", ").append(eElement.getElementsByTagName("xBairro").item(0).getTextContent());
                    buffer.append(", ").append(eElement.getElementsByTagName("xMun").item(0).getTextContent());
                    buffer.append(", ").append(eElement.getElementsByTagName("UF").item(0).getTextContent()).append("</lf></lf>");
                    buffer.append("<ne>").append(nfceType).append("</ne></lf>");
                    buffer.append(nfceObs).append("</lf></lf>");
                    
                    //Informações dos produtos
                    buffer.append("DETALHE DA VENDA").append("</ce><c></lf>");
                    buffer.append("________________________________________________________________</lf>");
                    buffer.append("<ne>Código  Descrição                Qtde UN      Vl Unit   Vl Total</ne>").append("</ne></lf>");
                    NodeList produtos = eElement.getElementsByTagName("prod");
                    for(int i = 0; i < produtos.getLength(); i++){
                        String cProd = produtos.item(i).getChildNodes().item(0).getTextContent();
                        buffer.append(cProd.substring(cProd.length()-6, cProd.length())).append(String.format("%2s",""));
                        String prodName = produtos.item(i).getChildNodes().item(2).getTextContent();
                        if (prodName.length() > 20){
                            buffer.append(prodName.substring(0, 20)).append(String.format("%4s",""));
                        }else
                            buffer.append(prodName).append(String.format("%"+(24-prodName.length())+"s",""));
                        String qCom = produtos.item(i).getChildNodes().item(7).getTextContent();
                        buffer.append(qCom).append(String.format("%"+(8-qCom.length())+"s",""));
                        String uCom = produtos.item(i).getChildNodes().item(6).getTextContent();
                        buffer.append(uCom).append(String.format("%"+(6-uCom.length())+"s",""));
                        String vUnCom = produtos.item(i).getChildNodes().item(8).getTextContent();
                        buffer.append(vUnCom).append(String.format("%"+(13-vUnCom.length())+"s",""));
                        String vProd = produtos.item(i).getChildNodes().item(9).getTextContent();
                        buffer.append(String.format("%5s",vProd)).append("</lf>");
                        
                        int remain = prodName.length() - 20;
                        while (remain > 0) {                            
                            if (remain > 20) {
                                buffer.append(String.format("%8s","")).append(prodName.substring(prodName.length() - remain, prodName.length() - remain + 20)).append("</lf>");
                            }else
                                buffer.append(String.format("%8s","")).append(prodName.substring(prodName.length() - remain, prodName.length())).append("</lf>");
                            remain -= 20;
                        }
                    }
                    buffer.append("________________________________________________________________</lf>");
                    String qtdItens = eElement.getElementsByTagName("indTot").item(0).getTextContent();
                    buffer.append("Qtde. total de Itens:").append(String.format("%"+(43)+"s",qtdItens)).append("</lf>");
                    String vTotal = eElement.getElementsByTagName("vPag").item(0).getTextContent();
                    buffer.append("Valor Total R$:").append(String.format("%"+(64-15)+"s",vTotal)).append("</lf>");
                    String desconto = eElement.getElementsByTagName("vDesc").item(0).getTextContent();
                    buffer.append("Desconto R$:").append(String.format("%"+(64-11)+"s",desconto)).append("</lf>");
                    String frete = eElement.getElementsByTagName("vFrete").item(0).getTextContent();
                    buffer.append("Frete R$:").append(String.format("%"+(64-9)+"s",frete)).append("</lf>");
                    String vPagar = eElement.getElementsByTagName("vNF").item(0).getTextContent();
                    buffer.append("<ne>").append("Valor a Pagar R$:").append(String.format("%"+(64-17)+"s",vPagar)).append("</lf></ne>");
                    buffer.append("________________________________________________________________</lf>");
                    
                    //Informações de Totais do DANFE NFC-e
                    String tPag = eElement.getElementsByTagName("tPag").item(0).getTextContent();
                    String typeOfPayment;
                    switch (tPag) {
                        case "01": typeOfPayment = "Dinheiro"; break;
                        case "02": typeOfPayment = "Cheque"; break;
                        case "03": typeOfPayment = "Cartão de Crédito"; break;
                        case "04": typeOfPayment = "Cartão de Débito"; break;
                        case "05": typeOfPayment = "Crédito Loja"; break;
                        case "10": typeOfPayment = "Vale Alimentação"; break;
                        case "11": typeOfPayment = "Vale Refeição"; break;
                        case "12": typeOfPayment = "Vale Presente"; break;
                        case "13": typeOfPayment = "Vale Combustível"; break;
                        case "14": typeOfPayment = "Duplicata Mercantil"; break;
                        case "15": typeOfPayment = "Boleto Bancário"; break;
                        case "90": typeOfPayment = "Sem Pagamento"; break;
                        case "99": typeOfPayment = "Outros"; break;
                        default:
                            return "Invalid option for TYPE OF PAYMENT.";
                    }
                    
                    buffer.append("FORMA DE PAGAMENTO").append(String.format("%"+(64-16)+"s", "VALOR PAGO")).append("</lf>");
                    //Verificar esse vPag
                    String vPag = eElement.getElementsByTagName("vPag").item(0).getTextContent();
                    buffer.append(typeOfPayment).append(String.format("%"+(64-typeOfPayment.length())+"s", vPag)).append("</lf>");
                    buffer.append("________________________________________________________________</lf></lf>");
                    buffer.append("<ce><ne>Consulte pela Chave de Acesso em").append("</lf></ne>");
                    buffer.append(eElement.getElementsByTagName("urlChave").item(0).getTextContent()).append("</lf>");
                    mask.setMask("#### #### #### #### #### #### #### #### #### #### ####");
                    buffer.append(mask.valueToString(eElement.getElementsByTagName("chNFe").item(0).getTextContent())).append("<c></lf>");
                    buffer.append("<qr>5,48,").append(eElement.getElementsByTagName("qrCode").item(0).getTextContent()).append("</qr></lf>");
                    buffer.append("Protocolo de Autorização</lf>");
                    buffer.append(eElement.getElementsByTagName("nProt").item(0).getTextContent()).append("</lf>");
                    buffer.append("Data de Emissão: ").append(eElement.getElementsByTagName("dhEmi").item(0).getTextContent()).append("</ce></lf></gui>");
                }
            }
            
            
           
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(printCouponNFCe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(printCouponNFCe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(printCouponNFCe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(printCouponNFCe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return buffer.toString();
    }
    
}
