package com.lvmama.back.web.ord.refundMent;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.A;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPaymentGateway;
import com.lvmama.comm.pet.po.pay.PayPaymentRefundment;
import com.lvmama.comm.pet.po.pay.PayPrePayment;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pay.PayPaymentGatewayService;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.vo.PayRefundDetail;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;


/**
 * 退款明细查询类.
 * 
 * @author fengyu
 */
@SuppressWarnings("unused")
public class PayRefundDetailQueryAction extends BaseAction {
	
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(PayRefundDetailQueryAction.class);
	private static final long serialVersionUID = 1L;

	/**
	 * 退款服务接口
	 */
	private PayPaymentRefundmentService payPaymentRefundmentService;
	/**
	 * 
	 */
	private PayPaymentService payPaymentService;
	/**
	 * 
	 */
	private ComLogService comLogService;
	/**
	 * 
	 */
	private TopicMessageProducer resourceMessageProducer;

	/**
	 * 退款明细查询结果
	 */
	private List<PayRefundDetail> payRefundDetailList;

	/**
	 * 退款服务接口
	 */
	private PayPaymentGatewayService payPaymentGatewayService;
	
	/**
	 * 退款服务对像.
	 */
	private OrdRefundMentService ordRefundMentService;
	/**
	 * 本页退款合计
	 */
	String countPageAmountYuan = "";   

	private Map<String, Object> searchRefundMentMap = new HashMap<String, Object>();

	private List<PayPaymentGateway> payPaymentGatewayList = new ArrayList<PayPaymentGateway>();
	
	
	private Long paymentRefundmentId;
	private String refundBank;
	private String paymentGateway;
	private PayPaymentGateway payPaymentGateway;

	/**
	 * @return
	 */
	public void doBefore() {
		Map<String, String> paramMap = new HashMap<String, String>();
		payPaymentGatewayList = payPaymentGatewayService.selectPayPaymentGatewayByParamMap(paramMap);
		PayPaymentGateway emptyPayPaymentGateway = new PayPaymentGateway();
		emptyPayPaymentGateway.setGateway("");
		emptyPayPaymentGateway.setGatewayName("全部");
		payPaymentGatewayList.add(0, emptyPayPaymentGateway);
		
		if(paymentRefundmentId!=null){
			PayPaymentRefundment payPaymentRefundment =payPaymentRefundmentService.selectPaymentRefundmentByPK(paymentRefundmentId);
			this.refundBank=payPaymentRefundment.getRefundGateway();
		}
		if(StringUtils.isNotBlank(paymentGateway)){
			payPaymentGateway =payPaymentGatewayService.selectPaymentGatewayByGateway(paymentGateway);	
		}
	}

	private void doQuery() {
		searchRefundMentMap = formatMap(searchRefundMentMap);
		Map<String, Object> map = initialPageInfoByMap(payPaymentRefundmentService.selectPayRefundDetailCount(searchRefundMentMap), searchRefundMentMap);
		int skipResults = 0;
		int maxResults = 10;
		if(map.get("skipResults")!=null){
			skipResults=Integer.parseInt(map.get("skipResults").toString());
		}
		if(map.get("maxResults")!=null){
			maxResults=Integer.parseInt(map.get("maxResults").toString());
		}
		searchRefundMentMap.put("skipResults",skipResults);
		searchRefundMentMap.put("maxResults",maxResults);
		payRefundDetailList = payPaymentRefundmentService.selectPayRefundDetailList(searchRefundMentMap, skipResults, maxResults);

		((Label)this.getComponent().getFellow("_totalRowCountLabel")).setValue(String.valueOf(payRefundDetailList.size()));

		Long countPageAmount = 0L;
		for (PayRefundDetail payRefundDetail : payRefundDetailList) {
			if(null != payRefundDetail.getAmount()) {
				countPageAmount += payRefundDetail.getAmount();
			}
		}
		countPageAmountYuan = PriceUtil.formatDecimal(PriceUtil.convertToYuan(countPageAmount));
	}

	public void selectRefundStatus(String value) {
		searchRefundMentMap.put("refundStatus", value);
	}

	public void selectPaymentGateway(String value) {
		searchRefundMentMap.put("paymentGateway", value);
	}

	public void selectRefundGateway(String value) {
		searchRefundMentMap.put("refundGateway", value);
	}
	public void selectIsAllowRefund(String value) {
		searchRefundMentMap.put("isAllowRefund", value);
	}
	/**
	 * 手工成功处理.<br>
	 * 界面交互
	 */
	public void manualRefundmentSucc(final Map<String,Object> attributeMap) {
		final Long payPaymentRefundmentId = (Long) attributeMap.get("payPaymentRefundmentId");
		final String str = "确定要手工成功处理？";
		final String operatorName = this.getSessionUserName();
		try {
			Messagebox.show(str, "确认信息", Messagebox.YES | Messagebox.NO,
					Messagebox.ERROR, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								PayPaymentRefundment payRefundment = payPaymentRefundmentService.selectPaymentRefundmentByPK(payPaymentRefundmentId);
									PayPrePayment payPrePayment = payPaymentService.selectPrePaymentByPaymentId(payRefundment.getPaymentId());
									if (payPrePayment != null) {
										if (Constant.PAYMENT_PRE_STATUS.PRE_PAY.name().equalsIgnoreCase(payPrePayment.getStatus())) {
											payPrePayment.setStatus(Constant.PAYMENT_PRE_STATUS.PRE_CANCEL.name());
										} else if (Constant.PAYMENT_PRE_STATUS.PRE_SUCC.name().equalsIgnoreCase(payPrePayment.getStatus())) {
											payPrePayment.setStatus(Constant.PAYMENT_PRE_STATUS.PRE_REFUND.name());
										}
										payPrePayment.setCancelTime(new Date());
									}
									payRefundment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
									payRefundment.setCallbackInfo("手工成功处理");
									payRefundment.setCallbackTime(new Date());
									payRefundment.setOperator(getOperatorName());
									boolean isSucc = payPaymentRefundmentService.updatePyamentRefundmentAndPayPayPayment(payRefundment,payPrePayment);
									if (isSucc) {
										ComLog log = new ComLog();
										log.setObjectId(payRefundment.getPayRefundmentId());
										log.setObjectType("PAY_PAYMENT_REFUNDMENT");
										log.setParentId(payRefundment.getObjectId());
										log.setParentType("ORD_REFUNDMENT");
										log.setLogType("PAY_PAYMENT_REFUNDMENT_OFFLINE");
										log.setLogName("");
										log.setOperatorName(operatorName);
										log.setContent("操作员进行了手工成功处理");
										log.setCreateTime(new Date());
										comLogService.addComLog(log);										
										Message message = MessageFactory.newRefundSuccessCallMessage(payRefundment.getPayRefundmentId());
										resourceMessageProducer.sendMsg(message);
									}
									// 刷新页面
									refreshComponent("search");
									break;
								} 
							}
					});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 手工失败处理.<br>
	 * 界面交互
	 */
	public void manualRefundmentFail(final Map<String,Object> attributeMap) {
		final Long payPaymentRefundmentId = (Long) attributeMap.get("payPaymentRefundmentId");
		final String str = "确定要手工失败处理？";
		final String operatorName = this.getSessionUserName();
		try {
			Messagebox.show(str, "确认信息", Messagebox.YES | Messagebox.NO,
					Messagebox.ERROR, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								PayPaymentRefundment payRefundment = payPaymentRefundmentService.selectPaymentRefundmentByPK(payPaymentRefundmentId);
								payRefundment.setStatus(Constant.PAYMENT_SERIAL_STATUS.FAIL.name());
								payRefundment.setCallbackInfo("手工失败处理");
								payRefundment.setCallbackTime(new Date());
								payRefundment.setOperator(getOperatorName());
								boolean isSucc = payPaymentRefundmentService.updatePyamentRefundment(payRefundment);
								if(isSucc){
									ComLog log = new ComLog();
									log.setObjectId(payRefundment.getPayRefundmentId());
									log.setObjectType("PAY_PAYMENT_REFUNDMENT");
									log.setParentId(payRefundment.getObjectId());
									log.setParentType("ORD_REFUNDMENT");
									log.setLogType("PAY_PAYMENT_REFUNDMENT_OFFLINE");
									log.setLogName("");
									log.setOperatorName(operatorName);
									log.setContent("操作员进行了手工失败处理");
									log.setCreateTime(new Date());
									comLogService.addComLog(log);		
								}
								// 刷新页面
								refreshComponent("search");
								break;
							}
						}
					});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 手工重新退款.<br>
	 * 界面交互
	 */
	public void manualRefundmentRefund(final Map<String,Object> attributeMap) {
		final Long payPaymentRefundmentId = (Long) attributeMap.get("payPaymentRefundmentId");
		final String str = "确定要手工重新退款？";
		final String operatorName = this.getSessionUserName();
		try {
			Messagebox.show(str, "确认信息", Messagebox.YES | Messagebox.NO,
					Messagebox.ERROR, new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								
								PayPaymentRefundment payRefundment = payPaymentRefundmentService.selectPaymentRefundmentByPK(payPaymentRefundmentId);
								payRefundment.setStatus(Constant.PAYMENT_SERIAL_STATUS.CREATE.name());
								payRefundment.setOperator(getOperatorName());
								boolean isSucc = payPaymentRefundmentService.updatePyamentRefundment(payRefundment);
								if(isSucc){
									ComLog log = new ComLog();
									log.setObjectId(payRefundment.getPayRefundmentId());
									log.setObjectType("PAY_PAYMENT_REFUNDMENT");
									log.setParentId(payRefundment.getObjectId());
									log.setParentType("ORD_REFUNDMENT");
									log.setLogType("PAY_PAYMENT_REFUNDMENT_OFFLINE");
									log.setLogName("");
									log.setOperatorName(operatorName);
									log.setContent("操作员进行了手工重新退款处理,原退款流水号:"+payRefundment.getSerial());
									log.setCreateTime(new Date());
									comLogService.addComLog(log);	
									Message msg = MessageFactory.newPaymentRefundmentMessage(payRefundment.getObjectId());
									msg.setAddition(payRefundment.getBizType());
									resourceMessageProducer.sendMsg(msg);
								}
								alert("退款处理中");
								// 刷新页面
								refreshComponent("search");
								break;
							}
						}
					});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 拆分退款.
	 * @param a
	 */
	public void splitRefundment(final Map<String,Object> attributeMap){
		ZkMessage.showQuestion("您确定拆分退款到产品吗?", 
				new ZkMsgCallBack()	{
					public void execute() {
						String  refundmentId= (String)attributeMap.get("refundmentId");
						OrdRefundment orf = ordRefundMentService.findOrdRefundmentById(Long.valueOf(refundmentId));
						if(null==orf){
							alert("没有找到退款信息！");
						}else if (orf.isRefunded()) {
							ordRefundMentService.refundAmountSplit(orf);
							alert("拆分退款成功");
						}else{
							alert("拆分退款失败，请检查退款金额是否不是0，是否已经做过了退款等！");
						}
					}
				}, new ZkMsgCallBack() {
					public void execute() {}
				}
		);
	}
	/**
	 * 退款明细查询
	 */
	public void queryPayRefundDetail() {
		doQuery();
	}
	
	
	/**
	 * 导出excel
	 * @author ZHANG Nan
	 */
	public void doExport()
	{
		Long count=payPaymentRefundmentService.selectPayRefundDetailCount(searchRefundMentMap);
		if(count!=null && count>65535){
			alert("导出的记录数超过了65535行,请缩小导出范围!");
			return ;
		}
		payRefundDetailList = payPaymentRefundmentService.selectPayRefundDetailList(searchRefundMentMap, 0,Integer.parseInt(count.toString()));
		doExcel(payRefundDetailList, "/WEB-INF/resources/template/refundDetailListTemplate.xls");
	}
	private void doExcel(List<PayRefundDetail> excelList,String path){
		try {
			File templateResource = ResourceUtil.getResourceFile(path);
			String templateFileName = templateResource.getAbsolutePath();
			String date=DateUtil.getFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
			String destFileName = Constant.getTempDir() + "/refundDetail "+date+".xls";

			Map<String,List<PayRefundDetail>> beans = new HashMap<String,List<PayRefundDetail>>();
			beans.put("excelList", excelList);
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, beans, destFileName);

			File file = new File(destFileName);
			if (file != null && file.exists()) {
				Filedownload.save(file, "application/vnd.ms-excel");
			} else {
				alert("导出Excel失败");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 修改退款网关.
	 * @param a
	 */
	public void modifyRefundGateway(){
		String oldRefundGateway="";
		PayPaymentRefundment payPaymentRefundment =payPaymentRefundmentService.selectPaymentRefundmentByPK(paymentRefundmentId);
		oldRefundGateway=payPaymentRefundment.getRefundGateway();
		payPaymentRefundment.setRefundGateway(refundBank);
		payPaymentRefundmentService.updatePayPaymentRefundmentRefundGateway(payPaymentRefundment);
		alert("更改完毕!");
		ComLog log = new ComLog();
		log.setObjectId(payPaymentRefundment.getPayRefundmentId());
		log.setObjectType("PAY_PAYMENT_REFUNDMENT");
		log.setParentId(payPaymentRefundment.getObjectId());
		log.setParentType("ORD_REFUNDMENT");
		log.setLogType("PAY_PAYMENT_REFUNDMENT_OFFLINE");
		log.setLogName("");
		log.setOperatorName(this.getSessionUserName());
		log.setContent("操作员强制修改退款网关  由:"+oldRefundGateway+"  改为:"+refundBank);
		log.setCreateTime(new Date());
		comLogService.addComLog(log);		
		refreshComponent("wind_pay_refund_detail_query_modify_refund_gateway_colse");
	}
	
	
	public Map<String, Object> getSearchRefundMentMap() {
		return searchRefundMentMap;
	}
	public void setSearchRefundMentMap(Map<String, Object> searchRefundMentMap) {
		this.searchRefundMentMap = searchRefundMentMap;
	}
	public void setCountPageAmountYuan(String countPageAmountYuan) {
		this.countPageAmountYuan = countPageAmountYuan;
	}

	public PayPaymentRefundmentService getPayPaymentRefundmentService() {
		return payPaymentRefundmentService;
	}

	public void setPayPaymentRefundmentService(
			PayPaymentRefundmentService payPaymentRefundmentService) {
		this.payPaymentRefundmentService = payPaymentRefundmentService;
	}

	public List<PayRefundDetail> getPayRefundDetailList() {
		return payRefundDetailList;
	}

	public void setPayRefundDetailList(List<PayRefundDetail> payRefundDetailList) {
		this.payRefundDetailList = payRefundDetailList;
	}

	public String getCountPageAmountYuan() {
		return countPageAmountYuan;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public PayPaymentGatewayService getPayPaymentGatewayService() {
		return payPaymentGatewayService;
	}

	public void setPayPaymentGatewayService(
			PayPaymentGatewayService payPaymentGatewayService) {
		this.payPaymentGatewayService = payPaymentGatewayService;
	}

	public List<PayPaymentGateway> getPayPaymentGatewayList() {
		return payPaymentGatewayList;
	}

	public void setPayPaymentGatewayList(
			List<PayPaymentGateway> payPaymentGatewayList) {
		this.payPaymentGatewayList = payPaymentGatewayList;
	}

	public void setOrdRefundMentService(OrdRefundMentService ordRefundMentService) {
		this.ordRefundMentService = ordRefundMentService;
	}

	public Long getPaymentRefundmentId() {
		return paymentRefundmentId;
	}

	public void setPaymentRefundmentId(Long paymentRefundmentId) {
		this.paymentRefundmentId = paymentRefundmentId;
	}

	public String getRefundBank() {
		return refundBank;
	}

	public void setRefundBank(String refundBank) {
		this.refundBank = refundBank;
	}

	public String getPaymentGateway() {
		return paymentGateway;
	}

	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public PayPaymentGateway getPayPaymentGateway() {
		return payPaymentGateway;
	}

	public void setPayPaymentGateway(PayPaymentGateway payPaymentGateway) {
		this.payPaymentGateway = payPaymentGateway;
	}

	
}
