/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gertec.G250.functions;

import gnu.io.NRSerialPort;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author AMP
 */
public class openDoor {
    
    public static Lock lock = new ReentrantLock();
 
    
    /*public static OutputStream openDoor(String port) throws FileNotFoundException{
        OutputStream out = new PrintStream(port);
        return out;             
    }*/
    
    public static OutputStream openDoor(PrintService printService) throws IOException, InterruptedException{
        ExecutorService executor = Executors.newSingleThreadExecutor();
        PipedOutputStream pouts = new PipedOutputStream();
        PipedInputStream pins = new PipedInputStream();
        pouts.connect(pins);

        Runnable runnablePrint = () -> {
            try {
                lock.lock();
                DocFlavor df = DocFlavor.INPUT_STREAM.AUTOSENSE;
                Doc d = new SimpleDoc(pins, df, null);
                
                DocPrintJob job = printService.createPrintJob();
                job.print(d, null);
                lock.unlock();
            } catch (PrintException ex) {
                throw new RuntimeException(ex);
            }finally{
               lock.unlock(); 
            }
        };
        executor.execute(runnablePrint);
        executor.shutdown();
        return pouts;             
    }
    
    public static Socket openDoor(String host, int port) throws IOException{
        if (host != null || port > 0) {
            Socket socket = new Socket(host,port);
            return socket;
        }else{
           throw new IllegalArgumentException("Ip e/ou porta inválido!"); 
        } 
    }
    
    
    public static NRSerialPort openDoor(String port) throws IOException{
        int baudRate = 9600;
        NRSerialPort serial = new NRSerialPort(port.toUpperCase(), baudRate);
        serial.connect();
        return serial;
    }
    /*Teste usando JSSC
    public static jssc.SerialPort openDoor(String port) throws IOException, SerialPortException{
        jssc.SerialPort serialPort = new jssc.SerialPort("Com3");
            serialPort.openPort();//Open serial port
        serialPort.setParams(SerialPort.BAUDRATE_9600, 
                             SerialPort.DATABITS_8,
                             SerialPort.STOPBITS_1,
                             SerialPort.PARITY_NONE);
        return serialPort;
    }*/
    
}
