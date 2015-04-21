package com.lvmama.back.sweb.groupadvice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 
 *  将Map中的数据写入到word模板中.<br/>
 *  word模板中的占位符: ${placeholder}<br/>
 *  循环标签:&lt;jw:forEach item="${placeholder}" var="e">&lt;/jw:forEach>
 *
 */
public class DocxTransformer {
	private static final Logger logger = Logger.getLogger(DocxTransformer.class);
	//占位符前缀.
	private static final String PLACEHODER_PREFIX = "${";
	//占位符后缀.
	private static final String PLACEHODER_SUFFIX = "}";

	public void tranfDocx(String sourceFile, Map<String, Object> dataMap,
			String targetFile) throws FileNotFoundException, IOException {
		FileOutputStream out = new FileOutputStream(new File(targetFile));
		XWPFDocument doc = new XWPFDocument(new FileInputStream(new File(
				sourceFile)));
		 
		List<XWPFParagraph> ps = doc.getParagraphs();
		for (XWPFParagraph p : ps) {
			List<XWPFRun> rs = p.getRuns();
			for (int j = 0; j < rs.size(); j++) {
				XWPFRun r = rs.get(j);
				List<CTText> ts = r.getCTR().getTList();
				for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
					if (r.toString().equals(PLACEHODER_PREFIX + entry.getKey() + PLACEHODER_SUFFIX)) {
						ts.get(0).setStringValue(entry.getValue() == null ? "" : entry.getValue().toString());
					}
				}
			}
		}

		List<XWPFTable> ts = doc.getTables();
		for (XWPFTable t : ts) {
			if (!isValidTable(t)) {
				throw new RuntimeException("<jw:forEach>的开始、结束标签不配对!");
			}
			List<XWPFTableRow> trs = t.getRows();
			for (int m = 0; m < trs.size(); m++) {
				List<XWPFTableCell> trcs = trs.get(m).getTableCells();
				for (int i = 0; i < trcs.size(); i++) {
					XWPFTableCell xtc =  trcs.get(i);
					ForEachBeginTag forEachBeginTag = new ForEachBeginTag(xtc.getText());
					if (forEachBeginTag.getValid()) {
						List<?> list = (List<?>)dataMap.get(forEachBeginTag.getItemAttrValue());
						if (list == null) {
							throw new RuntimeException("模板文件中不存的属性:" + forEachBeginTag.getItemAttrValue());
						}
						XWPFTableRow row = t.getRow(m + 1);
		 					for (int y = 0; y < list.size(); y++) {
							XWPFTableRow newRow = t.createRow();
							newRow.setCantSplitRow(row.isCantSplitRow());
							newRow.setHeight(row.getHeight());
							newRow.setRepeatHeader(row.isRepeatHeader());
							List<XWPFTableCell> rowCess2 = row.getTableCells();
							List<XWPFTableCell> xtcses = newRow.getTableCells();
							Object map2 = list.get(y);
							BeanWrapper beanWrapper = new BeanWrapperImpl(map2);
							for (int x = 0; x < xtcses.size(); x++) {
								String cellText = rowCess2.get(x).getText();
								if (cellText == null || cellText.trim().equals("")) {
									continue;
								}
								String propertyName = cellText.substring(forEachBeginTag.getVarAttrValue().length() + 3,cellText.length() - 1);
								Object propertyValue = beanWrapper.getPropertyValue(propertyName);
								String text = "" + (propertyValue == null ? "" : propertyValue);
								xtcses.get(x).setText(text);
							}
						}
						 t.removeRow(m);
						 t.removeRow(m);
						 t.removeRow(m);  
						 	//t.setInsideHBorder(XWPFBorderType.SINGLE, 1, 1, "000000"); //RGB:BLACK
		 				 	//t.setInsideVBorder(XWPFBorderType.SINGLE, 1, 1, "000000");
		 				//t.setCellMargins(10, 10, 10, 10);
		 				//t.setColBandSize(10);
		 				//t.setRowBandSize(10);
					}
					 
					 
					/*
					//在table表格中,如果cell中已存在内容,则需要使用此方式将cell中的内容进行替换.
					CTP ctp = trcs.get(i).getCTTc().getPList().get(0);
					XWPFParagraph p = xtc.getParagraph(ctp);
					List<XWPFRun> rs = p.getRuns();
					for (int j = 0; j < rs.size(); j++) {
						XWPFRun r = rs.get(j);
						List<CTText> cttexts = r.getCTR().getTList();
						for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
							if (r.toString().equals(entry.getKey())) {
								cttexts.get(0).setStringValue(entry.getValue().toString());
							}
						}
					}
					*/
				}
			}// 
			
		}
		doc.write(out);
		out.flush();
		out.close();
	}
	/**
	 * 检查此table中的<jw:forEach></jw:forEach>标签是否只出现一对.
	 * @param t
	 * @return
	 */
	private boolean isValidTable(XWPFTable t) {
		List<XWPFTableRow> trs = t.getRows();
		List<String> forEachTagList = new ArrayList<String>();
		for (int m = 0; m < trs.size(); m++) {
			List<XWPFTableCell> trcs = trs.get(m).getTableCells();
			for (int i = 0; i < trcs.size(); i++) {
			XWPFTableCell xtc =  trcs.get(i);
			ForEachEndTag forEachEndTag = new ForEachEndTag(xtc.getText());
			if (forEachEndTag.getValid()) {
				forEachTagList.add(xtc.getText());
				continue;
			}
			ForEachBeginTag forEachBeginTag = new ForEachBeginTag(xtc.getText());
			if (forEachBeginTag.getValid()) {
				forEachTagList.add(xtc.getText());
				continue;
			}
			}
		}
		/*
		if (forEachTagList.size() % 2 != 0) {
			throw new RuntimeException("<jw:forEach>的开始、结束标签不配对!" + forEachTagList);
		}
		*/
		return forEachTagList.size() % 2 == 0;
	}

	
	private static class ForEachEndTag {
		private static final String END_TAG_PATTERN = "</\\s*jw:forEach\\s*>";
		private String endTag;
		private ForEachEndTag(String endTag) {
			this.endTag = endTag;
		}
		public boolean getValid() {
			return this.endTag == null ? false : this.endTag.matches(END_TAG_PATTERN);
		}
	}
	
	private static class ForEachBeginTag {
		//e.g. "<jw:forEach item=\" ${addressList}\" var=\"e\">"
		private static final String BEGIN_TAG_PATTERN = "<jw:forEach\\s+item\\s*=\\s*\"\\s*\\$\\{\\s*(([a-zA-Z]|[0-9])+)\\s*\\}\"\\s+var\\s*=\\s*\"(([a-zA-Z]|[0-9])+)\"\\s*>";
		private static final int ITEM_GROUP_INDEX = 1;
		private static final int VAR_GROUP_INDEX = 3;
		private String beginTag;
		private boolean valid;
		private String itemAttrValue;
		private String varAttrValue;
		private ForEachBeginTag(String beginTag) {
			this.beginTag = beginTag;
			this.process();
		}
		private void process() {
			Pattern pattern = Pattern.compile(BEGIN_TAG_PATTERN);
			Matcher matcher = pattern.matcher(this.beginTag);
			if (matcher.matches()) {
				this.valid = true;
				this.itemAttrValue = matcher.group(ITEM_GROUP_INDEX);
				this.varAttrValue = matcher.group(VAR_GROUP_INDEX);
			}
		}
		public boolean getValid() {
			return this.valid;
		}
		public String getItemAttrValue() {
			return this.itemAttrValue;
		}
		public String getVarAttrValue() {
			return this.varAttrValue;
		}
	}

}
