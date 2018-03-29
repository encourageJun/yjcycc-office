package org.yjcycc.office.mapper;

import java.util.List;
import java.util.Map;

import org.yjcycc.office.common.entity.Depart;
import org.yjcycc.tools.common.mapper.MyBatisBaseMapper;

public interface DepartMapper extends MyBatisBaseMapper<Depart> {

	Depart getByMap(Map<String,Object> map);

	List<Depart> findPagerByMap(Map<String,Object> map);
	
	int updateByMap(Map<String,Object> map);
	
	int batchDelete(String ids);
	
}
