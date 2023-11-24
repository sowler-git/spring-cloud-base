package com.itmy.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: niusaibo
 * @date: 2023-10-13 14:52
 */
public enum  UserSourceEnum {
    /**
     * 1-9:web端的用户来源
     * 10-19:第三方的用户来源
     */
    REGISTERED(1,"注册"),
    IMPORT(2,"导入"),
    SYSTEM_ADD(3,"系统添加"),

    LDAP(10, "LDAP"),
    O365(11,"O365"),
    OA(12,"OA"),
    DINGDING(13,"钉钉"),
    WECHAT(14,"微信"),
    QQ(15,"QQ"),

    ;


    private Integer code;
    private String msg;

    UserSourceEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public static List<Integer> localUser(){
        return new ArrayList<Integer>(Arrays.asList(REGISTERED.getCode(),IMPORT.getCode(),SYSTEM_ADD.getCode()));
    }

    public static Integer findByThirdpartLogin(ThirdpartLoginEnum thirdpartLoginEnum) {
        if (thirdpartLoginEnum.equals(ThirdpartLoginEnum.DINGTALK)) {
            return DINGDING.getCode();
        }
        if (thirdpartLoginEnum.equals(ThirdpartLoginEnum.WECHAT)) {
            return WECHAT.getCode();
        }
        if (thirdpartLoginEnum.equals(ThirdpartLoginEnum.LDAP)) {
            return LDAP.getCode();
        }
        if (thirdpartLoginEnum.equals(ThirdpartLoginEnum.O365)) {
            return O365.getCode();
        }
        return -1;
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
