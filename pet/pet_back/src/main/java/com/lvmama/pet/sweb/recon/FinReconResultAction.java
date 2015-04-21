package com.lvmama.pet.sweb.recon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pay.FinReconBankStatement;
import com.lvmama.comm.pet.po.pay.FinReconResult;
import com.lvmama.comm.pet.service.fin.FinGLService;
import com.lvmama.comm.pet.service.pay.FinReconBankStatementService;
import com.lvmama.comm.pet.service.pay.FinReconResultService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.GL_STATUS;
import com.lvmama.comm.vo.Constant.RECON_GW_TYPE;
import com.lvmama.comm.vo.Constant.RECON_STATUS;
import com.lvmama.comm.vo.Constant.TRANSACTION_SOURCE;
import com.lvmama.comm.vo.Constant.TRANSACTION_TYPE;


/**
 * super后台->财务管理->财务勾兑->勾兑结果Action
 * @author ZHANG Nan
 *
 */
public class FinReconResultAction extends BackBaseAction {

	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 413794939934106247L;

	/**
	 * 页面参数传递
	 */
	private Map<String,String> paramMap=new HashMap<String,String>();
	/**
	 * 页面跳转地址
	 */
	private String target;

	/**
	 * 对账网关
	 */
	private RECON_GW_TYPE [] reconGateways;
	/**
	 * 对账状态
	 */
	private RECON_STATUS[] reconStatuss;
	/**
	 * 交易类型
	 */
	private TRANSACTION_TYPE[] transactionTypes;
	/**
	 * 交易来源
	 */
	private TRANSACTION_SOURCE[] transactionSources;
	/**
	 * 记账状态
	 */
	private GL_STATUS[] glStatuss;
	/**
	 * 对账结果集合
	 */
	private List<FinReconResult> finReconResultList;
	/**
	 * 对账结果对象
	 */
	private FinReconResult finReconResult;
	/**
	 * 对账结果主键
	 */
	private Long reconResultId;
	/**
	 * 对账结果接口
	 */
	private FinReconResultService finReconResultService;
	/**
	 * 交易收入总额(单位:元)
	 */
	private String transactionAmountSum;
	/**
	 * 交易支付总额(单位:元)
	 */
	private String transactionBankAmountSum;
	
	
	/**
	 * 日志类型
	 */
	private static String LOG_TYPE="FIN_RECON_RESUTL";
	/**
	 * 日志接口
	 */
	protected ComLogService comLogRemoteService;
	
	private FinGLService finGLService;
	
	private File file;
	
	//银行交易明细接口
	private FinReconBankStatementService finReconBankStatementService;
	
	
	/**
	 * 初始化页面
	 * @author ZHANG Nan
	 * @return
	 */
	public String load(){
		initData();
		if(reconResultId!=null && reconResultId>0){
			finReconResult=finReconResultService.selectByReconResultId(reconResultId);
		}
		if(StringUtils.isNotBlank(target)){
			return target;
		}
		return SUCCESS;
	}
	
	/**
	 * 页面LIST查询
	 * @author ZHANG Nan
	 * @return
	 */
	public String query() {
		initData();
		pagination=initPage();
		paramMap.put("start", String.valueOf(pagination.getStartRows()));
		paramMap.put("end", String.valueOf(pagination.getEndRows()));
		
		Long finReconResultListCount=finReconResultService.selectReconResultListByParasCount(paramMap);
		if(finReconResultListCount!=null && finReconResultListCount>0){
			finReconResultList= finReconResultService.selectReconResultListByParas(paramMap);
			
			Map<String,String> amountMap=finReconResultService.selectTransactionAmountByParamMap(paramMap);
			if(amountMap!=null){
				transactionAmountSum=amountMap.get("TRANSACTIONAMOUNTSUM");
				transactionBankAmountSum=amountMap.get("TRANSACTIONBANKAMOUNTSUM");
			}
		}
		pagination.setTotalResultSize(finReconResultListCount);
		pagination.buildUrl(getRequest());
		return SUCCESS;
	}
	/**
	 * 修改对账结果
	 * @author ZHANG Nan
	 * @throws IOException
	 */
	public void modify() throws IOException{
		try {
			int rows=finReconResultService.update(finReconResult);
			String content="修改成功.对账状态改为:"+finReconResult.getReconStatusZH()+",交易来源改为:"+finReconResult.getTransactionSourceZH()+",备注改为:"+finReconResult.getMemo()+",记账状态改为:"+finReconResult.getGlStatusZH()+",影响记录:"+rows;
			comLogRemoteService.insert(LOG_TYPE, null, finReconResult.getReconResultId(), getSessionUserName(), null, null, content, null);
			this.getResponse().getWriter().write("{result:'修改成功!'}");
		} catch (Exception e) {
			e.printStackTrace();
			comLogRemoteService.insert(LOG_TYPE, null, finReconResult.getReconResultId(), getSessionUserName(), null, null, "修改失败", null);
			this.getResponse().getWriter().write("{result:'修改失败!'}");
		}
	} 
	/**
	 * 初始化基本数据
	 * @author ZHANG Nan
	 */
	public void initData(){
		reconGateways=Constant.RECON_GW_TYPE.values();
		reconStatuss=Constant.RECON_STATUS.values();
		transactionTypes=Constant.TRANSACTION_TYPE.values();
		transactionSources=Constant.TRANSACTION_SOURCE.values();
		glStatuss=Constant.GL_STATUS.values();
	}
	
	/**
	 * 重新入账
	 * @throws IOException 
	 */
	public void reGenerateGLData() throws IOException{
		try {
			finReconResultService.updateFailedReconResultGLStatus();
			finGLService.generateGLData();
			this.getResponse().getWriter().write("重新入账成功");
		} catch (Exception e) {
			e.printStackTrace();
			this.getResponse().getWriter().write("重新入账失败");
		}
	} 
	
	/**
	 * 删除老数据后重新入账
	 * @throws IOException 
	 */
	public void deleteOldDataReGenerateGLData() throws IOException{
		try {
			finReconResultService.updateFailedReconResultGLStatus();
			finGLService.generateGLDataBeforeCleanOldData();
			LOG.info("deleteOldDataReGenerateGLData", "operator is "+getSessionUserName());
			this.getResponse().getWriter().write("重新入账成功");
		} catch (Exception e) {
			e.printStackTrace();
			this.getResponse().getWriter().write("重新入账失败");
		}
	}
	
	/**
	 * 导出勾兑数据
	 * @author lvhao
	 * @return
	 */
	@Action("exportDataReconGLData")
	public void exportDataReconGLData(){
		initData();
		Long finReconResultListCount=finReconResultService.selectReconResultListByParasCount(paramMap);
		if(finReconResultListCount!=null && finReconResultListCount>0){
			finReconResultList= finReconResultService.selectReconResultListByParas(paramMap);
			
			Map<String,String> amountMap=finReconResultService.selectTransactionAmountByParamMap(paramMap);
			if(amountMap!=null){
				transactionAmountSum=amountMap.get("TRANSACTIONAMOUNTSUM");
				transactionBankAmountSum=amountMap.get("TRANSACTIONBANKAMOUNTSUM");
			}
		}
		this.output(finReconResultList, "/WEB-INF/resources/template/finReconResultTemplate.xls");
	}
	
	/**
	 * 导出
	 * @param list
	 * @param template
	 */
	private void output(List list,String template){
		FileInputStream fin=null;
		OutputStream os=null;
		try
		{
			File templateResource = ResourceUtil.getResourceFile(template);
			Map beans = new HashMap();
			beans.put("list", list);
			XLSTransformer transformer = new XLSTransformer();
			File destFileName = new File(Constant.getTempDir() + "/excel"+new Date().getTime()+".xls");
			transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName.getAbsolutePath());
					
			getResponse().setContentType("application/vnd.ms-excel");
			getResponse().setHeader("Content-Disposition", "attachment; filename=" + destFileName.getName());
			os=getResponse().getOutputStream();
			fin=new FileInputStream(destFileName);
			IOUtils.copy(fin, os);
			
			os.flush();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			IOUtils.closeQuietly(fin);
			IOUtils.closeQuietly(os);
		}
	}
	
	/**
	 * 导入交行POS机、杉德POS机数据
	 * @author lvhao
	 * @return
	 */
	public void importCsv() {
		BufferedReader reader = null;
		String message="success";
		
		if(this.file==null){
			this.sendAjaxMsg("上传文件为Null");
			return;
		}
		
		try {
			reader = new BufferedReader(new FileReader(file));
			// 前7行信息，为标题信息，不用,如果需要，注释掉
            for(int i=0;i<7;i++){
            	reader.readLine();
            }
			String line = null;
			List<FinReconBankStatement> list = new ArrayList<FinReconBankStatement>();
			while ((line = reader.readLine()) != null) {
				String item[] = line.split("\\|");// CSV格式文件为逗号分隔符文件，这里根据逗号切分
				if(item.length==1){
					break;
				}
				
				FinReconBankStatement finReconBankStatement = new FinReconBankStatement();
				finReconBankStatement.setGateway(Constant.PAYMENT_GATEWAY.SAND_POS.name());
				finReconBankStatement.setTransactionType(Constant.TRANSACTION_TYPE.PAYMENT.name());
				finReconBankStatement.setCreateTime(new Date());
				
				for (int i = 1; i < item.length; i++) {
					String value = item[i];
					if (StringUtils.isNotEmpty(value)) {
						value = value.trim();
						if(i==1){
							finReconBankStatement.setBankPaymentTradeNo(value);
						}else if(i==2){
							finReconBankStatement.setAmount(PriceUtil.convertToFen(value));
						}else if(i==9){
							finReconBankStatement.setBankGatewayTradeNo(value);
						}else if(i==16){
							finReconBankStatement.setTransactionTime(strToDate(value));
						}
					}
				}
				list.add(finReconBankStatement);
			}
			finReconBankStatementService.insert(list);
		} catch (Exception e) {
			e.printStackTrace();
			message="操作失败";
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		this.sendAjaxMsg(message);
	}
	
	private Date strToDate(String str) {
	   SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	   Date date = null;
	   try {
		   date = format.parse(str);
	   } catch (ParseException e) {
		   e.printStackTrace();
	   }
	   return date;
	}
	
	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	public RECON_GW_TYPE[] getReconGateways() {
		return reconGateways;
	}

	public void setReconGateways(RECON_GW_TYPE[] reconGateways) {
		this.reconGateways = reconGateways;
	}

	public RECON_STATUS[] getReconStatuss() {
		return reconStatuss;
	}

	public void setReconStatuss(RECON_STATUS[] reconStatuss) {
		this.reconStatuss = reconStatuss;
	}

	public TRANSACTION_TYPE[] getTransactionTypes() {
		return transactionTypes;
	}

	public void setTransactionTypes(TRANSACTION_TYPE[] transactionTypes) {
		this.transactionTypes = transactionTypes;
	}

	public List<FinReconResult> getFinReconResultList() {
		return finReconResultList;
	}

	public void setFinReconResultList(List<FinReconResult> finReconResultList) {
		this.finReconResultList = finReconResultList;
	}

	public FinReconResultService getFinReconResultService() {
		return finReconResultService;
	}

	public void setFinReconResultService(FinReconResultService finReconResultService) {
		this.finReconResultService = finReconResultService;
	}

	public String getTransactionAmountSum() {
		return transactionAmountSum;
	}

	public void setTransactionAmountSum(String transactionAmountSum) {
		this.transactionAmountSum = transactionAmountSum;
	}

	public String getTransactionBankAmountSum() {
		return transactionBankAmountSum;
	}

	public void setTransactionBankAmountSum(String transactionBankAmountSum) {
		this.transactionBankAmountSum = transactionBankAmountSum;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public TRANSACTION_SOURCE[] getTransactionSources() {
		return transactionSources;
	}

	public void setTransactionSources(TRANSACTION_SOURCE[] transactionSources) {
		this.transactionSources = transactionSources;
	}

	public FinReconResult getFinReconResult() {
		return finReconResult;
	}

	public void setFinReconResult(FinReconResult finReconResult) {
		this.finReconResult = finReconResult;
	}

	public Long getReconResultId() {
		return reconResultId;
	}

	public void setReconResultId(Long reconResultId) {
		this.reconResultId = reconResultId;
	}

	public ComLogService getComLogRemoteService() {
		return comLogRemoteService;
	}

	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}

	public GL_STATUS[] getGlStatuss() {
		return glStatuss;
	}

	public void setGlStatuss(GL_STATUS[] glStatuss) {
		this.glStatuss = glStatuss;
	}

	public FinGLService getFinGLService() {
		return finGLService;
	}

	public void setFinGLService(FinGLService finGLService) {
		this.finGLService = finGLService;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public FinReconBankStatementService getFinReconBankStatementService() {
		return finReconBankStatementService;
	}

	public void setFinReconBankStatementService(
			FinReconBankStatementService finReconBankStatementService) {
		this.finReconBankStatementService = finReconBankStatementService;
	}
	
	
	
}
