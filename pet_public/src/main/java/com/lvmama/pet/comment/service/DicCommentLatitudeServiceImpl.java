/**
 * 
 */
package com.lvmama.pet.comment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.service.comment.DicCommentLatitudeService;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.PRODUCT_TYPE;
import com.lvmama.pet.comment.dao.DicCommentLatitudeDAO;

/**
 * @author liuyi
 * 点评字典SERVICE
 */
public class DicCommentLatitudeServiceImpl implements DicCommentLatitudeService {

	/**
	 * 日志输出器
	 */
	private final static Log LOG = LogFactory
			.getLog(DicCommentLatitudeServiceImpl.class);
	
	/**
	 * 维度的数据库接口
	 */
	private DicCommentLatitudeDAO dicCommentLatitudeDAO;
	
	@Override
	public Long insert(final DicCommentLatitude dic) {
		return dicCommentLatitudeDAO.insert(dic);
	}

	@Override
	public List<DicCommentLatitude> getDicCommentLatitudeList(final Map<String, Object> parames) {
		return dicCommentLatitudeDAO.getDicCommentLatitudeList(parames);
	}

	@Override
	public int update(final DicCommentLatitude dic) {
		return dicCommentLatitudeDAO.update(dic);
	}

	@Override
	public DicCommentLatitude queryByKey(final String id) {
		return dicCommentLatitudeDAO.queryByKey(id);
	}

	/**
	 * 查询景点的4个纬度
	 * @param place 景点
	 * @return List<DicCommentLatitude>
	 */
	public List<DicCommentLatitude> getDicCommentLatitudeListBySubject(Place place) {
 
		List<DicCommentLatitude> latitudes = null;
		String subject = place.getCmtTitle();

		// 景点门票依主题取维度，目的地统一维度，酒店及产品也统一维度
		if (Constant.STAGE_OF_HOTEL == Integer.parseInt(place.getStage())) {
			subject = "酒店和酒店产品";
		} else if (Constant.STAGE_OF_CITY == Integer.parseInt(place.getStage())) {
			subject = "目的地";
		}

		if (!StringUtils.isEmpty(subject)) {
			Map<String, Object> parames = new HashMap<String, Object>();
			parames.put("subject", subject);
			latitudes = dicCommentLatitudeDAO.getDicCommentLatitudeList(parames);
		}

		// 主题有对应的纬度
		if (null != latitudes && !latitudes.isEmpty()) {
			return latitudes;
		} else {
			// place主题无对应的纬度和place无主题
			return getDefaultDicCommentLatitude(place.getStage());
		}
	}
	
	/**
	 * 获取默认的点评维度
	 * @return 默认的点评维度
	 */
	private List<DicCommentLatitude> getDefaultDicCommentLatitude(String stage) {

		String otherOfSubject = null;
		// place无主题,但place酒店类型取主题: 其他主题
		if ("3".equalsIgnoreCase(stage)) {
			otherOfSubject = "其他主题";
		} else {
			// place主题无对应的纬度和place无主题,默认主题为: 其它
			otherOfSubject = "其它";
		}

		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("subject", otherOfSubject);
		List<DicCommentLatitude> latitudes = dicCommentLatitudeDAO.getDicCommentLatitudeList(parames);

		if (latitudes != null && latitudes.size() > 0) {
			return latitudes;
		} else {
			return new ArrayList<DicCommentLatitude>(0);
		}
	}
	
	/*
	 * 获取产品点评的维度
	 * @param toPlace 产品目的地
	 * @param productType 产品类型
	 * @return List<DicCommentLatitude>
	 * */
	public List<DicCommentLatitude> getLatitudesOfProduct(Place toPlace, String productType) {
		List<DicCommentLatitude> laList = null;
		
		//对于酒店和门票的产品点评，要插入对应目的地信息，并使用对应目的地的维度
		if(productType.equals(Constant.PRODUCT_TYPE.TICKET.name())|| productType.equals(Constant.PRODUCT_TYPE.HOTEL.name())){
			if(toPlace == null) return null;
			laList = getDicCommentLatitudeListBySubject(toPlace);
		} else {
			//线路
			 laList = getDicCommentLatitudeListByProductSubject(productType);
		}
		return laList;
	}
	
	/**
	 * 查询产品的纬度
	 * @return List<DicCommentLatitude>
	 */
	private List<DicCommentLatitude> getDicCommentLatitudeListByProductSubject(String productType) {
		List<DicCommentLatitude> latitudes = null;

		Constant.PRODUCT_LATITUDE productLatitude = Constant.PRODUCT_LATITUDE.getProductLatitude(productType);
		String subject = productLatitude.getChSubject();

		Map<String, Object> parames = new HashMap<String, Object>();
		parames.put("subject", subject);
		latitudes = dicCommentLatitudeDAO.getDicCommentLatitudeList(parames);

		return latitudes;
	}
	
	public void setDicCommentLatitudeDAO(final DicCommentLatitudeDAO dicCommentLatitudeDAO) {
		this.dicCommentLatitudeDAO = dicCommentLatitudeDAO;
	}

}
