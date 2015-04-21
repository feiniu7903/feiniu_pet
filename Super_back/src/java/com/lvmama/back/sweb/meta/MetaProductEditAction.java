/**
 * 
 */
package com.lvmama.back.sweb.meta;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.back.sweb.prod.ProductException;
import com.lvmama.back.utils.StringUtil;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaProductControl;
import com.lvmama.comm.bee.po.meta.MetaProductTicket;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductControlService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.MetaBranchRelateProdBranch;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.po.work.WorkGroup;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.SupContractService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.service.work.WorkGroupService;
import com.lvmama.comm.utils.CopyUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.FIN_CURRENCY;
import com.lvmama.comm.vst.service.EbkSuperClientService;


/**
 * 采购产品添加的表单
 * @author yangbin
 *
 */

public abstract class MetaProductEditAction<T extends MetaProduct> extends AbstractMetaProductAction{

	private Class<T> metaProductClazz;
	private ComMessageService comMessageService;
	private BCertificateTargetService bCertificateTargetService;
	private SupContractService supContractService;
	private SupplierService supplierService;	
	private List<CodeItem> currencyList;
	private List<SupContract> supContractList=Collections.emptyList();
	private ProdProductBranchService prodProductBranchService;
	private ProdProductService prodProductService;
	private MetaProductBranchService metaProductBranchService;
	private List<WorkGroup> groupNameList;
	private WorkGroupService workGroupService;
	private TopicMessageProducer productMessageProducer;
	private EbkSuperClientService ebkSuperClientService;
	
	private MetaProductControlService metaProductControlService;
	
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	/**
	 * 
	 * @return
	 */
	protected String[] getFieldParams(){
		return null;
	}
	public List<CodeItem> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<CodeItem> currencyList) {
		this.currencyList = currencyList;
	}

	/**
	 * 获取币种集合
	 */
	public void getCurrency() {
		// 查询所有的币种
		FIN_CURRENCY[] currencys = FIN_CURRENCY.values();
		currencyList = new ArrayList<CodeItem>();
		for(FIN_CURRENCY c : currencys){
			currencyList.add(new CodeItem(c.name(), c.getCnName()));
		}
	}
	public MetaProductEditAction(String prodType) {
		super("prod");
		this.prodType=prodType;
		metaProductClazz=(Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		try{
			metaProduct=(T)metaProductClazz.newInstance();
			metaProduct.setProductType(prodType);
		}catch(Exception ex){			
		}
	}
	protected T metaProduct;
	
	protected String prodType;
	private String payToTarget=Constant.PAYMENT_TARGET.TOLVMAMA.name();//默认值
	/**
	 * 
	 */
	private static final long serialVersionUID = -1823762419453084070L;
	
	public abstract String addMetaProduct();	
	
	public String goAfter() {
		// 查询所在组织名称
		groupNameList = workGroupService.queryWorkGroupName();
		return "input";
	}
	
	public void doBefore(){
		if(metaProductId==null||metaProductId<1)
			throw new ProductException();
		
		setMetaProduct((T)metaProductService.getMetaProduct(metaProductId, prodType));
		metaProductType=metaProduct.getProductType();//该值由数据库当中给出
		payToTarget=metaProduct.getPaymentTarget();
		SupSupplier ss=supplierService.getSupplier(metaProduct.getSupplierId());		
		if(ss!=null){
			metaProduct.setSupplierName(ss.getSupplierName());
			if(metaProduct.getContractId()!=null){
				SupContract sc=supContractService.getContract(metaProduct.getContractId());				
				metaProduct.setContractNo(sc.getContractNo());
			}else{
				supContractList = supContractService.selectContractBySupplierId(ss.getSupplierId());
			}
		}
	}
	
	public boolean isNotSwitch(){
		Boolean isCallPRC=false;
		String control = Constant.getInstance().getProperty("vst.ebkSuperClientService");
		log.info("vst RPC 'ebkSuperClientService' service call cfg:"+control);
		if (StringUtils.isNotBlank(control)) {
			isCallPRC=Boolean.valueOf(control);
		}
		return isCallPRC;
	}
	
	public void updateVstEbkSuperProd(){
		log.info("start updateVstEbkSuperProd method");
		if (isNotSwitch()) {
			MetaProduct metaProduct1=metaProductService.getMetaProduct(metaProduct.getMetaProductId());  //采购对象
			List<MetaProductBranch> list=metaProductBranchService.selectBranchListByProductId(metaProduct1.getMetaProductId()); //采购类别
			boolean flag=false;
			for (int i = 0; i < list.size(); i++) {
				List<MetaBranchRelateProdBranch> metaBranchRelateProdBranchList = metaProductBranchService.selectProdProductAndProdBranchByMetaBranchId(Long.valueOf(String.valueOf(list.get(i).getMetaBranchId())));
				//根据采购类别查看关联的销售类别结果集类
				List<Long> productIds= new ArrayList<Long>();
				for (int j = 0; j < metaBranchRelateProdBranchList.size(); j++) {
					MetaBranchRelateProdBranch metaBranchRelateProdBranch = metaBranchRelateProdBranchList.get(j);  //销售类别对象
					if(productIds.contains(metaBranchRelateProdBranch.getProdProductId())){
						continue;
					}
					productIds.add(metaBranchRelateProdBranch.getProdProductId());
					ProdProduct prodProduct=prodProductService.getProdProductById(metaBranchRelateProdBranch.getProdProductId());//销售对象
					String online=prodProduct.getOnLine();
					if (online.equals("true")) {
						flag=true;
						break;
					}
				}
			}
			log.info("update vst updateEbkSuperProd,MetaProductId:"+metaProduct1.getMetaProductId()+",saleFlag:"+flag);
			String flags="N";
			if(flag==true){
				flags="Y";
			}
			Map<MetaProduct,String> map=new HashMap<MetaProduct,String>();
			map.put(metaProduct1, flags);
			log.info("update vst updateEbkSuperProd");
			ArrayList<String> results=ebkSuperClientService.updateEbkSuperProd(map).getReturnContent();
			for (String item : results) {
				String [] strings = item.split("#"); 
				if ("0".equals(strings[1])) {
					log.error("Call vst RPC service 'updateEbkSuperProd' error:"+strings[0]+"  操作失败！");
				}else if("1".equals(strings[1])){
					log.info("Call vst RPC service 'updateEbkSuperProd' "+strings[0]+"  success.");
				}
			}


			/**HashMap<Long,Integer> map1 = results.getReturnContent();
			for(Long key:map1.keySet()){
				if (map1.get(key)==0) {
					log.error("Call vst RPC service 'updateEbkSuperProd' error:"+key+"  操作失败！");
				}else if(map1.get(key)==1){
					log.info("Call vst RPC service 'updateEbkSuperProd' "+key+"  success.");
				}
			}*/
		}
		log.info("end updateVstEbkSuperProd method");
	}
	
	public void saveMetaProduct(){	
		JSONResult result=new JSONResult();
		try{
			metaProduct.setPaymentTarget(payToTarget);
			if(metaProduct.isPaymentToSupplier()&&metaProduct.getIsResourceSendFax()=="false"){
				throw new Exception("支付给供应商的，必须选择资源审核通过后发传真");
			}
			if (Constant.PAYMENT_TARGET.TOLVMAMA.name().equals(metaProduct.getPaymentTarget()) && !Constant.PRODUCT_TYPE.HOTEL.name().equals(metaProduct.getProductType()) && "true".equals(metaProduct.getIsResourceSendFax())) {
				throw new IllegalArgumentException("支付给驴妈妈的非酒店产品只能设置支付完成后生成传真!");
			} 
			if (StringUtil.hasIllegalCharacter(metaProduct.getProductName())){
				throw new IllegalArgumentException("产品名称不可包含'<','>','&'");
			}
			boolean hasNew=true;                                                                                                                                                                                                                                       
			if (metaProduct.getMetaProductId() == null) {
				metaProduct.setCreateUserId(this.getSessionUser().getUserId());
				metaProduct.setOrgId(this.getSessionUser().getDepartmentId());
				Long mid = metaProductService.addMetaProduct(metaProduct, getOperatorNameAndCheck());
				metaProduct.setMetaProductId(mid);
				/**
				 * 向中间表添加酒店产品信息
				 * vst
				 */
				if (isNotSwitch()) {
					log.info("add vst addEbkSuperProd,metaProduct:"+metaProduct);
					if("HOTEL".equals(metaProduct.getProductType())){
						ResultHandleT resultHandleT=ebkSuperClientService.addEbkSuperProd(metaProduct);
						if(resultHandleT.isSuccess()){
							log.info("Call vst RPC service 'addEbkSuperProd' success.");
						}else{
							log.error("Call vst RPC service 'addEbkSuperProd' error:"+resultHandleT.getMsg());
						}
					}
				}
				comMessageService.addSystemComMessage(Constant.EVENT_TYPE.ADD_PRODUCT.name(),"新增采购产品:"+metaProduct.getProductName(), Constant.SYSTEM_USER);
			} else {
				//检查已配置的预控信息是否与预控级别相同
				checkControlType();
				
				checkedResourceSendFax();
				MetaProduct productEntity=metaProductService.getMetaProduct(metaProduct.getMetaProductId(),metaProduct.getProductType());
				if(productEntity==null){
					throw new IllegalArgumentException("更新的产品不存在");
				}
				//如果之前是支付给驴妈妈，但现在支付给供应商就需要查询对应的采购类别是否存在有资源不需要审核的时间价格表
				if(productEntity.isPaymentToLvmama()&&metaProduct.isPaymentToSupplier()){
					if(metaProductService.checkNotNeedResourceConfirm(metaProduct.getMetaProductId())){
						throw new IllegalArgumentException("支付给供应商时间价格表不可以设置为资源需确认:否");
					}
				}
				String able2 = "";
				if(metaProduct.isTicket()) {
					MetaProductTicket t2 = (MetaProductTicket)productEntity;
					able2 = t2.getTodayOrderAble();
				}
				productEntity=merginProduct(metaProduct,productEntity,getRequest().getParameterNames());
				hasNew=false;
				//先不用权限，直接保存
	//			if(Constant.PRODUCT_TYPE.OTHER.name().equals(metaProduct.getProductType())){ 
				metaProductService.updateMetaProduct(productEntity, getOperatorNameAndCheck());
				
				/**
				 * 修改中间表酒店产品信息
				 * vst
				 */
				
				try{
					updateVstEbkSuperProd();
				}catch(Exception ex){
					log.error("updateVstEbkSuperProd:修改中间表酒店产品信息错误！");
					ex.printStackTrace();
				}
				
				
				if(metaProduct.isTicket()) {
					MetaProductTicket t1 = (MetaProductTicket)metaProduct;
					String able1 = t1.getTodayOrderAble();
					if(StringUtils.isNotEmpty(able1) && StringUtils.isNotEmpty(able2)) {
						if(!able1.equalsIgnoreCase(able2)) {
							updateTodayOrderAble(metaProduct.getMetaProductId());
						}
					} else if(StringUtils.isEmpty(able1) && StringUtils.isEmpty(able2)){
					} else {
						updateTodayOrderAble(metaProduct.getMetaProductId());
					}
				}
	//			} else {
	//				metaProductService.updateMetaProductWithTask(metaProduct, getOperatorNameAndCheck());
	//			}
			}
			
			// 发送修改采购产品的消息
			if (Constant.PRODUCT_TYPE.TICKET.name().equals(metaProduct.getProductType())) {
				productMessageProducer.sendMsg(MessageFactory.newMetaProductUpdateMessage(metaProduct.getMetaProductId(), payToTarget));
			}
			
			// 发送修改采购产品为路线类型的消息
			if (Constant.PRODUCT_TYPE.ROUTE.name().equals(metaProduct.getProductType())) {
				productMessageProducer.sendMsg(MessageFactory.newMetaProductTypeByRouteUpdateMessage(metaProduct.getMetaProductId()));
			}
			result.put("metaProductId", metaProduct.getMetaProductId());
			result.put("hasNew", hasNew);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	private void checkControlType() {
		//metaProduct
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("productId", metaProduct.getMetaProductId());
		List<MetaProductControl> cList = metaProductControlService.getProductControlListByCondition(condition);
		if (cList.size() > 0) {
			MetaProductControl control = cList.get(0);
			if (!control.getControlType().equals(metaProduct.getControlType())) {
				throw new RuntimeException("该采购产品已经设置预控,此次设置的预控级别与以前设置不一致,请删除该产品预控信息或保持原有预控级别");
			}
		}
	}
	//更新门票是否当天可预订
	private void updateTodayOrderAble(Long metaProductId) {
		//该采购产品被关联的所有销售类别
		List<ProdProductBranchItem> itemList = prodProductBranchService.selectItemsByMetaProductId(metaProductId);
		for (int i = 0; i < itemList.size(); i++) {
			Long prodBranchId = itemList.get(i).getProdBranchId();
			ProdProductBranch branch = prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
			if(branch != null) {
				String todayOrderAble = "true";
				//查找销售类别关联的采购类别
				List<MetaProductBranch> metaProductBranchList = metaProductBranchService.getMetaProductBranchByProdBranchId(prodBranchId);
				if(!metaProductBranchList.isEmpty()) {
					for (int j = 0; j < metaProductBranchList.size(); j++) {
						Long metaId = metaProductBranchList.get(j).getMetaProductId();
						MetaProduct metaProduct = metaProductService.getMetaProduct(metaId, Constant.PRODUCT_TYPE.TICKET.name());
						if(metaProduct != null) {
							MetaProductTicket mpt = (MetaProductTicket)metaProduct;
							if(!mpt.hasTodayOrderAble()) {
								todayOrderAble = "false";
								break;
							}
						} else {
							todayOrderAble = "false";
							break;
						}
					}
				} else {
					todayOrderAble = "false";
				}
				branch.setTodayOrderAble(todayOrderAble);
				prodProductBranchService.updateByPrimaryKeySelective(branch);
			}
		}
	}
	
	private MetaProduct merginProduct(MetaProduct form,MetaProduct entity,java.util.Enumeration<String> params){
		List<String> field=new ArrayList<String>();
		field.add("paymentTarget");
		if(!ArrayUtils.isEmpty(getFieldParams())){
			for(String param:getFieldParams()){
				field.add(param);
			}
		}
		return CopyUtil.copy(entity, form, params, "metaProduct.",field);
	}
	
	/**
	 * 限定资源审核后发送传真
	 * 1,如果是酒店类型，且选择资源审核后发传真的，则它的发送策略必须为立即发送
	 * 2,对于非酒店类型，且支付对象为支付给驴妈妈，则它的必须设置为支付完成后发送传真
	 * @param metaProductId
	 */
	
	private void checkedResourceSendFax() {
		if (Constant.PRODUCT_TYPE.HOTEL.name().equals(metaProduct.getProductType()) && "true".equals(metaProduct.getIsResourceSendFax())) {
			List<SupBCertificateTarget> bcertificateTargetList = bCertificateTargetService.selectSuperMetaBCertificateByMetaProductId(metaProduct.getMetaProductId());
			if (bcertificateTargetList!= null && !bcertificateTargetList.isEmpty()&& !"IMMEDIATELY".equals(bcertificateTargetList.get(0).getFaxStrategy())) {
				throw new IllegalArgumentException("酒店类型产品选择资源审核后发传真，则传真的发送策略必须选为立即发送");
			}
		}
	}

	/**
	 * @return the metaProduct
	 */
	public T getMetaProduct() {
		return metaProduct;
	}

	/**
	 * @param metaProduct the metaProduct to set
	 */
	public void setMetaProduct(T metaProduct) {
		this.metaProduct = metaProduct;
	}
	
	
	public List<CodeItem> getPayToTargetList(){
		return CodeSet.getInstance().getCodeList(Constant.CODE_TYPE.PAYMENT_TARGET.name());
	}
	
	
	

	/**
	 * @return the payToTarget
	 */
	public String getPayToTarget() {
		return payToTarget;
	}
	/**
	 * @param payToTarget the payToTarget to set
	 */
	public void setPayToTarget(String payToTarget) {
		this.payToTarget = payToTarget;
	}

	/**
	 * @param comMessageService the comMessageService to set
	 */
	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}

	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}

	/**
	 * 判断是否需要显示
	 * @return
	 */
	public boolean isDecreaseDisabled(){
		return StringUtils.equals(Constant.PRODUCT_TYPE.HOTEL.name(), prodType);
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setSupContractService(SupContractService supContractService) {
		this.supContractService = supContractService;
	}

	public List<SupContract> getSupContractList() {
		return supContractList;
	}
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}
	public List<WorkGroup> getGroupNameList() {
		return groupNameList;
	}
	public void setGroupNameList(List<WorkGroup> groupNameList) {
		this.groupNameList = groupNameList;
	}
	public WorkGroupService getWorkGroupService() {
		return workGroupService;
	}
	public void setWorkGroupService(WorkGroupService workGroupService) {
		this.workGroupService = workGroupService;
	}
	public void setProductMessageProducer(
			TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}
	public void setEbkSuperClientService(EbkSuperClientService ebkSuperClientService) {
		this.ebkSuperClientService = ebkSuperClientService;
	}
	public void setMetaProductControlService(
			MetaProductControlService metaProductControlService) {
		this.metaProductControlService = metaProductControlService;
	}
	
}
