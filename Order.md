# 指令词
是为了解决 在特定的场景下 当用户说了一些简单的指令 比如 在播放器界面下 说 “播放” “暂停” 当你注册了指令词 大耳朵就会将用户的意图分发给你 你来执行具体的操作 <br>

- 第一步 需要鉴权 具体步骤参考 [鉴权] 如果不鉴权 将不能和大耳朵进行通信

- 第二步 指令词的注册


应用指令词
```java
val tell = Tell()
val hashMap = ConcurrentHashMap<String, String>()
hashMap.put("搜索", "searchTag")
tell.appCacheMap = hashMap
tell.pck = packageName
tell.tellType = TELL_APP_CACHE
TellManager.getInstance().tell(App.sApp, tell)
```
[界面指令词](https://github.com/RiverrunNetwork/voicelink/blob/master/view_cache_detail.md)
```java
val tell = Tell()
val hashMap = ConcurrentHashMap<String, String>()
hashMap.put("播放", "playTag")
tell.viewCacheMap = hashMap
tell.pck = packageName
tell.className = this@DemoView.javaClass.name
tell.tellType = TELL_VIEW_CACHE or TELL_TIPS
TellManager.getInstance().tell(App.sApp, tell)
```
提示指令词
```java
val tell = Tell()
tell.tipsMap = map
tell.pck = packageName
tell.className = this@DemoView.javaClass.name
tell.tellType = TELL_TIPS
TellManager.getInstance().tell(App.sApp, tell)
```
系统指令词 <br>
SequenceCode.TYPE_PAGE 代表 支持页数指令 比如 下一页 上一页 等<br>
SequenceCode.TYPE_NUM 代表支持 第x个指令 比如 第10个 第100个 <br>
```java
val tell = Tell()
tell.pck = packageName
tell.className = this@DemoView.javaClass.name
tell.tellType = TELL_SYSTEM
tell.sequencecode = SequenceCode.TYPE_PAGE or SequenceCode.TYPE_NUM
TellManager.getInstance().tell(App.sApp, tell)
```
还可以自定义系统指令词<br>
InterceptorNet("删除第.*.*",12345423) 前半部分是正则表达式 后半部分是你自己定义的类型<br>
```java
val data = ArrayList<InterceptorNet>()
data.add(InterceptorNet("删除第.*.*",12345423))
TellManager.getInstance().nlpSystem(App.sApp,this@DemoView.javaClass.name,data)
```
混合使用<br>
我们的指令词 支持各种混合使用
```java
val tell = Tell()
val appMap = ConcurrentHashMap<String, String>()
appMap.put("搜索", "searchTag")
tell.appCacheMap = appMap
val viewMap = ConcurrentHashMap<String, String>()
viewMap.put("收藏", "collectTag")
tell.viewCacheMap = viewMap
tell.pck = packageName
tell.sequencecode = SequenceCode.TYPE_PAGE or SequenceCode.TYPE_NUM
tell.tellType = TELL_APP_CACHE or TELL_SYSTEM or TELL_VIEW_CACHE or TELL_TIPS
TellManager.getInstance().tell(App.sApp, tell)
```

- 第三步 指令词的接收

首先需要自己写一个应用级别得service 下面以TestService举例子 其中intent.action.user. 为固定格式 com.bftv.tell.a 为你应用得包名字
```java
<service
android:name=".TestService"
android:exported="true"
android:enabled="true"
>
<intent-filter>
      <action android:name="intent.action.user.com.bftv.tell.a" />
</intent-filter>
</service>
```
接下来看下TestService
```java
class TestService : Service() {

    companion object {
        val TAG = "TestService"
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "TestService-onCreate")
    }

    override fun onBind(intent: Intent?): IBinder {
        return stub
    }

    var stub: IUserStatusNotice.Stub = object : IUserStatusNotice.Stub() {
        override fun onRecyclingNotice(recyclingData: RecyclingData?) {
            Log.e(TAG, "回收数据通知${recyclingData.toString()}")
        }

        override fun onAsr(asr: String?, age: Int, sex: Int) {
            Log.e(TAG, "用户说完话了:"+asr)
            Handler(Looper.getMainLooper()).post(Runnable {
                Toast.makeText(App.sApp,"用户说完话了$asr",Toast.LENGTH_SHORT).show()
            })
        }

        @Throws(RemoteException::class)
        override fun onInterception(interceptionData: InterceptionData) {
            Log.e(TAG, "onInterception:" + interceptionData.toString())
            if(interceptionData.tellType.equals(TellCode.TELL_APP_CACHE)){
                Log.e(TAG,"应用指令词")
                if(interceptionData.equals("searchTag")){
                    Log.e(TAG,"命中搜索")
                }
            }else if(interceptionData.tellType.equals(TellCode.TELL_VIEW_CACHE)){
                Log.e(TAG,"界面指令词")
                if(interceptionData.equals("playTag")){
                    Log.e(TAG,"命中界面")
                }
            }else if(interceptionData.tellType.equals(TellCode.TELL_TIPS)){
                Log.e(TAG,"提示指令词")
            }else if(interceptionData.tellType.equals(TellCode.TELL_SYSTEM)){
                Log.e(TAG,"系统指令词")
                if(interceptionData.functionType == FunctionCode.NEXT){
                    Log.e(TAG,"命中下一页")
                }else if(interceptionData.functionType == FunctionCode.PLAY){
                    Log.e(TAG,"命中播放第${interceptionData.index}")
                }
            }
            DataChange.getInstance().notifyDataChange(interceptionData)
        }

        @Throws(RemoteException::class)
        override fun onShow(isFar: Boolean) {
            Log.e(TAG, "用户开始说话")
            Handler(Looper.getMainLooper()).post(Runnable {
                Toast.makeText(App.sApp,"onShow",Toast.LENGTH_SHORT).show()
            })
        }

    }
}
```
我们会将用户得意图传给你自己定义的Service 然后你可以通过 例如DataChange
```java
DataChange.getInstance().notifyDataChange(interceptionData)
```
或者EventBus 等 方式将service的内容 传给当前展示的界面上

