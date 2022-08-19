package com.gertec.G250.functions;

import static com.gertec.G250.functions.returnModel.RETURN_MODEL;
import gnu.io.NRSerialPort;
import java.io.IOException;
import java.net.Socket;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author adels
 */
public class returnFirmware {
    
    public static byte [] RETURN_FIRMWARE = {0x1D, 0x49, 0x41};
    
    public static String returnFirmware(NRSerialPort serialPort) throws IOException, SerialPortException{
        String response = "";
        char temp;
        serialPort.getOutputStream().write(RETURN_FIRMWARE);
        temp = (char) serialPort.getInputStream().read();
        while(temp != 0){
            temp = (char) serialPort.getInputStream().read();
            response += temp;
        }
        return response;
    }
    
    public static String returnFirmware(Socket socket) throws IOException{
        String response = "";
        char temp;
        socket.getOutputStream().write(RETURN_FIRMWARE);
        temp = (char) socket.getInputStream().read();
        while(temp != 0){
            temp = (char) socket.getInputStream().read();
            response += temp;
        }
        //System.out.println("Response: "+response);
        return response;
    }
    
}
