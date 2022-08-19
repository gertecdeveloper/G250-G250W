package com.gertec.G250.functions;

import gnu.io.NRSerialPort;
import java.io.IOException;
import java.net.Socket;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author adels
 */
public class returnSerialNumber {
    
    public static byte [] RETURN_SERIAL_NUMBER = {0x1D, 0x49, 0x44};
    
    public static String returnSerialNumber(NRSerialPort serialPort) throws IOException{
        String response = "";
        char temp;
        serialPort.getOutputStream().write(RETURN_SERIAL_NUMBER);
        temp = (char) serialPort.getInputStream().read();
        while(temp != 0){
            temp = (char) serialPort.getInputStream().read();
            response += temp;
        }
        //System.out.println("Response: "+response);
        return response;
    }
    
    public static String returnSerialNumber(Socket socket) throws IOException{
        String response = "";
        char temp;
        socket.getOutputStream().write(RETURN_SERIAL_NUMBER);
        temp = (char) socket.getInputStream().read();
        while(temp != 0){
            temp = (char) socket.getInputStream().read();
            response += temp;
        }
        //System.out.println("Response: "+response);
        return response;
    }
    
}
