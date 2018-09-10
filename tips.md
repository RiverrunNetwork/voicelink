# 瀑布流中VoiceTip的使用


使用流程：

``` java
// 第一步，获取VoiceTipView
AIVoiceTipView tipsView = findViewById(XXX);
// 第二步，设置本地词
VoiceTips tips = new VoiceTips();
// 此时会显示数据，并将数据发送给大耳朵处理，处理完成后通过IUserStatusNotice的tips方法返回处理结果
tipsView.renderTips(Application, tips);
// 第三步，将处理结果更新到VoiceTipView
tipsView.updateTips(tips);

```

需要引入的资源：<br>
``` java
// 大耳朵头像显示需要
lib.sharedres-release.aar
//VoiceTipView库
lib.voicetip-XXX.aar
authentication-XXX.jar
constantplugin-XXX.jar
thirdtell-XXX.jar
voicethirdparty-XXX.jar
```
