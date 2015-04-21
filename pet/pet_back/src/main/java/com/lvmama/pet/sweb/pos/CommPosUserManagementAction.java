package com.lvmama.pet.sweb.pos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pay.PayPos;
import com.lvmama.comm.pet.po.pay.PayPosCommercial;
import com.lvmama.comm.pet.po.pay.PayPosUser;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.pay.PayPosCommercialService;
import com.lvmama.comm.pet.service.pay.PayPosService;
import com.lvmama.comm.pet.service.pay.PayPosUserService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.WebUtils;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.Constant;

/**
 * 交通银行Pos机员工管理.
 * @author liwenzhan.
 */
@ParentPackage("json-default")
@Results({
		@Result(name = "user_insert", location = "/WEB-INF/pages/back/pos/user/posUser_insert.jsp"),
		@Result(name = "user_query", location = "/WEB-INF/pages/back/pos/user/posUser_management.jsp"),
		@Result(name = "user_update", location = "/WEB-INF/pages/back/pos/user/posUser_update.jsp") })
public class CommPosUserManagementAction extends BackBaseAction {

	/**
	 * .
	 */
	private static final long serialVersionUID = 5785365897950228775L;
	/**
	 * POS员工服务.
	 */
	private PayPosUserService payPosUserService;
	/**
	 * POS终端服务.
	 */
	private PayPosService payPosService;
	/**
	 * POS商户服务.
	 */
	private PayPosCommercialService payPosCommercialService;
	/**
	 * POS商户实体类.
	 */
	private PayPosUser payPosUser;
	
	/**
	 * 查询员工编号.
	 */
	private String searchEmpNo;
	/**
	 * 查询POS员工姓名.
	 */
	private String searcEmpName;
	
	/**
	 * 查询POS员工状态.
	 */
	private String searchEmpStatus;
	
	/**
	 * POS员工ID.
	 */
	private Long commPosUserId;
	/**
	 * 员工编号.
	 */
	private String empNo;
	/**
	 * 员工密码*(MD5加密).
	 */
	private String empPasswd;
	/**
	 * POS公司编号.
	 */
	private String empCompanyNo;
	
	/**
	 * POS公司名称.
	 */
	private String empCompanyName;
	/**
	 * POS员工姓名.
	 */
	private String empName;
	/**
	 * POS员工所在地
	 */
	private String empLocation;
	/**
	 * POS员工状态.
	 */
	private String empStatus;
	/**
	 * 员工所属POS终端ID.
	 */
	private Long commPosId;
	/**
	 * 员工所属POS商户ID.
	 */
	private Long commercialId;
	
	/**
	 * 商户列表LIST.
	 */
	private List<PayPosCommercial> commercialList;
	/**
	 * 商户和终端对应关系哈希MAP.
	 */
	private Map<PayPosCommercial,List<PayPos>> terminalMap;
	
	
	private Page<PayPosUser> payPosUserPage;
	private Long perPageRecord=10L;
	
	private PermUserService permUserService;
	
	private String search;
	
	private List<PayPosCommercial> searchCommercialList = new ArrayList<PayPosCommercial>();
	
	private List<PayPos> searchPayPosList;
	private Long searchCommercialId;
	private Long searchCommPosId;
 	/**
	 * 
	 * .
	 */

	public CommPosUserManagementAction() {
		super();
	}

	@Action(value = "/pos/searchUserName") 
	public void searchUserName(){ 
		JSONArray array=new JSONArray(); 
		List<PermUser> permUserList=permUserService.selectListByUserNameOrRealName(search); 
		for (PermUser permUser : permUserList) { 
		JSONObject obj=new JSONObject(); 
		obj.put("id", permUser.getUserName()); 
		obj.put("text", permUser.getRealName()+"("+permUser.getUserName()+")"); 
		array.add(obj); 
	   } 
		JSONOutput.writeJSON(getResponse(), array);
	}
	
	/**
	 * 根据条件查询 POS员工.
	 * 
	 */
	@Action("/pos/selectUser")
	public String SelectPosUser() {
		Map<String,String> param=buildParameter();
		payPosUserPage =payPosUserService.selectPayPosUserPageByParam(param,perPageRecord,page);
		payPosUserPage.buildUrl(getRequest());
		payPosUserPage.setUrl(WebUtils.getUrl(getRequest(),param));
		selectCommerialList();
		return "user_query";
	}
	
	/**
	 * 初始化POS员工查询页面.
	 */
	
	@Action("/pos/goUserList")
	public String execute() {
		selectCommerialList();
		return "user_query";
	}

	private void selectCommerialList() {
		Map<String,Object> params=new HashMap<String, Object>();
		searchCommercialList  = payPosCommercialService.select(params);
		
	}


	@Action(value="/pos/loadCommPoss",results=@Result(name="comPoss",type="json",
			params={"includeProperties","searchPayPosList\\[\\d+\\]\\.terminalNo,searchPayPosList\\[\\d+\\]\\.posId"}))
	public String loadCommPoss(){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("commercialId",searchCommercialId);
		searchPayPosList=payPosService.select(params);
		return "comPoss";
	}
	
	/**
	 * 初始化添加POS员工页面.
	 * @return
	 */
	@Action("/pos/addPosUserPage")
	public String addPosUserPage() {
		chackCommPosList();			
		return "user_insert";
	}

	private void chackCommPosList() {
		Map<String,Object> params=new HashMap<String, Object>();
		commercialList=payPosCommercialService.select(params);
		terminalMap=new HashMap<PayPosCommercial,List<PayPos>>();
		for(PayPosCommercial commercialItem : commercialList) {
			params=new HashMap<String, Object>();
			params.put("commercialId",commercialItem.getCommercialId());
			List<PayPos> terminalList=payPosService.select(params);
			if(terminalList.size()!=0){
				terminalMap.put(commercialItem, terminalList);
			}
		}
	}
	
	/**
	 * 初始化POS员工查询页面.
	 * @return
	 */
	@Action("/pos/getListPage")
	public String getListPage() {
		return "user_query";
	}
	/**
	 * 初始化POS员工更新页面.
	 * @return
	 */
	@Action("/pos/updateUserPage")
	public String updatePosUserPage() {
		chackCommPosList();	
		payPosUser = payPosUserService.selectById(commPosUserId);
		commercialId=payPosUser.getCommercialId();
		commPosId=payPosUser.getCommPosId();
		return "user_update";
	}


	/**
	 * 
	 * 插入POS员工.
	 */
	@Action("/pos/insertUser")
	public void InsertPosUser() {

		JSONResult result = new JSONResult();
		try {
			Assert.isTrue(!(commercialId.equals("")), "请选择商户号");
			Assert.isTrue((commPosId!=null), "请选择终端号");
			Assert.hasLength(empNo.trim(), "员工号不可以为空");
			Assert.hasLength(empName.trim(), "用户名不可以为空");
			Assert.hasLength(empPasswd.trim(), "密码不可以为空");
			
			
			Map<String,Object> params=new HashMap<String, Object>();
			if (this.empNo!=null) {
				params.put("empNo", empNo);
			}
            if(payPosUserService.selectListCountByParam(params)>0){
            	throw new Exception("该员工号已经存在,请确定员工号");
            }
			
			PayPosUser payPosUser = new PayPosUser();
			payPosUser.setEmpCompanyName(empCompanyName);
			payPosUser.setEmpCompanyNo(empCompanyNo);
			payPosUser.setEmpNo(empNo);
			payPosUser.setEmpName(empName);
			payPosUser.setEmpLocation(empLocation);
			//LvmamaMd5Key对密码进行加密
			payPosUser.setEmpPasswd(MD5.md5(empPasswd).toLowerCase());
			payPosUser.setEmpStatus(empStatus);
			payPosUser.setCommercialId(commercialId);
			payPosUser.setCommPosId(commPosId);
			Long posUserId = payPosUserService.insert(payPosUser, this.getSessionUserName());
			result.put("posUserId", posUserId);
		} catch (Exception ex) {
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * POS员工状态更改.
	 */
	@Action("/pos/modifyStatusUser")
	public void modifyStatus(){
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue(!(commPosUserId==null||commPosUserId<1),"POS员工信息不可以为空");
			Assert.hasLength(empStatus,"POS员工状态不可以为空");
			PayPosUser cpu = payPosUserService.selectById(commPosUserId);
			if(cpu==null){
				throw new Exception("您修改的POS员工信息不存在!");
			}
			cpu.setEmpStatus(empStatus);
			payPosUserService.modifyStatus(cpu,
					this.getSessionUserName(), empStatus);		
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	/**
	 * POS员工密码更改.
	 */
	@Action("/pos/modifyPassword")
	public void modifyPassword(){
		JSONResult result=new JSONResult();
		try{
			Assert.isTrue(!(commPosUserId==null||commPosUserId<1),"POS员工信息不可以为空");
			Assert.hasLength(empPasswd,"密码不可以为空");
			PayPosUser cpu = payPosUserService.selectById(commPosUserId);
			if(cpu==null){
				throw new Exception("您修改的POS员工信息不存在!");
			}
			cpu.setEmpPasswd(MD5.md5(empPasswd).toLowerCase());
			payPosUserService.modifyPassword(cpu,
					this.getSessionUserName(), empPasswd);
			empPasswd="";
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	/**
	 * 更新 POS用户信息.
	 */
	@Action("/pos/updateUser")
	public void UpdatePosUser() {
		JSONResult result = new JSONResult();
		try {
			Assert.hasLength(empNo.trim(), "员工号不可以为空");
			Assert.hasLength(empName.trim(), "用户名不可以为空");
			PayPosUser oldPayPosUser =  payPosUserService.selectById(commPosUserId);
			if(oldPayPosUser!=null && !oldPayPosUser.getEmpNo().equals(empNo)){
				Map<String,Object> params=new HashMap<String, Object>();
				if (this.empNo!=null) {
					params.put("empNo", empNo);
				}
	            if(payPosUserService.selectListCountByParam(params)>0){
	            	throw new Exception("该员工号已经存在,请确定员工号");
	            }
			}
			PayPosUser payPosUser = payPosUserService.selectById(commPosUserId);
			payPosUser.setCommPosId(commPosId);
			payPosUser.setCommercialId(commercialId);
			payPosUser.setPosUserId(commPosUserId);
			payPosUser.setEmpCompanyName(empCompanyName);
			payPosUser.setEmpCompanyNo(empCompanyNo);
			payPosUser.setEmpNo(empNo);
			payPosUser.setEmpName(empName);
			payPosUser.setEmpLocation(empLocation);
			payPosUserService.update(oldPayPosUser,payPosUser,this.getSessionUserName());
			result.put("posUserId",commPosUserId);
		} catch (Exception ex) {
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}


	/**
	 * 建立查询条件.
	 * 
	 * @return
	 */
	private Map<String, String> buildParameter() {
		Map<String, String> params = new HashMap<String, String>();

		if(commercialId!=null){
			this.searchCommercialId = this.commercialId;
			this.searchCommPosId = this.commPosId;
		}
		if(searchCommercialId!=null && searchCommercialId>0){
			params.put("commercialId", searchCommercialId.toString());
		}
		if(searchCommPosId!=null && searchCommercialId>0){
			params.put("commPosId", searchCommPosId.toString());
		}
		if (StringUtils.isNotEmpty(this.searchEmpStatus)) {
			params.put("empStatus", searchEmpStatus);
		}
		if (StringUtils.isNotEmpty(this.searcEmpName)) {
			params.put("empName", searcEmpName.trim());
		}
		if (StringUtils.isNotEmpty(this.searchEmpNo)) {
			params.put("empNo", searchEmpNo.trim());
		}
		return params;
	}

	//Setters & Getters 
	public PayPosUser getPayPosUser() {
		return payPosUser;
	}

	public void setPayPosUser(PayPosUser payPosUser) {
		this.payPosUser = payPosUser;
	}

	public String getSearchEmpNo() {
		return searchEmpNo;
	}

	public void setSearchEmpNo(String searchEmpNo) {
		this.searchEmpNo = searchEmpNo;
	}

	public String getSearcEmpName() {
		return searcEmpName;
	}

	public void setSearcEmpName(String searcEmpName) {
		this.searcEmpName = searcEmpName;
	}

	public String getSearchEmpStatus() {
		return searchEmpStatus;
	}

	public void setSearchEmpStatus(String searchEmpStatus) {
		this.searchEmpStatus = searchEmpStatus;
	}

	public Long getCommPosUserId() {
		return commPosUserId;
	}

	public void setCommPosUserId(Long commPosUserId) {
		this.commPosUserId = commPosUserId;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getEmpPasswd() {
		return empPasswd;
	}

	public void setEmpPasswd(String empPasswd) {
		this.empPasswd = empPasswd;
	}

	public String getEmpCompanyNo() {
		return empCompanyNo;
	}

	public void setEmpCompanyNo(String empCompanyNo) {
		this.empCompanyNo = empCompanyNo;
	}

	public String getEmpCompanyName() {
		return empCompanyName;
	}

	public void setEmpCompanyName(String empCompanyName) {
		this.empCompanyName = empCompanyName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpLocation() {
		return empLocation;
	}

	public void setEmpLocation(String empLocation) {
		this.empLocation = empLocation;
	}

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public Long getCommPosId() {
		return commPosId;
	}

	public void setCommPosId(Long commPosId) {
		this.commPosId = commPosId;
	}

	public Long getCommercialId() {
		return commercialId;
	}

	public void setCommercialId(Long commercialId) {
		this.commercialId = commercialId;
	}

	public List<PayPosCommercial> getCommercialList() {
		return commercialList;
	}

	public void setCommercialList(List<PayPosCommercial> commercialList) {
		this.commercialList = commercialList;
	}

	public Map<PayPosCommercial, List<PayPos>> getTerminalMap() {
		return terminalMap;
	}

	public void setTerminalMap(Map<PayPosCommercial, List<PayPos>> terminalMap) {
		this.terminalMap = terminalMap;
	}

	public Page<PayPosUser> getPayPosUserPage() {
		return payPosUserPage;
	}

	public void setPayPosUserPage(Page<PayPosUser> payPosUserPage) {
		this.payPosUserPage = payPosUserPage;
	}


	public Long getPerPageRecord() {
		return perPageRecord;
	}

	public void setPerPageRecord(Long perPageRecord) {
		this.perPageRecord = perPageRecord;
	}

	public void setPayPosUserService(PayPosUserService payPosUserService) {
		this.payPosUserService = payPosUserService;
	}

	public void setPayPosService(PayPosService payPosService) {
		this.payPosService = payPosService;
	}

	public void setPayPosCommercialService(
			PayPosCommercialService payPosCommercialService) {
		this.payPosCommercialService = payPosCommercialService;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public List<PayPosCommercial> getSearchCommercialList() {
		return searchCommercialList;
	}

	public void setSearchCommercialList(List<PayPosCommercial> searchCommercialList) {
		this.searchCommercialList = searchCommercialList;
	}

	public List<PayPos> getSearchPayPosList() {
		return searchPayPosList;
	}

	public void setSearchPayPosList(List<PayPos> searchPayPosList) {
		this.searchPayPosList = searchPayPosList;
	}

	public Long getSearchCommercialId() {
		return searchCommercialId;
	}

	public void setSearchCommercialId(Long searchCommercialId) {
		this.searchCommercialId = searchCommercialId;
	}

	public Long getSearchCommPosId() {
		return searchCommPosId;
	}

	public void setSearchCommPosId(Long searchCommPosId) {
		this.searchCommPosId = searchCommPosId;
	}

	public static void main(String[] args) {
		PayPosCommercial p = new PayPosCommercial();
		p.setCommercialId(0l);
		p.setCommercialNo("--");
		p.setCommercialName("--全部--");
		List<PayPosCommercial> list = new ArrayList<PayPosCommercial>();
		list.add(p);
		System.out.println(list.size());
		
	}
	
	public List<PayPos> getSearchCommPosList(){
		if(searchCommercialId==null){
			return Collections.emptyList();
		}else{
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("commercialId",searchCommercialId);
			return  payPosService.select(params);
		}
		
		
	}
}
