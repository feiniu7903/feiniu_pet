package com.lvmama.clutter.job;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.clutter.service.IClientHotelService;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrder;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrderRelateLog;
import com.lvmama.comm.pet.service.mobile.MobileHotelService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.elong.model.GuaranteeRule;
import com.lvmama.elong.model.OrderRelation;

/**
 * 该定时任务就是完成以下这个问题：
 * 当客人入住情况发生变化(增加或减少房间、延住、换酒店等)，系统会在原始订单的基础上生成一张新订单。
 * 本接口即用来查询新老订单的关系，一般使用场景是：老订单状态变化为特定状态(已结账、删除、删除另换酒店)时查询是否有新订单。
 *    同时需要在本地数据库中保持一个标记来表示是否已经请求过该接口，避免重复调用。
 * 须使用https访问本接口。
 *
 * @author qinzubo
 *
 */
public class MobileHotelOrderRelationJob {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	// 常量 
	private MobileHotelService mobileHotelService;
	
	/**
	 * 调用
	 */
	private IClientHotelService  clientHotelService;

	/**
	 * 更新订单的子订单.
	 */
    public void initOrderRelation() {
    	if (Constant.getInstance().isJobRunnable()) {
    		// 查询订单状态为  已结账、删除、删除另换酒店，且没有查询过的订单
    		if ( ClutterConstant.isOcsJobRunnable()) {
    			logger.info("......initOrderRelation  start.....1......");
    			Map<String,Object> params = new HashMap<String,Object>();
    			params.put("validStatusIn", "true"); //  已结账、删除、删除另换酒店
    			params.put("queryRelated", "N"); // 还请求过该接口
    			Long orderCount = mobileHotelService.countMobileHotelOrderList(params);// 订单总数 
    			
    			long size = orderCount/1000 ;// 每次最多只能查询1000条数据 
    			if(orderCount%1000 != 0) {
    				size++;
    			}
    			Page p = new Page(1000, 1);
    		    // 如果总数量大于1000 ，则分页处理。
    			for(long i = 1; i <= size ;i++) {
    				p.setPage(i);
    				params.put("isPaging", "true"); // 是否使用分页
    				params.put("startRows", p.getStartRows());
    				params.put("endRows", p.getEndRows());
    				List<MobileHotelOrder> orderList = mobileHotelService.queryMobileHotelOrderList(params);
    				
    				if(null != orderList && orderList.size() > 0) {
    					for(int j = 0 ; j < orderList.size();j++) {
    						MobileHotelOrder mho = orderList.get(j);
    						boolean hasRelated = true;
    						try {
    							if(null != mho && null != mho.getOrderId()) {
    								// 根据订单id ，查找是否有孩子订单。
    								params.put("orderIds", mho.getOrderId()+"");
    								params.put("relationType", "Child");
    								Map<String,Object> resultMap = clientHotelService.getOrderRelation(params);
    								if(null != resultMap && null != resultMap.get("datas")) {
    									List<OrderRelation> list = (List<OrderRelation>)resultMap.get("datas");
    									if(null != list && list.size() > 0) {
    										
    										// 更新子订单到数据库中，并且。
    										for(int x = 0; x < list.size();x++) {
    											OrderRelation relation = list.get(x);
    											Long childOrderId = relation.getChildId();
    											
    											// 更新 订单
    											params.put("orderId", childOrderId+"");
    											Map<String,Object> orderMap = clientHotelService.orderDetail(params);
    											if(null != orderMap && null != orderMap.get("orderDetailResult")  ) {
    												Map<String,Object> orderDetailMap = (Map<String,Object>)orderMap.get("orderDetailResult") ;
    												if(null != orderDetailMap) {
    													//复制订单内容  
    													MobileHotelOrderRelateLog mhor = copyOrder2OrderRelatedLog(mho);
    													
    													// 把子订单内容更新到订单中，包括orderId 
    													mho.setOrderId(childOrderId);
    													mho.setArrivaldate(ClientUtils.getT(orderDetailMap, "arrivalDate",Date.class));
    													mho.setCancelTime(ClientUtils.getT(orderDetailMap, "cancelTime",Date.class));
    													mho.setCreateTime(new Date());
    													mho.setCurrencyCode(ClientUtils.getT(orderDetailMap, "currencyCode",String.class));
    													mho.setDeparturedate(ClientUtils.getT(orderDetailMap, "departureDate",Date.class));
    													Object obj = orderDetailMap.get("guaranteeRule");
    													if(null != obj && !"null".equals(obj.toString())) {
    													    GuaranteeRule g = (GuaranteeRule)orderDetailMap.get("guaranteeRule");
    														mho.setGuaranteeAmount((long)g.getAmount());
    														mho.setIsGruarantee("Y");
    													} else {
    														mho.setGuaranteeAmount(0l);
    														mho.setIsGruarantee("N");
    													}
    													mho.setIsValid("Y");
    													mho.setMessage("child order");
    													mho.setStatus(ClientUtils.getT(orderDetailMap, "statusCode",String.class));
    													mho.setTotalPrice((((BigDecimal)(null==orderDetailMap.get("totalPrice")?0:orderDetailMap.get("totalPrice"))).multiply(new BigDecimal("100"))).longValue());
    													mho.setUserId(mho.getUserId());
    													// 还有几个字段 
    													mho.setQuantity(Long.valueOf(null == orderDetailMap.get("numberOfRooms") ?"0":orderDetailMap.get("numberOfRooms").toString()));
    													mho.setRoomId(ClientUtils.getT(orderDetailMap, "roomTypeId",String.class));
    													mho.setHotelId(ClientUtils.getT(orderDetailMap, "hotelId",String.class));
    													mho.setQueryRelated("N");
    													mho.setIsChanged("Y");
    													
    													// 保存related信息 ；
    													mobileHotelService.insertMobileHotelOrderRelateLog(mhor);
    													
    													// 更新订单信息
    													mobileHotelService.updateMobileHotelOrder(mho);
    													
    													hasRelated = false;
    												}
    											}    
    										}
    									}
    								}
    								
    								//如果没有孩子订单 ；则更新父订单queryRelated ； QUERY_RELATED ,表示是否查询过 . 
    								if(hasRelated) {
    									mho.setQueryRelated("Y");
    									mobileHotelService.updateMobileHotelOrder(mho);
    									logger.info("......initOrderRelation  updateMobileHotelOrder...mho.setQueryRelated('Y')....9.....orderId==" + mho.getOrderId());
    								}
    							}
    						}catch(Exception e){
    							e.printStackTrace();
    							logger.info("......initOrderRelation  error.....10.....orderId==" + mho.getOrderId());
    						}
    					}
    				}
    			}
    		}
    		logger.info("......initOrderRelation  end.....11......");
    	}
    }
	
    
    
    /**
     * 复制order到orderRelatedLog 
     * @param mho
     * @return
     */
	public MobileHotelOrderRelateLog copyOrder2OrderRelatedLog(MobileHotelOrder mho) {
		MobileHotelOrderRelateLog m = new MobileHotelOrderRelateLog();
		m.setArrivaldate(mho.getArrivaldate());
		m.setCancelTime(mho.getCancelTime());
		m.setCreateTime(mho.getCreateTime());
		m.setCurrencyCode(mho.getCurrencyCode());
		m.setDeparturedate(mho.getDeparturedate());
		m.setGuaranteeAmount(mho.getGuaranteeAmount());
		m.setHotelId(mho.getHotelId());
		m.setIsGruarantee(mho.getIsGruarantee());
		m.setIsValid(mho.getIsValid());
		m.setLvHotelOrderId(mho.getLvHotelOrderId());
		m.setMessage(mho.getMessage());
		m.setOrderId(mho.getOrderId());
		m.setQuantity(mho.getQuantity());
		m.setRoomId(mho.getRoomId());
		m.setStatus(mho.getStatus());
		m.setTotalPrice(mho.getTotalPrice());
		m.setUserId(mho.getUserId());
		
		return m;
	}
	
	public void setMobileHotelService(MobileHotelService mobileHotelService) {
		this.mobileHotelService = mobileHotelService;
	}

	public void setClientHotelService(IClientHotelService clientHotelService) {
		this.clientHotelService = clientHotelService;
	}

}
