package com.bftv.tell.a

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.bftv.fui.constantplugin.TellCode.TELL_APP_CACHE
import com.bftv.fui.tell.Tell
import com.bftv.fui.tell.TellManager
import com.bftv.fui.thirdparty.InterceptionData
import com.bftv.fui.thirdparty.VoiceFeedback
import com.bftv.fui.thirdparty.notify.DataChange
import com.bftv.fui.thirdparty.notify.IVoiceObserver
import com.bftv.tell.a.R.id.btn_app
import kotlinx.android.synthetic.main.demo_layout.*
import java.util.HashMap

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
            hashMap.put("!搜索", "searchTag")
            tell.appCacheMap = hashMap
            tell.pck = packageName
            tell.tellType = TELL_APP_CACHE
            TellManager.getInstance().tell(App.sApp, tell)
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