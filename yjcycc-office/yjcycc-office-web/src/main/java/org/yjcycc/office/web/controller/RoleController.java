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
import org.yjcycc.office.api.RoleService;
import org.yjcycc.office.common.entity.Role;
import org.yjcycc.office.common.rmi.RMIClient;
import org.yjcycc.tools.common.Pager;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

private Logger logger = Logger.getLogger(RoleController.class);
	
	/**
	 * 角色列表
	 * @url /role/list
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
		
		RoleService roleService = (RoleService)RMIClient.getRemoteService(RoleService.class);
		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Pager<Role> pager = roleService.findPager(map, pageNum, pageSize);
			pro.put("pager", pager);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误 
			return pro;
		}
		
		pro.put("status", 0); // 成功
		return pro;
	}
	
	/**
	 * 新增/修改角色
	 * @url /role/save
	 * @param request
	 * @return json
	 * {
	 *   status : 服务状态码
	 * }
	 * @服务状态码
	 * {
	 *   0 成功 
	 *   1 服务器内部错误 
	 *   2 参数roleName不能为空
	 *   3 参数roleId对应的数据不存在 
	 *   4 角色名称重复
	 * }
	 */
	@RequestMapping(value = "/save")
	public Object save(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();
		
		Integer roleId = StringUtils.isBlank(request.getParameter("roleId")) ? null : Integer.parseInt(request.getParameter("roleId"));
		String roleName = request.getParameter("roleName");
		String roleDesc = request.getParameter("roleDesc");
		if (StringUtils.isBlank(roleName)) {
			pro.put("status", 2); // 参数roleName不能为空
			return pro;
		}
		Role role = new Role();
		
		RoleService roleService = (RoleService)RMIClient.getRemoteService(RoleService.class);
		
		if (roleId == null) {
			role.setRoleName(roleName);
			try {
				role = roleService.get(role);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				pro.put("status", 1); // 服务器内部错误
				return pro;
			}
			if (role != null) {
				pro.put("status", 4); // 角色名称重复
				return pro;
			}
			
			role = new Role();
		} else {
			role.setId(roleId);
			try {
				role = roleService.get(role);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				pro.put("status", 1); // 服务器内部错误
				return pro;
			}
			
			if (role == null) {
				pro.put("status", 3); // 参数roleId对应的数据不存在
				return pro;
			}
		}
		
		role.setRoleName(roleName);
		role.setRoleDesc(roleDesc);
		try {
			roleService.saveOrUpdate(role);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		
		pro.put("status", 0); // 保存成功
		return pro;
	}
	
	/**
	 * 删除机构
	 * @url /role/delete
	 * @param request
	 * @return json
	 * {
	 *   status : 服务状态码
	 * }
	 * @服务状态码
	 * {
	 *   0 成功 
	 *   1 服务器内部错误 
	 *   2 参数roleId不能为空 
	 *   3 参数roleId对应的数据不存在 
	 * }
	 */
	@RequestMapping(value = "/delete")
	public Object delete(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();
		
		Integer roleId = StringUtils.isBlank(request.getParameter("roleId")) ? null : Integer.parseInt(request.getParameter("roleId"));
		if (roleId == null) {
			pro.put("status", 2); // 参数roleId不能为空
			return pro;
		}
		
		RoleService roleService = (RoleService)RMIClient.getRemoteService(RoleService.class);
		
		Role role = new Role();
		role.setId(roleId);
		try {
			role = roleService.get(role);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		if (role == null) {
			pro.put("status", 3); // 参数roleId对应的数据不存在
			return pro;
		}
		
		try {
			roleService.delete(role);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		
		pro.put("status", 0);
		return pro;
	}
	
}
