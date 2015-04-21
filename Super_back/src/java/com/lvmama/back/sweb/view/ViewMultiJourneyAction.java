package com.lvmama.back.sweb.view;

import com.lvmama.back.sweb.prod.ProdViewPageBaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewMultiJourney;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.view.ViewMultiJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageJourneyService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Results({
	@Result(name = "input", location = "/WEB-INF/pages/back/view/view_multi_journey.jsp"),
	@Result(name = "auditingShow", location = "/WEB-INF/pages/back/prod/auditing/view_multi_journey_auditing_show.jsp"),
	@Result(name = "editMultiJourney", location = "/WEB-INF/pages/back/view/edit_multi_journey.jsp")
	})
/**
 * 多行程
 * 
 * @author shihui
 * */
public class ViewMultiJourneyAction extends ProdViewPageBaseAction {

	private static final long serialVersionUID = 1283287586616326134L;
	
	private ViewMultiJourney viewMultiJourney;
	
	private ViewMultiJourneyService viewMultiJourneyService;
	
	private Date createBeginDate;
	
	private Date createEndDate;
	
	private boolean copy = false;
	
	private Long productId;
	
	private ViewPageJourneyService viewPageJourneyService;
	
	private ProdProductService prodProductService;
	
	public ViewMultiJourneyAction() {
		super();
		setMenuType("journey");
	}

	@Override
	public boolean doBefore() {
		if(!super.doBefore()){
			return false;
		}
		if(!(product instanceof ProdRoute)){
			return false;
		}
		return true;
	}
	@Action(value = "toViewMultiJourneyAuditingShow")
    public String toViewMultiJourneyAuditingShow(){
        this.goEdit();
        return "auditingShow";
    }
	@Override
	@Action(value="/view/toViewMultiJourney")
	public String goEdit() {
		if(productId != null) {
			queryMultiJourneyList();
		}
		return "input";
	}

	/**
	 * 新增或修改或复制新增行程明细
	 * */
	@Override
	@Action(value="/view/saveMultiJourney")
	public void save() {
		JSONResult result = new JSONResult();
		try {
			if(viewMultiJourney == null) {
				throw new Exception("传递参数为空,操作失败!");
			}
			if(viewMultiJourney.getMultiJourneyId() == null || copy) {
				Long preMultiJourneyId = viewMultiJourney.getMultiJourneyId();
				viewMultiJourney.setMultiJourneyId(null);
				Long newId = viewMultiJourneyService.insert(viewMultiJourney, this.getOperatorNameAndCheck());
				//复制行程明细
				if(preMultiJourneyId != null && copy) {
					List<ViewJourney> vjList = viewPageJourneyService.getViewJourneyByMultiJourneyId(preMultiJourneyId);
					for (int i = 0; i < vjList.size(); i++) {
						ViewJourney vj = vjList.get(i);
						ViewJourney newVj = new ViewJourney();
						BeanUtils.copyProperties(newVj, vj);
						newVj.setMultiJourneyId(newId);
						viewPageJourneyService.insertViewJourney(newVj, getOperatorNameAndCheck());
					}
				}
			} else {
				viewMultiJourneyService.update(viewMultiJourney, this.getOperatorNameAndCheck());
			}
			
			prodProductService.markProductSensitive(viewMultiJourney.getProductId(), hasSensitiveWord);
			//发送修改销售产品的通知ebk消息
			productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(viewMultiJourney.getProductId()));
		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 根据参数查询列表
	 * */
	@Action(value="/view/queryMultiJourneyList")
	public String queryMultiJourneyList() {
		Map<String,Object> params = buildParams();
		if(params.get("productId") == null) {
			return "input";
		}
		String success = toResult(params);
		params.put("page", pagination.getPage());
		params.put("perPageRecord", pagination.getPerPageRecord());
		product = prodProductService.getProdProduct((Long)params.get("productId"));
		return success;
	}
	
	public Map<String, Object> buildParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		if(productId != null){
			params.put("productId", productId);
		}
		if(viewMultiJourney == null) {
			return params;
		}
		if(createBeginDate != null) {
			params.put("createBeginDate", createBeginDate);
		}
		if(createEndDate != null) {
			params.put("createEndDate", createEndDate);
		}
		if(StringUtils.isNotEmpty(viewMultiJourney.getJourneyName())) {
			params.put("journeyName", viewMultiJourney.getJourneyName());
		}
		if(viewMultiJourney.getDays() != null) {
			params.put("days", viewMultiJourney.getDays());
		}
		if(viewMultiJourney.getNights() != null) {
			params.put("nights", viewMultiJourney.getNights());
		}
		if(StringUtils.isNotEmpty(viewMultiJourney.getValid())) {
			params.put("valid", viewMultiJourney.getValid());
		}
		return params;
	}
	
	private String toResult(Map<String,Object> params){
		Integer totalRowCount = viewMultiJourneyService.selectRowCount(params);
		pagination = super.initPagination();
		pagination.setTotalRecords(totalRowCount);
		params.put("_startRow", pagination.getFirstRow());
		params.put("_endRow", pagination.getLastRow());
		List<ViewMultiJourney> viewMultiJourneyList = viewMultiJourneyService.queryMultiJourneyByParams(params);
		pagination.setRecords(viewMultiJourneyList);
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		return "input";
	}
	
	/**
	 * 更改有效状态
	 * */
	@Action(value="/view/changeValidStatus")
	public void changeValidStatus() {
		JSONResult result = new JSONResult();
		try {
			if(viewMultiJourney == null || viewMultiJourney.getMultiJourneyId() == null) {
				throw new Exception("参数异常,操作失败!");
			}
			ViewMultiJourney journey = viewMultiJourneyService.selectByPrimaryKey(viewMultiJourney.getMultiJourneyId());
			if(journey == null) {
				throw new Exception("数据异常,操作失败!");
			}
			//必须有行程明细才能设置为有效
			if("N".equals(journey.getValid())) {
				List<ViewJourney> vjList = viewPageJourneyService.getViewJourneyByMultiJourneyId(viewMultiJourney.getMultiJourneyId());
				if(vjList.isEmpty()) {
					throw new Exception("请先添加行程明细信息!");
				}
				ViewContent ccVc = viewPageService.getViewContentByMultiJourneyId(viewMultiJourney.getMultiJourneyId(), Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name());
				if(ccVc == null) {
					throw new Exception("请先添加费用说明");
				}
			} else {
				List<Long> branchIds = viewMultiJourneyService.getBranchIdsByMultiJourneyId(viewMultiJourney.getProductId(), viewMultiJourney.getMultiJourneyId());
				//将对应的时间价格表删除
				viewMultiJourneyService.deleteTimePriceByMultiJourneyId(viewMultiJourney.getProductId(), viewMultiJourney.getMultiJourneyId());
				//发消息更新对应的信息
				if(branchIds != null && branchIds.size() > 0) {
					for (int i = 0; i < branchIds.size(); i++) {
						productMessageProducer.sendMsg(MessageFactory.newProductSellPriceMessage(branchIds.get(i)));
					}
				}
			}
			journey.setValid("Y".equals(journey.getValid()) ? "N" : "Y");
			viewMultiJourneyService.update(journey, this.getOperatorNameAndCheck());
			//发送修改销售产品的通知ebk消息
			productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(viewMultiJourney.getProductId()));
		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	@Action(value = "/view/toEditMultiJourney")
	public String toEditViewMultiJourney() {
		if(viewMultiJourney != null && viewMultiJourney.getMultiJourneyId() != null) {
			viewMultiJourney = viewMultiJourneyService.selectByPrimaryKey(viewMultiJourney.getMultiJourneyId());
		}
		return "editMultiJourney";
	}

	public ViewMultiJourney getViewMultiJourney() {
		return viewMultiJourney;
	}

	public void setViewMultiJourney(ViewMultiJourney viewMultiJourney) {
		this.viewMultiJourney = viewMultiJourney;
	}

	public void setViewMultiJourneyService(
			ViewMultiJourneyService viewMultiJourneyService) {
		this.viewMultiJourneyService = viewMultiJourneyService;
	}

	public void setCreateBeginDate(Date createBeginDate) {
		this.createBeginDate = createBeginDate;
	}

	public void setCreateEndDate(Date createEndDate) {
		this.createEndDate = createEndDate;
	}

	public void setCopy(boolean copy) {
		this.copy = copy;
	}

	public boolean isCopy() {
		return copy;
	}

	public Date getCreateBeginDate() {
		return createBeginDate;
	}

	public Date getCreateEndDate() {
		return createEndDate;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setViewPageJourneyService(
			ViewPageJourneyService viewPageJourneyService) {
		this.viewPageJourneyService = viewPageJourneyService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
}
