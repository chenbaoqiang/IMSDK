//Generated. DO NOT modify.
package com.feinno.sdk.enums;

/**
 * 消息报告的值
 */
public enum ReportValue {
    /**已被禁止*/
  FORBIDDEN(6),
    /**出现错误*/
    ERROR(7),
    /**递送成功*/
    DELIVERED(1),
    /**已经存储*/
    STORED(4),
    /**已经显示*/
    DISPLAYED(2),
    /**发送失败*/
    FAILED(5),
    /**处理完毕*/
    PROCESSED(3);
  private int nCode;
  private ReportValue(int code) {
    this.nCode = code;
  }
  public int value() { return this.nCode; }

    /**
     * 将整形值转换为枚举值
     * @param v 整型值
     * @return ReportValue枚举值
     */
  public static ReportValue fromInt(int v) {
    switch(v){
      case 6:
      return FORBIDDEN;
      case 7:
      return ERROR;
      case 1:
      return DELIVERED;
      case 4:
      return STORED;
      case 2:
      return DISPLAYED;
      case 5:
      return FAILED;
      case 3:
      return PROCESSED;
      default:
      return null;
    }
  }

  @Override
  public String toString() {
    return String.valueOf (this.nCode );
  }
}
