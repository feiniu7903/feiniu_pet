package com.lvmama.order.sms;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.passport.dao.PassCodeDAO;
import com.lvmama.passport.dao.PassPortCodeDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductBranchItemDAO;
import com.lvmama.prd.dao.ProdProductDAO;

public class ChimelongSmsCreator extends AbstractOrderSmsCreator implements SingleSmsCreator {
	private static final Log log = LogFactory.getLog(ChimelongSmsCreator.class);
	private PassCodeDAO passCodeDAO = (PassCodeDAO)SpringBeanProxy.getBean("passCodeDAO");
	private OrderItemMetaDAO orderItemMetaDAO = (OrderItemMetaDAO)SpringBeanProxy.getBean("orderItemMetaDAO");
	private OrderService orderServiceProxy=(OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	private OrderItemProdDAO orderItemProdDAO = (OrderItemProdDAO)SpringBeanProxy.getBean("orderItemProdDAO");
	private PassPortCodeDAO passPortCodeDAO=(PassPortCodeDAO)SpringBeanProxy.getBean("passPortCodeDAO");
	private ProdProductDAO prodProductDAO = (ProdProductDAO)SpringBeanProxy.getBean("prodProductDAO");
	private ProdProductBranchItemDAO prodProductBranchItemDAO = (ProdProductBranchItemDAO)SpringBeanProxy.getBean("prodProductBranchItemDAO");
	private ProdProductBranchDAO prodProductBranchDAO = (ProdProductBranchDAO)SpringBeanProxy.getBean("prodProductBranchDAO");
	private OrdOrder order;
	private PassCode passCode;
	private String visitDate;
	
	public ChimelongSmsCreator(Long codeId, String mobile) {
		this.mobile = mobile;
		passCode = passCodeDAO.getPassCodeByCodeId(codeId);
		this.objectId=passCode.getOrderId();
		order = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
	}

	@Override
	Map<String, Object> getContentData() {
		log.info("ChimelongSmsCreator getContentData");
		Map<String, Object> data = new HashMap<String, Object>();
		String format="yyyy-MM-dd";
		visitDate = DateUtil.getFormatDate(order.getVisitTime(), format);
		String content=getAduletAndChildByProduct();
		data.put("orderId",objectId);
		data.put("visitDate", visitDate);
		data.put("content",content);
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
						resultProductName.append("【"+prodProductBranch.getBranchName());
						resultProductName.append(this.getAduletAndChild(orderItemMetas, resultOrdItemId,tmpOrdOrderItemProds));
						resultProductName.append("】");
					}
				}
				
			}
		}
		return resultProductName.toString();
	}
	
	/**
	 * 查找指定采购产品在订单里的订购数，成人数，和儿童数
	 * @param itemMetas
	 * @return
	 */
	private  String getAduletAndChild(List<OrdOrderItemMeta> orderItemMetas,Set<Long> resultOrdItemId,List<OrdOrderItemProd> ordOrderItemProd ){
		long adult = 0;
		long child = 0;
		String code="";
		for (OrdOrderItemProd ordItemProd: ordOrderItemProd) {
			for (Long ordItemId : resultOrdItemId) {
				log.info("longValue:"+ordItemProd.getOrderItemProdId().longValue()+":"+ordItemId.longValue());
				if(ordItemProd.getOrderItemProdId().longValue() == ordItemId.longValue()) {
					ProdProductBranch prodProductBranch = prodProductBranchDAO.selectByPrimaryKey(ordItemProd.getProdBranchId());
					
					adult = adult + ordItemProd.getQuantity() * prodProductBranch.getAdultQuantity();
					child = child + ordItemProd.getQuantity() * prodProductBranch.getChildQuantity();
					List<OrdOrderItemMeta> itemMetaList=orderItemMetaDAO.selectByOrderItemId(ordItemId.longValue());
					for (OrdOrderItemMeta ItemMeta : itemMetaList) {
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("objectId", ItemMeta.getOrderItemMetaId());
						params.put("orderId",passCode.getOrderId());
						List<PassCode> passCodelist = passCodeDAO.queryPassCodeByParam(params);
						for(PassCode passCode:passCodelist){
							if (passCode != null && StringUtils.isNotBlank(passCode.getCode())) {
								code = passCode.getCode();
							}
						}
					}
				}
			}
		}
		
		
		StringBuilder productName=new StringBuilder();
		if(code!=""){
			productName.append(" 取票码"+code+"，含");
		}
		log.info("orderItemMetas Info : size="+orderItemMetas.size()+",isStudent="+orderItemMetas.get(0).isStudent());
		if(orderItemMetas!=null&&orderItemMetas.size()==1&&orderItemMetas.get(0).isStudent()){
			productName.append("学生人数："+(adult+child));
		}else{
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
	ProdChannelSms getSmsTemplate() {
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.ORDER_FOR_CHIMELONG.name());
	}
}
