package com.itmy.picture.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("tb_file_oss")
public class FileOSS extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long tenantId;

    /**
     * 原文件名
     */
    private String originName;

    /**
     * HASH名
     */
    private String hashName;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 连接
     */
    private String path;


    public static final String TENANT_ID = "tenant_id";

    public static final String ORIGIN_NAME = "origin_name";

    public static final String HASH_NAME = "hash_name";

    public static final String FILE_SUFFIX = "file_suffix";

    public static final String FILE_SIZE = "file_size";

    public static final String SHORT_PATH = "path";


}
