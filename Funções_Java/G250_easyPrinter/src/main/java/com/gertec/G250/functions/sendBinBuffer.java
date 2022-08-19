/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gertec.G250.functions;

import gnu.io.NRSerialPort;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author AMP
 */
public class sendBinBuffer {
    
    public static void sendBinBuffer(byte [] buffer, Socket socket) throws Exception{
        socket.getOutputStream().write(buffer);
        socket.getOutputStream().flush();
    }
    
    public static void sendBinBuffer(byte [] buffer, NRSerialPort serialPort) throws Exception{
        serialPort.getOutputStream().write(buffer);
    }
    
    /*public static void sendBinBuffer(String buffer, OutputStream outputStream) throws Exception{
        outputStream.write(buffer.getBytes());
        outputStream.flush();
    }
        
    public static void sendBinBuffer(byte [] buffer, String port) throws Exception{
        OutputStream outputStream = openDoor.openDoor(port);
        outputStream.write(buffer);
        closeDoor.closeDoor(outputStream);          
    }
    
    public static void sendBinBuffer(byte [] buffer, PrintService printService) throws Exception{
        OutputStream outputStream = openDoor.openDoor(printService);
        outputStream.write(buffer);
        closeDoor.closeDoor(outputStream);          
    }
    
    public static void sendBinBuffer(byte [] buffer, String host, int port) throws Exception{
        OutputStream outputStream = openDoor.openDoor(host, port);
        outputStream.write(buffer);
        closeDoor.closeDoor(outputStream);          
    }
    
    public void printerReset(String port) throws Exception{
        sendBinBuffer.sendBinBuffer(RESTART, port);
    }
    
    public void printerReset(String host, int port) throws Exception{
        sendBinBuffer(RESTART, host, port);
    }*/
    
}
