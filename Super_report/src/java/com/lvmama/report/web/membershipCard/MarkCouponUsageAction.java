package com.lvmama.report.web.membershipCard;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jxls.transformer.XLSTransformer;

import org.zkoss.zul.Filedownload;

import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.report.po.CouponUsegeModel;
import com.lvmama.report.service.MarkCouponUsageService;
import com.lvmama.report.web.BaseAction;

public class MarkCouponUsageAction extends BaseAction {
	private static final long serialVersionUID = 863373052839952240L;
	
	private MarkCouponUsageService markCouponUsageService;
	protected Map<String, Object> searchConds = new HashMap<String, Object>();
	private List<CouponUsegeModel> list= new ArrayList<CouponUsegeModel>();
	private List<CodeItem> orderStatusList=new ArrayList<CodeItem>();
	public void doBefore() throws Exception{
		orderStatus();
	}
	
	public void search() throws Exception{
		if(validate()){
			list=markCouponUsageService.query(searchConds);
			count(list);
		}
	}
	/**
	 * 统计
	 * @param list
	 */
	private void count(List<CouponUsegeModel> list){
		Set<Object> channelCount=new HashSet<Object>();
		Set<Object> couponUsageCount=new HashSet<Object>();
		Set<Object> couponCodeCount=new HashSet<Object>();
		int orderCount=0;
		Double amountCount=0d,actualPayCount=0d;
		for(CouponUsegeModel coupon:list){
			channelCount.add(coupon.getChannelName());
			couponUsageCount.add(coupon.getCouponName());
			couponCodeCount.add(coupon.getCouponCode());
			amountCount+=(coupon.getAmount()!=null?coupon.getAmount():0);
			orderCount+=(coupon.getOrderId()!=null?1:0);
			actualPayCount+=(coupon.getActualPayFloat()!=null?coupon.getActualPayFloat():0);
		}
		DecimalFormat df = new DecimalFormat( "#.00"); 
		String floorStr="共有"+channelCount.size()+"个渠道，"+couponUsageCount.size()+
						"个优惠活动，"+couponCodeCount.size()+"个优惠活动号码，优惠活动总金额"+amountCount+
						"元，共"+orderCount+"笔订单，订单总额为"+df.format(actualPayCount)+"元。";
		searchConds.put("floorCount", floorStr);
	}
	public void download() {
		try {
			File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/markCouponUsageTemplate.xls");
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/markCouponUsage.xls";
			
			Map<String,Object> param = new HashMap<String,Object>();

			param.put("list", list);
			param.put("floorCount", searchConds.get("floorCount"));
			
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, param, destFileName);
			File file = new File(destFileName);
			if (file != null && file.exists()) {
				Filedownload.save(file, "application/vnd.ms-excel");
			} else {
				alert("下载失败");
				return;
			}
			alert("下载成功");
		} catch (Exception e) {
			alert(e.getMessage());
		}
	}
	private boolean validate()throws Exception{
		if(searchConds.get("orderId")!=null){
			String  orderId=(String)searchConds.get("orderId");
			if(!(orderId.trim().matches("\\d*"))){
				alert("订单号请输入数字");
				return false;
			};
		}
		return true;
	}
	private  void orderStatus(){
		orderStatusList.add(new CodeItem(null, "请选择"));
		orderStatusList.add(new CodeItem("NORMAL", "正常"));
		orderStatusList.add(new CodeItem("CANCEL", "取消"));
		orderStatusList.add(new CodeItem("FINISHED", "完成"));
		orderStatusList.add(new CodeItem("UNCONFIRM", "未确认结束"));
	}
	public MarkCouponUsageService getMarkCouponUsageService() {
		return markCouponUsageService;
	}

	public void setMarkCouponUsageService(
			MarkCouponUsageService markCouponUsageService) {
		this.markCouponUsageService = markCouponUsageService;
	}

	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}

	public List<CouponUsegeModel> getList() {
		return list;
	}

	public void setList(List<CouponUsegeModel> list) {
		this.list = list;
	}

	public List<CodeItem> getOrderStatusList() {
		return orderStatusList;
	}

	public void setOrderStatusList(List<CodeItem> orderStatusList) {
		this.orderStatusList = orderStatusList;
	}
}
