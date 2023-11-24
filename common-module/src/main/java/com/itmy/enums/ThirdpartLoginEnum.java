package com.itmy.enums;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: niusaibo
 * @date: 2023-10-13 14:53
 * 第三方登录方式
 */
@Getter
public enum ThirdpartLoginEnum {
    /**
     * 0365
     */
    O365("O365_LOGIN"),

    DINGTALK("DINGTALK_LOGIN"),

    WECHAT("WECHAT_LOGIN"),

    LDAP("LDAP_LOGIN")


    ;

    private String key;

    ThirdpartLoginEnum() {
    }

    ThirdpartLoginEnum(String key) {
        this.key = key;
    }

    public static List<String> valueKeys() {
        ThirdpartLoginEnum[] values = values();
        return Stream.of(values).map(ThirdpartLoginEnum::getKey).collect(Collectors.toList());
    }

    public static String getName(String key) {
        ThirdpartLoginEnum[] values = values();
        for (ThirdpartLoginEnum value : values) {
            if (value.getKey().equals(key)) {
                return value.name();
            }
        }
        return null;
    }
}
