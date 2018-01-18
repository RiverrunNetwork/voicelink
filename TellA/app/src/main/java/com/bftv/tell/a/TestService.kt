package com.bftv.tell.a

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.bftv.fui.constantplugin.FunctionCode
import com.bftv.fui.thirdparty.IUserStatusNotice
import com.bftv.fui.thirdparty.InterceptionData
import com.bftv.fui.thirdparty.RecyclingData
import com.bftv.fui.thirdparty.notify.DataChange

/**
 * Author by Less on 17/11/21.
 */

class TestService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.e("Less", "TestService-onCreate")
    }

    override fun onBind(intent: Intent?): IBinder {
        return stub
    }

    var stub: IUserStatusNotice.Stub = object : IUserStatusNotice.Stub() {
        override fun onRecyclingNotice(recyclingData: RecyclingData?) {
            Log.e("Less", "回收数据通知${recyclingData.toString()}")
        }

        override fun onAsr(asr: String?, age: Int, sex: Int) {
            Log.e("Less", "用户说完话了:"+asr)
        }

        @Throws(RemoteException::class)
        override fun onInterception(interceptionData: InterceptionData) {
            Log.e("Less", "onInterception:" + interceptionData.toString())
            DataChange.getInstance().notifyDataChange(interceptionData)
        }

        @Throws(RemoteException::class)
        override fun onShow(isFar: Boolean) {
            Log.e("Less", "用户开始说话")
        }

    }
}