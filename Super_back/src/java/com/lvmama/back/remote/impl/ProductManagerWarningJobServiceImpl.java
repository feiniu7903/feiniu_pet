package com.lvmama.back.remote.impl;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.back.remote.ProductManagerWarningJobService;
import com.lvmama.back.remote.impl.template.BuyOutStockWarningEmailTemplate;
import com.lvmama.comm.bee.service.meta.MetaProductControlService;
import com.lvmama.comm.bee.vo.view.ViewMetaProductControl;
import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * 任务说明:根据预控配置发送预警邮件给产品经理
 * @author zuoxiaoshuai
 *
 */
public class ProductManagerWarningJobServiceImpl implements ProductManagerWarningJobService, Serializable {

    private static final long serialVersionUID = 3447543756084801777L;
    
    static Log LOG = LogFactory.getLog(ProductManagerWarningJobServiceImpl.class);

    public TaskResult execute(Long logId,String parameter) throws Exception {
    	TaskResult  result = new TaskResult();
    	LOG.info(String.format("Task ProductManagerWarningJob [%d], parameter [%s], begining ------------------", logId, parameter));
    	MetaProductControlService metaProductControlService =
			 	(MetaProductControlService) SpringBeanProxy.getBean("metaProductControlService");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String warningTime = dateFormat.format(new Date());
        Map<String, Object> searchConds = new HashMap<String, Object>();
        searchConds.put("_startRow", 0);
        searchConds.put("_endRow", 4000);
        searchConds.put("roleArea", Constant.PRODUCT_CONTROL_ROLE.ROLE_ALL);
        searchConds.put("time", 1);
        List<ViewMetaProductControl> firstViewList = metaProductControlService.getWithoutTotalQuantityViewControlList(searchConds);
        checkViewList(firstViewList, 1, warningTime);
        
        searchConds.put("time", 2);
        List<ViewMetaProductControl> secondViewList = metaProductControlService.getWithoutTotalQuantityViewControlList(searchConds);
        checkViewList(secondViewList, 2, warningTime);
        
        searchConds.put("time", 3);
        List<ViewMetaProductControl> thirdViewList = metaProductControlService.getWithoutTotalQuantityViewControlList(searchConds);
        checkViewList(thirdViewList, 3, warningTime);
        
        searchConds.put("finish", true);
        searchConds.remove("time");
        List<ViewMetaProductControl> finishViewList = metaProductControlService.getWithoutTotalQuantityViewControlList(searchConds);
        sendFinishEmails(finishViewList);
        
        result.setStatus(1);//完成
        return result;
    }

    
	private void sendFinishEmails(List<ViewMetaProductControl> finishViewList) {
		EmailClient emailClient = (EmailClient) SpringBeanProxy.getBean("emailClient");
		for (ViewMetaProductControl control : finishViewList) {
			Map<String, String> param = new HashMap<String, String>();
    		param.put("${productName}", control.getProductName());
    		param.put("${productId}", control.getProductId() + "");
    		String emailContent = BuyOutStockWarningEmailTemplate.getFinishSaleEmailContent(param);
			
			EmailContent content = new EmailContent();
    		content.setFromAddress("service@cs.lvmama.com"); 
    		content.setFromName("驴妈妈旅游网"); 
    		content.setSubject("预警邮件"); 
    		content.setToAddress(control.getManagerEmail());
    		content.setContentText(emailContent); 
    		emailClient.sendEmail(content);
		}
	}


	private void checkViewList(List<ViewMetaProductControl> viewList, int time, String warningTime) {
		EmailClient emailClient = (EmailClient) SpringBeanProxy.getBean("emailClient");
		DecimalFormat df = new DecimalFormat("#.00");
		for (ViewMetaProductControl control : viewList) {
			//勾选未达到提醒
			if ("true".equals(control.getNotGot())) {
				if (control.getSaleQuantity() < control.getHoopQuantity()) {
					Map<String, String> param = new HashMap<String, String>();
	        		param.put("${productName}", control.getProductName());
	        		param.put("${productId}", control.getProductId() + "");
	        		param.put("${warningTime}", warningTime);
	        		String emailContent = BuyOutStockWarningEmailTemplate.getNotGotEmailContent(param);
					
					EmailContent content = new EmailContent();
	        		content.setFromAddress("service@cs.lvmama.com"); 
	        		content.setFromName("驴妈妈旅游网"); 
	        		content.setSubject("预警邮件"); 
	        		content.setToAddress(control.getManagerEmail());
	        		content.setContentText(emailContent); 
	        		emailClient.sendEmail(content);
					return;
				}
			}
			
        	//当实际销量/应销量>=阀值时
			Long lv = (time == 1) ? control.getFirstLevel() : (time == 2) ? control.getSecondLevel() : control.getThirdLevel();
			double levelValue = (lv * 1.0) / 100;
			double scale = 1.0 - control.getSaleQuantity() * 1.0 / control.getHoopQuantity();
        	if (scale >= levelValue) {
        		if (StringUtils.isEmpty(control.getManagerEmail())) {
        			LOG.info(String.format("The manager email of product [Name = %s, ID = %d] is empty", control.getProductName(), control.getProductId()));
        			continue;
        		}
        		Map<String, String> param = new HashMap<String, String>();
        		param.put("${productName}", control.getProductName());
        		param.put("${productId}", control.getProductId() + "");
        		param.put("${warningTime}", warningTime);
        		//1-实际销量/应销量
        		param.put("${levelValue}", df.format(scale * 100.0) + "%");
        		String emailContent = BuyOutStockWarningEmailTemplate.getEmailContent(param);
        		EmailContent content = new EmailContent();
        		content.setFromAddress("service@cs.lvmama.com"); 
        		content.setFromName("驴妈妈旅游网"); 
        		content.setSubject("预警邮件"); 
        		content.setToAddress(control.getManagerEmail()); 
        		content.setContentText(emailContent); 
        		emailClient.sendEmail(content);
        	}
        }
	}
}
