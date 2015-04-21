package com.lvmama.jinjiang.model.response;

import java.util.List;
/**
 * 批量同步线路代码返回结果
 * @author chenkeke
 *
 */
public class LineCodesResponse extends AbstractResponse{
	private List<String> lineCodes;
	public List<String> getLineCodes() {
		return lineCodes;
	}
	public void setLineCodes(List<String> lineCodes) {
		this.lineCodes = lineCodes;
	}
}
