package com.bftv.fui.test;

import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bftv.fui.thirdparty.IRemoteVoice;
import com.bftv.fui.thirdparty.RomoteVoiceService;
import com.bftv.fui.thirdparty.SimpleLog;
import com.bftv.fui.thirdparty.VoiceFeedback;
import com.bftv.fui.thirdparty.bean.AllIntent;
import com.bftv.fui.thirdparty.bean.MiddleData;
import com.bftv.fui.thirdparty.notify.DataChange;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

            String type = "help_cmd";
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(nlpJson);
                type = jsonObject.getString("help_cmd");
                if(!TextUtils.isEmpty(type)){
                    VoiceFeedback voiceFeedback = DataChange.getInstance().notifyDataChange(nlpJson);
                    if(voiceFeedback != null){
                        try {
                            iRemoteVoice.sendMessage(voiceFeedback);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        SimpleLog.l("send");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            if(type.equals("help_cmd")){
//                VoiceFeedback voiceFeedback = DataChange.getInstance().notifyDataChange(nlpJson);
//                if(voiceFeedback != null){
//                    try {
//                        iRemoteVoice.sendMessage(voiceFeedback);
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                    SimpleLog.l("send");
//                }
//            }else if(type.equals("middle_cmd")){
//                VoiceFeedback voiceFb = new VoiceFeedback();
//                voiceFb.isHasResult = true;
//                voiceFb.listMiddleData = new ArrayList<>();
//                voiceFb.feedback = "中间层接受到指令哈哈哈";
//                voiceFb.type = VoiceFeedback.TYPE_MIDDLE;
//                for(int i = 0; i < 1; i++){
//                    voiceFb.listMiddleData.add(makeData());
//                }
//                try {
//                    iRemoteVoice.sendMessage(voiceFb);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//            }

        }
        else{
            VoiceFeedback voiceFb = new VoiceFeedback();
            voiceFb.isHasResult = true;
            voiceFb.listMiddleData = new ArrayList<>();
            voiceFb.feedback = "这里是淘宝的测试数据";
            voiceFb.type = VoiceFeedback.TYPE_MIDDLE;
            for(int i = 0; i < 50; i++){
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

    private MiddleData makeData(){
        MiddleData middleData = new MiddleData();
        middleData.middlePic = "http://gw.alicdn.com/i1/TB1WXL2SXXXXXc4XFXXXXXXXXXX_!!0-item_pic.jpg";
        middleData.title = "鸿星尔克男鞋跑步鞋红夏季新款运动鞋男士网面透气休闲官方旗舰店";
        List<AllIntent> list = new ArrayList<>();
        AllIntent allIntent = new AllIntent();
        allIntent.type = "uri";
        allIntent.uri = "淘宝的uri淘宝的uri数据严格保密 需要自己替换";
        allIntent.entranceWords = "open";
        list.add(allIntent);
        middleData.listIntent = list;
        return middleData;
    }

}
