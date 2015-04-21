package com.lvmama.jinjiang.model.response;

import com.lvmama.jinjiang.model.SimpleGroup;

public class GetGroupResposne extends AbstractResponse{
	private SimpleGroup simpleGroup;

	public SimpleGroup getSimpleGroup() {
		return simpleGroup;
	}

	public void setSimpleGroup(SimpleGroup simpleGroup) {
		this.simpleGroup = simpleGroup;
	}
}
