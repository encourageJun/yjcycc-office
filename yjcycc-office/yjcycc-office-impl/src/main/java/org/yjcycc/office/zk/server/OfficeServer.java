package org.yjcycc.office.zk.server;

import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.rmi.server.ExportException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;
import org.yjcycc.office.api.BranchService;
import org.yjcycc.office.api.DepartService;
import org.yjcycc.office.api.MyNoteService;
import org.yjcycc.office.common.constant.ZkNodeConstant;
import org.yjcycc.office.common.tools.config.OfficeRmiConfig;
import org.yjcycc.office.common.tools.config.SystemConfig;
import org.yjcycc.office.common.tools.config.ToolsConfig;
import org.yjcycc.tools.common.util.SystemUtil;
import org.yjcycc.tools.zk.model.ProducerConsumerEnum;
import org.yjcycc.tools.zk.model.UsingIpPort;
import org.yjcycc.tools.zk.model.XxNode;
import org.yjcycc.tools.zk.server.AbstractZookeeperClientRegister;
import org.yjcycc.tools.zk.server.rmi.RMIRegister;

@Component
public class OfficeServer extends AbstractZookeeperClientRegister implements Lifecycle, ApplicationContextAware ,InitializingBean {
	
	private static Logger logger = Logger.getLogger(OfficeServer.class);
	
	private static ApplicationContext applicationContext = null;
	
	private transient boolean isRun = false;
	
	private UsingIpPort usingIpPort  = null;
	
	private int port = 0;
	
	@Override
	public String getNameSpaceForZK() {
		return ZkNodeConstant.NAME_SPACE;
	}

	@Override
	public String getNodePathForZk() {
		return ZkNodeConstant.BASE_OFFICE_PATH;
	}

	@Override
	public String getConnectUrlForZk() {
		return ToolsConfig.getInstance().getZookeeperConnUrl();
	}

	@Override
	public Charset getCharSetForZk() {
		return ZkNodeConstant.CHARSET;
	}

	@Override
	@PostConstruct
	public void start() {
		/**
		 * 是否使用某个自定的配置来启动，多节点分多主机部署时需要；单主机时不要配置。
		 */
		String specifyIpPortIndex = OfficeRmiConfig.getInstance().getSpecifyIpPortIndex();
		if(specifyIpPortIndex == null || "".equals(specifyIpPortIndex.trim())){
			//按照升序 读取配置文件 启动。。。。
			for(int index = 0; index < OfficeRmiConfig.MAX_NODES; index ++){
				try {
					registRmiService(index);
					initUsingIpPort();
					isRun = true;
					logger.info("OrderServer "+index+" started ok!");
					return ;
				} catch(ExportException e0){
					logger.error(e0);
					isRun = false;
				} catch (RemoteException e3) {
					logger.error(e3);
					e3.printStackTrace();
					isRun = false;
				}
			}
			logger.error("OrderServer 启动遇到问题，"+OfficeRmiConfig.MAX_NODES+"套配置没有一套有效的.....");
		}else{
			logger.info("系统配置 指定按照第 ["+specifyIpPortIndex+"] 套配置来启动：");
			try {
				int index = Integer.parseInt(specifyIpPortIndex);
				registRmiService(index);
				initUsingIpPort();
				isRun = true;
				logger.info("OrderServer "+index+" started ok!");
				return ;
			} catch(ExportException e0){
				isRun = false;
			} catch (RemoteException e3) {
				logger.error(e3);
				e3.printStackTrace();
				isRun = false;
			}
		}
	}
	
	/**
	 * 注册rmi服务
	 * @param index
	 * @throws RemoteException
	 * @throws ExportException
	 */
	private void registRmiService(int index) throws RemoteException ,ExportException {
		if(index == 0){
			//如果是重试模式，加载第0套配置
			port = OfficeRmiConfig.getInstance().getPort0();
		}else if(index == 1){
			//如果非重试模式，加载第1套配置
			port = OfficeRmiConfig.getInstance().getPort1();
		} else if(index == 2){
			//如果非重试模式，加载第2套配置
			port = OfficeRmiConfig.getInstance().getPort2();
		}  else{
			throw new IllegalArgumentException("只支持0，1，2，共3套配置！不支持：" + index);
		}
		
		// 曝露服务
		RMIRegister register = new RMIRegister(port);
		register.regist(MyNoteService.class, applicationContext.getBean(MyNoteService.class));
		register.regist(BranchService.class, applicationContext.getBean(BranchService.class));
		register.regist(DepartService.class, applicationContext.getBean(DepartService.class));
		
	}
	
	/**
	 * 初始化uip节点信息
	 */
	private void initUsingIpPort() {
		String serverIp = SystemUtil.getHostAddress();
		int pid = SystemUtil.getPid();
		usingIpPort = new UsingIpPort(serverIp, port, pid);
		
		// 维稳监控
		System.setProperty("java.rmi.server.hostname" , serverIp);
	}

	/**
	 * 创建Zookeeper 节点树，声明当前结点是 主节点；
	 * @throws Exception 
	 */
	public boolean createTreeNode() throws Exception {
		XxNode node = new XxNode();
		node.setPcMode(ProducerConsumerEnum.PRODUCER);
		node.setEnvriment(SystemConfig.getEnviroment());
		node.setName("商品服务");
		node.setUip(this.usingIpPort);
		//luoshan 20170503 - 灰度与流量路由
		boolean result = createTreeNode(node);
		//boolean result = zkClient.createTreeNode(this.using);		
		return result;
	}

	@Override
	@PreDestroy
	public void stop() {
		isRun = false;
	}

	@Override
	public boolean isRunning() {
		return isRun;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(applicationContext == null){
			logger.error("appContext not awared...... error");
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		applicationContext = appContext;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public UsingIpPort getUsingIpPort() {
		return usingIpPort;
	}

	public void setUsingIpPort(UsingIpPort usingIpPort) {
		this.usingIpPort = usingIpPort;
	}
	
}
