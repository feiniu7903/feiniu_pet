package com.lvmama.back.sweb.groupadvice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;

import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;

public class HtmlTemplateGroupAdviceNote extends GroupAdviceNote {
	private static final Logger logger = Logger.getLogger(HtmlTemplateGroupAdviceNote.class);
	//html模板文件存放路径. Map<产品子类型,模板文档路径>
	private Map<String,String> templateMap = new HashMap<String,String>();
	public HtmlTemplateGroupAdviceNote() {
			templateMap.put(Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name(), ResourceUtil.getResourceFile(GroupAdviceNoteUtils.TEMPLATE_FOLDER + "/freeness_long.template").getAbsolutePath());
			templateMap.put(Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name(), ResourceUtil.getResourceFile(GroupAdviceNoteUtils.TEMPLATE_FOLDER + "/group_long.template").getAbsolutePath());
	}
	
	protected String fileSuffix() {
		return GroupAdviceNoteUtils.HTML_FILE_SUFFIX;
	}
	
	/**
	 * 根据产品类型获取模板文件原始内容
	 * @param subProductType
	 * @return
	 */
	public String getTplContent(String subProductType) throws Exception{
		String content =null;
		String sourceFile = templateMap.get(subProductType);
		if(null==sourceFile){
			throw new RuntimeException("不存在该类型产品模板");
		}
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile)));
			content = FileCopyUtils.copyToString(br);
		}catch(Exception e){
			logger.info(e);
		}
		return content;
	}
	
	/**
	 * 渲染内容
	 * @param content
	 */
	public String initContentFromData(String content){
			super.initDataMap();
			return composeMessage(content,this.dataMap);
	}
	
	protected void doCreateFile() {
		String sourceFile = templateMap.get(super.prodProduct.getSubProductType());
		String targetFile = super.getFileName();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile)));
			String content = org.springframework.util.FileCopyUtils.copyToString(br);
			content = composeMessage(content,this.dataMap);
			FileOutputStream out = new FileOutputStream(new File(targetFile));
			out.write(content.getBytes("UTF-8"));
			out.flush();
			out.close();
			 
		
		} catch (FileNotFoundException e) {
			logger.info(e);
		} catch (IOException e) {
			logger.info(e);
		}catch (Exception e) {
			logger.info(e);
		}
		
	}
	
	/**
	 * 将模板文件(template)中的${placeholder}占位符替换为dataMap中的有效值.
	 * @param template
	 * @param dataMap
	 * @return
	 */
	private static String composeMessage(String template, Map<String,Object> dataMap) {
		String result = template;
		for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
			result = result.replaceAll("\\$\\{" + entry.getKey() + "\\}", "" + processNullValue(entry.getValue()));
		}
		return result;
	}
	
	protected void doInitDataMap() {
	//行程
	 this.dataMap.put("viewJourneyVoList", this.createViewJourneyVoListString());
	 //出游人信息,客人信息包括：姓名、证件类型、（身份证号和护照号、其他）证件号.
	 this.dataMap.put("travellerList", this.createTravellerListString());
	 //费用说明,费用包含、费用不包含.
	 this.dataMap.put("costContain", this.createCostContain());
	 //行前须知.
	 this.dataMap.put("acitonToknow", this.createActionToknow());
	}

	private String createTravellerListString() {
		StringBuilder sb = new StringBuilder();
		List<OrdPerson> travellerList = super.ordOrder.getTravellerList();
		for (OrdPerson e : travellerList) {
			sb.append("<tr>");
			sb.append("<td>");
			sb.append(processNullValue(e.getName()));
			sb.append("</td>");
			sb.append("<td>");
			sb.append(processNullValue(e.getZhCertType()));
			sb.append("</td>");
			sb.append("<td>");
			sb.append(processNullValue(e.getCertNo()));
			sb.append("</td>");
			sb.append("</tr>");
		}
		return sb.toString();
	}

	private String createViewJourneyVoListString() {
		StringBuilder sb = new StringBuilder();
		List<ViewJourneyVo> viewJourneyVoList = super.getViewJourneyVoList();
		for (ViewJourneyVo e : viewJourneyVoList) {
			sb.append("<tr>");
			sb.append("<td>");
			sb.append(processNullValue(e.getVisitTime()));
			sb.append("</td>");
			sb.append("<td>");
			sb.append(processNullValue(e.getContent()));
			sb.append("</td>");
			sb.append("<td>");
			sb.append(processNullValue(e.getDinner()));
			sb.append("</td>");
			sb.append("<td>");
			sb.append(processNullValue(e.getHotel()));
			sb.append("</td>");
			sb.append("</tr>");
		}
		return sb.toString();
	}
	
	private String createCostContain() {
		StringBuilder sb = new StringBuilder();
		String costContain = "";   //费用包含.
		String noCostContain = ""; //费用不包含.
		 for (Map.Entry<String, Object> entry: super.viewPage.getContents().entrySet()) {
			 String type=entry.getKey();
			 String content=String.valueOf(entry.getValue());
			 if (StringUtils.isBlank(content)) {
				 continue;
			 }
			 if (Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name().equals(type)) {
				 costContain  = content;
			 }
			 if (Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name().equals(type)) {
				 noCostContain = content;
			 }
		 }
		 sb.append("费用包含:");
		 sb.append(costContain);
		 sb.append("<br/>");
		 sb.append("费用不包含:");
		 sb.append(noCostContain);
		return sb.toString();
	}
	
	private String createActionToknow() {
		String result = "";
		for (Map.Entry<String, Object> entry:  super.viewPage.getContents().entrySet()) {
			 String type=entry.getKey();
			 String content=String.valueOf(entry.getValue());
			 if (StringUtils.isBlank(content)) {
				 continue;
			 }
			 if (Constant.VIEW_CONTENT_TYPE.ACITONTOKNOW.name().equals(type)) {
				result =content;
			 }
		 }
		return result;
	}
	
	/**
	 * 如果参数为null值,则返回空字符串.
	 * @param arg0
	 * @return
	 */
	private static String processNullValue(Object arg0) {
		return arg0 == null ? "" : arg0.toString();
	}
	
}
