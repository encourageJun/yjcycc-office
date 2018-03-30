package org.yjcycc.office.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjcycc.office.api.DepartService;
import org.yjcycc.office.common.entity.Depart;
import org.yjcycc.office.mapper.DepartMapper;

@Service("departService")
public class DepartServiceImpl<T extends Depart> extends BaseServiceImpl<Depart> implements DepartService<Depart>{
	
	/*@SuppressWarnings("unchecked")
	@Override
	public void saveOrUpdate(Depart depart) throws RemoteException {
		if (depart.getDepartId() == null || depart.getDepartId() == 0) {
			departMapper.insert(depart);
		} else {
			departMapper.update(depart);
		}
	}*/
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private DepartMapper departMapper;
	
	@SuppressWarnings("unchecked")
	@Autowired
	public void initBaseMapper() {
		setBaseMapper(departMapper);
	}
	
}
