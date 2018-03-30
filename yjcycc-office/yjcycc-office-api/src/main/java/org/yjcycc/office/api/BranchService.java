package org.yjcycc.office.api;

import java.rmi.RemoteException;

import org.yjcycc.office.common.entity.Branch;

public interface BranchService extends BaseService<Branch> {

	void saveOrUpdate(Branch branch) throws RemoteException;
	
}
