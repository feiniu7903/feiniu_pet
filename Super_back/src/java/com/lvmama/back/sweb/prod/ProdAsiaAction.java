package com.lvmama.back.sweb.prod;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.bee.po.prod.ProdOther;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;

/**
 * 签证销售产品处理.

 */
@Results({
	@Result(name="input",location="/WEB-INF/pages/back/prod/base/visa_product.jsp")
})

public class ProdAsiaAction extends ProdProductAction<ProdOther>{
	private static final long serialVersionUID = 747825731246840468L;
	public ProdAsiaAction() {
		super(Constant.PRODUCT_TYPE.OTHER);
		addFields("subProductType");
	}
	@Override
	@Action(value="/prod/editAsiaProduct")
	public String goEdit() {
		if(!doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		initEditProduct();
		return goAfter();
	}

	@Override
	@Action("/prod/toAddAsiaProduct")
	public String goResult() {
		product.setSubProductType(Constant.SUB_PRODUCT_TYPE.VISA.name());
		return goAfter();
	}

	@Override
	@Action("/prod/saveAsiaProduct")
	public void save() {
		saveProduct();
	}
	@Override
	protected ResultHandle doSaveBefore() {
		ResultHandle handle=super.doSaveBefore();
		ProdOther other=(ProdOther)product;
		Integer count=checkTimePrice(product.getProductId(),other.getVisaSelfSign());
		if(count>0){
			handle.setMsg("是否自备签与销售时间价格表不符");
			return handle;
		}
		return handle;
	}
	
	public List<CodeItem> getRegionNamesList(){
		return ProductUtil.getregionNamesXLQT();
	}
}
