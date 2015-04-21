package com.lvmama.clutter.job;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.clutter.service.IBaiduActivityService;
import com.lvmama.clutter.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 生成xml文件 
 * @author qinzubo
 *
 */
public class MobileBaiduXmlJob {
	private final Logger log = Logger.getLogger(this.getClass());
	protected  IBaiduActivityService baiduActivityService;
	
	public void initBaidXmlInfo() {
		log.info("....MobileBaiduXmlJob start.....");
		if (Constant.getInstance().isJobRunnable()) {
			Map<String, Object> params = new HashMap<String,Object>();
			try{
				baiduActivityService.generatorBaiduXml(params );
				log.info("....MobileBaiduXmlJob success.....");
			}catch(Exception e){
				e.printStackTrace();
				log.error("....MobileBaiduXmlJob error.....");
			}
			
		
		}
	}

	public IBaiduActivityService getBaiduActivityService() {
		return baiduActivityService;
	}

	public void setBaiduActivityService(IBaiduActivityService baiduActivityService) {
		this.baiduActivityService = baiduActivityService;
	}

}
