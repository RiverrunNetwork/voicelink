
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

- 先解释下onRenderTip(map: HashMap<String, String>, code: Int)<br>
map  是值得提示词<br>
code 是指的sequencecode <bar>
- 具体描述下整个流程 <br>
该功能不负责和大耳朵通讯，所以会通过onRenderTip方法将需要通讯的数据回调给你，由你来和大耳朵通讯<br>
提示词分为两部分 一种是网络的,一种是本地的,本地的提示词是你给的比如 “音乐” “第二个”,网络提示词是bar自己拉取的<br>
(1)本地提示词<br>
本地提示词是你本地写死的词语分为两种一种是简单的提示词，一种是复杂的提示词<br>
      - 简单提示词<br>
        是指一些没有变化的词语 比如 “音乐” “开心”<br>
      - 复杂提示词<br>
        是指灵活多变的词语 比如“第x个” “xxx的电影”<br>
简单提示词 你需要自己写value,比如 hashMap.put("音乐", "music") 当用户说“音乐” 大耳朵会给你“music”<br>
复杂提示词 你需要写value，比如 hashMap.put("第二个",Constant.NO_VALUE) 
        当用户命中第几个这块的逻辑和 [大耳朵系统指令词](https://github.com/RiverrunNetwork/voicelink/blob/master/word_system.md)一样<br>
        
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


