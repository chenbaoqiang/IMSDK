//Generated. DO NOT modify.
package com.feinno.sdk.enums;

/**
 * 音视频通话的状态码
 */
public enum AvSessionStates {
    /**连接成功，正在通话，不包含响铃时间(来电和去电)*/
    CONNECTED(3),
    /**被对方保持通话(来电和去电)*/
    HELD(15),
    /**主动保持通话(来电和去电)*/
    HOLD(14),
    /**被对方挂断(来电和去电)（去电对方响铃挂断为Rejected）*/
    HUNG_UP(8),
    /**主动挂断(来电和去电)*/
    END(9),

    /**去电时尝试连接，对方响铃之前*/
    CONNECTING(1),
    /**去电对方不可达*/
    NOT_REACHABLE(11),
    /**去电时对方正在响铃*/
    RINGING(2),
    /**去电时对方忙*/
    BUSY(5),
    /**去电对方响铃，被对方挂断*/
    REJECTED(7),

    /**被邀请（来电），正在响铃*/
    INVITED(12),
    /**被邀请（来电）响铃后用户同意邀请（接听），此时连接尚未建立*/
    ACCEPTED(13),

    /**连接出现错误*/
    ERROR(10),
    /**连接失败*/
    FAILED(4),
    /**连接超时*/
    TIMEOUT(6);

    public static boolean isNewSession(int intState) {
        AvSessionStates state = AvSessionStates.fromInt(intState);
        return state == INVITED || state == CONNECTING;
    }

    public static boolean isEndSessionState(int state) {
        return isEndSessionState(AvSessionStates.fromInt(state));
    }

    public static boolean isEndSessionState(AvSessionStates state) {
        return (state == HUNG_UP
                || state == END
                || state == NOT_REACHABLE
                || state == BUSY
                || state == REJECTED
                || state == ERROR
                || state == FAILED
                || state == TIMEOUT);
    }

    public static boolean isOffHookState(int state) {
        return isOffHookState(AvSessionStates.fromInt(state));
    }

    public static boolean isOffHookState(AvSessionStates state) {
        return state == CONNECTED || state == HELD || state == HOLD;
    }

    public static boolean isIncomingOrOutgoingState(AvSessionStates state) {
        return state == INVITED || state == ACCEPTED || state == RINGING;
    }

    public static boolean isRingBackState(int state) {
        return state == RINGING.value();
    }

    public static boolean isRingBackState(AvSessionStates state) {
        return state == RINGING;
    }

    public static boolean isRingState(int state) {
        return state == INVITED.value();
    }

    public static boolean isRingState(AvSessionStates state) {
        return state == INVITED;
    }

    public static boolean isTimingState(int state) {
        return isTimingState(fromInt(state));
    }

    public static boolean isTimingState(AvSessionStates state) {
        return state == CONNECTED || state == HELD || state == HOLD;
    }

    public static boolean canHungUpState(AvSessionStates state) {
        return state == CONNECTED || state == HELD || state == HOLD ||
                state == CONNECTING || state == RINGING || state == INVITED || state == ACCEPTED;
    }

  private int nCode;
  private AvSessionStates(int code) {
    this.nCode = code;
  }
  public int value() { return this.nCode; }

    /**
     * 将整形值转换为枚举值
     * @param v 整型值
     * @return AvSessionStates枚举值
     */
  public static AvSessionStates fromInt(int v) {
    switch(v){
      case 3:
      return CONNECTED;
      case 2:
      return RINGING;
      case 5:
      return BUSY;
      case 15:
      return HELD;
      case 14:
      return HOLD;
      case 13:
      return ACCEPTED;
      case 12:
      return INVITED;
      case 10:
      return ERROR;
      case 11:
      return NOT_REACHABLE;
      case 8:
      return HUNG_UP;
      case 1:
      return CONNECTING;
      case 9:
      return END;
      case 7:
      return REJECTED;
      case 4:
      return FAILED;
      case 6:
      return TIMEOUT;
      default:
      return null;
    }
  }

  @Override
  public String toString() {
      String state;
      switch (this) {
          case CONNECTED:
              state = "Connected";
              break;
          case HELD:
              state = "Held";
              break;
          case HOLD:
              state = "Hold";
              break;
          case HUNG_UP:
              state = "HungUp";
              break;
          case END:
              state = "End";
              break;
          case CONNECTING:
              state = "Connecting";
              break;
          case NOT_REACHABLE:
              state = "NotReachable";
              break;
          case RINGING:
              state = "Ringing";
              break;
          case BUSY:
              state = "Busy";
              break;
          case REJECTED:
              state = "Rejected";
              break;
          case INVITED:
              state = "Invited";
              break;
          case ACCEPTED:
              state = "Accepted";
              break;
          case ERROR:
              state = "Error";
              break;
          case FAILED:
              state = "Failed";
              break;
          case TIMEOUT:
              state = "Timeout";
              break;
          default: {
              state = "NULL";
              break;
          }
      }
      return state;
  }
}
