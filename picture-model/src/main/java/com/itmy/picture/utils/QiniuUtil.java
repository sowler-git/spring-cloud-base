package com.itmy.picture.utils;

import com.google.gson.Gson;

import com.itmy.picture.enums.EQiNiuArea;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 七牛云工具类
 *
 */
@Slf4j
@Component
public class QiniuUtil {


    /**
     * 七牛云上传图片
     *
     * @param localFilePath
     * @return
     */
    public String uploadQiniu(File localFilePath, Map<String, String> qiNiuConfig) throws QiniuException {

        //构造一个带指定Zone对象的配置类
        Configuration cfg = setQiNiuArea(qiNiuConfig.get("QI_NIU_AREA"));
        //生成上传凭证，然后准备上传
        String accessKey = qiNiuConfig.get("QI_NIU_ACCESS_KEY");
        String secretKey = qiNiuConfig.get("QI_NIU_SECRET_KEY");
        String bucket = qiNiuConfig.get("QI_NIU_BUCKET");
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        String key =UUID.randomUUID().toString();
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        Response response = uploadManager.put(localFilePath, key, upToken);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        log.info("{七牛图片上传key: " + putRet.key + ",七牛图片上传hash: " + putRet.hash + "}");
        return putRet.key;
    }

    /**
     * 七牛云上传图片
     *
     * @param localFilePath
     * @return
     */
    public String uploadQiniu(File localFilePath) throws QiniuException {
        //构造一个带指定Zone对象的配置类
        String qiNiuArea = " ";
        Configuration cfg = setQiNiuArea(qiNiuArea);
        //生成上传凭证，然后准备上传
        String accessKey = "";
        String secretKey = "";
        String bucket = "";
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        String key = UUID.randomUUID().toString();
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        Response response = uploadManager.put(localFilePath, key, upToken);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        log.info("{七牛图片上传key: " + putRet.key + ",七牛图片上传hash: " + putRet.hash + "}");
        return putRet.key;
    }


    /**
     * 删除七牛云文件
     *
     * @param fileName
     * @param qiNiuConfig
     * @return
     */
    public int deleteFile(String fileName, Map<String, String> qiNiuConfig) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = setQiNiuArea(qiNiuConfig.get("QI_NIU_AREA"));
        //获取上传凭证
        String accessKey = qiNiuConfig.get("QI_NIU_ACCESS_KEY");
        String secretKey = qiNiuConfig.get("QI_NIU_SECRET_KEY");
        String bucket = qiNiuConfig.get("QI_NIU_BUCKET");
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            Response delete = bucketManager.delete(bucket, key);
            log.info("{七牛云文件 {} 删除成功", fileName);
            return delete.statusCode;
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            log.error(ex.getMessage());
        }
        return -1;
    }

    /**
     * 批量删除七牛云图片
     *
     * @param fileNameList
     * @param qiNiuConfig
     * @return
     */
    public Boolean deleteFileList(List<String> fileNameList, Map<String, String> qiNiuConfig) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = setQiNiuArea(qiNiuConfig.get("QI_NIU_AREA"));
        //获取上传凭证
        String accessKey = qiNiuConfig.get("QI_NIU_ACCESS_KEY");
        String secretKey = qiNiuConfig.get("QI_NIU_SECRET_KEY");
        String bucket = qiNiuConfig.get("QI_NIU_BUCKET");
        int successCount = 0;
        for (String fileName : fileNameList) {
            String key = fileName;
            Auth auth = Auth.create(accessKey, secretKey);
            BucketManager bucketManager = new BucketManager(auth, cfg);
            try {
                Response delete = bucketManager.delete(bucket, key);
                log.info("{七牛云文件 {} 删除成功", fileName);
                successCount += 1;
            } catch (QiniuException ex) {
                //如果遇到异常，说明删除失败
                log.error(ex.getMessage());
            }
        }
        return successCount == fileNameList.size();
    }

    /**
     * 设置七牛云上传区域（内部方法）
     *
     * @param area
     * @return
     */
    private Configuration setQiNiuArea(String area) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = null;

        //zong2() 代表华南地区
        switch (EQiNiuArea.valueOf(area).getCode()) {
            case "z0": {
                cfg = new Configuration(Zone.zone0());
            }
            break;
            case "z1": {
                cfg = new Configuration(Zone.zone1());
            }
            break;
            case "z2": {
                cfg = new Configuration(Zone.zone2());
            }
            break;
            case "na0": {
                cfg = new Configuration(Zone.zoneNa0());
            }
            break;
            case "as0": {
                cfg = new Configuration(Zone.zoneAs0());
            }
            break;
            default: {
                return null;
            }
        }
        return cfg;
    }


}