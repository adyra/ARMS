/*
 * MessageInfo.java
 *
 * Created on March 16, 2007, 10:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.StringTokenizer;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

/**
 *
 * @author ady
 */
public class MessageInfo {
    
    private byte[]stream;
    private double size;
    private String fileEx = "txt ";
    /** Creates a new instance of MessageInfo */
    
    public MessageInfo(String text) {
        this.stream = text.getBytes();      
        this.size = stream.length;
    }
    
    public MessageInfo(byte[] stream) {
        this.stream = stream;
        this.size = stream.length;
    }
    
    public MessageInfo(File file){
        byte[] streambf = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            this.size = fis.available();//1024.0;
            
            String fileName = file.getName();
            StringTokenizer st = new StringTokenizer(fileName,".");
            st.nextToken();
            this.fileEx = st.nextToken();
            while (this.fileEx.length() < 4){
                this.fileEx += " "; 
            }
            
            streambf = new byte[fis.available()];          
            fis.read(streambf);
            fis.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
                ex.printStackTrace();
        }
        
        this.stream = streambf;
        this.size = streambf.length;
    }

    public String getFileEx() {
        return fileEx;
    }

    public void setFileEx(String fileEx) {
        this.fileEx = fileEx;
    }
    
    public byte[] getStream(){
        return this.stream;
    }

    public void setStream(byte[] stream) {
        this.stream = stream;
    }
    

    public String streamToText(){
        return new String(this.stream);
    }
    
    public void streamToFile(String nama){
        try {
            FileOutputStream fos = new FileOutputStream(nama + "." + this.fileEx.trim());
            fos.write(this.stream);
            fos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public double getSize() {
        return size;
    }
    
    public static void main(String[] args){
        
        MessageInfo mi = new MessageInfo(new File("C:\\tes sound\\main.tx"));
        
        System.out.println("ext:" + mi.getFileEx());
        System.out.println(mi.streamToText());
        
        mi.streamToFile("C:\\tes sound\\main.txt");
    }
    
    
    
}
