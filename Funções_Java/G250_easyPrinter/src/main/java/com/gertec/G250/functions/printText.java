/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gertec.G250.functions;

import gnu.io.NRSerialPort;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import jssc.SerialPort;

/**
 *
 * @author AMP
 */
public class printText {

    private static Map<String, byte[]> hashMap = new HashMap<String, byte[]>();

    private static final byte[] RESTART = {0x1b, 0x40}; // limpa os dados e reset as configurações da impressora

    private static final byte[] LF = {0x0a}; // line feed
    private static final byte[] HT = {0x09}; // line feed

    private static final byte[] CR = {0x0d}; // retorna o carro de impressão

    private static final byte[] BOLD_ON = {0x1b, 0x45, 0x01}; // texto negrito
    private static final byte[] BOLD_OFF = {0x1b, 0x45, 0x00}; // encerra texto negrito

    private static final byte[] UNDERLINE_ON = {0x1b, 0x2d, 0x01}; // sublinhado
    private static final byte[] UNDERLINE_OFF = {0x1b, 0x2d, 0x00}; // encerra sublinhado

    private static final byte[] FONT_LEFT = {0x1b, 0x61, 0x30}; // texto a esquerda
    private static final byte[] FONT_CENTER = {0x1b, 0x61, 0x31}; // texto CENTRALIZADO
    private static final byte[] FONT_RIGHT = {0x1b, 0x61, 0x32}; // texto a direita

    private static final byte[] EXPAND_TEXT_ON = {0x1b, 0x20, 0x10}; // texto EXPANDIDO
    private static final byte[] EXPAND_TEXT_OFF = {0x1b, 0x20, 0x00}; // encerra texto EXPANDIDO

    private static final byte[] INVERT_FONT_ON = {0x1d, 0x42, 0x01}; // FONTE INVERTIDA
    private static final byte[] INVERT_FONT_OFF = {0x1d, 0x42, 0x00}; // encerra FONTE INVERTIDA

    private static final byte[] NORMAL_FONT = {0x1b, 0x21, 0x00}; // Fonte normal

    private static final byte[] DOUBLE_HEIGHT_FONT = {0x1b, 0x21, 0x10}; // ALTURA DUPLA

    private static final byte[] DOUBLE_WIDTH_FONT = {0x1b, 0x21, 0x20}; // LARGURA DUPLA

    private static final byte[] DOUBLE_HEIGHT_WIDTH_FONT = {0x1b, 0x21, 0x30}; // LARGURA e LATURA DUPLAS

    private static final byte[] SMALL_FONT_ON = {0x1b, 0x4d, 0x01}; // FONTE PEQUENA ON
    private static final byte[] SMALL_FONT_OFF = {0x1b, 0x4d, 0x00}; // FONTE PEQUENA OFF

    private static final String charset = "cp860";

    public static void printText(String text, Socket socket) throws IOException, Exception {
        //sendBinBuffer.sendBinBuffer(RESTART, outputStream);
        sendBinBuffer.sendBinBuffer(decodeText(text), socket);
    }

    public static void printText(String text, NRSerialPort serial) throws IOException, Exception {
        sendBinBuffer.sendBinBuffer(decodeText(text), serial);
    }

    /*public static void printText(String text, String host, int port) throws IOException, Exception {
        sendBinBuffer.sendBinBuffer(decodeText(text), host, port);
    }*/
    public static void lineFeed(int n, Socket socket) throws Exception {
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                sendBinBuffer.sendBinBuffer(LF, socket);
            }
        }
    }

    /**
     * Formata o texto recebido substituindo as tags pelos comandos equivalentes
     *
     * @param text texto a ser formatado
     * @return um buffer binário com os dados já formatados para ser envia para
     * a impressora
     * @throws IOException
     * @throws Exception
     */
    public static byte[] decodeText(String text) throws IOException, Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        String newText = text;

        loadHashMap();

        for (String key : hashMap.keySet()) {
            newText = newText.replaceAll(key, "#-1" + key + "#-1");
        }

        newText = newText.replaceAll("<cbar>", "#-1<cbar>");
        newText = newText.replaceAll("</cbar>", "</cbar>#-1");
        newText = newText.replaceAll("<qr>", "#-1<qr>");
        newText = newText.replaceAll("</qr>", "</qr>#-1");

        String[] arrText = newText.split("#-1");

        for (String subText : arrText) {

            if (subText.contains("<cbar>")) {
                String[] cbarProperties = subText.replace("<cbar>", "")
                        .replace("</cbar>", "")
                        .split(",");

                if (cbarProperties.length == 5) {
                    String cbarType = cbarProperties[0];
                    int width = Integer.valueOf(cbarProperties[1]);
                    int height = Integer.valueOf(cbarProperties[2]);
                    int hri = Integer.valueOf(cbarProperties[3]);

                    if (cbarType != null & !cbarProperties[4].isEmpty()) {
                        buffer.write(printCustomBarCode.getBufferBarCode(height, width, 
                                hri, cbarType, cbarProperties[4]));
                    } else {
                        throw new IllegalArgumentException("Tag CBAR: valores dos parâmetros incorretos.");
                    }
                } else {
                    throw new IllegalArgumentException("Tag CBAR: Número de parâmetros incorreto.");
                }
            } else if (subText.contains("<qr>")) {
                String[] qrProperties = subText.replace("<qr>", "")
                        .replace("</qr>", "")
                        .split(",");

                //System.out.println("Contém qrCode");

                if (qrProperties.length == 3) {
                    int size = Integer.valueOf(qrProperties[0]);
                    int corrLevel = Integer.valueOf(qrProperties[1]);

                    if (qrProperties[2] != null) {
                        buffer.write(printQRCODE.getBufferQRCODE(size, corrLevel, qrProperties[2]));
                    } else {
                        throw new IllegalArgumentException("Tag QR: valores de parâmetros incorretos.");
                    }
                } else {
                    throw new IllegalArgumentException("Tag QR: Número de parâmetros incorreto.");
                }
            } else if (hashMap.containsKey(subText)) {
                buffer.write(hashMap.get(subText));
            } else {
                buffer.write(subText.getBytes(charset));
            }
        }
        return buffer.toByteArray();
    }

    public static void goToCenter(Socket outputStream) throws Exception {
        sendBinBuffer.sendBinBuffer(FONT_CENTER, outputStream);
    }

    public static void goToRight(Socket outputStream) throws Exception {
        sendBinBuffer.sendBinBuffer(FONT_RIGHT, outputStream);
    }

    public static void goToLeft(Socket outputStream) throws Exception {
        sendBinBuffer.sendBinBuffer(FONT_LEFT, outputStream);
    }

    private static void loadHashMap() {
        hashMap.put("<ne>", BOLD_ON);
        hashMap.put("</ne>", BOLD_OFF);
        hashMap.put("<ce>", FONT_CENTER);
        hashMap.put("</ce>", FONT_LEFT);
        hashMap.put("<c>", SMALL_FONT_ON);
        hashMap.put("</c>", SMALL_FONT_OFF);
        hashMap.put("<p>", NORMAL_FONT);
        hashMap.put("</p>", NORMAL_FONT);
        hashMap.put("<ald>", FONT_RIGHT);
        hashMap.put("</ald>", FONT_LEFT);
        hashMap.put("<ex>", EXPAND_TEXT_ON);
        hashMap.put("</ex>", EXPAND_TEXT_OFF);
        hashMap.put("<da>", DOUBLE_HEIGHT_FONT);
        hashMap.put("</da>", NORMAL_FONT);
        hashMap.put("<dl>", DOUBLE_WIDTH_FONT);
        hashMap.put("</dl>", NORMAL_FONT);
        hashMap.put("<dal>", DOUBLE_HEIGHT_WIDTH_FONT);
        hashMap.put("</dal>", NORMAL_FONT);
        hashMap.put("<fi>", INVERT_FONT_ON);
        hashMap.put("</fi>", INVERT_FONT_OFF);
        hashMap.put("</lf>", LF);
        hashMap.put("</ht>", HT);
        hashMap.put("<su>", UNDERLINE_ON);
        hashMap.put("</su>", UNDERLINE_OFF);
        hashMap.put("</cr>", CR);
        hashMap.put("</gui>", trigGuill.TRIG_GUILL);
    }

}
