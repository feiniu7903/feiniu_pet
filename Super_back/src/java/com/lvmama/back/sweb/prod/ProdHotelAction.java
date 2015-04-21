/**
 * 
 */
package com.lvmama.back.sweb.prod;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.ProdHotel;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;

/**
 * 酒店产品基本信息
 * @author yangbin
 *
 */
@Results({
	@Result(name="input",location="/WEB-INF/pages/back/prod/base/hotel_product.jsp")
})
public class ProdHotelAction extends ProdProductAction<ProdHotel>{

	
	
	
	public ProdHotelAction() {
		super(Constant.PRODUCT_TYPE.HOTEL);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 666585636375414936L;

	@Override
	@Action(value="/prod/editHotelProduct")
	public String goEdit() {
		if(!doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		initEditProduct();
		return goAfter();
	}

	@Override
	@Action("/prod/toAddHotelProduct")
	public String goResult() {
		return goAfter();
	}

	@Override
	@Action("/prod/saveHotelProduct")
	public void save() {
		// TODO Auto-generated method stub
		saveProduct();
	}
	
	

	/* (non-Javadoc)
	 * @see com.lvmama.back.sweb.prod.ProdProductAction#doSaveBefore()
	 */
	@Override
	protected ResultHandle doSaveBefore() {
		ResultHandle handle=super.doSaveBefore();
		if(Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name().equals(product.getSubProductType())){
			if(product.getDays()==null||product.getDays()<1){
				handle.setMsg("酒店套餐入住天数必须大于0");
				return handle;
			}
		}
		return handle;
	}

	public List<CodeItem> getSubProductTypeList(){
		return ProductUtil.getHotelSubTypeList();
	}
	
	public List<CodeItem> getRegionNamesList(){
		return ProductUtil.getregionNamesMPJD();
	}

}
