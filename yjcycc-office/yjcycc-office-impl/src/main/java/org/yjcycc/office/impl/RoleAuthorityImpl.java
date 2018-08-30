package org.yjcycc.office.impl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjcycc.office.api.RoleAuthorityService;
import org.yjcycc.office.common.entity.RoleAuthority;
import org.yjcycc.office.mapper.RoleAuthorityMapper;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;

@Service("roleAuthorityService")
public class RoleAuthorityImpl extends BaseServiceImpl<RoleAuthority> implements RoleAuthorityService {

	@Autowired
	private RoleAuthorityMapper roleAuthorityMapper;
	
	@Autowired
	private void initMapper() {
		setBaseMapper(roleAuthorityMapper);
	}
	
	@Override
	public List<RoleAuthority> findAllList(Map<String,Object> map) throws RemoteException {
		return roleAuthorityMapper.findAllList(map);
	}

	@Override
	public List<String> findAuthorities(Map<String, Object> map) throws RemoteException {
		return roleAuthorityMapper.findAuthorities(map);
	}
	
}
