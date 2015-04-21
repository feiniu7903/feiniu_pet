/**
 * 
 */
package com.lvmama.comm.vo.cert.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkOrderDataRev;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.ord.OrdRefundMentItem;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
public abstract class EbkCertDataBuilder {
	public static final String EBK_CERTIFICATE="EBK_CERTIFICATE";
	public static final String ORD_ORDER="ORD_ORDER";
	private Date currentTime;
	protected final Map<String,Object> params;
	private Log logger = LogFactory.getLog(getClass());
	//备注信息列表
	private List<String> faxMemoList = new ArrayList<String>();
	
	public EbkCertDataBuilder(Map<String, Object> params) {
		super();
		if(logger.isDebugEnabled()){
			logger.debug("current build:"+getClass());
		}
		this.params = params;
		this.order = getObject(ORD_ORDER);
		this.certificate = getObject(EBK_CERTIFICATE);
		this.currentTime = new Date();
		
	}

	protected OrdOrder order;
	protected EbkCertificate certificate;
	private List<EbkOrderDataRev> list;
	public void makeData(){
		list = new ArrayList<EbkOrderDataRev>();
		for(EbkCertificateItem item:certificate.getEbkCertificateItemList()){
			makeCertItemData(item);
			if(!updateOnlyItems&&!certificate.isTicket()){
				addMemo(item.getFaxMemo());
			}
		}
		if(!updateOnlyItems){
			makeCertData();
		}
	}
	
	protected final void addData(EbkOrderDataRev rev){
		list.add(rev);
	}
	/**
	 * 
	 * @param itemId 可为空，为空时代表是当凭证的对象
	 * @param dataType
	 * @param value
	 */
	protected final void addData(Long itemId,Constant.EBK_CERT_OBJ_TYPE dataType,String value){
		EbkOrderDataRev rev = new EbkOrderDataRev();
		rev.setCreateTime(currentTime);
		rev.setDataType(dataType.name());
		rev.setEbkCertificateId(certificate.getEbkCertificateId());
		rev.setEbkCertificateItemId(itemId);
		rev.setValue(value);
		addData(rev);
	}
	
	protected final void addData(Long itemId,Constant.EBK_CERT_OBJ_TYPE dataType,Map<String,Object> map){
		JSONObject obj=JSONObject.fromObject(map);
		addData(itemId,dataType,obj.toString());
	}
	
	/**
	 * 生成一个凭证的数据
	 */
	private void makeCertData(){
		if(!certificate.isTicket()){
			makeTraveller(null);
		}
		Map<String,Object> map = getCertBaseInfo();
		if(map==null){
			map = new HashMap<String, Object>();
		}
		if(Constant.EBK_CERTIFICATE_TYPE.CANCEL.name().equals(certificate.getEbkCertificateType())){
			map.put("refundMentInfo", "");
		}
		
		BCertificateTargetService bCertificateTargetService = SpringBeanProxy.getBean(BCertificateTargetService.class,"bCertificateTargetService");
		SupBCertificateTarget target = bCertificateTargetService.getBCertificateTargetByTargetId(certificate.getTargetId());
		map.put("showSettlementFlag", target.getShowSettlePriceFlag());		
		if(!certificate.isTicket()||certificate.hasEbkCertificateTypeChange()){
			map.put("orderId", order.getOrderId());
			map.put("faxMemo", getFaxMemoStr());
		}
		JSONObject obj=JSONObject.fromObject(map);
		addData(null,Constant.EBK_CERT_OBJ_TYPE.EBK_CERTIFICATE,obj.toString());
	}
	/**
	 * 得到所有的退款信息
	 * 该信息实时计算
	 * @param ordOrderItemMetaId
	 * @return
	 */
	private String getOrdRefundMentInfo(EbkCertificate ebkCertificate){
		OrdRefundMentService ordRefundMentService = SpringBeanProxy.getBean(OrdRefundMentService.class,"ordRefundMentService");
		StringBuffer sb =new StringBuffer();
		for(EbkCertificateItem certificateItem:ebkCertificate.getEbkCertificateItemList()){
			List<OrdRefundMentItem> list = ordRefundMentService.queryRefundMentItem(certificateItem.getOrderItemMetaId());
			for(OrdRefundMentItem item:list){
				OrdRefundment ordRefundment =ordRefundMentService.findOrdRefundmentById(item.getRefundmentId());
				if(ordRefundment.isRefunded()||StringUtils.equalsIgnoreCase(Constant.REFUNDMENT_STATUS.REFUND_VERIFIED.name(), ordRefundment.getStatus())){
					if(Constant.REFUND_ITEM_TYPE.SUPPLIER_BEAR.name().equals(item.getType())){
						sb.append(Constant.REFUND_ITEM_TYPE.SUPPLIER_BEAR.getCnName());
						sb.append(PriceUtil.convertToYuan(item.getAmount()));
					}else if(Constant.REFUND_ITEM_TYPE.VISITOR_LOSS.name().equals(item.getType())){
						sb.append(Constant.REFUND_ITEM_TYPE.VISITOR_LOSS.getCnName());
						sb.append(PriceUtil.convertToYuan(item.getActualLoss()));
					}
					sb.append("元;");
				}
			}
		}
		if(sb.length()>0){
			sb.setLength(sb.length()-1);
			sb.append("。");
		}
		if(sb.length()<1){
			return "";
		}
		return sb.toString();
	}
	
	protected Map<String,Object> getCertBaseInfo(){
		return null;
	}
	
	protected void makeTraveller(Long ebkCertificateItemId){
		List<OrdPerson> travellerList = order.getTravellerList();

		for(OrdPerson person:travellerList){
			JSONObject obj = new JSONObject();
			obj.put("name", person.getName());
			obj.put("zhCertType", person.getZhCertType());
			obj.put("mobile", person.getMobile());
			obj.put("certNo", person.getCertNo());
			if(certificate.isHotel()){
				obj.put("pinyin", person.getPinyin());
			} 
			obj.put("id", IdentityUtil.makePersonId(person));
			addData(ebkCertificateItemId, Constant.EBK_CERT_OBJ_TYPE.ORD_PERSON, obj.toString());
		}
	}
	
	/**
	 * 生成一个子项当中的数据
	 * @param item
	 */
	protected abstract void makeCertItemData(EbkCertificateItem item);
	
	@SuppressWarnings("unchecked")
	protected <T>T getObject(String key){
		return (T)params.get(key);
	}
	
	protected Map<String,Object> converProductName(String str,Map<String,Object> map){
		int start=str.lastIndexOf("(");
		String sub=str.substring(start+1);
		int count=StringUtils.countMatches(sub, ")");
		while(count-->1){
			start=str.lastIndexOf("(",start-1);
		}
		String metaBranchName = str;
		String metaProductName = str;
		if(start != -1){
			metaBranchName = str.substring(start+1,str.length()-1);
			metaProductName = str.substring(0,start);
		}
		
		map.put("metaBranchName", metaBranchName);
		map.put("metaProductName", metaProductName);
		return map;
	}
	
	/**
	 * 取产品的显示的单价
	 * @param itemMeta
	 * @param itemProd
	 * @return
	 */
	protected Float getPrice(OrdOrderItemMeta itemMeta,OrdOrderItemProd itemProd){
		if(order.isPayToLvmama()){
			return itemMeta.getActualSettlementPriceYuan();
		}else {
			if(itemProd != null){
				return itemProd.getPriceYuan();
			}else {
				logger.error("OrdOrderItemProd is null;orderId:"+order.getOrderId());
				return 0F;
			}
		}
	}
	
	protected Float getTotalAmount(OrdOrderItemMeta itemMeta,OrdOrderItemProd itemProd){
		if(order.isPayToLvmama()){
			return itemMeta.getTotalSettlementPriceYuan();
		}else{
			//TODO 单房型的需要单独考虑？？
			if(itemProd != null){
				return itemProd.getPriceYuan()*itemProd.getQuantity();
			}else {
				logger.error("OrdOrderItemProd is null;orderId:"+order.getOrderId());
				return 0F;
			}
		}
	}
	
	protected OrdOrderItemProd getItemProd(final Long ordOrderItemProd){
		OrdOrderItemProd itemProd = (OrdOrderItemProd)CollectionUtils.find(order.getOrdOrderItemProds(), new Predicate() {			
			@Override
			public boolean evaluate(Object arg0) {
				return ordOrderItemProd.equals(((OrdOrderItemProd)arg0).getOrderItemProdId());
			}
		});
		return itemProd;
	}
	
	public List<EbkOrderDataRev> getDataRevList(){
		return list;
	}
	
	/**
	 * 与支付对象中文有区别
	 * @return
	 */
	protected String getCollect(){
		if(order.isPayToLvmama()){
			return "驴妈妈收款";
		}else{
			return "供应商收款";
		}
	}
	
	/**
	 * 添加备注信息
	 * @param faxMemo
	 */
	protected void addMemo(final String faxMemo){
		if(StringUtils.isEmpty(faxMemo)){
			return;
		}
		String str = (String)CollectionUtils.find(faxMemoList, new Predicate() {
			
			@Override
			public boolean evaluate(Object arg0) {
				return StringUtils.equals(faxMemo, ((String)arg0));
			}
		});
		if(str==null){
			faxMemoList.add(faxMemo);
		}
	}
	
	private String getFaxMemoStr(){
		if(faxMemoList.isEmpty()){
			return "";
		}
		StringBuffer sb =new StringBuffer();
		for(String s:faxMemoList){
			sb.append(s);
			sb.append(",");
		} 
		if(sb.length()>0){
			sb.setLength(sb.length()-1);
		}
		return sb.toString();
	}
	
	protected void addMetaProductName(OrdOrderItemMeta meta,Map<String,Object> map){
		if(StringUtils.isEmpty(metaProductName)){//无值时直接替换
			metaProductName = (String)map.get("metaProductName");
			return;
		}
		if(meta.isRouteProductType()){
			if(ArrayUtils.contains(firstBranchType,meta.getBranchType())){
				metaProductName = (String)map.get("metaProductName");
			}
		}else if(meta.isTicketProductType()){
			if(!ProductUtil.Hotel.EXTRABED.name().equals(meta.getBranchType())){
				metaProductName = (String)map.get("metaProductName");
			}
		}
	}
	
	protected String getDate(Date visitTime){
		if(order.IsAperiodic()&&visitTime==null){
			return "";
		}else{
			return DateUtil.formatDate(visitTime, "yyyy-MM-dd");
		}
	}
	
	private final String[] firstBranchType={ProductUtil.Route.ADULT.name(),ProductUtil.Route.CHILD.name()};
	
	/**
	 * 只修改子项数据
	 */
	public void updateOnlyItems(){
		updateOnlyItems = true;
	}
	boolean updateOnlyItems=false;
	protected String metaProductName;
}