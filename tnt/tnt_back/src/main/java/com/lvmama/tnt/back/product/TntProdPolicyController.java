package com.lvmama.tnt.back.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lvmama.tnt.comm.util.web.BaseCommonController;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.comm.vo.TntConstant.COM_LOG_OBJECT_TYPE;
import com.lvmama.tnt.comm.vo.TntConstant.COM_LOG_TYPE;
import com.lvmama.tnt.prod.po.TntProdPolicy;
import com.lvmama.tnt.prod.service.TntProdPolicyService;

@Controller
@RequestMapping("/prodPolicy")
public class TntProdPolicyController extends BaseCommonController {

	@Autowired
	private TntProdPolicyService tntProdPolicyService;

	/** 打开编辑公告的弹窗 **/
	@RequestMapping(value = "saveChannelPolicy")
	public void showEditBox(Long tntPolicyId, String discountY, String reason,
			Model model, HttpServletResponse response,
			HttpServletRequest request) {
		TntProdPolicy policy = tntProdPolicyService.getById(tntPolicyId);
		policy.setDiscountY(discountY);
		if (tntProdPolicyService.updateById(policy)) {
			String content = "分销价  = 销售价 - (销售价 - 结算价) * " + discountY + "%  修改原因：  "
					+ reason;
			insertComLog(request, response, content, "修改渠道通用策略",
					COM_LOG_TYPE.changeChannelPolicy.name(), tntPolicyId,
					COM_LOG_OBJECT_TYPE.TNT_POLICY.getCode());
		}
		sendAjaxResultByJson(response, "true");
	}

	/** 修改针对分销商的分销策略 **/
	@RequestMapping(value = "saveDistPolicy")
	public void showDistEditBox(Long tntPolicyId, String userId, String discountY, String reason,
			Model model, HttpServletResponse response,
			HttpServletRequest request) {
		TntProdPolicy t = new TntProdPolicy();
		Long targetId = Long.parseLong(userId);
		t.setTargetType(TntConstant.PROD_TARGET_TYPE.DISTRIBUTOR.name());
		t.setTargetId(targetId);
		TntProdPolicy policy = tntProdPolicyService.getByDist(t);
		//如果存在targetType='DISTRIBUTOR',ID=targetId的分销商,那么更新这个分销商的分销策略
		if (null!=policy){
			TntProdPolicy updatepolicy = tntProdPolicyService.getById(tntPolicyId);
			updatepolicy.setDiscountY(discountY);
			if (tntProdPolicyService.updateById(updatepolicy)) {
				String content = "分销价  = 销售价 - (销售价 - 结算价) * " + discountY + "%  修改原因：  "
						+ reason;
				insertComLog(request, response, content, "修改分销商通用策略",
						COM_LOG_TYPE.changeDistributor.name(), targetId,
						COM_LOG_OBJECT_TYPE.TNT_POLICY.getCode());
			}
		}else{
			//如果不存在targetType='DISTRIBUTOR',ID=targetId的分销商,那么插入条这个分销商的分销策略
			TntProdPolicy p = new TntProdPolicy();
			p.setTargetId(targetId);
			p.setTargetType(TntConstant.PROD_TARGET_TYPE.DISTRIBUTOR.name());
			p.setDiscountY(discountY);
			p.setPolicyType(TntConstant.PROD_POLICY_TYPE.CUT_PROFIT.name());
			p.setProductType(TntConstant.PRODUCT_TYPE.TICKET.name());
			if (tntProdPolicyService.insert(p)){
				String content = "分销价  = 销售价 - (销售价 - 结算价) * " + discountY + "%  修改原因：  "
						+ reason;
				insertComLog(request, response, content, "修改分销商通用策略",
						COM_LOG_TYPE.changeDistributor.name(), targetId,
						COM_LOG_OBJECT_TYPE.TNT_POLICY.getCode());
			}
		}    
		sendAjaxResultByJson(response, "true");
	}
}
