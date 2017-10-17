# 暴风语音接入平台

> 文档版本:V1.0

- 简介
- 鉴权
- 自定义语音界面

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

## 自定义语音界面

在特殊的场景下 大耳朵的语音界面会把当前界面挡住 或者当前的应用想自定义自己的界面 如果有这种需求那么很高兴的告诉你 大耳朵是支持的<br>
- 第一步 就是当用户进入特定的(需要自定义语音动画的界面)界面 通知大耳朵 调用如下代码
```java
TellManager.getInstance().needAsr(Context context, String you_app_pck);
```
- 第二步 需要您注册一个service 用于接收当前用户的状态 代码如下
```java
   <service
            android:name="xxx"
            android:exported="true"
            android:enabled="true"
            >
            <intent-filter>
                <action android:name="intent.action.user.you_package" />
            </intent-filter>
        </service>
 ```
 ```java
   public class xxx extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    IUserStatusNotice.Stub stub = new IUserStatusNotice.Stub() {
        
        @Override
        public void onShow(boolean b) throws RemoteException {
            Log.e("Less", "用户开始说话");
        }

        @Override
        public void showUserText(String userTxt, int age, int sex) throws RemoteException {
            Log.e("Less", "用户说完话了 age-用户的年龄 sex－用户的性别");
        }

        @Override
        public void setRecording(int vol) throws RemoteException {
            Log.e("Less", "用户说话的声音大小");
        }

        @Override
        public void setRecognizing() throws RemoteException {
            Log.e("Less", "用户说完话了 开始语音转文字 需要时间");
        }

        @Override
        public void onShowErrorText(String error) throws RemoteException {
            Log.e("Less", "发生错误了");
        }

        @Override
        public void shortClick() throws RemoteException {
            Log.e("Less", "用户按的非常快");
        }
    };
   }
 ```
