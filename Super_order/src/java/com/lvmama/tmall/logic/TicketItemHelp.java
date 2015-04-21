package com.lvmama.tmall.logic;

import com.kayak.telpay.mpi.util.StringUtils;
import com.lvmama.comm.bee.po.tmall.ProdTimePrice;
import com.lvmama.comm.bee.po.tmall.TaobaoProductSync;
import com.lvmama.comm.bee.po.tmall.TaobaoTicketSku;
import com.lvmama.comm.bee.service.tmall.TOPInterface;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.DateUtil;
import com.taobao.api.ApiException;
import com.taobao.api.domain.TicketItem;
import com.taobao.api.domain.TicketItemProcessResult;
import com.taobao.api.response.ItemSkuDeleteResponse;
import com.taobao.api.response.TicketItemUpdateResponse;
import com.taobao.api.response.TicketItemsGetResponse;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;


/**
 * 修改门票信息
 * 门票是 一个 类别 一个类别修改的
 *
 * @author linkai
 *
 */
public class TicketItemHelp {
    private static Log log = LogFactory.getLog(TicketItemHelp.class);

    private TaobaoProductSync taobaoProductSync;
    private TaobaoTicketSku taobaoTicketSku;
    private ComLogService comLogService;

    private Long aheadHour; // 最晚入园时间

    private String userName;// 操作人

    public TicketItemHelp(TaobaoProductSync taobaoProductSync) {
        this.taobaoProductSync = taobaoProductSync;
    }

    public TicketItemHelp(TaobaoProductSync taobaoProductSync, TaobaoTicketSku taobaoTicketSku) {
        this.taobaoProductSync = taobaoProductSync;
        this.taobaoTicketSku = taobaoTicketSku;
    }

    /**
     * 门票商品状态
     * @param auctionStatus	门票商品状态（onsale：上架，instock：仓库）
     */
    public boolean updateAuctionStatus(String auctionStatus) {
        Long itemId = taobaoProductSync.getTbItemId();
        boolean isSuccess = false;
        try {
            TicketItemUpdateResponse response = updateTicketItem(itemId, null, auctionStatus);
            TicketItemProcessResult result = response.getTicketItemProcessResult();
            isSuccess = response.isSuccess();
            // 返回成功
            if (isSuccess) {
                // 修改成功， 如果broken==false则表示成功发布商品，则通过item_id字段获取新生成商品id； 如果broken==true则表示发布商品遇到问题，则通过broken_reasons字段获取到失败原因。
                if (!result.getBroken()) {
                    log.info("update ticket auctionStatus success! itemId=" + itemId + ", Status=" + auctionStatus);
                    insetSystemLog(itemId, "updateSkuEffDates", "更新门票SKU", "更新门票SKU成功，ItemId=" + itemId);

                    // 修改失败
                } else {
                    log.info("update ticket auctionStatus failed! itemId=" + itemId + ", Status=" + auctionStatus + ", cause:" + Arrays.toString(result.getBrokenReasons().toArray()));
                    insetSystemLog(itemId, "updateAuctionStatus", "更新门票状态", "更新门票状态失败，ItemId=" + itemId + ", Status=" + auctionStatus + ", cause:" + Arrays.toString(result.getBrokenReasons().toArray()));
                    isSuccess = false;
                }
            } else {
                log.info("update ticket auctionStatus failed! itemId=" + itemId + ", Status=" + auctionStatus + ", ErrorMsg:" + response.getSubMsg());
                insetSystemLog(itemId, "updateAuctionStatus", "更新门票状态", "更新门票状态失败，ItemId=" + itemId + ", Status=" + auctionStatus + ", ErrorMsg:" + response.getSubMsg());
            }

        } catch (ApiException e) {
            e.printStackTrace();
            log.error("update ticket auctionStatus error! itemId=" + itemId + ", auctionStatus=" + auctionStatus);
        }

        // 提交消息
        return isSuccess;
    }

    /**
     * 删除sku
     */
    public boolean deleteSkuEffDates() {
        Long itemId = taobaoProductSync.getTbItemId();
        String tbPidVid = taobaoTicketSku.getTbPidVid();
        String outerId = taobaoTicketSku.getTbOuterId();
        boolean isSuccess = false;
        // 新的sku
        String sku = null;
        try {
            // 删除SKU
            sku = getSkuEffDates(itemId, tbPidVid, outerId, null);
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("delete ticket sku error, get sku failed! itemId=" + itemId + ", outerId" + outerId + ", tbPidVid=" + tbPidVid, e);
        }
        // 如果sku为null
        if (sku == null) return false;
        try {
            TicketItemUpdateResponse response = updateTicketItem(itemId, sku, null);
            TicketItemProcessResult result = response.getTicketItemProcessResult();
            isSuccess = response.isSuccess();
            // 返回成功
            if (isSuccess) {
                // 删除成功， 如果broken==false则表示成功发布商品，则通过item_id字段获取新生成商品id； 如果broken==true则表示发布商品遇到问题，则通过broken_reasons字段获取到失败原因。
                if (!result.getBroken()) {
                    log.info("delete ticket sku success! itemId=" + itemId + ", outerId=" + outerId + ", tbPidVid=" + tbPidVid);
                    insetSystemLog(itemId, "deleteSkuEffDates", "删除门票SKU", "删除门票SKU成功，itemId=" + itemId + ", outerId=" + outerId + ", tbPidVid=" + tbPidVid);

                    // 删除失败
                } else {
                    log.info("delete ticket sku failed! itemId=" + itemId + ", outerId=" + outerId + ", tbPidVid=" + tbPidVid + ", cause:" + Arrays.toString(result.getBrokenReasons().toArray()) + ", sku=" + sku);
                    insetSystemLog(itemId, "deleteSkuEffDates", "删除门票SKU", "删除门票SKU失败，itemId=" + itemId + ", outerId=" + outerId + ", tbPidVid=" + tbPidVid + ", cause:" + Arrays.toString(result.getBrokenReasons().toArray()));
                    isSuccess = false;
                }
            } else {
                log.info("delete ticket sku failed! itemId=" + itemId + ", outerId=" + outerId + ", tbPidVid=" + tbPidVid + ", ErrorMsg:" + response.getSubCode() + "-" + response.getSubMsg() + ", sku=" + sku);
                insetSystemLog(itemId, "deleteSkuEffDates", "删除门票SKU", "删除门票SKU失败，itemId=" + itemId + ", outerId=" + outerId + ", tbPidVid=" + tbPidVid + ", ErrorMsg:" + response.getSubCode() + "-" + response.getSubMsg() );
            }
        } catch (ApiException e) {
            log.error("delete ticket sku error! itemId=" + itemId + ", outerId=" + outerId + ", tbPidVid=" + tbPidVid + ", sku=" + sku, e);
        }
        return isSuccess;
    }

    /**
     * 更新sku中的入园信息
     * @param prodTimePrices    价格日历
     */
    public boolean updateSkuEffDates(List<ProdTimePrice> prodTimePrices) {
        Long itemId = taobaoProductSync.getTbItemId();
        String tbPidVid = taobaoTicketSku.getTbPidVid();
        String outerId = taobaoTicketSku.getTbOuterId();
        // 如果时间价格为空，则返回
        if (prodTimePrices == null || prodTimePrices.isEmpty()) {
            log.error("update ticket sku failed, prodTimePricesList is null! itemId=" + itemId + ", outerId" + outerId + ", tbPidVid=" + tbPidVid);
            return false;
        }
        // 初始化最大提前入园时间
        initAheadHour(prodTimePrices);
        // 是否去除当前天的日历表
        initProdTimePrice(prodTimePrices, aheadHour);

        boolean isSuccess = false;
        // 新的sku
        String sku = null;
        try {
            sku = getSkuEffDates(itemId, tbPidVid, outerId, prodTimePrices);
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("update ticket sku error, get sku failed! itemId=" + itemId + ", outerId" + outerId + ", tbPidVid=" + tbPidVid, e);
        }
        // 如果sku为null
        if (sku == null) return false;
        try {
            TicketItemUpdateResponse response = updateTicketItem(itemId, sku, null);
            TicketItemProcessResult result = response.getTicketItemProcessResult();
            isSuccess = response.isSuccess();
            // 返回成功
            if (isSuccess) {
                // 修改成功， 如果broken==false则表示成功发布商品，则通过item_id字段获取新生成商品id； 如果broken==true则表示发布商品遇到问题，则通过broken_reasons字段获取到失败原因。
                if (!result.getBroken()) {
                    log.info("update ticket sku success! itemId=" + itemId + ", outerId=" + outerId + ", tbPidVid=" + tbPidVid + ", sku=" + sku);
                    insetSystemLog(itemId, "updateSkuEffDates", "更新门票SKU", "更新门票SKU成功，ItemId=" + itemId + ", outerId=" + outerId + ", tbPidVid=" + tbPidVid);

                    // 修改失败
                } else {
                    log.info("update ticket sku failed! itemId=" + itemId + ", outerId=" + outerId + ", tbPidVid=" + tbPidVid + ", cause:" + Arrays.toString(result.getBrokenReasons().toArray()) + ", sku=" + sku);
                    insetSystemLog(itemId, "updateSkuEffDates", "更新门票SKU", "更新门票SKU失败，itemId=" + itemId + ", outerId=" + outerId + ", tbPidVid=" + tbPidVid + ", cause:" + Arrays.toString(result.getBrokenReasons().toArray()));
                    isSuccess = false;
                }
            } else {
                log.info("update ticket sku failed! itemId=" + itemId + ", outerId=" + outerId + ", tbPidVid=" + tbPidVid + ", ErrorMsg:" + response.getSubCode() + "-" + response.getSubMsg() + ", sku=" + sku);
                insetSystemLog(itemId, "updateSkuEffDates", "更新门票SKU", "更新门票SKU失败，itemId=" + itemId + ", outerId=" + outerId + ", tbPidVid=" + tbPidVid + ", ErrorMsg:" + response.getSubMsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("update ticket sku error! itemId=" + itemId + ", outerId=" + outerId + ", tbPidVid=" + tbPidVid + ", sku=" + sku, e);
        }
        return isSuccess;
    }

    /**
     * 获取最大提前入园时间
     * @param prodTimePrices    价格日历
     */
    private void initAheadHour(List<ProdTimePrice> prodTimePrices) {
        // 获取最大提前入园时间
        Long tempAheadHour;
        for (ProdTimePrice prodTimePrice : prodTimePrices) {
            tempAheadHour = prodTimePrice.getAheadHour();
            if (tempAheadHour != null) {
                if (aheadHour != null) {
                    if (aheadHour < tempAheadHour) {
                        aheadHour = tempAheadHour;
                    }
                } else {
                    aheadHour = tempAheadHour;
                }
            }
        }
    }

    /**
     * 是否去除当前天的日历表
     *
     * @param prodTimePrices    价格日历
     * @param aheadHour         入园提前小时数
     */
    private void initProdTimePrice(List<ProdTimePrice> prodTimePrices, long aheadHour) {
        // 获取电子票类型
        String ticketType = taobaoTicketSku.getTbTicketType();
        // 将分钟数转换为小时数
        aheadHour = aheadHour / 60;
        // 如果是实体票，需要加上2个小时（淘宝票类型，1：实体票；2：电子票；）
        if (ticketType.equals("1")) {
            aheadHour = aheadHour + 2;
        }
        long aheadAtHour, aheadAtMinute;
        if (aheadHour > 0) {
            // 得出小时数
            aheadAtHour = 24 - (aheadHour % 24);
            aheadAtMinute = 0;
        } else {

            aheadAtHour = aheadHour * -1;
            aheadAtMinute = 0;
        }
        // 判断当前的时间是否能存在
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, (int)aheadAtHour);
        c.set(Calendar.MINUTE, (int)aheadAtMinute);
        c.set(Calendar.SECOND, 0);
        Date cDate = new Date();
        // 如果大于入园时间则删除当前天
        if (cDate.after(c.getTime())) {
            if (prodTimePrices.size() > 0) {
                prodTimePrices.remove(0);
            }
        }
    }

    private void insetSystemLog(Long itemId, String logType, String logName, String content) {
        if (comLogService != null) {
            if (StringUtils.isEmpty(userName)) {
                userName = "SYSTEM";
            }
            comLogService.insert("TAOBAO_TICKET_PROD", itemId, itemId, userName, logType, logName, content, "TAOBAO_PROD");
        }
    }

    /**
     * 更新sku中的入园信息
     *
     * @param itemId        淘宝产品ID
     * @param tbPidVid      pid_vid
     * @param outId         outer_id
     * @param prodTimePrices    价格日历
     * @throws ApiException
     */
    private String getSkuEffDates(Long itemId, String tbPidVid, String outId, List<ProdTimePrice> prodTimePrices) throws ApiException {
        String sku;
        // 获取远端SKU
        sku = getRemoteSku(itemId);
        // 更新SKU
        sku = getUpdateSku(sku, tbPidVid, outId, prodTimePrices);
        // 修改门票信息
        return sku;
    }

    /**
     * 提交SKU
     * @param itemId        淘宝产品ID
     * @param sku           sku
     * @param auctionStatus 状态
     * @throws ApiException
     */
    private TicketItemUpdateResponse updateTicketItem(Long itemId, String sku, String auctionStatus) throws ApiException {
        return TOPInterface.updateTaobaoTicketTtem(itemId, sku, auctionStatus);
    }

    /**
     * 获取SKU
     * @param numIid    淘宝产品ID
     * @return  淘宝上的sku
     * @throws ApiException
     */
    private String getRemoteSku(Long numIid) throws ApiException {
        TicketItemsGetResponse response = TOPInterface.findTaobaoTicketItems(String.valueOf(numIid));
        List<TicketItem> ticketList = response.getTicketItems();
        if (ticketList.isEmpty()) {
            return null;
        }
        return ticketList.get(0).getSkus();
    }

    /**
     * 获取要更新sku，以及删除SKU
     * @param oldSku        旧的SKU
     * @param tbPidVid      piv_vid
     * @param prodTimePrices    价格日历
     * @return  新的SKU
     */
    public String getUpdateSku(String oldSku, String tbPidVid, String outId, List<ProdTimePrice> prodTimePrices) {
        // 根据时间，获取新的sku
        String effDates = getEffDates(oldSku, tbPidVid, outId, prodTimePrices);
        if (StringUtils.isBlank(effDates)) {
            return JsonHelpUtil.deleteJson(oldSku, "SKU", tbPidVid);
        }
        return JsonHelpUtil.updateJson(oldSku, effDates, "SKU", tbPidVid, "effDates");
    }


    /**
     * 获取入园时间（将单个日期时间改为范围日期）
     * @param prodTimePrices    价格日历
     * @return  入园价格
     */
    private String getEffDates(String oldSku, String tbPidVid, String outId, List<ProdTimePrice> prodTimePrices) {
        // 价格分组
        Map<String, Object> effDates = skuPricesGroup(prodTimePrices);

        int num = 0;
        // 获取最高的分组数
        Set<String> nums = effDates.keySet();
        for (String str : nums) {
            int tmepNum = Integer.parseInt(str);
            if (num < tmepNum) {
                num = tmepNum;
            }
        }
        // 添加其他outerId的日期
        JSONObject jsonObject = JsonHelpUtil.path2JsonObject(oldSku, "SKU", tbPidVid, "effDates");
        String[] jsonKeys = JsonHelpUtil.getJsonKeys(jsonObject);
        // 获取入园时间的Key
        for (String typeKey : jsonKeys) {
            // 获取 商家编码
            String outerId = JsonHelpUtil.path2String(jsonObject, typeKey, "outerId").trim();
            if (!StringUtils.equals(outerId, outId)) {
                num++;
                effDates.put(String.valueOf(num), JsonHelpUtil.path2JsonObject(jsonObject, typeKey));
            }
        }
        // 如果effDates空，则返回null
        if (effDates.isEmpty()) {
            return null;
        }
        return JSONObject.fromObject(effDates).toString();
    }

    /**
     * 价格分组
     */
    private Map<String, Object> skuPricesGroup(List<ProdTimePrice> prodTimePrices) {
        Map<String, Object> effDates;
        if (prodTimePrices == null || prodTimePrices.isEmpty()) {
            effDates = new HashMap<String, Object>();
        } else {
            // 首先判断价格是否一致
            if (checkTime(prodTimePrices)) {
                effDates = timeHandle(prodTimePrices);

                // 判断星期数是否一致
            } else if (checkWeek(prodTimePrices)) {
                effDates = weekHandle(prodTimePrices);

                // 自动分时间段
            } else {
                effDates = autoHandle(prodTimePrices);
            }
        }
        return effDates;
    }

    /**
     * 对比时间是否全部一致
     * @param prodTimePrices    价格日历
     * @return  判断时间
     */
    private boolean checkTime(List<ProdTimePrice> prodTimePrices) {
        boolean b = true;
        Long priceCheck = null;
        Long price;
        for (ProdTimePrice prodTimePrice : prodTimePrices) {
            price = prodTimePrice.getPrice();
            if (price == null) {
                b = false;
                break;
            }
            if (priceCheck == null) {
                priceCheck = price;
            } else {
                if (!priceCheck.equals(price)) {
                    b = false;
                    break;
                }
            }
        }
        return b;
    }

    /**
     * 判断星期数是否一致
     * @param prodTimePrices    价格日历
     * @return  检查是否按时间来区分
     */
    private boolean checkWeek(List<ProdTimePrice> prodTimePrices) {
        boolean b = true;
        Long price;
        String week;
        Map<String, Long> map = new HashMap<String, Long>();
        for (ProdTimePrice prodTimePrice : prodTimePrices) {
            week = getWeek(prodTimePrice.getSpecDate());
            price = prodTimePrice.getPrice();
            if (map.containsKey(week)) {
                Long weekPrice = map.get(week);
                if (!((price == null && weekPrice == null) || (price != null && weekPrice != null && price.equals(weekPrice)))) {
                    b = false;
                    break;
                }
            } else {
                map.put(week, price);
            }
        }
        return b;
    }

    /**
     * 根据统一时间来处理
     * @param prodTimePrices    价格日历
     * @return  时间处理
     */
    private Map<String, Object> timeHandle(List<ProdTimePrice> prodTimePrices) {
        Map<String, Object> jsonEffDate;
        ProdTimePrice prodTimePrice;
        String outerId;
        String ticketType;
        String startDate;
        String endDate;
        Long price;
        Long dayStock;

        prodTimePrice = prodTimePrices.get(0);
        // 商家编码
        outerId = taobaoTicketSku.getTbOuterId();
        // 获取最开始的时间
        startDate = getStartDate(prodTimePrices);
        // 获取最后的时间
        endDate = getEndDate(prodTimePrices);
        // 价钱
        price = prodTimePrices.get(0).getPrice();
        // 门票类型 （淘宝票类型，1：实体票；2：电子票；）
        ticketType = taobaoTicketSku.getTbTicketType();
        // 库存
        dayStock = getDayStock(prodTimePrice.getDayStock(), prodTimePrices);

//		jsonEffDate = new JsonEffDate();
        jsonEffDate = initJsonEffDate();
        // 入园基本信息
        initEffDateBase(jsonEffDate, outerId, price, dayStock);
        // 入园时间
        initEffDate(jsonEffDate, startDate, endDate, "1");
        // 入园限制
        initTimeLimit(jsonEffDate, ticketType, aheadHour);

        // 添加节点
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("1", jsonEffDate);
        return root;
    }

    /**
     * 根据星期数来处理
     * @param prodTimePrices    价格日历
     * @return  按星期来处理
     */
    private Map<String, Object> weekHandle(List<ProdTimePrice> prodTimePrices) {
        Map<String, WeekPrices> map = new HashMap<String, WeekPrices>();
        for (ProdTimePrice prodTimePrice : prodTimePrices) {
            // 获取价钱
            Long price = prodTimePrice.getPrice();
            if (price == null) {
                continue;
            }
            // 获取星期数
            String week = getWeek(prodTimePrice.getSpecDate());
            if (map.containsKey(week)) {
                WeekPrices weekPrices = map.get(week);
                Long weekPrice = weekPrices.prices;
                // 如果价钱一样，则获取对应的星期数
                if (weekPrice != null && price.equals(weekPrice)) {
                    weekPrices.data.add(prodTimePrice);
                }
            } else {
                WeekPrices weekPrices = new WeekPrices();
                weekPrices.prices = price;
                weekPrices.data = new ArrayList<ProdTimePrice>();
                weekPrices.data.add(prodTimePrice);
                map.put(week, weekPrices);
            }
        }

        String outerId;
        String ticketType;
        String startDate;
        String endDate;

        // 商编码
        outerId = taobaoTicketSku.getTbOuterId();
        // 门票类型（淘宝票类型，1：实体票；2：电子票；）
        ticketType = taobaoTicketSku.getTbTicketType();
        // 获取最开始的时间
        startDate = getStartDate(prodTimePrices);
        // 获取最后的时间
        endDate = getEndDate(prodTimePrices);

        Set<String> weeks = map.keySet();
        Map<Long, Map<String, Object>> effMap = new HashMap<Long, Map<String, Object>>();
        for (String week : weeks) {
            WeekPrices weekPrices = map.get(week);
            // 获取价钱一样的时间价格
            Long price = weekPrices.prices;
            if (price != null) {
                if (effMap.containsKey(price)) {
                    ProdTimePrice prodTimePrice;
                    long dayStock;

                    Map<String, Object> jsonEffDate = effMap.get(price);


                    // 添加星期数
                    addWeek(jsonEffDate, week);
                    // 添加库存
                    prodTimePrice = weekPrices.data.get(0);
                    dayStock = getDayStock(prodTimePrice.getDayStock(), weekPrices.data);
                    addDayStock(jsonEffDate, dayStock);
                } else {
                    ProdTimePrice prodTimePrice;
                    long dayStock;

                    prodTimePrice = weekPrices.data.get(0);
                    // 库存
                    dayStock = getDayStock(prodTimePrice.getDayStock(), weekPrices.data);

                    Map<String, Object> jsonEffDate = initJsonEffDate();
                    // 入园基本信息
                    initEffDateBase(jsonEffDate, outerId, price, dayStock);
                    // 入园时间
                    initEffDate(jsonEffDate, startDate, endDate, "1");
                    // 入园限制
                    initTimeLimit(jsonEffDate, ticketType, aheadHour);
                    // 添加星期数
                    addWeek(jsonEffDate, week);
                    effMap.put(price, jsonEffDate);
                }
            }
        }
        // 添加节点
        Map<String, Object> root = new HashMap<String, Object>();
        int count = 1;
        for (Map<String, Object> jsonEffDate : effMap.values()) {
            root.put(String.valueOf(count++), jsonEffDate);
        }
        return root;
    }

    /**
     * 根据时间分段处理
     * @param prodTimePrices    价格日历
     * @return  自动按时间分段
     */
    private Map<String, Object> autoHandle(List<ProdTimePrice> prodTimePrices) {
        // 添加节点
        Map<String, Object> root;
        String outerId;
        String ticketType;

        String startDate = null;
        String endDate = null;
        Long oldPrice = null;

        root = new HashMap<String, Object>();
        // 商编码
        outerId = taobaoTicketSku.getTbOuterId();
        // 门票类型
        ticketType = taobaoTicketSku.getTbTicketType();
        long stockCount = 0;
        int count = 1;
        for (ProdTimePrice prodTimePrice : prodTimePrices) {
            Long price = prodTimePrice.getPrice();
            if (stockCount != -1) {
                stockCount += prodTimePrice.getDayStock();
            }
            // 如果 当前价钱 和 旧的价钱都等于null 则直接跳过
            if (price == null && oldPrice == null) {
                continue;

                // 当前价钱为null，旧的价钱不为null，  获取 当前价钱 不 等于旧的价钱
            } else if ((price == null && oldPrice != null) || (price != null && oldPrice != null && !oldPrice.equals(price))) {
                // 初始化消息
                Map<String, Object> jsonEffDate = initJsonEffDate();
                // 入园基本信息
                initEffDateBase(jsonEffDate, outerId, oldPrice, getDayStock(stockCount));
                // 入园时间
                initEffDate(jsonEffDate, startDate, endDate, "1");
                // 入园限制
                initTimeLimit(jsonEffDate, ticketType, aheadHour);
                root.put(String.valueOf(count++), jsonEffDate);

                // 初始化 旧价钱
                oldPrice = null;
            }
            // 价钱
            if (price != null && oldPrice == null) {
                // 旧的价钱
                oldPrice = prodTimePrice.getPrice();
                // 开始时间
                startDate = DateUtil.formatDate(prodTimePrice.getSpecDate(), "yyyy-MM-dd");
                // 初始化库存
                stockCount = prodTimePrice.getDayStock();
            }
            // 最后时间
            endDate = DateUtil.formatDate(prodTimePrice.getSpecDate(), "yyyy-MM-dd");
        }
        if (oldPrice != null) {
            Map<String, Object> jsonEffDate = initJsonEffDate();
            // 入园基本信息
            initEffDateBase(jsonEffDate, outerId, oldPrice, getDayStock(stockCount));
            // 入园时间
            initEffDate(jsonEffDate, startDate, endDate, "1");
            // 入园限制
            initTimeLimit(jsonEffDate, ticketType, aheadHour);
            root.put(String.valueOf(count), jsonEffDate);
        }
        return root;
    }

    private String getStartDate(List<ProdTimePrice> prodTimePrices) {
        Date date = prodTimePrices.get(0).getSpecDate();
        return DateUtil.formatDate(date, "yyyy-MM-dd");
    }

    private String getEndDate(List<ProdTimePrice> prodTimePrices) {
        Date date = prodTimePrices.get(prodTimePrices.size() - 1).getSpecDate();
        return DateUtil.formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 1：星期一；2：星期二；3：星期三；4：星期四；5：星期五；6：星期六；7：星期日；
     * @param date  时间
     * @return  星期数
     */
    public String getWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK);
        if (week == 1) {
            week = 7;
        } else {
            week--;
        }
        return String.valueOf(week);
    }

    /**
     * 初始化基础信息
     * @param jsonEffDate   json
     * @param outerId	商家编码
     * @param price		价格
     * @param inventory	库存
     */
    public void initEffDateBase(Map<String, Object> jsonEffDate, String outerId, Long price, Long inventory) {
        jsonEffDate.put("type", "1");
        jsonEffDate.put("outerId", outerId);
        jsonEffDate.put("price", (price / 100));
        jsonEffDate.put("inventory", inventory);
        jsonEffDate.put("tag", 1);
    }

    /**
     * 初始化入园时间
     * @param jsonEffDate   json
     * @param startDate	开始时间
     * @param endDate	结束时间
     * @param effDays	入园日期开始后多少天有效，例如：从买家指定的入园日期开始后当天有效（设置为：1）
     */
    @SuppressWarnings("unchecked")
    public void initEffDate(Map<String, Object> jsonEffDate, String startDate, String endDate, String effDays) {
        Map<String, Object> effDate = (Map<String, Object>) jsonEffDate.get("effDate");
        effDate.put("startDate", startDate);
        effDate.put("endDate", endDate);
        effDate.put("effDays", effDays);
    }

    /**
     * 初始化 入园限制
     * 入园限制：请至少在入园前1天的，12点前指定入园日期（1， 12 ，0）
     * 如果是电子票产品，提前预订小时数和我们主站该产品的提前预订小时数一致；
     * 如果是实体票产品，提前预订小时数在我们主站该产品的提前预订小时数上增加 2 个小时（因为客服处理需要时间）；
     * @param jsonEffDate   json
     * @param ticketType	门票类型：1，实体票；2，电子票；
     * @param aheadHour		预约时间分钟数
     */
    @SuppressWarnings("unchecked")
    public void initTimeLimit(Map<String, Object> jsonEffDate, String ticketType, long aheadHour) {
        Map<String, Object> timeLimit = (Map<String, Object>) jsonEffDate.get("timeLimit");
        Map<String, Object> limit = (Map<String, Object>) timeLimit.get("limit");
        limit.put("type", "1");
        // 将分钟数转换为小时数
        aheadHour = aheadHour / 60;
        // 如果是实体票，需要加上2个小时（淘宝票类型，1：实体票；2：电子票；）
        if (ticketType.equals("1")) {
            aheadHour = aheadHour + 2;
        }
        String aheadDays, aheadAtHour, aheadAtMinute;
        if (aheadHour > 0) {
            // 计算出天数
            long day = (aheadHour / 24) + 1;
            // 得出小时数
            long hour = 24 - (aheadHour % 24);

            aheadDays = String.valueOf(day);
            aheadAtHour = String.valueOf(hour);
            aheadAtMinute = "0";
        } else {
            long hour = aheadHour * -1;

            aheadDays = "0";
            aheadAtHour = String.valueOf(hour);
            aheadAtMinute = "0";
        }
        limit.put("aheadDays", aheadDays);
        limit.put("aheadAtHour", aheadAtHour);
        limit.put("aheadAtMinute", aheadAtMinute);
    }

    /**
     * 添加 星期数
     * @param jsonEffDate   json
     * @param week          星期数
     */
    @SuppressWarnings("unchecked")
    public void addWeek(Map<String, Object> jsonEffDate, String week) {
        Map<String, Object> effDate = (Map<String, Object>) jsonEffDate.get("effDate");
        List<String> weeks = (List<String>) effDate.get("weeks");
        if (weeks != null) {
            if (!weeks.contains(week)) {
                weeks.add(week);
                // 排序
                Collections.sort(weeks);
            }
        } else {
            weeks = new ArrayList<String>();
            weeks.add(week);
            effDate.put("weeks", weeks);
        }
    }

    public Map<String, Object> initJsonEffDate() {
        Map<String, Object> jsonEffDate = new HashMap<String, Object>();
        jsonEffDate.put("type", null);		// 有效期类型，0-非指定日票，1-指定日票，2-年卡
        jsonEffDate.put("price", null);		// 价格
        jsonEffDate.put("inventory", null);	// 库存，无限制：10000；
        jsonEffDate.put("outerId", null);	// 商家编码
        jsonEffDate.put("tag", null);

        Map<String, Object> effDate = new HashMap<String, Object>();
        effDate.put("startDate", null);		// 有效期时间段开始时间,null代表未设置
        effDate.put("endDate", null);		// 有效期时间段结束时间,null代表未设置
        effDate.put("weeks", null);			// 有效期周,1~7代表周一到周日，null代表未设置
        effDate.put("startHour", null);		// 有效期开始时间小时,null代表未设置
        effDate.put("startMinute", null);	// 有效期开始时间分钟,null代表未设置
        effDate.put("endHour", null);		// 有效期结束时间小时,null代表未设置
        effDate.put("endMinute", null);		// 有效期结束时间分钟,null代表未设置
        effDate.put("effDays", null);		// xx后n天内有效的天数，购买后、出票后、开卡后n天内有效,null代表未设置
        jsonEffDate.put("effDate", effDate);

        Map<String, Object> timeLimit = new HashMap<String, Object>();
        Map<String, Object> limit = new HashMap<String, Object>();
        limit.put("type", null);			// 入园时间限制类型,0-不限，1-提前n天的n点n分，2-提前n小时n分钟
        limit.put("aheadDays", null);		// 入园时间限制类型为提前n天的n点n分时才使用，入园时间提前n天的天数
        limit.put("aheadAtHour", null);		// 入园时间限制类型为提前n天的n点n分时才使用，入园时间在n点n分之前的小时
        limit.put("aheadAtMinute", null);	// 入园时间限制类型为提前n天的n点n分时才使用，入园时间在n点n分之前的分钟
        limit.put("aheadHours", null);		// 入园时间限制类型为提前n小时n分时才使用，入园时间提前n小时的小时
        limit.put("aheadMinutes", null);	// 入园时间限制类型为提前n小时n分时才使用，入园时间提前n分钟的分钟
        timeLimit.put("limit", limit);

        // 有效期类型为年卡时才使用
        Map<String, Object> autoActivate = new HashMap<String, Object>();
        autoActivate.put("type", null);		// 是否有最晚自动开卡时间,0-无，1-有最晚自动开卡
        autoActivate.put("time", null);		// 天数,无最晚自动开发时间时为null
        timeLimit.put("autoActivate", autoActivate);

        jsonEffDate.put("timeLimit", timeLimit);
        return jsonEffDate;
    }

    /**
     * 添加 库存
     *
     * @param jsonEffDate   json
     * @param dayStock      库存
     */
    public void addDayStock(Map<String, Object> jsonEffDate, Long dayStock) {
        Long oldDayStock = (Long) jsonEffDate.get("inventory");
        if (oldDayStock != null) {
            if (oldDayStock == 10000) {
                return;
            } else {
                oldDayStock += dayStock;
                jsonEffDate.put("inventory", oldDayStock);
            }
        } else {
            jsonEffDate.put("inventory", dayStock);
        }
    }

    private Long getDayStock(Long dayStock) {
        long count = 0;
        if (dayStock != null) {
            if (dayStock == -1) {
                count = 10000L;
            } else {
                count = dayStock;
            }
        } else {
            count = 10000L;
        }
        return count;
    }

    private Long getDayStock(Long dayStock, List<ProdTimePrice> prodTimePrices) {
        long count = 0;
        if (dayStock != null) {
            if (dayStock == -1) {
                count = 10000L;
            } else {
                for (ProdTimePrice prodTimePrice : prodTimePrices) {
                    long d = prodTimePrice.getDayStock();
                    count += d;
                }
            }
        } else {
            count = 10000L;
        }
        return count;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    class WeekPrices {
        public Long prices;			// 售价
        public List<ProdTimePrice> data;
    }

    public ComLogService getComLogService() {
        return comLogService;
    }

    public void setComLogService(ComLogService comLogService) {
        this.comLogService = comLogService;
    }



}
