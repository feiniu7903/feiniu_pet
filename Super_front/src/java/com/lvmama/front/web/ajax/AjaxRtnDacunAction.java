package com.lvmama.front.web.ajax;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.user.AnnHongbaoHj;
import com.lvmama.comm.pet.po.user.Annhongbao;
import com.lvmama.comm.pet.po.user.Annliping;
import com.lvmama.comm.pet.po.user.Annwinnerslist;
import com.lvmama.comm.pet.po.user.UserTopic;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.front.tools.dacun.QuestionUtil;
import com.lvmama.front.web.BaseAction;

/**
 * 周年庆大促系统ajax
 * 
 * @author nixianjun
 * 
 */
public class AjaxRtnDacunAction extends BaseAction {

	@Autowired
	private UserUserProxy userUserProxy;
	@Autowired
	private MarkCouponService markCouponService;
	@Autowired
	private CashAccountService cashAccountService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2323231L;

	@Action(value = "/519dacun/ajaxcheckHuoJiang")
	public void ajaxcheckHuoJiang() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String ip = InternetProtocol.getRemoteAddr(getRequest());
		if(InternetProtocol.isInnerIP2(ip)){
			map.put("success", "false");
	    	map.put("message","IP受限");
	    	printRtn(map);
			return;
		}
		if (this.isLogin() == false) {
			map.put("success", "false");
			map.put("message", "未登录");
			printRtn(map);
			return;
		}
		List<Annwinnerslist> list = userUserProxy.queryWinnerslistByUserId(this
				.getUser().getId());
		if (null != list && list.size() > 0) {
			map.put("success", "false");
			map.put("message", "您已经抽过奖");
			printRtn(map);
			return;
		}
		UserTopic userTopic = userUserProxy.queryUserTopicById(this.getUser()
				.getId());
	     if (null == userTopic) {
			map.put("success", "false");
			map.put("message", "亲，您的条件不符哦~请查看游戏规则。");
			printRtn(map);
			return;
		}
		int jieguo = huojiangRandom();
		if (jieguo == 6) {
			Annwinnerslist annlist = new Annwinnerslist();
			annlist.setUserId(this.getUser().getId());
			annlist.setUserName(this.getUser().getUserName());
			annlist.setHuojiangTime(DateUtil.getTodayDate());
			annlist.setLpName("10元门票现金抵用券");
			annlist.setProjectName("周年庆");
			annlist.setCreateDate(new Date());
			annlist.setFlag(1L);
			userUserProxy.insertWinnerslist(annlist);
			sendCouponIdAndUserToDacu(this.getUser(), 4475L);
		} else if (jieguo == 1||jieguo == 2||jieguo==3||jieguo==4||jieguo==5||jieguo==7||jieguo==8) {
			//查询礼品库存是否存在
			Annliping ann=userUserProxy.queryAnnliping(Long.valueOf(jieguo));
			if(null!=ann&&ann.getLpSpare()>=1){
				Annwinnerslist annlist = new Annwinnerslist();
				annlist.setUserId(this.getUser().getId());
				annlist.setUserName(this.getUser().getUserName());
				annlist.setHuojiangTime(DateUtil.getTodayDate());
				//(1:卡当u型枕，2：pad min,3:旅游产品，4:户外大礼包，5：JACK WOLFSKIN 旅行盥洗包，6：10元7:JACK WOLFSKIN 登山帽子.8:虎贝尔安全座椅)
				annlist.setLpName(ann.getLpName());
				annlist.setProjectName("周年庆");
				annlist.setCreateDate(new Date());
				annlist.setFlag(1L);
				annlist.setLpId(ann.getLpId());
				if(!userUserProxy.insertMinDazhuanPanWinnerslist(annlist,jieguo,1)){
					jieguo=6;
				}
			}else{
				jieguo=6;
			}
		}
		map.put("success", "true");
		map.put("winner", jieguo);
		printRtn(map);
		return;
	}
	
	
	/**
	 * 抢红包（问题提问）
	 * @throws IOException
	 */
	@Action(value = "/519dacun/ajaxQuestion")
	public void ajaxQuestion() throws IOException {
		String ip = InternetProtocol.getRemoteAddr(getRequest());
		Map<String, Object> map = new HashMap<String, Object>();
		if(InternetProtocol.isInnerIP2(ip)){
			map.put("success", "false");
	    	map.put("errorText","IP受限");
	    	printRtn(map);
			return;
		}
 	 	if (this.isLogin() == false) {
			map.put("success", "false");
			map.put("errorText", "未登录");
			printRtn(map);
			return;
		}else{
			
			Date date = new Date();
			Date begin = DateUtil.stringToDate("2014-5-9 09:00:00", "yyyy-MM-dd HH:mm:ss");
			Date end = DateUtil.stringToDate("2014-5-19 23:59:59", "yyyy-MM-dd HH:mm:ss");
			if (date.getTime() < begin.getTime()
					|| date.getTime() > end.getTime()) {
				map.put("success", "false");
		    	map.put("errorText","不是抽奖活动时间");
		    	printRtn(map);
				return;
			}
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("senddate", date);
			List<Annhongbao> hongbaolist = this.userUserProxy.queryAnnhongbaoByParam(param);
			if(hongbaolist == null || hongbaolist.size() == 0){
				map.put("success", "false");
		    	map.put("errorText","不是抽奖活动时间");
		    	printRtn(map);
		    	return ;
			}
		    param.put("userId", getUser().getId());
		    param.put("createTime", date);
			// 验证每天只能抽5次
		    List<AnnHongbaoHj> list = this.userUserProxy.queryAnnHongbaoHj(param);
		    if(list != null && list.size() >=5){		    	
		    	map.put("success", "false");
		    	map.put("errorText","每天只能抽取5次");
		    	printRtn(map);
		    	return ;
		    }
			
			int i =  ((int)(1+Math.random()*(7-1+1)));
			String question=QuestionUtil.QUESTION_ENUM.getQuestion(String.valueOf(i));
			String[] s=question.split(",");
			map.put("success", "true");
			map.put("question", s[0]);
			map.put("anwsera", s[1]);
			map.put("anwserb", s[2]);
			map.put("right", s[3]);
			printRtn(map);
			return;
		 }
	}
	
	/**
	 * 抢红包 答错
	 * @throws IOException
	 */
	@Action(value = "/519dacun/ajaxanwserwrong")
	public void ajaxanwserwrong() throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		if (this.isLogin() == false) {
			map.put("success", "false");
			map.put("message", "未登录");
			printRtn(map);
			return;
 	 	}
		AnnHongbaoHj bean = new AnnHongbaoHj();
		bean.setFlag(0);
	 	bean.setUserId(getUser().getId());
	 	bean.setCreateTime(new Date());
	 	this.userUserProxy.saveAnnHongbaoHJ(bean);
	 	map.put("success", "true");
		printRtn(map);
	 	return;
	}
	
	/**
	 * 抢红包
	 * @throws IOException
	 */
	@Action(value = "/519dacun/ajaxQianghongbao")
	public void ajaxQianghongbao() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		
 	 	if (this.isLogin() == false) {
			map.put("success", "false");
			map.put("message", "未登录");
			printRtn(map);
			return;
 	 	}else{
 	 		Date date = new Date();
 	 		AnnHongbaoHj bean = new AnnHongbaoHj();
 	 		bean.setFlag(0);
 	 		bean.setUserId(getUser().getId());
 	 		bean.setCreateTime(date);
 	 		
		    int i=hongbaoRandom();
		    
		    //0-7点抽红包返回没抽中
		    int _hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		    if(_hour >= 0 && _hour <= 7){
		    	this.userUserProxy.saveAnnHongbaoHJ(bean);
		    	map.put("success", "false");
		    	printRtn(map);
		    	return ;
		    }
		    Map<String, Object> param = new HashMap<String, Object>();
		    param.put("userId", getUser().getId());
		    // 一个用户总共只能领50元红包
		    Long sumMoney = this.userUserProxy.selectSumMoneyByUserId(param);
		    if(sumMoney != null && (sumMoney + i) > 50){
		    	// 保存红包领取记录
		    	this.userUserProxy.saveAnnHongbaoHJ(bean);
		    	map.put("success", "false");
		    	map.put("message", "一个用户总共只能领50元红包");
		    	printRtn(map);
		    	return ;
		    }
		    // 每个用户一天只能中奖3次
		    param.put("createTime", date);
		    List<AnnHongbaoHj> hjlist = this.userUserProxy.queryAnnHongbaoHj(param);
		    if(hjlist != null && hjlist.size()>2){
		    	int _temp = 0;
		    	for (int j = 0; j < hjlist.size(); j++) {
					if(hjlist.get(j).getFlag() == 1){
						_temp++;
					}
				}
		    	if(_temp>=3){
		    		this.userUserProxy.saveAnnHongbaoHJ(bean);
		    		map.put("success", "false");
			    	printRtn(map);
		    		return ;
		    	}
		    }
		    param.clear();
		    param.put("senddate", date);
		    List<Annhongbao> hongbaolist = this.userUserProxy.queryAnnhongbaoByParam(param);
		    if(hongbaolist != null && hongbaolist.size()>0){
		    	if(! ((i == 2 && hongbaolist.get(0).getTwoShengyu() > 2) 
		    			|| (i == 5 && hongbaolist.get(0).getFiveShengyu() > 5) 
		    			|| (i == 15 && hongbaolist.get(0).getFifteenShengyu() > 15) 
		    			|| (i == 50 && hongbaolist.get(0).getFiftyShengyu() > 50))){
		    		// 保存红包领取记录
		    		this.userUserProxy.saveAnnHongbaoHJ(bean);
		    		map.put("success", "false");
			    	printRtn(map);
		    		return ;
		    	}		    	
		    }else{
		    	map.put("success", "false");
		    	printRtn(map);
		    	return ;
		    }
		    Annhongbao annhongbao = hongbaolist.get(0);
		    if(i==2 && annhongbao.getTwoShengyu()-i > 0){
		    	annhongbao.setTwoShengyu(annhongbao.getTwoShengyu()-i);
 	 		}else if(i == 5 && annhongbao.getFiveShengyu()-i > 0){
		    	annhongbao.setFiveShengyu(annhongbao.getFiveShengyu()-i);
		    }else if(i == 15 && annhongbao.getFifteenShengyu()-i > 0){
		    	annhongbao.setFifteenShengyu(annhongbao.getFifteenShengyu()-i);
 	 		}else if(i == 50 && annhongbao.getFiftyShengyu()-i > 0){
		    	annhongbao.setFiftyShengyu(annhongbao.getFiftyShengyu()-i);
		    }else{
		    	map.put("success", "false");
		    	printRtn(map);
		    	return ;
		    }
		    param.put("zhongjiangMoney", i);
		    bean.setFlag(1);
		    bean.setHjMoney(i);
		    if(!userUserProxy.saveAnnHongbaoUpdate(bean, param,i,getUser().getId())){
		    	map.put("success", "false");
		    	printRtn(map);
		    	return ;
		    }
		    
		    
		    //查询奖金
		    
		    map.put("success", "true");
		    map.put("jiebie", i);
 			printRtn(map);
			return;
		 }
	}
	/**
	 * 
	 * @param userId
	 * @param couponId
	 */
	public void  sendCouponIdAndUserToDacu(UserUser theuser,long couponId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", theuser.getId());
		param.put("couponId", couponId);
		List<MarkCouponCode> couponCodes =  this.markCouponService.queryByUserAndCoupon(param);
		if(couponCodes != null && couponCodes.size() > 0){
			return;
		}
		MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(couponId);
		 markCouponService.bindingUserAndCouponCode(theuser, markCouponCode.getCouponCode());
	}

	@Action(value = "/519dacun/ajaxAddHuoJiangMessage")
	public void ajaxAddHuoJiangMessage() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (this.isLogin() == false) {
			map.put("success", "false");
			printRtn(map);
		} else {
			Annwinnerslist annlist = new Annwinnerslist();
			annlist.setUserId(this.getUser().getId());
			annlist.setRealName(new String(this.getRequestParameter("realName")
					.toString().getBytes("iso8859_1"), "UTF-8"));
			annlist.setMobile(Long.valueOf(this.getRequestParameter("mobile")));
			annlist.setZipcode(Long.valueOf(this.getRequestParameter("zipcode")));
			annlist.setAddress(new String(this.getRequestParameter("address")
					.toString().getBytes("iso8859_1"), "UTF-8"));
			userUserProxy.updateWinnerslist(annlist);
			map.put("success", "true");
			printRtn(map);
		}
	}
	
	@Action(value = "/519dacun/ajaxgetSystemNowDate")
	public void ajaxgetSystemNowDate() throws IOException{
		Date endDate=DateUtil.getDateByStr("2014-05-19 23:59:59", "yyyy-MM-dd HH:mm:ss");
		long nowtime=new Date().getTime();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shengyuTimelong", endDate.getTime()-nowtime);
		Calendar cal = Calendar.getInstance();
	    int day = cal.get(Calendar.DATE);
	    int month = cal.get(Calendar.MONTH) + 1;
	    int year = cal.get(Calendar.YEAR);
	    int hour=cal.get(Calendar.HOUR_OF_DAY);
	    int minute=cal.get(Calendar.MINUTE);
	    int second=cal.get(Calendar.SECOND);
	    map.put("day", day);
	    map.put("month", month);
	    map.put("year", year);
	    map.put("hour", hour);
	    map.put("minute", minute);
	    map.put("second", second);
	   printRtn(map);
	}
	

	/**
	 * 1,2,3,4,5,6,7,8 等奖；(1:卡当u型枕，2：pad min,3:旅游产品，4:户外大礼包，5：JACK WOLFSKIN 旅行盥洗包，6：10元7:JACK WOLFSKIN 登山帽子.8:虎贝尔安全座椅)
	 * 
	 * @return
	 */
	public static int huojiangRandom() {
		/**
		 * 5月15-18号抽奖逻辑 ipad
		 */
		int ganlv = (int) (1+Math.random() * 100000);
 		Date crrentDate=new Date();
		Date crrentDateForYMD=DateUtil.getTodayYMDDate();
		Date begin = DateUtil.stringToDate("2014-5-15 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date end = DateUtil.stringToDate("2014-5-18 23:59:59", "yyyy-MM-dd HH:mm:ss");
		String key = "";
		if (crrentDate.getTime() > begin.getTime()
				&& crrentDate.getTime() < end.getTime()) {
			
			if(ganlv>=1&&ganlv<=10&&crrentDateForYMD.equals(DateUtil.stringToDate("2014-5-15", "yyyy-MM-dd"))){
				key = "2014-5-15IpadCache";
				if(MemcachedUtil.getInstance().get(key) == null){
					try {
						MemcachedUtil.getInstance().set(key, 3600*24,"1");
						return 2;
					} catch (Exception e) {}
				}
			}
			if(ganlv>=20&&ganlv<=30&&crrentDateForYMD.equals(DateUtil.stringToDate("2014-5-16", "yyyy-MM-dd"))){
				key = "2014-5-16IpadCache";
				if(MemcachedUtil.getInstance().get(key) == null){
					try {
						MemcachedUtil.getInstance().set(key, 3600*24,"1");
						return 2;
					} catch (Exception e) {}
				}
			}
			if(ganlv>=40&&ganlv<=50&&crrentDateForYMD.equals(DateUtil.stringToDate("2014-5-17", "yyyy-MM-dd"))){
				key = "2014-5-17IpadCache";
				if(MemcachedUtil.getInstance().get(key) == null){
					try {
						MemcachedUtil.getInstance().set(key, 3600*24,"1");
						return 2;
					} catch (Exception e) {}
				}
			}
			if(ganlv>=60&&ganlv<=70&&crrentDateForYMD.equals(DateUtil.stringToDate("2014-5-18", "yyyy-MM-dd"))){
				key = "2014-5-18IpadCache";
				if(MemcachedUtil.getInstance().get(key) == null){
					try {
						MemcachedUtil.getInstance().set(key, 3600*24,"1");
						return 2;
					} catch (Exception e) {}
				}
			}
			
 		}
		/**
		 * 5月19号抽奖逻辑 ipad
		 */
		Date begin519 = DateUtil.stringToDate("2014-5-19 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date end519 = DateUtil.stringToDate("2014-5-19 23:59:59", "yyyy-MM-dd HH:mm:ss");
		if(crrentDate.getTime() > begin519.getTime()
				&& crrentDate.getTime() < end519.getTime()){
 			if(ganlv>=80&&ganlv<=90){
 				key = "2014-5-19IpadCache";
				if(MemcachedUtil.getInstance().get(key) == null){
					try {
						MemcachedUtil.getInstance().set(key, 3600*24,"1");
						return 2;
					} catch (Exception e) {}
				}
			}else if(ganlv>=1&&ganlv<=20){
				return 3;
			}
			Date tenHour = DateUtil.stringToDate("2014-5-19 10:00:00", "yyyy-MM-dd HH:mm:ss");
			/**
			 * 十点开始每小时发放  4:JACK WOLFSKIN户外大礼包，5：JACK WOLFSKIN 旅行盥洗包， 7:JACK WOLFSKIN 登山帽子.8:虎贝尔安全座椅
			 */
			if(crrentDate.getTime()>=tenHour.getTime()){
				String hourkey = "dacun_519_key_nicky_private_key_2014519_"
						+ DateUtil.getTodayHourDate();
				Long hourkeyLong = (Long) MemcachedUtil.getInstance().get(hourkey);
				if (null==hourkeyLong||(0L<=hourkeyLong&&hourkeyLong<5L)) {
					if(ganlv>=1000&&ganlv<=2000){
						if(hourkeyLong==null){
							hourkeyLong=1L;
						}else{
							hourkeyLong=hourkeyLong+1;
						}
						MemcachedUtil.getInstance().set(hourkey, 3600*24,hourkeyLong);
						return 4;
					}else if(ganlv>2000&&ganlv<=3000){
						if(hourkeyLong==null){
							hourkeyLong=1L;
						}else{
							hourkeyLong=hourkeyLong+1;
						}
						MemcachedUtil.getInstance().set(hourkey, 3600*24,hourkeyLong);
						return 5;
					}else if(ganlv>3000&&ganlv<=4000){
						if(hourkeyLong==null){
							hourkeyLong=1L;
						}else{
							hourkeyLong=hourkeyLong+1;
						}
						MemcachedUtil.getInstance().set(hourkey, 3600*24,hourkeyLong);
						return 7;
					}else if(ganlv>4000&&ganlv<=5000){
						if(hourkeyLong==null){
							hourkeyLong=1L;
						}else{
							hourkeyLong=hourkeyLong+1;
						}
						MemcachedUtil.getInstance().set(hourkey, 3600*24,hourkeyLong);
						return 8;
					}
					
 				}
			}
			
		}

		//其他机会(1,6)
  		if (1000 < ganlv && ganlv < 5000) {
			key = "dacun_519_key_nicky_private_key"
					+ DateUtil.formatDate(new Date(), "yyyy_MM_dd");
			String shuliang = (String) MemcachedUtil.getInstance().get(key);
			if (null == shuliang) {
				MemcachedUtil.getInstance().set(key, 3600*24,"1");
				return 1;
			}
		}
		return 6;
	}


	/**
	 * 1,2,3,4 等奖；(2元，5元，15元，50元)
	 * 红包
	 * @return 获奖奖额
	 */
	public static int hongbaoRandom() {
		int i = (int) (1+Math.random() * 100);
 		if (1 <= i && i <= 60) {
			 return 2;
		}else if(60 <i && i <= 85){
			 return 5;
		}else if(85 <i && i <=95){
			 return 15;
		}else if(95 <i && i <=100){
			return 50;
		}
		return 2;
	}


	/**
	 * 输出返回码
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param object
	 *            Ajax返回的对象
	 * @throws IOException
	 *             IOException
	 */
	protected void printRtn(final Object object) throws IOException  {
		String json = null;
		getResponse().setContentType("text/json; charset=utf-8");
		if (null == object) {
			return;
		} else {
			if (object instanceof java.util.Collection) {
				json = JSONArray.fromObject(object).toString();
			} else {
				json = JSONObject.fromObject(object).toString();
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("返回对象:" + json);
			}
		}
		if (getRequest().getParameter("jsoncallback") == null) {
			getResponse().getWriter().print(json);
		} else {
			getResponse().getWriter().print(
					getRequest().getParameter("jsoncallback") + "(" + json
							+ ")");
		}
	}
}
