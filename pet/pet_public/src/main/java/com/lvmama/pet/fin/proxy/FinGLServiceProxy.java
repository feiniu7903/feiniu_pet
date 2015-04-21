package com.lvmama.pet.fin.proxy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.fin.FinGLInterface;
import com.lvmama.comm.pet.po.fin.FinGLInterfaceReq;
import com.lvmama.comm.pet.po.fin.FinGLInterfaceRsp;
import com.lvmama.comm.pet.po.fin.FinGLSubjectConfig;
import com.lvmama.comm.pet.po.fin.FinGlSubjectCfg;
import com.lvmama.comm.pet.po.fin.FinBizItem.BIZ_STATUS;
import com.lvmama.comm.pet.service.fin.FinBizItemService;
import com.lvmama.comm.pet.service.fin.FinGLBizService;
import com.lvmama.comm.pet.service.fin.FinGLService;
import com.lvmama.comm.pet.service.fin.FinGLSubjectConfigService;
import com.lvmama.comm.pet.service.fin.FinGlSubjectCfgService;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.HttpsUtil.HttpResponseWrapper;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.FIN_GL_ACCOUNT_TYPE;
import com.lvmama.comm.vo.Constant.FIN_SUBJECT_TYPE;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class FinGLServiceProxy implements FinGLBizService {
	private static Log log = LogFactory.getLog(FinGLServiceProxy.class);

	/**
	 * 对账接口
	 */
	protected FinGLService finGLService;
	
	private FinBizItemService finBizItemService;
	
	private FinGlSubjectCfgService finGlSubjectCfgService;
	
	private FinGLSubjectConfigService finGLSubjectConfigService;

	protected XStream xStream = null;

	protected  String contentType = "text/xml; charset=UTF-8";

	protected  String requestCharacter = "UTF-8";

	// 入账成功类型
	protected  final String STATUS_SUCCESS = "success";
	// 入账失败类型
	protected  final String STATUS_FAIL = "fail";
	public static Object LOCKMEMCACHED = new Object();
	public static final String KEY = "FIN_GL_INTERFACE_SEND_LOCK";
	/**
	 * 发送对账结果操作
	 * 
	 * @param httpResponseWrapper
	 */
	public void send() {
		HttpResponseWrapper httpResponseWrapper = null;
		try {
			if(isOnDoingMemCached(KEY)){
				return;
			}
			// 初始化XStream
			if (null == xStream) {
				xStream = new XStream(new DomDriver("UTF-8"));
				xStream.processAnnotations(FinGLInterfaceReq.class);
				xStream.processAnnotations(FinGLInterfaceRsp.class);
			}

			// 获取出账集合
			List<FinGLInterface> fullFinList = new ArrayList<FinGLInterface>();
			int count =0;
			do {
				String batchNo=null;
				String responseStr=null;
				try{
					Map<String,Object> params =new HashMap<String,Object>();
					params.put("end", 500);
					fullFinList = finGLService.queryByBatch(params);
					count=fullFinList.size();
					if (count > 0) {
						Collections.sort(fullFinList, new FinGLInterfaceComparator());
						do{
							List<Long> reconResultIdList=new ArrayList<Long>();
							Map<Long, FinGLInterface> glInterFaceMap=new HashMap<Long, FinGLInterface>();
							try{
								List<FinGLInterface> finList = getBatchList(fullFinList);
								for (FinGLInterface finGLInterface : finList) {
									reconResultIdList.add(finGLInterface.getReconResultId());
									glInterFaceMap.put(finGLInterface.getGlInterfaceId(), finGLInterface);
								}
								// 更新状态
								Map<String, Object> paraMap = new HashMap<String, Object>();
								// 序列化对象
								FinGLInterfaceReq req = new FinGLInterfaceReq();
								batchNo = finList.get(0).getBatchNo();
								req.addList(finList);
								String xmlStr = xStream.toXML(req);
								StringBuffer sendStr = new StringBuffer();
								sendStr.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
								sendStr.append(xmlStr);
			
								// 发送报文
								Constant constant = Constant.getInstance();
								Map<String, String> map = new HashMap<String, String>();
								map.put("content", sendStr.toString().replace("__", "_"));
								log.info("batchNo="+batchNo+" subsize="+finList.size()+" size="+fullFinList.size());
								httpResponseWrapper = HttpsUtil.requestPostFormResponse(
										constant.getValue("u8.interface.url"), map,requestCharacter);
								responseStr = httpResponseWrapper.getResponseString(requestCharacter);
								log.info(responseStr);
								// 解析返回报文
								FinGLInterfaceRsp rst = (FinGLInterfaceRsp) xStream.fromXML(responseStr.toString());
								httpResponseWrapper.close();
								if (null != rst && null != rst.getItem()&& rst.getItem().size() > 0) {
									parseXml(rst,paraMap,glInterFaceMap);
								}
							}catch(Exception e){
								log.error(e);
								// 更新状态
								Map<String, Object> paraMap = new HashMap<String, Object>();
								paraMap.put("batchNo", batchNo);
								paraMap.remove("errorFlag");
								paraMap.put("receivablesStatus", STATUS_FAIL);
								String receivablesResultMsg = e.getMessage() + responseStr;
								if(receivablesResultMsg.length()>500){
									receivablesResultMsg = receivablesResultMsg.substring(0, 500);
								}
								paraMap.put("receivablesResult", receivablesResultMsg);
								finGLService.updateStatus(paraMap);
								//更新财务流水
								finBizItemService.batchUpdateFinBizItemStatus(BIZ_STATUS.FAIL, reconResultIdList);
							}
						}while(fullFinList.size()>0);
					}
				}catch(Exception e){
					log.error(e);
					// 更新状态
					Map<String, Object> paraMap = new HashMap<String, Object>();
					paraMap.put("errorFlag", true);
					paraMap.put("receivablesStatus", STATUS_FAIL);
					String receivablesResultMsg = e.getMessage();
					if(receivablesResultMsg.length()>500){
						receivablesResultMsg = receivablesResultMsg.substring(0, 500);
					}
					paraMap.put("receivablesResult", receivablesResultMsg);
					finGLService.updateStatus(paraMap);
				}
			} while (count > 0);
		} catch (Exception e) {
			log.error(e);
			// 更新状态
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("errorFlag", true);
			paraMap.put("receivablesStatus", STATUS_FAIL);
			String receivablesResultMsg = e.getMessage();
			if(receivablesResultMsg.length()>500){
				receivablesResultMsg = receivablesResultMsg.substring(0, 500);
			}
			paraMap.put("receivablesResult", receivablesResultMsg);
			finGLService.updateStatus(paraMap);
			return;
		} finally {
			if (null != httpResponseWrapper) {
				httpResponseWrapper.close();
			}
			releaseMemCached(KEY);
		}
	}

	public void parseXml(final FinGLInterfaceRsp rst,final Map<String,Object> paraMap,Map<Long, FinGLInterface> glInterFaceMap){

		// 判断一个批次里是不是都成功，有一条失败，就通通不入库
		boolean successFlag = true;
		for (int i = 0; i < rst.getItem().size(); i++) {
			String statusCode = rst.getItem().get(i).getSucceed();
			if (!"0".equals(statusCode)) {
				successFlag = false;
				break;
			}
		}

		for (int i = 0; i < rst.getItem().size(); i++) {
			// 主键id
			String id = rst.getItem().get(i).getKey();
			// 返回状态
			String statusCode = rst.getItem().get(i).getSucceed();
			// 返回状态描述
			String statusDesc = rst.getItem().get(i).getDsc();
			String inoId = rst.getItem().get(i).getInoId();

			// 更新状态
			paraMap.put("id", id);
			if ("0".equals(statusCode)) {
				if (successFlag) {
					paraMap.put("receivablesStatus",STATUS_SUCCESS);
				} else {
					// 有一条失败的情况下，成功的数据也更新为失败
					paraMap.put("receivablesStatus",STATUS_FAIL);
					statusDesc = "同批次号其它数据有问题";
				}
				FinGLInterface finGLInterface=glInterFaceMap.get(Long.valueOf(id));
				if(null!=finGLInterface){
					//更新财务流水
					List<Long> ids=new ArrayList<Long>();
					ids.add(finGLInterface.getReconResultId());
					finBizItemService.batchUpdateFinBizItemStatus(BIZ_STATUS.POST, ids);
				}
			} else {
				paraMap.put("receivablesStatus", STATUS_FAIL);
				FinGLInterface finGLInterface=glInterFaceMap.get(Long.valueOf(id));
				if(null!=finGLInterface){
					//更新财务流水
					List<Long> ids=new ArrayList<Long>();
					ids.add(finGLInterface.getReconResultId());
					finBizItemService.batchUpdateFinBizItemStatus(BIZ_STATUS.FAIL, ids);
				}
			}
			paraMap.put("receivablesResult", statusDesc);
			paraMap.put("inoId", inoId);
			finGLService.updateStatus(paraMap);
		}
	
	}
	protected List<FinGLInterface> getBatchList(final List<FinGLInterface> list){
		List<FinGLInterface> subList =  new ArrayList<FinGLInterface>();
		if(list.isEmpty()){
			return subList;
		}
		String batchNo = list.get(0).getBatchNo();
		for(int i=0;i<list.size();i++){
			if(batchNo.equals(list.get(i).getBatchNo())){
				subList.add(list.get(i));
				list.remove(i);
				i--;
			}else{
				break;
			}
		}
		return subList;
	}
	
	public static boolean isOnDoingMemCached(String key) {
		synchronized(LOCKMEMCACHED){
			if(MemcachedUtil.getInstance().get(key) !=null){
				return true;			
			}else{
				//缓存设置有效时间为5分钟
				if(!MemcachedUtil.getInstance().set(key, 10*60*60, key)){
					log.error("请检查MemCached服务器或相应的配置文件！");
				}
				return false;
			}
		}
	}
	
	public static void releaseMemCached(String key) {
		synchronized(LOCKMEMCACHED){
			if(!MemcachedUtil.getInstance().remove(key)){
				log.error("请检查MemCached服务器或相应的配置文件！");
			}
		}
	}
	public void receive(final String fileStr){
		
	}
	protected class FinGLInterfaceComparator implements Comparator{

		@Override
		public int compare(Object o1, Object o2) {
			FinGLInterface p0=(FinGLInterface)o1;  
			FinGLInterface p1=(FinGLInterface)o2;  
			int flag=p0.getBatchNo().compareTo(p1.getBatchNo());
			return flag;
		}
	}
	public FinGLService getFinGLService() {
		return finGLService;
	}

	public void setFinGLService(FinGLService finGLService) {
		this.finGLService = finGLService;
	}

	public void setFinBizItemService(FinBizItemService finBizItemService) {
		this.finBizItemService = finBizItemService;
	}

	public void setFinGlSubjectCfgService(
			FinGlSubjectCfgService finGlSubjectCfgService) {
		this.finGlSubjectCfgService = finGlSubjectCfgService;
	}

	public void setFinGLSubjectConfigService(
			FinGLSubjectConfigService finGLSubjectConfigService) {
		this.finGLSubjectConfigService = finGLSubjectConfigService;
	}

	@Override
	public void initCode() {
		
		//删除全部配置
		finGlSubjectCfgService.deleteAllCfg();
		
		initINSTEAD_INCOME();
		
		initBOOKING_INCOME();
		
		initGUEST_DRAWCASH();
		
		initCASH_ACCOUNT_RECHARGE();
		
		initNORMAL_DRAWCASH();
		
		initCANCEL_TO_CREATE_NEW();
		
		initREFUNDMENT();
		
		initCOMPENSATION();
		
		initBOOKING_INCOME_PARTPAY();
		
		initINSTEAD_INCOME_POS();
		
		initREFUND_FEE();
		
		initREFUNDMENT_ADVANCE();
		
		initDETERMINE_INCOME();
	}
	
	
	private void initREFUNDMENT_ADVANCE(){
		//做两条数据
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("subjectType", FIN_SUBJECT_TYPE.DETERMINE_LENDER_SUBJECT.getCode());
		List<FinGLSubjectConfig> configList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(paramMap);
		for (FinGLSubjectConfig finGLSubjectConfig : configList) {
			FinGlSubjectCfg cfg=new FinGlSubjectCfg();
			cfg.setAccountType(FIN_GL_ACCOUNT_TYPE.REFUNDMENT_ADVANCE.getCode());
			cfg.setLendSubjectCode(finGLSubjectConfig.getSubjectCode());//贷方科目
			cfg.setLendConfig1(finGLSubjectConfig.getConfig1());//产品类型
			cfg.setLendConfig2(finGLSubjectConfig.getConfig2());//产品子类
			cfg.setLendConfig3(finGLSubjectConfig.getConfig3());//是否实体票 true:实体票 false：非实体
			cfg.setLendConfig4(finGLSubjectConfig.getConfig4());//是否为境外 Y:境外 N:境内
			cfg.setLendConfig5(finGLSubjectConfig.getConfig5());//所属区域
			finGlSubjectCfgService.insertFinGlSubjectCfg(cfg);
		}
		
		Map<String, Object> secondParamMap=new HashMap<String, Object>();
		secondParamMap.put("subjectType", FIN_SUBJECT_TYPE.PRE_PAYMENT_LENDER_SUBJECT.getCode());
		List<FinGLSubjectConfig> secondConfigList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(secondParamMap);
		for (FinGLSubjectConfig finGLSubjectConfig : secondConfigList) {
			FinGlSubjectCfg cfg=new FinGlSubjectCfg();
			cfg.setAccountType(FIN_GL_ACCOUNT_TYPE.REFUNDMENT_ADVANCE.getCode());
			cfg.setLendSubjectCode(finGLSubjectConfig.getSubjectCode());//贷方科目
			cfg.setLendConfig1(finGLSubjectConfig.getConfig1());//产品类型
			cfg.setLendConfig2(finGLSubjectConfig.getConfig2());//产品子类
			cfg.setLendConfig3(finGLSubjectConfig.getConfig3());//是否实体票 true:实体票 false：非实体
			cfg.setLendConfig4(finGLSubjectConfig.getConfig4());//是否为境外 Y:境外 N:境内
			cfg.setLendConfig5(finGLSubjectConfig.getConfig5());//所属区域
			finGlSubjectCfgService.insertFinGlSubjectCfg(cfg);
		}
	}
	
	private void initREFUND_FEE(){
		//做两条数据
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("subjectType", FIN_SUBJECT_TYPE.PAYMENT_BORROWER_SUBJECT.getCode());
		List<FinGLSubjectConfig> configList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(paramMap);
		for (FinGLSubjectConfig finGLSubjectConfig : configList) {
			FinGlSubjectCfg cfg=new FinGlSubjectCfg();
			cfg.setAccountType(FIN_GL_ACCOUNT_TYPE.REFUND_FEE.getCode());
			cfg.setBorrowSubjectCode(finGLSubjectConfig.getSubjectCode());//借方科目
			cfg.setConfig6(finGLSubjectConfig.getConfig1());//借方支付网关
			finGlSubjectCfgService.insertFinGlSubjectCfg(cfg);
		}
		
		Map<String, Object> secondParamMap=new HashMap<String, Object>();
		secondParamMap.put("subjectType", FIN_SUBJECT_TYPE.PAY_FEE_BORROWER_SUBJECT.getCode());
		List<FinGLSubjectConfig> secondConfigList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(secondParamMap);
		for (FinGLSubjectConfig finGLSubjectConfig : secondConfigList) {
			FinGlSubjectCfg cfg=new FinGlSubjectCfg();
			cfg.setAccountType(FIN_GL_ACCOUNT_TYPE.REFUND_FEE.getCode());
			cfg.setBorrowSubjectCode(finGLSubjectConfig.getSubjectCode());//借方科目
			cfg.setConfig6(finGLSubjectConfig.getConfig1());//借方支付网关
			finGlSubjectCfgService.insertFinGlSubjectCfg(cfg);
		}
	}
	
	
	private void initINSTEAD_INCOME_POS(){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("subjectType", FIN_SUBJECT_TYPE.PAYMENT_LENDER_SUBJECT.getCode());
		List<FinGLSubjectConfig> configList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(paramMap);
		for (FinGLSubjectConfig finGLSubjectConfig : configList) {
			FinGlSubjectCfg cfg=new FinGlSubjectCfg();
			cfg.setAccountType(FIN_GL_ACCOUNT_TYPE.INSTEAD_INCOME_POS.getCode());
			cfg.setBorrowSubjectCode(finGLSubjectConfig.getSubjectCode());//借方科目
			cfg.setConfig7(finGLSubjectConfig.getConfig1());//所属公司
			cfg.setLendSubjectCode("22413004");//贷方科目
			finGlSubjectCfgService.insertFinGlSubjectCfg(cfg);
		}
	}
	
	
	private void initBOOKING_INCOME_PARTPAY(){
		FinGlSubjectCfg cfg=new FinGlSubjectCfg();
		cfg.setAccountType(FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME_PARTPAY.getCode());
		cfg.setBorrowSubjectCode("22413001");//借方科目
		cfg.setLendSubjectCode("220316");//贷方科目
		finGlSubjectCfgService.insertFinGlSubjectCfg(cfg);
	}
	
	private void initCOMPENSATION(){
		FinGlSubjectCfg cfg=new FinGlSubjectCfg();
		cfg.setAccountType(FIN_GL_ACCOUNT_TYPE.COMPENSATION.getCode());
		cfg.setBorrowSubjectCode("122106");//借方科目
		cfg.setLendSubjectCode("22413001");//贷方科目
		finGlSubjectCfgService.insertFinGlSubjectCfg(cfg);
	}
	
	
	private void initDETERMINE_INCOME(){
		
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("subjectType", FIN_SUBJECT_TYPE.PRE_PAYMENT_LENDER_SUBJECT.getCode());
		List<FinGLSubjectConfig> configList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(paramMap);
		
		Map<String, Object> lendParamMap=new HashMap<String, Object>();
		lendParamMap.put("subjectType", FIN_SUBJECT_TYPE.DETERMINE_LENDER_SUBJECT.getCode());
		List<FinGLSubjectConfig> lendConfigList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(lendParamMap);
		
		for (FinGLSubjectConfig finGLSubjectConfig : configList) {
			
			for (FinGLSubjectConfig finGLSubjectConfig2 : lendConfigList) {
				FinGlSubjectCfg cfg=new FinGlSubjectCfg();
				cfg.setAccountType(FIN_GL_ACCOUNT_TYPE.DETERMINE_INCOME.getCode());
				cfg.setBorrowSubjectCode(finGLSubjectConfig.getSubjectCode());//借方科目
				cfg.setConfig1(finGLSubjectConfig.getConfig1());//产品类型
				cfg.setConfig2(finGLSubjectConfig.getConfig2());//产品子类
				cfg.setConfig3(finGLSubjectConfig.getConfig3());//是否实体票 true:实体票 false：非实体
				cfg.setConfig4(finGLSubjectConfig.getConfig4());//是否为境外 Y:境外 N:境内
				cfg.setConfig7(finGLSubjectConfig.getConfig5());//所属公司
				
				cfg.setLendSubjectCode(finGLSubjectConfig2.getSubjectCode());//贷方科目
				cfg.setLendConfig1(finGLSubjectConfig2.getConfig1());//产品类型
				cfg.setLendConfig2(finGLSubjectConfig2.getConfig2());//产品子类
				cfg.setLendConfig3(finGLSubjectConfig2.getConfig3());//是否实体票 true:实体票 false：非实体
				cfg.setLendConfig4(finGLSubjectConfig2.getConfig4());//是否为境外 Y:境外 N:境内
				cfg.setLendConfig7(finGLSubjectConfig2.getConfig5());//所属公司
				
				finGlSubjectCfgService.insertFinGlSubjectCfg(cfg);
			}
		}
	}
	
	
	
	private void initREFUNDMENT(){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("subjectType", FIN_SUBJECT_TYPE.INSTEADREFUND_BORROWER_SUBJECT.getCode());
		List<FinGLSubjectConfig> configList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(paramMap);
		
		Map<String, Object> lendParamMap=new HashMap<String, Object>();
		lendParamMap.put("subjectType", FIN_SUBJECT_TYPE.PAYMENT_BORROWER_SUBJECT.getCode());
		List<FinGLSubjectConfig> lendConfigList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(lendParamMap);
		
		for (FinGLSubjectConfig finGLSubjectConfig : configList) {
			for (FinGLSubjectConfig finGLSubjectConfig2 : lendConfigList) {
				FinGlSubjectCfg cfg=new FinGlSubjectCfg();
				cfg.setAccountType(FIN_GL_ACCOUNT_TYPE.REFUNDMENT.getCode());
				cfg.setBorrowSubjectCode(finGLSubjectConfig.getSubjectCode());//借方科目
				cfg.setConfig7(finGLSubjectConfig.getConfig1());//借方所属公司
				cfg.setLendSubjectCode(finGLSubjectConfig2.getSubjectCode());//借方科目
				cfg.setLendConfig6(finGLSubjectConfig2.getConfig1());//贷方支付网关
				finGlSubjectCfgService.insertFinGlSubjectCfg(cfg);
			}
		}
	}
	
	
	private void initCANCEL_TO_CREATE_NEW(){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("subjectType", FIN_SUBJECT_TYPE.PAYMENT_LENDER_SUBJECT.getCode());
		List<FinGLSubjectConfig> configList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(paramMap);
		for (FinGLSubjectConfig finGLSubjectConfig : configList) {
			FinGlSubjectCfg cfg=new FinGlSubjectCfg();
			cfg.setAccountType(FIN_GL_ACCOUNT_TYPE.CANCEL_TO_CREATE_NEW.getCode());
			cfg.setLendConfig7(finGLSubjectConfig.getConfig1());//贷方所属公司
			cfg.setLendSubjectCode(finGLSubjectConfig.getSubjectCode());//贷方科目
			finGlSubjectCfgService.insertFinGlSubjectCfg(cfg);
		}
	}
	
	
	
	private void initNORMAL_DRAWCASH(){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("subjectType", FIN_SUBJECT_TYPE.NORMALDRAWCASH_BORROWER_SUBJECT.getCode());
		List<FinGLSubjectConfig> configList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(paramMap);
		
		Map<String, Object> lendParamMap=new HashMap<String, Object>();
		lendParamMap.put("subjectType", FIN_SUBJECT_TYPE.PAYMENT_BORROWER_SUBJECT.getCode());
		List<FinGLSubjectConfig> lendConfigList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(lendParamMap);
		
		for (FinGLSubjectConfig finGLSubjectConfig : configList) {
			for (FinGLSubjectConfig finGLSubjectConfig2 : lendConfigList) {
				FinGlSubjectCfg cfg=new FinGlSubjectCfg();
				cfg.setAccountType(FIN_GL_ACCOUNT_TYPE.NORMAL_DRAWCASH.getCode());
				cfg.setBorrowSubjectCode(finGLSubjectConfig.getSubjectCode());//借方科目编码
				cfg.setConfig6(finGLSubjectConfig.getConfig1());//借方支付网关
				cfg.setLendConfig6(finGLSubjectConfig2.getConfig1());//贷方支付网关
				cfg.setLendSubjectCode(finGLSubjectConfig2.getSubjectCode());//贷方科目
				finGlSubjectCfgService.insertFinGlSubjectCfg(cfg);
			}
		}
	}
	
	
	
	
	/**
	 * 存款账户充值
	 */
	private void initCASH_ACCOUNT_RECHARGE(){
		Map<String, Object> lenderParamMap = new HashMap<String, Object>();
		lenderParamMap.put("subjectType", FIN_SUBJECT_TYPE.PAYMENT_BORROWER_SUBJECT.name());
		List<FinGLSubjectConfig> configList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(lenderParamMap);
		for (FinGLSubjectConfig finGLSubjectConfig : configList) {
			FinGlSubjectCfg cfg=new FinGlSubjectCfg();
			cfg.setAccountType(FIN_GL_ACCOUNT_TYPE.CASH_ACCOUNT_RECHARGE.getCode());
			cfg.setConfig6(finGLSubjectConfig.getConfig1());//支付网关
			cfg.setBorrowSubjectCode(finGLSubjectConfig.getSubjectCode());//借方科目
			cfg.setLendSubjectCode("220312");//贷方科目编码,固定的,虚拟账户
			finGlSubjectCfgService.insertFinGlSubjectCfg(cfg);
		}
	}
	
	
	
	/**
	 * 游客提现
	 */
	private void initGUEST_DRAWCASH(){
		Map<String, Object> lenderParamMap = new HashMap<String, Object>();
		lenderParamMap.put("subjectType", FIN_SUBJECT_TYPE.PAYMENT_BORROWER_SUBJECT.name());
		List<FinGLSubjectConfig> configList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(lenderParamMap);
		for (FinGLSubjectConfig finGLSubjectConfig : configList) {
			FinGlSubjectCfg cfg=new FinGlSubjectCfg();
			cfg.setAccountType(FIN_GL_ACCOUNT_TYPE.GUEST_DRAWCASH.getCode());
			cfg.setBorrowSubjectCode("220312");//借方科目编码,固定的,虚拟账户
			cfg.setLendConfig6(finGLSubjectConfig.getConfig1());//支付网关
			cfg.setLendSubjectCode(finGLSubjectConfig.getSubjectCode());//贷方科目
			finGlSubjectCfgService.insertFinGlSubjectCfg(cfg);
		}
	}
	
	
	/**
	 * 预收
	 */
	private void initBOOKING_INCOME(){
		Map<String, Object> lenderParamMap = new HashMap<String, Object>();
		lenderParamMap.put("subjectType", FIN_SUBJECT_TYPE.PRE_PAYMENT_LENDER_SUBJECT.name());
		List<FinGLSubjectConfig> configList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(lenderParamMap);
		for (FinGLSubjectConfig finGLSubjectConfig : configList) {
			FinGlSubjectCfg cfg=new FinGlSubjectCfg();
			cfg.setAccountType(FIN_GL_ACCOUNT_TYPE.BOOKING_INCOME.getCode());
			cfg.setBorrowSubjectCode("22413001");//借方科目固定
			cfg.setLendConfig1(finGLSubjectConfig.getConfig1());//产品类型
			cfg.setLendConfig2(finGLSubjectConfig.getConfig2());//产品子类型
			cfg.setLendConfig3(finGLSubjectConfig.getConfig3());//是否实体票 true:实体票 false：非实体
			cfg.setLendConfig4(finGLSubjectConfig.getConfig4());//是否为境外 Y:境外 N:境内
			cfg.setLendConfig5(finGLSubjectConfig.getConfig5());//所属区域
			cfg.setLendSubjectCode(finGLSubjectConfig.getSubjectCode());//贷方科目
			finGlSubjectCfgService.insertFinGlSubjectCfg(cfg);
		}
	}

	/**
	 * 代收
	 */
	private void initINSTEAD_INCOME() {
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("subjectType", FIN_SUBJECT_TYPE.PAYMENT_BORROWER_SUBJECT.getCode());
		List<FinGLSubjectConfig> configList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(paramMap);
		
		Map<String, Object> lParamMap=new HashMap<String, Object>();
		lParamMap.put("subjectType", FIN_SUBJECT_TYPE.PAYMENT_LENDER_SUBJECT.getCode());
		List<FinGLSubjectConfig> lConfigList=finGLSubjectConfigService.selectFinGLSubjectConfigByParamMap(lParamMap);
		
		for (FinGLSubjectConfig finGLSubjectConfig : configList) {
			for (FinGLSubjectConfig finGLSubjectConfig2 : lConfigList) {
				FinGlSubjectCfg cfg=new FinGlSubjectCfg();
				cfg.setConfig6(finGLSubjectConfig.getConfig1());//借：对账网关
				cfg.setBorrowSubjectCode(finGLSubjectConfig.getSubjectCode());
				cfg.setLendConfig7(finGLSubjectConfig2.getConfig1());//代：所属公司
				cfg.setLendSubjectCode(finGLSubjectConfig2.getSubjectCode());
				cfg.setAccountType(FIN_GL_ACCOUNT_TYPE.INSTEAD_INCOME.getCode());
				finGlSubjectCfgService.insertFinGlSubjectCfg(cfg);
			}
		}
	}

	

}
