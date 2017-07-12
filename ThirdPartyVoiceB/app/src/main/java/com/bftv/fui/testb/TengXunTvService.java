package com.bftv.fui.testb;

import com.bftv.fui.thirdparty.RomoteVoiceService;
import com.bftv.fui.thirdparty.VoiceFeedback;

/**
 * @author less
 * @version 1.0
 * @title 类的名称
 * @description 该类主要功能描述
 * @company 北京奔流网络信息技术有限公司
 * @created 2017/7/7 0007 18:43
 * @changeRecord [修改记录] <br/>
 */
public class TengXunTvService extends RomoteVoiceService {

    @Override
    public VoiceFeedback send(String s, String s1) {
        VoiceFeedback voiceFeedback = new VoiceFeedback();
        voiceFeedback.feedback = s;
        return voiceFeedback;
    }

}
