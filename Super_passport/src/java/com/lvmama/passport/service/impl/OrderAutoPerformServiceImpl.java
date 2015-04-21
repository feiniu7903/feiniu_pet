package com.lvmama.passport.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.pass.OrderAutoPerformService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.passport.processor.OrderPerformProcessor;
import com.lvmama.passport.processor.ProcessorFactory;
import com.lvmama.passport.processor.UsedCodeProcessor;

/**
 * 通关产品自动履行服务
 * @author lipengcheng
 *
 */
public class OrderAutoPerformServiceImpl implements OrderAutoPerformService{
	private PassCodeService passCodeService;
	private UsedCodeProcessor usedCodeProcessor;
	private Log log = LogFactory.getLog(OrderAutoPerformServiceImpl.class);
	/**
	 * 通关产品订单自动履行方法
	 */
	public void autoPerform() {
		Map<String,Object> params = new HashMap<String,Object>();
		long pageNum = 1;
		long pageSize = 200;
		int totalChecked = 0;
		
		params.put("currentPage", pageNum);
		params.put("pageSize", pageSize);
		
		Page<PassCode> passCodePage = passCodeService.selectPassCodeAutoPerform(params);
		List<PassCode> passCodeList = passCodePage.getItems();
		long totalPage = passCodePage.getTotalPages();
		
		log.info("order auto perform: page num="+pageNum+", page size="+pageSize+", current page results="+passCodeList.size());
		do {
			pageNum ++; //页数先加1，以防死循环
			if(passCodeList!=null && !passCodeList.isEmpty()){
				totalChecked += passCodeList.size();
				
				log.info("order auto perform: first code_id="+passCodeList.get(0).getCodeId());
				doPerform(passCodeList);
				
				params.put("currentPage", pageNum);
				
				passCodePage = passCodeService.selectPassCodeAutoPerform(params);
				passCodeList = passCodePage.getItems();
				log.info("order auto perform: page num="+pageNum+", page size="+pageSize+", current page results="+passCodeList.size());
			}
		} while ( pageNum<=totalPage );
		
		log.info("order auto perform endpoint, "+totalChecked+" results has been checked. ");
	}
	
	//修复通关产品订单自动履行数据方法
	public void autoPerform(List<PassCode> passCodeList){
		if(passCodeList!=null && !passCodeList.isEmpty()){
			doPerform(passCodeList);
		}
	}
	
	/**
	 * 执行自动履行
	 * @param passCodeList
	 */
	private void doPerform(List<PassCode> passCodeList) {
		for (PassCode passCode : passCodeList) {
			if (passCode != null) {
				List<PassPortCode> passPortList = this.passCodeService.queryProviderByCode(passCode.getCodeId());
				passCode.setPassPortList(passPortList);
				
				OrderPerformProcessor orderPerform = null;
				try {
					orderPerform = (OrderPerformProcessor) ProcessorFactory.create(passCode);
					if (orderPerform != null) {
						Passport passport = orderPerform.perform(passCode);
						if (passport != null) {
							Long targetId = passCode.getPassPortList().get(0).getTargetId();
							passport.setPortId(targetId);
							passport.setOutPortId(targetId.toString());
							passport.setSerialno(passCode.getSerialNo());
							String code = usedCodeProcessor.update(passport);
							log.info("passCode: " + passCode.getCodeId() + "AutoUpdatePerformStatus: " + code);
						}
					}
				} catch (Exception e) {
					log.error("AutoPerform Exception:" , e);
				}

			}
		}
	}
	 
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setUsedCodeProcessor(UsedCodeProcessor usedCodeProcessor) {
		this.usedCodeProcessor = usedCodeProcessor;
	}
	
}
