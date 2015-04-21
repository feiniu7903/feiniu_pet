package com.lvmama.comm.pet.service.place;

import java.sql.SQLException;
import java.util.List;

import com.lvmama.comm.pet.po.place.PlacePhoto;

public interface PlacePhotoService {
	
	/**
	 * 查询出目的的相关的图片；最大结果是10条；
	 * 
	 * @param placePhoto
	 * @return
	 */
	public List<PlacePhoto> queryByPlacePhoto(PlacePhoto placePhoto);
	
	/**
	 * 保存或者更新对象
	 * 
	 * @param placePhoto
	 */
	public void saveOrUpdatePlacePhoto(PlacePhoto placePhoto);
	
	/**
	 * 删除
	 * 
	 * @param placePhoto
	 * @throws SQLException 
	 */
	public void delete(PlacePhoto placePhoto) throws SQLException;
	
	/**
	 * 批量修改图片的SEQ
	 * 
	 * @param placePhoto
	 * @throws SQLException 
	 */
	public void batchSavePhotoSeq(String placePhotoIds);
	
	/**
	 * 查询出相关的图片；
	 * 
	 * @param placePhoto
	 * @return
	 */
	public PlacePhoto queryByPlacePhotoId(Long placePhotoId);
}
