/**
 * 包含了收听业务需要的状态信息
 *
 * <p>
 *  Session 是指一个业务场景中的上下文信息, 例如：
 * </p>
 *
 * <p>
 *     在消息逻辑中:
 *     <ul>
 *         <li>
 *				收到了一条消息，会通过一条Action为 {@link com.feinno.sdk.BroadcastActions}.ACTION_MESSAGE_TEXT_SESSION
 *				的广播通知 SDK 使用者，同时会将 {@link com.feinno.sdk.session.TextMessageSession}
 *				放在Intent的Extras中传递给该 SDK 使用者。
 *         </li>
 *     </ul>
 * </p>
 */
package com.feinno.sdk.session;