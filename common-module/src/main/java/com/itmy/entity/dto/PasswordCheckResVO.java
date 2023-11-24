package com.itmy.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordCheckResVO {

    private Boolean lengthResult;

    private Boolean typeResult;

    private UserPasswordRule.PasswordLen requiredPasswordLen;

    private UserPasswordRule.PassWordComplexity requiredPassWordComplexity;


}
