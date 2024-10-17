package com.kingyu.flappybird.util;

public class SpeedUtil {
    private static SpeedUtil instance;
    private int speed;

    // 私有构造函数，防止外部实例化
    private SpeedUtil() {
        speed = 0; // 初始化速度
    }

    // 获取单例实例
    public static SpeedUtil getInstance() {
        if (instance == null) {
            instance = new SpeedUtil();
        }
        return instance;
    }

    // 增加速度
    public void increaseSpeed() {
        speed += 1;
        System.out.println("当前速度: " + speed);
    }

    // 减少速度
    public void decreaseSpeed() {
        speed -= 1;
        System.out.println("当前速度: " + speed);
    }

    // 获取当前速度
    public int getSpeed() {
        return speed;
    }
}
