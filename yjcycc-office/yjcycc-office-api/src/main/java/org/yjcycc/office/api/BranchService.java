package org.yjcycc.office.api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import org.yjcycc.office.common.entity.Branch;

import com.github.pagehelper.Page;

public interface BranchService extends Remote {

	Page<Branch> findPager(Map<String,Object> map, int pageNum, int pageSize) throws RemoteException;
	
}
