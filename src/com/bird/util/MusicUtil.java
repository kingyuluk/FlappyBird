package com.bird.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * 音乐工具类
 * 
 * @author Kingyu wav音频：JDK提供的类可直接解码 mp3音频：JDK没有提供支持，需要使用第三方的工具包
 * 
 */
public class MusicUtil {

	private static AudioStream fly;
	private static AudioStream crash;
	private static AudioStream score;
	
	private static InputStream flyIn;
	private static InputStream crashIn;
	private static InputStream scoreIn;
	
	//wav播放
	public static void playFly() {
		try {
			// create an audiostream from the inputstream
			flyIn = new FileInputStream("resources/wav/fly.wav");
			fly = new AudioStream(flyIn);
		} catch (FileNotFoundException fnfe) {
		} catch (IOException ioe) {
		}
		AudioPlayer.player.start(fly);
	}
	
	public static void playCrash() {
		try {
			// create an audiostream from the inputstream
			crashIn = new FileInputStream("resources/wav/crash.wav");
			crash = new AudioStream(crashIn);
		} catch (FileNotFoundException fnfe) {
		} catch (IOException ioe) {
		}
		AudioPlayer.player.start(crash);
	}
	
	public static void playScore() {
		try {
			// create an audiostream from the inputstream
			scoreIn = new FileInputStream("resources/wav/score.wav");
			score = new AudioStream(scoreIn);
		} catch (FileNotFoundException fnfe) {
		} catch (IOException ioe) {
		}
		AudioPlayer.player.start(score);
	}
}
