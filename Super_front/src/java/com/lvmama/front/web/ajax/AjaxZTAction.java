package com.lvmama.front.web.ajax;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.user.Annliping;
import com.lvmama.comm.pet.po.user.Annwinnerslist;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.front.web.BaseAction;

public class AjaxZTAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String projectName;
	@Autowired
	private UserUserProxy userUserProxy;
	@Autowired 
	private OrderService orderService;
	private Map<String, Object> param;
	String productIds[] = {"79578","80700","85902","6194","105676","96625","97498","86167","108034","99238","105352","92500","89264","2266","106305","96876","80504","88364","69683","48655","71273","78607","80540","63050","41195","110053","2894","80488","159","109850","23464","47027","6225","78072","104243","96747","3438","72683","25727","5018","96523","110050","6134","25295","29161","26443","79250","65693","87485","85671","77708","102732","99398","94229","104145","96875","41736","96901","106662"};
	private Long money; //捐款钱

	/***
	 * 根据产品id 查询总订单
	 */
	@Action(value = "/zhuanti/getOrderNum")
	public void  getOrder()throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		Date begin = DateUtil.stringToDate("2014-5-29", "yyyy-MM-dd");
		Date endTime = DateUtil.stringToDate("2014-6-15", "yyyy-MM-dd");
		if(productIds==null){
			return ;
		}
		String key = "ZT_orderNum_tongxingtongle";
		Long orderNum =  (Long) MemcachedUtil.getInstance().get(key);
		if(orderNum == null){
		//订单数
			orderNum=orderService.getOrderCountByProductIds(productIds,begin,endTime);
			MemcachedUtil.getInstance().set(key,MemcachedUtil.ONE_HOUR ,orderNum);
		}
		if(orderNum>0){
		     money= orderNum*2+1000;
		     map.put("money", money);
		     printRtn(map);
		}else{
			map.put("money", 1000);
			printRtn(map);
		}
	}
	
	
	/**
	 * 查询获奖名单 根据专题名
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "/zhuanti/getwinnerslist")
	public void getwinnerslist() throws IOException{
		if(projectName==null || projectName.equals("")){
			return ;
		}
		String key = "ANNLISTS_tongxingtongle";
		List<Annwinnerslist> annlists = (List<Annwinnerslist>) MemcachedUtil.getInstance().get(key);
		if(annlists == null){
			param = new HashMap<String, Object>();
			param.put("size", 20);
			param.put("projectName", projectName);
			param.put("flag", 0);
			annlists = this.userUserProxy.queryWinnerslistByMap(param);
			MemcachedUtil.getInstance().set(key,MemcachedUtil.ONE_HOUR,annlists);
		}
		if(annlists!=null)
			printRtn(annlists);
	}
	
	
	/**
	 * 专题抽奖
	 * @throws IOException
	 */
	@Action(value = "/zhuanti/choujiang")
	public void choujiang() throws IOException {
		if(projectName==null || projectName.equals("")){
			return ;
		}
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
		List<Annwinnerslist> list = userUserProxy.queryWinnerslistByUserIdAndProject(this
				.getUser().getId(),this.projectName);
		if (null != list && list.size() > 2) {
			map.put("success", "false");
			map.put("message", "每天只能抽3次");
			printRtn(map);
			return;
		}
		
		int jieguo = huojiangRandom();
		Annwinnerslist annlist = new Annwinnerslist();
		annlist.setUserId(this.getUser().getId());
		annlist.setUserName(this.getUser().getUserName());
		annlist.setCreateDate(new Date());
		annlist.setProjectName(this.projectName);
		if(jieguo!=0){
			annlist.setFlag(0L);
			param = new HashMap<String, Object>();
			param.put("lpId", jieguo);
			param.put("ztname", this.projectName);
			Annliping ann=userUserProxy.queryAnnlipingByParam(param);
			annlist.setLpId(Long.valueOf(jieguo));
			annlist.setLpName(ann.getLpName());
			if(null!=ann&&ann.getLpSpare()>=1){
				if(!userUserProxy.insertZTchoujiang(annlist, ann.getLpType(),getUser(), 1)){
					jieguo = 0;
					annlist.setFlag(1L);
					annlist.setLpId(null);
					annlist.setLpName(null);
					userUserProxy.insertWinnerslist(annlist);
				}
			}else{
				jieguo = 0;
				annlist.setFlag(1L);
				annlist.setLpId(null);
				annlist.setLpName(null);
				userUserProxy.insertWinnerslist(annlist);
			}
		}else{
			annlist.setFlag(1L);
			userUserProxy.insertWinnerslist(annlist);
		}
		map.put("success", "true");
		map.put("winner", this.getWinner(jieguo));
		if(annlist.getLpName()!=null){
			map.put("lpname", annlist.getLpName());
		}
		printRtn(map);
		return;
	}
	
	/**
	 * 中奖结果转成页面对应数字
	 * @param jieguo
	 * @return
	 */
	private int getWinner(int jieguo){
		if(jieguo==9)return 1;
		if(jieguo==10)return 6;
		if(jieguo==11)return 5;
		if(jieguo==12)return 4;
		if(jieguo==13)return 2;
		return 3;
	}
	
	/**
	 * 童心童乐中奖逻辑 9-趣味玩具,10-爱冲印120元冲印券,11-驴妈妈5元全场通用优惠券,
	 * 12-天天果园20元立减券,13-价值1500元的华尔街体验大礼包
	 * @return
	 */
	public static int huojiangRandom() {
		int ganlv = (int) (1+Math.random() * 10000);
		if(ganlv>=1 && ganlv<=10){
			return 9;
		}else if(ganlv>=11 && ganlv <= 30){
			return 10;
		}else if(ganlv>=1001 && ganlv <= 1200){
			return 11;
		}else if(ganlv>=31 && ganlv <= 50){
			return 12;
		}else if(ganlv>=201 && ganlv <= 230){
			return 13;
		}
		return 0;
	}
	
	@Action(value = "/zhuanti/ajaxAddHuoJiangMessage")
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = StringUtil.toUTF8(projectName);
	}

	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}


	public Long getMoney() {
		return money;
	}


	public void setMoney(Long money) {
		this.money = money;
	}
	
}
