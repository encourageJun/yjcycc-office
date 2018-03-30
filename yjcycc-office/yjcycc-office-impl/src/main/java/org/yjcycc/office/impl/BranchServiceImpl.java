package org.yjcycc.office.impl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjcycc.office.api.BranchService;
import org.yjcycc.office.common.entity.Branch;
import org.yjcycc.office.common.util.PagerUtil;
import org.yjcycc.office.mapper.BranchMapper;
import org.yjcycc.tools.common.Pager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("branchService")
public class BranchServiceImpl implements BranchService {
	
	@Override
	public Pager<Branch> findPager(Map<String,Object> map, int pageNum, int pageSize) throws RemoteException {
		PageHelper.startPage(pageNum, pageSize);
		List<Branch> branchList = branchMapper.findPagerByMap(map);
		
		if (branchList == null) {
			return null;
		}
		
		return PagerUtil.getPager(new PageInfo<Branch>(branchList));
	}
	
	@Override
	public void saveOrUpdate(Branch branch) throws RemoteException {
		if (branch.getBranchId() == null || branch.getBranchId() == 0) {
			branchMapper.insert(branch);
		} else {
			branchMapper.update(branch);
		}
	}
	
	@Override
	public Branch get(Branch branch) throws RemoteException {
		return branchMapper.get(branch);
	}
	
	@Override
	public void delete(Branch branch) throws RemoteException {
		branchMapper.delete(branch);
	}

	@Autowired
	private BranchMapper branchMapper;
	
}
