/**
 * 
 */
package com.lvmama.back.sweb.prod;

import com.lvmama.comm.bee.po.prod.ProdOther;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.util.List;

/**
 * 其他的产品基本信息
 * @author yangbin
 *
 */
@Results({
	@Result(name="input",location="/WEB-INF/pages/back/prod/base/other_product.jsp")
})
public class ProdOtherAction extends ProdProductAction<ProdOther> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6416122355592133491L;

	public ProdOtherAction() {
		super(Constant.PRODUCT_TYPE.OTHER);
	}

	@Override
	@Action(value="/prod/editOtherProduct")
	public String goEdit() {
		if(!doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		initEditProduct();
		return goAfter();
	}

	@Override
	@Action("/prod/toAddOtherProduct")
	public String goResult() {
		return goAfter();
	}

	@Override
	@Action("/prod/saveOtherProduct")
	public void save() {
		saveProduct();
	}

	public List<CodeItem> getSubProductTypeList(){
		return ProductUtil.getOtherSubTypeList();
	}
	
	public List<CodeItem> getRegionNamesList(){
		return ProductUtil.getregionNamesXLQT();
	}

    public List<CodeItem> getRouteSubProductTypeList() {
        return ProductUtil.getRouteSubTypeList();
    }
}
