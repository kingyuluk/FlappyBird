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
public class GameScore {
	private static final GameScore GAME_SCORE = new GameScore();
	
	private long score = 0; // 分数
	private long bestScore; // 最高分数

	private GameScore() {
		bestScore = -1;
		try {
			loadBestScore();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static GameScore getInstance() {
		return GAME_SCORE;
	}

	// 装载最高纪录
	private void loadBestScore() throws Exception {
		File file = new File(Constant.SCORE_FILE_PATH);
		if (file.exists()) {
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			bestScore = dis.readLong();
			dis.close();
		}
	}

	// 保存最高纪录
	public void saveBestScore(long score) throws Exception {
		File file = new File(Constant.SCORE_FILE_PATH);
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
		dos.writeLong(score);
		dos.close();
	}

	public long getBestScore() {
		return bestScore;
	}

	public long getScore() {
		return score;
	}
	public void setScore(Bird bird) {
		if(!bird.isDead()){
			MusicUtil.playScore(); //每次得分播放音效
			score += 1;
			//小鸟没死时记分
		}
	}
	
	// 判断是否为最高纪录
	public void isSaveScore() {
		long score = getScore();
		if (bestScore < score)
			bestScore = score;
		try {
			saveBestScore(bestScore);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//重置计分器
	public void reset() {
		score = 0;
	}
	
//	private int timeState; // 计时器的状态
//	public static final int STATE_READY = 0; // 计时就绪
//	public static final int STATE_START = 1; // 计时开始
//	public static final int STATE_OVER = 2; // 计时结束
//  以下为游戏计时的相关方法，因改进了记分方式目前无用
//	/**
//	 * 游戏用时，毫秒
//	 * 
//	 * @return
//	 */
//	public long getTime() {
//		if (timeState == STATE_READY) {
//			return startTime;
//		} else if (timeState == STATE_START) {
//			return (System.currentTimeMillis() - startTime);
//		} else {
//			return (endTime - startTime);
//		}
//	}
//
//	//游戏时间转换为秒
//	public long getTimeInSeconds() {
//		return getTime() / 1000;
//	}
//
//	// 计时器是否就绪
//	public boolean isReadyTiming() {
//		return timeState == STATE_READY;
//	}
//
//	// 开始计时
//	public void startTiming() {
//		startTime = System.currentTimeMillis();
//		timeState = STATE_START;
//	}
//
//	// 结束计时
//	public void endTiming() {
//		endTime = System.currentTimeMillis();
//		timeState = STATE_OVER;
//	}
//
//	private static final int FIRST_SCORE_TIME = 6600; // 从游戏开始到通过第一根水管的所需时间
//	private static final int PER_SCORE_TIME = 2900; // 通过后续每一根水管的间隔的所需时间
//
//	//将游戏时间转换为通过水管的数量
//	public long TimeToScore() {
//		long time = getTime();
//		long temp = score;
//		if (time >= FIRST_SCORE_TIME && time < FIRST_SCORE_TIME + PER_SCORE_TIME) {
//			score = 1;   //time大于FIRST_SCORE_TIME且未到第二对水管
//		} else if (time >= FIRST_SCORE_TIME + PER_SCORE_TIME) {
//			score = (int) (time - FIRST_SCORE_TIME) / PER_SCORE_TIME + 1;
//		}
//		if (score - temp > 0) {
//			MusicUtil.playScore(); //每次得分播放音效
//		}
//		return score;
//	}
//
//	/**
//	 * 是否正在计时
//	 * 
//	 * @return
//	 */
//	public boolean isTiming() {
//		return timeState == STATE_START;
//	}

}
