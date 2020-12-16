package com.kingyu.flappybird.component;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.kingyu.flappybird.util.Constant;
import com.kingyu.flappybird.util.GameUtil;

/**
 * 游戏背景类，实现游戏背景的绘制
 * 
 * @author Kingyu
 *
 */
public class GameBackground {

	private static final BufferedImage BackgroundImg;// 背景图片

	private final int speed; // 背景层的速度
	private int layerX; // 背景层的坐标

	public static final int GROUND_HEIGHT;

	static {
		BackgroundImg = GameUtil.loadBufferedImage(Constant.BG_IMG_PATH);
		assert BackgroundImg != null;
		GROUND_HEIGHT = BackgroundImg.getHeight() / 2;
	}

	// 在构造器中初始化
	public GameBackground() {
		this.speed = Constant.GAME_SPEED;
		this.layerX = 0;
	}

	// 绘制方法
	public void draw(Graphics g, Bird bird) {
		// 绘制背景色
		g.setColor(Constant.BG_COLOR);
		g.fillRect(0, 0, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);

		// 获得背景图片的尺寸
		int imgWidth = BackgroundImg.getWidth();
		int imgHeight = BackgroundImg.getHeight();

		int count = Constant.FRAME_WIDTH / imgWidth + 2; // 根据窗口宽度得到图片的绘制次数
		for (int i = 0; i < count; i++) {
			g.drawImage(BackgroundImg, imgWidth * i - layerX, Constant.FRAME_HEIGHT - imgHeight, null);
		}
		
		if(bird.isDead()) {  //小鸟死亡则不再绘制
			return;
		}
		movement();
	}

	// 背景层的运动逻辑
	private void movement() {
		layerX += speed;
		if (layerX > BackgroundImg.getWidth())
			layerX = 0;
	}
}
