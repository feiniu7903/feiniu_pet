package com.lvmama.pet.sweb.prod.ins;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.prod.ProdInsurance;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.prod.ProdInsuranceService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.pet.sweb.prod.ProdProductAction;


/**
 * 保险类产品的操作类。所有对保险类产品的管理都有此Action入口进入. 
 */
@Results({
	@Result(name="list",location="/WEB-INF/pages/back/prod/insurance/insurance_list.jsp"),
	@Result(name="edit",location="/WEB-INF/pages/back/prod/insurance/insurance_edit.jsp")
})
public final class InsuranceProductAction extends ProdProductAction {	
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -7960370107844801440L;
	/**
	 * 日志
	 */
	private final static Log LOG = LogFactory.getLog(InsuranceProductAction.class);
	/**
	 * 保险类产品的远程服务
	 */
	private ProdInsuranceService prodInsuranceService;
	/**
	 * 产品经理的远程服务
	 */
	private PermUserService permUserService;
	
	/**
	 * 需要被操作的保险产品
	 */
	private ProdInsurance insurance;
	/**
	 * 根据产品名称查询保险产品
	 */
	private String productName;
	/**
	 * 根据产品标识查询保险产品
	 */
	private Long productId;
	/**
	 * 根据供应商查询保险产品
	 */
	private Long supplierId;
	/**
	 * 产品经理
	 */
	private PermUser permUser;

	/**
	 * 列表查询页面的入口
	 * @return 列表查询页面标识 
	 */
	@Action("/prod/insurance/list")
	@SuppressWarnings("unchecked")
	public String list() {
		pagination=initPage();
		pagination.setPageSize(20);
		Map<String,Object> param=buildParam();
		pagination.setTotalResultSize(prodInsuranceService.selectRowCount(param));
		if(pagination.getTotalResultSize() > 0){
			param.put("_startRow", pagination.getStartRows());
			param.put("_endRow", pagination.getEndRows());
			List<ProdInsurance> list=prodInsuranceService.getProdInsuranceByMap(param);
			
			pagination.setItems(list);
		}
		pagination.buildUrl(getRequest());
		
		return "list";
	}
	
	/**
	 * 新增或修改保险产品的入口
	 * @return 编辑保险页面标识
	 */
	@Action("/prod/insurance/edit")
	public String edit() {
		if (null != productId) {
			insurance = prodInsuranceService.getProdInsuranceByPrimaryKey(productId);
		}
		if (null == insurance) {
			insurance = new ProdInsurance();
		}
		if (null != insurance.getManagerId()) {
			permUser = permUserService.getPermUserByUserId(insurance.getManagerId());
		}
		return "edit";
	}
	
	/**
	 * 复制保险产品的入口
	 * @return 编辑保险页面标识
	 */
	@Action("/prod/insurance/copy")
	public String copy() {
		if (null != productId) {
			insurance = prodInsuranceService.getProdInsuranceByPrimaryKey(productId);
			insurance.setProductId(null);
		}
		if (null == insurance) {
			insurance = new ProdInsurance();
		}
		return "edit";
	}	
	
	/**
	 * 保存保险产品
	 * @return
	 * <p>保存从页面传入的保险产品基本信息，如果传入的保险产品已经存在主键标示，则是更新，否则保存</p>
	 */
	@Action("/prod/insurance/save")
	public void save() {
		JSONResult result=new JSONResult();
		if (null != insurance && checkProduct(insurance)) {
			if (null == insurance.getProductId()) {
				prodInsuranceService.add(insurance, getSessionUserName());
				debug(LOG, "save insurance product succeed! product's id : " + insurance.getProductId());
			} else {
				prodInsuranceService.update(insurance, getSessionUserName());
				debug(LOG, "update insurance product succeed! product's id : " + insurance.getProductId());
			}
			result.put("success", true);
			result.output(getResponse());
		} else {
			LOG.debug("It's a joke? insurance is null, what to save?");
			result.put("success", false);
			result.output(getResponse());
		}
	}
	
	@Override
	protected boolean checkProduct(Object object) {
		if (null != object && object instanceof ProdInsurance) {
			ProdInsurance insurance = (ProdInsurance) object;
			return StringUtils.isNotBlank(insurance.getProductName());
		} else {
			return false;
		}
	}
	
	/**
	 * 构造查询参数
	 * @return
	 */
	private Map<String,Object> buildParam(){
		Map<String,Object> map=new HashMap<String, Object>();
		if (StringUtils.isNotBlank(this.productName)) {
			map.put("productName", productName);
		}
		if (null != productId) {
			map.put("productId", productId);
		}
		if (null != supplierId && 0 != supplierId.longValue()) {
			map.put("supplierId", supplierId);
		}
		return map;
	}

	public void setProdInsuranceService(ProdInsuranceService prodInsuranceService) {
		this.prodInsuranceService = prodInsuranceService;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public ProdInsurance getInsurance() {
		return insurance;
	}

	public void setInsurance(ProdInsurance insurance) {
		this.insurance = insurance;
	}

	public PermUser getPermUser() {
		return permUser;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

}
