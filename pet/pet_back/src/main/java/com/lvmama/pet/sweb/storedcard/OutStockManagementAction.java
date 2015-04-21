package com.lvmama.pet.sweb.storedcard;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.money.StoredCard;
import com.lvmama.comm.pet.po.money.StoredCardStock;
import com.lvmama.comm.pet.service.money.StoredCardService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.Pagination;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.WebUtils;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.Constant;

/**
 * 储值卡入库/出库管理.
 * 
 * @author sunruyi
 * 
 */
@Results({ @Result(name = "outStockList", location = "/WEB-INF/pages/back/stored/out_stock_list.jsp"),
		@Result(name = "outStockGenerate", location = "/WEB-INF/pages/back/stored/out_stock_generate.jsp"),
		@Result(name = "cardStockSave", type = "json"),
		@Result(name = "outCardStockDetail", location = "/WEB-INF/pages/back/stored/card_stock_detail.jsp"),
		@Result(name = "showOutStockCardQueryMessage", type = "json"),
		@Result(name = "outStockUpdate", type = "json"),
		@Result(name = "activeCardVerify", location = "/WEB-INF/pages/back/stored/card_stock_verify.jsp"),
		@Result(name = "outCardStockDetailToUpdate", location = "/WEB-INF/pages/back/stored/card_out_stock_update.jsp"),
		@Result(name = "cancleOutStockDispatcher", location = "/WEB-INF/pages/back/stored/card_stock_cancle.jsp")
		})
@ParentPackage("json-default")
public class OutStockManagementAction extends BackBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2157476429354762750L;
	/**
	 * 储值卡库服务.
	 */
	private StoredCardService storedCardService;
	/**
	 * Pagination
	 */
	protected Pagination pagination;
	/**
	 * 库单号.
	 */
	private String stockId;
	/**
	 * 时间点A.
	 */
	private Date beginTime;
	/**
	 * 时间点B.
	 */
	private Date endTime;
	/**
	 * 出库单集合.
	 */
	private List<Map<String, Object>> cardStockList;
	/**
	 * 将要激活的出库单中卡统计集合.
	 */
	private List<Map<String, Object>> activeCardStatisticsList;
	/**
	 * 开始流水号.
	 */
	private String beginSerialNo;
	/**
	 * 结束流水号 .
	 */
	private String endSerialNo;
	/**
	 * 储值卡在库状态.
	 */
	private String stockStatus;
	/**
	 * 出库单中储值卡统计集合.
	 */
	private List<Map<String, Object>> cardStatisticsList;
	/**
	 * cardStatisticsList的index索引.
	 */
	private int index;
	/**
	 * 返回到页面的jsonMsg.
	 */
	private String jsonMsg;
	/**
	 * 出库单详细.
	 */
	private List<StoredCard> storedCardList;
	/**
	 * 客户.
	 */
	private String customer;
	/**
	 * 接收人.
	 */
	private String accepter;
	/**
	 * 备注信息.
	 */
	private String memo;
	/**
	 * 入/出库单.
	 */
	private StoredCardStock cardStock;
	/**
	 * 作废原因.
	 */
	private String cancleReason;
	/**
	 * 是否符合根据出库单来激活、作废储值卡.
	 */
	private boolean conform;
	/**
	 * 实收金额.
	 */
	private String actualReceived;
	/**
	 * 付款单位.
	 */
	private String paymentUnit;
	/**
	 * 收款方式.
	 */
	private String paymentType;
	/**
	 * 支付时间.
	 */
	private Date paymentTime;
	
	private Page<Map> outStockPage;

	private Long perPageRecord=10L;
	
	private String notInAmountList;
	private String notInAmount;

	
	/**
	 * 跳转至出库单查询页面.
	 * @return
	 */
	@Action(value = "/stored/outStockDispatcher")
	public String outStockDispatcher() {
		return "outStockList";
	}
	/**
	 * 出库单查询.
	 * @return
	 */
	@Action(value = "/stored/outStockList")
	public String outStockList() {
		Map<String, Object> parameters = initOutCardStockListParam();
		this.outStockPage = storedCardService.cardStockStats(parameters, perPageRecord, page);
		this.outStockPage.setUrl(WebUtils.getUrl(getRequest()));
		return "outStockList";
	}
	
	/**
	 * 为cardStockList方法构造参数.
	 * @return 参数map.
	 */
	private Map<String,Object> initOutCardStockListParam(){
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("stockType", "OUT_STOCK");
		if (!StringUtil.isEmptyString(stockId)) {
			maps.put("stockId", stockId.trim());
		}
		if (beginTime != null && endTime != null) {
			maps.put("beginTime", DateUtil.getDayStart(beginTime));
			maps.put("endTime", DateUtil.getDayEnd(endTime));
		}
		return maps;
	}
	
	/**
	 * 跳转到入库单生成页面.
	 * @return outStockGenerate
	 */
	@Action(value = "/stored/outStockGenerate")
	public String outStockGenerate() {
		super.removeSession("cardStatisticsList");
		return "outStockGenerate";
	}

	/**
	 * 统计两个流水号之间的储值卡面额、数量、总金额.
	 * @return outStockGenerate
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "/stored/outStockCardQuery")
	public String outStockCardQuery() {
		// 开始流水号、结束流水号都不为空时,展开查询.
		boolean flag = !StringUtil.isEmptyString(beginSerialNo) && !StringUtil.isEmptyString(endSerialNo);
		if (flag) {
			cardStatisticsList = storedCardService.addCardStatistics(cardStatisticsList, this.initOutStockCardQueryParam());
		}
		return "outStockGenerate";
	}
	/**
	 * 查询提示框.
	 * @return
	 */
	@Action(value = "/stored/showOutStockCardQueryMessage")
	public String showOutStockCardQueryMessage() {
		JSONResult result = new JSONResult();
		// 开始流水号、结束流水号都不为空时,展开查询.
		boolean flag = !StringUtil.isEmptyString(beginSerialNo) && !StringUtil.isEmptyString(endSerialNo);
		if (flag) {
			cardStatisticsList = storedCardService.addCardStatistics(cardStatisticsList, this.initOutStockCardQueryParam());
			if(cardStatisticsList.isEmpty()){
				jsonMsg = "nothing";
			}
		}
		return "outStockGenerate";
	}
	/**
	 * 为stockCardQuery初始化参数.
	 * @return 参数map.
	 */
	private Map<String,Object> initOutStockCardQueryParam(){
		Map<String,Object> maps = new HashMap<String,Object>();
		if (!StringUtil.isEmptyString(beginSerialNo) && !StringUtil.isEmptyString(endSerialNo)) {
			maps.put("beginSerialNo", beginSerialNo.trim());
			maps.put("endSerialNo", endSerialNo.trim());
			maps.put("stockStatus", Constant.STORED_CARD_ENUM.INTO_STOCK.name());
			maps.put("status", Constant.STORED_CARD_ENUM.NORMAL.name());
		}
		if(!StringUtil.isEmptyString(notInAmountList)){
			List list = StringUtil.split(notInAmountList, ",");
			maps.put("notInAmountList", list);
		}
		
		return maps;
	}
	
	/**
	 * 生成入库单.
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "/stored/outStockSave")
	public void outStockSave() throws Exception {
		JSONResult result = new JSONResult();
		// 开始流水号、结束流水号都不为空时,展开查询.
		boolean flag = !StringUtil.isEmptyString(beginSerialNo) && !StringUtil.isEmptyString(endSerialNo);
		if(!flag){
			this.jsonMsg = "nothing";
		} else {
			Long stockIdLong = new Long(0L);
			stockIdLong = storedCardService.buildCardStock(initOutStockSaveParam());
			this.stockId = stockIdLong.toString();
			this.jsonMsg = "ok";
		}
		result.put("jsonMsg", jsonMsg);
		result.put("stockId", stockId);
		result.output(getResponse());
	}
	
	/**
	 * 为outStockSave初始化参数.
	 * @return 参数map.
	 */
	private Map<String,Object> initOutStockSaveParam(){
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("type", "OUT");
		maps.put("operatorName", super.getSessionUserName());
		maps.put("customer",customer);
		maps.put("accepter", accepter);
		maps.put("memo", memo);
		
		if (!StringUtil.isEmptyString(beginSerialNo) && !StringUtil.isEmptyString(endSerialNo)) {
			maps.put("beginSerialNo", beginSerialNo.trim());
			maps.put("endSerialNo", endSerialNo.trim());
			maps.put("stockStatus", Constant.STORED_CARD_ENUM.INTO_STOCK.name());
			maps.put("status", Constant.STORED_CARD_ENUM.NORMAL.name());
		}
		if(!StringUtil.isEmptyString(notInAmountList)){
			List list = StringUtil.split(notInAmountList,",");
			maps.put("notInAmountList", list);
		}
		return maps;
	}
	
	/**
	 * 删除之前查询出来的储值卡.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "/stored/outStockCardDelete")
	public String outStockCardDelete() {
		cardStatisticsList = (List<Map<String, Object>>) super.getSession("cardStatisticsList");
		cardStatisticsList = storedCardService.removeCardStatisticsByIndex(cardStatisticsList, index);
		super.removeSession("cardStatisticsList");
		return "outStockGenerate";
	}
	/**
	 * 出库单详细.
	 * @return
	 */
	@Action(value = "/stored/outCardStockDetail")
	public String outCardStockDetail(){
		if (!StringUtil.isEmptyString(stockId)) {
			cardStock = storedCardService.queryCardStockByStockId(new Long(stockId.trim()));
		}
		return "outCardStockDetail";
	}
	/**
	 * 弹出作废原因填写页面.
	 */
	@Action(value="cancleOutStockDispatcher")
	public String cancleOutStockDispatcher(){
		return "cancleOutStockDispatcher";
	}
	/**
	 * 作废出库单状态.
	 * @return
	 */
	@Action(value="/stored/cancleOutStockByParam")
	public void cancleOutStockByParam(){
		JSONResult result=new JSONResult();
		try{
			boolean flag = storedCardService.cancleOutStockByParam(initCancleOutStockByParam());
			result.put("flag", flag);
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	/**
	 * 为intoStockCancle初始化参数.
	 * @return
	 */
	private Map<String,Object> initCancleOutStockByParam(){
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("outStockId", stockId.trim());
		maps.put("operatorName", super.getSessionUserName());
		maps.put("cancleReason", cancleReason);
		return maps;
	}
	@Action(value="/stored/activeCardVerify")
	public String activeCardVerify(){
		activeCardStatisticsList = storedCardService.verifyOutStock(this.initBeforeActiveCardByOutStockIdParam());
		conform = storedCardService.isOK(Long.parseLong(stockId));
		return "activeCardVerify";
	}
	private Map<String,Object> initBeforeActiveCardByOutStockIdParam(){
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("outStockId", stockId.trim());
		return maps;
	}
	/**
	 * 展示出库单修改页面.
	 * @return
	 */
	@Action(value="/stored/outCardStockDetailToUpdate")
	public String outCardStockDetailToUpdate(){
		if (!StringUtil.isEmptyString(stockId)) {
			cardStock = storedCardService.queryCardStockByStockId(new Long(stockId.trim()));
		}
		return "outCardStockDetailToUpdate";
	}
	/**
	 * 出库单修改.
	 * @return
	 */
	@Action(value="/stored/outStockUpdate")
	public String outStockUpdate(){
		boolean isSuccess = storedCardService.updateOutStock(cardStock, super.getSessionUserName());
		if(isSuccess){
			this.jsonMsg = "true";
		} else {
			this.jsonMsg = "false";
		}
		return "outStockUpdate";
	}
	/**
	 * 激活该出库单内所有储值卡.
	 */
	@Action(value="/stored/activeCardByOutStockId")
	public void activeCardByOutStockId(){
		JSONResult result=new JSONResult();
		try{
			boolean flag = storedCardService.activeCardByParam(initActiveCardByOutStockIdParam());
			result.put("flag", flag);
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	/**
	 * 为activeCardByOutStockId初始化参数.
	 * @return
	 */
	private Map<String,Object> initActiveCardByOutStockIdParam(){
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("outStockId", stockId.trim());
		maps.put("paymentType", paymentType);
		maps.put("paymentUnit", paymentUnit);
		maps.put("paymentTime", paymentTime);
		maps.put("actualReceived", actualReceived);
		maps.put("operatorName", super.getSessionUserName());
		maps.put("status", Constant.STORED_CARD_ENUM.NORMAL.name());
		return maps;
	}
	/**
	 * 出库单导出.
	 */
	@Action(value="/stored/outStockCardExport")
	public void outStockCardExport(){
		List<StoredCard> list = storedCardService.storedCardExport(this.initOutStockCardExportParam());
		output(list, "/WEB-INF/resources/template/stockCardTemplate.xls");
	}
	private Map<String,Object> initOutStockCardExportParam(){
		Map<String,Object> maps = new HashMap<String,Object>();
		maps.put("outStockId", stockId.trim());
		maps.put("operatorName", super.getSessionUserName());
		return maps;
	}
	private void output(List<StoredCard> list,String template){
		FileInputStream fin=null;
		OutputStream os=null;
		try{
			File templateResource = ResourceUtil.getResourceFile(template);
			Map<String,Object> beans = new HashMap<String,Object>();
			beans.put("cardList", list);
			XLSTransformer transformer = new XLSTransformer();
			String destFileName = Constant.getTempDir() + "/excel"+new Date().getTime()+".xls";
			transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName);
			getResponse().setHeader("Content-Disposition", "attachment; filename=" +this.stockId + ".xls");
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
	public void setstoredCardService(StoredCardService storedCardService) {
		this.storedCardService = storedCardService;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStockId() {
		return stockId;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public List<Map<String, Object>> getCardStockList() {
		return cardStockList;
	}
	public void setCardStockList(List<Map<String, Object>> cardStockList) {
		this.cardStockList = cardStockList;
	}
	public String getBeginSerialNo() {
		return beginSerialNo;
	}
	public void setBeginSerialNo(String beginSerialNo) {
		this.beginSerialNo = beginSerialNo;
	}
	public String getEndSerialNo() {
		return endSerialNo;
	}
	public void setEndSerialNo(String endSerialNo) {
		this.endSerialNo = endSerialNo;
	}
	public String getStockStatus() {
		return stockStatus;
	}
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}
	public List<Map<String, Object>> getCardStatisticsList() {
		return cardStatisticsList;
	}
	public void setCardStatisticsList(List<Map<String, Object>> cardStatisticsList) {
		this.cardStatisticsList = cardStatisticsList;
	}
	public List<Map<String, Object>> getActiveCardStatisticsList() {
		return activeCardStatisticsList;
	}
	public void setActiveCardStatisticsList(List<Map<String, Object>> activeCardStatisticsList) {
		this.activeCardStatisticsList = activeCardStatisticsList;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getJsonMsg() {
		return jsonMsg;
	}
	public void setJsonMsg(String jsonMsg) {
		this.jsonMsg = jsonMsg;
	}
	public List<StoredCard> getStoredCardList() {
		return storedCardList;
	}
	public void setStoredCardList(List<StoredCard> storedCardList) {
		this.storedCardList = storedCardList;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getAccepter() {
		return accepter;
	}
	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public StoredCardStock getCardStock() {
		return cardStock;
	}
	public void setCardStock(StoredCardStock cardStock) {
		this.cardStock = cardStock;
	}
	public String getCancleReason() {
		return cancleReason;
	}
	public void setCancleReason(String cancleReason) {
		this.cancleReason = cancleReason;
	}
	public boolean getConform() {
		return conform;
	}
	public void setConform(boolean conform) {
		this.conform = conform;
	}
	public String getActualReceived() {
		return actualReceived;
	}
	public void setActualReceived(String actualReceived) {
		this.actualReceived = actualReceived;
	}
	public String getPaymentUnit() {
		return paymentUnit;
	}
	public void setPaymentUnit(String paymentUnit) {
		this.paymentUnit = paymentUnit;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public Date getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getNotInAmount() {
		return notInAmount;
	}
	public void setNotInAmount(String notInAmount) {
		this.notInAmount = notInAmount;
	}
	public String getNotInAmountList() {
		return notInAmountList;
	}
	public void setNotInAmountList(String notInAmountList) {
		this.notInAmountList = notInAmountList;
	}
	public Page<Map> getOutStockPage() {
		return outStockPage;
	}
	public void setOutStockPage(Page<Map> outStockPage) {
		this.outStockPage = outStockPage;
	}
	public Long getPerPageRecord() {
		return perPageRecord;
	}
	public void setPerPageRecord(Long perPageRecord) {
		this.perPageRecord = perPageRecord;
	}
}
