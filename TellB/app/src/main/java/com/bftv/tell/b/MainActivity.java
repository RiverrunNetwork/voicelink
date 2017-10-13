package com.bftv.tell.b;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bftv.fui.tell.Tell;
import com.bftv.fui.tell.TellManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tell tell = new Tell();
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("bbb我是码农","这里是自定义json格式");
                //tell.mTellMaps = hashMap;
                tell.pck = MainActivity.this.getPackageName();
                List<String> list = new ArrayList<>();
                list.add("bbbb喊暴风大耳朵说 我是码农");
                tell.mTips = list;
                TellManager.getInstance().send(MainActivity.this,tell);
            }
        });
    }
}
