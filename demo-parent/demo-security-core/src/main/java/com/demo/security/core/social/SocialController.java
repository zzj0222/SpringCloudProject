/**
 * 
 */
package com.demo.security.core.social;

import com.laiai.core.BaseController;
import org.springframework.social.connect.Connection;

import com.demo.security.core.social.support.SocialUserInfo;

/**
 * @author zhailiang
 *
 */
public abstract class SocialController extends BaseController {

	/**
	 * 根据Connection信息构建SocialUserInfo
	 * @param connection
	 * @return
	 */
	protected SocialUserInfo buildSocialUserInfo(Connection<?> connection) {
		SocialUserInfo userInfo = new SocialUserInfo();
		userInfo.setProviderId(connection.getKey().getProviderId());
		userInfo.setProviderUserId(connection.getKey().getProviderUserId());
		userInfo.setNickname(connection.getDisplayName());
		userInfo.setHeadimg(connection.getImageUrl());
		return userInfo;
	}
	
}
