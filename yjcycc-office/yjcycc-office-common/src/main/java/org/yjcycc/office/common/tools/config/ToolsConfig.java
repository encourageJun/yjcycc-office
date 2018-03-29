package org.yjcycc.office.common.tools.config;

import java.util.Properties;

import org.apache.log4j.Logger;

public class ToolsConfig {

	private static Logger logger = Logger.getLogger(ToolsConfig.class);

	private static final ToolsConfig instance = new ToolsConfig();

	private static final String RMI_CONFIG_FILE = "tools.properties";

	/**
	 * activemq 链接url
	 */
	private String activemqJmsBrokerUrl;

	/**
	 * zookeeper 集群 服务链接
	 */
	private String zookeeperConnUrl;

	/**
	 * mongo用户名
	 */
	private String mongoUsername;
	/**
	 * mongo密码
	 */
	private String mongoPassword;
	/**
	 * mongo服务器ip地址
	 */
	private String mongoServerIp;
	/**
	 * mongo服务器端口号
	 */
	private int mongoServerPort;
	
	/** 初始化 */
	private ToolsConfig() {
		try {
			Properties prop = new Properties();
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(RMI_CONFIG_FILE));
			String dotenv = "." + SystemConfig.getEnviroment();

			activemqJmsBrokerUrl = (prop.getProperty("activemq.jms.broker.url" + dotenv));

			zookeeperConnUrl = prop.getProperty("zookeeper.url" + dotenv);

			setMongoUsername(prop.getProperty("mongo.username" + dotenv));
			setMongoPassword(prop.getProperty("mongo.password" + dotenv));
			setMongoServerIp(prop.getProperty("mongo.server.ip" + dotenv));
			setMongoServerPort(Integer.parseInt(prop.getProperty("mongo.server.port" + dotenv)));

			logger.info(">>>>>>>>>>>>>>>>> jmsBrokerUrl:" + activemqJmsBrokerUrl);
			logger.info(">>>>>>>>>>>>>>>>> zookeeperConnUrl:" + zookeeperConnUrl);
			logger.info(">>>>>>>>>>>>>>>>> mongo:ip=" + getMongoServerIp() + ", port=" + getMongoServerPort()
					+ ", username=" + getMongoUsername() + ", password=" + getMongoPassword());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("RemoteConfig 配置异常：", e);
		}
	}
	
	public static final ToolsConfig getInstance() {
		return instance;
	}

	public String getZookeeperConnUrl() {
		return zookeeperConnUrl;
	}

	public void setZookeeperConnUrl(String zookeeperConnUrl) {
		this.zookeeperConnUrl = zookeeperConnUrl;
	}

	public String getMongoUsername() {
		return mongoUsername;
	}

	public void setMongoUsername(String mongoUsername) {
		this.mongoUsername = mongoUsername;
	}

	public String getMongoPassword() {
		return mongoPassword;
	}

	public void setMongoPassword(String mongoPassword) {
		this.mongoPassword = mongoPassword;
	}

	public String getMongoServerIp() {
		return mongoServerIp;
	}

	public void setMongoServerIp(String mongoServerIp) {
		this.mongoServerIp = mongoServerIp;
	}

	public int getMongoServerPort() {
		return mongoServerPort;
	}

	public void setMongoServerPort(int mongoServerPort) {
		this.mongoServerPort = mongoServerPort;
	}

	public String getActivemqJmsBrokerUrl() {
		return activemqJmsBrokerUrl;
	}

	public void setActivemqJmsBrokerUrl(String activemqJmsBrokerUrl) {
		this.activemqJmsBrokerUrl = activemqJmsBrokerUrl;
	}

}
