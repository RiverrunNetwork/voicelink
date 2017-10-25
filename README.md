# 暴风语音接入平台

> 文档版本:V1.0

- 简介
- 集成
- 鉴权
- 自定义语音界面
- 特定指令词
- 功能指令词
- 主动拉起大耳朵
- 界面跳转
- 系统指令词
- 快速指令词
- 大耳朵应用和资源文件下载
- 问题反馈
- 合作伙伴

## 简介

暴风大耳朵是基于暴风电视的一款免遥控的语音软件,任何第三方软件 需要在暴风电视上使用语音功能必须接入暴风语音平台<br>


## 集成

目前大耳朵的sdk 采用的的jar包的形式 主要事为了兼容eclipse的用户 将如下jar一次集成到项目中<br>
链接 : https://github.com/RiverrunNetwork/voicelink/tree/master/TellA/app/libs<br>

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
- 第一步 需要鉴权 具体步骤参考 “鉴权” 如果不鉴权 将不能和大耳朵进行通信

- 第二步 就是当用户进入特定的(需要自定义语音动画的界面)界面 通知大耳朵 调用如下代码
```java
TellManager.getInstance().needAsr(Context context, String you_app_pck);
```
- 第三步 需要您注册一个service 用于接收当前用户的状态 代码如下
```java
   <service
            android:name="TestService"
            android:exported="true"
            android:enabled="true"
            >
            <intent-filter>
                <action android:name="intent.action.user.you_package" />
            </intent-filter>
        </service>
 ```
 ```java
   public class TestService extends Service {

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
        public void showUserText(final String userTxt, int age, int sex) throws RemoteException {
            Log.e("Less", "用户说完话了 age-用户的年龄 sex－用户的性别"+userTxt);

        }

        @Override
        public void setRecording(int vol) throws RemoteException {
            Log.e("Less", "用户说话的声音大小"+vol);
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

        @Override
        public void onInterception(final String nlpJson, final String flag, String pck, int age, int sex, int index) throws RemoteException {
            Log.e("Less", "拦截处理=nlpJson第三方自定义的value值｜flag第三方自定义的标签|pck包名字|age用户说话的年龄|sex用户说话的性别|index第几个");
            DataChange.getInstance().notifyDataChange(nlpJson+"|+"+flag);
        }
    };
}
 ```
 - 第四步 退出界面 或者不用了一定要调用如下方法 否则会导致大耳朵异常
 ```java
 TellManager.getInstance().clearAsr(Context context, String pck)
 ```


## 特定指令词
任何一个应用都可以向大耳朵注册特定的指令词语 比如微信 向大耳朵注册指令词 “聊天” 那么当用户命中“聊天”这个词语那么我们就将当前用户的指令词 分发给微信
,注意该功能只是临时性的,当有新的指令词注册进来 之前的就会失效.<br>

- 第一步 需要鉴权 具体步骤参考 “鉴权” 如果不鉴权 将不能和大耳朵进行通信
- 第二步 将你自定义的词组通过如下方式传给大耳朵
 ```java
TellManager.getInstance().send(Context context, Tell tell);

Tell tell = new Tell();
HashMap<String,String> hashMap = new HashMap<String, String>();
hashMap.put("大耳朵会通过用户的话匹配这个map的key","匹配成功后会返回给你这个map的value");
tell.mTellMaps = hashMap;
tell.pck = "你项目的包名字";
TellManager.getInstance().tell(MainActivity.this,tell);
 ```
- 第三步 注册service 步骤和 “自定义语音界面 第二步” 步骤相同 当用户命中我们会回调onInterception(...) 方法

## 功能指令词
任何一个应用都可以向大耳朵注册功能指令词 功能指令词一般用来控制按钮点击 比如语音控制某一个button点击,这里举一个例子 比如某一个播放器的详情界面 他有一个收藏按钮 当用户在这个详情界面之上 并且喊 “收藏” 或者其他相关指令词 大耳朵就会将该指令词传给当前的app用来告诉当前app执行收藏按钮的点击操作<br>

- 第一步 需要鉴权 具体步骤参考 “鉴权” 如果不鉴权 将不能和大耳朵进行通信
- 第二步 在当前界面Activity onCreate() 的时候向大耳朵注册功能指令词
```java
Tell tell = new Tell();
tell.flag = "btn_function";
HashMap<String, String> hashMap = new HashMap<String, String>();
hashMap.put("你好", "播放功能");
tell.mTellMaps = hashMap;
tell.className = "com.bftv.tell.a.MainActivity";
tell.pck = MainActivity.this.getPackageName();
TellManager.getInstance().addFunctionTell(App.sApp, tell);
```
- 第三步 在界面退出 onDestroy() 方法调用清空操作

```java
TellManager.getInstance().removeFunctionTell()
```
- 第四步 注册service 步骤和 “自定义语音界面 第二步” 步骤相同 当用户命中我们会回调onInterception(...) 方法

- 第五步 到了第四步已经能将大耳朵的命令传送到了service了，但是如何从service将命令给到当前的activity呢？这里大耳朵提供一套解决方案提供给第三方app<br>
在当前的界面实现接口<br>
```java
MainActivity extends AppCompatActivity implements IVoiceObserver
```
在onCreate注册监听<br>
```java
DataChange.getInstance().addObserver(this);
```
在onDestroy()移除监听<br>
```java
DataChange.getInstance().deleteObserver(this);
```
最后在service上添加如下代码<br>
```java
@Override
public void onInterception(final String nlpJson, final String flag, String pck, int age, int sex, int index) throws RemoteException {
       Log.e("Less", "拦截处理=nlpJson第三方自定义的value值｜flag第三方自定义的标签|pck包名字|age用户说话的年龄|sex用户说话的性别|index第几个");
DataChange.getInstance().notifyDataChange(nlpJson+"|+"+flag);
}
```
在当前界面就能收到消息了<br>

## 主动拉起大耳朵
为了省去 喊暴风大耳朵的麻烦操作 第三方可以在合适的场景下 直接启动语音 进行说话<br>
```java
 TellManager.getInstance().farPull(Context context, you_app_pck);
```

## 界面跳转
大耳朵能跳转任何Acitivty 启动任何广播 启动任何广播 发送特定的系统事件，这些都功能都是对第三方开放的，第三方的app需要按照对应的规则配置到暴风大耳朵审核后台，审核通过后即可上线<br>
- 界面跳转配置规则
https://github.com/RiverrunNetwork/voicelink/blob/master/intent.md
- 审核后台
敬请期待(服务端的小伙伴在加紧开发)

## 系统指令词
任何第三方都可以使用大耳朵已经成熟的指令词模块 比如第x集 快进3分 ...<br>
- 需要调用needSystemFunction的方法来通知大耳朵当前界面支持的功能 <br>
needSystemFunction(Application context, String pck, String className, int sequence)<br>
pck       当前应用的包名字 <br>
className 当前界面的类名字 <br>
sequence  需要的功能 SequenceCode.TYPE_NUM(支持第x个) SequenceCode.TYPE_PAGE(支持页数) 如果想都支持用“｜”间隔就好 <br>
例子如下<br>
```java
TellManager.getInstance().needSystemFunction(App.sApp,"com.bftv.tell.a","com.bftv.tell.a.MainActivity", SequenceCode.TYPE_PAGE);
```

## 快速指令词
大耳朵强大的能力在这里又一次展现，在这里第三方app无需改动任何代码 就能和大耳朵配合 第三方需要把某一个按钮的相应的信息配置在大耳朵审核后台，审核通过后，
大耳朵就能控制该按钮了，别问我怎么做的，只管欣赏大耳朵的魔力就好
- 敬请期待

## 大耳朵应用和资源文件下载
- 链接:http://pan.baidu.com/s/1b5nKQU  密码需要联系 yulingyan@bftv.com

## 问题反馈
- 如果您有任何问题 可以把您的问题写到Issues里面 我们会认真回答每一个人的任何问题

## 合作伙伴
<img src="http://live-fengmi.b0.upaiyun.com/imgconfig/ai/taobao.png" width="70" height="70" /> 


 
