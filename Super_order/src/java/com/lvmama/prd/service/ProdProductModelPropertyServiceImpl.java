package com.lvmama.prd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.service.prod.ProdProductModelPropertyService;
import com.lvmama.comm.pet.po.prod.ProdProductModelProperty;
import com.lvmama.comm.vo.ProdProductModelPropertyVO;
import com.lvmama.prd.dao.ProdProductModelPropertyDAO;
import com.lvmama.prd.dao.RouteProductDAO;

public class ProdProductModelPropertyServiceImpl implements
		ProdProductModelPropertyService {

	private ProdProductModelPropertyDAO prodProductModelPropertyDAO;
	private RouteProductDAO routeProductDAO;
	
	/**
	 * 
	 */
	public void saveProdProductModelProperty(
			List<ProdProductModelProperty> prodProductModelPropertyList) {
		if(prodProductModelPropertyList!=null&&prodProductModelPropertyList.size()>0){
			boolean flag=false;
			for(ProdProductModelProperty model:prodProductModelPropertyList){
				if(!flag){
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("productId", model.getProductId());
					map.put("isMaintain", model.getIsMaintain());
					prodProductModelPropertyDAO.deleteProdProductModelPropertyByProductId(map);
					flag=true;
				}
				prodProductModelPropertyDAO.saveProdProductModelProperty(model);
			}
		}
		
	}
	
	public void updateProdRule(String productId, String routeCateGory,
			String routeStandard, String departArea,String travelTime,String dataStr) {
		List<ProdProductModelProperty> list=new ArrayList<ProdProductModelProperty>();
		if(dataStr!=null&&!"".equals(dataStr)){
			ProdProductModelProperty prodProductModelProperty=null;
			String[] tempDataStr=dataStr.split(",");
			if(tempDataStr!=null){
				for(int i=0;i<tempDataStr.length;i++){
					if(tempDataStr[i]!=null&&!"".equals(tempDataStr[i])){
						String[] dataValue=tempDataStr[i].split("_");
						if(dataValue[1].equals("Y")){
							String[] pid=dataValue[2].split("[*]");
							for(int n=0;n<pid.length;n++){
								prodProductModelProperty=new ProdProductModelProperty();
								prodProductModelProperty.setModelPropertyId(Long.parseLong(pid[n]));
								prodProductModelProperty.setProductId(Long.parseLong(productId));
								prodProductModelProperty.setIsMaintain("N");
								list.add(prodProductModelProperty);
							}
						}else{
							prodProductModelProperty=new ProdProductModelProperty();
							prodProductModelProperty.setModelPropertyId(Long.parseLong(dataValue[2]));
							prodProductModelProperty.setProductId(Long.parseLong(productId));
							prodProductModelProperty.setIsMaintain("N");
							list.add(prodProductModelProperty);
						}
					}
					
					
				}
			}
			
		}
		saveProdProductModelProperty(list);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("productId", productId);
		map.put("routeCateGory", routeCateGory);
		map.put("routeStandard", routeStandard);
		map.put("departArea", departArea);
		map.put("travelTime", travelTime);
		routeProductDAO.updateProductRouteByProductId(map);
	}
	
	
	@Override
	public void clearProdProductModelPropertyByProductId(Long productId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("productId", productId);
		prodProductModelPropertyDAO.deleteProdProductModelPropertyByProductId(map);
		/*StringBuffer sb=new StringBuffer("");
		sb.append(" ROUTE_CATEGORY=null,");
		sb.append(" ROUTE_STANDARD=null,");
		sb.append(" DEPART_AREA=null,");
		sb.append(" TRAVEL_TIME=null ");
		map.put("isClear", sb.toString());
		routeProductDAO.updateProductRouteByProductId(map);*/
	}
	@Override
	public boolean isCheckExistByProperty(String propertyId) {
		long count=prodProductModelPropertyDAO.isCheckExistByProperty(propertyId);
		if(count>0)
			return true;
		return false;
	}

	public List<ProdProductModelProperty> selectByParam(Map<String, Object> map){
		return prodProductModelPropertyDAO.selectByParam(map);
	}
	
	public List<ProdProductModelPropertyVO> getProdProductModelPropertyVOByProductId(
			String productId) {
		return prodProductModelPropertyDAO.getProdProductModelPropertyVOByProductId(productId);
	}
	public List<ProdProductModelProperty> getProdProductModelPropertyByProductId(
			String productId) {
		return prodProductModelPropertyDAO.getProdProductModelPropertyByProductId(productId);
	}
	public ProdProductModelPropertyDAO getProdProductModelPropertyDAO() {
		return prodProductModelPropertyDAO;
	}
	public void setProdProductModelPropertyDAO(
			ProdProductModelPropertyDAO prodProductModelPropertyDAO) {
		this.prodProductModelPropertyDAO = prodProductModelPropertyDAO;
	}
	
	public RouteProductDAO getRouteProductDAO() {
		return routeProductDAO;
	}
	public void setRouteProductDAO(RouteProductDAO routeProductDAO) {
		this.routeProductDAO = routeProductDAO;
	}
	
}
