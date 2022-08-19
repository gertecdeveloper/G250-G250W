/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gertec.G250.functions;

import gnu.io.NRSerialPort;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import jssc.SerialPortException;

/**
 *
 * @author AMP
 */
public class closeDoor {
    
    public static void closeDoor(OutputStream outputStream) throws IOException{
        outputStream.close();
    }
    
    public static void closeDoor(NRSerialPort serialPort) throws IOException, SerialPortException{
        serialPort.disconnect();
    }
    
    /*public static void closeDoor(jssc.SerialPort serialPort) throws IOException, SerialPortException{
        serialPort.closePort();
    }*/
    
    public static void closeDoor(Socket socket) throws IOException{
        socket.close();
    }
    
}
