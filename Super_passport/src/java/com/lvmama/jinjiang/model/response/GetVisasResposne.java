package com.lvmama.jinjiang.model.response;

import java.util.List;

import com.lvmama.jinjiang.model.Visa;

public class GetVisasResposne extends AbstractResponse{
	private List<Visa> Visas;

	public List<Visa> getVisas() {
		return Visas;
	}

	public void setVisas(List<Visa> visas) {
		Visas = visas;
	}
}
