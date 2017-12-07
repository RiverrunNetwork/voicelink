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
import com.bftv.fui.tell.Tell
import com.bftv.fui.tell.TellManager
import com.bftv.fui.thirdparty.InterceptionData
import com.bftv.fui.thirdparty.VoiceFeedback
import com.bftv.fui.thirdparty.notify.DataChange
import com.bftv.fui.thirdparty.notify.IVoiceObserver
import com.bftv.fui.voicehelpexpandview.AIFuncViewListener
import com.bftv.fui.voicehelpexpandview.util.Tip
import kotlinx.android.synthetic.main.demo_layout.*

/**
 * Author by Less on 17/11/15.
 */
class DemoView : Activity(), IVoiceObserver {
    override fun update(interceptionData: InterceptionData?): VoiceFeedback? {
        Handler(Looper.getMainLooper()).post { Toast.makeText(this@DemoView, "接到了:" + interceptionData.toString(), Toast.LENGTH_SHORT).show() }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_layout)

        //应用指令词
        btn_app.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            val hashMap = HashMap<String, String>()
            hashMap.put("搜索", "searchTag")
            tell.appCacheMap = hashMap
            tell.functionSupportType = FunctionCode.PLAY
            tell.pck = packageName
            tell.tellType = TELL_APP_CACHE
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //界面指令词
        btn_view.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            val hashMap = HashMap<String,String>()
            hashMap.put("播放","playTag")
            tell.viewCacheMap = hashMap
            tell.pck = packageName
            tell.functionSupportType = FunctionCode.PLAY
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_VIEW_CACHE
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //追加界面指令词
        btn_view_append.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            val hashMap = HashMap<String,String>()
            hashMap.put("收藏","collectTag")
            tell.viewCacheMap = hashMap
            tell.pck = packageName
            tell.isAppend = true
            tell.tellType = TELL_VIEW_CACHE
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //系统指令词
        btn_system_app.setOnClickListener(View.OnClickListener {
            val tell = Tell()
            tell.pck = packageName
            tell.className = this@DemoView.javaClass.name
            tell.tellType = TELL_SYSTEM
            tell.sequencecode = SequenceCode.TYPE_PAGE
            TellManager.getInstance().tell(App.sApp, tell)
        })

        //拉起语音
        btn_pull_far.setOnClickListener(View.OnClickListener {
            TellManager.getInstance().farPull(App.sApp,packageName)
        })

        //发送ASR
        btn_send_asr.setOnClickListener(View.OnClickListener {
            TellManager.getInstance().sendAsr(App.sApp,packageName,"下一页")
        })

        //提示词
        btn_tips.setOnClickListener(View.OnClickListener {
            val hashMap = HashMap<String,String>()
            hashMap.put("影视库", "video")
            hashMap.put("下一页", Constant.NO_VALUE)
            funview.updateTipWords(hashMap,object :AIFuncViewListener{
                override fun onRenderTip(map: HashMap<String, String>, code: Int) {
                    val tell = Tell()
                    tell.tipsMap = map
                    tell.pck = packageName
                    tell.className = this@DemoView.javaClass.name
                    tell.tellType = TELL_SYSTEM or TELL_TIPS
                    if(code == 0){
                        tell.sequencecode = SequenceCode.TYPE_PAGE
                    }else{
                        tell.sequencecode = code
                    }
                    TellManager.getInstance().tell(App.sApp, tell)
                }
                override fun onItemClicked(position: Int, tip: Tip) {
                    Log.e("Less","onItemClicked"+tip.key)
                    TellManager.getInstance().sendAsr(App.sApp,packageName,tip.key)
                }
            },this@DemoView.javaClass.name,"1.0")
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

}