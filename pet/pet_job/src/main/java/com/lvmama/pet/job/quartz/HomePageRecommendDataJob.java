package com.lvmama.pet.job.quartz;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.homePage.HomePageUtils;

/**
 * 首页内容推荐所需要的每天更新的程序
 * @author Administrator
 *
 */
public class HomePageRecommendDataJob  implements Runnable {
	
	private static final Log log = LogFactory
			.getLog(HomePageRecommendDataJob.class);
	
	/**
	 * 推荐管理的远程服务
	 */
	@Autowired
	private RecommendInfoService recommendInfoService;
	/**
	 * 订单远程调用服务
	 */
	@Autowired
	private OrderService orderServiceProxy;
	

	@Action("/homePage/homeJob")
	public void run() {
		if (true){
			try {
			xianShiTeMai();
			reMiaoPaiHang();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 限时特卖模块的更新
	 */
	private void xianShiTeMai() {
		Map<String, Object> param =new HashMap<String, Object>();

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		for(Long parentBlockId : HomePageUtils.FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.values()) {
			param.clear();
			param.put("dataCode", HomePageUtils.XIAN_SHI_TE_MAI_DATA_CODE);
			param.put("parentRecommendBlockId", parentBlockId);
			
			List<RecommendInfo> recommendInfos = recommendInfoService.queryRecommendInfoByParam(param);
			//log.info("首页 限时特卖 "+recommendInfos);
			recommendInfos = HomePageUtils.distribute(recommendInfos);
			for (RecommendInfo info : recommendInfos) {
				if (StringUtils.isBlank(info.getBakWord4())&&StringUtils.isBlank(info.getBakWord5())) {
					info.setBakWord4(DateUtil.getFormatDate(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));

					Calendar endTime = (Calendar) cal.clone();
					endTime.add(Calendar.DATE, Integer.parseInt(info.getBakWord2()) - 1);
					endTime.set(Calendar.HOUR_OF_DAY, 23);
					endTime.set(Calendar.MINUTE, 59);
					endTime.set(Calendar.SECOND, 59);
					info.setBakWord5(DateUtil.getFormatDate(endTime.getTime(), "yyyy-MM-dd HH:mm:ss"));
					recommendInfoService.updateRecommendInfo(info);
					log.info("限时特卖 第一次更新:parentBlockId:"+parentBlockId+info.getTitle()+"  上线时间:"+info.getBakWord4()+" 剩下上线天数："+info.getBakWord2()+" 下线时间："+info.getBakWord5());
				}else if (// 上线后，如果上过一天，就减去一天，job为每天跑一次
						// 减去一天
			DateUtil.getDateByStr(info.getBakWord5(), "yyyy-MM-dd HH:mm:ss")
					.after(new Date())
					&& info.getBakWord2() != null
					&& StringUtils.isNotBlank(info.getBakWord2())
					&& StringUtil.isNumber(info.getBakWord2())) {
				info.setBakWord2(String.valueOf(Integer.valueOf(info
						.getBakWord2()) - 1));
				recommendInfoService.updateRecommendInfo(info);
				log.info("限时特卖  第2-N次更新:parentBlockId:"+parentBlockId + info.getTitle() + "  上线时间:"
						+ info.getBakWord4() + " 剩下上线天数："
						+ info.getBakWord2() + " 下线时间："
						+ info.getBakWord5());
			} else {
				log.info("限时特卖  :parentBlockId:"+parentBlockId + info.getTitle() + "  上线时间:"
						+ info.getBakWord4() + " 剩下上线天数："
						+ info.getBakWord2() + " 下线时间："
						+ info.getBakWord5()+"没有更新操作 ");
				continue;
			}
			}
			
		}
	}
	
	/**
	 * 热销排行模块的更新
	 */
	private void reMiaoPaiHang() {
		Map<String, Object> param =new HashMap<String, Object>();
		for(Long parentBlockId : HomePageUtils.FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.values()) {
			param.clear();
			param.put("dataCode", HomePageUtils.RE_MIAO_PAI_HANG_CODE);
			param.put("parentRecommendBlockId", parentBlockId);
			List<RecommendInfo> recommendInfos = recommendInfoService.queryRecommendInfoByParam(param);
			for (RecommendInfo info : recommendInfos) {
				if (null != info && StringUtils.isNotBlank(info.getRecommObjectId())) {
					CompositeQuery compositeQuery = new CompositeQuery();
					
					OrderIdentity orderIdentity=new OrderIdentity();
					orderIdentity.setProductid(Long.parseLong(info.getRecommObjectId()));
					compositeQuery.setOrderIdentity(orderIdentity);
					OrderTimeRange orderTimeRange = new OrderTimeRange();
					Calendar cal = Calendar.getInstance();
					cal.setTime(info.getCreateTime());
					cal.add(Calendar.MONTH, -2);
					cal.set(Calendar.DAY_OF_MONTH, 1);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					orderTimeRange.setCreateTimeStart(cal.getTime());	
					compositeQuery.setOrderTimeRange(orderTimeRange);
					Long total = orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery);
					
					info.setBakWord2(String.valueOf(total + Integer.parseInt(StringUtils.isNotEmpty(info.getBakWord3()) ? info.getBakWord3() : "0")));
					
					recommendInfoService.updateRecommendInfo(info);
				}
			}
		}
	}

	public void setRecommendInfoService(RecommendInfoService recommendInfoService) {
		this.recommendInfoService = recommendInfoService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	
}
