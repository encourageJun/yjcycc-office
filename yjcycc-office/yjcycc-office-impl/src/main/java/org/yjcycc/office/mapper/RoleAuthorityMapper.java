package org.yjcycc.office.mapper;

import java.util.List;
import java.util.Map;

import org.yjcycc.office.common.entity.RoleAuthority;
import org.yjcycc.tools.common.mapper.MyBatisBaseMapper;

public interface RoleAuthorityMapper extends MyBatisBaseMapper<RoleAuthority> {

	List<RoleAuthority> findAllList(Map<String,Object> map);
	
	List<String> findAuthorities(Map<String, Object> map);
	
}
