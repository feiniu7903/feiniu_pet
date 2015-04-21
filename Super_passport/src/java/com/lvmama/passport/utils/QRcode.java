package com.lvmama.passport.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.lvmama.comm.vo.Constant;

public class QRcode {


	private static final String prefix = "LVMM";
	private static final String postfix = "TCOD";

	// 二维码图属性
	private static final BarcodeFormat barcodeFormat = BarcodeFormat.QR_CODE;
	private static final String imageFormat = "PNG";
	private static final String charsetEncoding = "UTF8";
	private static int width = 150;
	private static int height = 150;

	private static String getContent(String addCode){
		return prefix+addCode+postfix;
	}
	
	public static void generate(String addCode,OutputStream outStream) throws WriterException, IOException {

		String width_conf = Constant.getInstance().getProperty("lvmama.qrcode.width");
		String height_conf = Constant.getInstance().getProperty("lvmama.qrcode.height");
		if(StringUtils.isNotEmpty(width_conf)&&StringUtils.isNotEmpty(height_conf)){
			width = Integer.valueOf(width_conf);
			height = Integer.valueOf(height_conf);
		}
		
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		// 字符编码
		hints.put(EncodeHintType.CHARACTER_SET, charsetEncoding);

		MultiFormatWriter barcodeWriter = new MultiFormatWriter();
		BitMatrix matrix = barcodeWriter.encode(getContent(addCode), barcodeFormat, width, height, hints);
		MatrixToImageWriter.writeToStream(matrix, imageFormat, outStream);

	}
}
