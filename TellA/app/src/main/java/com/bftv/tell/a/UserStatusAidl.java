package com.bftv.tell.a;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bftv.fui.thirdparty.IUserStatusNotice;

/**
 * Author by Less on 17/10/17.
 */

public class UserStatusAidl extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    IUserStatusNotice.Stub stub = new IUserStatusNotice.Stub() {
        @Override
        public void onShow(boolean b) throws RemoteException {
            Log.e("Less", "remoteOnShow");
        }

        @Override
        public void showUserText(String s, int i, int i1) throws RemoteException {
            Log.e("Less", "remoteShowUserText");
        }

        @Override
        public void setRecording(int i) throws RemoteException {
            Log.e("Less", "remoteSetRecording");
        }

        @Override
        public void setRecognizing() throws RemoteException {
            Log.e("Less", "remoteSetRecognizing");
        }

        @Override
        public void onShowErrorText(String s) throws RemoteException {
            Log.e("Less", "remoteOnShowErrorText");
        }

        @Override
        public void shortClick() throws RemoteException {
            Log.e("Less", "remoteShortClick");
        }
    };
}
