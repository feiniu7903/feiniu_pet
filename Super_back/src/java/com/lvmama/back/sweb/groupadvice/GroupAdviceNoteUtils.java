package com.lvmama.back.sweb.groupadvice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.w3c.tidy.Tidy;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.pdf.PDFRender;
/**
 * 
 * 出团通知书工具类.<br/>
 * 主要功能:<br/>
 * 1.常量的定义,如:出团通知书模板文档文件夹,文档后缀名,<br/>
 * 2.文档转换,从word文档转换为pdf文档,<br/>
 * 3.文件名的生成,<br/>
 * 4.Tidy 处理字符串特殊字符<br/>
 * 5.将指定内容写入到pdf文档<br/>
 */
public class GroupAdviceNoteUtils {
	private static final Logger logger = Logger.getLogger(GroupAdviceNoteUtils.class);
	/**
	 * 出团通知书模板文档文件夹.
	 */
	public static final String TEMPLATE_FOLDER = "/WEB-INF/resources/template";
	public static final String DATE_FORMAT = "yyyyMMddHHmmss";
	/**
	 * 文档后缀:HTML文档.
	 */
	public static final String HTML_FILE_SUFFIX = ".html";
	/**
	 * 文档后缀:WORD2007+文档.
	 */
	public static final String DOCX_FILE_SUFFIX = ".docx";
	/**
	 * 文档后缀:WORD2003文档.
	 */
	public static final String DOC_FILE_SUFFIX = ".doc";
	
	/**
	 * 文档后缀:PDF文档.
	 */
	public static final String PDF_FILE_SUFFIX = ".pdf";
	private GroupAdviceNoteUtils() {
		
	}
	
	/**
	 * 创建上传到super_fs的文件名.
	 * @param orderId 订单号.
	 * @param fileSuffix 文件后缀名,取值为: HTML_FILE_SUFFIX、DOCX_FILE_SUFFIX、PDF_FILE_SUFFIX其中之一.
	 * @return 返回绝对路径名.
	 */
	public static String createFileName(Long orderId,String fileSuffix) {
		StringBuilder sb = new StringBuilder();
		sb.append(System.getProperty("java.io.tmpdir"));
		sb.append("/GROUP_ADVICE_NOTE");
		sb.append("_");
		sb.append(Thread.currentThread().getName());
		sb.append("_");
		sb.append(orderId);
		sb.append("_");
		sb.append(DateUtil.getFormatDate(new java.util.Date(), DATE_FORMAT));
		sb.append(fileSuffix);
		return sb.toString();
	}
	
	public static String convert(String content) {
		 ByteArrayOutputStream out = new ByteArrayOutputStream();
		 ByteArrayInputStream in = null;
		 in = new ByteArrayInputStream(content.getBytes());
		 Tidy tidy = new Tidy();
		 tidy.setXmlOut(true);
		  try {
			   tidy.setXmlOut(true);
			   //tidy.setDropFontTags(true);   // 删除字体节点
			   tidy.setDropEmptyParas(true);  // 删除空段落
			   tidy.setFixComments(true);   // 修复注释
			   tidy.setFixBackslash(true);   // 修复反斜杆
			   tidy.setMakeClean(false);   // 删除混乱的表示
			   tidy.setQuoteNbsp(false);   // 将空格输出为 ?
			   tidy.setQuoteMarks(false);   // 将双引号输出为 "
			   //tidy.setQuoteAmpersand(true);  // 将 & 输出为 &
			   tidy.setShowWarnings(false);  // 不显示警告信息

			   tidy.setOutputEncoding("UTF-8");
			   tidy.setTidyMark(false);
			   tidy.setPrintBodyOnly(true);
			   tidy.setXHTML(false); 
			   tidy.setInputEncoding("UTF-8");
			   tidy.setEncloseText(false);
			   tidy.parse(in, out);
			   String result= new String(out.toByteArray());
			   in.close();
			   out.close();
		
			   return result;
		  } catch (Exception e) {
			  logger.info(e);
		  }
		  return "";
	}
	
	/**
	 * 将内容content写入pdfFilePath的文档中.
	 * @param content
	 * @param pdfFilePath
	 */
	public static void createPdfDocument(String content,String pdfFilePath) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(pdfFilePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PDFRender pdfRender = new PDFRender(content);
		try {
			String simsunFilePath = "/WEB-INF/resources/econtractTemplate/SIMSUN.TTC";
			if (!ResourceUtil.getResourceFile(simsunFilePath).exists()) {
				simsunFilePath = "/WEB-INF/resources/econtractTemplate/simsun.ttc";
			}
			pdfRender.addFont(ResourceUtil.getResourceFile(simsunFilePath).getAbsolutePath(), BaseFont.IDENTITY_H,
				      BaseFont.NOT_EMBEDDED);
		} catch (IOException e) {
			logger.info(e);
		} catch (DocumentException e) {
			logger.info(e);
		}
		try {
			pdfRender.output(out);
		} catch (DocumentException e) {
			logger.info(e);
		}
	}
	
	/**
	 * 创建出团通知书的下载文件名:格式为  订单号_GROUP_ADVICE_NOTE_yyyyMMddHHmmss.
	 * @param orderId 订单号.
	 * @return
	 */
	public static String createAffixName(Long orderId,String fileSuffix) {
		StringBuilder sb = new StringBuilder();
		sb.append(orderId);
		sb.append("_GROUP_ADVICE_NOTE_");
		sb.append(DateUtil.getFormatDate(new java.util.Date(), DATE_FORMAT));
		sb.append(fileSuffix);
		return sb.toString();
	}
}
