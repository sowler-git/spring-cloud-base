package com.itmy.picture.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmy.constant.Consts;
import com.itmy.enums.ErrorEnum;
import com.itmy.picture.entity.FileOSS;
import com.itmy.picture.entity.UploadFileResult;
import com.itmy.picture.mapper.FileOSSMapper;
import com.itmy.picture.service.IFileOSService;
import com.itmy.picture.utils.MinIoTemplate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
public class FileOSServiceImpl extends ServiceImpl<FileOSSMapper, FileOSS> implements IFileOSService {

	@Autowired
	private MinIoTemplate minIoTemplate;

	@Override
	public UploadFileResult uploadFile(MultipartFile file, String folderPath) throws Exception {

		UploadFileResult result = new UploadFileResult();
		String[] nameArray = file.getOriginalFilename().split("\\.");
		String suffix = nameArray[nameArray.length - 1];

		JSONObject object;
		if (suffix.equals("png") || suffix.equals("PNG") || suffix.equals("jpg") || suffix.equals("JPG")
				|| suffix.equals("jpeg") || suffix.equals("JPEG") || suffix.equals("gif")) {
			object = minIoTemplate.uploadImage(file.getInputStream(), file.getOriginalFilename(), suffix, folderPath);
		}
		else if(suffix.equals("xlsx") || suffix.equals("xls") || suffix.equals("doc") || suffix.equals("docx") ||
				suffix.equals("txt") ||suffix.equals("ppt") || suffix.equals("pptx") || suffix.equals("xmind") ||
				suffix.equals("pdf") || suffix.equals("zip") || suffix.equals("apk") || suffix.equals("mp4") || suffix.equals("avi")  || suffix.equals("rar")) {
			object = minIoTemplate.uploadFile(file.getInputStream(), file.getOriginalFilename(), suffix, folderPath);
		} else {
			throw new Exception(ErrorEnum.FILE_FORMAT_ERROR.getMsg());
		}
		if (object.getBoolean("success") == false) {
			result.setSuccess(false);
			return result;
		}

		FileOSS fileOSS = new FileOSS();
		fileOSS.setFileSize(file.getSize());
		fileOSS.setFileSuffix(suffix);
		fileOSS.setPath(object.getString("path"));
		fileOSS.setOriginName(object.getString("originalName"));
		fileOSS.setHashName(object.getString("hashName"));
		this.save(fileOSS);

		result.setSuccess(true);
		result.setHashName(object.getString("hashName"));
		result.setOriginalName(object.getString("originalName"));
		result.setPath(object.getString("path"));
		result.setFileSize(file.getSize());
		return result;
	}

	@Override
	public UploadFileResult uploadFile(InputStream inputStream, String originalFilename, String folderPath)
			throws Exception {

		UploadFileResult result = new UploadFileResult();
		String[] fileNameArray = originalFilename.split("\\.");
		String fileSuffix = fileNameArray[fileNameArray.length - 1];
		JSONObject object;
		if (fileSuffix.equals("png") || fileSuffix.equals("PNG") || fileSuffix.equals("jpg") || fileSuffix.equals("JPG")
				|| fileSuffix.equals("jpeg") || fileSuffix.equals("JPEG") || fileSuffix.equals("gif")) {
			object = minIoTemplate.uploadImage(inputStream, originalFilename, fileSuffix, folderPath);

		} else if(fileSuffix.equals("xlsx") || fileSuffix.equals("xls") || fileSuffix.equals("doc") || fileSuffix.equals("docx") ||
			fileSuffix.equals("txt") ||fileSuffix.equals("ppt") || fileSuffix.equals("pptx") || fileSuffix.equals("xmind") ||
			fileSuffix.equals("pdf") || fileSuffix.equals("zip") || fileSuffix.equals("apk") || fileSuffix.equals("mp4") || fileSuffix.equals("avi")  || fileSuffix.equals("rar")) {
			object = minIoTemplate.uploadFile(inputStream, originalFilename, fileSuffix, folderPath);
		}
		else {
			throw new Exception(ErrorEnum.FILE_FORMAT_ERROR.getMsg());
	}

		if (!object.getBoolean("success")) {
			result.setSuccess(false);
			return result;
		}

		FileOSS fileOSS = new FileOSS();
		fileOSS.setFileSize((long) inputStream.available());
		fileOSS.setFileSuffix(fileSuffix);
		fileOSS.setPath(object.getString("path"));
		fileOSS.setOriginName(object.getString("originalName"));
		fileOSS.setHashName(object.getString("hashName"));
		this.save(fileOSS);

		result.setSuccess(true);
		result.setHashName(object.getString("hashName"));
		result.setOriginalName(object.getString("originalName"));
		result.setPath(object.getString("path"));
		return result;

	}

	@Override
	public InputStream getFileInputStream(String filePath, String bucketName) throws Exception {
		// filePath做切割
		String[] str = filePath.split(Consts.MINIO_BUCKET_NAME);
		if (str.length == 2) {
			return minIoTemplate.getObject(bucketName, str[1]);
		}
		return null;
	}

	@Override
	public List<FileOSS> listByHash(List<String> hashs) {
		QueryWrapper<FileOSS> wrapper = new QueryWrapper<>();
		wrapper.in(FileOSS.HASH_NAME, hashs);
		return list(wrapper);
	}

	@Override
	public FileOSS getByHashName(String hashName) {
		if (StringUtils.isBlank(hashName)) {
			return null;
		}
		QueryWrapper<FileOSS> wrapper = new QueryWrapper<>();
		wrapper.eq(FileOSS.HASH_NAME, hashName);
		//wrapper.last("LIMIT 1");

		List<FileOSS> list = baseMapper.selectList(wrapper);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		list = list.stream().sorted(Comparator.comparing(FileOSS::getCreateTime).reversed()).collect(Collectors.toList());
		return list.get(0);
	}

	@Override
	public boolean validateAdjunctFileSize(List<String> fileHashs, Long totalSizeM) {
		List<FileOSS> list = listByHash(fileHashs);
		Long total = list.stream().mapToLong(m->m.getFileSize()).sum();
		if (total > (totalSizeM*1024*1024))
			return false;
		return true;
	}

}
