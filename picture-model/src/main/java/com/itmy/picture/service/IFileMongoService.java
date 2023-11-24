package com.itmy.picture.service;

import com.itmy.entity.base.Response;
import com.itmy.picture.entity.FileMongo;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: niusaibo
 * @date: 2023-06-29 14:40
 */
public interface IFileMongoService {

    /**
     * 文件上传
     * @param file
     * @param request
     * @return
     */
     Response ssoFile(MultipartFile file, HttpServletRequest request) throws IOException;


    /**
     * 访问文件
     * @param id
     * @return
     */
     FileMongo downloadFile(String id);

}
