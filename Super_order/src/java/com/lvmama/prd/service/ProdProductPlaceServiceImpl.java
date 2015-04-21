package com.lvmama.prd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.com.dao.ComPlaceDAO;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.ProdProductPlaceDAO;


public class ProdProductPlaceServiceImpl implements ProdProductPlaceService {

	private ProdProductPlaceDAO prodProductPlaceDAO;
	private ComPlaceDAO comPlaceDAO;
	private ComLogDAO comLogDAO;
	
	public void delete(Long prodProductId,String operatorName) {
		
		ProdProductPlace prodProductPlace = prodProductPlaceDAO.selectByPrimaryKey(prodProductId);
		prodProductPlaceDAO.delete(prodProductId);
		
		comLogDAO.insert("PROD_PRODUCT_PLACE", prodProductPlace.getProductId(), prodProductPlace.getProductPlaceId(),operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.deletePlace.name(),
				"删除产品标地", "标地名称[ "+prodProductPlace.getPlaceName()+" ]", "PROD_PRODUCT");
	}

	public ProdProductPlace insert(ProdProductPlace prodProductPlace,String operatorName) {
		Long pk=prodProductPlaceDAO.insert(prodProductPlace);
		prodProductPlace.setProductPlaceId(pk);
		
		comLogDAO.insert("PROD_PRODUCT_PLACE", prodProductPlace.getProductId(), pk,operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.insertPlace.name(),
				"添加产品标地", "标地名称[ "+prodProductPlace.getPlaceName()+" ]", "PROD_PRODUCT");
		return prodProductPlace;
	}

	@Override
	public void insertOrUpdateTrafficPlace(Long productId,
			Long fromPlaceId,Long toPlaceId) {
		boolean needAddFrom=true;
		boolean needAddTo=true;
		List<ProdProductPlace> list=selectByProductId(productId);
		for(ProdProductPlace ppp:list){
			if(StringUtils.equals("true", ppp.getFrom())){
				if(ppp.getPlaceId().equals(fromPlaceId)){
					needAddFrom=false;
				}else{
					prodProductPlaceDAO.delete(ppp.getProductPlaceId());
				}
			}else if(StringUtils.equals("true", ppp.getTo())){
				if(ppp.getPlaceId().equals(toPlaceId)){
					needAddTo=false;
				}else{
					prodProductPlaceDAO.delete(ppp.getProductPlaceId());
				}
			}
		}
		ProdProductPlace ppp=new ProdProductPlace();
		if(needAddFrom){
			ppp.setFrom("true");
			ppp.setPlaceId(fromPlaceId);
			ppp.setProductId(productId);
			prodProductPlaceDAO.insert(ppp);		
			ppp = new ProdProductPlace();
		}
		if(needAddTo){
			ppp.setTo("true");
			ppp.setPlaceId(toPlaceId);
			ppp.setProductId(productId);
			prodProductPlaceDAO.insert(ppp);
		}
		
		if(needAddFrom||needAddTo){
			comLogDAO.insert("PROD_PRODUCT_PLACE", productId,null,"SYSTEM",
					Constant.COM_LOG_PRODUCT_EVENT.insertPlace.name(),
					"添加产品标地","根据打包重置销售产品出发地与目的地", "PROD_PRODUCT");
		}
	}
	
	public List<ProdProductPlace> selectByProductId(Long productId) {
		return prodProductPlaceDAO.selectByProductId(productId);
	}

	
	public ProdProductPlaceDAO getProdProductPlaceDAO() {
		return prodProductPlaceDAO;
	}


	public void setProdProductPlaceDAO(ProdProductPlaceDAO prodProductPlaceDAO) {
		this.prodProductPlaceDAO = prodProductPlaceDAO;
	}

	public List<ProdProductPlace> findProdProductPlace(Long prodProductId) {
		return prodProductPlaceDAO.findProdProductPlace(prodProductId);
	}
 
	@Override
	public ResultHandleT<ProdProductPlace> changeFT(Long productPlaceId,String ftstr, String operatorName) {
		ResultHandleT<ProdProductPlace> result=new ResultHandleT<ProdProductPlace>();
		try{
			Assert.notNull(productPlaceId,"产品标的信息不存在");
			Assert.notNull(ftstr,"参数错误");
			ProdProductPlace ppp=prodProductPlaceDAO.selectByPrimaryKey(productPlaceId);
			Assert.notNull(ppp);
			String ft=ftstr.toUpperCase();
			if(StringUtils.equals(ft, "TO")){
				if(StringUtils.equals(ppp.getTo(), "true")){
					throw new IllegalArgumentException("当前标地已经是目的地");
				}
				ppp.setTo("true");			
			}else if(StringUtils.equals(ft, "FROM")){
				if(StringUtils.equals(ppp.getFrom(), "true")){
					throw new IllegalArgumentException("当前标地已经是出发地");
				}
				ppp.setFrom("true");
			}else{
				throw new IllegalArgumentException("变更的参数信息不正确");
			}
			
			Map<String,Object> param=new HashMap<String, Object>();
			param.put(ft, "true");
			param.put("productId", ppp.getProductId());
			//先清空当前产品的之前设置
			prodProductPlaceDAO.clearProductPlaceFT(param);
			
			//更新现在指定的
			prodProductPlaceDAO.updateByPrimaryKey(ppp);
			
			String temp = StringUtils.equals(ft, "TO")?"目的地":"出发地";
			comLogDAO.insert("PROD_PRODUCT_PLACE", ppp.getProductId(),ppp.getProductPlaceId(),operatorName,
					Constant.COM_LOG_PRODUCT_EVENT.changePlaceFT.name(),
					"修改标地 ", "修改标地[ "+ppp.getPlaceName()+" ]为"+temp, "PROD_PRODUCT");
			result.setReturnContent(ppp);
		}catch(IllegalArgumentException ex){
			result.setMsg(ex.getMessage());
		}
		return result;
	}

	@Override
	public ProdProductPlace selectByPrimaryKey(Long pk) {
		return prodProductPlaceDAO.selectByPrimaryKey(pk);
	}

	@Override
	public Place getToDestByProductId(long productId) {
		return comPlaceDAO.getToDestByProductId(productId);
	}
	
	@Override
	public Place getFromDestByProductId(long productId) {
		return comPlaceDAO.getFromDestByProductId(productId);
	}
	
	public void setComPlaceDAO(ComPlaceDAO comPlaceDAO) {
		this.comPlaceDAO = comPlaceDAO;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	@Override
	public Long selectDestByProductId(Long productId) {
		return prodProductPlaceDAO.selectDestByProductId(productId);
	}

	@Override
	public List<Place> getComPlaceByProductId(Long productId) {
		return this.comPlaceDAO.getComPlaceByProductId(productId);
	}
	
	@Override
	public List<Place> getNewComPlaceByProductId(Long productId) {
		return this.comPlaceDAO.getNewComPlaceByProductId(productId);
	}

	@Override
	public List<ProdProductPlace> getProdProductPlaceListByProductId(
			Long productId) {
		return prodProductPlaceDAO.getProdProductPlaceListByProductId(productId);
	}

	

}
