package com.lvmama.businessreply.utils.etao;

import java.util.List;

import com.lvmama.businessreply.po.EtaoProduct;


public class GroupBuyBuildFactory {

	/**
	 * 针对于团购生成XML的方法
	 * 
	 * @param groupBuyInfoList
	 * @param code
	 *            团购商户的唯一标识
	 * @param xmlPath
	 *            服务器root路径
	 */
	public static void buildXML(List<EtaoProduct> groupBuyInfoList, String code, String xmlPath) {
		if ("ETAO".equals(code)) {
			GroupBuyEtaoBuild.buildXML(groupBuyInfoList, xmlPath);
		}
	}

	/**
	 * 针对于一淘生成XML的方法
	 * 
	 * @param groupBuyInfoList
	 *            团购信息列表
	 * @param totalPageNum
	 * @param curPageNum
	 * @param xmlPath
	 */
	public static void buildXMLForEtao(List<EtaoProduct> groupBuyInfoList, int totalPageNum, int curPageNum, String xmlPath) {
		EtaoBuild.buildXML(groupBuyInfoList, totalPageNum, curPageNum, xmlPath);
	}

}
