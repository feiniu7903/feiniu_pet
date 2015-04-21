package com.lvmama.finance.settlement.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvmama.comm.pet.po.pub.ComContact;
import com.lvmama.comm.pet.service.pub.ContactService;
import com.lvmama.finance.base.BaseAction;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.annotation.PageSearch;
import com.lvmama.finance.base.annotation.Version;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlement;
import com.lvmama.finance.settlement.ibatis.po.OrdSubSettlement;
import com.lvmama.finance.settlement.ibatis.vo.SimpleOrdSettlement;
import com.lvmama.finance.settlement.service.OrdSettlementService;

/**
 * 结算单管理
 * 
 * @author yanggan
 * 
 */
@Controller
@RequestMapping("/settlement/ordsettlement")
public class OrdSettlementAction extends BaseAction {

	@Autowired
	private OrdSettlementService ordSettlementService;
	@Autowired
	private ContactService contactService;

	/**
	 * 进入结算单管理页面
	 * 
	 * @return 管理页面
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(@RequestParam(value="from" ,required = false) String from,Model model) {
		if("op".equals(from)){
			model.addAttribute("settlementType", "GROUP");
		}else{
			model.addAttribute("settlementType", "ORDER");
		}
		return "settlement/ordsettlement/manage";
	}

	@RequestMapping(value = "/index/{ids}", method = RequestMethod.GET)
	public String search(@PathVariable("ids") String ids, @RequestParam(value = "from", required = false) String from, Model model) {
		model.addAttribute("settlementId", ids);
		model.addAttribute("init", true);
		if("op".equals(from)){
			model.addAttribute("settlementType", "GROUP");
		}else{
			model.addAttribute("settlementType", "ORDER");
		}
		return "settlement/ordsettlement/manage";
	}
	
	/**
	 * 查询结算单
	 * 
	 * @return 结算单信息
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/search")
	public Page<OrdSettlement> search() {
		Page<OrdSettlement> pageOrd = ordSettlementService.searchOrdSettlement();
		return pageOrd;
	}

	/**
	 * 预存款打款
	 * 
	 * @return
	 */
	@Version
	@RequestMapping(value = "/pay/1", method = RequestMethod.POST)
	public void pay(OrdSettlement ors,Model model) {
		Integer res = ordSettlementService.advancedepositsBalpay(ors);
		model.addAttribute("res", res);
		model.addAttribute("ors", ors);
	}

	/**
	 * 线下打款
	 * 
	 * @return
	 */
	@Version
	@RequestMapping(value = "/pay/2", method = RequestMethod.POST)
	public void bankPay(OrdSettlement ors, @RequestParam("bank") String bank, @RequestParam("operatetimes") String operatetimes, @RequestParam("serial") String serial,Model model) {
		ordSettlementService.bankPay(ors, bank, operatetimes, serial);
		model.addAttribute(ors);
	}

	/**
	 * 结算单详细信息
	 * 
	 * @param id
	 *            结算单ID
	 * @return
	 */
	@RequestMapping(value = "/info/{id}/{type}", method = RequestMethod.GET)
	public String confirm(@PathVariable("id") Long id,@PathVariable("type") Integer type, Model model) {
		// 固化对象信息
		SimpleOrdSettlement so = ordSettlementService.searchSimpleOrdSettlement(id);
		ComContact contact=null;
		if(so.getTargetId()!=null){
			contact = this.contactService.getSupSettlementTragetContactByTargetId(Long.valueOf(so.getTargetId()+""));			
		}
		if(contact==null){
			contact = new ComContact();
		}
		so.setContact(contact);
		// 原始对象信息
		SimpleOrdSettlement initalSo = ordSettlementService.searchInitalOrdSettlement(id);
		ComContact contactSo=null;
		if(initalSo.getTargetId()!=null){
			contactSo = this.contactService.getSupSettlementTragetContactByTargetId(Long.valueOf(initalSo.getTargetId()+""));			
		}
		if(contactSo==null){
			contactSo = new ComContact();
		}
		initalSo.setContact(contactSo);
		model.addAttribute("ors", so);
		model.addAttribute("initalOrs", initalSo);
		model.addAttribute("type", type);
		return "settlement/ordsettlement/info";
	}
	
	/**
	 * 刷新原始结算单信息
	 * 
	 * @param id
	 *            结算单ID
	 * @return
	 */
	@RequestMapping(value = "/queryInitalInfo", method = RequestMethod.POST)
	public SimpleOrdSettlement queryInitalInfo(@RequestParam("id") Long id) {
		// 原始对象信息
		SimpleOrdSettlement initalSo = ordSettlementService.searchInitalOrdSettlement(id);
		ComContact contactSo = null;
		if(initalSo!=null&&initalSo.getTargetId()!=null&&initalSo.getTargetId()>1){
			contactSo = this.contactService.getSupSettlementTragetContactByTargetId(Long.valueOf(initalSo.getTargetId()+""));
		}
		if(contactSo==null){
			contactSo = new ComContact();
		}
		initalSo.setContact(contactSo);
		OrdSettlement ors = new OrdSettlement();
		ors.setSettlementId(new Long(initalSo.getSettlementId()));
		ors.setMemo(initalSo.getMemo());
		ors.setBankName(initalSo.getBankName());
		ors.setBankAccountName(initalSo.getBankAccountName());
		ors.setBankAccount(initalSo.getBankAccount());
		ors.setAlipayName(initalSo.getAlipayName());
		ors.setAlipayAccount(initalSo.getAlipayAccount());
		// 修改固话结算对象数据
		ordSettlementService.updateInitalInfo(id, ors);
		
		return initalSo;
	}

	/**
	 * 结算单确认
	 * 
	 * @param ors
	 *            结算单信息
	 */
	@Version
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public void confirm(OrdSettlement ors,Model model) {
		ordSettlementService.confirm(ors);
		model.addAttribute(ors);
	}

	/**
	 * 结算单结算
	 * 
	 * @param ors
	 *            结算单信息
	 */
	@Version
	@RequestMapping(value = "/settt", method = RequestMethod.POST)
	public void settt(OrdSettlement ors,Model model) {
		ordSettlementService.settlement(ors);
		model.addAttribute("ors", ors);
	}

	/**
	 * 进入结算子单页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/sub/search/{id}")
	public String sub_search(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("init", true);
		model.addAttribute("settlementId", id);
		return "settlement/ordsettlement/sub_settlement";
	}

	/**
	 * 查询结算子单
	 * 
	 * @return
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/sub/search")
	public Page<OrdSubSettlement> sub_search() {
		return ordSettlementService.searchOrdSubSettlement();
	}

	/**
	 * 删除结算子单
	 * 
	 * @param settlementId
	 *            结算单ID
	 * @param subSettlementId
	 *            结算子单ID
	 * @param model
	 */
	@RequestMapping(value = "/sub/remove/{settlementId}/{subSettlementId}")
	public void removeSubSettlement(@PathVariable("settlementId") Long settlementId, @PathVariable("subSettlementId") Long subSettlementId, Model model) {
		Integer res = ordSettlementService.deleteSubSettlement(settlementId, subSettlementId);
		model.addAttribute(res);
	}
	
}
