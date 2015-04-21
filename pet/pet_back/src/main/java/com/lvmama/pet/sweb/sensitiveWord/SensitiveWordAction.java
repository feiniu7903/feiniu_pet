package com.lvmama.pet.sweb.sensitiveWord;

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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.sensitiveW.SensitiveWord;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sensitiveW.SensitiveWordService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.LogObject;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

/**
 * 敏感词管理类
 * 
 * @author shihui
 * 
 */
@SuppressWarnings("serial")
@Results({
		@Result(name = "s_word_list", location = "/WEB-INF/pages/back/sensitiveWord/s_word_list.jsp"),
		@Result(name = "edit_s_word", location = "/WEB-INF/pages/back/sensitiveWord/edit_s_word.jsp"),
		@Result(name = "import_excel", location = "/WEB-INF/pages/back/sensitiveWord/import_excel.jsp")})
public class SensitiveWordAction extends BackBaseAction {

	private String content;

	private SensitiveWordService sensitiveWordService;

	private Long sensitiveId;

	private SensitiveWord sensitiveWord;

	private Long[] sensitiveIds;

	private ComLogService comLogService;
	
	private File file;
	
	private String fileFileName;
	
	private String fileContentType;
	
	private static final Log logger = LogFactory.getLog(SensitiveWordAction.class);

	/**
	 * 页面入口
	 * 
	 * @return
	 */
	@Action(value = "/sensitiveWord/toSensitiveWord")
	public String toSensitiveWord() {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(content)) {
			params.put("content", content);
		}
		Long totalRecords = sensitiveWordService.selectByParamsCount(params);
		pagination = Page.page(30, page);
		pagination.setTotalResultSize(totalRecords);
		pagination.buildUrl(getRequest());

		params.put("_startRow", pagination.getStartRows());
		params.put("_endRow", pagination.getEndRows());

		pagination.setItems(sensitiveWordService.selectByParams(params));
		return "s_word_list";
	}

	/**
	 * 新增或修改弹出框
	 * */
	@Action(value = "/sensitiveWord/showEditDialog")
	public String showEditDialog() {
		if (sensitiveId != null) {
			sensitiveWord = sensitiveWordService
					.selectByPrimaryKey(sensitiveId);
		}
		return "edit_s_word";
	}

	/**
	 * 新增或修改敏感词
	 * */
	@Action(value = "/sensitiveWord/editSensitiveWord")
	public void editSensitiveWord() {
		JSONResult result = new JSONResult();
		try {
			if (sensitiveWord == null) {
				throw new Exception("数据为空!");
			}
			sensitiveWord.setContent(sensitiveWord.getContent().trim());
			// 新增
			if (sensitiveWord.getSensitiveId() == null) {
				String content = sensitiveWord.getContent();
				String[] cs = content.split(",");
				String msg = "";
				for (int i = 0; i < cs.length; i++) {
					String con = cs[i].trim();
					if(StringUtils.isEmpty(con)) {
						continue;
					}
					SensitiveWord sw = new SensitiveWord();
					sw.setContent(con);
					Long id = sensitiveWordService.insert(sw);
					if(id == null) {
						msg += "敏感词：'" + con + "'已经存在!\n";
					} else {
						sw.setSensitiveId(id);
						LogObject.addSensitiveWordLog(sw,
								this.getSessionUserNameAndCheck(),
								comLogService);
					}
				}
				if (StringUtils.isNotEmpty(msg)) {
					throw new Exception(msg);
				}
			} else {// 修改
				SensitiveWord sw = sensitiveWordService
						.selectByPrimaryKey(sensitiveWord.getSensitiveId());
				if (sw != null) {
					if(!sw.getContent().equals(sensitiveWord.getContent())) {
						Long count = sensitiveWordService.update(sensitiveWord);
						if(count == null) {
							throw new Exception("敏感词：'" + sensitiveWord.getContent() + "'已经存在!");
						} else { 
							LogObject.updateSensitiveWordLog(sensitiveWord, sw,
									this.getSessionUserNameAndCheck(), comLogService);
						}
					}
				}
			}
		} catch (Exception e) {
			result.raise(e.getMessage());
		}
		result.output(getResponse());
	}

	/**
	 * 删除敏感词
	 * */
	@Action(value = "/sensitiveWord/deleteSensitiveWord")
	public void deleteSensitiveWord() {
		JSONResult result = new JSONResult();
		try {
			if (sensitiveId == null) {
				throw new Exception("操作异常!");
			}
			SensitiveWord sw = sensitiveWordService
					.selectByPrimaryKey(sensitiveId);
			if (sw != null) {
				sensitiveWordService.deleteByPrimaryKey(sensitiveId);
				LogObject.deleteSensitiveWordLog(sw,
						this.getSessionUserNameAndCheck(), comLogService);
			}
		} catch (Exception e) {
			result.raise(e.getMessage());
		}
		result.output(getResponse());
	}

	/**
	 * 批量删除敏感词
	 * */
	@Action(value = "/sensitiveWord/deleteSensitiveWords")
	public void deleteSensitiveWords() {
		JSONResult result = new JSONResult();
		try {
			if (sensitiveIds == null || sensitiveIds.length < 1) {
				throw new Exception("操作异常!");
			}
			List<SensitiveWord> list = sensitiveWordService.selectListByIds(sensitiveIds);
			sensitiveWordService.deleteByIds(sensitiveIds);
			for (int i = 0; i < list.size(); i++) {
				LogObject.deleteSensitiveWordLog(list.get(i),
						this.getSessionUserNameAndCheck(), comLogService);
			}
		} catch (Exception e) {
			result.raise(e.getMessage());
		}
		result.output(getResponse());
	}

	private void output(List<SensitiveWord> list, String template,String key) {
		FileInputStream fin = null;
		OutputStream os = null;
		try {
			File templateResource = ResourceUtil.getResourceFile(template);
			Map<String, Object> beans = new HashMap<String, Object>();
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
	
	@Action(value = "/sensitiveWord/exportAll")
	public void exportAll() {
		List<SensitiveWord> list = this.sensitiveWordService.getAllSensitiveWords();
		logger.info("exportAll...有" + list.size() + "条记录");
		if(!list.isEmpty()) {
			String template = "/WEB-INF/resources/template/sensitiveWordTemplate.xls";
			output(list, template,"sensitiveWordList");
		}
	}
	
	@Action(value = "/sensitiveWord/showImportDialog")
	public String showImportDialog() {
		return "import_excel";
	}
	
	/**
	 * 导入excel
	 * */
	@Action(value = "/sensitiveWord/importExcel")
	public void importExcel() {
		JSONResult result = new JSONResult();
		String msg = "";
		try {
			if(file == null) {
				throw new Exception("操作异常！");
			}
			Workbook  xwb = WorkbookFactory.create(new FileInputStream(file));
			Sheet sheet = xwb.getSheetAt(0);  
			// 循环输出表格中的内容  
			for (int i = sheet.getFirstRowNum() + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row  row = sheet.getRow(i);  
		    	Cell cell = row.getCell(1);
		    	if(cell == null){
		    		continue;
		    	}
		    	String value = cell.toString().trim();
		    	if(Cell.CELL_TYPE_NUMERIC == cell.getCellType() && value.matches("^[-+]?\\d+\\.[0]+$")){
		    		value = value.substring(0, value.indexOf("."));
		    	}
		        if(StringUtils.isNotBlank(value)){
					SensitiveWord sw = new SensitiveWord();
					sw.setContent(value);
					Long id = sensitiveWordService.insert(sw);
					if(id == null) {
						msg += "内容为[" + value + "]的敏感词已经存在!\n";
					} else {
						sw.setSensitiveId(id);
						LogObject.addSensitiveWordLog(sw,
								this.getSessionUserNameAndCheck(),
								comLogService);
					}
		        }
			}
		} catch (Exception e) {
			result.raise(e.getMessage());
		} 
		if(StringUtil.isNotEmptyString(msg)) {
			result.raise(msg);
		}
		result.output(getResponse());
	}
	
	public void setSensitiveWordService(
			SensitiveWordService sensitiveWordService) {
		this.sensitiveWordService = sensitiveWordService;
	}

	public Long getSensitiveId() {
		return sensitiveId;
	}

	public void setSensitiveId(Long sensitiveId) {
		this.sensitiveId = sensitiveId;
	}

	public SensitiveWord getSensitiveWord() {
		return sensitiveWord;
	}

	public void setSensitiveWord(SensitiveWord sensitiveWord) {
		this.sensitiveWord = sensitiveWord;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long[] getSensitiveIds() {
		return sensitiveIds;
	}

	public void setSensitiveIds(Long[] sensitiveIds) {
		this.sensitiveIds = sensitiveIds;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
}
