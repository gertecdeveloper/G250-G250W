/*
 * 
 */
package com.gertec.G250.functions;

import gnu.io.NRSerialPort;
import java.net.Socket;
import jssc.SerialPort;

/**
 *
 * @author AMP
 */
public class printEAN13 {
    
    public static void printEAN13(int hight, int width, int  hri,
            String barCode, NRSerialPort serialPort) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, "ean13", 
                barCode, serialPort);
        
    }
    
    public static void printEAN13(String barCode, NRSerialPort serialPort) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, 2, 1, "ean13", barCode, serialPort);
        
    }
    
    public static void printEAN13(int hight, int width, int  hri,
            String barCode, Socket socket) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, "ean13", 
                barCode, socket);
        
    }
    
    public static void printEAN13(String barCode, Socket socket) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, 2, 1, "ean13", barCode, socket);
        
    }
    
    /*public static void printEAN13(String barCode, PrintService printService) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._2, 
                printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.EAN13, barCode, printService);
        
    }
    
    public static void printEAN13(int hight, printCustomBarCode.WIDTHVALUES width, 
            printCustomBarCode.HRI hri,
            String barCode, String host, int port) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, printCustomBarCode.TYPE.EAN13, 
                barCode, host, port);
        
    }
    
    public static void printEAN13(String barCode, String host, int port) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._2, 
                printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.EAN13, barCode, host, port);
        
    }*/
}
