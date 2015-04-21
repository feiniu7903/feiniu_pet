package com.lvmama.comm.pet.vo;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.seo.RecommendInfo;

public class PlaceStateDests  implements java.io.Serializable {
	private List<Place> chinaDestsRecomm = new ArrayList<Place>();
	private List<Place> chinaDestsAF = new ArrayList<Place>();
	private List<Place> chinaDestsGJ = new ArrayList<Place>();
	private List<Place> chinaDestsKN = new ArrayList<Place>();
	private List<Place> chinaDestsPW = new ArrayList<Place>();
	private List<Place> chinaDestsXZ = new ArrayList<Place>();

	private List<RecommendInfo> stateDestsRecomm = new ArrayList<RecommendInfo>();
	private List<Place> asiaDests = new ArrayList<Place>();
	private List<Place> americaDests = new ArrayList<Place>();
	private List<Place> europeDests = new ArrayList<Place>();
	private List<Place> africaDests = new ArrayList<Place>();
	private List<Place> oceaniaDests = new ArrayList<Place>();

	public List<Place> getChinaDestsRecomm() {
		return chinaDestsRecomm;
	}

	public void setChinaDestsRecomm(List<Place> chinaDestsRecomm) {
		this.chinaDestsRecomm = chinaDestsRecomm;
	}

	public List<Place> getChinaDestsAF() {
		return chinaDestsAF;
	}

	public void setChinaDestsAF(List<Place> chinaDestsAF) {
		this.chinaDestsAF = chinaDestsAF;
	}

	public List<Place> getChinaDestsGJ() {
		return chinaDestsGJ;
	}

	public void setChinaDestsGJ(List<Place> chinaDestsGJ) {
		this.chinaDestsGJ = chinaDestsGJ;
	}

	public List<Place> getChinaDestsKN() {
		return chinaDestsKN;
	}

	public void setChinaDestsKN(List<Place> chinaDestsKN) {
		this.chinaDestsKN = chinaDestsKN;
	}

	public List<Place> getChinaDestsPW() {
		return chinaDestsPW;
	}

	public void setChinaDestsPW(List<Place> chinaDestsPW) {
		this.chinaDestsPW = chinaDestsPW;
	}

	public List<Place> getChinaDestsXZ() {
		return chinaDestsXZ;
	}

	public void setChinaDestsXZ(List<Place> chinaDestsXZ) {
		this.chinaDestsXZ = chinaDestsXZ;
	}

	public List<RecommendInfo> getStateDestsRecomm() {
		return stateDestsRecomm;
	}

	public void setStateDestsRecomm(List<RecommendInfo> stateDestsRecomm) {
		this.stateDestsRecomm = stateDestsRecomm;
	}

	public List<Place> getAsiaDests() {
		return asiaDests;
	}

	public void setAsiaDests(List<Place> asiaDests) {
		this.asiaDests = asiaDests;
	}

	public List<Place> getAmericaDests() {
		return americaDests;
	}

	public void setAmericaDests(List<Place> americaDests) {
		this.americaDests = americaDests;
	}

	public List<Place> getEuropeDests() {
		return europeDests;
	}

	public void setEuropeDests(List<Place> europeDests) {
		this.europeDests = europeDests;
	}

	public List<Place> getAfricaDests() {
		return africaDests;
	}

	public void setAfricaDests(List<Place> africaDests) {
		this.africaDests = africaDests;
	}

	public List<Place> getOceaniaDests() {
		return oceaniaDests;
	}

	public void setOceaniaDests(List<Place> oceaniaDests) {
		this.oceaniaDests = oceaniaDests;
	}
}
