package com.kingyu.flappybird.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * 音乐工具类
 *
 * @author Kingyu
 * wav音频：JDK提供的类可直接解码 mp3音频：JDK没有提供支持，需要使用第三方的工具包
 */
public class MusicUtil {

    private static AudioStream fly;
    private static AudioStream crash;
    private static AudioStream score;

    // wav播放
    public static void playFly() {
        try {
            // create an AudioStream from the InputStream
            InputStream flyIn = new FileInputStream("resources/wav/fly.wav");
            fly = new AudioStream(flyIn);
        } catch (IOException ignored) {
        }
        AudioPlayer.player.start(fly);
    }

    public static void playCrash() {
        try {
            // create an AudioStream from the InputStream
            InputStream crashIn = new FileInputStream("resources/wav/crash.wav");
            crash = new AudioStream(crashIn);
        } catch (IOException ignored) {
        }
        AudioPlayer.player.start(crash);
    }

    public static void playScore() {
        try {
            // create an AudioStream from the InputStream
            InputStream scoreIn = new FileInputStream("resources/wav/score.wav");
            score = new AudioStream(scoreIn);
        } catch (IOException ignored) {
        }
        AudioPlayer.player.start(score);
    }
}
