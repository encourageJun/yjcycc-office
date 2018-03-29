package org.yjcycc.office.common.constant;

import java.nio.charset.Charset;

import org.yjcycc.office.common.tools.config.SystemConfig;

public class ZkNodeConstant {

	public static Charset CHARSET = Charset.forName("utf-8");
	
	public static final String NAME_SPACE = "yjcycc/" + SystemConfig.getEnviroment(); 

	public static final String BASE_OFFICE_PATH = "/office"; 
	
}
