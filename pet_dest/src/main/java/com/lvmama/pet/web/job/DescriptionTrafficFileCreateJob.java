package com.lvmama.pet.web.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.place.PlacePageService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.utils.homePage.PlaceUtils;
import com.lvmama.comm.vo.Constant;

/**
 * 景点酒店中描述+交通信息创建文件
 * 
 * @author nixianjun
 * 
 */
public class DescriptionTrafficFileCreateJob implements Runnable {
	private static Log log = LogFactory
			.getLog(DescriptionTrafficFileCreateJob.class);
	public static final Integer PAGESIZE = 900;
	private PlaceService placeService;

	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("DescriptionTrafficFileCreateJob Running!");
			Map<String, Object> map = new HashMap<String, Object>();
			List<Long> list = new ArrayList<Long>();
			list.add(Long.valueOf(Constant.PLACE_STAGE.PLACE_FOR_SCENIC
					.getCode()));
			map.put("stages", list);
			map.put("isValid", "Y");	
		    Integer count=	this.placeService.countPlaceListByParam(map).intValue();
			Integer pageCount = PlaceUtils.getTotalPages(count, PAGESIZE);
			for(int curPage =1;curPage<=pageCount;curPage++){
					int startRow = (curPage - 1) * PAGESIZE + 1;
					int endRow = curPage * PAGESIZE;
					map.put("startRows", startRow);
 					map.put("endRows", endRow);
 					createItemPageFile(map);
 					log.info("####第"+curPage+"页");
			}
			log.info("##DescriptionTrafficFileCreateJob Ending!");
		}
	}

	/**
	 * 分页创建file
	 * @param map
	 * @author nixianjun 2013-9-2
	 */
	private void  createItemPageFile(Map<String, Object> map){
		List<Place> placeList = this.placeService
					.queryPlaceListByParam(map);
			for (Place p : placeList) {
				if (null != p.getPlaceId()) {
					 Place p2 =  placeService.getDescripAndTrafficByPlaceId(p.getPlaceId());
		 			 PlaceUtils.createDescriptionAndTrafficInfo(p2);
				}
			}
	}
	
	/**
	 * @param placeService
	 *            the placeService to set
	 */
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}
	

}
