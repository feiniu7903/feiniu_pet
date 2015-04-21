package com.lvmama.clutter.web.baidu;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.axis.utils.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.dom4j.Document;
import org.dom4j.Element;

import com.lvmama.clutter.model.MobileBranch;
import com.lvmama.clutter.service.IClientProductService;
import com.lvmama.clutter.utils.BaiduActivityUtils;
import com.lvmama.clutter.utils.BaiduXmlGenerator;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.pet.po.mobile.MobileActBaidu;
import com.lvmama.comm.pet.po.mobile.MobileActBaiduOrder;
import com.lvmama.comm.pet.po.mobile.MobileActBaiduStock;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.service.mobile.MobileClientService;
import com.lvmama.comm.pet.service.place.PlacePhotoService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.search.PlaceSearchInfoService;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.enums.PlacePhotoTypeEnum;

/**
 * 百度后台操作 ， 
 * @author qinzubo
 *
 */
@Namespace("/mobile/bdDoor")
public class BaiduDoorAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	IClientProductService mobileProductService;
	
	public IClientProductService getMobileProductService() {
		return mobileProductService;
	}

	protected MobileClientService mobileClientService;
	/**
	 * 目的地服务
	 */
	protected PlaceService placeService;
	

	protected PlaceSearchInfoService placeSearchInfoService;
	
	/**
	 * 图片服务
	 */
	protected PlacePhotoService placePhotoService;
	
	
	private String opt; // 操作类别 
	private String sandby="";
	private String canSelQty="";
	private String  sinleOrbatch="";
	private String  keys="";
	private String  isCounter="";
	private String  second="";
	private String  count="";
	private String  userNo="";
	private String  booktype="";
	private String  table="";
	private String  d_table="";
	private String  productId="";
	
	private String fileName;

	/**
	 * 百度后台设置方法 
	 * @return
	 */
	@Action("baiduBackDoor")
	public void baiduDo() {
		Map<String,Object> resultMap = super.resultMapCreator();
		String ip = InternetProtocol.getRemoteAddr(getRequest());
		log.info("...baiduBackDoor ip...."+ip);// 114.80.83.166   180.169.51.82
		if(!"114.80.83.166".equals(ip)) {
			if(!StringUtils.isEmpty(opt)) {
				// 查看当前时间段信息
				if("0".equals(opt)) {
					resultMap.put("banjia", BaiduActivityUtils.getHourFromStartDate());
					resultMap.put("lijian", BaiduActivityUtils.getDay4Sandby(BaiduActivityUtils.getDayFromStartDate()));
				 // 修改每天可预订数量 半价票 和 立减票 
				}else if("1".equals(opt)) {
					String qty = canSelQty; // 可售数量 
					int i = this.initBaiduCanBookOfDay(sandby,Long.valueOf(qty));
					resultMap.put("modifyQty", i);
				// 修改总表数量
				} else if("2".equals(opt)) {
					String qty = canSelQty; // 可售数量 
					int i = this.initBaiduStockInfo2(Long.valueOf(qty));
					resultMap.put("modifyQty", i);
				//清楚缓存 
				}else if("3".equals(opt)) {
					int i = this.cleanMemecached4Baidu(sinleOrbatch,keys,sandby,booktype);// 删除相关memcached 
					resultMap.put("modifyQty", i);
				// 修改memcached 计数器，当天已经预订的数量 
				}else if("4".equals(opt)) {
					int i = this.modifyMemecached4Baidu(Integer.valueOf(second),isCounter,sinleOrbatch,keys,sandby,Long.valueOf(count));//修改相关memcached 
					resultMap.put("modifyQty", i);
				// 初始化缓存  
				}else if("5".equals(opt)) {
					int i = this.initMemcached(sandby);
					resultMap.put("modifyQty", i);
				// 查看memcached信息 
				}else if("6".equals(opt)) {
					int i = this.searchMemcached(booktype,sinleOrbatch,sandby,keys,resultMap);
					resultMap.put("modifyQty", i);
				// 查看销售信息 
				}else if("7".equals(opt)) {
					String type = table; // 1：每天可售表 ； 2：总表 ;3:用户信息 
					int i = this.searchBaiduAct(type,sandby,userNo,resultMap);
					resultMap.put("modifyQty", i);
				// 清除用户信息 ；慎用。。。
				} else if("8".equals(opt)) {
					int i = this.initBaiduActUserInfo(userNo,Long.valueOf(productId),d_table);
					resultMap.put("modifyQty", i);
				// 报表 
				}else if("9".equals(opt)){
					//this.baiduReport();
				} // 初始化 天数表 可预订数据 
				else if("10".equals(opt)) {
					this.initBaiduCanBookOfDay();
				// 初始化总表可预订数量
				} else if("11".equals(opt)) {
					this.initBaiduStockInfo();
					/*Object sandby = BaiduActivityUtils.getDay4Sandby(BaiduActivityUtils.getDayFromStartDate());
					Map<String, Object> param = new HashMap<String,Object>();
					param.put("productid", "66881");
					param.put("amOrPm", sandby); // 表示当前第几个时间段 
					List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
					if(null != list && list.size() > 0) {
						MobileActBaidu mbs = list.get(0);
						Long canSel= mbs.getCanSelQty();
						String key = sandby+BaiduActivityUtils.BAIDU_CAN_ORDER_DAY+"66881";
						MemcachedUtil.getInstance().set(key,BaiduActivityUtils.BAIDU_CAN_ORDER_DAY_TIME,canSel);
					}
					Object sandby = BaiduActivityUtils.getHourFromStartDate()
					System.out.print("....list="+sandby);*/
					
				//清楚缓存 1，单个请 ，批量清 
				}
			}
		}
		JSONObject json = JSONObject.fromObject(resultMap); 
		String responseJson = json.toString();
		this.sendAjaxResult(responseJson);
	}

	  /**
		 * 初始化每天可以预定张数 
		 */
		private void initBaiduCanBookOfDay() {
			int totalDays = 18; // 共18个时间段
			for(int d = 1 ; d <= totalDays;d++) {
				// 共100个半价产品
				String[] ids = ClutterConstant.getBaiduActProductIdList();
				if(null != ids && ids.length > 0) {
					for(int i = 0 ;i < ids.length; i++) {
						MobileActBaidu mab = new MobileActBaidu();
						mab.setAmOrPm(d+""); // 时间段
						mab.setValid("1");
						mab.setProductid(Long.valueOf(ids[i]));
						mab.setCanSelQty(Long.valueOf(ClutterConstant.getBaiduActSellQty()));
						mab.setQuantity(0l);
						mab.setUpdateDate(new Date());
						mobileClientService.insertMobileActBaidu(mab);
					}
				}
				
			}
			
			long totalDays2 = 9l; // 共9个时间段
			for(long d = 1 ; d <= totalDays2;d++) {
				// 共100个半价产品
				String[] ids = ClutterConstant.getBaiduActSandByProductIdList();
				if(null != ids && ids.length > 0) {
					for(int i = 0 ;i < ids.length; i++) {
						MobileActBaidu mab = new MobileActBaidu();
						mab.setAmOrPm(BaiduActivityUtils.getDay4Sandby(d)); // 时间段
						mab.setValid("1");
						mab.setProductid(Long.valueOf(ids[i]));
						mab.setCanSelQty(Long.valueOf(ClutterConstant.getBaiduActSellQty()));
						mab.setQuantity(0l);
						mab.setUpdateDate(new Date());
						mobileClientService.insertMobileActBaidu(mab);
					}
				}
				
			}
			
		}
		
		/**
		 * 库存 
		 */
		private void initBaiduStockInfo() {
			// 共100个半价产品
			String[] ids = ClutterConstant.getBaiduActProductIdList();
			if(null != ids && ids.length > 0) {
				for(int i = 0 ;i < ids.length; i++) {
					MobileActBaiduStock mab = new MobileActBaiduStock();
					mab.setValid("1");
					mab.setProductid(Long.valueOf(ids[i]));
					mab.setCanSelQty(ClutterConstant.getBaiduActSellQty()*18l);
					mab.setQuantity(0l);
					mab.setUpdateDate(new Date());
					mobileClientService.insertMobileActBaiduStock(mab);
				}
			}
		}
		
		/**
		 *  清除用户信息 ；慎用。。。
		 * @param userNo
		 * @param d_table
		 * @return
		 */
		private int initBaiduActUserInfo(String userNo, Long productId,String d_table) {
			int i = 0 ;
			if(StringUtils.isEmpty(userNo)) {
				return i;
			}
			String key = BaiduActivityUtils.BAIDU_USER_ORDER+userNo;
			MemcachedUtil.getInstance().remove(key);
			log.info(".... baidu act.....delete userInfo initBaiduActUserInfo....userNo="+userNo);
			if("true".equals(d_table)) {
				log.info(".............userNo="+userNo+"..productId="+productId);
				mobileClientService.deleteByUserId(userNo,productId);
			}
			
			return 0;
		}

		/**
		 * 查看表数据 
		 * @param type  1：每天可售表 ； 2：总表  ;3:用户表
		 * @param sandby 时间段 
		 * @return
		 */
		private int searchBaiduAct(String type, String sandby,String userNo,Map<String,Object> resultMap) {
			int i = 0 ; 
			if(StringUtils.isEmpty(type)) {
				return i;
			}
			if("3".equals(type)) {
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("lvUserid", userNo);
				List<MobileActBaiduOrder> list = mobileClientService.queryMobileActBaiduOrderList(params);
				for(MobileActBaiduOrder mbs:list) {
					mbs.setCreateDate(null);
				}
				resultMap.put("datas", list);
			} else if("1".equals(type)) {
				Map<String, Object> param = new HashMap<String,Object>();
				param.put("amOrPm", sandby+""); // 表示当前第几个时间段 
				List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
				for(MobileActBaidu mbs:list) {
					mbs.setUpdateDate(null);
				}
				resultMap.put("datas", list);
				
			}else if("2".equals(type)){
				Map<String, Object> param = new HashMap<String,Object>();
				List<MobileActBaiduStock> list = mobileClientService.queryMobileActBaiduStockList(param);
				for(MobileActBaiduStock mbs:list) {
					mbs.setUpdateDate(null);
				}
				resultMap.put("datas", list);
			}
			return i;
		}

		/**
		 * 看memcached信息
		 *  @param booktype 1:已预订；2:当前可预订 
		 * @param sinleOrbatch
		 * @param sandby
		 * @param keys
		 * @return
		 */
		private int searchMemcached(String booktype,String sinleOrbatch, String sandby, String keys,Map<String,Object> resultMap) {
			int i = 0;
			 // 如果是单个查询 ，需要完整的keys 
			if("1".equals(sinleOrbatch)) {
				if(StringUtils.isEmpty(keys)) {
					return i;
				}
				String[] keyArrays = keys.split(",");
				for(int a = 0 ; a < keyArrays.length;a++) {
					// 
					try {
						Object l = "";
						if("1".equals(booktype)) {
							l = MemcachedUtil.getInstance().get(keyArrays[a]);
						} else {
							l = MemcachedUtil.getInstance().getCounter(keyArrays[a]);
						}
						
						resultMap.put(keyArrays[a], l);
						i++;
						
					}catch(Exception e){
						e.printStackTrace();
						log.info("...baidu act   delete memcatched key error...key="+keyArrays[a]);
					}
				}
			// 批量查询  
			} else if("2".equals(sinleOrbatch)) {
				if(StringUtils.isEmpty(sandby)) {
					return i;
				}
				Map<String, Object> param = new HashMap<String,Object>();
				param.put("amOrPm", sandby+""); // 表示当前第几个时间段 
				List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
				if(null != list && list.size() > 0) {
					log.info("....baidu act searchMemcached...sandBy="+sandby + "..size=" + list.size());
					for(MobileActBaidu mbo:list) {
						// 放入缓存 
						String key = sandby+BaiduActivityUtils.BAIDU_HAS_BOOKED_DAY+mbo.getProductid();
						if("2".equals(booktype)) {
							key = sandby+BaiduActivityUtils.BAIDU_CAN_ORDER_DAY+mbo.getProductid();
						}
						try {
							Object l = "";
							if("2".equals(booktype)) {
								l = MemcachedUtil.getInstance().get(key);
							} else {
								l = MemcachedUtil.getInstance().getCounter(key);
							}
							
							resultMap.put(key, l);
							i++;
						}catch(Exception e){
							e.printStackTrace();
							log.info("...baidu act  searchMemcached delete memcatched key error...222..key="+MemcachedUtil.getInstance().remove(key));
						}
					}
				}
			}
			return i;
		}

		/**
		 * 初始化缓存  ,每天可售金额 
		 * @param sandby
		 * @return
		 */
		private int initMemcached(String sandby) {
			int i = 0 ; 
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("amOrPm", sandby+""); // 表示当前第几个时间段 
			List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
			if(null != list && list.size() > 0) {
				log.info("....baidu act initMemcached...sandby="+sandby + "..size=" + list.size());
				for(MobileActBaidu mbo:list) {
					// 放入缓存 
					String key = sandby + BaiduActivityUtils.BAIDU_CAN_ORDER_DAY+mbo.getProductid();
					try {
						MemcachedUtil.getInstance().set(key,BaiduActivityUtils.BAIDU_CAN_ORDER_DAY_TIME,mbo.getCanSelQty());
						i++;
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
			return i;
		}

		/**
		 * 修改memcached计数器
		 * @param sec  设置秒
		 * @param isCounter  是否修改计数器  1： 修改计数器 ； 否则没有修改 
		 * @param sinleOrbatch
		 * @param keys
		 * @param sandby
		 * @return
		 */
		private int modifyMemecached4Baidu(int sec,String isCounter ,String sinleOrbatch, String keys,
				String sandBy,Long count) {
			int i = 0 ; 
			if(StringUtils.isEmpty(sinleOrbatch) ) {
				return i;
			}
	        // 如果是单个修改，需要完整的keys 
			if("1".equals(sinleOrbatch)) {
				if(StringUtils.isEmpty(keys)) {
					return i;
				}
				String[] keyArrays = keys.split(",");
				for(int a = 0 ; a < keyArrays.length;a++) {
					// 
					try {
						if("1".equals(isCounter)) {
							// 如果计数器存在 ，则删除计数器，然后初始化 
							MemcachedUtil.getInstance().addOrIncrAndInit(keyArrays[a], count);
						} else {
							MemcachedUtil.getInstance().set(keyArrays[a], sec,count);
						}
						i++;
						
					}catch(Exception e){
						e.printStackTrace();
						log.info("...baidu act   delete memcatched key error...key="+keyArrays[a]);
					}
				}
			// 批量修改  计数器 
			} else if("2".equals(sinleOrbatch)) {
				if(StringUtils.isEmpty(sandBy)) {
					return i;
				}
				Map<String, Object> param = new HashMap<String,Object>();
				param.put("amOrPm", sandBy+""); // 表示当前第几个时间段 
				List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
				if(null != list && list.size() > 0) {
					log.info("....baidu act modifyMemecached4Baidu...sandBy="+sandBy + "..size=" + list.size());
					for(MobileActBaidu mbo:list) {
						String key = sandBy+BaiduActivityUtils.BAIDU_HAS_BOOKED_DAY+mbo.getProductid();
						if("1".equals(isCounter)) {
							
						} else {
							key = sandBy+BaiduActivityUtils.BAIDU_CAN_ORDER_DAY+mbo.getProductid();
						}
						
						try {
							if("1".equals(isCounter)) {
								// 放入缓存  - 计数器 
								MemcachedUtil.getInstance().addOrIncrAndInit(key, count);
							} else {
								// 放入缓存  - 非计数器 
								MemcachedUtil.getInstance().set(key,sec, count);
							}
							i++;
						}catch(Exception e){
							e.printStackTrace();
							log.info("...baidu act   delete memcatched key error...222..key="+MemcachedUtil.getInstance().remove(key));
						}
					}
				}
			}
			return i;
		}

		/**
		 * 删除相关memcached 
		 * @param sinleOrbatch 1:单个清楚（一次可以清楚多个key,用逗号隔开','） ；2：批量清 ；
		 * @param keys  （一次可以清楚多个key,用逗号隔开','）
		 * @param sandBy  时间段 
		 * @param   booktype 1:已预订；2:当前可预订 
		 */
		private int cleanMemecached4Baidu(String sinleOrbatch,
				String keys,String sandBy,String bookeType) {
			int i = 0 ; 
			if(StringUtils.isEmpty(sinleOrbatch) ) {
				return i;
			}
	        // 如果是单个清楚 ，需要完整的keys 
			if("1".equals(sinleOrbatch)) {
				if(StringUtils.isEmpty(keys)) {
					return i;
				}
				String[] keyArrays = keys.split(",");
				for(int a = 0 ; a < keyArrays.length;a++) {
					// 
					try {
						MemcachedUtil.getInstance().remove(keyArrays[a]);
					}catch(Exception e){
						e.printStackTrace();
						log.info("...baidu act   delete memcatched key error...key="+keyArrays[a]);
					}
				}
			// 批量删除 
			} else if("2".equals(sinleOrbatch)) {
				if(StringUtils.isEmpty(sandBy)) {
					return i;
				}
				
				Map<String, Object> param = new HashMap<String,Object>();
				param.put("amOrPm", sandBy+""); // 表示当前第几个时间段 
				List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
				if(null != list && list.size() > 0) {
					log.info("....baidu act initCanBookOfDayInfo...sandBy="+sandBy + "..size=" + list.size());
					for(MobileActBaidu mbo:list) {
						// 放入缓存 
						String key = sandBy + BaiduActivityUtils.BAIDU_CAN_ORDER_DAY+mbo.getProductid();
						if("2".equals(bookeType)) {
							key = sandBy + BaiduActivityUtils.BAIDU_HAS_BOOKED_DAY+mbo.getProductid();
						}
						try {
							MemcachedUtil.getInstance().remove(key);
						}catch(Exception e){
							e.printStackTrace();
							log.info("...baidu act   delete memcatched key error...222..key="+MemcachedUtil.getInstance().remove(key));
						}
						i++;
					}
				}
			}
			return i;
		}

		/**
		 * 修改每天可预订数量 
		 * ticketType  1:半价票 ； 0：立减票 
		 */
		private int initBaiduCanBookOfDay(String sandBy,Long canSelQty) {
			int i = 0;
			if(StringUtils.isEmpty(sandBy) ) {
				return i;
			}
		
			Map<String, Object> param = new HashMap<String,Object>();
			param.put("amOrPm", sandBy+""); // 对应立减票表示当前第几个时间段 
			List<MobileActBaidu> list = mobileClientService.queryMobileActBaiduList(param);
			if(null != list && list.size() > 0) {
				log.info("....baidu act initBaiduCanBookOfDay.....sandBy="+sandBy + "..size=" + list.size());
				for(MobileActBaidu mbo:list) {
					try{
						if(null != mbo) {
							if(mbo.getQuantity() > canSelQty ) {
								mbo.setQuantity(canSelQty); // 如果已经销售数量大于要设置的数量 ，则该产品不可售了。
							}
							
							if(null != canSelQty) {
								mbo.setCanSelQty(canSelQty);
							}
							mobileClientService.updateMobileActBaidu(mbo);
							i ++;
						}
					}catch(Exception e) {
						e.printStackTrace();
						log.error("....baidu act initBaiduCanBookOfDay..error...sandBy="+sandBy + "..size=" + list.size());
					}
					
				}
			}
			return i;
		}
		
		/**
		 * 修改库存 
		 */
		private int  initBaiduStockInfo2(Long canselQty) {
			int i = 0;
			if(null == canselQty ) {
				return i;
			}
			
			Map<String, Object> param = new HashMap<String,Object>();
			List<MobileActBaiduStock> list = mobileClientService.queryMobileActBaiduStockList(param);
			if(null != list && list.size() > 0) {
				log.info("....baidu act initBaiduStockInfo.....canselQty="+canselQty + "..size=" + list.size());
				for(MobileActBaiduStock mbo:list) {
					try{
						if(null != mbo) {
							if(mbo.getQuantity() > canselQty ) {
								mbo.setQuantity(canselQty); // 如果已经销售数量大于要设置的数量 ，则该产品不可售了。
							}
							
							if(null != canselQty) {
								mbo.setCanSelQty(canselQty);
							}
							mobileClientService.updateMobileActBaiduStock(mbo);
							i++;
						}
					}catch(Exception e) {
						e.printStackTrace();
						log.error("....baidu act initBaiduCanBookOfDay..error...canselQty="+canselQty + "..size=" + list.size());
					}
					
				}
			}
			return i;
		}
		
		/**
		 * 生成百度所需的xml格式文件 . 
		 */
		@Action("generatorBaiduXml")
		public Map<String, Object> generatorBaiduXml(Map<String, Object> params) {
			//
			String t_name = "grab4baidu.xml";
			if(null != params.get("fileName")) {
				t_name = params.get("fileName").toString().trim()+".xml";
			}
			String fileNamePrex = BaiduActivityUtils.getRootPath()+File.separator+"baidu"+File.separator;
			String fileName = fileNamePrex+t_name;
			log.info("...generatorBaiduXml fileName="+fileName);
			//String fileName="E://test.xml";
			String[] placeIds = ClutterConstant.getBaiduActPlaceids();
			BaiduXmlGenerator bxg = BaiduXmlGenerator.getInstance();
			Document doc = bxg.getDocument();
			 Element element = bxg.getUrlSetElement(doc);
			
			for(String strPlaceId:placeIds) {
				try {
					Long placeId = Long.valueOf(strPlaceId);
					Place place = this.placeService.queryPlaceByPlaceId(placeId);
					// 获取景点对应的产品信息
					if(null == place) {
						log.error("....generatorBaiduXml...error ... place can not find...placeId="+placeId);
						continue;
					}
					place.setImgUrl(this.imgUrl(placeId)); // 设置图片 
					
					// 总点评数 和 分数 
					PlaceSearchInfo psi = placeSearchInfoService.getPlaceSearchInfoByPlaceId(placeId);
					place.setCommentCount(null == psi.getCmtNum()?0l:psi.getCmtNum()); // 点评总数
					place.setAvgScore(null == psi.getCmtNiceRate()?0f:(psi.getCmtNiceRate().intValue()*20)); // 点评评价分数 . 
					
					// 可售数量 
					Map<String,Object> param = new HashMap<String,Object>();
					param.put("placeId", placeId);
					Map<String, Object> productMap = mobileProductService.getBranches(param);
					
					// 过滤掉不存在priceList的数据 
					if(this.hasPriceList(productMap)) {
						// 生成 xml 
						bxg.getUrlInfo(element, place, productMap);
					} 
					
				}catch(Exception e) {
					e.printStackTrace();
					log.error("...generatorBaiduXml...error......33.....");
				}
			}
			
			bxg.getneratorBaiduXml(doc, fileName);
			
			// 生成sitemap
			Document smDoc = bxg.getDocument();
			try{
				Element elementSm = bxg.getSiteMap(smDoc);
				
				List<String> names = new ArrayList<String>();
				names.add("http://qing.lvmama.com/clutter/baidu/"+t_name);
				bxg.genaratorSiteMap(elementSm,names);
				
				//bxg.genaratorSiteMap(elementSm,"http://qing.lvmama.com/clutter/baidu/"+t_name);
				bxg.getneratorBaiduXml(smDoc, fileNamePrex+"sitemaps.xml");
			}catch(Exception e) {
				e.printStackTrace();
				log.error("...genaratorSiteMap...error......44.....");
			}
			
			return null;
		}
		
		/**
		 * 是否有可售门票 
		 * @param productMap
		 * @return
		 */
		public boolean hasPriceList(Map<String,Object> productMap) {
			boolean b = false;
			if(null != productMap  ) {
				if(null != productMap.get("single")) {
					List<Map<String,Object>> obj = (List<Map<String, Object>>) productMap.get("single");
					if(null != obj && obj.size() > 0) {
						List<MobileBranch> mbList = (List<MobileBranch>)obj.get(0).get("datas");
						if(null != mbList && mbList.size() > 0) {
							b = true;
						}
					}
				} 
				if(null != productMap.get("union")) {
					List<Map<String,Object>> obj = (List<Map<String, Object>>) productMap.get("union");
					if(null != obj && obj.size() > 0) {
						List<MobileBranch> mbList = (List<MobileBranch>)obj.get(0).get("datas");
						if(null != mbList && mbList.size() > 0) {
							b = true;
						}
					}
				}
				if(null != productMap.get("suit")) {
					List<Map<String,Object>> obj = (List<Map<String, Object>>) productMap.get("suit");
					if(null != obj && obj.size() > 0) {
						List<MobileBranch> mbList = (List<MobileBranch>)obj.get(0).get("datas");
						if(null != mbList && mbList.size() > 0) {
							b = true;
						}
					}
				}
			}
			
			return b;
		}
		
		/**
		 * 获取url 
		 * @param placeId
		 * @return
		 */
		private String imgUrl(Long placeId ){
			PlacePhoto pp = new PlacePhoto();
			pp.setType(PlacePhotoTypeEnum.MIDDLE.name());
			pp.setPlaceId(placeId);
			List<PlacePhoto> ppList =  this.placePhotoService.queryByPlacePhoto(pp);
			if(null != ppList && ppList.size() > 0) {
				PlacePhoto p = ppList.get(0);
				return p.getImagesUrl();
			}
			return "";
		}
		
		
		public void setMobileProductService(IClientProductService mobileProductService) {
			this.mobileProductService = mobileProductService;
		}

		public MobileClientService getMobileClientService() {
			return mobileClientService;
		}

		public void setMobileClientService(MobileClientService mobileClientService) {
			this.mobileClientService = mobileClientService;
		}

		public PlaceService getPlaceService() {
			return placeService;
		}

		public void setPlaceService(PlaceService placeService) {
			this.placeService = placeService;
		}

		public PlaceSearchInfoService getPlaceSearchInfoService() {
			return placeSearchInfoService;
		}

		public void setPlaceSearchInfoService(
				PlaceSearchInfoService placeSearchInfoService) {
			this.placeSearchInfoService = placeSearchInfoService;
		}

		public PlacePhotoService getPlacePhotoService() {
			return placePhotoService;
		}

		public void setPlacePhotoService(PlacePhotoService placePhotoService) {
			this.placePhotoService = placePhotoService;
		}

		public String getOpt() {
			return opt;
		}

		public void setOpt(String opt) {
			this.opt = opt;
		}

		public String getSandby() {
			return sandby;
		}

		public void setSandby(String sandby) {
			this.sandby = sandby;
		}

		public String getCanSelQty() {
			return canSelQty;
		}

		public void setCanSelQty(String canSelQty) {
			this.canSelQty = canSelQty;
		}

		public String getSinleOrbatch() {
			return sinleOrbatch;
		}

		public void setSinleOrbatch(String sinleOrbatch) {
			this.sinleOrbatch = sinleOrbatch;
		}

		public String getKeys() {
			return keys;
		}

		public void setKeys(String keys) {
			this.keys = keys;
		}

		public String getIsCounter() {
			return isCounter;
		}

		public void setIsCounter(String isCounter) {
			this.isCounter = isCounter;
		}

		public String getSecond() {
			return second;
		}

		public void setSecond(String second) {
			this.second = second;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		public String getUserNo() {
			return userNo;
		}

		public void setUserNo(String userNo) {
			this.userNo = userNo;
		}

		public String getBooktype() {
			return booktype;
		}

		public void setBooktype(String booktype) {
			this.booktype = booktype;
		}

		public String getTable() {
			return table;
		}

		public void setTable(String table) {
			this.table = table;
		}

		public String getD_table() {
			return d_table;
		}

		public void setD_table(String d_table) {
			this.d_table = d_table;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

	
	
}
