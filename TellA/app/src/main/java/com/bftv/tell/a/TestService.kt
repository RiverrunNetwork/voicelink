package com.bftv.tell.a

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.RemoteException
import android.util.Log
import android.widget.Toast
import com.bftv.fui.constantplugin.FunctionCode
import com.bftv.fui.constantplugin.TellCode
import com.bftv.fui.thirdparty.*
import com.bftv.fui.thirdparty.notify.DataChange

/**
 * Author by Less on 17/11/21.
 */

class TestService : Service() {

    companion object {
        val TAG = "TestService"
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "TestService-onCreate")
    }

    override fun onBind(intent: Intent?): IBinder {
        return stub
    }

    var stub: IUserStatusNotice.Stub = object : IUserStatusNotice.Stub() {

        override fun onControl(p0: String?) {
        }

        override fun onAsr(asr: String?, age: Int, sex: Int, iRemoteFeedback: IRemoteFeedback) {
            Log.e(TAG, "用户说完话了:"+asr)
            val feed = Feedback()
            feed.isHasResult = true
            feed.ttsContent = "我收到了,很开心!"
            iRemoteFeedback.feedback(feed)
            Handler(Looper.getMainLooper()).post({
                Toast.makeText(App.sApp,"用户说完话了$asr",Toast.LENGTH_SHORT).show()
            })
        }

        override fun onRecyclingNotice(recyclingData: RecyclingData?) {
            Log.e(TAG, "回收数据通知${recyclingData.toString()}")
        }

        @Throws(RemoteException::class)
        override fun onInterception(interceptionData: InterceptionData) {
            Log.e(TAG, "onInterception:" + interceptionData.toString())
            if(interceptionData.tellType.equals(TellCode.TELL_APP_CACHE)){
                Log.e(TAG,"应用指令词")
                if(interceptionData.equals("searchTag")){
                    Log.e(TAG,"命中搜索")
                }
            }else if(interceptionData.tellType.equals(TellCode.TELL_VIEW_CACHE)){
                Log.e(TAG,"界面指令词")
                if(interceptionData.equals("playTag")){
                    Log.e(TAG,"命中界面")
                }
            }else if(interceptionData.tellType.equals(TellCode.TELL_TIPS)){
                Log.e(TAG,"提示指令词")
            }else if(interceptionData.tellType.equals(TellCode.TELL_SYSTEM)){
                Log.e(TAG,"系统指令词")
                if(interceptionData.functionType == FunctionCode.NEXT){
                    Log.e(TAG,"命中下一页")
                }else if(interceptionData.functionType == FunctionCode.PLAY){
                    Log.e(TAG,"命中播放第${interceptionData.index}")
                }
            }
            DataChange.getInstance().notifyDataChange(interceptionData)
        }

        @Throws(RemoteException::class)
        override fun onShow(isFar: Boolean) {
            Log.e(TAG, "用户开始说话")
            Handler(Looper.getMainLooper()).post({
                Toast.makeText(App.sApp,"onShow",Toast.LENGTH_SHORT).show()
            })
        }

    }
}