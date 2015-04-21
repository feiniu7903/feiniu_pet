package com.lvmama.comm.bee.service.tmall;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.tmall.ProdTimePrice;
import com.lvmama.comm.bee.po.tmall.TaobaoProductSync;
import com.lvmama.comm.bee.po.tmall.TaobaoProductSyncPojo;

public interface TaobaoProductSyncService {
    /**
     * 添加淘宝产品
     */
    void insertTaobaoProductSync(TaobaoProductSync taobaoProductSync, String userName);

    /**
     * 添加淘宝产品
     */
    void insertTaobaoProductSync(TaobaoProductSync taobaoProductSync);

    int updateTaobaoProductSync(TaobaoProductSync taobaoProductSync);

    int updateAuctionStatus(TaobaoProductSync taobaoProductSync);

    int deleteTaobaoProductSync(Long id);

    TaobaoProductSync getTaobaoProductSync(Long id);

    List<TaobaoProductSync> getTaobaoProductSync(Map<String, Object> pageMap);

    List<TaobaoProductSync> getTaobaoProductSyncByItemId(Long itemId);

    Long getCountByItemId(Long itemId);

    Integer getTaobaoTravelSyncCount(Map<String, Object> pageMap);

    List<TaobaoProductSyncPojo> getTaobaoTravelSyncList(Map<String, Object> pageMap);

    Integer getTaobaoTicketSyncCount(Map<String, Object> pageMap);

    List<TaobaoProductSyncPojo> getTaobaoTicketSyncList(Map<String, Object> pageMap);

    Long getSeq();

    int updateTravelComboType(Long travelComboId, String prodBranchIds);

    void updateTicketIsSync(Long ticketSkuId, String isSync);

    void updateTicketIsSync(Long ticketSkuId, String isSync, Long itemId, String operatorName);

    List<Long> getTaobaoProductItemIdList(Map<String, Object> pageMap);

    /**
     * 同步淘宝产品（门票，线路）
     */
    void syncTaobaoProduct();

    /**
     * 同步淘宝门票产品
     * @return 返回消息
     */
    String syncTaobaoTicketProduct();

    /**
     * 同步淘宝线路产品
     * @return 返回消息
     */
    String syncTaobaoTravelProduct();

    Long getTaobaoProductSyncCountByMap(Map<String, Object> params);

    List<Long> getTicketSkuId(Map<String, Object> params);

    /**
     * 更新淘宝门票 sku
     * @param productId
     * @param prodBranchId
     */
    void updateTaobaoTicketSkuEffDates(Long productId, Long prodBranchId);

    /**
     * 更新淘宝线路套餐
     * @param productId
     * @param prodBranchId
     */
    void updateTaobaoTravelComboCalendar(Long productId, Long prodBranchId);

    /**
     * 更新淘宝线路团购天数
     * @param productId
     */
    void updateTaobaoTravelDuration(Long productId);

    /**
     * 更新门票信息
     * @param itemId
     */
    boolean updateTaobaoTicketInfo(Long itemId);

    /**
     * 更新门票信息
     * @param itemId
     * @param userName 操作人
     */
    boolean updateTaobaoTicketInfo(Long itemId, String userName);

    /**
     * 更新线路信息
     * @param itemId
     */
    boolean updateTaobaoTravelInfo(Long itemId);

    /**
     * 更新线路信息
     * @param itemId
     * @param userName
     */
    boolean updateTaobaoTravelInfo(Long itemId, String userName);

    /**
     * 淘宝产品类别下线
     * @param productId
     * @param prodBranchId
     */
    void updateTaobaoProdBranchAuctionStatus(Long productId, Long prodBranchId);

    /**
     * 淘宝产品下线
     * @param productId
     */
    void updateTaobaoProdAuctionStatus(Long productId);


    /**
     * 获取时间价格列表
     * @param productId
     * @param prodBranchId
     * @param beginDate
     * @param endDate
     * @param isOnLine
     * @return
     */
    List<TimePrice> getTimePrices(Long productId, Long prodBranchId, Date beginDate, Date endDate, boolean isOnLine);

    /**
     * 获取90天内的价格时间列表
     * @param productId
     * @param prodBranchId
     * @param isOnLine		判断是否在线
     * @return
     */
    List<ProdTimePrice> getProdTimePrices(Long productId, Long prodBranchId, boolean isOnLine);

    /**
     * 获取90天内的价格时间列表
     * @param productId
     * @param prodBranchId
     * @return
     */
    List<ProdTimePrice> getProdTimePrices(Long productId, Long prodBranchId);

    /**
     * JOB 获取可以更新的套餐ID
     * @param params
     * @return
     */
    List<Long> getTravelToTravelComboId(Map<String, Object> params);

    /**
     * 更新套餐类型
     * @param travelComboId
     * @return
     */
    Boolean updateTaobaoTravelComboCalendar(Long travelComboId);

    /**
     * 更新套餐类型
     */
    Boolean updateTaobaoTravelComboCalendar(Long travelComboId, String userName);

    /**
     * 更新线路同步
     * @param travelComboId
     * @param isSync
     * @param itemId
     * @param userName
     */
    void updateTravelIsSync(Long travelComboId, String isSync, Long itemId, String userName);

    /**
     * 更新门票价格日历
     */
    Boolean updateTaobaoTicketSkuEffDates(Long ticketSkuId, String userName);

    /**
     * 更新门票价格日历
     */
    Boolean updateTaobaoTicketSkuEffDates(Long ticketSkuId);

}
