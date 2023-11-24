package com.itmy.picture.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itmy.picture.entity.FileOSS;
import com.itmy.picture.entity.UploadFileResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 * 包含OSS上传文件、根据hashName查询FileOSS对象相关
 */
public interface IFileOSService extends IService<FileOSS> {

	/**
	 * OSS上传文件
	 * @param file
	 * @return
	 * @throws Exception
	 */
	UploadFileResult uploadFile(MultipartFile file, String folderPath) throws Exception;

	/**
	 * OSS上传文件
	 * @param inputStream
	 * @param originalFilename 文件名格式为 ****.***
	 * @param folderPath 文件存储路径 /类别/日期/
	 * @return
	 * @throws Exception
	 */
	UploadFileResult uploadFile(InputStream inputStream,String originalFilename ,String folderPath) throws Exception;

	/**
	 * OSS获取文件流
	 * @param filePath
	 * @param bucketName Consts.MINIO_BUCKET_NAME
	 * @return
	 * @throws Exception
	 */
	InputStream getFileInputStream(String filePath,String bucketName) throws Exception;

	/**
	 * 批量获取文件信息
	 * @param hashs
	 * @return
	 */
	List<FileOSS> listByHash(List<String> hashs);

	/**
	 * 根据hash集合获取文件信息
	 * @param hashName
	 * @return
	 */
	FileOSS getByHashName(String hashName);

	/**
	 * 校验邮件附件大小是否小于10M
	 * @param fileHashs
	 * @param totalSizeM 限制总大小 单位：M
	 */
	boolean validateAdjunctFileSize(List<String> fileHashs, Long totalSizeM);

}
