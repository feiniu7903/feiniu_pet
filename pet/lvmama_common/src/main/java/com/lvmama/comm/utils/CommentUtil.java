/**
 * 
 */
package com.lvmama.comm.utils;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductChannel;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.vo.comment.CmtPlaceTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CmtProdTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.comm.vo.comment.PlaceCmtCommentVO;
import com.lvmama.comm.vo.comment.ProductCmtCommentVO;

/**
 * @author liuyi
 * 点评子对象组装类
 *
 */
public class CommentUtil {
	
	private static final Log LOG = LogFactory.getLog(CommentUtil.class);
	
	/**
	 * 组成目的地/景点点评对象
	 * @param commonCmtCommentVO
	 * @param place
	 * @return
	 */
	public static PlaceCmtCommentVO  composePlaceComment(CommonCmtCommentVO commonCmtCommentVO, 
			Place place)
	{
		PlaceCmtCommentVO placeCmtCommentVO = new PlaceCmtCommentVO();
		BeanUtils.copyProperties(commonCmtCommentVO, placeCmtCommentVO);
		if(place != null) {
			placeCmtCommentVO.setPlaceName(place.getName());
			placeCmtCommentVO.setStage(place.getStage());
			placeCmtCommentVO.setPinYinUrl(place.getPinYinUrl());
		}
		return placeCmtCommentVO;
	}
	
	
	/**
	 * 组成产品点评对象
	 * @param commonCmtCommentVO
	 * @param productSearchInfo
	 * @return
	 */
	public static ProductCmtCommentVO composeProductComment(CommonCmtCommentVO commonCmtCommentVO, ProdProduct product, 
			List<ProdProductChannel> productChannelList)
    {
    	ProductCmtCommentVO productCmtCommentVO = new ProductCmtCommentVO();
    	BeanUtils.copyProperties(commonCmtCommentVO, productCmtCommentVO);
    	if(product != null){
    		String channel = "";
    		if(productChannelList != null && productChannelList.size() > 0)
    		{
    			//如果是后台产品则可点评，但不能推广相关产品前台页
    			for(int i = 0; i < productChannelList.size(); i++)
    			{
    				channel += productChannelList.get(i).getProductChannel() + ",";
    			}
    		}
    		productCmtCommentVO.setProductChannel(channel);
        	productCmtCommentVO.setProductName(product.getProductName());
        	productCmtCommentVO.setProductOfflineTime(product.getOfflineTime());
        	//productCmtCommentVO.setProductOnlineTime(productSearchInfo.g)
        	productCmtCommentVO.setProductOnline(product.getOnLine());
        	productCmtCommentVO.setProductSellPrice(product.getSellPrice());
        	productCmtCommentVO.setProductType(product.getProductType());
        	productCmtCommentVO.setIsProductValid(product.getValid());
        	productCmtCommentVO.setSmallImage(product.getSmallImage());
        	productCmtCommentVO.setProductLargeImage(product.getSmallImage());
    	}
    	return  productCmtCommentVO;
    }
	
	
	
	/**
	 * 组成产品点评对象
	 * @param commonCmtCommentVO
	 * @param productSearchInfo
	 * @return
	 */
//	public static ProductCmtCommentVO composeProductComment(CommonCmtCommentVO commonCmtCommentVO, 
//	       ProductSearchInfo productSearchInfo)
//    {
//    	ProductCmtCommentVO productCmtCommentVO = new ProductCmtCommentVO();
//    	BeanUtils.copyProperties(commonCmtCommentVO, productCmtCommentVO);
//    	if(productSearchInfo != null){
//    		productCmtCommentVO.setProductChannel(productSearchInfo.getChannel());
//        	productCmtCommentVO.setProductName(productSearchInfo.getProductName());
//        	productCmtCommentVO.setProductOfflineTime(productSearchInfo.getOfflineTime());
//        	//productCmtCommentVO.setProductOnlineTime(productSearchInfo.g)
//        	productCmtCommentVO.setProductOnline(productSearchInfo.getOnLine());
//        	productCmtCommentVO.setProductSellPrice(productSearchInfo.getSellPrice());
//        	productCmtCommentVO.setProductType(productSearchInfo.getProductType());
//        	productCmtCommentVO.setIsProductValid(productSearchInfo.getIsValid());
//        	productCmtCommentVO.setSmallImage(productSearchInfo.getSmallImage());
//        	productCmtCommentVO.setProductLargeImage(productSearchInfo.getLargeImage());
//    	}
//    	return  productCmtCommentVO;
//    }
	 
	/**
	 * 组成景点点评统计数据
	 * @param CmtTitleStatisticsVO
	 * @return
	 */
	public static CmtPlaceTitleStatisticsVO composePlaceTitleStatistics(CmtTitleStatisticsVO cmtTitleStatisticsVO,PlaceSearchInfo placeSearchInfo){

		CmtPlaceTitleStatisticsVO cmtPlaceTitleStaVO = new CmtPlaceTitleStatisticsVO();
		BeanUtils.copyProperties(cmtTitleStatisticsVO, cmtPlaceTitleStaVO);
		
		if(placeSearchInfo != null){
			cmtPlaceTitleStaVO.setPlaceId(placeSearchInfo.getPlaceId());
			cmtPlaceTitleStaVO.setPinYin(placeSearchInfo.getPinYin());
			cmtPlaceTitleStaVO.setPinYinUrl(placeSearchInfo.getPinYinUrl());
			cmtPlaceTitleStaVO.setPlaceSmallImage(placeSearchInfo.getSmallImage());
			cmtPlaceTitleStaVO.setProvice(placeSearchInfo.getProvince());
			cmtPlaceTitleStaVO.setCity(placeSearchInfo.getCity());
			cmtPlaceTitleStaVO.setPlaceLargeImage(placeSearchInfo.getLargeImage());
			cmtPlaceTitleStaVO.setTitleName(placeSearchInfo.getName());
			cmtPlaceTitleStaVO.setStage(""+placeSearchInfo.getStage());
		}
		return cmtPlaceTitleStaVO;
	}
	
	/**
	 * 组成产品点评统计数据
	 * @param cmtTitleStatisticsVO
	 * @return
	*/
//	public static  CmtProdTitleStatisticsVO composeProdTitleStatistics(CmtTitleStatisticsVO cmtTitleStatisticsVO, ProductSearchInfo productSearchInfo){
//		
//		CmtProdTitleStatisticsVO cmtProdTitleStaVO = new CmtProdTitleStatisticsVO();
//		BeanUtils.copyProperties(cmtTitleStatisticsVO, cmtProdTitleStaVO);
//		
//		if(productSearchInfo != null){
//			if(productSearchInfo.getProductId().equals(productSearchInfo.getProductId())){
//				cmtProdTitleStaVO.setProductType(productSearchInfo.getProductType());
//				cmtProdTitleStaVO.setProductLargeImage(productSearchInfo.getLargeImage());
//				cmtProdTitleStaVO.setTitleName(productSearchInfo.getProductName());
//				/* 获取 订单信息 order
//				 * cmtProdTitleStaVO.setOrderId();
//				cmtProdTitleStaVO.setOrderNo(orderNo);
//				cmtProdTitleStaVO.setOrderCreateTime(orderCreateTime);
//				cmtProdTitleStaVO.setIsCashRefund(isCashRefund);
//				cmtProdTitleStaVO.setCashRefund(cashRefund);
//				cmtProdTitleStaVO.setCountOfProduct(countOfProduct);
//				cmtProdTitleStaVO.setOnLine(productSearchInfo.geto);
//				*/
//			}
//		}
//		return cmtProdTitleStaVO;
//	}
	
	
	public static  CmtProdTitleStatisticsVO composeProdTitleStatistics(CmtTitleStatisticsVO cmtTitleStatisticsVO, ProdProduct product){
		
		CmtProdTitleStatisticsVO cmtProdTitleStaVO = new CmtProdTitleStatisticsVO();
		BeanUtils.copyProperties(cmtTitleStatisticsVO, cmtProdTitleStaVO);
		
		if(product != null){
			if(product.getProductId().equals(cmtTitleStatisticsVO.getProductId())){
				cmtProdTitleStaVO.setProductType(product.getProductType());
				cmtProdTitleStaVO.setProductLargeImage(product.getSmallImage());
				cmtProdTitleStaVO.setTitleName(product.getProductName());
				/* 获取 订单信息 order
				 * cmtProdTitleStaVO.setOrderId();
				cmtProdTitleStaVO.setOrderNo(orderNo);
				cmtProdTitleStaVO.setOrderCreateTime(orderCreateTime);
				cmtProdTitleStaVO.setIsCashRefund(isCashRefund);
				cmtProdTitleStaVO.setCashRefund(cashRefund);
				cmtProdTitleStaVO.setCountOfProduct(countOfProduct);
				cmtProdTitleStaVO.setOnLine(productSearchInfo.geto);
				*/
			}
		}
		return cmtProdTitleStaVO;
	}
	
	
	public static CmtProdTitleStatisticsVO composeProdTitleStatistics(OrderAndComment result){
		
		CmtProdTitleStatisticsVO cmtProdTitleStaVO = new CmtProdTitleStatisticsVO();
		if(result != null){
			cmtProdTitleStaVO.setOrderId(Long.parseLong(result.getOrderId()));
			cmtProdTitleStaVO.setOrderNo(result.getOrderNo());
			cmtProdTitleStaVO.setOrderCreateTime(result.getOrderCreateTime());
			cmtProdTitleStaVO.setIsCashRefund(result.getIsCashRefund());
			cmtProdTitleStaVO.setCashRefund(result.getCashRefund());
			cmtProdTitleStaVO.setProductId(result.getProductId());
			cmtProdTitleStaVO.setTitleName(result.getProductName());
			cmtProdTitleStaVO.setProductType(result.getProductType());
			cmtProdTitleStaVO.setOnLine(result.getProductOnLine());
			cmtProdTitleStaVO.setCountOfProduct(result.getProductQuantity());
			cmtProdTitleStaVO.setOrderVisitTime(result.getOrderVisitTime());
		}
		return cmtProdTitleStaVO;
	}
	
	/**
	 * 调用PHP接口写站内信
	 * @param subject
	 * @param message
	 * @param type
	 */
	public static void synchLetter(final String subject,final String message,final String type,final String uid){
		try{
			String timeStamp=String.valueOf(new Date().getTime());
			String token=MD5.md5("3MposhDToEfS28mlzubvx8zD2hJI1AoR"+timeStamp);
			
			StringBuffer sb = new StringBuffer("http://www.lvmama.com/message/index.php?r=PrivatePm/Create");
			
			sb.append("&subject=").append(URLEncoder.encode(subject, "utf-8"))
				.append("&message=").append(URLEncoder.encode(message, "utf-8"))
				.append("&type=").append(URLEncoder.encode(type, "utf-8"))
			    .append("&uid=").append(URLEncoder.encode(uid, "utf-8"))
			    .append("&token=").append(token)
			    .append("&timeStamp=").append(timeStamp);
//			if (LOG.isDebugEnabled()) {
//				LOG.debug("synchLetter data：" + sb.toString());
//			}
			String jason = HttpsUtil.requestGet(sb.toString()); 
			jason= jason.substring(jason.indexOf(":")+1, jason.indexOf(",")); 
			if(Integer.valueOf(jason)!=200){ 
			LOG.info("Code:==>"+jason + " URL:==>"+sb.toString()); 
			}
		}catch(Exception e){
			LOG.error(e);
		}
	}
		
  
}
