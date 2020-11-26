package com.kingyu.flappybird.game;

import com.kingyu.flappybird.util.Constant;
import com.kingyu.flappybird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 游戏结束界面
 *
 * @author Kingyu
 *
 */
public class GameOverAnimation {
    private final BufferedImage scoreImg; // 计分牌
    private final BufferedImage overImg; // 结束标志
    private final BufferedImage againImg; // 继续标志

    public GameOverAnimation(){
        overImg = GameUtil.loadBufferedImage(Constant.OVER_IMG_PATH);
        scoreImg = GameUtil.loadBufferedImage(Constant.SCORE_IMG_PATH);
        againImg = GameUtil.loadBufferedImage(Constant.AGAIN_IMG_PATH);
    }

    private static final int SCORE_LOCATE = 5; // 计分牌位置补偿参数
    private int flash = 0; // 图片闪烁参数

    public void draw(Graphics g, Bird bird) {
        int x = Constant.FRAME_WIDTH - overImg.getWidth() >> 1;
        int y = Constant.FRAME_HEIGHT / 4;
        g.drawImage(overImg, x, y, null);

        // 绘制计分牌
        x = Constant.FRAME_WIDTH - scoreImg.getWidth() >> 1;
        y = Constant.FRAME_HEIGHT / 3;
        g.drawImage(scoreImg, x, y, null);

        // 绘制本局的分数
        g.setColor(Color.white);
        g.setFont(Constant.SCORE_FONT);
        x = (Constant.FRAME_WIDTH - scoreImg.getWidth() / 2 >> 1) + SCORE_LOCATE;// 位置补偿
        y += scoreImg.getHeight() >> 1;
        String str = Long.toString(bird.getCurrentScore());
        x -= GameUtil.getStringWidth(Constant.SCORE_FONT, str) >> 1;
        y += GameUtil.getStringHeight(Constant.SCORE_FONT, str);
        g.drawString(str, x, y);

        // 绘制最高分数
        if (bird.getBestScore() > 0) {
            str = Long.toString(bird.getBestScore());
            x = (Constant.FRAME_WIDTH + scoreImg.getWidth() / 2 >> 1) - SCORE_LOCATE;// 位置补偿
            x -= GameUtil.getStringWidth(Constant.SCORE_FONT, str) >> 1;
            g.drawString(str, x, y);
        }

        // 绘制继续游戏，图像闪烁
        final int COUNT = 30; // 闪烁周期
        if (flash++ > COUNT)
            GameUtil.drawImage(againImg,Constant.FRAME_WIDTH - againImg.getWidth() >> 1, Constant.FRAME_HEIGHT / 5 * 3, g);
        if (flash == COUNT * 2) // 重置闪烁参数
            flash = 0;
    }
}
