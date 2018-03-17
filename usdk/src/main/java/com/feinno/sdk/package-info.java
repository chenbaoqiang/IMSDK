/**
 * 该包用于SDK的初始化、配置、等功能，目前SDK已经具备了支持多用户同时使用的能力
 * <p>
 * 这个包是整个 SDK 核心所在，提供了 SDK 初始化、配置、事件通知注册等内容.
 * SDK 的初始化，通过 {@link com.feinno.sdk.Sdk} 类来实现， 初始化过程通过 {@link com.feinno.sdk.SdkConf}
 * 来实现对 SDK 的配置设置.
 * </p>
 *
 * <p>
 *
 *     SDK 中的业务，包含两大类:
 *
 *       <ul><li>主动调用</li>
 *         <li>事件通知</li>
 *       </ul>
 *
 *     事件通知接口，在 Sdk 初始化过程中进行注册， 详细参见 ({@link com.feinno.sdk.Listener})，
 *     ({@link com.feinno.sdk.IListenerProvider}).
 *
 *     <br />
 *
 *     主动调用类型操作，主要参见 {@link com.feinno.sdk.api} 包.
 *
 *     <br />
 * </p>
 * <p>
 *     这两类业务中，都围绕着 <I>Session</I> 来进行操作， Session 是一个业务的上下文信息，
 *     每一个业务都会存在一个 Session 对象与之相对应，具体参见 {@link com.feinno.sdk.session} 包
 *
 * </p>
 *
 * <p>
 *     SDK的多用户支持。每个用户都需要有一个对应的{@link com.feinno.sdk.Sdk.SdkState}对象。
 *     需要使用SDK的多用户功能时，可以通过调用{@link com.feinno.sdk.Sdk}的静态方法addNewState，获得一个{@link com.feinno.sdk.Sdk.SdkState}对象。
 *     不同用户调用{@link com.feinno.sdk.api}包中的接口时，需传入各自对应的{@link com.feinno.sdk.Sdk.SdkState}对象。
 * </p>
 *
 */
package com.feinno.sdk;