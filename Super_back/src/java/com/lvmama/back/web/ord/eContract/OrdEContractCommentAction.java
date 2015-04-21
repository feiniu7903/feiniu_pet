package com.lvmama.back.web.ord.eContract;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zul.Messagebox;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdEContractComment;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.utils.StringUtil;

public class OrdEContractCommentAction extends BaseAction {

	private static final long serialVersionUID = -4786853164716567416L;
	private Log logger=LogFactory.getLog(OrdEContractCommentAction.class);
	
	private OrdEContractService ordEContractService;
	private String eContractId;
	private List<OrdEContractComment> list=new ArrayList<OrdEContractComment>();
	private OrdEContractComment comment=new OrdEContractComment();
	/**
	 * 根据电子合同ID查询备注列表 
	 */
	protected void doBefore() throws Exception {
		if(null!=eContractId){
			logger.debug("根据订单电子合同ID查询合同备注列表 合同ID："+eContractId);
			list = ordEContractService.queryByEContractId(eContractId);
		}
	}
	/**
	 * 插入一条合同备注
	 * @throws InterruptedException
	 */
	public void save() throws InterruptedException{
		if(StringUtil.isEmptyString(comment.getEContractId())){
			comment.setEcontractId(eContractId);
		}
		if(validate()){
			comment.setCreatedUser(getOperatorName());
			comment.setCreatedDate(new Date());
			ordEContractService.insertOrdEContractComment(comment);
			list = ordEContractService.queryByEContractId(eContractId);
			comment.setContractComment(null);
		}
	}
	private boolean validate() throws InterruptedException{
		if(StringUtil.isEmptyString(comment.getContractComment())){
			Messagebox.show("请补充合同备注信息", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		}
		if(StringUtil.isEmptyString(comment.getEContractId())){
			Messagebox.show("电子合同ID号为空", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		}
		return true;
	}
 
	public String geteContractId() {
		return eContractId;
	}
	public void seteContractId(String eContractId) {
		this.eContractId = eContractId;
	}
	public List<OrdEContractComment> getList() {
		return list;
	}
	public void setList(List<OrdEContractComment> list) {
		this.list = list;
	}
	public OrdEContractComment getComment() {
		return comment;
	}
	public void setComment(OrdEContractComment comment) {
		this.comment = comment;
	}
	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}
}
