package com.killerchess.view.utils;

import com.killerchess.view.View;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class SoundPlayer {

    private static final String SOUNDS_LOCAL_PATH = "sounds/";
    private static final String END_GAME = SOUNDS_LOCAL_PATH + "end_game.wav";
    private static final String START_GAME = SOUNDS_LOCAL_PATH + "start_game.wav";
    private static final String MOVE = SOUNDS_LOCAL_PATH + "move.wav";
    private static final String CLICK = SOUNDS_LOCAL_PATH + "click.wav";

    public static void playOnChessmanMove() {
        playSound(MOVE);
    }

    public static void playOnChessmanClick() {
        playSound(CLICK);
    }

    public static void playOnGameStart() {
        playSound(START_GAME);
    }

    public static void playOnGameEnd() {
        playSound(END_GAME);
    }

    private static void playSound(String filePath) {

        try {
//            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(filePath)));
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(View.class.getClassLoader().getResourceAsStream(filePath)));

            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.setFramePosition(0);

            clip.start();
            while (clip.getFramePosition() != clip.getFrameLength()) {
            }
            clip.close();

            inputStream.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

}
