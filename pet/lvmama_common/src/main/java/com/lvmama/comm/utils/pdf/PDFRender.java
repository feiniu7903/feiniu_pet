package com.lvmama.comm.utils.pdf;

import java.io.IOException;
import java.io.OutputStream;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.PDFEncryption;

import com.lowagie.text.DocumentException;

/**
 * pdf生成过程
 * @author yangbin
 *
 */
public class PDFRender {
	private static final String PDF_OWN_PASSWORD="lvmama.com_owner_password__";
	private String content;
	private ITextRenderer render;
	private ITextFontResolver resolver;
	public PDFRender(String content)
	{
		this.content=content;
		render=new ITextRenderer();
		resolver=render.getFontResolver();
	}
	
	public PDFRender(ContentTemplate template)throws IOException
	{
		this(template.build());		
	}
	
	public void addFont(String path,boolean embedded)throws IOException,DocumentException
	{
		resolver.addFont(path, embedded);
	}
	public void addFont(String path,String encoding,boolean embedded)throws IOException,DocumentException
	{
		resolver.addFont(path, encoding, embedded);
	}
	
	public void output(OutputStream os)throws DocumentException
	{
		try {
			PDFEncryption encrypt=new PDFEncryption(null, PDF_OWN_PASSWORD.getBytes());
			render.setPDFEncryption(encrypt);	
			content = content.substring(content.indexOf("<html"));
			render.setDocumentFromString(content);
			render.layout();
			render.createPDF(os);
			render.finishPDF();
		} catch (Exception e) {
			throw new DocumentException(e);
		}
		
	}
}
