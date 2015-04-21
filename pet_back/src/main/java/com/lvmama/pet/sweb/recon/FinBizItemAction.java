package com.lvmama.pet.sweb.recon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.fin.FinBizItem;
import com.lvmama.comm.pet.po.fin.FinBizItem.BIZ_STATUS;
import com.lvmama.comm.pet.service.fin.FinBizItemService;
import com.lvmama.comm.pet.service.fin.FinGLService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.GL_STATUS;
import com.lvmama.comm.vo.Constant.RECON_GW_TYPE;
import com.lvmama.comm.vo.Constant.RECON_STATUS;
import com.lvmama.comm.vo.Constant.TRANSACTION_SOURCE;
import com.lvmama.comm.vo.Constant.TRANSACTION_TYPE;

/**
 * super后台->财务管理->财务勾兑->流水管理Action
 * @author lvhao
 *
 */
public class FinBizItemAction extends BackBaseAction {
	
	/**
	 * 页面参数传递
	 */
	private Map<String,String> paramMap=new HashMap<String,String>();
	
	/**
	 * 页面跳转地址
	 */
	private String target;
	
	/**
	 * 对账网关
	 */
	private RECON_GW_TYPE [] reconGateways;
	
	/**
	 * 对账状态
	 */
	private RECON_STATUS[] reconStatuss;
	
	/**
	 * 交易类型
	 */
	private TRANSACTION_TYPE[] transactionTypes;
	
	/**
	 * 交易来源
	 */
	private TRANSACTION_SOURCE[] transactionSources;
	
	/**
	 * 记账状态
	 */
	private GL_STATUS[] glStatuss;
	
	/**
	 * 流水状态
	 */
	private BIZ_STATUS[] bizStatuss;
	
	/**
	 * 流水集合
	 */
	private List<FinBizItem> finBizItemList;
	
	/**
	 * 流水对象
	 */
	private FinBizItem finBizItem;

	/**
	 * 流水接口
	 */
	private FinBizItemService finBizItemService;
	
	/**
	 * 交易收入总额(单位:元)
	 */
	private String transactionAmountSum;
	
	/**
	 * 交易支付总额(单位:元)
	 */
	private String transactionBankAmountSum;
	
	/**
	 * 日志类型
	 */
	private static String LOG_TYPE="FIN_RECON_RESUTL";
	
	/**
	 * 日志接口
	 */
	protected ComLogService comLogRemoteService;
	
	private FinGLService finGLService;
	
	/**
	 * 初始化页面
	 * @author lvhao
	 * @return
	 */
	public String load(){
		initData();
		if(StringUtils.isNotBlank(target)){
			return target;
		}
		return SUCCESS;
	}
	
	/**
	 * 手动添加一条流水
	 * @author lvhao
	 * @return
	 */
	public void addFinBizItem(){
		doValidate();
		Long amount = PriceUtil.convertToFen(finBizItem.getAmountBig());
		Long bankAmount = PriceUtil.convertToFen(finBizItem.getBankAmountBig());
		finBizItem.setAmount(amount);
		finBizItem.setBankAmount(bankAmount);
		String createUser = finBizItem.getCreateUser();
		if(StringUtil.isEmptyString(createUser)){
			createUser = this.getSessionUserName();
		}
		finBizItem.setCreateUser(createUser);
		finBizItemService.insertFinBizItem(finBizItem);
		JSONObject json=new JSONObject();
		json.put("success", true);
		this.sendAjaxMsg(json.toString());
	}
	
	private void doValidate(){
		Assert.notNull(finBizItem.getAmountBig(),"我方交易金额不可以为空");
		Assert.notNull(finBizItem.getBankAmountBig(),"银行交易金额不可以为空");
		Assert.notNull(finBizItem.getCallbackTime(),"我方交易时间不可以为空");
		Assert.notNull(finBizItem.getTransactionTime(),"银行交易时间不可以为空");
		Assert.hasLength(finBizItem.getTransactionType(),"交易类型不可以为空");
		Assert.hasLength(finBizItem.getGateway(),"对账网关不可以为空");
		Assert.notNull(finBizItem.getBankReconTime(),"银行对账日期不可以为空");
		Assert.notNull(finBizItem.getCreateTime(),"创建时间不可以为空");
		Assert.notNull(finBizItem.getMemo(),"备注不可以为空");
		Assert.notNull(finBizItem.getOrderId(),"订单号不可以为空");
		Assert.notNull(finBizItem.getGlStatus(),"记账状态不可以为空");
		Assert.notNull(finBizItem.getGlTime(),"记账时间不可以为空");
		Assert.notNull(finBizItem.getFeeType(),"费用类型不可以为空");
		Assert.notNull(finBizItem.getBizStatus(),"状态不可以为空");
	}
	
	
	/**
	 * 页面LIST查询
	 * @author lvhao
	 * @return
	 */
	public String query() {
		initData();
		pagination=initPage();
		paramMap.put("start", String.valueOf(pagination.getStartRows()));
		paramMap.put("end", String.valueOf(pagination.getEndRows()));
		
		Long finBizItemListCount=finBizItemService.selectFinBizItemListByParasCount(paramMap);
		if(finBizItemListCount!=null && finBizItemListCount>0){
			finBizItemList= finBizItemService.selectFinBizItemListByParas(paramMap);
			Map<String,String> amountMap=finBizItemService.selectTransactionAmountByParamMap(paramMap);
			if(amountMap!=null){
				transactionAmountSum=amountMap.get("TRANSACTIONAMOUNTSUM");
				transactionBankAmountSum=amountMap.get("TRANSACTIONBANKAMOUNTSUM");
			}
			
		}
		pagination.setTotalResultSize(finBizItemListCount);
		pagination.buildUrl(getRequest());
		return SUCCESS;
	}
	
	/**
	 * 初始化基本数据
	 * @author lvhao
	 */
	public void initData(){
		reconGateways=Constant.RECON_GW_TYPE.values();
		reconStatuss=Constant.RECON_STATUS.values();
		transactionTypes=Constant.TRANSACTION_TYPE.values();
		transactionSources=Constant.TRANSACTION_SOURCE.values();
		glStatuss=Constant.GL_STATUS.values();
		bizStatuss = BIZ_STATUS.values();
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	public RECON_GW_TYPE[] getReconGateways() {
		return reconGateways;
	}

	public void setReconGateways(RECON_GW_TYPE[] reconGateways) {
		this.reconGateways = reconGateways;
	}

	public RECON_STATUS[] getReconStatuss() {
		return reconStatuss;
	}

	public void setReconStatuss(RECON_STATUS[] reconStatuss) {
		this.reconStatuss = reconStatuss;
	}

	public TRANSACTION_TYPE[] getTransactionTypes() {
		return transactionTypes;
	}

	public void setTransactionTypes(TRANSACTION_TYPE[] transactionTypes) {
		this.transactionTypes = transactionTypes;
	}

	public TRANSACTION_SOURCE[] getTransactionSources() {
		return transactionSources;
	}

	public void setTransactionSources(TRANSACTION_SOURCE[] transactionSources) {
		this.transactionSources = transactionSources;
	}

	public GL_STATUS[] getGlStatuss() {
		return glStatuss;
	}

	public void setGlStatuss(GL_STATUS[] glStatuss) {
		this.glStatuss = glStatuss;
	}
	
	public BIZ_STATUS[] getBizStatuss() {
		return bizStatuss;
	}

	public void setBizStatuss(BIZ_STATUS[] bizStatuss) {
		this.bizStatuss = bizStatuss;
	}

	public List<FinBizItem> getFinBizItemList() {
		return finBizItemList;
	}

	public void setFinBizItemList(List<FinBizItem> finBizItemList) {
		this.finBizItemList = finBizItemList;
	}

	public FinBizItem getFinBizItem() {
		return finBizItem;
	}

	public void setFinBizItem(FinBizItem finBizItem) {
		this.finBizItem = finBizItem;
	}

	public FinBizItemService getFinBizItemService() {
		return finBizItemService;
	}

	public void setFinBizItemService(FinBizItemService finBizItemService) {
		this.finBizItemService = finBizItemService;
	}

	public String getTransactionAmountSum() {
		return transactionAmountSum;
	}

	public void setTransactionAmountSum(String transactionAmountSum) {
		this.transactionAmountSum = transactionAmountSum;
	}

	public String getTransactionBankAmountSum() {
		return transactionBankAmountSum;
	}

	public void setTransactionBankAmountSum(String transactionBankAmountSum) {
		this.transactionBankAmountSum = transactionBankAmountSum;
	}

	public static String getLOG_TYPE() {
		return LOG_TYPE;
	}

	public static void setLOG_TYPE(String lOG_TYPE) {
		LOG_TYPE = lOG_TYPE;
	}

	public ComLogService getComLogRemoteService() {
		return comLogRemoteService;
	}

	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}

	public FinGLService getFinGLService() {
		return finGLService;
	}

	public void setFinGLService(FinGLService finGLService) {
		this.finGLService = finGLService;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	
	
}
