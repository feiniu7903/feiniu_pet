package com.lvmama.distribution.model.ckdevice.vo;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dom4j.DocumentException;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ConstantMsg;

public class CKProductInfoBody implements CKBody {

	String currentPage;
	String productType;
	String pageSize;
	String beginDate;
	String endDate;
	
	
	public String getCurrentPage() {
		return StringUtil.replaceNullStr(currentPage);
	}


	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}


	public String getProductType() {
		return StringUtil.replaceNullStr(productType);
	}


	public void setProductType(String productType) {
		this.productType = productType;
	}


	public String getPageSize() {
		return StringUtil.replaceNullStr(pageSize);
	}


	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}


	public String getBeginDate() {
		return StringUtil.replaceNullStr(beginDate);
	}


	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}


	public String getEndDate() {
		return StringUtil.replaceNullStr(endDate);
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getKeyWord(){
		return getCurrentPage()+getPageSize()+getProductType();
	}
	
	public int getStartRow(){
		int startRow = ((Integer.valueOf(currentPage) - 1) * Integer.valueOf(pageSize)) + 1;
		return startRow<1?1:startRow;
	}

	public int getEndRow(){
		int len = Integer.valueOf(currentPage) * Integer.valueOf(pageSize);
		return len<1?1:len;
	}
	public Date getBeginTime(){
		if(StringUtils.isNotBlank(beginDate)){
			return DateUtil.toDate(beginDate, "yyyy-MM-dd");
		}else{
			return DateUtil.getTodayYMDDate();
		}
	}
	public Date getEndTime(){
		if(StringUtils.isNotBlank(beginDate)){
			return DateUtil.toDate(endDate, "yyyy-MM-dd");
		}else{
			return DateUtils.addDays(DateUtil.getTodayYMDDate(), 1);
		}
	}
	@Override
	public void init(String requestXml) throws DocumentException {
		currentPage = TemplateUtils.getElementValue(requestXml, "//request/body/parameter/currentPage");
		pageSize = TemplateUtils.getElementValue(requestXml, "//request/body/parameter/pageSize");
		productType = TemplateUtils.getElementValue(requestXml, "//request/body/parameter/productType");
		beginDate = TemplateUtils.getElementValue(requestXml, "//request/body/parameter/priceTimeStart");
		endDate = TemplateUtils.getElementValue(requestXml, "//request/body/parameter/priceTimeEnd");
	}


	public String checkParams() {
		if(StringUtil.isNumber(currentPage)&&StringUtil.isNumber(pageSize) && (Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)||Constant.PRODUCT_TYPE.TICKET.name().equals(productType))){
			if(StringUtils.isNotBlank(beginDate)){
				if(StringUtil.isDate(beginDate) && StringUtil.isDate(endDate)){
					return ConstantMsg.CK_MSG.SUCCESS.getCode();
				}
				return ConstantMsg.CK_MSG.UNDEFINED_PARAM.getCode();
			}
			return ConstantMsg.CK_MSG.SUCCESS.getCode();
		}
		return ConstantMsg.CK_MSG.UNDEFINED_PARAM.getCode();
	}

}
