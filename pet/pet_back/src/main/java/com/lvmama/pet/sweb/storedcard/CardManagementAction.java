package com.lvmama.pet.sweb.storedcard;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.money.StoredCard;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.service.money.StoredCardService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.Pagination;
import com.lvmama.comm.utils.WebUtils;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.Constant;
/**
 * 储值卡日常管理.
 * @author liwenzhan
 *
 */
@Results({
	@Result(name = "card_list", location = "/WEB-INF/pages/back/stored/card_management.jsp")
})
public class CardManagementAction extends BackBaseAction {
	
	
	/**
	 * 序列化ID.
	 */
	private static final long serialVersionUID = 264118791662651337L;
	/**
	 * Pagination.
	 */
	protected Pagination pagination;
	
	/**
	 * 要修改卡的ID.
	 */
	private Long scardId;
	/**
	 * 要修改卡的状态.
	 */
	private String cardStatus;
	
	private String cardActiveStatus;
	/**
	 * 批次号.
	 */
	private String batchNo;
	/**
	 * 流水号.
	 */
	private String serialNo;
	/**
	 * 在库开始时间.
	 */
	private Date beginOutTime;
	/**
	 * 在库结束时间.
	 */
	private Date endOutTime;
	/**
	 * 在库状态.
	 */
	private String stockStatus;
	/**
	 * 常规状态.
	 */
	private String status;
	/**
	 * 激活状态.
	 */
	private String activeStatus;
	
	/**
	 * 开始过期时间.
	 */
	private Date beginOverTime;
	/**
	 * 开始过期时间.
	 */
	private Date endOverTime;
	
	 
	/**
	 * 储值卡Service.
	 */
	private StoredCardService storedCardService;
	
	/**
	 * 入库单单号.
	 */
	private String intoStockId;
	/**
	 * 出库单单号.
	 */
	private String outStockId;
	
	/**
	 * 作废原因.
	 */
	private String cancelMemo;
	
	private Page<StoredCard> storedCardPage;
	private Long perPageRecord=10L;

	/**
	 * 默认进入卡列表页面，不读取数据.
	 * @return
	 */
	@Action(value = "/stored/geCardList")
	public String goBatchList(){
		return "card_list";
	}
	
	/**
	 * 查询.
	 * @return
	 */
	@Action(value = "/stored/cardList")
	public String batchList(){
		
		Map<String,String> map=new HashMap<String, String>();
		
		Map<String,Object> param=buildParameter();
		storedCardPage =storedCardService.selectCardByParam(param,perPageRecord,page);
		storedCardPage.setUrl(WebUtils.getUrl(getRequest(),map));
		return "card_list";
	}
	
	
	
	/**
	 * 更改常规状态. 
	 */
	@Action("/stored/changeStatus")
	public void changeVisaStatus(){
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue(!(scardId==null||scardId<1),"储值卡不可以为空");
			Assert.hasLength(cardStatus,"状态不可以为空");
			StoredCard card = storedCardService.queryStoredCardById(Long.valueOf(scardId));
			if(card==null){
				throw new Exception("您修改的储值卡不存在!");
			}
			card.setStatus(cardStatus);
			if(Constant.STORED_CARD_ENUM.FINISHED.name().equals(cardStatus)){
			  card.setOverTime(new Date());
			}
			storedCardService.updateStoredCard(card,this.getSessionUserName(),"STATUS");
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 储值卡作废. 
	 */
	@Action("/stored/changeCancelStatus")
	public void changeCancelStatus(){
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue(!(scardId==null||scardId<1),"储值卡不可以为空");
			Assert.hasLength(cancelMemo,"作废原因不可以为空");
			StoredCard card = storedCardService.queryStoredCardById(Long.valueOf(scardId));
			if(card==null){
				throw new Exception("您修改的储值卡不存在!");
			}
			card.setStatus(Constant.STORED_CARD_ENUM.CANCEL.name());
			storedCardService.updateStoredCard(card,this.getSessionUserName(),"OTHER","作废原因:"+cancelMemo);
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	
	/**
	 * 更改激活状态. 
	 */
	@Action("/stored/changeActiceStatus")
	public void changeActiceStatus(){
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue(!(scardId==null||scardId<1),"储值卡不可以为空");
			Assert.hasLength(cardActiveStatus,"状态不可以为空");
			StoredCard card = storedCardService.queryStoredCardById(Long.valueOf(scardId));
			if(card==null){
				throw new Exception("您修改的储值卡不存在!");
			}
			card.setActiveStatus(cardActiveStatus);
			storedCardService.updateStoredCard(card,this.getSessionUserName(),"ACTIVESTATUS");
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 储值卡延期. 
	 */
	@Action("/stored/changeCardOver")
	public void changeCardOver(){
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue(!(scardId==null||scardId<1),"储值卡不可以为空");
			StoredCard card = storedCardService.queryStoredCardById(Long.valueOf(scardId));
			if(card==null){
				throw new Exception("您修改的储值卡不存在!");
			}
			//延期一年
			card.setOverTime(DateUtil.mergeDateTimeAddYear(card.getOverTime(),1));
			if(Constant.STORED_CARD_ENUM.FINISHED.name().equals(card.getStatus())){
			card.setStatus(Constant.STORED_CARD_ENUM.NORMAL.name());
			}
			storedCardService.updateStoredCard(card,this.getSessionUserName(),"OVERTIME");
			result.put("overTime", DateFormatUtils.format(card.getOverTime(), "yyyy-MM-dd HH:mm:ss"));
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	
	 /**
     * 包装储值卡查询条件.
     * @return
     */
    private Map<String,Object> buildParameter(){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("type", Constant.CARD_TYPE.olded.getCode());
		if (StringUtils.isNotEmpty(this.batchNo)) {
			map.put("cardBatchNo", batchNo);
		}
		if (StringUtils.isNotEmpty(this.serialNo)) {
			map.put("serialNo", serialNo);
		}
		if (beginOutTime!=null) {
			map.put("beginOutTime",DateUtil.getDayStart(beginOutTime));
		}
		if (endOutTime!=null) {
			map.put("endOutTime", DateUtil.getDayEnd(endOutTime));
		}
		if (beginOverTime!=null) {
			map.put("beginOverTime",DateUtil.getDayStart(beginOverTime));
		}
		if (endOverTime!=null) {
			map.put("endOverTime", DateUtil.getDayEnd(endOverTime));
		}
		if (StringUtils.isNotEmpty(this.status)) {
			map.put("status", status);
		}
		if (StringUtils.isNotEmpty(this.activeStatus)) {
			map.put("activeStatus", activeStatus);
		}
		if (StringUtils.isNotEmpty(this.stockStatus)) {
			map.put("stockStatus", stockStatus);
		}
		if (StringUtils.isNotEmpty(this.intoStockId)) {
			map.put("intoStockId", intoStockId);
		}
		if (StringUtils.isNotEmpty(this.outStockId)) {
			map.put("outStockId", outStockId);
		}
		return map;
	}

    
    
    
    /**
	 * 读取数据库当中储值卡的常规状态
	 * @return
	 */
	public List<CodeItem> getCardStatusList(){
		return CodeSet.getInstance().getCachedCodeList(Constant.CODE_TYPE.STORED_CARD_STATUS.name());
	}
	/**
	 * 读取数据库当中储值卡的激活状态
	 * @return
	 */
	public Constant.STORED_CARD_ACTIVE_STATUS[] getCardActiveStatusList(){
		return Constant.STORED_CARD_ACTIVE_STATUS.values();
	}

	public Long getScardId() {
		return scardId;
	}

	public void setScardId(Long scardId) {
		this.scardId = scardId;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public StoredCardService getStoredCardService() {
		return storedCardService;
	}

	public void setStoredCardService(StoredCardService storedCardService) {
		this.storedCardService = storedCardService;
	}

	public String getCardActiveStatus() {
		return cardActiveStatus;
	}

	public void setCardActiveStatus(String cardActiveStatus) {
		this.cardActiveStatus = cardActiveStatus;
	}

	public Date getBeginOutTime() {
		return beginOutTime;
	}

	public void setBeginOutTime(Date beginOutTime) {
		this.beginOutTime = beginOutTime;
	}

	public Date getEndOutTime() {
		return endOutTime;
	}

	public void setEndOutTime(Date endOutTime) {
		this.endOutTime = endOutTime;
	}

	public Date getBeginOverTime() {
		return beginOverTime;
	}

	public void setBeginOverTime(Date beginOverTime) {
		this.beginOverTime = beginOverTime;
	}

	public Date getEndOverTime() {
		return endOverTime;
	}

	public void setEndOverTime(Date endOverTime) {
		this.endOverTime = endOverTime;
	}

	public String getIntoStockId() {
		return intoStockId;
	}

	public void setIntoStockId(String intoStockId) {
		this.intoStockId = intoStockId;
	}

	public String getOutStockId() {
		return outStockId;
	}

	public void setOutStockId(String outStockId) {
		this.outStockId = outStockId;
	}

	public String getCancelMemo() {
		return cancelMemo;
	}

	public void setCancelMemo(String cancelMemo) {
		this.cancelMemo = cancelMemo;
	}

	public Page<StoredCard> getStoredCardPage() {
		return storedCardPage;
	}

	public void setStoredCardPage(Page<StoredCard> storedCardPage) {
		this.storedCardPage = storedCardPage;
	}

	public Long getPerPageRecord() {
		return perPageRecord;
	}

	public void setPerPageRecord(Long perPageRecord) {
		this.perPageRecord = perPageRecord;
	}

}
