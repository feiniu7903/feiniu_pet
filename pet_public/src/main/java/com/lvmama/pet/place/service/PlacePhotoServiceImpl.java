package com.lvmama.pet.place.service;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.service.place.PlacePhotoService;
import com.lvmama.pet.place.dao.PlacePhotoDAO;

public class PlacePhotoServiceImpl implements PlacePhotoService {
	private PlacePhotoDAO placePhotoDAO;

	public void setPlacePhotoDAO(PlacePhotoDAO placePhotoDAO) {
		this.placePhotoDAO = placePhotoDAO;
	}

	@Override
	public List<PlacePhoto> queryByPlacePhoto(PlacePhoto placePhoto) {
		return placePhotoDAO.queryByPlacePhoto(placePhoto);
	}

	@Override
	public void saveOrUpdatePlacePhoto(PlacePhoto placePhoto) {
		placePhotoDAO.saveOrUpdatePlacePhoto(placePhoto);
	}

	@Override
	public void delete(PlacePhoto placePhoto) throws SQLException {
		placePhotoDAO.deleteByPrimaryKey(placePhoto.getPlacePhotoId());
	}
	
	@Override
	public void batchSavePhotoSeq(String placePhotoIds) {
		if(StringUtils.isNotBlank(placePhotoIds)){
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			Map<String,Object> param=null;
			String[] items=placePhotoIds.split(",");
			if(items.length>0){
				for(String item:items){
					String[] photo=item.split("_");
					param=new HashMap<String,Object>();					
					list.add(param);
					PlacePhoto placePhoto = new PlacePhoto();
					placePhoto.setPlacePhotoId(Long.parseLong(photo[0]));
					placePhoto.setSeq(Long.parseLong(photo[1]));					
					saveOrUpdatePlacePhoto( placePhoto);
				}
				
			}
		}
	}

	@Override
	public PlacePhoto queryByPlacePhotoId(Long placePhotoId) {
		PlacePhoto placePhoto = null;
		try {
			placePhoto = placePhotoDAO.selectByPrimaryKey(placePhotoId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return placePhoto;
	}

}
