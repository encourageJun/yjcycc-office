package org.yjcycc.office.web.controller;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
		
		int pageNum = StringUtils.isBlank(request.getParameter("pageNum")) ? 1 : Integer.parseInt(request.getParameter("pageNum"));
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 1 : Integer.parseInt(request.getParameter("pageSize"));
		
		BranchService branchService = (BranchService)RMIClient.getRemoteService(BranchService.class);
		
		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("branchId", branchId);
		try {
			Pager<Branch> pager = branchService.findPager(map, pageNum, pageSize);
			pro.put("pager", pager);
			
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}
		
		return pro;
	}
	
}
