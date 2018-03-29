package org.yjcycc.office.mapper;

import java.util.List;
import java.util.Map;

import org.yjcycc.office.common.entity.MyNote;

public interface MyNoteMapper {

	void save(MyNote myNote);
	
	MyNote findById(Integer noteId);
	
	List<MyNote> findPager(Map<String,Object> map);
	
}
