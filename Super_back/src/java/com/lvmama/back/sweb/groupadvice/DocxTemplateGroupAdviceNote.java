package com.lvmama.back.sweb.groupadvice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
public class DocxTemplateGroupAdviceNote extends GroupAdviceNote {
	private static final Logger logger = Logger.getLogger(DocxTemplateGroupAdviceNote.class);
	//html模板文件存放路径. Map<产品子类型,模板文档路径>
	private Map<String,String> templateMap = new HashMap<String,String>();
	
	public DocxTemplateGroupAdviceNote() {
			templateMap.put(Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name(), ResourceUtil.getResourceFile(GroupAdviceNoteUtils.TEMPLATE_FOLDER + "/freeness_long.docx").getAbsolutePath());
			templateMap.put(Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name(), ResourceUtil.getResourceFile(GroupAdviceNoteUtils.TEMPLATE_FOLDER + "/group_long.docx").getAbsolutePath());
			templateMap.put(Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name(), ResourceUtil.getResourceFile(GroupAdviceNoteUtils.TEMPLATE_FOLDER + "/group_foreign.docx").getAbsolutePath());
			templateMap.put(Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name(), ResourceUtil.getResourceFile(GroupAdviceNoteUtils.TEMPLATE_FOLDER + "/freeness_foreign.docx").getAbsolutePath());
	}
	
	protected void doInitDataMap() {
	//行程
	 this.dataMap.put("viewJourneyVoList", this.getViewJourneyVoList());
	 //出游人信息,客人信息包括：姓名、证件类型、（身份证号和护照号、其他）证件号.
	 this.dataMap.put("travellerList", this.ordOrder.getTravellerList());
	}

	protected String fileSuffix() {
		return GroupAdviceNoteUtils.DOCX_FILE_SUFFIX;
	}

	protected void doCreateFile() {
		DocxTransformer docxTransformer = new DocxTransformer();
		String sourceFile = super.getTemplatePath();
		if (sourceFile == null) {
			sourceFile = templateMap.get(super.prodProduct.getSubProductType());
		}
		String targetFile = this.getFileName();
		try {
			docxTransformer.tranfDocx(sourceFile, super.dataMap, targetFile);
		} catch (FileNotFoundException e) {
			logger.info(e);
		} catch (IOException e) {
			logger.info(e);
		}
	}

}
