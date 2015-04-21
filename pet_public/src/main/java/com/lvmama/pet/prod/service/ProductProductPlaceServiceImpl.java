package com.lvmama.pet.prod.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlacePlaceDest;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.po.prod.ProductProductPlace;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.prod.ProductProductPlaceService;
import com.lvmama.pet.place.dao.PlacePlaceDestDAO;
import com.lvmama.pet.prod.dao.ProductProductPlaceDAO;

public class ProductProductPlaceServiceImpl implements ProductProductPlaceService {
	@Autowired
	private ProductProductPlaceDAO productProductPlaceDAO;
	@Autowired
	private PlacePlaceDestDAO placePlaceDestDAO; 
	@Autowired
	private PlaceService placeService;
	
	@Override
	public void updateProductProductPlaceByProductId(final Long productId, final List<ProdProductPlace> list) {

        if (list == null || list.size() == 0) { //如果一个标的都没有，那就全干掉吧～
            productProductPlaceDAO.deleteByProductId(productId);
        }else{
			productProductPlaceDAO.deleteByProductId(productId);
			Long fromPlaceId=getFromPlace(list);
			ProductProductPlace productProductPlace=null;
			for(ProdProductPlace prodProductPlace:list){
				if(fromPlaceId!=null&&!"".equals(fromPlaceId)){
					if(fromPlaceId.equals(prodProductPlace.getPlaceId()))
						continue;
				}
				productProductPlace=new ProductProductPlace();
				Place place=placeService.queryPlaceByPlaceId(prodProductPlace.getPlaceId());
				productProductPlace.setParentPlaceId(place.getParentPlaceId());
				productProductPlace.setFromPlaceId(fromPlaceId);
				productProductPlace.setProductId(productId);
				productProductPlace.setPlaceId(prodProductPlace.getPlaceId());
				productProductPlace.setIsOriginal("true");
				if(StringUtils.isNotBlank(prodProductPlace.getTo())&&"true".equals(prodProductPlace.getTo())){
					productProductPlace.setIsToPlace("true");
				}else{
					productProductPlace.setIsToPlace("false");
				}
				processParentPlace(productId,fromPlaceId,"false",prodProductPlace.getParentPlaceIdList());
				
				Map<String,Object> param=new HashMap<String,Object>();
				param.put("productId", productId);
				param.put("placeId", prodProductPlace.getPlaceId());
				List<ProductProductPlace> productProductPlaceList = productProductPlaceDAO.query(param);
				ProductProductPlace temp=null;
				if (null != productProductPlaceList && !productProductPlaceList.isEmpty()) {
					temp=productProductPlaceList.get(0);
					productProductPlaceDAO.updateById(productProductPlace.getIsOriginal(),productProductPlace.getIsToPlace(),temp.getId());
				} else {
					productProductPlaceDAO.insert(productProductPlace);
				}
			}
		}
	}
	//计算是否有出发地,以及封装父类place
	private Long getFromPlace(List<ProdProductPlace> list){
		Long fromPlaceId=null;
		for(ProdProductPlace prodProductPlace:list){
			if(prodProductPlace.getFrom()!=null&&!"".equals(prodProductPlace.getFrom())&&"true".equals(prodProductPlace.getFrom())){
				fromPlaceId=prodProductPlace.getPlaceId();
				prodProductPlace.setParentPlaceIdList(null);
			}else{
				List<PlacePlaceDest> parentPlaceIdList=(List<PlacePlaceDest>)placePlaceDestDAO.getParentPlaceIdListByCurrentPlaceId(prodProductPlace.getPlaceId());
				prodProductPlace.setParentPlaceIdList(parentPlaceIdList);
			}
			
		}
		return fromPlaceId;
	}
	//添加所有父类place
	private void processParentPlace(Long productId,Long fromPlaceId,String isToPlace,List<PlacePlaceDest> placeIdList){
		if(placeIdList!=null&&placeIdList.size()>0){
			ProductProductPlace productProductPlace=null;
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("productId", productId);
			for(PlacePlaceDest tempPlaceId:placeIdList){
				param.put("placeId", tempPlaceId.getPlaceId());
				List<ProductProductPlace> list = productProductPlaceDAO.query(param);
				if (null == list || list.isEmpty()) {
					productProductPlace=new ProductProductPlace();
					productProductPlace.setParentPlaceId(tempPlaceId.getParentPlaceId());
					productProductPlace.setFromPlaceId(fromPlaceId);
					productProductPlace.setProductId(productId);
					productProductPlace.setPlaceId(tempPlaceId.getPlaceId());
					productProductPlace.setIsOriginal("false");
					productProductPlace.setIsToPlace(isToPlace);
					productProductPlaceDAO.insert(productProductPlace);
				} 
				
			}
		}
		
	}
	@Override
	public List<ProductProductPlace> getProductProductPlaceListByPlaceId(Long placeId, int startIndex,int maxResult) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("startRows", startIndex);
		param.put("endRows", maxResult);
		param.put("placeId", placeId);
		param.put("isPaging", "Y");
		return productProductPlaceDAO.query(param);
	}
	

	public List<ProductProductPlace> getProductProductPlaceListByProductId(Long productId){
		return productProductPlaceDAO.queryProductProductPlaceListByProductId(productId);
	}
	@Override
	public int countProductProductPlaceListByPlaceId(Long placeId) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("placeId", placeId);
		List<ProductProductPlace> list=productProductPlaceDAO.query(param);
		if(list!=null&&!list.isEmpty()){
			return list.size();
		}
		return 0;
	}
}
