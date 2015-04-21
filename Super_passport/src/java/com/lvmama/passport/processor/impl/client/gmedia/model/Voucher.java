package com.lvmama.passport.processor.impl.client.gmedia.model;

public class Voucher {
	private String voucherId;
	private String content;
	private String title;
	private String voucherType;
	private String remain;
	private String revalid;
	private String status;
	private String printType;
	private String printMode;
	private String printCount;
	private String printContent;
	private String validDate;
	private String reserveDate;
	private String checkType;
	private CodeImg codeImg;
	private Memo memo;
	private Devices devices;
	private String extContent;
	
	/**
	 * 组装VoucherXml String
	 * 
	 * @return
	 */
    private StringBuffer createVoucherXmlString() {
        StringBuffer voucherXml = new StringBuffer()
            .append("<VoucherId>").append(this.voucherId).append("</VoucherId>")
    
            .append("<Title>").append(this.title).append("</Title>")
    
            .append("<Content>").append(this.content).append("</Content>")
    
            .append("<VoucherType>").append(this.voucherType).append("</VoucherType>")
    
            .append("<Remain>").append(this.remain).append("</Remain>")
    
            .append("<Revalid>").append(this.revalid).append("</Revalid>")
    
            .append("<Status>").append(this.status).append("</Status>")
    
            .append("<ValidDate>").append(this.validDate).append("</ValidDate>")
    
            .append("<ReserveDate>").append(this.reserveDate).append("</ReserveDate>")
    
            .append("<CheckType>").append(this.checkType).append("</CheckType>")
    
            .append("<PrintType>").append(this.printType).append("</PrintType>")
    
            .append("<PrintMode>").append(this.printMode).append("</PrintMode>")
    
            .append("<PrintCount>").append(this.printCount).append("</PrintCount>")
    
            .append("<PrintContent>").append(this.printContent).append("</PrintContent>");
        	if (codeImg!=null) {
        		voucherXml.append("<CodeImg>").append(this.codeImg.toResponseCodeImgXml()).append("</CodeImg>");
        	} else {
        		voucherXml.append("<CodeImg></CodeImg>");
        	}
        	if (memo!=null) {
        		voucherXml.append("<Memo>").append(this.memo.toResponseMemoXml()).append("</Memo>");
        	} else {
        		voucherXml.append("<Memo></Memo>");
        	}
        return voucherXml;
    }

    /**
     * 获得单条协议的VoucherXML Result
     * 
     * @return
     */
    public String toResponseVoucherXmlNoDrivers() {
        StringBuilder buf = new StringBuilder()
                .append("<Voucher>")
                .append(this.createVoucherXmlString())
                .append("<ExtContent>").append(this.getExtContent()).append("</ExtContent>")
                .append("</Voucher>");
        return buf.toString();

    }

    /**
     * 获得多条协议的VoucherXML Result
     * 
     * @return
     */
    public String toResponseVoucherXml() {
        StringBuilder buf = new StringBuilder()
                .append("<Voucher>")
                .append(this.createVoucherXmlString())
                .append("<Devices>").append(this.devices.toResponseDevicesXml()).append("</Devices>")
                .append("</Voucher>");
        return buf.toString();
    }
	public String getVoucherId() {
		return voucherId;
	}
	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}
	public String getRemain() {
		return remain;
	}
	public void setRemain(String remain) {
		this.remain = remain;
	}
	public String getRevalid() {
		return revalid;
	}
	public void setRevalid(String revalid) {
		this.revalid = revalid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPrintType() {
		return printType;
	}
	public void setPrintType(String printType) {
		this.printType = printType;
	}
	public String getPrintMode() {
		return printMode;
	}
	public void setPrintMode(String printMode) {
		this.printMode = printMode;
	}
	public String getPrintCount() {
		return printCount;
	}
	public void setPrintCount(String printCount) {
		this.printCount = printCount;
	}
	public CodeImg getCodeImg() {
		return codeImg;
	}
	public void setCodeImg(CodeImg codeImg) {
		this.codeImg = codeImg;
	}
	public Memo getMemo() {
		return memo;
	}
	public void setMemo(Memo memo) {
		this.memo = memo;
	}
	public Devices getDevices() {
		return devices;
	}
	public void setDevices(Devices devices) {
		this.devices = devices;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPrintContent() {
		return printContent;
	}
	public void setPrintContent(String printContent) {
		this.printContent = printContent;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(String reserveDate) {
		this.reserveDate = reserveDate;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getExtContent() {
		return extContent;
	}
	public void setExtContent(String extContent) {
		this.extContent = extContent;
	}
	
}
