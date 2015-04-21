/**
 * 
 */
package com.lvmama.back.sweb.meta;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.meta.MetaProductHotel;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;

/**
 * @author yangbin
 *
 */
@Results({
	@Result(name="input",location="/WEB-INF/pages/back/meta/add_hotel.jsp")
})
public class MetaProductHotelAction extends MetaProductEditAction<MetaProductHotel> {

	public MetaProductHotelAction() {
		super(Constant.PRODUCT_TYPE.HOTEL.name());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2653518566850094386L;

	@Override
	@Action("/meta/toAddHotel")
	public String addMetaProduct() {
		return goAfter();
	}

	@Override
	@Action("/meta/saveHotel")
	public void save() {
		saveMetaProduct();		
	}

	@Override
	@Action("/meta/toEditHotel")
	public String toEdit() {
		doBefore();
		return goAfter();
	}

	public List<CodeItem> getSubProductTypeList(){
		return ProductUtil.getHotelSubTypeList();
	}
}
