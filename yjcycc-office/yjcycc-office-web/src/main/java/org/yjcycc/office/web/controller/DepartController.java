package org.yjcycc.office.web.controller;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yjcycc.office.api.DepartService;
import org.yjcycc.office.common.entity.Depart;
import org.yjcycc.office.common.rmi.RMIClient;
import org.yjcycc.office.dto.DepartDTO;
import org.yjcycc.tools.common.Pager;

@RestController
@RequestMapping(value = "/depart")
public class DepartController {

	private Logger logger = Logger.getLogger(DepartController.class);
	
	/**
	 * 部门列表
	 * @url /depart/list
	 * @param request
	 * @return json
	 * {
	 *   status : 服务状态码
	 *   pager : 
	 *   {
	 *   	"pageNum": 1,
     *		"pageSize": 10,
     *		"totalCount": 1,
     *		"list": [
     *		  {
     *  		"id": 1,
     *  		"branchName": "aa",
     *			"departId": 1,
     *			"departName": "aa",
     *			"principalUser": "yangjun",
     *			"connectTelNo": "020-11112222",
     *			"connectMobileTelNo": "13025139969",
     *			"faxes": "encourage_jun@163.com"
     *		  }
     *		]
	 *   }
	 * }
	 * @服务状态码
	 * {
	 *   0 成功 
	 *   1 服务器内部错误 
	 * }
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/list")
	public Object list(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();
		
		int pageNum = StringUtils.isBlank(request.getParameter("pageNum")) ? 1 : Integer.parseInt(request.getParameter("pageNum"));
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 10 : Integer.parseInt(request.getParameter("pageSize"));
		
		DepartService departService = (DepartService)RMIClient.getRemoteService(DepartService.class);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Pager<DepartDTO> pager = departService.findPager(map, pageNum, pageSize);
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
	 * 新增/修改部门
	 * @url /depart/save
	 * @param request
	 * @return json
	 * {
	 *   status : 服务状态码
	 * }
	 * @服务状态码
	 * {
	 *   0 成功 
	 *   1 服务器内部错误 
	 *   2 参数branchId/departName/principalUser不能为空
	 *   3 参数departId对应的数据不存在 
	 *   4 部门名称重复
	 * }
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/save")
	public Object save(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();
		
		Integer departId = StringUtils.isBlank(request.getParameter("departId")) ? null : Integer.parseInt(request.getParameter("departId"));
		Integer branchId = StringUtils.isBlank(request.getParameter("branchId")) ? null : Integer.parseInt(request.getParameter("branchId"));
		String connectTelNo = request.getParameter("connectTelNo");
		String connectMobileTelNo = request.getParameter("connectMobileTelNo");
		String faxes = request.getParameter("faxes");
		String departName = request.getParameter("departName");
		String principalUser = request.getParameter("principalUser");
		if (branchId == null || StringUtils.isBlank(departName) || StringUtils.isBlank(principalUser)) {
			pro.put("status", 2); // 参数branchId/departName/principalUser不能为空
			return pro;
		}
		Depart depart = new Depart();
		
		DepartService departService = (DepartService)RMIClient.getRemoteService(DepartService.class);
		
		if (departId == null) {
			depart.setDepartName(departName);
			try {
				depart = (Depart) departService.get(depart);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				pro.put("status", 1); // 服务器内部错误 
				return pro;
			}
			if (depart != null) {
				pro.put("status", 4); // 部门名称重复
				return pro;
			}
			depart = new Depart();
		} else {
			depart.setId(departId);
			try {
				depart = (Depart) departService.get(depart);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				pro.put("status", 1); // 服务器内部错误
				return pro;
			}
			
			if (depart == null) {
				pro.put("status", 3); // 参数departId对应的数据不存在
				return pro;
			}
		}
		
		depart.setDepartName(departName);
		depart.setBranchId(branchId);
		depart.setPrincipalUser(principalUser);
		depart.setConnectTelNo(connectTelNo);
		depart.setConnectMobileTelNo(connectMobileTelNo);
		depart.setFaxes(faxes);
		try {
			departService.saveOrUpdate(depart);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		
		pro.put("status", 0);
		return pro;
	}
	
	/**
	 * 删除部门
	 * @url /depart/delete
	 * @param request
	 * @return json
	 * {
	 *   status : 服务状态码
	 * }
	 * @服务状态码
	 * {
	 *   0 成功 
	 *   1 服务器内部错误 
	 *   2 参数departId不能为空 
	 *   3 参数departId对应的数据不存在 
	 * }
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/delete")
	public Object delete(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();
		
		Integer departId = StringUtils.isBlank(request.getParameter("departId")) ? null : Integer.parseInt(request.getParameter("departId"));
		if (departId == null) {
			pro.put("status", 2); // 参数departId不能为空
			return pro;
		}
		
		DepartService departService = (DepartService)RMIClient.getRemoteService(DepartService.class);
		Depart depart = new Depart();
		depart.setId(departId);		
		try {
			depart = (Depart) departService.get(depart);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		if (depart == null) {
			pro.put("status", 3); // 参数departId对应的数据不存在
			return pro;
		}
		
		try {
			departService.delete(depart);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		
		pro.put("status", 0);
		return pro;
	}
	
	
	
}
