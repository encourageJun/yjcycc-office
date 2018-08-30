package org.yjcycc.office.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yjcycc.office.api.FileInfoService;
import org.yjcycc.office.common.entity.FileInfo;
import org.yjcycc.office.mapper.FileInfoMapper;
import org.yjcycc.tools.common.service.impl.BaseServiceImpl;

@Service("fileInfoService")
public class FileInfoServiceImpl extends BaseServiceImpl<FileInfo> implements FileInfoService {

	@Autowired
	private FileInfoMapper fileInfoMapper;
	
	@Autowired
	private void initMapper() {
		setBaseMapper(fileInfoMapper);
	}
	
}
