package com.lvmama.back.sweb.view;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.prod.ViewJourneyTips;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.view.ViewPageJourneyTipsService;
import com.lvmama.comm.utils.json.JSONResult;
/**处理行程小贴士的Action类
 * @author zx
 *
 */
@Results({
	@Result(name = "viewJourneyTips", location = "/WEB-INF/pages/back/view/view_journey_tip.jsp"),
	@Result(name = "goAction", location ="/view/toViewJourneyTips.do?journeyId=${journeyId}",type="redirect")
	})
public class ViewJourneyTipsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ViewPageJourneyTipsService viewPageJourneyTipsService;
	private ViewJourneyTips viewJourneyTips = new ViewJourneyTips();
	private List<ViewJourneyTips> journeyTipList;// 表格数据
	private Long tipId;
	private Long journeyId;
	private String tipContent;
	private Long productId;
	private String hasSensitiveWord;
	private ProdProductService prodProductService;
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getTipContent() {
		return tipContent;
	}

	public void setTipContent(String tipContent) {
		this.tipContent = tipContent;
	}

	public List<ViewJourneyTips> getJourneyTipList() {
		return journeyTipList;
	}

	public void setJourneyTipList(List<ViewJourneyTips> journeyTipList) {
		this.journeyTipList = journeyTipList;
	}

	public Long getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(Long journeyId) {
		this.journeyId = journeyId;
	}

	public Long getTipId() {
		return tipId;
	}

	public void setTipId(Long tipId) {
		this.tipId = tipId;
	}

	public void setViewJourneyTips(ViewJourneyTips viewJourneyTips) {
		this.viewJourneyTips = viewJourneyTips;
	}

	public void setViewPageJourneyTipsService(
			ViewPageJourneyTipsService viewPageJourneyTipsService) {
		this.viewPageJourneyTipsService = viewPageJourneyTipsService;
	}

	public void doBefore() {
	}

	/**
	 * 页面载入显示
	 * */
	@Action("/view/toViewJourneyTips")
	public String goEdit() {
		this.loadDataList();
		return "viewJourneyTips";
	}

	/**
	 * 添加行程贴士
	 * zx
	 */
	@Action("/view/saveJourneyTips")
	public void save() {
		JSONResult result=new JSONResult();
		try {
			Assert.notNull(journeyId,"行程信息不存在");
			viewJourneyTips.setJourneyId(journeyId);
			viewJourneyTips.setTipContent(tipContent);
			Long tipId = viewPageJourneyTipsService.insertViewJourney(viewJourneyTips);
			viewJourneyTips.setTipId(tipId);
			prodProductService.markProductSensitive(productId, hasSensitiveWord);
			
			result.put("journeyId", viewJourneyTips.getJourneyId());
			result.put("journeyTips", viewJourneyTips);
		} catch (Exception e) {
			result.raise(e);
		}
		result.output(getResponse());
	}
	
	/**
	 * 删除行程
	 * */
	@Action("/view/doDeleteJourneyTips")
	public void doDelete(){
		JSONResult result=new JSONResult();
		try {
			Assert.notNull(tipId,"小贴士不存在");
			viewPageJourneyTipsService.deleteViewJourneyTip(tipId);
			
			prodProductService.markProductSensitive(productId, null);
			result.put("tipId", tipId);
		} catch (Exception e) {
			result.raise(e);
		}
		result.output(getResponse());
	}
	
	/**
	 * 根据行程ID加载行程小贴士列表
	 * */
	public void loadDataList() {
		if(this.journeyId != null) {
			journeyTipList = viewPageJourneyTipsService.getViewJourneyTipsByJourneyId(journeyId);
		}
	}

	public String getHasSensitiveWord() {
		return hasSensitiveWord;
	}

	public void setHasSensitiveWord(String hasSensitiveWord) {
		this.hasSensitiveWord = hasSensitiveWord;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	
}
