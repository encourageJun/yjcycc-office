package org.yjcycc.office.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import org.yjcycc.office.common.entity.Branch;
import org.yjcycc.tools.common.Pager;

public interface BranchService extends Remote {

	Pager<Branch> findPager(Map<String,Object> map, int pageNum, int pageSize) throws RemoteException;
	
	void saveOrUpdate(Branch branch) throws RemoteException;
	
	Branch get(Branch branch) throws RemoteException;
	
	void delete(Branch branch) throws RemoteException;
	
}
