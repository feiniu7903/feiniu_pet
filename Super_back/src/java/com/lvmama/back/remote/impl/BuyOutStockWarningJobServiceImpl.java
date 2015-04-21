package com.lvmama.back.remote.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.back.remote.BuyOutStockWarningJobService;
import com.lvmama.comm.bee.po.meta.ProductControlRole;
import com.lvmama.comm.bee.service.meta.MetaProductControlService;
import com.lvmama.comm.bee.service.meta.ProductControlRoleService;
import com.lvmama.comm.bee.vo.view.ViewMetaProductControl;
import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * 任务说明:发送预控报表给配置了报表权限的人员
 * @author zuoxiaoshuai
 *
 */
public class BuyOutStockWarningJobServiceImpl implements BuyOutStockWarningJobService, Serializable {

    private static final long serialVersionUID = 3447543756084801777L;
    
    static Log LOG = LogFactory.getLog(BuyOutStockWarningJobServiceImpl.class);

    public TaskResult execute(Long logId,String parameter) throws Exception {
    	TaskResult  result = new TaskResult();
    	LOG.info(String.format("Task uyOutStockWarning [%d], parameter [%s], begining ------------------", logId, parameter));
        ProductControlRoleService productControlRoleService = (ProductControlRoleService) SpringBeanProxy.getBean("productControlRoleService");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("_endRow", 4000);
        List<ProductControlRole> roleList = productControlRoleService.getRoleListByCondition(param);
        LOG.info(String.format("Task uyOutStockWarning , roleList size is [%d]", roleList.size()));
        for (ProductControlRole role : roleList) {
        	execute(role);
        }
        result.setStatus(1);//完成
        return result;
    }

	private void execute(ProductControlRole role) throws Exception {
		MetaProductControlService metaProductControlService =
				 	(MetaProductControlService) SpringBeanProxy.getBean("metaProductControlService");
		PermUserService permUserService = (PermUserService) SpringBeanProxy.getBean("permUserService");
		PermUser user = permUserService.getPermUserByUserId(role.getUserId());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", role.getUserId());
		param.put("roleArea", role.getRoleArea());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date reportDate = format.parse(format.format(new Date()));
		param.put("reportDate", reportDate);
		if (Constant.PRODUCT_CONTROL_ROLE.ROLE_MANAGER.name().equals(
			role.getRoleArea())) {
			param.put("managerList", role.getManagerIdList().replace("[", "")
						.replace("]", ""));
		}
		List<ViewMetaProductControl> views = metaProductControlService.getReportViewControlList(param);
		if (views.size() == 0) {
			LOG.info("User [" + user.getRealName() + "] has no report data.");
			return;
		}
		String now = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
		EmailClient emailClient = (EmailClient) SpringBeanProxy.getBean("emailClient");
		EmailContent content = new EmailContent();
		content.setFromAddress("service@cs.lvmama.com"); 
		content.setFromName("驴妈妈旅游网"); 
		content.setSubject(now + "-预控报表"); 
		content.setToAddress(user.getEmail()); 
		content.setContentText(createEmailContent(views));
		emailClient.sendEmail(content);
	}

	private String createEmailContent(List<ViewMetaProductControl> views) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<html><head><meta http-equiv=Content-Type content=\"text/html;charset=utf-8\"></head><body>");
		buffer.append("<table cellpadding=0 cellspacing=0 border=1><tr style=\"background-color:#99BBE8\">");
		buffer.append("<th>所属中心</th><th>申请人</th><th>预控级别</th><th>采购产品ID</th><th>采购产品名称</th>");
		buffer.append("<th>类别ID</th><th>类别名</th><th>供应商编号</th><th>供应商名称</th><th>可延期</th>");
		buffer.append("<th>可退</th><th>销售起始日期</th><th>销售截止日期</th><th>使用有效起始日</th><th>使用有效结束日</th>");
		buffer.append("<th>预控数量</th><th>应销量</th><th>买断产品实际销量</th><th>总销量</th><th>资源剩余量</th>");
		buffer.append("<th>资源出库率</th><th>预警值</th>");
		buffer.append("</tr>");
		for (ViewMetaProductControl view : views) {
			buffer.append("<tr>");
			buffer.append(String.format("<td>%s</td><td>%s</td><td>%s</td><td>%d</td><td>%s</td>",
					StringUtils.trimToEmpty(view.getBelongCenter()),
					StringUtils.trimToEmpty(view.getApplierName()),
					Constant.PRODUCT_CONTROL_TYPE.getCnName(view.getControlType()),
					view.getProductId(),
					StringUtils.trimToEmpty(view.getProductName())
					));
			buffer.append(String.format("<td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td>",
					view.getProductBranchId() == null ? "" : view.getProductBranchId().toString(),
					StringUtils.trimToEmpty(view.getBranchName()),
					view.getSupplierId() == null ? "" : view.getSupplierId().toString(),
					StringUtils.trimToEmpty(view.getSupplierName()),
					view.getDelayAbleStr()
					));
			buffer.append(String.format("<td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td>",
					view.getBackAbleStr(),
					view.getSaleStartDateStr(),
					view.getSaleEndDateStr(),
					view.getStartDateStr(),
					view.getEndDateStr()
					));
			buffer.append(String.format("<td>%d</td><td>%d</td><td>%d</td><td>%d</td><td>%d</td>",
					view.getControlQuantity(),
					view.getHoopQuantity(),
					view.getSaleQuantity(),
					view.getTotalQuantity(),
					view.getLeaveQuantity()
					));
			buffer.append(String.format("<td>%.2f</td><td>%.2f</td>",
					view.getOutStockScale(),
					view.getWarningValue()
					));
			buffer.append("</tr>");
		}
		buffer.append("</table></body></html>");		
		return buffer.toString();
	}
}
