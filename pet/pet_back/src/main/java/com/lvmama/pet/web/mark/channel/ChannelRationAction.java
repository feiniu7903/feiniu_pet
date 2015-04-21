package com.lvmama.pet.web.mark.channel;

public class ChannelRationAction extends ChannelAction {
	private static final long serialVersionUID = -4782352866412434495L;
	private String firstId;
	private String secondId;
	private String threeId;
	private String disabled;
	
	protected void doAfter() throws Exception {
		super.doBefore();
		super.doAfter();
	}
	protected String getComponetName() {
		return "subChannelAction";
	}

	public void setFirstId(String firstId) {
		this.firstId = firstId;
	}

	public void setSecondId(String secondId) {
		this.secondId = secondId;
	}
	public void setThreeId(String threeId) {
		this.threeId = threeId;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
}
