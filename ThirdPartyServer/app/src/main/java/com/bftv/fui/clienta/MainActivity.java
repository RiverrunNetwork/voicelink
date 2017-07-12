package com.bftv.fui.clienta;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.bftv.fui.accessibility.VoiceAccessibility;
import com.bftv.fui.thirdparty.BindAidlManager;
import com.bftv.fui.thirdparty.IRemoteVoice;
import com.bftv.fui.thirdparty.SimpleLog;
import com.bftv.fui.thirdparty.VoiceContast;
import com.bftv.fui.thirdparty.VoiceFeedback;

import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            VoiceAccessibility.enable(this);
            VoiceAccessibility.openSystemSetting(this);
        }catch (Exception e){
            e.printStackTrace();
        }



        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BindAidlManager.getInstance().dealMessage(getApplicationContext(), "com.bftv.fui.test", "加载A客户端", "播放", new BindAidlManager.OnBindAidlListener() {
                    @Override
                    public void onSuccess(VoiceFeedback voiceFeedback) {
                        if (voiceFeedback != null) {
                            SimpleLog.l("xxxxxx:" + voiceFeedback.feedback);
                        }
                    }
                });
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //BindAidlManager.getInstance().dealMessage(getApplicationContext(),"com.bftv.fui.testb", "加载B客户端","播放");
            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(VoiceContast.ACTION_COMMON_MESSAGE);
                sendOrderedBroadcast(intent, null, new DefaultBroadcastReceiver(), null, Activity.RESULT_OK, "用户说了一句话", null);
            }
        });

        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.sendBroadcast(new Intent("action.test.a"));
            }
        });
    }


}
