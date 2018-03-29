package org.yjcycc.office.impl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjcycc.office.api.DepartService;
import org.yjcycc.office.common.entity.Depart;
import org.yjcycc.office.mapper.DepartMapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service("departService")
public class DepartServiceImpl implements DepartService {
	
	@Override
	public Page<Depart> findPager(Map<String,Object> map, int pageNum, int pageSize) throws RemoteException {
		PageHelper.startPage(pageNum, pageSize);
		List<Depart> branchList = departMapper.findPagerByMap(map);
		
		if (branchList == null) {
			return null;
		}
		
		return (Page<Depart>)branchList;
	}

	@Autowired
	private DepartMapper departMapper;
	
}
