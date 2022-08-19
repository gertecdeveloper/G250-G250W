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
public class returnStatus {

    private static final byte[] REALTIME_STATUS = {0x10, 0x04, 0x01};

    public static String returnStatus(NRSerialPort serialPort) throws Exception {
        int [] arrBits = new int[8];
        serialPort.getOutputStream().write(REALTIME_STATUS);
        int response = serialPort.getInputStream().read();
        int mask = 1 << 7;
        for(int bit = 1; bit <= 8; bit++){
            arrBits[bit-1] = (response & mask)==0? 0:1;
            //System.out.println(arrBits[bit-1]);
            response <<= 1;
        }
        if (arrBits[6]==1 & arrBits[4]==0) {
            return "online";
        }else{
            return "offline";
        }
        
    }

    public static String returnStatus(Socket socket) throws Exception {
        int [] arrBits = new int[8];
        socket.getOutputStream().write(REALTIME_STATUS);
        int response = socket.getInputStream().read();
        int mask = 1 << 7;
        for(int bit = 1; bit <= 8; bit++){
            arrBits[bit-1] = (response & mask)==0? 0:1;
            response <<= 1;
        }
        
        if (arrBits[6]==1 & arrBits[4]==0) {
            return "online";
        }else{
            return "offline";
        }

    }

}
