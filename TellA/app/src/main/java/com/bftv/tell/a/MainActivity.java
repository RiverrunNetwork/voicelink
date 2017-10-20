package com.bftv.tell.a;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bftv.fui.tell.Tell;
import com.bftv.fui.tell.TellManager;
import com.bftv.fui.thirdparty.VoiceFeedback;
import com.bftv.fui.thirdparty.notify.DataChange;
import com.bftv.fui.thirdparty.notify.IVoiceObserver;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements IVoiceObserver{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataChange.getInstance().addObserver(this);

        final EditText et = (EditText) findViewById(R.id.user_txt);

        //模拟用户说话点击确定
        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("intent.action.bftv.test");
                intent.putExtra("test", et.getText().toString());
                sendBroadcast(intent);
            }
        });

        //特定指令词
        findViewById(R.id.btn_tedingzhilingci).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tell tell = new Tell();
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("test", "我这里返回结果了 哈哈哈哈");
                tell.mTellMaps = hashMap;
                tell.pck = MainActivity.this.getPackageName();
                TellManager.getInstance().tell(App.sApp, tell);
            }
        });

        //功能指令词
        findViewById(R.id.btn_function).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tell tell = new Tell();
                tell.falg = "btn_function";
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("play", "播放功能");
                tell.mTellMaps = hashMap;
                tell.pck = MainActivity.this.getPackageName();
                TellManager.getInstance().addFunctionTell(App.sApp, tell);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataChange.getInstance().deleteObserver(this);
        TellManager.getInstance().removeFunctionTell(App.sApp,MainActivity.this.getPackageName());
    }

    @Override
    public VoiceFeedback update(final String str) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,"TestApp-MainActivity接到了:"+str,Toast.LENGTH_SHORT).show();
            }
        });
        return null;
    }
}
