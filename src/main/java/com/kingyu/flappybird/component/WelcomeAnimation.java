package com.kingyu.flappybird.component;

import com.kingyu.flappybird.util.Constant;
import com.kingyu.flappybird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 游戏启动界面
 *
 * @author Kingyu
 *
 */
public class WelcomeAnimation {

	private final BufferedImage titleImg;
	private final BufferedImage noticeImg;

	private int flashCount = 0; // 图像闪烁参数

	public WelcomeAnimation() {
		titleImg = GameUtil.loadBufferedImage(Constant.TITLE_IMG_PATH);
		noticeImg = GameUtil.loadBufferedImage(Constant.NOTICE_IMG_PATH);
	}

	public void draw(Graphics g) {
		int x = (Constant.FRAME_WIDTH - titleImg.getWidth()) >> 1;
		int y = Constant.FRAME_HEIGHT / 3;
		g.drawImage(titleImg, x, y, null);

		// 使notice的图像闪烁
		final int CYCLE = 30; // 闪烁周期
		if (flashCount++ > CYCLE)
			GameUtil.drawImage(noticeImg, Constant.FRAME_WIDTH - noticeImg.getWidth() >> 1, Constant.FRAME_HEIGHT / 5 * 3, g);
		if (flashCount == CYCLE * 2)
			flashCount = 0;
	}

}
