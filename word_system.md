
# 系统指令词

- 第一步 需要鉴权 具体步骤参考 “鉴权” 如果不鉴权 将不能和大耳朵进行通信
- 第二步 系统指令词 是指运用大耳朵内部已经成熟的指令词 帮助其他的应用完善功能
pck       当前应用的包名字 <br>
className 当前界面的类名字 <br>
sequence  需要的功能 SequenceCode.TYPE_NUM(支持第x个) SequenceCode.TYPE_PAGE(支持页数) 如果想都支持用“｜”间隔就好 <br>
```java
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_SYSTEM
            tell.sequencecode = SequenceCode.TYPE_PAGE
            TellManager.getInstance().tell(App.sApp, tell)
```
- 第三步 当大耳朵命中你注册的指令词之后如何回传给你呢？ 这块需要你写一个 应用级别的Server去接收
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

- 第四步 是不是大家还有一个疑问 那就是我在Service 里面获取到了InterceptionData 如何传到 需要的Activity 里面
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
