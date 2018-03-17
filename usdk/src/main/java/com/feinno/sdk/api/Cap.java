package com.feinno.sdk.api;

import com.feinno.sdk.Sdk;
import com.feinno.sdk.SdkApi;
import com.feinno.sdk.Callback;
import com.feinno.sdk.session.CapsSession;

/**
 * 该类提供了用于能力交换的接口
 */
public class Cap {

    /**
     * 查询某一电话号码的能力
     * @param number 要查询能力的电话号码
     * @param indent 用于回调的PendingIntent
     */
    //public void getCap(String  number, PendingIntent indent) { }

    /**
     * 查询某一电话号码的能力
     * @param sdkState {@link com.feinno.sdk.Sdk.SdkState}对象，用于指定当前用户等状态信息
     * @param number 要查询能力的电话号码
     * @param cb 用于回调的Callback接口
     */
    public static void getCap(Sdk.SdkState sdkState, String number, Callback<CapsSession> cb){
        SdkApi.capsexchange(sdkState, number, cb);
    }
}
