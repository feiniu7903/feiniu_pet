/**
 * 
 */
package com.lvmama.back.sweb.meta;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.meta.MetaProductOther;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * @author yangbin
 *
 */
@Results({
	@Result(name="input",location="/WEB-INF/pages/back/meta/add_other.jsp")
})
public class MetaProductOtherAction extends MetaProductEditAction<MetaProductOther>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8154199204553801960L;

	public MetaProductOtherAction() {
		super(Constant.PRODUCT_TYPE.OTHER.name());
		// TODO Auto-generated constructor stub
	}

	@Override
	@Action("/meta/toAddOther")
	public String addMetaProduct() {
		// 获取币种集合
		getCurrency();
				
		return goAfter();
	}

	@Override
	@Action("/meta/saveOther")
	public void save() {
		// TODO Auto-generated method stub
		saveMetaProduct();
	}

	@Override
	@Action("/meta/toEditOther")
	public String toEdit() {
		doBefore();
		getCurrency();
		return goAfter();
	}

	public List<CodeItem> getSubProductTypeList(){
		return ProductUtil.getOtherSubTypeList(true);
	}

}
