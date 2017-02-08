/*
 * Capture.java
 *
 * Created on February 16, 2007, 9:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package player;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import ui.MainForm;
import util.WaveInfo;
import wavemark.*;

/**
 *
 * @author ady
 */
public class Capture implements Runnable {

        public TargetDataLine line;
        public Thread thread;
        public SamplingGraphs samplingGraph;
        private AudioInputStream audioInputStream;
        private MainForm mainform;
    
        public void start() {
            this.thread = new Thread(this);
            this.thread.setName("Capture");
            this.thread.start();
        }

        public void stop() {
            this.thread = null;
        }
        
        private void shutDown(String message) {
            if (thread != null) {
                this.thread = null;
                this.samplingGraph.stop();
                System.err.println(message);
                this.samplingGraph.repaint();
            }
        }

        public void run() {
            this.audioInputStream = null;
            
            // define the required attributes for our line, 
            // and make sure a compatible line is supported.

            //ini cuma contoh audio format--------------------------------------
            AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
            float rate = 44100;
            int sampleSize = 16;
            int channels = 2;
            boolean bigEndian = false;
            //------------------------------------------------------------------
          
            AudioFormat format = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize/8)*channels, rate, bigEndian);
            //AudioFormat format = formatControls.getFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                        
            if (!AudioSystem.isLineSupported(info)) {
                shutDown("Line matching " + info + " not supported.");
                return;
            }

            // get and open the target data line for capture.

            try {
                this.line = (TargetDataLine) AudioSystem.getLine(info);
                this.line.open(format, this.line.getBufferSize());
            } catch (LineUnavailableException ex) { 
                shutDown("Unable to open the line: " + ex);
                return;
            } catch (SecurityException ex) { 
                shutDown(ex.toString());
                return;
            } catch (Exception ex) { 
                shutDown(ex.toString());
                return;
            }

            // play back the captured audio data
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int frameSizeInBytes = format.getFrameSize();
            int bufferLengthInFrames = this.line.getBufferSize() / 8;
            int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
            byte[] data = new byte[bufferLengthInBytes];
            int numBytesRead;
            
            this.line.start();

            while (this.thread != null) {
                if((numBytesRead = this.line.read(data, 0, bufferLengthInBytes)) == -1) {
                    break;
                }
                out.write(data, 0, numBytesRead);
            }

            // we reached the end of the stream.  stop and close the line.
            this.line.stop();
            this.line.close();
            this.line = null;

            // stop and close the output stream
            try {
                out.flush();
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // load bytes into the audio input stream for playback

            byte audioBytes[] = out.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
            this.audioInputStream = new AudioInputStream(bais, format, audioBytes.length / frameSizeInBytes);
                     
            try {
                this.audioInputStream.reset();
            } catch (Exception ex) { 
                ex.printStackTrace(); 
                return;
            }
            
            
            //this.mainform.setWaveSource(new WaveInfo(this.audioInputStream));
            WaveInfo waveRec = new WaveInfo(this.audioInputStream);
            waveRec.setRecordedAudio(true);
            waveRec.setFileName("Recorded Audio");
            this.mainform.addStreamtoList(0, waveRec);
            this.mainform.setStreamList();
            this.samplingGraph.setSourceStream(waveRec);
            this.samplingGraph.createWaveForm();
        }

        public void setSamplingGraph(SamplingGraphs samplingGraph){
            this.samplingGraph = samplingGraph;
        }

        public void setForm(MainForm mainForm) {
            this.mainform = mainForm;
    }
    } // End class Capture