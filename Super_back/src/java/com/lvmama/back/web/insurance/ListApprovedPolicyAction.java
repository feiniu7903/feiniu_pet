package com.lvmama.back.web.insurance;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Filedownload;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.insurance.InsPolicyInfo;
import com.lvmama.comm.bee.service.insurance.PolicyInfoService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.POLICY_RESULT;
import com.lvmama.comm.vo.Constant.POLICY_STATUS;

public class ListApprovedPolicyAction extends BaseAction {
	private static final long serialVersionUID = 5848870783655773582L;

	private PolicyInfoService policyInfoService = (PolicyInfoService) SpringBeanProxy
			.getBean("policyInfoService");
		
	private Map<String,Object> searchConds = new HashMap<String,Object>();
	private List<InsPolicyInfo> policyInfos = new ArrayList<InsPolicyInfo>();
	
	private List<KeyValuePair> policyStatusList = new ArrayList<KeyValuePair>();
	private List<KeyValuePair> policyCompanyList = new ArrayList<KeyValuePair>();
	private String selectedPolicyStatus = "VERIFIED";
	
	@Override
	public void doBefore() {
		policyStatusList.add(new KeyValuePair("全部", POLICY_STATUS.VERIFIED.name()));
		policyStatusList.add(new KeyValuePair("等待投保", "WAITTING_REQUEST"));
		policyStatusList.add(new KeyValuePair("投保成功", "REQUEST_SUCCEED"));
		policyStatusList.add(new KeyValuePair("投保失败", "REQUEST_FAILED"));
		policyStatusList.add(new KeyValuePair("等待废除保单", "WAITTING_CANCEL"));
		policyStatusList.add(new KeyValuePair("废除保单成功", "CANCEL_SUCCEED"));
		policyStatusList.add(new KeyValuePair("废除保单失败", "CANCEL_FAILED"));
		
		policyCompanyList.add(new KeyValuePair("全部", ""));
		policyCompanyList.add(new KeyValuePair("平安保险", "21"));
		policyCompanyList.add(new KeyValuePair("大众保险", "6054"));
		policyCompanyList.add(new KeyValuePair("太平养老", "6624"));
		policyCompanyList.add(new KeyValuePair("阳光保险", "6761"));
		policyCompanyList.add(new KeyValuePair("太平洋保险", "7510"));
	}

	public void loadDataList() {
		searchConds.remove("policyStatus");
		searchConds.remove("policyResult");
		searchConds.remove("policyResultIsNull");
		if ("VERIFIED".equals(selectedPolicyStatus)) {
			searchConds.put("policyStatus", "VERIFIED");
		}
		if ("WAITTING_REQUEST".equals(selectedPolicyStatus)) {
			searchConds.put("policyStatus", POLICY_STATUS.REQUESTED.name());
			searchConds.put("policyResultIsNull", true);
		}
		if ("REQUEST_SUCCEED".equals(selectedPolicyStatus)) {
			searchConds.put("policyStatus", POLICY_STATUS.REQUESTED.name());
			searchConds.put("policyResult", POLICY_RESULT.REQUEST_SUCCESS.name());
		}
		if ("REQUEST_FAILED".equals(selectedPolicyStatus)) {
			searchConds.put("policyStatus", POLICY_STATUS.REQUESTED.name());
			searchConds.put("policyResult", POLICY_RESULT.REQUEST_FAILURE.name());
		}
		if ("WAITTING_CANCEL".equals(selectedPolicyStatus)) {
			searchConds.put("policyStatus", POLICY_STATUS.CANCELLED.name());
			searchConds.put("policyResultIsNull", true);
		}
		if ("CANCEL_SUCCEED".equals(selectedPolicyStatus)) {
			searchConds.put("policyStatus", POLICY_STATUS.CANCELLED.name());
			searchConds.put("policyResult", POLICY_RESULT.CANCEL_SUCCESS.name());
		}
		if ("CANCEL_FAILED".equals(selectedPolicyStatus)) {
			searchConds.put("policyStatus", POLICY_STATUS.CANCELLED.name());
			searchConds.put("policyResult", POLICY_RESULT.CANCEL_FAIL.name());
		}
		searchConds = initialPageInfoByMap(policyInfoService.countInsPolicyInfo(searchConds), searchConds);
		policyInfos = policyInfoService.query(searchConds);
	}
	
	
	/**
	 * @author lancey
	 * 导出查询清单为execl
	 */
	public void doExportPolicy()
	{
		Map<String,Object> searchCondsClone=new HashMap<String, Object>();
		searchCondsClone.putAll(searchConds);
		if(searchCondsClone.containsKey("maxResults"))
			searchCondsClone.remove("maxResults");
		List<InsPolicyInfo> list=policyInfoService.queryForReport(searchCondsClone);
		doExcel(list, "/WEB-INF/resources/template/policyListTemplate.xls");
	}
	
	public void cancel(Map<String,Object> parameters) {
		if (null != parameters && null != parameters.get("policyId")) {
			final Long policyId = (Long) parameters.get("policyId");
			ZkMessage.showQuestion("您确定需要废除此保单吗?", new ZkMsgCallBack() {
				public void execute() {
					InsPolicyInfo policy = policyInfoService.queryByPK(policyId);
					if (null != policy) {
						policy.setPolicyStatus(Constant.POLICY_STATUS.CANCELLED.name());
						policy.setPolicyResult(null);
						policyInfoService.update(policy);
						ZkMessage.showInfo("已提交作废申请，请稍后刷新列表!");
					} else {
						ZkMessage.showInfo("对不起，我没有找到需要作废的保单");
					}			
				}
			}, new ZkMsgCallBack() {
				public void execute() {
				}
			});				
		}	
	}
	
	
	/**
	 * @author yangbin
	 * 代码从Super_report复制过来
	 * 导出execl文件
	 * @param excelList
	 * @param path
	 */
	private void doExcel(List excelList,String path){
		try {
			File templateResource = ResourceUtil.getResourceFile(path);
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/excel.xls";

			Map beans = new HashMap();
			beans.put("excelList", excelList);
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, beans, destFileName);

			File file = new File(destFileName);
			if (file != null && file.exists()) {
				Filedownload.save(file, "application/vnd.ms-excel");
			} else {
				alert("下载失败");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void changeStatus(String value) {
		selectedPolicyStatus = value;
	}
	
	public void changeCompany(String value) {
		if (StringUtils.isBlank(value)) {
			searchConds.remove("supplierId");
		} else {
			searchConds.put("supplierId", Long.valueOf(value));
		}
	}

	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}

	public List<InsPolicyInfo> getPolicyInfos() {
		return policyInfos;
	}

	public List<KeyValuePair> getPolicyStatusList() {
		return policyStatusList;
	}

	public List<KeyValuePair> getPolicyCompanyList() {
		return policyCompanyList;
	}
}
