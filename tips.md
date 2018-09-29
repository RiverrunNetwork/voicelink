# 瀑布流中VoiceTip的使用


使用流程：

<img src="TellA/img/img.jpg"/>

``` java
// 第一步，获取VoiceTipView
AIVoiceTipView tipsView = findViewById(XXX);
// 第二步，设置本地词
VoiceTips tips = new VoiceTips();
tips.pck = "ok.less.org.okl"
tips.className = "com.bftv.fui.children.view.video.VideoListActivity"
tips.classNameAppend = "append"
tips.version = "4.0"
tips.refreshTime = 20000 // 默认10秒

tips.tipsCacheMap = ConcurrentHashMap(4);
tips.tipsCacheMap.put("推荐点好看的视频"], "");
tips.tipsCacheMap.put("今天天气怎么样"], "");
tips.tipsCacheMap.put("给我来点音乐"], "");

// 注册tips渲染回调(将实际显示的词回调回来)
tipsView.setTipsListener(listener);
// 此时会显示数据，并将数据发送给大耳朵处理，处理完成后通过IUserStatusNotice的tips方法返回处理结果
tipsView.renderTips(Application, tips);
// 第三步，将tips方法回调的处理结果更新到VoiceTipView，注意是IUserStatusNotice的tips方法返回的处理结果
TellManagerHelper.getInstance().notifyTipsChange(tips);

// ###### 以下方法需要手动调用 ###### //

// 当VoiceTips界面不可见时需要暂停动画
tipsView.pauseAnim();
// 重新可见时恢复动画
tipsView.resumeAnim();
// 界面销毁时释放
tipsView.release();

// ###### ################ ###### //

```

需要引入的资源：<br>
``` java
// 大耳朵头像显示需要
lib.sharedres-release.aar
//VoiceTipView库
voice-lib.voicetip-XXX.aar
authentication-XXX.jar
constantplugin-XXX.jar
thirdtell-XXX.jar
voicethirdparty-XXX.jar
```
