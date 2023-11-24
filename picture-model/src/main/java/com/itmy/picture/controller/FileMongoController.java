package com.itmy.picture.controller;


import com.itmy.entity.base.Response;
import com.itmy.picture.entity.FileMongo;
import com.itmy.picture.service.IFileMongoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author nsbo
 * @Date 2022/1/17 15:31
 */
@Api(tags = "上传文件管理")
@RestController
@RequestMapping("/file")
public class FileMongoController {


    @Autowired
    private IFileMongoService fileMongoService;

    @ApiOperation("上传文件")
    @PostMapping("/oss")
    public Response ssoFile(MultipartFile file, HttpServletRequest request) throws Exception {
      return fileMongoService.ssoFile(file,request);
    }



    @ApiOperation("下载文件")
    @GetMapping(value = "/download/{id}")
    public void downloadFile(@PathVariable("id") String fileId,
                             HttpServletResponse response) throws IOException {
        FileMongo fileOss = fileMongoService.downloadFile(fileId);
        byte[] bytes = fileOss.getContent().getData();
        String contentType = fileOss.getContentType();
        String fileName = fileOss.getFileName();
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType(contentType);
        response.setHeader("Content-Type", contentType);
        response.setHeader("Accept-Ranges", "bytes");
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Connection", "keep-alive");
        response.setHeader("Content-Length", new Long(fileOss.getFileSize()).toString());
        //设置文件名称
        if (contentType.equals("application/octet-stream")){
            response.setHeader("Content-Disposition",
                    "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
        }
        response.flushBuffer();
        try(ServletOutputStream os = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os)){
            bos.write(bytes);

            os.flush();
            bos.flush();
        }
    }

}
