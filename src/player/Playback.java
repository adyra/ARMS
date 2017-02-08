/*
 * Playback.java
 *
 * Created on February 16, 2007, 10:00 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import util.WaveInfo;
import wavemark.*;

/**
 *
 * @author ady
 */
    public class Playback implements Runnable {
    
        final int bufSize = 16384;

        public SourceDataLine line;
        public Thread thread;
        private String errStr;
        public SamplingGraphs samplingGraph;
        private AudioInputStream ais;
        public WaveInfo waveInfo;

        
        
        public Playback(){
            
        }
        
     
        public void setSourceStream(AudioInputStream ais){
            this.ais = ais;
        }

        public void setSourceStream(WaveInfo waveInfo){
            this.waveInfo = waveInfo;
            try {
                if (this.waveInfo.isRecordedAudio())
                    this.ais = waveInfo.getAudioInputStream();
                else
                    this.ais = AudioSystem.getAudioInputStream(new BufferedInputStream (new FileInputStream (waveInfo.getFile())));       
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (UnsupportedAudioFileException ex) {
                ex.printStackTrace();
            }       
        }          
        

        public void setSourceStream(File file){
        try {
            this.ais = AudioSystem.getAudioInputStream(new BufferedInputStream (new FileInputStream (file)));       
            //this.ais = AudioSystem.getAudioInputStream(file);             
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        }       
        }

        public void setSamplingGraph(SamplingGraphs samplingGraph){
            this.samplingGraph = samplingGraph;
        }

        public void start() {
            errStr = null;
            this.thread = new Thread(this);
            this.thread.setName("Playback");
            this.thread.start();
        }

        public void stop() {
            this.thread = null;
        }
        
        private void shutDown(String message) {
            if ((errStr = message) != null) {
                System.err.println(errStr);
                this.samplingGraph.repaint();
            }
            if (this.thread != null) {
                this.thread = null;
                this.samplingGraph.stop();
            } 
        }

        public void run() {
            // make sure we have something to play
            if (this.ais == null) {
                shutDown("No loaded audio to play back");
                return;
            }
           
            // reset to the beginnning of the stream
            try {
                this.ais.reset();
            } catch (Exception e) {
                shutDown("Unable to reset the stream\n" + e);
                return;
            }
            
            
            // get an AudioInputStream of the desired format for playback
            AudioFormat format = this.ais.getFormat();
            //AudioInputStream playbackInputStream = AudioSystem.getAudioInputStream(format, waveAudio.getAudioInputStream());
            AudioInputStream playbackInputStream = this.ais;
                        
            if (playbackInputStream == null) {
                shutDown("Unable to convert stream of format ");
                return;
            }

            // define the required attributes for our line, 
            // and make sure a compatible line is supported.

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            if (!AudioSystem.isLineSupported(info)) {
                shutDown("Line matching " + info + " not supported.");
                return;
            }

            // get and open the source data line for playback.

            try {
                this.line = (SourceDataLine) AudioSystem.getLine(info);
                this.line.open(format, bufSize);
            } catch (LineUnavailableException ex) { 
                shutDown("Unable to open the line: " + ex);
                return;
            }

            // play back the captured audio data

            int frameSizeInBytes = format.getFrameSize();
            int bufferLengthInFrames = this.line.getBufferSize() / 8;
            int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
            byte[] data = new byte[bufferLengthInBytes];
            int numBytesRead = 0;

            // start the source data line
            this.line.start();

            while (thread != null) {
                try {
                    if ((numBytesRead = playbackInputStream.read(data)) == -1) {
                        break;
                    }
                    int numBytesRemaining = numBytesRead;
                    while (numBytesRemaining > 0 ) {
                        numBytesRemaining -= this.line.write(data, 0, numBytesRemaining);
                    }
                } catch (Exception e) {
                    shutDown("Error during playback: " + e);
                    break;
                }
            }
            // we reached the end of the stream.  let the data play out, then
            // stop and close the line.
            if (thread != null) {
                this.line.drain();
            }
            this.line.stop();
            this.line.close();
            this.line = null;
            shutDown(null);
        }
    } // End class Playback
