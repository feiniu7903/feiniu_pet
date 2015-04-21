/**
 * 
 */
package com.lvmama.pet.sweb.sup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.utils.CopyUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.sweb.EditAction;

/**
 * 凭证对象操作
 * @author yangbin
 *
 */
@Results({
	@Result(name="index",location="/WEB-INF/pages/back/sup/bcertificate_target.jsp"),
	@Result(name="input",location="/WEB-INF/pages/back/sup/edit_bcertificate_target.jsp")
})
public class SupBCertificateTargetAction extends SupTargetBaseAction implements EditAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9134798741535657850L;
	private List<SupBCertificateTarget> bcertificateList;
	private SupBCertificateTarget supBCertificateTarget;
	private BCertificateTargetService bCertificateTargetService;
	private ComLogService comLogRemoteService;
	private SupplierService supplierService;

	private String search;

	private MetaProductService metaProductService;
	
	private List<String> cfgItem;

	
	@Action("/sup/target/bCertificate")
	public String index(){
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("supplierId", supplierId);
		bcertificateList=bCertificateTargetService.findBCertificateTarget(param);
		return "index";
	}

	@Override
	@Action("/sup/target/toAddBCertificate")
	public String toAdd() {
		if(supplierId==null||supplierId<1){
			return errorParam;
		}
		getRequest().setAttribute("cfgList", Constant.EBK_AUDIT_ITEM_CONFIG.values());
		supBCertificateTarget = new SupBCertificateTarget();
		supBCertificateTarget.setSupplierId(supplierId);
		supBCertificateTarget.setFaxNo(supplierService.getSupplier(supplierId).getFax());
		
		return INPUT;
	}

	@Override
	@Action("/sup/target/toEditBCertificate")
	public String toEdit() {
		if(targetId==null||targetId<1){
			return errorParam;
		}
		supBCertificateTarget = bCertificateTargetService.getBCertificateTargetByTargetId(targetId);
		if(supBCertificateTarget==null){
			return errorParam;
		}
		getRequest().setAttribute("cfgList", Constant.EBK_AUDIT_ITEM_CONFIG.values());
		setSupplierId(supBCertificateTarget.getSupplierId());
		initEditContact(targetId, "SUP_B_CERTIFICATE_TARGET");
//		if("false".equals(supBCertificateTarget.getManualFaxFlag()))
//		{
//			supBCertificateTarget.setManualFaxFlag(null);
//		}
		return INPUT;
	}

	@Override
	@Action("/sup/target/saveBCertificate")
	public void save() {
		saveHandle(supBCertificateTarget.getSupplierId(),"SUP_B_CERTIFICATE_TARGET");
	}
	

	@Override
	protected Long doSaveTarget() {
		long targetId = 0l;
		if(supBCertificateTarget.getTargetId()==null){
			targetId = bCertificateTargetService.addBCertificateTarget(supBCertificateTarget,getSessionUserNameAndCheck());
			
			//SupBCertificateTarget target = this.bCertificateTargetService.getBCertificateTargetByTargetId(targetId);
			//LogObject.addBCertificateTargetLog(target, getSessionUserNameAndCheck(),comLogRemoteService);
		}else{
			SupBCertificateTarget entity=bCertificateTargetService.getBCertificateTargetByTargetId(supBCertificateTarget.getTargetId());
			if(entity==null){
				throw new IllegalArgumentException("修改的对象不存在");
			}
			
			//如果有绑定的不定期采购产品，则不能修改凭证方式
			if(entity.hasSupplier() || entity.hasDimension()) {
				Long count = metaProductService.selectMetaProductCountByTargetId(entity.getTargetId(), "META_B_CERTIFICATE");
				if(count > 0) {
					if(supBCertificateTarget.hasSendFax() != entity.hasSendFax() 
							|| supBCertificateTarget.hasSupplier() != entity.hasSupplier() 
							|| supBCertificateTarget.hasDimension() != entity.hasDimension()) {
						throw new IllegalArgumentException("该对象有绑定的不定期采购产品，不能修改其B凭证方式！");
					}
				}
			}
			
			CopyUtil.copy(entity, supBCertificateTarget, getRequest().getParameterNames(),"supBCertificateTarget.");
			entity.setFaxFlag(supBCertificateTarget.getFaxFlag());
			entity.setDimensionFlag(supBCertificateTarget.getDimensionFlag());
			entity.setSupplierFlag(supBCertificateTarget.getSupplierFlag());
			if(supBCertificateTarget.getManualFaxFlag()!=null&&!"".equals(supBCertificateTarget.getManualFaxFlag()))
			{
				entity.setManualFaxFlag(supBCertificateTarget.getManualFaxFlag());
			} else {
				entity.setManualFaxFlag("false");
			}
			//更新供应商是否使用禁售功能
			entity.setSupplierForbidSaleFalg(supBCertificateTarget.getSupplierForbidSaleFalg());
			entity.setPriceStockVerifyFalg(supBCertificateTarget.getPriceStockVerifyFalg());
			
			
			if(null!=cfgItem&&cfgItem.size()>0){
				StringBuilder strBuilder=new StringBuilder();
				for(String cfg:cfgItem){
					strBuilder.append(cfg).append(";");
				}
				entity.setEbkProdAuditCfg(strBuilder.toString());
			}else{
				entity.setEbkProdAuditCfg(null);
			}
			
			//SupBCertificateTarget oldSupBCertificateTarget = bCertificateTargetService.getBCertificateTargetByTargetId(entity.getTargetId());
			bCertificateTargetService.updateBCertificateTarget(entity, getSessionUserNameAndCheck());
			
			targetId = supBCertificateTarget.getTargetId();
			
			//LogObject.updateBCertificateTargetLog(entity,oldSupBCertificateTarget, getSessionUserNameAndCheck(),comLogRemoteService);
		}
		return targetId;
	}
	
	@Action("/sup/target/queryCertificateList")
	public void queryCertificateList(){
		JSONArray array=new JSONArray();
		Map<String,Object> param = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(search)){
			param.put("name", search);
			param.put("_startRow", 0L);
			param.put("_endRow", 10L);
			 List<SupBCertificateTarget> certificateList = bCertificateTargetService.findBCertificateTarget(param);
			if(CollectionUtils.isNotEmpty(certificateList)){
				for(SupBCertificateTarget target:certificateList){
					JSONObject obj=new JSONObject();
					obj.put("id", target.getTargetId());
					obj.put("text", target.getName());
					array.add(obj);
				}
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}

	public SupBCertificateTarget getSupBCertificateTarget() {
		return supBCertificateTarget;
	}

	public void setSupBCertificateTarget(SupBCertificateTarget supBCertificateTarget) {
		this.supBCertificateTarget = supBCertificateTarget;
	}

	public List<SupBCertificateTarget> getBcertificateList() {
		return bcertificateList;
	}
	
	public Constant.BCERT_TYPE[] getBcertTypeList(){
		return Constant.BCERT_TYPE.values();
	}
	
	public Constant.FAX_STRATEGY[] getFaxStrategyList(){
		return Constant.FAX_STRATEGY.values();
	}

	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}

	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setCfgItem(List<String> cfgItem) {
		this.cfgItem = cfgItem;
	}
	
}
