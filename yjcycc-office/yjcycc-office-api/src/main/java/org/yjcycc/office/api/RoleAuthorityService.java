package org.yjcycc.office.api;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.yjcycc.office.common.entity.RoleAuthority;

public interface RoleAuthorityService extends BaseService<RoleAuthority> {

	List<RoleAuthority> findAllList(Map<String,Object> map) throws RemoteException;
	
	List<String> findAuthorities(Map<String,Object> map) throws RemoteException;
	
}
