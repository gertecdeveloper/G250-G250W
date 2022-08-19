/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gertec.G250.functions;

import java.awt.print.PrinterJob;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.OutputStream;
import javax.print.PrintService;

/**
 *
 * @author AMP
 */
public class G250EasyPrinterTest {
    
    public static void main(String[] args) {
                        
            OutputStream port;
            
            String text = "Testando texto de formatação com tags: </lf><ne>Texto Negrito</ne> & "
                    + "<su> texto sublinhado </su> além disso podemos </lf>"
                    + "<ne><ce> centralizar o texto em negrito </ce></ne></lf>"
                    + "e <c> diminuir a font </c> entre outras opções.</lf>"
                    + "<ald>Texto a Direita</ald></lf>"
                    + "<ex>Texto expandido</ex>"
                    + "<p>Texto normal </p>"
                    + "<da>Altura Dupla</da>"
                    + "<dl>largura Dupla</dl>"
                    + "<dal>Altura e largura Dupla</dal>"
                    + "<fi>Fonte invertida</fi>"
                    + "Teste de </ht> tab horizontal</lf>"
                    + "teste de código de barras</lf>"
                    + "<ce><cbar>code128,2,100,1,234565567</cbar></ce></lf>"
                    + "teste de QRCode</lf>"
                    + "<ce><qr>6,48,texto simples de qrCode via tag</qr></ce></lf>"
                    + "</lf></lf></gui>";
           //Abrir porta via rede
//           port = openDoor.openDoor("192.168.123.100", 9100);
           
           //Chamada via porta COM
           //port = openDoor.openDoor("COM3");
           
//           if(iniConfig.iniConfig("Com3"))
//                System.out.println("Sucesso");
//           if(iniConfig.iniConfig("192.168.1.100", "9100"))
//                System.out.println("Sucesso");
           
           //Abre a conexao via spool
//           PrintService pservice = getPrintServiceByName("G250");
//           port = openDoor.openDoor(pservice);
           
           //Testes de impressao de texto
//            byte[] RESTART = {0x1b, 0x40};
//            printText.lineFeed(1, port);
//            sendBinBuffer(RESTART, port);
//            sendBinBuffer("texto 1".getBytes(), port);
//            printText.printText(text, port);
//            printText.printText("teste de impressão de texto sem tags, "
//                    + "verificando se o problema e o tamanho do texto", port);
//            printText.lineFeed(4, port);
//           
//            //códigos de barras
//            printText.printText("Código COD93", port);
//            printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._1, 
//                    printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.CODE93, 
//                    "12345678910", port);
//           
//            printText.lineFeed(2, port);//Executa Line Feeds n vezes
//            printText.printText("Código CODE128", port);
//            printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._1, 
//                    printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.CODE128, "12345678910", port);
//            
//            printText.lineFeed(2, port);
//            printText.printText("Código CODE39", port);
//            printCustomBarCode.printCustomBarCode(200, printCustomBarCode.WIDTHVALUES._2, 
//                    printCustomBarCode.HRI.ABOVE, 
//                    printCustomBarCode.TYPE.CODE39, "12345678910", port);
//
//            printText.lineFeed(2, port);
//            printText.printText("Código UPCA", port);
//            printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._1, 
//                    printCustomBarCode.HRI.NONE, 
//                    printCustomBarCode.TYPE.UPCA, "123456789102", port);
//            
//            printText.lineFeed(2, port);
//            printText.printText("Código UPCE", port);
//            printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._2, 
//                    printCustomBarCode.HRI.BELOW, 
//                    printCustomBarCode.TYPE.UPCE, "123456789102", port);
//            
//            printText.lineFeed(2, port);
//            printText.printText("Código EAN8", port);
//            printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._1, 
//                    printCustomBarCode.HRI.ABOVE, 
//                    printCustomBarCode.TYPE.EAN8, "12345677", port);
//            
//            printText.lineFeed(2, port);
//            printText.printText("Código EAN13", port);
//            printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._1, 
//                    printCustomBarCode.HRI.ABOVE, 
//                    printCustomBarCode.TYPE.EAN13, "1234567891031", port);
//            
//            printText.lineFeed(2, port);
//            printText.printText("Código ITF", port);
//            printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._1, 
//                    printCustomBarCode.HRI.ABOVE, 
//                    printCustomBarCode.TYPE.ITF, "12345678910", port);
//            
//            printText.lineFeed(4, port);
//            trigGuill.trigGuill(port);
//            
//            printText.lineFeed(2, port);
//            printText.printText("Teste de QRCode", port);
//            printQRCODE.printQRCODE(5, printQRCODE.CORRECTION_LEVEL.L, "teste de impressão qrCode",
//                    port);
//            
//            printText.lineFeed(4, port);
//            trigGuill.trigGuill(port);
//            printText.lineFeed(2, port);
//            printText.printText("Testando as funções individuais de codigo de barras", port);
//            
//            printText.printText("Code128", port);
//            printCODE128.printCODE128("1245678945466", port);
//            printText.lineFeed(4, port);
//            trigGuill.trigGuill(port);
//            printText.printText("Code39", port);
//            printCODE39.printCODE39("012345678910", port);
//            printText.lineFeed(4, port);
//            trigGuill.trigGuill(port);
//            printText.printText("Code93", port);
//            printCODE93.printCODE93("012345678910", port);
//            printText.lineFeed(4, port);
//            trigGuill.trigGuill(port);
//            printText.printText("Code EAN8", port);
//            printEAN8.printEAN8("12345678", port);
//            printText.lineFeed(4, port);
//            trigGuill.trigGuill(port);
//            printText.printText("Code EAN13", port);
//            printEAN13.printEAN13("1234567891013", port);
//            printText.lineFeed(4, port);
//            trigGuill.trigGuill(port);
//            printText.printText("Code ITF", port);
//            printITF.printITF("012345678910", port);
//            printText.lineFeed(4, port);
//            trigGuill.trigGuill(port);
//            printText.printText("Code UPCA", port);
//            printUPCA.printUPCA("123456789604", port);
//            printText.lineFeed(4, port);
//            trigGuill.trigGuill(port);
//            printText.printText("Code UPCE", port);
//            printUPCE.printUPCE("012345678910", port);
//
//            printText.printText("Testando a impressão de imagens</lf>", port);
//            printText.goToCenter(port);//centraliza
//            //Envia o caminho da imagem para impressao.
//            //Também pode ser enviado um objeto de java.io.File
//            printBITMAP.printBITMAP("C:/Users/Adels/Pictures/personagem0.jpg", port);
//            
//            //aciona a guilhotina
//            printText.lineFeed(4, port);
//            trigGuill.trigGuill(port);
            
            //fecha a porta
            //closeDoor.closeDoor(port);
          
    }
    
    /*public static String[] getListPrintServicesNames() {
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        String[] printServicesNames = new String[printServices.length];
        for (int i = 0; i < printServices.length; i++) {
            printServicesNames[i] = printServices[i].getName();
            //System.out.println(""+printServices[i].getName());
        }
        return printServicesNames;
    }

    public static PrintService getDefaultPrintService() {
        PrintService foundService = PrintServiceLookup.lookupDefaultPrintService();
        if (foundService == null) {
            throw new IllegalArgumentException("Default Print Service is not found");
        }
        return foundService;

    }*/

    public static PrintService getPrintServiceByName(String printName) {
        PrintService[] printServices = PrinterJob.lookupPrintServices();
        PrintService foundService = null;
        
        if(printServices.length == 0){
            throw new IllegalArgumentException("Nenhuma impressora cadastrada!");
        }

        for (PrintService service : printServices) {
            if (service.getName().compareToIgnoreCase(printName) == 0 ||
                    service.getName().toLowerCase().contains(printName.toLowerCase())) {
                foundService = service;
                return foundService;
            }
        }
        
        if (foundService == null) {
            throw new IllegalArgumentException("Impressora não enontrada!");
        }
        return foundService;
    }
    
}
