# 暴风语音接入平台

> 文档版本:V1.1

## 简介

暴风大耳朵是基于暴风电视的一款免遥控的语音软件,任何第三方软件需要在暴风电视上使用语音功能,需要接入暴风语音平台<br>


## [集成](https://github.com/RiverrunNetwork/voicelink/tree/master/TellA/app/libs)<br>
目前大耳朵的sdk 采用的的jar包的形式 主要是为了兼容eclipse的用户 将如下jar一次集成到项目中<br>
## [下载大耳朵](https://github.com/RiverrunNetwork/voicelink/tree/master/TellA/apk)<br>
## 鉴权
任何第三方应用和大耳朵进行语音交互都需要和大耳朵进行语音鉴权<br>

- 如何鉴权 ？<br>
将如下代码放到应用的AndroidManifest文件中<br>

```java
<provider
            android:name="com.bftv.fui.authentication.AuthenticationProvider"
            android:authorities="com.bftv.voice.provider.you_package"
            android:exported="true" />
```
## [应用指令词](https://github.com/RiverrunNetwork/voicelink/blob/master/word_app.md)
任何一个应用都可以向大耳朵注册特定的指令词语 比如微信 向大耳朵注册指令词 “打开朋友圈” 那么当用户命中“打开朋友圈”这个词语那么我们就将当前用户的指令词 分发给微信.当你通过该接口注册了 应用指令词 那么你的整个应用都是生效的<br>
## [界面指令词](https://github.com/RiverrunNetwork/voicelink/blob/master/word_view.md)
任何一个界面都可以大耳朵注册特定的指令词语  比如微信的和好友聊天界面 向大耳朵注册了指令词 “打开软键盘” 那么当用户命中了 “打开软键盘” 我们就将该用户提前注册好的 分发给朋友圈界面.当你通过该接口注册了 界面指令词 那么你当前界面就会生效<br>
## [系统指令词](https://github.com/RiverrunNetwork/voicelink/blob/master/word_system.md)
任何第三方都可以使用大耳朵已经成熟的指令词模块 比如第x集 快进3分 ...<br>
## [语音bar](https://github.com/RiverrunNetwork/voicelink/blob/master/bar.md)
大耳朵开放了语音bar,任何第三方app,都可以引入语音bar<br>
## 主动拉起大耳朵
为了省去 喊暴风大耳朵的麻烦操作 第三方可以在合适的场景下 直接启动语音 进行说话<br>
```java
 TellManager.getInstance().farPull(App.sApp,packageName)
```
## 消息控制
任何第三方应用都能向大耳朵发送消息 例如 你可以发送 “刘德华的电影” 那么 大耳朵会对该消息进行 分词-理解－查找数据－展示,当然您只需要传递一个消息就可以了，剩下的工作交给大耳朵就好
```java
TellManager.getInstance().sendAsr(App.sApp,packageName,"下一页")
```
## 问题反馈
- 如果您有任何问题 可以把您的问题写到Issues里面 我们会认真回答每一个人的任何问题<br>
<img src="https://github.com/RiverrunNetwork/voicelink/blob/master/TellA/img/%E6%9A%B4%E9%A3%8E%E8%AF%AD%E9%9F%B3%E6%8E%A5%E5%85%A5%E5%B9%B3%E5%8F%B0%E7%BE%A4%E4%BA%8C%E7%BB%B4%E7%A0%81.png" width="300" height="300" /> 

## 合作伙伴
<img src="http://live-fengmi.b0.upaiyun.com/imgconfig/ai/taobao.png" width="70" height="70" />&ensp;&ensp;&ensp;&ensp; <img src="https://github.com/RiverrunNetwork/voicelink/blob/master/TellA/img/xiaobanlong.png" width="70" height="70" /> 


 
