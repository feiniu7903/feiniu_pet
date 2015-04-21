package com.lvmama.clutter.xml.lv.po;

import java.io.Writer;
import java.util.ArrayList;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.pet.po.client.ClientCity;
import com.lvmama.comm.pet.po.client.ClientGroupon2;
import com.lvmama.comm.pet.po.client.ClientPicture;
import com.lvmama.comm.pet.po.client.ClientProvince;
import com.lvmama.comm.pet.po.client.ClientTimePrice;
import com.lvmama.comm.pet.po.client.ClientUser;
import com.lvmama.comm.pet.po.client.ViewClientOrder;
import com.lvmama.comm.pet.po.client.ViewOrdPerson;
import com.lvmama.comm.pet.po.client.ViewPlace;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;


/**
 * 客户端二期xml解析类
 *  
 */
public class ClientXmlAliasSet {
	
	protected static String PREFIX_CDATA    = "<![CDATA[";    
	protected static String SUFFIX_CDATA    = "]]>"; 
	/**
	 * request
	 * @return
	 */
	public static XStream getCommonRequestObj() {
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("request", RequestObject.class);
		xStream.alias("head", RequestHead.class);
		xStream.alias("body", RequestBody.class);
		return xStream;
	}
	
	
	/**
	 * request
	 * @return
	 */
	public static XStream getAllRequestObj() {
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("request", RequestObject.class);
		xStream.alias("head", RequestHead.class);
		xStream.alias("body", RequestBody.class);
		xStream.alias("orderItem", RequestOrderItem.class);
		xStream.aliasField("list", RequestBody.class, "orderItemList");
		return xStream;
	}
	
	public static XStream getCommonResponse() {
		//设置class 别名
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("response", ResponseObject.class);
		xStream.alias("head", ResponseHead.class);
		xStream.alias("body", ResponseBody.class);
		return xStream;
	}
	
	/**
	 * 切换城市response
	 * @return
	 */
	
	public static XStream getDestListResponse() {
		//设置class 别名
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("response", ResponseObject.class);
		xStream.alias("head", ResponseHead.class);
		xStream.alias("body", ResponseBody.class);
		xStream.alias("province", ClientProvince.class);
		xStream.alias("city", ClientCity.class);
		/**
		 * 设置属性别名
		 */
		xStream.aliasField("list", ResponseBody.class, "listProvince");
		xStream.aliasField("list", ClientProvince.class, "clientCity");
		return xStream;
	}
	
	
	/**
	 * 目的地详细信息
	 * @return
	 */
	public static XStream getPlaceDetailsXml() {
		//设置class 别名
		XStream xStream = initXStream(true);
		xStream.alias("response", ResponseObject.class);
		xStream.alias("head", ResponseHead.class);
		xStream.alias("body", ResponseBody.class);
		xStream.alias("place", ViewPlace.class);
		return xStream;
	}
	
	
	
	/**
	 * 景区列表返回
	 * @return
	 */
	public static XStream getPlaceListResponse(){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("response", ResponseObject.class);
		xStream.alias("head", ResponseHead.class);
		xStream.alias("body", ResponseBody.class);
		xStream.alias("place", ViewPlace.class);
		xStream.aliasField("list", ResponseBody.class, "placeList");
		return xStream;
	}
	
	/**
	 * 产品列表xml解析
	 * @return
	 */
	public static XStream getProductListResponse(){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("response", ResponseObject.class);
		xStream.alias("head", ResponseHead.class);
		xStream.alias("body", ResponseBody.class);
		xStream.alias("place", ViewPlace.class);
		xStream.alias("product", ProductSearchInfo.class);
		
		xStream.aliasField("list", ResponseBody.class, "productListInfo");
		return xStream;
	}
	
	/**
	 * 解析用户信息xml
	 * @return
	 */
	public static XStream getUserInfoResponse(){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("response", ResponseObject.class);
		xStream.alias("head", ResponseHead.class);
		xStream.alias("body", ResponseBody.class);
		xStream.alias("place", ViewPlace.class);
		xStream.alias("user", ClientUser.class);
		
		return xStream;
	}
	
	public static XStream getTimePriceListResponse(){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("response", ResponseObject.class);
		xStream.alias("head", ResponseHead.class);
		xStream.alias("body", ResponseBody.class);
		xStream.alias("timePrice", ClientTimePrice.class);
		xStream.aliasField("list", ResponseBody.class, "timePriceList");
		return xStream;
	}
	
	public static XStream getProductDetailsResponse(){
		
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("response", ResponseObject.class);
		xStream.alias("head", ResponseHead.class);
		xStream.alias("body", ResponseBody.class);
		xStream.alias("picture", ComPicture.class);
		xStream.alias("content", ViewContent.class);
		xStream.alias("journey", ViewJourney.class);
		xStream.aliasField("listContent", ViewPage.class, "contentList");
		xStream.aliasField("listPicture", ViewPage.class, "pictureList");
		xStream.aliasField("listJourney", ViewPage.class, "journeyList");
		return xStream;
	}
	
	public static XStream getOrderListResponse(){
		
		XStream xStream = initXStream(true);
		xStream.alias("response", ResponseObject.class);
		xStream.alias("head", ResponseHead.class);
		xStream.alias("body", ResponseBody.class);
		xStream.alias("order", ViewClientOrder.class);
		xStream.alias("person", ViewOrdPerson.class);
		xStream.aliasField("list", ResponseBody.class, "ordersList");
		return xStream;
		
	}
	
	public static XStream createOrderDetailsResponse(){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("response", ResponseObject.class);
		xStream.alias("head", ResponseHead.class);
		xStream.alias("body", ResponseBody.class);
		xStream.alias("order", OrdOrder.class);
		xStream.alias("ordPerson", OrdPerson.class);
		xStream.alias("ordOrderItemProd", OrdOrderItemProd.class);
		xStream.alias("ordOrderItemMeta", OrdOrderItemMeta.class);
		return xStream;
	}
	
	public static XStream createGrouponListResponse(){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("response", ResponseObject.class);
		xStream.alias("head", ResponseHead.class);
		xStream.alias("body", ResponseBody.class);
		xStream.alias("gouponOn", ClientGroupon2.class);
		xStream.alias("picture", ClientPicture.class);
		xStream.aliasField("list", ClientGroupon2.class, "pictureList");
		xStream.aliasField("list", ResponseBody.class, "gouponList");
		return xStream;
	}
	
	
	/**
	 * 
	 * @param isAddCDATA
	 * @return
	 */
	private static XStream initXStream(boolean isAddCDATA){    
        XStream xstream = null;
        if(isAddCDATA){
            xstream =  new XStream(
               new XppDriver() {
                  public HierarchicalStreamWriter createWriter(Writer out) {
                     return new PrettyPrintWriter(out) {
                     protected void writeText(QuickWriter writer, String text) {
                          if(text.startsWith(PREFIX_CDATA)
                             && text.endsWith(SUFFIX_CDATA)) {
                        	  int next = text.indexOf(SUFFIX_CDATA);
                              writer.write(text.substring(9, next));
                          } else {
                              super.writeText(writer, text);
                          }
                      }
                    };
                  };
                }
            );
        } else {
            xstream = new XStream();
        }
     return xstream;
    }
	
	public static RequestObject getRequestObjectFromXml(String reqXml){
		XStream reqXtream = ClientXmlAliasSet.getAllRequestObj();
		RequestObject requestObj = (RequestObject) reqXtream.fromXML(reqXml);
		requestObj.getBody().paramters = new ArrayList<String>();
		return requestObj;
	}
}
