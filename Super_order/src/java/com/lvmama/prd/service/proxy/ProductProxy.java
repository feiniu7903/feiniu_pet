package com.lvmama.prd.service.proxy;

import java.beans.PropertyEditor;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.TimeRange;
import com.lvmama.comm.bee.po.prod.TimeRange.TimeRangePropertEditor;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProductService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;

public class ProductProxy implements ProductService {
	
	private MetaProductService metaProductService;
	protected TopicMessageProducer productMessageProducer;
	
	@Override
	public String saveOrUpdateMetaTimePrice(String advancedOpt, TimePrice timePriceBean, String operatorName) {
		// 检查属性
		if (!timePriceBean.isSpecifiedDateDuration()) {
			return"时间周期不可以为空";
		}
		if (timePriceBean.getBeginDate().after(timePriceBean.getEndDate())) {
			return"时间区间不正确";
		}
		Date today=DateUtil.getDayStart(new Date());
		if (timePriceBean.getBeginDate().before(today)) {
			return"修改时间范围最早开始时间为当前日期";
		}
		if(timePriceBean.getEndDate().after(DateUtils.addYears(today, 2))){
			return "修改时间范围最晚时间期限为两年";
		}
		
		if ("op3".equals(advancedOpt)) {// 修改全部属性
			// 如果此采购产品没有禁售
			if (timePriceBean.isClosed()) {
				long num = metaProductService.selectIsExistProdProduct(timePriceBean);
				if (num > 0) {
					return"此日此产品不可禁售";
				}
			}
		}
		try {
			if("op1".equals(advancedOpt) || "op3".equals(advancedOpt)) {
				MetaProduct metaProduct = metaProductService.getMetaProductByBranchId(timePriceBean.getMetaBranchId());
				//校验不定期产品的时间价格表
				if(metaProduct.IsAperiodic()) {
					ResultHandle handle = metaProductService.aperiodicTimePriceValidation(timePriceBean, timePriceBean.getMetaBranchId());
					if(handle.isFail()) {
						return handle.getMsg();
					}
					timePriceBean.setCancelStrategy(Constant.CANCEL_STRATEGY.MANUAL.name());//默认人工确认可退改
					timePriceBean.setAheadHour(-1440L);//-24小时
				}
			}
			if ("op1".equals(advancedOpt)) { // 修改价格
				metaProductService.updateTimePrice(timePriceBean, timePriceBean.getMetaBranchId(), operatorName);
			} else if ("op2".equals(advancedOpt)){// 修改库存
				metaProductService.updateStock(timePriceBean, timePriceBean.getMetaBranchId(), operatorName);
			} else if ("op3".equals(advancedOpt)) {// 修改全部属性
				metaProductService.saveTimePrice(timePriceBean, timePriceBean.getMetaBranchId(), operatorName);
			} else  if("op4".equals(advancedOpt)){//修改自动清库存小时数
				metaProductService.updateZeroStock(timePriceBean, timePriceBean.getMetaBranchId(), operatorName);
			} else if("op5".equals(advancedOpt)) { //修改最晚取消小时数
				metaProductService.updateHour(timePriceBean, timePriceBean.getMetaBranchId(), operatorName);
			}
			
			// 现在的消息直接向类别发送.
			TimeRange range=new TimeRange(timePriceBean.getBeginDate(),timePriceBean.getEndDate());
			PropertyEditor editor = new TimeRangePropertEditor();
			editor.setValue(range);			
			productMessageProducer.sendMsg(MessageFactory.newProductMetaPriceMessage(timePriceBean.getMetaBranchId(),editor.getAsText()));
		} catch (Exception e) {
			return e.getMessage();
		}
		return null;
	}

	public MetaProductService getMetaProductService() {
		return metaProductService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public TopicMessageProducer getProductMessageProducer() {
		return productMessageProducer;
	}

	public void setProductMessageProducer(TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}

}
