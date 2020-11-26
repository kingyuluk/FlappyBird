package com.kingyu.flappybird.game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.kingyu.flappybird.util.Constant;
import com.kingyu.flappybird.util.GameUtil;

/**
 * 游戏中各种元素层的类
 *
 * @author Kingyu
 */

public class GameElementLayer {
    private final List<Pipe> pipes; // 水管的容器

    // 构造器
    public GameElementLayer() {
        pipes = new ArrayList<>();
    }

    // 绘制方法
    public void draw(Graphics g, Bird bird) {
        // 遍历水管容器，如果可见则绘制，不可见则归还
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            if (pipe.isVisible()) {
                pipe.draw(g, bird);
            } else {
                Pipe remove = pipes.remove(i);
                PipePool.giveBack(remove);
                i--;
            }
        }
        // 碰撞检测
        isCollideBird(bird);
        pipeBornLogic(bird);
    }

    /**
     * 添加水管的逻辑： 当容器中添加的最后一个元素完全显示到屏幕后，添加下一对； 水管成对地相对地出现，空隙高度为窗口高度的1/6；
     * 每对水管的间隔距离为屏幕高度的1/4； 水管的高度的取值范围为窗口的[1/8~5/8]
     */
    public static final int VERTICAL_INTERVAL = Constant.FRAME_HEIGHT / 5;
    public static final int HORIZONTAL_INTERVAL = Constant.FRAME_HEIGHT >> 2;
    public static final int MIN_HEIGHT = Constant.FRAME_HEIGHT >> 3;
    public static final int MAX_HEIGHT = ((Constant.FRAME_HEIGHT) >> 3) * 5;

    private void pipeBornLogic(Bird bird) {
        if (bird.isDead()) {
            // 鸟死后不再添加水管
            return;
        }
        if (pipes.size() == 0) {
            // 若容器为空，则添加一对水管
            int topHeight = GameUtil.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1); // 随机生成水管高度

            Pipe top = PipePool.get("Pipe");
            top.setAttribute(Constant.FRAME_WIDTH, -Constant.TOP_PIPE_LENGTHENING,
                    topHeight + Constant.TOP_PIPE_LENGTHENING, Pipe.TYPE_TOP_NORMAL, true);

            Pipe bottom = PipePool.get("Pipe");
            bottom.setAttribute(Constant.FRAME_WIDTH, topHeight + VERTICAL_INTERVAL,
                    Constant.FRAME_HEIGHT - topHeight - VERTICAL_INTERVAL, Pipe.TYPE_BOTTOM_NORMAL, true);

            pipes.add(top);
            pipes.add(bottom);
        } else {
            // 判断最后一对水管是否完全进入游戏窗口，若进入则添加水管
            Pipe lastPipe = pipes.get(pipes.size() - 1); // 获得容器中最后一个水管
            int currentDistance = lastPipe.getX() - bird.getBirdX() + Bird.BIRD_WIDTH / 2; // 小鸟和最后一根水管的距离
            final int SCORE_DISTANCE = Pipe.PIPE_WIDTH * 2 + HORIZONTAL_INTERVAL; // 小于得分距离则得分
            if (lastPipe.isInFrame()) {
                if (pipes.size() >= PipePool.FULL_PIPE - 2) {
                    ScoreCounter.getInstance().score(bird);
                }
                try {
                    int currentScore = (int) ScoreCounter.getInstance().getCurrentScore() + 1; // 获取当前分数
                    // 移动水管刷新的概率随当前分数递增，当得分大于19后全部刷新移动水管
                    if (GameUtil.isInProbability(currentScore, 20)) {
                        if (GameUtil.isInProbability(1, 4)) // 生成移动水管和移动悬浮水管的概率
                            addMovingHoverPipe(lastPipe);
                        else
                            addMovingNormalPipe(lastPipe);
                    } else {
                        if (GameUtil.isInProbability(1, 2)) // 生成静止普通水管和静止悬浮水管的概率
                            addNormalPipe(lastPipe);
                        else
                            addHoverPipe(lastPipe);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 添加普通水管
     *
     * @param lastPipe 传入最后一根水管以获取x坐标
     */
    private void addNormalPipe(Pipe lastPipe) {
        int topHeight = GameUtil.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1); // 随机生成水管高度
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL; // 新水管的x坐标 = 最后一对水管的x坐标 + 水管的间隔

        Pipe top = PipePool.get("Pipe"); // 从水管对象池中获取对象

        // 设置x, y, height, type属性
        top.setAttribute(x, -Constant.TOP_PIPE_LENGTHENING, topHeight + Constant.TOP_PIPE_LENGTHENING,
                Pipe.TYPE_TOP_NORMAL, true);

        Pipe bottom = PipePool.get("Pipe");
        bottom.setAttribute(x, topHeight + VERTICAL_INTERVAL, Constant.FRAME_HEIGHT - topHeight - VERTICAL_INTERVAL,
                Pipe.TYPE_BOTTOM_NORMAL, true);

        pipes.add(top);
        pipes.add(bottom);
    }

    /**
     * 添加悬浮水管
     *
     * @param lastPipe 传入最后一根水管以获取x坐标
     */
    private void addHoverPipe(Pipe lastPipe) {

        // 随机生成水管高度,屏幕高度的[1/4,1/6]
        int topHoverHeight = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 6, Constant.FRAME_HEIGHT / 4);
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL; // 新水管的x坐标 = 最后一对水管的x坐标 + 水管的间隔
        int y = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 12, Constant.FRAME_HEIGHT / 6); // 随机水管的y坐标，窗口的[1/6,1/12]

        int type = Pipe.TYPE_HOVER_NORMAL;

        // 生成上部的悬浮水管
        Pipe topHover = PipePool.get("Pipe");
        topHover.setAttribute(x, y, topHoverHeight, type, true);

        // 生成下部的悬浮水管
        int bottomHoverHeight = Constant.FRAME_HEIGHT - 2 * y - topHoverHeight - VERTICAL_INTERVAL;
        Pipe bottomHover = PipePool.get("Pipe");
        bottomHover.setAttribute(x, y + topHoverHeight + VERTICAL_INTERVAL, bottomHoverHeight, type, true);

        pipes.add(topHover);
        pipes.add(bottomHover);

    }

    /**
     * 添加移动的悬浮水管
     *
     * @param lastPipe 传入最后一根水管以获取x坐标
     */
    private void addMovingHoverPipe(Pipe lastPipe) {

        // 随机生成水管高度,屏幕高度的[1/4,1/6]
        int topHoverHeight = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 6, Constant.FRAME_HEIGHT / 4);
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL; // 新水管的x坐标 = 最后一对水管的x坐标 + 水管的间隔
        int y = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 12, Constant.FRAME_HEIGHT / 6); // 随机水管的y坐标，窗口的[1/6,1/12]

        int type = Pipe.TYPE_HOVER_HARD;

        // 生成上部的悬浮水管
        Pipe topHover = PipePool.get("MovingPipe");
        topHover.setAttribute(x, y, topHoverHeight, type, true);

        // 生成下部的悬浮水管
        int bottomHoverHeight = Constant.FRAME_HEIGHT - 2 * y - topHoverHeight - VERTICAL_INTERVAL;
        Pipe bottomHover = PipePool.get("MovingPipe");
        bottomHover.setAttribute(x, y + topHoverHeight + VERTICAL_INTERVAL, bottomHoverHeight, type, true);

        pipes.add(topHover);
        pipes.add(bottomHover);

    }

    /**
     * 添加移动的普通水管
     *
     * @param lastPipe 传入最后一根水管以获取x坐标
     */
    private void addMovingNormalPipe(Pipe lastPipe) {
        int topHeight = GameUtil.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1); // 随机生成水管高度
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL; // 新水管的x坐标 = 最后一对水管的x坐标 + 水管的间隔

        Pipe top = PipePool.get("MovingPipe");
        top.setAttribute(x, -Constant.TOP_PIPE_LENGTHENING, topHeight + Constant.TOP_PIPE_LENGTHENING,
                Pipe.TYPE_TOP_HARD, true);

        Pipe bottom = PipePool.get("MovingPipe");
        bottom.setAttribute(x, topHeight + VERTICAL_INTERVAL, Constant.FRAME_HEIGHT - topHeight - VERTICAL_INTERVAL,
                Pipe.TYPE_BOTTOM_HARD, true);

        pipes.add(top);
        pipes.add(bottom);
    }

    /**
     * 判断元素和小鸟是否发生碰撞
     *
     * @param bird 传入小鸟对象
     */
    public void isCollideBird(Bird bird) {
        // 若鸟已死则不再判断
        if (bird.isDead()) {
            return;
        }
        // 遍历水管容器
        for (Pipe pipe : pipes) {
            // 判断碰撞矩形是否有交集
            if (pipe.getPipeRect().intersects(bird.getBirdRect())) {
                bird.deadBirdFall();
                return;
            }
        }
    }

    // 重置元素层
    public void reset() {
        for (Pipe pipe : pipes) {
            PipePool.giveBack(pipe);
        }
        pipes.clear();
    }
}
