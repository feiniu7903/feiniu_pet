package com.lvmama.elong.service;

import java.util.List;
import java.util.Map;

import com.lvmama.elong.exception.ElongServiceException;
import com.lvmama.elong.model.HotelBrand;
/**
 * 获取hotelBrand列表
 * @author qinzubo
 *
 */
public interface IHotelBrandService {
	/**
	 * 获取全部hotel列表数据 
	 * @return
	 * @throws ElongServiceException
	 */
	List<HotelBrand> getHotelBrandList(Map<String,Object> params) throws ElongServiceException;
}
