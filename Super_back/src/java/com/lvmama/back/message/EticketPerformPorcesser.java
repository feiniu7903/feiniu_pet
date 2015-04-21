package com.lvmama.back.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.tmall.OrdTmallMap;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.tmall.OrdTmallMapService;
import com.lvmama.comm.bee.service.tmall.TOPInterface;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.vo.Constant;
import com.taobao.api.ApiException;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.VmarketEticketSendResponse;

/**
 * 电子凭证接收消息处理业务逻辑
 * @author dingming
 *
 */
public class EticketPerformPorcesser implements MessageProcesser {
	private static final Log log = LogFactory.getLog(EticketPerformPorcesser.class);
	private OrderService orderServiceProxy;
	private PassCodeService passCodeService;
	private OrdTmallMapService ordTmallMapService;

	@Override
	public void process(Message message) {
		log.info(message);
		log.info(" EticketPerformPorcesser process..... ");
		if(message.isOrderPerformMsg()){
			PassCode passCode = passCodeService.getPassCodeByOrderIdStatus(message.getObjectId());
			if(passCode!=null){
				callConsume(passCode);
			}
		}else if(message.isPasscodeApplySuccessMsg()){
			PassCode passCode = passCodeService.getPassCodeByCodeId(message.getObjectId());
			if(passCode!=null){
				callBackSendCodeSuccess(passCode);
			}
		}
	}
	
	/**
	 * 如果是淘宝电子凭证平台的订单  执行发码成功回调动作,
	 * 调淘宝接口更改旗帜颜色,并将驴妈妈订单号加上
	 * @param passCode
	 */
	public  void callBackSendCodeSuccess(PassCode passCode){
		String code=passCode.getCode();
			OrdTmallMap ordTmallMap=getTmallOrderByPassCOde(passCode);
			if(ordTmallMap!=null&&ordTmallMap.getToken()!=null){
				try {
					log.info(ordTmallMap.getTmallOrderNo()+" is eticket order begin call eticket send interface!!!");
					VmarketEticketSendResponse response =TOPInterface.eticketSend(Long.valueOf(ordTmallMap.getTmallOrderNo()), code+":"+ordTmallMap.getBuyNum(), ordTmallMap.getToken());
					//如果成功，更改旗帜颜色,并将驴妈妈订单号加上
					if(response.isSuccess()){		
						StringBuffer callbackMemo = new StringBuffer();
						try {  
							TradeFullinfoGetResponse resp = TOPInterface.getFullIfo(ordTmallMap.getTmallOrderNo());// 调淘宝详情接口 得到
							callbackMemo.append("\r\n驴妈妈订单号:").append(ordTmallMap.getLvOrderId());
							if(resp.getTrade().getSellerMemo()!=null){
								TOPInterface.updateMemo(Long.valueOf(ordTmallMap.getTmallOrderNo()), resp.getTrade().getSellerMemo() + "  " + callbackMemo.toString(), 4L);
							}else{
							TOPInterface.updateMemo(Long.valueOf(ordTmallMap.getTmallOrderNo()), "  " + callbackMemo.toString(), 4L);
							}
						} catch (Exception e) {
							log.error(this.getClass(), e);
							
						}		
					}else{
						orderServiceProxy.cancelOrder(ordTmallMap.getLvOrderId(), "电子凭证平台发码回调失败执行废单操作", "system");
						ordTmallMap.setStatus("failure");
						ordTmallMap.setFailedReason("回调发码成功接口失败,执行废单操作");
						ordTmallMapService.updateByOrdSelective(ordTmallMap);
						TOPInterface.updateMemo(Long.valueOf(ordTmallMap.getTmallOrderNo()), "\r\n驴妈妈系统已废单", 2L);
						log.info(ordTmallMap.getLvOrderId()+"废单!!!");
					}
					log.info(ordTmallMap.getTmallOrderNo()+" is eticket order end call eticket send interface!!!");
				} catch (ApiException e) {
					log.error(ordTmallMap.getTmallOrderNo()+"call eticketSend exception");
					orderServiceProxy.cancelOrder(ordTmallMap.getLvOrderId(), "电子凭证平台发码回调失败执行废单操作", "system");
					ordTmallMap.setStatus("failure");
					ordTmallMap.setFailedReason("回调发码成功接口失败,执行废单操作");
					ordTmallMapService.updateByOrdSelective(ordTmallMap);
					TOPInterface.updateMemo(Long.valueOf(ordTmallMap.getTmallOrderNo()), "\r\n驴妈妈系统已废单", 2L);
					log.info(ordTmallMap.getLvOrderId()+"废单!!!");
					log.error(this,e);
				}
		}
	}
	
	/**
	 * 如果是淘宝电子凭证平台的订单  执行核销回调动作
	 * @param orderid
	 */
	public void callConsume(PassCode passCode){
		String code=passCode.getCode();
			OrdTmallMap ordTmallMap=getTmallOrderByPassCOde(passCode);
			if(ordTmallMap!=null&&ordTmallMap.getToken()!=null){
				try {
					log.info(ordTmallMap.getTmallOrderNo()+" begin call consume interface");
					TOPInterface.eticketConsume(Long.valueOf(ordTmallMap.getTmallOrderNo()), code,Long.valueOf(ordTmallMap.getBuyNum()), ordTmallMap.getToken());
					log.info(ordTmallMap.getTmallOrderNo()+" end call consume interface");
				} catch (NumberFormatException e) {
					log.error(ordTmallMap.getTmallOrderNo()+"call eticketCounsume exception");
					log.error(this,e);
				} catch (ApiException e) {
					log.error(ordTmallMap.getTmallOrderNo()+"call eticketCounsume exception");
					log.error(this,e);
				}
			}
	}
	
	/**
	 * 根据passCode获取 ordTmallMap
	 * @param passCode
	 * @return OrdTmallMap
	 */
	public OrdTmallMap getTmallOrderByPassCOde(PassCode passCode){
		Long orderid=passCode.getOrderId();
		OrdOrder ordOrder=orderServiceProxy.queryOrdOrderByOrderId(orderid);
		OrdTmallMap ordTmallMap=null;
		if(ordOrder!=null&&ordOrder.getChannel().equals(Constant.CHANNEL.TAOBAL.name())){
			ordTmallMap=ordTmallMapService.selectByLvOrderId(orderid);
		}
		return ordTmallMap;
	}
	

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setOrdTmallMapService(OrdTmallMapService ordTmallMapService) {
		this.ordTmallMapService = ordTmallMapService;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
	
	

}
