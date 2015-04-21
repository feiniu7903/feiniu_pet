package com.lvmama.clutter.model.sumsang;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
	private static final long serialVersionUID = -3054581224783500423L;
	private Head head;
	private List<View> view;
	private List<Alert> alerts;
	private List<Partner> partners;

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public List<View> getView() {
		return view;
	}

	public void setView(List<View> view) {
		this.view = view;
	}

	public List<Alert> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}

	public List<Partner> getPartners() {
		return partners;
	}

	public void setPartners(List<Partner> partners) {
		this.partners = partners;
	}

	@Override
	public String toString() {
		return "Data [head=" + head + ", view=" + view + ", alerts=" + alerts
				+ ", partners=" + partners + "]";
	}
}
