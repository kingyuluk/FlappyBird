package com.bird.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bird.util.Constant;
import com.bird.util.GameUtil;

/**
 * 游戏背景类，绘制游戏背景的内容都在此类
 * 
 * @author Kingyu
 *
 */
public class GameBackground {

	// 背景图片
	private BufferedImage BackgroundImg;

	// 在构造器中对资源初始化
	public GameBackground() {
		BackgroundImg = GameUtil.loadBUfferedImage(Constant.BG_IMG_PATH);
	}

	// 定义绘制方法,用系统提供的画笔将图片绘制到指定位置
	public void draw(Graphics g) {
		// 绘制背景色
		g.setColor(Constant.BG_COLOR);
		g.fillRect(0, 0, Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);

		// 获得背景图片的尺寸
		int imgWidth = BackgroundImg.getWidth();
		int imgHeight = BackgroundImg.getHeight();

		int count = Constant.FRAME_WIDTH / imgWidth + 1; // 绘制次数
		for (int i = 0; i < count; i++) {
			g.drawImage(BackgroundImg, imgWidth * i, Constant.FRAME_HEIGHT - imgHeight, null);
		}
	}

}
