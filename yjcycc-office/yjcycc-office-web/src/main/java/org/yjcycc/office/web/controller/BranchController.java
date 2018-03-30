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
import org.yjcycc.office.api.BranchService;
import org.yjcycc.office.common.entity.Branch;
import org.yjcycc.office.common.rmi.RMIClient;
import org.yjcycc.tools.common.Pager;

@RestController
@RequestMapping(value = "/branch")
public class BranchController {

	private Logger logger = Logger.getLogger(BranchController.class);
	
	/**
	 * 新增/修改机构
	 * @url /branch/list
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
     *  		"branchId": 1,
     *  		"branchName": "aa",
     *			"branchShortName": "a"
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
	@RequestMapping(value = "/list")
	public Object list(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();
		
		int pageNum = StringUtils.isBlank(request.getParameter("pageNum")) ? 1 : Integer.parseInt(request.getParameter("pageNum"));
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 10 : Integer.parseInt(request.getParameter("pageSize"));
		
		BranchService branchService = (BranchService)RMIClient.getRemoteService(BranchService.class);
		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Pager<Branch> pager = branchService.findPager(map, pageNum, pageSize);
			pro.put("pager", pager);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误 
		}
		
		pro.put("status", 0); // 成功
		return pro;
	}
	
	/**
	 * 新增/修改机构
	 * @url /branch/save
	 * @param request
	 * @return json
	 * {
	 *   status : 服务状态码
	 * }
	 * @服务状态码
	 * {
	 *   0 成功 
	 *   1 服务器内部错误 
	 *   2 参数branchName或branchShortName不能为空
	 *   3 参数branchId对应的数据不存在 
	 *   4 机构名称重复
	 * }
	 */
	@RequestMapping(value = "/save")
	public Object save(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();
		
		BranchService branchService = (BranchService)RMIClient.getRemoteService(BranchService.class);
		
		Integer branchId = StringUtils.isBlank(request.getParameter("branchId")) ? null : Integer.parseInt(request.getParameter("branchId"));
		String branchName = request.getParameter("branchName");
		String branchShortName = request.getParameter("branchShortName");
		if (StringUtils.isBlank(branchName) || StringUtils.isBlank(branchShortName)) {
			pro.put("status", 2); // 参数branchName或branchShortName不能为空
			return pro;
		}
		Branch branch = new Branch();
		
		if (branchId == null) {
			branch.setBranchName(branchName);
			try {
				branch = branchService.get(branch);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				pro.put("status", 1); // 服务器内部错误
				return pro;
			}
			if (branch != null) {
				pro.put("status", 4); // 机构名称重复
				return pro;
			}
			
			branch = new Branch();
		} else {
			branch.setBranchId(branchId);
			try {
				branch = branchService.get(branch);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				pro.put("status", 1); // 服务器内部错误
				return pro;
			}
			
			if (branch == null) {
				pro.put("status", 3); // 参数branchId对应的数据不存在
				return pro;
			}
		}
		
		branch.setBranchName(branchName);
		branch.setBranchShortName(branchShortName);
		try {
			branchService.saveOrUpdate(branch);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		
		pro.put("status", 0); // 保存成功
		return pro;
	}
	
	/**
	 * 新增/修改机构
	 * @url /branch/delete
	 * @param request
	 * @return json
	 * {
	 *   status : 服务状态码
	 * }
	 * @服务状态码
	 * {
	 *   0 成功 
	 *   1 服务器内部错误 
	 *   2 参数branchIdId不能为空 
	 *   3 参数branchId对应的数据不存在 
	 * }
	 */
	@RequestMapping(value = "/delete")
	public Object delete(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();
		
		Integer branchId = StringUtils.isBlank(request.getParameter("branchId")) ? null : Integer.parseInt(request.getParameter("branchId"));
		if (branchId == null) {
			pro.put("status", 2); // 参数branchIdId不能为空
			return pro;
		}
		
		BranchService branchService = (BranchService)RMIClient.getRemoteService(BranchService.class);
		
		Branch branch = new Branch();
		branch.setBranchId(branchId);
		try {
			branch = branchService.get(branch);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		if (branch == null) {
			pro.put("status", 3); // 参数branchId对应的数据不存在
			return pro;
		}
		
		try {
			branchService.delete(branch);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		
		pro.put("status", 0);
		return pro;
	}
	
}
