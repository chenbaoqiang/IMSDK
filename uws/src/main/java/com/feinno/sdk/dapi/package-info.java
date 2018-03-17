/**
 * 该包包含了UWS的管理接口和RCS业务的逻辑入口。
 *
 * <p>
 *     RCS业务都是在Service中实现的，因此使用之前需要在AndroidManifest中声明一个service，即com.feinno.sdk.dapi.RCSWorkingService。
 *     然后，通过{@link com.feinno.sdk.dapi.RCSManager}可以启动或停止Service。要想使用该Service，
 *     还需要调用{@link com.feinno.sdk.dapi.RCSManager}中的接口使用手机号码添加一个用户。
 *     当然还可以查看已有用户或者删除已有用户。
 * <p>
 *		RCS业务的逻辑接口包括：
 *		<ul>
 *		    <li>
 *				RCS消息接口：{@link com.feinno.sdk.dapi.RCSMessageManager}
 *			</li>
 *		    <li>
 *				VoWifi音视频接口：{@link com.feinno.sdk.dapi.AVCallManager}
 *			</li>
 *		    <li>
 *				群组接口：{@link com.feinno.sdk.dapi.GroupManager}
 *			</li>
 *		    <li>
 *				能力查询接口：{@link com.feinno.sdk.dapi.CapsManager}
 *			</li>
 *		    <li>
 *				登录接口：{@link com.feinno.sdk.dapi.LoginManager}
 *			</li>
 *		</ul>
 * </p>
 */
package com.feinno.sdk.dapi;