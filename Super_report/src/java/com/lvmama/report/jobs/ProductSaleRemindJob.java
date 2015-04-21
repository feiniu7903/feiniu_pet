package com.lvmama.report.jobs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.report.service.StockInsufficientService;

/**
 * 产品售卖日期临近过期时系统邮件提醒功能
 * @author shangzhengyuan
 * @createDate 2012-09-18
 */
public class ProductSaleRemindJob implements Runnable {
	private static final Logger LOG = Logger.getLogger(ProductSaleRemindJob.class);
	private static final String URL_FIXED = "http://www.lvmama.com/product/";
	private StockInsufficientService stockInsufficientService;
	 
	private String mailFrom;
	private String subject;
	private EmailClient emailClient;
	@Override
	public void run() {
		boolean jobRunnable = Constant.getInstance().isJobRunnable();
		boolean isRun = false;
		if(!"true".equals(Constant.getInstance().getProperty("product.sale.remind.enabled"))){
			LOG.info("ProductSaleRemindJob product.sale.remind.enabled=false");
		}else{
			isRun=true;
		}
		
		if (jobRunnable && isRun) {
			final String TD ="<td>";  //TD标签
			final String _TD ="</td>";//TD结束标签
			//1.读取邮件初始内容
			String emailInitContent = readEmailContent();
			if(StringUtil.isEmptyString(emailInitContent)){
				LOG.warn("ProductSaleRemind not find template");
				return ;
			}
			//获取库存不足信息列表
			List<Map<String,Object>> result =null;
			try{
				result =  stockInsufficientService.productSaleRemindList();
			}catch(Exception e){
				LOG.warn(e.getMessage());
			}
			
			if(result.size() == 0){
				LOG.info("data not find!");
				return ;
			}
			//提取信息内容
			EmailContent emailContent=new EmailContent();
			Map<String,String> sendMap = new HashMap<String,String>();
			for(Map<String,Object> entry:result){
				//取得邮箱
				String email = (String)entry.get("EMAIL");
				if(!StringUtil.isEmptyString(email)){
					BigDecimal productId = (BigDecimal)entry.get("PRODUCT_ID");
					String productName = (String)entry.get("PRODUCT_NAME");
					BigDecimal prodBranchId = (BigDecimal)entry.get("PROD_BRANCH_ID");
					String branchName = (String)entry.get("BRANCH_NAME");
					String specDate = (String)entry.get("SPEC_DATE");
					StringBuffer sb = new StringBuffer();
					sb.append("<tr>")
					  .append(TD).append("<a href='").append(URL_FIXED).append(productId).append("'>").append(productName).append("</a>").append(_TD)
					  .append(TD).append(productId).append(_TD)
					  .append(TD).append(prodBranchId).append(_TD)
					  .append(TD).append(branchName).append(_TD)
					  .append(TD).append(specDate).append(_TD)
					  .append("</tr>");
					if(null == sendMap.get(email)){
						sendMap.put(email, sb.toString());
					}else{
						sendMap.put(email, sendMap.get(email)+sb.toString());
					}
				}
			}
			//清除结果集，释放内存
			result.clear();
			Iterator<String> iterator = sendMap.keySet().iterator();
			while(iterator.hasNext()){
				String email = iterator.next();
				String value = sendMap.get(email);
				try {
					emailContent.setFromAddress(mailFrom);
					emailContent.setFromName("驴妈妈旅游网");
					emailContent.setSubject(subject);
					emailContent.setToAddress(email);
					Map<String,Object> paramters = new HashMap<String,Object>();
					paramters.put("productList", value);
					String content1 = StringUtil.composeMessage(emailInitContent, paramters);
					emailContent.setContentText(content1);
					emailClient.sendEmail(emailContent);
				} catch (Exception e) {
					LOG.warn("send email error ("+email+")  content\r\n"+value+"\r\n"+e);
				}
			}
			sendMap.clear();
		}
	}
	public EmailClient getEmailClient() {
		return emailClient;
	}
	private String readEmailContent(){
		try {
			StringBuffer sb = new StringBuffer();
			File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/productSaleRemind.template");
			FileReader fileread=new FileReader(templateResource);
			BufferedReader bufread=new BufferedReader(fileread);
			String line;
			while((line=bufread.readLine())!=null){
				sb.append(line+"\n");
			}
			bufread.close();
			return sb.toString();
		} catch (IOException e) {
			LOG.warn("read email content error IOEXCEPTION \r\n"+e.getMessage());
			return null;
		}
	}
	public StockInsufficientService getStockInsufficientService() {
		return stockInsufficientService;
	}
	public void setStockInsufficientService(
			StockInsufficientService stockInsufficientService) {
		this.stockInsufficientService = stockInsufficientService;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setEmailClient(EmailClient emailClient) {
		this.emailClient = emailClient;
	}

}
