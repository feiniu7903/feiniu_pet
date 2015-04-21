package com.lvmama.back.sweb.market.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.service.market.MarkActivityBlacklistService;
import com.lvmama.comm.pet.po.mark.MarkActivityBlacklist;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONResult;

/**
 * @author shihui
 * 
 *         营销活动黑名单管理(列表、新增、修改、删除)
 */
@ParentPackage("json-default")
@Results({
		@Result(name = "backlist_list", location = "/WEB-INF/pages/back/market/activity/blacklist_list.jsp"),
		@Result(name = "edit_backlist", location = "/WEB-INF/pages/back/market/activity/edit_blacklist.jsp"),
		@Result(name = "import_csv", location = "/WEB-INF/pages/back/market/activity/import_csv.jsp") })
public class MarkActivityBlacklistAction extends BaseAction {

	private static final long serialVersionUID = -5811756932658917486L;

	private String keyword;

	private String keywordType;

	private MarkActivityBlacklistService markActivityBlacklistService;

	private List<MarkActivityBlacklist> markActivityBlacklistList;

	private MarkActivityBlacklist markActivityBlacklist;

	private Long blackId;

	private File file;
	
	private String fileFileName;
	
	private String fileContentType;

	/**
	 * 查询黑名单列表
	 * */
	@Action("/mark_activity/toMarkActivityBacklist")
	public String toMarkActivityBacklist() {
		Map<String, Object> paramMap = new HashMap<String, Object>();

		//没输入关键则查询所有数据
		if (StringUtils.isNotBlank(keyword)) {
			if (StringUtils.isNotBlank(keywordType)) {
				if("EMAIL".equals(keywordType)) {
					paramMap.put("email", keyword);
				} else {
					paramMap.put("mobileNumber", keyword);
				}
			} else {//未选择查询类型则查询所有类型
				paramMap.put("mobileNumber", keyword);
				paramMap.put("email", keyword);
			}
		}
		pagination = super.initPagination();

		Long totalRowCount = markActivityBlacklistService
				.getMarkActivityBlacklistCount(paramMap);

		paramMap.put("_startRow", pagination.getFirstRow());
		paramMap.put("_endRow", pagination.getLastRow());

		markActivityBlacklistList = markActivityBlacklistService
				.getMarkActivityBlacklist(paramMap);

		pagination.setTotalRecords(totalRowCount);
		pagination.setRecords(markActivityBlacklistList);
		pagination.setActionUrl(WebUtils.getUrl(
				"/mark_activity/toMarkActivityBacklist.do", true, paramMap));
		return "backlist_list";
	}

	/**
	 * 新增或修改弹出框
	 * */
	@Action(value = "/mark_activity/showEditDialog")
	public String showEditDialog() {
		if (blackId != null) {
			markActivityBlacklist = markActivityBlacklistService
					.selectByPrimaryKey(blackId);
		}
		return "edit_backlist";
	}

	/**
	 * 新增或修改黑名单
	 * */
	@Action(value = "/mark_activity/editMarkActivityBlacklist")
	public void editMarkActivityBlacklist() {
		JSONResult result = new JSONResult();
		try {
			String mobileNumber = markActivityBlacklist.getMobileNumber();
			String email = markActivityBlacklist.getEmail();
			//校验数据格式
			if(mobileNumber == null && email == null) {
				throw new Exception("数据不能为空!");
			} else {
				if(StringUtils.isNotEmpty(mobileNumber)) {
					if(!StringUtil.validMobileNumber(mobileNumber)) {
						throw new Exception("手机号码格式错误!");
					}
				}
				if(StringUtils.isNotEmpty(email)) {
					if(!StringUtil.validEmail(email)) {
						throw new Exception("邮箱地址格式错误!");
					}
				}
			}
			//检验是否已经存在该用户
			if(checkIsExisted(mobileNumber, email, markActivityBlacklist.getBlackId())) {
				throw new Exception("该用户已经存在!");
			}
			// 新增
			if (markActivityBlacklist.getBlackId() == null) {
				markActivityBlacklistService
						.saveMarkActivityBlacklist(markActivityBlacklist);
			} else {// 修改
				markActivityBlacklistService
						.updateMarkActivityBlacklist(markActivityBlacklist);
			}
		} catch (Exception e) {
			result.raise(e.getMessage());
		}
		result.output(getResponse());
	}

	/**
	 * 删除黑名单
	 * */
	@Action(value = "/mark_activity/deleteMarkActivityBlacklist")
	public void deleteMarkActivityBlacklist() {
		JSONResult result = new JSONResult();
		try {
			if(blackId == null) {
				throw new Exception("操作异常!");
			}
			markActivityBlacklistService
					.deleteMarkActivityBlacklist(blackId);
		} catch (Exception e) {
			result.raise(e.getMessage());
		}
		result.output(getResponse());
	}

	/**
	 * 导入文件弹出框
	 * */
	@Action(value = "/mark_activity/showImportDialog")
	public String showImportDialog() {
		return "import_csv";
	}

	/**
	 * 导入csv
	 * */
	@Action(value = "/mark_activity/importCsv")
	public void importCsv() {
		JSONResult result = new JSONResult();
		BufferedReader reader = null;
		String msg = "";
		try {
			if(file == null) {
				throw new Exception("操作异常！");
			}
			
			reader = new BufferedReader(new FileReader(file));
			reader.readLine();// 第一行信息，为标题信息，不用,如果需要，注释掉
			String line = null;
			while ((line = reader.readLine()) != null) {
				String item[] = line.split(",");// CSV格式文件为逗号分隔符文件，这里根据逗号切分
				String mobileNumber = null, email = null;
				//手机号或者邮箱地址可能为空,可能的情况有：1,15901728644;2,shihui@lvmama.com;3,15901728644,shihui@lvmama.com
				boolean flag = false;
				for (int i = 1; i < item.length; i++) {
					String value = item[i];
					//是否是手机号
					if(StringUtil.validMobileNumber(value)) {
						mobileNumber = value;
					} else if(StringUtil.validEmail(value)) {//是否是邮箱
						email = value;
					} else {
						flag = true;
					}
				}
				if(flag) {
					//若都不是,则记录,操作结束给予提示
					msg += "编号为" + item[0] + "的数据有误!请核对!\n";
				}
				if (StringUtils.isNotEmpty(mobileNumber)
						|| StringUtils.isNotEmpty(email)) {
					//检验是否已经存在该用户
					if(!checkIsExisted(mobileNumber, email, null)) {
						MarkActivityBlacklist mab = new MarkActivityBlacklist();
						mab.setMobileNumber(mobileNumber);
						mab.setEmail(email);
						markActivityBlacklistService.saveMarkActivityBlacklist(mab);
					} else {
						//若都不是,则记录,操作结束给予提示
						msg += "编号为" + item[0] + "的用户已经存在!\n";
					}
				}
			}
		} catch (Exception e) {
			result.raise(e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if(StringUtil.isNotEmptyString(msg)) {
			result.raise(msg);
		}
		result.output(getResponse());
	}

	/**
	 * 校验用户是否已存在
	 * 
	 * @param mobileNumber
	 * @param email
	 * 
	 * @return 存在返回true,否则返回false
	 * */
	private boolean checkIsExisted(String mobileNumber, String email, Long blackId) {
		//检验是否已经存在该用户
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("mobileNumber", mobileNumber);
		paramMap.put("email", email);
		if(blackId != null) {
			paramMap.put("blackId", blackId);
		}
		Long count = markActivityBlacklistService.checkIsExisted(paramMap);
		return count > 0;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeywordType() {
		return keywordType;
	}

	public void setKeywordType(String keywordType) {
		this.keywordType = keywordType;
	}

	public List<MarkActivityBlacklist> getMarkActivityBlacklistList() {
		return markActivityBlacklistList;
	}

	public MarkActivityBlacklist getMarkActivityBlacklist() {
		return markActivityBlacklist;
	}

	public void setMarkActivityBlacklist(
			MarkActivityBlacklist markActivityBlacklist) {
		this.markActivityBlacklist = markActivityBlacklist;
	}

	public void setBlackId(Long blackId) {
		this.blackId = blackId;
	}

	public Long getBlackId() {
		return blackId;
	}

	public void setMarkActivityBlacklistService(
			MarkActivityBlacklistService markActivityBlacklistService) {
		this.markActivityBlacklistService = markActivityBlacklistService;
	}

	public void setMarkActivityBlacklistList(
			List<MarkActivityBlacklist> markActivityBlacklistList) {
		this.markActivityBlacklistList = markActivityBlacklistList;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

}
