/*
 * 
 */
package com.gertec.G250.functions;

import gnu.io.NRSerialPort;
import java.io.ByteArrayOutputStream;
import java.net.Socket;

/**
 *
 * @author AMP
 */
public class printCustomBarCode {

    /**
     * Define os tipos de códigos de barra possíveis
     *
     */
    public static enum TYPE {
        UPCA(0, "\\d{11,12}$", 4),
        UPCE(1, "\\d{11,12}$", 4),
        EAN8(3, "\\d{7,8}$", 4),
        EAN13(2, "\\d{12,13}$", 4),
        CODE39(69, "\\d{1,255}$", 4),
        ITF(70, "\\d{1,255}$", 4),
        CODE93(72, "\\d{1,255}$", 4),
        CODE128(73, "\\d{2,255}$", 4),
        CODE128c(73, "", 4);

        public int value;
        public String filterRegex;
        public int maxWidth;

        TYPE(int value, String filterRegex, int maxWidth) {
            this.value = value;
            this.filterRegex = filterRegex;
            this.maxWidth = maxWidth;
        }

        public static printCustomBarCode.TYPE getCBARTypeForName(String codeName) {
            switch (codeName.toLowerCase()) {
                case "code128":
                    return printCustomBarCode.TYPE.CODE128;
                case "code128c":
                    return printCustomBarCode.TYPE.CODE128c;
                case "code39":
                    return printCustomBarCode.TYPE.CODE39;
                case "code93":
                    return printCustomBarCode.TYPE.CODE93;
                case "ean13":
                    return printCustomBarCode.TYPE.EAN13;
                case "ean8":
                    return printCustomBarCode.TYPE.EAN8;
                case "itf":
                    return printCustomBarCode.TYPE.ITF;
                case "upca":
                    return printCustomBarCode.TYPE.UPCA;
                case "upce":
                    return printCustomBarCode.TYPE.UPCE;
                default:
                    return null;
            }
        }
    }

    /**
     * imprime um código de barras via porta serial conforme o tipo definido
     *
     * @param height - altura
     * @param width - largura
     * @param hri - posição do código (NONE não imprime nenhum valor de código)
     * @param codeType - tipo do código de barras a ser impresso
     * @param data - dados a serem impressos
     * @param serialPort- porta para onde serão enviados os dados
     * @throws Exception
     */
    public static void printCustomBarCode(int height, int width, int hri, String codeType,
            String data, NRSerialPort serialPort) throws Exception {
        sendBinBuffer.sendBinBuffer(getBufferBarCode(height, width, hri, codeType, data), serialPort);

    }

    public static void printCustomBarCode(int height, int width, int hri, String codeType,
            String data, Socket socket) throws Exception {
        sendBinBuffer.sendBinBuffer(getBufferBarCode(height, width, hri, codeType, data), socket);

    }

    /**
     *
     * imprime um código de barras via porta rede conforme o tipo definido
     *
     * @param hight - altura
     * @param width - largura
     * @param hri - posição do código (NONE não imprime nenhum valor de código)
     * @param codeType - tipo do código de barras a ser impresso
     * @param data - dados a serem impressos
     * @param printService - PrintService da impressora
     * @throws Exception
     */
    /*public static void printCustomBarCode(int hight, WIDTHVALUES width, HRI hri, TYPE codeType,
            String data, PrintService printService) throws Exception{
        
        sendBinBuffer.sendBinBuffer(getBufferBarCode(hight, width, hri, codeType, data), 
                printService);
      
    }*/
    /**
     *
     * imprime um código de barras via porta rede conforme o tipo definido
     *
     * @param hight - altura
     * @param width - largura
     * @param hri - posição do código (NONE não imprime nenhum valor de código)
     * @param codeType - tipo do código de barras a ser impresso
     * @param data - dados a serem impressos
     * @param host - ip da impressora na rede
     * @param port - porta de conexão (padrão é 9100)
     * @throws Exception
     */
    /*public static void printCustomBarCode(int hight, WIDTHVALUES width, HRI hri, TYPE codeType,
            String data, String host, int port) throws Exception{
        
        sendBinBuffer.sendBinBuffer(getBufferBarCode(hight, width, hri, codeType, data), host, port);
      
    }*/
    /**
     * formata os comandos e dados como um buffer binário para serem enviados
     * para impressora
     *
     * @param height - altura
     * @param width - largura
     * @param hri - posição do código (NONE não imprime nenhum valor de código)
     * @param codeBar- tipo do código de barras a ser impresso
     * @param data - dados a serem impressos
     * @return buffer binário com os comandos e dados prontos para serem
     * enviados para impressora
     * @throws Exception
     */
    public static byte[] getBufferBarCode(int height, int width, int hri, String codeBar,
            String data) throws Exception {
        TYPE codeType = TYPE.getCBARTypeForName(codeBar);

        if (!codeBar.equals("code128c")) {
            if (!data.matches(codeType.filterRegex)) {
                throw new IllegalArgumentException("Código de barras mal formatado - " + codeType.filterRegex);
            }
        }
        

        if (width <= 0 || width > codeType.maxWidth) {
            throw new IllegalArgumentException("Largura do código de barras incorreto. Valores válidos são de 1"
                    + " - " + codeType.maxWidth);
        }

        if (hri < 0 || width > 3) {
            throw new IllegalArgumentException("Valor de HRI incorreto. Valores válidos são de 0 - 3");
        }

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        //Altura
        buffer.write(29);
        buffer.write(104);//104 ou h
        buffer.write((0 <= height & height <= 255) ? height : 100);
        //largura
        buffer.write(29);
        buffer.write(119);//119 ou w
        buffer.write(width);

        //HRI
        buffer.write(29);
        buffer.write(72);//72 ou H
        buffer.write(hri);
        if (!codeBar.equals("code128c")) {
            //imprimir código de barras
            buffer.write(29);
            buffer.write(107);//107 ou k
            buffer.write(codeType.value);
             if (codeType.value <= 3) {
                buffer.write(data.getBytes(), 0, data.length());
                buffer.write(0);
            } else {
                //dados
                buffer.write(data.length());
                buffer.write(data.getBytes(), 0, data.length());
            }
        }else{
             buffer.write(29);
            buffer.write(107);//107 ou k
            buffer.write(73);
            buffer.write(44);
            buffer.write(data.getBytes(), 0, data.length());
        }
        
        return buffer.toByteArray();
    }

}
