# 暴风语音接入平台

> 文档版本:V0.3

暴风语音接入平台支持以下几个功能 <br>

- 界面跳转
- 指令控制


## 界面跳转 

- 描述 <br>
用户通过暴风大耳朵 打开某个界面 这里 叫做 "界面跳转" 下面以 影视库详情界面举例

- 语句 <br>
用户 : 打开影视库的详情界面

- 具体对接文档 <br>
https://github.com/RiverrunNetwork/voicelink/blob/master/intent.md <br>

## 指令控制

- 描述 <br>
用户通过暴风大耳朵 在某个界面下控制 某一个按钮 下面以 影视库详情界面 播放按钮举例 <br>

- 语句 <br>
用户 : 播放 (当前用户已经处在 影视库 详情界面下)

- 具体对接文档 <br>
https://github.com/RiverrunNetwork/voicelink/blob/master/cmd_control.md <br>

## 大耳朵中间层数据控制

- 描述 <br>
用户通过暴风大耳朵 喊 某一个场景类的词语  然后返回数据给 中间层 并且展示<br>

- 语句 <br>
用户 : 我想看刘德华的电影 <br>

- 具体对接文档 <br>

和 指令控制类似 具体见demo <br>

## 测试ASR

- 用过暴风大耳朵 获取用户的Asr <br>

- 联系暴风的小伙伴 获取Debug apk 暴风的小伙伴通过如下方式发送用户的asr 

```java

Intent intent = new Intent("com.bftv.fui.test.speak");
intent.putExtra("userTxt",txt);
getApplication().sendBroadcast(intent);  

```




