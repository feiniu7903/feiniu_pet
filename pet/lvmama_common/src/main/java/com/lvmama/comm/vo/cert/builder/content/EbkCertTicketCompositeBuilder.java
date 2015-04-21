/**
 * 
 */
package com.lvmama.comm.vo.cert.builder.content;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.vo.cert.CertificateItemVo;
import com.lvmama.comm.vo.cert.TicketCertificateItemList;

/**
 * @author yangbin
 *
 */
public class EbkCertTicketCompositeBuilder extends EbkCertCompositeBuilder {

	public EbkCertTicketCompositeBuilder(EbkCertificate newEbkCertBuilder) {
		super(newEbkCertBuilder);
	}

	@Override
	protected void handleCertificateItem(Map<String,Object> map,Map<String,Object> newMap){
		List<CertificateItemVo> list = (List<CertificateItemVo>)newMap.get("items");
		List<CertificateItemVo> oldList = (List<CertificateItemVo>)map.get("items");
		int pos=0;
		for(CertificateItemVo item:list){
			//Map<String,Object> baseInfo = item.getBaseInfo();
			TicketCertificateItemList oldItem = getOrderItem(oldList, item);
			TicketCertificateItemList newItem = TicketCertificateItemList.class.cast(item);
			if(oldItem!=null){
				for(CertificateItemVo child:newItem.getItemVoList()){
					CertificateItemVo oldChild = getOldItem(oldItem.getItemVoList(), child);
					if(oldChild!=null){
						String content = handleMap(oldChild.getBaseInfo(), child.getBaseInfo(),"supentity");
						if(StringUtils.isNotEmpty(content)){
							log.append(child.getBaseInfo().get("metaBranchName"));
							log.append(content);
							log.append("<br/>");
						}
					}else{
						log.append("新增 订单");
						log.append(child.getBaseInfo().get("orderId"));
						log.append(child.getBaseInfo().get("metaBranchName"));
					}
				}
			}
			if(pos==0){
				pos++;
				List<Map<String,Object>> newTraveller = item.getTravellerList();
				List<Map<String,Object>> oldTraveller = oldItem.getTravellerList();
				handleTraveller(oldTraveller,newTraveller);
			}
		}
		//门票不管之前的老数据
	}
	
	/**
	 * 根据订单ID取出子类集合
	 */
	private TicketCertificateItemList getOrderItem(List<CertificateItemVo> oldList,final CertificateItemVo newItem){
		final String key = newItem.getBaseInfo().get("orderId").toString();
		CertificateItemVo item = (CertificateItemVo)CollectionUtils.find(oldList,new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				CertificateItemVo old = (CertificateItemVo)arg0;
				return StringUtils.equals(key, old.getBaseInfo().get("orderId").toString());
			}
		});
		return TicketCertificateItemList.class.cast(item);
	}

	@Override
	protected String[] getSkipEbkCertificateKeys() {
		return new String[]{"faxMemo","orderId"};
	} 
	
	
	
}
