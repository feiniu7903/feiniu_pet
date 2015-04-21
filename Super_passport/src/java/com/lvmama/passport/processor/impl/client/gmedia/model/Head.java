package com.lvmama.passport.processor.impl.client.gmedia.model;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.passport.processor.impl.client.gmedia.Base64;
import com.lvmama.passport.processor.impl.client.gmedia.MD5;
import com.lvmama.passport.utils.WebServiceConstant;
@SuppressWarnings("unused")
public class Head {
	private static final Log log = LogFactory.getLog(Head.class);
	private static final String VERSION="1";
	private static final String CODE_TYPE_DM="1";
	private static final String CODE_TYPE_TCODE="2";
	private static final String CODE_TYPE_QR="3";
	private static final String GEN_IMAGE="0";
    private String version;
    private String sequenceId;
    private String partnerCode;
    private String signed;
    private String statusCode;
    private String message;
    private String commandId;
    
	/**
	 * 时间戳
	 */
	private String timeStamp;
	/**
	 * 交易时间，精确至毫秒。格式：yyyyMMddHHmmssSSS。
	 */
	private String dealTime;
    private PassCode passCode;
    public Head(){
    	
    }
    public Head(PassCode passCode){
    	this.passCode=passCode;
    }
    /**
     * 申请码消息签名信息
     * @return
     */
    public String makeSignedForApplyCode(){
    	StringBuilder buf=new StringBuilder();
    	 buf.append(this.timeStamp)
    	.append(this.passCode.getSerialNo())
    	.append(this.dealTime)
    	.append(CODE_TYPE_TCODE)
    	.append(GEN_IMAGE)
    	.append(this.passCode.getTerminalContent())
  	   .append(this.passCode.getAddCode()==null?"":this.passCode.getAddCode())
  	   
  	   .append(this.sequenceId)
  	   .append(WebServiceConstant.getProperties("gmedia_num"));
    	String signed=buf.toString();
    	log.info("++++++++++++++++++++++++Signed Message:"+buf.toString());
    	signed=this.encodeHead(signed);
       return signed;
    }
    
    /**
     * 废码消息签名信息
     * @return
     */
    public String makeSignedForDestoyCode(){
    	StringBuilder buf=new StringBuilder();
    	 buf.append(this.timeStamp)
    	.append(this.passCode.getSerialNo())
    	
  	   .append(this.sequenceId)
  	   .append(WebServiceConstant.getProperties("gmedia_num"));
    	String signed=buf.toString();
    	log.info("++++++++++++++++++++++++Signed Message:"+buf.toString());
    	signed=this.encodeHead(signed);
       return signed;
    }
    
    /**
     * 更新显示内容消息签名信息
     * @return
     */
    public String makeSignedForUpdateContent(){
    	StringBuilder buf=new StringBuilder();
    	 buf.append(this.timeStamp)
    	.append(this.passCode.getSerialNo())
    	.append(this.dealTime)
    	.append(CODE_TYPE_TCODE)
    	.append(GEN_IMAGE)
    	.append(this.passCode.getUpdateTerminalContent())
  	   .append(this.passCode.getAddCode()==null?"":this.passCode.getAddCode())
  	   
  	   .append(this.sequenceId)
  	   .append(WebServiceConstant.getProperties("gmedia_num"));
    	String signed=buf.toString();
    	log.info("++++++++++++++++++++++++Signed Message:"+buf.toString());
    	signed=this.encodeHead(signed);
       return signed;
    }
    
    /**
     * 回收请求内容消息签名信息
     * @return
     */
    public String makeSignedForUsedCode(Body body){
    	String extContent="";
		try {
			extContent = Base64.encode(body.getExtContent().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	extContent=extContent.replaceAll("\r", "").replaceAll("\n", "");
    	String content=body.getContent().replaceAll("\r", "").replaceAll("\n", "");
    	StringBuilder buf=new StringBuilder();
    	 buf.append(body.getTimeStamp())
    	.append(body.getVoucherId())
    	.append(content)
    	.append(extContent)

  	   .append(this.sequenceId)
  	   .append(WebServiceConstant.getProperties("gmedia_num"));
    	String signed=buf.toString();
    	log.info("++++++++++++++++++++++++Signed Message:"+buf.toString());
    	signed=this.encodeHead(signed);
       return signed;
    }
    
    /**
     * 更改人数请求内容消息签名信息
     * @return
     */
    public String makeSignedForUpdatePerson(Body body){
    	String extContent="";
		try {
			extContent = Base64.encode(body.getExtContent().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	extContent=extContent.replaceAll("\r", "").replaceAll("\n", "");
    	StringBuilder buf=new StringBuilder();
    	 buf.append(body.getTimeStamp())
    	.append(extContent)

  	   .append(this.sequenceId)
  	   .append(WebServiceConstant.getProperties("gmedia_num"));
    	String signed=buf.toString();
    	log.info("++++++++++++++++++++++++Signed Message:"+buf.toString());
    	signed=this.encodeHead(signed);
       return signed;
    }
    
    
    /**
     * 验证码请求内容消息签名信息
     * @return
     */
    public String makeSignedForValidCode(Body body){
    	String extContent="";
		try {
			extContent = Base64.encode(body.getExtContent().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	extContent=extContent.replaceAll("\r", "").replaceAll("\n", "");
    	String content=body.getContent().replaceAll("\r", "").replaceAll("\n", "");
    	StringBuilder buf=new StringBuilder();
    	 buf.append(body.getTimeStamp())
    	.append(body.getVoucherId())
    	.append(body.getStatus())
    	.append(content)
    	.append(extContent)

  	   .append(this.sequenceId)
  	   .append(WebServiceConstant.getProperties("gmedia_num"));
    	String signed=buf.toString();
    	log.info("++++++++++++++++++++++++Signed Message:"+buf.toString());
    	signed=this.encodeHead(signed);
       return signed;
    }

    /**
     * 单条
     * 离线请求内容消息签名信息
     * @return
     */
    public String makeSingedSingleVoucher(Body body) {
        StringBuilder buf=new StringBuilder();
        buf.append(body.getTimeStamp());
        buf.append(createVoucherBufferString(body.getVoucher()));
    	buf.append(this.sequenceId);
	    buf.append(WebServiceConstant.getProperties("gmedia_num"));
        String signed=buf.toString();
        log.info("+++++++单条++++++makeSingedSingleVoucher+++++++++++Signed Message:"+buf.toString());
        signed=this.encodeHead(signed);
       return signed;
    }
    
    /**
     * 多条
     * 离线请求内容消息签名信息
     * @return
     */
    public String makeSignedForVoucher(Body body){
    	Vouchers vouchers=body.getVouchers();
    	StringBuilder buf=new StringBuilder()
        	.append(body.getTimeStamp())
        	.append(body.getCurrentPage())
        	.append(body.getFetchSize())
        	.append(body.getFactSize());
    	
    	List<Voucher> voucherList=vouchers.getVoucher();
    	for(Voucher voucher:voucherList){
    		buf.append(createVoucherBufferString(voucher));
    	}

    	buf.append(this.sequenceId);
	    buf.append(WebServiceConstant.getProperties("gmedia_num"));
    	String signed=buf.toString();
    	log.info("+++++++++多条+++++++makeSignedForVoucher++++++++Signed Message:"+buf.toString());
    	signed=this.encodeHead(signed);
       return signed;
    }
    
    /**
     * 创建 voucher 的buffer string
     * @param param
     * @param voucher
     * @return
     */
    private StringBuilder createVoucherBufferString(Voucher voucher) {
        StringBuilder buf = new StringBuilder();
        buf.append(voucher.getVoucherId());
        buf.append(voucher.getTitle());
        String content=org.apache.commons.lang3.StringUtils.isBlank(voucher.getContent()) ? "":voucher.getContent().replaceAll("\r", "").replaceAll("\n", "");
        buf.append(content);
        buf.append(voucher.getVoucherType());
        buf.append(voucher.getRemain());
        buf.append(voucher.getRevalid());
        buf.append(voucher.getStatus());
        
        buf.append(voucher.getValidDate());
        buf.append(voucher.getReserveDate());
        buf.append(voucher.getCheckType());
        buf.append(voucher.getPrintType());
        buf.append(voucher.getPrintMode());
        buf.append(voucher.getPrintCount());
        buf.append(voucher.getPrintContent());
        
        if (voucher.getContent()!=null) {
        	CodeImg codeImg=voucher.getCodeImg();
	        buf.append(codeImg.getType());
	        buf.append(codeImg.getContent());
        }

        if (voucher.getMemo()!=null) {
	        Memo memo=voucher.getMemo();
	        List<Field> fields=memo.getFields();
	        for(Field field:fields){
	        	buf.append(field.getId());
	        	buf.append(field.getName());
	        	buf.append(field.getValue());
	        }
        }
        Devices devices=voucher.getDevices();
        if(devices != null) {
            List<String> deviceId = devices.getDeviceId();
            for (String deviceNo : deviceId) {
                buf.append(deviceNo);
            }
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(voucher.getExtContent())) {
        	buf.append(voucher.getExtContent());
        }
        return buf;
    }
    
    /**
     *同步打印内容请求内容消息签名信息
     * @return
     */
    public String makeSignedForFormat(Body body){
    	List<Format> formates=body.getFormat();
    	StringBuilder buf=new StringBuilder();
    	 buf.append(body.getTimeStamp());
    	 for(Format format:formates){
    		 buf.append(format.getType());
    		 buf.append(format.getContent());
    		 buf.append(format.getLogoUrl());
    	 }
    	 
  	   buf.append(this.sequenceId)
  	   .append(WebServiceConstant.getProperties("gmedia_num"));
    	String signed=buf.toString();
    	log.info("++++++++++++++++++++++++Signed Message:"+buf.toString());
    	signed=this.encodeHead(signed);
       return signed;
    }
    
    public String toRequestHeadXml(){
    	StringBuilder buf=new StringBuilder();
    	buf.append("<Head>");
		buf.append("<Version>").append(VERSION).append("</Version>");
		buf.append("<SequenceId>").append(this.sequenceId).append("</SequenceId>");
		buf.append("<PartnerCode>").append(WebServiceConstant.getProperties("gmedia_num")).append("</PartnerCode>");
		buf.append("<Signed>").append(this.signed).append("</Signed>");
		buf.append("</Head>");
		log.info("++++++++++++++++++++++++Request Head Xml:"+buf.toString());
    	return buf.toString();
    }
    
    public String toResponseHeadXml(){
    	StringBuilder buf=new StringBuilder();
    	buf.append("<Head>");
		buf.append("<Version>").append(VERSION).append("</Version>");
		buf.append("<StatusCode>").append(this.statusCode).append("</StatusCode>");
		buf.append("<Message>").append(this.message).append("</Message>");
		buf.append("<SequenceId>").append(this.sequenceId).append("</SequenceId>");
		buf.append("<Signed>").append(this.signed).append("</Signed>");
		buf.append("</Head>");
		log.info("++++++++++++++++++++++++Response Head Xml"+buf.toString());
    	return buf.toString();
    }

    private String encodeHead(String signed){
    	try {
    		byte[] md5info=MD5.encode(signed.getBytes("UTF-8"));
    		signed=Base64.encode(md5info);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
       return signed;
    }
	public PassCode getPassCode() {
		return passCode;
	}
	public void setPassCode(PassCode passCode) {
		this.passCode = passCode;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(String sequenceId) {
		this.sequenceId = sequenceId;
	}
	public String getPartnerCode() {
		return partnerCode;
	}
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	public String getSigned() {
		return signed;
	}
	public void setSigned(String signed) {
		this.signed = signed;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public String getCommandId() {
		return commandId;
	}
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
    
}
