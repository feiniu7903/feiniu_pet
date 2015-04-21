package com.lvmama.clutter.web.baidu;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.service.IBaiduActivityService;
import com.lvmama.clutter.utils.BaiduActivityUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.DateUtil;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.ServletUtil;

@ParentPackage("clutterCreateOrderInterceptorPackage")
@ResultPath("/clutterCreateOrderInterceptor")
@Results({
		@Result(name = "baidu_transfer", location = "/WEB-INF/baiduTransfer.html", type = "freemarker"),
		@Result(name = "baiduLogin", location = "/WEB-INF/pages/baidu/baiduLogin.html", type = "freemarker"),
		@Result(name = "error", location = "/WEB-INF/error.html", type = "freemarker") })
@Namespace("/mobile/bd")
public class BaiduActivityAction extends BaseAction {
	private static final long serialVersionUID = -6164959037367208915L;
	
	
	private String code;
	
	private IBaiduActivityService baiduActivityService;
	
	private String service;
	
	private String productId;
	
	private String branchId;
	
	private String baidutype;//1是框内0是框外
	
	private static String format = "yyyy-MM-dd HH:mm:ss";
	private static final long baiduActStartDate = DateUtil.getDateTime(ClutterConstant.getBaiduActStartDate(),format); // 开始日期
	private static final long baiduActStartDate4Sandby = DateUtil.getDateTime(ClutterConstant.getBaiduActStartDate4Sandby(),format); // 立减票开始日期
	private static final long baiduActEndDate = DateUtil.getDateTime(ClutterConstant.getBaiduActEndDate(),format);// 结束日期
	
	/**
	 * 百度登陆中转页-百度调用
	 * @return
	 */
	@Action("baiduTransfer")
	public String baiduTransfer() {
		return "baidu_transfer";
	}
	/**
	 * 百度登陆中转页-驴妈妈调用
	 * @return
	 */
	@Action("baiduLogin")
	public String baiduLogin() {
		return "baiduLogin";
	}
	/**
	 * 百度登陆后进行绑定/驴妈妈登陆操作
	 */
	@Action("t_baiduLogin")
	public void TobaiduLogin() {
		//获取access_token
		String jsonStr =HttpsUtil.proxyRequestGet("https://openapi.baidu.com/oauth/2.0/token?grant_type="+"authorization_code"+"&client_id="+"4tjhI1dx8WPmCIKtl365x7Xg"+"&client_secret="+"CdRBKcdVQDdZn8AFoBwgZlfyAxd5Es08"+"&redirect_uri="+"http://qing.lvmama.com/static/baidulogin.html"+"&code="+code, InternetProtocol.getRemoteAddr(getRequest()));
		
		Map<String,Object> result =  JSONObject.fromObject(jsonStr);
		
		if(result!=null && result.get("error")==null){
			String access_token=result.get("access_token")==null ? "" : result.get("access_token").toString();
			String lvsessionid = ServletUtil.getLvSessionId(getRequest(),
					getResponse());
			UserCooperationUser u=baiduActivityService.getCooperationUserByToken(access_token,lvsessionid);
			
			JSONObject resultLogin=new JSONObject();
			if(u!=null){
				//登录成功bd_uid存入cookie
				ServletUtil.addCookie(getResponse(), "bd_uid_order", u.getCooperationUserAccount());
				resultLogin.put("status",true);
			}else{
				resultLogin.put("status",false);
			}
			this.sendAjaxResult(resultLogin.toString());
		}else{
			//获取access_token失败
			this.sendAjaxResult(result.toString());
		}
	}
	/**
	 * 判断商品是否可预订--不可预订倒计时
	 */
	@Action("product_status")
	public void getProductStatus() {
		String productStatus=baiduActivityService.getProductStatus(Long.valueOf(productId));
		JSONObject resultLogin=new JSONObject();
		resultLogin.put("productId",productId);
		resultLogin.put("productStatus",productStatus);
		resultLogin.put("countDown",countDown());
		resultLogin.put("baiduValid", isValidateDate(productId));
		this.sendAjaxResult(resultLogin.toString());
	}
	/**
	 * 判断商品是否可预订--立减票(框内/框外)
	 */
	@Action("product_status_reduce")
	public void productStatusReduce() {
		String productStatus=baiduActivityService.booking4HalfAndSandbyTicket(Long.valueOf(productId),baidutype);
		JSONObject resultLogin=new JSONObject();
		resultLogin.put("productId",productId);
		resultLogin.put("productStatus",productStatus);
		resultLogin.put("baiduValid", isValidateDate(productId));
		this.sendAjaxResult(resultLogin.toString());
	}
	/**
	 * 判断商品是否可预订--立减票(框内/框外)===IOS
	 */
	@Action("product_iosstatus_reduce")
	public void productIosStatusReduce() {
		String productStatus=baiduActivityService.booking4HalfAndSandbyTicket(Long.valueOf(productId),baidutype);
		JSONObject resultLogin=new JSONObject();
		resultLogin.put("productId",productId);
		if("0".equals(baidutype)){
			resultLogin.put("productStatus","1000");//IOS 立减不限制baidutype
		}else{
			resultLogin.put("productStatus",productStatus);
		}
		resultLogin.put("baiduValid", isValidateDate(productId));
		this.sendAjaxResult(resultLogin.toString());
	}
	/**
	 * 百度活动开始时间毫秒数
	 * @return
	 */
	public String countDown(){
		int ststus=BaiduActivityUtils.getAmOrPm();
		
		Calendar c = Calendar.getInstance();
		int h = c.get(Calendar.HOUR_OF_DAY);
		int m = c.get(Calendar.MINUTE);
		int s = c.get(Calendar.SECOND);
		
		Long bookTime=0L;
		if(ststus==0){//am
			bookTime=(long)-(m*60+s)*1000+(21-h)*60*60*1000;
		}else{//pm
			if(h>=0 && h<9){
				bookTime=(long)-(m*60+s)*1000+(9-h)*60*60*1000;
			}else{
				bookTime=(long)-(m*60+s)*1000+(24-h+9)*60*60*1000;
			}
		}
		
		return bookTime.toString();
	}
	public boolean getBaiduFinsshTiming(){
		Calendar c = Calendar.getInstance();
		int m = c.get(Calendar.MONTH)+1;
		int d = c.get(Calendar.DAY_OF_MONTH);
		int h = c.get(Calendar.HOUR_OF_DAY);
		if(m>=5 && d>=3 && h>=9){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 是否有效的日期范围
	 *  baidu.act.startdate=2014-04-03 09:00:00
     *  baidu.act.enddate=2014-05-03 23:59:59
	 * @return  true 日期有效 ；false 日期无效
	 */
	public String isValidateDate(String productId) {
		// 产品id  105030  105022  不需要开始日期限制 
		if("105030".equals(productId) || "105022".equals(productId)) {
			return "0";
		}
		long startDate=0L;
		String type=BaiduActivityUtils.ticketType(productId);
		if("1".equals(type)){//半价开始时间
			startDate=baiduActStartDate;
		}else if("2".equals(type)){//立减开始时间
			startDate=baiduActStartDate4Sandby;
		}else{//其他票
			startDate=System.currentTimeMillis();
		}
		if(System.currentTimeMillis() < startDate) {//未开始
			return "-1";
		}else if(System.currentTimeMillis() > baiduActEndDate){//已结束
			return "1";
		}else{//进行中
			return "0";
		}
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public IBaiduActivityService getBaiduActivityService() {
		return baiduActivityService;
	}
	public void setBaiduActivityService(IBaiduActivityService baiduActivityService) {
		this.baiduActivityService = baiduActivityService;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getBaidutype() {
		return baidutype;
	}
	public void setBaidutype(String baidutype) {
		this.baidutype = baidutype;
	}
	
	
	
	
}
