
# 应用指令词

- 第一步 需要鉴权 具体步骤参考 “鉴权” 如果不鉴权 将不能和大耳朵进行通信
- 第二步 将你自定义的词组通过如下方式传给大耳朵，比如你做了一款 播放器类型的app,您希望在整个应用中只要用户说 “搜索” 都能跳转到搜索界面
  那么你就将如下代码添加到application里面 例如：
 ```java
            val tell = Tell()
            val hashMap = ConcurrentHashMap<String, String>()
            hashMap.put("搜索", "searchTag")
            tell.appCacheMap = hashMap
            tell.pck = packageName
            tell.tellType = TELL_APP_CACHE
            TellManager.getInstance().tell(App.sApp, tell)
 ```
 其中 “搜索” 会和用户说的话进行精确匹配，如果在“搜索”前面添加“!” 例如"!搜索" 则会进行模糊匹配，
 如果用户说“xx搜索xx” 依然会命中
 如果命中那么 大耳朵会返回给你 "searchTag" 这块你可以写任何值 这就需要小伙伴的发挥了！<br>
 
- 第三步 当大耳朵命中你注册的指令词之后如何回传给你呢？ 这块需要你写一个 应用级别的Server去接收
```java
class TestService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.e("Less", "TestService-onCreate")
    }

    override fun onBind(intent: Intent?): IBinder {
        return stub
    }

    var stub: IUserStatusNotice.Stub = object : IUserStatusNotice.Stub() {
        override fun onRecyclingNotice(recyclingData: RecyclingData?) {
            Log.e("Less", "回收数据通知${recyclingData.toString()}")
        }

        override fun onAsr(asr: String?, age: Int, sex: Int) {
            Log.e("Less", "用户说完话了:"+asr)
        }

        @Throws(RemoteException::class)
        override fun onInterception(interceptionData: InterceptionData) {
            Log.e("Less", "onInterception:" + interceptionData.toString())
            DataChange.getInstance().notifyDataChange(interceptionData)
        }

        @Throws(RemoteException::class)
        override fun onShow(isFar: Boolean) {
            Log.e("Less", "用户开始说话")
        }

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
其中 onInterception 方法返回 InterceptionData 具体解释如下
```java
public class InterceptionData implements Parcelable{

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

    //功能指令类型
    public int functionType;
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

- 第五步
```java
InterceptionData 这个Bean中 的 functionType 是很重要值，当functionType == -1 的时候 
这个时候你需要去needValue 寻找你需要的内容
```

