package com.itmy.picture.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

/**
 * @Author: niusaibo
 * @date: 2023-06-29 14:35
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("tb_file_oss")
public class FileMongo {

    @MongoId
    @Field("_id")
    private ObjectId id;

    /**
     * 原名
     */
    @Field("file_name")
    private String fileName;

    /**
     * HASH名
     */
    @Field("hash_name")
    private String hashName;


    /**
     * 文件类型
     */
    @Field("content_type")
    private String contentType;

    /**
     * 文件后缀
     */
    @Field("file_suffix")
    private String fileSuffix;

    /**
     * 文件大小
     */
    @Field("file_size")
    private Long fileSize;

    /**
     * 创建时间
     */
    @Field("create_time")
    private LocalDateTime createTime;

    /**
     * 文件内容
     */
    @Field("content")
    private Binary content;

    /**
     * 文件描述
     */
    @Field("description")
    private String description;

    /**
     * 大文件管理GridFS的ID
     */
    @Field("grid_fs_id")
    private String gridFSId;

}
