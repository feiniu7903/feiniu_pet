/**
 * 
 */
package com.lvmama.back.sweb.prod;

import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import org.apache.struts2.convention.annotation.Action;

import java.util.List;


/**
 * 自由组合打包的产品.
 * 在自主打包的属性上加上了为true标记.
 * @author yangbin
 *
 */
public class ProdRouteGroupAction extends ProdRouteAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1525559446480712536L;

	
	
	public ProdRouteGroupAction() {
		super();
		// TODO Auto-generated constructor stub		
		saveProductUrl="saveRouteSelfPackProduct";
	}

	


	/* (non-Javadoc)
	 * @see com.lvmama.back.sweb.prod.ProdRouteAction#getSubProductTypeList()
	 */
	@Override
	public List<CodeItem> getSubProductTypeList() {
		// TODO Auto-generated method stub
		return ProductUtil.getRouteSPSubTypeList();
	}




	/* (non-Javadoc)
	 * @see com.lvmama.back.sweb.prod.ProdProductAction#doBefore()
	 */
	@Override
	public boolean doBefore() {
		// TODO Auto-generated method stub
		if(!super.doBefore()){
			return false;
		}
		if(!product.hasSelfPack()){
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.back.sweb.prod.ProdRouteAction#goEdit()
	 */
	@Override
	@Action(value = "/prod/editRouteSelfPackProduct")
	public String goEdit() {
		if(!doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		return super.goEdit();
	}




	/* (non-Javadoc)
	 * @see com.lvmama.back.sweb.prod.ProdRouteAction#goResult()
	 */
	@Override
	@Action("/prod/toAddRouteSelfPackProduct")
	public String goResult() {
		// TODO Auto-generated method stub
		product.setSelfPack("true");
		return super.goResult();
	}




	/* (non-Javadoc)
	 * @see com.lvmama.back.sweb.prod.ProdRouteAction#save()
	 */
	@Override
	@Action("/prod/saveRouteSelfPackProduct")
	public void save() {
		// TODO Auto-generated method stub
		super.save();
	}




	/* (non-Javadoc)
	 * @see com.lvmama.back.sweb.prod.ProdRouteAction#doSaveBefore()
	 */
	@Override
	protected ResultHandle doSaveBefore() {
		// TODO Auto-generated method stub
		product.setSelfPack("true");
		return super.doSaveBefore();
	}
	
	

	


}
