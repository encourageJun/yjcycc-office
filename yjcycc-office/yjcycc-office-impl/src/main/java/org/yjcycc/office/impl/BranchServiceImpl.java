package org.yjcycc.office.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjcycc.office.api.BranchService;
import org.yjcycc.office.common.entity.Branch;
import org.yjcycc.office.mapper.BranchMapper;

@Service("branchService")
public class BranchServiceImpl extends BaseServiceImpl<Branch> implements BranchService {
	
	@Autowired
	private BranchMapper branchMapper;
	
	@Autowired
	public void initBaseMapper() {
		setBaseMapper(branchMapper);
	}
	
}
