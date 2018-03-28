# 暴风语音接入平台

> 文档版本:V1.4.4

## 简介
暴风大耳朵是基于暴风电视的一款免遥控的语音软件,任何第三方软件如果在暴风电视上使用语音功能,需要接入该平台<br>
## [集成](https://github.com/RiverrunNetwork/voicelink/tree/master/TellA/app/libs)<br>
- voicehelpexpandview...aar 该功能只适用于和暴风深度定制的应用,其他应用可以不集成<br>
## 下载大耳朵 <br>
版本问题请联系 zhangqiwen@bftv.com <br>
## 鉴权
任何第三方应用和大耳朵进行语音交互都需要和大耳朵进行语音鉴权<br>

- 如何鉴权 ？<br>
将如下代码放到应用的AndroidManifest文件中 you_package 是你应用的包的名字<br>

```java
<provider
            android:name="com.bftv.fui.authentication.AuthenticationProvider"
            android:authorities="com.bftv.voice.provider.you_package"
            android:exported="true" />
```
## [指令词](https://github.com/RiverrunNetwork/voicelink/blob/master/Order.md)
指令词目前分为四种类型 分别是[应用指令词] [界面指令词] [提示指令词] [系统指令词]<br>
- 应用指令词
是指当你注册了一遍 在整个项目中都会生效的指令词.
- 界面指令词
是指当你注册一遍 在当前界面下会生效的指令词 该功能可注册大量数据<br>
- 提示指令词
只有和暴风深度合作的应用才会用到该功能,该功能是指 当你注册一次之后 会显示在界面底部bar上面<br>
- 系统指令词
是指一些较为复杂的功能,比如第x个 打开第x个 等较为复杂的指令词,这块大耳朵进行了统一封装 供第三方应用统一调用<br>
## 主动拉起大耳朵
为了省去 喊大耳朵的麻烦操作 第三方可以在合适的场景下 直接启动语音 进行说话<br>
```java
 TellManager.getInstance().farPull(App.sApp,packageName)
```
## 消息控制
任何第三方应用都能向大耳朵发送消息 例如 你可以发送 “刘德华的电影” 那么 大耳朵会对该消息进行 分词-理解－查找数据－展示,当然您只需要传递一个消息就可以了，剩下的工作交给大耳朵就好
```java
TellManager.getInstance().sendAsr(App.sApp,packageName,"下一页")
```
## [获取用户的asr](https://github.com/RiverrunNetwork/voicelink/blob/master/asr.md)
目前为了满足特殊需求的合作伙伴，大耳朵开放了ASR的功能
## 返回
大耳朵目前的返回是模式的遥控器返回建，基本满足大部分需求，但是有一部分应用需求比较特殊，比如 “爱奇艺” 的播放界面，需要连续按两次返回键才能才会
为了满足这种场景，大耳朵开放了“返回”功能，会将back指令发送给用户的app,自己去处理界面的关闭
```java
val tell = Tell()
tell.pck = packageName
tell.className = this@DemoView.javaClass.name
tell.tellType = TELL_SYSTEM
tell.sequencecode = SequenceCode.TYPE_BACK
TellManager.getInstance().tell(App.sApp, tell)
```
## 发送通知
```java
var notice = Notice()
notice.pck = packageName
notice.message = "消息"
notice.title = "标题"
val hashMap = HashMap<String, String>()
hashMap.put("提示词", "tips")
notice.noticeTipsMap = hashMap
TellManager.getInstance().sendNotice(App.sApp, notice)
```
## 问题反馈
- 如果您有任何问题 可以把您的问题写到Issues里面 我们会认真回答每一个人的任何问题<br>
<img src="https://github.com/RiverrunNetwork/voicelink/blob/master/TellA/img/%E6%9A%B4%E9%A3%8E%E8%AF%AD%E9%9F%B3%E6%8E%A5%E5%85%A5%E5%B9%B3%E5%8F%B0%E7%BE%A4%E4%BA%8C%E7%BB%B4%E7%A0%81.png" width="300" height="300" /> 





 
