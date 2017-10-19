package com.bftv.tell.a;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bftv.fui.tell.Tell;
import com.bftv.fui.tell.TellManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                TellManager.getInstance().tell(MainActivity.this, tell);
            }
        });

        //
        findViewById(R.id.btn_function).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tell tell = new Tell();
                tell.falg = "btn_function";
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("play", "播放功能");
                tell.mTellMaps = hashMap;
                tell.pck = MainActivity.this.getPackageName();
                TellManager.getInstance().addFunctionTell(MainActivity.this, tell);
            }
        });

    }
}
