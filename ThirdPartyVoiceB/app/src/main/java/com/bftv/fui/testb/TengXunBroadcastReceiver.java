package com.bftv.fui.testb;

import com.bftv.fui.thirdparty.CommonVoiceBroadcastReceiver;
import com.bftv.fui.thirdparty.VoiceFeedback;

/**
 * @author less
 * @version 1.0
 * @title 类的名称
 * @description 该类主要功能描述
 * @company 北京奔流网络信息技术有限公司
 * @created 2017/7/7 0007 21:49
 * @changeRecord [修改记录] <br/>
 */
public class TengXunBroadcastReceiver extends CommonVoiceBroadcastReceiver {

    @Override
    public VoiceFeedback transferMessage(String s, VoiceFeedback voiceFeedback) {
        VoiceFeedback feedback = new VoiceFeedback();
        feedback.feedback = "这是腾讯的广播";
        if(voiceFeedback != null){
            feedback.feedback += voiceFeedback.feedback;
        }
        return feedback;
    }
}
