/*
 * WatermarkAnalyzer.java
 *
 * Created on October 3, 2007, 12:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package util;

import java.text.DecimalFormat;
import watermark.WatermarkingFactory;

/**
 *
 * @author ady
 */
public class WatermarkAnalyzer {
    
    private int[] dataHeader = new int[WatermarkingFactory.HEADER_LENGTH];
    
    /** Creates a new instance of WatermarkAnalyzer */
    public WatermarkAnalyzer(WaveInfo wi) {
        int[] sampel = wi.getAudioSamples();
        for (int i=0;i<this.dataHeader.length;i++){
            this.dataHeader[i] = sampel[i];
        }
    }
       
    public boolean isContainWatermark(){
        byte[] dataHead = new byte[WatermarkingFactory.MARKER.length()];
        
        int startIndex = 0;
        for (int i=0;i<dataHead.length;i++){
            int charEx = 0;
            for (int j=0;j<8;j++){
                int bitExtract = this.dataHeader[startIndex] & 1;
                charEx |= bitExtract << j;
                startIndex++;
            }
            dataHead[i] = (byte) charEx;
        }
        String head = new String(dataHead);
        System.out.println(head);
        if (head.equals(WatermarkingFactory.MARKER))
            return true;
        else
            return false;
    }

    public int getLengthMessage(){
        int startIndex = 24;
        int charEx =0;
        for (int j=0;j<32;j++){
            int bitExtract = this.dataHeader[startIndex] & 1;
            charEx |= bitExtract << j;
            startIndex++;
         }
        
        if (this.getWatermarkingMethod() == 0 ){
            int lsb = Integer.parseInt(this.getValueMethod());
            int numByte = charEx * lsb / 8;
        
            return numByte;
        }else{                 
            int numByte = charEx / 8;
            return numByte;       
        }        
    }
    
    public String getMessageExt(){
        int startIndex = 56;
        byte[] extArray = new byte[4];
        for (int i=0;i<extArray.length;i++){
            int charEx = 0;
            for (int j=0;j<8;j++){
                int bitExtract = this.dataHeader[startIndex] & 1;
                charEx |= bitExtract << j;
                startIndex++;
            }
            extArray[i] = (byte) charEx;
        }
        return new String(extArray);        
    } 
    
    public int getWatermarkingMethod(){
        int startIndex = 88;
        int method =0;
        for (int j=0;j<8;j++){
            int bitExtract = this.dataHeader[startIndex] & 1;
            method |= bitExtract << j;
            startIndex++;
         }
        return method;
    }
    
    public String getValueMethod(){
        int startIndex = 96;
        int value =0;
        for (int j=0;j<16;j++){
            int bitExtract = this.dataHeader[startIndex] & 1;
            value |= bitExtract << j;
            startIndex++;
        }
        
        if (this.getWatermarkingMethod() == 0 )
            return Integer.toString(value);
        else{                 
            System.out.println(value);
            double fvalue = (double) value/100;
            DecimalFormat df = new DecimalFormat("###.##"); 
            return df.format(fvalue);       
        }
    }
    
    public boolean isCompressed(){
        int startIndex = 112;
        int compressed =0;
        for (int j=0;j<8;j++){
            int bitExtract = this.dataHeader[startIndex] & 1;
            compressed |= bitExtract << j;
            startIndex++;
         }
        if (compressed == 0)
            return false;
        else
            return true;                    
    }
    
    public boolean isEncrypted(){
        int startIndex = 120;
        int encrypted =0;
        for (int j=0;j<8;j++){
            int bitExtract = this.dataHeader[startIndex] & 1;
            encrypted |= bitExtract << j;
            startIndex++;
         }
        if (encrypted == 0)
            return false;
        else
            return true;                    
    }    
    
}
    
    
    
    
    
    
    
    

