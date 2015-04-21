package com.lvmama.back.sweb.distribution;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.distribution.DistributionTuanCoupon;
import com.lvmama.comm.bee.po.distribution.DistributorTuanInfo;
import com.lvmama.comm.bee.service.distribution.DistributionTuanCouponService;
import com.lvmama.comm.bee.service.distribution.DistributionTuanService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name = "tuangouCouponCodeList", location = "/WEB-INF/pages/back/distribution/tuangouorder/distributionTuanCouponCode.jsp")
})
@ParentPackage("json-default")
public class TuangouCouponCodeManagerAction extends BackBaseAction{

	private static final long serialVersionUID = -1177388763818982952L;
	private String distributionCouponCode;
	private String orderId;//订单号
	private String productName;//产品名称
	private String productId;//产品Id
	private String branchId;//类别Id
	private Long batchId;//批次Id
	private String creator; //创建人
	private String createTimeStart;//创建开始时间
	private String createTimeEnd;//创建结束时间
	private String bookTimeStart;//预订开始时间
	private String bookTimeEnd;//预订结束时间
	private String checkinStart;//游玩开始时间
	private String checkinEnd;//游玩结束时间
	private String couponCodeStatus;//券码状态
	private String performStatus;//履行状态
	private String orderStatus;//订单状态
	private String couponCodeIds;//券码串
	private String couponCodeId;//单个券码
	private Page<DistributionTuanCoupon> couponCodeList = new Page<DistributionTuanCoupon>();
	private DistributionTuanCouponService distributionTuanCouponService;
	private ComLogService comLogService;
	private Long distributorInfoId;//分销商
	/** 
	 * 用于页面展示的分销商信息集合
	 * */
	private List<DistributorTuanInfo> distributorList;
	/** 关于分销的服务*/
	private DistributionTuanService distributionTuanService;
	
	@Action(value ="/distribution/tuangouorder/CouponCodeList")
	public String CouponCodeList() {
		Map<String,Object> params = buildParams();
		couponCodeList.setTotalResultSize(distributionTuanCouponService.queryCount(params));
		couponCodeList.buildUrl(getRequest());
		couponCodeList.setCurrentPage(super.page);
		params.put("start", couponCodeList.getStartRows());
		params.put("end", couponCodeList.getEndRows());
		if(couponCodeList.getTotalResultSize()>0){
			List<DistributionTuanCoupon> couponList =distributionTuanCouponService.queryList(params);
			couponCodeList.setItems(couponList);
		}
		distributorList = distributionTuanService.selectByDistributorChannelType(Constant.CHANNEL.DIST_YUYUE.name());
		return "tuangouCouponCodeList";
	}
	
	
	@Action("/distribution/tuangouorder/CancelOneCouponCode")
	public void CancelOneCouponCode(){
		try {
			
			boolean flag =this.doCouponCodeCancel(couponCodeId);
			insertComLog("废券码","废券码操作",Constant.COM_LOG_ORDER_EVENT.batchCancelCouponCode.name(),
					Long.valueOf(couponCodeId),Constant.COM_LOG_OBJECT_TYPE.COUPON_CODE.getCode());
			if(flag){
				sendAjaxMsg("{result:true}");
			}else{
				sendAjaxMsg("{result:false}");
			}
		}catch(Exception e) {
			e.printStackTrace();
			sendAjaxMsg("{result:false}");
		}
	}
	
	@Action("/distribution/tuangouorder/batchCancelCouponCode")
	public void batchCancelCouponCode(){
		try{
			String[] orderArray = couponCodeIds.split(",");
			int count = orderArray.length;
			int successCount = 0;
			int failCount = 0;
			StringBuffer jsonResult = new StringBuffer();
			for(String couponCodeId:orderArray){
				boolean flag = this.doCouponCodeCancel(couponCodeId);
				if(flag){
					successCount = successCount+1;
					insertComLog("成功废除券码","批量废券码操作",Constant.COM_LOG_ORDER_EVENT.batchCancelCouponCode.name(),
							Long.parseLong(couponCodeId),Constant.COM_LOG_OBJECT_TYPE.COUPON_CODE.getCode());
				}else{
					failCount = failCount + 1;
					insertComLog("失败废除券码","批量废券码操作",Constant.COM_LOG_ORDER_EVENT.batchCancelCouponCode.name(),
							Long.parseLong(couponCodeId),Constant.COM_LOG_OBJECT_TYPE.COUPON_CODE.getCode());
				}
			}
			insertComLog("分销批量废券码操作","批量废券码",Constant.COM_LOG_ORDER_EVENT.batchCancelCouponCode.name(),batchId,Constant.COM_LOG_OBJECT_TYPE.COUPON_CODE.getCode());
			jsonResult.append("需要废除"+count+"个券码！\\r\\n");
			jsonResult.append("已成功废除"+successCount+"个券码，"+failCount+"个券码废除失败！");
			sendAjaxMsg("{'result':true,'msg':'"+jsonResult.toString()+"'}");
		}catch(Exception e){
			e.printStackTrace();
			sendAjaxMsg("废券码异常！");
		}
	}
	
	
	@Action("/distribution/tuangouorder/activateCouponCode")
	public void activateCouponCode(){
		try{
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("distributionCouponCode", couponCodeId);
			param.put("orderId","");
			param.put("status","NORMAL");
			int count=distributionTuanCouponService.activateCouponCode(param);
			insertComLog("重新激活券码","重新激活券码操作",Constant.COM_LOG_ORDER_EVENT.activateCouponCode.name(),
					Long.valueOf(couponCodeId),Constant.COM_LOG_OBJECT_TYPE.COUPON_CODE.getCode());
			if(count>0){
				sendAjaxMsg("{result:true}");
			}else{
				sendAjaxMsg("{result:false}");
			}
		}catch(Exception e){
			e.printStackTrace();
			sendAjaxMsg("{result:false}");
		}
	}
	
	@Action("/distribution/tuangouorder/exportExcelCouponCode")
	public void exportExcelCouponCode(){
		Map<String,Object> params = buildParams();
		List<DistributionTuanCoupon> couponList =distributionTuanCouponService.queryCouponCodeList(params);
		String template = "/WEB-INF/resources/template/couponCodeTemplate.xls";
		output(couponList, template,"couponList");
	}
	
	
	private void output(List<DistributionTuanCoupon> list, String template,String key) {
		FileInputStream fin = null;
		OutputStream os = null;
		try {
			File templateResource = ResourceUtil.getResourceFile(template);
			Map<String, Object> beans=new HashMap<String, Object>();
			beans.put(key, list);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			beans.put("dateFormat", dateFormat);
			XLSTransformer transformer = new XLSTransformer();
			File destFileName = new File(Constant.getTempDir() + "/excel" + new Date().getTime() + ".xls");
			transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName.getAbsolutePath());
			getResponse().setContentType("application/vnd.ms-excel");
			getResponse().setHeader("Content-Disposition", "attachment; filename=" + destFileName.getName());
			os = getResponse().getOutputStream();
			fin = new FileInputStream(destFileName);
			IOUtils.copy(fin, os);
			os.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fin);
			IOUtils.closeQuietly(os);
		}
	}
	private void insertComLog(String content,String logName,String logType,Long objectId,String objectType){
		PermUser user = getSessionUser();
		ComLog comlog = new ComLog();
		comlog.setContent(content);
		comlog.setContentType(Constant.COM_LOG_CONTENT_TYPE.VARCHAR.name());
		comlog.setCreateTime(new Date());
		comlog.setLogName(logName);
		comlog.setLogType(logType);
		comlog.setObjectId(objectId);
		comlog.setObjectType(objectType);
		comlog.setParentId(objectId);
		comlog.setOperatorName(user.getUserName());
		comLogService.addComLog(comlog);
	}
	
	private boolean doCouponCodeCancel(String couponId){
		try {
			boolean flag=false;
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("distributionCouponCode", couponId);
			param.put("status","DESTROYED");
			int count=distributionTuanCouponService.logicalDelete(param);
			if(count>0){
				flag=true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Map<String,Object> buildParams(){
		Map<String, Object> params = new HashMap<String, Object>();
		if(distributorInfoId!=null){
			params.put("distributorInfoId", distributorInfoId);
		}
		if (!StringUtil.isEmptyString(productName)) {
			params.put("productName", productName.trim());
		}
		if (!StringUtil.isEmptyString(productId)) {
			params.put("productId", productId.trim());
		}
		if (!StringUtil.isEmptyString(branchId)) {
			params.put("branchId", branchId.trim());
		}
		if (batchId != null) {
			params.put("batchId", batchId);
		}
		if (!StringUtil.isEmptyString(createTimeStart)) {
			params.put("createtimeStart", DateUtil.stringToDate(createTimeStart, "yyyy-MM-dd"));
		}
		if (!StringUtil.isEmptyString(createTimeEnd)) {
			params.put("createtimeEnd", DateUtil.dsDay_Date(DateUtil.stringToDate(createTimeEnd, "yyyy-MM-dd"),1));
		}
		if (!StringUtil.isEmptyString(bookTimeStart)) {
			params.put("bookTimeStart", DateUtil.stringToDate(bookTimeStart, "yyyy-MM-dd"));
		}
		if (!StringUtil.isEmptyString(bookTimeEnd)) {
			params.put("booktimeEnd", DateUtil.dsDay_Date(DateUtil.stringToDate(bookTimeEnd, "yyyy-MM-dd"),1));
		}
		if (!StringUtil.isEmptyString(checkinStart)) {
			params.put("checkinStart", DateUtil.stringToDate(checkinStart, "yyyy-MM-dd"));
		}
		if (!StringUtil.isEmptyString(checkinEnd)) {
			params.put("checkinEnd", DateUtil.stringToDate(checkinEnd, "yyyy-MM-dd"));
		}
		if (couponCodeStatus!="") {
			params.put("status", couponCodeStatus);
		}
		if (orderStatus!="") {
			params.put("orderStatus",orderStatus);
		}
		if (performStatus!="") {
			params.put("performStatus", performStatus);
		}
		
		if (!StringUtil.isEmptyString(distributionCouponCode)) {
			params.put("distributionCouponCode",distributionCouponCode.trim());
		}
		if (!StringUtil.isEmptyString(creator)) {
			params.put("creator",creator.trim());
		}
		if (!StringUtil.isEmptyString(orderId)) {
			params.put("orderId",orderId.trim());
		}
		return params;
	}

	public String getDistributionCouponCode() {
		return distributionCouponCode;
	}

	public void setDistributionCouponCode(String distributionCouponCode) {
		this.distributionCouponCode = distributionCouponCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getCouponCodeStatus() {
		return couponCodeStatus;
	}

	public void setCouponCodeStatus(String couponCodeStatus) {
		this.couponCodeStatus = couponCodeStatus;
	}
	

	public Page<DistributionTuanCoupon> getCouponCodeList() {
		return couponCodeList;
	}
	
	public void setCouponCodeList(Page<DistributionTuanCoupon> couponCodeList) {
		this.couponCodeList = couponCodeList;
	}

	public String getCreator() {
		return creator;
	}


	public void setCreator(String creator) {
		this.creator = creator;
	}


	public void setDistributionTuanCouponService(
			DistributionTuanCouponService distributionTuanCouponService) {
		this.distributionTuanCouponService = distributionTuanCouponService;
	}
	
	public String getCouponCodeIds() {
		return couponCodeIds;
	}


	public void setCouponCodeIds(String couponCodeIds) {
		this.couponCodeIds = couponCodeIds;
	}


	public String getCouponCodeId() {
		return couponCodeId;
	}


	public void setCouponCodeId(String couponCodeId) {
		this.couponCodeId = couponCodeId;
	}


	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}


	public String getPerformStatus() {
		return performStatus;
	}


	public void setPerformStatus(String performStatus) {
		this.performStatus = performStatus;
	}


	public String getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}


	public String getBookTimeStart() {
		return bookTimeStart;
	}


	public void setBookTimeStart(String bookTimeStart) {
		this.bookTimeStart = bookTimeStart;
	}


	public String getBookTimeEnd() {
		return bookTimeEnd;
	}


	public void setBookTimeEnd(String bookTimeEnd) {
		this.bookTimeEnd = bookTimeEnd;
	}


	public String getCheckinStart() {
		return checkinStart;
	}


	public void setCheckinStart(String checkinStart) {
		this.checkinStart = checkinStart;
	}


	public String getCheckinEnd() {
		return checkinEnd;
	}


	public void setCheckinEnd(String checkinEnd) {
		this.checkinEnd = checkinEnd;
	}
	
	public Long getDistributorInfoId() {
		return distributorInfoId;
	}
	public void setDistributorInfoId(Long distributorInfoId) {
		this.distributorInfoId = distributorInfoId;
	}
	
	public List<DistributorTuanInfo> getDistributorList() {
		return distributorList;
	}
	public void setDistributorList(List<DistributorTuanInfo> distributorList) {
		this.distributorList = distributorList;
	}
	
	public DistributionTuanService getDistributionTuanService() {
		return distributionTuanService;
	}
	public void setDistributionTuanService(
			DistributionTuanService distributionTuanService) {
		this.distributionTuanService = distributionTuanService;
	}
}
