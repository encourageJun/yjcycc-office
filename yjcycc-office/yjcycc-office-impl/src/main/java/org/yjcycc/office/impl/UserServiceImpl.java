package org.yjcycc.office.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjcycc.office.api.UserService;
import org.yjcycc.office.common.entity.User;
import org.yjcycc.office.mapper.UserMapper;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;

@Service("userService")
public class UserServiceImpl<T extends User> extends BaseServiceImpl<User> implements UserService<User> {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private void initMapper() {
		setBaseMapper(userMapper);
	}
	
}
