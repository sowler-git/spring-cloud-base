package com.itmy.entity;

import lombok.Data;

/**
 * @Author: niusaibo
 * @date: 2023-10-09 11:23
 */
@Data
public class Blog {

    private String uid;

    private String title;

    private String summary;

    private String content;

    private String tagUid;

    private String tagName;

}
