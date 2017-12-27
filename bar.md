
# 语音bar

第一步 : [导入bar](https://github.com/RiverrunNetwork/voicelink/tree/master/TellA/app/libs)<br>

第二步 : 引入bar依赖文件
```java
compile 'com.android.support:recyclerview-v7:26.1.0'
```
第三步 : xml中引入
```java
<com.bftv.fui.voicehelpexpandview.AIFuncView
        android:id="@+id/funview"
        android:layout_gravity="bottom"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```
第四步 再界面的onCreate()或者onResume()方法中添加如下代码<br>

- 先解释下onRenderTip

```java
fun tips(){
        val hashMap = LinkedHashMap<String, String>()
        hashMap.put("音乐", "music")
        hashMap.put("第二个",Constant.NO_VALUE)
        funview.okUpdate(hashMap, object : AIFuncViewListener {
            override fun onItemClicked(tip: Tip) {
                Log.e("Less", "onItemClicked" + tip.key)
                TellManager.getInstance().sendAsr(App.sApp, packageName, tip.key)
            }

            override fun onRenderTip(map: HashMap<String, String>, code: Int) {
                Log.e("Less", "onRenderTip" + map.size+"|code:"+code)
                val tell = Tell()
                tell.tipsMap = map
                tell.pck = packageName
                tell.className = this@DemoView.javaClass.name
                tell.tellType = TELL_TIPS
                tell.sequencecode = SequenceCode.TYPE_NUM or code
                tell.tellType = TELL_SYSTEM or TELL_TIPS
                TellManager.getInstance().tell(App.sApp, tell)
            }
        },"appendName")
    }
```
第五步
记得回收资源
```java
override fun onDestroy() {
        super.onDestroy()
        funview.release()
    }
```


