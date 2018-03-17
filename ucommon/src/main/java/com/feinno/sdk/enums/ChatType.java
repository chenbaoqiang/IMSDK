//Generated. DO NOT modify.
package com.feinno.sdk.enums;

/**
 * 聊天类型
 */
public enum ChatType {
  /**一对一聊天*/
  SINGLE(1),
  /**广播消息*/
  BROADCAST(3),
  /**群组聊天*/
  GROUP(2),
  /**公众账号消息*/
  PUBLIC_ACCOUNT(4),
  /**定向消息*/
  DIRECTED(5);
  private int nCode;

  private ChatType(int code) {
    this.nCode = code;
  }

  public int value() { return this.nCode; }

  /**
   * 将整形值转换为枚举值
   * @param v 整型值
   * @return ChatType枚举值
   */
  public static ChatType fromInt(int v) {
    switch(v){
      case 1:
      return SINGLE;
      case 3:
      return BROADCAST;
      case 2:
      return GROUP;
      case 4:
      return PUBLIC_ACCOUNT;
      case 5:
      return DIRECTED;
      default:
      return null;
    }
  }

  @Override
  public String toString() {
    return String.valueOf (this.nCode );
  }
}
