package com.lvmama.tmall.service;

import java.util.*;

import org.apache.commons.lang.StringUtils;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.tmall.ProdTimePrice;
import com.lvmama.comm.bee.po.tmall.TaobaoProductSync;
import com.lvmama.comm.bee.po.tmall.TaobaoProductSyncPojo;
import com.lvmama.comm.bee.po.tmall.TaobaoTicketSku;
import com.lvmama.comm.bee.po.tmall.TaobaoTravelCombo;
import com.lvmama.comm.bee.po.tmall.TaobaoTravelComboType;
import com.lvmama.comm.bee.service.tmall.TaobaoProductSyncService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.PRODUCT_TYPE;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.logic.ProductTimePriceLogic;
import com.lvmama.tmall.dao.TaobaoProductSyncDAO;
import com.lvmama.tmall.dao.TaobaoTicketSkuDAO;
import com.lvmama.tmall.dao.TaobaoTravelComboDAO;
import com.lvmama.tmall.dao.TaobaoTravelComboTypeDAO;
import com.lvmama.tmall.logic.TaobaoSyncHelp;
import com.lvmama.tmall.logic.TicketItemHelp;
import com.lvmama.tmall.logic.TravelItemHelp;

public class TaobaoProductSyncServiceImpl implements TaobaoProductSyncService {
	private TaobaoProductSyncDAO taobaoProductSyncDAO;
	private TaobaoTicketSkuDAO taobaoTicketSkuDAO;
	private TaobaoTravelComboDAO taobaoTravelComboDAO;
	private TaobaoTravelComboTypeDAO taobaoTravelComboTypeDAO;
	private ProdProductDAO prodProductDAO;
	private ProdProductBranchDAO prodProductBranchDAO; 
	private ProductTimePriceLogic productTimePriceLogic;
	private ComLogService comLogService;

	@Override
	public List<TaobaoProductSyncPojo> getTaobaoTravelSyncList(
			Map<String, Object> pageMap) {
		List<TaobaoProductSyncPojo> list = taobaoProductSyncDAO.selectTaobaoTravelSyncList(pageMap);
		if (!list.isEmpty()) {
			// 获取产品类型
			Map<String, Object> params = new HashMap<String, Object>();
			for (TaobaoProductSyncPojo taobaoProductSyncPojo : list) {
				Long travelComboId = taobaoProductSyncPojo.getTravelComboId();
				if (travelComboId == null) {
					continue;
				}
				params.put("travelComboId", travelComboId);
				// 获取线路套餐类型
				List<TaobaoTravelComboType> comboTypes = taobaoTravelComboTypeDAO.selectTaobaoTravelComboType(params);
				taobaoProductSyncPojo.setComboTypes(comboTypes);
				// 获取套餐名字
				String branchNames = null;
				for (TaobaoTravelComboType ttct : comboTypes) {
					if (branchNames != null) {
						if (StringUtils.isNotEmpty(ttct.getBranchName())) {
							branchNames += "," + ttct.getBranchName();
						}
					} else {
						if (StringUtils.isNotEmpty(ttct.getBranchName())) {
							branchNames = ttct.getBranchName();
						}
					}
				}
				taobaoProductSyncPojo.setBranchName(branchNames);
			}
		}
		return list;
	}

	@Override
	public List<TaobaoProductSyncPojo> getTaobaoTicketSyncList(Map<String, Object> pageMap) {
		return taobaoProductSyncDAO.selectTaobaoTicketSyncList(pageMap);
	}
	
	@Override
	public List<Long> getTaobaoProductItemIdList(Map<String, Object> pageMap) {
		return taobaoProductSyncDAO.selectTaobaoProductItemIdList(pageMap);
	}
	
	@Override
	public Long getSeq() {
		return taobaoProductSyncDAO.selectSeq();
	}

    /**
     * 添加淘宝产品
     */
    public void insertTaobaoProductSync(TaobaoProductSync taobaoProductSync) {
        insertTaobaoProductSync(taobaoProductSync, null);
    }

	/**
	 * 添加淘宝产品
	 */
	@Override
	public void insertTaobaoProductSync(TaobaoProductSync taobaoProductSync, String userName) {
		// 判断是否是新增
		if (taobaoProductSync.getId() == null) {
			// 添加淘宝同步
			taobaoProductSyncDAO.insertTaobaoProductSync(taobaoProductSync);
			
			// 门票
			if (taobaoProductSync.getTbType().equals(TaobaoSyncHelp.TB_TYPE_TICKET)) {
				insetSystemLog("TAOBAO_TICKET_PROD", taobaoProductSync.getTbItemId(), "insertTaobaoProductSync", "添加淘宝同步信息", "添加淘宝同步信息，ItemId=" + taobaoProductSync.getTbItemId(), userName);
				
				// 线路
			} else if (taobaoProductSync.getTbType().equals(TaobaoSyncHelp.TB_TYPE_TRAVEL)) {
				insetSystemLog("TAOBAO_TRAVEL_PROD", taobaoProductSync.getTbItemId(), "insertTaobaoProductSync", "添加淘宝同步信息", "添加淘宝同步信息，ItemId=" + taobaoProductSync.getTbItemId(), userName);
			}
		} else {
			// 更新淘宝同步
			taobaoProductSyncDAO.updateTaobaoProductSync(taobaoProductSync);
			// 删除门票详细信息
			if (taobaoProductSync.getTbType().equals(TaobaoSyncHelp.TB_TYPE_TICKET)) {
				taobaoTicketSkuDAO.deleteTaobaoTicketSkuByTbProdSyncId(taobaoProductSync.getId());
				insetSystemLog("TAOBAO_TICKET_PROD", taobaoProductSync.getTbItemId(), "updateTaobaoProductSync", "更新淘宝同步信息", "更新淘宝同步信息，ItemId=" + taobaoProductSync.getTbItemId(), userName);
				
				// 删除线路详细信息
			} else if (taobaoProductSync.getTbType().equals(TaobaoSyncHelp.TB_TYPE_TRAVEL)) {
				//　删除线路套餐类别
				List<TaobaoTravelCombo> list = taobaoTravelComboDAO.selectTaobaoTravelComboList(taobaoProductSync.getId());
				for (TaobaoTravelCombo taobaoTravelCombo : list) {
					taobaoTravelComboTypeDAO.deleteTaobaoTravelComboTypeByTravelComboId(taobaoTravelCombo.getId());
				}
				// 删除线路套餐
				taobaoTravelComboDAO.deleteTaobaoTravelComboByTbProdSyncId(taobaoProductSync.getId());
				insetSystemLog("TAOBAO_TRAVEL_PROD", taobaoProductSync.getTbItemId(), "updateTaobaoProductSync", "更新淘宝同步信息", "更新淘宝同步信息，ItemId=" + taobaoProductSync.getTbItemId(), userName);
			}
		}
		// 不管是更新还是新增，都是要添加门票和线路明细的
		// 添加门票产品信息
		List<TaobaoTicketSku> skus = taobaoProductSync.getTicketSkus();
		if (skus != null && !skus.isEmpty()) {
			for (TaobaoTicketSku taobaoTicketSku : skus) {
				taobaoTicketSku.setTbProdSyncId(taobaoProductSync.getId());
				// 是否同步 1 为同步，0 为不同步， 默认是同步
				taobaoTicketSku.setIsSync("0");
				taobaoTicketSkuDAO.insertTaobaoTicketSku(taobaoTicketSku);
			}
		}
		// 添加线路产品套餐信息
		List<TaobaoTravelCombo> combos = taobaoProductSync.getTravelCombos();
		if (combos != null && !combos.isEmpty()) {
			for (TaobaoTravelCombo taobaoTravelCombo : combos) {
				taobaoTravelCombo.setTbProdSyncId(taobaoProductSync.getId());
				// 是否同步 1 为同步，0 为不同步， 默认是同步
				taobaoTravelCombo.setIsSync("0");
				taobaoTravelComboDAO.insertTaobaoTravelCombo(taobaoTravelCombo);
				// 添加线路套餐类型
				Long travelComboId = taobaoTravelCombo.getId();
				taobaoTravelComboTypeDAO.insertTaobaoTravelComboType(travelComboId, TaobaoSyncHelp.TB_COMBO_TYPE_MAN);
				taobaoTravelComboTypeDAO.insertTaobaoTravelComboType(travelComboId, TaobaoSyncHelp.TB_COMBO_TYPE_CHILD);
				taobaoTravelComboTypeDAO.insertTaobaoTravelComboType(travelComboId, TaobaoSyncHelp.TB_COMBO_TYPE_DIFF);
			}
		}
	}

    /**
     * 删除淘宝产品同步
     * @param taobaoProductSync 淘宝产品同步
     */
    private void deleteTaobaoProductSync(TaobaoProductSync taobaoProductSync) {
        // 删除门票详细信息
        if (taobaoProductSync.getTbType().equals(TaobaoSyncHelp.TB_TYPE_TICKET)) {
            taobaoTicketSkuDAO.deleteTaobaoTicketSkuByTbProdSyncId(taobaoProductSync.getId());
            insetSystemLog("TAOBAO_TICKET_PROD", taobaoProductSync.getTbItemId(), "deleteTaobaoProductSync", "删除淘宝同步产品信息", "删除淘宝同步产品信息，ItemId=" + taobaoProductSync.getTbItemId());

            // 删除线路详细信息
        } else if (taobaoProductSync.getTbType().equals(TaobaoSyncHelp.TB_TYPE_TRAVEL)) {
            //　删除线路套餐类别
            List<TaobaoTravelCombo> list = taobaoTravelComboDAO.selectTaobaoTravelComboList(taobaoProductSync.getId());
            for (TaobaoTravelCombo taobaoTravelCombo : list) {
                taobaoTravelComboTypeDAO.deleteTaobaoTravelComboTypeByTravelComboId(taobaoTravelCombo.getId());
            }
            // 删除线路套餐
            taobaoTravelComboDAO.deleteTaobaoTravelComboByTbProdSyncId(taobaoProductSync.getId());
            insetSystemLog("TAOBAO_TRAVEL_PROD", taobaoProductSync.getTbItemId(), "deleteTaobaoProductSync", "删除淘宝同步产品信息", "删除淘宝同步产品信息，ItemId=" + taobaoProductSync.getTbItemId());
        }
        // 删除判断同步消息
        deleteTaobaoProductSync(taobaoProductSync.getId());
    }

    /**
     * 添加系统日志
     */
	private void insetSystemLog(String objectType, Long itemId, String logType, String logName, String content) {
        insetSystemLog(objectType, itemId, logType, logName, content, null);
	}

    /**
     * 添加系统日志
     */
    private void insetSystemLog(String objectType, Long itemId, String logType, String logName, String content, String userName) {
        if (userName == null) {
            userName = "SYSTEM";
        }
        comLogService.insert(objectType, itemId, itemId, userName, logType, logName, content, "TAOBAO_PROD");
    }

	@Override
	public int updateTaobaoProductSync(TaobaoProductSync taobaoProductSync) {
		return taobaoProductSyncDAO.updateTaobaoProductSync(taobaoProductSync);
	}

	@Override
	public int deleteTaobaoProductSync(Long id) {
		return taobaoProductSyncDAO.deleteTaobaoProductSync(id);
	}

	@Override
	public TaobaoProductSync getTaobaoProductSync(Long id) {
		return taobaoProductSyncDAO.selectTaobaoProductSync(id);
	}
	
	@Override
	public List<TaobaoProductSync> getTaobaoProductSync(Map<String, Object> pageMap) {
		return taobaoProductSyncDAO.selectTaobaoProductSync(pageMap);
	}

	@Override
	public int updateAuctionStatus(TaobaoProductSync taobaoProductSync) {
		return taobaoProductSyncDAO.updateAuctionStatus(taobaoProductSync);
	}

	@Override
	public List<TaobaoProductSync> getTaobaoProductSyncByItemId(Long itemId) {
		return taobaoProductSyncDAO.selectTaobaoProductSyncByItemId(itemId);
	}

	@Override
	public Long getCountByItemId(Long itemId) {
		return taobaoProductSyncDAO.selectCountByItemId(itemId);
	}

	@Override
	public Integer getTaobaoTicketSyncCount(Map<String, Object> pageMap) {
		return taobaoProductSyncDAO.selectTaobaoTicketSyncCount(pageMap);
	}
	
	@Override
	public Integer getTaobaoTravelSyncCount(Map<String, Object> pageMap) {
		return taobaoProductSyncDAO.selectTaobaoTravelSyncCount(pageMap);
	}
	
	@Override
	public int updateTravelComboType(Long travelComboId, String prodBranchIds) {
		String[] str = prodBranchIds.split(",");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("travelComboId", travelComboId);
		List<TaobaoTravelComboType> ttctList = new ArrayList<TaobaoTravelComboType>();
		String pbis = null;
		for (String ps : str) {
			String[] vs = ps.split(":");
			params.put("comboType", vs[0]);
			List<TaobaoTravelComboType> list = taobaoTravelComboTypeDAO.selectTaobaoTravelComboType(params);
			TaobaoTravelComboType comboType = list.get(0);
			if (vs.length > 1 && StringUtils.isNotBlank(vs[1])) {
				Long prodBranchId = Long.valueOf(vs[1]);
				comboType.setProdBranchId(prodBranchId);
				if (pbis == null) {
					pbis = "" + prodBranchId;
				} else {
					pbis = pbis + "," + prodBranchId;
				}
			} else {
				comboType.setProdBranchId(null);
			}
			ttctList.add(comboType);
		}

		/*if (pbis != null) {
            // 判断是否有重复的产品类别ID
			params = new HashMap<String, Object>();
			params.put("not_travelComboId", travelComboId);
			params.put("prodBranchIds", pbis);
			Long count = taobaoTravelComboTypeDAO.selectTaobaoTravelComboType2CountByMap(params);
			if (count > 0) {
				return 0;
			}
		}*/
        for (TaobaoTravelComboType taobaoTravelComboType : ttctList) {
            taobaoTravelComboTypeDAO.updateTaobaoTravelComboType(taobaoTravelComboType);
        }
		return 1;
	}
	
	/**
	 * 更新淘宝门票 sku
	 */
	@Override
	public void updateTaobaoTicketSkuEffDates(Long productId, Long prodBranchId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("productId", productId);
        params.put("prodBranchId", prodBranchId);
        params.put("isSync", "1");
        List<TaobaoTicketSku> ticketList = taobaoTicketSkuDAO.selectTaobaoTicketSku(params);
        if (ticketList.isEmpty()) {
            return;
        }
        for (TaobaoTicketSku taobaoTicketSku : ticketList) {
            TaobaoProductSync taobaoProductSync = getTaobaoProductSync(taobaoTicketSku.getTbProdSyncId());
            updateTicketSku(taobaoProductSync, taobaoTicketSku, productId, prodBranchId, null);
        }
	}


    /**
     * 更新淘宝门票 sku
     */
    @Override
    public Boolean updateTaobaoTicketSkuEffDates(Long ticketSkuId) {
        return updateTaobaoTicketSkuEffDates(ticketSkuId, "");
    }
	
	/**
	 * 更新淘宝门票 sku
	 */
	@Override
	public Boolean updateTaobaoTicketSkuEffDates(Long ticketSkuId, String userName) {
		TaobaoTicketSku taobaoTicketSku = taobaoTicketSkuDAO.selectTaobaoTicketSku(ticketSkuId);
		TaobaoProductSync taobaoProductSync = taobaoProductSyncDAO.selectTaobaoProductSync(taobaoTicketSku.getTbProdSyncId());
		Long productId = taobaoTicketSku.getProductId();
		Long prodBranchId = taobaoTicketSku.getProdBranchId();
		if (productId == null || prodBranchId == null) {
			return false;
		}
		// 更新淘宝门票 sku
		return updateTicketSku(taobaoProductSync, taobaoTicketSku, productId, prodBranchId, userName);
	}
	
	/**
	 * 更新淘宝门票 sku 价格
	 * @param taobaoProductSync 淘宝产品同步类
	 * @param taobaoTicketSku   淘宝门票SKU
	 * @param productId         产品ID
	 * @param prodBranchId      产品类别ID
	 * @return      是否更新成功
	 */
	private Boolean updateTicketSku(TaobaoProductSync taobaoProductSync, TaobaoTicketSku taobaoTicketSku, Long productId, Long prodBranchId, String userName) {
		boolean isSuccess = false;
		String isSync = taobaoTicketSku.getIsSync();
		// 只有 同步状态为同步 的才能修改价格日历
		if (StringUtils.isNotBlank(isSync) && isSync.equals("1")) {
			// 获取时间日历
			List<ProdTimePrice> prodTimePrices = getProdTimePrices(productId, prodBranchId);
			// 去除 头尾的空格。
			prodTimePrices = TaobaoSyncHelp.initProdTimePriceList(prodTimePrices);
			boolean b = true;
            // 如果门票产品有库存限制，则更新将同步状态，改为不同步。
            for (ProdTimePrice prodTimePrice : prodTimePrices) {
				if (prodTimePrice.getDayStock() >= 0) {
					updateTicketIsSync(taobaoTicketSku.getId(), "0", taobaoProductSync.getTbItemId(), "SYSTEM");
					b = false;
					break;
				}
			}
			if (!b) return false;
			// 更新sku
			TicketItemHelp ticketItemHelp = new TicketItemHelp(taobaoProductSync, taobaoTicketSku);
            ticketItemHelp.setUserName(userName);
			ticketItemHelp.setComLogService(comLogService);
			isSuccess = ticketItemHelp.updateSkuEffDates(prodTimePrices);
		}
		return isSuccess;
	}
	
	/**
	 * job获取套餐ID
	 */
	@Override
	public List<Long> getTravelToTravelComboId(Map<String, Object> params) {
		return taobaoProductSyncDAO.selectTravelToTravelComboId(params);
	}
	
	/**
	 * 更新淘宝线路套餐
	 */
	@Override
	public void updateTaobaoTravelComboCalendar(Long productId, Long prodBranchId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		params.put("prodBranchId", prodBranchId);
		params.put("isSync", "1");
		// 更新线路
		List<Long> travelComboIds = getTravelToTravelComboId(params);
        if (travelComboIds.isEmpty()) {
            return;
        }
		// 获取时间日历
		for (Long travelComboId : travelComboIds) {
			updateTaobaoTravelComboCalendar(travelComboId);
		}
	}
	
	/**
	 * 更新淘宝线路套餐
	 */
	@Override
	public Boolean updateTaobaoTravelComboCalendar(Long travelComboId) {
		return updateTaobaoTravelComboCalendar(travelComboId, "");
	}

    /**
     * 更新淘宝线路套餐
     */
    @Override
    public Boolean updateTaobaoTravelComboCalendar(Long travelComboId, String userName) {
        TaobaoTravelCombo taobaoTravelCombo = taobaoTravelComboDAO.selectTaobaoTravelCombo(travelComboId);
        TaobaoProductSync taobaoProductSync = taobaoProductSyncDAO.selectTaobaoProductSync(taobaoTravelCombo.getTbProdSyncId());
        Long productId = taobaoTravelCombo.getProductId();
        if (productId == null) {
            return false;
        }
        return updateTravelComboCalendar(taobaoProductSync, taobaoTravelCombo, productId, userName);
    }
	
	/**
	 * 更新淘宝线路套餐
	 * @param taobaoProductSync 淘宝产品同步类
	 * @param taobaoTravelCombo 淘宝线路套餐类
	 * @param productId         产品ID
	 * @return  是否更新成功
	 */
	private Boolean updateTravelComboCalendar(TaobaoProductSync taobaoProductSync, TaobaoTravelCombo taobaoTravelCombo, Long productId, String userName) {
		boolean isSuccess = false;
		String isSync = taobaoTravelCombo.getIsSync();
		Long travelComboId = taobaoTravelCombo.getId();
		// 判断套餐是否能同步
		if (StringUtils.isNotEmpty(isSync) && isSync.equals("1")) {
			// 获取时间日历
			Map<String, List<ProdTimePrice>> ptpMap = getMapProdTimePrice(productId, travelComboId);
            boolean isUpdate = true;
            // 判断是否需要删除该套餐
            if (ptpMap == null || ptpMap.isEmpty()) {
                isUpdate = false;

                // 如果成人 和 儿童 都为空，那么删除该套餐
            } else if ((ptpMap.get(TaobaoSyncHelp.TB_COMBO_TYPE_MAN) == null || ptpMap.get(TaobaoSyncHelp.TB_COMBO_TYPE_MAN).isEmpty())
                    && (ptpMap.get(TaobaoSyncHelp.TB_COMBO_TYPE_CHILD) == null || ptpMap.get(TaobaoSyncHelp.TB_COMBO_TYPE_CHILD).isEmpty())) {
                isUpdate = false;
            }
            // 是否能更新
            if (isUpdate) {
                // 更新线路套餐
                TravelItemHelp travelItemHelp = new TravelItemHelp(taobaoProductSync, taobaoTravelCombo);
                travelItemHelp.setUserName(userName);
                travelItemHelp.setComLogService(comLogService);
                isSuccess = travelItemHelp.updateTravelComboCalendar(ptpMap);
            } else {
                // 删除淘宝线路 套餐
                deleteTaobaoTravelComboCalendar(taobaoProductSync, taobaoTravelCombo);
                // 删除淘宝线路产品
                deleteTaobaoTravelProduct(taobaoProductSync);
                isSuccess = true;
            }
		}
		return isSuccess;
	}
	
	/**
	 * 获取线路套餐（只获取在线的）
	 * @param productId     产品ID
	 * @param travelComboId 线路套餐ID
	 * @return  线路套餐类别map
	 */
	private Map<String, List<ProdTimePrice>> getMapProdTimePrice(Long productId, Long travelComboId) {
		Map<String, List<ProdTimePrice>> ptpMap = new HashMap<String, List<ProdTimePrice>>();
		// 获取套餐类型
		List<TaobaoTravelComboType> ttctList = taobaoTravelComboTypeDAO.selectTaobaoTravelComboTypeByTravelComboId(travelComboId);
		for (TaobaoTravelComboType taobaoTravelComboType : ttctList) {
			String comboType = taobaoTravelComboType.getComboType();
			Long prodBranchId = taobaoTravelComboType.getProdBranchId();
			// 添加套餐时间价格列表
			ptpMap.put(comboType, null);
			if (prodBranchId != null) {
				// 只获取在线的产品
				List<ProdTimePrice> list = getProdTimePrices(productId, prodBranchId, true);
				if (!list.isEmpty()) {
					ptpMap.put(comboType, list);
				}
			}
		}
		return ptpMap;
	}
	
	/**
	 * 获取90天内的价格时间列表
     * @param productId     产品ID
     * @param prodBranchId  产品类别ID
	 * @return  时间价格日历
	 */
	@Override
	public List<ProdTimePrice> getProdTimePrices(Long productId, Long prodBranchId) {
		return getProdTimePrices(productId, prodBranchId, false);
	}
	
	/**
	 * 获取90天内的价格时间列表
	 * @param productId     产品ID
	 * @param prodBranchId  产品类别ID
	 * @param isOnLine		判断是否在线
	 * @return  时间价格日历
	 */
	@Override
	public List<ProdTimePrice> getProdTimePrices(Long productId, Long prodBranchId, boolean isOnLine) {
		// 如果产品类别下线了，则不做同步
		List<TimePrice> timePrices = getTimePrices(productId, prodBranchId, isOnLine);
        return TaobaoSyncHelp.getProdTimePrices(timePrices, productId, prodBranchId);
	}
	
	/**
	 * 获取90天内的价格时间列表
	 * @param productId     产品ID
	 * @param prodBranchId  产品类别ID
	 * @return      价格日历列表
	 */
	private List<TimePrice> getTimePrices(Long productId, Long prodBranchId, boolean isOnLine) {
		Date beginDate;
		Date endDate;
		Calendar calendar = Calendar.getInstance();
		beginDate = DateUtil.toYMDDate(calendar.getTime());
		calendar.add(Calendar.DAY_OF_YEAR, 90);
		endDate = DateUtil.toYMDDate(calendar.getTime());
		
		return getTimePrices(productId, prodBranchId, beginDate, endDate, isOnLine);
	}
	
	public List<TimePrice> getTimePrices(Long productId, Long prodBranchId, Date beginDate, Date endDate, boolean isOnLine) {
		List<TimePrice> tList = Collections.emptyList();
		// 判断该销售类别是否在线
		if (isOnLine) {
			ProdProductBranch prodProductBranch = prodProductBranchDAO.selectByPrimaryKey(prodBranchId);
			if (prodProductBranch == null || !prodProductBranch.hasOnline()) {
				return tList;
			}
		}
		
		ProdProduct product = prodProductDAO.selectByPrimaryKey(productId);
		Date date = productTimePriceLogic.selectNearBranchTimePriceByBranchId(prodBranchId);
		if(date!=null && date.before(endDate)){
			if(beginDate.before(date)){ // 如果开始日期比指定日期要早。改成数据库当中能拿出来的最早日期
				beginDate = date;
			}
			int maxdays = DateUtil.getDaysBetween(beginDate, endDate);
			if(maxdays > product.getShowSaleDays()){
				maxdays = product.getShowSaleDays();
			}
			tList = productTimePriceLogic.getTimePriceList(productId, prodBranchId, maxdays, beginDate);
		}
		return tList;
	}

	/* 
	 * 更新淘宝线路团购天数
	 */
	@Override
	public void updateTaobaoTravelDuration(Long productId) {
        ProdProduct prod = prodProductDAO.selectProductDetailByProductType(productId, PRODUCT_TYPE.ROUTE.name());
        if (prod != null) {
            ProdRoute prodRoute = (ProdRoute) prod;
            // 提前确定成团小时数
            int aheadConfirmHours = prodRoute.getAheadConfirmHours();
            long duration;
            // 0 - 负数 都是提前一天
            if (aheadConfirmHours <= 0) {
                duration = 1;

                // 如果大于零 小于等于24 是提前2天
            } else if(aheadConfirmHours <= 24) {
                duration = 2;

            } else {
                duration = ((aheadConfirmHours - 1) / 24) + 2;
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("productId", productId);
            List<TaobaoTravelCombo> taobaoTravelCombos = taobaoTravelComboDAO.selectTaobaoTravelCombo(map);
            for (TaobaoTravelCombo taobaoTravelCombo : taobaoTravelCombos) {
                TaobaoProductSync taobaoProductSync = getTaobaoProductSync(taobaoTravelCombo.getTbProdSyncId());
                TravelItemHelp travelItemHelp = new TravelItemHelp(taobaoProductSync);
                travelItemHelp.setComLogService(comLogService);
                // 更新团购天数
                travelItemHelp.updateTravelItemDuration(duration);
            }
        }
	}
	
	/**
	 * 更新 淘宝门票 是否同步
	 */
	@Override
	public void updateTicketIsSync(Long ticketSkuId, String isSync, Long itemId, String operatorName) {
		TaobaoTicketSku taobaoTicketSku = taobaoTicketSkuDAO.selectTaobaoTicketSku(ticketSkuId);
		taobaoTicketSku.setIsSync(isSync);
		taobaoTicketSkuDAO.updateTaobaoTicketSku(taobaoTicketSku);
		StringBuilder sb = new StringBuilder();
		if (isSync.equals("0")) {
			sb.append("更新淘宝门票同步状态，同步状态为不同步。");
		} else {
			sb.append("更新淘宝门票同步状态，同步状态为同步。");
		}
		sb.append("itemId=").append(itemId).append(" ,");
		sb.append("prodBranchId=").append(taobaoTicketSku.getProdBranchId());
		comLogService.insert("TAOBAO_TICKET_PROD", itemId, itemId, operatorName, "updateTicketIsSync", "更新淘宝门票同步状态", sb.toString(), "TAOBAO_PROD");
	}
	
	/* 
	 * 更新 淘宝门票 是否同步
	 */
	@Override
	public void updateTicketIsSync(Long ticketSkuId, String isSync) {
		TaobaoTicketSku taobaoTicketSku = taobaoTicketSkuDAO.selectTaobaoTicketSku(ticketSkuId);
		taobaoTicketSku.setIsSync(isSync);
		taobaoTicketSkuDAO.updateTaobaoTicketSku(taobaoTicketSku);
	}
	
	/**
	 * 淘宝产品类别下线
	 */
	@Override
	public void updateTaobaoProdBranchAuctionStatus(Long productId, Long prodBranchId) {
        // 销售产品
        ProdProduct prodProduct = prodProductDAO.selectByPrimaryKey(productId);
		// 销售产品类别
		ProdProductBranch prodProductBranch = prodProductBranchDAO.selectByPrimaryKey(prodBranchId);
        // 淘宝产品类别下线(需要判断是否在线)
        updateTaobaoProdBranchAuctionStatus(prodProduct, prodProductBranch, true);
	}


    /**
     * 淘宝产品类别下线
     */
    private void updateTaobaoProdBranchAuctionStatus(ProdProduct prodProduct, ProdProductBranch prodProductBranch, boolean checkOnline) {
        Long prodBranchId = prodProductBranch.getProdBranchId();

        /*
            如果是在线，那么就不做操作。
            （checkOnline 是否需要判断类别在线，如果是产品下线的话，就不需要此判断，产品下线默认该产品下的所有类别都不能卖）
        */
        if (checkOnline && prodProductBranch.hasOnline()) {
            return;
        }
        // 获取产品类型
        Long productId = prodProduct.getProductId();
        String type = prodProduct.getProductType();

        // 判断是否 门票或者 线路
        if (type.equals(PRODUCT_TYPE.TICKET.name()) || type.equals(PRODUCT_TYPE.ROUTE.name())) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("productId", productId);
            params.put("prodBranchId", prodBranchId);
            // 门票
            if (type.equals(PRODUCT_TYPE.TICKET.name())) {
                // 获取门票的SKU
                List<TaobaoTicketSku> list = taobaoTicketSkuDAO.selectTaobaoTicketSku(params);
                // 存放被删除过SKU的淘宝产品同步ID
                Map<Long, TaobaoProductSync> tbProdSyncIds = new HashMap<Long, TaobaoProductSync>();
                // 循环删除下线的SKU
                for (TaobaoTicketSku taobaoTicketSku : list) {
                    Long tbProdSyncId = taobaoTicketSku.getTbProdSyncId();
                    // 获取淘宝产品同步类
                    TaobaoProductSync taobaoProductSync = getTaobaoProductSync(tbProdSyncId);
/*
                    // 根据 淘宝同步产品ID 获取sku数量
                    Map<String, Object> params2 = new HashMap<String, Object>();
                    params2.put("tbProdSyncId", tbProdSyncId);
                    Long aLong = taobaoTicketSkuDAO.selectTaobaoTicketSkuToCount(params2);
                    // 如果淘宝门票同步只有一个SKU了（就是本事查到），那么直接删除该淘宝产品
                    if (aLong == 1) {
                        // 下线淘宝上的产品
                        TicketItemHelp ticketItemHelp = new TicketItemHelp(taobaoProductSync);
                        ticketItemHelp.updateAuctionStatus("instock");
                        // 从库中删除淘宝产品
                        deleteTaobaoProductSync(taobaoProductSync);
                        continue;
                    }
*/
                    // 添加被删除过SKU的淘宝产品同步ID
                    tbProdSyncIds.put(taobaoProductSync.getId(), taobaoProductSync);
                    // 获取帮助类
                    TicketItemHelp ticketItemHelp = new TicketItemHelp(taobaoProductSync, taobaoTicketSku);
                    ticketItemHelp.setComLogService(comLogService);
                    // 删除淘宝上的SKU
                    ticketItemHelp.deleteSkuEffDates();
                    // 删除SKU类（从库中）
                    taobaoTicketSkuDAO.deleteTaobaoTicketSku(taobaoTicketSku.getId());
                }
                // 是否下线淘宝产品，如果 对应的门票产品没有 SKU，那么就将门票产品下线
                for (Long tbProdSyncId : tbProdSyncIds.keySet()) {
                    Map<String, Object> params2 = new HashMap<String, Object>();
                    params2.put("tbProdSyncId", tbProdSyncId);
                    Long aLong = taobaoTicketSkuDAO.selectTaobaoTicketSkuToCount(params2);
                    // 如果淘宝门票同步产品没有对应的SKU了，那么直接删除
                    if (aLong == 0) {
                        TaobaoProductSync taobaoProductSync = tbProdSyncIds.get(tbProdSyncId);
                        // 下线淘宝上的产品
                        TicketItemHelp ticketItemHelp = new TicketItemHelp(taobaoProductSync);
                        ticketItemHelp.updateAuctionStatus("instock");
                        // 从库中删除淘宝产品
                        deleteTaobaoProductSync(taobaoProductSync);
                    }
                }

                // 线路
            } else {
                // 如果有该类别则更新
                updateTaobaoTravelComboCalendar(productId, prodBranchId);
            }
        }
    }
	
	/* 
	 * 淘宝产品下线
	 */
	@Override
	public void updateTaobaoProdAuctionStatus(Long productId) {
		// 获取产品类型
		ProdProduct prodProduct = prodProductDAO.selectByPrimaryKey(productId);
		// 只更新下线
		if (prodProduct.isOnLine()) {
			return;
		}
        String type = prodProduct.getProductType();

		// 判断是否 门票
		if (type.equals(PRODUCT_TYPE.TICKET.name())) {
            // 门票
            List<ProdProductBranch> prodProductBranches = prodProductBranchDAO.getProductBranchByProductId(productId, null);
            for (ProdProductBranch prodProductBranch : prodProductBranches) {
                // 淘宝产品类别下线(不需要判断是否在线)
                updateTaobaoProdBranchAuctionStatus(prodProduct, prodProductBranch, false);
            }

            // 判断是否 线路
		} else if (type.equals(PRODUCT_TYPE.ROUTE.name())) {
            // 线路
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("productId", productId);
            List<TaobaoTravelCombo> taobaoTravelCombos = taobaoTravelComboDAO.selectTaobaoTravelCombo(params);
            // 存放被删除过SKU的淘宝产品同步ID
            Map<Long, TaobaoProductSync> tbProdSyncIds = new HashMap<Long, TaobaoProductSync>();
            for (TaobaoTravelCombo taobaoTravelCombo : taobaoTravelCombos) {
                // 获取淘宝产品同步类
                TaobaoProductSync taobaoProductSync = getTaobaoProductSync(taobaoTravelCombo.getTbProdSyncId());
                // 添加被删除淘宝产品同步ID
                tbProdSyncIds.put(taobaoProductSync.getId(), taobaoProductSync);
                // 删除淘宝线路套餐
                deleteTaobaoTravelComboCalendar(taobaoProductSync, taobaoTravelCombo);
            }

            // 是否下线淘宝产品，如果 对应的线路产品没有 套餐，那么就将线路产品下线
            for (Long tbProdSyncId : tbProdSyncIds.keySet()) {
                // 删除淘宝线路产品
                deleteTaobaoTravelProduct(tbProdSyncIds.get(tbProdSyncId));
            }
        }
	}

    /**
     * 删除淘宝线路套餐
     */
    private void deleteTaobaoTravelComboCalendar(TaobaoProductSync taobaoProductSync, TaobaoTravelCombo taobaoTravelCombo) {
        // 获取帮助类
        TravelItemHelp travelItemHelp = new TravelItemHelp(taobaoProductSync, taobaoTravelCombo);
        // 删除淘宝线路套餐
        travelItemHelp.deleteTravelComboCalendar();
        // 删除线路套餐（从库中）
        taobaoTravelComboTypeDAO.deleteTaobaoTravelComboTypeByTravelComboId(taobaoTravelCombo.getId());
        taobaoTravelComboDAO.deleteTaobaoTravelCombo(taobaoTravelCombo.getId());
    }

    /**
     * 删除淘宝线路产品
     */
    private void deleteTaobaoTravelProduct(TaobaoProductSync taobaoProductSync) {
        Long tbProdSyncId = taobaoProductSync.getId();
        int aLong = taobaoTravelComboDAO.selectTaobaoTravelComboList(tbProdSyncId).size();
        // 如果淘宝门票同步产品没有对应的SKU了，那么直接删除
        if (aLong == 0) {
            // 下线淘宝上的产品
            TravelItemHelp travelItemHelp = new TravelItemHelp(taobaoProductSync);
            travelItemHelp.updateTravelItemStatus("instock");
            // 从库中删除淘宝产品
            deleteTaobaoProductSync(taobaoProductSync);
        }
    }

	
	@Override
	public boolean updateTaobaoTicketInfo(Long itemId) {
		TaobaoSyncHelp help = new TaobaoSyncHelp(this);
		return help.updateTickeInfo(itemId);
	}

    @Override
    public boolean updateTaobaoTicketInfo(Long itemId, String userName) {
        TaobaoSyncHelp help = new TaobaoSyncHelp(this);
        help.setUserName(userName);
        return help.updateTickeInfo(itemId);
    }

	@Override
	public boolean updateTaobaoTravelInfo(Long itemId) {
        TaobaoSyncHelp help = new TaobaoSyncHelp(this);
        return help.updateTravelInfo(itemId);
	}

    @Override
    public boolean updateTaobaoTravelInfo(Long itemId, String userName) {
        TaobaoSyncHelp help = new TaobaoSyncHelp(this);
        help.setUserName(userName);
        return help.updateTravelInfo(itemId);
    }

    @Override
    public List<Long> getTicketSkuId(Map<String, Object> params) {
        return taobaoProductSyncDAO.queryTicketSkuId(params);
    }

	@Override
	public void syncTaobaoProduct() {
		TaobaoSyncHelp taobaoSyncHelp = new TaobaoSyncHelp(this);
		// 同步 淘宝商品
		taobaoSyncHelp.processTaobao();
	}

    /**
     * 同步淘宝门票产品
     */
    @Override
    public String syncTaobaoTicketProduct() {
        return syncTaobaoProduct(TaobaoSyncHelp.TB_TYPE_TICKET);
    }

    /**
     * 同步淘宝线路产品
     */
    @Override
    public String syncTaobaoTravelProduct() {
        return syncTaobaoProduct(TaobaoSyncHelp.TB_TYPE_TRAVEL);
    }

    /**
     * 同步淘宝产品
     */
    private String syncTaobaoProduct(String tbType) {
        TaobaoSyncHelp taobaoSyncHelp;
        // 处理门票
        if (StringUtils.equals(TaobaoSyncHelp.TB_TYPE_TICKET, tbType)) {
            taobaoSyncHelp = new TaobaoSyncHelp(this);
            taobaoSyncHelp.onlyProcessTicket();

            // 处理线路
        } else if(StringUtils.equals(TaobaoSyncHelp.TB_TYPE_TRAVEL, tbType)) {
            taobaoSyncHelp = new TaobaoSyncHelp(this);
            taobaoSyncHelp.onlyProcessTravel();
        } else {
            return "";
        }
        // 同步 淘宝商品
        taobaoSyncHelp.processTaobaoOnsale();
        // 删除 下线的淘宝产品
        for (Long itemId : taobaoSyncHelp.getDelItemIds()) {
            List<TaobaoProductSync> taobaoProductSyncs = getTaobaoProductSyncByItemId(itemId);
            for (TaobaoProductSync taobaoProductSync : taobaoProductSyncs) {
                deleteTaobaoProductSync(taobaoProductSync);
            }
        }
        // 添加日志
        addSyncTaobaoProductLog(taobaoSyncHelp, Integer.parseInt(tbType));
        // 添加返回数
        return "新增了" + taobaoSyncHelp.getAddItemIds().size() + "个淘宝产品，删除了" + taobaoSyncHelp.getDelItemIds().size() + "个淘宝产品。";
    }

    /**
     * 添加 同步淘宝产品日志
     * @param taobaoSyncHelp    同步帮助类
     * @param logType       1：门票；2：线路；
     */
    private void addSyncTaobaoProductLog(TaobaoSyncHelp taobaoSyncHelp, int logType) {
        String str;
        String objectType;
        String logTypeStr;
        String logName;
        boolean b = true;
        StringBuilder sb = new StringBuilder();
        if (logType == 1) {
            str = "门票";
            objectType = "TAOBAO_TICKET_PROD";
            logTypeStr = "syncTaobaoTicketProduct";
            logName = "同步淘宝门票产品";
        } else {
            str = "线路";
            objectType = "TAOBAO_TRAVEL_PROD";
            logTypeStr = "syncTaobaoTravelProduct";
            logName = "同步淘宝线路产品";
        }
        sb.append("新增了淘宝").append(str).append("产品：");
        for (Long itemId : taobaoSyncHelp.getAddItemIds()) {
            if (b) {
                b = false;
            } else {
                sb.append(",");
            }
            sb.append(itemId);
        }
        b = true;
        sb.append("；删除了淘宝").append(str).append("产品：");
        for (Long itemId : taobaoSyncHelp.getDelItemIds()) {
            if (b) {
                b = false;
            } else {
                sb.append(",");
            }
            sb.append(itemId);
        }
        insetSystemLog(objectType, (long) logType, logTypeStr, logName, sb.toString());
    }
	
	@Override
	public Long getTaobaoProductSyncCountByMap(Map<String, Object> params) {
		return taobaoProductSyncDAO.selectTaobaoProductSyncCountByMap(params);
	}

    @Override
    public void updateTravelIsSync(Long travelComboId, String isSync,
                                   Long itemId, String userName) {
        TaobaoTravelCombo taobaoTravelCombo = taobaoTravelComboDAO.selectTaobaoTravelCombo(travelComboId);
        taobaoTravelCombo.setIsSync(isSync);
        taobaoTravelComboDAO.updateTaobaoTravelCombo(taobaoTravelCombo);
        StringBuilder sb = new StringBuilder();
        if (isSync.equals("0")) {
            sb.append("更新淘宝线路同步状态，同步状态为不同步。");
        } else {
            sb.append("更新淘宝线路同步状态，同步状态为同步。");
        }
        sb.append("itemId=").append(itemId);
        comLogService.insert("TAOBAO_TRAVEL_PROD", itemId, itemId, userName, "updateTicketIsSync", "更新淘宝线路同步状态", sb.toString(), "TAOBAO_PROD");
    }
	
	public ProductTimePriceLogic getProductTimePriceLogic() {
		return productTimePriceLogic;
	}

	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}

	public TaobaoTicketSkuDAO getTaobaoTicketSkuDAO() {
		return taobaoTicketSkuDAO;
	}

	public void setTaobaoTicketSkuDAO(TaobaoTicketSkuDAO taobaoTicketSkuDAO) {
		this.taobaoTicketSkuDAO = taobaoTicketSkuDAO;
	}

	public TaobaoTravelComboDAO getTaobaoTravelComboDAO() {
		return taobaoTravelComboDAO;
	}

	public void setTaobaoTravelComboDAO(TaobaoTravelComboDAO taobaoTravelComboDAO) {
		this.taobaoTravelComboDAO = taobaoTravelComboDAO;
	}
	public TaobaoProductSyncDAO getTaobaoProductSyncDAO() {
		return taobaoProductSyncDAO;
	}
	
	public void setTaobaoProductSyncDAO(TaobaoProductSyncDAO taobaoProductSyncDAO) {
		this.taobaoProductSyncDAO = taobaoProductSyncDAO;
	}

	public ProdProductDAO getProdProductDAO() {
		return prodProductDAO;
	}

	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public ProdProductBranchDAO getProdProductBranchDAO() {
		return prodProductBranchDAO;
	}

	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}

	public TaobaoTravelComboTypeDAO getTaobaoTravelComboTypeDAO() {
		return taobaoTravelComboTypeDAO;
	}

	public void setTaobaoTravelComboTypeDAO(
			TaobaoTravelComboTypeDAO taobaoTravelComboTypeDAO) {
		this.taobaoTravelComboTypeDAO = taobaoTravelComboTypeDAO;
	}

}
