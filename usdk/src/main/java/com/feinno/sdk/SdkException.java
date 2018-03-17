package com.feinno.sdk;

import org.keplerproject.luajava.LuaException;

/**
 * SDK异常
 */
public class SdkException extends LuaException{
    public SdkException(String str) {
        super(str);
    }

    public SdkException(Exception e) {
        super(e);
    }
}
