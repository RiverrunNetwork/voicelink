package com.bftv.fui.clienta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.bftv.fui.accessibility.VoiceAccessibility;
import com.bftv.fui.thirdparty.BindAidlManager;
import com.bftv.fui.thirdparty.SimpleLog;
import com.bftv.fui.thirdparty.VoiceFeedback;

/**
 * Author by Less on 17/7/10.
 */

public class TestBrocast extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        SimpleLog.l("onReceive:"+VoiceAccessibility.sPackageName);
        BindAidlManager.getInstance().dealMessage(App.sContext, "com.bftv.fui.test", "播放", "播放", new BindAidlManager.OnBindAidlListener() {
            @Override
            public void onSuccess(VoiceFeedback voiceFeedback) {
                SimpleLog.l("尼玛收不到消息");
            }
        });
    }

}
