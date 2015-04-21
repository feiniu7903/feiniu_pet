/**
 * 
 */
package com.lvmama.pet.sweb.contract;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.service.sup.SupContractService;
import com.lvmama.comm.utils.json.JSONResult;

/**
 * @author yangbin
 *
 */
public class ContractJSONAction extends BackBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 913930570205769101L;
	private Long supplierId;
	private SupContractService supContractService;
	
	/**
	 * 按供应商读取合同的json数据
	 */
	@Action("/contract/searchJSON")
	public void searchContract(){
		JSONResult result=new JSONResult();
		try{
			List<SupContract> list=supContractService.selectContractBySupplierId(supplierId);
			JSONArray array=new JSONArray();
			if(!list.isEmpty()){
				for(SupContract sc:list){
					JSONObject obj=new JSONObject();
					obj.put("contractId", sc.getContractId());
					obj.put("contractNo", sc.getContractNo()+"("+sc.getContractId()+")");
					array.add(obj);
				}
			}
			result.put("list", array);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}

	

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}



	public void setSupContractService(SupContractService supContractService) {
		this.supContractService = supContractService;
	}
	
	
}
