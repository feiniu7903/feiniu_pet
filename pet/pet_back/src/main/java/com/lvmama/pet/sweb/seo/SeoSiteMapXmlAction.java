package com.lvmama.pet.sweb.seo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.service.seo.SeoSiteMapXmlService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ExeSh;
import com.lvmama.comm.utils.FileUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 生成SiteMap Xml文件.
 * 
 * @author zuozhengpeng
 */
@Results({ 
	@Result(name = "jumpMenuMap", type="redirect", location = "/seo/jumpMenuMap.do")
	})
public class SeoSiteMapXmlAction extends com.lvmama.comm.BackBaseAction {
	private static final long serialVersionUID = 7186239527854957499L;
	private Log log = LogFactory.getLog(SeoSiteMapXmlAction.class);
	private SeoSiteMapXmlService seoSiteMapXmlService;
	
	private String xmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><urlset>";
	private String xmlEnd = "</urlset>";
	private int pageSize = 999;
	private String filePath = "";
	private List<String> fileNameList;
	/**
	 * 生成sitemap目录名.
	 */
	public static String SITEMAP_DIR = "sitemap";
	String frequency = "";
	String cycle ="";
	String xmlQuantity="";
	@Action("/seo/single/synSiteMap")
	public void synSiteMapFile() throws IOException {
		ExeSh.exeSh(Constant.getInstance().getProperty("seoSiteMapScy"));
		this.getResponse().getWriter().write("{\"flag\":\"true\"}");
	}
	/**
	 * 生成SiteMap文件.
	 */
	@Action("/seo/single/generateSiteMap")
	public String generateSiteMap() {
		try {
			String xmlPath = this.getSession().getServletContext().getRealPath("/");
			this.GenerateSiteMap(xmlPath, frequency, cycle, Integer.parseInt(xmlQuantity));
		} catch (Exception e) {
			log.error("Gennerate siteMap exception:", e);
		}
		return "jumpMenuMap";
	}
	

	public void GenerateSiteMap(String xmlPath, String frequency, String cycle, int xmlQuantity) throws Exception {
		log.info("begin generate siteMap.........");
		// 删除已经存在的目录,并开始进行新的记录.
		filePath = xmlPath + SITEMAP_DIR;
		FileUtil.deleteDirectory(filePath);
		fileNameList = new ArrayList<String>();
		/* 生成景点Xml */
		log.info("begin generate siteMap by place.........");
		GeneratePlaceXml(frequency, cycle, xmlQuantity);
		/* 生成目的地Xml */
		log.info("begin generate siteMap by dest.........");
		GenerateDestXml(frequency, cycle, xmlQuantity);
		/* 生成景点点评Xml */
		log.info("begin generate siteMap by comment.........");
		GenerateCmtPlaceXml(frequency, cycle, xmlQuantity);
		/* 生成首页销售产品Xml */
		log.info("begin generate siteMap by prodproduct.........");
		GenerateProdProdutXml(frequency, cycle, xmlQuantity);
		/* 生成所有文件名称的导航Xml */
		log.info("begin generate siteMap by fileName.........");
		GenerateFileNameXml();
		log.info("end generate siteMap.........");
	}
	
	/**
	 * 生成目的地详细的Xml内容.
	 * 
	 * @param frequency
	 *            更新频率
	 * @param cycle
	 *            周期
	 * @param xmlQuantity
	 *            Xml数量
	 */
	public void GenerateDestXml(String frequency, String cycle, int xmlQuantity) throws Exception {
		String stage="1";
		int totalResultSize = Integer.valueOf(seoSiteMapXmlService.queryPlaceAllCount(stage)+"");
		
		// 写入文件次数
		int totalWrite = getTotalPages(totalResultSize, xmlQuantity);
		// 取数据次数
		int xmlCouter = xmlQuantity < pageSize ? 1 : (getTotalPages(xmlQuantity, pageSize));
		// 数据条数
		int resultCounter = xmlQuantity < pageSize ? xmlQuantity : pageSize;
		// 分页次数
		int countPage = 0;
		
		Map<String,Object> placeAllMap=seoSiteMapXmlService.queryPlaceAllMap(totalResultSize,totalWrite,xmlCouter,resultCounter,countPage,stage);
		
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "", "dest_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/scenery_1_1_0_1", "dest_place_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/comment", "dest_comment_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/guide", "dest_guide_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/hotel", "dest_home_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/dish", "dest_dish_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/traffic", "dest_traffic_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/entertainment", "dest_entertainment_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/shop", "dest_shop_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/weekendtravel", "dest_weekendtravel_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/ticket_tab", "dest_ticket_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/freeness_tab", "dest_freeness_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/hotel_tab", "dest_hotel_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/surrounding_tab", "dest_surrounding_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/dest2dest_tab_frm", "dest_frm_", stage,totalResultSize,placeAllMap);
	}

	/**
	 * 生成景点详细的Xml内容.
	 * 
	 * @param frequency
	 *            更新频率
	 * @param cycle
	 *            周期
	 * @param xmlQuantity
	 *            Xml数量
	 */
	public void GeneratePlaceXml(String frequency, String cycle, int xmlQuantity) throws Exception {
		String stage="2";
		int totalResultSize = Integer.valueOf(seoSiteMapXmlService.queryPlaceAllCount(stage)+"");
		
		// 写入文件次数
		int totalWrite = getTotalPages(totalResultSize, xmlQuantity);
		// 取数据次数
		int xmlCouter = xmlQuantity < pageSize ? 1 : (getTotalPages(xmlQuantity, pageSize));
		// 数据条数
		int resultCounter = xmlQuantity < pageSize ? xmlQuantity : pageSize;
		// 分页次数
		int countPage = 0;
		
		Map<String,Object> placeAllMap=seoSiteMapXmlService.queryPlaceAllMap(totalResultSize,totalWrite,xmlCouter,resultCounter,countPage,stage);
		
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "", "place_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/place", "place_place_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/comment", "place_comment_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/guide", "place_guide_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/hotel", "place_home_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/dish", "place_dish_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/traffic", "place_traffic_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/entertainment", "place_entertainment_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/shop", "place_shop_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/weekendtravel", "place_weekendtravel_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/photo", "place_photo_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/ticket_12_1_1", "place_ticket_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/hotel_12_1_1", "place_hotel_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/package_12_1_1", "place_freeness_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/line_12_1_1", "place_surrounding_", stage,totalResultSize,placeAllMap);
		WritePlaceXml(frequency, cycle, totalWrite,xmlCouter,resultCounter,countPage, "/map", "place_map_", stage,totalResultSize,placeAllMap);
	}

	/**
	 * 通过页面上参数,频率,周期,生成的Xml数量 三个参数生成Xml文档. 例:xml内容由三个参数组成.
	 * 
	 * @param frequency
	 *            频率 从0.1到1.0 值越大，处理紧急性越强
	 * @param cycle
	 *            周期 年月日等参数.每日没月更新抓取xml文档.
	 * @param xmlQuantity
	 *            生成的Xml数量 例：当其为3000时,每3000个xml生成一个文档.
	 * @param urlType
	 *            url详细访问地址 例:页面www.lvmama.co/product/xxxxx/place
	 * @param fileName
	 *            生成文件名
	 * @param stage
	 *            生成类型 例:'1'为目的地,'2'为景区
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void WritePlaceXml(String frequency, String cycle, int totalWrite,int xmlCouter,int resultCounter,int countPage, String urlType, String fileName, String stage,int totalResultSize,Map<String,Object> placeAllMap) throws Exception {
		
		Date date = new Date();
		for (int j = 1; j < totalWrite; j++) {
			StringBuilder xmlStrBuffer = new StringBuilder(xmlStart);
			String _fileName = fileName + j + ".xml";
			fileNameList.add(_fileName);
			File file = new File(filePath, _fileName);
			OutputStreamWriter outWrite = null;
			try {
				outWrite = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
				for (int i = 1; i <= xmlCouter; i++) {
					countPage = countPage + 1;
					List<Place> page = (List<Place>)placeAllMap.get(totalWrite+"_"+resultCounter+"_"+countPage+"_"+stage+"_"+i);
					for (int k = 0; page!=null && k < page.size(); k++) {
						xmlStrBuffer.append("<url>");
						xmlStrBuffer.append("<loc>http://www.lvmama.com/dest/" + page.get(k).getPinYinUrl() + "" + urlType + "</loc>");
						xmlStrBuffer.append("<lastmod>" + DateUtil.getFormatDate(date, "yyyy-MM-dd") + "</lastmod>");
						xmlStrBuffer.append("<changefreq>" + cycle + "</changefreq>");
						xmlStrBuffer.append("<priority>" + frequency + "</priority>");
						xmlStrBuffer.append("</url>");
					}
					outWrite.write(xmlStrBuffer.toString());
					outWrite.flush();
					xmlStrBuffer.delete(xmlStart.length()-1, xmlStrBuffer.length() - 1);
				}
				outWrite.write(xmlEnd);
				outWrite.flush();
			} catch (Exception e) {
				throw e;
			} finally {
				if (outWrite != null) {
					outWrite.close();
				}
			}
		}
	}

	/**
	 * 生成点评Xml文档.通过页面上参数,频率,周期,生成的Xml数量 三个参数生成Xml文档. 例:xml内容由三个参数组成.
	 * 
	 * @param frequency
	 *            频率 从0.1到1.0 值越大，处理紧急性越强
	 * @param cycle
	 *            周期 年月日等参数.每日没月更新抓取xml文档.
	 * @param xmlQuantity
	 *            生成的Xml数量 例：当其为3000时,每3000个xml生成一个文档.
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	private void GenerateCmtPlaceXml(String frequency, String cycle, int xmlQuantity) throws Exception {
		String stage = null;
		int totalResultSize = Integer.valueOf(seoSiteMapXmlService.queryPlaceAllCount(stage)+"");
		
		// 写入文件次数
		int totalWrite = getTotalPages(totalResultSize, xmlQuantity);
		// 取数据次数
		int xmlCouter = xmlQuantity < pageSize ? 1 : (getTotalPages(xmlQuantity, pageSize));
		// 数据条数
		int resultCounter = xmlQuantity < pageSize ? xmlQuantity : pageSize;
		// 分页次数
		int countPage = 0;
		
		Map<String,Object> placeAllMap=seoSiteMapXmlService.queryPlaceAllMap(totalResultSize,totalWrite,xmlCouter,resultCounter,countPage,stage);
		
		Date date = new Date();
		for (int j = 1; j < totalWrite; j++) {
			StringBuilder xmlStrBuffer = new StringBuilder(xmlStart);
			String fileName = "comment_" + j + ".xml";
			fileNameList.add(fileName);
			File file = new File(filePath, fileName);
			OutputStreamWriter outWrite = null;
			try {
				outWrite = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
				for (int i = 1; i <= xmlCouter; i++) {
					countPage = countPage + 1;
					List<Place> page =  (List<Place>)placeAllMap.get(totalWrite+"_"+resultCounter+"_"+countPage+"_"+stage+"_"+i);
					for (int k = 0;page!=null && k < page.size(); k++) {
						xmlStrBuffer.append("<url>");
						xmlStrBuffer.append("<loc>http://www.lvmama.com/comment/" + page.get(k).getPlaceId() + "-1</loc>");
						xmlStrBuffer.append("<lastmod>" + DateUtil.getFormatDate(date, "yyyy-MM-dd") + "</lastmod>");
						xmlStrBuffer.append("<changefreq>" + cycle + "</changefreq>");
						xmlStrBuffer.append("<priority>" + frequency + "</priority>");
						xmlStrBuffer.append("</url>");
					}
					outWrite.write(xmlStrBuffer.toString());
					outWrite.flush();
					xmlStrBuffer.delete(xmlStart.length()-1, xmlStrBuffer.length() - 1);
				}
				outWrite.write(xmlEnd);
				outWrite.flush();
			} catch (Exception e) {
				throw e;
			} finally {
				if (outWrite != null) {
					outWrite.close();
				}
			}
		}
	}

	/**
	 * 
	 * 生成产品Xml文档.通过页面上参数,频率,周期,生成的Xml数量 三个参数生成Xml文档. 例:xml内容由三个参数组成.
	 * 
	 * @param frequency
	 *            频率 从0.1到1.0 值越大，处理紧急性越强
	 * @param cycle
	 *            周期 年月日等参数.每日没月更新抓取xml文档.
	 * @param xmlQuantity
	 *            生成的Xml数量 例：当其为3000时,每3000个xml生成一个文档.
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	private void GenerateProdProdutXml(String frequency, String cycle, int xmlQuantity) throws Exception {
		int totalResultSize = Integer.valueOf(seoSiteMapXmlService.queryPlaceProductAllCount()+"");;
		// 写入文件次数
		int totalWrite = getTotalPages(totalResultSize, xmlQuantity);
		// 取数据次数
		int xmlCouter = xmlQuantity < pageSize ? 1 : (getTotalPages(xmlQuantity, pageSize));
		// 数据条数.例:1000条数据或800条数据.
		int resultCounter = xmlQuantity < pageSize ? xmlQuantity : pageSize;
		// 分页次数
		int countPage = 0;
		
		Map<String,Object> placeProductAllMap=seoSiteMapXmlService.queryPlaceProductAllMap(totalResultSize,totalWrite,xmlCouter,resultCounter,countPage);
		
		Date date = new Date();
		// 数据库总记录数/页面上文件里面的xml数量.例如:11000%3000 4次写入文件.
		for (int j = 1; j < totalWrite; j++) {
			StringBuilder xmlStrBuffer = new StringBuilder(xmlStart);
			String fileName = "product_" + j + ".xml";
			fileNameList.add(fileName);
			File file = new File(filePath, fileName);
			OutputStreamWriter outWrite = null;
			try {
				outWrite = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
				for (int i = 1; i <= xmlCouter; i++) {
					countPage = countPage + 1;
					List<ProductSearchInfo> page = (List<ProductSearchInfo>)placeProductAllMap.get(totalWrite+"_"+xmlCouter+"_"+resultCounter+"_"+countPage+"_"+i);
					// 每次取出的1000条数据.然后写入文件.
					for (int k = 0; page!=null && k <page.size(); k++) {
						xmlStrBuffer.append("<url>");
						xmlStrBuffer.append("<loc>http://www.lvmama.com/product/" + page.get(k).getProductId() + "</loc>");
						xmlStrBuffer.append("<lastmod>" + DateUtil.getFormatDate(date, "yyyy-MM-dd") + "</lastmod>");
						xmlStrBuffer.append("<changefreq>" + cycle + "</changefreq>");
						xmlStrBuffer.append("<priority>" + frequency + "</priority>");
						xmlStrBuffer.append("</url>");
					}
					outWrite.write(xmlStrBuffer.toString());
					outWrite.flush();
					xmlStrBuffer.delete(xmlStart.length()-1, xmlStrBuffer.length() - 1);
				}
				outWrite.write(xmlEnd);
				outWrite.flush();
			} catch (Exception e) {
				throw e;
			} finally {
				if (outWrite != null) {
					outWrite.close();
				}
			}
		}
	}

	public void GenerateFileNameXml() throws Exception {
		String xmlStart = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><sitemapindex xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">";
		String xmlEnd = "</sitemapindex>";
		StringBuilder xmlStrBuffer = new StringBuilder(xmlStart);
		Date date = new Date();
		for (int k = 0; k < this.fileNameList.size(); k++) {
			String fileName = this.fileNameList.get(k);
			xmlStrBuffer.append("<sitemap>");
			xmlStrBuffer.append("<loc>http://www.lvmama.com/sitemap/" + fileName + "</loc>");
			xmlStrBuffer.append("<lastmod>" + DateUtil.getFormatDate(date, "yyyy-MM-dd") + "</lastmod>");
			xmlStrBuffer.append("</sitemap>");
		}
		xmlStrBuffer.append(xmlEnd);
		FileUtil.writeNewFile("sitemap.xml", this.filePath, xmlStrBuffer.toString());
	}

	public int getTotalPages(int totalResultSize, int pageSize) {
		if (totalResultSize % pageSize > 0)
			return totalResultSize / pageSize + 1;
		else
			return totalResultSize / pageSize;
	}

	public void setSeoSiteMapXmlService(SeoSiteMapXmlService seoSiteMapXmlService) {
		this.seoSiteMapXmlService = seoSiteMapXmlService;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public void setXmlQuantity(String xmlQuantity) {
		this.xmlQuantity = xmlQuantity;
	}
	
}
