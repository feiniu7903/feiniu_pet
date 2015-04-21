package com.lvmama.pet.job.quartz;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.po.perm.PermOrganization;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.service.perm.PermOrganizationService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.sup.SupContractService;
import com.lvmama.comm.utils.Configuration;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
 * 周一至周五发送30天到期与50天到期的合同邮件提醒
 * @author chenkeke
 *
 */
public class ContractExpiredEmailSendJob implements Runnable{
	private static Log LOG = LogFactory.getLog(ContractExpiredEmailSendJob.class);
	private EmailClient emailClient;
	private SupContractService supContractService;
	private PermUserService permUserService;
	private PermOrganizationService permOrganizationService;
	private String from;
    private freemarker.template.Configuration cfg;  
    private Template template; 
    private Constant constant =  Constant.getInstance();
	@Override
	public void run() {
		//JOB启动
		if (Constant.getInstance().isJobRunnable()  && Constant.getInstance().isJobRunnable("ContractExpiredEmailSendJob")) {
			LOG.info("Auto ContractExpiredEmailSendJob running.....");
			if(StringUtil.isEmptyString(from)){
				try {
					from = Configuration.getConfiguration().getPropertyValue("email.properties", "mail.from.address");	
				} catch (Exception e) {
				}
			}
			if(cfg==null){
		        try {

			        cfg = new freemarker.template.Configuration();  
			         //设置模板文件目录  			        
					cfg.setDirectoryForTemplateLoading(ResourceUtil.getResourceFile("/WEB-INF/resources/contractExpiredTemplate/")); 
			         // 取得模板文件  
			        template = cfg.getTemplate("email_template.ftl");  
				} catch (IOException e) {
					LOG.error("ContractExpiredEmailSendJob error:"+e.getMessage());
					return;
				} 
			}
			//发送还有5天到期的合同邮件提醒
			this.sendMail(5);
			//发送还有30天到期的合同邮件提醒
			this.sendMail(30);
			LOG.info("Auto ContractExpiredEmailSendJob end.....");
		}
	}
	private void sendMail(int days){
		Map<String, Object> params = new HashMap<String, Object>();
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		//周一时,开始日期前去2天(目的是将上周末的邮件集中在本周一发)
		if(dayOfWeek-1==1){
			params.put("startDays", days-2);
		}else{
			params.put("startDays", days);
		}
		params.put("endDays", days);
		List<SupContract> supContracts = supContractService.selectContractExpiredList(params);		
		Map<Long, List<SupContract>> sendMap = new HashMap<Long, List<SupContract>>(); 
		List<SupContract> sendList = new ArrayList<SupContract>();
		for (SupContract supContract : supContracts) {
			if(sendMap.get(supContract.getManagerId())!=null){
				sendList = sendMap.get(supContract.getManagerId());
			}else{
				sendList = new ArrayList<SupContract>();
			}
			sendList.add(supContract);
			sendMap.put(supContract.getManagerId(), sendList);
		}
		Iterator<Entry<Long, List<SupContract>>>  iterator = sendMap.entrySet().iterator();
		PermUser managerUser = null;
		Long managerId = null;
		Entry<Long, List<SupContract>> entry = null;
		while (iterator.hasNext()) {
			entry= iterator.next();
			managerId = entry.getKey();				
			sendList= entry.getValue();
			managerUser = permUserService.getPermUserByUserId(managerId);
			if(managerUser==null || StringUtil.isEmptyString(managerUser.getEmail())){
				LOG.info("ContractExpiredEmailSendJob managerId:"+managerId+" User could not be found or the e-mail address is empty can't send");
				continue;
			}
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put("days", days);
			rootMap.put("managerName", managerUser.getRealName());
			rootMap.put("supContractList", sendList);
			StringWriter stringWriter = new StringWriter();  
	        try {
				template.process(rootMap, stringWriter);
				EmailContent email = new EmailContent();
				email.setFromAddress(from);
				email.setFromName("后台系统");
				email.setSubject("合同即将到期提醒");
				email.setContentText(stringWriter.toString());
				email.setCcAddress(this.getCcAddress(managerUser.getDepartmentId()));
				email.setToAddress(managerUser.getEmail());
				emailClient.sendEmailDirect(email);
			} catch (Exception e) {
				LOG.error("ContractExpiredEmailSendJob error:"+e.getMessage());
			}   
		}
	}
	/**
	 * 根据部门ID取得抄送的用户Email
	 * @param departmentId
	 * @return
	 */
	private String getCcAddress(Long departmentId){
		StringBuffer ccAddress = new StringBuffer();
		String freenessOrgIds = constant.getProperty("contractExpiredEmailSend.freeness.orgIds");
		String freenessCcAddress = constant.getProperty("contractExpiredEmailSend.freeness.ccAddress");
		String foreignOrgIds = constant.getProperty("contractExpiredEmailSend.foreign.orgIds");
		String foreignCcAddress = constant.getProperty("contractExpiredEmailSend.foreign.ccAddress");
		String longOrgIds = constant.getProperty("contractExpiredEmailSend.long.orgIds");
		String longCcAddress = constant.getProperty("contractExpiredEmailSend.long.ccAddress");
		//特殊处理 如果是国内自助游中心下的长线部 算成国内长线中心
		if(isEqualsOrgId("366",departmentId)  && !StringUtil.isEmptyString(longCcAddress)){
			if(ccAddress.toString().trim().length()>0){
				ccAddress.append(",");
			}
			ccAddress.append(longCcAddress);
		}else{

			//是否是国内自由行组
			if(isEqualsOrgId(freenessOrgIds, departmentId) && !StringUtil.isEmptyString(freenessCcAddress)){
				if(ccAddress.toString().trim().length()>0){
					ccAddress.append(",");
				}
				ccAddress.append(freenessCcAddress);
			}
			//是否是出境产品中心
			if(isEqualsOrgId(foreignOrgIds, departmentId) && !StringUtil.isEmptyString(foreignCcAddress)){
				if(ccAddress.toString().trim().length()>0){
					ccAddress.append(",");
				}
				ccAddress.append(foreignCcAddress);
			}
			//是否是国内长线
			if(isEqualsOrgId(longOrgIds, departmentId) && !StringUtil.isEmptyString(longCcAddress)){
				if(ccAddress.toString().trim().length()>0){
					ccAddress.append(",");
				}
				ccAddress.append(longCcAddress);
			}
		}
		
		return ccAddress.toString().replaceAll("^\\,+|\\,+$","");//去除两头的逗号(,)
	}
	/**
	 * 取得有相同的ORGID （包含上级组织）
	 * @param orgIds
	 * @param departmentId
	 * @return
	 */
	private boolean isEqualsOrgId(String orgIds,Long departmentId){
		boolean exists = false;
		PermOrganization permOrganization =null;
		do {
			if(!StringUtil.isEmptyString(orgIds)){
				for (String orgId : orgIds.split(",")) {
					try {
						if(Long.parseLong(orgId)==departmentId){
							exists = true;
							break;
						}
					} catch (Exception e) {
					}
				}
			}
			if(exists){
				break;
			}
			permOrganization =permOrganizationService.getOrganizationByOrgId(departmentId);
			departmentId = permOrganization!=null?permOrganization.getParentOrgId():null;
		} while (departmentId!=null && permOrganization!=null && permOrganization.getPermLevel()!=1);
		
		return exists;
		
	}
	public EmailClient getEmailClient() {
		return emailClient;
	}
	public void setEmailClient(EmailClient emailClient) {
		this.emailClient = emailClient;
	}
	public SupContractService getSupContractService() {
		return supContractService;
	}
	public void setSupContractService(SupContractService supContractService) {
		this.supContractService = supContractService;
	}
	public PermUserService getPermUserService() {
		return permUserService;
	}
	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}
	public PermOrganizationService getPermOrganizationService() {
		return permOrganizationService;
	}
	public void setPermOrganizationService(
			PermOrganizationService permOrganizationService) {
		this.permOrganizationService = permOrganizationService;
	}

}
