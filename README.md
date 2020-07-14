# Flappy Bird
Flappy Bird for desktop platforms.

基于Java和JDK基本库编写

开发平台为macOS 10.15.5，开发工具为Eclipse IDE (4.16.0)，Java SE 8[1.8.0_251]


## Overview

本项目为Flappy bird的桌面平台版，具备原版的所有功能，且相较于原版优化了游戏难度并加入移动型水管，增加可玩性。

## How to play
直接运行FlappyBird.jar即可开始游戏。

游戏使用空格键操作。

每局游戏随机刷新所有元素，小鸟受到重力作用会不断下坠，敲击空格键使小鸟振翅向上飞，游戏过程中需要玩家控制小鸟不断飞行，并注意躲避随机生成的水管，每飞过一对水管就会得分，飞行过程中如果撞到水管或掉落在地则游戏结束。


## 游戏界面

### 游戏启动
![image](https://github.com/kingyuluk/FlappyBird/blob/master/resources/readme_img/start.png)

### 运行示例
![image](https://github.com/kingyuluk/FlappyBird/blob/master/resources/readme_img/play.gif)

### 游戏结束
![image](https://github.com/kingyuluk/FlappyBird/blob/master/resources/readme_img/over.png)


## Package Contents
* com.bird.app    游戏的入口

* com.bird.main   游戏的内容

* com.bird.util   自定义的工具

## Change Log

v1.2.2 - July 12, 2020
* 移除了计时器，优化了游戏的记分方式，现在记分更准确了

v1.2.1 - July 12, 2020
* 使用AudioClip类的方法播放连续的短音频可能会导致线程冲突使游戏卡顿

  改用sun.audio类的AudioPlayer方法播放音频
  
v1.2.0 - July 11, 2020
* 现在水管可以移动了，移动型水管刷新的概率会随着当前游戏分数递增

v1.1.0 - July 11, 2020
* 添加了悬浮型的水管

v1.0.0 - July 10, 2020
* 具备完整的游戏功能

## Notes

* 文本编码格式为UTF-8，若注释出现乱码请修改编译器的文本编码格式

* sun包在不同操作系统和不同版本的JDK中可能发生变化，因此无法确保工作在所有JAVA平台上

## Contact
* email: <kingyuluk@hotmail.com>

## License

* 图片与音效资源皆来源于网络，仅供学习交流
