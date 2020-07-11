package com.bird.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.bird.util.Constant;
import com.bird.util.MusicUtil;

/**
 * 游戏计时类, 单例类，方便调用
 * 
 * @author Kingyu
 *
 */
public class GameTime {
	private static final GameTime GAME_TIME = new GameTime();
	
	private int timeState; // 计时器的状态
	public static final int STATE_READY = 0; // 计时就绪
	public static final int STATE_START = 1; // 计时开始
	public static final int STATE_OVER = 2; // 计时结束

	private long startTime = 0; // 开始时间 ms
	private long endTime = 0; // 开始时间 ms
	private long score = 0; // 分数
	private long bestScore; // 最高分数

	private GameTime() {
		timeState = STATE_READY;
		bestScore = -1;
		try {
			loadBestTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static GameTime getInstance() {
		return GAME_TIME;
	}

	// 装载最高纪录
	private void loadBestTime() throws Exception {
		File file = new File(Constant.SCORE_FILE_PATH);
		if (file.exists()) {
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			bestScore = dis.readLong();
			dis.close();
		}
	}

	// 保存最高纪录
	public void saveBestScore(long time) throws Exception {
		File file = new File(Constant.SCORE_FILE_PATH);
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
		dos.writeLong(time);
		dos.close();
	}

	public long getBestScore() {
		return bestScore;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void setOverTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * 游戏用时，毫秒
	 * 
	 * @return
	 */
	public long getTime() {
		if (timeState == STATE_READY) {
			return startTime;
		} else if (timeState == STATE_START) {
			return (System.currentTimeMillis() - startTime);
		} else {
			return (endTime - startTime);
		}
	}

	//游戏时间转换为秒
	public long getTimeInSeconds() {
		return getTime() / 1000;
	}

	// 计时器是否就绪
	public boolean isReadyTiming() {
		return timeState == STATE_READY;
	}

	// 开始计时
	public void startTiming() {
		startTime = System.currentTimeMillis();
		timeState = STATE_START;
	}

	// 结束计时并判断是否保存记录
	public void endTiming() {
		endTime = System.currentTimeMillis();
		timeState = STATE_OVER;
		// 判断本次得分是否为最高分
		long score = TimeToScore();
		if (bestScore < score)
			bestScore = score;
		try {
			saveBestScore(bestScore);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final int FIRST_SCORE_TIME = 6600; // 从游戏开始到通过第一根水管的所需时间
	private static final int PER_SCORE_TIME = 2900; // 通过后续每一根水管的间隔的所需时间

	//将游戏时间转换为通过水管的数量
	public long TimeToScore() {
		long time = getTime();
		long temp = score;
		if (time >= FIRST_SCORE_TIME && time < FIRST_SCORE_TIME + PER_SCORE_TIME) {
			score = 1;   //time大于FIRST_SCORE_TIME且未到第二对水管
		} else if (time >= FIRST_SCORE_TIME + PER_SCORE_TIME) {
			score = (int) (time - FIRST_SCORE_TIME) / PER_SCORE_TIME + 1;
		}
		if (score - temp > 0) {
			MusicUtil.playScore(); //每次得分播放音效
		}
		return score;
	}

	/**
	 * 是否正在计时
	 * 
	 * @return
	 */
	public boolean isTiming() {
		return timeState == STATE_START;
	}

	//重置计时器
	public void reset() {
		timeState = STATE_READY;
		startTime = 0;
		endTime = 0;
		score = 0;
	}

}
