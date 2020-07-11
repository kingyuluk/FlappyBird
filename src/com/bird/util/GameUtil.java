package com.bird.util;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import javax.imageio.ImageIO;

/**
 * 工具类，游戏中用到的工具都在此类
 * 
 * @author Kingyu
 *
 */
public class GameUtil {

	private GameUtil() {
	} // 私有化，防止其他类实例化此类

	/**
	 * 装载图片的方法
	 * 
	 * @param imgPath 图片路径
	 * @return
	 */
	public static BufferedImage loadBUfferedImage(String imgPath) {
		try {
			return ImageIO.read(new FileInputStream(imgPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断任意概率的概率性事件是否发生
	 * 
	 * @param numerator   分子，不小于0的值
	 * @param denominator 分母，不小于0的值
	 * @return 概率性事件发生返回true，否则返回false
	 */
	public static boolean isInProbability(int numerator, int denominator)throws Exception{
		// 分子分母不小于0
		if (numerator <= 0 || denominator <= 0) {
			throw new Exception("传入了非法的参数");
		}
		//分子大于分母，一定发生
		if(numerator >= denominator) {
			return true;
		}

		return getRandomNumber(1, denominator + 1) <= numerator;
	}

	/**
	 * 返回指定区间的一个随机数
	 * 
	 * @param min 区间最小值，包含
	 * @param max 区间最大值，不包含
	 * @return 该区间的随机数
	 */
	public static int getRandomNumber(int min, int max) {
		return (int) (Math.random() * (max - min) + min);
	}
	
	/**
	 * 获得指定字符串在指定字体的宽高
	 */
	public static int getStringWidth(Font font, String str) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true); 
		int textHeight = (int)(font.getStringBounds(str, frc).getWidth());
		return textHeight;
	}

	public static int getStringHeight(Font font, String str) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true); 
		int textHeight = (int)(font.getStringBounds(str, frc).getHeight());
		return textHeight;
	}
	
}
