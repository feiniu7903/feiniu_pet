package com.lvmama.prd.service;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaProductHotel;
import com.lvmama.comm.bee.po.meta.MetaProductOther;
import com.lvmama.comm.bee.po.meta.MetaProductRoute;
import com.lvmama.comm.bee.po.meta.MetaProductTicket;
import com.lvmama.comm.bee.po.meta.MetaProductTraffic;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.prd.dao.MetaProductBranchDAO;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;

public class MetaProductServiceImpl implements MetaProductService {
	private Log loger = LogFactory.getLog(this.getClass());
	
	private MetaProductDAO metaProductDAO;
	private MetaTimePriceDAO metaTimePriceDAO;
	private ComLogDAO comLogDAO;
	private MetaProductBranchDAO metaProductBranchDAO;
	private ProdProductBranchService prodProductBranchService;	
	private OrderItemMetaDAO orderItemMetaDAO;
	public List<MetaProduct> findMetaProduct(Map param) {
		return metaProductDAO.findMetaProduct(param);
	}

	public Long addMetaProduct(MetaProduct metaProduct, String operatorName) {
		Long id = metaProductDAO.insert(metaProduct);
		if (id != null) {
			comLogDAO.insert("META_PRODUCT", null, metaProduct.getMetaProductId(), operatorName, 
					Constant.COM_LOG_ORDER_EVENT.insertMetaProduct.name(), "创建采购产品", "创建名称为[ "+metaProduct.getProductName()+" ]的采购产品", 
					null);
		}
		return id;
	}

	/**
	 * 更新采购产品
	 */
	public void updateMetaProduct(MetaProduct metaProduct, String operatorName) {
		String str = this.generLogStr(metaProduct, operatorName);
		metaProductDAO.updateByPrimaryKey(metaProduct);		
		if (!"".equals(str)) {
			comLogDAO.insert("META_PRODUCT", null, metaProduct.getMetaProductId(), operatorName, Constant.COM_LOG_ORDER_EVENT.updateMetaProduct.name(), "更新采购产品", str, null);
		}
	}
	
	/**
	 * 使所有的采购产品的任务生效
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws Exception 
	 */
	public void updateEffectedTask(List<Map<String, Object>> changeList){
		MetaProduct metaBefore = this.getMetaProduct(((MetaProduct) changeList.get(0).get("metaProduct")).getMetaProductId());
		MetaProduct metaAfter = new MetaProduct();
		try {
			PropertyUtils.copyProperties(metaAfter, metaBefore);
		} catch (Exception e) {
			loger.error("updateEffectedTask PropertyUtils.copyProperties error ", e);
		}
		
		for (Map<String, Object> map : changeList) {
			MetaProduct metaProduct = (MetaProduct) map.get("metaProduct");
			String operatorName = map.get("operatorName").toString();
			
			metaAfter = createMetaProductAfter(metaAfter, metaBefore, metaProduct);
			
			String str = this.generLogStr(metaProduct, operatorName);
			if (!"".equals(str)) {
				comLogDAO.insert("META_PRODUCT", null, metaProduct.getMetaProductId(), operatorName, Constant.COM_LOG_ORDER_EVENT.updateMetaProduct.name(), "编辑采购产品", str, null);
			}
			
			
		}
		
		metaProductDAO.updateByPrimaryKey(metaAfter);
	}
	
	/**
	 * 处理多条任务覆盖性问题
	 * 
	 * @param metaAfter
	 * @param metaBefore
	 * @param param
	 * @return
	 */
	private MetaProduct createMetaProductAfter(MetaProduct metaAfter,MetaProduct metaBefore,MetaProduct param) {
		if (!LogViewUtil.logIsEmptyStr(param.getProductName()).equals(LogViewUtil.logIsEmptyStr(metaBefore.getProductName()))) {
			metaAfter.setProductName(param.getProductName());
		}
		if (!LogViewUtil.logIsEmptyStr(param.getBizCode()).equals(LogViewUtil.logIsEmptyStr(metaBefore.getBizCode()))) {
			metaAfter.setBizCode(param.getBizCode());
		}
		if (!LogViewUtil.logIsEmptyStr(param.getPaymentTarget()).equals(LogViewUtil.logIsEmptyStr(metaBefore.getPaymentTarget()))) {
			metaAfter.setPaymentTarget(param.getPaymentTarget());
		}
		if (!LogViewUtil.logIsEmptyStr(param.getValidDays() + "").equals(LogViewUtil.logIsEmptyStr(metaBefore.getValidDays() + ""))) {
			metaAfter.setValidDays(param.getValidDays());
		}
		
		if(!LogViewUtil.logIsEmptyStr(param.getIsResourceSendFax()).equals(LogViewUtil.logIsEmptyStr(metaBefore.getIsResourceSendFax()))){
			metaAfter.setIsResourceSendFax(param.getIsResourceSendFax());
		}
		if (!LogViewUtil.logIsEmptyStr(param.getTerminalContent()).equals(LogViewUtil.logIsEmptyStr(metaBefore.getTerminalContent()))) {
			metaAfter.setTerminalContent(param.getTerminalContent());
		}
		if (param.getManagerId()!=null && !(param.getManagerId()).equals(metaBefore.getManagerId())) {
			metaAfter.setManagerId(param.getManagerId());
		}
		
		
		// 线路//其他//门票//酒店
		if (param instanceof MetaProductTicket) {
			if (!LogViewUtil.logIsEmptyStr(param.getSubProductType()).equals(LogViewUtil.logIsEmptyStr(metaBefore.getSubProductType()))) {
				metaAfter.setSubProductType(param.getSubProductType());
				MetaProductTicket oldProductTicket=(MetaProductTicket) param;
				try {
					PropertyUtils.copyProperties(oldProductTicket, metaAfter);
				} catch (Exception e) {
					loger.error("createMetaProductAfter PropertyUtils.copyProperties MetaProductTicket metaAfter error ",e);
				}
				
				return oldProductTicket;
			}
		} else if (param instanceof MetaProductHotel) {
			if (!LogViewUtil.logIsEmptyStr(param.getSubProductType()).equals(LogViewUtil.logIsEmptyStr(metaBefore.getSubProductType()))) {
				metaAfter.setSubProductType(param.getSubProductType());
			}
		} else if (param instanceof MetaProductOther) {
			MetaProductOther oldProductOther = this.getMetaProductOtherByProductId(metaBefore.getMetaProductId());
			MetaProductOther newProductOther = (MetaProductOther) param;
			if (!LogViewUtil.logIsEmptyStr(newProductOther.getSubProductType()).equals(LogViewUtil.logIsEmptyStr(metaBefore.getSubProductType()))) {
				metaAfter.setSubProductType(newProductOther.getZhSubProductType());
			}
			if (!LogViewUtil.logIsEmptyStr(newProductOther.getInsuranceDay() + "").equals(LogViewUtil.logIsEmptyStr(oldProductOther.getInsuranceDay() + ""))) {
				try {
					oldProductOther.setInsuranceDay(newProductOther.getInsuranceDay());
					PropertyUtils.copyProperties(oldProductOther, metaAfter);
				} catch (Exception e) {
					loger.error("createMetaProductAfter PropertyUtils.copyProperties oldProductRoute metaAfter error ",e);
				}
				return oldProductOther;
			}
		}

		return metaAfter;
	}
	
	public String generLogStr(MetaProduct metaProduct, String operatorName) {
		StringBuffer strBuf = new StringBuffer();
		MetaProduct oldMetaProduct = this.getMetaProduct(metaProduct.getMetaProductId(),metaProduct.getProductType());
		if (!LogViewUtil.logIsEmptyStr(metaProduct.getProductName()).equals(LogViewUtil.logIsEmptyStr(oldMetaProduct.getProductName()))) {
			strBuf.append(LogViewUtil.logEditStr("采购产品名称", oldMetaProduct.getProductName(), metaProduct.getProductName()));
		}
		if (!LogViewUtil.logIsEmptyStr(metaProduct.getBizCode()).equals(LogViewUtil.logIsEmptyStr(oldMetaProduct.getBizCode()))) {
			strBuf.append(LogViewUtil.logEditStr("采购产品编号", oldMetaProduct.getBizCode(), metaProduct.getBizCode()));
		} 
		if (!LogViewUtil.logIsEmptyStr(metaProduct.getPaymentTarget()).equals(LogViewUtil.logIsEmptyStr(oldMetaProduct.getPaymentTarget()))) {
			strBuf.append(LogViewUtil.logEditStr("支付对象", oldMetaProduct.getZhPaymentTarget(), metaProduct.getZhPaymentTarget()));
		} 
		if (!LogViewUtil.logIsEmptyStr(metaProduct.getValidDays() + "").equals(LogViewUtil.logIsEmptyStr(oldMetaProduct.getValidDays() + ""))) {
			strBuf.append(LogViewUtil.logEditStr("电子通关码有效天数", oldMetaProduct.getValidDays() + "", metaProduct.getValidDays() + ""));
		}
	 
		if (metaProduct.getManagerId()!=null && !(metaProduct.getManagerId()).equals(oldMetaProduct.getManagerId())) {
			strBuf.append(LogViewUtil.logEditStr("采购经理ID", oldMetaProduct.getManagerId()==null ? "" : oldMetaProduct.getManagerId().toString(), metaProduct.getManagerId()==null ? "" : metaProduct.getManagerId().toString()));
		}
		if (metaProduct.getControlType() == null && oldMetaProduct.getControlType() != null) {
			strBuf.append(LogViewUtil.logEditStr("预控级别", ""));
		}
		if (metaProduct.getControlType() != null) {
			if (!metaProduct.getControlType().equals(oldMetaProduct.getControlType())) {
				strBuf.append(LogViewUtil.logEditStr("预控级别", Constant.PRODUCT_CONTROL_TYPE.getCnName(metaProduct.getControlType())));
			}
		}
		
		if (StringUtils.isBlank(metaProduct.getFilialeName())&& StringUtils.isNotBlank(metaProduct.getFilialeName())) {
			strBuf.append(LogViewUtil.logEditStr("采购主体", ""));
		}
		if (StringUtils.isNotBlank(metaProduct.getFilialeName())) {
			if (!metaProduct.getFilialeName().equals(oldMetaProduct.getFilialeName())) {
				strBuf.append(LogViewUtil.logEditStr("采购主体", metaProduct.getFilialeCnName()));
			}
		}
		
		if (!LogViewUtil.logIsEmptyStr(metaProduct.getIsResourceSendFax()).equals(LogViewUtil.logIsEmptyStr(oldMetaProduct.getIsResourceSendFax()))) {
			strBuf.append(LogViewUtil.logEditStr("资源审核发送传真", "true".equals(oldMetaProduct.getIsResourceSendFax()) ? "是" : "否", "true".equals(metaProduct.getIsResourceSendFax()) ? "是" : "否"));
		}
		
		// 线路//其他//门票//酒店
		if (metaProduct instanceof MetaProductTicket) {
			MetaProductTicket metaProductTicket = (MetaProductTicket)metaProduct;
			MetaProductTicket oldMetaProductTicket = (MetaProductTicket)oldMetaProduct;
			if (!LogViewUtil.logIsEmptyStr(metaProduct.getSubProductType()).equals(LogViewUtil.logIsEmptyStr(oldMetaProduct.getSubProductType()))) {
				strBuf.append(LogViewUtil.logEditStr("特征描述-门票类型", oldMetaProduct.getZhSubProductType(), metaProduct.getZhSubProductType()));
			}
			if (!LogViewUtil.logIsEmptyStr(metaProductTicket.getTodayOrderAble()).equals(LogViewUtil.logIsEmptyStr(oldMetaProductTicket.getTodayOrderAble()))) {
				strBuf.append(LogViewUtil.logEditStr("是否支持手机客户端当天预订", oldMetaProductTicket.getTodayOrderAble(), metaProductTicket.getTodayOrderAble()));
			}
			if (!LogViewUtil.logIsEmptyStr(metaProductTicket.getLastTicketTime()+"").equals(LogViewUtil.logIsEmptyStr(oldMetaProductTicket.getLastTicketTime()+""))) {
				strBuf.append(LogViewUtil.logEditStr("最短换票间隔小时数", oldMetaProductTicket.getLastTicketTime()+"", metaProductTicket.getLastTicketTime()+""));
			}
			if (!LogViewUtil.logIsEmptyStr(metaProductTicket.getLastPassTime()+"").equals(LogViewUtil.logIsEmptyStr(oldMetaProductTicket.getLastPassTime()+""))) {
				strBuf.append(LogViewUtil.logEditStr("最晚入园前多少小时数可售", oldMetaProductTicket.getLastPassTime()+"", metaProductTicket.getLastPassTime()+""));
			}
		} else if (metaProduct instanceof MetaProductHotel) {
			if (!LogViewUtil.logIsEmptyStr(metaProduct.getSubProductType()).equals(LogViewUtil.logIsEmptyStr(oldMetaProduct.getSubProductType()))) {
				strBuf.append(LogViewUtil.logEditStr("特征描述-子类型", oldMetaProduct.getZhSubProductType(), metaProduct.getZhSubProductType()));
			}
			if(Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name().equals(metaProduct.getSubProductType())){
				if (!LogViewUtil.logIsEmptyStr(((MetaProductHotel)metaProduct).getNights()+"").equals(LogViewUtil.logIsEmptyStr(((MetaProductHotel)oldMetaProduct).getNights()+""))) {
					strBuf.append(LogViewUtil.logEditStr("套餐晚数", ((MetaProductHotel)oldMetaProduct).getNights()+"", ((MetaProductHotel)metaProduct).getNights()+""));
				}
			}
			
		} else if (metaProduct instanceof MetaProductRoute) {
			MetaProductRoute oldProductRoute = this.getMetaProductRouteByProductId(oldMetaProduct.getMetaProductId());
			MetaProductRoute newProductRoute = (MetaProductRoute) metaProduct;
			 
			if (!LogViewUtil.logIsEmptyStr(newProductRoute.getSubProductType()).equals(LogViewUtil.logIsEmptyStr(oldMetaProduct.getSubProductType()))) {
				strBuf.append(LogViewUtil.logEditStr("特征描述-线路类型", oldProductRoute.getZhSubProductType(), newProductRoute.getZhSubProductType()));
			}
		} else if (metaProduct instanceof MetaProductOther) {
			MetaProductOther oldProductOther = this.getMetaProductOtherByProductId(oldMetaProduct.getMetaProductId());
			MetaProductOther newProductOther = (MetaProductOther) metaProduct;
			if (!LogViewUtil.logIsEmptyStr(newProductOther.getSubProductType()).equals(LogViewUtil.logIsEmptyStr(oldMetaProduct.getSubProductType()))) {
				strBuf.append(LogViewUtil.logEditStr("特征描述-子类型", oldProductOther.getZhSubProductType(), newProductOther.getZhSubProductType()));
			}
			if (!LogViewUtil.logIsEmptyStr(newProductOther.getInsuranceDay() + "").equals(LogViewUtil.logIsEmptyStr(oldProductOther.getInsuranceDay() + ""))) {
				strBuf.append(LogViewUtil.logEditStr("保险期限", oldProductOther.getInsuranceDay() + "", newProductOther.getInsuranceDay() + ""));
			}
		} else if (metaProduct instanceof MetaProductTraffic){
			MetaProductTraffic oldTraffic = (MetaProductTraffic)this.metaProductDAO.getMetaProduct(oldMetaProduct.getMetaProductId(),Constant.PRODUCT_TYPE.TRAFFIC.name());
			MetaProductTraffic newTraffic = (MetaProductTraffic) metaProduct;
			if (!LogViewUtil.logIsEmptyStr(newTraffic.getSubProductType()).equals(LogViewUtil.logIsEmptyStr(oldMetaProduct.getSubProductType()))) {
				strBuf.append(LogViewUtil.logEditStr("特征描述-子类型", oldTraffic.getZhSubProductType(), newTraffic.getZhSubProductType()));
			}
			if (!LogViewUtil.logIsEmptyStr(newTraffic.getDirection()).equals(LogViewUtil.logIsEmptyStr(oldTraffic.getDirection()))) {
				strBuf.append(LogViewUtil.logEditStr("单程/往返", oldTraffic.getZhDirection(), newTraffic.getZhDirection()));
			}
			if (!LogViewUtil.logIsEmptyStr(newTraffic.getGoFlight() + "").equals(LogViewUtil.logIsEmptyStr(oldTraffic.getGoFlight() + ""))) {
				strBuf.append(LogViewUtil.logEditStr("去程航班ID", oldTraffic.getGoFlight() + "", newTraffic.getGoFlight() + ""));
			}
			if (!LogViewUtil.logIsEmptyStr(newTraffic.getBackFlight() + "").equals(LogViewUtil.logIsEmptyStr(oldTraffic.getBackFlight() + ""))) {
				strBuf.append(LogViewUtil.logEditStr("回程航班ID", oldTraffic.getBackFlight() + "", newTraffic.getBackFlight() + ""));
			}
			if (!LogViewUtil.logIsEmptyStr(newTraffic.getDays() + "").equals(LogViewUtil.logIsEmptyStr(oldTraffic.getDays() + ""))) {
				strBuf.append(LogViewUtil.logEditStr("行程天数", oldTraffic.getDays() + "", newTraffic.getDays() + ""));
			}
		}
		return strBuf.toString();
	}


	public MetaProductRoute getMetaProductRouteByProductId(Long productId) {
		return this.metaProductDAO.getMetaMetaProductRouteByProductId(productId);
	}

	public MetaProductOther getMetaProductOtherByProductId(Long productId) {
		return this.metaProductDAO.getMetaProductOtherByProductId(productId);
	}

	public MetaProduct getMetaProduct(Long metaProductId, String type) {
		return this.metaProductDAO.getMetaProduct(metaProductId, type);
	}
	
	public MetaProduct getMetaProductByMetaProductId(Long metaProductId){
		MetaProduct metaProduct = this.metaProductDAO.getMetaProductByPk(metaProductId);
		return this.metaProductDAO.getMetaProduct(metaProductId, metaProduct.getProductType());
	}
	public CalendarModel getMetaTimePriceByMetaProductId(Long metaBranchId, Date beginTime, Date endTime) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("beginDate", beginTime);
		param.put("endDate", endTime);
		param.put("metaBranchId", metaBranchId);
		List<TimePrice> metaTimePriceList = metaTimePriceDAO.getMetaTimePriceByMetaBranchId(param);

		MetaProductBranch metaProductBranch = metaProductBranchDAO.selectBrachByPrimaryKey(metaBranchId);
		if (metaProductBranch.isTotalDecrease()) {
			for (TimePrice timePrice : metaTimePriceList) {
				if (metaProductBranch.getTotalStock() != null) {
					timePrice.setDayStock(metaProductBranch.getTotalStock());
				}
			}
		}
		CalendarModel calendarModel = new CalendarModel();
		calendarModel.setTimePrice(metaTimePriceList,beginTime);
		return calendarModel;
	}

	/**
	 * 保存修改TimePrice的日志
	 * @param bean
	 * @param operatorName
	 */
	private void saveTimePriceLogStr(TimePrice timePrice, String operatorName) {
		TimePrice bean = timePrice;
		TimePrice oldBean = getOldTimePrice(bean);
		
		if(bean.isClosed()) {
			comLogDAO.insert(null, timePrice.getMetaBranchId(), bean.getTimePriceId(), operatorName, Constant.COM_LOG_ORDER_EVENT.updateMetaTimePrice.name(), "编辑采购时间价格表",createTimePriceLogStr(bean, operatorName), "META_TIME_PRICE");
		} else if (bean.getBeginDate() != null) {
			if (bean != null && oldBean == null) {
				comLogDAO.insert(null, timePrice.getMetaBranchId(), bean.getMetaBranchId(), operatorName, Constant.COM_LOG_ORDER_EVENT.insertMetaTimePrice.name(), "创建采购时间价格表", createTimePriceLogStr(bean, operatorName), "META_TIME_PRICE");
			} else {
				comLogDAO.insert(null, timePrice.getMetaBranchId(), bean.getMetaBranchId(), operatorName, Constant.COM_LOG_ORDER_EVENT.updateMetaTimePrice.name(), "编辑采购时间价格表", createTimePriceLogStr(bean, operatorName), "META_TIME_PRICE");
			}
		}
	}
	
	/**
	 * 获得原来老的TimePrice
	 * 
	 * @param bean
	 * @return
	 */
	private TimePrice getOldTimePrice(TimePrice bean) {
		TimePrice oldBean = new TimePrice();
		if (bean.getBeginDate() != null) {
			Long theZero = bean.getBeginDate().getTime();
			Date specDate = new Date(theZero);
			oldBean = metaTimePriceDAO.getMetaTimePriceByIdAndDate(bean.getMetaBranchId(), specDate);
		}
		return oldBean;
	}
	
	/**
	 * 判断是否存在旧的时间价格表.
	 * @param productId
	 * @param date
	 * @return
	 */
	public boolean hasOldTimePrice(Long productId,Date date){
		return metaTimePriceDAO.getMetaTimePriceByIdAndDate(productId, date)!=null;
	}
	
	/**
	 * 创建TimePrice的log日子的描述详情str
	 * 
	 * @param bean
	 * @param isClosed
	 * @param operatorName
	 * @return
	 */
	private String createTimePriceLogStr(TimePrice timePrice, String operatorName) {
		TimePrice bean = timePrice;
		String str = "";
		String formart = "yyyy-MM-dd";
		if (bean.isClosed()) {
			str = operatorName + "将" + DateUtil.getDateTime(formart, bean.getBeginDate()) + "与" + DateUtil.getDateTime(formart, bean.getEndDate())+ "时间内采购产品设置为禁售";
			return str;
		}
		
		StringBuffer strBuf = new StringBuffer();
		TimePrice oldBean = getOldTimePrice(bean);
		
		if (bean != null && oldBean == null) {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			if (bean.getBeginDate() != null) {
				strBuf.append(LogViewUtil.logEditStr("时间周期", "", f.format(bean.getBeginDate()) + "," + f.format(bean.getEndDate())));
			}
			if (bean.getWeekOpen() != null) {
				String monDay = "true".equals(bean.getMonday()) ? "星期一" : "";
				String tuesday = "true".equals(bean.getTuesday()) ? "星期二" : "";
				String wednesday = "true".equals(bean.getWednesday()) ? "星期三" : "";
				String thursday = "true".equals(bean.getThursday()) ? "星期四" : "";
				String friday = "true".equals(bean.getFriday()) ? "星期五" : "";
				String saturday = "true".equals(bean.getSaturday()) ? "星期六" : "";
				String sunday = "true".equals(bean.getSunday()) ? "星期天" : "";
				strBuf.append(LogViewUtil.logNewStrByColumnName("按星期 ", monDay + " " + tuesday + " " + wednesday + " " + thursday + " " + friday + " " + saturday + " " + sunday + " "));
			}
			if (bean.getMarketPriceF() != 0) {
				strBuf.append(LogViewUtil.logNewStrByColumnName("门市价", bean.getMarketPriceF() + ""));
			}
			if (null != bean.getSettlementPriceF() && !bean.getSettlementPriceF().equals("0")) {
				strBuf.append(LogViewUtil.logNewStrByColumnName("结算价", bean.getSettlementPriceF() + ""));
			}
			if (bean.getDayStock() != 0) {
				strBuf.append(LogViewUtil.logNewStrByColumnName("是否限量", bean.getDayStock() + ""));
			}
			strBuf.append(LogViewUtil.logNewStrByColumnName("资源需确认",bean.getResourceConfirm()+""));
			strBuf.append(LogViewUtil.logNewStrByColumnName("是否可超卖",bean.getOverSale()+""));
			strBuf.append(LogViewUtil.logNewStrByColumnName("库存",bean.getDayStockStr()+""));
			if(bean.getSuggestPrice()!=null){
				strBuf.append(LogViewUtil.logNewStrByColumnName("建议价", bean.getSuggestPriceF()+""));
			}
			strBuf.append(LogViewUtil.logNewStrByColumnName("网站提前预定小时数", bean.getAheadHourFloat() + ""));
			strBuf.append(LogViewUtil.logNewStrByColumnName("退改策略", bean.getCancelStrategy()));
			strBuf.append(LogViewUtil.logNewStrByColumnName("网站最晚取消小时数", bean.getCancelHourFloat() + ""));
			if(StringUtils.isNotEmpty(bean.getEarliestUseTime())) {
				strBuf.append(LogViewUtil.logNewStrByColumnName("最早换票/使用时间", bean.getEarliestUseTime()));
			}
			if(StringUtils.isNotEmpty(bean.getLatestUseTime())) {
				strBuf.append(LogViewUtil.logNewStrByColumnName("最晚换票/使用时间", bean.getLatestUseTime()));
			}
			if (!"".equals(strBuf.toString())) {
				str = strBuf.toString();
			}
		} else {
//			Map<String, Object> param = new HashMap<String, Object>();
//			param.put("metaBranchId", bean.getMetaBranchId());
//			List<TimePrice> list = this.metaTimePriceDAO.getMetaTimePriceByBranchIdAsc(param);
//			if (list.size() > 0) {
//				TimePrice beginTime = (TimePrice) list.get(0);
//				TimePrice endTime = (TimePrice) list.get(list.size() - 1);
//				oldBean.setBeginDate(beginTime.getSpecDate());
//				oldBean.setEndDate(endTime.getSpecDate());
//			}
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			strBuf.append("时间周期"+ f.format(bean.getBeginDate()) + "," + f.format(bean.getEndDate()));
			
			if (!LogViewUtil.logIsEmptyStr(bean.getWeekOpen() + "").equals(LogViewUtil.logIsEmptyStr(oldBean.getWeekOpen() + ""))) {
				String monDay = "true".equals(bean.getMonday()) ? "星期一" : "";
				String tuesday = "true".equals(bean.getTuesday()) ? "星期二" : "";
				String wednesday = "true".equals(bean.getWednesday()) ? "星期三" : "";
				String thursday = "true".equals(bean.getThursday()) ? "星期四" : "";
				String friday = "true".equals(bean.getFriday()) ? "星期五" : "";
				String saturday = "true".equals(bean.getSaturday()) ? "星期六" : "";
				String sunday = "true".equals(bean.getSunday()) ? "星期天" : "";
				strBuf.append(LogViewUtil.logEditStr("按星期 ", monDay + " " + tuesday + " " + wednesday + " " + thursday + " " + friday + " " + saturday + " " + sunday + " "));
			}

			if (bean.getMarketPriceF() != 0 && !LogViewUtil.logIsEmptyStr(bean.getMarketPriceF() + "").equals(LogViewUtil.logIsEmptyStr(oldBean.getMarketPriceF() + ""))) {
				strBuf.append(LogViewUtil.logEditStr("门市价", bean.getMarketPriceF() + ""));
			}
			if (null != bean.getSettlementPriceF() && !bean.getSettlementPriceF().equals("0")
					&& !LogViewUtil.logIsEmptyStr(bean.getSettlementPriceF() + "").equals(LogViewUtil.logIsEmptyStr(oldBean.getSettlementPriceF() + ""))) {
				strBuf.append(LogViewUtil.logEditStr("结算价", bean.getSettlementPriceF() + ""));
			}
			if (bean.getDayStock() != 0 && !LogViewUtil.logIsEmptyStr(bean.getDayStock() + "").equals(LogViewUtil.logIsEmptyStr(oldBean.getDayStock() + ""))) {
				strBuf.append(LogViewUtil.logEditStr("是否限量", bean.getDayStock() + ""));
			}
			if (bean.getSuggestPrice() != null && !LogViewUtil.logIsEmptyStr(bean.getSuggestPrice() + "").equals(LogViewUtil.logIsEmptyStr(oldBean.getSuggestPrice() + ""))) {
				strBuf.append(LogViewUtil.logEditStr("建议价", bean.getSuggestPriceF() + ""));
			}
			strBuf.append(LogViewUtil.logNewStrByColumnName("资源需确认",bean.getResourceConfirm()+""));
			strBuf.append(LogViewUtil.logNewStrByColumnName("是否可超卖",bean.getOverSale()+""));
			strBuf.append(LogViewUtil.logNewStrByColumnName("库存",bean.getDayStockStr()+""));
			strBuf.append(LogViewUtil.logNewStrByColumnName("网站提前预定小时数", bean.getAheadHourFloat() + ""));
			strBuf.append(LogViewUtil.logNewStrByColumnName("退改策略", bean.getCancelStrategy()));
			strBuf.append(LogViewUtil.logNewStrByColumnName("网站最晚取消小时数", bean.getCancelHourFloat() + ""));
			if(StringUtils.isNotEmpty(oldBean.getEarliestUseTime()) || StringUtils.isNotEmpty(bean.getEarliestUseTime())) {
				strBuf.append(LogViewUtil.logNewStrByColumnName("最早换票/使用时间", bean.getEarliestUseTime()));
			}
			if(StringUtils.isNotEmpty(oldBean.getLatestUseTime()) || StringUtils.isNotEmpty(bean.getLatestUseTime())) {
				strBuf.append(LogViewUtil.logNewStrByColumnName("最晚换票/使用时间", bean.getLatestUseTime()));
			}
			if (!"".equals(strBuf.toString())) {
				str = strBuf.toString();
			}
		}
		return str;
	}
	/**
	 * 只修改价格
	 * @param bean
	 * @param metaBranchId
	 * @param operatorName
	 */
	public void updateTimePrice(TimePrice bean, Long metaBranchId, String operatorName) {
		if (bean.isClosed()) {
			return;
		}
		Long theZero = bean.getBeginDate().getTime();
		Calendar cal = Calendar.getInstance();
		while (theZero <= bean.getEndDate().getTime()) {
			cal.setTimeInMillis(theZero);
			if (bean.isSpecday(cal.get(Calendar.DAY_OF_WEEK))) {
				Date specDate = new Date(theZero);
				TimePrice timePrice = metaTimePriceDAO.getMetaTimePriceByIdAndDate(metaBranchId, specDate);				
				if (timePrice != null) {
					timePrice.setSettlementPrice(bean.getSettlementPrice());
					timePrice.setMarketPrice(bean.getMarketPrice());
					timePrice.setSuggestPrice(bean.getSuggestPrice());
					metaTimePriceDAO.update(timePrice);
				}
			}
			theZero += 86400000;
		}
		saveTimePriceLogStr(bean, operatorName);//保存日子信息
	}
	
	public void updateStock(TimePrice bean, Long metaBranchId, String operatorName) {
		if (bean.isClosed()) {
			return;
		}
		Long theZero = bean.getBeginDate().getTime();
		Calendar cal = Calendar.getInstance();
		while (theZero <= bean.getEndDate().getTime()) {
			cal.setTimeInMillis(theZero);
			if (bean.isSpecday(cal.get(Calendar.DAY_OF_WEEK))) {
				Date specDate = new Date(theZero);
				TimePrice timePrice = metaTimePriceDAO.getMetaTimePriceByIdAndDate(metaBranchId, specDate);				
				if (timePrice != null) {
					fillTimePriceDayStock(timePrice, bean.getIsAddDayStock(), bean.getDayStock());
					
					//早餐份数
					if(bean.getBreakfastCount() != null) {
						timePrice.setBreakfastCount(bean.getBreakfastCount());
					}
					if(bean.getResourceConfirm() != null && !"".equals(bean.getResourceConfirm())) {
						timePrice.setResourceConfirm(bean.getResourceConfirm());
					}
					timePrice.setOverSale(bean.getOverSale());
					timePrice.setStockFlag(bean.getStockFlag());
					metaTimePriceDAO.update(timePrice);
				}
			}
			theZero += 86400000;
		}
		saveTimePriceLogStr(bean, operatorName);//保存日子信息
	}

	/**
	 * 日剩余库存与总库存的设置
	 * @author: ranlongfei 2013-1-7 下午1:45:19
	 * @param oldTime
	 * @param isAddDayStock 增减或原值
	 * @param stock
	 */
	private void fillTimePriceDayStock(TimePrice oldTime, String isAddDayStock, Long stock) {
		if(oldTime.getDayStock() < 0) {
			oldTime.setDayStock(0);
		}
		if(oldTime.getTotalDayStock() < 0) {
			oldTime.setTotalDayStock(0L);
		}
		long stockAdd = 0;
		long totalStockAdd = 0;
		if("true".equals(isAddDayStock)) {
			//增加库存
			stockAdd = oldTime.getDayStock() + stock;
			totalStockAdd = oldTime.getTotalDayStock() + stock;
		} else if("false".equals(isAddDayStock)) {
			//减少库存
			stockAdd = oldTime.getDayStock() - stock;
			totalStockAdd = oldTime.getTotalDayStock() - stock;
			if(stockAdd < 0) {
				throw new RuntimeException("库存不足，无法减少");
			}
		} else {
			//后台的直接库存量
			//总库存数量
			if (stock == -1) {
				stockAdd = -1;
				totalStockAdd = -1;
			} else {
				// 日库存变化的量值（原来的日库存-现填写的日库存）
				stockAdd = stock;
				//总库存=总库存-日库存变化的量值
				totalStockAdd = oldTime.getTotalDayStock() - (oldTime.getDayStock() - stock);
			}
		}
		oldTime.setDayStock(stockAdd);
		oldTime.setTotalDayStock(totalStockAdd);
	}
	
	public void insertTimePrice(TimePrice bean) {
		metaTimePriceDAO.insert(bean);
	}
	
	
	public void updateTimePrice(TimePrice bean, TimePrice timePrice) {
		initPrice(bean,timePrice);
		metaTimePriceDAO.update(timePrice);
	}
	
	private void initPrice(TimePrice bean, TimePrice timePrice){
		//总库存数量
		if (bean.getDayStock() == -1) {
			timePrice.setTotalDayStock(bean.getDayStock());
		} else {
			// 日库存变化的量值（原来的日库存-现填写的日库存）
			long var = timePrice.getDayStock() - bean.getDayStock();
			//总库存=总库存-日库存变化的量值
			timePrice.setTotalDayStock(timePrice.getTotalDayStock() - var);
		}
		timePrice.setSettlementPrice(bean.getSettlementPrice());
		timePrice.setMarketPrice(bean.getMarketPrice());
		timePrice.setDayStock(bean.getDayStock());
		timePrice.setOverSale(bean.getOverSale());
		timePrice.setResourceConfirm(bean.getResourceConfirm());
		timePrice.setZeroStockHour(bean.getZeroStockHour());
		//早餐份数
		timePrice.setBreakfastCount(bean.getBreakfastCount());
		//建议售价
		timePrice.setSuggestPrice(bean.getSuggestPrice());

		timePrice.setAheadHour(bean.getAheadHour());
		timePrice.setCancelHour(bean.getCancelHour());
		metaTimePriceDAO.update(timePrice);

	}
	
	public void updateDynamicTimePrice(TimePrice timePrice) {
		metaTimePriceDAO.updateDynamic(timePrice);
	}
	
	public void saveTimePrice(TimePrice bean, Long metaBranchId, String operatorName) {
		if (bean.isClosed()) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("metaBranchId", metaBranchId);
			param.put("beginDate", bean.getBeginDate());
			param.put("endDate", bean.getEndDate());
			metaTimePriceDAO.deleteByBeginDateAndEndDate(param);
		} else {
			Long theZero = bean.getBeginDate().getTime();
			Calendar cal = Calendar.getInstance();
			while (theZero <= bean.getEndDate().getTime()) {
				cal.setTimeInMillis(theZero);
				if (bean.isSpecday(cal.get(Calendar.DAY_OF_WEEK))) {
					Date specDate = new Date(theZero);
					TimePrice timePrice = metaTimePriceDAO.getMetaTimePriceByIdAndDate(metaBranchId, specDate);				
					fillTimePriceDefault(bean, timePrice);
					if (timePrice == null) {
						TimePrice metaTimePrice = new TimePrice();
						metaTimePrice.setSpecDate(specDate);
						metaTimePrice.setMetaBranchId(bean.getMetaBranchId());
						metaTimePrice.setProductId(bean.getProductId());
						metaTimePrice.setSettlementPrice(bean.getSettlementPrice());
						metaTimePrice.setMarketPrice(bean.getMarketPrice());
						metaTimePrice.setDayStock(bean.getDayStock());
						metaTimePrice.setOverSale(bean.getOverSale());
						metaTimePrice.setZeroStockHour(bean.getZeroStockHour());
						metaTimePrice.setResourceConfirm(bean.getResourceConfirm());
						//早餐份数
						metaTimePrice.setBreakfastCount(bean.getBreakfastCount());
						//建议售价
						metaTimePrice.setSuggestPrice(bean.getSuggestPrice());
						//总库存数量
						metaTimePrice.setTotalDayStock(bean.getDayStock());
						metaTimePrice.setAheadHour(bean.getAheadHour());
						metaTimePrice.setCancelHour(bean.getCancelHour());
						metaTimePrice.setEarliestUseTime(bean.getEarliestUseTime());
						metaTimePrice.setLatestUseTime(bean.getLatestUseTime());
						metaTimePrice.setCancelStrategy(bean.getCancelStrategy());
						metaTimePrice.setStockFlag(bean.getStockFlag());
						metaTimePriceDAO.insert(metaTimePrice);
					} else {
						//总库存数量
						if (bean.getDayStock() == -1) {
							timePrice.setTotalDayStock(bean.getDayStock());
						} else {
							// 日库存变化的量值（原来的日库存-现填写的日库存）
							long var = timePrice.getDayStock() - bean.getDayStock();
							//总库存=总库存-日库存变化的量值
							timePrice.setTotalDayStock(timePrice.getTotalDayStock() - var);
						}
						timePrice.setSettlementPrice(bean.getSettlementPrice());
						timePrice.setMarketPrice(bean.getMarketPrice());
						timePrice.setDayStock(bean.getDayStock());
						timePrice.setOverSale(bean.getOverSale());
						timePrice.setResourceConfirm(bean.getResourceConfirm());
						timePrice.setZeroStockHour(bean.getZeroStockHour());
						//早餐份数
						timePrice.setBreakfastCount(bean.getBreakfastCount());
						//建议售价
						timePrice.setSuggestPrice(bean.getSuggestPrice());
						timePrice.setAheadHour(bean.getAheadHour());
						timePrice.setCancelHour(bean.getCancelHour());
						timePrice.setEarliestUseTime(bean.getEarliestUseTime());
						timePrice.setLatestUseTime(bean.getLatestUseTime());
						timePrice.setCancelStrategy(bean.getCancelStrategy());
						timePrice.setStockFlag(bean.getStockFlag());
						metaTimePriceDAO.update(timePrice);
					}
				}
				theZero += 86400000;
			}
		}
		
		saveTimePriceLogStr(bean, operatorName);//保存日子信息
	}
	
	/**
	 * 不定期产品时间价格表校验
	 * */
	public ResultHandle aperiodicTimePriceValidation(TimePrice bean, Long metaBranchId) {
		ResultHandle handle = new ResultHandle();
		//取最晚的时间价格
		TimePrice maxTimePrice = metaTimePriceDAO.getMinOrMaxTimePriceByMetaBranchId(metaBranchId, true);
		if(maxTimePrice != null) {
			//最早的时间价格
			TimePrice minTimePrice = metaTimePriceDAO.getMinOrMaxTimePriceByMetaBranchId(metaBranchId, false);
			Date minSpecDate = DateUtil.getDayStart(minTimePrice.getSpecDate());
			Date maxSpecDate = DateUtil.getDayStart(maxTimePrice.getSpecDate());
			Date currentDate = DateUtil.getDayStart(new Date());
			Date beanBeginDate = DateUtil.getDayStart(bean.getBeginDate());
			Date beanEndDate = DateUtil.getDayStart(bean.getEndDate());
			//当前时间在已存在的时间价格结束期前
			if(!currentDate.after(maxSpecDate)) {
				if(!beanBeginDate.after(minSpecDate) && !beanEndDate.before(maxSpecDate)) {} else {
					if(!bean.isClosed()) {
						if(bean.getMarketPrice() != maxTimePrice.getMarketPrice()) {
							handle.setMsg("已存在的时间价格区间还未过期,本次修改的市场价必须跟已存在的时间价格区间的市场价一致");
							return handle;
						}
					}
				}
			}
		}
		return handle;
	}

	/**
	 * 填写默认值：默认无限库存、默认需要审核、默认不能超卖、默认为0早餐
	 * <br>或者原值，不改变
	 * @author: ranlongfei 2013-1-7 下午2:38:02
	 * @param newPrice
	 * @param oldPrice
	 */
	private void fillTimePriceDefault(TimePrice newPrice, TimePrice oldPrice) {
		if (oldPrice == null) {
			// 默认无限库存
			if(newPrice.getDayStock() == -2) {
				newPrice.setDayStock(-1);
			}
			//默认需要审核
			if(newPrice.getResourceConfirm() == null) {
				newPrice.setResourceConfirm("true");
			}
			//默认不能超卖
			if(newPrice.getOverSale() == null) {
				newPrice.setOverSale("false");
			}
			//默认为0早餐
			if(newPrice.getBreakfastCount() == null) {
				newPrice.setBreakfastCount(0L);
			}
		} else {
			// 默认无限库存
			if(newPrice.getDayStock() == -2) {
				newPrice.setDayStock(oldPrice.getDayStock());
			}
			//默认需要审核
			if(newPrice.getResourceConfirm() == null) {
				newPrice.setResourceConfirm(oldPrice.getResourceConfirm());
			}
			//默认不能超卖
			if(newPrice.getOverSale() == null) {
				newPrice.setOverSale(oldPrice.getOverSale());
			}
			//默认为0早餐
			if(newPrice.getBreakfastCount() == null) {
				newPrice.setBreakfastCount(oldPrice.getBreakfastCount());
			}
		}
	}

	public Long selectIsExistProdProduct(TimePrice timePriceBean) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("metaBranchId", timePriceBean.getMetaBranchId());
		params.put("beginDate", timePriceBean.getBeginDate());
		params.put("endDate", timePriceBean.getEndDate());
		return metaTimePriceDAO.selectIsExistProdProduct(params);
	}
	/**
	 * 删除指定时间段的时间价格表
	 * @param param
	 * @author FangWeiQuan
	 * 2013-5-27 15:15:02
	 */
	public void deleteByBeginDateAndEndDate(Map<String,Object> param){
		metaTimePriceDAO.deleteByBeginDateAndEndDate(param);
	}

	
	public List<MetaProduct> getMetaProductByProductId(long productId) {
		return metaProductDAO.getMetaProductByProductId(productId);
	}
	
	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}

	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDao) {
		this.metaTimePriceDAO = metaTimePriceDao;
	}

	public void insertMetaTimePrice(TimePrice record) {
		metaTimePriceDAO.insert(record);
	}

	public OrderItemMetaDAO getOrderItemMetaDAO() {
		return orderItemMetaDAO;
	}

	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}

	public void changeMetaProductValid(Map params, String operatorName) {
		metaProductDAO.markIsValid(params);
		
		comLogDAO.insert("META_PRODUCT", null, (Long)params.get("metaProductId"), operatorName, Constant.COM_LOG_ORDER_EVENT.updateMetaProduct.name(), 
				"更新采购产品", MessageFormat.format("更新名称为[{0}]的采购产品状态为[{1}]", params.get("productName"),params.get("validStr")), null);
	}

	public MetaProduct getMetaProduct(Long metaProductId) {
		return metaProductDAO.getMetaProductByPk(metaProductId);
	}

	public Integer selectRowCount(Map searchConds) {
		return metaProductDAO.selectRowCount(searchConds);
	} 
	
	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}
 
	public void updateManager(HashMap params) {
		metaProductDAO.updateManager(params);
	}

	public void updateOrgIds(Map<String, Object> params) {
		metaProductDAO.updateOrgIds(params);
	}

	public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
		this.metaProductBranchDAO = metaProductBranchDAO;
	}

	@Override
	public boolean checkNotNeedResourceConfirm(Long metaProductId) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("metaProductId", metaProductId);
		map.put("beginDate", DateUtil.getDayStart(new Date()));
		return metaTimePriceDAO.selectNotNeedResourceConfirm(map)>0;
	}
	
	@Override
	public List<MetaProduct> getMetaProductListByTargetId(Map<String, Object> params) {
		return metaProductDAO.getMetaProductListByTargetId(params);
	}

	@Override
	public Integer getMetaProductListByTargetIdCount(Map<String, Object> params) {
		return metaProductDAO.getMetaProductListByTargetIdCount(params);
	}

	@Override
	public void updateZeroStock(TimePrice bean, Long metaBranchId,
			String operatorName) {
		if (bean.isClosed()) {
			return;
		}
		Long theZero = bean.getBeginDate().getTime();
		Calendar cal = Calendar.getInstance();
		while (theZero <= bean.getEndDate().getTime()) {
			cal.setTimeInMillis(theZero);
			if (bean.isSpecday(cal.get(Calendar.DAY_OF_WEEK))) {
				Date specDate = new Date(theZero);
				TimePrice timePrice = metaTimePriceDAO.getMetaTimePriceByIdAndDate(metaBranchId, specDate);				
				if (timePrice != null) {
					timePrice.setZeroStockHour(bean.getZeroStockHour());
					metaTimePriceDAO.update(timePrice);
				}
			}
			theZero += 86400000;
		}
		saveTimePriceLogStr(bean, operatorName);//保存日子信息
	}
	/**
	 * 根据采购产品类别id查询采购产品信息
	 */
	public List<MetaProduct> getEbkMetaProductByBranchIds(List<Long> metaBranchIds){
		return metaProductDAO.getEbkMetaProductByBranchIds(metaBranchIds);
	}
	/**
	 * 根据采购产品id查询采购产品信息
	 */
	public List<MetaProduct> getEbkMetaProductByProductId(Long metaProductId){
		return metaProductDAO.getEbkMetaProductByProductId(metaProductId);
	}

	@Override
	public void updateStockByTimeAndBrachId(String[] specDates,
			Long metaBranchId, String operatorName) {
		try{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<specDates.length;i++){
			Date specDate =sdf.parse(specDates[i]);
			TimePrice timePrice = metaTimePriceDAO.getMetaTimePriceByIdAndDate(metaBranchId,specDate);
			if (timePrice != null) {
				timePrice.setDayStock(0);
				timePrice.setOverSale("false");
				metaTimePriceDAO.update(timePrice);
			}
		}
		saveTimePriceLogInfo(specDates,metaBranchId,operatorName);//保存日子信息
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void saveTimePriceLogInfo(String[] specdates,Long metaBranchId,String operatorName){
		String spectimes="";
		for(int i=0;i<specdates.length;i++){
			spectimes+=specdates[i]+",";
		}
		String Logstr=operatorName+"将时间价格表中采购产品分支ID["+metaBranchId+"],入住日期["+spectimes+"]库存设为0，超卖否";
		comLogDAO.insert(null, metaBranchId, metaBranchId, operatorName,
			Constant.COM_LOG_ORDER_EVENT.updateMetaTimePrice.name(),"关房操作",
			Logstr,"META_TIME_PRICE");
	}

	@Override
	public TimePrice getMetaTimePriceByIdAndDate(Long metaBranchId, Date specDate) {
		return metaTimePriceDAO.getMetaTimePriceByIdAndDate(metaBranchId, specDate);
	}

	@Override
	public void updateHour(TimePrice bean, Long metaBranchId,
			String operatorName) {
		if (bean.isClosed()) {
			return;
		}
		Long theZero = bean.getBeginDate().getTime();
		Calendar cal = Calendar.getInstance();
		while (theZero <= bean.getEndDate().getTime()) {
			cal.setTimeInMillis(theZero);
			if (bean.isSpecday(cal.get(Calendar.DAY_OF_WEEK))) {
				Date specDate = new Date(theZero);
				TimePrice timePrice = metaTimePriceDAO.getMetaTimePriceByIdAndDate(metaBranchId, specDate);				
				if (timePrice != null) {
					timePrice.setAheadHour(bean.getAheadHour());
					timePrice.setCancelHour(bean.getCancelHour());
					timePrice.setCancelStrategy(bean.getCancelStrategy());
					metaTimePriceDAO.update(timePrice);
				}
			}
			theZero += 86400000;
		}
		saveTimePriceLogStr(bean, operatorName);//保存日子信息
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	@Override
	public Long selectMetaProductCountByTargetId(Long targetId,
			String targetType) {
		return metaProductDAO.selectMetaProductCountByTargetId(targetId, targetType);
	}

	@Override
	public MetaProduct getMetaProductByBranchId(Long branchId) {
		return metaProductDAO.getMetaProductByBranchId(branchId);
	}

	@Override
	public List<MetaProduct> getMetaProductListByPerformTargetId(Map<String, Object> params) {
		return metaProductDAO.getMetaProductListByPerformTargetId(params);
	}

	@Override
	public Integer getMetaProductListByPerformTargetIdCount(Map<String, Object> params) {
		return metaProductDAO.getMetaProductListByPerformTargetIdCount(params); 
	}

	@Override
	public List<MetaProduct> getEbkUserMetaProductsByParam(Map<String, Object> params) {
		return metaProductDAO.getEbkUserMetaProductsByParam(params);
	}
	/**
	 * 查询订单子子项id
	 */
	@Override
	public List<OrdOrderItemMeta> queryOrderItemMetaIdByOrderId(Long orderId) {
		return orderItemMetaDAO.selectByOrderId(orderId);
	}

	@Override
	public void updateTrainTimePrice(TimePrice bean, Long metaBranchId) {
		// TODO Auto-generated method stub
		bean.setMetaBranchId(metaBranchId);
		metaTimePriceDAO.updateTrainTimePrice(bean);
	}

	@Override
	public MetaProductTraffic getTrainMetaProduct(String fullName) {
		// TODO Auto-generated method stub
		return metaProductDAO.getTrainMetaProduct(fullName);
	}

	@Override
	public void signSendEmail(
			Map<String, Object> parameters) {
		metaProductDAO.signSendEmail(parameters);
	}
}
