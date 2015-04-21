package com.lvmama.clutter.job;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.clutter.service.IBaiduActivityService;
import com.lvmama.clutter.utils.BaiduActivityUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.comm.vo.Constant;

/**
 * 刷新最大请求数 
 * @author qinzubo
 *
 */
public class MobileBaiduReqJob {

	private final Logger log = Logger.getLogger(this.getClass());
	protected  IBaiduActivityService baiduActivityService;
	
	/**
	 * 刷新最大请求数；
	 * 每个小时的5分15分30分45分执行一次 。
	 */
	public void initBdMaxReqAmount() {
		// 半价票request最大请求数 
		Long sandby = BaiduActivityUtils.getHourFromStartDate();
		if (Constant.getInstance().isJobRunnable()&& ClutterConstant.isOcsJobRunnable()) {
			log.info("....baidu act job initBdMaxReqAmount start.. ");
			// 半价票 - 当天可销售金额
			if(sandby>=0 && sandby < 22) {
				try{
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("opt", "13");
					params.put("sandby", sandby+"");
					baiduActivityService.only4JunitTest(params);
					log.info("....baidu act job initBdMaxReqAmount successs.. ");
				}catch(Exception e) {
					e.printStackTrace();
					log.info("......baidu act job initBdMaxReqAmount error  ");
				}
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
