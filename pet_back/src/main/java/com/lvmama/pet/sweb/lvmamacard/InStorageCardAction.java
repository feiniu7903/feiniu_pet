package com.lvmama.pet.sweb.lvmamacard;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.po.lvmamacard.LvmamaStoredCard;
import com.lvmama.comm.pet.service.lvmamacard.LvmamacardService;
import com.lvmama.comm.pet.vo.EmailAttachmentData;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.FileUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.lvmamacard.DESUtils;
import com.lvmama.comm.utils.lvmamacard.JxcellEnc;
import com.lvmama.comm.utils.lvmamacard.LvmamaCardUtils;
import com.lvmama.comm.vo.Constant;
/**
 * 
 * @author nixianjun
 *
 */
@Results({@Result(name="input",location="/WEB-INF/pages/back/lvmamacard/inStorageCard.jsp"),
	     @Result(name="inStorageCard" ,location="/WEB-INF/pages/back/lvmamacard/inStorageCard.jsp" ),
	      @Result(name="inStorageCard_add" ,location="/WEB-INF/pages/back/lvmamacard/inStorageCard_add.jsp" ),
	      @Result(name="inStorageCard_initpass" ,location="/WEB-INF/pages/back/lvmamacard/inStorageCard_initpass.jsp" )
 })
public class InStorageCardAction extends CardBaseAction {
	private static Log log = LogFactory.getLog(InStorageCardAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1457548754L;
	@Autowired
	private LvmamacardService lvmamacardService;
	
	private Integer amount;
	private Integer count;
	private String  validDate;
	private String inCode;
	private String cardNo;
	private String searchIncode;
	private String searchCardNo;
	private Integer searchAmount;
	private Integer searchInStatus;
	private long cancelCount;
	@Autowired
	private EmailClient emailClient;
	/**
	 * 卡总量
	 */
	private Long cardCount;
	/**
	 * 通过卡没出库数量卡总量
	 */
	private Long cardCountForNoStock;
	 
	/**
	 * 
	 * @return
	 * @author nixianjun 2013-12-6
	 */
	@Action("/inStorageCard/query")
	public String query(){
		    Map param=new HashMap<String, Object>();
		    if(StringUtils.isNotEmpty(searchIncode)){
		    	param.put("incode", searchIncode);
		    }
		    if(StringUtils.isNotEmpty(searchCardNo)){
		    	param.put("cardNo", searchCardNo);
		    }
		    if(null!=searchAmount){
		    	param.put("amount", searchAmount);
		    }
		    if(null!=searchInStatus){
		    	param.put("inStatus", searchInStatus);
		    }
		    pagination=initPage();
		    pagination.setCurrentPage(pagination.getCurrentPage());
			pagination.setTotalResultSize(lvmamacardService.countByParamForInStorege(param));
			pagination.setPageSize(10L);
			if(pagination.getTotalResultSize()>0){
				 param.put("start", pagination.getStartRows());
				 param.put("end", pagination.getEndRows());
				 param.put("orderby", "in_id");
				 param.put("order", "desc");
 				 pagination.setAllItems(lvmamacardService.queryByParamForInStorege(param));
			}
			pagination.buildUrl(getRequest());  
			
			cardCount=lvmamacardService.getCardCountByParamForInStorege(param);
			//未出库卡总量
			param.put("status", Constant.CARD_STATUS.INITIALIZATION.getCode());
			param.put("stockStatus", Constant.STORED_CARD_STOCK_STATUS.INTO_STOCK.getCode());
			cardCountForNoStock=lvmamacardService.getCardCountByParamForInStorege(param);
 		return "inStorageCard";
	}
	@Action("/inStorageCard/initadd")
	public String initadd(){
		 return "inStorageCard_add";
	}
	@Action("/inStorageCard/add")
	public void add(){
		Map map=new HashMap<String, Object>();
		map.put("success", "true");
		if(null==amount||null==count){
			map.put("success", "false");
			map.put("message", "面值或者数量或者日期为空！");
		}else if(StringUtil.isEmptyString(validDate)){
			lvmamacardService.insertStoredCardInAndbatchInsertStoredCard(amount,count,null);
			super.comLogService.insert(LvmamaCardUtils.STORED_CARD_IN, null,null, super.getSessionUser().getUserName(), Constant.COM_LOG_OBJECT_TYPE.INSTOREDCARD.getCode(), "入库单新增", "入库单新增,面值："+amount+"数量:"+count+"有效期：无", null);
		}else{
			lvmamacardService.insertStoredCardInAndbatchInsertStoredCard(amount,count,DateUtil.stringToDate(validDate
					+ " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			super.comLogService.insert(LvmamaCardUtils.STORED_CARD_IN, null,null, super.getSessionUser().getUserName(), Constant.COM_LOG_OBJECT_TYPE.INSTOREDCARD.getCode(), "入库单新增", "入库单新增,面值："+amount+"数量:"+count, null);
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
	}
	@Action("/inStorageCard/cancel")
	public void cancel(){
		Map map=new HashMap<String, Object>();
		map.put("success","false");
		if(StringUtils.isNotEmpty(inCode)){
			lvmamacardService.cancelLvmamaStoredCardByInCode(inCode, Constant.STORED_CARD_STATUS.CANCEL.getCode(),Constant.CARD_IN_STATUS.three.getCode());
			super.comLogService.insert(LvmamaCardUtils.STORED_CARD_IN, null,null, super.getSessionUser().getUserName(), Constant.COM_LOG_OBJECT_TYPE.INSTOREDCARD.getCode(), "入库单取消", "入库单取消,入库号："+inCode, null);
			map.put("success","true");
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
 	}
	/**
	 * 校验通过
	 * 
	 * @author nixianjun 2013-12-10
	 */
	@Action("/inStorageCard/validatePass")
	public void validatePass(){
		Map map=new HashMap<String, Object>();
		map.put("success","false");
		if(StringUtils.isNotEmpty(inCode)){
			Map param=new HashMap<String, Object>();
			param.put("inCode", inCode);
			param.put("inStatus", Integer.valueOf(Constant.CARD_IN_STATUS.two.getCode()));
			lvmamacardService.updateForInStorage(param);
			super.comLogService.insert(LvmamaCardUtils.STORED_CARD_IN, null,null, super.getSessionUser().getUserName(), Constant.COM_LOG_OBJECT_TYPE.INSTOREDCARD.getCode(), "入库单校验", "入库单已校验,入库号："+inCode, null);
			map.put("success","true");
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
  	}
	@Action("/inStorageCard/initpass")
	public String initpass(){
		Map param =new HashMap<String, Object>();
		param.put("cardBatchNo", inCode);
		param.put("status", Constant.STORED_CARD_STATUS.CANCEL.getCode());
		cancelCount=0L;
		cancelCount=lvmamacardService.countByParamForLvmamaStoredCard(param);
		return "inStorageCard_initpass";
	}
	/**
	 * 入库批次通过
	 * 
	 * @author nixianjun 2013-12-17
	 */
	@Action("/validateStorageCard/pass")
	public void pass(){
		Map map=new HashMap<String, Object>();
		map.put("success", "false");
		if(StringUtil.isNotEmptyString(inCode)){
			lvmamacardService.passLvmamaStoredCardByInCode(inCode, Constant.STORED_CARD_STOCK_STATUS.INTO_STOCK.getCode(),Constant.CARD_IN_STATUS.four.getCode());
			map.put("success", "true");
			super.comLogService.insert(LvmamaCardUtils.STORED_CARD_IN, null,null, super.getSessionUser().getUserName(), Constant.COM_LOG_OBJECT_TYPE.INSTOREDCARD.getCode(), "入库单通过", "入库单通过，入库号："+inCode, null);

		}
		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
	}
	@Action("/inStorageCard/outexcel")
	public void outexcel(){
		Map param=new HashMap<String, Object>();
		param.put("cardBatchNo", inCode);
		param.put("FORREPORT", "true");
		List<LvmamaStoredCard>  list=lvmamacardService.queryByParamForLvmamaStoredCard(param);
		 List<LvmamaStoredCard> newList=new ArrayList<LvmamaStoredCard>();
		for(LvmamaStoredCard card:list){
			card.setDesPassword(DESUtils.getInstance().getDesString(
					card.getPassword()));
			newList.add(card);
		} 
		 if (null != newList&&newList.size()>=0) {
			 //删除缓存文件
			 deteleTmepFile();
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("list", newList);
 			String excelTemplate = LvmamaCardUtils.LVMAMACARDINEXCELFILE;
			String destFileName =  super.writeExcelByjXls(beans,
					excelTemplate,ResourceUtil.getResourceFile(LvmamaCardUtils.LVMAMACARDINEXCELFILETEMP1).getAbsolutePath());
			//输出文件
			String outfile= ResourceUtil.getResourceFile(LvmamaCardUtils.LVMAMACARDINEXCELFILETEMP2+inCode+".xls").getAbsolutePath();
			//加密
		    JxcellEnc.doEnc(destFileName, outfile, LvmamaCardUtils.getLvmamacardFilePassword());
			super.comLogService.insert(LvmamaCardUtils.STORED_CARD_IN, null,null, super.getSessionUser().getUserName(), Constant.COM_LOG_OBJECT_TYPE.INSTOREDCARD.getCode(), "入库单导出并发邮件", "入库单导出并发邮件，入库号："+inCode, null);
			//导出
			// super.writeAttachment(outfile, "lvmamacard"+inCode);
			//发送邮件
			sendEmail(outfile);
			 //删除缓存文件
			 //deteleTmepFile();
			 this.sendAjaxMsg("发送邮件成功!"+"邮件地址："+LvmamaCardUtils.getLvmamacardCardEmailToAddress());
		} 
	}
	
	/**
	 * 
	 * 
	 * @author nixianjun 2013-12-11
	 */
	private void deteleTmepFile(){
		try {
			FileUtil.deleteDirectory(ResourceUtil.getResourceFile(LvmamaCardUtils.LVMAMACARDINEXCELFILETEMPDIR).getAbsolutePath());
		} catch (Exception e) {
			this.log.info("instorageCardAction 入库导出，缓存文件删除，出现异常！");
		}
	}
	public void sendEmail(String fileName){
		EmailContent emailContent=new EmailContent();
		emailContent.setFromName(LvmamaCardUtils.getLvmamacardCardEmailFromName());
		emailContent.setToAddress(LvmamaCardUtils.getLvmamacardCardEmailToAddress());
		emailContent.setSendTime(new Date());
		emailContent.setSubject(LvmamaCardUtils.getLvmamacardCardEmailSubject());
		emailContent.setContentText(LvmamaCardUtils.getLvmamacardCardEmailContentText());
		List<EmailAttachmentData> files = new ArrayList<EmailAttachmentData>();
		// 增加附件
 		 EmailAttachmentData data = new EmailAttachmentData(new File(fileName));
		 files.add(data);
 		emailClient.sendEmailDirect(emailContent, files);
	}
	
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getInCode() {
		return inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getSearchIncode() {
		return searchIncode;
	}
	public void setSearchIncode(String searchIncode) {
		this.searchIncode = searchIncode;
	}
	public String getSearchCardNo() {
		return searchCardNo;
	}
	public void setSearchCardNo(String searchCardNo) {
		this.searchCardNo = searchCardNo;
	}
	public Integer getSearchAmount() {
		return searchAmount;
	}
	public void setSearchAmount(Integer searchAmount) {
		this.searchAmount = searchAmount;
	}
	public Integer getSearchInStatus() {
		return searchInStatus;
	}
	public void setSearchInStatus(Integer searchInStatus) {
		this.searchInStatus = searchInStatus;
	}
	public long getCancelCount() {
		return cancelCount;
	}
	public void setCancelCount(long cancelCount) {
		this.cancelCount = cancelCount;
	}
	public Long getCardCount() {
		return cardCount;
	}
	public void setCardCount(Long cardCount) {
		this.cardCount = cardCount;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public Long getCardCountForNoStock() {
		return cardCountForNoStock;
	}
	public void setCardCountForNoStock(Long cardCountForNoStock) {
		this.cardCountForNoStock = cardCountForNoStock;
	}
}
