package com.gertec.G250.functions;

import gnu.io.NRSerialPort;
import java.net.Socket;

/**
 *
 * @author adels
 */
public class statusDrawer {
    
    private static final byte[] REALTIME_STATUS = {0x10, 0x04, 0x01};

    public static String statusDrawer(NRSerialPort serialPort) throws Exception {
        int [] arrBits = new int[8];
        serialPort.getOutputStream().write(REALTIME_STATUS);
        int response = serialPort.getInputStream().read();
        int mask = 1 << 7;
        for(int bit = 1; bit <= 8; bit++){
            arrBits[bit-1] = (response & mask)==0? 0:1;
            response <<= 1;
        }
        if (arrBits[6]==1 & arrBits[5]==0) {
            return "Drawer Open";
        }else if(arrBits[6]==1 & arrBits[5]==1) {
            return "Drawer Closed";
        }else{
            return "inaccessible printer";
        }
        
    }

    public static String statusDrawer(Socket socket) throws Exception {
        int [] arrBits = new int[8];
        socket.getOutputStream().write(REALTIME_STATUS);
        int response = socket.getInputStream().read();
        int mask = 1 << 7;
        for(int bit = 1; bit <= 8; bit++){
            arrBits[bit-1] = (response & mask)==0? 0:1;
            response <<= 1;
        }
        
        if (arrBits[6]==1 & arrBits[5]==0) {
            return "Drawer Open";
        }else if(arrBits[6]==1 & arrBits[5]==1) {
            return "Drawer Closed";
        }else{
            return "inaccessible printer";
        }

    }
    
}
