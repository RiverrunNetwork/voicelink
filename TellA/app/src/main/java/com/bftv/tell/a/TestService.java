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
        public void onShow(boolean b) throws RemoteException {
            Log.e("Less", "用户开始说话");
        }

        @Override
        public void showUserText(final String userTxt, int age, int sex) throws RemoteException {
            Log.e("Less", "用户说完话了 age-用户的年龄 sex－用户的性别");

        }

        @Override
        public void setRecording(int vol) throws RemoteException {
            Log.e("Less", "用户说话的声音大小");
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

        @Override
        public void onInterception(final String nlpJson, final String flag, String pck, int age, int sex, int index) throws RemoteException {
            Log.e("Less", "拦截处理=nlpJson第三方自定义的value值｜flag第三方自定义的标签|pck包名字|age用户说话的年龄|sex用户说话的性别|index第几个");
            DataChange.getInstance().notifyDataChange(nlpJson+"|+"+flag);

        }
    };
}

