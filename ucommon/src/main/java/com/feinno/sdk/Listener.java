package com.feinno.sdk;

/**
 * 被叫处理服务端事件的Listener接口
 */
public interface Listener<T> {
    void run(final T session);
}
