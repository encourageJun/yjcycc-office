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
import org.yjcycc.office.api.RoleService;
import org.yjcycc.office.api.UserService;
import org.yjcycc.office.common.entity.Depart;
import org.yjcycc.office.common.entity.Role;
import org.yjcycc.office.common.entity.User;
import org.yjcycc.office.common.rmi.RMIClient;
import org.yjcycc.tools.common.Pager;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class);
	
	/**
	 * 用户列表
	 * @url /user/list
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
     *  		"id": 1,
     *  		"userName": "yangjun",
     *			"realName": "杨俊",
     *			"password": "123",
     *			"departId": 4,
     *			"roleId": 3,
     *			"gender": 0,
     *			"userState": 0,
     *			"picUrl": "http://www.baidu.com"
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
		
		UserService userService = (UserService)RMIClient.getRemoteService(UserService.class);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Pager<User> pager = userService.findPager(map, pageNum, pageSize);
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
	 * 新增/修改用户
	 * @url /user/save
	 * @param request
	 * @return json
	 * {
	 *   status : 服务状态码
	 * }
	 * @服务状态码
	 * {
	 *   0 成功 
	 *   1 服务器内部错误 
	 *   2 参数departId/roleId/userName/realName/password/confirmPassword不能为空
	 *   3 参数userId对应的数据不存在
	 *   4 用户名重复
	 *   5 两次输入密码不一致
	 *   6 参数departId对应的数据不存在
	 *   7 参数roleId对应的数据不存在
	 * }
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/save")
	public Object save(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();

		Integer userId = StringUtils.isBlank(request.getParameter("userId")) ? null : Integer.parseInt(request.getParameter("userId"));
		Integer departId = StringUtils.isBlank(request.getParameter("departId")) ? null : Integer.parseInt(request.getParameter("departId"));
		Integer roleId = StringUtils.isBlank(request.getParameter("roleId")) ? null : Integer.parseInt(request.getParameter("roleId"));
		Integer gender = StringUtils.isBlank(request.getParameter("gender")) ? 0 : Integer.parseInt(request.getParameter("gender"));
		Integer userState = StringUtils.isBlank(request.getParameter("userState")) ? 0 : Integer.parseInt(request.getParameter("userState"));
		String userName = request.getParameter("userName");
		String realName = request.getParameter("realName");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String picUrl = request.getParameter("picUrl");
		if (departId == null || roleId == null || StringUtils.isBlank(userName) || StringUtils.isBlank(realName)
				|| StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
			pro.put("status", 2); // 参数departId/roleId/userName/realName/password/confirmPassword不能为空
			return pro;
		}
		if (!password.equals(confirmPassword)) {
			pro.put("status", 5); // 两次输入密码不一致
			return pro;
		}
		
		Depart depart = new Depart();
		depart.setId(departId);
		DepartService departService = (DepartService)RMIClient.getRemoteService(DepartService.class);
		try {
			depart = (Depart) departService.get(depart);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误 
			return pro;
		}
		if (depart == null) {
			pro.put("status", 6); // 参数departId对应的数据不存在
			return pro;
		}
		
		Role role = new Role();
		role.setId(roleId);
		RoleService roleService = (RoleService)RMIClient.getRemoteService(RoleService.class);
		try {
			role = (Role) roleService.get(role);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误 
			return pro;
		}
		if (role == null) {
			pro.put("status", 7); // 参数roleId对应的数据不存在
			return pro;
		}
		
		User user = new User();
		
		UserService userService = (UserService)RMIClient.getRemoteService(UserService.class);
		
		if (userId == null) {
			user.setUserName(userName);
			try {
				user = (User) userService.get(user);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				pro.put("status", 1); // 服务器内部错误 
				return pro;
			}
			if (user != null) {
				pro.put("status", 4); // 用户名重复
				return pro;
			}
			user = new User();
		} else {
			user.setId(userId);
			try {
				user = (User) userService.get(user);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				pro.put("status", 1); // 服务器内部错误
				return pro;
			}
			
			if (user == null) {
				pro.put("status", 3); // 参数userId对应的数据不存在
				return pro;
			}
		}

		user.setUserName(userName);
		user.setRealName(realName);
		user.setPassword(password);
		user.setDepartId(departId);
		user.setRoleId(roleId);
		user.setGender(gender);
		user.setPicUrl(picUrl);
		user.setUserState(userState);
		try {
			userService.saveOrUpdate(user);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		
		pro.put("status", 0);
		return pro;
	}
	
	/**
	 * 删除用户
	 * @url /user/delete
	 * @param request
	 * @return json
	 * {
	 *   status : 服务状态码
	 * }
	 * @服务状态码
	 * {
	 *   0 成功 
	 *   1 服务器内部错误 
	 *   2 参数userId不能为空 
	 *   3 参数userId对应的数据不存在 
	 * }
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/delete")
	public Object delete(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();
		
		Integer userId = StringUtils.isBlank(request.getParameter("userId")) ? null : Integer.parseInt(request.getParameter("userId"));
		if (userId == null) {
			pro.put("status", 2); // 参数userId不能为空
			return pro;
		}
		
		UserService userService = (UserService)RMIClient.getRemoteService(UserService.class);
		User user = new User();
		user.setId(userId);		
		try {
			user = (User) userService.get(user);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		if (user == null) {
			pro.put("status", 3); // 参数userId对应的数据不存在
			return pro;
		}
		
		try {
			userService.delete(user);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		
		pro.put("status", 0);
		return pro;
	}
	
	/**
	 * 用户详情
	 * @url /user/get
	 * @param request
	 * @return json
	 * {
	 *   "status": 服务状态码
	 *   "user": {
     *  	"id": 1,
     *  	"userName": "yangjun",
     *		"realName": "杨俊",
     *		"password": "123",
     *		"departId": 4,
     *		"roleId": 3,
     *		"gender": 0,
     *		"userState": 0,
     *		"picUrl": "http://www.baidu.com"
	 *   }
	 * }
	 * @服务状态码
	 * {
	 *   0 成功 
	 *   1 服务器内部错误 
	 *   2 参数userId不能为空 
	 *   3 参数userId对应的数据不存在 
	 * }
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/get")
	public Object get(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();
		
		Integer userId = StringUtils.isBlank(request.getParameter("userId")) ? null : Integer.parseInt(request.getParameter("userId"));
		if (userId == null) {
			pro.put("status", 2); // 参数userId不能为空
			return pro;
		}
		
		UserService userService = (UserService)RMIClient.getRemoteService(UserService.class);
		User user = new User();
		user.setId(userId);		
		try {
			user = (User) userService.get(user);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			pro.put("status", 1); // 服务器内部错误
			return pro;
		}
		if (user == null) {
			pro.put("status", 3); // 参数userId对应的数据不存在
			return pro;
		}
		pro.put("user", user);
		
		pro.put("status", 0);
		return pro;
	}
	
}
