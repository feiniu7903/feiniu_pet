package com.lvmama.comm.utils.homePage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.utils.DateUtil;

public class HomePageUtils {
	
	/**
	 * 八个要刷新页面的城市 块id
	 */
	  public static final Long SH_BLOCKID=10081L;//上海
	  public static final Long CD_BLOCKID=10409L;//成都
	  public static final Long BJ_BLOCKID=10414L;//北京  
	  public static final Long GZ_BLOCKID=10415L;//广州
	  
	  public static final Long HZ_BLOCKID=13097L;//杭州
	  public static final Long SZ_BLOCKID=13098L;//深圳
	  public static final Long SY_BLOCKID=15596L;//三亚
	  public static final Long NJ_BLOCKID=13102L;//南京
	  public static final Long GX_BLOCKID=21520L;//广西
	  
	/**
	 * 八个要刷新页面的城市 简写名称
	 */
	  public static final String SH="SH";//上海
	  public static final String CD="CD";//成都
	  public static final String BJ="BJ";//北京
	  public static final String GZ="GZ";//广州
	  public static final String HZ="HZ";//杭州
	  public static final String SZ="SZ";//深圳
	  public static final String SY="SY";//三亚
	  public static final String NJ="NJ";//南京
	  public static final String GX="GX";//广西
	  public static final String SD="SD";//山东
	  public static final String LN="LN";//东北
	/**
	 * 对应模块列表
	 */
	public static final Map<String, Long> FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING = new HashMap<String, Long>();
	static {
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put(SH, SH_BLOCKID);//上海
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put(CD, CD_BLOCKID);//成都
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put(BJ, BJ_BLOCKID);//北京
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put(GZ, GZ_BLOCKID);//广州
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put(HZ, HZ_BLOCKID);//杭州
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put(SZ, SZ_BLOCKID);//深圳
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put(SY, SY_BLOCKID);//三亚
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put(NJ, NJ_BLOCKID);//南京
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put(GX, GX_BLOCKID);//广西
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put(SD, BJ_BLOCKID);//山东调用北京的
		FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.put(LN, BJ_BLOCKID);//东北调用北京的
	}
	
	/**
	 * 限时特卖的dataCode
	 */
	public static final String XIAN_SHI_TE_MAI_DATA_CODE = "XianShiTeMai";
	/**
	 * 热卖排行的dataCode
	 */
	public static final String RE_MIAO_PAI_HANG_CODE = "ReXiaoPaiHang";

	/**
	 * 门票2条，周边3条，国内2条，出境3条
	 */
	public static final String TICKET="1";
	public static final String AROUND="2";
	public static final String DESTROUTE="3";
	public static final String ABROAD="4";
	
	/**
	 * 计算需要保留的数据(门票2条，周边3条，国内2条，出境3条)
	 * @param recommendInfos
	 * @return
	 */
	public static  List<RecommendInfo> distribute(final List<RecommendInfo> recommendInfos) {
		RecommendInfo[] ticketRecommendInfos = new RecommendInfo[2];
		RecommendInfo[] aroundRecommendInfos = new RecommendInfo[3];
		RecommendInfo[] destRecommendInfos = new RecommendInfo[2];
		RecommendInfo[] abroadRecommendInfos = new RecommendInfo[3];
		
		Date now = new Date();
		for (RecommendInfo info : recommendInfos) {
			if ((StringUtils.isBlank(info.getBakWord4()) && StringUtils.isBlank(info.getBakWord5()))
					|| DateUtil.getDateByStr(info.getBakWord5(),
							"yyyy-MM-dd HH:mm:ss").after(now)) {
				if (TICKET.equals(info.getRemark())) {
					if (null == ticketRecommendInfos[0]) {
						ticketRecommendInfos[0] = info;
						continue;
					}
					if (null == ticketRecommendInfos[1]) {
						ticketRecommendInfos[1] = info;
						continue;
					}
					continue;
				}
				if (AROUND.equals(info.getRemark())) {
					if (null == aroundRecommendInfos[0]) {
						aroundRecommendInfos[0] = info;
						continue;
					}
					if (null == aroundRecommendInfos[1]) {
						aroundRecommendInfos[1] = info;
						continue;
					}
					if (null == aroundRecommendInfos[2]) {
						aroundRecommendInfos[2] = info;
						continue;
					}
					continue;
				}
				if (DESTROUTE.equals(info.getRemark())) {
					if (null == destRecommendInfos[0]) {
						destRecommendInfos[0] = info;
						continue;
					}
					if (null == destRecommendInfos[1]) {
						destRecommendInfos[1] = info;
						continue;
					}
					continue;					
				}
				if (ABROAD.equals(info.getRemark())) {
					if (null == abroadRecommendInfos[0]) {
						abroadRecommendInfos[0] = info;
						continue;
					}
					if (null == abroadRecommendInfos[1]) {
						abroadRecommendInfos[1] = info;
						continue;
					}
					if (null == abroadRecommendInfos[2]) {
						abroadRecommendInfos[2] = info;
						continue;
					}
					continue;	
				}
			}
		}
		
		List<RecommendInfo> _recommendInfos = new ArrayList<RecommendInfo>(10);
		for (RecommendInfo info : ticketRecommendInfos) {
			if (null != info) {
				_recommendInfos.add(info);
			}
		}
		for (RecommendInfo info : aroundRecommendInfos) {
			if (null != info) {
				_recommendInfos.add(info);
			}
		}
		for (RecommendInfo info : destRecommendInfos) {
			if (null != info) {
				_recommendInfos.add(info);
			}
		}
		for (RecommendInfo info : abroadRecommendInfos) {
			if (null != info) {
				_recommendInfos.add(info);
			}
		}
		
		return _recommendInfos;
	}
	
	
	
	/**
	 * 计算需要（限时特卖）上线的数据(门票2条，周边3条，国内2条，出境3条)
	 * @param recommendInfos
	 * @return
	 */
	public static  List<RecommendInfo> filterDataToTeMai(final List<RecommendInfo> recommendInfos) {
		RecommendInfo[] ticketRecommendInfos = new RecommendInfo[2];
		RecommendInfo[] aroundRecommendInfos = new RecommendInfo[3];
		RecommendInfo[] destRecommendInfos = new RecommendInfo[2];
		RecommendInfo[] abroadRecommendInfos = new RecommendInfo[3];
		Date now = new Date();
 		for (RecommendInfo info : recommendInfos) {
			if (StringUtils.isNotBlank( info.getBakWord4()) && StringUtils.isNotBlank( info.getBakWord5())
					&& DateUtil.getDateByStr(info.getBakWord4(), "yyyy-MM-dd HH:mm:ss").before(now)
					&& DateUtil.getDateBeforeHours(DateUtil.getDateByStr(info.getBakWord5(), "yyyy-MM-dd HH:mm:ss"),-1).after(now)) {
				if (TICKET.equals(info.getRemark())) {
					if (null == ticketRecommendInfos[0]) {
						ticketRecommendInfos[0] = info;
						continue;
					}
					if (null == ticketRecommendInfos[1]) {
						ticketRecommendInfos[1] = info;
						continue;
					}
					continue;
				}
				if (AROUND.equals(info.getRemark())) {
					if (null == aroundRecommendInfos[0]) {
						aroundRecommendInfos[0] = info;
						continue;
					}
					if (null == aroundRecommendInfos[1]) {
						aroundRecommendInfos[1] = info;
						continue;
					}
					if (null == aroundRecommendInfos[2]) {
						aroundRecommendInfos[2] = info;
						continue;
					}
					continue;
				}
				if (DESTROUTE.equals(info.getRemark())) {
					if (null == destRecommendInfos[0]) {
						destRecommendInfos[0] = info;
						continue;
					}
					if (null == destRecommendInfos[1]) {
						destRecommendInfos[1] = info;
						continue;
					}
					continue;					
				}
				if (ABROAD.equals(info.getRemark())) {
					if (null == abroadRecommendInfos[0]) {
						abroadRecommendInfos[0] = info;
						continue;
					}
					if (null == abroadRecommendInfos[1]) {
						abroadRecommendInfos[1] = info;
						continue;
					}
					if (null == abroadRecommendInfos[2]) {
						abroadRecommendInfos[2] = info;
						continue;
					}
					continue;	
				}
			}
		}
		
		List<RecommendInfo> _recommendInfos = new ArrayList<RecommendInfo>(10);
		for (RecommendInfo info : ticketRecommendInfos) {
			if (null != info) {
				_recommendInfos.add(info);
			}
		}
		for (RecommendInfo info : aroundRecommendInfos) {
			if (null != info) {
				_recommendInfos.add(info);
			}
		}
		for (RecommendInfo info : destRecommendInfos) {
			if (null != info) {
				_recommendInfos.add(info);
			}
		}
		for (RecommendInfo info : abroadRecommendInfos) {
			if (null != info) {
				_recommendInfos.add(info);
			}
		}
		
		return _recommendInfos;
	}
	
	/**
	 * 计算需要（热销排行）上线的数据(门票2条，周边3条，国内2条，出境3条)
	 * @param recommendInfos
	 * @return recommendInfos
	 */
	public static  List<RecommendInfo> filterDataToReXiao(final List<RecommendInfo> recommendInfos) {
		RecommendInfo[] ticketRecommendInfos = new RecommendInfo[2];
		RecommendInfo[] aroundRecommendInfos = new RecommendInfo[3];
		RecommendInfo[] destRecommendInfos = new RecommendInfo[2];
		RecommendInfo[] abroadRecommendInfos = new RecommendInfo[3];
  		for (RecommendInfo info : recommendInfos) {
				if (TICKET.equals(info.getRemark())) {
					if (null == ticketRecommendInfos[0]) {
						ticketRecommendInfos[0] = info;
						continue;
					}
					if (null == ticketRecommendInfos[1]) {
						ticketRecommendInfos[1] = info;
						continue;
					}
					continue;
				}
				if (AROUND.equals(info.getRemark())) {
					if (null == aroundRecommendInfos[0]) {
						aroundRecommendInfos[0] = info;
						continue;
					}
					if (null == aroundRecommendInfos[1]) {
						aroundRecommendInfos[1] = info;
						continue;
					}
					if (null == aroundRecommendInfos[2]) {
						aroundRecommendInfos[2] = info;
						continue;
					}
					continue;
				}
				if (DESTROUTE.equals(info.getRemark())) {
					if (null == destRecommendInfos[0]) {
						destRecommendInfos[0] = info;
						continue;
					}
					if (null == destRecommendInfos[1]) {
						destRecommendInfos[1] = info;
						continue;
					}
					continue;					
				}
				if (ABROAD.equals(info.getRemark())) {
					if (null == abroadRecommendInfos[0]) {
						abroadRecommendInfos[0] = info;
						continue;
					}
					if (null == abroadRecommendInfos[1]) {
						abroadRecommendInfos[1] = info;
						continue;
					}
					if (null == abroadRecommendInfos[2]) {
						abroadRecommendInfos[2] = info;
						continue;
					}
					continue;	
				}
			}
 
		
		List<RecommendInfo> _recommendInfos = new ArrayList<RecommendInfo>(10);
		for (RecommendInfo info : ticketRecommendInfos) {
			if (null != info) {
				_recommendInfos.add(info);
			}
		}
		for (RecommendInfo info : aroundRecommendInfos) {
			if (null != info) {
				_recommendInfos.add(info);
			}
		}
		for (RecommendInfo info : destRecommendInfos) {
			if (null != info) {
				_recommendInfos.add(info);
			}
		}
		for (RecommendInfo info : abroadRecommendInfos) {
			if (null != info) {
				_recommendInfos.add(info);
			}
		}
		
		return _recommendInfos;
	}

	/**
	 * 删选出首页限时特卖数据通过类型
	 * @param recommendInfos 限时特卖推荐数据
	 * @author nixianjun
	 */
	public static  List<RecommendInfo> getFrontDataTeMaiByType(final List<RecommendInfo> recommendInfos,final String type){
		List<RecommendInfo> r=filterDataToTeMai(recommendInfos);
		//排序
		Collections.sort(r, new Comparator<RecommendInfo>(){
			public int compare(RecommendInfo o1, RecommendInfo o2) {
				if (Integer.parseInt(o1.getRemark()) != Integer.parseInt(o2.getRemark())) {
					if(StringUtils.isNotBlank(o1.getRemark())&&StringUtils.isNotBlank(o2.getRemark())){
						try{
							return Integer.parseInt(o1.getRemark()) >= Integer.parseInt(o2.getRemark())? 1 : -1;
						}catch(Exception e){
							return 0;
						 }
					}
				} else {
					if(StringUtils.isNotBlank(o1.getBakWord3())&&StringUtils.isNotBlank(o2.getBakWord3())){
						try{
							return Float.parseFloat(o1.getBakWord3()) >= Float.parseFloat(o2.getBakWord3()) ? 1 : -1; 
						}catch(Exception e){
							return 0;
						 }
					} 
				}
				return 1;
			}
		});
		
		List<RecommendInfo> recommendInfoList=new ArrayList<RecommendInfo>(10);
		for(RecommendInfo var:r){
			if(null!=var&&type.equals(var.getRemark())){
				recommendInfoList.add(var);
			}
		}
		return recommendInfoList;
	}
	/**
	 * 删选出首页热销排行数据通过类型
	 * @param reXiaoPaiHang
	 * @param type 类型
	 * @return
	 * @author:nixianjun 2013-7-2
	 */
	public static List<RecommendInfo> getFrontDataReXiaoPaiHangByType(
		   List<RecommendInfo> reXiaoPaiHang,final String type) {
		// 限定10条
		List<RecommendInfo> s = HomePageUtils.filterDataToReXiao(reXiaoPaiHang);
		// 排序
		Collections.sort(s, new Comparator<RecommendInfo>() {
			public int compare(RecommendInfo o1, RecommendInfo o2) {
				if (Integer.parseInt(o1.getRemark()) != Integer.parseInt(o2
						.getRemark())) {
					if (StringUtils.isNotBlank(o1.getRemark())
							&& StringUtils.isNotBlank(o2.getRemark())) {
						try {
							return Integer.parseInt(o1.getRemark()) >= Integer
									.parseInt(o2.getRemark()) ? 1 : -1;
						} catch (Exception e) {
							return 0;
						}
					}
				} else {
					if (StringUtils.isNotBlank(o1.getBakWord3())
							&& StringUtils.isNotBlank(o2.getBakWord3())) {
						try {
							return (Integer.parseInt(o1.getBakWord2()) <= Integer
									.parseInt(o2.getBakWord2())) ? 1 : -1;
						} catch (Exception e) {
							return 0;
						}
					}
				}
				return 1;
			}
		});
		List<RecommendInfo> recommendInfoList=new ArrayList<RecommendInfo>(10);
		for(RecommendInfo var:s){
			if(null!=var&&type.equals(var.getRemark())){
				recommendInfoList.add(var);
			}
		}
		return recommendInfoList;

	}
}
