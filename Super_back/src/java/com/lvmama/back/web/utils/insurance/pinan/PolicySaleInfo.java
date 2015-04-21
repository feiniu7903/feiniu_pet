package com.lvmama.back.web.utils.insurance.pinan;

public class PolicySaleInfo implements PinAnRequest {

	@Override
	public String toXMLString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<policySaleInfo>");
		buffer.append("<businessSourceCode>2</businessSourceCode>");
		buffer.append("<businessSourceDetailCode>2</businessSourceDetailCode>");
		buffer.append("<channelSourceCode>J</channelSourceCode>");
		buffer.append("<channelSourceDetailCode>A</channelSourceDetailCode>");
		buffer.append("<saleAgentCode>2020002046</saleAgentCode>");
		buffer.append("<dataSource>LVMAMA</dataSource>");
		buffer.append("<saleGroupCode/>");
		buffer.append("<businessMode/>");
		buffer.append("<policySaleAgentInfo>");
		buffer.append("<policySaleAgentInfo>");
		buffer.append("<saleAgentCode>2020002046</saleAgentCode>");
		buffer.append("<mainSaleAgentFlag>1</mainSaleAgentFlag>");
		buffer.append("<saleGroupCode/>");
		buffer.append("<commisionScale>1</commisionScale>");
		buffer.append("</policySaleAgentInfo>");
		buffer.append("</policySaleAgentInfo>");
		buffer.append("</policySaleInfo>");
		return buffer.toString();
	}
}
