/*
 * WaveAudio.java
 *
 * Created on February 16, 2007, 10:19 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author ady
 */
public class WaveInfo {
    
    private AudioInputStream audioInputStream;
    private AudioFormat audioFormat;
    private File file;
    private String fileName = "Untitled";
    private boolean RecordedAudio = false;
    private int lengthSample = 0;
    //private int[] audioSamples;
//    private float[] floatSamples;
//    private byte[] audioByte;
    
    /** Creates a new instance of WaveAudio */
    public WaveInfo(File file) {
        if (file != null && file.isFile()) {
            try {
                this.file = file;
                this.audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream (new FileInputStream (file)));       
                this.audioInputStream.mark(this.audioInputStream.available());
                this.audioFormat = this.audioInputStream.getFormat();
                this.fileName = file.getName();
                this.lengthSample = this.getAudioSamples().length;
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }       
        }
    }
        
    public WaveInfo(String pathfile){
       File file = new File(pathfile);
        if (file != null && file.isFile()) {
            try {
                this.file = file;
                this.audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream (new FileInputStream (file)));       
                this.audioInputStream.mark(this.audioInputStream.available());
                this.audioFormat = this.audioInputStream.getFormat();
                this.fileName = file.getName();
                this.lengthSample = this.getAudioSamples().length;                
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }       
        }      
    }
    
    public WaveInfo(AudioInputStream audioInputStream){
        this.audioInputStream = audioInputStream;
        try {
            this.audioInputStream.mark(this.audioInputStream.available());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.audioFormat = audioInputStream.getFormat();
        this.lengthSample = this.getAudioSamples().length;
    }
    
    
    public WaveInfo(){
    }
    
    public WaveInfo(int[] samples, AudioFormat format){
        this.audioFormat = format;
        this.lengthSample = samples.length;
        byte[] audioByte = null;
        
        if (this.getBitPerSample() == 16) {
            int nlengthInByte = samples.length * 2;
            audioByte = new byte[nlengthInByte];
            int offset = 0;
            if (this.isBigEndian()) {
                for (int i = 0; i < samples.length; i++) {
                    audioByte[offset] = (byte) ((samples[i] >> 8) & 255); 
                    offset++;
                    audioByte[offset] = (byte) (samples[i] & 255);
                    offset++;
                }
            }else{
                for (int i = 0; i < samples.length; i++) {
                    audioByte[offset] = (byte) (samples[i] & 255);
                    offset++;
                    audioByte[offset] = (byte) ((samples[i] >> 8) & 255); 
                    offset++;
                }
             }
        } else if (this.getBitPerSample() == 8) {
            int nlengthInByte = samples.length;
            audioByte = new byte[nlengthInByte];
            if (this.audioFormat.getEncoding().toString().startsWith("PCM_SIGN")) {
                for (int i = 0; i < samples.length; i++) {
                    audioByte[i] = (byte) samples[i];
                }
            }else{
                for (int i = 0; i < samples.length; i++) {
                    audioByte[i] = (byte) (samples[i] + 128);
                }
            }
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(audioByte);
        this.audioInputStream = new AudioInputStream(bais, this.audioFormat, audioByte.length / this.audioFormat.getFrameSize());
        try {
            this.audioInputStream.mark(this.audioInputStream.available());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            this.audioInputStream.reset();
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        return;
        }        
    }

     public WaveInfo(float[] samples, AudioFormat format){
        this.audioFormat = format;
        this.lengthSample = samples.length;
        byte[] audioByte = null;
        
        if (this.getBitPerSample() == 16) {
            int nlengthInByte = samples.length * 2;
            audioByte = new byte[nlengthInByte];
            int offset = 0;
            //samples = Math.min(1.0F, Math.max(-1.0F, fSample);
            if (this.isBigEndian()) {
                for (int i = 0; i < samples.length; i++) {
                    samples[i] = Math.min(1.0F, Math.max(-1.0F, samples[i]));
                    int nSample = Math.round(samples[i] * 32767.0F);
                    audioByte[offset] = (byte) ((nSample >> 8) & 255); 
                    offset++;
                    audioByte[offset] = (byte) (nSample & 255);
                    offset++;
                }
            }else{
                for (int i = 0; i < samples.length; i++) {
                    samples[i] = Math.min(1.0F, Math.max(-1.0F, samples[i]));
                    int nSample = Math.round(samples[i] * 32767.0F);
                    audioByte[offset] = (byte) (nSample & 255);
                    offset++;
                    audioByte[offset] = (byte) ((nSample >> 8) & 255); 
                    offset++;
                }
             }
        } else if (this.getBitPerSample() == 8) {
            int nlengthInByte = samples.length;
            audioByte = new byte[nlengthInByte];
            if (this.audioFormat.getEncoding().toString().startsWith("PCM_SIGN")) {
                for (int i = 0; i < samples.length; i++) {
                    samples[i] = Math.min(1.0F, Math.max(-1.0F, samples[i]));
                    int nSample = Math.round(samples[i] * 32767.0F);
                    audioByte[i] = (byte) nSample;
                }
            }else{
                for (int i = 0; i < samples.length; i++) {
                    samples[i] = Math.min(1.0F, Math.max(-1.0F, samples[i]));
                    int nSample = Math.round(samples[i] * 32767.0F);                    
                    audioByte[i] = (byte) (nSample + 128);
                }
            }
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(audioByte);
        this.audioInputStream = new AudioInputStream(bais, this.audioFormat, audioByte.length / this.audioFormat.getFrameSize());
        try {
            this.audioInputStream.mark(this.audioInputStream.available());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try {
            this.audioInputStream.reset();
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        return;
        }
    }
   
    
    public void setAudioInputStream( AudioInputStream audioInputStream){
        this.audioInputStream = audioInputStream;
        
        this.audioFormat = audioInputStream.getFormat();      
    }
    
    public String getFileName(){
        return this.fileName;
    }

    public File getFile(){
        return this.file;
    }

    public AudioFormat getAudioFormat(){
        return this.audioFormat;
    }
    
    public int getChannels(){
        return this.audioFormat.getChannels();
    }
    
    public float getFrameRate(){
        return this.audioFormat.getFrameRate();
    }
    
    public int getBitPerSample(){
        return this.audioFormat.getSampleSizeInBits();
    }
     
    public boolean isBigEndian(){
        return this.audioFormat.isBigEndian();
    }

    public void setRecordedAudio(boolean RecordedAudio) {
        this.RecordedAudio = RecordedAudio;
    }

    public boolean isRecordedAudio() {
        return RecordedAudio;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
      
    public void saveToFile(String name, AudioFileFormat.Type fileType){
        if (this.audioInputStream == null) {
            System.err.println("No loaded audio to save");
            return;
        }    
        // reset to the beginnning of the captured data
        try {
            audioInputStream.reset();
        } catch (Exception e) { 
            System.err.println("Unable to reset stream " + e);
            return;
        }

        File file = new File(name);
        try {
            if (AudioSystem.write(audioInputStream, fileType, file) == -1) {
                throw new IOException("Problems writing to file");
            }
        } catch (Exception ex) {
            System.err.println(ex.toString()); 
        }
    }
    
    public byte[] getAudioByte(){
        try {
            
            this.audioInputStream.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        byte[] audioBytes = null;

        try {
            audioBytes = new byte[(int) (this.audioInputStream.getFrameLength() * this.audioFormat.getFrameSize())];            
            this.audioInputStream.read(audioBytes);
        } catch (Exception ex) { 
              System.err.println("gagal" + ex.toString());
        }
        
        return audioBytes;
    }
    
    public int[] getAudioSamples(){
        byte[] audioBytes = this.getAudioByte();
        
        int[] audioData = null;
        
        if (this.getBitPerSample() == 16) {
            int nlengthInSamples = audioBytes.length / 2;
            audioData = new int[nlengthInSamples];
            if (this.isBigEndian()) {
                for (int i = 0; i < nlengthInSamples; i++) {
                    /* First byte is MSB (high order) */
                    int MSB = (int) audioBytes[2*i];
                    /* Second byte is LSB (low order) */
                    int LSB = (int) audioBytes[2*i+1];
                    audioData[i] = MSB << 8 | (255 & LSB);
                }
            }else{
                for (int i = 0; i < nlengthInSamples; i++) {
                    /* First byte is LSB (low order) */
                    int LSB = (int) audioBytes[2*i];
                    /* Second byte is MSB (high order) */
                    int MSB = (int) audioBytes[2*i+1];
                    audioData[i] = MSB << 8 | (255 & LSB);
                }
             }
        } else if (this.getBitPerSample() == 8) {
            int nlengthInSamples = audioBytes.length;
            audioData = new int[nlengthInSamples];
            if (this.audioFormat.getEncoding().toString().startsWith("PCM_SIGN")) {
                for (int i = 0; i < audioBytes.length; i++) {
                    audioData[i] = audioBytes[i];
                }
            }else{
                for (int i = 0; i < audioBytes.length; i++) {
                    audioData[i] = audioBytes[i] - 128;
                }
            }
        }
        return audioData;
    }   
    public AudioInputStream getAudioInputStream(){
        try {
            this.audioInputStream.reset();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return this.audioInputStream;
        
    }
 
    public double getLength(){
        double milliseconds = (double)((this.audioInputStream.getFrameLength() * 1000) / this.audioInputStream.getFormat().getFrameRate());
        return milliseconds / 1000.0;
    }
    
    public Vector getProperties(){
        DecimalFormat formatter = new DecimalFormat("###.##");
        Vector listProperties = new Vector();
        Vector properties1 = new Vector();
        properties1.add("File Name");
        properties1.add(this.getFileName());
        
        listProperties.add(properties1);
        
        Vector properties2 = new Vector();
        properties2.add("Size");
        if (this.file != null)
            properties2.add(this.file.length() + " bytes");
        else
            properties2.add("-");

        listProperties.add(properties2);

        Vector properties3 = new Vector();
        properties3.add("Length");
        properties3.add(formatter.format(this.getLength()) + " seconds");
 
        listProperties.add(properties3);
 
        Vector properties4 = new Vector();
        properties4.add("Sample Rate");
        properties4.add(formatter.format(this.getFrameRate()) + " Hz");
        
        listProperties.add(properties4);
        
        Vector properties5 = new Vector();
        properties5.add("Bit Rate");
        properties5.add(this.getBitPerSample() + " bps");
        
        listProperties.add(properties5);
        
        Vector properties6 = new Vector();
        properties6.add("Channels");
        String channel = this.getChannels() == 1? "Mono":"Stereo";
        properties6.add(channel);
        
        listProperties.add(properties6);
        
        Vector properties7 = new Vector();
        properties7.add("Big Endian");
        String bigend = this.isBigEndian()? "True":"False";
        properties7.add(bigend);
        
        listProperties.add(properties7);
               
    return listProperties;    
    }

    
    public float[] getFloatSamples(){
        byte[] audioBytes = this.getAudioByte();
        
        float[] audioData = null;
        
        if (this.getBitPerSample() == 16) {
            int nlengthInSamples = audioBytes.length / 2;
            audioData = new float[nlengthInSamples];
            if (this.isBigEndian()) {
                for (int i = 0; i < nlengthInSamples; i++) {
                    /* First byte is MSB (high order) */
                    byte MSB = audioBytes[2*i];
                    /* Second byte is LSB (low order) */
                    byte LSB = audioBytes[2*i+1];
                    //audioData[i] = MSB << 8 | (255 & LSB);
                    audioData[i] = ((MSB << 8) | (LSB & 0xFF) )/ 32768.0F;
                }
            }else{
                for (int i = 0; i < nlengthInSamples; i++) {
                    /* First byte is LSB (low order) */
                    byte LSB = audioBytes[2*i];
                    /* Second byte is MSB (high order) */
                    byte MSB = audioBytes[2*i+1];
                    //audioData[i] = MSB << 8 | (255 & LSB);
                    audioData[i] = ((LSB & 0xFF) | (MSB << 8) )/ 32768.0F;                   
                }
             }
        } else if (this.getBitPerSample() == 8) {
            int nlengthInSamples = audioBytes.length;
            audioData = new float[nlengthInSamples];
            if (this.audioFormat.getEncoding().toString().startsWith("PCM_SIGN")) {
                for (int i = 0; i < audioBytes.length; i++) {
                    //audioData[i] = audioBytes[i];
                    audioData[i] = audioBytes[i] / 128.0F;
                }
            }else{
                for (int i = 0; i < audioBytes.length; i++) {
                    //audioData[i] = audioBytes[i] - 128;
                    audioData[i] = ((audioBytes[i] & 0xFF) - 128)/ 128.0F;
                }
            }
        }

        return audioData;
    }

    public int getLengthSample() {
        return lengthSample;
    }
    

    public String toString(){
        return "  " + this.fileName;
    }
}
