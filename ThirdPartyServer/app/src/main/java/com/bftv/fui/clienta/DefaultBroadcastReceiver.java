package com.bftv.fui.clienta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bftv.fui.thirdparty.VoiceFeedback;

import static com.bftv.fui.clienta.App.sContext;

/**
 * @author less
 * @version 1.0
 * @title 类的名称
 * @description 该类主要功能描述
 * @company 北京奔流网络信息技术有限公司
 * @created 2017/7/7 0007 21:16
 * @changeRecord [修改记录] <br/>
 */
public class DefaultBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = getResultExtras(false);
        if(bundle != null){
            VoiceFeedback voiceFeedback = bundle.getParcelable("common_key");
            if(voiceFeedback != null){
                Toast.makeText(sContext,"resultData:"+voiceFeedback.feedback,Toast.LENGTH_SHORT).show();
            }
        }
    }

}
