package org.yjcycc.office.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjcycc.office.api.UserService;
import org.yjcycc.office.common.entity.User;
import org.yjcycc.office.mapper.UserMapper;

@Service("userService")
public class UserServiceImpl<T extends User> extends BaseServiceImpl<User> implements UserService<User> {

	@SuppressWarnings("rawtypes")
	@Autowired
	private UserMapper userMapper;
	
	@SuppressWarnings("unchecked")
	@Autowired
	private void initMapper() {
		setBaseMapper(userMapper);
	}
	
}
