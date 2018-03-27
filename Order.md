# 指令词
是为了解决 在特定的场景下 当用户说了一些简单的指令 比如 在播放器界面下 说 “播放” “暂停” 当你注册了指令词 大耳朵就会将用户的意图分发给你 你来执行具体的操作 <br>

- 第一步 需要鉴权 具体步骤参考 [鉴权] 如果不鉴权 将不能和大耳朵进行通信

- 第二步 指令词的注册
(1)应用指令词
```java
val tell = Tell()
val hashMap = ConcurrentHashMap<String, String>()
hashMap.put("搜索", "searchTag")
tell.appCacheMap = hashMap
tell.pck = packageName
tell.tellType = TELL_APP_CACHE
TellManager.getInstance().tell(App.sApp, tell)
```
(2)界面指令词
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
(2)提示指令词
```java
val tell = Tell()
tell.tipsMap = map
tell.pck = packageName
tell.className = this@DemoView.javaClass.name
tell.tellType = TELL_TIPS
tell.sequencecode = code
tell.isAppend = true
tell.tellType = TELL_SYSTEM or TELL_TIPS
TellManager.getInstance().tell(App.sApp, tell)
```

                
