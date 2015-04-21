package com.lvmama.prd.dao;

import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.RouteProduct;
import com.lvmama.comm.pet.po.place.Place;

public class RouteProductDAO extends BaseIbatisDAO {

	public RouteProduct getProductByProductId(long productId) {
		ProdProduct prodProduct = new ProdProduct();
		prodProduct.setProductId(productId);
		prodProduct = (ProdProduct) super.queryForObject("PROD_PRODUCT.selectProdRouteByPrimaryKey",prodProduct);
		if(prodProduct!=null){
			RouteProduct routeProduct = new RouteProduct();
			if(routeProduct!=null){
				routeProduct.setFrom((Place)super.queryForObject("COM_PLACE.getFromDestByProductId",productId));
				routeProduct.setTo((Place)super.queryForObject("COM_PLACE.getToDestByProductId",productId));
			}else {
				routeProduct = new RouteProduct();
			}
			routeProduct.setProdProduct(prodProduct);
			return routeProduct;
		}
		return null;
	}
	public void updateProductRouteByProductId(Map map){
		super.update("PROD_ROUTE.updateProductRouteByProductId",map);
	}

}
