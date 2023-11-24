package com.itmy.picture.utils;


import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;

import com.itmy.constant.Consts;
import io.minio.MinioClient;
import io.minio.policy.PolicyType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;


@Component
public class MinIoTemplate {

    private final static String MINIO_ACCESS_KEY = "minio_admin";

    private final static String MINIO_SECRET_KEY = "Admin@itmy";


    private MinioClient minioClient;

    private MinioClient minioClient() throws Exception {
        if (minioClient == null) {
            synchronized (MinIoTemplate.class) {
                if(minioClient == null) {
                    minioClient = new MinioClient("http://localhost:9000", MINIO_ACCESS_KEY, MINIO_SECRET_KEY);
                    minioClient.setTimeout(3000, 3000, 3000);
                }
            }
        }
        return minioClient;
    }

    /**
     * 上传图片
     * @param inputStream
     * @param originalName
     * @return
     * @throws Exception
     */
    public JSONObject uploadImage(InputStream inputStream, String originalName,String suffix,String folderPath) throws Exception {
        return upload(inputStream, originalName, "image/jpeg",suffix,folderPath);
    }

    /**
     * 上传视频
     * @param inputStream
     * @param originalName
     * @return
     * @throws Exception
     */
    public JSONObject uploadVideo(InputStream inputStream, String originalName,String suffix,String folderPath) throws Exception {
        return upload(inputStream, originalName, "video/mp4",suffix,folderPath);
    }

    /**
     * 上传文件
     * @param inputStream
     * @param originalName
     * @return
     * @throws Exception
     */
    public JSONObject uploadFile(InputStream inputStream, String originalName,String suffix,String folderPath) throws Exception {
        return upload(inputStream, originalName, "application/octet-stream",suffix,folderPath);
    }

    /**
     * 上传失败Excel文件
     * @param inputStream
     * @param originalName
     * @return
     * @throws Exception
     */
    public JSONObject uploadExcelFile(InputStream inputStream, String originalName,String suffix,String folderPath) throws Exception {
        return uploadExcel(inputStream, originalName, "application/octet-stream",suffix,folderPath);
    }

    /**
     * 上传字符串大文本内容
     * @param str
     * @return
     * @throws Exception
     */
    public JSONObject uploadString(String str,String suffix,String folderPath) throws Exception {
        if (StringUtils.isBlank(str)) {
            return new JSONObject();
        }
        InputStream inputStream = new ByteArrayInputStream(str.getBytes());
        return upload(inputStream, null, "text/html",suffix,folderPath);
    }


    /**
     * 获取文件外链
     * @param bucketName
     * @param objectName
     * @param expires
     * @return
     * @throws Exception
     */
    public String getObjectURL(String bucketName, String objectName, Integer expires) throws Exception{
        return minioClient().presignedGetObject(bucketName, objectName, expires);
    }

    /**
     * 获取文件
     * @param bucketName
     * @param objectName
     * @return
     * @throws Exception
     */
    public InputStream getObject(String bucketName, String objectName) throws Exception{
        return minioClient().getObject(bucketName, objectName);
    }

    /**
     * 上传文件
     * @param inputStream
     * @param originalName
     * @param contentType
     * @return
     * @throws Exception
     */
    private JSONObject upload(InputStream inputStream, String originalName, String contentType,String suffix,
                                     String folderPath) throws Exception {
        String hashName = encodeMD5(originalName + "_" + System.currentTimeMillis() + "_" + Math.random() * 1000) + "." + suffix;
        String objectName = folderPath + hashName;
        if (minioClient().bucketExists(Consts.MINIO_BUCKET_NAME) == false){
            minioClient().makeBucket(Consts.MINIO_BUCKET_NAME);
            minioClient().setBucketPolicy(Consts.MINIO_BUCKET_NAME,"*", PolicyType.READ_WRITE);
        }
        minioClient().putObject(Consts.MINIO_BUCKET_NAME, objectName, inputStream, contentType);
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);

        String minioUrlPrefix = Consts.MINIO_URL_PREFIX;
        resultObject.put("path", minioUrlPrefix + "/" + Consts.MINIO_BUCKET_NAME + "/" + objectName);
        resultObject.put("originalName", originalName);
        resultObject.put("hashName", hashName);
        return resultObject;
    }

    public static String encodeMD5(String message) {
        String md5str = "";

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] input = message.getBytes();
            byte[] buff = md.digest(input);
            md5str = bytesToHex(buff);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return md5str;
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();

        for(int i = 0; i < bytes.length; ++i) {
            int digital = bytes[i];
            if (digital < 0) {
                digital += 256;
            }

            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }

    /**
     * 上传文件
     * @param inputStream
     * @param originalName
     * @param contentType
     * @return
     * @throws Exception
     */
    private JSONObject uploadExcel(InputStream inputStream, String originalName, String contentType,String suffix,
                                     String folderPath) throws Exception {
        String hashName = originalName + "_" + System.currentTimeMillis() + "." + suffix;
        String objectName = folderPath + hashName;
        if (minioClient().bucketExists(Consts.MINIO_BUCKET_NAME) == false){
            minioClient().makeBucket(Consts.MINIO_BUCKET_NAME);
            minioClient().setBucketPolicy(Consts.MINIO_BUCKET_NAME,"*",PolicyType.READ_WRITE);
        }
        minioClient().putObject(Consts.MINIO_BUCKET_NAME, objectName, inputStream, contentType);
        JSONObject resultObject = new JSONObject();
        resultObject.put("success", true);
        String minioUrlPrefix = Consts.MINIO_URL_PREFIX;
        resultObject.put("path", minioUrlPrefix + "/" + Consts.MINIO_BUCKET_NAME + "/" + objectName);
        resultObject.put("originalName", originalName);
        resultObject.put("hashName", hashName);
        return resultObject;
    }

    /**
     * 下载文件
     * @param fileName
     * @param response
     * @param request
     */
    public void download(String fileName, HttpServletResponse response, HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String[] nameArray = StrUtil.split(fileName, "-");
        try (InputStream inputStream = minioClient().getObject(nameArray[0], nameArray[1])) {
            //解决乱码
            if ( //IE 8 至 IE 10
                    userAgent.toUpperCase().contains("MSIE") ||
                            //IE 11
                            userAgent.contains("Trident/7.0")) {
                fileName = java.net.URLEncoder.encode(nameArray[1], "UTF-8");
            } else {
                fileName = new String(nameArray[1].getBytes("UTF-8"), "iso-8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IoUtil.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {

        }
    }

}
