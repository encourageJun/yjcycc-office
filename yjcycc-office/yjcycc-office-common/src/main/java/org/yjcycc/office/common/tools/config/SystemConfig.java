package org.yjcycc.office.common.tools.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 系统配置管理信息； 相关参数
 * @author Rosun
 *
 */
public class SystemConfig {

	private static final SystemConfig instance = new SystemConfig();
 
	private static final String SYS_CONFIG_FILE = "system.conf.properties";
	 
	
	private Properties prop = new Properties();

	/** 初始化 */
	private SystemConfig() {
		InputStream is = null;
		try{
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(SYS_CONFIG_FILE);
			prop.load(is);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException("SystemConfig 配置异常：",ex);
		} finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static final SystemConfig getInstance(){
		return instance;
	}
	
 
	public String getPropWithoutEnv(String key){
		return prop.getProperty(key);
	}
	
	/**
	 * 是否启用日志收集功能？
	 * @return
	 */
	public Boolean isEnabledLogger(){
//		logger.enable=false
		String ena = getPropWithoutEnv("logger.enable");
		if(ena == null){
			return false;
		}
		Boolean b = Boolean.parseBoolean(ena);
		return b;
	} 
	
	/**
	 * 是否启用mongoDB 来记录动作业务数据？
	 * @return
	 */
	public Boolean isEnabledMongoDB(){
//		mongodb.enable=false
		String ena = getPropWithoutEnv("mongodb.enable");
		if(ena == null){
			return false;
		}
		Boolean b = Boolean.parseBoolean(ena);
		return b;
	} 
	
	/**
	 * 获取接收dlq邮件的email地址
	 * @return
	 */
	public static String getDlqEmails() {
		String emails = "";
		SystemConfig m = SystemConfig.getInstance();
		emails = m.getPropWithoutEnv("dlq.mail.emails." + SystemConfig.getEnviroment());
		return emails;
	}
	
	/**
	 * 获取接收dlq短信的手机号码
	 * @return
	 */
	public static String getDlqMobiles() {
		String mobiles = "";
		SystemConfig m = SystemConfig.getInstance();
		mobiles = m.getPropWithoutEnv("dlq.sms.mobiles." + SystemConfig.getEnviroment());
		return mobiles;
	}
	
	/**
	 * 获取dlq邮件开关
	 * @return
	 */
	public static String getDlqOpenMailStatus() {
		String openMailStatus = "";
		SystemConfig m = SystemConfig.getInstance();
		openMailStatus = m.getPropWithoutEnv("dlq.openmail.status." + SystemConfig.getEnviroment());
		return openMailStatus;
	}
	
	/**
	 * 获取dlq短信开关
	 * @return
	 */
	public static String getDlqOpenSmsStatus() {
		String openSmsStatus = "";
		SystemConfig m = SystemConfig.getInstance();
		openSmsStatus = m.getPropWithoutEnv("dlq.opensms.status." + SystemConfig.getEnviroment());
		return openSmsStatus;
	}

	
	/**
	 * 获取环境；
	 * 
	 * @return  返回  prod | devp | test | uat 4者之一
	 */
	public static String getEnviroment(){
		SystemConfig m = SystemConfig.getInstance();
		String env = m.getPropWithoutEnv("env");
		if(env == null || "".equalsIgnoreCase(env)){
			throw new IllegalStateException("当前环境未指定");
		}
		if(!env.equals("prod") && !env.equals("devp") && !env.equals("test") && !env.equals("uat")){
			throw new IllegalStateException("env 不是期待的值。  " + env + ", 期待 prod | devp | test | uat 四者之一");
		}
		return env;
	}
}
