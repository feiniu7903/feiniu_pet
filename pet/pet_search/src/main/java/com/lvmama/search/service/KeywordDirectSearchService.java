package com.lvmama.search.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 关键字直接搜索栏目判断逻辑
 * 当一个关键字进来后，用户没有选取下拉补全的提示关键字,这个时候这个关键字要做一个逻辑运算来判断跳那个频道
 * 如果输入的是productID则根据产品ID查询
 * @param  String
 * @author HZ
 **/
public interface KeywordDirectSearchService {
	
	/**
	 *判断关键字 keyword是不是归属出发地fromDestId
	 * **/
	public  boolean fromDestIsZoneOfKeyword(String fromDestId ,String keyword) ;
	
	
	/**
	 * 根据已经选定的频道和关键字,运算下应该跳转的频道 
	 * **/
	
	public String decideChannel(HttpServletRequest request, HttpServletResponse response, String fromChannel, String newChannel, String fromDest, String keyword, String orikeyword);
	
	/** 如果考虑分词字典,这里做分词处理
	 * 例如杭州灵隐寺切分为杭州+灵隐寺
	 *  **/
	public  String splitLogic(String keyword) ;
}
