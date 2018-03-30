package org.yjcycc.office.api;

import java.rmi.RemoteException;

import org.yjcycc.office.common.entity.Depart;

public interface DepartService<T extends Depart> extends BaseService<Depart> {

	void saveOrUpdate(Depart depart) throws RemoteException;
	
}
