package com.lvmama.pet.place.dao;

import java.sql.SQLException;
import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlacePhoto;

public class PlacePhotoDAO extends BaseIbatisDAO {

	public int deleteByPrimaryKey(Long placePhotoId) throws SQLException {
		PlacePhoto key = new PlacePhoto();
		key.setPlacePhotoId(placePhotoId);
		int rows = super.delete("PLACE_PHOTO.deleteByPrimaryKey", key);
		return rows;
	}

	public PlacePhoto selectByPrimaryKey(Long placePhotoId) throws SQLException {
		PlacePhoto key = new PlacePhoto();
		key.setPlacePhotoId(placePhotoId);
		PlacePhoto record = (PlacePhoto) super.queryForObject("PLACE_PHOTO.selectByPrimaryKey", key);
		return record;
	}

	@SuppressWarnings("unchecked")
	public List<PlacePhoto> queryByPlacePhoto(PlacePhoto placePhoto) {
		return super.queryForList("PLACE_PHOTO.queryPlacePhotos", placePhoto);
	}

	public void saveOrUpdatePlacePhoto(PlacePhoto placePhoto) {
		if(placePhoto.getPlacePhotoId() == null) {
			super.insert("PLACE_PHOTO.insert", placePhoto);
		} else {
			super.update("PLACE_PHOTO.update", placePhoto);
		}

	}
}