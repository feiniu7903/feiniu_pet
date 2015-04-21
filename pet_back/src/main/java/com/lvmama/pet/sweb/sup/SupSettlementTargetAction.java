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
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.utils.CopyUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.sweb.EditAction;

/**
 * 结算对象管理
 * @author yangbin
 *
 */
@Results({
	@Result(name="index",location="/WEB-INF/pages/back/sup/settlement_target.jsp"),
	@Result(name="input",location="/WEB-INF/pages/back/sup/edit_settlement_target.jsp")
})
public class SupSettlementTargetAction extends SupTargetBaseAction implements EditAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1130161580107277318L;
	
	private SupSettlementTarget settlementTarget;
	private List<SupSettlementTarget> settlementTargetList;
	private ComLogService comLogRemoteService;
	private MetaProductService metaProductService;
	private SettlementTargetService settlementTargetService;
	
	/**
	 * 根据下拉框输入的内容动态的查询结算对象
	 */
	@Action("/sup/target/settlementSearch")
	public void search(){
		String search = getRequest().getParameter("search");
		List<SupSettlementTarget> list = settlementTargetService.selectSupSettlementTargetByName(search, 10);
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(SupSettlementTarget ss:list){
				JSONObject obj=new JSONObject();
				obj.put("id", ss.getTargetId());
				obj.put("text", ss.getName()+"("+ss.getTargetId()+")");
				array.add(obj);
			}
		}
		sendAjaxResultByJson(array.toString());
	}
	
	@Action("/sup/target/settlement")
	public String goIndex(){
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("supplierId", supplierId);
		settlementTargetList = settlementTargetService.findSupSettlementTarget(param);
		return "index";
	}

	@Override
	@Action("/sup/target/toAddSettlement")
	public String toAdd() {
		if(supplierId==null||supplierId<1){
			return errorParam;
		}
		settlementTarget = new SupSettlementTarget();
		settlementTarget.setSupplierId(supplierId);
		return INPUT;
	}

	@Override
	@Action("/sup/target/toEditSettlement")
	public String toEdit() {
		if(targetId==null||targetId<1){
			return errorParam;
		}
		settlementTarget = settlementTargetService.getSettlementTargetById(targetId);
		if(settlementTarget==null){
			return errorParam;
		}
		setSupplierId(settlementTarget.getSupplierId());
		initEditContact(targetId, "SUP_SETTLEMENT_TARGET");
		return INPUT;
	}

	@Override
	@Action("/sup/target/saveSettlement")
	public void save() {
		saveHandle(settlementTarget.getSupplierId(),"SUP_SETTLEMENT_TARGET");
	}

	@Override
	protected Long doSaveTarget() {
		long targetId = 0l;
		if(settlementTarget.getTargetId()==null){
			targetId = settlementTargetService.addSettlementTarget(settlementTarget, getSessionUserNameAndCheck());
			//SupSettlementTarget targ = this.settlementTargetService
			//		.getSettlementTargetById(targetId);
			//LogObject.addSupSettlementTargetLog(targ,
			//		getSessionUserNameAndCheck(), comLogRemoteService);
		}else{
			SupSettlementTarget entity=settlementTargetService.getSettlementTargetById(settlementTarget.getTargetId());
			if(entity==null){
				throw new IllegalArgumentException("修改的对象不存在");
			}
			
			//如果有绑定的不定期采购产品，则不能修改结算周期
			if(StringUtils.isNotEmpty(entity.getSettlementPeriod()) && Constant.SETTLEMENT_PERIOD.PERMONTH.name().equalsIgnoreCase(entity.getSettlementPeriod())) {
				if(StringUtils.isEmpty(settlementTarget.getSettlementPeriod()) || !Constant.SETTLEMENT_PERIOD.PERMONTH.name().equalsIgnoreCase(settlementTarget.getSettlementPeriod())) {
					Long count = metaProductService.selectMetaProductCountByTargetId(entity.getTargetId(), "META_SETTLEMENT");
					if(count > 0) {
						throw new IllegalArgumentException("该对象有绑定的不定期采购产品，不能修改其结算周期！");
					}
				}
			}
			
			entity=CopyUtil.copy(entity, settlementTarget, getRequest().getParameterNames(),"settlementTarget.");
			
			//SupSettlementTarget oldSupSettlementTarget = settlementTargetService
			//		.getSettlementTargetById(entity.getTargetId());
			settlementTargetService.updateSettlementTarget(entity, getSessionUserNameAndCheck());
			targetId = settlementTarget.getTargetId();
			//LogObject.updateSettlementTargetLog(entity,
			//		oldSupSettlementTarget, getSessionUserNameAndCheck(),
			//		comLogRemoteService);
		}
		return targetId;
	}

	public List<SupSettlementTarget> getSettlementTargetList() {
		return settlementTargetList;
	}

	public SupSettlementTarget getSettlementTarget() {
		return settlementTarget;
	}

	public void setSettlementTarget(SupSettlementTarget settlementTarget) {
		this.settlementTarget = settlementTarget;
	}
	
	public void setSettlementTargetService(
			SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}
	
	public Constant.SETTLEMENT_TARGET_TYPE[] getSettlementTargetTypeList(){
		return Constant.SETTLEMENT_TARGET_TYPE.values();
	}
	public Constant.SETTLEMENT_PAYMENT_TYPE[] getPaymentTypeList(){
		return Constant.SETTLEMENT_PAYMENT_TYPE.values();
	}
	
	public Constant.SETTLEMENT_PERIOD[] getSettlementPeriodList(){
		return Constant.SETTLEMENT_PERIOD.values();
	}

	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}
	
}
