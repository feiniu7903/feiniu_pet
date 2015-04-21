package com.lvmama.finance.settlement.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.perm.UserOrgAuditPermService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.finance.base.BaseAction;
import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.annotation.PageSearch;
import com.lvmama.finance.settlement.ibatis.po.AllotOrderManage;
import com.lvmama.finance.settlement.ibatis.po.FinSupplierAllot;
import com.lvmama.finance.settlement.ibatis.po.SettlementTarget;
import com.lvmama.finance.settlement.ibatis.po.SupplierInfo;
import com.lvmama.finance.settlement.ibatis.po.User;
import com.lvmama.finance.settlement.ibatis.vo.SimpleSupplier;
import com.lvmama.finance.settlement.service.AllotOrderManageService;

/**
 * 分单管理Action
 * 
 * @author zhangwenjun
 * 
 */
@Controller
@RequestMapping("/settlement/allotOrderManage/")
public class AllotOrderManageAction extends BaseAction{

	@Autowired
	private AllotOrderManageService allotOrderManageService;
	
	// 权限roleId
	public static final Long ROLE_ID = 374L;
	// 权限接口
	@Autowired
	private UserOrgAuditPermService userOrgAuditPermServiceProxy;
	
	/**
	 * 进入分单管理
	 * 
	 * @return 管理页面
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String advancedeposits(Model model) {
		// 获取当前登陆人的名称
		HttpSession session = FinanceContext.getSession();
		PermUser user = (PermUser) session.getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
		Long userId = user.getUserId();
		Boolean flag = userOrgAuditPermServiceProxy.checkUserRole(userId, ROLE_ID);
		model.addAttribute("flag", flag);
		
		return "settlement/allotOrderManage/manage";
	}
	
	/**
	 * 查询供应商的分单信息
	 * 
	 * @return 结果页面
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/search")
	public Page<SimpleSupplier> search() {
		
		return allotOrderManageService.search();
	}
	
	/**
	 * 
	 */
	@RequestMapping(value = "/queryUser")
	public void queryUser(@RequestParam("userName") String userName, Model model){
		User user = allotOrderManageService.queryUser(userName);
		model.addAttribute("user", user);
	}
	
	/**
	 * 添加/修改供应商分单信息
	 * 
	 * 
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(FinSupplierAllot finSupplierAllot){
		allotOrderManageService.update(finSupplierAllot.getSupplierIdAdd(), finSupplierAllot.getUserNameAdd());
	}
	
	/**
	 * 删除供应商分单信息
	 * @param supplierId
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(@RequestParam("supplierId") Long supplierId){
		allotOrderManageService.delete(supplierId);
	}
	
	/**
	 * 批量修改供应商分单信息
	 * @param supplierId
	 */
	@RequestMapping(value = "/updateBatch", method = RequestMethod.POST)
	public void updateBatch(FinSupplierAllot finSupplierAllot){
		String supplierIds = finSupplierAllot.getSupplierIds();
		String[] supplierArray = supplierIds.split(",");
		// 循环修改分单信息
		for(int i=0; i<supplierArray.length; i++){
			allotOrderManageService.update(Long.parseLong(supplierArray[i]), finSupplierAllot.getUserNameBatch());
		}
	}
	
	/**
	 * 删除供应商分单信息
	 * @param supplierId
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	public void deleteBatch(@RequestParam("supplierIds") String supplierIds){
		String[] supplierArray = supplierIds.split(",");
		// 循环删除分单信息
		for(int i=0; i<supplierArray.length; i++){
			allotOrderManageService.delete(Long.parseLong(supplierArray[i]));
		}
	}

	/**
	 * 进入供应商详情页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/sub/search/{id}")
	public String sub_search(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("init", true);
		model.addAttribute("settlementId", id);
		return "settlement/allotOrderManage/supplierDetail";
	}

	/**
	 * 导出
	 */
	@PageSearch(autobind=true)
	@RequestMapping(value = "/exportAllot")
	public void export(){
			String templatePath = "/WEB-INF/resources/template/allot_detail.xls";
			String fileName = "allot_detail" + DateUtil.getFormatDate(new Date(), "yyyyMMddHHmmss") + ".xls";
			Map<String, Object> map = new HashMap<String, Object>();
			List<AllotOrderManage> list = allotOrderManageService.exportAllot();
			if(list.size()==0){
				AllotOrderManage a = new AllotOrderManage();
				list.add(a);
			}
			map.put("list",list);
			this.exportXLS(map, templatePath, fileName);
	}
	
	/**
	 * 进入供应商详情页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/searchSupplier/{id}")
	public String searchSupplier(@PathVariable("id") Long id, Model model) {
		// 查询供应商详情
		SupplierInfo supplierInfo = allotOrderManageService.searchSupplier(id);
		// 结算对象集合
		List<SettlementTarget> targetList = allotOrderManageService.searchTarget(id);
		
		model.addAttribute("supplierInfo", supplierInfo);
		model.addAttribute("targetList", targetList);
		return "settlement/allotOrderManage/supplierDetail";
	}
	
}
