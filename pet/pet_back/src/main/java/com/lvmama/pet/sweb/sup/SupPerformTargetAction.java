/**
 * 
 */
package com.lvmama.pet.sweb.sup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.utils.CopyUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.sweb.EditAction;

/**
 * @author yangbin
 *
 */
@Results({
	@Result(name="index",location="/WEB-INF/pages/back/sup/perform_target.jsp"),
	@Result(name="input",location="/WEB-INF/pages/back/sup/edit_perform_target.jsp")
})
public class SupPerformTargetAction extends SupTargetBaseAction implements EditAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7542616883301383032L;

	private SupPerformTarget performTarget;
	private PerformTargetService performTargetService;
	private List<SupPerformTarget> performTargetList;
	private ComLogService comLogRemoteService;
	private MetaProductService metaProductService;
	
	@Action("/sup/target/perform")
	public String index(){
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("supplierId", supplierId);
		performTargetList = performTargetService.findSupPerformTarget(param);
		return "index";
	}
	
	@Override
	@Action("/sup/target/toAddPerform")
	public String toAdd() {
		if(supplierId==null||supplierId<1){
			return errorParam;
		}
		performTarget = new SupPerformTarget();
		performTarget.setSupplierId(supplierId);
		return INPUT;
	}

	@Override
	@Action("/sup/target/toEditPerform")
	public String toEdit() {
		if(targetId==null||targetId<1){
			return errorParam;
		}
		performTarget = performTargetService.getSupPerformTarget(targetId);
		setSupplierId(performTarget.getSupplierId());
		initEditContact(targetId, "SUP_PERFORM_TARGET");
		return INPUT;
	}

	@Override
	@Action("/sup/target/savePerform")
	public void save() {
		saveHandle(performTarget.getSupplierId(),"SUP_PERFORM_TARGET");
	}

	@Override
	protected Long doSaveTarget() {		
		long targetId = 0l;
		if(performTarget.getTargetId()==null){
			targetId = performTargetService.addPerformTarget(performTarget, getSessionUserNameAndCheck());
			
			//SupPerformTarget target = this.performTargetService.getSupPerformTarget(targetId);
			//LogObject.addSupPerformTargetLog(target, getSessionUserNameAndCheck(),comLogRemoteService);
		}else{
			SupPerformTarget entity=performTargetService.getSupPerformTarget(performTarget.getTargetId());
			if(entity==null){
				throw new IllegalArgumentException("修改的对象不存在");
			}
			//如果有绑定的不定期采购产品，则不能修改履行方式
			if(StringUtils.isNotEmpty(entity.getCertificateType()) && Constant.CCERT_TYPE.SMS.name().equalsIgnoreCase(entity.getCertificateType())) {
				if(StringUtils.isEmpty(performTarget.getCertificateType()) || !Constant.CCERT_TYPE.SMS.name().equalsIgnoreCase(performTarget.getCertificateType())) {
					Long count = metaProductService.selectMetaProductCountByTargetId(entity.getTargetId(), "META_PERFORM");
					if(count > 0) {
						throw new IllegalArgumentException("该对象有绑定的不定期采购产品，不能修改其履行方式！");
					}
				}
			} else if(StringUtils.isNotEmpty(entity.getCertificateType()) && Constant.CCERT_TYPE.DIMENSION.name().equalsIgnoreCase(entity.getCertificateType())) {
				if(StringUtils.isEmpty(performTarget.getCertificateType()) || !Constant.CCERT_TYPE.DIMENSION.name().equalsIgnoreCase(performTarget.getCertificateType())) {
					Long count = metaProductService.selectMetaProductCountByTargetId(entity.getTargetId(), "META_PERFORM");
					if(count > 0) {
						throw new IllegalArgumentException("该对象有绑定的不定期采购产品，不能修改其履行方式！");
					}
				}
			}
			
			entity=CopyUtil.copy(entity, performTarget, getRequest().getParameterNames(),"performTarget.");
			
			//SupPerformTarget oldPerformTargetService = performTargetService.getSupPerformTarget(entity.getTargetId());
			performTargetService.updatePerformTarget(entity, getSessionUserNameAndCheck());
			
			targetId = performTarget.getTargetId();
			
			//LogObject.updateSupPerformTargetLog(entity,oldPerformTargetService, this.getSessionUserNameAndCheck(),comLogRemoteService);
		}
		return targetId;
	}

	public List<SupPerformTarget> getPerformTargetList() {
		return performTargetList;
	}

	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}

	public SupPerformTarget getPerformTarget() {
		return performTarget;
	}

	public void setPerformTarget(SupPerformTarget performTarget) {
		this.performTarget = performTarget;
	}

	public Constant.CCERT_TYPE[] getCertificateTypeList(){
		return Constant.CCERT_TYPE.values();
	}

	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}
}
