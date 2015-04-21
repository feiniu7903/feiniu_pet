package com.lvmama.clutter.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.cookie.DateUtils;

import com.lvmama.clutter.model.JianYiOrderDTO;
import com.lvmama.clutter.model.JianYiProduct;
import com.lvmama.clutter.model.MobileMyFavorite;
import com.lvmama.clutter.model.MobileProduct;
import com.lvmama.clutter.model.MobileRecommend;
import com.lvmama.clutter.model.MobileUser;
import com.lvmama.clutter.model.OrderDataItem;
import com.lvmama.comm.pet.po.mobile.MobileFavorite;
import com.lvmama.comm.pet.po.mobile.MobileRecommendInfo;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.po.work.WorkOrder;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UserLevelUtils;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;

/**
 * 移动端专用 from v3 
 * @author
 *
 */
public class MobileCopyPropertyUtils {

    /**
     * 初始化用户信息 . 
     * @param user   元数据
     * @param cu     目的数据
     * @param moneyAccount   现金账户
     * @return
     */
	public static MobileUser copyUserUser2MobileUser(UserUser user ,MobileUser cu,CashAccountVO moneyAccount) {
		cu.setUserId(user.getUserId());
		cu.setEmail(user.getEmail());
		cu.setImageUrl(user.getImageUrl());
		cu.setMobileNumber(StringUtil.trimNullValue(user.getMobileNumber()));
		cu.setNickName(StringUtil.trimNullValue(user.getNickName()));
		cu.setRealName(StringUtil.trimNullValue(user.getRealName()));
		cu.setSaveCreditCard("Y".equals(user.getSaveCreditCard()));
		cu.setMobileCanChecked( !"Y".equals(user.getIsMobileChecked())); // 手机号是否验证
		cu.setNameCanUpdate(!"Y".equalsIgnoreCase(user.getNameIsUpdate()));
		cu.setGener(user.getGender()); // 性别
		String showName="";
	
		if(!StringUtil.isEmptyString(user.getNickName())){
			showName = user.getNickName();
		} else if(!StringUtil.isEmptyString(user.getEmail())){
			showName = user.getEmail();
		} else if(!StringUtil.isEmptyString(user.getMobileNumber())){
			String mobile = user.getMobileNumber();
			if(StringUtil.isNotEmptyString(mobile)){
				showName = StringUtil.hiddenMobile(mobile);
			}
		} else {
			showName = user.getUserName();
		}
		showName = filterUserName(showName); //客户端过滤掉一些无用字符 。 
		
		cu.setUserName(showName);
		cu.setPoint(user.getPoint());
		Long point = 0L;
		if (user.getPoint() != null) {
			point = user.getPoint();
		}
		cu.setLevel(UserLevelUtils.getLevel(point));

		cu.setWithdraw(user.getWithdraw() == null ? "¥0" : "¥"+ClientUtils.subZeroAndDot(""+PriceUtil.convertToYuan(user.getWithdraw())));
		if (moneyAccount != null) {
			cu.setAwardBalance("¥"+ClientUtils.subZeroAndDot(""+moneyAccount.getBonusBalanceYuan()));
			cu.setCashBalance("¥"+ClientUtils.subZeroAndDot(""+moneyAccount.getTotalMoneyYuan()));
		} else {
			cu.setAwardBalance("¥0");
			cu.setCashBalance("¥0");
		}
		return cu;
	}
	
	/**
	 * 过滤用户名 
	 * @param userName
	 * @return
	 */
	public static String filterUserName(String userName) {
		if(!StringUtils.isEmpty(userName)) {
			// 需要过滤字符串：From sina weibo From tencent weibo From alipay 
			// 需要过滤第一个括号内容  "("
			try {
				int lastIndex = userName.lastIndexOf("(");
				if(lastIndex != -1) {
					userName = userName.substring(0,lastIndex);
					userName = userName.toLowerCase();
					userName = userName.replace(" ", "").replace("fromsinaweibo", "").replace("fromtencentweibo", "").replace("fromalipay", "");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return userName;
	}
	
	/**
	 * 产品属性复制.
	 * @param productList 门票,线路,酒店产品相关搜索列表 （源数据）
	 * @param cpList 目的数据
	 * @param productType 产品类型
	 */
	public static void copyProductList(List<ProdBranchSearchInfo> productList,List<MobileProduct> cpList,String productType){
		for (ProdBranchSearchInfo viewProductSearchInfo : productList) {
			
			MobileProduct cp = new MobileProduct();
			cp.setProductName(viewProductSearchInfo.getProductName());
			cp.setProductId(viewProductSearchInfo.getProductId());
			cp.setBranchId(viewProductSearchInfo.getProdBranchId());
			cp.setProductType(productType);
			if(StringUtil.isEmptyString(cp.getSmallImage())){
				cp.setSmallImage("");
			}
			cpList.add(cp);
		}
	}
	
	/**
	 * 复制MobileRecommendInfo中信息到MobileRecommend中 .
	 * @param mriList  源数据列表
	 * @param mrList  目的数据列表
	 * @return  目的数据列表
	 */
	public static List<MobileRecommend> copyMobileRecommendInfo2MobileRecommend(List<MobileRecommendInfo> mriList,List<MobileRecommend> mrList){
		if(null == mriList || mriList.size() == 0 ) {
			return mrList;
		}
		for(MobileRecommendInfo mri:mriList) {
			MobileRecommend mr = new MobileRecommend();
			mr.setId(mri.getId());
			mr.setObjectId(mri.getObjectId());
			mr.setObjectType(mri.getObjectType());
			mr.setRecommendContent(mri.getRecommendContent());
			mr.setRecommendHDImageUrl(mri.getRecommendHDImageUrl());
			mr.setRecommendImageUrl(mri.getRecommendImageUrl());
			mr.setRecommendTitle(mri.getRecommendTitle());
			mr.setUrl(mri.getUrl());
			mr.setHdUrl(mri.getHdUrl());
			mr.setLat(mri.getLatitude());
			mr.setLon(mri.getLongitude());
			mr.setPrice(null == mri.getPrice() ?"":mri.getPrice());
			mrList.add(mr);
		}
		return mrList;
	}
	
	/**
	 * 复制MobileRecommendInfo中信息到MobileRecommend中 .
	 * @param mriList  源数据列表
	 * @param mrList  目的数据列表
	 * @return  目的数据列表
	 */
	public static List<MobileRecommend> copyMobileRecommendInfo2MobileRecommend(List<MobileRecommendInfo> mriList,List<MobileRecommend> mrList,String os){
		if(null == mriList || mriList.size() == 0 ) {
			return mrList;
		}
		for(MobileRecommendInfo mri:mriList) {
			MobileRecommend mr = new MobileRecommend();
			mr.setId(mri.getId());
			mr.setObjectId(mri.getObjectId());
			mr.setObjectType(mri.getObjectType());
			mr.setRecommendContent(mri.getRecommendContent());
			mr.setRecommendImageUrl(mri.getRecommendImageUrl());
			mr.setRecommendTitle(mri.getRecommendTitle());
			mr.setUrl(getUrlByOs(mri.getUrl(),os));
			mrList.add(mr);
		}
		return mrList;
	}
	
	/**
	 * 更加操作系统获取 url 
	 * @param url
	 * @param os
	 * @return
	 */
	public static String getUrlByOs(String url,String os) {
		if(StringUtils.isEmpty(url)) {
			return "";
		}
		String[] urls = url.split("[; ；]");
		int length = urls.length;
		if("IPHONE".equalsIgnoreCase(os)) {
			if(length > 0) {
				return urls[0];
			}
		}else if("ANDROID".equalsIgnoreCase(os)) {
			if(length > 1) {
				return urls[1];
			} else {
				return urls[0];
			}
		}else if("IPAD".equalsIgnoreCase(os)) {
			if(length > 2) {
				return urls[2];
			} else {
				return urls[0];
			}
		}else {
			return urls[length-1];
		}
		return urls[0];
	}
	
	/**
	 * 复制列表. 
	 * @param mfList   源数据
	 * @param mmfList 目的数据. 
	 * @return mmfList
	 */
	public static List<MobileMyFavorite> copyFavoriteList2MyFavoriteList(List<MobileFavorite> mfList ,List<MobileMyFavorite> mmfList) {
		if(null == mfList || mfList.size() == 0 ) {
			return mmfList;
		}
		for(MobileFavorite mf:mfList) {
			mmfList.add(copyFavorite2MyFavorite(mf,new MobileMyFavorite()));
		}
		
		return mmfList;
	}
	
	/**
	 * 我的收藏复制. 
	 * @param mf   源数据
	 * @param mmf 目的数据. 
	 * @return mmf
	 */
	public static MobileMyFavorite copyFavorite2MyFavorite(MobileFavorite mf ,MobileMyFavorite mmf) {
		if(null != mf && null != mmf) {
			mmf.setCreatedTime(DateUtil.dateFormat(mf.getCreatedTime(),"yyyy-MM-dd HH:mm:ss"));
			mmf.setId(mf.getId());
			mmf.setObjectId(mf.getObjectId());
			mmf.setObjectImageUrl(mf.getObjectImageUrl()==null?"":mf.getObjectImageUrl());
			mmf.setObjectName(mf.getObjectName());
			mmf.setObjectType(mf.getObjectType());
		}
		return mmf;
	}
	
	/**
	 * 版本号。
	 * @param src
	 * @param dest
	 * @return
	 */
	public static com.lvmama.clutter.model.MobileVersion copyMobileVersion2MobileVersion(com.lvmama.comm.pet.po.mobile.MobileVersion src ,com.lvmama.clutter.model.MobileVersion dest) {
		if(null != src && null != dest) {
			dest.setId(src.getId());
			dest.setContent(src.getContent());
			dest.setForceUpdate("true".equals(src.getForceUpdate())?true:false);
			dest.setAuditing("true".equals(src.getIsAuditing())?true:false);
			dest.setTitle(src.getTitle());
			dest.setUpdateUrl(src.getUpdateUrl());
			dest.setVersion(src.getVersion());
			dest.setCreateTime(DateUtil.dateFormat(src.getCreatedTime(),"yyyy-MM-dd HH:mm:ss"));
		}
		return dest;
	}
	
	
	/**
	 * 工单处理时限
	 * @param date
	 * @return long 
	 */
	public static  Long limitTime(Date date){
		Long limitTime=0L; 
		 //日期
        int day = date.getDate();
        //小时
        int hour = date.getHours();
        //分钟
        int minute = date.getMinutes();
        //时间处理
        if((hour>=11 && hour<19) || (hour==19 && minute<=30)){//11点到19.30 处理时间为120分钟
        	limitTime=120L;
        }else {
        	if((hour>19 && hour<=23) || (hour==19 && minute>=30)){//19.30到24点--
        			limitTime=(long) ((35-hour)*60-minute);
        	}else{//0-11点
        		limitTime=(long) ((11-hour)*60-minute);
        	}
        }
	      return limitTime;
	}
	
	/**
	 * 简易预订产品列表组装
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String probuceListDatas(List<OrderDataItem> orderDataList) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer("");
		if (null != orderDataList && orderDataList.size() > 0) {
			for (int i = 0; i < orderDataList.size(); i++) {
				OrderDataItem mb = orderDataList.get(i);
				if (null != mb && mb.getBranchNum() > 0) {
					if (StringUtils.isEmpty(sb)) {
						sb.append(mb.getShortName()).append("<"+mb.getBranchId()+">"+"￥"+mb.getSellPriceYuan()+"x").append(mb.getBranchNum());
					} else {
						sb.append(",").append(mb.getShortName()).append("<"+mb.getBranchId()+">"+"￥"+mb.getSellPriceYuan()+"x")
								.append(mb.getBranchNum());
					}
				}
			}
		}
		return sb.toString();
	}
	
}
