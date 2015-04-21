package com.lvmama.vst.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.vst.service.VstProdUpdateService;
import com.lvmama.comm.vst.vo.VstProdGoodsTimePriceVo;
import com.lvmama.prd.dao.MetaTimePriceDAO;


/**
 * VST系统更新super产品信息服务
 * 
 * @author taiqichao
 *
 */
public class VstProdUpdateServiceImpl implements VstProdUpdateService {
	
	
	private static final Log LOG=LogFactory.getLog(VstProdUpdateServiceImpl.class);
	
	private static final String OPERATOR_NAME="SYSTEM";
	
	private ProdProductService prodProductService;
	
	private ProdProductBranchService prodProductBranchService;
	
	private MetaProductService metaProductService;
	
	private MetaTimePriceDAO metaTimePriceDAO;
	
	private ComLogService comLogService;
	
	
	@Override
	public void updateProductLineStatus(ProdProduct prodProduct,ProdProductBranch prodProductBranch,boolean onLine){
		LOG.info("Vst update product online:"+onLine+",branch id:"+prodProductBranch.getProdBranchId()+",product id:"+prodProduct.getProductId());
		//更新产品上线下线状态
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("productId", prodProduct.getProductId());
		params.put("onLine", String.valueOf(onLine));
		params.put("productName", prodProduct.getProductName());
		params.put("onLineStr", prodProduct.getStrOnLine());
		prodProductService.markIsSellable(params, OPERATOR_NAME);
		//更新类别上线下线状态
		prodProductBranch.setOnline(String.valueOf(onLine));
		prodProductBranchService.updateByPrimaryKeySelective(prodProductBranch);
	}
	
	@Override
	public void saveTimePrice(MetaProductBranch metaProductBranch,List<VstProdGoodsTimePriceVo> timePrice){
		LOG.info("Vst update product time price,meta branch id:"+metaProductBranch.getMetaBranchId()+",meta product id:"+metaProductBranch.getMetaProductId());
		StringBuffer logBuilder = new StringBuffer();
		for (VstProdGoodsTimePriceVo vstProdGoodsTimePriceVo : timePrice) {
			//采购时间价格
			Date specDate=DateUtil.stringToDate(DateUtil.formatDate(vstProdGoodsTimePriceVo.getSpecDate(), "yyyy-MM-dd"), "yyyy-MM-dd");
			TimePrice metaTimePrice = metaProductService.getMetaTimePriceByIdAndDate(metaProductBranch.getMetaBranchId(),specDate);
			//两边时间价格表不一致，导致关班问题
			if("Y".equals(vstProdGoodsTimePriceVo.getForbiddenSell())&&null==metaTimePrice){
				LOG.info("vst update time price fail,the forbidden time could be found,time is:"+specDate);
				continue;
			}
			//生成日志
			logBuilder.append(createTimePriceLogStr(vstProdGoodsTimePriceVo, metaTimePrice,OPERATOR_NAME)).append("|");
			//已经存在，并且关班，删除
			if(null != metaTimePrice&&"Y".equals(vstProdGoodsTimePriceVo.getForbiddenSell())){
				metaTimePriceDAO.deleteByPK(metaTimePrice.getTimePriceId());
			}else{//新增/修改
				if(null==metaTimePrice){//新增
					metaTimePrice= new TimePrice();
					metaTimePrice.setProductId(metaProductBranch.getMetaProductId());
					metaTimePrice.setMetaBranchId(metaProductBranch.getMetaBranchId());
					metaTimePrice.setSpecDate(specDate);
					if("Y".equals(vstProdGoodsTimePriceVo.getOverSaleFlag())){
						metaTimePrice.setOverSale("true");
					}else{
						metaTimePrice.setOverSale("false");
					}
					if("Y".equals(vstProdGoodsTimePriceVo.getResourceConfirm())){
						metaTimePrice.setResourceConfirm("true");
					}else{
						metaTimePrice.setResourceConfirm("false");
					}
					metaTimePrice.setTotalDayStock(0L);
					metaTimePrice.setAheadHour(vstProdGoodsTimePriceVo.getAheadHour());
					metaTimePrice.setCancelHour(vstProdGoodsTimePriceVo.getCancelHour());
					metaTimePrice.setCancelStrategy(vstProdGoodsTimePriceVo.getCancelStrategy());
					if(null!=vstProdGoodsTimePriceVo.getSettlementPrice()){
						metaTimePrice.setSettlementPrice(vstProdGoodsTimePriceVo.getSettlementPrice());
					}
					if(null!=vstProdGoodsTimePriceVo.getPrice()){
						metaTimePrice.setSuggestPrice(vstProdGoodsTimePriceVo.getPrice());
						metaTimePrice.setMarketPrice(vstProdGoodsTimePriceVo.getPrice());
					}
					if(null!=vstProdGoodsTimePriceVo.getBreakfastCount()){
						metaTimePrice.setBreakfastCount(vstProdGoodsTimePriceVo.getBreakfastCount().longValue());
					}
					metaTimePrice.setDayStock(vstProdGoodsTimePriceVo.getDayStock());
					metaProductService.insertTimePrice(metaTimePrice);
				}else{//修改
					if("Y".equals(vstProdGoodsTimePriceVo.getOverSaleFlag())){
						metaTimePrice.setOverSale("true");
					}else{
						metaTimePrice.setOverSale("false");
					}
					if("Y".equals(vstProdGoodsTimePriceVo.getResourceConfirm())){
						metaTimePrice.setResourceConfirm("true");
					}else{
						metaTimePrice.setResourceConfirm("false");
					}
					metaTimePrice.setCancelHour(vstProdGoodsTimePriceVo.getCancelHour());
					metaTimePrice.setAheadHour(vstProdGoodsTimePriceVo.getAheadHour());
					metaTimePrice.setCancelStrategy(vstProdGoodsTimePriceVo.getCancelStrategy());
					if(null!=vstProdGoodsTimePriceVo.getSettlementPrice()){
						metaTimePrice.setSettlementPrice(vstProdGoodsTimePriceVo.getSettlementPrice());
					}
					if(null!=vstProdGoodsTimePriceVo.getPrice()){
						metaTimePrice.setSuggestPrice(vstProdGoodsTimePriceVo.getPrice());
						metaTimePrice.setMarketPrice(vstProdGoodsTimePriceVo.getPrice());
					}
					if(null!=vstProdGoodsTimePriceVo.getDayStock()){
						metaTimePrice.setDayStock(vstProdGoodsTimePriceVo.getDayStock());
					}
					if(null!=vstProdGoodsTimePriceVo.getBreakfastCount()){
						metaTimePrice.setBreakfastCount(vstProdGoodsTimePriceVo.getBreakfastCount().longValue());
					}
					metaProductService.updateDynamicTimePrice(metaTimePrice);
				}
			}
		}
		
		//采购的日志
		if(StringUtils.isNotBlank(logBuilder.toString())){
			comLogService.insert(null, metaProductBranch.getMetaBranchId(), metaProductBranch.getMetaBranchId(), OPERATOR_NAME,"VST_PRODCUT_SYN","系统同步", logBuilder.toString(), "META_TIME_PRICE");
		}
	}
	
	/**
	 * 生成日志
	 * @param timePrice 
	 * @param oldTimePrice
	 * @param operatorName
	 * @return
	 */
	private String createTimePriceLogStr(VstProdGoodsTimePriceVo timePrice, TimePrice oldTimePrice,String operatorName) {
		StringBuffer logBuilder = new StringBuffer();
		if (null!=oldTimePrice&&"Y".equals(timePrice.getForbiddenSell())) {//禁售
			logBuilder.append(operatorName+ "将" + DateUtil.getDateTime("yyyy-MM-dd", timePrice.getSpecDate())+"时间采购产品设置为禁售");
			return logBuilder.toString();
		}
		if (null!= timePrice&& null==oldTimePrice) {//新增日志
			logBuilder.append("新增时间价格："+DateUtil.getDateTime("yyyy-MM-dd", timePrice.getSpecDate()));
			logBuilder.append(LogViewUtil.logNewStrByColumnName("门市价", timePrice.getPrice()+ ""));
			logBuilder.append(LogViewUtil.logNewStrByColumnName("结算价", timePrice.getSettlementPrice()+ ""));
			if (timePrice.getDayStock() != 0) {
				logBuilder.append(LogViewUtil.logNewStrByColumnName("是否限量", timePrice.getDayStock() + ""));
			}
			logBuilder.append(LogViewUtil.logNewStrByColumnName("资源需确认",timePrice.getResourceConfirm()+""));
			logBuilder.append(LogViewUtil.logNewStrByColumnName("是否可超卖",timePrice.getOverSaleFlag()+""));
			logBuilder.append(LogViewUtil.logNewStrByColumnName("库存",timePrice.getDayStock()+""));
			logBuilder.append(LogViewUtil.logNewStrByColumnName("建议价", timePrice.getPrice()+""));
			logBuilder.append(LogViewUtil.logNewStrByColumnName("网站提前预订小时数", timePrice.getAheadHour()+ ""));
			logBuilder.append(LogViewUtil.logNewStrByColumnName("退改策略", timePrice.getCancelStrategy()));
			logBuilder.append(LogViewUtil.logNewStrByColumnName("网站最晚取消小时数", timePrice.getCancelHour()+ ""));
		} else {//修改
			logBuilder.append("编辑时间价格："+DateUtil.getDateTime("yyyy-MM-dd", timePrice.getSpecDate()));
			if (timePrice.getPrice()!= 0) {
				logBuilder.append(LogViewUtil.logEditStr("门市价", timePrice.getPrice() + ""));
			}
			if (null != timePrice.getSettlementPrice()) {
				logBuilder.append(LogViewUtil.logEditStr("结算价", timePrice.getSettlementPrice() + ""));
			}
			if (timePrice.getDayStock() != 0 && !LogViewUtil.logIsEmptyStr(timePrice.getDayStock() + "").equals(LogViewUtil.logIsEmptyStr(oldTimePrice.getDayStock() + ""))) {
				logBuilder.append(LogViewUtil.logEditStr("是否限量", timePrice.getDayStock() + ""));
			}
			if (timePrice.getPrice() != null) {
				logBuilder.append(LogViewUtil.logEditStr("建议价", timePrice.getPrice() + ""));
			}
			logBuilder.append(LogViewUtil.logNewStrByColumnName("资源需确认",timePrice.getResourceConfirm()+""));
			logBuilder.append(LogViewUtil.logNewStrByColumnName("是否可超卖",timePrice.getOverSaleFlag()+""));
			logBuilder.append(LogViewUtil.logNewStrByColumnName("库存",timePrice.getDayStock()+""));
			logBuilder.append(LogViewUtil.logNewStrByColumnName("网站提前预订小时数", timePrice.getAheadHour() + ""));
			logBuilder.append(LogViewUtil.logNewStrByColumnName("退改策略", timePrice.getCancelStrategy()));
			logBuilder.append(LogViewUtil.logNewStrByColumnName("网站最晚取消小时数", timePrice.getCancelHour() + ""));
		}
		return logBuilder.toString();
	}
	
	@Override
	public void updateStock(MetaProductBranch metaProductBranch,Long stock, Date start, Date end){
		
		if(null==stock){
			throw new RuntimeException("The stock must by not null.");
		}
		
		if(start.after(end)){
			throw new RuntimeException("The start date must before end date.");
		}
		
		while (start.getTime()<=end.getTime()) {
			
			Date specDate=DateUtil.stringToDate(DateUtil.formatDate(start, "yyyy-MM-dd"), "yyyy-MM-dd");
			
			//采购库存
			TimePrice metaTimePrice = metaTimePriceDAO.getMetaTimePriceByIdAndDate(metaProductBranch.getMetaBranchId(), specDate);		
			if(null!=metaTimePrice){
				Long currentStock=metaTimePrice.getDayStock()+stock;
				if(currentStock < 0) {
					throw new RuntimeException("The meta time price day stock must greater than 0.");
				}
				metaTimePrice.setDayStock(currentStock);
				metaTimePriceDAO.update(metaTimePrice);
			}
			
			//累加天数，处理下一天
			start=DateUtil.dsDay_Date(start, 1);
		}
		
	}
	
	
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}
	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}
	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	
	

}
