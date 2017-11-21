package com.bftv.tell.a

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.bftv.fui.thirdparty.IUserStatusNotice
import com.bftv.fui.thirdparty.InterceptionData
import com.bftv.fui.thirdparty.notify.DataChange

/**
 * Author by Less on 17/11/21.
 */

class TestService : Service() {

    override fun onBind(intent: Intent?): IBinder {
        return stub
    }

    var stub: IUserStatusNotice.Stub = object : IUserStatusNotice.Stub() {

        @Throws(RemoteException::class)
        override fun onInterception(interceptionData: InterceptionData) {
            Log.e("Less", "onInterception:" + interceptionData.toString())
            DataChange.getInstance().notifyDataChange(interceptionData)
        }

        @Throws(RemoteException::class)
        override fun onShow(isFar: Boolean) {
            Log.e("Less", "用户开始说话")
        }

        @Throws(RemoteException::class)
        override fun showUserText(userTxt: String, age: Int, sex: Int) {
            Log.e("Less", "用户说完话了 age-用户的年龄 sex－用户的性别" + userTxt)

        }

        @Throws(RemoteException::class)
        override fun setRecording(vol: Int) {
            Log.e("Less", "用户说话的声音大小" + vol)
        }

        @Throws(RemoteException::class)
        override fun setRecognizing() {
            Log.e("Less", "用户说完话了 开始语音转文字 需要时间")
        }

        @Throws(RemoteException::class)
        override fun onShowErrorText(error: String) {
            Log.e("Less", "发生错误了")
        }

        @Throws(RemoteException::class)
        override fun shortClick() {
            Log.e("Less", "用户按的非常快")
        }

    }
}