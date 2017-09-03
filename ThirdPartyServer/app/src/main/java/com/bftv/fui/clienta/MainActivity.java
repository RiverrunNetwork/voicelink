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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bftv.fui.accessibility.VoiceAccessibility;
import com.bftv.fui.cp.CommentsDataSource;
import com.bftv.fui.intent.AppIntentManager;
import com.bftv.fui.thirdparty.BindAidlManager;
import com.bftv.fui.thirdparty.IRemoteVoice;
import com.bftv.fui.thirdparty.SimpleLog;
import com.bftv.fui.thirdparty.VoiceContast;
import com.bftv.fui.thirdparty.VoiceFeedback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    /**
     * PCK_A 是a的包名
     */
    private static final String PCK_A = "com.bftv.fui.test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 获取相应的权限 第三方开发者忽略就好
         */
        try {
            VoiceAccessibility.enable(this);
            VoiceAccessibility.openSystemSetting(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         *  检查A是否支持aidl
         */
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = CommentsDataSource.isSupportAidl(MainActivity.this, PCK_A);
                Toast.makeText(MainActivity.this, "A是否支持aid:" + result, Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 全局跳转
         */

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 启动项目A的方式如下
                 *
                 * Intent intent = new Intent("android.test.a");
                 * startActivity(intent);
                 *
                 * 按照 暴风平台跳转规则进行转换
                 * https://github.com/BFTVVoice/VoiceLink/blob/master/intent.md
                 */
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("type", "uri");
                hashMap.put("uri", "tvtaobao://home?app=taobaosdk&module=detail&notshowloading=true&from=tvspeech&itemId=547757210519");
                AppIntentManager.getInstance().setIntent(MainActivity.this, hashMap.toString(), false);
            }
        });

        /**
         * 发送指令控制
         */
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("filter", "bigPic");
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(hashMap.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json = jsonObject.toString();
                String finalJson = getContainJson("help_cmd", json);
                Log.e("Less","finalJson:"+finalJson);
                BindAidlManager.getInstance().dealMessage(MainActivity.this, "com.bftv.fui.test", "查看大图", finalJson, new BindAidlManager.OnBindAidlListener() {
                    @Override
                    public void onSuccess(final VoiceFeedback voiceFeedback) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (voiceFeedback != null) {
                                }
                            }
                        });
                    }

                    @Override
                    public void onTimeOut(VoiceFeedback voiceFeedback) {

                    }

                    @Override
                    public void onTimeOut() {

                    }
                });
            }
        });

        /**
         * 中间层显示数据
         */
        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BindAidlManager.getInstance().dealMessage(MainActivity.this, "com.bftv.fui.test", "我想买牛仔裤", null, new BindAidlManager.OnBindAidlListener() {
                    @Override
                    public void onSuccess(final VoiceFeedback voiceFeedback) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (voiceFeedback != null) {
                                    Toast.makeText(MainActivity.this, voiceFeedback.feedback, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, MiddleActivity.class);
                                    intent.putExtra("test", voiceFeedback);
                                    startActivity(intent);
                                }
                            }
                        });
                    }

                    @Override
                    public void onTimeOut(VoiceFeedback voiceFeedback) {

                    }

                    @Override
                    public void onTimeOut() {

                    }
                });
            }
        });

    }

    public static String getContainJson(String key, String value) {
        return "{\"" + key + "\":" + value + "}";
    }
}
