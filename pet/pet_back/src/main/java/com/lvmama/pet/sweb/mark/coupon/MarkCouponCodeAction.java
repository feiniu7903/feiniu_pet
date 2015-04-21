package com.lvmama.pet.sweb.mark.coupon;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;

public class MarkCouponCodeAction extends CouponBackBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 772690450417059786L;
	/**
	 * 优惠码列表
	 */
	private List<MarkCouponCode> markCouponCodeList;
	/**
	 * 优惠券批次标识
	 */
	private Long couponId;
	/**
	 * 生成优惠码数量
	 */
	private int generateNumber;
	/**
	 * 导出excel的文件名
	 */
	private String excelFileName;
	/**
	 * 导出excel的文件流
	 */
	private InputStream excelStream;
	
	private MarkCoupon markCoupon = new MarkCoupon();
	

	/**
	 * 打开生成优惠码的页面
	 * @return
	 */
	@Action(value="/mark/coupon/createMarkCouponCodes",results={@Result(location = "/WEB-INF/pages/back/mark/createMarkCouponCodes.jsp")})
	public String createMarkCouponCodes() {	
		return SUCCESS;
	}
	
	/**
	 * 显示指定批次下的优惠码列表
	 * @return
	 */
	@Action(value="/mark/coupon/queryMarkCouponCodes",results={@Result(location = "/WEB-INF/pages/back/mark/couponCodeList.jsp")})
	public String queryMarkCouponCodes() {
		this.initMarkCoupon();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("couponId", couponId);
		
		Integer totalRecords = markCouponService.selectMarkCouponCodeRowCount(parameters);
		pagination = Page.page(10, page);
		pagination.setTotalResultSize(totalRecords);
		pagination.setUrl("javascript:refreshDiv(argPage)");
		//pagination.setUrl("/mark/coupon/createMarkCouponCodes?couponId=" + couponId + "&page=");
		parameters.put("_startRow", pagination.getStartRows());
		parameters.put("_endRow", pagination.getEndRows());
		
		markCouponCodeList = markCouponService.selectMarkCouponCodeByParam(parameters);
		for(int i = 0; i < markCouponCodeList.size(); i++){
			MarkCouponCode markCouponCode = markCouponCodeList.get(i);
			MarkCoupon markCoupon = markCouponService.selectMarkCouponByPk(markCouponCode.getCouponId());
			markCouponCode.setValidTimeByCouponDefination(markCoupon);
		}
		return SUCCESS;
	}
	
	private void initMarkCoupon() {
		this.markCoupon = this.markCouponService.selectMarkCouponByPk(this.couponId);
	}
	
	/**
	 * 生成优惠码
	 * @return
	 */
	@Action(value="/mark/coupon/generateMarkCouponCodes")
	public void generateMarkCouponCodes() {
		Map<String,Object> jsonMessage = new HashMap<String,Object>();
		if (0 != generateNumber && null != couponId) {
			MarkCoupon markCoupon = markCouponService.selectMarkCouponByPk(couponId);
			
	     	if (null == markCoupon || !"true".equals(markCoupon.getValid())) {
	     		jsonMessage.put("success", false);
				jsonMessage.put("message", "优惠为空或不可用。");
	     	} else{
				    markCouponService.generateMarkCouponCodeByCouponId(couponId, generateNumber, "force");
					jsonMessage.put("success", true);
					String OperateName = super.getSessionUserName();
					this.saveComLog("COUPON_BUSINESS_BIND", couponId , OperateName, Constant.COUPON_ACTION.COUPON_GENERATE.name(),
							"生成优惠券", "优惠券名称为：" + (String)markCoupon.getCouponName());
	     	}
		}else{
			jsonMessage.put("success", false);
			jsonMessage.put("message", "优惠ID为空或数字非法。");
		}
		try {
			this.getResponse().getWriter().println(JSONObject.fromObject(jsonMessage));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*
	@Action(value="/mark/coupon/generateMarkCouponCodes",results={@Result(location = "/WEB-INF/pages/back/mark/couponCodeList.jsp")})
	public String generateMarkCouponCodes() {
		if (0 != generateNumber && null != couponId) {
			markCouponService.generateMarkCouponCodeByCouponId(couponId, generateNumber);
		}
		return queryMarkCouponCodes();
	}
	*/
	
	/**
	 * 导出优惠码列表
	 * @return 
	 */
	@Action(value="/mark/coupon/exportExcel",results={@Result(type="stream",name="success",params={"contentType","application/vnd.ms-excel","inputName","excelStream","contentDisposition","attachment;filename=${excelFileName}","bufferSize","1024"})})
	public String exportExcel() throws IOException {
			File templateResource = ResourceUtil.getResourceFile("/WEB-INF/resources/template/couponCodeTemplate.xls");
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/couponCodeTemplate_" + System.currentTimeMillis() +".xls";
			Map<String,Object> beans = new HashMap<String,Object>();
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("couponId", this.couponId);
			markCouponCodeList = new ArrayList<MarkCouponCode>();
			Integer allCodeNum = this.markCouponService.selectMarkCouponCodeRowCount(parameters);
			if (allCodeNum>10000) {
				return INPUT;
			}			
			for(int j = 0; j < allCodeNum; j=j+900){//避免一次返回的数据超过1000，被截取
				parameters.put("_startRow", j+1);
				parameters.put("_endRow", j+900);
				List<MarkCouponCode> returnMarkCouponCodeList = this.markCouponService.selectMarkCouponCodeByParam(parameters);
				markCouponCodeList.addAll(returnMarkCouponCodeList);
			}
			MarkCoupon markCoupon = markCouponService.selectMarkCouponByPk(this.couponId);
			for(int i = 0; i < markCouponCodeList.size(); i++){
				MarkCouponCode markCouponCode = markCouponCodeList.get(i);
				markCouponCode.setValidTimeByCouponDefination(markCoupon);
			}
			beans.put("couponCode", markCouponCodeList);
			
			try {
				new XLSTransformer().transformXLS(templateFileName, beans, destFileName);
			} catch (ParsePropertyException e) {
				e.printStackTrace();
				return INPUT;
			} catch (InvalidFormatException e) {
				e.printStackTrace();
				return INPUT;
			}
			 
			File file = new File(destFileName);
			
			if (file != null && file.exists()) {				
				 this.excelFileName = file.getName();
				 this.excelStream = new FileInputStream(file);
				 return SUCCESS;
			} else {
				return INPUT;
			}
		
		
	}	
	
	public MarkCoupon getMarkCoupon() {
		return markCoupon;
	}

	public void setMarkCoupon(MarkCoupon markCoupon) {
		this.markCoupon = markCoupon;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(final Long couponId) {
		this.couponId = couponId;
	}

	public int getGenerateNumber() {
		return generateNumber;
	}

	public void setGenerateNumber(final int generateNumber) {
		this.generateNumber = generateNumber;
	}

	public List<MarkCouponCode> getMarkCouponCodeList() {
		return markCouponCodeList;
	}

	public void setMarkCouponService(final MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}	
	
	

//	public void setMarkCouponService(MarkCouponService markCouponService) {
//		this.markCouponService = markCouponService;
//	}
//	
//	//生成指定数量的优惠券code码,并保存到数据库中.
//		private void saveMarkCouponCode(int num,MarkCoupon markCoupon) {
//			List<MarkCouponCode> markCouponCodeList = this.createMarkCouponCodeBatch(num, markCoupon);
//			this.markCouponService.insertMarkCouponCodeBatch(markCouponCodeList);
//		}
//	
//	//生成指定数量的随机优惠券code码.
//		private List<MarkCouponCode> createMarkCouponCodeBatch(int num,MarkCoupon markCoupon) {
//			List<MarkCouponCode> result = new ArrayList<MarkCouponCode>(num);
//			Set<String> set = new HashSet<String>();
//			int i = 0;
//			int begin = 0;
//			while (true) {
//				 Random random = new Random(System.currentTimeMillis() + (i++));
//				 long r = random.nextLong();
//				 r = Math.abs(r);
//				 String rString = String.valueOf(r);
//				 begin = random.nextInt(rString.length() - COUPON_CODE_FRAGMENT_LENGTH);
//				 String couponCodeFragment = rString.substring(begin, begin + COUPON_CODE_FRAGMENT_LENGTH);
//				 set.add(couponCodeFragment);
//				if (set.size() >= num) {
//					break;
//				}
//			}
//
//			for (String couponCodeFragment : set) {
//				 String couponCode = new StringBuilder().append(markCoupon.getCouponType()).append(markCoupon.getFirstCode()).append(couponCodeFragment).toString();
//				 MarkCouponCode markCouponCode = new MarkCouponCode();
//				 markCouponCode.setCouponId(markCoupon.getCouponId());
//				 markCouponCode.setCouponCode(couponCode);
//				 result.add(markCouponCode);
//			}
//			return result;
//		}
//		
//		private InputStream excelStream;
//		
//		private String excelFileName;
//		
//		public InputStream getExcelStream() {
//			return excelStream;
//		}
//
//		public void setExcelStream(InputStream excelStream) {
//			this.excelStream = excelStream;
//		}
//
//		public String getExcelFileName() {
//			return excelFileName;
//		}
//
//		public void setExcelFileName(String excelFileName) {
//			this.excelFileName = excelFileName;
//		}
//

		
		
	
}
