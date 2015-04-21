package com.lvmama.pet.sweb.storedcard;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import com.lvmama.comm.pet.service.money.StoredCardService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.WebUtils;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

/**
 * 储值卡入库/出库管理.
 * 
 * @author sunruyi
 * 
 */
@Results({
		@Result(name = "intoStockList", location = "/WEB-INF/pages/back/stored/into_stock_list.jsp"),
		@Result(name = "intoStockGenerate", location = "/WEB-INF/pages/back/stored/into_stock_generate.jsp") })
@ParentPackage("json-default")
public class IntoStockManagementAction extends BackBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2579647558580797705L;
	/**
	 * 储值卡库服务.
	 */
	private StoredCardService storedCardService;
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
	 * 入库单集合.
	 */
	private List<Map<String, Object>> cardStockList;
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
	 * 入库单中储值卡统计集合.
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
	 * 入库单详细.
	 */
	private List<StoredCard> storedCardList;

	private Page<Map> storedCardStockPage;

	private Long perPageRecord = 10L;

	private String amountStr;
	
	private String notInAmountList;


	/**
	 * 跳转至入库单查询页面.
	 * 
	 * @return
	 */
	@Action(value = "/stored/intoStockDispatcher")
	public String intoStockDispatcher() {
		return "intoStockList";
	}

	/**
	 * 入库单查询.
	 * 
	 * @return
	 */
	@Action(value = "/stored/intoStockList")
	public String intoStockList() {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> parameters = initIntoCardStockListParam();

		this.storedCardStockPage = storedCardService
				.cardStockStats(parameters, perPageRecord, page);


		this.storedCardStockPage.setUrl(WebUtils.getUrl(getRequest()));

		return "intoStockList";
	}

	/**
	 * 为cardStockList方法构造参数.
	 * 
	 * @return 参数map.
	 */
	private Map<String, Object> initIntoCardStockListParam() {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("stockType", "INTO_STOCK");
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
	 * 
	 * @return intoStockGenerate
	 */
	@Action(value = "/stored/intoStockGenerate")
	public String intoStockGenerate() {
		super.removeSession("cardStatisticsList");
		return "intoStockGenerate";
	}

	/**
	 * 统计两个流水号之间的储值卡面额、数量、总金额.
	 * 
	 * @return intoStockGenerate
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "/stored/intoStockCardQuery")
	public String intoStockCardQuery() {
		// 开始流水号、结束流水号都不为空时,展开查询.
		boolean flag = !StringUtil.isEmptyString(beginSerialNo)
				&& !StringUtil.isEmptyString(endSerialNo);
		if (flag) {
			cardStatisticsList = storedCardService.addCardStatistics(cardStatisticsList,
					this.initIntoStockCardQueryParam());
		}
		return "intoStockGenerate";
	}

	/**
	 * 查询提示框.
	 * 
	 * @return
	 */
	@Action(value = "/stored/showIntoStockCardQueryMessage")
	public void showIntoStockCardQueryMessage() {
		JSONResult result = new JSONResult();
		// 开始流水号、结束流水号都不为空时,展开查询.
		boolean flag = !StringUtil.isEmptyString(beginSerialNo)
				&& !StringUtil.isEmptyString(endSerialNo);
		if (flag) {
			cardStatisticsList = storedCardService.addCardStatistics(cardStatisticsList,
					this.initIntoStockCardQueryParam());
			if (cardStatisticsList.isEmpty()) {
				jsonMsg = "nothing";
				result.put("jsonMsg", jsonMsg);
			}
		}
		result.output(getResponse());
	}

	/**
	 * 为stockCardQuery初始化参数.
	 * 
	 * @return 参数map.
	 */
	private Map<String, Object> initIntoStockCardQueryParam() {
		Map<String, Object> maps = new HashMap<String, Object>();
		if (!StringUtil.isEmptyString(beginSerialNo) && !StringUtil.isEmptyString(endSerialNo)) {
			maps.put("beginSerialNo", beginSerialNo.trim());
			maps.put("endSerialNo", endSerialNo.trim());
			maps.put("stockStatus", Constant.STORED_CARD_ENUM.NO_STOCK.name());
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
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "/stored/intoStockSave")
	public void intoStockSave() throws Exception {
		JSONResult result = new JSONResult();
		// 开始流水号、结束流水号都不为空时,展开查询.
		boolean flag = !StringUtil.isEmptyString(beginSerialNo)
				&& !StringUtil.isEmptyString(endSerialNo);
		List amountList = new ArrayList();
		if (!StringUtil.isEmptyString(amountStr)) {
			amountList = StringUtil.split(amountStr, ",");
		}
		if (!flag && amountStr == null) {
			this.jsonMsg = "nothing";
		} else {
			Long stockIdLong = new Long(0L);
			stockIdLong = storedCardService.buildCardStock(initIntoStockSaveParam());
			this.stockId = stockIdLong.toString();
			this.jsonMsg = "ok";
		}
		result.put("jsonMsg", jsonMsg);
		result.put("stockId", stockId);

		result.output(getResponse());
	}

	/**
	 * 为intoStockSave初始化参数.
	 * 
	 * @return 参数map.
	 */
	private Map<String, Object> initIntoStockSaveParam() {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("type", "INTO");
		maps.put("operatorName", super.getSessionUserName());
		maps.put("beginSerialNo", beginSerialNo.trim());
		maps.put("endSerialNo", endSerialNo.trim());
		
		if(!StringUtil.isEmptyString(notInAmountList)){
			List list = StringUtil.split(notInAmountList, ",");
			maps.put("notInAmountList", list);
		}
		return maps;
	}

	/**
	 * 删除之前查询出来的储值卡.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "/stored/intoStockCardDelete")
	public String intoStockCardDelete() {
		cardStatisticsList = storedCardService.removeCardStatisticsByIndex(cardStatisticsList,
				index);
		return "intoStockGenerate";
	}

	/**
	 * 入库单导出.
	 */
	@Action(value = "/stored/intoStockCardExport")
	public void intoStockCardExport() {
		List<StoredCard> list = storedCardService.storedCardExport(this
				.initIntoStockCardExportParam());
		output(list, "/WEB-INF/resources/template/stockCardTemplate.xls");
	}

	private Map<String, Object> initIntoStockCardExportParam() {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("intoStockId", stockId.trim());
		maps.put("operatorName", super.getSessionUserName());
		return maps;
	}

	private void output(List<StoredCard> list, String template) {
		FileInputStream fin = null;
		OutputStream os = null;
		try {
			File templateResource = ResourceUtil.getResourceFile(template);
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("cardList", list);
			XLSTransformer transformer = new XLSTransformer();
			String destFileName = Constant.getTempDir() + "/excel" + new Date().getTime() + ".xls";
			transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName);
			getResponse().setHeader("Content-Disposition",
					"attachment; filename=" + this.stockId + ".xls");
			getResponse().setContentType("application/vnd.ms-excel");
			os = getResponse().getOutputStream();
			fin = new FileInputStream(destFileName);
			IOUtils.copy(fin, os);
			os.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
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

	public StoredCardService getStoredCardService() {
		return storedCardService;
	}

	public void setStoredCardService(StoredCardService storedCardService) {
		this.storedCardService = storedCardService;
	}

	public Page<Map> getStoredCardStockPage() {
		return storedCardStockPage;
	}

	public void setStoredCardStockPage(Page<Map> storedCardStockPage) {
		this.storedCardStockPage = storedCardStockPage;
	}

	public Long getPerPageRecord() {
		return perPageRecord;
	}

	public void setPerPageRecord(Long perPageRecord) {
		this.perPageRecord = perPageRecord;
	}

	public String getAmountStr() {
		return amountStr;
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}

	public String getNotInAmountList() {
		return notInAmountList;
	}

	public void setNotInAmountList(String notInAmountList) {
		this.notInAmountList = notInAmountList;
	}
}
