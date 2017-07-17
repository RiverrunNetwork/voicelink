# 指令控制

| 下载aar           | 
| -------------- 
| https://github.com/RiverrunNetwork/voicelink/blob/master/ThirdPartyServer/app/libs/voicethirdparty-release.aar<br> |

## 初始化包
```java
try{
            /**
             * 初始化包
             */
            BindAidlManager.getInstance().init(this,getPackageName(),true);
        }catch (Exception e){
            e.printStackTrace();
        }
```

## 实现观察者模式
```java

extends AppCompatActivity implements IVoiceObserver

@Override
    protected void onResume() {
        super.onResume();
        DataChange.getInstance().addObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DataChange.getInstance().deleteObserver(this);
    }
```

## 继承 RomoteVoiceService
```
public class TaoBaoTvService extends RomoteVoiceService {


    @Override
    public VoiceFeedback send(String userTxt, String nlpJson, IRemoteVoice iRemoteVoice) {
        VoiceFeedback voiceFeedback = DataChange.getInstance().notifyDataChange(nlpJson);
        try {
            iRemoteVoice.sendMessage(voiceFeedback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        SimpleLog.l("send");
        return voiceFeedback;
    }
}
```
## 实现 update
```java
@Override
    public VoiceFeedback update(String s) {
        VoiceFeedback voiceFeedback = new VoiceFeedback();
        voiceFeedback.feedback = "我是Test1";
        voiceFeedback.isHasResult = true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBtnCollect.performClick();
            }
        });

        return voiceFeedback;
    }
```
