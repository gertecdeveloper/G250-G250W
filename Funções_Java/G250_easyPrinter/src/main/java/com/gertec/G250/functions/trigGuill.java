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
public class trigGuill {
    
     public static final byte[] TRIG_GUILL = {0x1d, 0x56, 0x41, 0x00}; // cortar papel
    
    public static void trigGuill(Socket socket) throws Exception{
        sendBinBuffer.sendBinBuffer(TRIG_GUILL, socket);
    }
    
    public static void trigGuill(NRSerialPort serialPort) throws Exception{
        sendBinBuffer.sendBinBuffer(TRIG_GUILL, serialPort);
    }
    
    /*public static void trigGuill(PrintService printService) throws Exception{
        sendBinBuffer.sendBinBuffer(TRIG_GUILL, printService);
    }
    
    public static void trigGuill(String host, int port) throws Exception{
        sendBinBuffer.sendBinBuffer(TRIG_GUILL, host, port);
    }*/
    
}
