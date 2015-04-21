/**
 * 
 */
package com.lvmama.back.sweb.prod;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.LimitSaleTime;
import com.lvmama.comm.bee.service.LimitSaleTimeService;
import com.lvmama.comm.utils.json.JSONResult;

/**
 * 时间限制.
 * @author yangbin
 *
 */
@Results({
	@Result(name="input",location="/WEB-INF/pages/back/prod/limit_sale_time.jsp"),
	@Result(name="limt_sale_list",location="/WEB-INF/pages/back/prod/limt_sale_list.jsp")
})
public class LimitSaleTimeAction extends ProductAction{

	private List<LimitSaleTime> limitSaleTimeList;
	private LimitSaleTimeService limitSaleTimeService;
	private LimitSaleTime saleTime;
	private boolean all=false;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4182040047756059125L;

	@Override
	@Action(value="/prod/editSaleTime")
	public String goEdit() {
		if(!doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		limitSaleTimeList=limitSaleTimeService.queryLimitSaleTimeByproductId(productId);
		if(all){
			return goAfter();			
		}else{
			return "limt_sale_list";
		}
	}

	@Override
	@Action(value="/prod/saveSaleTime")
	public void save() {
		JSONResult result=new JSONResult(getResponse());
		if(saleTime==null){
			result.raise("对象为空.").output();
			return;
		}
		
		List<LimitSaleTime> list = limitSaleTimeService.queryByProductIdAndLimitTime(saleTime);
		if(list != null && list.size()>0){
			result.raise("该时间限制已经存在.").output();
			return;
		}
		
		limitSaleTimeService.saveLimitSaleTime(saleTime);		
		result.output();
	}
	
	/**
	 * 删除.
	 */
	@Action("/prod/deleteLimitSale")
	public void delete(){
		JSONResult result=new JSONResult(getResponse());
		if(saleTime==null){
			result.raise("对象为空.").output();
			return;
		}
		limitSaleTimeService.deleteByLimitSaleTimeId(saleTime.getLimitSaleTimeId());
		
		result.output();
	}

	/**
	 * @param limitSaleTimeService the limitSaleTimeService to set
	 */
	public void setLimitSaleTimeService(LimitSaleTimeService limitSaleTimeService) {
		this.limitSaleTimeService = limitSaleTimeService;
	}

	/**
	 * @return the limitSaleTimeList
	 */
	public List<LimitSaleTime> getLimitSaleTimeList() {
		return limitSaleTimeList;
	}

	/**
	 * @return the all
	 */
	public boolean isAll() {
		return all;
	}

	/**
	 * @param all the all to set
	 */
	public void setAll(boolean all) {
		this.all = all;
	}

	/**
	 * @return the saleTime
	 */
	public LimitSaleTime getSaleTime() {
		return saleTime;
	}

	/**
	 * @param saleTime the saleTime to set
	 */
	public void setSaleTime(LimitSaleTime saleTime) {
		this.saleTime = saleTime;
	}	
	
	
}
