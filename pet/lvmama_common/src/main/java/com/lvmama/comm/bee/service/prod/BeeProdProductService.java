package com.lvmama.comm.bee.service.prod;

import java.util.List;

import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;



public interface BeeProdProductService extends ProductHeadQueryService {
	List<CalendarModel> selectSaleTimePriceByProductId(Long productId);
	List<CalendarModel> selectSaleTimePrice(Long prodBranchId);
}
