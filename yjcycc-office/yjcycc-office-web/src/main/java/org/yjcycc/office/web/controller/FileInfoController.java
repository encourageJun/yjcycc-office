package org.yjcycc.office.web.controller;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yjcycc.office.api.FileInfoService;
import org.yjcycc.office.common.entity.FileInfo;
import org.yjcycc.office.common.rmi.RMIClient;
import org.yjcycc.tools.common.Pager;

@RestController
@RequestMapping(value = "/file")
public class FileInfoController {

	private Logger logger = Logger.getLogger(FileInfoController.class);
	
	/**
	 * 用户列表
	 * @url /file/list
	 * @param request
	 * @return json
	 * {
	 *   "status": 服务状态码
	 *   "pager": 
	 *   {
	 *   	"pageNum": 1,
     *		"pageSize": 10,
     *		"totalCount": 1,
     *		"list": [
     *		  {
     *	 		"id": 1,
     *   		"parentId": null,
     *   		"fileType": 0,
     *   		"fileName": "文件夹1",
     *   		"filePath": null,
     *   		"ifDelete": 0,
     *   		"remark": "文件夹1",
     *   		"fileOwner": "yangjun",
     *   		"createDate": 1522641000000
     *   	  }
     *		]
	 *   }
	 * }
	 * @服务状态码
	 * {
	 *   0 成功 
	 *   1 服务器内部错误 
	 * }
	 */
	@RequestMapping(value = "/list")
	public Object list(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();
		
		int pageNum = StringUtils.isBlank(request.getParameter("pageNum")) ? 1 : Integer.parseInt(request.getParameter("pageNum"));
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 10 : Integer.parseInt(request.getParameter("pageSize"));
		
		FileInfoService fileInfoService = (FileInfoService)RMIClient.getRemoteService(FileInfoService.class);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Pager<FileInfo> pager = fileInfoService.findPager(map, pageNum, pageSize);
			pro.put("pager", pager);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误 
			return pro;
		}
		
		pro.put("status", 0);
		return pro;
	}
	
	/**
	 * 新增/修改文件
	 * @url /file/save
	 * @param request
	 * @return json
	 * {
	 *   status : 服务状态码
	 * }
	 * @服务状态码
	 * {
	 *   0 成功 
	 *   1 服务器内部错误 
	 *   2 参数fileType/fileName/fileOwner/filePath不能为空
	 *   3 参数fileInfoId对应的数据不存在
	 * }
	 */
	@RequestMapping(value = "/save")
	public Object save(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();

		Integer fileInfoId = StringUtils.isBlank(request.getParameter("fileInfoId")) ? null : Integer.parseInt(request.getParameter("fileInfoId"));
		Integer fileType = StringUtils.isBlank(request.getParameter("fileType")) ? null : Integer.parseInt(request.getParameter("fileType"));
		Integer parentId = StringUtils.isBlank(request.getParameter("parentId")) ? null : Integer.parseInt(request.getParameter("parentId"));
		Integer ifDelete = StringUtils.isBlank(request.getParameter("ifDelete")) ? 0 : Integer.parseInt(request.getParameter("ifDelete"));
		String fileName = request.getParameter("fileName");
		String fileOwner = request.getParameter("fileOwner");
		String filePath = request.getParameter("filePath");
		String remark = request.getParameter("remark");
		if (fileType == null || StringUtils.isBlank(fileName) || StringUtils.isBlank(fileOwner)
				|| StringUtils.isBlank(filePath)) {
			pro.put("status", 2); // 参数fileType/fileName/fileOwner/filePath不能为空
			return pro;
		}
		
		
		FileInfoService fileInfoService = (FileInfoService)RMIClient.getRemoteService(FileInfoService.class);
		
		FileInfo fileInfo = new FileInfo();
		
		if (fileInfoId != null) {
			fileInfo.setId(fileInfoId);
			try {
				fileInfo = (FileInfo) fileInfoService.get(fileInfo);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				pro.put("status", 1); // 服务器内部错误
				return pro;
			}
			
			if (fileInfo == null) {
				pro.put("status", 3); // 参数fileInfoId对应的数据不存在
				return pro;
			}
		} else {
			fileInfo.setCreateDate(new Date());
		}

		fileInfo.setParentId(parentId);
		fileInfo.setFileType(fileType);
		fileInfo.setFileName(fileName);
		fileInfo.setFileOwner(fileOwner);
		fileInfo.setFilePath(filePath);
		fileInfo.setIfDelete(ifDelete);
		fileInfo.setRemark(remark);
		try {
			fileInfoService.saveOrUpdate(fileInfo);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		
		pro.put("status", 0);
		return pro;
	}
	
	/**
	 * 删除文件
	 * @url /file/delete
	 * @param request
	 * @return json
	 * {
	 *   status : 服务状态码
	 * }
	 * @服务状态码
	 * {
	 *   0 成功 
	 *   1 服务器内部错误 
	 *   2 参数fileInfoId不能为空 
	 *   3 参数fileInfoId对应的数据不存在 
	 * }
	 */
	@RequestMapping(value = "/delete")
	public Object delete(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();
		
		Integer fileInfoId = StringUtils.isBlank(request.getParameter("fileInfoId")) ? null : Integer.parseInt(request.getParameter("fileInfoId"));
		if (fileInfoId == null) {
			pro.put("status", 2); // 参数fileInfoId不能为空
			return pro;
		}
		
		FileInfoService fileInfoService = (FileInfoService)RMIClient.getRemoteService(FileInfoService.class);
		FileInfo fileInfo = new FileInfo();
		fileInfo.setId(fileInfoId);		
		try {
			fileInfo = (FileInfo) fileInfoService.get(fileInfo);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		if (fileInfo == null) {
			pro.put("status", 3); // 参数fileInfo对应的数据不存在
			return pro;
		}
		
		try {
			fileInfoService.delete(fileInfo);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		
		pro.put("status", 0);
		return pro;
	}
	
	/**
	 * 文件详情
	 * @url /file/get
	 * @param request
	 * @return json
	 * {
	 *   "status": 服务状态码
	 *   "fileInfo": {
     *	 	"id": 1,
     *   	"parentId": null,
     *   	"fileType": 0,
     *   	"fileName": "文件夹1",
     *   	"filePath": null,
     *   	"ifDelete": 0,
     *   	"remark": "文件夹1",
     *   	"fileOwner": "yangjun",
     *   	"createDate": 1522641000000
     *   }
	 * }
	 * @服务状态码
	 * {
	 *   0 成功 
	 *   1 服务器内部错误 
	 *   2 参数fileInfoId不能为空 
	 *   3 参数fileInfoId对应的数据不存在 
	 * }
	 */
	@RequestMapping(value = "/get")
	public Object get(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();
		
		Integer fileInfoId = StringUtils.isBlank(request.getParameter("fileInfoId")) ? null : Integer.parseInt(request.getParameter("fileInfoId"));
		if (fileInfoId == null) {
			pro.put("status", 2); // 参数fileInfoId不能为空
			return pro;
		}
		
		FileInfoService fileInfoService = (FileInfoService)RMIClient.getRemoteService(FileInfoService.class);
		FileInfo fileInfo = new FileInfo();
		fileInfo.setId(fileInfoId);		
		try {
			fileInfo = (FileInfo) fileInfoService.get(fileInfo);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		if (fileInfo == null) {
			pro.put("status", 3); // 参数fileInfoId对应的数据不存在
			return pro;
		}
		pro.put("fileInfo", fileInfo);
		
		pro.put("status", 0);
		return pro;
	}
	
}
