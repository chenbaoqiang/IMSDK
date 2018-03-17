package com.interrcs.sdk;

/**
 * SDK执行异步方法时的回调接口
 */
public interface Callback<T> {
    void run(final T result);
}