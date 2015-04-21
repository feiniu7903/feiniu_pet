package com.lvmama.back.job;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;

public class PriceUpdateJob implements Runnable {
	
	private static final Log log = LogFactory.getLog(PriceUpdateJob.class);
	private ProdProductService prodProductService;
	private ProdProductBranchService prodProductBranchService;
	
	public void run(){
		if (Constant.getInstance().isJobRunnable()) {
			long begin = System.currentTimeMillis();
			updateAllProdBranch();//首先更新类别的价格
			updateAllProduct();
			log.info("Update product sell_price and market_price. spent :"+ (System.currentTimeMillis()-begin));
		}
	}
	
	private void updateAllProduct(){
		int page=1;
		while(true){
			Page<Long> pp = prodProductService.selectAllProductId(pageSize,page);
			if(CollectionUtils.isEmpty(pp.getItems())){
				break;
			}
			for (Long productId : pp.getItems()) {
				try{
					prodProductService.updatePriceByProductId(productId);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(pp.hasNext()){
				page++;				
			}else{
				break;
			}
		}
	}
	final long pageSize=800;
	//更新所有的类别销售价
	private void updateAllProdBranch(){
		long page=1;
		while(true){
			Page<Long> pp=prodProductBranchService.selectAllBranchId(pageSize,page);
			if(CollectionUtils.isEmpty(pp.getItems())){
				break;
			}
			for(Long prodBranchId:pp.getItems()){
				try{
					//新的销售价=销售价-最优惠的早定早惠
					prodProductBranchService.updatePriceByBranchId(prodBranchId);
				}catch(Exception ex){
					log.error(ex,ex);
					log.info("invalid number prodBranchId:"+prodBranchId);
				}
			}
			
			if(pp.hasNext()){
				page++;				
			}else{
				break;
			}
		}
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	/**
	 * @param prodProductBranchService the prodProductBranchService to set
	 */
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}
}
