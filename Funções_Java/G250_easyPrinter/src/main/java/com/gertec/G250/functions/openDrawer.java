/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gertec.G250.functions;

import gnu.io.NRSerialPort;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.net.Socket;
import jssc.SerialPort;


/**
 *
 * @author AMP
 */
public class openDrawer{
    
    public static byte [] PULSE_PIN_COMMAND = {27,112, 48, 50, 50};
        
    public static void openDrawer(NRSerialPort serialPort) throws Exception{
        sendBinBuffer.sendBinBuffer(PULSE_PIN_COMMAND, serialPort);
    }
    
    public static void openDrawer(Socket socket) throws Exception{
        sendBinBuffer.sendBinBuffer(PULSE_PIN_COMMAND, socket);
    }
    
}
