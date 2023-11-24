package com.itmy.picture.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import com.itmy.entity.base.Response;
import com.itmy.picture.entity.FileMongo;
import com.itmy.picture.mapper.FileMongoDao;
import com.itmy.picture.service.IFileMongoService;
import com.mongodb.client.gridfs.model.GridFSFile;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


/**
 * @Author: niusaibo
 * @date: 2023-06-29 14:41
 */
@Service
public class FileMongoServiceImpl implements IFileMongoService {

    @Autowired
    private FileMongoDao fileMongoDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GridFsTemplate gridFsTemplate;

//    @Autowired
//    private GridFSBucket gridFSBucket;

    private static Logger logger = LoggerFactory.getLogger(FileMongoServiceImpl.class);

    @Override
    public Response ssoFile(MultipartFile file, HttpServletRequest request) throws IOException {
        String[] nameArray = file.getOriginalFilename().split("\\.");
        String suffix = nameArray[nameArray.length - 1];
        String originalFilename = file.getOriginalFilename();
        String newFileName = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + RandomStringUtils.randomNumeric(3)  + "." + suffix;
        long size = file.getSize();
        FileMongo fileMongo;
        if (suffix.equals("png") || suffix.equals("PNG") || suffix.equals("jpg") || suffix.equals("JPG")
                || suffix.equals("jpeg") || suffix.equals("JPEG") || suffix.equals("gif")){
            fileMongo = uploadImage(originalFilename, newFileName, suffix, size, file.getBytes(),file.getInputStream());
        } else if(suffix.equals("xlsx") || suffix.equals("xls") || suffix.equals("doc") || suffix.equals("docx")
                || suffix.equals("txt") ||suffix.equals("ppt") || suffix.equals("pptx") || suffix.equals("xmind")
                || suffix.equals("pdf") || suffix.equals("zip") || suffix.equals("apk") || suffix.equals("rar")){
            fileMongo = uploadFile(originalFilename, newFileName, suffix, size, file.getBytes(),file.getInputStream());
        } else if(suffix.equals("mp4") || suffix.equals("mov") || suffix.equals("avi") || suffix.equals("mpg")
                || suffix.equals("swf") || suffix.equals("3gp") || suffix.equals("rm") || suffix.equals("rmvb")
                || suffix.equals("mkv")  || suffix.equals("wmv")){
            fileMongo = uploadVideo(originalFilename, newFileName, suffix, size, file.getBytes(),file.getInputStream());
        } else if (suffix.equals("mp3") || suffix.equals("wav") || suffix.equals("ogg")
                || suffix.equals("flac")){
           //MP3格式  //wav
            fileMongo = uploadAudio(originalFilename,newFileName,suffix,size,file.getBytes(),file.getInputStream());
        } else {
            return Response.fail("上传类型错误");
        }
        String fileUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()
                +File.separator+"file"+File.separator+"download"+File.separator + fileMongo.getId();
        return Response.success(fileUrl);
    }

    /**
     *  上传图片
     * @param bytes
     * @param originalName
     * @param suffix
     * @throws Exception
     */
    public FileMongo uploadImage(String originalName, String hashName, String suffix,
                            Long fileSize,byte[] bytes,InputStream is){
         return upload(originalName,hashName, "image/jpeg",suffix,fileSize,bytes,is);
    }

    /**
     * 上传视频
     * @param originalName
     * @return
     * @throws Exception
     */
    public FileMongo uploadVideo(String originalName, String hashName, String suffix,
                            Long fileSize,byte[] bytes,InputStream is){
         return upload(originalName,hashName, "video/mp4",suffix,fileSize,bytes,is);
    }

    /**
     * 上传文件
     * @param originalName
     * @return
     * @throws Exception
     */
    public FileMongo uploadFile(String originalName, String hashName, String suffix,
                           Long fileSize,byte[] bytes,InputStream is){
        return upload(originalName,hashName, "application/octet-stream",suffix,fileSize,bytes,is);
    }

    /**
     * 上传音频
     * @param originalName
     * @param hashName
     * @param suffix
     * @param fileSize
     * @param bytes
     * @return
     */
    public FileMongo uploadAudio(String originalName, String hashName, String suffix,
                               Long fileSize,byte[] bytes,InputStream is){
        String contentType;
       if (suffix.equals("wav")){
            contentType = "audio/wav";
        } else {
            contentType = "audio/mpeg";
        }
        return upload(originalName,hashName, contentType,suffix,fileSize,bytes,is);
    }


    private FileMongo upload(String originalName,String hashName,
                        String contentType, String suffix,
                        Long size, byte[] bytes,InputStream is){
        FileMongo fileOss = FileMongo.builder()
                .fileName(originalName)
                .hashName(hashName)
                .contentType(contentType)
                .fileSuffix(suffix)
                .fileSize(size)
                .createTime(LocalDateTime.now())
                .build();
        if (size > 16777216){
            logger.info("file size too long... need to use GridFS . size: {}",size);
            String fileToGridFSId = uploadFileToGridFS(is, contentType);
            fileOss.setGridFSId(fileToGridFSId);
        }else {
            logger.info("small file storage...");
            fileOss.setContent(new Binary(bytes));
        }
        fileMongoDao.save(fileOss);
        return fileOss;
    }

    public LocalDateTime getUtcTime(LocalDateTime utcDateTime){
        LocalDateTime localDateTime =utcDateTime.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime;
    }

    /**
     * 上传文件到Mongodb的GridFs中
     * @param in
     * @param contentType
     * @return
     */
    private String uploadFileToGridFS(InputStream in , String contentType){
        String gridfsId = IdUtil.simpleUUID();
        //将文件存储进GridFS中
        gridFsTemplate.store(in, gridfsId , contentType);
        return gridfsId;
    }


    @Override
    public FileMongo downloadFile(String fileId){
        Optional<FileMongo> fileOssOptional = fileMongoDao.findById(fileId);
        if (!fileOssOptional.isPresent()){
            return new FileMongo();
        }
        FileMongo fileOss = fileOssOptional.get();
        //处理时间
        fileOss.setCreateTime(getUtcTime(fileOss.getCreateTime()));
        if (!StringUtils.isEmpty(fileOss.getGridFSId())
                && fileOss.getContent() == null){
            findFileToGridFS(fileOss.getGridFSId(),fileOss);
        }
        return fileOss;
    }

    private void findFileToGridFS(String gridFSId,FileMongo fileOss){
        Query gridQuery = new Query()
                .addCriteria(Criteria.where("filename").is(gridFSId));
        //根据GridFSId查询文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(gridQuery);
        //打开流下载对象
//        GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(gridFSId);
//        if (downloadStream.getGridFSFile().getLength() > 0){
//            //获取流对象
//            GridFsResource resource=new GridFsResource(gridFSFile,downloadStream);
//            byte[] readBytes = IoUtil.readBytes(resource.getContent());
//            fileOss.setContent(new Binary(readBytes));
//        }
        GridFsResource resource = gridFsTemplate.getResource(gridFSFile);
        byte[] readBytes = IoUtil.readBytes(resource.getContent());
        fileOss.setContent(new Binary(readBytes));
    }
}
