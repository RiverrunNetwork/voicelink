# 暴风语音接入平台

> 文档版本:V1.0

- 简介
- 鉴权
- 反控
- 主控

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

## 主控

大耳朵控制第三方应用 并且提供必要数据支持第三方这里叫做主控<br>

- 第三方应用自定义语音动画 并且拿到用户状态<br>
第一步需要在matis里面注册service
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
第二步创建自己的Service
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
            Log.e("Less", "remoteOnShow");
        }

        @Override
        public void showUserText(String s, int i, int i1) throws RemoteException {
            Log.e("Less", "remoteShowUserText");
        }

        @Override
        public void setRecording(int i) throws RemoteException {
            Log.e("Less", "remoteSetRecording");
        }

        @Override
        public void setRecognizing() throws RemoteException {
            Log.e("Less", "remoteSetRecognizing");
        }

        @Override
        public void onShowErrorText(String s) throws RemoteException {
            Log.e("Less", "remoteOnShowErrorText");
        }

        @Override
        public void shortClick() throws RemoteException {
            Log.e("Less", "remoteShortClick");
        }
    };
   }
 ```
第三步 您需要通过反控在用户说话之前把您的package_name 告诉大耳朵 之后就ok了<br>
