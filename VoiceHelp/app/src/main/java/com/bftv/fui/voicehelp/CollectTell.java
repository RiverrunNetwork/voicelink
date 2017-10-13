package com.bftv.fui.voicehelp;

import android.content.Context;

import com.bftv.fui.activity.use.TopUtil;
import com.bftv.fui.tell.Tell;

import java.util.HashMap;

import static com.bftv.fui.voicehelp.App.sContext;

/**
 * Author by Less on 17/10/10.
 */

public class CollectTell {

    private static final CollectTell ourInstance = new CollectTell();

    private Tell mTell;

    public static CollectTell getInstance() {
        return ourInstance;
    }

    private CollectTell() {
    }

    public void fixData(Tell tell){
        if(TopUtil.getTopPck(sContext).equals(tell.pck)){
            mTell = tell;
        }
    }

    public Tell getCacheData(){
        return mTell;
    }
}
