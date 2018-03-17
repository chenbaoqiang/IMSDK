package com.feinno.sdk.enums;

/**
 * 公众号下发消息的类型
 */
public enum PubMediaTypeEnum {
    /**文本*/
    TEXT(10),
    /**电子名片*/
    VCARD(18),
    /**位置*/
    LOCATION(19),
    /**纯图片消息*/
    PICTURE(20),
    /**纯视频消息*/
    VIDEO(30),
    /**纯音频消息*/
    AUDIO(40),
    /**单图文混排消息*/
    SINGLE_MIX(51),
    /**多图文混排消息*/
    MULTI_MIX(52),
    /**短信*/
    SMS(60);


    private int nCode;
    private PubMediaTypeEnum(int code) {
        this.nCode = code;
    }
    public int value() { return this.nCode; }

    /**
     * 根据整形值生成枚举值
     * @param v 整形值
     * @return 枚举值
     */
    public static PubMediaTypeEnum fromInt(int v) {
        switch(v){
            case 10:
                return TEXT;
            case 18:
                return VCARD;
            case 19:
                return LOCATION;
            case 20:
                return PICTURE;
            case 30:
                return VIDEO;
            case 40:
                return AUDIO;
            case 51:
                return SINGLE_MIX;
            case 52:
                return MULTI_MIX;
            case 60:
                return SMS;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return String.valueOf (this.nCode );
    }
}
