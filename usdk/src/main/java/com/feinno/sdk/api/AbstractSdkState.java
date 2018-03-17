package com.feinno.sdk.api;

import com.feinno.sdk.SdkException;

import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

public abstract class AbstractSdkState {
    protected abstract LuaState getLuaState();

    /**
     * 启动SdkState
     * @throws {@link com.feinno.sdk.SdkException}
     */
    protected abstract void start() throws SdkException;
}
