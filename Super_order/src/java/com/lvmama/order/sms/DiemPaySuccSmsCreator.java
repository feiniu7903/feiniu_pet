package com.lvmama.order.sms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.po.tmall.OrdTmallMap;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.tmall.OrdTmallMapService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.passport.dao.PassCodeDAO;
import com.lvmama.passport.dao.PassPortCodeDAO;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductBranchItemDAO;
import com.lvmama.prd.dao.ProdProductDAO;

public class DiemPaySuccSmsCreator extends AbstractOrderSmsCreator implements SingleSmsCreator {
	private static final Log log = LogFactory.getLog(DiemPaySuccSmsCreator.class);
	private PassCodeDAO passCodeDAO = (PassCodeDAO)SpringBeanProxy.getBean("passCodeDAO");
	private OrderItemProdDAO orderItemProdDAO = (OrderItemProdDAO)SpringBeanProxy.getBean("orderItemProdDAO");
	private OrderItemMetaDAO orderItemMetaDAO = (OrderItemMetaDAO)SpringBeanProxy.getBean("orderItemMetaDAO");
	private ProdProductDAO prodProductDAO = (ProdProductDAO)SpringBeanProxy.getBean("prodProductDAO");
	private OrderService orderServiceProxy=(OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	private PassPortCodeDAO passPortCodeDAO=(PassPortCodeDAO)SpringBeanProxy.getBean("passPortCodeDAO");
	private ProdProductBranchDAO prodProductBranchDAO = (ProdProductBranchDAO)SpringBeanProxy.getBean("prodProductBranchDAO");
	private ProdProductBranchItemDAO prodProductBranchItemDAO = (ProdProductBranchItemDAO)SpringBeanProxy.getBean("prodProductBranchItemDAO");
	private OrdTmallMapService ordTmallMapService = (OrdTmallMapService) SpringBeanProxy.getBean("ordTmallMapService");
	private String content;
	private String visitDate;
	private String latestUseTime;
	private String addCode;
	private String code;
	private OrdOrder order;
	private PassCode passCode;
	private boolean timingFlag;
	
	public DiemPaySuccSmsCreator(Long codeId, String mobile) {
		this.mobile = mobile;
		passCode = passCodeDAO.getPassCodeByCodeId(codeId);
		objectId=passCode.getOrderId();
		order = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
	}
	public DiemPaySuccSmsCreator(Long codeId, String mobile,boolean timingFlag) {
		this.timingFlag = timingFlag;
		this.mobile = mobile;
		passCode = passCodeDAO.getPassCodeByCodeId(codeId);
		objectId=passCode.getOrderId();
		order = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
	}

	@Override
	Map<String, Object> getContentData() {
		Map<String, Object> data = new HashMap<String, Object>();
		code = passCode.getCode();
		addCode = passCode.getAddCode();
		//非不定期
		if(!order.IsAperiodic()) {
			String format="yyyy-MM-dd";
			if(order.hasTodayOrder()){
				latestUseTime = "最早换票时间："+DateUtil.getFormatDate(order.getVisitTime(), "HH:mm")+",最晚换票时间:"+DateUtil.getFormatDate(order.getLatestUseTime(), "HH:mm");
			}
			visitDate = DateUtil.getFormatDate(order.getVisitTime(), format);
		}
		if (passCode.isForOrder()) {
			Set<Long> productIdSet=new HashSet<Long>();
			List<PassPortCode> passPortCodelist=passPortCodeDAO.searchPassPortByCodeId(passCode.getCodeId());
			String targetIdStr = "";
			for(PassPortCode	passPortCode : passPortCodelist) {
				targetIdStr = targetIdStr + passPortCode.getTargetId() + ",";
			}
			CompositeQuery compositeQuery = new CompositeQuery();
			compositeQuery.getPageIndex().setBeginIndex(0);
			compositeQuery.getPageIndex().setEndIndex(1000000000);
			compositeQuery.getMetaPerformRelate().setTargetId(targetIdStr);
			compositeQuery.getMetaPerformRelate().setOrderId(order.getOrderId());
			List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
			for(OrdOrderItemMeta ordOrderItemMeta:orderItemMetas){
				Long orderItemProdId=ordOrderItemMeta.getOrderItemId();
				OrdOrderItemProd itemProd=orderItemProdDAO.selectByPrimaryKey(orderItemProdId);
				if(itemProd.isNeedSendSms()){
					productIdSet.add(itemProd.getProductId());
				}
			}
		//	List<OrdOrderItemProd> items = orderItemProdDAO.selectByOrderId(objectId);
			//List<Long> productIdList = this.getOrdOrderProductId(items); 
			for (Long productId : productIdSet) {
				ProdProduct product = prodProductDAO.selectByPrimaryKey(productId);
				if (content==null) {
					content = product.getSmsContent();
				}else{
					content = content + "," + product.getSmsContent();
				}
			}
		}else if (passCode.isForOrderItemMeta()){
			OrdOrderItemMeta itemMeta = orderItemMetaDAO.selectByPrimaryKey(passCode.getObjectId());
			OrdOrderItemProd ordOrderItemProd = orderItemProdDAO.selectByPrimaryKey(itemMeta.getOrderItemId());
			boolean isNeedSendSms=false;
			for(OrdOrderItemProd itemProd:this.order.getOrdOrderItemProds() ){
				if (itemProd.isNeedSendSms()){
					isNeedSendSms=true;
					break;
				}
			}
			if (isNeedSendSms) {
				ProdProduct product = prodProductDAO.selectByPrimaryKey(ordOrderItemProd.getProductId());
				if (content==null) {
					content = product.getSmsContent();
				}else{
					content = content + "," + product.getSmsContent();
				}
				if(!order.IsAperiodic()) {
					data.put("sendTime", itemMeta.getVisitTime());
				}
			}
		}
		if(addCode!=null){
			if(order.IsAperiodic()) {
				data.put("addCode", "密码券:"+addCode+",");
			} else {
				data.put("addCode", "辅助码:"+addCode+",");
			}
		}else {
			data.put("addCode", "");
		}
		
		if ("BASE64".equalsIgnoreCase(code)){//对于苏州乐园等发送彩信的服务商，把CODE值设置为
			data.put("code", "");
			data.put("codeImage", passCode.getCodeImage());
		}else{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("codeId", passCode.getCodeId());
			params.put("_startRow",0);
			params.put("_endRow",1000);
			
			List<PassCode> passCodes = passCodeDAO.selectByParams(params);
			if(passCodes!=null && passCodes.size()==1){
				if(passCodes.get(0).getProviderName().startsWith("LVMAMA QR")){
					code+=" , 请提前打开此地址";
				}
			}
			data.put("code", "电子票凭证:\n"+code+",");
		}
		//非不定期
		if(!order.IsAperiodic()) {
			if (passCode.isNeedSendOrderid()){
				data.put("orderId", "订单号:"+objectId);
			}else{
				data.put("orderId", "");
			}
			//设置最晚取消时间，如果没有最晚取消时间,就不给此取消的提示
			data.put("time", order.getLastCancelStr());
			String quantityContent = getAduletAndChild();
			String productQuantityContent = getAduletAndChildByProduct();
			data.put("visitDate", visitDate);
			data.put("latestUseTime", latestUseTime);
			data.put("productQuantityContent", productQuantityContent);
			log.info("productQuantityContent:" + productQuantityContent);
			data.put("quantityContent", quantityContent);
			log.info("quantityContent:" + quantityContent);
			data.put("content", StringUtils.isEmpty(content)?"":content);
		} else {
			String validContent = "";
			String dateFormat = "yyyy-MM-dd";
			for(OrdOrderItemProd ooip : order.getOrdOrderItemProds()) {
				validContent += ooip.getZhBranchName()
						+ ":"
						+ DateUtil.getFormatDate(ooip.getValidBeginTime(),
								dateFormat)
						+ "至"
						+ DateUtil
								.getFormatDate(ooip.getValidEndTime(), dateFormat);
				if(StringUtils.isNotEmpty(ooip.getInvalidDateMemo())) {
					validContent += "(" + ooip.getInvalidDateMemo() + ")";
				}
				validContent += "、";
			}
			data.put("validContent", validContent.substring(0, validContent.length()-1));
			data.put("orderId", objectId);
			//淘宝需要取产品名称和数量 add by shihui
			if(StringUtils.isNotEmpty(order.getChannel()) && Constant.CHANNEL.TAOBAL.name().equalsIgnoreCase(order.getChannel())) {
				OrdTmallMap map = ordTmallMapService.selectByLvOrderId(objectId);
				if(map!=null) {
					ProdProduct product = prodProductDAO.selectByPrimaryKey(map.getProductId());
					data.put("productName", product.getProductName());
					data.put("buyNum", map.getBuyNum());
				}
			}
			data.put("content", "," + (StringUtils.isEmpty(content)?"":content));
		}
		return data;
	}
	private String getAduletAndChildByProduct(){

		List<PassPortCode> passPortCodelist=passPortCodeDAO.searchPassPortByCodeId(passCode.getCodeId());
		String targetIdStr = "";
		for(PassPortCode	passPortCode : passPortCodelist) {
			targetIdStr = targetIdStr + passPortCode.getTargetId() + ",";
		}
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		compositeQuery.getMetaPerformRelate().setTargetId(targetIdStr);
		compositeQuery.getMetaPerformRelate().setOrderId(order.getOrderId());
		List<OrdOrderItemMeta> orderItemMetas =null;
		StringBuffer resultProductName = new StringBuffer();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", order.getMainProduct().getProductId());
		List<ProdProduct> prodProducts =  prodProductDAO.selectProductByParms(params);
		if(prodProducts.size()>0){

			resultProductName.append(prodProducts.get(0).getProductName()+" " );
			List<OrdOrderItemProd> ordOrderItemProds = order.getOrdOrderItemProds();
			List<ProdProductBranchItem> prodProductBranchItems = null;
			ProdProductBranch prodProductBranch =null;
			Map<Long,Boolean> executeProds = new HashMap<Long, Boolean>();//已经执行的类别
			for (OrdOrderItemProd ordOrderItemProd : ordOrderItemProds) {
				if(executeProds.get(ordOrderItemProd.getProdBranchId())==null ||executeProds.get(ordOrderItemProd.getProdBranchId())==false ){
					executeProds.put(ordOrderItemProd.getProdBranchId(), true);
					prodProductBranch = prodProductBranchDAO.selectByPrimaryKey(ordOrderItemProd.getProdBranchId());
					orderItemMetas = new ArrayList<OrdOrderItemMeta>();
					prodProductBranchItems = prodProductBranchItemDAO.selectBranchItemByProdBranchId(ordOrderItemProd.getProdBranchId());
					for (ProdProductBranchItem prodProductBranchItem : prodProductBranchItems) {
						compositeQuery.getMetaPerformRelate().setMetaBranchId(prodProductBranchItem.getMetaBranchId());
						orderItemMetas.addAll(orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery));				
					}

					Set<Long> resultOrdItemId = new HashSet<Long>(); 
					for (OrdOrderItemMeta ordItemMeta : orderItemMetas) {
						log.info("getOrderItemId:"+ordItemMeta.getOrderItemId());
						resultOrdItemId.add(ordItemMeta.getOrderItemId());
					}
					List<OrdOrderItemProd> tmpOrdOrderItemProds = new ArrayList<OrdOrderItemProd>();
					tmpOrdOrderItemProds.add(ordOrderItemProd);
					if(orderItemMetas.size()>0){
						resultProductName.append("\r"+prodProductBranch.getBranchName());
						resultProductName.append(":(");
						resultProductName.append(this.getAduletAndChild(orderItemMetas, resultOrdItemId,tmpOrdOrderItemProds));
						resultProductName.append(")");
					}
				}
				
			}
		}
		return resultProductName.toString();
	}
	/**
	 * 查找订单里的订购数，成人数，和儿童数
	 * @param itemMetas
	 * @return
	 */
	private String getAduletAndChild(){
		List<PassPortCode> passPortCodelist=passPortCodeDAO.searchPassPortByCodeId(passCode.getCodeId());
		String targetIdStr = "";
		for(PassPortCode	passPortCode : passPortCodelist) {
			targetIdStr = targetIdStr + passPortCode.getTargetId() + ",";
		}
		CompositeQuery compositeQuery = new CompositeQuery();
		compositeQuery.getPageIndex().setBeginIndex(0);
		compositeQuery.getPageIndex().setEndIndex(1000000000);
		compositeQuery.getMetaPerformRelate().setTargetId(targetIdStr);
		compositeQuery.getMetaPerformRelate().setOrderId(order.getOrderId());
		List<OrdOrderItemMeta> orderItemMetas = orderServiceProxy.compositeQueryOrdOrderItemMetaByMetaPerformRelate(compositeQuery);
		Set<Long> resultOrdItemId = new HashSet<Long>(); 
		for (OrdOrderItemMeta ordItemMeta : orderItemMetas) {
			log.info("getOrderItemId:"+ordItemMeta.getOrderItemId());
			resultOrdItemId.add(ordItemMeta.getOrderItemId());
		}
		return this.getAduletAndChild(orderItemMetas, resultOrdItemId,order.getOrdOrderItemProds());
	}
	/**
	 * 查找指定采购产品在订单里的订购数，成人数，和儿童数
	 * @param itemMetas
	 * @return
	 */
	private  String getAduletAndChild(List<OrdOrderItemMeta> orderItemMetas,Set<Long> resultOrdItemId,List<OrdOrderItemProd> ordOrderItemProd ){
		

		//List<OrdOrderItemProd> ordOrderItemProd = order.getOrdOrderItemProds();
		long adult = 0;
		long child = 0;
		for (OrdOrderItemProd ordItemProd: ordOrderItemProd) {
			for (Long ordItemId : resultOrdItemId) {
				log.info("longValue:"+ordItemProd.getOrderItemProdId().longValue()+":"+ordItemId.longValue());
				if(ordItemProd.getOrderItemProdId().longValue() == ordItemId.longValue()) {
					ProdProductBranch prodProductBranch = prodProductBranchDAO.selectByPrimaryKey(ordItemProd.getProdBranchId());
					
					adult = adult + ordItemProd.getQuantity() * prodProductBranch.getAdultQuantity();
					child = child + ordItemProd.getQuantity() * prodProductBranch.getChildQuantity();
				}
			}
		}
		
		StringBuilder productName=new StringBuilder();
		log.info("orderItemMetas Info : size="+orderItemMetas.size()+",isStudent="+orderItemMetas.get(0).isStudent());
		if(orderItemMetas!=null&&orderItemMetas.size()==1&&orderItemMetas.get(0).isStudent()){
			productName.append("学生人数："+(adult+child));
		}else{
			productName.append("包含人数：");//+adult+"成人，"+child+"儿童"
			if(adult!=0){
				productName.append(adult+"成人");
			}
			if(child!=0){
				if(adult!=0){
					productName.append("，");
				}
				productName.append(child+"儿童");
			}
		}
		log.info("getAduletAndChild:"+productName.toString());
		return productName.toString();
	}
	
	@Override
	public ComSms createSingleSms() {
		if(Constant.CHANNEL.EXPORT_DIEM.name().equalsIgnoreCase(order.getChannel())){
			log.info("orderId:"+order.getOrderId()+"  has EXPORT_DIEM channel. DON'T send sms.");
			return null;
		}
		ComSms comSms = super.createSingleSms();
		//如果是定时的就游玩前一天17点发送
		if(comSms!=null && timingFlag){
			Date sendTime = DateUtil.toDate(DateUtil.formatDate(DateUtil.dsDay_Date(order.getVisitTime(), -1), "yyyyMMdd")+"170000", "yyyyMMddHHmmss");
			comSms.setSendTime(sendTime);
		}
		return comSms;
	}
	@Override
	ProdChannelSms getSmsTemplate() {
		//不定期走自己的短信模板
		if(order.IsAperiodic()) {
			log.info("OrderId:"+objectId+",Order Channel:"+order.getChannel()+",DIEM_APER_PAYMENT_SUCC");
			return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.DIEM_APER_PAYMENT_SUCC.name());
		} else {
			log.info("OrderId:"+objectId+",Order Channel:"+order.getChannel()+",DIEM_PAYMENT_SUCC");
			return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.DIEM_PAYMENT_SUCC.name());
		}
	}
}
