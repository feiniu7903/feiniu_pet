/**
 * 
 */
package com.lvmama.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.service.SupplierStockCheckService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.SupplierProductInfo;

/**
 * @author yangbin
 *
 */
public class SupplierStockCheckServiceImpl implements SupplierStockCheckService {

	/* (non-Javadoc)
	 * @see com.lvmama.comm.bee.service.SupplierStockCheckService#checkStock(com.lvmama.comm.vo.SuplierProductInfo)
	 */
	@Override
	public ResultHandleT<SupplierProductInfo> checkStock(BuyInfo buyinfo,SupplierProductInfo info) {
		ResultHandleT<SupplierProductInfo> result = new ResultHandleT<SupplierProductInfo>();
		if(info.isEmpty()){
			result.setMsg("产品数据为空");
			return result;
		}
		
		for(SupplierProductInfo.HANDLE handle:info.getMap().keySet()){
			List<SupplierProductInfo.Item> list = info.getMap().get(handle);
			CheckStockHandle stockHandle = SpringBeanProxy.getBean(CheckStockHandle.class,getBeanName(handle));
			stockHandle.check(buyinfo,list);
			getResult(result,list);
		}
		result.setReturnContent(info);
		return result;
	}
	
	private void getResult(ResultHandleT<SupplierProductInfo> result,List<SupplierProductInfo.Item> list){
		if(result.isSuccess()){
			for(SupplierProductInfo.Item item:list){
				if(result.isSuccess()){
					if(item.getStock()==SupplierProductInfo.STOCK.LACK){
						if(StringUtils.isNotEmpty(item.getLackReason())){
							result.setMsg(item.getLackReason());
						}else{
							result.setMsg("库存不足");
						}
					}
				}
			}
		}
	}

	/**
	 * 得到bean名称，格式为handle+"CheckStockHandle"
	 * @param handle
	 * @return
	 */
	private String getBeanName(SupplierProductInfo.HANDLE handle){
		String name= handle.name().toLowerCase();//
		return name+"CheckStockHandle";
	}
}
