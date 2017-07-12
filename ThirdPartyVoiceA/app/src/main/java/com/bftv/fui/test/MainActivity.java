package com.bftv.fui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bftv.fui.thirdparty.BindAidlManager;
import com.bftv.fui.thirdparty.SimpleLog;
import com.bftv.fui.thirdparty.VoiceFeedback;
import com.bftv.fui.thirdparty.notify.DataChange;
import com.bftv.fui.thirdparty.notify.IVoiceObserver;

public class MainActivity extends AppCompatActivity implements IVoiceObserver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BindAidlManager.getInstance().init(this,null,true);

        int pid = android.os.Process.myPid();
        SimpleLog.l("pid:"+pid);

        DataChange.getInstance().addObserver(this);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataChange.getInstance().notifyDataChange("123");
            }
        });

    }

    @Override
    public VoiceFeedback update(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this,"用户点击我了:"+s,Toast.LENGTH_SHORT).show();
            }
        });
        VoiceFeedback voiceFeedback = new VoiceFeedback();
        voiceFeedback.isHasResult = true;
        voiceFeedback.feedback = "A用户成功处理";
        return voiceFeedback;
    }
}
