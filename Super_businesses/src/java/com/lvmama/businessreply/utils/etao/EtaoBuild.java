package com.lvmama.businessreply.utils.etao;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.businessreply.po.EtaoProduct;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;


/**
 * 一淘构造XML类.
 * 
 * @author huyunyan
 * @see com.lvmama.group.service.IGroupBuyService
 */
public class EtaoBuild {
	private static Map<String,String> productTypeMap = new HashMap<String,String>();
	public static void buildXML(final List<EtaoProduct> groupBuyInfoList, final int totalPageNum, final int curPageNum, final String basePath) {
		if (curPageNum > totalPageNum || curPageNum < 1) {
			return;
		}
		String etaoRootPath = Constant.getInstance().getProperty("ETAO_ROOT_PATH");
		StringBuilder xmlBuilder = new StringBuilder();
		String rootPath = XMLBuilderUtil.getDirPath(basePath, etaoRootPath);
		String fullIndexPath = XMLBuilderUtil.getFilePath(rootPath, Constant.getInstance().getProperty("ETAO_FULL_INDEX_FILANAME"));
		String itemPath = XMLBuilderUtil.getDirPath(rootPath, Constant.getInstance().getProperty("ETAO_ITEM_PATH"));
		if (curPageNum == 1) {
			File root = new File(rootPath);
			if(!root.exists()){
				root.mkdir();
			}
			XMLBuilderUtil.delFile(itemPath);
			XMLBuilderUtil.delFile(rootPath);
			xmlBuilder.append(buildXMLHead());
			buildSellerCatsDoc(rootPath);
		}
		if(groupBuyInfoList!=null && groupBuyInfoList.size()>0){ 
			xmlBuilder.append("<outer_id action=\"upload\">" + curPageNum+ "</outer_id>\r\n"); 
			buildXMLProductList(groupBuyInfoList, itemPath,curPageNum); 
		}
		if (curPageNum == totalPageNum) {
			xmlBuilder.append(buildXMLTail());
		}
		XMLBuilderUtil.appendToXML(fullIndexPath, xmlBuilder.toString());
		saveIncrementIndex(rootPath);
	}

	/**
	 * 生成XML文件头.
	 */
	private static String buildXMLHead() {
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
		xmlBuilder.append("<root>\r\n");
		xmlBuilder.append(XMLBuilderUtil.buildElement("version", Constant.getInstance().getProperty("ETAO_FEED_VERSION")));
		xmlBuilder.append(XMLBuilderUtil.buildElement("modified", new SimpleDateFormat(Constant.getInstance().getProperty("ETAO_DATE_FORMAT")).format(new Date())));
		xmlBuilder.append(XMLBuilderUtil.buildElement("cat_url", Constant.getInstance().getProperty("ETAO_SELLER_CATS_URL")));
		xmlBuilder.append(XMLBuilderUtil.buildElement("seller_id", Constant.getInstance().getProperty("ETAO_SELLER_ID")));
		xmlBuilder.append(XMLBuilderUtil.buildElement("dir", Constant.getInstance().getProperty("ETAO_ITEMS_URL_DIR")));
		xmlBuilder.append("<item_ids>");
		return xmlBuilder.toString();
	}

	/**
	 * 生成每页的产品列表XML.
	 * 
	 * @param groupBuyInfoList
	 */
	private static void buildXMLProductList(final List<EtaoProduct> groupBuyInfoList, final String itemPath,final Integer curPageNum) {
		StringBuilder itemBuilder = new StringBuilder();
		itemBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><items>");
		for (EtaoProduct groupBuyInfo : groupBuyInfoList) {
			itemBuilder.append(buildProdItemDoc(groupBuyInfo, itemPath)); // 逐个生成产品详细信息文件
		}
		itemBuilder.append("</items>");
		String path = XMLBuilderUtil.getFilePath(itemPath,""+curPageNum, "xml");
		XMLBuilderUtil.saveXML(path, itemBuilder.toString());
	}

	/**
	 * 生成XML文件尾.
	 */
	private static String buildXMLTail() {
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("</item_ids>");
		xmlBuilder.append("</root>");
		return xmlBuilder.toString();
	}

	/**
	 * 初始化商户自定义类型文档SellerCats.xml.
	 */
	public static void buildSellerCatsDoc(String rootPath) {
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		StringBuilder root = new StringBuilder();
		root.append(XMLBuilderUtil.buildElement("version", Constant.getInstance().getProperty("ETAO_FEED_VERSION")));
		root.append(XMLBuilderUtil.buildElement("modified", new SimpleDateFormat(Constant.getInstance().getProperty("ETAO_DATE_FORMAT")).format(new Date())));
		root.append(XMLBuilderUtil.buildElement("seller_id", Constant.getInstance().getProperty("ETAO_SELLER_ID")));
		root.append(Constant.getInstance().getProperty("etao_product_type_xml"));
		xmlBuilder.append(XMLBuilderUtil.buildElement("root", root.toString()));

		String path = XMLBuilderUtil.getFilePath(rootPath, Constant.getInstance().getProperty("ETAO_SELLER_CATS_FILENAME"));
		XMLBuilderUtil.saveXML(path, xmlBuilder.toString());
	}

	/**
	 * 创建ETAO销售产品XML文档（产品ID命名）.
	 * 
	 * @param groupBuyInfo
	 *            单个产品信息.
	 */
	public static StringBuilder buildProdItemDoc(EtaoProduct etao, String itemPath) {
		try{

		StringBuilder item = new StringBuilder();
		String title = etao.getProductName();
		if(null==title)title="";
		String desc = etao.getRecommandInfo();
		if(null==desc)desc="";
		item.append("<item>");
		item.append(XMLBuilderUtil.buildElement("seller_id", Constant.getInstance().getProperty("ETAO_SELLER_ID")));
		item.append(XMLBuilderUtil.buildElement("outer_id", String.valueOf(etao.getProductId())));
		item.append(XMLBuilderUtil.buildElement("title", title.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")));
		item.append(XMLBuilderUtil.buildElement("product_id", String.valueOf(etao.getProductId())));
		item.append(XMLBuilderUtil.buildElement("type", Constant.getInstance().getProperty("ETAO_TYPE")));
		item.append(XMLBuilderUtil.buildElement("price", new DecimalFormat("0.00").format(etao.getSellPrice())));
		item.append(XMLBuilderUtil.buildElement("available","1"));
		item.append(XMLBuilderUtil.buildElement("discount", Constant.getInstance().getProperty("STRING_EMPTY")));
		item.append(XMLBuilderUtil.buildElement("desc", desc.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;")));
		item.append(XMLBuilderUtil.buildElement("brand", Constant.getInstance().getProperty("MERCHANT_NAME")));
		item.append(XMLBuilderUtil.buildElement("tags", XMLBuilderUtil.getTags(etao.getTags(), "/")));
		item.append("<image is_default=\"true\">"+etao.getPictureUrl()+"</image>");
		item.append("<more_images>");
		for(ComPicture picture:etao.getPictures()){
			item.append(XMLBuilderUtil.buildElement("img", picture.getAbsoluteUrl()));
		}
		item.append("</more_images>");
		item.append(XMLBuilderUtil.buildElement("scids", getProdScids(etao.getSubProductType()))); // 获取产品类型
		item.append(XMLBuilderUtil.buildElement("post_fee", Constant.getInstance().getProperty("ETAO_POST_FEE")));
		item.append(XMLBuilderUtil.buildElement("showcase", Constant.getInstance().getProperty("ETAO_SHOWCASE")));
		item.append(XMLBuilderUtil.buildElement("href", Constant.getInstance().getProperty("LVMAMA_SITE_PRODUCT_DIR") + etao.getProductId()+"?losc=018735"));
		item.append("</item>");
		return item;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static void saveIncrementIndex(final String rootPath){
		File increment = new File(rootPath+Constant.getInstance().getProperty("ETAO_FULL_INDEX_INCREMENT"));
		StringBuffer sb = new StringBuffer();
		sb.append(buildXMLHead());
		sb.append("</item_ids>");
		sb.append("</root>");
		XMLBuilderUtil.saveXML(increment.getAbsolutePath(), sb.toString());
	}
	/**
	 * 判断产品信息获得自定义类型.
	 * 
	 * @return 自定义类型对应的编号.
	 */
	private static String getProdScids(String key) {
		if (key == null||"".equals(key))
			key = Constant.getInstance().getProperty("ETAO_SCIDS_OTHER_NAME");
		if(productTypeMap.isEmpty()){
			String setao_product_type_key = Constant.getInstance().getProperty("etao_product_type_key");
			String[] productTypeArray = setao_product_type_key.split(",");
			for(String type:productTypeArray){
				String[] array = type.split("/");
				if(array.length==2){
					productTypeMap.put(array[0], array[1]);
				}
			}
		}
		String type = productTypeMap.get(key);
		return StringUtil.isEmptyString(type)?"403":type;
	}
}
