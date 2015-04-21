package com.lvmama.report.web.membershipCard;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jxls.transformer.XLSTransformer;

import org.zkoss.zul.Filedownload;

import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.report.po.MembershipCardDetailsMV;
import com.lvmama.report.service.MembershipCardService;
import com.lvmama.report.web.BaseAction;
public class ListMembershipCardAction extends BaseAction {
private static final long serialVersionUID = -5367638041381632064L;
	
	private MembershipCardService membershipCardService;

	private List<MembershipCardDetailsMV> listMembershipCardDetails=new ArrayList<MembershipCardDetailsMV>();
	private List<CodeItem>   statusList;
	private List<CodeItem>   activePercentList;
	private List<CodeItem>   salesList;
	
	//查询条件
	private Map<String,Object> searchConds = new HashMap<String,Object>();
	
	public void doBefore() throws Exception{
		setList();
	}
	/**
	 * 根据查询内容查询结果
	 */
	public void loadDataList()throws Exception {
		//searchConds = initialPageInfoByMap(membershipCardService.count(searchConds), searchConds);
		if(validate()){
			listMembershipCardDetails = membershipCardService.query(searchConds);
			count();
		}
	}
	public void download() {
		try {
			File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/membershipCardTemplate.xls");
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/membershipCardCode.xls";
			
			Map<String,Object> param = new HashMap<String,Object>();

			param.put("listMembershipCardDetails", listMembershipCardDetails);
			param.put("floorCount", searchConds.get("floorCount"));
			
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, param, destFileName);
			File file = new File(destFileName);
			if (file != null && file.exists()) {
				Filedownload.save(file, "application/vnd.ms-excel");
			} else {
				alert("下载失败");
				return;
			}
			alert("下载成功");
		} catch (Exception e) {
			alert(e.getMessage());
		}
	}
	private void setList(){
		statusList=new ArrayList<CodeItem>();
		activePercentList=new ArrayList<CodeItem>();
		statusList.add(new CodeItem(null,"请选择"));
		statusList.add(new CodeItem("TRUE","已激活"));
		statusList.add(new CodeItem("FALSE","未激活"));
		activePercentList.add(new CodeItem(null,"请选择"));
		activePercentList.add(new CodeItem("ASC","从小到大"));
		activePercentList.add(new CodeItem("DESC","从大到小"));
		salesList=activePercentList;
	}
	private void count(){
		Set<Object> set=new HashSet<Object>();
		long cardCount=0,activedCountSum=0,unactivedCountSum=0,buyCountSum=0,actualPersonSum=0;
		Float actualSumSum=0F,activedCardsumSum=0F;
		for(MembershipCardDetailsMV obj: listMembershipCardDetails){
			set.add(obj.getChannelCode());
			cardCount+=obj.getAmount();
			activedCountSum+=obj.getActivedCount();
			unactivedCountSum+=obj.getUnactiveCount();
			buyCountSum+=obj.getBuyCount();
			actualPersonSum+=obj.getActualPerson();
			actualSumSum+=obj.getActualSum();
			activedCardsumSum+=obj.getActivedCardSum();
			setActivePercent(obj);
		}
		String floorStr="共"+set.size()+"个渠道，发卡总数"+
		                cardCount+"张，已激活数"+activedCountSum+"张，未激活数"+
		                unactivedCountSum+"张，总激活率"+
		                ((float)((float)(activedCountSum*10000)/(float)((activedCountSum+unactivedCountSum)>0?(activedCountSum+unactivedCountSum):1L)))/100+"%，订购人数"+
		                buyCountSum+"人，实付"+actualPersonSum+"人，销售总额"+ (float)(Math.round(actualSumSum*100))/100+"元"
		                +"，会员卡激活后销售总额"+activedCardsumSum+"元";
		searchConds.put("floorCount", floorStr);
	}
	private boolean validate()throws Exception{
		if(searchConds.get("beginAmount")!=null){
			String beginAmount=(String)searchConds.get("beginAmount");
			if(!(beginAmount.trim().matches("\\d*"))){
				alert("发卡开始数量请输入数字");
				return false;
			};
		}
		if(searchConds.get("endAmount")!=null){
			String beginAmount=(String)searchConds.get("endAmount");
			if(!(beginAmount.trim().matches("\\d*"))){
				alert("发卡结束数量请输入数字");
				return false;
			};
		}
		return true;
	}
	public void setActivePercent(MembershipCardDetailsMV obj) {
		Integer activedCount=obj.getActivedCount();
		Integer unactiveCount=obj.getUnactiveCount();
		String str="%";
		if(activedCount==null){
			activedCount=0;
		}
		if(unactiveCount==null){
			unactiveCount=0;
		}
		float i=unactiveCount+activedCount;
		if(i==0){
			i=1;
		}
		float f=((float)activedCount*100)/i;
		obj.setActivePercent(f+str);
	}
	public MembershipCardService getMembershipCardService() {
		return membershipCardService;
	}

	public void setMembershipCardService(MembershipCardService membershipCardService) {
		this.membershipCardService = membershipCardService;
	}

	public List<MembershipCardDetailsMV> getListMembershipCardDetails() {
		return listMembershipCardDetails;
	}

	public void setListMembershipCardDetails(
			List<MembershipCardDetailsMV> listMembershipCardDetails) {
		this.listMembershipCardDetails = listMembershipCardDetails;
	}

	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}
	public List<CodeItem> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<CodeItem> statusList) {
		this.statusList = statusList;
	}
	public List<CodeItem> getActivePercentList() {
		return activePercentList;
	}
	public void setActivePercentList(List<CodeItem> activePercentList) {
		this.activePercentList = activePercentList;
	}
	public List<CodeItem> getSalesList() {
		return salesList;
	}
	public void setSalesList(List<CodeItem> salesList) {
		this.salesList = salesList;
	}
}
