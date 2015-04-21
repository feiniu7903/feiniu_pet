package com.lvmama.comm.search.vo;

import java.io.Serializable;
import java.util.Map;

public class TypeCount implements Serializable {

	private static final long serialVersionUID = -2022751956301771064L;
	/** 关键词 */
	private String keyword;
	/**出发地 */
	private String fromDest;
	/** 景点门票的统计数量 */
	private int ticket;
	/** 特色酒店的统计数量 */
	private int hotel;
	/** 自由行（景点+酒店）的统计数量 */
	private int freetour;
	/** 自由行（机票+酒店）的统计数量 */
	private int freelong;
	/** 跟团游的统计数量 */
	private int group;
	/** 周边/当地跟团游的统计数量 */
	private int around;
	/** 度假的统计数量 */
	private int route;
	/** 查询结果 */
	private Map map;
	/**
	 * 线路按照产品名称查询
	 */
	private boolean routeSearchByName;
	/**
	 * 门票按照产品名称查询
	 */
	private boolean ticketSearchByName;
	/**
	 * 周边当地跟团游时 关键词为 出发地 查询到结果
	 */
	private boolean keywordIsFromDest;
	
	public int getTicket() {
		return ticket;
	}

	public void setTicket(int ticket) {
		this.ticket = ticket;
	}

	public int getHotel() {
		return hotel;
	}

	public void setHotel(int hotel) {
		this.hotel = hotel;
	}

	public int getFreetour() {
		return freetour;
	}

	public void setFreetour(int freetour) {
		this.freetour = freetour;
	}

	public int getFreelong() {
		return freelong;
	}

	public void setFreelong(int freelong) {
		this.freelong = freelong;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public int getAround() {
		return around;
	}

	public void setAround(int around) {
		this.around = around;
	}

	public int getRoute() {
		return route;
	}

	public void setRoute(int route) {
		this.route = route;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getFromDest() {
		return fromDest;
	}

	public void setFromDest(String fromDest) {
		this.fromDest = fromDest;
	}

	public boolean isRouteSearchByName() {
		return routeSearchByName;
	}

	public void setRouteSearchByName(boolean routeSearchByName) {
		this.routeSearchByName = routeSearchByName;
	}

	public boolean isKeywordIsFromDest() {
		return keywordIsFromDest;
	}

	public void setKeywordIsFromDest(boolean keywordIsFromDest) {
		this.keywordIsFromDest = keywordIsFromDest;
	}

	public boolean isTicketSearchByName() {
		return ticketSearchByName;
	}

	public void setTicketSearchByName(boolean ticketSearchByName) {
		this.ticketSearchByName = ticketSearchByName;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	
}
