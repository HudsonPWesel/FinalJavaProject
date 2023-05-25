import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The most useful class ever, I would suggest a link, so that nobody has to
 * worry about file playing sound ever again, especially without having javaFX
 */
public class SoundManager {
    /**
     * doesn't actually play sound, just returns Clip, which can be used to call
     * .start()
     * 
     * @param file can use PathFinder for path to file
     * @return Clip that can be called .start()
     */
    public static Clip playSound(File file) {
        File f = file;
        AudioInputStream audioIn = null;
        try {
            audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        } catch (UnsupportedAudioFileException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            clip.open(audioIn);
            return clip;
        } catch (LineUnavailableException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        clip.start();
        return null;
    }
}
