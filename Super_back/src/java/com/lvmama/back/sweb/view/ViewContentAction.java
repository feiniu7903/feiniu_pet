package com.lvmama.back.sweb.view;

import com.lvmama.back.sweb.prod.ProdViewPageBaseAction;
import com.lvmama.comm.bee.po.prod.*;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *销售产品详情
 */
@Results({
	@Result(name = "input", location = "/WEB-INF/pages/back/prod/product_detail.jsp"),
	@Result(name = "auditingShow", location = "/WEB-INF/pages/back/prod/auditing/product_detail_auditing_show.jsp")
	})
public class ViewContentAction extends ProdViewPageBaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5593501166325675118L;

	private ViewPage viewPage;
	private String MANAGERRECOMMEND;
	private String	ANNOUNCEMENT;
	private String	COSTCONTAIN;	
	private String	NOCOSTCONTAIN;	
	private String	SHOPPINGEXPLAIN;
	private String	ORDERTOKNOWN;
	private String	ACITONTOKNOW;
	private String	REFUNDSEXPLANATION;
	private String	PLAYPOINTOUT;
	private String  TRAFFICINFO;
	private String	VISA;
	private String	FEATURES;
	private String INTERIOR;
	private String RECOMMENDPROJECT;
	private String SERVICEGUARANTEE;
	private ViewContent viewContent;
	private List<ViewTravelTips> viewTravelTipsList;
	
	public ViewContentAction() {
		super();
		setMenuType("detail");		
	}

    @Action(value="/view/toViewContentAuditingShow")
    public String toViewContentAuditingShow(){
        this.goEdit();
        return "auditingShow";

    }
	@Override
	@Action(value="/view/toViewContent")
	public String goEdit() {
		if(!doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		viewPage= viewPageService.getViewPage(productId);
		if(product instanceof ProdRoute){
			viewTravelTipsList = prodProductService.selectViewTravelTipsByProductId(productId);
		}
		return goAfter();
	}


	@Action("/view/updateViewContent")
	public void save() {
		JSONResult result=new JSONResult();
		try{
			checkViewPage(productId);			
			List<ViewContent> list=new ArrayList<ViewContent>();
			boolean isMultiJourney = false;
			ProdProduct prod = prodProductService.getProdProduct(productId);
			if(prod.isRoute()) {
				ProdRoute pr = (ProdRoute) prod;
				isMultiJourney = pr.hasMultiJourney();
			}
			for(Constant.VIEW_CONTENT_TYPE vt:Constant.VIEW_CONTENT_TYPE.values()){
				if(isMultiJourney) {
					if(Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name().equals(vt.name()) || Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name().equals(vt.name())) {
						continue;
					}
				}
				Field field=ReflectionUtils.findField(this.getClass(), vt.name(),String.class);
				if(field!=null){
					try{
						String value=(String)field.get(this);
						ViewContent vc=new ViewContent();
						vc.setContent(value);
						vc.setContentType(vt.name());
						vc.setPageId(productId);
						list.add(vc);
					}catch(IllegalAccessException ex){						
					}
				}
			}		
			viewPage=new ViewPage();
			viewPage.setProductId(productId);
			viewPage.setContentList(list);			
			viewPageService.saveViewContent(viewPage,this.getOperatorNameAndCheck());	
			
			prodProductService.markProductSensitive(productId, hasSensitiveWord);
			// 发送修改销售产品的消息
			productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(viewPage.getProductId()));
			//发送place变更消息
			productMessageProducer.sendMsg(MessageFactory.newProductPlaceUpdateMessage(viewPage.getProductId()));
			//发送修改销售产品的通知ebk消息
			productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(viewPage.getProductId()));
			removeProductCache(viewPage.getProductId());
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	public ViewPage getViewPage() {
		return viewPage;
	}

	public void setViewPage(ViewPage viewPage) {
		this.viewPage = viewPage;
	}

	public ViewContent getViewContent() {
		return viewContent;
	}

	public void setViewContent(ViewContent viewContent) {
		this.viewContent = viewContent;
	}

	
	public String getMANAGERRECOMMEND() {
		return MANAGERRECOMMEND;
	}

	public void setMANAGERRECOMMEND(String mANAGERRECOMMEND) {
		MANAGERRECOMMEND = mANAGERRECOMMEND;
	}

	public String getANNOUNCEMENT() {
		return ANNOUNCEMENT;
	}

	public void setANNOUNCEMENT(String aNNOUNCEMENT) {
		ANNOUNCEMENT = aNNOUNCEMENT;
	}

	public String getCOSTCONTAIN() {
		return COSTCONTAIN;
	}

	public void setCOSTCONTAIN(String cOSTCONTAIN) {
		COSTCONTAIN = cOSTCONTAIN;
	}

	public String getNOCOSTCONTAIN() {
		return NOCOSTCONTAIN;
	}

	public void setNOCOSTCONTAIN(String nOCOSTCONTAIN) {
		NOCOSTCONTAIN = nOCOSTCONTAIN;
	}

	public String getSHOPPINGEXPLAIN() {
		return SHOPPINGEXPLAIN;
	}

	public void setSHOPPINGEXPLAIN(String sHOPPINGEXPLAIN) {
		SHOPPINGEXPLAIN = sHOPPINGEXPLAIN;
	}

	public String getVISA() {
		return VISA;
	}

	public void setVISA(String vISA) {
		VISA = vISA;
	}

	

	public String getORDERTOKNOWN() {
		return ORDERTOKNOWN;
	}


	public void setORDERTOKNOWN(String oRDERTOKNOWN) {
		ORDERTOKNOWN = oRDERTOKNOWN;
	}


	public String getACITONTOKNOW() {
		return ACITONTOKNOW;
	}


	public void setACITONTOKNOW(String aCITONTOKNOW) {
		ACITONTOKNOW = aCITONTOKNOW;
	}


	public String getREFUNDSEXPLANATION() {
		return REFUNDSEXPLANATION;
	}


	public void setREFUNDSEXPLANATION(String rEFUNDSEXPLANATION) {
		REFUNDSEXPLANATION = rEFUNDSEXPLANATION;
	}


	public String getPLAYPOINTOUT() {
		return PLAYPOINTOUT;
	}


	public void setPLAYPOINTOUT(String pLAYPOINTOUT) {
		PLAYPOINTOUT = pLAYPOINTOUT;
	}


	public String getTRAFFICINFO() {
		return TRAFFICINFO;
	}


	public void setTRAFFICINFO(String tRAFFICINFO) {
		TRAFFICINFO = tRAFFICINFO;
	}


	

	

	/**
	 * @return the fEATURES
	 */
	public String getFEATURES() {
		return FEATURES;
	}


	/**
	 * @param fEATURES the fEATURES to set
	 */
	public void setFEATURES(String fEATURES) {
		FEATURES = fEATURES;
	}


	public String getINTERIOR() {
		return INTERIOR;
	}


	public void setINTERIOR(String iNTERIOR) {
		INTERIOR = iNTERIOR;
	}
 

	/**
	 * @return the rECOMMENDPROJECT
	 */
	public String getRECOMMENDPROJECT() {
		return RECOMMENDPROJECT;
	}


	/**
	 * @param rECOMMENDPROJECT the rECOMMENDPROJECT to set
	 */
	public void setRECOMMENDPROJECT(String rECOMMENDPROJECT) {
		RECOMMENDPROJECT = rECOMMENDPROJECT;
	}


	/**
	 * @return the sERVICEGUARANTEE
	 */
	public String getSERVICEGUARANTEE() {
		return SERVICEGUARANTEE;
	}


	/**
	 * @param sERVICEGUARANTEE the sERVICEGUARANTEE to set
	 */
	public void setSERVICEGUARANTEE(String sERVICEGUARANTEE) {
		SERVICEGUARANTEE = sERVICEGUARANTEE;
	}

	public List<ViewTravelTips> getViewTravelTipsList() {
		return viewTravelTipsList;
	}

	public void setViewTravelTipsList(List<ViewTravelTips> viewTravelTipsList) {
		this.viewTravelTipsList = viewTravelTipsList;
	}
}
