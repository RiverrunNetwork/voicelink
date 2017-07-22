package com.bftv.fui.test;

import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bftv.fui.thirdparty.IRemoteVoice;
import com.bftv.fui.thirdparty.RomoteVoiceService;
import com.bftv.fui.thirdparty.SimpleLog;
import com.bftv.fui.thirdparty.VoiceFeedback;
import com.bftv.fui.thirdparty.notify.DataChange;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    public void send(String userTxt, String nlpJson, IRemoteVoice iRemoteVoice) {
        if(!TextUtils.isEmpty(nlpJson)){

            String tpye = "cmd";

            try {
                JSONObject jsonObject = new JSONObject(nlpJson);
                tpye = jsonObject.getString("type");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(tpye.equals("cmd")){
                VoiceFeedback voiceFeedback = DataChange.getInstance().notifyDataChange(nlpJson);
                if(voiceFeedback != null){
                    try {
                        iRemoteVoice.sendMessage(voiceFeedback);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    SimpleLog.l("send");
                }
            }else if(tpye.equals("middle_cmd")){
                VoiceFeedback voiceFb = new VoiceFeedback();
                voiceFb.isHasResult = true;
                voiceFb.listMiddleData = new ArrayList<>();
                voiceFb.feedback = "中间层接受到指令哈哈哈";
                voiceFb.type = VoiceFeedback.TYPE_MIDDLE;
                for(int i = 0; i < 1; i++){
                    voiceFb.listMiddleData.add(makeData());
                }
                try {
                    iRemoteVoice.sendMessage(voiceFb);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

        }
        else{
            //模拟第三方自己的nlp
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            VoiceFeedback voiceFb = new VoiceFeedback();
            voiceFb.isHasResult = true;
            voiceFb.listMiddleData = new ArrayList<>();
            voiceFb.feedback = "哈哈哈中间层数据我能控制了";
            voiceFb.type = VoiceFeedback.TYPE_MIDDLE;
            for(int i = 0; i < 5; i++){
                voiceFb.listMiddleData.add(makeData());
            }
            try {
                iRemoteVoice.sendMessage(voiceFb);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            SimpleLog.l("send");
        }
    }

    private VoiceFeedback.MiddleData makeData(){
        VoiceFeedback.MiddleData data = new VoiceFeedback.MiddleData();
        data.middleName = "123";
        return data;
    }

}
