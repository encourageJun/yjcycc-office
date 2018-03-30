package org.yjcycc.office.impl;

import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.yjcycc.office.api.BaseService;
import org.yjcycc.office.common.util.PagerUtil;
import org.yjcycc.tools.common.Pager;
import org.yjcycc.tools.common.mapper.MyBatisBaseMapper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class BaseServiceImpl<T> implements BaseService<T> {
	
	protected MyBatisBaseMapper<T> baseMapper;
	
	public void setBaseMapper(MyBatisBaseMapper<T> baseMapper) {
		this.baseMapper = baseMapper;
	}

//	@Override
	public Pager<T> findPager(Map<String, Object> map, int pageNum, int pageSize) throws RemoteException {
		PageHelper.startPage(pageNum, pageSize);
		List<T> list = baseMapper.findPagerByMap(map);
		
		if (list == null) {
			return null;
		}
		
		return PagerUtil.getPager(new PageInfo<T>(list));
	}

//	@Override
	public T get(T entity) throws RemoteException {
		return baseMapper.get(entity);
	}

//	@Override
	public void delete(T entity) throws RemoteException {
		baseMapper.delete(entity);
	}
	
	public void saveOrUpdate(T entity) throws RemoteException {
		try {
			Field field = entity.getClass().getField("id");
			Integer id = (Integer) field.get(entity);
			if (id == null || id == 0) {
				baseMapper.insert(entity);
			} else {
				baseMapper.update(entity);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

}
