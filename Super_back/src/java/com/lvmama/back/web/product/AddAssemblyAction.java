package com.lvmama.back.web.product;

import java.util.List;
import java.util.Map;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;

public class AddAssemblyAction extends BaseAction {
	private static final long serialVersionUID = 3594430378532189961L;
	
	protected ProdProductService prodProductService;
	protected ProdProduct product = new ProdProduct();
	private Long productId;
	private List<ProdAssemblyPoint> assemblyList;
	
	public void doBefore(){
		assemblyList = this.prodProductService.queryAssembly(productId);
	}
	
	public void addAssembly(String assembly){
		if(assembly != null && !"".equals(assembly.trim())){
			ProdAssemblyPoint ap = new ProdAssemblyPoint();
			ap.setProductId(productId);
			ap.setAssemblyPoint(assembly);
			
			prodProductService.saveAssembly(ap,this.getOperatorName());
			super.alert("添加上车地点成功！");
			refreshComponent("refresh");
		}
	}
	
	public void delAssembly(Map<String,Object> parameters){
		final Long assemblyPointId = (Long) parameters.get("assemblyPointId");
		if (null == assemblyPointId) {
			ZkMessage.showError("无法找到所需删除的上车地点，请重新尝试!");
			return;
		}
		ZkMessage.showQuestion("您确定需要删除此上车地点吗?此操作并不影响已生成的订单。", new ZkMsgCallBack() {
			public void execute() {
				prodProductService.delAssembly(assemblyPointId,getOperatorName());
				refreshComponent("refresh");
			}
		}, new ZkMsgCallBack() {
			public void execute() {
			}
		});
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public List<ProdAssemblyPoint> getAssemblyList() {
		return assemblyList;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
}
