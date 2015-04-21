package com.lvmama.clutter.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;


import com.lvmama.clutter.model.MobileBranch;
import com.lvmama.comm.pet.po.place.Place;

/**
 * 百度活动 ，生成对应的xml . 
 * @author qinzubo
 *
 */
public class BaiduXmlGenerator {
	private final Logger log = Logger.getLogger(this.getClass());
	
	private static String changefreq="always";
	private static String qing_url = "http://qing.lvmama.com/";
	private static String prefix_url = "http://qing.lvmama.com/clutter/";
	private static String tel = "4001-570-570";
	private static String img_url = "http://pic.lvmama.com/pics/";
	
	//
	private static  BaiduXmlGenerator xmlGanerator = new BaiduXmlGenerator();
	
	private BaiduXmlGenerator(){
		
	}
	
	/**
	 * 获取对象  
	 * @return
	 */
	public  static BaiduXmlGenerator getInstance() {
		if(null == xmlGanerator) {
			xmlGanerator =  new BaiduXmlGenerator();
		} 
		return xmlGanerator;
	}
	/**
	 * 生成Doument
	 * @return
	 */
	public  Document getDocument() {
		return  DocumentHelper.createDocument();  //创建文档   
	}
	
	/**
	 * 根节点 
	 * @param document
	 * @return
	 */
	public  Element getUrlSetElement(Document document) {
		return document.addElement("urlset");
	}
	

	/**
	 * 生成sitMap文件 
	 *  <sitemapindex>
	     <sitemap>
	       <loc>http://www.example.com/1.xml</loc>
	       <lastmod>2010-01-01</lastmod>
	     </sitemap>
	     <sitemap>
	       <loc>http://www.example.com/2.xml</loc>
	       <lastmod>2010-02-01</lastmod>
	     </sitemap>
	   </sitemapindex>
	 * @param smDoc
	 */
	public void genaratorSiteMap(Element elementSm, List<String> list) {
		if(null != list && list.size() > 0) {
			for(int i = 0 ; i < list.size();i++) {
				Element sitemapElement = elementSm.addElement("sitemap");
				sitemapElement.addElement("loc").setText(list.get(i));// 1
				sitemapElement.addElement("lastmod").setText(DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));// 1
			}
		}
	}
	
	public Element getSiteMap(Document smDoc) {
		 return smDoc.addElement("sitemapindex");
	}
	
	/**
	 * 生成xml文件 
	 * @param place
	 * @return
	 */
	public Document getneratorBaiduXml(Document document,String fileName) {
        try {    
            Writer fileWriter=new FileWriter(fileName);    
            XMLWriter xmlWriter=new XMLWriter(fileWriter);    
            xmlWriter.write(document);   //写入文件中 
            xmlWriter.close();   
        } catch (IOException e) {
        	e.printStackTrace();
        	log.info("....000..baidu act getneratorBaiduXml...error.....");
        }    
		return null;
	}
	
	/**
	 *  <url>
	        <!-- url，url标记每条信息的开始和结束，最少出现0次 最多出现50000次 -->
	        <loc><![CDATA[http://m.lvmama.com/ticket/piao-120044/]]></loc>
	       <!-- loc，该条数据的存放地址，最少出现1次 最多出现1次，类型为URL地址，最小长度1个字符     最大长度256个字符     必须符合正则表达式(http://)(.+) -->
	       <lastmod>2014-03-24</lastmod>
	       <!-- lastmod，指该条数据的最新一次更新时间，最少出现0次 最多出现1次，类型为日期或日期时间，格式为YYYY-MM-DD的日期或者格式为YYYY-MM-DDThh:mm:ss的日期时间（请注意日期与时间之间以“T”分隔） -->
	       <changefreq>always</changefreq>
	       <!-- changefreq，指该条数据的更新频率，最少出现0次 最多出现1次，类型为字符串，有效值为：always、hourly、daily、weekly、monthly、yearly、never -->
	       <priority>1.0</priority>
	       <!-- priority，用来指定此链接相对于其他链接的优先权比值，此值定于0.0-1.0之间，最少出现0次 最多出现1次，类型为小数，最小值为（包含）0.0    
	 * @throws Exception 
	 */
	public Element getUrlInfo(Element element,Place place,Map<String,Object> map) throws Exception {
		Element elementUrl = element.addElement("url");
		elementUrl.addElement("loc").addCDATA(this.getRequestUrl(place.getPlaceId()+""));// 1
		elementUrl.addElement("lastmod").setText(DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));// 1
		elementUrl.addElement("changefreq").setText(changefreq);// 1
		// elementUrl.addElement("priority").setText("0.0");// 权重 
		
		// dataInfo
		this.getDataInfo(elementUrl, place, map);
		return elementUrl;
	}
	
	/**
	 *  ticket 
	 *  <category>ticket</category>
         <!-- category，类别，最少出现1次 最多出现1次，类型为字符串 -->
         <subcate>ticket</subcate>
         <!-- subcate，子类目，最少出现1次 最多出现1次，类型为字符串 -->
         <source>驴妈妈</source>
         <!-- source，来源方，最少出现1次 最多出现1次，类型为字符串 -->
	 * @param element
	 * @throws Exception 
	 */
	public void getDataInfo(Element element,Place place,Map<String,Object> map) throws Exception {
		Element elementData = element.addElement("data");
		Element elementDisplay = elementData.addElement("display");
		
		//  ticket
		this.getTicketInfo(elementDisplay, place,map);
		
		elementDisplay.addElement("category").setText("lvyou");// 1
		elementDisplay.addElement("subcate").setText("ticket");// 1
		elementDisplay.addElement("source").setText("驴妈妈");// 1
		
	}
	
	
	
	/**
	 *  <spotName>欢乐谷</spotName>    
       <!-- spotName，门票景点唯一官方名称，最少出现1次 最多出现1次，类型为字符串,建议长度6个汉字以内-->
         <alias>上海欢乐谷</alias>
       <!-- *alias，景点别名，最少出现0次 最多出现1次，类型为字符串 --> 
		<spotID>1</spotID>
       <!-- spotID，此景点唯一id，最少出现1次 最多出现1次，类型为字符串-->
       <description>这里有被誉为“过山车界始祖”的木质过山车、享有“过山车之王”美誉的跌落式过山车、国际领先级4K高清飞行影院等先进的游乐设备。还有多个大型室内场馆：其中包括可容纳4500人的、带给游客至高艺术享受的华侨城大剧场。</description>
       <!-- *description，景点的描述，用于在搜索结果页内展示, 最少出现0次 最多出现1次，类型为字符串, 100汉字以内-->
         <detailUrl><![CDATA[http://m.lvmama.com/clutter/html5/index.htm?placeId=120044&tag=2]]></detailUrl>
       <!-- detailUrl，景点详情介绍，当用户点击更多介绍时的指向url，最少出现0次 最多出现1次，类型为字符串 -->
       <address>地址：上海市松江佘山林荫大道</address>
       <!-- address，景点详细地址，最少出现1次 最多出现1次，类型为字符串, 国内景区默认不加中国 -->
       <province>上海</province>
       <!-- province，景点所在省份，未来地域匹配使用，最少出现1次 最多出现1次，类型为字符串, 直辖市默认为省份名-->
       <city>上海</city>
       <!-- city，景点所在城市，未来地域匹配使用，最少出现1次 最多出现1次，类型为字符串, 不出现市字样 -->
       <starGrade>5A</starGrade>
       <!-- starGrade，景点星级类型，最少出现0次 最多出现1次，类型为字符串，合理值为[1-5]A-->
       <imageUrl>http://pic.lvmama.com/pics/uploads/pc/place2/120044/1394796874310.jpg</imageUrl>
       <!-- imageUrl，景点的图片链接，用于展现在弱需求时的简介图，最少出现1次 最多出现1次，展示前会对图片进行压缩，类型为字符串, 尺寸：168*168px，大小：可以提大图以保证清晰度，我们自己前端会将图片过timg压缩-->
       <spotScore>100</spotScore>
       <!-- score，景点热度的评分, 最少出现0次 最多出现1次，类型为数字 -->
       <comments>888</comments>
       <!-- comments，景点门票的评论数，最少出现0次 最多出现1次，类型为数字 -->
	 * @param element
	 * @param place
	 * @throws Exception 
	 */
	public void getTicketInfo(Element element,Place place,Map<String,Object> map) throws Exception {
		Element elementTicket = element.addElement("ticket");
		String placeId = place.getPlaceId() + "";
		elementTicket.addElement("spotName").setText(this.getBdName(place.getPlaceId(),place.getName()));// 1
		elementTicket.addElement("alias").setText(this.getBdAliasName(place.getPlaceId(),place.getName()));// 0
		elementTicket.addElement("spotID").setText(this.getStringInfo(place.getPlaceId()));// 1
		//elementTicket.addElement("description").setText("上运动互动娱乐平台。&#11;除了推广大众水上运动，风和水航海会下辖风和水帆船酒店");//0
		elementTicket.addElement("description").addCDATA(this.getStringInfo(ClientUtils.filterOutHTMLTags(this.filterXml(place.getRemarkes()))));//0
		elementTicket.addElement("detailUrl").addCDATA(this.getStringInfo(this.getDetailUrl(place.getPlaceId()+"")));// 1
		elementTicket.addElement("address").setText(this.getStringInfo(place.getAddress()));// 1
		elementTicket.addElement("province").setText(this.getStringInfo(place.getProvince()));// 1
		elementTicket.addElement("city").setText(this.getStringInfo(place.getCity()));// 1
		//elementTicket.addElement("starGrade").setText(this.getStringInfo(""));// 0  
		elementTicket.addElement("imageUrl").addCDATA(this.getStringInfo(img_url + place.getImgUrl()));// 1
		elementTicket.addElement("spotScore").setText(this.getNumInfo4Float(place.getAvgScore()));// 0
		elementTicket.addElement("comments").setText((null == place.getCommentCount()?"0":place.getCommentCount().intValue())+"");// 0
		
		// pricceInfo
		/*if(null != productMap) {
			getRequest().setAttribute("singleList", productMap.get("single")); // 门票
			getRequest().setAttribute("unionList", productMap.get("union")); // 联票
			getRequest().setAttribute("suitList", productMap.get("suit")); // 套票
		}*/
		if(null != map  ) {
			if(null != map.get("single")) {
				List<Map<String,Object>> obj = (List<Map<String, Object>>) map.get("single");
				if(null != obj && obj.size() > 0) {
					for(int i = 0 ; i < obj.size();i++) {
						this.getPriceInfo(elementTicket, (Map)obj.get(i),placeId+"");
					}
				}
			} 
			if(null != map.get("union")) {
				List<Map<String,Object>> obj = (List<Map<String, Object>>) map.get("union");
				if(null != obj && obj.size() > 0) {
					for(int i = 0 ; i < obj.size();i++) {
						this.getPriceInfo(elementTicket, (Map)obj.get(i),placeId+"");
					}
				}
			}
			if(null != map.get("suit")) {
				List<Map<String,Object>> obj = (List<Map<String, Object>>) map.get("suit");
				if(null != obj && obj.size() > 0) {
					for(int i = 0 ; i < obj.size();i++) {
						this.getPriceInfo(elementTicket, (Map)obj.get(i),placeId+"");
					}
				}
			}
		}
	}

	/**
	 *  <ticketTitle>故宫特价门票</ticketTitle>
        <!--  ticketTitle，门票标题，最少出现1次，最多出现1次，类型为字符串-->
        <ticketID>2266</ticketID>
        <!-- ticketed ，门票唯一标示，最少出现1次，最多出现1次，类型为字符串 -->
        <priceType>1</priceType>
		<!--priceType，价格类型，0-普通票，1-半价票，2-直减票，最少出现1次最多出现1次，类型整数-->
		<price>125</price>
		<!--price，购买价格，最少出现1次最多出现1次，类型浮点数-->
		<normalPrice>250</normalPrice >
		<!--normalPrice，票面价格，最少出现1次最多出现1次，类型浮点数-->
		<num>125</num>
		<!--num，门票数目，最少出现1次最多出现1次，类型整数，没有的时候填 -1-->
		<type>1</type>
		<!-- type，票类型id，用于显示为结果票名称，最少出现0次 最多出现1次，参考：0.特价票 1.成人票 2.儿童票 3.老人票 4.学生票 5.套票 6.夜场票 7.其他 与第一个接入的资源方确定各id代表的类型，其他资源方遵循次规定。如果提交时确认不含有的类型，请联系light-search-mco-rd@baidu.com新增类型 -->      
		<bookUrl><![CDATA[http://m.lvmama.com/clutter/ticketorder/order_fill.htm?productId=2266&branchId=2266&canOrderToday=false]]></bookUrl>
		<!-- bookUrl，景点门票的购买链接，用于搜索结果用户点击直接跳转，最少出现1次 最多出现1次，类型为字符串,符合正则表达式(http://)(.+) ，必须跳至对应票种的锚点-->
		<validTime>2014-04-01</validTime>
       <!-- validTime，门票有效期，特殊情况在括号内说明，最少出现0次 最多出现1次，类型为字符串 -->
       <useTime>09:00-24:00</useTime>
       <!-- useTime，门票每日使用时间，特殊情况在括号内说明，最少出现0次 最多出现1次，类型为字符串 -->
       <reservationTime>24</reservationTime>
       <!-- reservationTime，门票至少提前多少小时预约，不含此字段默认不需要预约，最少出现0次 最多出现1次，类型为整形 -->
       <reservationTel>4001-570-570</reservationTel>
       <!-- reservationTel，门票预约电话，不含此字段默认不需要预约，最少出现0次 最多出现1次，类型为整形 -->
       <serviceFacility>本产品须提前至少1天预订并完成支付$$本产品限网上及手机客户端预订，不接受电话预订。</serviceFacility>
       <!-- *serviceFacility，景点门票支持的服务，最少出现0次 最多出现1次，类型为字符串 -->
       
       
       map 一类票  ，比如门票 ，联票 等 
	 * @throws Exception 
	 */
	
	public void getPriceInfo(Element elementPriceList,Map<String,Object> map,String placeId) throws Exception {
		if(null != map.get("datas")) {
			@SuppressWarnings("unchecked")
			List<MobileBranch> list = (List<MobileBranch>)map.get("datas");
			if(null != list && list.size() > 0) {
				for(MobileBranch mb:list) {
					String ticketType = BaiduActivityUtils.ticketType(mb.getProductId()+"");
					Element element=elementPriceList.addElement("priceList");  
					element.addElement("ticketTitle").setText(this.getTitleInfo(map.get("productName"),mb.getFullName()));// 1
					element.addElement("ticketID").setText(this.getStringInfo(mb.getBranchId()));// 1
					element.addElement("priceType").setText(ticketType);// 1
					element.addElement("price").setText(mb.getSellPriceYuan()+"");// 1
					element.addElement("normalPrice").setText(this.getStringInfo(mb.getMarketPriceYuan()));// 1
					element.addElement("num").setText(this.getMaxNumber(mb.getProductId())+"");// 1
					element.addElement("type").setText(this.getStringInfo(this.getType(mb.getShortName())));// 0
					// 如果是其它票 
					element.addElement("bookUrl").addCDATA(this.getRequestUrl(placeId));// 1  mb.isCanOrderToday()
					//element.addElement("validTime").setText(this.getStringInfo(map.get("productName")));//0
					//element.addElement("useTime").setText(this.getStringInfo(map.get("productName")));//0
					//element.addElement("reservationTime").setText(this.getStringInfo(map.get("productName")));//0
					element.addElement("reservationTel").setText(tel);//0
					//element.addElement("serviceFacility").setText(this.getStringInfo(map.get("productName")));//0
				}
				
			} 
		}
	}
	
	private String getTitleInfo(Object object, String fullName) {
		if(null != object && StringUtils.isNotEmpty(object.toString())){
			return object.toString();
		}
		return fullName;
	}

	/**
	 * 计算半价票和立减票价格
	 * @param productId
	 * @param sellPriceYuan
	 * @return
	 */
	/*private String getPriceInfo(Long productId, Float sellPriceYuan) {
		// 如果是半价票
		if(BaiduActivityUtils.isHalfPriceTicket(productId+"")) {
			return (sellPriceYuan/2) +"";
		} else if(BaiduActivityUtils.isSandByTicket(productId+"")) {
			if(sellPriceYuan < 15) {
				return "1.0";
			}
			return (sellPriceYuan-15)+"";
		} else {
			return sellPriceYuan+"";
		}
	}
*/
	/**
	 * string 解析 
	 * @param obj
	 * @return
	 */
	private String getStringInfo(Object obj) {
		return String.valueOf(obj);
	}
	
	/**
	 *  最大可售数量
	 */
	private Long getMaxNumber(Long productId){
		// 如果是半价票 返回活动期间 最多可卖数量
		if(BaiduActivityUtils.isHalfPriceTicket(productId+"")) {
			return 9*360l;
		}
		return -1l;
	}
	
	/**
	 * 0.特价票 1.成人票 2.儿童票 3.老人票 4.学生票 5.套票 6.夜场票 7.其他
	 * 
	 * @return
	 */
	private String getType(String name) {
		if(name.indexOf("特价") != -1) {
			return "0";
		} else if(name.indexOf("成人") != -1) {
			return "1";
		}else if(name.indexOf("儿童") != -1) {
			return "2";
		}else if(name.indexOf("老人") != -1) {
			return "3";
		}else if(name.indexOf("学生") != -1) {
			return "4";
		}else if(name.indexOf("套票") != -1) {
			return "5";
		}else if(name.indexOf("夜场") != -1) {
			return "6";
		}
		return "7";
	}
	
	/**
	 * 预定
	 * <![CDATA[http://m.lvmama.com/clutter/ticketorder/order_fill.htm?productId=2266&branchId=2266&canOrderToday=false]]>
	 * @param url
	 * @return
	 */
	private String getBookUrl(Long productid,Long branchId ,boolean todayorderble) {
		String b_url = prefix_url+"ticketorder/order_fill.htm?productId="+productid+"&branchId="+branchId+"&canOrderToday="+todayorderble;
		return b_url;
	}
	
	
	/**
	 * 查看图文详情 url 
	 * @param placeid
	 * @return
	 */
	private String getDetailUrl(String placeid) {
		return prefix_url+"html5/index.htm?placeId="+placeid+"&tag=2";
	}
	
	/**
	 * 获取权重
	 * @return
	 */
	private String getPriority(int boot){
		if(0 == boot) {
			return "0.0";
		}
        return "1.0";
	}
	
	/**
	 * 景点访问地址  
	 * <![CDATA[http://m.lvmama.com/ticket/piao-120044/]]>
	 * @param placeId
	 * @return
	 */
	private String getRequestUrl(String placeId) {
		String url = qing_url+"ticket/piao-"+placeId+"/";
		return url;
	}

	
	/**
	 * num数据类型 
	 * @param obj
	 * @return
	 */
	private Integer getNumInfo(Object obj) {
		if(null == obj) {
			return 0;
		}
		return Integer.valueOf(obj+"");
	}
	
	/**
	 * 更加景点id获取ing点名称  
	 * @param placeId
	 * @param name
	 * @return
	 */
	private String getBdName(Long placeId, String name) {
		String placeName = BaiduActivityUtils.getSpotNameByPlaceId(placeId);
		if(StringUtils.isEmpty(placeName)) {
			placeName = name;
		}
		if(StringUtils.isEmpty(placeName)) {
			placeName = "未知";
		}
		return placeName;
	}

	/**
	 * 更加景点id获取ing点名称  
	 * @param placeId
	 * @param name
	 * @return
	 */
	private String getBdAliasName(Long placeId, String name) {
		String placeName = BaiduActivityUtils.getAliasByPlaceId(placeId);
		if(StringUtils.isEmpty(placeName)) {
			placeName = name;
		}
		if(StringUtils.isEmpty(placeName)) {
			placeName = "未知";
		}
		return placeName;
	}
	
	private String getNumInfo4Float(Float avgScore) {
		if(null == avgScore) {
			return "0";
		}
		int scor = avgScore.intValue();
		if(scor > 100) {
			scor = 100;
		}
		return scor+"";
	}
	
	public  static String filterXml(String str){  
		if(StringUtils.isEmpty(str) || StringUtils.isEmpty(str.trim())) {
			return "抱歉，暂无信息!";
		}
        // 清除掉所有特殊字符  
        String regEx = "[`~^&]";  
        Pattern p = Pattern.compile(regEx);  
        Matcher m = p.matcher(str);  
        return m.replaceAll("").trim();  
    }  
	

}
