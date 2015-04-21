package com.lvmama.pet.sweb.storedcard;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.money.StoredCard;
import com.lvmama.comm.pet.po.money.StoredCardBatch;
import com.lvmama.comm.pet.service.money.StoredCardService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.utils.WebUtils;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.Constant;
/**
 * 储值卡生成管理.
 * @author sunruyi
 *
 */
@SuppressWarnings("serial")
@Results({
	@Result(name = "batch_list", location = "/WEB-INF/pages/back/stored/batch_list.jsp"),
	@Result(name = "batch_Generate", location = "/WEB-INF/pages/back/stored/batch_generate.jsp")
})
public class BatchManagementAction extends BackBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4278801542045637864L;
	
	/**
	 * 储值卡Service.
	 */
	private StoredCardService storedCardService;

	/**
	 * 批次列表页   批次号.
	 */
	private String batchNo;
	/**
	 * 批次列表页   开始日期.
	 */
	private Date beginTime;
	/**
	 * 批次列表页   结束日期.
	 */
	private Date endTime;
	
	/**
	 * 批次生成页   批次金额.
	 */
	private Long cardAmount;
	/**
	 * 批次生成页   批次号数量.
	 */
	private Long cardCount;
	/**
	 * 批次生成页   批次过期日期.
	 */
	private Date overTime;
	
	private Long batchId;
	private List comLogList;
	private String cancelMemo;
	private Page<StoredCardBatch> storedCardBatchPage;
	private Long perPageRecord=10L;


	/**
	 * 默认进入批次页面，不读取数据.
	 * @return
	 */
	@Action("/stored/goBatchList")
	public String goBatchList(){
		return "batch_list";
	}
	
	/**
	 * 
	 * @return
	 */
	@Action(value = "/stored/batchList")
	public String batchList(){
		Map<String,Object> param=buildParameter();	
		this.storedCardBatchPage = storedCardService.selectBatchListByParam(param, perPageRecord, page);
		storedCardBatchPage.setUrl(WebUtils.getUrl(getRequest()));
		return "batch_list";
	}
	
	/**
	 * 整批次卡作废.
	 * @return
	 */
//	@Action("/stored/batchCancel")
//	public void batchCancel(){
//		JSONResult result=new JSONResult();
//		try{
//			storedCardService.batchCancel(batchId, this.getOperatorName());
//		}catch(Exception ex){
//			result.raise(new JSONResultException(ex.getMessage()));
//		}
//		result.output(getResponse());
//	}
	
	/**
	 * 整批次卡作废. 
	 */
	@Action("/stored/batchCancel")
	public void batchCancel(){
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue(!(batchId==null||batchId<1),"批次不可以为空");
			Assert.hasLength(cancelMemo,"作废原因不可以为空");
			StoredCardBatch batch = storedCardService.queryBatchById(batchId);
			if(batch==null){
				throw new Exception("您修改的批次不存在!");
			}
			Assert.isTrue(storedCardService.isBatchCancel(batch), "批次不可以作废,批次卡状态为正常状态，且库存状态为未入库状态，激活状态为未激活才可以作废");
			batch.setStatus(Constant.STORED_CARD_ENUM.CANCEL.name());
				storedCardService.batchCancel(batch, this.getSessionUserName(),"作废原因:"+cancelMemo);
				
		}catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	
	
	
	/**
	 * 默认进入批次生成页面.
	 * @return
	 */
	@Action("/stored/batchGenerate")
	public String goBatchGenerate(){
		return "batch_Generate";
	}

	/**
	 * 
	 * @return
	 */
	@Action(value = "/stored/AddBatchGenerate")
	public void batchGenerate(){
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue(!(cardAmount==null),"面值不可以为空");
			Assert.isTrue(!(cardCount==null),"数量不可以为空");
			Assert.isTrue(!(overTime==null),"时间不可以为空");
			StoredCardBatch cardBatch=new StoredCardBatch();
			cardBatch.setBatchNo(batchGenerateNo());
			cardBatch.setCreateTime(new Date());
			cardBatch.setOperator(this.getSessionUserName());
			cardBatch.setAmount(cardAmount*100);
			cardBatch.setCardCount(cardCount);
			cardBatch.setOverTime(DateUtil.getDayEnd(overTime));
			storedCardService.saveStoredCardBatch(cardBatch);
			result.put("generateBatchNo", cardBatch.getBatchNo());
		}catch(Exception ex){
			log.error(ex);
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	
	/**
	 * 导出报表
	 */
	@Action(value = "/stored/reportBatch")
	public void doOutputData(){
		
		Map<String,Object> map=new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(this.batchNo)) {
			map.put("cardBatchNo", batchNo);
		}
		List<StoredCard> list=storedCardService.doOutputStoredCard(map,this.getSessionUserName());
		output(list, "/WEB-INF/resources/template/batchCardTemplate.xls");
	}
	
	private void output(List<StoredCard> list,String template){
		FileInputStream fin=null;
		OutputStream os=null;
		try{
			File templateResource = ResourceUtil.getResourceFile(template);
			Map beans = new HashMap();
			beans.put("cardList", list);
			XLSTransformer transformer = new XLSTransformer();
			String destFileName = Constant.getTempDir() + "/excel"+new Date().getTime()+".xls";
			transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName);
			getResponse().setHeader("Content-Disposition", "attachment; filename=" +this.batchNo + ".xls");
			getResponse().setContentType("application/vnd.ms-excel");
			os=getResponse().getOutputStream();
			fin=new FileInputStream(destFileName);
			IOUtils.copy(fin, os);
			os.flush();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			IOUtils.closeQuietly(fin);
			IOUtils.closeQuietly(os);
		}
	}
	
	
	/**
	 * 批次号的生成规则.
	 * @param amount
	 * @return
	 */
    private String batchGenerateNo(){
    	String batchNo="";
    	String batch = UtilityTool.formatDate(new Date(), "yyyyMMdd");
    	 if(cardAmount==50L){batchNo=batch+"A";}	 
    	 if(cardAmount==100L){batchNo=batch+"B";}
    	 if(cardAmount==200L){batchNo=batch+"C";}
    	 if(cardAmount==500L){batchNo=batch+"D";}
    	 if(cardAmount==1000L){batchNo=batch+"E";}
    	 batchNo=batchNo+stringDecimal(storedCardService.selectBatchCount(batchNo)+1L);
		return batchNo;
	}
    
    /**
     * 
     * @param batchCount
     * @return
     */
    private String stringDecimal(final Long batchCount){
    	DecimalFormat batchNoFormat = null;
        if(batchCount.toString().length() == 1) {
        	batchNoFormat=new DecimalFormat("000");
    	}else if(batchCount.toString().length() == 2) {
    		batchNoFormat=new DecimalFormat("00");
    	}else{
    		batchNoFormat=new DecimalFormat("0");
    	}
    	return  batchNoFormat.format(batchCount);
    }

    
    
    /**
     * 包装查询条件.
     * @return
     */
    private Map<String,Object> buildParameter(){
		Map<String,Object> map=new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(this.batchNo)) {
			map.put("batchNo", batchNo);
		}
		if (beginTime!=null) {
			map.put("beginTime",DateUtil.getDayStart(beginTime));
		}
		if (endTime!=null) {
			map.put("endTime", DateUtil.getDayEnd(endTime));
		}
		return map;
	}
    
    /**
     * 
     * @return
     */
   public String batchComList(){
	   comLogList=storedCardService.queryComLog(batchId,"STORED_CARD_BATCH");
	   return null;
   }

	public StoredCardService getStoredCardService() {
		return storedCardService;
	}

	public void setStoredCardService(StoredCardService storedCardService) {
		this.storedCardService = storedCardService;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(Long cardAmount) {
		this.cardAmount = cardAmount;
	}

	public Long getCardCount() {
		return cardCount;
	}

	public void setCardCount(Long cardCount) {
		this.cardCount = cardCount;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public List getComLogList() {
		return comLogList;
	}

	public void setComLogList(List comLogList) {
		this.comLogList = comLogList;
	}

	public String getCancelMemo() {
		return cancelMemo;
	}

	public void setCancelMemo(String cancelMemo) {
		this.cancelMemo = cancelMemo;
	}

	public Page<StoredCardBatch> getStoredCardBatchPage() {
		return storedCardBatchPage;
	}

	public void setStoredCardBatchPage(Page<StoredCardBatch> storedCardBatchPage) {
		this.storedCardBatchPage = storedCardBatchPage;
	}

	public Long getPerPageRecord() {
		return perPageRecord;
	}

	public void setPerPageRecord(Long perPageRecord) {
		this.perPageRecord = perPageRecord;
	}

}
