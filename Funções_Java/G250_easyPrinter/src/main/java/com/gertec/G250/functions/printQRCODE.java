/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gertec.G250.functions;

import gnu.io.NRSerialPort;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import jssc.SerialPort;

/**
 *
 * @author AMP
 */
public class printQRCODE {
    
     public static enum CORRECTION_LEVEL {
        L(48),
        Q(49),
        M(50),
        H(51);
        
        public int value;
        CORRECTION_LEVEL(int value){
            this.value = value;
        }
        
        public static CORRECTION_LEVEL getCorrLevelForValue(int value){
            for (CORRECTION_LEVEL crr: CORRECTION_LEVEL.values()){
                if (crr.value == value) {
                    return crr;
                }
            }
            return null;
        }
    }
    
    public static void printQRCODE( int size, int correction,
            String data, NRSerialPort serialPort) throws Exception{
        
        sendBinBuffer.sendBinBuffer(getBufferQRCODE(size, correction, data), serialPort);
    }
    
    public static void printQRCODE( int size, int correction,
            String data, Socket socket) throws Exception{
        
        sendBinBuffer.sendBinBuffer(getBufferQRCODE(size, correction, data), socket);
    }
    
    
    public static byte[] getBufferQRCODE(int size, int correction, String data) throws Exception{
        if (1 > size || size > 16) {
            throw new IllegalArgumentException("Valor do tamanho invalido. "
                    + "Size deve ter valor entre 1 e 16");
        }
        
        if (correction < 48 || correction > 51) {
            throw new IllegalArgumentException("Valor de nível de correção invalido. "
                    + "Nível de Correção deve ter valor entre 48 e 51");
        }
        
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    
        int len = data.length() + 3;
        byte pL = (byte) (len % 256);
        byte pH = (byte) (len / 256);

        // Função 165
        byte [] f165 = {29, 40, 107, 4, 0, 49, 65, 50, 0};
        buffer.write(f165);

        // Função 167 - define o tamanho do qrCode
        //como o QRCode é quarado altura e largura são iguais
        byte [] f167 = {29, 40, 107, 3, 0, 49, 67, (byte) size};
        buffer.write(f167);

        // Função 169 - define a taxa de correção
        //n: 48 - 7%; 49 - 15%; 50 - 25%; 51 - 30%;
        byte [] f169 = {29, 40, 107, 3, 0, 49, 69, (byte) correction};
        buffer.write(f169); 
        

        // Função 180 - envia os dados para a área de armazenamento
        byte [] f180 = {29, 40, 107, pL, pH, 49, 80, 48};
        buffer.write(f180);
        buffer.write(data.getBytes("cp860"));
        
        // Função 181 - envia os dados para impressão
        byte [] f181 = {29, 40, 107, 3, 0, 49, 81, 48};
        buffer.write(f181);
        
        // Função 182 - Transmite as informações de tamanho na área de armazenamento de símbolos
        byte [] f182 = {29, 40, 107, 3, 0, 49, 82, 48};
        buffer.write(f182);
        
        return buffer.toByteArray();
    }
    
    
}
