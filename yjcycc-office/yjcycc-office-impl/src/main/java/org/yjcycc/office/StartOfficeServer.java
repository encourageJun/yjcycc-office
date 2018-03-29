package org.yjcycc.office;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yjcycc.office.common.tools.config.SystemConfig;
import org.yjcycc.office.common.tools.config.ToolsConfig;
import org.yjcycc.office.zk.server.OfficeServer;

public class StartOfficeServer {

	private static Logger logger = Logger.getLogger(StartOfficeServer.class);
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = null;
		try {
			logger.info("Lanch OfficeServer...  classpath:/spring/spring-context.xml");
			ctx = new ClassPathXmlApplicationContext("classpath:/spring/spring-context.xml");
			ctx.start();
			
			OfficeServer OfficeServer = ctx.getBean(OfficeServer.class);
			
			if(OfficeServer.isRunning()){
				//不管是不是主节点  都需要往zookeeper 节点树声明一下自身：创建一个节点！
				boolean flag = OfficeServer.createTreeNode();
				
				logger.info("创建节点, 结果：" + flag);
				if(flag == false){
					logger.error("OfficeServer 创建ZK节点失败，请检查配置。导致此失败的原因是 1.ZK无法连接 ;2.ZK上已存在相关节点 ;3.ZK操作权限受限");
					logger.error("\n\n############################## OfficeServer Failed launch:##############################");
					ctx.close();
					return ;
				}
			} else {
				logger.error("OfficeServer not started well, please check OfficeServer config");
				logger.error("\n\n############################## OfficeServer Failed launch:##############################" );
				ctx.close();
				return ;
			} 
			
			logger.info("\n\n############################## OfficeServer started:##############################");
			logger.info("\n\n############################## " + OfficeServer.getUsingIpPort());	
		} catch (Exception e) {
			logger.error("\n\n############################## OfficeServer Failed launch:##############################", e);
			return;
		}
		
		logger.info("@@@@ Using ZooKeeper url : " + ToolsConfig.getInstance().getZookeeperConnUrl());
		logger.info("@@@@ Current Enviroment  : " + SystemConfig.getEnviroment());
		
		synchronized (StartOfficeServer.class) {
			while (true) {
				try {
					StartOfficeServer.class.wait();
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	
}
