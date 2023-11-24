package com.itmy.entity.dto;


import com.itmy.utils.RandomUtils;
import com.itmy.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;


import static com.itmy.constant.Consts.*;

@Data
@Slf4j
public class UserPasswordRule{
    /**
     {
     "accountLockRule":{"lockMinutes":15,"lockable":false,"maxPasswordErrTime":3},
     "passWordComplexity":{
     "containsDigit":true,
     "containsLowerCase":false,
     "containsSpecial":false,
     "containsUpperCase":false
     },
     "passwordLen":{"max":10,"min":6},
     "rememberMeDays":30
     }
     */
    private PasswordLen passwordLen;

    private PassWordComplexity passWordComplexity;

    private Integer rememberMeDays;

    private AccountLockRule accountLockRule;

    public PasswordCheckResVO check(String password) {
        return PasswordCheckResVO.builder()
                .lengthResult(passwordLen.check(password))
                .typeResult(passWordComplexity.check(password))
                .requiredPassWordComplexity(passWordComplexity)
                .requiredPasswordLen(passwordLen)
                .build();
    }

    public String randomGenerate(){
        int num = 0;
        if (passWordComplexity.getContainsUpperCase()){
            num++;
        }
        if (passWordComplexity.getContainsLowerCase()){
            num++;
        }
        if (passWordComplexity.getContainsDigit()){
            num++;
        }
        if (passWordComplexity.getContainsSpecial()){
            num++;
        }

        // 生成随机数组
        int[] randomArray = new int[num];
        randomArray = RandomUtils.specifyTotal(num, passwordLen.getMin(),1, randomArray);

        // 根据随机数组拼接对应的随机字符
        StringBuffer characterLibrary = new StringBuffer();
        int index = 0;
        if (passWordComplexity.getContainsUpperCase()){
            characterLibrary.append(RandomStringUtils.random(randomArray[index], UPPERCASE_LETTERS));
            index++;
        }
        if (passWordComplexity.getContainsLowerCase()){
            characterLibrary.append(RandomStringUtils.random(randomArray[index], LOWERCASE_LETTERS));
            index++;
        }
        if (passWordComplexity.getContainsDigit()){
            characterLibrary.append(RandomStringUtils.random(randomArray[index], NUMBERS));
            index++;
        }
        if (passWordComplexity.getContainsSpecial()){
            characterLibrary.append(RandomStringUtils.random(randomArray[index], SPECIAL_CHARS));
            index++;
        }

        return RandomUtils.randomSort(characterLibrary.toString());
    }

    @Data
    public static class PasswordLen {
        /**
         * 密码最少长度
         */
        private Integer min;

        /**
         * 密码最大长度
         */
        private Integer max;

        public Boolean check(String password) {
            return password != null && password.length() >= min && password.length() <= max;
        }

        public String errorMsg() {
            return String.format("密码长度范围： %d - %d", min, max);
        }
    }

    @Data
    public static class PassWordComplexity {
        /**
         * 需要包含大写字母
         */
        private Boolean containsUpperCase;

        /**
         * 需要包含小写字母
         */
        private Boolean containsLowerCase;

        /**
         * 需要包含数字
         */
        private Boolean containsDigit;

        /**
         * 需要包含特殊字符
         */
        private Boolean containsSpecial;


        public Boolean check(String password) {
            if (password == null) {
                return false;
            }
            boolean curContainsUpperCase = false;
            boolean curContainsLowerCase = false;
            boolean curContainsDigit = false;
            boolean curContainsSpecial = false;
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (Character.isUpperCase(c)) {
                    curContainsUpperCase = true;
                }
                if (Character.isLowerCase(c)) {
                    curContainsLowerCase = true;
                }
                if (Character.isDigit(c)) {
                    curContainsDigit = true;
                }
                if (SPECIAL_CHARS.contains(String.valueOf(c))) {
                    curContainsSpecial = true;
                }
            }
            return (!containsUpperCase || curContainsUpperCase)
                    && (!containsLowerCase || curContainsLowerCase)
                    && (!containsDigit || curContainsDigit)
                    && (!containsSpecial || curContainsSpecial);
        }

        public String errorMsg() {
            StringBuilder sb = new StringBuilder("密码需要包含");
            if (containsUpperCase) {
                sb.append("大写字母，");
            }
            if (containsLowerCase) {
                sb.append("小写字母，");
            }
            if (containsDigit) {
                sb.append("数字，");
            }
            if (containsSpecial) {
                String specials = StringUtils.join(SPECIAL_CHARS.toCharArray(), '(', ')', '，');
                sb.append("特殊字符").append(specials).append('，');
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class AccountLockRule {
        /**
         * 密码错误多次是否锁定
         */
        private Boolean lockable;

        /**
         * 连续输入几次密码后自动锁定
         */
        private Integer maxPasswordErrTime;

        /**
         * 锁定时间（分）
         */
        private Integer lockMinutes;

    }
}
