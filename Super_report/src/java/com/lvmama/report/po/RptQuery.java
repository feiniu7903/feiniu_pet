package com.lvmama.report.po;

public class RptQuery {
	private boolean checked;
	private String queryId;
	private String title;
	private String sqlMapStatement;
	private String module;
	private String remark;
	private String pagePath;
	private String reportTemplate;
	private String seq;
	public String getQueryId() {
		return queryId;
	}
	public String getTitle() {
		return title;
	}
	public String getSqlMapStatement() {
		return sqlMapStatement;
	}
	public String getModule() {
		return module;
	}
	public String getRemark() {
		return remark;
	}
	public String getReportTemplate() {
		return reportTemplate;
	}
	public String getSeq() {
		return seq;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setSqlMapStatement(String sqlMapStatement) {
		this.sqlMapStatement = sqlMapStatement;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setReportTemplate(String reportTemplate) {
		this.reportTemplate = reportTemplate;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getPagePath() {
		return pagePath;
	}
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
