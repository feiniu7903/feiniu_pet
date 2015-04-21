package com.lvmama.back.web.ord.refundMent;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.COM_LOG_CASH_EVENT;

/**
 * 退款单管理
 * 
 * @author zhangwenjun
 */
public class OrdRefundmentAction extends BaseAction {
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
		serachMap.put("status", "APPLY_CONFIRM");
		String userName = this.getSessionUserName();
		serachMap.put("operateName", userName);
		serachMap.put("sysCode", Constant.COMPLAINT_SYS_CODE.SUPER.name());
	}
	
	/**
	 * 查询退款单
	 * @return
	 * @throws ParseException 
	 */
	public void queryRefundmentList(){
		serachMap = formatMap(serachMap);
		Map map = null;
		if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(serachMap.get("sysCode"))) {
			map = initialPageInfoByMap(getOrdRefundMentService().queryVstRefundmentListCount(serachMap), serachMap);
		}else {
			map = initialPageInfoByMap(getOrdRefundMentService().queryRefundmentListCount(serachMap), serachMap);
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
		ordRefundmentList = new ArrayList<OrdRefundment>();
		List<OrdRefundment> list = null;
		if(Constant.COMPLAINT_SYS_CODE.VST.name().equals(serachMap.get("sysCode"))) {
			list = getOrdRefundMentService().queryVstRefundmentList(serachMap);
		}else {
			list = getOrdRefundMentService().queryRefundmentList(serachMap);
		}
		for(int i=0; i<list.size(); i++){
			OrdRefundment ordRefundment = list.get(i);
			List<String> nameList = getOrdRefundMentService().queryManagerNameList(ordRefundment.getOrderId());
			StringBuffer nameSb = new StringBuffer();
			for(int j=0; j<nameList.size(); j++){
				nameSb.append(nameList.get(j));
				if(j != nameList.size()-1){
					nameSb.append(",");
				}
			}
			ordRefundment.setManagerName(nameSb.toString());
			ordRefundmentList.add(ordRefundment);
		}
	}
	
	/**
	 * 取消退款单
	 * @param refundmentId
	 */
	@SuppressWarnings("unchecked")
	public void cancelRefundment(final Long refundmentId){
		final String userName = this.getSessionUser().getUserName();
		ZkMessage.showQuestion("是否确认作废退款单?", new ZkMsgCallBack() {
			public void execute() {
				/**
				 *  修改退款单状态
				 */
				boolean result = getOrdRefundMentService().updateRefundStatus(refundmentId, Constant.REFUNDMENT_STATUS.CANCEL.name());
				
				// 刷新页面
				refreshComponent("search");
				
				if(result){
					
					/**
					 *  添加日志
					 */
					getOrdRefundMentService().insertLog("ORD_REFUNDMENT", refundmentId, refundmentId,
							userName, COM_LOG_CASH_EVENT.updateOrderRefundment.name(),
							"取消退款单", "取消退款单");
					
					alert("作废退款单成功");
				}else{
					alert("作废退款单失败");
				}
			}
		}, new ZkMsgCallBack() {
			public void execute() {
				
			}
		});
		
	}

	public OrdRefundMentService getOrdRefundMentService() {
		return (OrdRefundMentService) SpringBeanProxy.getBean("ordRefundMentService");
	}	
}
