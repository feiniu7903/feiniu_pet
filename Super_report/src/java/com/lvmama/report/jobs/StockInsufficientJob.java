package com.lvmama.report.jobs;
/**
 * 库存不足查询
 * @author shangzhengyuan
 * @createDate 2012-04-19
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.report.service.StockInsufficientService;

public class StockInsufficientJob implements Runnable{
	private static final Logger LOG = Logger.getLogger(StockInsufficientJob.class);
	private static final String URL_FIXED = "http://www.lvmama.com/product/";
	private StockInsufficientService stockInsufficientService;
	private MetaProductService metaProductService;
	private String contentMsg;
	private String mailFrom;
	private String subject;
	private Map<String,Object> parameters;
	private EmailClient emailClient;
	
	@Override
	public void run() {
		boolean jobRunnable = Constant.getInstance().isJobRunnable();
		
		if(!Constant.getInstance().getStockInsufficientMailSenderEnabled()){
			LOG.info("库存监控邮件提醒stock.insufficient.mail.enabled=false");
		}

		if (jobRunnable) {
			final String TD ="<td>";  //TD标签
			final String _TD ="</td>";//TD结束标签
			final String SIGN = "  "; //手机，邮箱分割符
			final String NULL = "null";//手机，邮箱为空填充字符
			//1.读取邮件初始内容
			String emailInitContent = readEmailContent();
			if(StringUtil.isEmptyString(emailInitContent)){
				LOG.warn("发送库存不足邮件没有找到邮件初始内容");
				return ;
			}
			//获取库存不足信息列表
			List<Map<String,Object>> result =null;
			try{
				result =  stockInsufficientService.query(parameters);
			}catch(Exception e){
				LOG.warn("发送库存不足\r\n" +e.getMessage());
			}
			
			if(result.size() == 0){
				LOG.info("发送库存不足消息没有找到数据");
				return ;
			}
			//提取信息内容
			Map<String,String> sureResult= new HashMap<String,String>();
			//Map<String, Object> map= new HashMap<String, Object>();
		    List<String> list=new ArrayList<String>();
			for(Map<String,Object> entry:result){
				//检测 是否已经提醒过
				String flag = (String)entry.get("STOCK_FLAG");
				if (flag!=null) {
					if (flag.equals("false")) {
						LOG.info("此条信息库存不足消息已经发送");
						continue;
					}
				}
				//取得手机
				String mobile = (String)entry.get("MOBILE");
				//取得邮箱
				String email = (String)entry.get("EMAIL");
				if(StringUtil.isEmptyString(mobile)){
					mobile = NULL;
				}
				if(StringUtil.isEmptyString(email)){
					email = NULL;
				}
				//把手机，邮箱设置成KEY
				String user = mobile+SIGN+email;
				//提取要发送到邮箱中的内容
				java.math.BigDecimal productId= (java.math.BigDecimal)entry.get("PRODUCT_ID");
				String productName = (String)entry.get("PRODUCT_NAME");
				java.math.BigDecimal priceId = (java.math.BigDecimal)entry.get("TIME_PRICE_ID");
				String specDate = (String)entry.get("SPEC_DATE");
				java.math.BigDecimal dayStock = (java.math.BigDecimal)entry.get("DAY_STOCK");
				StringBuffer sb = new StringBuffer();
				sb.append("<tr>")
				  .append(TD).append("<a href='").append(URL_FIXED).append(productId).append("'>").append(productName).append("</a>").append(_TD)
				  .append(TD).append(productId).append(_TD)
				  .append(TD).append(specDate).append(_TD)
				  .append(TD).append(dayStock).append(_TD)
				  .append("</tr>");
				//根据USER信息放入内容
				if(null == sureResult.get(user)){
					sureResult.put(user, sb.toString());
				}else{
					sureResult.put(user, sureResult.get(user)+sb.toString());
				}
				list.add(priceId.toString());
				//map.put("priceId", priceId);
//				Map<String, Object> map= new HashMap<String, Object>();
//				map.put("stokflag", "false" );
//				map.put("priceId", Long.valueOf(priceId+""));
//				metaProductService.signSendEmail(map);
			}
			//清除结果集，释放内存
			result.clear();
			Iterator<String> iterator = sureResult.keySet().iterator();
			//根据手机，邮箱发送消息
			while(iterator.hasNext()){
				String key = iterator.next();
				String[] info = key.split(SIGN);
				String mobile = info[0];
				String email = info[1];
				String subContent = (String) sureResult.get(key);
				if(email.equals(NULL) && mobile.equals(NULL)){
					LOG.info("发送邮件验证失败 邮箱("+email+") 手机("+mobile+")");
					continue;
				}
				//发送邮件
				if(Constant.getInstance().getStockInsufficientMailSenderEnabled()){
					try {
						EmailContent emailContent=new EmailContent();
						emailContent.setFromAddress(mailFrom);
						emailContent.setFromName("驴妈妈旅游网");
						emailContent.setSubject(subject);
						emailContent.setToAddress(email);
						String content1 = null;
						Map<String,Object> entry = new HashMap<String,Object>();
						entry.put("productList", subContent);
						content1 = StringUtil.composeMessage(emailInitContent, entry);
						emailContent.setContentText(content1);
						emailClient.sendEmail(emailContent);
						
						for (int i = 0; i <list.size(); i++) {
							String priceId=list.get(i);
							Map<String, Object> map= new HashMap<String, Object>();
							map.put("priceId", priceId);
							map.put("stokflag", "false" );
							metaProductService.signSendEmail(map);
						}
					} catch (Exception e) {
						LOG.warn("发送邮件失败 邮箱("+email+") 库存不足内容\r\n"+subContent+"\r\n"+e.getMessage());
					}
				}
			}
		}
	}
	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}
	private String readEmailContent(){
		try {
			StringBuffer sb = new StringBuffer();
			File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/stockInsufficient.template");
			FileReader fileread=new FileReader(templateResource);
			BufferedReader bufread=new BufferedReader(fileread);
			String line;
			while((line=bufread.readLine())!=null){
				sb.append(line+"\n");
			}
			bufread.close();
			return sb.toString();
		} catch (IOException e) {
			LOG.warn("读取邮件内容失败 IOEXCEPTION \r\n"+e.getMessage());
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
 
	public String getContentMsg() {
		return contentMsg;
	}
	public void setContentMsg(String contentMsg) {
		this.contentMsg = contentMsg;
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
	public Map<String, Object> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	public void setEmailClient(EmailClient emailClient) {
		this.emailClient = emailClient;
	}

}
