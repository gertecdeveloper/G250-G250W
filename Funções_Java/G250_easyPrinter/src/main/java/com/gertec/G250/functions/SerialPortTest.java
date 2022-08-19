/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gertec.G250.functions;

import gnu.io.NRSerialPort;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.*;

import jssc.SerialPort;

/**
 *
 * @author adels
 */
public class SerialPortTest {
    
    public static void main(String[] args) {
        try {
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
            //Socket socket = openDoor.openDoor("192.168.1.100",9100);
            //SerialPort serialPort = openDoor.openDoor("Com3",0,0);
            //sendBinBuffer.sendBinBuffer("teste de impressao".getBytes(), serialPort);
            //returnStatus.returnStatus(socket);
            //closeDoor.closeDoor(socket);
            //closeDoor.closeDoor(serialPort);
            //NRSerialPort serial = openDoor.openDoor("com3");
            //Socket socket = openDoor.openDoor("192.168.1.100", 9100);
            //returnStatus.returnStatus(serial);
//            returnSerialNumber.returnSerialNumber(serial);
//            returnModel.returnModel(serial);
//            returnFirmware.returnFirmware(serial);
            //returnStatus.pegaBits();
            //
            /*UsbServices services = UsbHostManager.getUsbServices();
            UsbHub root = services.getRootUsbHub();
            listaPerifericos(root);*/
            //printText.printText(text, serial);
            //returnStatus.returnStatus(serial);
           //closeDoor.closeDoor(serial);
           String str = "129018";
            String str2 = String.format("%"+10+"s", str);
            System.out.println(str2);
           //Usando nio.files
//            ByteBuffer byteBuffer = ByteBuffer.wrap(text.getBytes());
//            Set options = new HashSet();
//            //options.add(StandardOpenOption.CREATE);
//            options.add(StandardOpenOption.APPEND);
//
//            Path file = Paths.get("COM3");
//            
//            SeekableByteChannel byteChannel = Files.newByteChannel(file, options);
//            byteChannel.write(byteBuffer);
        } catch (Exception ex) {
            Logger.getLogger(SerialPortTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*private static void listaPerifericos(UsbHub root) throws UsbException {
        List perifericos = root.getAttachedUsbDevices();
        Iterator i = perifericos.iterator();
        while(i.hasNext()){
                UsbDevice d = (UsbDevice) i.next();
                System.out.println(d.toString());
                if(d.isUsbHub())
                    listaPerifericos((UsbHub) d);

        }
    }*/
    
}
