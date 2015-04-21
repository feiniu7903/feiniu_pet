package com.lvmama.comm.pet.po.ebkpush;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.vo.ord.OrdOrderPerformResourceVO;
import com.lvmama.comm.pet.po.ebkpush.OrderHead;
import com.lvmama.comm.pet.po.ebkpush.OrderMeta;
import com.lvmama.comm.pet.po.ebkpush.OrderPerson;

public class ModelUtils {
	
	public static List<Object> buildSendDatas(List<OrdOrderPerformResourceVO> performResourcesList ){
		List<Object> list = new ArrayList<Object>();
		for (OrdOrderPerformResourceVO ordOrderPerformResourceVO : performResourcesList) {
			Map<String,Object> itemMap = new HashMap<String,Object>();
			OrderHead oh = new OrderHead();
			oh.setAddCode(ordOrderPerformResourceVO.getAddCode());
			oh.setAddCodeStatus(ordOrderPerformResourceVO.getAddCodeStatus());
			oh.setInvalidTime(ordOrderPerformResourceVO.getInvalidTime());
			oh.setOrderId(ordOrderPerformResourceVO.getOrderId());
			oh.setOrderViewStatus(ordOrderPerformResourceVO.getOrderViewStatus());
			oh.setPayTo(ordOrderPerformResourceVO.getPayTo());
			oh.setValidTime(ordOrderPerformResourceVO.getValidTime());
			oh.setInvalidDate(ordOrderPerformResourceVO.getInvalidDate());
			oh.setInvalidDateMemo(ordOrderPerformResourceVO.getInvalidDateMemo());
		
			List<OrderMeta> metaList = new ArrayList<OrderMeta>();
			for (OrdOrderItemMeta ordOrderItemMeta : ordOrderPerformResourceVO.getOrdOrderItemMetaList()) {
				OrderMeta om = new OrderMeta();
				om.setChildQuantity(ordOrderItemMeta.getChildQuantity().intValue());
				om.setAdultQuantity(ordOrderItemMeta.getAdultQuantity().intValue());
				om.setQuantity(ordOrderItemMeta.getQuantity().intValue());
				om.setOrderId(ordOrderItemMeta.getOrderId());
				om.setOrderItemMetaId(ordOrderItemMeta.getOrderItemMetaId());
				//add by gaoxin 2014/3/30
				om.setMetaProductId(ordOrderItemMeta.getMetaProductId());
				om.setMetaBranchId(ordOrderItemMeta.getMetaBranchId());
				om.setSellPrice(ordOrderItemMeta.getSellPrice().intValue());
				
				om.setProductName(ordOrderItemMeta.getProductName());
				om.setSettlementPrice(ordOrderItemMeta.getSettlementPrice());
				om.setTotalAdultQuantity(ordOrderItemMeta.getTotalAdultQuantity());
				om.setTotalChildQuantity(ordOrderItemMeta.getTotalChildQuantity());
				om.setProductQuantity(ordOrderItemMeta.getProductQuantity());
				om.setTotalQuantity(ordOrderItemMeta.getTotalQuantity());
				metaList.add(om);
			}
			List<OrderPerson> personList = new ArrayList<OrderPerson>();
			for (OrdPerson ordPerson : ordOrderPerformResourceVO.getOrdPersonList()) {
				
				if (ordPerson.getPersonId()==null){
					continue;
				}
				
				OrderPerson op = new OrderPerson();
				op.setCertNo(ordPerson.getCertNo());
				op.setCerType(ordPerson.getCertType());
				op.setFullAddress(ordPerson.getFullAddress());
				op.setMobile(ordPerson.getMobile());
				op.setName(ordPerson.getName());
				op.setOrderId(ordOrderPerformResourceVO.getOrderId());
				op.setPersonId(ordPerson.getPersonId());
				op.setPersonType(ordPerson.getPersonType());
				op.setZhCertType(ordPerson.getZhCertType());

				personList.add(op);
			}
			
			itemMap.put("metas", metaList);
			itemMap.put("persons", personList);

			itemMap.put("baseInfo", oh);
			list.add(itemMap);
		}
		return list;
	}
}
