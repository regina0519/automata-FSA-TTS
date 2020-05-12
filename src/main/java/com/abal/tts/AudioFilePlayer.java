package com.abal.tts;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
 
import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
 
public class AudioFilePlayer {
    private SourceDataLine line;
 
 
    public void play(String filePath) {
        AudioInputStream in = null;
        try {
            final File file = new File(filePath);
            in = getAudioInputStream(file);
            final AudioFormat outFormat = getOutFormat(in.getFormat());
            final Info info = new Info(SourceDataLine.class, outFormat);
            line =(SourceDataLine) AudioSystem.getLine(info);
            if (line != null) {
                line.open(outFormat);
                line.start();
                stream(getAudioInputStream(outFormat, in), line);
                line.drain();
                line.stop();
            }
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(AudioFilePlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AudioFilePlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(AudioFilePlayer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(AudioFilePlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void stop(){
        if(this.line==null)return;
        this.line.drain();
        this.line.stop();
    }
 
    private AudioFormat getOutFormat(AudioFormat inFormat) {
        final int ch = inFormat.getChannels();
        final float rate = inFormat.getSampleRate();
        return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
    }
 
    private void stream(AudioInputStream in, SourceDataLine line) 
        throws IOException {
        final byte[] buffer = new byte[65536];
        for (int n = 0; n != -1; n = in.read(buffer, 0, buffer.length)) {
            line.write(buffer, 0, n);
        }
    }
}