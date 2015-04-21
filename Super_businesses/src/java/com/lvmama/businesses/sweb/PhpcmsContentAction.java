package com.lvmama.businesses.sweb;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.businesses.review.util.KeyWordUtils;
import com.lvmama.comm.businesses.po.review.PhpcmsContent;
import com.lvmama.comm.businesses.service.review.InfonewsService;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.vo.Constant;

@Results({ @Result(name = "phpcmsContent", location = "/WEB-INF/pages/web/review/phpcmsContent.jsp") })
public class PhpcmsContentAction extends ContentAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 343434341L;
	private String subTable;
	@Autowired
	private InfonewsService infonewsService;

	@Action("/phpcmsContent/query")
	public String query() {
		super.initParam();
		param.put("tableName", this.subTable==null?"phpcms_c_down":this.subTable);
		pagination = initPage();
		pagination.setCurrentPage(pagination.getCurrentPage());
		pagination.setTotalResultSize(infonewsService.countForPhpcmsC(param));
		if (pagination.getTotalResultSize() > 0) {
			param.put("start", pagination.getStartRows() - 1);
			param.put("end", pagination.getPageSize());
			pagination.setAllItems(infonewsService.queryPhpcmsCByParam(param));
		}
		pagination.buildUrl(getRequest());

		return "phpcmsContent";
	}

	@Action("/phpcmsContent/update")
	public void update() {
		if (StringUtils.isNotBlank(arrayStr)) {
			List<String[]> list = parseArray(arrayStr);
			for (String[] m : list) {
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("contentid", m[0]);
				param.put("reviewstatus", m[1]);
				param.put("tableName", this.subTable);
				PhpcmsContent old=infonewsService.queryForPhpcmsContentById(Integer.valueOf(m[0]),this.subTable);
				if(null!=old&&(!old.getReviewstatus().equals(m[1]))){
					
					infonewsService.updateForPhpcmsC(param);
					if (m[1].equals(Constant.REVIEW_STATUS.black.getCode())) {
						param.put("status", "1");
						infonewsService.updateForPhpcmsContent(param);
					}
					if (old.getReviewstatus().equals(Constant.REVIEW_STATUS.black.getCode())) {
						param.put("status", "99");
						infonewsService.updateForPhpcmsContent(param);
					}
					//日志
					ComLog comlog=new ComLog();
					comlog.setObjectId(Long.valueOf(m[0]));
					comlog.setObjectType(KeyWordUtils.BBSCONTENT);
					comlog.setCreateTime(new Date());
					comlog.setContent("状态改为"+Constant.REVIEW_STATUS.getCnNameByCode(m[1]));
					comlog.setOperatorName(super.getSessionUserName());
					comLogService.addComLog(comlog);
				}
			}
			this.sendAjaxMsg("true");
		}
		 this.sendAjaxMsg("false");
	}

	@Override
	public String getMemcachPageSizeKey() {
		return KeyWordUtils.BUSSINESS_PAGE_SIZE_ + KeyWordUtils.PHPCMSCONTENT
				+ this.subTable;
	}

	/**
	 * @return the subTable
	 */
	public String getSubTable() {
		return subTable;
	}

	/**
	 * @return the subTables
	 */
	public Map getSubTables() {
		Map subTables = new HashMap<String, Object>();
		subTables.put("phpcms_c_" + "down", "phpcms_c_" + "down");
		subTables.put("phpcms_c_" + "guide", "phpcms_c_" + "guide");
		subTables.put("phpcms_c_" + "info", "phpcms_c_" + "info");
		subTables.put("phpcms_c_" + "ku6video", "phpcms_c_" + "ku6video");
		subTables.put("phpcms_c_" + "minisite", "phpcms_c_" + "minisite");
		subTables.put("phpcms_c_" + "news", "phpcms_c_" + "news");
		subTables.put("phpcms_c_" + "picture", "phpcms_c_" + "picture");
		subTables.put("phpcms_c_" + "product", "phpcms_c_" + "product");
		subTables.put("phpcms_c_" + "video", "phpcms_c_" + "video");
		return subTables;
	}

	/**
	 * @param subTable the subTable to set
	 */
	public void setSubTable(String subTable) {
		this.subTable = subTable;
	}

}
