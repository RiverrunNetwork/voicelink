package com.bftv.fui.test;

import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.bftv.fui.thirdparty.IRemoteVoice;
import com.bftv.fui.thirdparty.RomoteVoiceService;
import com.bftv.fui.thirdparty.SimpleLog;
import com.bftv.fui.thirdparty.VoiceFeedback;
import com.bftv.fui.thirdparty.notify.DataChange;

/**
 * @author less
 * @version 1.0
 * @title 类的名称
 * @description 该类主要功能描述
 * @company 北京奔流网络信息技术有限公司
 * @created 2017/7/7 0007 18:43
 * @changeRecord [修改记录] <br/>
 */
public class TaoBaoTvService extends RomoteVoiceService {


    @Override
    public VoiceFeedback send(String userTxt, String nlpJson, IRemoteVoice iRemoteVoice) {
        VoiceFeedback voiceFeedback = DataChange.getInstance().notifyDataChange(nlpJson);
        try {
            iRemoteVoice.sendMessage(voiceFeedback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        SimpleLog.l("send");
        return voiceFeedback;
    }
}
