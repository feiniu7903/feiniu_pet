package com.lvmama.pet.fin.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.comm.hessian.HessianService;
import com.lvmama.comm.pet.po.fin.FinBizItem;
import com.lvmama.comm.pet.po.fin.FinGLInterface;
import com.lvmama.comm.pet.po.fin.FinGLInterfaceDTO;
import com.lvmama.comm.pet.po.fin.FinGlSubjectCfg;
import com.lvmama.comm.pet.po.pay.FinReconResult;
import com.lvmama.comm.pet.service.fin.FinGLService;
import com.lvmama.comm.pet.service.pay.FinReconResultService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.FIN_GL_ACCOUNT_TYPE;
import com.lvmama.comm.vo.Constant.FIN_SUBJECT_TYPE;
import com.lvmama.pet.BaseService;
import com.lvmama.pet.fin.dao.FinBizItemDAO;
import com.lvmama.pet.fin.dao.FinGLInterfaceDAO;
import com.lvmama.pet.fin.dao.FinGLSubjectConfigDAO;
import com.lvmama.pet.pay.dao.FinReconResultDAO;

@HessianService("finGLService")
@Service("finGLService")
public class FinGLServiceImpl extends BaseService implements FinGLService {
	
	private static final Log loger=LogFactory.getLog(FinGLServiceImpl.class);
	
	@Autowired
	private FinReconResultService finReconResultService;

	@Autowired
	protected FinGLInterfaceDAO finGLInterfaceDAO;
	
	@Autowired
	private FinReconResultDAO finReconResultDAO;
	
	@Autowired
	private FinGLSubjectConfigDAO finGLSubjectConfigDAO;
	
	@Autowired
	private FinBizItemDAO finBizItemDAO;
	
	@Autowired
	FinGLServiceHelper finGLServiceHelper;
	
	private Map<Long,Long> transferOldOrderIdMap;
	
	@Override
	public List<FinGLInterface> queryByBatch(Map<String,Object> map) {
		return finGLInterfaceDAO.searchRecord(map);
	}
	@Override
	public Page<FinGLInterface> queryForPage(Map<String, Object> map) {
		return finGLInterfaceDAO.queryForPage(map);
	}
	@Override
	public void updateStatus(Map<String, Object> map) {
		finGLInterfaceDAO.updateStatus(map);
	}
	
	@Override
	public void updateStatusNull(Map<String, Object> map) {
		finGLInterfaceDAO.updateStatusNull(map);
	}

	@Override
	public List<FinGLInterface> query(Map<String, Object> paraMap) {
		return finGLInterfaceDAO.searchRecordByCondition(paraMap);
	}

	@Override
	public Long selectRowCount(Map<String, Object> paraMap) {
		return finGLInterfaceDAO.selectRowCount(paraMap);
	}

	@Override
	public void update(FinGLInterface obj) {
		finGLInterfaceDAO.update(obj);
	}
	
	/**
	 * 查找有问题的订单号
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> queryDayCountMisOrder(Map<String, Object> map){
		return finGLInterfaceDAO.queryDayCountMisOrder(map);
	}
	
	/**
	 * 统计系统入账信息
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> queryDayCount(Map<String, Object> map){
		return finGLInterfaceDAO.queryDayCount(map);
	}

	
	@Override
	public int delete(Map<String,Object> deleteParameters){
		return finGLInterfaceDAO.delete(deleteParameters);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page queryOrderAccount(Map<String,Object> map){
		return finGLInterfaceDAO.queryOrderAccount(map);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Page selectFinanceOrderMonitorPage(Map<String,Object> map){
		return finGLInterfaceDAO.selectFinanceOrderMonitorPage(map);
	}
	/**
	 * 生成代收收入
	 * 
	 * @param finBizItem
	 * @return
	 */
	private List<FinGLInterfaceResult> generateGLInterfaceForPayment(FinBizItem finBizItem){
		String filialeName = finGLInterfaceDAO.selectFilialeName(finBizItem.getOrderId());
		//POS机支付做代收
		if(Constant.PAYMENT_GATEWAY.COMM_POS_CASH.getCode().equals(finBizItem.getGateway())
				||Constant.PAYMENT_GATEWAY.COMM_POS.getCode().equals(finBizItem.getGateway())
		||Constant.PAYMENT_GATEWAY.SAND_POS_CASH.getCode().equals(finBizItem.getGateway())
				||Constant.PAYMENT_GATEWAY.SAND_POS.getCode().equals(finBizItem.getGateway())){
			return generateGLInterfaceForInsteadOfAccounts(finBizItem,filialeName);
		}	
		FinGLInterface finGLInterface = new FinGLInterface();
		finGLInterface.setTickedNo(String.valueOf(finBizItem.getOrderId()));
		finGLInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName("JOYU"));//帐套号
		finGLInterface.setMakeBillTime(finBizItem.getTransactionTime());
		finGLInterface.setSummary("代兴旅("+Constant.FILIALE_NAME.getCnName(filialeName)+")收"+finBizItem.getOrderId());
		finGLInterface.setBorrowerAmount(Float.valueOf(finBizItem.getBankAmount()));
		finGLInterface.setLenderAmount(Float.valueOf(finBizItem.getBankAmount()));
		finGLInterface.setProofType(finGLServiceHelper.getProofType(finBizItem.getGateway()));
		
		FinGlSubjectCfg finGlSubjectCfg=finGLServiceHelper.getSubjectCode(FIN_GL_ACCOUNT_TYPE.INSTEAD_INCOME, finBizItem.getGateway(), filialeName);
		//finGLInterface.setBorrowerSubjectCode(finGLServiceHelper.getSubjectCode(FIN_SUBJECT_TYPE.PAYMENT_BORROWER_SUBJECT, finBizItem.getGateway()));
		//finGLInterface.setLenderSubjectCode(finGLServiceHelper.getSubjectCode(FIN_SUBJECT_TYPE.PAYMENT_LENDER_SUBJECT,filialeName));
		finGLInterface.setBorrowerSubjectCode(finGlSubjectCfg.getBorrowSubjectCode());
		finGLInterface.setLenderSubjectCode(finGlSubjectCfg.getLendSubjectCode());
		finGLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.INSTEAD_INCOME.getCode());
		finGLInterface.setBatchNoCust(finGLInterface.getTickedNo()+"_"+finGLInterface.getProofType());

		FinGLInterfaceResult finGLInterfaceResult = new FinGLInterfaceResult();
		finGLInterfaceResult.setFinGLInterface(finGLInterface);
		finGLInterfaceResult.setSuccess(Boolean.TRUE);
		List<FinGLInterfaceResult> result = new ArrayList<FinGLInterfaceResult>();
		result.add(finGLInterfaceResult);
		if(Constant.PAYMENT_GATEWAY.STORED_CARD.getCode().equals(finBizItem.getGateway())){
			FinGLInterface czk = new FinGLInterface();
			BeanUtils.copyProperties(finGLInterface, czk);
			czk.setSummary("代景域收 "+finBizItem.getOrderId());
			czk.setBorrowerSubjectCode("220311");
			czk.setBorrowerSubjectName("储值卡行POS机");
			czk.setLenderSubjectCode("22413001");
			czk.setLenderSubjectName("其他应付款-景域");
			czk.setAccountBookId("702");
			FinGLInterfaceResult czkResult = new FinGLInterfaceResult();
			czkResult.setFinGLInterface(czk);
			czkResult.setSuccess(Boolean.TRUE);
			result.add(czkResult);
		}
		return result;		
	}
	
	
	
	/**
	 * 生成预收收入
	 * 
	 * @param finBizItem
	 * @return
	 */
	private List<FinGLInterfaceResult> generateGLInterfaceForPrePayment(FinBizItem finBizItem){
		List<FinGLInterfaceResult> resultList = new ArrayList<FinGLInterfaceResult>();
		FinGLInterfaceDTO finGLInterfaceDTOPayment = finGLInterfaceDAO.selectOrderPayment(finBizItem.getOrderId());
		if(isPartpay(finGLInterfaceDTOPayment,finBizItem)){//部分支付作预收
			FinGLInterfaceResult finGLInterfaceResult = generateGLInterfaceForPartpayBase(finBizItem,finGLInterfaceDTOPayment.getFilialeName(),Boolean.FALSE);
			resultList.add(finGLInterfaceResult);
			if(null==finGLInterfaceDTOPayment.getGlStatus()){
				finGLInterfaceDAO.updateOrdOrder(finBizItem.getOrderId(), Constant.GL_STATUS.DEPOSIT_RECEIVED_PART.getCode());
			//解决部分支付勾兑数据倒置的问题，做了预收不冲
			}else if(Constant.PAYMENT_STATUS.PAYED.name().equalsIgnoreCase(finGLInterfaceDTOPayment.getPaymentStatus()) && null!=finGLInterfaceDTOPayment.getGlStatus()&&!Constant.GL_STATUS.DEPOSIT_RECEIVED_PART.getCode().equalsIgnoreCase(finGLInterfaceDTOPayment.getGlStatus())){
				FinGLInterface finGLInterface2=new FinGLInterface();
				BeanUtils.copyProperties(finGLInterfaceResult.getFinGLInterface(), finGLInterface2);
				finGLInterface2.setBorrowerAmount(-finGLInterface2.getBorrowerAmount());
				finGLInterface2.setLenderAmount(-finGLInterface2.getLenderAmount());
				finGLInterface2.setMakeBillTime(finBizItem.getTransactionTime());
				finGLInterface2.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME_PARTPAY_HEDGE.getCode());
				finGLInterface2.setBatchNoCust(finBizItem.getOrderId()+"_"+finGLInterface2.getProofType());
				FinGLInterfaceResult finGLInterfaceResult2 = new FinGLInterfaceResult();
				finGLInterfaceResult2.setFinGLInterface(finGLInterface2);
				finGLInterfaceResult.setSuccess(Boolean.TRUE);
				resultList.add(finGLInterfaceResult2);
			}
		}else{
			if(null!=finGLInterfaceDTOPayment && (Constant.PAYMENT_STATUS.PAYED.name().equalsIgnoreCase(finGLInterfaceDTOPayment.getPaymentStatus()) || Constant.PAYMENT_STATUS.TRANSFERRED.name().equalsIgnoreCase(finGLInterfaceDTOPayment.getPaymentStatus()))&&Constant.GL_STATUS.DEPOSIT_RECEIVED_PART.getCode().equalsIgnoreCase(finGLInterfaceDTOPayment.getGlStatus())){
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("tickedNo", Long.toString(finBizItem.getOrderId()));//解决ORA-01722: invalid number
				param.put("accountType", Constant.FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME_PARTPAY.getCode());
				List<FinGLInterface> finGLInterfaces = finGLInterfaceDAO.searchRecordByCondition(param);
				//float amount = Float.valueOf(finReconResult.getAmount());
				if(null!=finGLInterfaces && !finGLInterfaces.isEmpty()){
					for(FinGLInterface finGLInterface:finGLInterfaces){
						//amount+=finGLInterface.getBorrowerAmount();
						finGLInterface.setBorrowerAmount(-finGLInterface.getBorrowerAmount());
						finGLInterface.setLenderAmount(-finGLInterface.getLenderAmount());
						finGLInterface.setMakeBillTime(finBizItem.getTransactionTime());
						finGLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME_PARTPAY_HEDGE.getCode());
						finGLInterface.setBatchNoCust(finBizItem.getOrderId()+"_"+finGLInterface.getProofType());
						FinGLInterfaceResult finGLInterfaceResult = new FinGLInterfaceResult();
						finGLInterfaceResult.setFinGLInterface(finGLInterface);	
						finGLInterfaceResult.setSuccess(Boolean.TRUE);
						resultList.add(finGLInterfaceResult);
					}
				}
			}
			List<FinGLInterfaceDTO> perPaymentParamList = finGLInterfaceDAO.selectPerPaymentParam(finBizItem.getOrderId());
			if(perPaymentParamList!=null && perPaymentParamList.size()>0){
				for(FinGLInterfaceDTO param: perPaymentParamList) {
					FinGLInterface finGLInterface = new FinGLInterface();
					finGLInterface.setTickedNo(String.valueOf(finBizItem.getOrderId()));
					finGLInterface.setProofType(finGLServiceHelper.getHashProofTypeByWL(finBizItem.getOrderId()));
					finGLInterface.setMakeBillTime(finBizItem.getTransactionTime());
					finGLInterface.setSummary("预收"+finBizItem.getOrderId()+param.getProductName());
					
					finGLInterface.setBorrowerAmount(Float.valueOf(param.getPaidAmount()));
					finGLInterface.setLenderAmount(Float.valueOf(param.getPaidAmount()));
					finGLInterface.setProductCode(""+param.getProductId());
					finGLInterface.setProductId(param.getProductId());
					finGLInterface.setProductName(param.getProductName());
					finGLInterface.setExt1(String.valueOf(finBizItem.getOrderId()));
					finGLInterface.setExt10(param.getTravelGroupCode());
					finGLInterface.setExt4(DateUtil.formatDate(param.getVisitTime(), "yyyy-MM-dd"));
					finGLInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName(param.getFilialeName()));
					
					FinGlSubjectCfg cfg=finGLServiceHelper.getSubjectCode(FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME, param);
					finGLInterface.setBorrowerSubjectCode(cfg.getBorrowSubjectCode());
					finGLInterface.setLenderSubjectCode(cfg.getLendSubjectCode());
					//finGLInterface.setLenderSubjectCode(finGLServiceHelper.getLenderSubjectCode(FIN_SUBJECT_TYPE.PRE_PAYMENT_LENDER_SUBJECT.name(), param));
					finGLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME.getCode());
					finGLInterface.setBatchNoCust(finBizItem.getOrderId()+"_"+finGLInterface.getProofType());

					FinGLInterfaceResult finGLInterfaceResult = new FinGLInterfaceResult();
					finGLInterfaceResult.setFinGLInterface(finGLInterface);
					finGLInterfaceResult.setSuccess(Boolean.TRUE);
					resultList.add(finGLInterfaceResult);
					if(null!=transferOldOrderIdMap.get(finBizItem.getOrderId())){
						FinGLInterfaceDTO oldTransOrder = finGLInterfaceDAO.selectCancelOrderDate(finBizItem.getOrderId());
						FinGLInterface transFinGLInterface = new FinGLInterface();
						BeanUtils.copyProperties(finGLInterface, transFinGLInterface);
						resultList.add(transferOldOrderHedge(transFinGLInterface,oldTransOrder,finBizItem.getOrderId(),transferOldOrderIdMap.get(finBizItem.getOrderId())));
					}
				}
				finGLInterfaceDAO.updateOrdOrder(finBizItem.getOrderId(), Constant.GL_STATUS.DEPOSIT_RECEIVED.getCode());
			}
		}
		
		return resultList;		
	}
	
	/**
	 * 判断是否做部分支付预收
	 * @param finGLInterfaceDTOPayment
	 * @param finBizItem
	 * @return
	 */
	private boolean isPartpay(final FinGLInterfaceDTO finGLInterfaceDTOPayment,final FinBizItem finBizItem){
		if(null==finGLInterfaceDTOPayment || null==finBizItem) return Boolean.FALSE;
		return  Constant.PAYMENT_STATUS.PARTPAY.name().equalsIgnoreCase(finGLInterfaceDTOPayment.getPaymentStatus()) 
		|| (null != finGLInterfaceDTOPayment.getPaymentFinishTime() && DateUtil
				.compareDateLessOneDayMore(finGLInterfaceDTOPayment.getPaymentFinishTime(),
						finBizItem.getTransactionTime()))
		|| (Constant.PAYMENT_STATUS.TRANSFERRED.name().equalsIgnoreCase(finGLInterfaceDTOPayment.getPaymentStatus()) && null == finGLInterfaceDTOPayment.getPaymentFinishTime());
	}
	
	/**
	 * 生成存款账户提现到卡数据
	 * @param finBizItem
	 * @return
	 */
	private FinGLInterfaceResult generateGLInterfaceForCashMoneyDraw(FinBizItem finBizItem){
		FinGLInterfaceResult finGLInterfaceResult = new FinGLInterfaceResult();
		FinGLInterface finGLInterface=new FinGLInterface();
		finGLInterface.setTickedNo("游客提现"+finBizItem.getReconResultId());//票号
		finGLInterface.setMakeBillTime(finBizItem.getTransactionTime());//制单日期：成功提现日期
		finGLInterface.setSummary("游客提现"+finBizItem.getReconResultId());//摘要
		finGLInterface.setBorrowerAmount(finBizItem.getBankAmount().floatValue());//借方金额:提现金额
		finGLInterface.setLenderAmount(finBizItem.getBankAmount().floatValue());//贷方金额:预收金额
		finGLInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName("JOYU"));//帐套号
		//初始化凭证类型
		finGLInterface.setProofType(finGLServiceHelper.getProofType(finBizItem.getGateway()));
		//借方科目编码,固定的,虚拟账户
		//finGLInterface.setBorrowerSubjectCode(finGLServiceHelper.getSubjectCode(FIN_SUBJECT_TYPE.VIRTUAL_ACCOUNT));
		FinGlSubjectCfg cfg=finGLServiceHelper.getSubjectCodeWithLendGateWay(FIN_GL_ACCOUNT_TYPE.GUEST_DRAWCASH, finBizItem.getGateway());
		//借方科目编码,固定的,虚拟账户
		finGLInterface.setBorrowerSubjectCode(cfg.getBorrowSubjectCode());
		finGLInterface.setLenderSubjectCode(cfg.getLendSubjectCode());
		//finGLInterface.setLenderSubjectCode(finGLServiceHelper.getSubjectCode(FIN_SUBJECT_TYPE.PAYMENT_BORROWER_SUBJECT,finBizItem.getGateway()));
		finGLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.GUEST_DRAWCASH.getCode());
		finGLInterface.setBatchNoCust("游客提现"+finBizItem.getReconResultId());//批量合并号
		finGLInterfaceResult.setFinGLInterface(finGLInterface);
		return finGLInterfaceResult;
	}
	
	/**
	 * 生成存款账户充值数据
	 * @param finBizItem
	 * @return
	 */
	private FinGLInterfaceResult generateGLInterfaceForCashRecharge(FinBizItem finBizItem){
		FinGLInterfaceResult finGLInterfaceResult = new FinGLInterfaceResult();
		FinGLInterface finGLInterface=new FinGLInterface();
		finGLInterface.setTickedNo("现金账户充值"+finBizItem.getReconResultId());//票号
		finGLInterface.setMakeBillTime(finBizItem.getTransactionTime());//制单日期：成功充值日期
		finGLInterface.setSummary("现金账户充值"+finBizItem.getReconResultId());//摘要
		finGLInterface.setBorrowerAmount(finBizItem.getBankAmount().floatValue());//借方金额:充值金额
		finGLInterface.setLenderAmount(finBizItem.getBankAmount().floatValue());//贷方金额
		finGLInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName("JOYU"));//帐套号
		//初始化凭证类型
		finGLInterface.setProofType(finGLServiceHelper.getProofType(finBizItem.getGateway()));
		//根据充值渠道,查询借方科目编码
		//finGLInterface.setBorrowerSubjectCode(finGLServiceHelper.getSubjectCode(FIN_SUBJECT_TYPE.PAYMENT_BORROWER_SUBJECT,finBizItem.getGateway()));
		FinGlSubjectCfg cfg=finGLServiceHelper.getSubjectCodeWithBorrowGateWay(FIN_GL_ACCOUNT_TYPE.CASH_ACCOUNT_RECHARGE,finBizItem.getGateway());
		finGLInterface.setBorrowerSubjectCode(cfg.getBorrowSubjectCode());
		finGLInterface.setLenderSubjectCode(cfg.getLendSubjectCode());
		//finGLInterface.setLenderSubjectCode(finGLServiceHelper.getSubjectCode(FIN_SUBJECT_TYPE.VIRTUAL_ACCOUNT));
		finGLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.CASH_ACCOUNT_RECHARGE.getCode());
		finGLInterface.setBatchNoCust("现金账户充值"+finBizItem.getReconResultId());//批量合并号

		finGLInterfaceResult.setFinGLInterface(finGLInterface);
		return finGLInterfaceResult;
	}
	
	/**
	 * 生成手续费数据(支付手续费、退款手续费、普通提现、对外付款 、废单重下 无法对账)
	 * @param finBizItem
	 * @return
	 */
	@Deprecated
	private FinGLInterfaceResult generateGLInterfaceForPaymentFee(FinBizItem finBizItem){
		FinGLInterfaceResult finGLInterfaceResult = new FinGLInterfaceResult();
		FinGLInterface finGLInterface=new FinGLInterface();
		finGLInterface.setTickedNo(Constant.PAYMENT_GATEWAY.getCnName(finBizItem.getGateway())+"手续费"+finBizItem.getReconResultId());//票号
		finGLInterface.setMakeBillTime(finBizItem.getTransactionTime());//制单日期：支付平台扣收取费时间
		finGLInterface.setSummary(Constant.PAYMENT_GATEWAY.getCnName(finBizItem.getGateway())+"手续费"+finBizItem.getReconResultId());//摘要
		finGLInterface.setBorrowerAmount(finBizItem.getBankAmount().floatValue());//借方金额:充值金额
		finGLInterface.setLenderAmount(finBizItem.getBankAmount().floatValue());//贷方金额
		finGLInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName("JOYU"));//帐套号
		finGLInterface.setProductCode("zfb");//手续费、退手续费 有项目代码，根据支付渠道来的，写到产品编码，目前只有支付宝，可以直接写成zfb
		finGLInterface.setProductName(Constant.PAYMENT_GATEWAY.getCnName(finBizItem.getGateway())+"手续费");
		//初始化凭证类型
		finGLInterface.setProofType(finGLServiceHelper.getProofType(finBizItem.getGateway()));
		//根据充值渠道,查询借方科目编码
		finGLInterface.setBorrowerSubjectCode(finGLServiceHelper.getSubjectCode(FIN_SUBJECT_TYPE.PAY_FEE_BORROWER_SUBJECT,finBizItem.getGateway()));
		//查询贷方科目编码
		finGLInterface.setLenderSubjectCode(finGLServiceHelper.getSubjectCode(FIN_SUBJECT_TYPE.PAYMENT_BORROWER_SUBJECT));
		finGLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.FEE.getCode());
		finGLInterface.setBatchNoCust(Constant.PAYMENT_GATEWAY.getCnName(finBizItem.getGateway())+"手续费"+finBizItem.getReconResultId());//批量合并号

		finGLInterfaceResult.setFinGLInterface(finGLInterface);
		return finGLInterfaceResult;
	}
	
	/**
	 * 生成退手续费数据
	 * @param finBizItem
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private List<FinGLInterfaceResult> generateGLInterfaceForRefundmentFee(FinBizItem finBizItem) throws IllegalAccessException, InvocationTargetException{
		
		//全部都在借方科目编码产生2条数据
		List<FinGLInterfaceResult> finGLInterfaceResultList=new ArrayList<FinGLInterfaceResult>();
		
		
		List<FinGlSubjectCfg> cfgList=finGLServiceHelper.getSubjectCodeRefundFeeWithGateWay(finBizItem.getGateway());
		
		//第一条数据
		FinGLInterfaceResult finGLInterfaceResult = new FinGLInterfaceResult();
		FinGLInterface finGLInterface=new FinGLInterface();
		finGLInterface.setCreateTime(new Date());
		finGLInterface.setTickedNo(Constant.PAYMENT_GATEWAY.getCnName(finBizItem.getGateway())+"退手续费"+finBizItem.getReconResultId());//票号
		finGLInterface.setMakeBillTime(finBizItem.getTransactionTime());//制单日期：支付平台退手续费时间
		finGLInterface.setSummary(Constant.PAYMENT_GATEWAY.getCnName(finBizItem.getGateway())+"退手续费"+finBizItem.getReconResultId());//摘要
		finGLInterface.setProductCode("zfb");//手续费、退手续费 有项目代码，根据支付渠道来的，写到产品编码，目前只有支付宝，可以直接写成zfb
		finGLInterface.setProductName(Constant.PAYMENT_GATEWAY.getCnName(finBizItem.getGateway())+"手续费");
		finGLInterface.setBorrowerAmount(finBizItem.getBankAmount().floatValue());//借方金额:支付平台退费金额
		finGLInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName("JOYU"));//帐套号
		finGLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.REFUND_FEE.getCode());
		//初始化凭证类型
		finGLInterface.setProofType(finGLServiceHelper.getProofType(finBizItem.getGateway()));
		//根据充值渠道,查询借方科目编码
		finGLInterface.setBorrowerSubjectCode(cfgList.get(0).getBorrowSubjectCode());
		//finGLInterface.setBorrowerSubjectCode(finGLServiceHelper.getSubjectCode(FIN_SUBJECT_TYPE.PAYMENT_BORROWER_SUBJECT,finBizItem.getGateway()));
		finGLInterface.setIsStd("N");
		finGLInterface.setBatchNoCust(Constant.PAYMENT_GATEWAY.getCnName(finBizItem.getGateway())+"退手续费"+finBizItem.getReconResultId());//批量合并号
		
		finGLInterfaceResult.setFinGLInterface(finGLInterface);
		
		//第二条数据
		FinGLInterfaceResult finGLInterfaceResult2 = new FinGLInterfaceResult();
		FinGLInterface finGLInterface2=new FinGLInterface();
		BeanUtils.copyProperties(finGLInterface,finGLInterface2);
		//借方金额:支付平台退费金额
		finGLInterface2.setBorrowerAmount(-finBizItem.getBankAmount().floatValue());
		//根据充值渠道,查询借方科目编码
		finGLInterface2.setBorrowerSubjectCode(cfgList.get(1).getBorrowSubjectCode());
		//finGLInterface2.setBorrowerSubjectCode(finGLServiceHelper.getSubjectCode(FIN_SUBJECT_TYPE.PAY_FEE_BORROWER_SUBJECT, finBizItem.getGateway()));
		finGLInterface.setIsStd("N");
		finGLInterfaceResult2.setFinGLInterface(finGLInterface2);
		
		finGLInterfaceResultList.add(finGLInterfaceResult);
		finGLInterfaceResultList.add(finGLInterfaceResult2);
		return finGLInterfaceResultList;
	}
	
	/**
	 * 生成普通提现（公司提现）数据
	 * @param finBizItem
	 * @return
	 */
	private FinGLInterfaceResult generateGLInterfaceForNormalDrawcash(FinBizItem finBizItem){
		FinGLInterfaceResult finGLInterfaceResult = new FinGLInterfaceResult();
		FinGLInterface finGLInterface=new FinGLInterface();
		finGLInterface.setTickedNo(Constant.PAYMENT_GATEWAY.getCnName(finBizItem.getGateway())+"金融工具提现"+finBizItem.getReconResultId());//票号
		finGLInterface.setMakeBillTime(finBizItem.getTransactionTime());//制单日期
		finGLInterface.setSummary(Constant.PAYMENT_GATEWAY.getCnName(finBizItem.getGateway())+"金融工具提现"+finBizItem.getReconResultId());//摘要
		finGLInterface.setBorrowerAmount(finBizItem.getBankAmount().floatValue());//借方金额:支付平台提现金额
		finGLInterface.setLenderAmount(finBizItem.getBankAmount().floatValue());//贷方金额：支付平台提现金额
		finGLInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName("JOYU"));//帐套号
		//初始化凭证类型
		finGLInterface.setProofType(finGLServiceHelper.getProofType(finBizItem.getGateway()));
		//根据不同的支付平台来确认借方科目。
		//支付宝，拉卡拉，百付手机端平台是提现到建行的;招行直连，招行分期是提现到招行的;电话预授权，银联在线预授权是提现到交行的;
		
		FinGlSubjectCfg cfg=finGLServiceHelper.getSubjectCodeByGateWay(FIN_GL_ACCOUNT_TYPE.NORMAL_DRAWCASH, finBizItem.getGateway(),finBizItem.getGateway());
		finGLInterface.setBorrowerSubjectCode(cfg.getBorrowSubjectCode());
		finGLInterface.setLenderSubjectCode(cfg.getLendSubjectCode());
		
		//finGLInterface.setBorrowerSubjectCode(finGLServiceHelper.getSubjectCode(FIN_SUBJECT_TYPE.NORMALDRAWCASH_BORROWER_SUBJECT, finBizItem.getGateway()));
		//根据支付平台来确认科目编码
		//finGLInterface.setLenderSubjectCode(finGLServiceHelper.getSubjectCode(FIN_SUBJECT_TYPE.PAYMENT_BORROWER_SUBJECT, finBizItem.getGateway()));

		finGLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.NORMAL_DRAWCASH.getCode());
		finGLInterface.setBatchNoCust(Constant.PAYMENT_GATEWAY.getCnName(finBizItem.getGateway())+"金融工具提现"+finBizItem.getReconResultId());//批量合并号
		finGLInterfaceResult.setFinGLInterface(finGLInterface);
		return finGLInterfaceResult;
	}
	
	/**
	 * 产生废单重下数据
	 * @param finBizItem 勾兑结果
	 * @return
	 */
	private List<FinGLInterfaceResult> generateGLInterfaceForCancelToCreateNew(FinBizItem finBizItem){
		Map<String,Object> dataMap=finGLInterfaceDAO.selectOrderInfo(finBizItem.getOrderId());
		Long oldOrderId=MapUtils.getLong(dataMap, "ORI_ORDER_ID");
		//String glStatus=MapUtils.getString(dataMap, "GL_STATUS");
		//String visitTime = MapUtils.getString(dataMap, "VISIT_TIME");
		Long prePaidNo = finGLInterfaceDAO.selectPaidNoForCancePrePay(oldOrderId);
		
		if((Constant.PAYMENT_GATEWAY.CHINAPAY_PRE.getCode().equalsIgnoreCase(finBizItem.getGateway())
				&& prePaidNo!=null && prePaidNo.longValue()>0) 
				||!Constant.PAYMENT_GATEWAY.CHINAPAY_PRE.getCode().equalsIgnoreCase(finBizItem.getGateway())){
			try{
				Map<String,Object> parameters = new HashMap<String,Object>();
				parameters.put("tickedNo", Long.toString(oldOrderId));
				parameters.put("accountTypes", new String[]{Constant.FIN_GL_ACCOUNT_TYPE.CANCEL_INCOME_HEDGE.getCode()});
				List<FinGLInterface> oldHedgeDatas = finGLInterfaceDAO.searchRecordByCondition(parameters);
				if(null==oldHedgeDatas || (null!=oldHedgeDatas && oldHedgeDatas.isEmpty())){
					return createCancelToCreateNewOrder(finBizItem,oldOrderId);
				}else{
					finBizItem.setMemo("已做废单重下，关联订单"+oldOrderId);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			finBizItem.setMemo("不符合废单重下入账条件，关联订单"+oldOrderId);
		}
		 ArrayList<FinGLInterfaceResult> list = new ArrayList<FinGLInterfaceResult>();
		 FinGLInterfaceResult result = new FinGLInterfaceResult();
		 result.setHasData(false);
		 result.setSuccess(true);
		 list.add(result);
		 return list;
	}
	
	/**
	 * 1. 根据老订单号取出已做预收，或部分预收的数据
	 * 2. 根据 老订单号取出老订单取消时间及所属公司
	 * 3. 根据老订单预收数据冲预收
	 * 4. 根据新订单查询预收数据
	 * 5. 新订单做预收
	 * 6. 如果新老订单所属公司不同，则做对冲数据，先取出老订单的代收数据，再根据代收数据做对冲数据
	 * @param finBizItem
	 * @param oldOrderId
	 * @param sujectType
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private List<FinGLInterfaceResult> createCancelToCreateNewOrder(final FinBizItem finBizItem,final Long oldOrderId) throws IllegalAccessException, InvocationTargetException{
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("accountType", Constant.FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME.getCode());
		parameters.put("tickedNo", Long.toString(oldOrderId));
		List<FinGLInterface> oldOrderDatas = finGLInterfaceDAO.searchRecordByCondition(parameters);
		if(null==oldOrderDatas || (null!=oldOrderDatas && oldOrderDatas.isEmpty())){
			parameters.put("accountType",Constant.FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME_PARTPAY.getCode());
			oldOrderDatas = finGLInterfaceDAO.searchRecordByCondition(parameters);
		}
		FinGLInterfaceDTO oldTransOrder = finGLInterfaceDAO.selectCancelOrderDate(oldOrderId);
		parameters.put("accountTypes", new String[]{Constant.FIN_GL_ACCOUNT_TYPE.CANCEL_INCOME_HEDGE.getCode()});
		List<FinGLInterface> oldHedgeDatas = finGLInterfaceDAO.searchRecordByCondition(parameters);
		List<FinGLInterfaceResult> transferResults = new ArrayList<FinGLInterfaceResult>();
		if(null!=oldOrderDatas && !oldOrderDatas.isEmpty() && (null==oldHedgeDatas ||(null!=oldHedgeDatas && oldHedgeDatas.isEmpty()))){
			for(FinGLInterface finGLInterface:oldOrderDatas){
				transferResults.add(transferOldOrderHedge(finGLInterface,oldTransOrder,oldOrderId,finBizItem.getOrderId()));
			}
		}else{
			finBizItem.setMemo("老订单没有做预收,订单号"+oldOrderId);
			transferOldOrderIdMap.put(oldOrderId, finBizItem.getOrderId());
		}
		finBizItem.setTransactionTime(oldTransOrder.getCancelTime());
		List<FinBizItem> reconResults = new ArrayList<FinBizItem>();
		FinGLInterfaceDTO finGLInterfaceDTOPayment = finGLInterfaceDAO.selectOrderPayment(finBizItem.getOrderId());
		if(isPartpay(finGLInterfaceDTOPayment,finBizItem)){
			Map<String,String> reconparameters = new HashMap<String,String>();
			reconparameters.put("orderId", Long.toString(finBizItem.getOrderId()));
			reconparameters.put("transactionType", Constant.TRANSACTION_TYPE.CANCEL_TO_CREATE_NEW.getCode());
			reconparameters.put("glStatusesNull", "'"+Constant.GL_STATUS.INIT.getCode()+"','"+Constant.GL_STATUS.FAILED.getCode()+"'");
			//TODO remove it
			List<FinReconResult>  reconResultsTemp= finReconResultDAO.selectReconResultListByParas(reconparameters);
			for (FinReconResult temp : reconResultsTemp) {
				FinBizItem item=new FinBizItem();
				BeanUtils.copyProperties(temp, item);
				reconResults.add(item);
			}
		}else{
			reconResults.add(finBizItem);
		}
		for(FinBizItem finRecon:reconResults){
			//新订单销售产品做预收
			List<FinGLInterfaceResult>  newOrderDatas =generateGLInterfaceForPrePayment(finRecon);
			if(null!=newOrderDatas && !newOrderDatas.isEmpty()){
				for(FinGLInterfaceResult result:newOrderDatas){
					result.getFinGLInterface().setSummary("废单重下"+oldOrderId+"-"+finBizItem.getOrderId());
					result.getFinGLInterface().setMakeBillTime(oldTransOrder.getCancelTime());
				}
				transferResults.addAll(newOrderDatas);
			}else{
				finBizItem.setMemo(" 没有找到拆分后数据或部分支付数据");
			}
		}

		FinGLInterfaceDTO newTransOrder = finGLInterfaceDAO.selectCancelOrderDate(finBizItem.getOrderId());
		String newFilialeName = newTransOrder.getFilialeName();
		if(!oldTransOrder.getFilialeName().equalsIgnoreCase(newFilialeName)){
			parameters.put("tickedNo", Long.toString(oldOrderId));
			parameters.put("accountTypes",new String[]{Constant.FIN_GL_ACCOUNT_TYPE.INSTEAD_INCOME.getCode(),Constant.FIN_GL_ACCOUNT_TYPE.INSTEAD_INCOME_POS.getCode()});
			List<FinGLInterface> closeOutCollection = finGLInterfaceDAO.searchRecordByCondition(parameters);
			for(FinGLInterface closeOutData:closeOutCollection){
				FinGLInterface finGLInterface = new FinGLInterface();
				finGLInterface.setTickedNo(String.valueOf(finBizItem.getOrderId()));
				finGLInterface.setProofType("FDCX");
				finGLInterface.setIsStd("N");
				finGLInterface.setBatchNoCust(finBizItem.getOrderId() + "_"+ finGLInterface.getProofType());// 新订单号+凭证类型
				finGLInterface.setMakeBillTime(oldTransOrder.getCancelTime());// 废单日期
				finGLInterface.setSummary("废单重下往来调整" + oldOrderId + "-"+ finBizItem.getOrderId());// 摘要:废单重现+老订单号-新订单号
				finGLInterface.setLenderAmount(-closeOutData.getLenderAmount());
				finGLInterface.setLenderSubjectCode(closeOutData.getLenderSubjectCode());
				finGLInterface.setLenderSubjectName("其他应付款-"+Constant.FILIALE_NAME.getCnName(oldTransOrder.getFilialeName()));
				finGLInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName("JOYU"));
				finGLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.CANCEL_TO_CREATE_NEW.getCode());
				FinGLInterface newFinGLInterface = new FinGLInterface();
				BeanUtils.copyProperties(finGLInterface,newFinGLInterface);
				newFinGLInterface.setLenderAmount(closeOutData.getLenderAmount());
				FinGlSubjectCfg cfg=finGLServiceHelper.getLendSubjectCode(FIN_GL_ACCOUNT_TYPE.CANCEL_TO_CREATE_NEW, newFilialeName);
				newFinGLInterface.setLenderSubjectCode(cfg.getLendSubjectCode());
				//newFinGLInterface.setLenderSubjectCode(finGLServiceHelper.getSubjectCode(FIN_SUBJECT_TYPE.PAYMENT_LENDER_SUBJECT,newFilialeName));
				newFinGLInterface.setLenderSubjectName("其他应收款-"+Constant.FILIALE_NAME.getCnName(newFilialeName));
				FinGLInterfaceResult finGLInterfaceResult = new FinGLInterfaceResult();
				FinGLInterfaceResult finGLInterfaceResult2 = new FinGLInterfaceResult();
				finGLInterfaceResult.setFinGLInterface(finGLInterface);
				finGLInterfaceResult2.setFinGLInterface(newFinGLInterface);
				transferResults.add(finGLInterfaceResult);
				transferResults.add(finGLInterfaceResult2);
			}
		}
		return transferResults;
	}
	
	private  FinGLInterfaceResult transferOldOrderHedge(FinGLInterface finGLInterface,FinGLInterfaceDTO oldTransOrder,Long oldOrderId,Long newOrderId){
		finGLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.CANCEL_INCOME_HEDGE.getCode());
		finGLInterface.setBorrowerSubjectCode("22413001");
		finGLInterface.setBorrowerAmount(-finGLInterface.getBorrowerAmount());
		finGLInterface.setLenderAmount(-finGLInterface.getLenderAmount());
		finGLInterface.setMakeBillTime(oldTransOrder.getCancelTime());
		finGLInterface.setProofType("FDCX");
		finGLInterface.setReceivablesResult(null);
		finGLInterface.setReceivablesStatus(null);
		finGLInterface.setBatchNoCust(newOrderId+"_"+finGLInterface.getProofType());
		finGLInterface.setSummary("废单重下" + oldOrderId + "-"+newOrderId);// 摘要:废单重现+老订单号-新订单号
		finGLInterface.setInoId(null);
		FinGLInterfaceResult finGLInterfaceResult = new FinGLInterfaceResult();
		finGLInterfaceResult.setFinGLInterface(finGLInterface);
		return finGLInterfaceResult;
	}
	@Override
	public void generateGLInterfaceByReconResult(FinBizItem finBizItem) {
		try {
			List<FinGLInterfaceResult> finGLInterfaceResult = new ArrayList<FinGLServiceImpl.FinGLInterfaceResult>();
			
			// 抓取总账数据
			if (Constant.TRANSACTION_TYPE.PAYMENT.name().equals(finBizItem.getTransactionType())) {
				//代收
				List<FinGLInterfaceResult> resultTemps = generateGLInterfaceForPayment(finBizItem);
				if(resultTemps!=null){
					finGLInterfaceResult.addAll(resultTemps);
				}
				//预收
				finGLInterfaceResult.addAll(generateGLInterfaceForPrePayment(finBizItem));
			}
			//退款
			else if(Constant.TRANSACTION_TYPE.REFUNDMENT.name().equals(finBizItem.getTransactionType())){
				finGLInterfaceResult.addAll(generateGLInterfaceForRefundedment(finBizItem));
			}
			//存款账户提现到卡 ||  提款到支付宝
			else if (Constant.TRANSACTION_TYPE.CASH_MONEY_DRAW.name().equals(finBizItem.getTransactionType()) ||
					Constant.TRANSACTION_TYPE.CASH_MONEY_DRAW_ALIPAY.name().equals(finBizItem.getTransactionType())) {
				finGLInterfaceResult.add(generateGLInterfaceForCashMoneyDraw(finBizItem));
			}
			//存款账户充值
			else if (Constant.TRANSACTION_TYPE.CASH_RECHARGE.name().equals(finBizItem.getTransactionType())) {
				finGLInterfaceResult.add(generateGLInterfaceForCashRecharge(finBizItem));
			}
			//手续费
			else if (Constant.TRANSACTION_TYPE.PAYMENT_FEE.name().equals(finBizItem.getTransactionType())) {
				finGLInterfaceResult.add(generateGLInterfaceForPaymentFee(finBizItem));
			}
			//退手续费
			else if(Constant.TRANSACTION_TYPE.REFUNDMENT_FEE.name().equals(finBizItem.getTransactionType())){
				finGLInterfaceResult = generateGLInterfaceForRefundmentFee(finBizItem);
			}
			//普通提现
			else if(Constant.TRANSACTION_TYPE.NORMAL_DRAWCASH.name().equals(finBizItem.getTransactionType())){
				finGLInterfaceResult.add(generateGLInterfaceForNormalDrawcash(finBizItem));
			}
			//废单重下
			else if(Constant.TRANSACTION_TYPE.CANCEL_TO_CREATE_NEW.name().equals(finBizItem.getTransactionType())){
				finGLInterfaceResult=generateGLInterfaceForCancelToCreateNew(finBizItem);
			}
			//对外付款
			else if(Constant.TRANSACTION_TYPE.FOREIGN_PAYMENT.name().equals(finBizItem.getTransactionType())){
				
				FinBizItem finBizItemDb = finBizItemDAO.findFinBizItemDOByPrimaryKey(finBizItem.getBizItemId());
				if(finBizItemDb!=null){
					finBizItemDb.setMemo("Foreign payment can't account.");
					finBizItemDb.setGlStatus(Constant.GL_STATUS.POSTED.name());
					finBizItemDb.setGlTime(new Date());
					finBizItemDAO.updateFinBizItemDO(finBizItemDb);
				}
				
				FinReconResult finReconResultDb= finReconResultDAO.selectByReconResultId(finBizItem.getReconResultId());
				finReconResultDb.setMemo(finBizItem.getMemo());
				finReconResultDb.setGlStatus(finBizItem.getGlStatus());
				finReconResultDb.setGlTime(finBizItem.getGlTime());
				finReconResultDAO.updateGLStatus(finReconResultDb);
				
				return;
			}
			
			if(null!=finGLInterfaceResult && finGLInterfaceResult.size() == 0){
				createGLData(finBizItem, null);
			}else{
				// 生成总账数据
				for (FinGLInterfaceResult finGLResult : finGLInterfaceResult) {
					if(null!=finGLResult.getFinGLInterface()){
						finGLResult.getFinGLInterface().setReconResultId(finBizItem.getReconResultId());
					}
					createGLData(finBizItem, finGLResult);
				}
			}
		} catch (Exception e) {
			loger.error("Generate GLInterface Error: Recon result id: " + finBizItem.getReconResultId() 
						+ ", order id: " + finBizItem.getOrderId(),
						e);
			finBizItem.setMemo("Error Msg: " + e.getMessage());
			createGLData(finBizItem, null);
		}
	}
	
	/**
	 * 与退款有关的做账操作
	 * @param finBizItem
	 * @param list
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private List<FinGLInterfaceResult> generateGLInterfaceForRefundedment(final FinBizItem finBizItem) throws IllegalAccessException, InvocationTargetException{
		FinGLInterfaceResult result = generateGLInterfaceForInsteadRefund(finBizItem);
		List<FinGLInterfaceResult> list= new ArrayList<FinGLInterfaceResult>();
		/**
		 * 查询要进行冲预收，收入，违约金的数据
		 * 1.根据勾兑的数据ID查找。
		 * 2.根据勾兑表与退款支付记录表关联找出与此退款记录相关的勾兑记录，即根据退款ID查找勾兑记录
		 * 3.如果勾兑记录集中的第一次记录状态为未勾兑，则继续查询数据。
		 * 4.根据前几步操作查询出的退款ID查找退款产品项，其它相关信息则与订单表，订单子项表，产品类别表查出
		 * 5.如果退款表中的入账状态为未入账，则取出所得的数据。
		 */
		List<FinGLInterfaceDTO> orderItems = finGLInterfaceDAO.selectRefundedPordByAccountId(finBizItem.getReconResultId());
		if(null!=orderItems && !orderItems.isEmpty()&&orderItems.size()>0){
			Map<String,List<FinGLInterfaceResult>> fixedMap = new HashMap<String,List<FinGLInterfaceResult>> ();
			FinGLInterfaceDTO finGLInterfaceDTOPayment = finGLInterfaceDAO.selectOrderPayment(finBizItem.getOrderId());
			String batchNo = null;
			if(Constant.PAYMENT_STATUS.PARTPAY.name().equalsIgnoreCase(finGLInterfaceDTOPayment.getPaymentStatus())&&(null==finBizItem.getGlStatus()|| Constant.GL_STATUS.INIT.name().equals(finBizItem.getGlStatus()))){//部分支付作预收
				FinGLInterfaceResult finGLInterfaceResult = generateGLInterfaceForPartpayBase(finBizItem,finGLInterfaceDTOPayment.getFilialeName(),Boolean.TRUE);
				finGLInterfaceResult.getFinGLInterface().setSummary("退"+finBizItem.getOrderId());
				finGLInterfaceResult.getFinGLInterface().setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.REFUNDMENT_INCOME_PARTPAY.getCode());
				batchNo = finGLInterfaceResult.getFinGLInterface().getBatchNo();
				list.add(finGLInterfaceResult);
				list.add(result);//代退
				List<FinGLInterfaceResult> fixedList =new ArrayList<FinGLInterfaceResult>();
				fixedList.add(finGLInterfaceResult);
				fixedMap.put("results",fixedList);
		    //取订单入账状态，这个要求取勾兑数据时要按一定的顺序取。如果错了，有可能先取了代退数据，后取了代收数据，使得订单的入账状态为空。	
			}else if(null!=orderItems.get(0).getGlStatus()){
				if(null==orderItems.get(0).getReconGlStatus() || Constant.GL_STATUS.INIT.name().endsWith(orderItems.get(0).getReconGlStatus())){
					Map<String,List<FinGLInterfaceResult>> resultMap=generateGLInterfaceForRefundRushedInAdvance(finBizItem,result.getFinGLInterface(),orderItems);
	 				list.addAll(resultMap.get("results"));
	 				fixedMap.put("results",resultMap.get("results"));
	 				batchNo = resultMap.get("results").get(0).getFinGLInterface().getBatchNo();
					//如果已做收入，则新增一条退款冲收入
					if (Constant.GL_STATUS.REAL_INCOME.name().equals(
							orderItems.get(0).getGlStatus()) || Constant.GL_STATUS.REAL_COST.name().equals(orderItems.get(0).getGlStatus())
							|| (DateUtil.getDaysBetween(new Date(),finBizItem.getTransactionTime()) == 0
								&& null != orderItems.get(0).getVisitTime() 
								&& DateUtil.compareDateLessOneDayMore(DateUtil.getAfterDay(finBizItem.getTransactionTime()),orderItems.get(0).getVisitTime()))
						){
						list.addAll(generateGLInterfaceForRefundImpactIncome(resultMap.get("results2")));
					}
				}
				list.add(result);//代退
			}else{
				String error="Refundedment error invalid state, recon result id:"+finBizItem.getReconResultId()+",order id:"+finBizItem.getOrderId()+",GL_STATUS value: "+orderItems.get(0).getGlStatus();
				finBizItem.setMemo(error);
				result.setSuccess(Boolean.FALSE);
				loger.error(error);
			}
			
			//如果有违约金，则把违约金入账
			Long penaltyAmount=0L;
			String filialeName=null;
			for(FinGLInterfaceDTO fin:orderItems){
				if(fin.getOrderId().equals(finBizItem.getOrderId())&&fin.getReconResultId().equals(finBizItem.getReconResultId())){
					penaltyAmount=fin.getPenaltyAmount();
					filialeName=fin.getFilialeName();
					break;
				}
			}
			if(penaltyAmount>0 && null!=batchNo){
				FinGLInterfaceResult penaltyData = generateGLInterfaceForPenaltyAmount(finBizItem.getOrderId(),result.getFinGLInterface(),Float.valueOf(penaltyAmount),batchNo,filialeName);//违约金
				if(null!=penaltyData){
					if(Constant.PAYMENT_STATUS.PARTPAY.name().equalsIgnoreCase(finGLInterfaceDTOPayment.getPaymentStatus())){
						penaltyData.getFinGLInterface().setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.REFUNDMENT_INCOME_PARTPAY.getCode());
					}
					list.add(penaltyData);
					try{
						List<FinGLInterfaceResult> fixedList = fixedMap.get("results");
						List<FinGLInterfaceResult> subfixedList = new ArrayList<FinGLInterfaceResult>();
						if(null!=fixedList && !fixedList.isEmpty()){
							Float countNum =0.0f;
							for(FinGLInterfaceResult ft:fixedList){
								if(!Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equalsIgnoreCase(ft.getFinGLInterface().getSubProductType())&&ft.getFinGLInterface().getLenderAmount().longValue()<0){
									countNum+=(ft.getFinGLInterface().getLenderAmount());
									subfixedList.add(ft);
								}
							}
							if(countNum.longValue()==0){
								loger.info("orderId"+finBizItem.getOrderId()+" refunded Amount count is zero!");
							}else{
								Float subCountNum =0.0f;
								for(int f=0;f<subfixedList.size();f++){
									FinGLInterfaceResult ft = subfixedList.get(f);
										Float lenderAmount = new BigDecimal((ft.getFinGLInterface().getLenderAmount().longValue())*(penaltyAmount*1.0f)/countNum).setScale(-2, BigDecimal.ROUND_HALF_UP).longValue()*1.0f;
										if(subfixedList.size()>1 ){
											if(f<subfixedList.size()-1){
												subCountNum+=lenderAmount*1.0f;
											}else{
												lenderAmount = penaltyAmount*1.0f-subCountNum;
											}
										}else{
											lenderAmount = penaltyAmount*1.0f;
										}
										if(lenderAmount>0){
											FinGLInterface penaltyDeduction = new FinGLInterface();
											BeanUtils.copyProperties(ft.getFinGLInterface(), penaltyDeduction);
											penaltyDeduction.setLenderAmount(-lenderAmount);
											FinGLInterfaceResult fint = new FinGLInterfaceResult();
											fint.setFinGLInterface(penaltyDeduction);
											list.add(fint);
										}
								}
							}
						}
					}catch(Exception e){
						loger.error(e);
					}
				}
			}
		}else{
			orderItems = finGLInterfaceDAO.selectRefundedCompensationByAccountId(finBizItem.getReconResultId());
			FinGLInterfaceResult resultCompensation = null;
			if(null!=orderItems && !orderItems.isEmpty()&&orderItems.size()>0){
				for(int i=0;i<orderItems.size();i++){
					if(finBizItem.getReconResultId().longValue()!=orderItems.get(i).getReconResultId().longValue() || i>0 ||
							(null!=orderItems.get(0).getReconGlStatus() && !Constant.GL_STATUS.INIT.name().endsWith(orderItems.get(0).getReconGlStatus()))){
						continue;
					}
					resultCompensation = generateGLInterfaceForRefundRushedCompensation(finBizItem,orderItems.get(i));
				}
				if(null!=resultCompensation){
					list.add(resultCompensation);
					list.add(result);
				}else{
					resultCompensation = new FinGLInterfaceResult();
					finBizItem.setGlStatus(Constant.GL_STATUS.POSTED.name());
					finBizItem.setMemo("已做补偿");
					resultCompensation.setHasData(Boolean.FALSE);
					resultCompensation.setSuccess(Boolean.TRUE);
					list.add(resultCompensation);
				}
			}else{
				finBizItem.setMemo("没有找到退款冲预收的数据");
				result.setSuccess(Boolean.FALSE);
			}
		}
		return list;
	}
	/**
	 * 生成代退款总账数据
	 * 
	 * @param finBizItem
	 * @return
	 */
	private FinGLInterfaceResult generateGLInterfaceForInsteadRefund(FinBizItem finBizItem) {
		String filialeName = finGLInterfaceDAO.selectFilialeName(finBizItem.getOrderId());	

		FinGLInterface finGLInterface = new FinGLInterface();
		finGLInterface.setCreateTime(new Date());
		finGLInterface.setTickedNo(finBizItem.getOrderId().toString()); // 订单号
		finGLInterface.setMakeBillTime(finBizItem.getTransactionTime());// 制单日期银行交易时间
		finGLInterface.setSummary("代兴旅("+Constant.FILIALE_NAME.getCnName(filialeName)+")退 " + finBizItem.getOrderId());// 摘要

		//借方科目编码
		FinGlSubjectCfg cfg=finGLServiceHelper.getSubjectCodeWithFilialeNameAndGateWay(FIN_GL_ACCOUNT_TYPE.REFUNDMENT,filialeName,finBizItem.getGateway());
		finGLInterface.setBorrowerSubjectCode(cfg.getBorrowSubjectCode());
		finGLInterface.setLenderSubjectCode(cfg.getLendSubjectCode());
		//finGLInterface.setBorrowerSubjectCode(finGLServiceHelper.getSubjectCode(FIN_SUBJECT_TYPE.INSTEADREFUND_BORROWER_SUBJECT, filialeName));
		finGLInterface.setBorrowerAmount(finBizItem.getBankAmount().floatValue()); // 借方金额
		//finGLInterface.setLenderSubjectCode(finGLServiceHelper.getSubjectCode(FIN_SUBJECT_TYPE.PAYMENT_BORROWER_SUBJECT, finBizItem.getGateway()));// 贷方科目编码
		finGLInterface.setLenderAmount(finBizItem.getBankAmount().floatValue()); // 贷方科目编码
		finGLInterface.setBorrowerSubjectName("其他应付款-景域文化");
		//凭证类型 根据支付渠道来确认具体的凭证编码
		finGLInterface.setProofType(finGLServiceHelper.getProofType(finBizItem.getGateway()));
		finGLInterface.setAccountType(FIN_GL_ACCOUNT_TYPE.REFUNDMENT.name());// 做账类型
		finGLInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName("JOYU"));// 帐套号
		finGLInterface.setBatchNoCust(finBizItem.getOrderId().toString()+"_"+finGLInterface.getProofType()); // 批次合并号

		FinGLInterfaceResult result = new FinGLInterfaceResult();
		result.setFinGLInterface(finGLInterface);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.fin.FinGLService#generateGLInterfaceByOrderGlStatus()
	 */
	@Override
	public void generateGLInterfaceByOrderGlStatus() {	
		List<FinGLInterfaceDTO> perPaymentParamList = new ArrayList<FinGLInterfaceDTO>();
		perPaymentParamList = finGLInterfaceDAO.selectParamByGlStatus(Constant.GL_STATUS.REAL_INCOME_ERROR.getCode());
		Map<Long,Long> orders = new HashMap<Long,Long>();
		for(FinGLInterfaceDTO param: perPaymentParamList){
			if(null==orders.get(param.getOrderId())){
				finGLInterfaceDAO.updateOrdOrder(param.getOrderId(), Constant.GL_STATUS.DEPOSIT_RECEIVED.getCode());
				orders.put(param.getOrderId(), param.getOrderId());
			}
		}
		do{
			loger.info("begin generate finance realIncome data!");
			perPaymentParamList = finGLInterfaceDAO.selectParamByGlStatus(Constant.GL_STATUS.DEPOSIT_RECEIVED.getCode());
			for(FinGLInterfaceDTO param: perPaymentParamList) {
				try {
					FinGLInterface finGLInterface = new FinGLInterface();
					finGLInterface.setTickedNo(String.valueOf(param.getOrderId()));
					finGLInterface.setMakeBillTime(param.getVisitTime());
					finGLInterface.setSummary("确认收入"+param.getOrderId()+param.getProductName());
					
				    FinGlSubjectCfg cfg=finGLServiceHelper.getSubjectCode(FIN_GL_ACCOUNT_TYPE.DETERMINE_INCOME, param, param);
				    finGLInterface.setBorrowerSubjectCode(cfg.getBorrowSubjectCode());
				    finGLInterface.setLenderSubjectCode(cfg.getLendSubjectCode());
					//finGLInterface.setBorrowerSubjectCode(finGLServiceHelper.getLenderSubjectCode(FIN_SUBJECT_TYPE.PRE_PAYMENT_LENDER_SUBJECT.getCode(),param));
					finGLInterface.setBorrowerAmount(Float.valueOf(param.getPaidAmount()-param.getRefundedAmount()));
					finGLInterface.setLenderAmount(Float.valueOf(param.getPaidAmount()-param.getRefundedAmount()));
					finGLInterface.setProductCode(""+param.getProductId());
					finGLInterface.setProductId(param.getProductId());
					finGLInterface.setProductName(param.getProductName());//少了类别
					finGLInterface.setExt1(String.valueOf(param.getOrderId()));
					finGLInterface.setExt10(param.getTravelGroupCode());
					finGLInterface.setExt4(DateUtil.formatDate(param.getVisitTime(),"yyyy-MM-dd"));
					finGLInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName(param.getFilialeName()));
					finGLInterface.setProofType(finGLServiceHelper.getHashProofTypeBySRCB(param.getOrderId()));
					finGLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.DETERMINE_INCOME.getCode());
					//finGLInterface.setLenderSubjectCode(finGLServiceHelper.getLenderSubjectCode(FIN_SUBJECT_TYPE.DETERMINE_LENDER_SUBJECT.getCode(),param));
					finGLInterface.setBatchNoCust(param.getOrderId()+"_SRCB");
	
					
					finGLInterfaceDAO.insert(finGLInterface);// 生成总账数据
					finGLInterfaceDAO.updateOrdOrder(param.getOrderId(), Constant.GL_STATUS.REAL_INCOME.getCode());//更改订单状态
				} catch (Exception e) {
					finGLInterfaceDAO.updateOrdOrder(param.getOrderId(), Constant.GL_STATUS.REAL_INCOME_ERROR.getCode());//更改订单状态,防止无限循环
					loger.error(e);
				}
			}
		}while(perPaymentParamList.size()>0);

	}

	/**
	 * 生成退款冲预收数据
	 * @param finBizItem
	 * @param finGLInterface
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private Map<String,List<FinGLInterfaceResult>> generateGLInterfaceForRefundRushedInAdvance(
			final FinBizItem finBizItem,final FinGLInterface finGLInterface,final List<FinGLInterfaceDTO> orderItems) throws IllegalAccessException, InvocationTargetException{
		return generateGLInterfaceForRefundOffsetBase(finBizItem,finGLInterface,orderItems,Constant.FIN_GL_ACCOUNT_TYPE.REFUNDMENT_ADVANCE.name());
	}

	private List<FinGLInterfaceResult>  generateGLInterfaceForRefundImpactIncome(final List<FinGLInterfaceResult> finGLInterfaceResults) throws IllegalAccessException, InvocationTargetException{
		List<FinGLInterfaceResult> results = new ArrayList<FinGLInterfaceResult>();
		for(FinGLInterfaceResult finGLInterfaceResult:finGLInterfaceResults){
			FinGLInterface finGLInterface = finGLInterfaceResult.getFinGLInterface();
			FinGLInterface gLInterface = new FinGLInterface();
			BeanUtils.copyProperties(finGLInterface,gLInterface);
			gLInterface.setBorrowerAmount(-gLInterface.getBorrowerAmount());
			gLInterface.setLenderAmount(-gLInterface.getLenderAmount());
			gLInterface.setLenderSubjectCode(gLInterface.getTempLenderSubjectCode());
			gLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.REFUNDMENT_INCOME.name());
			gLInterface.setBatchNoCust(gLInterface.getTickedNo()+"_"+gLInterface.getProofType()); // 批次合并号
			if(!Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equals(gLInterface.getSubProductType())){
				FinGLInterfaceResult result = new FinGLInterfaceResult();
				result.setFinGLInterface(gLInterface);
				results.add(result);
			}
		}
		return results;
	}
	/**
	 * 生成退款冲账数据
	 * @param finBizItem
	 * @param finGLInterface
	 * @param finGLInterfaceType
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private Map<String,List<FinGLInterfaceResult>> generateGLInterfaceForRefundOffsetBase(
			final FinBizItem finBizItem,final FinGLInterface finGLInterface,final List<FinGLInterfaceDTO> orderItems,final String finGLInterfaceType) throws IllegalAccessException, InvocationTargetException {
		Map<String,List<FinGLInterfaceResult>> resultMap=new HashMap<String,List<FinGLInterfaceResult>>();
		if(orderItems.isEmpty()){
			return null;
		}
		List<FinGLInterfaceResult> results = new ArrayList<FinGLInterfaceResult>();
		List<FinGLInterfaceResult> results2 = new ArrayList<FinGLInterfaceResult>();
		Long reconResultId = orderItems.get(0).getReconResultId();
		for(FinGLInterfaceDTO param:orderItems){
			if(reconResultId.longValue()!=param.getReconResultId().longValue()){
				continue;
			}
			String filialeName = "SH_FILIALE";
			if(null!=param.getFilialeName()){
				filialeName = param.getFilialeName();
			}
			Long subRefundedAmount =0L;
			//退款金额
			if(null!=param.getRefundedAmount()){
				subRefundedAmount = param.getRefundedAmount();
			}
			
			List<FinGlSubjectCfg> cfgList=finGLServiceHelper.getRefundmentAdvanceSubjectCode(param);
			
			//第一条
			FinGLInterface finInterface = new FinGLInterface();
			BeanUtils.copyProperties(finGLInterface,finInterface);
			finInterface.setBorrowerSubjectCode(null);
			finInterface.setBorrowerAmount(null);
			finInterface.setMakeBillTime(finBizItem.getTransactionTime());//退款时间
			finInterface.setSummary("退 "+finBizItem.getOrderId()+" "+param.getProductName()+"("+param.getBranchName()+")");//摘要
			finInterface.setLenderSubjectCode("22413001");  //贷方科目
			finInterface.setTempLenderSubjectCode(cfgList.get(0).getLendSubjectCode());
			//finInterface.setTempLenderSubjectCode(finGLServiceHelper.getLenderSubjectCode(FIN_SUBJECT_TYPE.DETERMINE_LENDER_SUBJECT.name(),param));  //贷方科目
			finInterface.setSubProductType(param.getSubProductType());
			finInterface.setProductCode(""+param.getProductId()); //销售产品代码
			finInterface.setProductName(param.getProductName()+"("+param.getBranchName()+")");//销售产品名称
			finInterface.setLenderAmount(subRefundedAmount.floatValue()); //贷方金额 同借方金额
			finInterface.setAccountType(finGLInterfaceType);
			finInterface.setExt1(String.valueOf(finBizItem.getOrderId()));
			finInterface.setExt10(param.getTravelGroupCode());
			finInterface.setExt4(DateUtil.formatDate(param.getVisitTime(), "yyyy-MM-dd"));
			//初始化凭证类型
			finInterface.setProofType(finGLServiceHelper.getHashProofTypeByWL(finBizItem.getOrderId()));
			//账套号 订单销售产品所属公司对应的帐套号
			finInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName(filialeName));
			finInterface.setIsStd("N");
			finInterface.setBatchNoCust(finBizItem.getOrderId().toString()+"_"+finInterface.getProofType()); // 批次合并号

			FinGLInterfaceResult result = new FinGLInterfaceResult();
			result.setFinGLInterface(finInterface);
			
			//第二条
			FinGLInterface finInterface2 = new FinGLInterface();
			BeanUtils.copyProperties(finInterface,finInterface2);
			finInterface2.setLenderSubjectCode(cfgList.get(1).getLendSubjectCode());
			//finInterface2.setLenderSubjectCode(finGLServiceHelper.getLenderSubjectCode(FIN_SUBJECT_TYPE.PRE_PAYMENT_LENDER_SUBJECT.name(),param));
			finInterface2.setLenderAmount(-subRefundedAmount.floatValue());
			finInterface2.setBorrowerAmount(null);
			FinGLInterfaceResult result2 = new FinGLInterfaceResult();
			result2.setFinGLInterface(finInterface2);
			
			results.add(result);
			results.add(result2);
			
			
			//构建冲收入数据
			FinGLInterface finInterface3 = new FinGLInterface();
			BeanUtils.copyProperties(finInterface,finInterface3);
			finInterface3.setBorrowerSubjectCode(finInterface2.getLenderSubjectCode());
			finInterface3.setBorrowerAmount(subRefundedAmount.floatValue());
			finInterface3.setIsStd("Y");
			FinGLInterfaceResult result3 = new FinGLInterfaceResult();
			result3.setFinGLInterface(finInterface3);
			results2.add(result3);
			
		}
		resultMap.put("results", results);
		resultMap.put("results2", results2);
		return resultMap;
	}
	/**
	 * 根据勾兑号得到退款中的违约金记录
	 * @param reconResultId
	 * @param finGLInterface
	 * @return
	 */
	private FinGLInterfaceResult generateGLInterfaceForPenaltyAmount(final Long orderId, final FinGLInterface finGLInterface,final Float penaltyAmount,final String batchNo,final String filialeName){
		FinGLInterface finGLPenalty = new FinGLInterface();
		finGLPenalty.setTickedNo(finGLInterface.getTickedNo()); // 订单号
		finGLPenalty.setMakeBillTime(finGLInterface.getMakeBillTime());// 制单日期银行交易时间
		finGLPenalty.setLenderSubjectCode("630101"); // 贷方科目
		finGLPenalty.setLenderSubjectName("营业外收入-退款手续费");
		finGLPenalty.setLenderAmount(penaltyAmount);
		finGLPenalty.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName(filialeName));
		finGLPenalty.setProofType(finGLServiceHelper.getHashProofTypeByWL(orderId));
		finGLPenalty.setSummary("退"+finGLInterface.getTickedNo());
		finGLPenalty.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.REFUNDMENT_ADVANCE.getCode());
		finGLPenalty.setIsStd("N");
		finGLPenalty.setBatchNo(batchNo); // 批次合并号

		FinGLInterfaceResult finGLInterfaceResult = new FinGLInterfaceResult();
		finGLInterfaceResult.setFinGLInterface(finGLPenalty);
		return finGLInterfaceResult;
	}
	
	
	
	private FinGLInterfaceResult  generateGLInterfaceForRefundRushedCompensation(final FinBizItem finReconResult,final FinGLInterfaceDTO dto){
		FinGLInterface compensation = new FinGLInterface();
		compensation.setTickedNo(String.valueOf(finReconResult.getOrderId()));
		
		compensation.setBorrowerSubjectName("其他应收款-补偿款");
		compensation.setBorrowerAmount(Float.valueOf(dto.getAmount()));
		compensation.setMakeBillTime(finReconResult.getTransactionTime());//退款时间
		compensation.setSummary("补偿 "+finReconResult.getOrderId());//摘要
		
		compensation.setLenderSubjectName("其他应付款-景域");
		compensation.setLenderAmount(Float.valueOf(dto.getAmount()));
		compensation.setSupplierCode("00001");
		compensation.setSupplierName("驴妈妈管理中心质保组");
		
		
		FinGlSubjectCfg cfg=finGLServiceHelper.getSubjectCode(FIN_GL_ACCOUNT_TYPE.COMPENSATION);
		compensation.setBorrowerSubjectCode(cfg.getBorrowSubjectCode());//借方科目
		compensation.setLenderSubjectCode(cfg.getLendSubjectCode());  //贷方科目
		
		compensation.setProofType(finGLServiceHelper.getHashProofTypeByWL(finReconResult.getOrderId()));
		//账套号 订单销售产品所属公司对应的帐套号
		compensation.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName(dto.getFilialeName()));
		compensation.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.COMPENSATION.getCode());
		compensation.setBatchNoCust(finReconResult.getOrderId().toString()+"_"+compensation.getProofType()); // 批次合并号
		FinGLInterfaceResult finGLInterfaceResult = new FinGLInterfaceResult();
		finGLInterfaceResult.setFinGLInterface(compensation);
		return finGLInterfaceResult;
	}
	
	/**
	 * 产生部分支付数据
	 * @param finBizItem 勾兑信息
	 * @param filialeName 所属公司
	 * @param isHedge 是否对冲
	 * @return
	 */
	private FinGLInterfaceResult generateGLInterfaceForPartpayBase(final FinBizItem finBizItem,final String filialName,final boolean isHedge){
		FinGLInterface finGLInterface = new FinGLInterface();
		finGLInterface.setTickedNo(String.valueOf(finBizItem.getOrderId()));
		finGLInterface.setProofType("WL");
		finGLInterface.setMakeBillTime(finBizItem.getTransactionTime());
		finGLInterface.setSummary("预收"+finBizItem.getOrderId());
		//finGLInterface.setBorrowerSubjectCode("22413001");
		finGLInterface.setBorrowerSubjectName("景域应付");
		
		float amount = Float.valueOf(finBizItem.getAmount());
		if(isHedge){
			amount = -amount;
		}
		finGLInterface.setBorrowerAmount(amount);
		finGLInterface.setLenderAmount(amount);
		//finGLInterface.setLenderSubjectCode("220316");
		finGLInterface.setLenderSubjectName("部分支付");
		
		FinGlSubjectCfg cfg=finGLServiceHelper.getSubjectCode(FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME_PARTPAY);
		finGLInterface.setBorrowerSubjectCode(cfg.getBorrowSubjectCode());//借方科目
		finGLInterface.setLenderSubjectCode(cfg.getLendSubjectCode());  //贷方科目
		
		
		finGLInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName(filialName));
		finGLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME_PARTPAY.getCode());//部分支付
		finGLInterface.setBatchNoCust(finBizItem.getOrderId()+"_WL");

		FinGLInterfaceResult finGLInterfaceResult = new FinGLInterfaceResult();
		finGLInterfaceResult.setFinGLInterface(finGLInterface);
		finGLInterfaceResult.setSuccess(Boolean.TRUE);
		return finGLInterfaceResult;
	}
	private List<FinGLInterfaceResult> generateGLInterfaceForInsteadOfAccounts(final FinBizItem finReconResult,final String filialeName){
		List<FinGLInterfaceResult> result = new ArrayList<FinGLInterfaceResult>();
		FinGLInterfaceResult toXingLu = generateGLInterfaceForInsteadOfAccountsBase(finReconResult,filialeName,Boolean.FALSE);
		FinGLInterfaceResult toJoyu = generateGLInterfaceForInsteadOfAccountsBase(finReconResult,filialeName,Boolean.TRUE);
		result.add(toXingLu);
		result.add(toJoyu);
		return result;
	}
	private FinGLInterfaceResult generateGLInterfaceForInsteadOfAccountsBase(final FinBizItem finReconResult,final String filialeName,boolean isJoyu){
		FinGLInterface finGLInterface = new FinGLInterface();
		finGLInterface.setTickedNo(String.valueOf(finReconResult.getOrderId()));//订单号
		finGLInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName(Constant.FILIALE_NAME.SH_FILIALE.getCode()));//帐套号
		finGLInterface.setMakeBillTime(finReconResult.getTransactionTime());//制单日期
		finGLInterface.setSummary("代景域收"+finReconResult.getOrderId());//摘要
		
		finGLInterface.setBorrowerAmount(Float.valueOf(finReconResult.getBankAmount()));
		finGLInterface.setLenderAmount(Float.valueOf(finReconResult.getBankAmount()));
		finGLInterface.setProofType("XL-JTYH");
		//TODO 如何处理这种情况？
		finGLInterface.setBorrowerSubjectCode("12210328");//借方科目编码
		finGLInterface.setBorrowerSubjectName("其他应收款-交行POS机");//借方科目名称
		//查询贷方科目编码
		finGLInterface.setLenderSubjectCode("22413001");//贷方科目编码
		finGLInterface.setLenderSubjectName("其他应付款-景域");
		finGLInterface.setAccountType(Constant.FIN_GL_ACCOUNT_TYPE.INSTEAD_INCOME_POS.getCode());
		finGLInterface.setBatchNoCust(finGLInterface.getTickedNo()+"_"+finGLInterface.getProofType());//批量合并号
		if(isJoyu){
			finGLInterface.setSummary("代兴旅("+Constant.FILIALE_NAME.getCnName(filialeName)+")收"+finReconResult.getOrderId());//摘要
			finGLInterface.setBorrowerSubjectName("其他应付款-兴旅");//借方科目名称
			FinGlSubjectCfg cfg=finGLServiceHelper.getLendSubjectCode(FIN_GL_ACCOUNT_TYPE.INSTEAD_INCOME_POS,filialeName);
			finGLInterface.setBorrowerSubjectCode(cfg.getBorrowSubjectCode());//借方科目编码
			finGLInterface.setLenderSubjectCode(cfg.getLendSubjectCode());//贷方科目编码
			finGLInterface.setAccountBookId(finGLServiceHelper.getAccountBookIdByFilialeName("JOYU"));//帐套号
		}
		FinGLInterfaceResult finGLInterfaceResult = new FinGLInterfaceResult();
		finGLInterfaceResult.setFinGLInterface(finGLInterface);
		finGLInterfaceResult.setSuccess(Boolean.TRUE);
		loger.info("##generateGLInterfaceForInsteadOfAccounts:--"+finGLInterface);
		return finGLInterfaceResult;
	}
	/**
	 * 生成总账数据
	 * @param finBizItem
	 * @param finGLInterfaceResult
	 */
	private void createGLData(FinBizItem finBizItem, FinGLInterfaceResult finGLInterfaceResult) { 
		if (finGLInterfaceResult != null && finGLInterfaceResult.isSuccess()) { 
			if(finGLInterfaceResult.hasData){ 
				finGLInterfaceDAO.insert(finGLInterfaceResult.getFinGLInterface()); 
			} 
		
			FinReconResult finReconResultDb=finReconResultDAO.selectByReconResultId(finBizItem.getReconResultId()); 
			if(finReconResultDb!=null){ 
				finReconResultDb.setGlStatus(Constant.GL_STATUS.POSTED.name()); 
				finReconResultDb.setGlTime(new Date()); 
				finReconResultDAO.updateGLStatus(finReconResultDb); 
			} 
		
			// 成功后更新记账状态 
			FinBizItem finBizItemDb = finBizItemDAO.findFinBizItemDOByPrimaryKey(finBizItem.getBizItemId()); 
			if(finBizItemDb!=null){ 
				finBizItemDb.setGlStatus(Constant.GL_STATUS.POSTED.name()); 
				finBizItemDb.setGlTime(new Date()); 
				if(null!=finReconResultDb){ 
					finBizItemDb.setMemo(finReconResultDb.getMemo()); 
				} 
				finBizItemDAO.updateFinBizItemDO(finBizItemDb); 
			} 
		
		} else { 
		
			FinReconResult finReconResultDb=finReconResultDAO.selectByReconResultId(finBizItem.getReconResultId()); 
			if(finReconResultDb!=null){ 
				finReconResultDb.setGlStatus(Constant.GL_STATUS.FAILED.name()); 
				finReconResultDb.setGlTime(new Date()); 
				finReconResultDAO.updateGLStatus(finReconResultDb); 
			} 
		
			FinBizItem finBizItemDb = finBizItemDAO.findFinBizItemDOByPrimaryKey(finBizItem.getBizItemId()); 
			if(finBizItemDb!=null){ 
				finBizItem.setGlStatus(Constant.GL_STATUS.FAILED.name()); 
				finBizItem.setGlTime(new Date()); 
				if(null!=finReconResultDb){ 
					finBizItemDb.setMemo(finReconResultDb.getMemo()); 
				} 
				finBizItemDAO.updateFinBizItemDO(finBizItem); 
			} 
	
		} 
	}

	@Override
	public void generateGLData() {
		generateGLData(Boolean.FALSE);
	}
	@Override
	public void generateGLDataBeforeCleanOldData(){
		generateGLData(Boolean.TRUE);
	}
	public void generateGLData(boolean isClean) {
		// 获取出账集合
		List<FinBizItem> finReconResultList = new ArrayList<FinBizItem>();
		transferOldOrderIdMap = new HashMap<Long, Long>();
		Map<String,Object> deleteParameters = new HashMap<String,Object>();
		do {
			loger.info("begin generate finance data!");
			finReconResultList = finBizItemDAO.selectBizItemListForBatch();
			for (FinBizItem reconResult : finReconResultList) {
				if(isClean){
					deleteParameters.put("reconResultId", reconResult.getReconResultId());
					finGLInterfaceDAO.delete(deleteParameters);
					finBizItemDAO.deleteFinBizItemDOByPrimaryKey(reconResult.getBizItemId());
				}
				if(null==reconResult.getTransactionTime()){
					if(null==reconResult.getCallbackTime()){
						reconResult.setTransactionTime(reconResult.getBankReconTime());
					}else{
						reconResult.setTransactionTime(reconResult.getCallbackTime());
					}
				}
				this.generateGLInterfaceByReconResult(reconResult);
			}
		} while (finReconResultList.size() > 0);
		generateGLInterfaceByOrderGlStatus();
	}
	
	
	public void setFinReconResultService(FinReconResultService finReconResultService) {
		this.finReconResultService = finReconResultService;
	}

	class FinGLInterfaceResult{
		private FinGLInterface finGLInterface;
		private boolean isSuccess = true;
		private boolean hasData = true;
		private String failedResult;
		public FinGLInterface getFinGLInterface() {
			return finGLInterface;
		}
		public void setFinGLInterface(FinGLInterface finGLInterface) {
			this.finGLInterface = finGLInterface;
		}
		public boolean isSuccess() {
			return isSuccess;
		}
		public void setSuccess(boolean isSuccess) {
			this.isSuccess = isSuccess;
		}
		public String getFailedResult() {
			return failedResult;
		}
		public void setFailedResult(String failedResult) {
			this.failedResult = failedResult;
		}
		public boolean isHasData() {
			return hasData;
		}
		public void setHasData(boolean hasData) {
			this.hasData = hasData;
		}
	}

	
}
