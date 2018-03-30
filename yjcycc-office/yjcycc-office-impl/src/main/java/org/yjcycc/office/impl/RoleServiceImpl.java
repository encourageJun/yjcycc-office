package org.yjcycc.office.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjcycc.office.api.RoleService;
import org.yjcycc.office.common.entity.Role;
import org.yjcycc.office.mapper.RoleMapper;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private void initMapper() {
		setBaseMapper(roleMapper);
	}
	
}
