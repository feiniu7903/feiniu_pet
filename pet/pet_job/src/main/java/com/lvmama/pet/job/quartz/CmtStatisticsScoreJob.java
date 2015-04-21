package com.lvmama.pet.job.quartz;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.service.comment.CmtLatitudeStatistisService;
import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.pet.service.search.PlaceSearchInfoService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;

public class CmtStatisticsScoreJob {
	
	private static final Log log = LogFactory
			.getLog(CmtStatisticsScoreJob.class);
	
	/**
	 * 景区点评服务统计逻辑接口
	 */
	private CmtTitleStatistisService cmtTitleStatistisService;
	private CmtLatitudeStatistisService cmtLatitudeStatistisService;
	private ProductSearchInfoService productSearchInfoService;
	private PlaceSearchInfoService placeSearchInfoService;
	private ProdProductService prodProductService;
	
	public void run(){
		if (Constant.getInstance().isJobRunnable()) {
			
			//景点和产品的点评统计
			long begin = System.currentTimeMillis();
			this.cmtTitleStatistisService.mergeStatisticsPlaceScore();
			log.info("merge Statistics Place Score at:"+ (System.currentTimeMillis()- begin));
			
			begin = System.currentTimeMillis();
			this.cmtTitleStatistisService.mergeStatisticsProductScore();
			log.info("merge Statistics Product Score at:"+ (System.currentTimeMillis()- begin));
			
			//先查出没有TitleName的记录，获取数据填充字段:title_name,stage,product_type
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("hasNoTitleName", "true");
			List<CmtTitleStatisticsVO> list = cmtTitleStatistisService.getCommentStatisticsList(param);
			for(CmtTitleStatisticsVO vo : list){
				CmtTitleStatisticsVO update = composeCmtTitleStatistics(vo);
				cmtTitleStatistisService.update(update);
			}
			
			//景点和产品的维度统计
			begin = System.currentTimeMillis();
			this.cmtLatitudeStatistisService.mergeStatisticsPlaceLatitudeAvgScore();
			log.info("merge Statistics Place Latitude Avg Score at:"+ (System.currentTimeMillis()- begin));
			
			begin = System.currentTimeMillis();
			this.cmtLatitudeStatistisService.mergeStatisticsProductLatitudeAvgScore();
			log.info("merge Statistics Product Latitude Avg Score at:"+ (System.currentTimeMillis()- begin));
			
			
			//更新product search info 表的comment count
			log.info("update product search info comment at:"+ (System.currentTimeMillis()- begin));
			try
			{
				productSearchInfoService.updateProductSearchInfoCmtNum();
			}
			catch(Exception ex)
			{
				log.error(ex,ex);
			}
			log.info("update product search info comment at:"+ (System.currentTimeMillis()- begin));
			
		}
	}

	private CmtTitleStatisticsVO composeCmtTitleStatistics(CmtTitleStatisticsVO cmtTitleStatisticsVO)
	{
		if (null != cmtTitleStatisticsVO.getProductId()) {
			ProdProduct product = prodProductService.getProdProductById(cmtTitleStatisticsVO.getProductId());
			return CommentUtil.composeProdTitleStatistics(cmtTitleStatisticsVO, product);
		} 
		else //包含老体验点评(没ProductId)和普通点评Constant.EXPERIENCE_COMMON_TYPE
		{
			PlaceSearchInfo placeSearchInfo = placeSearchInfoService.getPlaceSearchInfoByPlaceId(cmtTitleStatisticsVO.getPlaceId());
			return CommentUtil.composePlaceTitleStatistics(cmtTitleStatisticsVO, placeSearchInfo);
		}
	}

	public void setCmtTitleStatistisService(
			CmtTitleStatistisService cmtTitleStatistisService) {
		this.cmtTitleStatistisService = cmtTitleStatistisService;
	}

	public void setCmtLatitudeStatistisService(
			CmtLatitudeStatistisService cmtLatitudeStatistisService) {
		this.cmtLatitudeStatistisService = cmtLatitudeStatistisService;
	}

	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}

	public void setPlaceSearchInfoService(PlaceSearchInfoService placeSearchInfoService) {
		this.placeSearchInfoService = placeSearchInfoService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
}
