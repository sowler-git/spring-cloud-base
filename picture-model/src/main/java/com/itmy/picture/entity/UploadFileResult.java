package com.itmy.picture.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lidy
 * @date 2019-10-23 11:13
 * @desc
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileResult {

    private boolean success;

    private String path;

    private String originalName;

    private String hashName;

    private Long fileSize;
}
