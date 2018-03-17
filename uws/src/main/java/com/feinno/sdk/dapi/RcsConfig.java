package com.feinno.sdk.dapi;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class RcsConfig {
    private String mqttNav;
    private  String protocol;
    private String proxy;
    private String cryptoType;
    
    private int debug;
    private int batch;
    private int conversation;

    public RcsConfig(){
        this.debug = -1;
        this.batch = -1;
        this.conversation = -1;
    }
    
    public String toJsonString() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if(!TextUtils.isEmpty(mqttNav)) {
            jsonObject.put("mqtt_nav", mqttNav);
        }
        if(!TextUtils.isEmpty(protocol)) {
            jsonObject.put("protocol", protocol);
        }
        if(!TextUtils.isEmpty(proxy)) {
            jsonObject.put("proxy", proxy);
        }
        if(!TextUtils.isEmpty(cryptoType)) {
            jsonObject.put("crypto_type", cryptoType);
        }
        
        if(debug >=0 && debug <=1){
            jsonObject.put("debug", debug);
        }
         if(batch >=0 && batch <=1){
            jsonObject.put("batch", batch);
        }
         if(conversation >=0 && conversation <=1){
            jsonObject.put("conversation", conversation);
        }
        return jsonObject.toString();
    }
    
    public String getMqttNav() {
        return mqttNav;
    }

    /**
     * mqtt 服务器访问地址 (默认 NULL SDK内置)
     * @param mqttNav
     */
    public void setMqttNav(String mqttNav) {
        this.mqttNav = mqttNav;
    }

    public String getProtocol() {
        return protocol;
    }

    /**
     * 网络协议 1. "mqtt": tcp直连 2: "http" : 使用http 代理 (默认 NULL SDK内置)
     * @param protocol
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProxy() {
        return proxy;
    }

    /**
     * 代理地址，仅支持http代理 (默认 NULL SDK内置)
     * @param proxy
     */
    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getCryptoType() {
        return cryptoType;
    }

    /**
     *  协议加密方式， 支持 1: "" 不加密 2: "rc4" 3: "aes" (默认 NULL SDK内置)
     * @param cryptoType
     */
    public void setCryptoType(String cryptoType) {
        this.cryptoType = cryptoType;
    }

    public int getDebug() {
        return debug;
    }

    /**
     * 是否开启debug日志 ， 0： 不支持， 1 ： 支持 (默认 -1 SDK内置)
     * @param debug
     */
    public void setDebug(int debug) {
        this.debug = debug;
    }

    public int getBatch() {
        return batch;
    }

    /**
     * 是否支持批推送消息 ，0： 不支持， 1 ： 支持 (默认 -1 SDK内置)
     * @param batch
     */
    public void setBatch(int batch) {
        this.batch = batch;
    }

    public int getConversation() {
        return conversation;
    }

    /**
     * 是否支持会话列表刷新， 0： 不支持， 1 ： 支持 (默认 -1 SDK内置) 
     * @param conversation
     */
    public void setConversation(int conversation) {
        this.conversation = conversation;
    }
   
}
