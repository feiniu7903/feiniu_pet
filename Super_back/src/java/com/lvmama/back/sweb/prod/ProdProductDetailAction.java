/**
 * 
 */
package com.lvmama.back.sweb.prod;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.prod.ProdHotel;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

/**
 * 读取产品信息
 * @author yangbin
 *
 */
public class ProdProductDetailAction extends BaseAction{

	private ProdProductService prodProductService;
	private PlaceService placeService;
	private Long productId;
	
	@Action("/prod/getDetailJSON")
	public void getProductDetail(){
		JSONResult result=new JSONResult();
		if(productId!=null&&productId>0){
			ProdProduct product=prodProductService.getProdProductPlaceById(productId);
			result.put("filialeName", Constant.FILIALE_NAME.getCnName(product.getFilialeName()));
			String productZone="";
			boolean is_route=false;
			boolean ff=false;
			
			//出境的取对应的区域，非出境的取目的地的省信息
			if(product instanceof ProdRoute &&(Constant.ROUTE_SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equals(product.getSubProductType())||Constant.ROUTE_SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equals(product.getSubProductType()))){			
					productZone=CodeSet.getInstance().getCodeName("PROD_ROUTE_DEPART_AREA", ((ProdRoute)product).getDepartArea());
					ff=true;
			}
			
			if(product instanceof ProdRoute){
				result.put("day", ((ProdRoute)product).getDays());
			}else if(product instanceof ProdHotel){
				result.put("day", ((ProdHotel)product).getDays());
			}
			
			Place toPlace=product.getToPlace();
			if(toPlace!=null){
				if(StringUtils.isEmpty(productZone)){
					productZone=toPlace.getProvince();
				}
				result.put("toPlaceName", toPlace.getName());
			}
			
			Place fromPlace=prodProductService.getFromDestByProductId(productId);
			if(fromPlace!=null){
				result.put("fromPlaceName", fromPlace.getName());
			}
			result.put("is_route",is_route);
			result.put("ff",ff);
			result.put("subProductType", product.getSubProductType());
			result.put("productZone", productZone);
			//result.put("product", obj);
		}
		result.output(getResponse());
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}
	
}
