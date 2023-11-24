package com.itmy.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: niusaibo
 * @date: 2023-10-13 14:36
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResVO {

    /**
     * 登录凭证
     */
    private String token;

    /**
     * 是否是第一次登陆
     */
    private Boolean isFirstLogin;

    /**
     * 是否是中台登录
     */
    private Boolean isMiddleLogin;

}
