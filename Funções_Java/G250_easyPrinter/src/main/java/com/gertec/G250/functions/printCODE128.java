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
public class printCODE128 {
    
    public static void printCODE128(String barCode, NRSerialPort serialPort) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, 2, 1, "code128",barCode, serialPort);
        
    }
    
    public static void printCODE128(String barCode, Socket socket) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, 2, 1, "code128",barCode, socket);
        
    }
    
    
    public static void printCODE128(int hight, int width, 
            int hri, String barCode, NRSerialPort serialPort) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, "CODE128", 
                barCode, serialPort);
        
    }
    
    public static void printCODE128(int hight, int width, 
            int hri, String barCode, Socket socket) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, "CODE128", 
                barCode, socket);
        
    }
    
    
    
    /*public static void printCODE128(String barCode, String port) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._2, 
                printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.CODE128, 
                barCode, port);
        
    }
    
    
    public static void printCODE128(String barCode, String host, int port) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._2, 
                printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.CODE128, 
                barCode, host, port);
        
    }
    
    public static void printCODE128(String barCode, PrintService printService) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._2, 
                printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.CODE128, barCode,
                printService);
        
    }*/
    
//    public static void printCODE128(int hight, printCustomBarCode.WIDTHVALUES width, 
//            printCustomBarCode.HRI hri,
//            String barCode, String host, int port) throws Exception{
//        
//        printCustomBarCode.printCustomBarCode(hight, width, hri, printCustomBarCode.TYPE.CODE128, 
//                barCode, host, port);
//        
//    }
    
    
    
}
