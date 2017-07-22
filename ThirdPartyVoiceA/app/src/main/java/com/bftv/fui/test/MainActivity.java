package com.bftv.fui.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bftv.fui.thirdparty.BindAidlManager;
import com.bftv.fui.thirdparty.SimpleLog;
import com.bftv.fui.thirdparty.VoiceFeedback;
import com.bftv.fui.thirdparty.notify.DataChange;
import com.bftv.fui.thirdparty.notify.IVoiceObserver;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements IVoiceObserver{

    Button mBtnCollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataChange.getInstance().addObserver(this);

        try{
            /**
             * 初始化包
             */
            BindAidlManager.getInstance().init(this,getPackageName(),true);
        }catch (Exception e){
            e.printStackTrace();
        }



        mBtnCollect = (Button)findViewById(R.id.btn1);

        mBtnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public VoiceFeedback update(String s) {
        try{
            JSONObject jsonObject = new JSONObject(s);
            String type = jsonObject.getString("collect");
            if(type.equals("收藏")){
                VoiceFeedback voiceFeedback = new VoiceFeedback();
                voiceFeedback.feedback = "我是Test1";
                voiceFeedback.isHasResult = true;
                voiceFeedback.type = VoiceFeedback.TYPE_CMD;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBtnCollect.performClick();
                    }
                });
                return voiceFeedback;
            }
        }catch (Throwable t){

        }
        return null;
    }
}
