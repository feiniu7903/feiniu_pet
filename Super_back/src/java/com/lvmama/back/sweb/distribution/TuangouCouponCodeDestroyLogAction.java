package com.lvmama.back.sweb.distribution;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.distribution.DistributionTuanCoupon;
import com.lvmama.comm.bee.po.distribution.DistributionTuanDestroyLog;
import com.lvmama.comm.bee.service.distribution.DistributionTuanCouponService;
import com.lvmama.comm.bee.service.distribution.DistributionTuanDestroyLogService;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ExcelImport;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name = "destroyCouponCodeLogList", location = "/WEB-INF/pages/back/distribution/tuangouorder/bulkDestroyCouponCode.jsp"),
	@Result(name = "importExcel", location = "/WEB-INF/pages/back/distribution/tuangouorder/importExcel.jsp")
})
@ParentPackage("json-default")
public class TuangouCouponCodeDestroyLogAction extends BackBaseAction{

	private static final long serialVersionUID = -1177388763818982952L;
	private String creator;
	private String fileName;
	private String createTimeStart;
	private String createTimeEnd;
	private String up_createTimeStart;
	private String up_createTimeEnd;
	private Page<DistributionTuanDestroyLog> destroyCouponCodeList = new Page<DistributionTuanDestroyLog>();
	private DistributionTuanDestroyLogService distributionTuanDestroyLogService;
	private File file;
	private String fileFileName;
	private DistributionTuanCouponService distributionTuanCouponService;
	private ComLogService comLogService;
	private FSClient fsClient;
	private Long logId;
	private Long fId;
	
	@Action(value ="/distribution/tuangouorder/CouponCodeDestroyList")
	public String CouponCodeDestroyList() {
		Map<String,Object> params = buildParams();
		destroyCouponCodeList.setTotalResultSize(distributionTuanDestroyLogService.queryCount(params));
		destroyCouponCodeList.buildUrl(getRequest());
		destroyCouponCodeList.setCurrentPage(super.page);
		params.put("start", destroyCouponCodeList.getStartRows());
		params.put("end", destroyCouponCodeList.getEndRows());
		if(destroyCouponCodeList.getTotalResultSize()>0){
			List<DistributionTuanDestroyLog> couponList =distributionTuanDestroyLogService.queryList(params);
			destroyCouponCodeList.setItems(couponList);
		}
		return "destroyCouponCodeLogList";
	}
	
	@Action("/distribution/tuangouorder/importExcel")
    public String importExcel(){
        return "importExcel";
    }
	
	@Action("/distribution/tuangouorder/downExcel")
    public void downExcel(){
		DistributionTuanDestroyLog log = distributionTuanDestroyLogService.find(logId);
		if(log!=null && log.getPristineId().equals(fId)){
			downLoad(fId, log.getFileName());
		}else if(log!=null && log.getErrorId().equals(fId)){
			String str = "error_"+log.getFileName();
			if(str.lastIndexOf(".xlsx")>0){
				str = str.substring(0,str.length()-1);
			}
			downLoad(fId, str);
		}
		
    }
	
	@Action("/distribution/tuangouorder/importExcelData")
	public void importExcelData() {
		String message="";
		if(this.file==null){
			this.sendAjaxMsg("上传文件为Null");
			return;
		}
		List<String> list;
		try {
			PermUser user = getSessionUser();
			list = ExcelImport.excImport(new FileInputStream(file));
			DistributionTuanDestroyLog log=new DistributionTuanDestroyLog();
			List<DistributionTuanDestroyLog> errorLogList=new ArrayList<DistributionTuanDestroyLog>();
			Map<String, Object> param=new HashMap<String, Object>();
			int sumNum=list.size();
			int errorNum=0;
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmsss");
			log.setStartTime(new Date());
			if(list!=null && list.size()>0){
				for(String couponCode:list){
					param.put("distributionCouponCode", couponCode);
					DistributionTuanCoupon dc=distributionTuanCouponService.queryCouponCodeBycouponId(param);
					if(dc!=null){
						if(dc.getStatus().equals("NORMAL")){
							dc.setStatus("DESTROYED");
							distributionTuanCouponService.update(dc);
							insertComLog("成功废除券码"+couponCode,"批量废券码操作",Constant.COM_LOG_ORDER_EVENT.batchCancelCouponCode.name(),
									Long.parseLong(couponCode),Constant.COM_LOG_OBJECT_TYPE.COUPON_CODE.getCode());
							
						}else{
							DistributionTuanDestroyLog error=new DistributionTuanDestroyLog();
							errorNum++;
							String code=couponCode;
							String msg=Constant.DISTRIBUTION_TUAN_COUPON_STATUS.getCnName(dc.getStatus());
							error.setCouponCode(code);
							error.setErrorReson(msg);
							errorLogList.add(error);
							insertComLog("废除券码"+couponCode+"失败,原因：该券码"+msg,"批量废券码操作",Constant.COM_LOG_ORDER_EVENT.batchCancelCouponCode.name(),
									Long.parseLong(couponCode),Constant.COM_LOG_OBJECT_TYPE.COUPON_CODE.getCode());
						}
					}else{
						errorNum++;
						String code=couponCode;
						String msg="该券码不存在！";
						DistributionTuanDestroyLog error=new DistributionTuanDestroyLog();
						error.setCouponCode(code);
						error.setErrorReson(msg);
						errorLogList.add(error);
					}
				}
				message="success";
			}else{
				message="没有券码被废";
			}
			
			log.setEndTime(new Date());
			log.setDistType(Constant.DISTRIBUTION_TUAN_DESTROY_LOG_DIST_TYPE.DIST_TUANGOU.getCode());
			log.setStatus(Constant.DISTRIBUTION_TUAN_DESTROY_LOG_STATUS.RUNNED.getCode());
			String startStr=fileFileName.substring(0,fileFileName.indexOf("."));
			String endStr=fileFileName.substring(fileFileName.indexOf("."));
			String newFileName=startStr+"_"+df.format(new Date());
			log.setUploadTime(new Date());
			log.setCreator(String.valueOf(user.getUserName()));
			log.setFileName(newFileName+endStr);
			log.setSuccessNum(Long.valueOf(sumNum-errorNum));
			log.setSumNum(Long.valueOf(sumNum));
			//保存数据
			Long logId =distributionTuanDestroyLogService.insert(log);
			String rootpath="";
			//将源文件上传服务器
			Long fid = uploadFile(file,newFileName+endStr);
			distributionTuanDestroyLogService.updatePristineId(logId,fid);
			//将废券码失败记录的文件上传服务器
			readErrorLog(logId,"error_"+newFileName+"xls",errorLogList);
		} catch (Exception e) {
			e.printStackTrace();
			message="操作失败,废券码异常";
		}
		
		this.sendAjaxMsg(message);
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
	private Long uploadFile( File file, String fileName){
		try {
			InputStream inputStream=new FileInputStream(file);
			return fsClient.uploadFile(fileName, inputStream, "DISTRIBUTION_XLSX");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return 0L;
	}
	
	private void downLoad(Long fileIdStr, String fileNameStr) {
		OutputStream os = null;
		try {
			String agent = getRequest().getHeader("User-Agent").toLowerCase();
			String fileName = new String(fileNameStr.getBytes("UTF-8"), "ISO8859-1");
			if(agent.indexOf("msie") >0){
				fileName = URLEncoder.encode(fileNameStr, "UTF-8");//IE浏览器
			}
			getResponse().reset();
			getResponse().setHeader("Content-Disposition",
					"attachment; filename=" + fileName);
			os = getResponse().getOutputStream();
			ComFile resultFile = fsClient.downloadFile(fileIdStr);
			IOUtils.copy(resultFile.getInputStream(), os);
			os.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(os);
		}
	}
	
	public void readErrorLog(Long logId,String fileName,List<DistributionTuanDestroyLog> errors) throws Exception{
        //创建一个EXCEL  
        Workbook wb = new HSSFWorkbook();  
        //创建一个SHEET  
        Sheet sheet1 = wb.createSheet("批量废券码失败日志");  
        String[] title = {"券码","失败原因"};  
        int i=0;  
        //创建一行  
        Row row = sheet1.createRow((short)0);  
        //填充标题  
        for (String  s:title){  
            Cell cell = row.createCell(i);  
            cell.setCellValue(s);  
            i++;  
        }  
        //下面是填充数据  
        int r=1;
        for (DistributionTuanDestroyLog log :errors) {
        	 int j=0;
        	 Row  rowName= sheet1.createRow((short)r); 
        	 String [] content={log.getCouponCode(),log.getErrorReson()};
        	 for (String s:content){  
                 Cell cell = rowName.createCell(j);  
                 cell.setCellValue(s);  
                 j++;
             }  
        	 r++;
		}
        
		File file = File.createTempFile("distribution", ".xls");
		FileOutputStream fos = new FileOutputStream(file);
        wb.write(fos);  
        Long fid = uploadFile(file,fileName);
		distributionTuanDestroyLogService.updateErrorId(logId,fid);
		fos.close();  
	}
	
	
	public Map<String,Object> buildParams(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("distType", Constant.DISTRIBUTION_TUAN_DESTROY_LOG_DIST_TYPE.DIST_TUANGOU.getCode());
		if (!StringUtil.isEmptyString(fileName)) {
			params.put("fileName", fileName.trim());
		}
		if (!StringUtil.isEmptyString(creator)) {
			params.put("creator", creator.trim());
		}
		if (!StringUtil.isEmptyString(createTimeStart)) {
			params.put("createtimeStart",StringUtil.isNotEmptyString(createTimeStart) ? DateUtil.stringToDate(createTimeStart, "yyyy-MM-dd"): null);
		}
		if (!StringUtil.isEmptyString(createTimeEnd)) {
			params.put("createtimeEnd",StringUtil.isNotEmptyString(createTimeEnd) ? DateUtil.getDateAfterDays(DateUtil.stringToDate(createTimeEnd, "yyyy-MM-dd"), 1): null);
		}
		if (!StringUtil.isEmptyString(up_createTimeStart)) {
			params.put("up_createtimeStart",StringUtil.isNotEmptyString(up_createTimeStart) ? DateUtil.stringToDate(up_createTimeStart, "yyyy-MM-dd"): null);
		}
		if (!StringUtil.isEmptyString(up_createTimeEnd)) {
			params.put("up_createtimeEnd",StringUtil.isNotEmptyString(up_createTimeEnd) ? DateUtil.getDateAfterDays(DateUtil.stringToDate(up_createTimeEnd, "yyyy-MM-dd"), 1): null);
		}

		return params;
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public String getUp_createTimeStart() {
		return up_createTimeStart;
	}

	public void setUp_createTimeStart(String up_createTimeStart) {
		this.up_createTimeStart = up_createTimeStart;
	}

	public String getUp_createTimeEnd() {
		return up_createTimeEnd;
	}

	public void setUp_createTimeEnd(String up_createTimeEnd) {
		this.up_createTimeEnd = up_createTimeEnd;
	}

	public Page<DistributionTuanDestroyLog> getDestroyCouponCodeList() {
		return destroyCouponCodeList;
	}

	public void setDestroyCouponCodeList(
			Page<DistributionTuanDestroyLog> destroyCouponCodeList) {
		this.destroyCouponCodeList = destroyCouponCodeList;
	}

	public void setDistributionTuanDestroyLogService(
			DistributionTuanDestroyLogService distributionTuanDestroyLogService) {
		this.distributionTuanDestroyLogService = distributionTuanDestroyLogService;
	}

	
	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setDistributionTuanCouponService(
			DistributionTuanCouponService distributionTuanCouponService) {
		this.distributionTuanCouponService = distributionTuanCouponService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getFId() {
		return fId;
	}

	public void setFId(Long fId) {
		this.fId = fId;
	}
	
	
}
