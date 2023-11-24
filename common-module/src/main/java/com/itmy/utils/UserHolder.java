package com.itmy.utils;

import com.alibaba.fastjson.JSONObject;
import com.itmy.entity.CurrentUser;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author niusaibo
 * @date 2019-01-25 3:05 PM
 */
@Slf4j
public final class UserHolder {

	private static ThreadLocal<CurrentUser> userThreadLocal = new ThreadLocal<>();

	public static void setCurrentUser(CurrentUser currentUser) {
		userThreadLocal.set(currentUser);
	}

	public static CurrentUser getCurrentUser() {
		return userThreadLocal.get();
	}

	public static void remove() {
		userThreadLocal.remove();
	}

	public static Long getTenantId() {
		CurrentUser currentUser = getCurrentUser();
		if (currentUser == null || currentUser.getTenantId() == null){
			//TODO app调用时没有TenantId
			return 1L;
		}
		return Objects.isNull(currentUser) ? 0L : currentUser.getTenantId();
	}

	public static Long getUserId() {
		CurrentUser currentUser = getCurrentUser();
		log.info("currentUser:{}",JSONObject.toJSONString(currentUser));
		Long userId = null;
		if(Objects.isNull(currentUser)) {
			return userId;
		} else {
			try {
				userId = currentUser.getId();
			} catch (Exception e) {
				return userId;
			}
		}
		return userId;
	}

}
