package main.java.outros;
 
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.FloatControl;
 
/**
 * This is an example program that demonstrates how to play back an audio file
 * using the SourceDataLine in Java Sound API.
 * @author www.codejava.net
 *
 */
public class ReproduzirAudio {
 
    // size of the byte buffer used to read/write the audio stream
    private static final int BUFFER_SIZE = 4096;
     
    /**
     * Play a given audio file.
     * @param audioFilePath Path of the audio file.
     */
    public void play(URL url ) {
        //File audioFile = new File();
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
            SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);
 
            audioLine.open(format);
            FloatControl gainControl = 
            	    (FloatControl) audioLine.getControl(FloatControl.Type.MASTER_GAIN);
            	gainControl.setValue(-1.0f);
 
            audioLine.start();
             
            System.out.println("Playback started.");
             
            byte[] bytesBuffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
 
            while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
                audioLine.write(bytesBuffer, 0, bytesRead);
            }
             
            audioLine.drain();
            audioLine.close();
            audioStream.close();
             
            System.out.println("Playback completed.");
             
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }      
    }
     
   
 
}