package org.yjcycc.office.web.shiro.realm;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.yjcycc.office.api.RoleAuthorityService;
import org.yjcycc.office.api.UserService;
import org.yjcycc.office.common.entity.User;
import org.yjcycc.office.common.rmi.RMIClient;

public class UserRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		
		String userName = (String) principalCollection.getPrimaryPrincipal();
		
		UserService<User> userService = (UserService) RMIClient.getRemoteService(UserService.class);
		User user = new User();
		user.setUserName(userName);
		try {
			user = userService.get(user);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		RoleAuthorityService roleAuthorityService = (RoleAuthorityService) RMIClient.getRemoteService(RoleAuthorityService.class);
		
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("roleId", user.getRoleId());
			List<String> permissionList = roleAuthorityService.findAuthorities(null);
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//			info.setStringPermissions(permissionList);
//			info.setRoles(roles);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

}
