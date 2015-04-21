package com.lvmama.back.web.ord.refundMent;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

/**
 * 订单退款申请
 * 
 * @author zhangwenjun
 */
public class OrdRefundApplyAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 查询条件.
	 */
	private Map serachMap=new HashMap();
	/**
	 * 返回结果集
	 */
	private List<OrdRefundment> ordRefundmentList;

	public Map getSerachMap() {
		return serachMap;
	}

	public void setSerachMap(Map serachMap) {
		this.serachMap = serachMap;
	}
	
	public List<OrdRefundment> getOrdRefundmentList() {
		return ordRefundmentList;
	}

	public void setOrdRefundmentList(List<OrdRefundment> ordRefundmentList) {
		this.ordRefundmentList = ordRefundmentList;
	}

	/**
	 * 初始化查询参数.
	 */
	public void doBefore() throws Exception {
		serachMap.put("refundType", "");
		String userName = this.getSessionUserName();
		serachMap.put("userName", userName);
		serachMap.put("sysCode", Constant.COMPLAINT_SYS_CODE.SUPER.name());
	}
	
	/**
	 * 查询退款申请
	 * @return
	 * @throws ParseException 
	 */
	public void queryRefundment(){
		serachMap = formatMap(serachMap);
		Map map = null;
		if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(serachMap.get("sysCode"))) {
			map = initialPageInfoByMap(getOrdRefundMentService().queryVstRefundmentCount(serachMap), serachMap);
		}else {
			map = initialPageInfoByMap(getOrdRefundMentService().queryRefundmentCount(serachMap), serachMap);
		}
		int skipResults=0;
		int maxResults=10;
		if(map.get("skipResults")!=null){
			skipResults=Integer.parseInt(map.get("skipResults").toString());
		}
		if(map.get("maxResults")!=null){
			maxResults=Integer.parseInt(map.get("maxResults").toString());
		}
		serachMap.put("skipResults",skipResults);
		serachMap.put("maxResults",maxResults);
		// 查询状态为申请的退款单
		if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(serachMap.get("sysCode"))) {
			ordRefundmentList = getOrdRefundMentService().queryVstRefundment(serachMap);
		}else {
			ordRefundmentList = getOrdRefundMentService().queryRefundment(serachMap);	
		}
		if(null != ordRefundmentList && ordRefundmentList.size() > 0){
			for(int i=0; i<ordRefundmentList.size(); i++){
				OrdRefundment ordRefundment = ordRefundmentList.get(i);	
				// 根据订单号去查询订单下的所有销售产品
				List<OrdOrderItemProd> prodsList = this.getOrdRefundMentService().queryProds(ordRefundment.getOrderId());
				// 销售产品名称
				StringBuffer prodsName = new StringBuffer();
				// 当销售产品不为空时，将所有的销售产品拼在 一起，用空格隔开
				if(null != prodsList && prodsList.size()>0){
					for(int j=0; j<prodsList.size(); j++){
						OrdOrderItemProd ordOrderItemProd = prodsList.get(j);
						prodsName.append(" " + ordOrderItemProd.getProductName());
					}
				}
				// 将销售产品重新赋值
				ordRefundment.setProductName(prodsName.toString());
			}
		}
	}

	public OrdRefundMentService getOrdRefundMentService() {
		return (OrdRefundMentService) SpringBeanProxy.getBean("ordRefundMentService");
	}	
}
