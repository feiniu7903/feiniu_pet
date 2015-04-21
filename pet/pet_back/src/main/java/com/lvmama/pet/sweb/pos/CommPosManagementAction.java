package com.lvmama.pet.sweb.pos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pay.PayPos;
import com.lvmama.comm.pet.po.pay.PayPosCommercial;
import com.lvmama.comm.pet.service.pay.PayPosCommercialService;
import com.lvmama.comm.pet.service.pay.PayPosService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.WebUtils;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;

/**
 * 交通银行POS终端管理.
 * @author liwenzhan.
 */
@ParentPackage("json-default")
@Results({
	@Result(name = "pos_insert", location = "/WEB-INF/pages/back/pos/terminal/terminal_insert.jsp"),
	@Result(name = "pos_query", location = "/WEB-INF/pages/back/pos/terminal/terminal_management.jsp"),
	@Result(name = "pos_update", location = "/WEB-INF/pages/back/pos/terminal/terminal_update.jsp")
})
public class CommPosManagementAction extends BackBaseAction{
	
	/**
	 * .
	 */
	private static final long serialVersionUID = -124544761331129479L;
	/**
	 * POS终端服务.
	 */
	private PayPosService payPosService;
	/**
	 * POS商户服务.
	 */
	private PayPosCommercialService payPosCommercialService;
	/**
	 * .
	 */
	private PayPos payPos;
	
	/**
	 * 查询终端号.
	 */
	private String searchTerminalNo;
	/**
	 * 查询终端状态.
	 */
	private String searchStatus;
	
	/**
	 * 终端ID.
	 */
	private Long commPosId;
	/**
	 * 终端号.
	 */
	private String terminalNo;
	/**
	 * 备注.
	 */
	private String memo;
	/**
	 * 终端状态.
	 */
	private String status;
	/**
	 * 商户ID.
	 */
	private Long commercialId;

	
	/**
	 * 终端List.
	 */
	private List<PayPos> commPosList;
	/**
	 * 商户List.
	 */
	private List<PayPosCommercial> commercialList;
	/**
	 * 返回链接标识.
	 */
	private String returnable;
	
	
	
	/**
	 * 商户ID.
	 */
	private Long searchCommercialId;
	
	
	private Page<PayPos> payPosPage;
	private Long perPageRecord=10L;
	
	
	/**
	 * .
	 */
	public CommPosManagementAction(){
		super();
	}
	
	/**
	 * 查询 Pos .
	 */
	@Action("/pos/selectPos")
	public String SelectPos(){
		Map<String,Object> params=new HashMap<String, Object>();
		commercialList=payPosCommercialService.select(params);
		 if(this.getSearchCommercialId()!=null){
		    this.commercialId=this.getSearchCommercialId();
		  }
		Map<String,String> param=buildParameter();
		payPosPage =payPosService.selectPayPosPageByParam(param,perPageRecord,page);
		payPosPage.setUrl(WebUtils.getUrl(getRequest(),param));
    	return "pos_query";
	}
	
	
	/**
	 * 获得查询页面.
	 * @return
	 */
	@Action("/pos/goPosList")
	public String execute(){
		return "pos_query";
	}
	/**
	 * 获得添加页.
	 * @return
	 */
	@Action("/pos/getAddPosPage")
	public String getAddPage(){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("commercialId", searchCommercialId);
		commercialList=payPosCommercialService.select(params);
		return "pos_insert";
	}
	
	/**
	 * 获得查询页.
	 * @return
	 */
	@Action("/pos/getListPosPage")
	public String getListPage(){
		Map<String,Object> params=new HashMap<String, Object>();
		commercialList=payPosCommercialService.select(params);
		return "pos_query";
	}
	/**
	 * 获得更新页.
	 * @return
	 */
	@Action("/pos/getUpdatePosPage")
	public String getUpdatePage(){
		payPos=payPosService.selectById(commPosId);
		commercialId=payPos.getCommercialId();
		Map<String,Object> params=new HashMap<String, Object>();
		commercialList=payPosCommercialService.select(params);
		return "pos_update";
	}
	

	/**
	 * 
	 * 插入POS终端.
	 */
	@Action("/pos/insertPos")
	public void InsertPos(){
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue(commercialId!=null,"请选择商户");
			Assert.hasLength(terminalNo.trim(),"终端号不可以为空");
			Assert.isTrue(!isTerminalNoExisted(), "该终端号已经存在,请确定终端号");
			
			PayPos payPos=new PayPos();
			payPos.setCommercialId(commercialId);
			payPos.setMemo(memo);
			payPos.setStatus(status);
			payPos.setTerminalNo(terminalNo);
			Long posId = payPosService.insert(payPos,this.getSessionUserName());
			result.put("posId",posId);
		}
		catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	
	/**
	 * 更改终端状态.
	 */
	@Action("/pos/modifyStatusPos")
	public void changeVisaStatus(){
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue(!(commPosId==null||commPosId<1),"终端号不可以为空");
			Assert.hasLength(status,"状态不可以为空");
			
			PayPos cp = payPosService.selectById(commPosId);
			if(cp==null){
				throw new Exception("您修改的终端信息不存在!");
			}
			cp.setStatus(status);
			payPosService.modifyStatus(cp,this.getSessionUserName(),status);
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	
	/**
	 * 更新 POS终端.
	 */
	@Action("/pos/updatePos")
	public void UpdatePos(){
		JSONResult result=new JSONResult();
		try{
			Assert.hasLength(terminalNo.trim(),"终端号不可以为空");
			PayPos oldPayPos=payPosService.selectById(commPosId);
			if(oldPayPos!=null && !oldPayPos.getTerminalNo().equals(terminalNo)){
				Assert.isTrue(!isTerminalNoExisted(), "该终端号已经存在,请确定终端号");
			}
			PayPos payPos=payPosService.selectById(commPosId);
			payPos.setPosId(commPosId);
			payPos.setTerminalNo(terminalNo);
			payPos.setCommercialId(commercialId);
			payPos.setMemo(memo);
			payPosService.update(oldPayPos,payPos,this.getSessionUserName());
			result.put("posId", payPos.getPosId());
		}catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
		
	}
	/**
	 * 判断终端号是否存在.
	 * @return
	 */
	private boolean isTerminalNoExisted() {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(this.terminalNo)) {
			params.put("terminalNo", terminalNo);
		}
		Long total = payPosService.getSelectCount(params);
		if (total != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
     * 建立查询条件.
     * @return
     */
    private Map<String,String> buildParameter(){
		Map<String,String> params=new HashMap<String, String>();
		if (this.searchStatus!=null) {
			params.put("status",searchStatus);
		}
		if (this.commercialId!=null) {
			params.put("commercialId", commercialId.toString());
		}
		if (StringUtils.isNotEmpty(this.searchTerminalNo)) {
			params.put("terminalNo", searchTerminalNo.trim());
		}
		if (StringUtils.isNotEmpty(this.memo)) {
			params.put("memo", memo);
		}
		if (this.searchCommercialId!=null) {
			params.put("commercialId", searchCommercialId.toString());
		}
		return params;
	}

    
  //setters and getters
	public PayPos getPayPos() {
		return payPos;
	}

	public void setPayPos(PayPos payPos) {
		this.payPos = payPos;
	}

	public String getSearchTerminalNo() {
		return searchTerminalNo;
	}

	public void setSearchTerminalNo(String searchTerminalNo) {
		this.searchTerminalNo = searchTerminalNo;
	}

	public String getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}

	public Long getCommPosId() {
		return commPosId;
	}

	public void setCommPosId(Long commPosId) {
		this.commPosId = commPosId;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCommercialId() {
		return commercialId;
	}

	public void setCommercialId(Long commercialId) {
		this.commercialId = commercialId;
	}

	public List<PayPos> getCommPosList() {
		return commPosList;
	}

	public void setCommPosList(List<PayPos> commPosList) {
		this.commPosList = commPosList;
	}

	public List<PayPosCommercial> getCommercialList() {
		return commercialList;
	}

	public void setCommercialList(List<PayPosCommercial> commercialList) {
		this.commercialList = commercialList;
	}

	public String getReturnable() {
		return returnable;
	}

	public void setReturnable(String returnable) {
		this.returnable = returnable;
	}

	public Page<PayPos> getPayPosPage() {
		return payPosPage;
	}

	public void setPayPosPage(Page<PayPos> payPosPage) {
		this.payPosPage = payPosPage;
	}
 
	public Long getPerPageRecord() {
		return perPageRecord;
	}

	public void setPerPageRecord(Long perPageRecord) {
		this.perPageRecord = perPageRecord;
	}

	public void setPayPosService(PayPosService payPosService) {
		this.payPosService = payPosService;
	}

	public void setPayPosCommercialService(
			PayPosCommercialService payPosCommercialService) {
		this.payPosCommercialService = payPosCommercialService;
	}

	public Long getSearchCommercialId() {
		return searchCommercialId;
	}

	public void setSearchCommercialId(Long searchCommercialId) {
		this.searchCommercialId = searchCommercialId;
	}
	
}
