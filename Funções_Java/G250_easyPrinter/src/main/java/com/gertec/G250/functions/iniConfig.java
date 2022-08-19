/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gertec.G250.functions;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.ini4j.Wini;

/**
 *
 * @author adels
 */
public class iniConfig {
    
     public static boolean iniConfig(String serialPort){
        try {            
            File file = new File("GertecEasyPrinter.INI");
            if(!file.exists()){
               file.createNewFile();
            }
            Wini ini = new Wini(file);
            ini.put("Serial","Port", serialPort);
            ini.store();
            return true;
        }catch(IOException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
     
     public static boolean iniConfig(String server, String port){
        try {            
            File file = new File("GertecEasyPrinter.INI");
            if(!file.exists()){
               file.createNewFile();
            }
            Wini ini = new Wini(file);
            ini.put("Network","Server", server);
            ini.put("Network","Port", port);
            ini.store();
            return true;
        }catch(IOException ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
     
    public static String [] getConfig() throws IOException{
        String [] arrResponse = new String[3];
        Wini ini = new Wini(new File("GertecEasyPrinter.INI"));
        arrResponse[0] = ini.get("Serial","Port", String.class);
        arrResponse[1] = ini.get("Network","Server", String.class);
        arrResponse[2] = ini.get("Network","Port", String.class);
        return arrResponse;
    }
    
}
