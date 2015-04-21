package com.lvmama.group.service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.com.dao.ComPlaceDAO;
import com.lvmama.comm.bee.po.group.GroupDream;
import com.lvmama.comm.bee.po.group.GroupDreamSubmitter;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.ReplaceEnter;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.GroupDreamInfo;
import com.lvmama.group.dao.GroupDreamDAO;
import com.lvmama.group.dao.GroupDreamSubmitterDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.logic.GrouponLogic;

public class GroupDreamServiceImpl implements GroupDreamService{

	private GroupDreamDAO groupDreamDAO;
	private GroupDreamSubmitterDAO groupDreamSubmitterDAO;
	private ComLogDAO comLogDAO;
	private ProdProductDAO prodProductDAO;
	private ComPlaceDAO comPlaceDAO;
	private GrouponLogic grouponLogic;
	@Override
	public GroupDream getGroupDream(Long dreamId) {
		return groupDreamDAO.selectByPrimaryKey(dreamId);
	}
	@Override
	public Integer selectRowCount(Map searchConds) {
		return groupDreamDAO.selectRowCount(searchConds);
	}
	@Override
    public List<GroupDream> getGroupDreams(Map param) {	
		  return  groupDreamDAO.getSupGroupDreams(param);
    } 
	@Override
	public Long addGroupDream (GroupDream groupDream,String operatorName){
		Long dreamId = groupDreamDAO.insert(groupDream);
		if(dreamId!=null){
			comLogDAO.insert("GROUP_DREAM",null,dreamId,operatorName,Constant.COM_LOG_GROUP_DREAM_EVENT.INSERT_GROUP_DREAM.name(),
				"新增团梦想信息",LogViewUtil.logNewStr(operatorName)+
				"-产品名称:"+groupDream.getProductName()+
				";最低团购价:"+groupDream.getLowDreamPriceDou()+
				";最高团购价"+groupDream.getHighDreamPriceDou()+
				";门市价格:"+groupDream.getMarketPriceDou()+";", null);
		}
		return dreamId;
	}
	/**
	 * addGroupDream回滚
	 * 
	 * @author: ranlongfei 2012-8-2 下午6:05:22
	 * @param groupDream
	 * @param operatorName
	 */
	@Override
	public void addGroupDreamRollback (GroupDream groupDream,String operatorName){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dreamId", groupDream.getDreamId());
		groupDreamDAO.deleteByPrimaryKey(param);
	}
 
	public void setSupGroupDreamDAO(GroupDreamDAO groupDreamDAO) {
		this.groupDreamDAO = groupDreamDAO;
	}
	@Override
	public void updateGroupDream(GroupDream groupDream,String operatorName)  {
		GroupDream  oldGroupDream=this.getGroupDream(groupDream.getDreamId());
		groupDreamDAO.updateByPrimaryKey(groupDream);
		
		StringBuffer editStr=new StringBuffer("");
		if(!oldGroupDream.getProductName().equals(groupDream.getProductName())){
			editStr.append(LogViewUtil.logEditStr("产品名称", 
					oldGroupDream.getProductName(), groupDream.getProductName()));
		}
		if(!oldGroupDream.getLowDreamPrice().equals(groupDream.getLowDreamPrice())){
			editStr.append(LogViewUtil.logEditStr("最低团购价", 
					oldGroupDream.getLowDreamPriceDou().toString(), groupDream.getLowDreamPriceDou().toString()));
		}
		if(!oldGroupDream.getHighDreamPrice().equals(groupDream.getHighDreamPrice())){
			editStr.append(LogViewUtil.logEditStr("最高团购价", 
					oldGroupDream.getHighDreamPriceDou().toString(), groupDream.getHighDreamPriceDou().toString()));
		}
		if(!oldGroupDream.getMarketPrice().equals(groupDream.getMarketPrice())){
			editStr.append(LogViewUtil.logEditStr("门市价格", 
					oldGroupDream.getMarketPriceDou().toString(), groupDream.getMarketPriceDou().toString()));
		}
		if(editStr.length()>0){
			comLogDAO.insert("GROUP_DREAM",null,groupDream.getDreamId(),operatorName,
					Constant.COM_LOG_GROUP_DREAM_EVENT.UPDATE_GROUP_DREAM.name(),
					"修改团梦想",operatorName+editStr,
			"GROUP_DREAM");
		}
	}
	
	/**
	 * 选定的信息是否被修改.
	 * @param oldList
	 * @param newList
	 * @param productId
	 * @return
	 */
	private Map isEditGroupDream(GroupDream oldGroupDream,GroupDream groupDream){
		Map<String,String> isEditMap=null;
		if(!oldGroupDream.getProductName().equals(groupDream.getProductName())){
			
		}
		return isEditMap;
	}
	@Override
	public void deleteGroupDream(GroupDream groupDream){
		groupDreamDAO.updateValidByPrimaryKey(groupDream);
	}

	public GroupDreamDAO getGroupDreamDAO() {
		return groupDreamDAO;
	}
	public void setGroupDreamDAO(GroupDreamDAO groupDreamDAO) {
		this.groupDreamDAO = groupDreamDAO;
	}
	
	public void setGroupDreamSubmitterDAO(
			GroupDreamSubmitterDAO groupDreamSubmitterDAO) {
		this.groupDreamSubmitterDAO = groupDreamSubmitterDAO;
	}
 
	@Override
	public Page getGroupDreamEnjoySubmitters(Page pageVO,Map queryParamMap) {
		Long dreamId = (Long) queryParamMap.get("dreamId");
		
		Long totalCount = groupDreamSubmitterDAO.countEnjoyDreamSubmitters(queryParamMap);
		pageVO.setTotalResultSize(totalCount);
		
		if(totalCount>0){
			Map map = new HashMap();
			map.putAll(queryParamMap);
			map.put("beginIndex", pageVO.getStartRows());
			map.put("endIndex", pageVO.getEndRows());
			List<GroupDreamSubmitter>  resultList = groupDreamSubmitterDAO.queryEnjoyDreamSubmitters(map);
			pageVO.setItems(resultList);
		}
		return pageVO;
	}

	@Override
	public GroupDream submitDream(Long dreamId, String email, String isEnjoy,
			String ipAddress) {
		
		GroupDream groupDream  = new GroupDream();
		
		//插入团梦想记录
		GroupDreamSubmitter  submitter = new GroupDreamSubmitter();
		submitter.setDreamId(dreamId);
		submitter.setEmail(email);
		submitter.setIpAddr(ipAddress);
		submitter.setIsEnjoy(isEnjoy);
		groupDreamSubmitterDAO.insert(submitter);
		//获取目前团梦想人数
		Map resultMap = groupDreamSubmitterDAO.countDreamSubmitNumsByDreamId(dreamId);
		Long enjoyCount =(Long) resultMap.get("ENJOY_COUNT");
		Long notEnjoyCount =(Long) resultMap.get("NOT_ENJOY_COUNT");
		Long totalJoinCount =(Long) resultMap.get("TOTAL_JOIN_COUNT");
		groupDream.setDreamId(dreamId);
		groupDream.setEnjoyCount(enjoyCount);
		groupDream.setNotEnjoyCount(notEnjoyCount);
		groupDream.setJoinTotalCount(totalJoinCount);
		return groupDream;
	}
	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	@Override
	public List<GroupDreamInfo> getCurrMonthDreamProducts() {
		Map params = new HashMap();
		List<GroupDreamInfo> returnList = new ArrayList<GroupDreamInfo>();
		List<GroupDream> resutlList = groupDreamDAO.queryDreamProducts(params);
		for (GroupDream dream : resutlList) {

			GroupDreamInfo god = new GroupDreamInfo();
			god.setDreamId(dream.getDreamId());
			god.setProductType(dream.getProductType());
			god.setProductName(dream.getProductName());
			god.setMarketPrice(dream.getMarketPrice());
			god.setEnjoyCount(dream.getEnjoyCount());
			god.setNotEnjoyCount(dream.getNotEnjoyCount());
			god.setLowDreamPrice(dream.getLowDreamPrice());
			god.setHighDreamPrice(dream.getHighDreamPrice());
			god.setIntroduction(ReplaceEnter.replaceEnterRn(dream.getIntroduction(), "DREAM"));
			god.setPicUrl(dream.getPicUrl());
			god.setCity(dream.getDest());
			returnList.add(god);
		}
		return returnList;
	}
	
	@Override
	public List<ProdProduct> queryOnlineProductInProductIds(List<Long> productIdList) {
		return prodProductDAO.queryOnlineProductInProductIds(productIdList);
	}
	@Override
	public List<ProdProduct> queryOnlineProductByParams(Map<String, Object> map) {
		return prodProductDAO.queryOnlineProductByParams(map);
	}

	@Override
	public Map<String, Object> getOnlineAndOffelineProductByChannel(Long page, Long pageSize) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> pageInfoMap = new HashMap<String, Object>();
		List<Map> returnList = new ArrayList<Map>();
		if (page == null || page <= 0) {
			page = 1L;
		}
		if (pageSize == null || pageSize <= 0) {

			pageSize = 10L;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		Long beginIndex = (page - 1) * pageSize + 1;

		Long endIndex = page * pageSize;

		params.put("beginIndex", beginIndex);
		params.put("endIndex", endIndex);
		Long totalRows = prodProductDAO.countOnlineAndOffelineProductByChannel(params);
		Long totalPage = totalRows % pageSize == 0 ? totalRows / pageSize : totalRows / pageSize + 1;
		if (page > totalPage) {
			page = totalPage;
		}
		pageInfoMap.put("totalRows", totalRows);
		pageInfoMap.put("totalPage", totalPage);
		pageInfoMap.put("page", page);
		pageInfoMap.put("pageSize", pageSize);

		List<Map> resultList = prodProductDAO.queryOnlineAndOffelineProductByChannel(params);
		for (Map rowMap : resultList) {
			ProdProduct pp = new ProdProduct();
			pp.setProductId(((Long) rowMap.get("PRODUCT_ID")));
			pp.setProductName((String) rowMap.get("PRODUCT_NAME"));
			pp.setSellPrice(rowMap.get("SELL_PRICE") == null ? null : ((Long) rowMap.get("SELL_PRICE")));
			pp.setMarketPrice(rowMap.get("MARKET_PRICE") == null ? null : ((Long) rowMap.get("MARKET_PRICE")));
			pp.setSubProductType((String) rowMap.get("SUB_PRODUCT_TYPE"));
			pp.setOfflineTime((Date) rowMap.get("OFFLINE_TIME"));
			pp.setProductType((String) rowMap.get("PRODUCT_TYPE"));
			pp.setOnlineTime((Date) rowMap.get("ONLINE_TIME"));
			pp.setSmallImage((String) rowMap.get("SMALL_IMAGE"));
			long offlineTime = 0;
			Map returnMap = new HashMap();
			returnMap.put("prodProduct", pp);
			returnMap.put("MANAGERRECOMMEND", rowMap.get("MANAGERRECOMMEND"));
			returnMap.put("CITY", rowMap.get("CITY"));
			returnMap.put("orderCount", rowMap.get("ORDER_COUNT"));
			if (pp.getMarketPriceYuan() > 0) {
				returnMap.put("discount", new BigDecimal(pp.getSellPriceYuan() / pp.getMarketPriceYuan() * 10).setScale(1, BigDecimal.ROUND_FLOOR).doubleValue());
			}
			// 是否团购结束
			returnMap.put("completeFlag", rowMap.get("COMPLETE_FLAG"));

			returnList.add(returnMap);
		}
		resultMap.put("recordList", returnList);
		resultMap.put("pageInfo", pageInfoMap);
		return resultMap;
	}

	@Override
	public Map<String, Object> getPrdJoinUsers(Long productId, Long page, Long pagesize) {
		//如果page和pageSize的值为空，重新赋值
		if(page==null || pagesize==null){
			page=1L;
			pagesize=5L;
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Map paramMap = new HashMap();
		paramMap.put("productId", productId);
		paramMap.put("beginIndex", (page - 1) * pagesize + 1);
		paramMap.put("endIndex", (page) * pagesize);
		Long count = prodProductDAO.countJoinUsersByProd(paramMap);
		returnMap.put("totalCount", count);
		try {
			List<Map<String,Object>> resultList = prodProductDAO.queryJoinUsersByProd(paramMap);
			if(resultList!=null&&resultList.size()>0){
				for (Map<String,Object> joinMap : resultList) {
					//截短产品名称
					String productName = (String) joinMap.get("PRODUCT_NAME");
					String subPrdName = StringUtil.subStringStr(productName, 25);
					joinMap.put("PRODUCT_NAME", subPrdName);
				}
			}
			returnMap.put("resultList", resultList);
			returnMap.put("succFlag", "Y");
		} catch (Exception e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error("getPrdJoinUsers", e);
			returnMap.put("succFlag", "N");

		}
		return returnMap;
	}

	@Override
	public Long countOrderByProduct(Long productId) {
		Map params = new HashMap();
		params.put("productId", productId);
		return prodProductDAO.countOrderByProduct(params);
	}
	@Override
	public Map<String,Object> getTodayGroupProduct(Long productId) {
		Map<String, Object> returnMap = grouponLogic.getTodayGroupProduct(productId);
		if (returnMap != null) {
			// 加载推荐产品的标的
			String key = "getTodayGroupProduct_recommendPrdPlace_" + productId;
			List<Place> recommendPrdPlace = (List<Place>) MemcachedUtil.getInstance().get(key);
			if (recommendPrdPlace == null) {
				recommendPrdPlace = comPlaceDAO.getComPlaceByProductId(productId);
				MemcachedUtil.getInstance().set(key, MemcachedUtil.getDateAfter(120),recommendPrdPlace);
			}
			returnMap.put("recommendPrdPlace", recommendPrdPlace);
		}
		return returnMap;
	}

	@Override
	public List<Map> getPrdTagByProductId(Long productId) {
		Map paramMap = new HashMap();
		paramMap.put("productId", productId);
		return prodProductDAO.queryPrdTagByProductId(paramMap);
	}
	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}
	public void setComPlaceDAO(ComPlaceDAO comPlaceDAO) {
		this.comPlaceDAO = comPlaceDAO;
	}
	public void setGrouponLogic(GrouponLogic grouponLogic) {
		this.grouponLogic = grouponLogic;
	}

}