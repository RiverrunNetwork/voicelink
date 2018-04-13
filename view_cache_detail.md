# 界面指令词

- 基本指令词
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
- 指令词追加
默认情况下 新的指令词会把老得指令词覆盖掉
如果isAppend设置为true 则不会进行覆盖
```java
val tell = Tell()
val hashMap = ConcurrentHashMap<String, String>()
hashMap.put("播放", "playTag")
tell.viewCacheMap = hashMap
tell.pck = packageName
tell.isAppend = true
tell.className = this@DemoView.javaClass.name
tell.tellType = TELL_VIEW_CACHE or TELL_TIPS
TellManager.getInstance().tell(App.sApp, tell)
```
- 批量修改指令词
比如注册了两个词分别是 “收藏” “详情” 那么我希望 支持"打开收藏" "打开详情" 又不想再注册两遍<br>


第一步 需要把value 添加“@”符号 只要添加了就代表支持批量修改<br>
```java
val tell = Tell()
val hashMap = ConcurrentHashMap<String, String>()
hashMap.put("收藏", "@collect")
hashMap.put("详情", "@detail")
tell.viewCacheMap = hashMap
tell.pck = packageName
tell.className = this@DemoView.javaClass.name
tell.tellType = TELL_VIEW_CACHE or TELL_TIPS
TellManager.getInstance().tell(App.sApp, tell)
```
第二步 将前缀整理到集合里面 那么就支持 "打开收藏"  "切换收藏" "换到收藏" 详情同理<br>
```java
val cacheData = ArrayList<String>(10)
cacheData.add("打开")
cacheData.add("切换")
cacheData.add("换到")
cacheData.add("进入")
cacheData.add("我要跳到")
cacheData.add("请打开")
cacheData.add("能不能打开")
cacheData.add("给我切换到")
TellManager.getInstance().nlpCache(App.sApp,this@DemoView.javaClass.name,cacheData)
```

