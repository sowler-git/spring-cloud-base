package com.itmy.enums;

/**
 * <p>
 * 登陆设备
 * </p>
 */
public enum LoginDeviceTypeEnum {

	/**
	 *
	 * 	pc端
	 *
 	 */
	PC(0, "pc"),

	/**
	 * PAD端
	 */
	PAD(1, "pad"),

	/**
	 * 	移动端
	 */
	PHONE(2, "phone"),

	;

	private int code;

	private String desc;

	LoginDeviceTypeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
