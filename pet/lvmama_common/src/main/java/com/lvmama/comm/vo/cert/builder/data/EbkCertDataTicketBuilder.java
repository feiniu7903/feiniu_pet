/**
 * 
 */
package com.lvmama.comm.vo.cert.builder.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkOrderDataRev;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.service.ord.OrderPersonService;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.cert.builder.EbkCertDataBuilder;
import com.lvmama.comm.vo.cert.builder.IdentityUtil;

/**
 * @author yangbin
 *
 */
public class EbkCertDataTicketBuilder extends EbkCertDataBuilder {

	
	private PerformTargetService performTargetService;
	private OrderPersonService orderPersonService;
	public EbkCertDataTicketBuilder(Map<String, Object> params) {
		super(params);
		performTargetService = SpringBeanProxy.getBean(PerformTargetService.class,"performTargetService");
		orderPersonService = SpringBeanProxy.getBean(OrderPersonService.class,"orderPersonService");
	}

	@Override
	protected void makeCertItemData(EbkCertificateItem item) {
		OrdOrderItemMeta meta = item.getOrderItemMeta();
		Map<String,Object> map = new HashMap<String, Object>();
		converProductName(meta.getProductName(),map);
		OrdOrderItemProd itemProd = getItemProd(meta.getOrderItemId());
		
		map.put("orderId", meta.getOrderId());
		map.put("visitTime", getDate(meta.getVisitTime()));
		map.put("quantity", meta.getQuantity()*meta.getProductQuantity());
		map.put("adultQuantity", meta.getTotalAdultQuantity());
		map.put("childQuantity", meta.getTotalChildQuantity());
		map.put("settlementPrice", getPrice(meta, itemProd));
		map.put("totalSettlementPrice", getTotalAmount(meta, itemProd));
		boolean supentity=false;
		if(StringUtils.equals("true", order.getPhysical())){
			List<SupPerformTarget> targetList = performTargetService.findSuperSupPerformTargetByMetaProductId(meta.getMetaProductId());
			if(CollectionUtils.isNotEmpty(targetList)){
				if(targetList.get(0).getCertificateType().equals(Constant.C_CERTIFICATE_TYPE.SUPENTITY.name())){
					supentity = true;
					map.put("expressAddress", getAddress(meta.getOrderId()));
				}
			}
		}
		map.put("supentity", supentity);
		map.put("id", IdentityUtil.make(item));
		map.put("zhPaymentTarget", getCollect());
		map.put("faxMemo", meta.getFaxMemo());
		JSONObject obj = JSONObject.fromObject(map);
		
		EbkOrderDataRev rev = new EbkOrderDataRev();
		rev.setDataType(Constant.EBK_CERT_OBJ_TYPE.EBK_CERTIFICATE_ITEM.name());
		rev.setValue(obj.toString());
		rev.setEbkCertificateItemId(item.getEbkCertificateItemId());
		rev.setEbkCertificateId(item.getEbkCertificateId());
		
		addData(rev);
		
		
		makeTraveller(item.getEbkCertificateItemId());
	}

	private String getAddress(final Long orderId){
		List<Person> list = orderPersonService.queryPersonByOrderId(orderId);
		if(CollectionUtils.isNotEmpty(list)){
			Person person=(Person)CollectionUtils.find(list, new Predicate() {
				@Override
				public boolean evaluate(Object arg0) {
					return Constant.ORD_PERSON_TYPE.ADDRESS.name().equals(((Person)arg0).getPersonType());
				}
			});
			if(person!=null){
				StringBuffer sb =new StringBuffer();
				sb.append(person.getFullAddress());
				if(StringUtils.isNotEmpty(person.getPostcode())){
					sb.append("(");
					sb.append(person.getPostcode());
					sb.append(")");
				}
				sb.append(person.getName());
				sb.append(person.getMobile());
				return sb.toString();
			}
		}
		return "";
	}
	
}
