package org.yjcycc.office.mapper;

import java.util.List;
import java.util.Map;

import org.yjcycc.office.common.entity.Branch;
import org.yjcycc.tools.common.mapper.MyBatisBaseMapper;

public interface BranchMapper extends MyBatisBaseMapper<Branch> {

	Branch getByMap(Map<String,Object> map);

	List<Branch> findPagerByMap(Map<String,Object> map);
	
	int updateByMap(Map<String,Object> map);
	
	int batchDelete(String ids);
	
}
