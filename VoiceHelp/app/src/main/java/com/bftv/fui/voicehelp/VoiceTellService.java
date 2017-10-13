package com.bftv.fui.voicehelp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.bftv.fui.tell.ITellMessage;
import com.bftv.fui.tell.TTS;
import com.bftv.fui.tell.Tell;
import com.bftv.fui.verification.AuthenticationManager;

/**
 * Author by Less on 17/10/10.
 */

public class VoiceTellService extends Service{

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final ITellMessage.Stub mBinder = new ITellMessage.Stub() {
        @Override
        public void sendMessage(Tell tell) throws RemoteException {
            boolean isOK = AuthenticationManager.getInstance().isSupport(VoiceTellService.this,tell.pck);
            if(!isOK){
                Log.e("Less","verification fail");
                return;
            }
            Log.e("Less","verification success");
            CollectTell.getInstance().fixData(tell);
            Log.e("Less","tell:"+tell.toString()+"||"+Thread.currentThread().getName());
        }

        @Override
        public void farPull(String s) throws RemoteException {
            boolean isOK = AuthenticationManager.getInstance().isSupport(VoiceTellService.this,s);
            if(!isOK){
                Log.e("Less","verification fail");
                return;
            }
            Log.e("Less","verification success");
            Log.e("Less","farPull:"+"||"+Thread.currentThread().getName());
        }

        @Override
        public void ttsControl(TTS tts) throws RemoteException {
            boolean isOK = AuthenticationManager.getInstance().isSupport(VoiceTellService.this,tts.pck);
            if(!isOK){
                Log.e("Less","verification fail");
                return;
            }
            Log.e("Less","verification success");
            Log.e("Less","ttsControl:"+tts.toString()+"||"+Thread.currentThread().getName());
        }
    };

}
