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
public class printCODE39 {
    
    
    
    public static void printCODE39(String barCode, NRSerialPort serialPort) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, 2, 1, "code39", barCode, serialPort);
        
    }
    
    public static void printCODE39(int hight, int width, 
            int hri, String barCode, NRSerialPort serialPort) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, "code39", 
                barCode, serialPort);
        
    }
    
    public static void printCODE39(String barCode, Socket socket) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, 2, 1, "code39", barCode, socket);
        
    }
    
    public static void printCODE39(int hight, int width, 
            int hri, String barCode, Socket socket) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, "code39", 
                barCode, socket);
        
    }
    
    /*public static void printCODE39(String barCode, PrintService printService) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._2, 
                printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.CODE39, barCode, printService);
        
    }
    
    public static void printCODE39(String barCode, String host, int port) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._2, 
                printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.CODE39, barCode, host, port);
        
    }
    
    public static void printCODE39(int hight, printCustomBarCode.WIDTHVALUES width, 
            printCustomBarCode.HRI hri,
            String barCode, String host, int port) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, printCustomBarCode.TYPE.CODE39, 
                barCode, host, port);
        
    }
    
    public static void printCODE39(int hight, printCustomBarCode.WIDTHVALUES width, 
            printCustomBarCode.HRI hri,
            String barCode, String port) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, printCustomBarCode.TYPE.CODE39, 
                barCode, port);
        
    }*/
}
