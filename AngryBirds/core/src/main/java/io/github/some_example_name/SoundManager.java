package io.github.some_example_name;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class SoundManager {

    private static Sound backgroundMusic;
    private static boolean isSoundOn = true; // Global sound state

    static {
        backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("sounds/song.mp3"));
    }

    public static void playMusic() {
        if (isSoundOn) {
            backgroundMusic.loop(); // Loop the music if sound is on
        }
    }

    public static void stopMusic() {
        backgroundMusic.stop(); // Stop music
    }

    public static void toggleSound() {
        isSoundOn = !isSoundOn;
        if (isSoundOn) {
            playMusic();
        } else {
            stopMusic();
        }
    }

    public static boolean isSoundOn() {
        return isSoundOn;
    }
}

