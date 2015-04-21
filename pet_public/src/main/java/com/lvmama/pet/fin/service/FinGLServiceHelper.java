package com.lvmama.pet.fin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.lvmama.comm.pet.po.fin.FinGLInterfaceDTO;
import com.lvmama.comm.pet.po.fin.FinGLSubjectConfig;
import com.lvmama.comm.pet.po.fin.FinGlSubjectCfg;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.FIN_GL_ACCOUNT_TYPE;
import com.lvmama.comm.vo.Constant.FIN_SUBJECT_TYPE;
import com.lvmama.pet.fin.dao.FinGLSubjectConfigDAO;
import com.lvmama.pet.fin.dao.FinGlSubjectCfgDAO;

/**
 * 财务做账助手类
 * 
 * @author taiqichao
 * 
 */
@Component
public class FinGLServiceHelper {

	private static final Log LOG = LogFactory.getLog(FinGLServiceHelper.class);

	private static final String WL="WL";
	private static final String FDCX="FDCX";
	private static final String ZFB="ZFB";
	private static final String SRCB="SRCB";
	private static final String NBYH="NBYH";
	
	@Autowired
	@Qualifier("finGLSubjectConfigDAO")
	private FinGLSubjectConfigDAO finGLSubjectConfigDAO;
	
	@Autowired
	@Qualifier("finGlSubjectCfgDAO")
	private FinGlSubjectCfgDAO finGlSubjectCfgDAO;
	
	
	/**
	 * 获取科目配置
	 * @param accountType 做账类别
	 * @param gateway 借方支付网关
	 * @param filialeName 贷方所属公司
	 * @return
	 */
	public FinGlSubjectCfg getSubjectCode(FIN_GL_ACCOUNT_TYPE accountType,String gateway,String filialeName){
		FinGlSubjectCfg term=new FinGlSubjectCfg();
		term.setAccountType(accountType.getCode());
		term.setConfig6(gateway);
		term.setLendConfig7(filialeName);
		List<FinGlSubjectCfg> resultList=finGlSubjectCfgDAO.findListByTerm(term);
		if(null!=resultList&&resultList.size()>0){
			if(resultList.size()==1){
				return resultList.get(0);
			}
			String error = "Not only one subject code configuration,accountType:"+accountType.getCode()+" , gateway:"+gateway+",filiale name:"+filialeName;
			LOG.error(error);
			throw new RuntimeException(error);
		}
		String error = "Can't find  subject code configuration, accountType:"+accountType.getCode()+" , gateway:"+gateway+",filiale name:"+filialeName;
		LOG.error(error);
		throw new RuntimeException(error);
	}
	
	/**
	 * 获取科目配置
	 * @param accountType 做账类别
	 * @param filialeName 借方支付网关
	 * @param gateway 贷方所属公司
	 * @return
	 */
	public FinGlSubjectCfg getSubjectCodeWithFilialeNameAndGateWay(FIN_GL_ACCOUNT_TYPE accountType,String filialeName,String gateway){
		FinGlSubjectCfg term=new FinGlSubjectCfg();
		term.setAccountType(accountType.getCode());
		term.setConfig7(filialeName);
		term.setLendConfig6(gateway);
		List<FinGlSubjectCfg> resultList=finGlSubjectCfgDAO.findListByTerm(term);
		if(null!=resultList&&resultList.size()>0){
			if(resultList.size()==1){
				return resultList.get(0);
			}
			String error = "Not only one subject code configuration,accountType:"+accountType.getCode()+" , gateway:"+gateway+",filiale name:"+filialeName;
			LOG.error(error);
			throw new RuntimeException(error);
		}
		String error = "Can't find  subject code configuration, accountType:"+accountType.getCode()+" , gateway:"+gateway+",filiale name:"+filialeName;
		LOG.error(error);
		throw new RuntimeException(error);
	}
	
	
	
	/**
	 * 获取科目配置
	 * @param accountType 做账类别
	 * @param filialeName 所属公司
	 * @return
	 */
	public FinGlSubjectCfg getLendSubjectCode(FIN_GL_ACCOUNT_TYPE accountType,String filialeName){
		FinGlSubjectCfg term=new FinGlSubjectCfg();
		term.setAccountType(accountType.getCode());
		term.setLendConfig7(filialeName);
		List<FinGlSubjectCfg> resultList=finGlSubjectCfgDAO.findListByTerm(term);
		if(null!=resultList&&resultList.size()>0){
			if(resultList.size()==1){
				return resultList.get(0);
			}
			String error = "Not only one subject code configuration,accountType:"+accountType.getCode()+",filiale name:"+filialeName;
			LOG.error(error);
			throw new RuntimeException(error);
		}
		String error = "Can't find  subject code configuration, accountType:"+accountType.getCode()+",filiale name:"+filialeName;
		LOG.error(error);
		throw new RuntimeException(error);
	}
	
	
	/**
	 * 获取科目配置
	 * @param accountType 做账类别
	 * @param lendGateway 贷方支付网关
	 * @return
	 */
	public FinGlSubjectCfg getSubjectCodeWithLendGateWay(FIN_GL_ACCOUNT_TYPE accountType,String lendGateway){
		FinGlSubjectCfg term=new FinGlSubjectCfg();
		term.setAccountType(accountType.getCode());
		term.setLendConfig6(lendGateway);
		List<FinGlSubjectCfg> resultList=finGlSubjectCfgDAO.findListByTerm(term);
		if(null!=resultList&&resultList.size()>0){
			if(resultList.size()==1){
				return resultList.get(0);
			}
			String error = "Not only one subject code configuration,accountType:"+accountType.getCode()+" , gateway:"+lendGateway;
			LOG.error(error);
			throw new RuntimeException(error);
		}
		String error = "Can't find  subject code configuration, accountType:"+accountType.getCode()+" , gateway:"+lendGateway;
		LOG.error(error);
		throw new RuntimeException(error);
	}
	
	/**
	 * 获取两个科目配置
	 * @param accountType
	 * @param gateway
	 * @return
	 */
	public List<FinGlSubjectCfg> getSubjectCodeRefundFeeWithGateWay(String gateway){
		FinGlSubjectCfg term=new FinGlSubjectCfg();
		term.setAccountType(FIN_GL_ACCOUNT_TYPE.REFUND_FEE.getCode());
		term.setConfig6(gateway);
		List<FinGlSubjectCfg> resultList=finGlSubjectCfgDAO.findListByTerm(term);
		if(null!=resultList&&resultList.size()>0){
			if(resultList.size()==2){
				return resultList;
			}
			String error = "More than two subject codes configuration,accountType:"+term.getAccountType()+" , gateway:"+gateway;
			LOG.error(error);
			throw new RuntimeException(error);
		}
		String error = "Can't find  subject code configuration, accountType:"+term.getAccountType()+" , gateway:"+gateway;
		LOG.error(error);
		throw new RuntimeException(error);
	}
	
	
	
	
	
	/**
	 * 获取科目配置
	 * @param accountType 做账类别
	 * @param borrowGateway 借方支付网关
	 * @param lendGateway 借方支付网关
	 * @return
	 */
	public FinGlSubjectCfg getSubjectCodeByGateWay(FIN_GL_ACCOUNT_TYPE accountType,String borrowGateway,String lendGateway){
		FinGlSubjectCfg term=new FinGlSubjectCfg();
		term.setAccountType(accountType.getCode());
		term.setConfig6(borrowGateway);
		term.setLendConfig6(lendGateway);
		List<FinGlSubjectCfg> resultList=finGlSubjectCfgDAO.findListByTerm(term);
		if(null!=resultList&&resultList.size()>0){
			if(resultList.size()==1){
				return resultList.get(0);
			}
			String error = "Not only one subject code configuration,accountType:"+accountType.getCode()+" , gateway:"+borrowGateway;
			LOG.error(error);
			throw new RuntimeException(error);
		}
		String error = "Can't find  subject code configuration, accountType:"+accountType.getCode()+" , gateway:"+borrowGateway;
		LOG.error(error);
		throw new RuntimeException(error);
	}
	
	
	
	
	
	
	
	/**
	 * 获取科目配置
	 * @param accountType 做账类别
	 * @param borrowGateway 借方支付网关
	 * @return
	 */
	public FinGlSubjectCfg getSubjectCodeWithBorrowGateWay(FIN_GL_ACCOUNT_TYPE accountType,String borrowGateway){
		FinGlSubjectCfg term=new FinGlSubjectCfg();
		term.setAccountType(accountType.getCode());
		term.setConfig6(borrowGateway);
		List<FinGlSubjectCfg> resultList=finGlSubjectCfgDAO.findListByTerm(term);
		if(null!=resultList&&resultList.size()>0){
			if(resultList.size()==1){
				return resultList.get(0);
			}
			String error = "Not only one subject code configuration,accountType:"+accountType.getCode()+" , gateway:"+borrowGateway;
			LOG.error(error);
			throw new RuntimeException(error);
		}
		String error = "Can't find  subject code configuration, accountType:"+accountType.getCode()+" , gateway:"+borrowGateway;
		LOG.error(error);
		throw new RuntimeException(error);
	}
	
	
	
	
	
	/**
	 * 获取预收贷方科目/确认收入贷方科目
	 * @param accountType
	 * @param dto
	 * @return
	 */
	public FinGlSubjectCfg getSubjectCode(FIN_GL_ACCOUNT_TYPE accountType,FinGLInterfaceDTO dto){
		FinGlSubjectCfg term=new FinGlSubjectCfg();
		term.setAccountType(accountType.getCode());
		term.setLendConfig1(dto.getProductType());
		term.setLendConfig2(dto.getSubProductType());
		term.setLendConfig3(dto.getPhysical());
		term.setLendConfig4(dto.getIsForegin());
		term.setLendConfig5(dto.getRegionName());
		List<FinGlSubjectCfg> resultList=finGlSubjectCfgDAO.findListByTerm(term);
		if(null!=resultList&&resultList.size()>0){
			if(resultList.size()==1){
				return resultList.get(0);
			}
			String error = "Not only one subject code configuration,accountType:"+accountType.getCode();
			LOG.error(error);
			throw new RuntimeException(error);
		}
		String error = "Can't find  subject code configuration, accountType:"+accountType.getCode();
		LOG.error(error);
		throw new RuntimeException(error);
	}
	
	/**
	 * 获得两个科目(退款冲预收（违约金）)
	 * @param dto
	 * @return
	 */
	public List<FinGlSubjectCfg> getRefundmentAdvanceSubjectCode(FinGLInterfaceDTO dto){
		FinGlSubjectCfg term=new FinGlSubjectCfg();
		term.setAccountType(FIN_GL_ACCOUNT_TYPE.REFUNDMENT_ADVANCE.getCode());
		term.setLendConfig1(dto.getProductType());
		term.setLendConfig2(dto.getSubProductType());
		term.setLendConfig3(dto.getPhysical());
		term.setLendConfig4(dto.getIsForegin());
		term.setLendConfig5(dto.getRegionName());
		List<FinGlSubjectCfg> resultList=finGlSubjectCfgDAO.findListByTerm(term);
		if(null!=resultList&&resultList.size()>0){
			if(resultList.size()==2){
				return resultList;
			}
			String error = "More than two subject codes configuration,accountType:"+term.getAccountType();
			LOG.error(error);
			throw new RuntimeException(error);
		}
		String error = "Can't find  subject code configuration, accountType:"+term.getAccountType();
		LOG.error(error);
		throw new RuntimeException(error);
	}
	
	/**
	 * 获取预收贷方科目/确认收入贷方科目
	 * @param accountType
	 * @param borrowDto 借方
	 * @param lendDto 贷方
	 * @return
	 */
	public FinGlSubjectCfg getSubjectCode(FIN_GL_ACCOUNT_TYPE accountType,FinGLInterfaceDTO borrowDto,FinGLInterfaceDTO lendDto){
		FinGlSubjectCfg term=new FinGlSubjectCfg();
		term.setAccountType(accountType.getCode());
		term.setConfig1(borrowDto.getProductType());
		term.setConfig2(borrowDto.getSubProductType());
		term.setConfig3(borrowDto.getPhysical());
		term.setConfig4(borrowDto.getIsForegin());
		term.setConfig5(borrowDto.getRegionName());
		
		term.setLendConfig1(lendDto.getProductType());
		term.setLendConfig2(lendDto.getSubProductType());
		term.setLendConfig3(lendDto.getPhysical());
		term.setLendConfig4(lendDto.getIsForegin());
		term.setLendConfig5(lendDto.getRegionName());
		
		List<FinGlSubjectCfg> resultList=finGlSubjectCfgDAO.findListByTerm(term);
		if(null!=resultList&&resultList.size()>0){
			if(resultList.size()==1){
				return resultList.get(0);
			}
			String error = "Not only one subject code configuration,accountType:"+accountType.getCode();
			LOG.error(error);
			throw new RuntimeException(error);
		}
		String error = "Can't find  subject code configuration, accountType:"+accountType.getCode();
		LOG.error(error);
		throw new RuntimeException(error);
	}
	
	/**
	 * 获取科目配置
	 * @param accountType 做账类别
	 * @return
	 */
	public FinGlSubjectCfg getSubjectCode(FIN_GL_ACCOUNT_TYPE accountType){
		FinGlSubjectCfg term=new FinGlSubjectCfg();
		term.setAccountType(accountType.getCode());
		List<FinGlSubjectCfg> resultList=finGlSubjectCfgDAO.findListByTerm(term);
		if(null!=resultList&&resultList.size()>0){
			if(resultList.size()==1){
				return resultList.get(0);
			}
			String error = "Not only one subject code configuration,accountType:"+accountType.getCode();
			LOG.error(error);
			throw new RuntimeException(error);
		}
		String error = "Can't find  subject code configuration, accountType:"+accountType.getCode();
		LOG.error(error);
		throw new RuntimeException(error);
	}
	
	

	/**
	 * 获取科目
	 * 
	 * @param paramMap
	 *            参数map
	 * @return 科目编码
	 */
	public String getSubjectCode(Map<String, Object> paramMap) {
		String subjectCode = FinGLSubjectCodeCache.getInstance().get(paramMap);
		if(LOG.isDebugEnabled()){
			if(StringUtils.isNotBlank(subjectCode)){
				LOG.debug("Hit subject cache code:"+FinGLSubjectCodeCache.getInstance().keyBuilder(paramMap));
			}
		}
		if (StringUtils.isBlank(subjectCode)) {
			LOG.debug("Miss subject cache key :"+FinGLSubjectCodeCache.getInstance().keyBuilder(paramMap));
			List<FinGLSubjectConfig> subjectConfigList = finGLSubjectConfigDAO.selectFinGLSubjectConfigByParamMap(paramMap);
			if (null != subjectConfigList && subjectConfigList.size() > 0) {
				subjectCode = subjectConfigList.get(0).getSubjectCode();
			}
			if (StringUtils.isBlank(subjectCode)) {
				String error = "Can't find  subject code configuration, subjectType is "+String.valueOf(paramMap.get("subjectType"))+" , query term is "+FinGLSubjectCodeCache.getInstance().keyBuilder(paramMap);
				LOG.error(error);
				throw new RuntimeException(error);
			}
			FinGLSubjectCodeCache.getInstance().put(paramMap, subjectCode);
		}
		return subjectCode;
	}
	
	/**
	 * 获取科目代码
	 * @param subjectType 科目类型
	 * @param config1 配置项1
	 * @return 科目代码
	 */
	public String getSubjectCode(FIN_SUBJECT_TYPE subjectType,String config1){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("subjectType", subjectType);
		paramMap.put("config1", config1);
		return this.getSubjectCode(paramMap);
	}
	/**
	 * 获取科目代码
	 * @param subjectType 科目类型
	 * @return 科目代码
	 */
	public String getSubjectCode(FIN_SUBJECT_TYPE subjectType){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("subjectType", subjectType);
		return this.getSubjectCode(paramMap);
	}

	/**
	 * 获取预收贷方科目/确认收入贷方科目
	 * 
	 * @param subjectType
	 *            科目类型
	 * @param productType
	 *            产品类型
	 * @param subProductType
	 *            产品子类型
	 * @param physical
	 *            是否实体票
	 * @param isForegin
	 *            境内境外
	 * @param regionName
	 *            区域名称
	 * @return 科目代码
	 */
	public  String getLenderSubjectCode(final String subjectType, final String productType, final String subProductType, final String physical, final String isForegin, final String regionName) {
		Map<String, Object> lenderParamMap = new HashMap<String, Object>();
		lenderParamMap.put("subjectType", subjectType);
		lenderParamMap.put("config1", productType);
		lenderParamMap.put("config2", subProductType);
		lenderParamMap.put("config3", physical);// 是否实体票 true:实体票 false：非实体
		lenderParamMap.put("config4", isForegin);// 是否为境外 Y:境外 N:境内
		lenderParamMap.put("config5", regionName);
		return this.getSubjectCode(lenderParamMap);
	}
	
	/**
	 * 获取预收贷方科目/确认收入贷方科目
	 * @param subjectType 科目类型
	 * @param finGLInterfaceDTO 销售产品相关信息
	 * @return 科目代码
	 */
	public String getLenderSubjectCode(String subjectType,FinGLInterfaceDTO dto){
		return this.getLenderSubjectCode(subjectType,dto.getProductType(),dto.getSubProductType(),dto.getPhysical(),dto.getIsForegin(),dto.getRegionName());
	}
	

	/**
	 * 根据支付渠道获得凭证类型
	 * 
	 * @param gateway
	 *            支付渠道
	 * @return 凭证类型代码
	 */
	public String getProofType(String gateway) {
		Map<String, Object> proofTypeParamMap = new HashMap<String, Object>();
		proofTypeParamMap.put("subjectType",Constant.FIN_SUBJECT_TYPE.CERTIFICATE_TYPE.name());
		proofTypeParamMap.put("config1", gateway);
		String proofType = this.getSubjectCode(proofTypeParamMap);
		if(ZFB.equalsIgnoreCase(proofType) || SRCB.equalsIgnoreCase(proofType) || NBYH.equalsIgnoreCase(proofType)){
			return this.getHashProofTypeBase(proofType, Double.doubleToLongBits(Math.random()));
		}
		return proofType;
	}
	

	/**
	 * 获取散列的固定凭证类型
	 * @param orderId 订单ID
	 * @return 凭证类型
	 */
	public String getHashProofTypeByWL(Long orderId) {
		return getHashProofTypeBase(WL,orderId);
	}
	
	/**
	 * 获取散列的固定凭证类型
	 * @param orderId 订单ID
	 * @return 凭证类型
	 */
	public String getHashProofTypeByFDCX(Long orderId) {
		return getHashProofTypeBase(FDCX,orderId);
	}
	
	public String getHashProofTypeByZFB(Long orderId){
		return getHashProofTypeBase(ZFB,orderId);
	}
	
	public String getHashProofTypeBySRCB(Long orderId){
		return getHashProofTypeBase(SRCB,orderId);
	}
	
	public String getHashProofTypeBase(final String hashProofType,final Long orderId){
		String result = hashProofType;
		if(null!=orderId){
			result += orderId%10+1;
		}
		return result;
	}
	/**
	 * 销售产品所属公司对应的帐套号
	 * @param filialeName 公司名称
	 * @return 公司对应的帐套号
	 */
	public String getAccountBookIdByFilialeName(String filialeName){
		Map<String, Object> paramMap=new HashMap<String, Object>();
		paramMap.put("subjectType", Constant.FIN_SUBJECT_TYPE.ACCOUNT_BOOK_SUBJECT.name());
		paramMap.put("config1", filialeName);
		return this.getSubjectCode(paramMap);
	}

}
