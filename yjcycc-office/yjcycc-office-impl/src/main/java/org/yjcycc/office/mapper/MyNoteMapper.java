package org.yjcycc.office.mapper;

import java.util.List;
import java.util.Map;

import org.yjcycc.office.common.entity.MyNote;
import org.yjcycc.tools.common.mapper.MyBatisBaseMapper;

public interface MyNoteMapper extends MyBatisBaseMapper<MyNote> {
	
	MyNote getByMap(Map<String,Object> map);

	List<MyNote> findPagerByMap(Map<String,Object> map);
	
	int updateByMap(Map<String,Object> map);
	
	int batchDelete(String ids);
	
}
