package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl; 

public class Sound {
    
    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc; 

    public Sound () {
        soundURL[0] = getClass().getResource("/sound/GameTheme.wav");
        soundURL[1] = getClass().getResource("/sound/Gulp.wav");
        soundURL[2] = getClass().getResource("/sound/TreeBreak.wav");
        soundURL[3] = getClass().getResource("/sound/Hit.wav");        
        soundURL[4] = getClass().getResource("/sound/Complete.wav"); 
        soundURL[5] = getClass().getResource("/sound/PickupAxe.wav"); 
        soundURL[6] = getClass().getResource("/sound/Cursor.wav"); 
        soundURL[7] = getClass().getResource("/sound/Select.wav"); 
        soundURL[8] = getClass().getResource("/sound/GameOver.wav"); 
    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            
            // Load volume control if available
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            }

        }catch(Exception e) {
            e.printStackTrace(); 
        }
    }

    public void play(){
        if(clip != null) clip.start();
    }

    public void loop(){
        if(clip != null) clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.close(); 
        }
    }
    
    public void setVolume(double volume) {
        if (fc != null) {
            // Convert volume (0.0 to 1.0) to Decibels
            float gain = (float)(Math.log(volume) / Math.log(10.0) * 20.0);
            
            if (gain < fc.getMinimum()) gain = fc.getMinimum();
            else if (gain > fc.getMaximum()) gain = fc.getMaximum();
            
            fc.setValue(gain);
        }
    }
}