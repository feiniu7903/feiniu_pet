package com.lvmama.pet.fin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.fin.FinGLInterface;
import com.lvmama.comm.pet.po.fin.FinGLInterfaceDTO;
import com.lvmama.comm.pet.po.fin.FinGroupCostDTO;
import com.lvmama.comm.pet.service.fin.FinGLCostService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.FIN_SUBJECT_TYPE;

public class FinGLProxy extends FinGLServiceImpl implements FinGLCostService{
	private static final Log loger=LogFactory.getLog(FinGLProxy.class);
	
	private ProdProductService prodProductService;
	private OrderService orderService;
	
	
	@Override
	public void generateGLData() {
		super.generateGLData(Boolean.FALSE);
		generateOrderCostGLData();
	}
	@Override
	public void generateGLDataBeforeCleanOldData(){
		super.generateGLData(Boolean.TRUE);
		generateOrderCostGLData();
	}
	
	public void generateOrderCostGLData(){
		int count =0;
		do{
			Map<String,Object> params =new HashMap<String,Object>();
			List<FinGroupCostDTO> orderCosts = super.finGLInterfaceDAO.queryOrderCostParam(params);
			params.put("isGroupCost", "true");
			List<FinGroupCostDTO> orderGroupCosts = super.finGLInterfaceDAO.queryOrderCostParam(params);
			orderCosts.addAll(orderGroupCosts);
			count = orderCosts.size();
			for(FinGroupCostDTO dto:orderCosts){
				try{
					generateOrderItemCostGLData(dto);
				}catch(Exception e){
					finGLInterfaceDAO.updateOrdOrderItemMetaGLStatus(dto.getItemId(), Constant.GL_STATUS.FAILED.name());
					e.printStackTrace();
					loger.info(dto.getOrderId()+" itemId="+dto.getItemId()+"\r\n"+e);	
				}
			}
		}while(count>0);
	}
	/**
	 * 生成订单成本入账数据
	 */
	@Override
	public void generateOrderCostGLData(Long orderId) {
		Map<String,Object> params =new HashMap<String,Object>();
		params.put("orderId", orderId);
		List<FinGroupCostDTO> orderCosts = super.finGLInterfaceDAO.queryOrderCostParam(params);
		params.put("isGroupCost", "true");
		List<FinGroupCostDTO> orderGroupCosts = super.finGLInterfaceDAO.queryOrderCostParam(params);
		orderCosts.addAll(orderGroupCosts);
		for(FinGroupCostDTO dto:orderCosts){
			try{
				generateOrderItemCostGLData(dto);
			}catch(Exception e){
				loger.info(orderId+" itemId="+dto.getItemId()+"\r\n"+e);
				finGLInterfaceDAO.updateOrdOrderItemMetaGLStatus(dto.getItemId(), Constant.GL_STATUS.FAILED.name());
			}
		}
	}
	
	public void generateOrderItemCostGLData(final FinGroupCostDTO param){
		Long orderId=param.getOrderId();
		FinGLInterface finGLInterface = new FinGLInterface();
		finGLInterface.setTickedNo(String.valueOf(orderId));

		finGLInterface.setProofType(finGLServiceHelper.getHashProofTypeBySRCB(orderId));
		finGLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.ORDER_COST.name());
		finGLInterface.setMakeBillTime(param.getVisitTime());
		finGLInterface.setSummary("确认"+orderId+"("+param.getName()+")成本");
		finGLInterface.setBorrowerSubjectCode(finGLServiceHelper.getSubjectCode(getCostCodeMap(FIN_SUBJECT_TYPE.COST_BORROWER_SUBJECT.getCode(),param)));
		finGLInterface.setBorrowerSubjectName("主营业务成本-"+Constant.SUB_PRODUCT_TYPE.getCnName(param.getSubProductType())+"-"+Constant.PRODUCT_TYPE.getCnName(param.getProductType()));
		finGLInterface.setBorrowerAmount(Float.valueOf(param.getAmount()));
		finGLInterface.setLenderSubjectCode(finGLServiceHelper.getSubjectCode(getCostCodeMap(FIN_SUBJECT_TYPE.COST_LENDER_SUBJECT.getCode(),param)));
		finGLInterface.setLenderSubjectName("应付账款-"+Constant.SUB_PRODUCT_TYPE.getCnName(param.getSubProductType())+"-"+Constant.PRODUCT_TYPE.getCnName(param.getProductType()));
		finGLInterface.setLenderAmount(Float.valueOf(param.getAmount()));
		finGLInterface.setProductCode("" + param.getProductId());
		finGLInterface.setProductId(param.getProductId());
		finGLInterface.setProductName(param.getProductName());
		finGLInterface.setSupplierCode(String.valueOf(param.getSupplierId()));
		finGLInterface.setExt1(String.valueOf(orderId));
		finGLInterface.setExt4(DateUtil.formatDate(param.getVisitTime(), "yyyy-MM-dd"));
		finGLInterface.setExt10(param.getTravelGroupCode());
		finGLInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName(param.getFilialeName()));
		finGLInterface.setBatchNoCust(orderId+"_"+finGLInterface.getProofType());
		finGLInterface.setReconResultId(param.getItemId());
		finGLInterfaceDAO.insert(finGLInterface);
		finGLInterfaceDAO.updateOrdOrderItemMetaGLStatus(param.getItemId(), Constant.GL_STATUS.REAL_COST.name());
	}

	/**
	 * 生成团成本入账
	 */
	@Override
	public String generateTravelGroupCostGLData(final String[] travelGroupCodes) {
		StringBuffer errorMsg = new StringBuffer();
		if(null!=travelGroupCodes){
			for(int i=0;i<travelGroupCodes.length;i++){
				try{
					generaterTravelGroupCostGLDataSingle(travelGroupCodes[i]);
				}catch(Exception e){
					loger.error(e);
					errorMsg.append(travelGroupCodes[i]+",");
				}
			}
		}else{
			List<FinGroupCostDTO> codes = super.finGLInterfaceDAO.queryGroupCode();
			for(int i=0;i<codes.size();i++){
				try{
					generaterTravelGroupCostGLDataSingle(codes.get(i).getTravelGroupCode());
				}catch(Exception e){
					loger.error(e);
					errorMsg.append(codes.get(i).getTravelGroupCode()+",");
				}
			}
		}
		return errorMsg.toString();
	}
	
	public void generaterTravelGroupCostGLDataSingle(final String travelGroupCode) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("accountType", Constant.FIN_GL_ACCOUNT_TYPE.GROUP_COST.name());
		map.put("tickedNo", travelGroupCode);
		List<FinGLInterface> datas = query(map);
		//finGLInterfaceDAO.delete(map);
		map.put("travelGroupCode", travelGroupCode);
		map.put("groupType", Constant.GROUP_TYPE.BYONESELF.name());
		map.put("settlementStatus", "CONFIRMED");
		List<FinGroupCostDTO>  groupCosts = super.finGLInterfaceDAO.queryGroupCostParam(map);
		for(FinGLInterface data:datas){
			data.setLenderAmount(-data.getLenderAmount());
			data.setBorrowerAmount(-data.getBorrowerAmount());
			finGLInterfaceDAO.insert(data);
		}
		if(null!=groupCosts && !groupCosts.isEmpty()){
			for(FinGroupCostDTO groupCost:groupCosts){
				FinGLInterface finGLInterface = new FinGLInterface();
				finGLInterface.setTickedNo(groupCost.getTravelGroupCode());
				finGLInterface.setProofType(finGLServiceHelper.getHashProofTypeBySRCB(groupCost.getProductId()));
				finGLInterface.setMakeBillTime(groupCost.getVisitTime());
				finGLInterface.setSummary("确认"+finGLInterface.getTickedNo()+"("+groupCost.getName()+")成本");
				finGLInterface.setBorrowerSubjectCode(finGLServiceHelper.getSubjectCode(getCostCodeMap(FIN_SUBJECT_TYPE.COST_BORROWER_SUBJECT.getCode(),groupCost)));
				finGLInterface.setBorrowerSubjectName("主营业务成本-"+groupCost.getSubProductTypeName()+"-"+groupCost.getProductTypeName());
				finGLInterface.setBorrowerAmount(Float.valueOf(groupCost.getSubTotalCosts()));
				finGLInterface.setLenderAmount(finGLInterface.getBorrowerAmount());
				finGLInterface.setProductCode(""+groupCost.getProductId());
				finGLInterface.setProductId(groupCost.getProductId());
				finGLInterface.setProductName(groupCost.getProductName());
				finGLInterface.setExt10(groupCost.getTravelGroupCode());
				finGLInterface.setExt4(DateUtil.formatDate(groupCost.getVisitTime(), "yyyy-MM-dd"));
				finGLInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName(groupCost.getFilialeName()));			
				finGLInterface.setLenderSubjectCode(finGLServiceHelper.getSubjectCode(getCostCodeMap(FIN_SUBJECT_TYPE.COST_LENDER_SUBJECT.getCode(),groupCost)));
				finGLInterface.setLenderSubjectName("应付账款-"+groupCost.getSubProductTypeName()+"-"+groupCost.getProductTypeName());
				finGLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.GROUP_COST.getCode());
				finGLInterface.setBatchNoCust(groupCost.getProductId()+"_"+finGLInterface.getProofType());
				finGLInterface.setSupplierCode(groupCost.getSupplierId());
				finGLInterface.setReconResultId(groupCost.getItemId());
				finGLInterfaceDAO.insert(finGLInterface);
			}
		}else{
			loger.info(travelGroupCode +"'s param is not data!");
		}
	}
	private Map<String,Object> getCostCodeMap(final String subjectType,final FinGLInterfaceDTO dto){
		Map<String,Object> codeParamMap = new HashMap<String,Object>();
		codeParamMap.put("subjectType", subjectType);
		codeParamMap.put("config1", dto.getProductType());
		if(Constant.PRODUCT_TYPE.TICKET.name().equalsIgnoreCase(dto.getProductType())){
			codeParamMap.put("config3", dto.getPhysical());
		}else if(Constant.PRODUCT_TYPE.HOTEL.name().equalsIgnoreCase(dto.getProductType())){
		}else if((Constant.PRODUCT_TYPE.OTHER.name().equalsIgnoreCase(dto.getProductType()) &&
				(Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equalsIgnoreCase(dto.getSubProductType()) || Constant.SUB_PRODUCT_TYPE.EXPRESS.name().equalsIgnoreCase(dto.getSubProductType()))) ||
				 Constant.PRODUCT_TYPE.TRAFFIC.name().equalsIgnoreCase(dto.getProductType())){
			codeParamMap.put("config2", dto.getSubProductType());
		}else if(Constant.PRODUCT_TYPE.OTHER.name().equalsIgnoreCase(dto.getProductType())){
			FinGroupCostDTO cost = finGLInterfaceDAO.queryMainProductItem(dto.getOrderId());
			if(Constant.PRODUCT_TYPE.OTHER.name().equalsIgnoreCase(cost.getProductType()) && !Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equalsIgnoreCase(cost.getSubProductType()) && !Constant.SUB_PRODUCT_TYPE.EXPRESS.name().equalsIgnoreCase(cost.getSubProductType())){
				loger.info("成本入账科目 其它产品类型没有找到主产品类型 "+cost.getProductType() +" orderId= "+dto.getOrderId());
				return null;
			}
			return getCostCodeMap(subjectType,cost);
		}else if(Constant.PRODUCT_TYPE.ROUTE.name().equalsIgnoreCase(dto.getProductType())){
			if(Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equalsIgnoreCase(dto.getSubProductType())
			 ||Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equalsIgnoreCase(dto.getSubProductType())){
				codeParamMap.put("config2", dto.getSubProductType());
				codeParamMap.put("config5", dto.getRegionName());
			}else{
				codeParamMap.put("config2", dto.getSubProductType());
			}
		}else{
			loger.info("成本入账科目没有此产品类型："+dto.getProductType());
			return null;
		}
		return codeParamMap;
	}

	public ProdProductService getProdProductService() {
		return prodProductService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
}
