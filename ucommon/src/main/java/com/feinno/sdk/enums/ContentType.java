//Generated. DO NOT modify.
package com.feinno.sdk.enums;

/**
 * 聊天消息的内容类型
 */
public enum ContentType {
    /**位置信息*/
    LOCATION(4),
    /**文本消息*/
    TEXT(1),
    /**语音消息*/
    AUDIO(3),
    /**图片消息*/
    PICTURE(2),
    /**通知信息*/
    NOTIFICATION(5),
    /**其它信息*/
    OTHER(6),
    /**电子名片**/
    VCARD(7),
    /**视频**/
    VIDEO(8),
    /**商店表情消息*/
    VEMOTICON(9),
    /**彩云文件消息*/
    CLOUDFILE(10),
    /**公众号图文混排消息*/
    PUBLIC_MSG(11),
    /**自定义消息(服务器透传)*/
    CUSTOM_MSG(100),
    /**透传消息IOT使用*/
    MSG_TYPE_NONSTOP(1000000);

  private int nCode;
  private ContentType(int code) {
    this.nCode = code;
  }
  public int value() { return this.nCode; }

    /**
     * 根据整形值生成枚举值
     * @param v 整形值
     * @return 枚举值
     */
  public static ContentType fromInt(int v) {
    switch(v){
      case 4:
      return LOCATION;
      case 1:
      return TEXT;
      case 3:
      return AUDIO;
      case 2:
      return PICTURE;
      case 5:
      return NOTIFICATION;
      case 6:
      return OTHER;
      case 7:
      return VCARD;
      case 8:
      return VIDEO;
      case 9:
      return VEMOTICON;
      case 10:
      return CLOUDFILE;
      case 11:
      return PUBLIC_MSG;
      case 100:
      return CUSTOM_MSG;
      default:
      return null;
    }
  }

  @Override
  public String toString() {
    return String.valueOf (this.nCode );
  }
}
