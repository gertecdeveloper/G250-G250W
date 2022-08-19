/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gertec.G250.functions;

import gnu.io.NRSerialPort;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import javax.imageio.ImageIO;

/**
 *
 * @author AMP
 */
public class printBITMAP {
    
    private static final byte[] PRINT_IMAGE_COMMAND = {0x1B, 0x2A, 33};//24 dots
    private static final byte[] LINE_SPACE_24=  {0x1B, 0x33, 24};
    public static final byte[] LF = {0x0a}; // line feed
    
    public static void printBITMAP(String path, NRSerialPort serialPort) throws Exception{
        sendBinBuffer.sendBinBuffer(imageProcessor(path), serialPort);
    }
    
    public static void printBITMAP(String path, Socket socket) throws Exception{      
        sendBinBuffer.sendBinBuffer(imageProcessor(path), socket);
    }
    
    /*public static void printBITMAP(File img, OutputStream outputStream) throws Exception{
        BufferedImage image = ImageIO.read(img);
        ByteArrayOutputStream out = imageProcessor(image);
        sendBinBuffer.sendBinBuffer(out.toByteArray(), outputStream);
    }*/
    
    /*public static void printBITMAP(String path, String port) throws Exception{
        BufferedImage image = ImageIO.read(new File(path));
        ByteArrayOutputStream out = imageProcessor(image);
        sendBinBuffer.sendBinBuffer(out.toByteArray(), port);
    }
    
    public static void printBITMAP(String path, PrintService printService) throws Exception{
        BufferedImage image = ImageIO.read(new File(path));
        ByteArrayOutputStream out = imageProcessor(image);
        sendBinBuffer.sendBinBuffer(out.toByteArray(), printService);
    }
    
    public static void printBITMAP(String path, String host, int port) throws Exception{
        BufferedImage image = ImageIO.read(new File(path));
        ByteArrayOutputStream out = imageProcessor(image);
        sendBinBuffer.sendBinBuffer(out.toByteArray(), host, port);
    }*/
    
    public static byte[] imageProcessor(String path) throws IOException{
        //Bitmap image = BitmapFactory.decodeFile(path);
        BufferedImage image = ImageIO.read(new File(path));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        final int threshold = 127;
        double multiplier = 570;
        double scale = multiplier/image.getWidth();
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] pixels = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                pixels[row][col] = image.getRGB(col, row);
            }
        }
        
        buffer.write(LINE_SPACE_24);
        
        for (int y = 0; y < pixels.length; y += 24) {
            buffer.write(PRINT_IMAGE_COMMAND);
            //define o nL e nH conforme a largura da imagem
            buffer.write(new byte[]{(byte)(pixels[y].length)
                                         , (byte)((pixels[y].length) >> 8)});
            for (int x = 0; x < pixels[y].length; x++) {
                byte[] slices = new byte[] {0, 0, 0};
                for (int auxy = y, i = 0; auxy < y + 24 && i < 3; auxy += 8, i++) {
                    byte slice = 0;
                    for (int b = 0; b < 8; b++) {
                        int yyy = auxy + b;
                        if (yyy >= pixels.length) {
                            continue;
                        }
                        int color = pixels[yyy][x]; 
                        int alpha, red, green, blue, luminance;
                        alpha = (color >> 24) & 0xff;
                        boolean v = false;
                        if (alpha == 0xff) {
                           red = (color >> 16) & 0xff;
                           green = (color >> 8) & 0xff;
                           blue = color & 0xff;
                           luminance = (int) (0.3 * red + 0.59 * green + 0.11 * blue);
                           v = luminance < threshold; 
                        }
                        
                        slice |= (byte) ((v ? 1 : 0) << (7 - b));
                    }
                    slices[i] = slice;
                }
               buffer.write(slices);
            }

            buffer.write(LF);
        }
        return buffer.toByteArray();
    }
    
}
