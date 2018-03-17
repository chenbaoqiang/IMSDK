//Generated. DO NOT modify.
package com.feinno.sdk.enums;

/**
 * 用户开通的状态
 */
public enum ProvisionStates {
    /**获取验证码*/
  GET_SMS_CODE(1),
    /**注册*/
    PROVISION(2);
  private int nCode;
  private ProvisionStates(int code) {
    this.nCode = code;
  }
  public int value() { return this.nCode; }

    /**
     * 将整形值转换为枚举值
     * @param v 整型值
     * @return ProvisionStates枚举值
     */
  public static ProvisionStates fromInt(int v) {
    switch(v){
      case 1:
      return GET_SMS_CODE;
      case 2:
      return PROVISION;
      default:
      return null;
    }
  }

  @Override
  public String toString() {
    return String.valueOf (this.nCode );
  }
}
