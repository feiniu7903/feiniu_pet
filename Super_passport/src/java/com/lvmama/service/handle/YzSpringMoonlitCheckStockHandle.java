package com.lvmama.service.handle;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.SupplierProductInfo;
import com.lvmama.comm.vo.SupplierProductInfo.Item;
import com.lvmama.passport.processor.impl.client.yzspringmoonlit.YzSpringMoonlitHelp;
import com.lvmama.passport.processor.impl.exception.TicketTypeNonexistentException;
import com.lvmama.service.CheckStockHandle;
/**
 * 扬州春江花月夜 票数校验
 * @author linkai
 *
 */
public class YzSpringMoonlitCheckStockHandle implements CheckStockHandle{
	
	private static final Log log = LogFactory.getLog(YzSpringMoonlitCheckStockHandle.class);
	private MetaProductBranchService metaProductBranchService;
	
	@Override
	public List<Item> check(BuyInfo buyinfo, List<Item> list) {
		log.info("YzSpringMoonlitCheckStockHandle check");
		// 获取帮助类
		YzSpringMoonlitHelp help = new YzSpringMoonlitHelp();
		for (Item item : list) {
			try {
				// select * from META_PRODUCT_BRANCH where META_BRANCH_ID=#metaBranchId#
				MetaProductBranch metaBranch = metaProductBranchService.getMetaBranch(item.getMetaBranchId());
				// 游玩时间
				String visitDate = DateUtil.formatDate(item.getVisitTime(), "yyyy-MM-dd");
				// 产品编号（票种编号）
				String productIdSupplier = metaBranch.getProductIdSupplier();
				// 产品类型（区域编号）
				String productTypeSupplier = metaBranch.getProductTypeSupplier();
				// 数量
				long quantity = item.getQuantity();
				// 票务查询接口，查询余票数
				Map<String, String> returnMap = help.queryTicket(visitDate, productIdSupplier, productTypeSupplier);
				// 判断是否请求成功
				String queryFlag = returnMap.get(YzSpringMoonlitHelp.QUERY_TICKET_FLAG_FIELD);
				if (queryFlag.equals(YzSpringMoonlitHelp.QUERY_TICKET_SUCCESS)) {
					// 获取余票数
					long leftSeat = Long.parseLong(returnMap.get("leftSeat"));
					// 检验票的数量是否够
					if (quantity > leftSeat) {
						item.setStock(SupplierProductInfo.STOCK.LACK);
						item.setLackReason("预定异常：库存不足");
					} else {
						item.setStock(SupplierProductInfo.STOCK.AMPLE);
					}
				} else {
					item.setStock(SupplierProductInfo.STOCK.LACK);
					item.setLackReason("预定异常：" + returnMap.get(YzSpringMoonlitHelp.QUERY_RETURN_INFO_FIELD));
				}
			} catch (TicketTypeNonexistentException e) {
				item.setStock(SupplierProductInfo.STOCK.LACK);
				item.setLackReason("您选择的票种不存在，请选择其他票种。 ");
				e.printStackTrace();
			} catch (Exception e) {
				item.setStock(SupplierProductInfo.STOCK.LACK);
				item.setLackReason("预定异常");
				e.printStackTrace();
			}
			
		}
		return list;
	}
	
	/*public List<Item> check2(BuyInfo buyinfo, List<Item> list) {
		log.info("YzSpringMoonlitCheckStockHandle check");
		// 获取帮助类
		YzSpringMoonlitHelp help = new YzSpringMoonlitHelp();
		for (Item item : list) {
			try {
				
				// select * from META_PRODUCT_BRANCH where META_BRANCH_ID=#metaBranchId#
				// MetaProductBranch metaBranch = metaProductBranchService.getMetaBranch(item.getMetaBranchId());
				MetaProductBranch metaBranch = new MetaProductBranch();
				metaBranch.setProductIdSupplier("1");
				metaBranch.setProductTypeSupplier("10");
				// 游玩时间
				String visitDate = DateUtil.formatDate(item.getVisitTime(), "yyyy-MM-dd");
				// 产品编号（票种编号）
				String productIdSupplier = metaBranch.getProductIdSupplier();
				// 产品类型（区域编号）
				String productTypeSupplier = metaBranch.getProductTypeSupplier();
				// 数量
				long quantity = item.getQuantity();
				// 票务查询接口，查询余票数
				Map<String, String> returnMap = help.queryTicket(visitDate, productIdSupplier, productTypeSupplier);
				// 判断是否请求成功
				String queryFlag = returnMap.get(YzSpringMoonlitHelp.QUERY_TICKET_FLAG_FIELD);
				if (queryFlag.equals(YzSpringMoonlitHelp.QUERY_TICKET_SUCCESS)) {
					// 获取余票数
					long leftSeat = Long.parseLong(returnMap.get("leftSeat"));
					// 检验票的数量是否够
					if (quantity > leftSeat) {
						item.setStock(SupplierProductInfo.STOCK.LACK);
						item.setLackReason("预定异常：库存不足");
					} else {
						item.setStock(SupplierProductInfo.STOCK.AMPLE);
					}
				} else {
					item.setStock(SupplierProductInfo.STOCK.LACK);
					item.setLackReason("预定异常：" + returnMap.get(YzSpringMoonlitHelp.QUERY_RETURN_INFO_FIELD));
				}
			} catch (TicketTypeNonexistentException e) {
				item.setStock(SupplierProductInfo.STOCK.LACK);
				item.setLackReason("您选择的票种不存在，请选择其他票种。 ");
				e.printStackTrace();
			} catch (Exception e) {
				item.setStock(SupplierProductInfo.STOCK.LACK);
				item.setLackReason("预定异常");
				e.printStackTrace();
			}
		}
		return list;
	}*/

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}
	
}
