/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gertec.G250.functions;

import gnu.io.NRSerialPort;
import java.net.Socket;

/**
 *
 * @author AMP
 */
public class printUPCA {
    
    public static void printUPCA(int hight, int width, int hri,
            String barCode, NRSerialPort serialPort) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, "upca", 
                barCode, serialPort);
        
    }
    
    public static void printUPCA(String barCode, NRSerialPort serialPort) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, 2, 1,  "upca", barCode, serialPort);
        
    }
    
    public static void printUPCA(int hight, int width, int hri,
            String barCode, Socket socket) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, "upca", 
                barCode, socket);
        
    }
    
    public static void printUPCA(String barCode, Socket socket) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, 2, 1,  "upca", barCode, socket);
        
    }
    
    /*public static void printUPCA(String barCode, PrintService printService) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._2, 
                printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.UPCA, barCode, printService);
        
    }
    
    public static void printUPCA(int hight, printCustomBarCode.WIDTHVALUES width, 
            printCustomBarCode.HRI hri,
            String barCode, String host, int port) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, printCustomBarCode.TYPE.UPCA, 
                barCode, host, port);
        
    }
    
    public static void printUPCA(String barCode, String host, int port) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._2, 
                printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.UPCA, barCode, host, port);
        
    }*/
}
