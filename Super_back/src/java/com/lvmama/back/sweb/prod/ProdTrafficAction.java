package com.lvmama.back.sweb.prod;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.ProdTraffic;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 交通产品添加
 * @author shihui
 *
 */
@Results({
	@Result(name="input",location="/WEB-INF/pages/back/prod/base/traffic_product.jsp")
})
public class ProdTrafficAction extends ProdProductAction<ProdTraffic>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 391084371933403333L;
	
	public ProdTrafficAction() {
		super(Constant.PRODUCT_TYPE.TRAFFIC);
	}

	@Override
	@Action("/prod/toAddTrafficProduct")
	public String goResult() {
		return goAfter();
	}

	@Override
	@Action(value="/prod/editTrafficProduct")
	public String goEdit() {
		if(!doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		initEditProduct();
		return goAfter();
	}

	@Override
	@Action("/prod/saveTraffictProduct")
	public void save() {
		// TODO Auto-generated method stub
		saveProduct();
	}

	public List<CodeItem> getSubProductTypeList(){
		return ProductUtil.getTrafficSubTypeList();
	}
}
