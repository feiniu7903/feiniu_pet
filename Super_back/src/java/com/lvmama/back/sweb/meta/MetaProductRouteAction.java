/**
 * 
 */
package com.lvmama.back.sweb.meta;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.meta.MetaProductRoute;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
@Results({
	@Result(name="input",location="/WEB-INF/pages/back/meta/add_route.jsp")
})
public class MetaProductRouteAction extends MetaProductEditAction<MetaProductRoute>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 582698462463965834L;

	public MetaProductRouteAction() {
		super(Constant.PRODUCT_TYPE.ROUTE.name());
	}

	@Override
	@Action("/meta/toAddRoute")
	public String addMetaProduct() {
		// 获取币种集合
		getCurrency();
		//获取组织集合
		return goAfter();
	}

	@Override
	@Action("/meta/saveRoute")
	public void save() {
		// TODO Auto-generated method stub
		saveMetaProduct();
	}

	@Override
	@Action("/meta/toEditRoute")
	public String toEdit() {
		doBefore();
		getCurrency();
		return goAfter();
	}
	


	public List<CodeItem> getSubProductTypeList(){
		return ProductUtil.getMetaRouteSubTypeList();
	}
}
