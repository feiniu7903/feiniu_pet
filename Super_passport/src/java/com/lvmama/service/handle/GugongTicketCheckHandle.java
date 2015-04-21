package com.lvmama.service.handle;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.SupplierProductInfo;
import com.lvmama.comm.vo.SupplierProductInfo.Item;
import com.lvmama.passport.processor.impl.client.gugong.GugongCheckRemainNumResponse;
import com.lvmama.passport.processor.impl.client.gugong.GugongConstant;
import com.lvmama.passport.processor.impl.client.gugong.GugongHTTPUtil;
import com.lvmama.passport.processor.impl.client.gugong.GugongTimePrice;
import com.lvmama.service.CheckStockHandle;

public class GugongTicketCheckHandle implements CheckStockHandle {
	private static final Log log = LogFactory.getLog(GugongTicketCheckHandle.class);
	private MetaProductBranchService metaProductBranchService;
	@Override
	public List<Item> check(BuyInfo buyinfo,List<Item> list) {
		for (Item item : list) {
			MetaProductBranch metaBranch = metaProductBranchService.getMetaBranch(item.getMetaBranchId());
			try {
				log.info("GugongTicketCheckHandle visitTime:"+item.getVisitTime());
				GugongTimePrice gugongTimePrice = GugongHTTPUtil.getTimePrices(DateUtil.formatDate(item.getVisitTime(), "yyyy-MM-dd"));
				String productIdSupplier = metaBranch.getProductIdSupplier();
				log.info("GugongTicketCheckHandle productIdSupplier:"+productIdSupplier);
				String settlementPrice =String.valueOf(PriceUtil.convertToYuan(item.getSettlementPrice()));
				log.info("settlementPrice:"+settlementPrice);
				log.info("quantity:"+item.getQuantity());
				String certNo="";
				for(Person p:buyinfo.getPersonList()){
					if(StringUtils.equals(p.getPersonType(),Constant.ORD_PERSON_TYPE.CONTACT.name())){
						certNo=p.getCertNo();
						break;
					}
				}
				log.info("cardNo:"+certNo);
				// 价格检测
				if (productIdSupplier.equals(GugongConstant.fullprice)) {
					if (!StringUtils.equals(settlementPrice, gugongTimePrice.getFullprice())) {
						item.setStock(SupplierProductInfo.STOCK.LACK);
						log.info("全价票票价有变动");
						// 提示价格变动
						item.setLackReason("该游玩时间的价格由"+settlementPrice+"变为"+gugongTimePrice.getStudentprice()+"，暂时不能预定。");
					}
				}
				if (productIdSupplier.equals(GugongConstant.halfprice)) {
					if (!StringUtils.equals(settlementPrice, gugongTimePrice.getHalfprice())) {
						item.setStock(SupplierProductInfo.STOCK.LACK);
						log.info("半价票票价有变动");
						// 提示价格变动
						item.setLackReason("该游玩时间的价格由"+settlementPrice+"变为"+gugongTimePrice.getStudentprice()+"，暂时不能预定。");
					}
				}
				if (productIdSupplier.equals(GugongConstant.studentprice)) {
					if (!StringUtils.equals(settlementPrice, gugongTimePrice.getStudentprice())) {
						item.setStock(SupplierProductInfo.STOCK.LACK);
						log.info("学生票票价有变动");
						// 提示价格变动
						item.setLackReason("该游玩时间的价格由"+settlementPrice+"变为"+gugongTimePrice.getStudentprice()+"，暂时不能预定。");
					}
				}
				// 库存检测
				if (item.getQuantity()>gugongTimePrice.getTotalnumber()) {
					item.setStock(SupplierProductInfo.STOCK.LACK);
					log.info("库存不足，暂时不能预定。");
					// 提示库存不足
					item.setLackReason("库存不足，暂时不能预定。");
				}

				// 剩余订购数量检测
				GugongCheckRemainNumResponse remain = GugongHTTPUtil.getCheckRemainNum(DateFormatUtils.format(item.getVisitTime(), "yyyy-MM-dd"), certNo);
				if (remain != null) {
					if (remain.getResultcode() == 0) {
						if (item.getQuantity() > remain.getRemainnum()) {
							item.setStock(SupplierProductInfo.STOCK.LACK);
							log.info("同一证件号同一游玩日期限购5张！");
							// 提示大于订购数量
							item.setLackReason("该订单预定失败，同一证件号同一游玩日期最多限购5张！");
						}
					}
				}

			} catch (Exception e) {
				item.setStock(SupplierProductInfo.STOCK.LACK);
				item.setLackReason("下单异常，请稍后再试！");
				log.error("GugongTicketCheckHandle Exception:" + e.getMessage());
			}
		}
		return list;
	}

	public void setMetaProductBranchService(MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}
}
