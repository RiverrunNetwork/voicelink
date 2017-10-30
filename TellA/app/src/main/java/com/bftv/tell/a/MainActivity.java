package com.bftv.tell.a;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bftv.fui.constantplugin.SequenceCode;
import com.bftv.fui.tell.Tell;
import com.bftv.fui.tell.TellManager;
import com.bftv.fui.thirdparty.InterceptionData;
import com.bftv.fui.thirdparty.VoiceFeedback;
import com.bftv.fui.thirdparty.notify.DataChange;
import com.bftv.fui.thirdparty.notify.IVoiceObserver;

import java.util.HashMap;

import static com.bftv.fui.constantplugin.TellCode.TELL_CACHE;
import static com.bftv.fui.constantplugin.TellCode.TELL_CORRECT;
import static com.bftv.fui.constantplugin.TellCode.TELL_FUNCTION;
import static com.bftv.fui.constantplugin.TellCode.TELL_SYSTEM;

public class MainActivity extends AppCompatActivity implements IVoiceObserver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataChange.getInstance().addObserver(this);

        findViewById(R.id.btn_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tell tell = new Tell();
                tell.className = MainActivity.this.getClass().getName();
                tell.pck = MainActivity.this.getPackageName();
                HashMap<String, String> cacheMap = new HashMap<String, String>();
                cacheMap.put("你好", "缓存");
                tell.cacheMap = cacheMap;
                HashMap<String, String> functionMap = new HashMap<String, String>();
                functionMap.put("播放", "功能");
                tell.functionMap = functionMap;
                tell.sequencecode = SequenceCode.TYPE_PAGE;
                HashMap<String, String> correntMap = new HashMap<String, String>();
                correntMap.put("错误", "纠错");
                tell.correctMap = correntMap;
                tell.tellType = TELL_CACHE | TELL_FUNCTION | TELL_SYSTEM | TELL_CORRECT;
                TellManager.getInstance().tell(App.sApp, tell);
            }
        });

        //特定指令词
        findViewById(R.id.btn_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tell tell = new Tell();
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("你好", "缓存");
                tell.cacheMap = hashMap;
                tell.pck = MainActivity.this.getPackageName();
                tell.tellType = TELL_CACHE;
                TellManager.getInstance().tell(App.sApp, tell);
            }
        });

        //功能指令词
        findViewById(R.id.btn_function).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tell tell = new Tell();
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("播放", "功能");
                tell.functionMap = hashMap;
                tell.pck = MainActivity.this.getPackageName();
                tell.tellType = TELL_FUNCTION;
                tell.className = MainActivity.this.getClass().getName();
                TellManager.getInstance().tell(App.sApp, tell);
            }
        });

        //获取系统功能
        findViewById(R.id.btn_system_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tell tell = new Tell();
                tell.pck = MainActivity.this.getPackageName();
                tell.tellType = TELL_SYSTEM;
                tell.sequencecode = SequenceCode.TYPE_PAGE;
                tell.className = MainActivity.this.getClass().getName();
                TellManager.getInstance().tell(App.sApp, tell);
            }
        });

        //纠错
        findViewById(R.id.btn_corrent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tell tell = new Tell();
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("错误", "纠错");
                tell.correctMap = hashMap;
                tell.pck = MainActivity.this.getPackageName();
                tell.tellType = TELL_CORRECT;
                tell.className = MainActivity.this.getClass().getName();
                TellManager.getInstance().tell(App.sApp, tell);
            }
        });


        //自定义语音动画并且获取asr
        findViewById(R.id.btn_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TellManager.getInstance().needAsr(App.sApp, MainActivity.this.getPackageName());
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataChange.getInstance().deleteObserver(this);
    }

    @Override
    public VoiceFeedback update(final InterceptionData interceptionData) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "TestApp-MainActivity接到了:" + interceptionData.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}
