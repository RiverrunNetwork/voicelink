package com.bftv.tell.a
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bftv.fui.constantplugin.FunctionCode
import com.bftv.fui.constantplugin.SequenceCode
import com.bftv.fui.constantplugin.Switch
import com.bftv.fui.constantplugin.TellCode.*
import com.bftv.fui.tell.Notice
import com.bftv.fui.tell.Tell
import com.bftv.fui.tell.TellManager
import com.bftv.fui.thirdparty.InterceptionData
import com.bftv.fui.thirdparty.VoiceFeedback
import com.bftv.fui.thirdparty.notify.DataChange
import com.bftv.fui.thirdparty.notify.IVoiceObserver
import com.bftv.fui.voicehelpexpandview.AIFuncViewListener
import com.bftv.fui.voicehelpexpandview.util.Tip
import kotlinx.android.synthetic.main.demo_layout.*
import java.util.concurrent.ConcurrentHashMap
import com.bftv.fui.constantplugin.TellCode.TELL_ASR
import com.bftv.fui.constantplugin.bean.InterceptorNet
import com.bftv.fui.tell.TTS
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.LinkedBlockingQueue


/**
 * Author by Less on 17/11/15.
 */
class DemoView : Activity(), IVoiceObserver {
    override fun update(interceptionData: InterceptionData?): VoiceFeedback? {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this@DemoView, "接到了" + interceptionData.toString(), Toast.LENGTH_LONG).show()
        }
        return null
    }

    override fun  onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_layout)
        //应用指令词
        btn_app.setOnClickListener({
            val tell = Tell()
            val hashMap = ConcurrentHashMap<String, String>()
            hashMap.put("搜索", "searchTag")
            tell.appCacheMap = hashMap
            tell.pck = packageName
            tell.tellType = TELL_APP_CACHE
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //应用指令模糊效果
        btn_app_blurry.setOnClickListener({
            val tell = Tell()
            val hashMap = HashMap<String, String>()
            hashMap.put("取消订单", "searchTag")
            tell.setHashMapAppCacheMap(hashMap)
            tell.pck = packageName
            tell.tellType = TELL_APP_CACHE
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //界面指令词
        btn_view.setOnClickListener({
            val tell = Tell()
            val hashMap = ConcurrentHashMap<String, String>()
            hashMap.put("播放", "playTag")
            hashMap.put("收藏", "@collectTag")
            tell.viewCacheMap = hashMap
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_VIEW_CACHE or TELL_TIPS
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //追加界面指令词
        btn_view_append.setOnClickListener({
            val tell = Tell()
            val hashMap = ConcurrentHashMap<String, String>()
            hashMap.put("收藏", "collectTag")
            tell.viewCacheMap = hashMap
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.isAppend = true
            tell.tellType = TELL_VIEW_CACHE
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //界面回收通知
        btn_view_recycler.setOnClickListener({
            val tell = Tell()
            val hashMap = ConcurrentHashMap<String, String>()
            hashMap.put("呵呵", "recycler")
            tell.viewCacheMap = hashMap
            tell.pck = packageName
            tell.isNeedViewCacheRecyclingNotice = true
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_VIEW_CACHE
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //界面NLP 目前支持 删除** 打开** 关闭**
        btn_view_nlp.setOnClickListener({
            val tell = Tell()
            val hashMap = ConcurrentHashMap<String, String>()
            hashMap.put("音乐", "music")
            tell.viewCacheMap = hashMap
            tell.pck = packageName
            tell.isNeedViewCacheRecyclingNotice = true
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_VIEW_CACHE
            tell.functionSupportType = FunctionCode.DELETE or FunctionCode.CLOSE or FunctionCode.OPEN
            TellManager.getInstance().tell(App.sApp, tell)
        })
        //系统指令词
        btn_system_app.setOnClickListener({
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_SYSTEM
            tell.sequencecode = SequenceCode.TYPE_PAGE or SequenceCode.TYPE_NUM
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //多种指令词
        btn_order.setOnClickListener({
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
        })

        //拉起语音
        btn_pull_far.setOnClickListener({
            TellManager.getInstance().farPull(App.sApp, packageName)
        })

        //发送ASR
        btn_send_asr.setOnClickListener({
            TellManager.getInstance().sendAsr(App.sApp, packageName, "下一页")
        })

        //发送通知
        btn_send_notice.setOnClickListener( {
            val notice = Notice()
            notice.img = "xxx"
            notice.pck = packageName
            notice.message = "消息"
            notice.title = "标题"
            val hashMap = HashMap<String, String>()
            hashMap.put("提示词", "tips")
            notice.noticeTipsMap = hashMap
            TellManager.getInstance().sendNotice(App.sApp, notice)
        })

        //自己处理返回
        btn_back.setOnClickListener({
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_SYSTEM
            tell.sequencecode = SequenceCode.TYPE_BACK
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //接管语音的ASR
        btn_asr.setOnClickListener({
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_ASR
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //接管语音的ASR,并且不显示语音动画
        btn_asr_hide_animation.setOnClickListener({
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_ASR
            tell.isHideAnimation = true
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //接管语音的ASR,并且不显示语音动画,并且使用游戏引擎
        btn_asr_hide_animation_ddz_game.setOnClickListener({
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_ASR
            tell.isHideAnimation = true
            tell.isNeedDDZGameConversion = true
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //接管语音的ASR,并且不显示语音动画,并且使用游戏引擎,并且开启连续识别
        btn_asr_hide_animation_ddz_game_continuous_recognition.setOnClickListener({
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_ASR
            tell.isHideAnimation = true
            tell.isNeedDDZGameConversion = true
            tell.isEnableContinuousRecognition = true
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //关闭ASR
        btn_asr_close.setOnClickListener(View.OnClickListener {
            TellManager.getInstance().clearAsr(App.sApp, packageName, this@DemoView.javaClass.name)
        })

        //隐藏语音动画和主动拉起大耳朵组合测试
        btn_hide_and_far.setOnClickListener(View.OnClickListener {
            TellManager.getInstance().farPull(App.sApp, packageName)
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_ASR
            tell.isHideAnimation = true
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //利用AIUI技术优化ASR结果,启动该技术会进行大量的计算,会影响返回速度。
        btn_aiui_better_asr.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            val hashMap = ConcurrentHashMap<String, String>()
            hashMap.put("刘德华是谁", "^yes")
            hashMap.put("测试", "test")
            tell.viewCacheMap = hashMap
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_VIEW_CACHE
            tell.isEnableBetterAsr = true
            TellManager.getInstance().tell(App.sApp, tell)
        })

        /**
         * 给AIUI进行打分 默认是5分 分数越低模糊度越低
         * 比如 如果你的分数是0 那么几乎不会改变任何ASR结果
         * 如果你的分数是10 那么只要是相似的结果都会发生变化
         * 当前只更改ClassName 对应的结果
         */
        btn_aiui_score.setOnClickListener(View.OnClickListener {
            TellManager.getInstance().addScore(App.sApp,packageName,this@DemoView.javaClass.name,5)
        })

        //启动Root权限
        btn_start_root.setOnClickListener(View.OnClickListener {
            TellManager.getInstance().
                    enableRoot(App.sApp, this@DemoView.javaClass.name, "10987654321")
        })

        //关闭Root权限
        btn_start_close_root.setOnClickListener(View.OnClickListener {
            TellManager.getInstance().clearRootAuthority(App.sApp)
        })


        //和大耳朵断开连接
        btn_interrupted.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@DemoView.javaClass.name)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            App.sApp!!.startActivity(intent)
            android.os.Process.killProcess(android.os.Process.myPid())
        })

        //关闭大耳朵语音
        btn_close_voice.setOnClickListener(View.OnClickListener {
            TellManager.getInstance().closeVoice(App.sApp)
        })

        //大耳朵语音播报
        btn_tts.setOnClickListener(View.OnClickListener {
            val tts = TTS()
            tts.pck = packageName
            tts.tts = "哈哈 我能控制语音播报啦 好开心!"
            TellManager.getInstance().tts(App.sApp, tts)
        })

        //批量映射指令词
        btn_nlp_cache.setOnClickListener({
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
        })

        //自定义系统指令词
        btn_nlp_system.setOnClickListener({
            val data = ArrayList<InterceptorNet>()
            data.add(InterceptorNet("删除第.*.*",12345423))
            TellManager.getInstance().nlpSystem(App.sApp,this@DemoView.javaClass.name,data)
        })

        //不使用SDK
        //调用如下方法之后所有的功能都是空方法,也就是说 语音功能无效 好处是不会损耗程序性能问题
        //主要是为了解决部分开发者 集成语音SDK,但不使用语音功能的问题
        btn_close.setOnClickListener({
            Switch.setUseSdk(false)
        })
        tips1.setOnClickListener({
            val map1 = LinkedHashMap<String, String>()
            map1.put("你好", "test1")
            tips("testa", map1)
        })

        tips2.setOnClickListener({
            val map2 = LinkedHashMap<String, String>()
            map2.put("哈哈", "test2")
            tips("testb", map2)
        })
    }

    override fun onResume() {
        super.onResume()
        DataChange.getInstance().addObserver(this)
    }

    override fun onPause() {
        super.onPause()
        DataChange.getInstance().deleteObserver(this)
    }

    fun tips(appendName: String, map: LinkedHashMap<String, String>) {
        funview.okUpdate(map, "you platfrom", appendName, 0, true, object : AIFuncViewListener {
            override fun onItemClicked(tip: Tip) {
                Log.e("Less", "onItemClicked" + tip.key)
                TellManager.getInstance().sendAsr(App.sApp, packageName, tip.key)
            }

            override fun onRenderTip(map: ConcurrentHashMap<String, String>, code: Int) {
                Log.e("Less", "onRenderTip" + map.size + "|code:" + code)
                val tell = Tell()
                tell.tipsMap = map
                tell.pck = packageName
                tell.className = this@DemoView.javaClass.name
                tell.tellType = TELL_TIPS
                tell.sequencecode = code
                tell.isAppend = true
                tell.tellType = TELL_SYSTEM or TELL_TIPS
                TellManager.getInstance().tell(App.sApp, tell)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        funview.release()
    }
}

