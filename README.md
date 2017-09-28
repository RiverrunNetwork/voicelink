# 暴风语音接入平台

> 文档版本:V0.8

暴风语音接入平台支持以下几个功能 <br>

- 界面跳转
- 指令控制
- 中间层展示

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
- 场景数据格式 <br>
```java
{
    "type": "cmd",
    "content": "收藏"
}
```

- 具体对接文档 <br>
https://github.com/RiverrunNetwork/voicelink/blob/master/cmd_control.md <br>

- 目前支持的指令 <br>
https://github.com/RiverrunNetwork/voicelink/blob/master/cmd.md <br>

- 扩充指令 <br>
如果您有扩充的需求发送邮件到renyang@bftv.com <br>

## 中间层展示

- 描述 <br>
用户通过暴风大耳朵 喊 某一个场景类的词语  然后返回数据给 中间层 并且展示<br>

- 语句 <br>
用户 : 我想看刘德华的电影 <br>

- 具体对接文档 <br>
 和指令控制类似 具体见demo <br>
- 中间层指令控制 <br>
 ```java
{
    "type": "middle_cmd",
    "content": "下一页"
}
```

## 测试ASR

- 用过暴风大耳朵 获取用户的Asr <br>

- 联系暴风的小伙伴 获取Debug apk 暴风的小伙伴通过如下方式发送用户的asr 

```java

Intent intent = new Intent("com.bftv.fui.test.speak");
intent.putExtra("userTxt",txt);
getApplication().sendBroadcast(intent);  

```

## 下载暴风大耳朵

- http://pan.baidu.com/s/1eSgsfOu <br>

- 密码私信暴风的小伙伴 <br>

## 第三方应用控制大耳朵

- 广播action com.baofengtv.action.tts <br>
- 目前大耳朵接受广播的代码如下 <br>
```java
action com.baofengtv.action.tts 大耳朵接受广播的action
参数 vocie_close boolean  是否关闭大耳朵  true关闭 反之不作处理
    vocie_tts   string   语音播报的内容
    vocie_is_end boolean 语音播报完是否自动关闭 如果true关闭 反之不关闭
```

## 第三方应用拉起远场

- 广播 action com.baofengtv.action_pull_far <br>

## 开启大耳朵调试开关

-  adb shell am broadcast -a com.baofengtv.action_debug_log --ez debug true <br>

## 开启大耳朵压力测试

－ adb shell am broadcast -a com.baofengtv.action_pressure_test --ei test 0 关闭压力测试 <br>

－ adb shell am broadcast -a com.baofengtv.action_pressure_test --ei test 1 开启压力测试 <br>

－ adb shell am broadcast -a com.baofengtv.action_pressure_test --ei test 2 获取压力测试次数 <br>

－ adb shell am broadcast -a com.baofengtv.action_pressure_test --ei test 3 清空压力测试次数<br>


