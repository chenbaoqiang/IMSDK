//Generated. DO NOT modify.
package com.feinno.sdk.enums;

/**
 * 消息报告的类型
 */
public enum ReportType {

  /**
   * 已送达
   */
  DELIVERED(1),
  /**
   * 正在输入
   */
  TYPING(2),
  /**
   * 更新消息 ID
   * */
  UPDATE_MSG_ID(4),

  /**
   * 文件上传/下载进度
   */
  FILE_PROGRESS(8),

  /**
   * 已读
   */
  READ(16),

  /**
   * 已焚
   */
  BURN(32),

  /**
   * 撤回
   */
  WITH_DRAW(64),

  /**
   * 群组消息已送达
   */
  GROUP_DELIVERED(128),

  /**
   * 群组消息已读
   */
  GROUP_READ(256),

  /**
   * 群组消息撤销
   */
  GROUP_WITH_DRAW(512),

  /**
   * 短信送达通知
   */
  DELIVEREDSMS(1024),

  /**
   * 短信超频率
   */
  SMSFREQUENCY(2048);

  private int nCode;

  ReportType(int code) {
    this.nCode = code;
  }

  public int value() {
    return this.nCode;
  }

  /**
   * 将整形值转换为枚举值
   *
   * @param v 整型值
   * @return ReportType枚举值
   */
  public static ReportType fromInt(int v) {
    switch (v) {
      case 1:
        return DELIVERED;
      case 2:
        return TYPING;
      case 3:
        return UPDATE_MSG_ID;
      case 4:
        return FILE_PROGRESS;
      case 16:
        return READ;
      case 32:
        return BURN;
      case 64:
        return WITH_DRAW;
      case 128:
        return GROUP_DELIVERED;
      case 256:
        return GROUP_READ;
      case 512:
        return GROUP_WITH_DRAW;
      case 1024:
        return DELIVEREDSMS;
      case 2048:
        return SMSFREQUENCY;
      default:
        return null;
    }
  }

  @Override
  public String toString() {
    return String.valueOf(this.nCode);
  }
}
