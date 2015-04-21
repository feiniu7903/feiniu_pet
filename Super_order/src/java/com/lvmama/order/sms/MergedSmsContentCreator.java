package com.lvmama.order.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.ProdProductBranchDAO;

public class MergedSmsContentCreator extends AbstractOrderSmsCreator implements SingleSmsCreator,MultiSmsCreator{
	Logger log = Logger.getLogger(MergedSmsContentCreator.class);
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private PassCodeService passCodeService = (PassCodeService) SpringBeanProxy.getBean("passCodeService");
	private ProdProductService prodProductService = (ProdProductService) SpringBeanProxy.getBean("prodProductService");
	private ProdProductBranchDAO prodProductBranchDAO = (ProdProductBranchDAO) SpringBeanProxy.getBean("prodProductBranchDAO");
	
	private OrdOrder order;
	private List<String> codeIds;
	private String productName;
	private PassCode passCode;
	private boolean generateSMS = true;	
	private List<PassPortCode> otherPassportcodeList = null;
	private List<ComSms> otherComSmsList = null;
	
	private MergedSmsContentCreator(PassCode passCode,String mobile, boolean mergeCertificateSMS) {
		log.info("mergeCertificateSMS:"+mergeCertificateSMS);
		this.objectId = passCode.getOrderId();
		log.info("orderId:"+objectId);
		order = orderServiceProxy.queryOrdOrderByOrderId(objectId);
		this.passCode = passCode;
		this.mobile = mobile;
	}

	public MergedSmsContentCreator(PassCode passCode2,String mobile, boolean mergeCertificateSMS2, boolean generateSMS) {
		this(passCode2,mobile,mergeCertificateSMS2);
		this.generateSMS = generateSMS;
		this.singleSmsInMerged(passCode2, mergeCertificateSMS2);
	}

	@Override
	Map<String, Object> getContentData() {
		Map<String, Object> data = new HashMap<String, Object>();
		
		StringBuffer content = new StringBuffer();
		content.append(productName);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("codeList",codeIds);
		List<PassCode> passCodeList = passCodeService.queryPassCodeByParam(params);
		for(PassCode passCode : passCodeList){
			content.append(passCode.getSmsContent());
		}
		
		data.put("orderId", objectId);
		data.put("visitDate", DateUtil.formatDate(order.getVisitTime(), "yyyy-MM-dd"));
		data.put("content", content.toString());
		return data;
	}
	
	@Override
	ProdChannelSms getSmsTemplate() {
		OrdOrder order = orderDAO.selectByPrimaryKey(objectId);
		return prodChannelSmsDAO.selectByTemplateIdAndChannelCode(order.getChannel(), Constant.SMS_TEMPLATE.MERGED_SMSCONTENT.name());
	}
	
	private String getProductName(PassCode passCode){
		List<OrdOrderItemMeta> ordItemMetaList = null;
		OrdOrderItemMeta itemMeta = null;
		if(passCode.isForOrder()){
			ordItemMetaList = orderServiceProxy.queryOrdOrderItemMetaByOrderId(passCode.getOrderId());
			itemMeta = ordItemMetaList.get(0);
		}else{
			itemMeta = orderServiceProxy.queryOrdOrderItemMetaBy(passCode.getObjectId());
		}
		OrdOrderItemProd ordItemProd = orderServiceProxy.queryOrdOrderItemProdById(itemMeta.getOrderItemId());
		ProdProductBranch prodProductBranch = prodProductBranchDAO.selectByPrimaryKey(ordItemProd.getProdBranchId());
		ProdProduct product = prodProductService.getProdProduct(prodProductBranch.getProductId());
		return product.getProductName();
	}

	private void singleSmsInMerged(PassCode passCode,boolean mergeCertificateSMS){
		List<OrdOrderItemMeta> ordItemMetaList = null;
		OrdOrderItemMeta itemMeta = null;
		if(passCode.isForOrder()){
			ordItemMetaList = orderServiceProxy.queryOrdOrderItemMetaByOrderId(passCode.getOrderId());
			itemMeta = ordItemMetaList.get(0);
		}else{
			itemMeta = orderServiceProxy.queryOrdOrderItemMetaBy(passCode.getObjectId());
		}
		OrdOrderItemProd ordItemProd = orderServiceProxy.queryOrdOrderItemProdById(itemMeta.getOrderItemId());
		ProdProductBranch prodProductBranch = prodProductBranchDAO.selectByPrimaryKey(ordItemProd.getProdBranchId());
		
		//凭证短信合并发送，并且是各子子项独立申码
		if(mergeCertificateSMS && passCode.isForOrderItemMeta() && generateSMS){
			//【${销售类别} 取票码 ${code}，辅助码xxxx ,含${adult} ${chaild}】
			String formatString = "【%s %s，%s，%s】";
			
			String branchName= prodProductBranch.getBranchName();
			String code="";
			if (!"BASE64".equalsIgnoreCase(passCode.getCode())){
				code = "取票码 "+passCode.getCode();
			}
			String addCode = passCode.getAddCode();
			
			if(StringUtils.isNotEmpty(addCode)){
				addCode = "辅助码 "+addCode;
			}else{
				addCode = "";
			}
			
			long adult = ordItemProd.getQuantity() * prodProductBranch.getAdultQuantity();
			long child = ordItemProd.getQuantity() * prodProductBranch.getChildQuantity();
					
			StringBuilder quantity=new StringBuilder();
			if(itemMeta.isStudent()){
				quantity.append("学生人数："+(adult+child));
			}else{
				quantity.append("含");
				if(adult>0){
					quantity.append(adult+"成人  ");
				}
				if(child>0){
					quantity.append(child+"儿童");
				}
			}
			String smsContent = String.format(formatString, branchName,code,addCode,quantity);
			log.info("=====singleSmsInMerged smsContent==========="+smsContent);
			PassCode temp=new PassCode();
			temp.setCodeId(passCode.getCodeId());
			temp.setSmsContent(smsContent);
			passCodeService.updatePassCode(temp);
			
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, List<String>> checkAllApplySuccess(PassCode passCode) {

		/*
		 * 检查订单中所有由非服务商发送短信、独立申码、并且开通了凭证短信合并功能
		 * 的所有短信是否都已经生成，然后按服务商分组passCode
		 * 
		 * 按服务商分组passCode的目的是，各服务商的凭证短信单独发送
		 */
		Map params = new HashMap();
		params.put("orderId", passCode.getOrderId());
		List<PassPortCode> passportcodeList = passCodeService.selectAllMergeSmsByParams(params);
		
		int mergeCount = 0;
		if(passCode.getCodeTotal().intValue() == passportcodeList.size()){
			for(PassPortCode passportcode : passportcodeList){
				//剔除非合并凭证短信的通关码
				if(!passportcode.isMergeCertificateSMS()){
					if(otherPassportcodeList == null){
						otherPassportcodeList = new ArrayList();
					}
					otherPassportcodeList.add(passportcode);
					
					continue;
				}
				mergeCount ++;
			}
		}else{
			return null;
		}
		
		params.put("checkSmsContent", "true");
		List<PassPortCode> passportcodeStatusList = passCodeService.selectAllMergeSmsByParams(params);
		log.info(passportcodeStatusList.size());
		//所有的合并凭证短信的码，都已经申码成功、生成短信内容片段成功
		if(mergeCount == passportcodeStatusList.size()){
			Map<String,List<String>> groupPassPortCode = new HashMap<String,List<String>>();
			for(PassPortCode passportcode : passportcodeStatusList){								
				String providerName = passportcode.getProviderName();
				Long codeId = passportcode.getCodeId();
				List<String> codeList = groupPassPortCode.get(providerName);
				if(codeList == null){
					codeList = new ArrayList<String>();						
				}
				codeList.add(codeId.toString());
				groupPassPortCode.put(providerName, codeList);
			}
			return groupPassPortCode;
		}
	
		return null;
	}

	private List<ComSms> createOtherSmsList(){
		if(otherPassportcodeList == null )
			return null;
		
		for(PassPortCode passPortCode : otherPassportcodeList){
			//此处未考虑 期票存在的情况
			SingleSmsCreator creator = new DiemPaySuccSmsCreator(passPortCode.getCodeId(), mobile,false);
			ComSms sms = creator.createSingleSms();
			if(otherComSmsList == null){
				otherComSmsList = new ArrayList<ComSms>();
			}
			otherComSmsList.add(sms);
		}
		
		return otherComSmsList;
	}
	
	@Override
	public List<ComSms> createSmsList() {
		this.productName =getProductName(passCode);
		Map<String, List<String>> allPassCode = checkAllApplySuccess(passCode);
		
		List<ComSms> smsList = null;
		log.info("productName:"+productName);
		if(StringUtils.isNotEmpty(this.productName) && allPassCode != null){
			smsList = new ArrayList<ComSms>();
			
			Iterator<String> providerNames = allPassCode.keySet().iterator();
			while(providerNames.hasNext()){
				String providerName = providerNames.next();
				this.codeIds = allPassCode.get(providerName);
				
				ComSms sms = createSingleSms();
				log.info("createSmsList sms content:"+sms.getContent());
				smsList.add(sms);
			}
			
			//因为申码成功时，非凭证合并的短信是单独发送，每个码一条短信
			//但是，短信重发时，也要一起把整个订单所对应的所有短信一起发送出去
			//所以，此处调用另一短信生成器
			//将非凭证合并的短信，增加到短信列表中，未考虑 期票的情况
			if(!generateSMS){
				List<ComSms> otherSmsList = createOtherSmsList();
				if( otherSmsList != null )
					smsList.addAll(otherComSmsList);
			}
		}
		return smsList;
	}
}
