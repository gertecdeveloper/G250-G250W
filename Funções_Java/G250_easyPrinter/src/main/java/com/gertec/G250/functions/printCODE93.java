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
public class printCODE93 {
    
    public static void printCODE93(int hight, int width,int  hri,
            String barCode, NRSerialPort serialPort) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, "code93", 
                barCode, serialPort);
        
    }
    
    public static void printCODE93(String barCode, NRSerialPort serialPort) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, 2, 1, "code93", barCode, serialPort);
        
    }
    
    public static void printCODE93(int hight, int width,int  hri,
            String barCode, Socket socket) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, "code93", 
                barCode, socket);
        
    }
    
    public static void printCODE93(String barCode, Socket socket) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, 2, 1, "code93", barCode, socket);
        
    }
    
    /*public static void printCODE93(String barCode, String port) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._2, 
                printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.CODE93, barCode, port);
        
    }
    
    public static void printCODE93(String barCode, PrintService printService) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._2, 
                printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.CODE93, barCode, printService);
        
    }
    
    public static void printCODE93(int hight, printCustomBarCode.WIDTHVALUES width, 
            printCustomBarCode.HRI hri,
            String barCode, String host, int port) throws Exception{
        
        printCustomBarCode.printCustomBarCode(hight, width, hri, printCustomBarCode.TYPE.CODE93, 
                barCode, host, port);
        
    }
    
    public static void printCODE93(String barCode, String host, int port) throws Exception{
        
        printCustomBarCode.printCustomBarCode(100, printCustomBarCode.WIDTHVALUES._2, 
                printCustomBarCode.HRI.ABOVE, printCustomBarCode.TYPE.CODE93, barCode, host, port);
        
    }*/
}
