
# 语音bar

第一步 : [导入bar](https://github.com/RiverrunNetwork/voicelink/blob/master/TellA/app/libs/voicehelpexpandview-release.aar)<br>

第二步 : 引入bar依赖文件
```java
compile 'com.android.support:recyclerview-v7:26.1.0'
```

第三步 再界面的onCreate方法中添加如下代码
```java
fun tips(){
        val hashMap = HashMap<String, String>()
        hashMap.put("音乐", "music")
        funview.okUpdate(hashMap, object : AIFuncViewListener {
            override fun onItemClicked(position: Int, tip: Tip) {
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
                if (code != 0) {
                    tell.sequencecode = code
                    tell.tellType = TELL_SYSTEM or TELL_TIPS
                }
                TellManager.getInstance().tell(App.sApp, tell)
            }
        })
    }
```


