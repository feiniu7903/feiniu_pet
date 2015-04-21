package com.lvmama.businessreply.utils.shantou;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.businessreply.po.EtaoProduct;
import com.lvmama.businessreply.utils.etao.XMLBuilderUtil;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.search.Shantou;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;


/**
 * 百度闪投构造
 * @author nixianjun 2013.5.22
 *
 */
public class ShantouBuild {
 	
	public static String  buildXMLForShantou(final List<Shantou> results,
			final int totalPageNum, final int curPageNum) {
		if (curPageNum > totalPageNum || curPageNum < 1) {
			return " ";
		}
 		StringBuilder xmlBuilder = new StringBuilder();//建立xml
 		if (curPageNum == 1) {
			xmlBuilder.append(buildXMLHead());//写文件头
 		}
		if(results!=null && results.size()>0){ 
 	 		for (Shantou shantou : results) {
	 			xmlBuilder.append(buildProdListData(shantou)); // 逐个生成产品详细信息文件
			}
 		}
		if (curPageNum == totalPageNum) {
			xmlBuilder.append(buildXMLTail());
		}
		return xmlBuilder.toString();
 	}
	/**
	 * 建立文件中产品数据
	 * @param shantou
	 * @return
	 */
	private static String buildProdListData(Shantou shantou) {
		StringBuilder item=new StringBuilder(); 
		item.append("<url>\r\n");
 		    item.append(XMLBuilderUtil.buildElement("loc", String.valueOf(shantou.getLoc())));
 		    item.append("<data>\r\n");
 		        //必填
		 		item.append(XMLBuilderUtil.buildElement("name", String.valueOf(shantou.getName())));
		 		item.append(XMLBuilderUtil.buildElement("price", new DecimalFormat("0.00").format(shantou.getSellPriceInteger())));
		 		item.append(XMLBuilderUtil.buildElement("outerID",String.valueOf(shantou.getOuterId())));
		 		item.append(XMLBuilderUtil.buildElement("sellerName","驴妈妈"));
		 		item.append(XMLBuilderUtil.buildElement("title",String.valueOf(shantou.getTitle())));
		 		//非必填
		 		item.append(XMLBuilderUtil.buildElement("category",String.valueOf(shantou.getLevel())));
		 		item.append(XMLBuilderUtil.buildElement("subCategory",String.valueOf(shantou.getSubLevel())));
		 		item.append(XMLBuilderUtil.buildElement("thirdCategory",String.valueOf(shantou.getThirdLevel())));
		 		item.append(XMLBuilderUtil.buildElement("targetUrl",String.valueOf(shantou.getTagetUrl())));
		 		item.append(XMLBuilderUtil.buildElement("value",new DecimalFormat("0.00").format(shantou.getMarketPriceInteger())));
 		 		item.append(XMLBuilderUtil.buildElement("image",String.valueOf(shantou.getSmallImageUrl())));
		 		item.append(XMLBuilderUtil.buildElement("sellerSiteUrl","http://www.lvmama.com"));

                //备注（门票和线路）
		 		item.append("<choice>\r\n");
			 		   item.append(buildXMLAttribute("分类",String.valueOf(shantou.getType() )));
			 		   item.append(buildXMLAttribute("分类Url",String.valueOf(shantou.getTypeUrl() )));
			 		   item.append(buildXMLAttribute("formCity",String.valueOf(shantou.getFromCity())));
			 		   item.append(buildXMLAttribute("toCity",String.valueOf(shantou.getToCity())));
			 		   item.append(buildXMLAttribute("formDate",String.valueOf(shantou.getFromDate())));
			 		   item.append(buildXMLAttribute("rank",String.valueOf(shantou.getRank())));
			 		   item.append(buildXMLAttribute("phone",String.valueOf("1010-6060")));
			 		   item.append(buildXMLAttribute("visitday",String.valueOf(shantou.getVisitDay())));
			 		  item.append("</choice>\r\n");

                
			item.append("</data>\r\n");
 		item.append("</url>\r\n");
		return item.toString();
		
	}
	
	/**
	 * 生成XML文件头.
	 */
	private static String buildXMLAttribute(String key,String value) {
		StringBuilder attribute=new StringBuilder(); 
			attribute.append("<attribute>\r\n");
			attribute.append(XMLBuilderUtil.buildElement("key",key));
			attribute.append(XMLBuilderUtil.buildElement("value",value));
			attribute.append("</attribute>\r\n");
		return attribute.toString();
	}
	/**
	 * 生成XML文件头.
	 */
	private static String buildXMLHead() {
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
		xmlBuilder.append("<urlset>\r\n");
		return xmlBuilder.toString();
	}


	/**
	 * 生成XML文件尾.
	 */
	private static String buildXMLTail() {
		StringBuilder xmlBuilder = new StringBuilder();
 		xmlBuilder.append("</urlset>");
		return xmlBuilder.toString();
	}

	 
}
