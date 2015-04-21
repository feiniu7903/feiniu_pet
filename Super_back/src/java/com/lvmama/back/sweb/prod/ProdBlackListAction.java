package com.lvmama.back.sweb.prod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.prod.ProdBlackList;
import com.lvmama.comm.pet.service.prod.ProdBlackListService;
import com.lvmama.comm.utils.ExcelImport;
import com.lvmama.comm.utils.StringUtil;

/**
 * 黑名单控制
 * 
 * @author zenglei
 *
 * 2014-5-4 16:07:31
 */
@Results({
	@Result(name = "insert", location = "/WEB-INF/pages/back/prod/blackList/insertBlackList.jsp", type = "dispatcher"),
	@Result(name = "update", location = "/WEB-INF/pages/back/prod/blackList/updateBlackList.jsp", type = "dispatcher"),
	@Result(name = "query", location = "/WEB-INF/pages/back/prod/blackList/blackLists.jsp", type = "dispatcher"),
	@Result(name = "showBlackDialog", location = "/WEB-INF/pages/back/prod/blackList/blackList_dialog.jsp", type = "dispatcher"),
	@Result(name = "queryByProds", location = "/WEB-INF/pages/back/prod/blackList/blackListsByProds.jsp", type = "dispatcher")
})
public class ProdBlackListAction extends BaseAction{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -4330190514844181628L;
	
	
	private ProdBlackListService prodBlackListService;
	
	//增、删、改
	private ProdBlackList prodBlackList;
	private String phoneNums;

	//导入号码
	private File phoneFile;
	private String phoneFileFileName;
	private String phoneFileContentType;

	//查询条件
	private String phone;
	private String productId;
	
	@Action("/prodblack/insertSkip")
	public String insertSkip(){
		return "insert";
	}
	
	@Action("/prodblack/insert")
	public void insertBlackList(){
		JSONObject json = new JSONObject();
		StringBuffer valiMessage = new StringBuffer();
		try {
			if(prodBlackList != null){
				List<ProdBlackList> blackList = new ArrayList<ProdBlackList>();
				if(prodBlackList.getBlackIsPhone().equals("0")){
					String[] phoneNum = phoneNums.split(",");
					for (String phone : phoneNum) {
						ProdBlackList black = new ProdBlackList();
						black.setBlackIsPhone(prodBlackList.getBlackIsPhone());
						black.setBlackCirculation(prodBlackList.getBlackCirculation());
						black.setBlackStartTime(prodBlackList.getBlackStartTime());
						black.setBlackEndTime(prodBlackList.getBlackEndTime());
						black.setBlackLimit(prodBlackList.getBlackLimit());
						black.setBlackReason(prodBlackList.getBlackReason());
						black.setProductId(prodBlackList.getProductId());
						black.setBlackPhoneNum(phone);
						if(this.validateBlackList(black)){
							blackList.add(black);
						}else{
							valiMessage.append(phone+",");
						}
					}
				}else{
					if(this.validateBlackList(prodBlackList)){
						blackList.add(prodBlackList);
					}
				}
				prodBlackListService.insertBlackList(blackList);
				json.put("success", true);
				json.put("message", "黑名单新增"+blackList.size()+"条!");
				//校验
				System.out.println(prodBlackList.getBlackIsPhone());
				if(prodBlackList.getBlackIsPhone().equals("0")){
					if(!valiMessage.toString().trim().equals("")){
						json.put("valiMessage", this.removeLastString(valiMessage.toString())+" 手机号该时间段内己被限制下单，不可重复设置!");
					}
				}else{
					if(blackList.size() == 0){
						json.put("valiMessage", " 该时间段内己有规则，不可重复设置!");
					}
				}
				super.sendAjaxResult(json.toString());
			}
		} catch (Exception e) {
			json.put("success", false);
			json.put("message", "未知错误!");
			super.sendAjaxResult(json.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * 秒杀产品集、黑名单
	 */
	@Action("/prodblack/insertByProds")
	public void insertBlackListByProds(){
		JSONObject json = new JSONObject();
		try{	
			List<ProdBlackList> blackList = new ArrayList<ProdBlackList>();
			blackList.add(prodBlackList);
			prodBlackListService.insertBlackList(blackList);
			
			json.put("success", true);
			super.sendAjaxResult(json.toString());
		}catch(Exception e){
			json.put("success", false);
			json.put("message", e.getMessage());
			super.sendAjaxResult(json.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * 校验 规则是否己经存在
	 */
	public boolean validateBlackList(ProdBlackList blackList){
		List<ProdBlackList> list = prodBlackListService.queryBlackListByBlacks(blackList);
		if(list != null && list.size() > 0){
			return false;
		}
		return true;
	}
	
	@Action("/prodblack/update")
	public String updateBlackList(){
		
		return "";
	}
	
	@Action("/prodblack/delete")
	public String deleteBlackList(){
		prodBlackListService.deleteBlackList(prodBlackList);
		return this.queryBlacklist(); 
	}
	
	@Action("/prodblack/deleteByProds")
	public void deleteByProds(){
		JSONObject json = new JSONObject();
		try{	
			
			prodBlackListService.deleteBlackList(prodBlackList);
			
			json.put("success", true);
			super.sendAjaxResult(json.toString());
		}catch(Exception e){
			json.put("success", false);
			json.put("message", e.getMessage());
			super.sendAjaxResult(json.toString());
			e.printStackTrace();
		}
	}
	
	@Action("/prodblack/query")
	public String queryBlacklist(){
		Map<String,Object> searchConds = initParam();
		
		toResult(searchConds);
		
		return "query";
	}
	
	@Action("/prodblack/queryByProds")
	public String queryBlacklistByProds(){
		Map<String, Object> searchConds = new HashMap<String, Object>();
		String[] isPhones = {"2","3","4"};
		//查找当前有效的用户限制
		searchConds.put("blackIsPhones", isPhones);
		toResult(searchConds);
		
		return "queryByProds";
	}
	
	@Action("/pub/showBlackDialog")
    public String showBlackDialog() {

        return "showBlackDialog";
    }
	
	@Action("/prodblack/upload")
	public void uploadPhoneNum(){
		JSONObject json = new JSONObject();
		if(this.phoneFile==null){
			json.put("success", false);
			json.put("message", "上传文件为Null");
			return;
		}else{
			try {
				List<String> list = ExcelImport.excImport(new FileInputStream(phoneFile));
				StringBuffer rightBuffer = new StringBuffer();
				StringBuffer wrongBuffer = new StringBuffer();
				StringBuffer repetitionBuffer = new StringBuffer();
				int repetitionNums = 0;
				int rightNums = 0;
				int wrongNums = 0;
				if(list != null && list.size() > 0){
					if(list.size() > 500){
						json.put("success", false);
						json.put("message", "一次最多导入500条数据");
					}else{
						for (String num : list) {
							if(StringUtil.validMobileNumber(num)){
								//校验是否有相同的号码
								if(this.unique(rightBuffer.toString(), num)){
									repetitionBuffer.append(num+",");
									repetitionNums++;
								}else{
									rightBuffer.append(num+",");
									rightNums++;
								}
							}else{
								wrongBuffer.append(num+",");
								wrongNums++;
							}
						}
						json.put("success", true);
						json.put("right", this.removeLastString(rightBuffer.toString()));
						json.put("wrong", this.removeLastString(wrongBuffer.toString()));
						json.put("repetition", this.removeLastString(repetitionBuffer.toString()));
						json.put("repetitionNums", repetitionNums);
						json.put("rigthNums", rightNums);
						json.put("wrongNums", wrongNums);
					}
				}else{
					json.put("success", false);
					json.put("message", "上传文件中无数据");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.sendAjaxResult(json.toString());
	}
	
	public boolean unique(String phoneNums,String phoneNum){
		if(phoneNums.indexOf(phoneNum) != -1){
			return true;
		}else{
			return false;
		}
	}
	public String removeLastString(String string){
		if(string.endsWith(",")){
			return string.substring(0,string.lastIndexOf(","));
		}else{
			return string;
		}
	}
	public Map<String, Object> initParam(){
		Map<String,Object> searchConds = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(phone)){
			searchConds.put("blackPhoneNum", phone);
		}
		if(StringUtils.isNotEmpty(productId)){
			searchConds.put("productId", productId);
		}
		return searchConds;
	}
	
	private void toResult(Map<String,Object> searchConds){
		Integer totalRowCount = prodBlackListService.selectRowCount(searchConds);
		pagination = super.initPagination();
		
		pagination.setTotalRecords(totalRowCount);
		
		searchConds.put("_startRow", pagination.getFirstRow());
		searchConds.put("pageSize", pagination.getPerPageRecord());
		
		List<ProdBlackList> productList = prodBlackListService.queryBlackListByParam(searchConds);
		pagination.setRecords(productList);
		
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		
	}
	
	public ProdBlackList getProdBlackList() {
		return prodBlackList;
	}

	public void setProdBlackList(ProdBlackList prodBlackList) {
		this.prodBlackList = prodBlackList;
	}

	public File getPhoneFile() {
		return phoneFile;
	}

	public void setPhoneFile(File phoneFile) {
		this.phoneFile = phoneFile;
	}

	public String getPhoneFileFileName() {
		return phoneFileFileName;
	}

	public void setPhoneFileFileName(String phoneFileFileName) {
		this.phoneFileFileName = phoneFileFileName;
	}

	public String getPhoneFileContentType() {
		return phoneFileContentType;
	}

	public void setPhoneFileContentType(String phoneFileContentType) {
		this.phoneFileContentType = phoneFileContentType;
	}

	public String getPhoneNums() {
		return phoneNums;
	}

	public void setPhoneNums(String phoneNums) {
		this.phoneNums = phoneNums;
	}

	public void setProdBlackListService(ProdBlackListService prodBlackListService) {
		this.prodBlackListService = prodBlackListService;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
}
