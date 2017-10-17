package com.bftv.tell.a;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import com.bftv.fui.tell.TTS;
import com.bftv.fui.tell.Tell;
import com.bftv.fui.tell.TellManager;
import com.bftv.fui.thirdparty.VoiceFeedback;
import com.bftv.fui.thirdparty.notify.DataChange;
import com.bftv.fui.thirdparty.notify.IVoiceObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IVoiceObserver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataChange.getInstance().addObserver(this);

        findViewById(R.id.btn_function).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tell tell = new Tell();
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("我是码农","这里是自定义json格式");
                //tell.mTellMaps = hashMap;
                tell.pck = MainActivity.this.getPackageName();
                List<String> list = new ArrayList<>();
                list.add("喊暴风大耳朵说 我是码农");
                tell.mTips = list;
                TellManager.getInstance().send(MainActivity.this,tell);
            }
        });


        findViewById(R.id.btn_far).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TellManager.getInstance().farPull(MainActivity.this,MainActivity.this.getPackageName());
            }
        });

        findViewById(R.id.btn_tts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TTS tts = new TTS();
                tts.isTTSClose = false;
                tts.isTTSEnd = true;
                tts.tts = "我叫暴风大耳朵";
                tts.pck = MainActivity.this.getPackageName();
                TellManager.getInstance().tts(MainActivity.this,tts);
            }
        });

        findViewById(R.id.btn_need_asr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TellManager.getInstance().needAsr(MainActivity.this,MainActivity.this.getPackageName());
            }
        });
    }

    @Override
    public VoiceFeedback update(String s) {
        Log.e("Less","update:"+s);
        VoiceFeedback voiceFeedback = new VoiceFeedback();
        voiceFeedback.feedback = "我是Test1";
        voiceFeedback.isHasResult = true;
        voiceFeedback.type = VoiceFeedback.TYPE_CMD;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.btn_function).performClick();
            }
        });
        return voiceFeedback;
    }
}
