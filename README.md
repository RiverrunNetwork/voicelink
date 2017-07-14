# 暴风语音接入平台

> 文档版本:V0.1

暴风语音接入平台支持以下几个功能 <br>

- 界面跳转
- 指令控制


## 界面跳转 

语句 : 打开影视库的详情界面 <br>
https://github.com/BFTVVoice/VoiceLink/blob/master/intent.md <br>

全局跳转接入平台7月底上线 <br>

2指令控制<br>
语句 : 播放 (用户在影视库详情界面 喊: 播放) <br>

step1 暴风大耳朵 会通过getTopActivity() 获取当前顶部的packagename <br>

step2 暴风大耳朵会通过 packagename bind 第三方app的service <br>

step3 发送用户说的话 和该用户自定义的Json文件 <br>
```java
private void send(String userTxt, String nlpJson) {
        SimpleLog.l("send-start");
        try {
            mIAsynRomoteVoice.asynMessage(new IRemoteVoice.Stub() {
                @Override
                public void sendMessage(VoiceFeedback voiceFeedback) throws RemoteException {
                    if (mOnBindAidlListener != null) {
                        mOnBindAidlListener.onSuccess(voiceFeedback);
                    }
                }
            }, userTxt, nlpJson);
        } catch (RemoteException e) {
            SimpleLog.l("send-err:" + e.getMessage());
            e.printStackTrace();
        }
    }
```

step4 用户自定义Json文件 前期可以以md的方式写在该项目中,8月中旬上线自定义Json文件平台<br>

step5 这里以 影视库的详情界面 举例子 用户在当前界面先实现 观察者模式 <br>
```java
public class MainActivity extends AppCompatActivity implements IVoiceObserver {
     
    省略部分代码
    @Override
    public VoiceFeedback update(final String s) {
    }
```
step6 String s 是用户自定义的Json文件 用户在update()方法中调用 播放Button就好 <br>

step7 通知update代码如下 <br>
```java
DataChange.getInstance().notifyDataChange(nlpJson); 
```

step8 播放button执行后 执行反馈给暴风大耳朵 代码如下 <br>
```java
private final IAsynRomoteVoice.Stub mBinder = new IAsynRomoteVoice.Stub() {
        @Override
        public void asynMessage(IRemoteVoice callBack, String userTxt, String nlpJson) throws RemoteException {
            SimpleLog.l("mBinder-sendMessage-userTxt:"+userTxt+"|nlpJson:"+nlpJson);
            send(userTxt,nlpJson,callBack);
        }
    };

callBack.sendMessage(new VoiceFeedback());
```

3 无侵入性指令控制 7月底出方案 <br>

4 第三方app控制 大耳朵中间层显示7月底出方案 <br>


aar Download <br>
https://github.com/RiverrunNetwork/voicelink/blob/master/ThirdPartyServer/app/libs/voicethirdparty-release.aar



