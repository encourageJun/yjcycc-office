package org.yjcycc.office.impl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjcycc.office.api.BranchService;
import org.yjcycc.office.common.entity.Branch;
import org.yjcycc.office.mapper.BranchMapper;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service("branchService")
public class BranchServiceImpl implements BranchService {
	
	@Override
	public Page<Branch> findPager(Map<String,Object> map, int pageNum, int pageSize) throws RemoteException {
		PageHelper.startPage(pageNum, pageSize);
		List<Branch> branchList = branchMapper.findPagerByMap(map);
		
		if (branchList == null) {
			return null;
		}
		
		return (Page<Branch>)branchList;
	}

	@Autowired
	private BranchMapper branchMapper;
	
}
