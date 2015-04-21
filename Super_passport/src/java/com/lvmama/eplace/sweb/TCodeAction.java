package com.lvmama.eplace.sweb;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.lvmama.comm.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.passport.utils.QRcode;

/**
 * 用于处理手机短信中的URL，客户点击URL后可以生成图形二维码<br/>
 * 图形二维码的引擎为 ZXing-2.1
 * 
 * @since 2013-04-18
 * @author zhangkexing
 */
public class TCodeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 10位长度的辅助码代码，placed in table pass_code column code.
	private String code;
	
	private PassCodeService passCodeService;
	private OrderService orderServiceProxy;

//	private static final String prefix = "LVMM";
//	private static final String postfix = "TCOD";

	//二维码图属性
//	private BarcodeFormat barcodeFormat = BarcodeFormat.QR_CODE;
//	private String imageFormat = "PNG";
//	private String charsetEncoding = "UTF8";
//	private int width = 400;
//	private int height = 400;
	
	private OrdOrder order;
	
	@SuppressWarnings("unused")
	@Action(value="/tcode/lt")
	public void generateQRCode() {
		if (StringUtils.isNotEmpty(code)) {
			String hostUrl = Constant.getInstance().getProperty("lvmama.tcode.url");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("code", hostUrl+code);
			PassCode passCode = passCodeService.getPassCodeByParams(params);

			
			if (passCode != null) {
				this.order = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
				if(orderIsCancel()||orderIsPerformed()||orderIsInvalid(passCode.getCodeId())){
					sendAjaxMsg("链接已失效");
					return;
				}
				String addCode = passCode.getAddCode();
				try {
					generate(addCode);
				} catch (WriterException e) {
					e.printStackTrace();
				} catch (IOException e) {
					if(e.toString().startsWith("ClientAbortException")){
						log.error(e.toString());
					}else{
						e.printStackTrace();
					}
				}
			} else if(passCode == null) {
				// TODO:CODE 数据库中PASSCODE不存在
				sendAjaxMsg("无效的访问");
			}
		} else {
			// TODO:URL中无CODE参数
			sendAjaxMsg("无效的访问");
		}

	}

	
	private boolean orderIsCancel(){
		return this.order.isCanceled();
	}
	
	private boolean orderIsPerformed(){
		return this.order.getOrderStatus().equals( Constant.ORDER_STATUS.FINISHED);
	}
	
	private boolean orderIsInvalid(Long codeId){
		PassPortCode passportcode = new PassPortCode();
		passportcode.setCodeId(codeId);
		List<PassPortCode> passportList = passCodeService.queryPassPortCodeByParam(passportcode);
		Date maxInvalidTime = null;
		for(PassPortCode passport : passportList){
			if(maxInvalidTime == null){
				maxInvalidTime = passport.getInvalidTime().compareTo(new Date()) == 1?passport.getInvalidTime():null;
			}else{
				maxInvalidTime = passport.getInvalidTime().compareTo(maxInvalidTime) == 1?passport.getInvalidTime():maxInvalidTime;
			}
		}
		return maxInvalidTime == null ? true : maxInvalidTime.compareTo(new Date()) == -1;
	}
	
	private void generate(String addCode) throws WriterException, IOException {

//		Map<EncodeHintType,Object> hints = new HashMap<EncodeHintType,Object>();
//	    //字符编码
//	    hints.put(EncodeHintType.CHARACTER_SET, charsetEncoding);
//
//	    MultiFormatWriter barcodeWriter = new MultiFormatWriter();
//	    BitMatrix matrix = barcodeWriter.encode(getContent(addCode),barcodeFormat,width, height,hints);
	    getResponse().setContentType("image/png");
	    QRcode.generate(addCode, getResponse().getOutputStream());
//	    MatrixToImageWriter.writeToStream(matrix, imageFormat, getResponse().getOutputStream());
	    
	}

//	private String getContent(String addCode){
//		return prefix+addCode+postfix;
//	}
	
	/**
	 * 从二维码扫描到的字符串中获取addCode(辅助码)
	 * @param src
	 * @return
	 */
//	public static String getAddCode(String src){
//		if(src!=null && src.length()>0){
//			if(src.startsWith(prefix) && src.endsWith(postfix)){
//				String addCode = src.substring(prefix.length(),src.length()-postfix.length());
//				boolean hasOtherChar = addCode.matches("\\D");
//				if(!hasOtherChar && (addCode.length() == 8 || addCode.length() == 6))
//					return addCode;
//			}
//		}
//		return null;
//	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}


	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

//	public static void main(String[] args) {
//		String addCode = getAddCode("LVMM123456d78TCOD");
//		System.out.println(addCode);
//	}

}
