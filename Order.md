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
界面指令词
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

