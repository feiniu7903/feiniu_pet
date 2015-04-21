package com.lvmama.pet.place.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.place.HotelTrafficInfo;
import com.lvmama.comm.pet.service.place.HotelTrafficInfoService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.place.dao.HotelTrafficInfoDAO;

public class HotelTrafficInfoServiceImpl implements HotelTrafficInfoService {
	@Autowired
	private HotelTrafficInfoDAO hotelTrafficInfoDAO;
	@Autowired
	private ComLogService comLogService;
	
	@Override
	public void insert(final HotelTrafficInfo trafficInfo, final String operatorName) {
		hotelTrafficInfoDAO.insert(trafficInfo);
		comLogService.insert("SCENIC_LOG_PLACE", null, trafficInfo.getPlaceId(), operatorName,
				Constant.SCENIC_LOG_PLACE.createPlaceInfo.name(),"新增酒店交通信息", "新增酒店交通信息【"+ trafficInfo.getChTrafficInfo() + "\t" + trafficInfo.getFrom() + "\t" + trafficInfo.getDistance() + "\t" + trafficInfo.getDescription() +"】", "");
	}
	
	@Override
	public void update(final HotelTrafficInfo trafficInfo, final String operatorName) {
		HotelTrafficInfo orgTrafficInfo = hotelTrafficInfoDAO.queryById(trafficInfo.getId());
		if (null != orgTrafficInfo) {
			hotelTrafficInfoDAO.update(trafficInfo);
			StringBuilder sb = new StringBuilder();
			if (!orgTrafficInfo.getTrafficStyle().equals(trafficInfo.getTrafficStyle())) {
				sb.append(orgTrafficInfo.getChTrafficInfo() + "-->" + trafficInfo.getChTrafficInfo() + ";");
			}
			if (!orgTrafficInfo.getFrom().equals(trafficInfo.getFrom())) {
				sb.append(orgTrafficInfo.getFrom() + "-->" + trafficInfo.getFrom() + ";");
			}
			if (!orgTrafficInfo.getDistance().equals(trafficInfo.getDistance())) {
				sb.append(orgTrafficInfo.getDistance() + "-->" + trafficInfo.getDistance() + ";");
			}
			if (!orgTrafficInfo.getDescription().equals(trafficInfo.getDescription())) {
				sb.append(orgTrafficInfo.getDescription() + "-->" + trafficInfo.getDescription() + ";");
			}			
			if (sb.length() != 0) {
				comLogService.insert("SCENIC_LOG_PLACE", null, trafficInfo.getPlaceId(), operatorName,
						Constant.SCENIC_LOG_PLACE.updatePlaceInfo.name(),"删除酒店交通信息", "更新酒店交通信息:" + sb.toString(), "");
			}
		}
		
		
	}
	
	@Override
	public void delete(final Long trafficInfoId, final String operatorName) {
		HotelTrafficInfo trafficInfo = hotelTrafficInfoDAO.queryById(trafficInfoId);
		if (null != trafficInfo) {
			hotelTrafficInfoDAO.delete(trafficInfoId);
			comLogService.insert("SCENIC_LOG_PLACE", null, trafficInfo.getPlaceId(), operatorName,
					Constant.SCENIC_LOG_PLACE.updatePlaceInfo.name(),"删除酒店交通信息", "删除酒店交通信息【"+ trafficInfo.getChTrafficInfo() + "\t" + trafficInfo.getFrom() + "\t" + trafficInfo.getDistance() + "\t" + trafficInfo.getDescription() +"】", "");			
		}
		
	}
	
	@Override
	public List<HotelTrafficInfo> queryByPlaceId(final Long placeId) {
		return hotelTrafficInfoDAO.queryByPlaceId(placeId);
	}
	
	@Override
	public HotelTrafficInfo queryByPlacePK(final Long id) {
		return hotelTrafficInfoDAO.queryById(id);
	}
}
