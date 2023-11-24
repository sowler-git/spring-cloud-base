package com.itmy.constant;

/**
 * @Author: niusaibo
 * @date: 2023-10-13 14:01
 */
public class Consts {

    public static final Long TENANT_ID = 1L;

    /**
     * 分隔符，用于部门拼接
     */
    public static final String DEPT_SEPARATOR = "->";

    public static final String SEPARATOR = "-";

    /**
     * 分隔符，用于rediskey拼接
     */
    public static final String POINT = ".";

    /**
     * 部门编号位数
     */
    public static final int NUMBEROFDIGITS = 8;

    /**
     * 中英文分隔符
     */
    public static final String LANGUAGE_SEPARATOR = "/";

    /**
     * 左括号
     */
    public static final String LEFT_BRACKETS = "(";

    /**
     * 右括号
     */
    public static final String RIGHT_BRACKETS = ")";

    /**
     * 部门连接符
     */
    public static final String AND_SEPARATOR = "&";

    /**
     * 顿号
     */
    public static final String NUMBER_SEPARATOR = "、";

    /**
     * 逗号
     */
    public static final String COMMA = ",";

    /**
     * 空格
     */
    public static final String SPACE_BAR = " ";

    /**
     * 循环会议允许冲突数量
     */
    public static final int ALLOWED_CONFLICT_NUM = 4;

    /**
     * 最多可预定多少天后的日期
     */
    public static final int MAX_BOOKING_DATE = 1080;

    /**
     * 一分钟多少毫秒
     */
    public static final long MINS = 60 * 1000L;

    /**
     * 邮件提醒的时间阈值
     */
    public static final long NOTICE_THRESHOLD = 15 * MINS;

    /**
     * 密码错误次数最限制
     */
    public static final int ACCOUNT_PWDERRORNUM = 5;

    /**
     * 一天有24小时
     */
    public static final int ONE_DAY_HOUR = 24;

    /**
     * 一个月30天
     */
    public static final int ONE_MONTH_DAY = 30;

    /**
     * 30分钟
     */
    public static final int HALT_AN_HOUR = 30;

    /**
     * 图片验证码内容
     */
    public final static char[] POSSIBLE_CHARS = "ABCDEFGHKMNPQRTUVWXY346789".toCharArray();


    /**
     * 图片验证码内容（仅数字）
     */
    public final static char[] POSSIBLE_NUM_CHARS = "346789".toCharArray();

    /**
     * 图片验证码长度
     */
    public final static int DEFAULT_CODE_LENGTH = 4;

    /**
     * 邮件验证码时效 分钟
     */
    public final static int EMAIL_CODE_AGING = 5;


    /**
     * 密码最小长度
     */
    public final static int PWD_MIN_LENGTH = 8;

    /**
     * 密码最大长度
     */
    public final static int PWD_MAX_LENGTH = 12;


    /**
     * 密码包含的特殊字符
     */
    public final static String SPECIAL_CHARS= "$@!%*#_~?&";
    public final static String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public final static String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    public final static String NUMBERS = "0123456789";


    /**
     * 桶名称
     */
    public final static String MINIO_BUCKET_NAME = "itmy-prod";
    public static final String MINIO_URL_PREFIX = "http://localhost:8080";

    public final static String MINIO_FOLDER_NAME_IMPORT_FAILED = "import_failed";
    public final static String MINIO_FOLDER_NAME_IMPORT_APK_IMAGES = "apk_images";
    public final static String MINIO_FOLDER_NAME_SYSTEM = "system";
    public final static String APK_CONFIG_PATH = "assets/theme_config";

    public final static String APK_IMAGE_PATH = "assets/theme_picture/";

    public final static String OSS_FOLDER_PATH_APK = "/apk/";

    public final static String OSS_FOLDER_PATH_FACE = "face";

    public final static String IMPORT_FAILED_EXCEL_NAME = "批量上传失败列表.xls";

    public final static String STAR = "*";

    public final static Integer MESSAGE_TYPE_USER = 1;

    public final static Integer MESSAGE_TYPE_DEPT = 2;



}
