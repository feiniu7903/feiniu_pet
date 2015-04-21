package com.lvmama.comm.utils.homePage;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.seo.SeoLinks;
import com.lvmama.comm.utils.FileUtil;
import com.lvmama.comm.utils.ResourceUtil;
 
/**
 * 给目的地，景区，酒店
 * @author nixianjun 2013-7-8
 *
 */
public class PlaceUtils {

	/**
	 * 景点详情页memcached前缀
	 */
	public static final String  SCENIC_MEMCACHED_PREFIX_KEY="SCENIC_PAGE_DATA_MAP_KEY_PLACEID_";
	 /*
	 * 目的地树形结构memcached前缀
	 */
	public static final String  LOADNAVIGATION_MEMCACHED_PREFIX_KEY="loadNavigation_";
	
	/**
	 * 新版景区主key
	 */
	public static final String TICKET_NEW_MEMCACHED_MAIN_NICKY_KEY = "TICKET_MEMCACHED_MAIN_NICKY_KEY_";
	public static final String TICKET_NEW_MEMCACHED_NO_MAIN_NICKY_KEY = "TICKET_NEW_MEMCACHED_NO_MAIN_NICKY_KEY_";
	public static final String TICKET_NEW_MEMCACHED_VICINITY_PLACE_KEY = "TICKET_NEW_MEMCACHED_VICINITY_PLACE_KEY_";
	/**
	 * 景点介绍memcached前缀
	 */
	public static final String TICKET_MEMCACHED_DESCRIPTION_KEY="TICKET_DESCRIPTION_KEY_LIUKANG_";
	
	/**
	 * 交通memcached前缀
	 */
	public static final String TICKET_MEMCACHED_TRAFFIC_INFO_KEY="TICKET_TRAFFIC_INFO_KEY_LIUKANG_";
	
	/**
	 * 目的地探索
	 */
	public static final String TICKET_MEMCACHED_DESTINATION_EXPLORE_KEY="TICKET_DESTINATION_EXPLORE_KEY_LIUKANG_";

 	/**
	 * 产品渠道前台
	 */
	public static final String FRONTEND = "FRONTEND";
	/**
	 * 公告类型
	 */
 	public static final String ALL = "ALL";
 	public static final String INTERNAL = "INTERNAL";
 	public static final String RECOMMEND = "RECOMMEND";
 	public static final String SCENIC = "SCENIC";
 	
 	
 	/**
 	 * 友情恋情去重
 	 * @return 
 	 */
	public static final List<SeoLinks> removeRepeatData(List<SeoLinks> list) {
		List<SeoLinks> newList = new ArrayList<SeoLinks>();
		Set<String> setList = new HashSet<String>();
		if (!list.isEmpty()) {
			for (SeoLinks seo : list) {
				if (!(setList.contains(seo.getPlaceIdAndUrl()))) {
					setList.add(seo.getPlaceIdAndUrl());
					newList.add(seo);
				}
			}
		}
		return newList;
	}
	/**
	 * 景点酒店描述+交通信息文件目录
	 */
	public static final String FILEDIR="/discripAndTrafficFtlFile";
	public static final String DESCRIPTION="description";
	public static final String TRAFFICINFO="trafficinfo";


	
	
	/**
	 * 
	 * @param placeId
	 * @author nixianjun 2013-9-2
	 */
 	public static void createDescriptionAndTrafficInfo(Place place){
		createFile("/"+place.getStage()+"_"+place.getPlaceId()+".ftl",PlaceUtils.DESCRIPTION,place.getDescription());
		createFile("/"+place.getStage()+"_"+place.getPlaceId()+".ftl",PlaceUtils.TRAFFICINFO,place.getTrafficInfo());
	}
	

	/**
	 *
	 * @param filename 文件名
	 * @param type 描述还是交通
	 * @param descrip  内容 
	 * @return 文件是否为空，true 是空，false 不是空
	 * @author nixianjun 2013-9-2
	 */
	public static void createFile(String filename,String type,String  content) {
 				FileUtil.writeFile(filename, ResourceUtil.getResourceFileName(PlaceUtils.FILEDIR+"/"+type),content==null?"":content
						);
 				 
	}
	
	/**
	 * @param totalResultSize
	 * @param pageSize
	 * @return
	 */
	public static int getTotalPages(int totalResultSize, int pageSize) {
		if (totalResultSize % pageSize > 0)
			return totalResultSize / pageSize + 1;
		else
			return totalResultSize / pageSize;
	}
	
	public static String QR_IMGTYPE="png";
	public static int QR_SIZE=7;
 	
	public static void main(String[] args) {
		String content="http://m.lvmama.com/ticket/piao-120044/?channel=QR";
		String imgPath="D:/qrcode/a.png";
 		   TwoDimensionCode.encoderQRCode(content, imgPath, QR_IMGTYPE, QR_SIZE);
	 
	}
}
