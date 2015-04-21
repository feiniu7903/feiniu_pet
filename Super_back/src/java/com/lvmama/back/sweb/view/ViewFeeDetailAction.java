package com.lvmama.back.sweb.view;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.ReflectionUtils;

import com.lvmama.back.sweb.prod.ProdViewPageBaseAction;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

/**
 *费用说明(多行程)
 */
@Results({
	@Result(name = "input", location = "/WEB-INF/pages/back/view/fee_detail.jsp")
	})
public class ViewFeeDetailAction extends ProdViewPageBaseAction{
	
	private static final long serialVersionUID = -880516823932250269L;
	private ViewContent ccViewContent;
	private ViewContent nccViewContent;
	private Long productId;
	private Long multiJourneyId;

	@Override
	@Action(value = "/view/toEditFeeDetail")
	public String goEdit() {
		if(productId != null && multiJourneyId != null) {
			ccViewContent = viewPageService.getViewContentByMultiJourneyId(multiJourneyId, Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name());
			nccViewContent = viewPageService.getViewContentByMultiJourneyId(multiJourneyId, Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name());
		}
		return goAfter();
	}


	@Action("/view/saveOrEditFeeDetail")
	public void save() {
		JSONResult result=new JSONResult();
		try{
			if(productId == null || multiJourneyId == null) {
				throw new Exception("传递参数为空,操作失败!");
			}
			checkViewPage(productId);
			if(ccViewContent.getContentId() == null) {
				ccViewContent.setContentType(Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name());
				ccViewContent.setMultiJourneyId(multiJourneyId);
				ccViewContent.setPageId(productId);
				viewPageService.insertViewContent(ccViewContent);
			} else {
				ViewContent vc = viewPageService.getViewContentByContentId(ccViewContent.getContentId());
				vc.setContent(ccViewContent.getContent());
				viewPageService.updateViewContent(vc);
			}
			
			if(nccViewContent.getContentId() == null) {
				nccViewContent.setContentType(Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name());
				nccViewContent.setMultiJourneyId(multiJourneyId);
				nccViewContent.setPageId(productId);
				viewPageService.insertViewContent(nccViewContent);
			} else {
				ViewContent vc = viewPageService.getViewContentByContentId(nccViewContent.getContentId());
				vc.setContent(nccViewContent.getContent());
				viewPageService.updateViewContent(vc);
			}
			
			prodProductService.markProductSensitive(productId, hasSensitiveWord);
			// 发送修改销售产品的消息
			productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(productId));
			//发送修改销售产品的通知ebk消息
			productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(productId));
			removeProductCache(productId);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}

	public Long getProductId() {
		return productId;
	}


	public void setProductId(Long productId) {
		this.productId = productId;
	}


	public Long getMultiJourneyId() {
		return multiJourneyId;
	}


	public void setMultiJourneyId(Long multiJourneyId) {
		this.multiJourneyId = multiJourneyId;
	}

	public ViewContent getCcViewContent() {
		return ccViewContent;
	}


	public ViewContent getNccViewContent() {
		return nccViewContent;
	}


	public void setCcViewContent(ViewContent ccViewContent) {
		this.ccViewContent = ccViewContent;
	}


	public void setNccViewContent(ViewContent nccViewContent) {
		this.nccViewContent = nccViewContent;
	}
}
