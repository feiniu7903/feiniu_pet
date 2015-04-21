package com.lvmama.passport.processor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PassportConstant;

/**
 * 回收回调实现
 * 
 * @author chenlinjun
 * @date:2010-9-25
 */
public class UsedCodeProcessor {
	private static final Log log = LogFactory.getLog(UsedCodeProcessor.class);
	private static final String ORDER = "ORD_ORDER";
	private static final String ORDER_ITEM = "ORD_ORDER_ITEM_META";

	private PassCodeService passCodeService;
	private OrderService orderServiceProxy;
	private ComLogService comLogService;
	/**
	 * 
	 * 回收回调参数列表<br>
	 * 申请流水号（String） 状态（String）
	 */
	public String update(Passport data) {
		PassCode passCode = passCodeService.getCodeBySerialNo(data.getSerialno());
		if(data.isPartPerform()){
			this.passCodeService.updatePassPortCode(passCode,data,true);
		}else{
			this.passCodeService.updatePassPortCode(passCode,data);
		}
		String result = addOrderPerform(data, passCode);
		return result;
	}
	
	/*public String updatePart(Passport data) {
		PassCode passCode = passCodeService.getCodeBySerialNo(data.getSerialno());
		this.passCodeService.updatePartPassCode(passCode, data);
		String result = addOrderPerform(data, passCode);
		return result;
	}*/

	private OrdOrderItemMeta getItemMeta(OrdOrder ordOrder, PassCode passCode) {
		for (OrdOrderItemMeta item : ordOrder.getAllOrdOrderItemMetas()) {
			if (item.getOrderItemMetaId().longValue() == (passCode.getObjectId().longValue())) {
				return item;
			}
		}
		return null;
	}
	private String addOrderPerform(Passport data, PassCode passCode) {
		String flag = PassportConstant.PASSCODE_APPLY_STATUS.SUCCESS.name();
		// 成人数
		Long adultQuantity = Long.valueOf(data.getAdult());
		// 儿童数
		Long childQuantity = Long.valueOf(data.getChild());
		log.info("adultQuantity Total:" + adultQuantity);
		log.info("childQuantity Total:" + childQuantity);
		StringBuilder buf=new StringBuilder();
		buf.append("设备号:"+data.getDeviceId());
		buf.append(",adultQuantity:"+adultQuantity);
		buf.append(",childQuantity:"+childQuantity);
		buf.append(",TargetId:"+data.getOutPortId());
		buf.append(",OrderId:"+passCode.getOrderId());
		
		// 需要更新履行对象属性
		boolean suc = false;
		boolean isQuantityChange = true;
		if (ORDER.equals(passCode.getObjectType())) {
			CompositeQuery compositeQuery = new CompositeQuery();
			compositeQuery.getMetaPerformRelate().setOrderId(passCode.getOrderId());
			compositeQuery.getMetaPerformRelate().setTargetId(data.getOutPortId());
			compositeQuery.getPageIndex().setBeginIndex(0);
			compositeQuery.getPageIndex().setEndIndex(1000000000);
			List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
			if(!orderItemMetas.isEmpty()){
				//对订单子子项做履行
				if(orderItemMetas.size() > 1) {
					for(OrdOrderItemMeta meta : orderItemMetas){
						buf.append(",Perform Method:"+ORDER_ITEM);
						suc = orderServiceProxy.insertOrdPerform(Long.valueOf(data.getOutPortId()), meta.getOrderItemMetaId(), ORDER_ITEM, meta.getTotalAdultQuantity(), meta.getTotalChildQuantity());
						if(!suc){
							break;
						}
					}
				} else if(orderItemMetas.size() == 1){
					if (adultQuantity == 0 && childQuantity == 0) {
						isQuantityChange = false;
						adultQuantity = orderItemMetas.get(0).getTotalAdultQuantity();
						childQuantity = orderItemMetas.get(0).getTotalChildQuantity();
					}
					suc = orderServiceProxy.insertOrdPerform(Long.valueOf(data.getOutPortId()), orderItemMetas.get(0).getOrderItemMetaId(), ORDER_ITEM, adultQuantity, childQuantity);
				}
			}else{
				flag = PassportConstant.PASSCODE_APPLY_STATUS.FAILED.name();
			}
		} else {
			if (adultQuantity == 0 && childQuantity == 0) {
				isQuantityChange = false;
				OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
				OrdOrderItemMeta itemMeta = this.getItemMeta(ordOrder, passCode);
				if (itemMeta != null) {
					adultQuantity = itemMeta.getTotalAdultQuantity();
					childQuantity = itemMeta.getTotalChildQuantity();
				}
			}
			buf.append(",Perform Method:"+passCode.getObjectType());
			suc = orderServiceProxy.insertOrdPerform(Long.valueOf(data.getOutPortId()), passCode.getObjectId(), passCode.getObjectType(), adultQuantity, childQuantity);
		}
		if (suc) {
			flag = PassportConstant.PASSCODE_APPLY_STATUS.SUCCESS.name();
			if((adultQuantity.intValue()>0||childQuantity.intValue()>0) && isQuantityChange){
				if(data.getProvider()!=null&&"NEWLAND".equalsIgnoreCase(data.getProvider())){
					boolean editFlag=orderServiceProxy.editOrder(passCode.getOrderId(), adultQuantity, childQuantity);
					log.info("NEWLAND editOrder result :"+editFlag);
				}
			}
		} else {
			flag = PassportConstant.PASSCODE_APPLY_STATUS.FAILED.name();
		}
		this.passLogs(passCode.getOrderId(), buf.toString(), passCode.getCodeId());
		return flag;
	}
	/**
	 * 通关日志
	 * @param orderId
	 * @param content
	 * @param codeId
	 */
	private void passLogs(Long orderId,String content,Long codeId){
		ComLog log = new ComLog();
		log.setObjectType("PASS_CODE");
		log.setParentId(orderId);
		log.setObjectId(codeId);
		log.setOperatorName("SYSTEM");
		log.setLogType(Constant.COM_LOG_ORDER_EVENT.systemApprovePass.name());
		log.setLogName("设备刷码通关");
		log.setContent(content);
		comLogService.addComLog(log);
	}

	public void callback() {
		// 通关系统和第三系统通信返回数据
		// Map<String, String> data = new HashMap<String, String>();
		// data.put("passport.portId", this.data.getOutPortId());
		// data.put("passport.serialno", this.data.getSerialno());
		// data.put("passport.child", this.data.getChild());
		// data.put("passport.adult", this.data.getAdult());
		// log.info("回收回调处理参数："+data.values());
		// SendRequest sendRequest = new SendRequest();
		// String result = sendRequest.send(new
		// UsedCodeRequest(data,this.data.getUrl()));
	}


	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
 
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

 
}
