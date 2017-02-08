/*
 * Util.java
 *
 * Created on April 11, 2007, 11:53 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.jdom.*;
import org.jdom.filter.ElementFilter;
import org.jdom.input.SAXBuilder;
import watermark.WatermarkingFactory;

/**
 *
 * @author ady
 */
public class Util {
    
    public static final String ADD_KEY = "aDy DhArMaDi";
    /** Creates a new instance of Util */
    public Util() {
    }
    
    public static String getValue(File file, String key){
        String value = "";
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            doc = builder.build(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JDOMException ex) {
            ex.printStackTrace();
        }

        Iterator itr = doc.getDescendants();
        itr = doc.getDescendants(new ElementFilter(key));
        while (itr.hasNext()) {
            Content c = (Content) itr.next();
            value = c.getValue();
        }
        
        return value;
    } 
    
    public static HashMap setToHashMap(File file){
        HashMap hm = new HashMap();
        
        hm.put("mode", getValue(file,"mode"));
        String method = getValue(file,"method");
        hm.put("method", method);
        
        if (method.equalsIgnoreCase("0")){
            hm.put("lsb", getValue(file,"lsb"));
        }else{
            hm.put("amplitude", getValue(file,"amplitude"));
        }    
        
        hm.put("warning", getValue(file,"warning"));
        hm.put("compress", getValue(file,"compress"));
        hm.put("encrypt", getValue(file,"encrypt"));       
        return hm;
    }
    
    public static String getExtensionFile(String namefile){
        StringTokenizer st = new StringTokenizer(namefile,".");
        st.nextToken();
        return st.nextToken();
    }
    
    public static String getTypeFile(String extension){
        String type = "unknown";
        if (extension.equalsIgnoreCase("txt"))
            type = "Text";
        else if (extension.equalsIgnoreCase("doc"))
            type = "Microsoft Word";
        else if (extension.equalsIgnoreCase("zip"))
            type = "Compressed File";
        else if (extension.equalsIgnoreCase("jpg"))
            type = "Image File";
        
        else
            type = "unknown";
        return type;
    }

    public static double countSNR(int[] sample1, int[] sample2){
        double rerata1 = 0;
        double rerata2 = 0;
        
        for (int i=0;i<sample1.length;i++){
            rerata1 += Math.pow(sample1[i], 2) ;
            rerata2 += Math.pow(sample1[i] - sample2[i], 2);
        }
        
        return 10*Math.log10(rerata1/rerata2);
    }

    public static double countRMSE(int[] sample1, int[] sample2){
        double rerata1 = 0;
        double rerata2 = 0;
        
        for (int i=0;i<sample1.length;i++){
            rerata1 += Math.pow(sample1[i], 2) ;
            rerata2 += Math.pow(sample1[i] - sample2[i], 2);
        } 
        System.out.println(rerata1);
        System.out.println(rerata2);
        
        return Math.sqrt(rerata2/rerata1);        
    }

    public static boolean isContainMark(int[] sampleAudio){
        String marker =  WatermarkingFactory.MARKER;
        byte[] markExtract = new byte[marker.length()];
        
        int i=0;
        int offset = 0;
        while (i <(marker.length()*8)){
            int byteExtract = 0;
            for (int j=0;j<8;j++){
                int bitExtract = sampleAudio[i] & 1;
                byteExtract |= bitExtract << j;
                i++;
            }
            markExtract[offset] = (byte) byteExtract;
            offset++;
        }
        String extractedMark = new String(markExtract);
        
        if (extractedMark.equals(marker))
            return true;
        else
            return false;
    }
    
    public static int getLengthMessage(int[] audioSamples){
        int lengthMessage = 0;
        //skip the marker
        int startIndex = WatermarkingFactory.MARKER.length() * 8;
        //get length message
        for (int i=0;i<32;i++){
            int bitExtract = audioSamples[startIndex] & 1;
            lengthMessage |= bitExtract << i;
            startIndex++;
        }
        // end get length message
        return lengthMessage;
    }
    
    public static String getExtensionMessage(int[] audioSamples){
        int startIndex = WatermarkingFactory.MARKER.length() * 8 + 32;
        //get file extension
        byte[] extArray = new byte[4];
        for (int i=0;i<extArray.length;i++){
            int charEx = 0;
            for (int j=0;j<8;j++){
                int bitExtract = audioSamples[startIndex] & 1;
                charEx |= bitExtract << j;
                startIndex++;
            }
            extArray[i] = (byte) charEx;
        }
        return new String(extArray);               
    }
    
    public static MessageInfo EncryptMessageInfo(MessageInfo mi, String key){
        
        MessageInfo mi2 = mi;
        byte[] dataplain = mi2.getStream(); //this.plaintext.getBytes();
        byte[] datakey = new byte[dataplain.length];
        
        String pass = key + Util.ADD_KEY;
        
        int offset = 0;
        for(int i=0;i<datakey.length;i++){
            datakey[i] = (byte) pass.charAt(offset);
            offset++;
            if (offset >= pass.length())
                offset = 0;            
        }
                
        byte[] chipper = new byte[dataplain.length];
        
        for (int i=0;i<dataplain.length;i++){
            chipper[i] = (byte) ((dataplain[i] + datakey[i]) % 256); 
        }
        
        mi2.setStream(chipper);
        
        return mi2;
    }
    
    public static MessageInfo DecryptMessageInfo(MessageInfo mi, String key){
        MessageInfo mi2 = mi;
        byte[] datachipper = mi.getStream();
        byte[] datakey = new byte[datachipper.length];
        
        String pass = key + Util.ADD_KEY;
        
        int offset = 0;
        for(int i=0;i<datakey.length;i++){
            datakey[i] = (byte) pass.charAt(offset);
            offset++;
            if (offset >= pass.length())
                offset = 0;            
        }
                
        byte[] plain = new byte[datachipper.length];
        
        for (int i=0;i<datachipper.length;i++){
            plain[i] = (byte) ((datachipper[i] - datakey[i]) % 256); 
        }
        
        mi2.setStream(plain);
        
        return mi2; 
    }
    
    public static MessageInfo compressMessageInfo(MessageInfo mi, int level) throws IOException{
        //MessageInfo mi2 = mi;

        FileOutputStream fos = new FileOutputStream("temp");
        fos.write(mi.getStream());
        fos.close();
            
        FileOutputStream fos2 = new FileOutputStream("entry.zip");
        ZipOutputStream targetStream = new ZipOutputStream(fos2);
        targetStream.setMethod(ZipOutputStream.DEFLATED);
        targetStream.setLevel(level);
        
        File temp = new File("temp");
        FileInputStream fis = new FileInputStream(temp);       
        BufferedInputStream sourceStream = new BufferedInputStream(fis);        
        targetStream.putNextEntry(new ZipEntry("temp"));
        
        byte[] data = new byte[1024];
        int bCnt = 0;
        while ( (bCnt = sourceStream.read(data, 0, 1024) ) != -1) {
            targetStream.write(data, 0, bCnt);
        }
        targetStream.flush();          
        targetStream.closeEntry();
        targetStream.close();
        sourceStream.close();
        
        temp.delete();
        
        File entry = new File("entry.zip");
        MessageInfo mi2 = new MessageInfo(entry);
        mi2.setFileEx(mi.getFileEx());
        
        entry.delete();
        
        
        
        return mi2;       
    }
    
    
    public static MessageInfo uncompressMessageInfo(MessageInfo mi, int level) throws IOException{
        //MessageInfo mi2 = mi;

        mi.streamToFile("entry");
        
        File zipfile = new File("entry." + mi.getFileEx().trim());
        
        FileInputStream fis = new FileInputStream(zipfile);
        ZipInputStream sourceStream = new ZipInputStream(fis);
        
        File temp = new File("temp.tmp");
        FileOutputStream fos = new FileOutputStream(temp);
        BufferedOutputStream targetStream = new BufferedOutputStream(fos);
        
        ZipEntry ze; 
        while ((ze = sourceStream.getNextEntry()) != null){       
            byte[] data = new byte[1024];
            int bCnt = 0;
            while ( (bCnt = sourceStream.read(data, 0, 1024) ) != -1) {
                targetStream.write(data, 0, bCnt);
            }
        }
        targetStream.flush();          
        targetStream.close();
        sourceStream.close();
        
        zipfile.delete();
        
        File entry = new File("temp.tmp");
        MessageInfo mi2 = new MessageInfo(entry);
        mi2.setFileEx(mi.getFileEx());
        
        entry.delete();                
        return mi2;       
    }
}
        
    
    