/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gertec.G250.functions;

import gnu.io.NRSerialPort;
import java.net.Socket;
import jssc.SerialPort;

/**
 *
 * @author AMP
 */
public class printUPCE {

    public static void printUPCE(int hight, int width, int hri,
            String barCode, NRSerialPort serialPort) throws Exception {

        printCustomBarCode.printCustomBarCode(hight, width, hri, "upce",
                barCode, serialPort);

    }

    public static void printUPCE(String barCode, NRSerialPort serialPort) throws Exception {

        printCustomBarCode.printCustomBarCode(100, 2, 1, "upce", barCode, serialPort);

    }
    
    public static void printUPCE(int hight, int width, int hri,
            String barCode, Socket socket) throws Exception {

        printCustomBarCode.printCustomBarCode(hight, width, hri, "upce",
                barCode, socket);

    }

    public static void printUPCE(String barCode, Socket socket) throws Exception {

        printCustomBarCode.printCustomBarCode(100, 2, 1, "upce", barCode, socket);

    }

    /*public static void printUPCE(String barCode, PrintService printService) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._2, 
                printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.UPCE, barCode, printService);
        
    }
    
    public static void printUPCE(int hight, printCustomBarCode.WIDTHVALUES width, 
            printCustomBarCode.HRI hri,
            String barCode, String host, int port) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, printCustomBarCode.TYPE.UPCE, 
                barCode, host, port);
        
    }
    
    public static void printUPCE(String barCode, String host, int port) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._2, 
                printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.UPCE, barCode, host, port);
        
    }*/
}
