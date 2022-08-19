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
public class returnModel {
    
    public static byte [] RETURN_MODEL = {0x1D, 0x49, 0x43};
    
    public static String returnModel(NRSerialPort serialPort) throws IOException, SerialPortException{
        String response = "";
        char temp;
        serialPort.getOutputStream().write(RETURN_MODEL);
        temp = (char) serialPort.getInputStream().read();
        while(temp != 0){
            temp = (char) serialPort.getInputStream().read();
            response += temp;
        }
        return response;
    }
    
    public static String returnModel(Socket socket) throws IOException{
        String response = "";
        char temp;
        socket.getOutputStream().write(RETURN_MODEL);
        temp = (char) socket.getInputStream().read();
        while(temp != 0){
            temp = (char) socket.getInputStream().read();
            response += temp;
        }
        //System.out.println("Response: "+response);
        return response;
    }
    
}
