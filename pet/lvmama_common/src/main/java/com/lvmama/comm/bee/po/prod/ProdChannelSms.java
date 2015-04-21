package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

public class ProdChannelSms implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -931759851694058546L;

	private Long channelSmsId;

    private Long channelId;

    private String content;

    private String templateId;

    private String channelCode;
    
    private String templateName;
    
    private String channel;
    private String channelCMCC;
    private String channelCUC;
    private String channelCT;
    public Long getChannelSmsId() {
        return channelSmsId;
    }

    public void setChannelSmsId(Long channelSmsId) {
        this.channelSmsId = channelSmsId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getChannelCMCC() {
		return channelCMCC;
	}

	public void setChannelCMCC(String channelCMCC) {
		this.channelCMCC = channelCMCC;
	}

	public String getChannelCUC() {
		return channelCUC;
	}

	public void setChannelCUC(String channelCUC) {
		this.channelCUC = channelCUC;
	}

	public String getChannelCT() {
		return channelCT;
	}

	public void setChannelCT(String channelCT) {
		this.channelCT = channelCT;
	}

}