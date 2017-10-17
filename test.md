# 暴风语音接入平台

> 文档版本:V1.0

- 简介
- 鉴权
- 反控

## 简介

暴风大耳朵是基于暴风电视的一款免遥控的语音软件,任何第三方软件 需要在暴风电视上使用语音功能必须接入暴风语音平台<br>

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

## 反控

大耳朵允许被第三方应用控制 这里简称反控<br>

- 如果您想得到大耳朵的asr信息，并且自己开发语音动画 你需要调用如下方法<br>
```java
TellManager.getInstance().needAsr(Context context, String you_app_pck);
```
