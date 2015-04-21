package com.lvmama.pet.sweb.recon;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.client.FileFinInterfaceClient;
import com.lvmama.comm.pet.po.fin.FinBizItem;
import com.lvmama.comm.pet.po.fin.FinGLInterface;
import com.lvmama.comm.pet.po.pay.FinReconResult;
import com.lvmama.comm.pet.service.fin.FinBizItemService;
import com.lvmama.comm.pet.service.fin.FinGLBizService;
import com.lvmama.comm.pet.service.fin.FinGLService;
import com.lvmama.comm.pet.service.pay.FinReconResultService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.FIN_GL_ACCOUNT_TYPE;

/**
 * 对账接口管理后台
 * 
 * @author zhangwengang
 * 
 */
@SuppressWarnings("serial")
@Results({
		@Result(name = "finInterfacePage", location = "/WEB-INF/pages/back/recon/queryFinInterface.jsp"),
		@Result(name = "editPage", location = "/WEB-INF/pages/back/recon/editFinInterface.jsp"),
		@Result(name = "findFinHistory", location = "/WEB-INF/pages/back/recon/findFinHistory.jsp"),
		@Result(name = "queryOrderAccount", location = "/WEB-INF/pages/back/recon/orderAccountCount.jsp"),
		@Result(name = "selectFinanceOrderMonitor",location="/WEB-INF/pages/back/recon/financeOrderMonitor.jsp")})
public class FinInterfaceAction extends BackBaseAction {

	/**
	 * 状态
	 */
	private String finStatus;
	/**
	 * 开始日期.
	 */
	private Date beginTime;
	/**
	 * 结束日期.
	 */
	private Date endTime;
	/**
	 * 入账类型
	 */
	private String accountType;
	/**
	 * 票号
	 */
	private String tickedNo;
	/**
	 * 主键id
	 */
	private String glInterfaceId;
	/**
	 * 对账接口信息
	 */
	private FinGLInterface finGLInterface;
	/**	 * 对账接口代理
	 */
	private FinGLBizService finGLServiceProxy;
	/**
	 * 对账接服务
	 */
	private FinGLService finGLService;
	/**
	 * 日志接口
	 */
	protected ComLogService comLogRemoteService;

	// 日志类型
	private static String LOG_TYPE = "FIN_RECON_RESUTL";
	
	private List<Map<String,Object>> orderDatas=new ArrayList<Map<String,Object>>();
	private List<Map<String,Object>> counts=new ArrayList<Map<String,Object>>();
	private List<Map<String,String>> countList= new ArrayList<Map<String,String>>();
	
	private String currentPage = "1";

	// 入账失败类型
	private String STATUS_FAIL = "fail";

	// 入账类型字典列表
	private FIN_GL_ACCOUNT_TYPE[] accountTypes;
	
	private String[] dateIntervals;
	
	private Page datas;

	private String fileName;
	
	private FileFinInterfaceClient fileFinInterfaceClient;
	
	/**
	 * 对账结果接口
	 */
	private FinReconResultService finReconResultService;
	//勾兑结果
	private List<FinReconResult> finReconResultList;
	
	/**
	 * 流水接口
	 */
	private FinBizItemService finBizItemService;
	//流水集合
	private List<FinBizItem> finBizItemList;
	
	/**
	 * 跳转到查询页面
	 * 
	 * @return
	 */
	@Action(value = "/recon/gotoQueryFinInterface")
	public String gotoPage() {
		// 初始化入账类型列表
		initDateIntervals();
		accountTypes = Constant.FIN_GL_ACCOUNT_TYPE.values();
		return "finInterfacePage";
	}

	/**
	 * 查询功能
	 * 
	 * @return
	 */
	@Action(value = "/recon/queryFinInterface")
	public String doQuery() {
		// 初始化入账类型列表
		initDateIntervals();
		accountTypes = Constant.FIN_GL_ACCOUNT_TYPE.values();
		// 分页初始化
		pagination = initPage();
		pagination.setPageSize(20);
		Map<String, Object> paraMap = getParams();
		pagination.setTotalResultSize(finGLService.selectRowCount(paraMap));
		if (pagination.getTotalResultSize() > 0) {
			paraMap.put("_startRow", pagination.getStartRows());
			paraMap.put("_endRow", pagination.getEndRows());
			List<FinGLInterface> finList = finGLService.query(paraMap);
			pagination.setItems(finList);
		}
		pagination.buildUrl(getRequest());
		return "finInterfacePage";
	}
	@Action(value = "/recon/queryFinInterfaceCount")
	public String doQueryOrderCount(){
		// 初始化入账类型列表
		initDateIntervals();
		accountTypes = Constant.FIN_GL_ACCOUNT_TYPE.values();
		Map<String, Object> paraMap = getParams();
		orderDatas = finGLService.queryDayCountMisOrder(paraMap);
		counts = finGLService.queryDayCount(paraMap);
		Map<String,Map<String,String>> mapCountList= new HashMap<String,Map<String,String>>();
		for(Map<String,Object> count:counts){
			String makeBillTime = String.valueOf(count.get("MAKE_BILL_TIME"));
			if(StringUtil.isEmptyString(makeBillTime) || "null".equalsIgnoreCase(makeBillTime)){
				makeBillTime = "合计";
			}
			Map<String,String> subCounts = mapCountList.get(makeBillTime);
			if(null==subCounts){
				subCounts = new HashMap<String,String>();
			}
			if(Constant.FIN_GL_ACCOUNT_TYPE.INSTEAD_INCOME.getCode().equals(String.valueOf(count.get("ACCOUNT_TYPE")))
			||Constant.FIN_GL_ACCOUNT_TYPE.INSTEAD_INCOME_POS.getCode().equals(String.valueOf(count.get("ACCOUNT_TYPE")))
			||Constant.FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME.getCode().equals(String.valueOf(count.get("ACCOUNT_TYPE")))
			||Constant.FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME_PARTPAY.getCode().equals(String.valueOf(count.get("ACCOUNT_TYPE")))
			||Constant.FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME_PARTPAY_HEDGE.getCode().equals(String.valueOf(count.get("ACCOUNT_TYPE")))
			||Constant.FIN_GL_ACCOUNT_TYPE.CANCEL_INCOME_HEDGE.getCode().equals(String.valueOf(count.get("ACCOUNT_TYPE")))){
				subCounts.put(String.valueOf(count.get("ACCOUNT_TYPE")), String.valueOf(count.get("BORROWER_AMOUNT")));
			}
			mapCountList.put(String.valueOf(makeBillTime), subCounts);
		}
		Iterator<String> iterator = mapCountList.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			Map<String,String> map = mapCountList.get(key);
			Long INSTEAD_INCOME =getMapValueIfNullTransferZero(Constant.FIN_GL_ACCOUNT_TYPE.INSTEAD_INCOME.getCode(),map);
			Long INSTEAD_INCOME_POS = getMapValueIfNullTransferZero(Constant.FIN_GL_ACCOUNT_TYPE.INSTEAD_INCOME_POS.getCode(),map);
			Long BOOKING_INCOME = getMapValueIfNullTransferZero(Constant.FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME.getCode(),map);
			Long BOOKING_INCOME_PARTPAY = getMapValueIfNullTransferZero(Constant.FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME_PARTPAY.getCode(),map);
			Long BOOKING_INCOME_PARTPAY_HEDGE =getMapValueIfNullTransferZero(Constant.FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME_PARTPAY_HEDGE.getCode(),map);
			Long CANCEL_INCOME_HEDGE = getMapValueIfNullTransferZero(Constant.FIN_GL_ACCOUNT_TYPE.CANCEL_INCOME_HEDGE.getCode(),map);
			map.put(Constant.FIN_GL_ACCOUNT_TYPE.INSTEAD_INCOME_POS.getCode(), String.valueOf((INSTEAD_INCOME_POS/2)));
			map.put("MINUS", String.valueOf(INSTEAD_INCOME+(INSTEAD_INCOME_POS/2)-(BOOKING_INCOME+BOOKING_INCOME_PARTPAY+BOOKING_INCOME_PARTPAY_HEDGE+CANCEL_INCOME_HEDGE)));
			map.put("MAKE_BILL_TIME", key);
			countList.add(map);
		}
		Collections.sort(countList, new ComparatorUser());
		return "finInterfacePage";
	}

	@Action("/recon/deleteFinInterfaceById")
	public String deleteFinInterfaceById(){
		if(glInterfaceId!=null){
			Map<String,Object> deleteParameters = new HashMap<String,Object>();
			deleteParameters.put("glInterfaceId", glInterfaceId);
			finGLService.delete(deleteParameters);
			comLogRemoteService.insert(LOG_TYPE, null,
					Long.parseLong(glInterfaceId),
					getSessionUserName(), null, "删除入账数据", "删除入账数据", null);
		}
		return doQuery();
	}
	
	/**
	 * 根据glInterfaceId查询总账数据对应的勾兑数据、流水记录
	 * @return
	 */
	@Action("/recon/findFinReconAndBizItemById")
	public String findFinReconAndBizItemById(){
		if(glInterfaceId!=null){
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("glInterfaceId", glInterfaceId);
			List<FinGLInterface> finList = finGLService.query(paraMap);
			if (null != finList && finList.size() > 0) {
				Map<String,String> paramMap=new HashMap<String,String>();
				
				finGLInterface = finList.get(0);
				Long reconResultId = finGLInterface.getReconResultId();
				String tickedNo = finGLInterface.getTickedNo();
				
				if(tickedNo.getBytes().length == tickedNo.length()){
					//如果订单号中不包含汉字,根据订单号反查询
					paramMap.put("orderId", tickedNo);
				}
				if(reconResultId!=null){
					paramMap.put("reconResultId", String.valueOf(reconResultId));
					finReconResultList = finReconResultService.selectReconResultListByParas(paramMap); //获取勾兑记录
					finBizItemList = finBizItemService.selectFinBizItemListByParas(paramMap);  //获取流水记录
				}

			}
		}
		return "findFinHistory";
	}

	@Action(value = "/recon/queryOrderAccount")
	public String queryOrderAccount(){
		initDateIntervals();
		accountTypes = Constant.FIN_GL_ACCOUNT_TYPE.values();
		Map<String, Object> paraMap = getParams();	
		paraMap.put("currentPage",getRequest().getParameter("page")!=null?getRequest().getParameter("page"):currentPage);
		paraMap.put("pageSize", 20);
		datas = finGLService.queryOrderAccount(paraMap);
		datas.buildUrl(getRequest());
		return "queryOrderAccount";
	}
	@Action(value="/recon/selectFinanceOrderMonitor")
	public String selectFinanceOrderMonitorPage(){
		initDateIntervals();
		accountTypes = Constant.FIN_GL_ACCOUNT_TYPE.values();
		Map<String, Object> paraMap = getParams();
		if(null!=paraMap.get("tickedNo")){
			paraMap.put("orderId", Long.parseLong((String)paraMap.get("tickedNo")));
		}
		if(paraMap.isEmpty())
			return "selectFinanceOrderMonitor";
		paraMap.put("currentPage",getRequest().getParameter("page")!=null?getRequest().getParameter("page"):currentPage);
		paraMap.put("pageSize", 20);
		datas = finGLService.selectFinanceOrderMonitorPage(paraMap);
		datas.buildUrl(getRequest());
		return "selectFinanceOrderMonitor";
	}
	
	private class ComparatorUser implements Comparator {
		public int compare(Object arg0, Object arg1) {
			Map<String, String> user0 = (Map<String, String>) arg0;
			Map<String, String> user1 = (Map<String, String>) arg1;
			int flag = user1.get("MAKE_BILL_TIME").compareTo(
					user0.get("MAKE_BILL_TIME"));
			return flag;
		}

	}
	private Long getMapValueIfNullTransferZero(final String key,final Map<String,String> map){
		String value=map.get(key);
		if(null==value){
			map.put(key, "0");
			return 0L;
		}
		return Long.valueOf(value);
	}
	public Map<String,Object> getParams(){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if (beginTime != null) {
			paraMap.put("beginTime", beginTime);
		}
		if (endTime != null) {
			paraMap.put("endTime", endTime);
		}
		if (StringUtils.isNotEmpty(finStatus)) {
			if ("unsend".equals(finStatus)) {
				paraMap.put("isNullFlag", true);
			} else {
				paraMap.put("finStatus", finStatus);
			}
		}
		if (StringUtils.isNotEmpty(accountType)) {
			paraMap.put("accountType", accountType);
		}
		if (StringUtils.isNotEmpty(tickedNo)) {
			paraMap.put("tickedNo", tickedNo);
		}
		return paraMap;
	}
	/**
	 * 跳转到修改页面
	 * 
	 * @return
	 */
	@Action(value = "/recon/gotoEditFinInterface")
	public String gotoEdit() {
		// 根据id获取信息
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("glInterfaceId", glInterfaceId);
		List<FinGLInterface> finList = finGLService.query(paraMap);
		if (null != finList && finList.size() > 0) {
			finGLInterface = finList.get(0);
		}
		return "editPage";
	}

	/**
	 * 修改功能
	 * 
	 * @return
	 */
	@Action(value = "/recon/editFinInterface")
	public void edit() {
		JSONResult result = new JSONResult();
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("glInterfaceId", finGLInterface.getGlInterfaceId());
			List<FinGLInterface> finList = finGLService.query(paraMap);
			if (null != finList && finList.size() > 0) {
				finGLService.update(finGLInterface);
				StringBuilder sb = new StringBuilder("修改记录为：");
				sb.append("借方科目编码" + finGLInterface.getBorrowerSubjectCode()
						+ ",");
				sb.append("贷方科目编码" + finGLInterface.getLenderSubjectCode()
						+ ",");
				sb.append("凭证类型" + finGLInterface.getProofType() + ",");
				sb.append("帐套号" + finGLInterface.getAccountBookId());
				comLogRemoteService.insert(LOG_TYPE, null,
						finGLInterface.getGlInterfaceId(),
						getSessionUserName(), null, null, sb.toString(), null);
			}
			result.put("hasNew", true);
			result.output(getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			comLogRemoteService.insert(LOG_TYPE, null,
					finGLInterface.getGlInterfaceId(), getSessionUserName(),
					null, null, "修改失败", null);
			result.put("msg", "修改失败");
			result.output(getResponse());
		}

	}

	/**
	 * 手工入账功能
	 * 
	 * @return
	 */
	@Action(value = "/recon/send")
	public void doSend() {
		// 更新所有返回失败的记录:状态='未发送'
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receivablesStatus", STATUS_FAIL);
		finGLService.updateStatusNull(map);

		// 发送对账结果
		if(StringUtil.isEmptyString(fileName)){
			finGLServiceProxy.send();
		}else{
			fileFinInterfaceClient.send();
		}
		this.sendAjaxMsg("success");
	}
	
	/**
	 * 
	 * @return
	 */
	@Action(value="/recon/receive")
	public void receive(){
		String userName = super.getRequestParameter("userName");
		String password = super.getRequestParameter("password");
		if(StringUtil.isEmptyString(userName) || StringUtil.isEmptyString(password)){
			this.sendAjaxMsg("userName is null or password is null");
			return;
		}
		if(!"u8".equals(userName) || !"u8password".equals(password)){
			this.sendAjaxMsg("userName is error or password is error");
			return;
		}
		if(StringUtil.isEmptyString(fileName)){
			this.sendAjaxMsg("fileName is null");
			return ;
		}
		if(!fileName.matches("^[^/\\s]+/[^/\\s]+\\.txt$")){
			this.sendAjaxMsg("file format is error, must has / and is only one");
			return;
		}
		fileFinInterfaceClient.receive(fileName);
		this.sendAjaxMsg("success");
	}
	
	
	/**
	 * 手工对账
	 * 
	 * @return
	 */
	@Action(value = "/recon/genGLInterfaceData")
	public void doGenGLInterface() {
		finGLService.generateGLData();
		this.sendAjaxMsg("success.");
	}
	
	private void initDateIntervals(){
		int t = DateUtil.getMonth(new java.util.Date());
		String endDate = DateUtil.formatDate(new java.util.Date(), "yyyyMM");
		dateIntervals = new String[t];
		int dateInteger=Integer.parseInt(endDate);
		for(int i=0;i<t;i++){
			dateIntervals[i]=(Integer.toString(dateInteger-i)).replaceAll("^(\\d{4})(\\d{2})$", "$1-$2");
		}
	}
	public String getFinStatus() {
		return finStatus;
	}

	public void setFinStatus(String finStatus) {
		this.finStatus = finStatus;
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

	public void setGlInterfaceId(String glInterfaceId) {
		this.glInterfaceId = glInterfaceId;
	}

	public String getGlInterfaceId() {
		return glInterfaceId;
	}

	public FinGLService getFinGLService() {
		return finGLService;
	}

	public void setFinGLService(FinGLService finGLService) {
		this.finGLService = finGLService;
	}

	public FinGLInterface getFinGLInterface() {
		return finGLInterface;
	}

	public void setFinGLInterface(FinGLInterface finGLInterface) {
		this.finGLInterface = finGLInterface;
	}

	public FIN_GL_ACCOUNT_TYPE[] getAccountTypes() {
		return accountTypes;
	}

	public void setAccountTypes(FIN_GL_ACCOUNT_TYPE[] accountTypes) {
		this.accountTypes = accountTypes;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getTickedNo() {
		return tickedNo;
	}

	public void setTickedNo(String tickedNo) {
		this.tickedNo = tickedNo;
	}
	public FinGLBizService getFinGLServiceProxy() {
		return finGLServiceProxy;
	}

	public void setFinGLServiceProxy(FinGLBizService finGLServiceProxy) {
		this.finGLServiceProxy = finGLServiceProxy;
	}
	public ComLogService getComLogRemoteService() {
		return comLogRemoteService;
	}

	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}

	public List<Map<String, Object>> getOrderDatas() {
		return orderDatas;
	}

	public void setOrderDatas(List<Map<String, Object>> orderDatas) {
		this.orderDatas = orderDatas;
	}

	public List<Map<String, Object>> getCounts() {
		return counts;
	}

	public void setCounts(List<Map<String, Object>> counts) {
		this.counts = counts;
	}

	public List<Map<String, String>> getCountList() {
		return countList;
	}

	public String[] getDateIntervals() {
		return dateIntervals;
	}

	public void setDateIntervals(String[] dateIntervals) {
		this.dateIntervals = dateIntervals;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public Page getDatas() {
		return datas;
	}

	public void setDatas(Page datas) {
		this.datas = datas;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileFinInterfaceClient(
			FileFinInterfaceClient fileFinInterfaceClient) {
		this.fileFinInterfaceClient = fileFinInterfaceClient;
	}

	public FinReconResultService getFinReconResultService() {
		return finReconResultService;
	}

	public void setFinReconResultService(FinReconResultService finReconResultService) {
		this.finReconResultService = finReconResultService;
	}

	public FinBizItemService getFinBizItemService() {
		return finBizItemService;
	}

	public void setFinBizItemService(FinBizItemService finBizItemService) {
		this.finBizItemService = finBizItemService;
	}

	public List<FinReconResult> getFinReconResultList() {
		return finReconResultList;
	}

	public void setFinReconResultList(List<FinReconResult> finReconResultList) {
		this.finReconResultList = finReconResultList;
	}

	public List<FinBizItem> getFinBizItemList() {
		return finBizItemList;
	}

	public void setFinBizItemList(List<FinBizItem> finBizItemList) {
		this.finBizItemList = finBizItemList;
	}
	
}
