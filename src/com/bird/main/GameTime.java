package com.bird.main;

/**
 * 游戏计时类
 * 
 * @author Kingyu
 *
 */
public class GameTime {
	private int gameState; // 游戏的状态
	public static final int STATE_READY = 0; // 游戏未开始
	public static final int STATE_START = 1; // 游戏开始
	public static final int STATE_OVER = 2; // 游戏结束

	private long startTime = 0; // 开始时间 ms
	private long endTime = 0; // 开始时间 ms

	public GameTime() {
		gameState = STATE_READY;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void setOverTime(long endTime) {
		this.endTime = endTime;
	}

	public int getGameState() {
		return gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}

	/**
	 * 游戏用时，毫秒
	 * @return
	 */
	public long getTime() {
		if(gameState == STATE_READY) {
			return startTime;
		}else if(gameState == STATE_START){
			return System.currentTimeMillis() - startTime;
		}else {
			return endTime - startTime;
		}
	}

	//是否准备开始计时
	public boolean isReadyTiming() {
		return gameState == STATE_READY;
	}

	// 开始计时
	public void startTiming() {
		startTime = System.currentTimeMillis();
		gameState = STATE_START;
	}

	// 结束计时
	public void endTiming() {
		endTime = System.currentTimeMillis();
		gameState = STATE_OVER;
	}

	/**
	 * 是否正在计时
	 * 
	 * @return
	 */
	public boolean isTiming() {
		return gameState == STATE_START;
	}

}
