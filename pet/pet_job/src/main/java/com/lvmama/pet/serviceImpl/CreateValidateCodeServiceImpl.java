package com.lvmama.pet.serviceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.MemcachedSeckillUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.service.CreateValidateCodeService;

public class CreateValidateCodeServiceImpl implements CreateValidateCodeService,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3386320503007354611L;
	
	private static final Log log = LogFactory.getLog(CreateValidateCodeServiceImpl.class);
	
	private ProductSearchInfoService productSearchInfoService;
	
	@Override
	public TaskResult execute(Long logId, String parameter) throws Exception {
		log.info(" =========== CreateValidateCodeJob  is run =========== job run ? "+Constant.getInstance().isJobRunnable());

		TaskResult  result = new TaskResult();
		
		if(Constant.getInstance().isJobRunnable()){
			
			productSearchInfoService = (ProductSearchInfoService) SpringBeanProxy.getBean("productSearchInfoService");
			
			//随机取产品类型
			long isTicket = Math.round(Math.random()*3+1);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("isTicket", isTicket);
			param.put("startRows", 0);
			param.put("endRows", 1000);
			
			List<String> validateCodeList = new ArrayList<String>();
			
			List<ProductSearchInfo> searchInfoList = productSearchInfoService.queryProductSearchInfoByParam(param);
			if(searchInfoList != null){
				for (ProductSearchInfo productSearchInfo : searchInfoList) {
					String hanziString = productSearchInfo.getProductName().replaceAll("[^\u4e00-\u9fa5]","");
					if(hanziString.length() >= 4){
						validateCodeList.add(hanziString.substring(0,4));
					}
				}
			}
			MemcachedSeckillUtil.getMemCachedSeckillClient().set(Constant.SECKILL.SECKILL_VALIDATE_CODE.toString(), validateCodeList);
			
			log.info(" =========== CreateValidateCodeJob MemcachedSkill: "+ Constant.SECKILL.SECKILL_VALIDATE_CODE.toString() +"========"+ validateCodeList.size());
		}
		result.setStatus(1);
		return result;
	}
}
