package com.lvmama.back.sweb.timeprice;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProductService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Map;

/**
 * 
 * @author yuzhibing#yuzhibing@lvmama.com
 */
@ParentPackage("json-default")
@Results({
        @Result(name = "toMetaTimePrice", location = "/WEB-INF/pages/back/calendar/meta_time_price.jsp"),
        @Result(name = "auditingShow", location = "/WEB-INF/pages/back/calendar/meta_time_price_auditing_show.jsp"),
        @Result(name = "metaTimePrice", location = "/WEB-INF/pages/back/calendar/meta_time.jsp")
})
public class MetaTimePriceAction extends AbstractTimePriceAction {
	private MetaProductService metaProductService;
	private MetaProductBranchService metaProductBranchService;
	private Long metaBranchId;
	private Long metaProductId;
	private CalendarModel calendarModel;
	private TimePrice timePrice;
	private MetaProduct metaProduct;
	private String advancedOpt;
	private boolean updateResourceConfirm;
	private ProductService productProxy;

	private String isTotalDecrease;
	
	@Action("/meta/saveMetaTimePrice")
	public void saveMetaTimePrice() {
		JSONResult result = new JSONResult();
		try {
			Assert.notNull(timePriceBean.getMetaBranchId(), "类别不存在");
			// 不为"只修改库存"和"修改自动清库存小时数"和"修改最晚取消小时数"时进行下列判断
			if (!"op2".equals(advancedOpt) && !"op4".equals(advancedOpt) && !"op5".equals(advancedOpt)) {
				if ((!timePriceBean.isClosed())) {
					// 结算价不设置为0
					if (hasSkipSetPrice()) {
						this.timePriceBean.setSettlementPriceF("0");
					} else {
						MetaProductBranch branch = metaProductBranchService.getMetaBranch(timePriceBean.getMetaBranchId());
						Assert.notNull(branch);
						metaProduct = metaProductService.getMetaProduct(branch.getMetaProductId());
						if (!Constant.SUB_PRODUCT_TYPE.VISA.name().equalsIgnoreCase(metaProduct.getSubProductType()) && timePriceBean.getSettlementPrice() < 1) {
							throw new Exception("结算价不可以为0");
						}
					}
				}
			}
			/**
			 * 是否增加库存
			 * <br>增加True，减少False，直接库存量null
			 */
			timePriceBean.setIsAddDayStock(null);
			if (timePriceBean.getDayStock()==0) {
				timePriceBean.setStockFlag("true");
				//Map map= new HashMap();
				//map.put("stokflag", "true" );
				//map.put("priceId", timePriceBean.getTimePriceId());
				//metaProductService.signSendEmail(map);
			}else {
				timePriceBean.setStockFlag("true");
			}
			String resultStr = productProxy.saveOrUpdateMetaTimePrice(advancedOpt, timePriceBean, this.getOperatorNameAndCheck());
			if(resultStr != null) {
				throw new Exception(resultStr);
			}
		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(getResponse());
	}



	@Action("/meta/toMetaTimePriceAuditingShow")
    public String toMetaTimePriceAuditingShow(){
        this.execute();
        return "auditingShow";
    }
	@Action("/meta/toMetaTimePrice")
	public String execute() {
		timePrice();
		return "toMetaTimePrice";
	}

	@Action("/meta/metaTime")
	public String timePrice() {
		MetaProductBranch branch = metaProductBranchService.getMetaBranch(metaBranchId);
		Assert.notNull(branch);
		isTotalDecrease = branch.getTotalDecrease();
		metaProductId = branch.getMetaProductId();
		metaProduct = metaProductService.getMetaProduct(branch.getMetaProductId());
		Assert.notNull(metaProduct);

		updateResourceConfirm = metaProduct.isPaymentToSupplier();

		Map<String, Object> map;
		if ("UP".equals(monthType)) {
			map = DateUtil.getBeginAndEndDateByDate(DateUtil.getTheMiddle(currPageDate, -1));
		} else if ("DOWN".equals(monthType)) {
			map = DateUtil.getBeginAndEndDateByDate(DateUtil.getTheMiddle(currPageDate, 1));
		} else {
			if (currPageDate == null) {
				currPageDate = new Date();
			}

			map = DateUtil.getBeginAndEndDateByDate(currPageDate);
		}
		Date beginDate = (Date) map.get("beginDate");
		Date endDate = (Date) map.get("endDate");
		currPageDate = (Date) (map.get("currPageDate"));
		// 查询采购产品的日期时间价格表
		calendarModel = metaProductService.getMetaTimePriceByMetaProductId(metaBranchId, beginDate, endDate);
		return "metaTimePrice";
	}

	/**
	 * @param metaProductBranchService
	 *            the metaProductBranchService to set
	 */
	public void setMetaProductBranchService(MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public Long getMetaBranchId() {
		return metaBranchId;
	}

	public void setMetaBranchId(Long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}

	public Long getMetaProductId() {
		return metaProductId;
	}

	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public CalendarModel getCalendarModel() {
		return calendarModel;
	}

	public TimePrice getTimePrice() {
		return timePrice;
	}

	public void setTimePrice(TimePrice timePrice) {
		this.timePrice = timePrice;
	}

	public MetaProduct getMetaProduct() {
		return metaProduct;
	}

	public boolean getIsTotalDecrease() {
		return StringUtils.equalsIgnoreCase("true", isTotalDecrease);
	}
	public void setMetaProduct(MetaProduct metaProduct) {
		this.metaProduct = metaProduct;
	}

	public void setAdvancedOpt(String advancedOpt) {
		this.advancedOpt = advancedOpt;
	}

	/**
	 * @param productMessageProducer
	 *            the productMessageProducer to set
	 */
	public void setProductMessageProducer(TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}

	/**
	 * @return the updateResourceConfirm
	 */
	public boolean isUpdateResourceConfirm() {
		return updateResourceConfirm;
	}

	public void setProductProxy(ProductService productProxy) {
		this.productProxy = productProxy;
	}

}
