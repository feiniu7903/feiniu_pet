package com.lvmama.passport.processor.impl.client.gmedia.model;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.passport.processor.impl.client.gmedia.Base64;
import com.lvmama.passport.processor.impl.client.gmedia.DES3;
import com.lvmama.passport.utils.WebServiceConstant;
@SuppressWarnings("unused")
public class Body {
	private static final Log log = LogFactory.getLog(Body.class);
	private static final String CODE_TYPE_DM="1";
	private static final String CODE_TYPE_TCODE="2";
	private static final String CODE_TYPE_QR="3";
	private static final String GEN_IMAGE="0";
	/**
	 * 时间戳
	 */
	private String timeStamp;
	/**
	 * 凭证ID，能唯一对应一次T-Code凭据申请。
	 */
	private String voucherId;
	/**
	 * 交易时间，精确至毫秒。格式：yyyyMMddHHmmssSSS。
	 */
	private String dealTime;
	/**
	 * 码图类型1：DM码，2：Tcode， 3：QR. 
	 */
	private String codeType;
	/**
	 * 是否生成码图图片，1：生成，0：不生成。如果没有此节点或没有值，则默认为不生成。
	 */
	private String genImage;
	/**
	 * 凭证内容，当终端设备识别码号后，会先显示此内容，此内容为格式化好的文本，识读设备将按此格式显示凭证内容。
	 */
	private String content;
	/**
	 * 凭据辅助码号,如果合作伙伴有自己的辅助码生成规则，则由合作伙伴产生，否则为空。
	 */
	private String assistCode;
	/**
	 * TCode码图，如果申请码图类别为DM码，则此节点为空。
	 */
	private String tCode;
	/**
	 * 码图，如果申请码图类型为DM码，则此处是经过Based64处理的码图图片。如果申请码类别为TCode，则此节点为生成的Tcode图片，
	 * 便于合作伙伴在自己的网站显示码图。如果GenImage节点不存在，为空或取值为不生成码图,则此节点为空。
	 */
	private String codeImage;
	/**
	 * 码图类型，1：JPEG，2：PNG，3：GIF
	 */
	private String imageType;
	
	private String barcode;
	/**
	 * 请求使用凭证的设备ID
	 */
    private String deviceId;
    /**
     * ExtContent:扩展内容，其中包装了合作伙伴和终端设备的交互协议，可以是任何格式。用BASEd64编码。
     */
    private String extContent;
    /**
     * Status:凭证状态，凭证是否有效，1：有效。0：无效。
     */
    private String status;
    private String reqCount;
	private PassCode passCode;

	//------------------离线接口属性-----------
	//--------------同步凭证----------------
	private String currentPage;
	private String fetchSize;
	private String scope;
	private String lastDate;
	private String factSize;
	
	private List<Format> format;
	private  Vouchers vouchers;
	private Voucher voucher;
	public Body() {

	}

	public Body(PassCode passCode){
	   this.passCode=passCode;
	   this.voucherId=this.passCode.getSerialNo();
	   this.codeType=CODE_TYPE_TCODE;
	   this.genImage=GEN_IMAGE;
	   this.content=this.passCode.getTerminalContent();
	   this.assistCode=this.passCode.getAddCode()==null?"":this.passCode.getAddCode();
   }
	/**
	 * 申请码请求消息体
	 * @return
	 */
   public String toApplyCodeRequestBodyXml(){
	   StringBuilder buf=new StringBuilder();
	   buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		  .append("<Body>")
		  .append("<TimeStamp>").append(this.timeStamp).append("</TimeStamp>")
		  .append("<VoucherId>").append(this.voucherId).append("</VoucherId>")
		  .append("<DealTime>").append(this.dealTime).append("</DealTime>")
		  .append("<CodeType>").append(this.codeType).append("</CodeType>")
		  .append("<GenImage>").append(this.genImage).append("</GenImage>")
		  .append("<Content>").append(this.content).append("</Content>")
		  .append("<AssistCode>").append(this.assistCode).append("</AssistCode>")
		  .append("</Body>");
	    String body = buf.toString();
	    log.info("++++++++++++++++++++++++ XML Message Body" + body);
		body=this.encodeBody(body);
	   return body;
   }
   /**
    * 废码请求消息体
    * @return
    */
   public String toDestoyCodeRequestBodyXml(){
	   StringBuilder buf=new StringBuilder();
	   buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		  .append("<Body>")
		  .append("<TimeStamp>").append(this.timeStamp).append("</TimeStamp>")
		  .append("<VoucherId>").append(this.voucherId).append("</VoucherId>")
		  .append("</Body>");
	    String body = buf.toString();
		if (log.isDebugEnabled()) {
			log.debug("++++++++++++++++++++++++ 消息体信息" + body);
		}
		body=this.encodeBody(body);
	   return body;
   }
   
   /**
    * 更新内容请求消息体
    * @return
    */
   public String toUpdateContentBodyXml(){
	   StringBuilder buf=new StringBuilder();
	   buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		  .append("<Body>")
		  .append("<TimeStamp>").append(this.timeStamp).append("</TimeStamp>")
		  .append("<VoucherId>").append(this.voucherId).append("</VoucherId>")
		  .append("<DealTime>").append(this.dealTime).append("</DealTime>")
		  .append("<CodeType>").append(this.codeType).append("</CodeType>")
		  .append("<GenImage>").append(this.genImage).append("</GenImage>")
		  .append("<Content>").append(this.passCode.getUpdateTerminalContent()).append("</Content>")
		  .append("<AssistCode>").append(this.assistCode).append("</AssistCode>")
		  .append("</Body>");
	    String body = buf.toString();
		if (log.isDebugEnabled()) {
			log.debug("++++++++++++++++++++++++ 消息体信息" + body);
		}
		body=this.encodeBody(body);
	   return body;
   }
   
   /**
    *回收请求消息体
    * @return
    */
   public String toUsedCodeBodyXml(){
	   StringBuilder buf=new StringBuilder();
		try {
			extContent = Base64.encode(this.getExtContent().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	   extContent=extContent.replaceAll("\r", "").replaceAll("\n", "");
	   buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		  .append("<Body>")
		  .append("<TimeStamp>").append(this.timeStamp).append("</TimeStamp>")
		  .append("<VoucherId>").append(this.voucherId).append("</VoucherId>")
		  .append("<Content>").append(this.content).append("</Content>")
		  .append("<ExtContent>").append(this.extContent).append("</ExtContent>")
		  .append("</Body>");
	    String body = buf.toString();
		if (log.isDebugEnabled()) {
			log.debug("++++++++++++++++++++++++ 消息体信息" + body);
		}
		body=this.encodeBody(body);
	   return body;
   }
   
   
   /**
    *修改人数请求消息体
    * @return
    */
   public String toUpdatePersonBodyXml(){
	   StringBuilder buf=new StringBuilder();
		try {
			extContent = Base64.encode(this.getExtContent().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	   extContent=extContent.replaceAll("\r", "").replaceAll("\n", "");
	   buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		  .append("<Body>")
		  .append("<TimeStamp>").append(this.timeStamp).append("</TimeStamp>")
		  .append("<ExtContent>").append(this.extContent).append("</ExtContent>")
		  .append("</Body>");
	    String body = buf.toString();
		if (log.isDebugEnabled()) {
			log.debug("++++++++++++++++++++++++ 消息体信息" + body);
		}
		body=this.encodeBody(body);
	   return body;
   }
   
   /**
    *验证码请求消息体
    * @return
    */
   public String toValidCodeBodyXml(){
	   StringBuilder buf=new StringBuilder();
		try {
			extContent = Base64.encode(this.getExtContent().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	   extContent=extContent.replaceAll("\r", "").replaceAll("\n", "");
	   buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		  .append("<Body>")
		  .append("<TimeStamp>").append(this.timeStamp).append("</TimeStamp>")
		  .append("<VoucherId>").append(this.voucherId).append("</VoucherId>")
		  .append("<Status>").append(this.status).append("</Status>")
		  .append("<Content>").append(this.content).append("</Content>")
		  .append("<ExtContent>").append(this.extContent).append("</ExtContent>")
		  .append("</Body>");
	    String body = buf.toString();
		if (log.isDebugEnabled()) {
			log.debug("++++++++++++++++++++++++ 消息体信息" + body);
		}
		body=this.encodeBody(body);
	   return body;
   }
   
   /**
    * 多条
    * 二维码平台向合作伙伴发送同步凭证请求消息体
    * @return
    */
   public String toVouchersBodyXml(){
       StringBuilder buf=new StringBuilder();
       buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
          .append("<Body>")
          .append("<TimeStamp>").append(this.timeStamp).append("</TimeStamp>")
          .append("<CurrentPage>").append(this.currentPage).append("</CurrentPage>")
          .append("<FetchSize>").append(this.fetchSize).append("</FetchSize>")
          .append("<FactSize>").append(this.factSize).append("</FactSize>")
          .append("<Vouchers>").append(this.vouchers.toResponseVouchersXml()).append("</Vouchers>")
          .append("</Body>");
        String body = buf.toString();
		if (log.isDebugEnabled()) {
			log.debug("++++++++++++++++++++++++ 消息体信息" + body);
		}
        body=this.encodeBody(body);
       return body;
   }
   
   /**
    * 单条
    * 二维码平台向合作伙伴发送同步凭证请求消息体
    * @return
    */
   public String createSingleVouchersBodyXml(){
       StringBuilder buf=new StringBuilder();
       buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
          .append("<Body>")
          .append("<TimeStamp>").append(this.timeStamp).append("</TimeStamp>")
          .append(this.voucher.toResponseVoucherXmlNoDrivers())
          .append("</Body>");
        String body = buf.toString();
		if (log.isDebugEnabled()) {
			log.debug("++++++++++++++++++++++++ 消息体信息" + body);
		}
        body=this.encodeBody(body);
       return body;
   }
   
   /**
    *二维码平台向合作伙伴发送同步打印内容请求消息体
    * @return
    */
   public String toFormatBodyXml(){
	   StringBuilder buf=new StringBuilder();
	   buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
		  .append("<Body>")
		  .append("<TimeStamp>").append(this.timeStamp).append("</TimeStamp>");
	   List<Format> formates=this.getFormat();
	   for(Format format:formates){
		   	  buf.append("<Format>");
			   	buf.append("<Type>").append(format.getType()).append("</Type>");
			   	buf.append("<Content>").append(format.getContent()).append("</Content>");
			   	buf.append("<LogoUrl>").append(format.getLogoUrl()).append("</LogoUrl>");
			  buf.append("</Format>");
	   }

		 buf.append("</Body>");
	    String body = buf.toString();
		if (log.isDebugEnabled()) {
			log.debug("++++++++++++++++++++++++ 消息体信息" + body);
		}
		body=this.encodeBody(body);
	   return body;
   }
   
    private String encodeBody(String body){
    	int bytelen = body.getBytes().length;
		byte[] result = null;
		byte[] bytekey = WebServiceConstant.getProperties("gmedia_key").getBytes();
		byte[] bytedata;
		try {
			bytedata = body.getBytes("UTF-8");
			result = DES3.encryptMode(bytekey, bytedata);
			body = Base64.encode(result);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		return body;
    }
	public String getGenImage() {
		return genImage;
	}

	public void setGenImage(String genImage) {
		this.genImage = genImage;
	}

	public PassCode getPassCode() {
		return passCode;
	}

	public void setPassCode(PassCode passCode) {
		this.passCode = passCode;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAssistCode() {
		return assistCode;
	}

	public void setAssistCode(String assistCode) {
		this.assistCode = assistCode;
	}

	public String getTCode() {
		return tCode;
	}

	public void setTCode(String code) {
		tCode = code;
	}

	public String getCodeImage() {
		return codeImage;
	}

	public void setCodeImage(String codeImage) {
		this.codeImage = codeImage;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getExtContent() {
		return extContent;
	}

	public void setExtContent(String extContent) {
		this.extContent = extContent;
	}

	public String getReqCount() {
		return reqCount;
	}

	public void setReqCount(String reqCount) {
		this.reqCount = reqCount;
	}



	public String getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(String fetchSize) {
		this.fetchSize = fetchSize;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getFactSize() {
		return factSize;
	}

	public void setFactSize(String factSize) {
		this.factSize = factSize;
	}

	public List<Format> getFormat() {
		return format;
	}

	public void setFormat(List<Format> format) {
		this.format = format;
	}

	public Vouchers getVouchers() {
		return vouchers;
	}

	public void setVouchers(Vouchers vouchers) {
		this.vouchers = vouchers;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

}
