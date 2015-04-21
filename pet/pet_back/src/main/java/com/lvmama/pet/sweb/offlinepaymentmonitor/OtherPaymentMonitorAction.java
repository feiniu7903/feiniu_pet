package com.lvmama.pet.sweb.offlinepaymentmonitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pay.PayPaymentDetail;
import com.lvmama.comm.pet.po.pay.PayPaymentGateway;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.pay.PayPaymentDetailService;
import com.lvmama.comm.pet.service.pay.PayPaymentGatewayService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.vo.CashPaymentComboVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.PAYMENT_DETAIL_OTHER_AUDIT_STATUS;



public class OtherPaymentMonitorAction extends BackBaseAction {
	
	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = -5705498939897839474L;

	/**
	 * 页面参数传递
	 */
	private Map<String,String> paramMap=new HashMap<String,String>();
	
	/**
	 * 其它支付-非现金审核状态(未确认=UNCONFIRMED、已确认=CONFIRM)   
	 */
	private List<PAYMENT_DETAIL_OTHER_AUDIT_STATUS> otherAuditStatusList=new ArrayList<PAYMENT_DETAIL_OTHER_AUDIT_STATUS>();
	/**
	 * 网关管理-其它支付集合
	 */
	private List<PayPaymentGateway> payPaymentGatewayList=new ArrayList<PayPaymentGateway>();
	
	private PayPaymentGatewayService payPaymentGatewayService;
	
	private PayPaymentService payPaymentService;
	/**
	 * 其它支付联合对象
	 */
	private List<CashPaymentComboVO> cashPaymentComboVOList;
	
	private CashPaymentComboVO cashPaymentComboVO;
	/**
	 * super系统用户
	 */
	private PermUserService permUserService;
	/**
	 * 自动完成联想搜索关键字
	 */
	private String search;
	
	private PayPaymentDetailService payPaymentDetailService;
	
	
	
	
	
	
	
	
	/**
	 * 初始化查询页面
	 * @author ZHANG Nan
	 * @return
	 */
	public String load(){
		initData();
		return SUCCESS;
	}
	/**
	 * 初始化基本数据
	 * @author ZHANG Nan
	 */
	private void initData(){
		otherAuditStatusList.add(PAYMENT_DETAIL_OTHER_AUDIT_STATUS.UNCONFIRMED);
		otherAuditStatusList.add(PAYMENT_DETAIL_OTHER_AUDIT_STATUS.CONFIRM);
		Map<String,String> gatewayParam=new HashMap<String,String>();
		gatewayParam.put("gatewayType", Constant.PAYMENT_GATEWAY_TYPE.OTHER.name());
		payPaymentGatewayList=payPaymentGatewayService.selectPayPaymentGatewayByParamMap(gatewayParam);
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
		Long cashPaymentComboVOListCount=payPaymentService.selectOtherPayPaymentAndDetailByParamMapCount(paramMap);
		if(cashPaymentComboVOListCount!=null && cashPaymentComboVOListCount>0){
			cashPaymentComboVOList= payPaymentService.selectOtherPayPaymentAndDetailByParamMap(paramMap);
		}
		pagination.setTotalResultSize(cashPaymentComboVOListCount);
		pagination.buildUrl(getRequest());
		return SUCCESS;
	}
	public void otherAuditConfirm() throws IOException{
		try {
			if(StringUtils.isNotBlank(paramMap.get("paymentDetailId"))){
				PayPaymentDetail payPaymentDetail=payPaymentDetailService.selectPaymentDetailByPK(Long.parseLong(paramMap.get("paymentDetailId")));
				payPaymentDetail.setAuditPerson(getSessionUserName());
				payPaymentDetail.setOtherAuditStatus(Constant.PAYMENT_DETAIL_OTHER_AUDIT_STATUS.CONFIRM.name());
				payPaymentDetailService.updatePayPaymentDetail(payPaymentDetail);
				this.getResponse().getWriter().write("{result:'确认成功!'}");
			}
			else{
				this.getResponse().getWriter().write("{result:'确认失败!'}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.getResponse().getWriter().write("{result:'确认失败!'}");
		}
	}
	/**
	 * super系统用户自动完成查询
	 * @author ZHANG Nan
	 */
	public void permUserAutoComplete(){
		JSONArray array=new JSONArray();
		List<PermUser> permUserList=permUserService.selectListByUserNameOrRealName(search);
		for (PermUser permUser : permUserList) {
			JSONObject obj=new JSONObject();
			obj.put("id", permUser.getUserName());
			obj.put("text", permUser.getRealName()+"("+permUser.getUserName()+")");
			array.add(obj);
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	/**
	 * 导出Excel
	 * @author ZHANG Nan
	 */
	public void exportExcel(){
		String templatePath = "/WEB-INF/resources/template/other_payment_monitor.xls";
		String outputFile="other_payment_monitor_"+ DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+".xls";
		cashPaymentComboVOList= payPaymentService.selectOtherPayPaymentAndDetailByParamMap(paramMap);
		if(cashPaymentComboVOList!=null && cashPaymentComboVOList.size()>65535){
			//判断excel导出结果集是否超过65535行则提示错误
			HttpServletResponse response= getResponse();
			String fullContentType = "text/html;charset=UTF-8";
			response.setContentType(fullContentType);
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			try {
				response.getWriter().write("导出的记录数超过了65535行,请缩小导出范围!");
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ;
		}
		Map<String,Object> beans = new HashMap<String,Object>();
		beans.put("list", cashPaymentComboVOList);
		exportXLS(beans, templatePath,outputFile);
	}
	/**
	 * xls文件下载
	 * @author ZHANG Nan
	 * @param map
	 * @param path
	 * @param fileName
	 */
	public void exportXLS(Map<String, Object> map, String path,String fileName){
		try {
			File templateResource = ResourceUtil.getResourceFile(path);
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName =System.getProperty("java.io.tmpdir") + "/" + fileName + ".xls";
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, map, destFileName);
			HttpServletResponse response = getResponse();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);   
			File file = new File(destFileName);
			InputStream inputStream=new FileInputStream(destFileName);  
			if (file != null && file.exists()) {
				OutputStream os=response.getOutputStream();  
	            byte[] b=new byte[1024];  
	            int length;  
	            while((length=inputStream.read(b))>0){  
	                os.write(b,0,length);  
	            }  
	            inputStream.close();  
			} else {
				throw new RuntimeException("Download failed!  path:"+path+" fileName:"+fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	
	
	public Map<String, String> getParamMap() {
		return paramMap;
	}
	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	public List<PAYMENT_DETAIL_OTHER_AUDIT_STATUS> getOtherAuditStatusList() {
		return otherAuditStatusList;
	}
	public void setOtherAuditStatusList(List<PAYMENT_DETAIL_OTHER_AUDIT_STATUS> otherAuditStatusList) {
		this.otherAuditStatusList = otherAuditStatusList;
	}
	public List<PayPaymentGateway> getPayPaymentGatewayList() {
		return payPaymentGatewayList;
	}
	public void setPayPaymentGatewayList(List<PayPaymentGateway> payPaymentGatewayList) {
		this.payPaymentGatewayList = payPaymentGatewayList;
	}
	public PayPaymentGatewayService getPayPaymentGatewayService() {
		return payPaymentGatewayService;
	}
	public void setPayPaymentGatewayService(PayPaymentGatewayService payPaymentGatewayService) {
		this.payPaymentGatewayService = payPaymentGatewayService;
	}
	public PayPaymentService getPayPaymentService() {
		return payPaymentService;
	}
	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
	public List<CashPaymentComboVO> getCashPaymentComboVOList() {
		return cashPaymentComboVOList;
	}
	public void setCashPaymentComboVOList(List<CashPaymentComboVO> cashPaymentComboVOList) {
		this.cashPaymentComboVOList = cashPaymentComboVOList;
	}
	public PermUserService getPermUserService() {
		return permUserService;
	}
	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public CashPaymentComboVO getCashPaymentComboVO() {
		return cashPaymentComboVO;
	}
	public void setCashPaymentComboVO(CashPaymentComboVO cashPaymentComboVO) {
		this.cashPaymentComboVO = cashPaymentComboVO;
	}
	public PayPaymentDetailService getPayPaymentDetailService() {
		return payPaymentDetailService;
	}
	public void setPayPaymentDetailService(PayPaymentDetailService payPaymentDetailService) {
		this.payPaymentDetailService = payPaymentDetailService;
	}
}
