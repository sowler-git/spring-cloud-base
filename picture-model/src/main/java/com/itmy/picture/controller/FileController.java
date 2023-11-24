package com.itmy.picture.controller;



import com.itmy.constant.Consts;
import com.itmy.entity.base.Response;
import com.itmy.enums.ErrorEnum;
import com.itmy.exception.ParamException;
import com.itmy.picture.entity.UploadFileResult;
import com.itmy.picture.service.IFileOSService;
import com.itmy.utils.DateTimeUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 */
@Slf4j
@RestController
public class FileController {


	@Autowired
	private IFileOSService fileOSService;

	@ApiOperation("oss上传文件")
	@PostMapping("/files/oss")
	public Response uploadOSS(MultipartFile file, HttpServletRequest httpServletRequest) {
		if (file == null) {
			throw new ParamException(ErrorEnum.INVALID_PARAM);
		}
		String folderPath = DateTimeUtils.convertTimeToYearString(System.currentTimeMillis()) + "/" +
			Consts.MINIO_FOLDER_NAME_SYSTEM + "/" + DateTimeUtils.convertTimeToDayString(System.currentTimeMillis()) + "/";
		UploadFileResult uploadApkResult;
		try {
			uploadApkResult = fileOSService.uploadFile(file,folderPath);
		} catch (Exception e){
			log.error("上传异常",e);
			// 日志
			throw new ParamException(ErrorEnum.FILE_CREATE_FAILER);
		}
		return Response.success(uploadApkResult);
	}




}
