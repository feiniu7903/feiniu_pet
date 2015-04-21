package com.lvmama.comm.utils.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFEncryption;
import org.xhtmlrenderer.simple.ImageRenderer;

import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.lvmama.comm.utils.ResourceUtil;

public class PdfUtil {
	public static final String PDF_FONT_URL = "/WEB-INF/resources/econtractTemplate/";// pdf模板相对路径
	public static final String PDF_OWNER_PASS_WORD="LvmamaIsTourismsFirst";
	private static final Logger logger = Logger.getLogger(PdfUtil.class);
	
	public static void main(String[] age){
		try {
			File file = new File("C:/Users/yuzhibing/Desktop/ROUTE.html");
			PdfUtil.htmlToImage(file, ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void htmlToImage(File f,String imageType) throws IOException{
		if (f.exists()) {
			String output = f.getAbsolutePath();
			output = output.substring(0, output.lastIndexOf(".")) +imageType;
			ImageRenderer.renderToImage(f, output, 700);
		}   
	}
	/**
	 * 根据文件路径创建PDF文件
	 * @param parameters
	 * @param toUrl
	 */
	public static void createPdfFile(final String content,final String toUrl) {
		ByteArrayOutputStream baos = null;
		FileOutputStream fs = null;
		try {
			baos = createPdfFile(content);
			if (null != baos) {
				fs = new FileOutputStream(new File(toUrl));
				baos.writeTo(fs);
			}
		} catch (IOException e) {
			logger.error("合同生成PDF文件  IOException:" + e);
		} finally {
			IOUtils.closeQuietly(baos);
			IOUtils.closeQuietly(fs);
		}
	}
	/**
	 * 创建PDF文件流
	 * @param parameters
	 * @param toUrl
	 */
	public static ByteArrayOutputStream createPdfFile(final String line) {
		try {
			String content = line.replaceAll("&nbsp;", "");
			if(!content.startsWith("<")){ 
				content = content.substring(content.indexOf('<')); 
			}
			content = content.replaceAll("&", "");
			content = content.replaceAll("<br>", "<br/>");
			content = content.replaceAll("</br>", "<br/>");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			File ttc = ResourceUtil.getResourceFile(PDF_FONT_URL+"simsun.ttc");
			if (!ttc.exists()) {
				ttc = ResourceUtil.getResourceFile(PDF_FONT_URL+"SIMSUN.TTC");
			}
			String fontPath=ttc.getAbsolutePath();
			ITextRenderer renderer = new ITextRenderer();
			ITextFontResolver fontResolver = renderer.getFontResolver();
			if(null!=fontPath) {
				fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			}
		
			renderer.setDocumentFromString(content);
			PDFEncryption encryption = new PDFEncryption(null, PDF_OWNER_PASS_WORD
					.getBytes(), PdfWriter.ALLOW_PRINTING);
			renderer.setPDFEncryption(encryption);
			renderer.layout();
			renderer.createPDF(baos);
			renderer.finishPDF();
			return baos;
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}
}
