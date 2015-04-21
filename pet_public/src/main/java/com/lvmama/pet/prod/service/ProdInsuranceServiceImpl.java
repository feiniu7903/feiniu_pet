package com.lvmama.pet.prod.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.prod.ProdInsurance;
import com.lvmama.comm.pet.po.prod.ProdProductHead;
import com.lvmama.comm.pet.service.prod.ProdInsuranceService;
import com.lvmama.comm.pet.service.prod.ProdProductHeadService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.prod.dao.ProdInsuranceDAO;

/**
 * 保险产品逻辑实现类
 *
 */
public class ProdInsuranceServiceImpl implements ProdInsuranceService {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(ProdInsuranceServiceImpl.class);
	/**
	 * 产品头逻辑服务
	 */
	@Autowired
	private ProdProductHeadService prodProductHeadService;
	/**
	 * 操作日志逻辑服务
	 */
	@Autowired
	private ComLogService comLogService;
	/**
	 * 保险产品的数据库操作类
	 */
	@Autowired
	private ProdInsuranceDAO prodInsuranceDAO;
	
	@Override
	public ProdInsurance add(final ProdInsurance insurance, final String operatorName) {
		if (null != insurance) {
			ProdProductHead head = new ProdProductHead(insurance);
			head.setProductId(prodProductHeadService.generateProductId());
			head = prodProductHeadService.save(head);
			
			insurance.setProductId(head.getProductId());
			prodInsuranceDAO.insert(insurance);
			
			debug("save insurance product succeed! product's id : " + insurance.getProductId());
			
			comLogService.insert(Constant.COM_LOG_OBJECT_TYPE.PROD_PRODUCT.name(), null, insurance.getProductId(), operatorName, "CREATE", "创建销售产品", operatorName + "创建此产品", null);
			return insurance;
		} else {
			return null;
		}
	}
	
	@Override
	public ProdInsurance update(final ProdInsurance insurance, final String operatorName) {
		if (null != insurance && null != insurance.getProductId()) {
			ProdInsurance oldInsurance = getProdInsuranceByPrimaryKey(insurance.getProductId());
			if (null != oldInsurance) {
				return update(insurance, oldInsurance, operatorName);
			}
		}
		return null;
	}
	
	@Override
	public ProdInsurance update(final ProdInsurance insurance, final ProdInsurance oldInsurance, final String operatorName) {
		if (null != insurance && null != insurance.getProductId() && null != oldInsurance) {
			ProdProductHead head = prodProductHeadService.getProdProductHeadByProductId(insurance.getProductId());
			if (null != head) {
				if (!insurance.getProductName().equals(head.getProductName())) {
					head.setProductName(insurance.getProductName());
					prodProductHeadService.update(head);
				}
				prodInsuranceDAO.update(insurance);
				
				debug("update insurance product succeed! product's id : " + insurance.getProductId());
				
				StringBuilder sb = new StringBuilder();
				sb.append(operatorName + "修改了此产品:");
				if (!insurance.getProductName().equals(oldInsurance.getProductName())) {
					sb.append("产品名称:").append(oldInsurance.getProductName()).append(" -> ").append(insurance.getProductName());
				}
				if (!insurance.getMarketPrice().equals(oldInsurance.getMarketPrice())) {
					sb.append(";市场价格:").append(oldInsurance.getMarketPriceYuan()).append(" -> ").append(insurance.getMarketPriceYuan());
				}
				if (!insurance.getSellPrice().equals(oldInsurance.getSellPrice())) {
					sb.append(";销售价格:").append(oldInsurance.getSellPriceYuan()).append(" -> ").append(insurance.getSellPriceYuan());
				}
				if (!insurance.getSettlementPrice().equals(oldInsurance.getSettlementPrice())) {
					sb.append(";结算价格:").append(oldInsurance.getSettlementPriceYuan()).append(" -> ").append(insurance.getSettlementPriceYuan());
				}
				if (!insurance.getDays().equals(oldInsurance.getDays())) {
					sb.append(";保险天数:").append(oldInsurance.getDays()).append(" -> ").append(insurance.getDays());
				}
				if (!insurance.getSupplierId().equals(oldInsurance.getSupplierId())) {
					sb.append(";供应商标识:").append(oldInsurance.getSupplierId()).append(" -> ").append(insurance.getSupplierId());
				}
				if (!insurance.getValid().equals(oldInsurance.getValid())) {
					sb.append(";是否上线:").append(oldInsurance.getValid()).append(" -> ").append(insurance.getValid());
				}
				if (!insurance.getManagerId().equals(oldInsurance.getManagerId())) {
					sb.append(";产品经理标识:").append(oldInsurance.getManagerId()).append(" -> ").append(insurance.getManagerId());
				}
				if (!insurance.getFilialeName().equals(oldInsurance.getFilialeName())) {
					sb.append(";所属分公司:").append(oldInsurance.getFilialeName()).append(" -> ").append(insurance.getFilialeName());
				}
				if (null == oldInsurance.getDescription() && null != insurance.getDescription()) {
					sb.append(";描述:").append("null -> ").append(insurance.getDescription());
				}
				if (null != oldInsurance.getDescription() && null == insurance.getDescription()) {
					sb.append(";描述:").append(oldInsurance.getDescription()).append(" -> null");
				}
				if (null != insurance.getDescription() && null != oldInsurance.getDescription() && !insurance.getDescription().equals(oldInsurance.getDescription())) {
					sb.append(";是否上线:").append(oldInsurance.getDescription()).append(" -> ").append(insurance.getDescription());
				}
				
				comLogService.insert(Constant.COM_LOG_OBJECT_TYPE.PROD_PRODUCT.name(), null, insurance.getProductId(), operatorName, "UPDATE", "更新销售产品", sb.toString(), null);
				return insurance;
			} else {
				debug("Cann't find product head! product's id : " + insurance.getProductId());
				return null;
			}	
		} else {
			return null;
		}		
	}
	
	@Override
	public Long selectRowCount(final Map<String, Object> param) {
		return prodInsuranceDAO.selectRowCount(param);
	}
	
	@Override
	public List<ProdInsurance> getProdInsuranceByMap(Map<String, Object> param) {
		if (null == param || param.isEmpty()) {
			debug("Parameters is null or empty, it will return empty list.");
			return new ArrayList<ProdInsurance>(0);
		} else {
			return prodInsuranceDAO.queryProdInsuranceByMap(param);
		}
	}
	
	@Override
	public ProdInsurance getProdInsuranceByPrimaryKey(Long productId) {
		if (null != productId) {
			return prodInsuranceDAO.queryProdInsuranceByPK(productId);
		} else {
			debug("productId is null, how to search product?");
			return null;
		}
	}
	
	/**
	 * 打印调试信息
	 * @param message 调试信息
	 */
	private void debug(final String message) {
		if (LOG.isDebugEnabled() && StringUtils.isNotBlank(message)) {
			LOG.debug(message);
		}
	}

}
