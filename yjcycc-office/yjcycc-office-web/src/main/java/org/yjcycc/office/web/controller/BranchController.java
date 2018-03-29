package org.yjcycc.office.web.controller;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yjcycc.office.api.BranchService;
import org.yjcycc.office.common.entity.Branch;
import org.yjcycc.office.common.rmi.RMIClient;
import org.yjcycc.tools.common.Pager;

@RestController
@RequestMapping(value = "/branch")
public class BranchController {

	private Logger logger = Logger.getLogger(BranchController.class);
	
	@RequestMapping(value = "/list")
	public Object list(HttpServletRequest request) {
		logger.info("start");
		
		Properties pro = new Properties();
		
		BranchService branchService = (BranchService)RMIClient.getRemoteService(BranchService.class);
		
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("branchId", branchId);
		try {
			Pager<Branch> pager = branchService.findPager(map, 2, 10);
			pro.put("pager", pager);
			
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}
		
		return pro;
	}
	
}
