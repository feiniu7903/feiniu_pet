package com.lvmama.back.sweb.distribution;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.distribution.DistributorTuanInfo;
import com.lvmama.comm.bee.service.distribution.DistributionTuanService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name = "searchListDistributor", location = "/WEB-INF/pages/back/distribution/distributor/searchListTuanDistributor.jsp"),
	@Result(name = "saveOrUpdateDistributor", location = "/WEB-INF/pages/back/distribution/distributor/saveOrUpdateTuanDistributor.jsp"),
	@Result(name = "searchDistributor", location = "/WEB-INF/pages/back/distribution/distributor/saveOrUpdateTuanDistributor.jsp")
})
@ParentPackage("json-default")
public class DistributorTuanManagementAction extends BackBaseAction{

	private static final long serialVersionUID = -1177388763818982952L;
	/**分销商名称*/
	private String distributorName;
	/**分销商Code*/
	private String distributorCode;
	/**分销商渠道类型 */
	private String channelType;
	/**分销商Id*/
	private Long distributorTuanInfoId;
	private String memo;
	/**分销商*/
	private DistributorTuanInfo distributor = new DistributorTuanInfo();
	/** 分销商分页 */
	private Page<DistributorTuanInfo> distributorPage = new Page<DistributorTuanInfo>();
	/** 关于分销的服务*/
	private DistributionTuanService distributionTuanService;
	
	/**	 查询分销商列表 */
	@Action("/distributiontuan/searchListDistributor")
	public String searchListDistributor(){
		Map<String, Object> params = new HashMap<String, Object>();
		/*分销商名称*/
		if (StringUtils.isNotBlank(this.distributorName)) {
			params.put("distributorName", this.distributorName.trim());
		}
		/*分销商id*/
		if(StringUtils.isNotBlank(distributorCode)){
			params.put("distributorCode", distributorCode.trim());
		}
		/**分销商类型*/
		if(StringUtils.isNotBlank(channelType)){
			params.put("channelType", channelType.trim());
		}
		distributorPage.setTotalResultSize(distributionTuanService.queryDistributorInfoCount(params));
		distributorPage.buildUrl(getRequest());
		distributorPage.setCurrentPage(super.page);
		params.put("start", distributorPage.getStartRows());
		params.put("end", distributorPage.getEndRows());
		if(distributorPage.getTotalResultSize()>0){
			distributorPage.setItems(distributionTuanService.selectDistributorByParams(params));
		}
		
		return "searchListDistributor";
	}
	
	/**
	 * 根据分销商code查询分销商
	 * @return
	 */
	@Action("/distributiontuan/searchDistributor")
	public String searchDistributor(){
		distributor= this.distributionTuanService.getDistributorTuanById(distributorTuanInfoId);
		return "searchDistributor";
	}
	
	/**
	 * 新增或修改分销商
	 */
	@Action("/distributiontuan/saveOrUpdateDistributor")
	public void saveOrUpdateDistributor(){
		ResultHandle result = new ResultHandle();
		DistributorTuanInfo distributor = new DistributorTuanInfo();
		distributor.setChannelType(channelType);
		distributor.setDistributorCode(distributorCode);
		distributor.setMemo(memo);
		distributor.setDistributorName(distributorName);
		if(distributorTuanInfoId == null){
			if(Constant.CHANNEL.EXPORT_DIEM.name().equals(channelType)){
				distributor.setPaymentCode(DistributorTuanInfo.PAYMENT_PREFIX.DIST_DAOMA_.name()+distributorCode);
			}
			if(Constant.CHANNEL.DIST_YUYUE.name().equals(channelType)){
				distributor.setPaymentCode(DistributorTuanInfo.PAYMENT_PREFIX.DIST_YUYUE_.name()+distributorCode);
			}
			try {
				this.distributionTuanService.insert(distributor);
				this.distributionTuanService.insertPamentGetWay(distributor.getPaymentCode(), distributor.getDistributorName());
				result.setMsg("新增成功");
			} catch (Exception e) {
				result.setMsg("新增失败");
				log.error("saveTuanDistributor",e);
			}
		}else{
			try {
				distributor.setDistributorTuanInfoId(distributorTuanInfoId);
				this.distributionTuanService.update(distributor);
				result.setMsg("更新成功");
			} catch (Exception e) {
				result.setMsg("更新失败");
				log.error("updateTuanDistributor",e);
			}
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	
	/**
	 * 修改分销产品设置 
	 */
	@Action("/distribution/editDistributionPro")
	public String editDistributionPro(){
		return "editDistributionPro";
	}
	
	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public void setDistributorCode(String distributorCode) {
		this.distributorCode = distributorCode;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setDistributor(DistributorTuanInfo distributor) {
		this.distributor = distributor;
	}

	public void setDistributorPage(Page<DistributorTuanInfo> distributorPage) {
		this.distributorPage = distributorPage;
	}

	
	public String getDistributorCode() {
		return distributorCode;
	}

	public String getChannelType() {
		return channelType;
	}

	public Long getDistributorTuanInfoId() {
		return distributorTuanInfoId;
	}

	public String getMemo() {
		return memo;
	}

	public Page<DistributorTuanInfo> getDistributorPage() {
		return distributorPage;
	}

	public void setDistributionTuanService(
			DistributionTuanService distributionTuanService) {
		this.distributionTuanService = distributionTuanService;
	}

	public void setDistributorTuanInfoId(Long distributorTuanInfoId) {
		this.distributorTuanInfoId = distributorTuanInfoId;
	}

	public DistributorTuanInfo getDistributor() {
		return distributor;
	}
	


}
