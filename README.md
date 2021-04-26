# banana-helper
[![GitHub stars](https://img.shields.io/github/stars/zhuweitung/banana-helper?style=flat-square)](https://github.com/zhuweitung/banana-helper/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/zhuweitung/banana-helper?style=flat-square)](https://github.com/zhuweitung/banana-helper/network)
[![GitHub issues](https://img.shields.io/github/issues/zhuweitung/banana-helper?style=flat-square)](https://github.com/zhuweitung/banana-helper/issues)
[![GitHub license](https://img.shields.io/github/license/zhuweitung/banana-helper?style=flat-square)](https://github.com/zhuweitung/banana-helper/blob/main/LICENSE)
[![GitHub contributors](https://img.shields.io/github/contributors/zhuweitung/banana-helper?style=flat-square)](https://github.com/zhuweitung/banana-helper/graphs/contributors)
![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/zhuweitung/banana-helper?style=flat-square)


- [目录](#目录)
  - [项目简介](#项目简介)
  - [功能描述](#功能描述)
  - [使用说明](#使用说明)
  - [配置说明](#配置说明)
  - [参考说明](#参考说明)
  - [特别声明](#特别声明)


## 项目简介

本项目是一个利用GitHub Actions来实现AcFun每日签到、投蕉、点赞、弹幕、分享的项目。目标是让每根香蕉最终的归宿是UP的口袋，而不是一个月之后烂掉。

## 功能描述

+ [x] 每天自动运行任务，运行时间可自定义（默认18:01执行，受Github服务器限制大概率会延迟几分钟执行）；
+ [x] 每日任务-签到（+蕉x5/随机爆蕉）；
+ [x] 每日任务-完成一次点赞（+蕉x5/翻倍）；
+ [x] 每日任务-分享内容（+蕉x5）；
+ [x] 每日任务-发一次评论或弹幕（+蕉x5）；
+ [ ] 每日任务-看番剧10分钟（+蕉x5）；
+ [ ] 每日任务-看视频20分钟（+蕉x16）；
+ [ ] 直播任务-看60分钟直播开扭蛋（+蕉x29）；
+ [x] 给每日香蕉榜TOP50投蕉点赞弹幕（-蕉x5N）；
+ [x] 给关注的up24小时内的视频动态投蕉点赞弹幕（-蕉x5N）；

## 使用说明

1. **Fork**本仓库；
2. 打开浏览器，访问[A站](https://www.acfun.cn/)，并登录自己的账号；
3. 在A站的页面上按下<kbd>F12</kbd>进入开发者工具；

<img src="https://gitee.com/zhuweitung/picbed/raw/master/20210420200020.png" style="display:inline-block"/>

4. 根据上述图解操作后获得`acPasstoken`和`auth_key`的值，将这两个值填入仓库>`Settings`>`Secrets`中；

<img src="https://gitee.com/zhuweitung/picbed/raw/master/20210419210900.png" style="display:inline-block"/>

5. 手动执行任务，测试配置是否正确；

<img src="https://gitee.com/zhuweitung/picbed/raw/master/20210419211126.png" style="display:inline-block"/>

6. 使用Turbo版Server酱推送说明

   + 前往[sct.ftqq.com](https://sct.ftqq.com/sendkey)点击登入，创建账号。

   + 点击点[SendKey](https://sct.ftqq.com/sendkey)，生成一个`Key`。将其增加到`Github Secrets`中，变量名为`SERVERPUSHKEY`

   + [配置消息通道](https://sct.ftqq.com/forward)，选择方糖服务号，保存即可。

7. Fork仓库后，GitHub Actions默认不自动执行任务，需要有commit记录才会激活，最简单就是修改readme.md，在文末添加空行提交，这样自动执行定时任务就激活了

## 配置说明

配置文件位于**src/main/resources/config.json**

| Key              | Value        | 说明                                                         |
| ---------------- | ------------ | ------------------------------------------------------------ |
| throwBananaNum   | Integer      | 每日投蕉数,默认30                                            |
| throwAndLike     | [0,1]        | 投蕉并点赞，默认`开启`                                       |
| throwAndDanmu    | [0,1]        | 投蕉并发送弹幕，默认`开启`                                   |
| isLike           | [0,1]        | 是否点赞（手机端每日任务），默认`开启`                       |
| isDanmu          | [0,1]        | 是否发送弹幕（手机端每日任务），默认`开启`                   |
| danmuPriority    | [0,1]        | 弹幕优先级，0：从视频弹幕池中取频率最高的发送，1：从自定义弹幕池中随机取弹幕，默认`0` |
| danmuPool        | [String,...] | 自定义弹幕池                                                 |
| isShare          | [0,1]        | 是否分享（手机端每日任务），默认`开启`                       |
| bananaPriority   | [0,1]        | 投蕉优先级，0：优先给每日香蕉榜投蕉（TOP50），1：优先给关注的up投蕉，默认`0` |
| followUpPriority | [uid,...]    | 关注up的投蕉优先级，up的uid可以进入up的主页获取              |
| userAgent        | String       | UA标识，AcFun/6.39.0 (iPhone; iOS 14.3; Scale/2.00)          |
| skipUpList       | [uid,...]    | 跳过投蕉点赞弹幕up列表，一般是一些喜欢刷屏的up（我说的就是你`萌次元`） |

## 参考说明

- 部分API参考[Sitoi/dailycheckin](https://github.com/Sitoi/dailycheckin)
- 代码结构参考[JunzhouLiu](https://github.com/JunzhouLiu)
- 消息通知参考[itning](https://github.com/itning)

## 特别声明

- 本仓库发布的脚本及其中涉及的任何解锁和解密分析脚本，仅用于测试和学习研究，禁止用于商业用途，不能保证其合法性，准确性，完整性和有效性，请根据情况自行判断。
- 本人对任何脚本问题概不负责，包括但不限于由任何脚本错误导致的任何损失或损害。
- 间接使用脚本的任何用户，包括但不限于建立VPS或在某些行为违反国家/地区法律或相关法规的情况下进行传播, 本人对于由此引起的任何隐私泄漏或其他后果概不负责。
- 请勿将本仓库的任何内容用于商业或非法目的，否则后果自负。
- 任何以任何方式查看此项目的人或直接或间接使用该项目的任何脚本的使用者都应仔细阅读此声明。本人保留随时更改或补充此免责声明的权利。一旦使用并复制了任何相关脚本或Script项目的规则，则视为您已接受此免责声明。
