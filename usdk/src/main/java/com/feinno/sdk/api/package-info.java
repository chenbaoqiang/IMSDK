/**
 * 该包是SDK提供的功能入口
 *
 * <p>
 *   该包中包含了所有的业务逻辑调用接口，例如要发送消息调用 {@link com.feinno.sdk.api.Message} 类型即可；
 *   想进行能力查询调用 {@link com.feinno.sdk.api.Cap} 类型即可.
 *
 * </p>
 *
 * <p>
 *     调用接口采用的是异步回调的方式， {@link com.feinno.sdk.api.Callback} 接口被使用，
 *     Session 会做为参数传递给 Callback, 例如 {@link com.feinno.sdk.session.MessageSession}
 *
 * </p>
 */
package com.feinno.sdk.api;