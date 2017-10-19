package com.bftv.fui.voicehelp;

import com.bftv.fui.nlp.CacheData;

/**
 * 三方数据缓存助手
 * <p>
 * Created by michael on 2017/10/13 0013.
 */

public class ThirdPartDataCacheHelper {

    private static ThirdPartDataCacheHelper thirdPartDataCacheHelper = new ThirdPartDataCacheHelper();

    public CacheData mCacheDatal;

    private ThirdPartDataCacheHelper() {

    }

    public static ThirdPartDataCacheHelper getInstance() {
        return thirdPartDataCacheHelper;
    }

}
