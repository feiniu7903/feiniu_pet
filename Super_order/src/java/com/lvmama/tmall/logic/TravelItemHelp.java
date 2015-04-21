package com.lvmama.tmall.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.taobao.api.domain.TravelItems;
import com.taobao.api.response.TravelItemsGetResponse;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.tmall.ProdTimePrice;
import com.lvmama.comm.bee.po.tmall.TaobaoProductSync;
import com.lvmama.comm.bee.po.tmall.TaobaoTravelCombo;
import com.lvmama.comm.bee.service.tmall.TOPInterface;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.taobao.api.ApiException;
import com.taobao.api.response.TravelItemsUpdateResponse;

/**
 * 修改线路信息
 * 线路是一次修改所有类别
 *
 * @author linkai
 *
 */
public class TravelItemHelp {
    private static Log log = LogFactory.getLog(TravelItemHelp.class);

    private TaobaoProductSync taobaoProductSync;
    private TaobaoTravelCombo taobaoTravelCombo;
    private ComLogService comLogService;
    private String userName; // 操作人

    public TravelItemHelp(TaobaoProductSync taobaoProductSync) {
        this.taobaoProductSync = taobaoProductSync;
    }

    public TravelItemHelp(TaobaoProductSync taobaoProductSync, TaobaoTravelCombo taobaoTravelCombo) {
        this.taobaoProductSync = taobaoProductSync;
        this.taobaoTravelCombo = taobaoTravelCombo;
    }

    /**
     * 更新套餐
     *
     * @param ptpMap	套餐类型 时间价格表
     * @return  是否更新成功
     */
    public boolean updateTravelComboCalendar(Map<String, List<ProdTimePrice>> ptpMap) {
        Long itemId = taobaoProductSync.getTbItemId();
        // 获取 时间日历
        String comboPriceCalendar = getComboPriceCalendar(ptpMap);
        String pidVid = taobaoTravelCombo.getTbPid() + ":" + taobaoTravelCombo.getTbVid();
        boolean isSuccess = false;
        try {
            TravelItemsUpdateResponse response = updateTravelItem(itemId, pidVid, comboPriceCalendar, null, null);
            isSuccess = response.isSuccess();
            if (isSuccess) {
                log.info("update travel comboPriceCalendar success! itemId=" + itemId + ", comboPriceCalendar=" + comboPriceCalendar);
                insetSystemLog(itemId, "updateTravelComboCalendar", "更新线路套餐信息", "更新线路套餐信息成功，ItemId=" + itemId);
            } else {
                log.info("update travel comboPriceCalendar failed! itemId=" + itemId + ", comboPriceCalendar=" + comboPriceCalendar + ", ErrorMsg:" + response.getSubMsg());
                insetSystemLog(itemId, "updateTravelComboCalendar", "更新线路套餐信息", "更新线路套餐信息失败，ItemId=" + itemId + ", ErrorMsg:" + response.getSubMsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("update travel comboPriceCalendar error! itemId=" + itemId, e);
        }
        return isSuccess;
    }

    /**
     * 更新下线状态
     *
     * @param approveStatus	商品上传后的状态。可选值:onsale(出售中),instock(仓库中);默认值:onsale。
     */
    public boolean updateTravelItemStatus(String approveStatus) {
        Long itemId = taobaoProductSync.getTbItemId();
        boolean isSuccess = false;
        try {
            TravelItemsUpdateResponse response = updateTravelItem(itemId, null, null, approveStatus, null);
            isSuccess = response.isSuccess();
            if (isSuccess) {
                log.info("update travel approveStatus success! itemId=" + itemId + ", approveStatus=" + approveStatus);
                insetSystemLog(itemId, "updateTravelItemStatus", "更新线路状态", "更新淘宝产品状态成功，ItemId=" + itemId + ", Status=" + approveStatus);
            } else {
                log.info("update travel approveStatus failed! itemId=" + itemId + ", approveStatus=" + approveStatus + ", ErrorMsg:" + response.getSubMsg());
                insetSystemLog(itemId, "updateTravelItemStatus", "更新线路状态", "更新淘宝产品状态失败，ItemId=" + itemId + ", Status=" + approveStatus + ", ErrorMsg:" + response.getSubMsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("update travel approveStatus error! itemId=" + itemId, e);
        }
        return isSuccess;
    }

    /**
     * 删除线路套餐
     * @return 是否删除成功
     */
    public boolean deleteTravelComboCalendar() {
        Long itemId = taobaoProductSync.getTbItemId();
        // 获取 时间日历
        String pidVid = taobaoTravelCombo.getTbPid() + ":" + taobaoTravelCombo.getTbVid();
        boolean isSuccess = false;
        try {
            String outId = deleteproductIdInOutId(taobaoTravelCombo.getProductId());
            TravelItemsUpdateResponse response = updateTravelItem(itemId, pidVid, outId);
            isSuccess = response.isSuccess();
            if (isSuccess) {
                log.info("delete travel comboPriceCalendar success! itemId=" + itemId + ",pidVid=" + pidVid);
                insetSystemLog(itemId, "deleteTravelComboCalendar", "删除线路套餐信息", "删除线路套餐信息成功，ItemId=" + itemId);
            } else {
                log.info("delete travel comboPriceCalendar failed! itemId=" + itemId + ",pidVid=" + pidVid + ", ErrorMsg:" + response.getSubMsg());
                insetSystemLog(itemId, "updateTravelComboCalendar", "删除线路套餐信息", "删除线路套餐信息失败，itemId=" + itemId + ",pidVid=" + pidVid + ", ErrorMsg:" + response.getSubMsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("delete travel comboPriceCalendar error! itemId=" + itemId + ",pidVid=" + pidVid, e);
        }
        return isSuccess;
    }

    /**
     * 删除outId中的productId
     * @param productId 产品ID
     * @return  返回删除后的OutId
     */
    private String deleteproductIdInOutId(Long productId) {
        Long itemId = taobaoProductSync.getTbItemId();
        StringBuilder sb = new StringBuilder();
        TravelItemsGetResponse response;
        try {
            response = TOPInterface.findTaobaoTravelItems(itemId);
            if (response.isSuccess() && response.getTravelItems() != null) {
                TravelItems travelItems = response.getTravelItems();
                String outerId = travelItems.getOuterId();
                String[] strs = outerId.split(",|，");
                boolean b = true;
                for (String str : strs) {
                    if (StringUtils.equals(str, String.valueOf(productId))) {
                        continue;
                    }
                    if (b) {
                        b = false;
                    } else {
                        sb.append(",");
                    }
                    sb.append(str);
                }
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("find TaobaoTravelItems outerId error! itemId=" + itemId, e);
        }
        return sb.toString();
    }

    /**
     * 更新最晚成团提前天数
     *
     * @param duration	最晚成团提前天数，最小0天，最大为300天。
     */
    public boolean updateTravelItemDuration(Long duration) {
        Long itemId = taobaoProductSync.getTbItemId();
        boolean isSuccess = false;
        try {
            TravelItemsUpdateResponse response = updateTravelItem(itemId, null, null, null, duration);
            isSuccess = response.isSuccess();
            if (isSuccess) {
                log.info("update travel duration success! itemId=" + itemId + ", duration=" + duration);
                insetSystemLog(itemId, "updateTravelItemDuration", "更新最晚成团提前天数", "更新最晚成团提前天数成功，ItemId=" + itemId + ", duration=" + duration);
            } else {
                log.info("update travel duration failed! itemId=" + itemId + ", duration=" + duration + ", ErrorMsg:" + response.getSubMsg());
                insetSystemLog(itemId, "updateTravelItemDuration", "更新最晚成团提前天数", "更新最晚成团提前天数失败，ItemId=" + itemId + ", duration=" + duration + ", ErrorMsg:" + response.getSubMsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("update travel duration error! itemId=" + itemId, e);
        }
        return isSuccess;
    }

    private void insetSystemLog(Long itemId, String logType, String logName, String content) {
        if (comLogService != null) {
            if (StringUtils.isEmpty(userName)) {
                userName = "SYSTEM";
            }
            comLogService.insert("TAOBAO_TRAVEL_PROD", itemId, itemId, "SYSTEM", logType, logName, content, "TAOBAO_PROD");
        }
    }

    /**
     * 获取套餐价格日历
     */
    private String getComboPriceCalendar(Map<String, List<ProdTimePrice>> ptpMap) {
        Map<String, Object> root = new HashMap<String, Object>();
        // 初始化 线路套餐 对象
        Map<String, Object> jsonCombo = initCombos();
        // 初始化 线路套餐
        String tbComboName = taobaoTravelCombo.getTbComboName();
        Long tbPid = taobaoTravelCombo.getTbPid();
        Long tbVid = taobaoTravelCombo.getTbVid();

        initJsonCombo(jsonCombo, tbComboName, tbPid, tbVid);
        // 设置 时间日历
        initJsonPriceCalendar(jsonCombo, ptpMap);
        // 添加 线路套餐
        addJsonCombo(root, jsonCombo);
        return JSONObject.fromObject(root).toString();
    }


    /**
     * 修改线路信息
     *
     * @param comboPriceCalendar	Json串，全量更新套餐价格日历信息（针对旅游度假线路的销售属性），定义了两个套餐日历价格
     * @param approveStatus			商品上传后的状态。可选值:onsale(出售中),instock(仓库中);默认值:onsale。
     * @param duration				最晚成团提前天数，最小0天，最大为300天。
     * @throws ApiException
     */
    private TravelItemsUpdateResponse updateTravelItem(Long itemId, String pidVid, String comboPriceCalendar, String approveStatus, Long duration, String outId) throws ApiException {
        return TOPInterface.updateTaobaoTravelItems(itemId, pidVid, comboPriceCalendar, approveStatus, duration, outId);
    }

    /**
     * 修改线路信息
     *
     * @param comboPriceCalendar	Json串，全量更新套餐价格日历信息（针对旅游度假线路的销售属性），定义了两个套餐日历价格
     * @param approveStatus			商品上传后的状态。可选值:onsale(出售中),instock(仓库中);默认值:onsale。
     * @param duration				最晚成团提前天数，最小0天，最大为300天。
     * @throws ApiException
     */
    private TravelItemsUpdateResponse updateTravelItem(Long itemId, String pidVid, String comboPriceCalendar, String approveStatus, Long duration) throws ApiException {
        return TOPInterface.updateTaobaoTravelItems(itemId, pidVid, comboPriceCalendar, approveStatus, duration, null);
    }

    /**
     * 删除套餐
     */
    private TravelItemsUpdateResponse updateTravelItem(Long itemId, String pidVid, String outId) throws ApiException {
        return updateTravelItem(itemId, pidVid, null, null, null, outId);
    }

    private Map<String, Object> initCombos() {
        Map<String, Object> combos = new HashMap<String, Object>();
        combos.put("combo_name", null);
        combos.put("pid", null);
        combos.put("vid", null);
        combos.put("price_calendar", null);
        return combos;
    }

    /**
     * 初始化
     * @param jsonCombo
     * @param comboName
     * @param pid
     * @param vid
     */
    private void initJsonCombo(Map<String, Object> jsonCombo, String comboName, Long pid, Long vid) {
        jsonCombo.put("combo_name", comboName);
        jsonCombo.put("pid", pid);
        jsonCombo.put("vid", vid);
    }

    /**
     * 设置时间日历
     */
    private void initJsonPriceCalendar(Map<String, Object> jsonCombo, Map<String, List<ProdTimePrice>> ptpMap) {
        // 将 线上时间转换为日历
//		List<Map<String, Object>> jsonPriceCalendars = timePrice2PriceCalendar(prodTimePrices);
        TravelComboTypeHelp comboTypeHelp = new TravelComboTypeHelp(ptpMap);
        jsonCombo.put("price_calendar", comboTypeHelp.getJsonPriceCalendar());
    }

    /**
     * 添加 套餐
     * @param root
     * @param jsonCombo
     */
    @SuppressWarnings("unchecked")
    private void addJsonCombo(Map<String, Object> root, Map<String, Object> jsonCombo) {
        if (root.get("combos") != null) {
            List<Map<String, Object>> jsonCombos = (List<Map<String, Object>>) root.get("combos");
            jsonCombos.add(jsonCombo);
        } else {
            List<Map<String, Object>> jsonCombos = new ArrayList<Map<String,Object>>();
            jsonCombos.add(jsonCombo);
            root.put("combos", jsonCombos);
        }
    }

    public ComLogService getComLogService() {
        return comLogService;
    }

    public void setComLogService(ComLogService comLogService) {
        this.comLogService = comLogService;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
