package com.bftv.fui.voicehelp;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.bftv.fui.nlp.CacheData;
import com.bftv.fui.nlp.FunctionRegister;
import com.bftv.fui.tell.ITellMessage;
import com.bftv.fui.tell.TTS;
import com.bftv.fui.tell.Tell;
import com.bftv.fui.thirdparty.UserStatusNoticeManager;
import com.bftv.fui.verification.AuthenticationManager;

/**
 * Author by Less on 17/10/10.
 */

public class VoiceTellService extends Service {

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
        public void tell(Tell tell) throws RemoteException {
            boolean isOK = AuthenticationManager.getInstance().isSupport(VoiceTellService.this, tell.pck);
            if (!isOK) {
                Log.e("Less", "voicehelp-nverification fail");
                return;
            }
            Log.e("Less", "voicehelp-nverification success");
            ThirdPartDataCacheHelper.getInstance().mCacheDatal = new CacheData(tell.pck, tell.mTellMaps, false, "User");
            UserStatusNoticeManager.getInstance().onBind(VoiceTellService.this, tell.pck);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(VoiceTellService.this, "收到了用户自定义的特定指令词大耳朵表示很开心", Toast.LENGTH_SHORT).show();
                }
            });
            Log.e("Less", "voicehelp-ntell:" + tell.toString() + "||" + Thread.currentThread().getName());
        }

        @Override
        public void farPull(String s) throws RemoteException {
            boolean isOK = AuthenticationManager.getInstance().isSupport(VoiceTellService.this, s);
            if (!isOK) {
                Log.e("Less", "voicehelp-nverification fail");
                return;
            }
            Log.e("Less", "voicehelp-nverification success");
            Log.e("Less", "fvoicehelp-narPull:" + "||" + Thread.currentThread().getName());
        }

        @Override
        public void ttsControl(TTS tts) throws RemoteException {
            boolean isOK = AuthenticationManager.getInstance().isSupport(VoiceTellService.this, tts.pck);
            if (!isOK) {
                Log.e("Less", "voicehelp-nverification fail");
                return;
            }
            Log.e("Less", "voicehelp-nverification success");
            Log.e("Less", "voicehelp-nttsControl:" + tts.toString() + "||" + Thread.currentThread().getName());
        }

        @Override
        public void needAsr(String s) throws RemoteException {
            Log.e("Less", "voicehelp-needAsr:" + s);
            UserStatusNoticeManager.getInstance().onBind(VoiceTellService.this, s);
        }

        @Override
        public void clearAsr(String s) throws RemoteException {

        }

        @Override
        public void addFunctionTell(Tell tell) throws RemoteException {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(VoiceTellService.this, "收到了用户的功能按钮大耳朵很开心", Toast.LENGTH_SHORT).show();
                }
            });
            FunctionRegister.addCacheData(tell.pck,new CacheData(tell.pck,tell.mTellMaps,false,tell.falg));
        }

        @Override
        public void removeFunctionTell(Tell tell) throws RemoteException {

        }
    };

}
