package com.bftv.tell.a;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bftv.fui.thirdparty.IUserStatusNotice;
import com.bftv.fui.thirdparty.InterceptionData;
import com.bftv.fui.thirdparty.VoiceFeedback;
import com.bftv.fui.thirdparty.notify.DataChange;

/**
 * Author by Less on 17/10/19.
 */

public class TestService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    IUserStatusNotice.Stub stub = new IUserStatusNotice.Stub() {

        @Override
        public void onInterception(InterceptionData interceptionData) throws RemoteException {
            Log.e("Less", "onInterception:" + interceptionData.toString());
            DataChange.getInstance().notifyDataChange(interceptionData);
        }

        @Override
        public void onShow(boolean isFar) throws RemoteException {
            Log.e("Less", "用户开始说话");
        }

        @Override
        public void showUserText(final String userTxt, int age, int sex) throws RemoteException {
            Log.e("Less", "用户说完话了 age-用户的年龄 sex－用户的性别" + userTxt);

        }

        @Override
        public void setRecording(int vol) throws RemoteException {
            Log.e("Less", "用户说话的声音大小" + vol);
        }

        @Override
        public void setRecognizing() throws RemoteException {
            Log.e("Less", "用户说完话了 开始语音转文字 需要时间");
        }

        @Override
        public void onShowErrorText(String error) throws RemoteException {
            Log.e("Less", "发生错误了");
        }

        @Override
        public void shortClick() throws RemoteException {
            Log.e("Less", "用户按的非常快");
        }

    };
}

