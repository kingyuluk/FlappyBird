# Flappy Bird
![](https://img.shields.io/badge/jdk-1.8.0-FFB6C1.svg?&logo=github)
![](https://img.shields.io/github/v/release/kingyuluk/FlappyBird?color=FFB6C1&logo=github)
![](https://img.shields.io/github/license/kingyuluk/FlappyBird?color=FFB6C1&logo=github)
![](https://img.shields.io/github/repo-size/kingyuluk/FlappyBird?color=FFB6C1&logo=github)

## Overview

基于Java基础类库编写的Flappy Bird桌面平台版。

具备原版所有功能，相较原版加入了移动型水管、优化了难度梯度。

[RL FlappyBird](https://github.com/kingyuluk/RL-FlappyBird)
基于本项目集成了Amazon的Deep Java Library (DJL)，可以使用强化学习(DQN)训练Flappy Bird

## How to play

* 通过 ```java -jar FlappyBird.jar```直接运行

* 运行源码中的 ```App:main``` 方法

游戏使用空格键操作。

每局游戏随机刷新所有元素，小鸟受到重力作用会不断下坠，敲击空格键使小鸟振翅向上飞，游戏过程中需要玩家控制小鸟不断飞行，并注意躲避随机生成的水管，每飞过一对水管就会得分，飞行过程中如果撞到水管或掉落在地则游戏结束。


## Preview

* Game ready

![image](https://github.com/kingyuluk/FlappyBird/blob/master/resources/readme_img/start.png)

* Game start

![image](https://github.com/kingyuluk/FlappyBird/blob/master/resources/readme_img/play.gif)

* Game over

![image](https://github.com/kingyuluk/FlappyBird/blob/master/resources/readme_img/over.png)

## Notes

* 文本编码格式为UTF-8，若注释出现乱码请修改编译器的文本编码格式

* sun包在不同操作系统和不同版本的JDK中可能发生变化，因此无法确保工作在所有JAVA平台上

* 图片与音效资源皆来源于网络，仅供学习交流

## Package Contents
* com.kingyu.flappybird.app    游戏主体

* com.kingyu.flappybird.component   游戏的组件

* com.kingyu.flappybird.util   工具包

## [Change Log](https://github.com/kingyuluk/FlappyBird/blob/master/CHANGELOG.md)

[v1.2.2](https://github.com/kingyuluk/FlappyBird/tree/33ad51a97bcb6c2adce3fc944fa5aea00d210198) - July 12, 2020
* 移除了计时器，优化了游戏的记分方式，现在记分更准确了

[v1.2.1](https://github.com/kingyuluk/FlappyBird/tree/9429be613a21752d2c61e38ca7df87fb4a0b51b9) - July 12, 2020
* 使用AudioClip类的方法播放连续的短音频可能会导致线程冲突使游戏卡顿，改用sun.audio类的AudioPlayer方法播放音频
  
[v1.2.0](https://github.com/kingyuluk/FlappyBird/tree/ab33686c8c2ace54da3ddffe220b40a33100989f) - July 11, 2020
* 现在水管可以移动了，移动型水管刷新的概率会随着当前游戏分数递增

[v1.1.0](https://github.com/kingyuluk/FlappyBird/tree/074595b3408a1323b41226d4b4259c6aff696888) - July 11, 2020
* 添加了悬浮型的水管

[v1.0.0](https://github.com/kingyuluk/FlappyBird/tree/d158fa5ca5927e1febcd460e8d61b5a16756c761) - July 10, 2020
* 具备原版的游戏功能

## License
[MIT](License) © Kingyu Luk
