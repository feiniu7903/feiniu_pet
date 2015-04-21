package com.lvmama.clutter.utils;


/**
 * 服务端地址
 * 
 * @author YangGan
 *
 */
public class ApiUrlUtil {
	
	/**
	 * 服务器URL
	 */
	//private static String SERVER_URL =ClutterConstant.getClutterHost();
	private static String SERVER_URL ="http://m.lvmama.com/";
	
	public static enum API {
		HOT_CITY("client/datas/placeHotCity.json"),
		PRODUCT_SEARCH("clutter/client/api/v2/queryGroupProductList.do?placeId=${1}&stage=2"),//分组的产品查询
		PLACE_DETAIL_SEARCH("clutter/client/api/v2/getPlaceDetails.do?placeId=${1}"),//景点详情查询
		COMMENT_SEARCH("clutter/client/api/v2/queryCommentListByPlaceId.do?placeId=${1}&page=${2}"),//点评查询
		PLACE_SEARCH("clutter/client/api/v2/placeSearch.do?keyword=${1}&stage=2&page=${2}&sort=${3}"),
		LONG_IN("clutter/client/api/v2/proxyLogin.do?userName=${1}&password=${2}&lvSessionId=${3}&udid=${4}"), // 登陆 
		MYORDER("clutter/client/api/v2/sso/queryUserOrderList.do?userId=${1}&page=${2}");
		
		private String url;
		API(String name){
			this.url=name;
		}
		public String getCode(){
			return this.name();
		}
		public String getUrl(){
			return this.url;
		}
		public static String getUrl(String code){
			for(API item:API.values()){
				if(item.getCode().equals(code)) {
					return item.getUrl();
				}
			}
			return code;
		}
		public String toString(){
			return this.getUrl();
		}
	}
	
	public static String getUrl(ApiUrlUtil.API url, String... params) {
		StringBuffer sb = new StringBuffer(SERVER_URL);
		String urlstr = url.getUrl();
		for (int i = 1; i <= params.length; i++) {
			Object o = params[i - 1];
			urlstr = urlstr.replace("${" + i + "}", o.toString());
		}
		return sb.append(urlstr).toString();
	}
}
