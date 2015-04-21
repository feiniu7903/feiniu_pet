package com.lvmama.clutter.job;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.lvmama.clutter.service.IBaiduActivityService;
import com.lvmama.clutter.utils.BaiduActivityUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.comm.pet.po.mobile.MobileActBaidu;
import com.lvmama.comm.pet.po.mobile.MobileActBaiduOrder;
import com.lvmama.comm.pet.service.mobile.MobileClientService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 百度活动定时任务 
 * @author qinzubo
 */
public class MobileBaiduActJob {

	private final Logger log = Logger.getLogger(this.getClass());
	protected MobileClientService mobileClientService;
	
	protected  IBaiduActivityService baiduActivityService;
	
	public static String[] productIds = ClutterConstant.getBaiduActProductIdList(); // 半价票产品ids
	
	/**
	 * 初始化活动数据 到 memcached数据到缓存中 .
	 * 1, 已经预订的用户id ；4点同步一次 ；
	 * 2, 每天把实际销售数据（order表） 和  活动表（stock）对比；如果不一致的话 实际更新到活动表 
	 * 3, 销售表更新到缓存 
	 */
	public void initActMemcachedInfo() {
		if (Constant.getInstance().isJobRunnable() 
				&& ClutterConstant.isOcsJobRunnable() 
				) {
			// 半价票 - 当天可销售金额
			try{
				this.initCanBookOfDayInfo();
				log.info("....baidu act job initTotalOrderInfo successs.. ");
			}catch(Exception e) {
				e.printStackTrace();
				log.info("......baidu act job initTotalOrderInfo error  ");
			}
			
			// 立减票框外 - 当天可售金额 
			try{
				this.initCanBookOfDay4Sandby();
				log.info("....baidu act job initCanBookOfDay4Sandby successs.. ");
			}catch(Exception e) {
				e.printStackTrace();
				log.info("......baidu act job initCanBookOfDay4Sandby error  ");
			}
			
			
			
			// 这个理论上执行一次就可以了。 user_order
			/*try{
				this.initUserOrderInfo();
				log.info("....baidu act job initUserOrderInfo successs  ");
			}catch(Exception e) {
				e.printStackTrace();
				log.info("......baidu act job initUserOrderInfo error  ");
			}*/
			
			
			
			/***************************  测试 块   上线时删除 ****************************/
			 
			  // this.test();
			
			/***************************  测试 块   上线时删除 ****************************/
		}
	}
	

	/***************************  测试 块   开始 ****************************/
	public void test() {
		//this.testMecached();
		
		// 线程数据
		for(int i = 0 ;i <= 200;i++) {
			Thread thread = new Thread(new BaiduThread()); 
			log.info("...Thread......start.... " + thread.getName());
			thread.start();  
		}
		
		this.testMecached();
	}
	
	
	private void testMecached() {
		try {
			Thread.sleep(100000);
			// 打印缓存信息 
			Long sandBy = BaiduActivityUtils.getHourFromStartDate();
			for(int u = 0 ; u < noss.length;u++) {
				for(int i = 0;i < productIds.length;i++) {
					String key = BaiduActivityUtils.BAIDU_USER_ORDER+noss[u];
					Object v = MemcachedUtil.getInstance().get(key);
					if(null != v) {
						System.out.println("....userHasBook...key=" + key + "..value=="+v);
					}
				}
			}
			
			// 当前可以预定  
			for(int i = 0;i < productIds.length;i++) {
				String key = sandBy + BaiduActivityUtils.BAIDU_CAN_ORDER_DAY+productIds[i];
				Object v = MemcachedUtil.getInstance().get(key);
				if(null != v) {
					System.out.println("....2 can book this time...key=" + key + "..value=="+v);
				}
			}
			
			// 当前已经预订数 
			for(int i = 0;i < productIds.length;i++) {
				String key = sandBy+BaiduActivityUtils.BAIDU_HAS_BOOKED_DAY+productIds[i];
				long qty = MemcachedUtil.getInstance().getCounter(key);
				if(qty != -1) {
					System.out.println("....hasorder...key=" + key + "..value=="+qty);
				}
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class BaiduThread  implements Runnable{
		public BaiduThread(){};
        public void run() {
        	for(int i = 0;i < productIds.length;i++) {
        		// 
        		String userNo = this.getUserNo();
        		Long productId = Long.valueOf(productIds[i]);
        		Map<String,Object> map  = baiduActivityService.baiduBooking(productId, userNo);
        		String code = map.get("code").toString();
        		log.info("...baiduBooking  code......start.... " + code);
        		if("1000".equals(code)) {
        			//log.info("........baiduBooking success.... productId="+productIds[i] +"...userno="+userNo +"...." + Thread.currentThread().getName());
        			try{
        				Map<String,Object> smap = baiduActivityService.baiduBeforSubmit(productId,productId, userNo,"");
            			String code2 = smap.get("code").toString();
            			//log.info("...baiduBeforSubmit  code......start.... " + code2);
            			if("1000".equals(code2)) {
            				//log.info("........baiduBeforSubmit success.... productId="+productIds[i] +"...userno="+userNo +"...." + Thread.currentThread().getName());
            			} else {
            				//baiduActivityService.baiduAfterSubmit(productId, userNo);
            			}
        			}catch(Exception e) {
        				e.printStackTrace();
        			}
        			
        		} else {
        			//log.info("........baiduBooking error.... productId="+productIds[i] +"...userno="+userNo +"...." + Thread.currentThread().getName());
        		}
        	}
        }
        
        
        private String getUserNo() {
    		// 28 用户
    		String[] nos = {"ff808081328b6bbe01328b7dfd4000c8","ff80808132b95cea0132e2abbd73420e","ff8080812e37e449012e382795640087","ff8080812e56d5ea012e570941f800be",
    				        "40288a8c23cce7120123e5d0edce6be9","ff8080812e427510012e44268386049e","ff8080812e427510012e44268386049e","ff8080812e427510012e44268386049e"
    						,"ff808081304ab70501306506cb1f7e50","f2d0c29f1fc4fe5f011fc580c90c0088","ff80808130e56a6d0130f2ea0d5e44d5","40288a8b2324cbe001232663b94c2265",
    						"ff8080812f54c41a012f597114161879","ff80808132b95cea0132cc6ea29d4a0b","ff8080812c8285c5012c919f26041558","ff8080813002aa8401300b4d33a637be"
    						,"ff8080813073d74c013074fc600d19da","ff8080812d2c3fc6012d50dd04585bd5","ff80808132864f3c01328afca21f24a7","ff80808130f9d9380130fb3f43880a0f",
    						"ff80808130d0a85b0130d17df00a059a","ff8080813088b92601308984fc990dc1","ff808081296964d701296c55f8420213","ff808081298ba83301298be634c70089",
    						"40288a8b2211e19d01221afd7a2c096c","40288a8d22a02c000122a03ef0b70813","5ad32f1a1ef0517d011ef1f018640728","ff8080812e427510012e44268386049e"};
    		Random rd = new Random();
    		int r = rd.nextInt(27);
    		
    		return nos[r];
    	}
    }
	
	static String[]  noss = {"ff808081328b6bbe01328b7dfd4000c8","ff80808132b95cea0132e2abbd73420e","ff8080812e37e449012e382795640087","ff8080812e56d5ea012e570941f800be",
	        "40288a8c23cce7120123e5d0edce6be9","ff8080812e427510012e44268386049e","ff8080812e427510012e44268386049e","ff8080812e427510012e44268386049e"
			,"ff808081304ab70501306506cb1f7e50","f2d0c29f1fc4fe5f011fc580c90c0088","ff80808130e56a6d0130f2ea0d5e44d5","40288a8b2324cbe001232663b94c2265",
			"ff8080812f54c41a012f597114161879","ff80808132b95cea0132cc6ea29d4a0b","ff8080812c8285c5012c919f26041558","ff8080813002aa8401300b4d33a637be"
			,"ff8080813073d74c013074fc600d19da","ff8080812d2c3fc6012d50dd04585bd5","ff80808132864f3c01328afca21f24a7","ff80808130f9d9380130fb3f43880a0f",
			"ff80808130d0a85b0130d17df00a059a","ff8080813088b92601308984fc990dc1","ff808081296964d701296c55f8420213","ff808081298ba83301298be634c70089",
			"40288a8b2211e19d01221afd7a2c096c","40288a8d22a02c000122a03ef0b70813","5ad32f1a1ef0517d011ef1f018640728","ff8080812e427510012e44268386049e"};
	
	/***************************  测试 块   结束****************************/
	
	
	/**
	 * 半价票 - 当天可销售数量
	 */
	private void initCanBookOfDayInfo() {
		Long sandBy = BaiduActivityUtils.getHourFromStartDate();
		
		if(sandBy >= 0 && sandBy < 22) {
			// 删掉不用的计数器
			/*if(sandBy > 2) {
				this.delUnusedMemcachedcounter(sandBy);
			}*/
			
			// 初始化下一时间段数据。 
			sandBy = sandBy + 1;
			
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("amOrPm", sandBy+""); // 表示当前第几个时间段 
			List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
			if(null != list && list.size() > 0) {
				log.info("....baidu act initCanBookOfDayInfo...sandBy="+sandBy + "..size=" + list.size());
				for(MobileActBaidu mbo:list) {
					// 放入缓存 
					Long pid = mbo.getProductid();
					this.initCanBookOfDayMemcached(mbo.getCanSelQty(), pid,sandBy);
					// 初始已经销售缓存
					this.initHasBookedMem(mbo.getProductid(), sandBy);
					// 初始化半价票请求数 - 计数器 
					this.initMaxReqAmout(pid, sandBy);
				}
			}
		}
	}

	/**
	 * 立减票框外  - 当天可销售数量
	 */
	private void initCanBookOfDay4Sandby() {
		Long sandBy = BaiduActivityUtils.getDayFromStartDate();
		
		if(sandBy >= 0 && sandBy < 11) {
			// 删掉不用的计数器
			/*if(sandBy > 2) {
				this.delUnusedMemcachedcounter(sandBy);
			}*/
			
			// 初始化下一时间段数据  
			sandBy = sandBy + 1;
			
			String sandBy2 = BaiduActivityUtils.getDay4Sandby(sandBy);
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("amOrPm", sandBy2+""); // 对应立减票表示当前第几个时间段 
			List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
			if(null != list && list.size() > 0) {
				log.info("....baidu act initCanBookOfDay4Sandby...sandBy="+sandBy2 + "..size=" + list.size());
				for(MobileActBaidu mbo:list) {
					// 放入缓存 
					this.initCanBookOfDayMemcached(mbo.getCanSelQty(), mbo.getProductid(),sandBy2);
					// 初始已经销售缓存
					this.initHasBookedMem(mbo.getProductid(), sandBy2);
				}
			}
		}
	}
	
	/**
	 * 初始化已经购买-计数器  
	 * 计数器初始值值为0 
	 */
	private void initHasBookedMem(Long productid,Object sandBy) {
		String key = sandBy+BaiduActivityUtils.BAIDU_HAS_BOOKED_DAY+productid;
		MemcachedUtil.getInstance().addOrIncr(key, 0l);
		
	}
	
	/**
	 * 初始化最多请求数-计数器
	 * 计数器初始值值为0 
	 */
	private void initMaxReqAmout(Long productid,Object sandBy) {
		// 最大请求数量
		String key2 = sandBy+BaiduActivityUtils.BAIDU_MAX_REQUEST+productid;
		MemcachedUtil.getInstance().addOrIncr(key2, 0l);
	}
	
	/**
	 * 删除不用的计数器 
	 * @param sandBy
	 */
	private void delUnusedMemcachedcounter(Long sandBy) {
		try{
			if(null != productIds && productIds.length > 0 && sandBy > 2) {
				log.info("....baidu act delUnusedMemcachedcounter...sandBy="+sandBy);
				for(String productid : productIds) {
					MemcachedUtil.getInstance().remove((sandBy-2)+BaiduActivityUtils.BAIDU_HAS_BOOKED_DAY+productid);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.info("....baidu act delUnusedMemcachedcounter..error...sandBy="+sandBy);
		}
	}

	/**
	 * 每天可销售数量 
	 * @param count
	 * @param productid
	 */
	private void initCanBookOfDayMemcached(Long count,Long productid,Object sandBy) {
		// 获取当前是第几个时间段 
		String key = sandBy + BaiduActivityUtils.BAIDU_CAN_ORDER_DAY+productid;
		try {
			MemcachedUtil.getInstance().set(key,BaiduActivityUtils.BAIDU_CAN_ORDER_DAY_TIME,count);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 更新用户信息到order表 
	 */
	private void initUserOrderInfo() {
		int pageSize = 1000;
		Map<String, Object> param = new HashMap<String,Object>();
		Long count = mobileClientService.countMobileActBaiduOrderList(param );
		if(null != count && count > 0) {
			Map<String, Object> params = new HashMap<String,Object>();
			long size = count/pageSize ; // 页数 
			if(count%pageSize != 0) {
				size++;
			}
			Page p = new Page(1000, 1);
			for(long i = 1; i <= size ;i++) {
				p.setPage(i);
				params.put("isPaging", "true"); // 是否使用分页
				params.put("startRows", p.getStartRows());
				params.put("endRows", p.getEndRows());
				List<MobileActBaiduOrder> list = mobileClientService.queryMobileActBaiduOrderList(params);
				if(null != list && list.size() > 0) {
					for(MobileActBaiduOrder mbo:list) {
						this.setMemcached4UserOrder(mbo.getLvUserid());
					}
				}
			}
		}
	}

	/**
	 * 设置缓存 
	 * @param userNo
	 */
	private void setMemcached4UserOrder(String userNo) {
		try {
			MemcachedUtil.getInstance().set(BaiduActivityUtils.BAIDU_USER_ORDER+userNo,BaiduActivityUtils.BAIDU_USER_ORDER_SUCCESS,"booked");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setMobileClientService(MobileClientService mobileClientService) {
		this.mobileClientService = mobileClientService;
	}


	public void setBaiduActivityService(IBaiduActivityService baiduActivityService) {
		this.baiduActivityService = baiduActivityService;
	}
}
