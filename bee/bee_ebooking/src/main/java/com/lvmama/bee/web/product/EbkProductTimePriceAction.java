package com.lvmama.bee.web.product;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ebooking.EbkProdBranch;
import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.ebooking.EbkProdBranchService;
import com.lvmama.comm.bee.service.ebooking.EbkProdTimePriceService;
import com.lvmama.comm.bee.vo.EbkCalendarModel;
import com.lvmama.comm.bee.vo.EbkProdTimePriceModel;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.vo.Constant;
@Results(value = {
		@Result(name = "SURROUNDING_GROUPqueryEbkProdTimePrice", location = "/WEB-INF/pages/ebooking/product/subPage/ebkGroupProductTimePrice.jsp"),
		@Result(name = "DOMESTIC_LONGqueryEbkProdTimePrice", location = "/WEB-INF/pages/ebooking/product/subPage/ebkLongProductTimePrice.jsp"),
		@Result(name = "ABROAD_PROXYqueryEbkProdTimePrice", location = "/WEB-INF/pages/ebooking/product/subPage/ebkProxyProductTimePrice.jsp"),
		@Result(name = "SURROUNDING_GROUPeditEbkProdTimePrice", location = "/WEB-INF/pages/ebooking/product/subPage/editGroupEbkProdTimePrice.jsp"),
		@Result(name = "DOMESTIC_LONGeditEbkProdTimePrice", location = "/WEB-INF/pages/ebooking/product/subPage/editLongEbkProdTimePrice.jsp"),
		@Result(name = "ABROAD_PROXYeditEbkProdTimePrice", location = "/WEB-INF/pages/ebooking/product/subPage/editProxyEbkProdTimePrice.jsp"),
		@Result(name = "SURROUNDING_GROUPeditOneDayProdTimePriceStock", location = "/WEB-INF/pages/ebooking/product/subPage/editGroupOneDayProdTimePriceStock.jsp"),
		@Result(name = "DOMESTIC_LONGeditOneDayProdTimePriceStock", location = "/WEB-INF/pages/ebooking/product/subPage/editLongOneDayProdTimePriceStock.jsp"),
		@Result(name = "ABROAD_PROXYeditOneDayProdTimePriceStock", location = "/WEB-INF/pages/ebooking/product/subPage/editProxyOneDayProdTimePriceStock.jsp")
		})
public class EbkProductTimePriceAction extends BaseEbkProductAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8785170365367681977L;
	
	private Map<String,String> branchType;
	@Autowired
	private EbkProdTimePriceService ebkProdTimePriceService;
	@Autowired
	private EbkProdBranchService ebkProdBranchService;
	
	private List<EbkProdTimePrice> ebkProdTimePriceList;
	
	private EbkCalendarModel ebkCalendarModel;
	private EbkCalendarModel calendarModel;
	private Long ebkProdBranchId;
	private Date currPageDate;
	private String monthType;
	private String branchName;
	
	private Integer newPriceStock = 0;
	private List<EbkProdBranch> ebkProdBranchList;
	
	private EbkProdTimePriceModel paramModel;
	
	private Long timePriceId;
	private EbkProdTimePrice ebkProdTimePrice;
	private Long aheadHourDay;
	private Long aheadHour;
	private Long aheadHourSecend;
	private EbkProdTimePrice.STOCK_TYPE[] stockTypes = EbkProdTimePrice.STOCK_TYPE.values();
	private String relationProduct="N";
	private String ebkProdBranchTypeName;

	/**
	 * 查询时间价格表
	 * @return
	 */
	@Action(value="/product/branch/queryEbkProdTimePrice")
	public String queryEbkProdTimePrice(){
		if(null==ebkProdBranchId){
			super.errorMessage="产品类别编码为空，不能进行后续操作";
			return "errorMessage";
		}
		EbkProdBranch branch= ebkProdBranchService.queryForEbkProdBranchId(ebkProdBranchId);
		ebkProdProductId = branch.getProdProductId();
		ebkProdBranchTypeName = branch.getBranchType();
		String result = isSupplierEbkProd();
		if(null!=result){
			return result;
		}
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
		currPageDate = (Date) (map.get("currPageDate"));
		Date beginDate = (Date)map.get("beginDate");
		Date endDate =(Date)map.get("endDate");
		
		

		EbkProdBranch ebkProdBranch = new EbkProdBranch();
		ebkProdBranch.setProdProductId(ebkProdProductId);
		List<EbkProdBranch> branchs = ebkProdBranchService.query(ebkProdBranch);
		Long seachBranchId = null;
		for(EbkProdBranch bch : branchs){
			if(Constant.ROUTE_BRANCH.VIRTUAL.name().equalsIgnoreCase(bch.getBranchType())){
				if(StringUtils.isNotBlank(bch.getVirtualBranchIds())){
					String ids[] = bch.getVirtualBranchIds().split(",");
					for(String id : ids){
						if(id.equalsIgnoreCase(ebkProdBranchId.toString())){
							seachBranchId = bch.getProdBranchId();
						}
					}
				}
			}
		}
		
		ebkCalendarModel =  ebkProdTimePriceService.selectEbkProdTimePriceByProdBranchId(ebkProdBranchId, beginDate, endDate);
		if(Constant.ROUTE_BRANCH.VIRTUAL.name().equalsIgnoreCase(branch.getBranchType())){
			calendarModel =  ebkProdTimePriceService.selectVirtualProdTimePriceByProdBranchId(ebkProdBranchId, beginDate, endDate);
		}else{
			calendarModel =  ebkProdTimePriceService.selectProdTimePriceByProdBranchId(ebkProdBranchId, beginDate, endDate);
		}
		if(null!=seachBranchId){
			EbkCalendarModel virtualEbkModel = ebkProdTimePriceService.selectEbkProdTimePriceByProdBranchId(seachBranchId, beginDate, endDate);
			EbkCalendarModel virtualSuperModel =  ebkProdTimePriceService.selectVirtualProdTimePriceByProdBranchId(seachBranchId, beginDate, endDate);

			setVirtualNumToTimePrice(virtualEbkModel,ebkCalendarModel);
			setVirtualNumToTimePrice(virtualSuperModel,calendarModel);
		}
		
		
		if(ebkCalendarModel.isHaveShareStock()){
			ebkCalendarModel.setShareStockBranchName(ebkProdBranchService.getBrancheNames(ebkCalendarModel.getShareStockBranchId()));
		}
		
		return ebkProductViewType+"queryEbkProdTimePrice";
	}
	
	private void setVirtualNumToTimePrice(EbkCalendarModel toSetModel,EbkCalendarModel setModel){
		Map<String, String> toSetMap = new HashMap<String, String>();
		String format = "yyyy-MM-dd";
		EbkProdTimePrice[][] toSetCalendar = toSetModel.getEbkCalendar();
		EbkProdTimePrice[][] setCalendar = setModel.getEbkCalendar();
		if(null!=toSetCalendar&&null!=setCalendar){
			for (int i = 0; i < toSetCalendar.length; i++) {
				EbkProdTimePrice[] objH = toSetCalendar[i];
				for (int j = 0; j < objH.length; j++) {
					EbkProdTimePrice tp = objH[j];
					String key = DateUtil.getDateTime(format, tp.getSpecDate());
					if(null!=tp.getProductId()&&null!=tp.getDayStock()){
						toSetMap.put(key, tp.getDayStock().toString());
					}
				}
			}
			if(!toSetMap.isEmpty()){
				for (int k = 0; k < setCalendar.length; k++) {
					EbkProdTimePrice[] objK = setCalendar[k];
					for (int m = 0; m < objK.length; m++) {
						EbkProdTimePrice tpM = objK[m];
						String keyM = DateUtil.getDateTime(format, tpM.getSpecDate());
						if(toSetMap.containsKey(keyM)){
							String daStock = toSetMap.get(keyM);
							if(StringUtils.isNotBlank(daStock)){
								tpM.setHaveShareStock(true);
								tpM.setShareStockNum(daStock);
							}
						}
					}
				}
			}
		}
	}
	
	@Action(value="/product/branch/editEbkProdTimePrice")
	public String editEbkProdTimePrice(){
		String result = isSupplierEbkProd();
		if(null!=result){
			return result;
		}
		EbkProdBranch ebkProdBranch = new EbkProdBranch();
		ebkProdBranch.setProdProductId(ebkProdProductId);
		ebkProdBranchList = ebkProdBranchService.query(ebkProdBranch);
		return ebkProductViewType+"editEbkProdTimePrice";
	}
	
	@Action(value="/product/branch/editOneDayProdTimePriceStock")
	public String editOneDayProdTimePriceStock(){
		if(null==ebkProdBranchId){
			errorMessage = "产品类别编码为空，不能操作";
			return "errorMessage";
		}
		if(null==currPageDate){
			errorMessage = "要编辑的日期为空，不能操作";
			return "errorMessage";
		}
		
		String result = null==isSupplierEbkProd()?isEditEbkEditStatus():null;
		if(null!=result){
			return result;
		}
		EbkProdBranch  branch = ebkProdBranchService.queryForEbkProdBranchId(ebkProdBranchId);
		String operateStatus = EbkProdTimePrice.OPERATE_STATUS.ADD_OPERATE.name();
		if(null!=branch && null!=branch.getProdProductBranchId()){
			List<TimePrice> timePriceList = ebkProdTimePriceService.selectProdTimePriceByParams(branch.getProdProductBranchId(),currPageDate,currPageDate);
			if(null!=timePriceList && !timePriceList.isEmpty()){
				operateStatus = EbkProdTimePrice.OPERATE_STATUS.UPDATE_OPERATE.name();
			}
		}
		ebkProdTimePrice = ebkProdTimePriceService.findEbkProdTimePriceDOByPrimaryKey(timePriceId);
		if(null==ebkProdTimePrice){
			ebkProdTimePrice = new EbkProdTimePrice();
			ebkProdTimePrice.setSpecDate(currPageDate);
			ebkProdTimePrice.setProductId(ebkProdProductId);
			ebkProdTimePrice.setProdBranchId(ebkProdBranchId);
			ebkProdTimePrice.setCancelStrategy(Constant.CANCEL_STRATEGY.MANUAL.name());
			ebkProdTimePrice.setForbiddenSell(Constant.TRUE_FALSE.FALSE.getAttr1());
			ebkProdTimePrice.setOperateStatus(operateStatus);
			ebkProdTimePrice.setBranchType(branch.getBranchType());
			ebkProdTimePrice.setOverSale(Constant.TRUE_FALSE.FALSE.getAttr1());
			ebkProdTimePrice.setResourceConfirm(Constant.TRUE_FALSE.FALSE.getAttr1());
			aheadHourDay = Long.valueOf(1);
			aheadHour =  Long.valueOf(12);
			aheadHourSecend =Long.valueOf(0);
		}else{
			if(EbkProdTimePrice.STOCK_TYPE.UNLIMITED_STOCK.name().equals(ebkProdTimePrice.getStockType())){
				ebkProdTimePrice.setDayStock(null);
			}
			ebkProdTimePrice.setSettlementPrice(ebkProdTimePrice.getSettlementPriceYuan());
			ebkProdTimePrice.setPrice(ebkProdTimePrice.getPriceYuan());
			ebkProdTimePrice.setMarketPrice(ebkProdTimePrice.getMarketPriceYuan());
			ebkProdTimePrice.setOperateStatus(operateStatus);
			ebkProdTimePrice.setBranchType(branch.getBranchType());
			if(null!=ebkProdTimePrice.getAheadHour()){
				aheadHourDay = ebkProdTimePrice.getAheadHour().longValue()/(60*24);
				aheadHour =  (ebkProdTimePrice.getAheadHour().longValue()-aheadHourDay*60*24)/60;
				aheadHourSecend =ebkProdTimePrice.getAheadHour().longValue()-aheadHourDay*60*24-aheadHour*60;
			}
			
		}
		return ebkProductViewType+"editOneDayProdTimePriceStock";
	}
	/**
	 * 修改时间价格库存
	 */
	@Action(value="/product/branch/updateEbkProdTimePriceStore")
	public void updateEbkProdTimePriceStore(){
		operateTimePrice(EbkProdTimePriceModel.MODEL_TYPE.NEW_PRICE_STOCK.getType(),EbkProdTimePrice.OPERATE_STATUS.ADD_OPERATE.getCode());
	}
	/**
	 * 修改时间价格
	 */
	@Action(value="/product/branch/updateEbkPriceTimePrice")
	public void updateEbkPriceTimePrice(){
		operateTimePrice(EbkProdTimePriceModel.MODEL_TYPE.UPDATE_PRICE.getType(),EbkProdTimePrice.OPERATE_STATUS.UPDATE_OPERATE.getCode());
	}
	/**
	 * 修改库存
	 */
	@Action(value="/product/branch/updateEbkPriceTimeStore")
	public void updateEbkPriceTimeStore(){
		operateTimePrice(EbkProdTimePriceModel.MODEL_TYPE.UPDATE_STOCK.getType(),EbkProdTimePrice.OPERATE_STATUS.UPDATE_OPERATE.getCode());
	}
	/**
	 * 修改价格和库存
	 */
	@Action(value="/product/branch/updateEbkPriceTimePriceAndStore")
	public void updateEbkPriceTimePriceAndStore(){
		operateTimePrice(EbkProdTimePriceModel.MODEL_TYPE.UDPATE_PRICE_STOCK.getType(),EbkProdTimePrice.OPERATE_STATUS.UPDATE_OPERATE.getCode());
	}
	@Action(value="/product/branch/updateOneDayProdTimePriceStock")
	public void updateOneDayProdTimePriceStock(){
		JSONObject json=new JSONObject();
		if(null==ebkProdTimePrice){
			json.put("success", Boolean.FALSE);
			json.put("message", -200);
			JSONOutput.writeJSON(getResponse(), json);
			return;
		}
		ebkProdProductId = ebkProdTimePrice.getProductId();
		ebkProdBranchId = ebkProdTimePrice.getProdBranchId();
		currPageDate = ebkProdTimePrice.getSpecDate();
		if(!isSupplierEbkProductJson()){
			return;
		}
		if(null==ebkProdBranchId){
			json.put("success", Boolean.FALSE);
			json.put("message", -2);
			JSONOutput.writeJSON(getResponse(), json);
			return;
		}
		if(null==currPageDate){
			json.put("success", Boolean.FALSE);
			json.put("message", -201);
			JSONOutput.writeJSON(getResponse(), json);
			return;
		}
		EbkProdBranch branch = ebkProdBranchService.queryForEbkProdBranchId(ebkProdTimePrice.getProdBranchId());
		Long superBranchId = branch.getProdProductBranchId();
		Long hour = (null==aheadHourDay?0L:aheadHourDay*60*24)+(null==aheadHour?0L:aheadHour*60)+(null==aheadHourSecend?0L:aheadHourSecend);
		ebkProdTimePrice.setAheadHour(hour);
		if(!Constant.ROUTE_BRANCH.VIRTUAL.name().equalsIgnoreCase(branch.getBranchType())){
			if(Constant.EBK_PRODUCT_VIEW_TYPE.SURROUNDING_GROUP.name().equalsIgnoreCase(ebkProductViewType)&&null==ebkProdTimePrice.getPrice()){
				json.put("success", Boolean.FALSE);
				json.put("message", -202);
				JSONOutput.writeJSON(getResponse(), json);
				return;
			}
			if(null==ebkProdTimePrice.getCancelStrategy() || null==ebkProdTimePrice.getForbiddenSell()
					|| null==ebkProdTimePrice.getMarketPrice() || null==ebkProdTimePrice.getSettlementPrice()
					|| null==ebkProdTimePrice.getOverSale() || null==ebkProdTimePrice.getResourceConfirm() || 0==hour.longValue()){
				json.put("success", Boolean.FALSE);
				json.put("message", -202);
				JSONOutput.writeJSON(getResponse(), json);
				return;
			}

			ebkProdTimePrice.setSettlementPrice(ebkProdTimePrice.getSettlementPriceFen());
			ebkProdTimePrice.setMarketPrice(ebkProdTimePrice.getMarketPriceFen());
			ebkProdTimePrice.setPrice(ebkProdTimePrice.getPriceFen());
		}
		
		if(EbkProdTimePrice.STOCK_TYPE.UNLIMITED_STOCK.name().equals(ebkProdTimePrice.getStockType())){
			ebkProdTimePrice.setDayStock(-1L);
		}
		try{
			List<TimePrice> timePriceList =null;
			if(null!=superBranchId){
				timePriceList = ebkProdTimePriceService.selectProdTimePriceByParams(superBranchId,currPageDate,currPageDate);
			}
			if(null==timePriceList || (null!=timePriceList && timePriceList.isEmpty())){
				ebkProdTimePrice.setOperateStatus(EbkProdTimePrice.OPERATE_STATUS.ADD_OPERATE.getCode());
			}else{
				ebkProdTimePrice.setOperateStatus(EbkProdTimePrice.OPERATE_STATUS.UPDATE_OPERATE.getCode());
			}
			if(null!=ebkProdTimePrice.getTimePriceId()){
				ebkProdTimePriceService.update(ebkProdTimePrice);
			}else{
				ebkProdTimePrice = ebkProdTimePriceService.insert(ebkProdTimePrice);
			}
			ComLog comLog = new ComLog();
			comLog.setObjectType("EBK_PROD_TIME_PRICE");
			comLog.setParentType("EBK_PROD_BRANCH");
			comLog.setParentId(ebkProdProductId);
			comLog.setLogName("编辑产品库存表");
			comLog.setLogType("editEbkProdTimeStockAndStockOneDay");
			comLog.setOperatorName(super.getSessionUserName());
			comLog.setCreateTime(new Date());
			comLog.setObjectId(ebkProdBranchId);
			if(Constant.ROUTE_BRANCH.VIRTUAL.name().equalsIgnoreCase(branch.getBranchType())){
				comLog.setContent("库存"
						+ ebkProdTimePrice.getDayStock()
						+ ",日库存变动类型="
						+ EbkProdTimePrice.STOCK_TYPE.getCnName(ebkProdTimePrice.getStockType())
						+ ",是否资源审核="
						+ ("true".equalsIgnoreCase(ebkProdTimePrice
								.getResourceConfirm()) ? "是" : "否") + ",是否超卖="
						+ ("true".equalsIgnoreCase(ebkProdTimePrice.getOverSale()) ? "是" : "否") );
			}else{
				comLog.setContent("日期="
						+ DateUtil.formatDate(ebkProdTimePrice.getSpecDate(),
								"yyyy-MM-dd")
						+ ",销售价="
						+ ebkProdTimePrice.getPriceYuan()
						+ ",门市价"
						+ ebkProdTimePrice.getMarketPriceYuan()
						+ ",结算价"
						+ ebkProdTimePrice.getSettlementPriceYuan()
						+ ",库存"
						+ ebkProdTimePrice.getDayStock()
						+ ",日库存变动类型="
						+ EbkProdTimePrice.STOCK_TYPE.getCnName(ebkProdTimePrice.getStockType())
						+ ",是否资源审核="
						+ ("true".equalsIgnoreCase(ebkProdTimePrice
								.getResourceConfirm()) ? "是" : "否") + ",是否超卖="
						+ ("true".equalsIgnoreCase(ebkProdTimePrice.getOverSale()) ? "是" : "否") + ",退改策略="
						+ Constant.CANCEL_STRATEGY.getCnName(ebkProdTimePrice.getCancelStrategy()) + ",是否禁售="
						+("true".equalsIgnoreCase( ebkProdTimePrice.getForbiddenSell()) ? "是" : "否") + ",提前预订小时数="
						+ ebkProdTimePrice.getAheadHour() + "分"+(Constant.EBK_PRODUCT_VIEW_TYPE.HOTEL.name().equalsIgnoreCase(super.ebkProductViewType)?",早餐份数="+ebkProdTimePrice.getBreakfastCount():""));
			}
			
			comLogService.addComLog(comLog);
			json.put("success", Boolean.TRUE);
			json.put("timePriceId",ebkProdTimePrice.getTimePriceId());
			JSONOutput.writeJSON(getResponse(), json);
		}catch(Exception e){
			e.printStackTrace();
			json.put("success", Boolean.FALSE);
			json.put("message", -500);
			JSONOutput.writeJSON(getResponse(), json);
		}
	}
	@Action(value="/product/branch/queryEbkRelationProdTimePrice")
    public String queryEbkRelationProdTimePrice(){
        if(null==ebkProdBranchId){
            super.errorMessage="产品类别编码为空，不能进行后续操作";
            return "errorMessage";
        }
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
        currPageDate = (Date) (map.get("currPageDate"));
        Date beginDate = (Date)map.get("beginDate");
        Date endDate =(Date)map.get("endDate");
        ebkCalendarModel = new EbkCalendarModel();
        ebkCalendarModel.setEbkTimePrice(new ArrayList<EbkProdTimePrice>(),beginDate); 
        calendarModel =  ebkProdTimePriceService.selectRelationProdTimePriceByProdBranchId(ebkProdBranchId, beginDate, endDate);
        relationProduct="Y";
        return ebkProductViewType+"queryEbkProdTimePrice";
    }
	private boolean validateParam(){
		if(!isSupplierEbkProductJson() || !isEditEbkEditStatusJson()){
			return Boolean.FALSE;
		}
		JSONObject json=new JSONObject();
		if(null==paramModel){
			json.put("success", Boolean.FALSE);
			json.put("message", -100);
			JSONOutput.writeJSON(getResponse(), json);
			return Boolean.FALSE;
		}
		if(null==newPriceStock){
			json.put("success", Boolean.FALSE);
			json.put("message", -1000);
			JSONOutput.writeJSON(getResponse(), json);
			return Boolean.FALSE;
		}
		if(null!=paramModel.getPriceStockSimples()){
			for(int i=0;i<paramModel.getPriceStockSimples().size();i++){
				EbkProdTimePrice timePrice = paramModel.getPriceStockSimples().get(i);
				if(null==timePrice){
					paramModel.getPriceStockSimples().remove(i);
					i--;
				}
			}
		}
		String validMessage = paramModel.validate(newPriceStock);
		if(null!=validMessage){
			json.put("success", Boolean.FALSE);
			json.put("message", -101);
			json.put("column", validMessage);
			JSONOutput.writeJSON(getResponse(), json);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	private void operateTimePrice(final Integer modelType,
			final String operateStatus) {
		JSONObject json = new JSONObject();
		try {

			newPriceStock = modelType;
			if (!validateParam()) {
				return;
			}
			if (!validate(modelType)) {
				return;
			}
			List<EbkProdTimePrice> ebkProdTimePrices = paramModel
					.getPriceStockSimples();
			List<Date> dates = new ArrayList<Date>();
			if (null == paramModel.getBeginDate()
					|| null == paramModel.getEndDate()) {
				String[] days = paramModel.getDays().split(",");
				for (String day : days) {
					dates.add(DateUtil.stringToDate(day, "yyyy-MM-dd"));
				}
			} else {
				dates = DateUtil.getDateList(paramModel.getBeginDate(),
						paramModel.getEndDate());
			}
			dates = cleanNotInWeekDate(dates);
			List<EbkProdTimePrice> prices = new ArrayList<EbkProdTimePrice>();
			List<ComLog> logs = new ArrayList<ComLog>();
			Long aheadHour = (long) paramModel.getAheadHourDay() * 24 * 60
					+ paramModel.getAheadHour() * 60
					+ paramModel.getAheadHourSecend();
			if (aheadHour != null && aheadHour.longValue() == 0) {
				aheadHour = null;
			}
			List<Long> prodBranchIds = new ArrayList<Long>();
			for (EbkProdTimePrice price : ebkProdTimePrices) {
				if (null != price.getProdBranchId()) {
					prodBranchIds.add(price.getProdBranchId());
				}
			}
			Map<String, Object> params = initParams(prodBranchIds);
			params.put("dates", dates);
			List<EbkProdTimePrice> ebkProdTimePriceOlds = ebkProdTimePriceService
					.query(params);
			List<Long> ignoreTimePriceId = new ArrayList<Long>();
			for (EbkProdTimePrice price : ebkProdTimePrices) {
				prodBranchIds.add(price.getProdBranchId());
				EbkProdBranch branch = ebkProdBranchService
						.queryForEbkProdBranchId(price.getProdBranchId());
				// 虚拟类别的销售类别id为0
				Long superBranchId = branch.getProdProductBranchId();
				List<TimePrice> timePriceList = null;
				if (null != superBranchId && superBranchId > 0) {// 非虚拟有销售价格
					timePriceList = ebkProdTimePriceService
							.selectProdTimePriceByParams(superBranchId,
									paramModel.getBeginDate(),
									paramModel.getEndDate());
				}
				if (null != superBranchId && superBranchId == 0L) {// 虚拟类别查询采购价格
					timePriceList = ebkProdTimePriceService
							.selectMetaTimePriceByParams(
									branch.getMetaProdBranchId(),
									paramModel.getBeginDate(),
									paramModel.getEndDate());
				}
				Map<Long, TimePrice> timePriceMap = toTimePriceMap(timePriceList);
				Map<String, EbkProdTimePrice> oldEbkProdTimePriceMap = toEbkProdTimePriceMap(ebkProdTimePriceOlds);

				for (Date date : dates) {
					boolean newObject = Boolean.TRUE;
					boolean oldObject = Boolean.TRUE;
					if (null != timePriceList
							&& !timePriceList.isEmpty()
							&& timePriceMap.get(DateUtil.accurateToDay(date)
									.getTime()) != null) {
						newObject = Boolean.FALSE;
					}
					if (null != ebkProdTimePriceOlds
							&& !ebkProdTimePriceOlds.isEmpty()
							&& !operateStatus
									.equals(EbkProdTimePrice.OPERATE_STATUS.ADD_OPERATE
											.name())) {
						EbkProdTimePrice oldEbkProdTimePrice = oldEbkProdTimePriceMap
								.get(DateUtil.accurateToDay(date).getTime()
										+ "-"
										+ price.getProdBranchId().longValue());
						if (oldEbkProdTimePrice != null
								&& EbkProdTimePrice.OPERATE_STATUS.ADD_OPERATE
										.name().equals(
												oldEbkProdTimePrice
														.getOperateStatus())) {
							ignoreTimePriceId.add(oldEbkProdTimePrice
									.getTimePriceId());
							oldObject = Boolean.FALSE;
						}
					} else if (null != ebkProdTimePriceOlds
							&& !ebkProdTimePriceOlds.isEmpty()
							&& operateStatus
									.equals(EbkProdTimePrice.OPERATE_STATUS.ADD_OPERATE
											.name())) {
						EbkProdTimePrice oldEbkProdTimePrice = oldEbkProdTimePriceMap
								.get(DateUtil.accurateToDay(date).getTime()
										+ "-"
										+ price.getProdBranchId().longValue());
						if (oldEbkProdTimePrice != null
								&& EbkProdTimePrice.OPERATE_STATUS.UPDATE_OPERATE
										.name().equals(
												oldEbkProdTimePrice
														.getOperateStatus())
								&& operateStatus
										.equals(EbkProdTimePrice.OPERATE_STATUS.ADD_OPERATE
												.name())) {
							ignoreTimePriceId.add(oldEbkProdTimePrice
									.getTimePriceId());
							oldObject = Boolean.FALSE;
						}
					}
					if ((newObject && operateStatus
							.equals(EbkProdTimePrice.OPERATE_STATUS.ADD_OPERATE
									.name()))
							|| (!newObject && !operateStatus
									.equals(EbkProdTimePrice.OPERATE_STATUS.ADD_OPERATE
											.name())) && oldObject) {
						prices.add(initEbkProdTimePrice(date, price,
								operateStatus, aheadHour));
					}
				}
				logs.add(setComLog(price.getProdBranchId(), modelType));
			}

			if (!ignoreTimePriceId.isEmpty()) {
				params.put("notInTimePriceIds", ignoreTimePriceId);
			}
			ebkProdTimePriceService.delete(params);
			ebkProdTimePriceService.insertBatch(prices);
			comLogService.addComLogList(logs);
			json.put("success", Boolean.TRUE);

		} catch (Exception e) {
			e.printStackTrace();
			json.put("success", Boolean.FALSE);
			json.put("message", -500);
		} finally {
			JSONOutput.writeJSON(getResponse(), json);
		}
	}
	
	private EbkProdTimePrice initEbkProdTimePrice(final Date date,final EbkProdTimePrice price,final String operateStatus,Long aheadHour){
		EbkProdTimePrice ebkPrice = new EbkProdTimePrice();
		ebkPrice.setSpecDate(DateUtil.accurateToDay(date));
		ebkPrice.setProdBranchId(price.getProdBranchId());
		ebkPrice.setBreakfastCount(paramModel.getBreackfastCount());
		ebkPrice.setCancelStrategy(paramModel.getCancelStrategy());
		ebkPrice.setForbiddenSell(paramModel.getForbiddenSell());
		ebkPrice.setProductId(ebkProdProductId);
		ebkPrice.setOperateStatus(operateStatus);
		
		ebkPrice.setPrice(price.getPriceFen());
		ebkPrice.setMarketPrice(price.getMarketPriceFen());
		ebkPrice.setSettlementPrice(price.getSettlementPriceFen());
		ebkPrice.setAheadHour(aheadHour);
		ebkPrice.setDayStock(price.getDayStock());
		ebkPrice.setStockType(price.getStockType());
		ebkPrice.setResourceConfirm(price.getResourceConfirm());
		ebkPrice.setOverSale(price.getOverSale());
		if(EbkProdTimePrice.STOCK_TYPE.UNLIMITED_STOCK.name().equals(price.getStockType())){
			ebkPrice.setDayStock(-1L);
		}
		return ebkPrice;
	}
	
	private Map<String,Object> initParams(final List<Long> prodBranchIds){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("beginDate", paramModel.getBeginDate());
		params.put("endDate", paramModel.getEndDate());
		if (paramModel.getBeginDate()!=null) {
			params.put("week", paramModel.getWeeks());
		}
		params.put("productId", ebkProdProductId);
		params.put("prodBranchIds", prodBranchIds);
		return params;
	}
	private ComLog setComLog(Long prodBranchId,Integer modelType){
		String logName = "编辑产品时间价格库存表";
		String logType = "editEbkProdTimePriceAndStock";
		if(modelType.intValue()==EbkProdTimePriceModel.MODEL_TYPE.UPDATE_PRICE.getType().intValue()){
			logName = "编辑产品时间价格";
			logType = "editEbkProdTimePrice";
		}else if(modelType.intValue()==EbkProdTimePriceModel.MODEL_TYPE.UPDATE_PRICE.getType().intValue()){
			logName = "编辑产品时间库存";
			logType = "editEbkProdTimeStock";
		}
		ComLog comLog = new ComLog();
		comLog.setObjectType("EBK_PROD_TIME_PRICE");
		comLog.setObjectId(prodBranchId);
		comLog.setParentType("EBK_PROD_BRANCH");
		comLog.setParentId(ebkProdProductId);
		comLog.setLogName(logName);
		comLog.setLogType(logType);
		comLog.setContent(paramModel.toStringCh(prodBranchId, modelType));
		comLog.setOperatorName(super.getSessionUserName());
		comLog.setCreateTime(new Date());
		return comLog;
	}
	private boolean validate(final Integer modelType){
		JSONObject json=new JSONObject();
		Map<String,Object> validMap=null; 
		paramModel.setEbkProductViewType(this.getEbkProductViewType());
		if(modelType.intValue()==EbkProdTimePriceModel.MODEL_TYPE.NEW_PRICE_STOCK.getType().intValue() || modelType.intValue()==EbkProdTimePriceModel.MODEL_TYPE.UDPATE_PRICE_STOCK.getType().intValue() ){
			validMap= paramModel.validatePriceAndStock();
		}else if(modelType.intValue()==EbkProdTimePriceModel.MODEL_TYPE.UPDATE_PRICE.getType().intValue()){
			validMap= paramModel.validatePrice();
		}else if(modelType.intValue()==EbkProdTimePriceModel.MODEL_TYPE.UPDATE_STOCK.getType().intValue()){
			validMap= paramModel.validateStock();
		}else{
			validMap = new HashMap<String,Object>();
		}
		if(null!=validMap){
			json.put("success", Boolean.FALSE);
			json.put("message", -102);
			json.put("column", validMap.get("column"));
			json.put("index", validMap.get("index"));
			JSONOutput.writeJSON(getResponse(), json);
			return Boolean.FALSE;
		}	
		return Boolean.TRUE;
	}
	private List<Date> cleanNotInWeekDate(final List<Date> dates){
		List<Integer> weeks = paramModel.getWeeks();//按周
		for(int i=0;i<dates.size();i++){
			Calendar cal =Calendar.getInstance();
			cal.setTime(dates.get(i));
			int week = cal.get(Calendar.DAY_OF_WEEK);
			boolean isHas = Boolean.FALSE;
			for(Integer day:weeks){
				if(week==day){
					isHas = Boolean.TRUE;
					break;
				}
			}
			if(!isHas){
				dates.remove(i);
				i--;
			}
		}
		return dates;
	}
	private Map<Long,TimePrice> toTimePriceMap(List<TimePrice> timePriceList){
		Map<Long,TimePrice> timePriceMap=new HashMap<Long,TimePrice>();
		if(null==timePriceList||timePriceList.size()==0){
			return timePriceMap;
		}
		for (TimePrice timePrice : timePriceList) {
			timePriceMap.put(DateUtil.accurateToDay(timePrice.getSpecDate()).getTime(), timePrice);
		}
		return timePriceMap;
	}
	private Map<String,EbkProdTimePrice> toEbkProdTimePriceMap(List<EbkProdTimePrice> timePriceList){
		Map<String,EbkProdTimePrice> timePriceMap=new HashMap<String,EbkProdTimePrice>();
		for (EbkProdTimePrice ebkProdTimePrice : timePriceList) {
			timePriceMap.put(DateUtil.accurateToDay(ebkProdTimePrice.getSpecDate()).getTime()+"-"+ebkProdTimePrice.getProdBranchId(), ebkProdTimePrice);
		}
		return timePriceMap;
	}
	public EbkProdProduct getEbkProdProduct() {
		return ebkProdProduct;
	}

	public void setEbkProdProduct(EbkProdProduct ebkProdProduct) {
		this.ebkProdProduct = ebkProdProduct;
	}

	public Map<String, String> getBranchType() {
		return branchType;
	}

	public void setBranchType(Map<String, String> branchType) {
		this.branchType = branchType;
	}

	public List<EbkProdTimePrice> getEbkProdTimePriceList() {
		return ebkProdTimePriceList;
	}

	public void setEbkProdTimePriceList(List<EbkProdTimePrice> ebkProdTimePriceList) {
		this.ebkProdTimePriceList = ebkProdTimePriceList;
	}

	public EbkCalendarModel getEbkCalendarModel() {
		return ebkCalendarModel;
	}

	public void setEbkCalendarModel(EbkCalendarModel ebkCalendarModel) {
		this.ebkCalendarModel = ebkCalendarModel;
	}

	public EbkCalendarModel getCalendarModel() {
		return calendarModel;
	}

	public void setCalendarModel(EbkCalendarModel calendarModel) {
		this.calendarModel = calendarModel;
	}

	public Long getEbkProdBranchId() {
		return ebkProdBranchId;
	}

	public void setEbkProdBranchId(Long ebkProdBranchId) {
		this.ebkProdBranchId = ebkProdBranchId;
	}

	public Date getCurrPageDate() {
		return currPageDate;
	}

	public void setCurrPageDate(Date currPageDate) {
		this.currPageDate = currPageDate;
	}

	public String getMonthType() {
		return monthType;
	}

	public void setMonthType(String monthType) {
		this.monthType = monthType;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public Integer getNewPriceStock() {
		return newPriceStock;
	}

	public void setNewPriceStock(Integer newPriceStock) {
		this.newPriceStock = newPriceStock;
	}

	public List<EbkProdBranch> getEbkProdBranchList() {
		return ebkProdBranchList;
	}

	public void setEbkProdBranchList(List<EbkProdBranch> ebkProdBranchList) {
		this.ebkProdBranchList = ebkProdBranchList;
	}
	public String getEbkProductViewType() {
		return ebkProductViewType;
	}

	public void setEbkProductViewType(String ebkProductViewType) {
		this.ebkProductViewType = ebkProductViewType;
	}

	public EbkProdTimePriceModel getParamModel() {
		return paramModel;
	}

	public void setParamModel(EbkProdTimePriceModel paramModel) {
		this.paramModel = paramModel;
	}

	public Long getTimePriceId() {
		return timePriceId;
	}

	public void setTimePriceId(Long timePriceId) {
		this.timePriceId = timePriceId;
	}

	public EbkProdTimePrice getEbkProdTimePrice() {
		return ebkProdTimePrice;
	}

	public void setEbkProdTimePrice(EbkProdTimePrice ebkProdTimePrice) {
		this.ebkProdTimePrice = ebkProdTimePrice;
	}

	public Long getAheadHourDay() {
		return aheadHourDay;
	}

	public void setAheadHourDay(Long aheadHourDay) {
		this.aheadHourDay = aheadHourDay;
	}

	public Long getAheadHour() {
		return aheadHour;
	}

	public void setAheadHour(Long aheadHour) {
		this.aheadHour = aheadHour;
	}

	public Long getAheadHourSecend() {
		return aheadHourSecend;
	}

	public void setAheadHourSecend(Long aheadHourSecend) {
		this.aheadHourSecend = aheadHourSecend;
	}

	public EbkProdTimePrice.STOCK_TYPE[] getStockTypes() {
		return stockTypes;
	}

    public String getRelationProduct() {
        return relationProduct;
    }

	public String getEbkProdBranchTypeName() {
		return ebkProdBranchTypeName;
	}

	public void setEbkProdBranchTypeName(String ebkProdBranchTypeName) {
		this.ebkProdBranchTypeName = ebkProdBranchTypeName;
	}

}
