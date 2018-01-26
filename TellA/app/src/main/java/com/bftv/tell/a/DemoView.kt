package com.bftv.tell.a
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bftv.fui.constantplugin.Constant
import com.bftv.fui.constantplugin.FunctionCode
import com.bftv.fui.constantplugin.SequenceCode
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

/**
 * Author by Less on 17/11/15.
 */
class DemoView : Activity(), IVoiceObserver {
    override fun update(interceptionData: InterceptionData?): VoiceFeedback? {
        Handler(Looper.getMainLooper()).post { Toast.makeText(this@DemoView, "接到了:+A", Toast.LENGTH_SHORT).show() }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_layout)
        //应用指令词
        btn_app.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            val hashMap = ConcurrentHashMap<String, String>()
            hashMap.put("搜索", "searchTag")
            tell.appCacheMap = hashMap
            tell.pck = packageName
            tell.tellType = TELL_APP_CACHE
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //应用指令模糊效果
        btn_app_blurry.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            val hashMap = HashMap<String, String>()
            hashMap.put("!谢谢你", "searchTag")
            tell.setHashMapAppCacheMap(hashMap)
            tell.pck = packageName
            tell.tellType = TELL_APP_CACHE
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //界面指令词
        btn_view.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            val hashMap = ConcurrentHashMap<String, String>()
            hashMap.put("播放", "playTag")
            tell.viewCacheMap = hashMap
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_VIEW_CACHE
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //追加界面指令词
        btn_view_append.setOnClickListener(View.OnClickListener {
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
        btn_view_recycler.setOnClickListener(View.OnClickListener {
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

        //界面删除功能
        btn_view_delete.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            val hashMap = ConcurrentHashMap<String, String>()
            hashMap.put("百度", "delete_baidu")
            tell.viewCacheMap = hashMap
            tell.pck = packageName
            tell.isNeedViewCacheRecyclingNotice = true
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_VIEW_CACHE
            tell.functionSupportType = FunctionCode.DELETE
            TellManager.getInstance().tell(App.sApp, tell)
        })
        //系统指令词
        btn_system_app.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_SYSTEM
            tell.sequencecode = SequenceCode.TYPE_PAGE or SequenceCode.TYPE_NUM
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //拉起语音
        btn_pull_far.setOnClickListener(View.OnClickListener {
            TellManager.getInstance().farPull(App.sApp, packageName)
        })

        //发送ASR
        btn_send_asr.setOnClickListener(View.OnClickListener {
            TellManager.getInstance().sendAsr(App.sApp, packageName, "下一页")
        })

        //发送通知
        btn_send_notice.setOnClickListener(View.OnClickListener {
            var notice = Notice()
            notice.pck = packageName
            notice.message = "消息"
            notice.title = "标题"
            val hashMap = HashMap<String, String>()
            hashMap.put("提示词", "tips")
            notice.noticeTipsMap = hashMap
            TellManager.getInstance().sendNotice(App.sApp, notice)
        })

        //自己处理返回
        btn_back.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_SYSTEM
            tell.sequencecode = SequenceCode.TYPE_BACK
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //接管语音的ASR
        btn_asr.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_ASR
            tell.isNeedPinYin = true
            TellManager.getInstance().tell(App.sApp, tell)
        })

        tips1.setOnClickListener(View.OnClickListener {
            var map1 = LinkedHashMap<String,String>()
            map1.put("你好","test1")
            tips("testa",map1)
        })

        tips2.setOnClickListener(View.OnClickListener {
            var map2 = LinkedHashMap<String,String>()
            map2.put("哈哈","test2")
            tips("testb",map2)
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

    fun tips(appendName : String,map : LinkedHashMap<String, String>) {
        funview.okUpdate(map, "you platfrom",appendName,0,true, object : AIFuncViewListener {
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

