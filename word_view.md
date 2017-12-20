
# 界面指令词

- 第一步 需要鉴权 具体步骤参考 “鉴权” 如果不鉴权 将不能和大耳朵进行通信
- 第二步 将你自定义的词组通过如下方式传给大耳朵，比如你做了一款 播放器类型的app,您希望在播放器界面只要用户说 “播放” 并且把该词语分发给你，用来控制播放 按钮 那么你就需要在界面的onCreate 中注册指令词<br>
```java
            val tell = Tell()
            val hashMap = HashMap<String,String>()
            hashMap.put("播放","playTag")
            tell.viewCacheMap = hashMap
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_VIEW_CACHE
            TellManager.getInstance().tell(App.sApp, tell)
```
其中 “播放” 会和用户说的话进行模糊匹配 如果用户说“xx播放xx” 依然会命中，如果在“播放”前面添加“!” 例如"!播放" 则会进行精确匹配，
如果命中那么 大耳朵会返回给你 "playTag" 这块你可以写任何值 这就需要小伙伴的发挥了！<br>
- 第三步 那如果我想追加指令词？ 比如我一个界面需要多个地方调用界面指令词
```java
            val tell = Tell()
            val hashMap = HashMap<String,String>()
            hashMap.put("收藏","collectTag")
            tell.viewCacheMap = hashMap
            tell.pck = packageName
            tell.isAppend = true
            tell.tellType = TELL_VIEW_CACHE
            TellManager.getInstance().tell(App.sApp, tell)
```
- 第四步 当大耳朵命中你注册的指令词之后如何回传给你呢？ 这块需要你写一个 应用级别的Server去接收
```java
public class TestService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    IUserStatusNotice.Stub stub = new IUserStatusNotice.Stub() {

        @Override
        public void onInterception(InterceptionData interceptionData) throws RemoteException {
            Log.e("Less", "onInterception:" + interceptionData.toString());
            DataChange.getInstance().notifyDataChange(interceptionData);
        }
}

       <service
            android:name=".TestService"
            android:exported="true"
            android:enabled="true"
            >
            <intent-filter>
                <action android:name="intent.action.user.你应用的包名字" />
            </intent-filter>
        </service>
```
关于 InterceptionData 大家是不是又是一脸蒙蔽 别急
```java
public class InterceptionData implements Parcelable{

    public static final String PAGE = "page";
    public static final String DEFAULT = "default";
    public static final String PRE = "pre";
    public static final String NEXT = "next";
    public static final String LOOK = "look";
    public static final String OPEN = "open";
    public static final String PLAY = "play";
    public static final String BUY = "buy";
    public static final String COLLECT = "collect";
    public static final String CART = "cart";

    //用户说话的年龄
    public int age;

    //用户说话的性别
    public int sex;

    //坐标
    public int index;

    //当前应用的包名
    public String pck;

    //当前界面的className
    public String className;

    //Tell类型
    public int tellType;

    //预留给第三方自定义字段
    public String flag;

    //暂时没想好预留1
    public String temp1;

    //暂时没想好预留2
    public String temp2;

    //这个很重要,是你放到Map里面的value值
    public String needValue;

    //系统指令类型
    public String nlpType;
```

其中 "needValue" 就是 你放到map里面的value <br>

- 第五步 是不是大家还有一个疑问 那就是我在Service 里面获取到了InterceptionData 如何传到 需要的Activity 里面
```java
       @Override
        public void onInterception(InterceptionData interceptionData) throws RemoteException {
            Log.e("Less", "onInterception:" + interceptionData.toString());
            DataChange.getInstance().notifyDataChange(interceptionData);
        }
        
        class DemoView : Activity(), IVoiceObserver {...}
        
        override fun onResume() {
        super.onResume()
        DataChange.getInstance().addObserver(this)
        }

        override fun onPause() {
        super.onPause()
        DataChange.getInstance().deleteObserver(this)
        }
        
        override fun update(interceptionData: InterceptionData?): VoiceFeedback? {
        Handler(Looper.getMainLooper()).post { Toast.makeText(this@DemoView, "接到了:" + interceptionData.toString(),                   Toast.LENGTH_SHORT).show() }
        return null
        }
```

按照上面的步骤写代码  会将InterceptionData 传递到update方法中<br>

- 第五步
```java
InterceptionData 这个Bean中 的 functionType 是很重要值，当functionType == -1 的时候 
那就是**指令词 这个时候你需要去needValue 寻找你需要的内容
```
