package com.itmy.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.itmy.utils.UserHolder;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;



@Component(value = "fillMetaObjectHandler")
public class FillMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		this.setFieldValByName("createTime", System.currentTimeMillis(), metaObject);
		this.setFieldValByName("createBy",
				UserHolder.getCurrentUser() == null ? "System" : UserHolder.getCurrentUser().getEmail(), metaObject);
		this.setFieldValByName("updateTime", System.currentTimeMillis(), metaObject);
		this.setFieldValByName("updateBy",
				UserHolder.getCurrentUser() == null ? "System" : UserHolder.getCurrentUser().getEmail(), metaObject);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		this.setFieldValByName("updateTime", System.currentTimeMillis(), metaObject);
		this.setFieldValByName("updateBy",
				UserHolder.getCurrentUser() == null ? "System" : UserHolder.getCurrentUser().getEmail(), metaObject);
	}

}