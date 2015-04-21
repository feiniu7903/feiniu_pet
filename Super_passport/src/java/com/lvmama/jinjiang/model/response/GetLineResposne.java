package com.lvmama.jinjiang.model.response;

import com.lvmama.jinjiang.model.Line;

/**
 * 获取线路返回值
 * @author chenkeke
 *
 */
public class GetLineResposne extends AbstractResponse{
	private Line line;

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}
}
