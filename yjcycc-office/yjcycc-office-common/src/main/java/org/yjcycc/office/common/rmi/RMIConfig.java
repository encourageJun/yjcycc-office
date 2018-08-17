package org.yjcycc.office.common.rmi;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yjcycc.office.common.constant.ZkNodeConstant;
import org.yjcycc.office.common.tools.config.SystemConfig;

public class RMIConfig {

	private static final Logger logger = LoggerFactory.getLogger(RMIConfig.class);
	
	/**
	 * RMI超时连接时间 60秒 
	 */
	public static final int RMI_TIMEOUT_CONNECT = 60*1000; 

	/**
	 * key 服务接口名的List，Value 对应的Zookeeper路径（决定了是那一类服务）
	 */
	private static Map<List<String>, String> servicePathMap = new HashMap<List<String>, String>();
	
	/**
	 * 兼容旧的客户端，luoshan 临时开关。用class的simplename或者长名称都能得到远程服务;不日作废。
	 */
	public static final boolean JIANRONG_JIU_KEHUDUAN = true;
	
	static{
		System.setProperty("sun.rmi.transport.tcp.responseTimeout", "60000"); 
		System.setProperty("sun.rmi.transport.tcp.readTimeout", "2000"); 
		System.setProperty("sun.rmi.transport.proxy.connectTimeout", "2000"); 
		System.setProperty("sun.rmi.transport.tcp.handshakeTimeout", "1000");
		
		//所有的远程服务，需要在这里先声明一下，消费者才能拿到这些服务。
		if(JIANRONG_JIU_KEHUDUAN){
			logger.warn("\n\n目前采用的是兼容旧客户端的模式，使用class的simplename或者长名称都能得到远程服务 @luoshan。\n\n");
		}
		
		// office服务
		List<String> office = new ArrayList<String>();
		office.add("org.yjcycc.office.api.BranchService");
		office.add("org.yjcycc.office.api.DepartService");
		office.add("org.yjcycc.office.api.MyNoteService");
		office.add("org.yjcycc.office.api.UserService");
		office.add("org.yjcycc.office.api.RoleService");
		office.add("org.yjcycc.office.api.FileInfoService");
		office.add("org.yjcycc.office.api.RoleAuthorityService");
		servicePathMap.put(office, ZkNodeConstant.BASE_OFFICE_PATH);
		
		if("devp".equals(SystemConfig.getEnviroment())){
			boolean xx = checkServicePathMap();
			logger.info("#### checkServicePathMap :" + xx);
			//戳错了 不要抛异常，有可能是正常的哈！！！！classpath下找不到而已！
			if(xx){
				logger.info("\r\nRMIConfig 远程接口注册OK！恭喜你！");
			}else{
				logger.warn("\r\nRMIConfig 远程接口注册可能有错误，部分声明的service可能不在classpath下或者接口名有错误。");
			}
		}
		//20170615  开发环境检查远程接口注册 看到warn 不要怕
	}
	
	public static Map<List<String>, String> getServicePathMap() {
		return servicePathMap;
	}

	@SuppressWarnings({ "rawtypes" })
	public static boolean checkServicePathMap(){
		boolean flag = true;
		try{
			for(Map.Entry<List<String>, String> entry : servicePathMap.entrySet()){
				List<String> servicesList = entry.getKey();
				for(String regService : servicesList){
					if(!regService.contains(".")){
						continue;
					}
					try{
						Class clazz = Class.forName(regService);
						boolean issub = Remote.class.isAssignableFrom(clazz);
						if(issub){
							logger.info("CHECKED OK : " + clazz); 
						}else{
							logger.error("CHECK ERROR :" + regService);
							flag = false;
						}
					}catch(Exception ex){
						logger.error("CHECK ERROR :" + regService);
						flag = false;
					}
				}
			}
		}catch(Exception ex){
			logger.warn("CHECK ERROR when checkServicePathMap:" + ex.getMessage());
			flag = false;
		}
		return flag;
	}
	
}
