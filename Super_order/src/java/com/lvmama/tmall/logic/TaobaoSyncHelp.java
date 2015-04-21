package com.lvmama.tmall.logic;

import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.tmall.ProdTimePrice;
import com.lvmama.comm.bee.po.tmall.TaobaoProductSync;
import com.lvmama.comm.bee.po.tmall.TaobaoTicketSku;
import com.lvmama.comm.bee.po.tmall.TaobaoTravelCombo;
import com.lvmama.comm.bee.service.tmall.TOPInterface;
import com.lvmama.comm.bee.service.tmall.TaobaoProductSyncService;
import com.lvmama.comm.utils.DateUtil;
import com.taobao.api.ApiException;
import com.taobao.api.domain.*;
import com.taobao.api.response.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * 淘宝帮助类
 *
 * @author linkai
 *
 */
public class TaobaoSyncHelp {

    private static Log log = LogFactory.getLog(TaobaoSyncHelp.class);

    // 查询淘宝产品状态
    private static final String FIND_STATUS_ONSALE = "onsale";
    private static final String FIND_STATUS_INVENTORY = "inventory";

    // 更新淘宝的状态 （可选值:onsale(出售中),instock(仓库中);默认值:onsale。）
    public static final String APPROVE_STATUS_ONSALE = "onsale";
    public static final String APPROVE_STATUS_INSTOCK = "instock";

    // 淘宝商品类型（1：门票；2：线路）
    public static final String TB_TYPE_TICKET = "1";
    public static final String TB_TYPE_TRAVEL = "2";

    // 淘宝票类型，1：实体票；2：电子票；
    public static final String TB_TICKET_TYPE_ENTITY = "1";
    public static final String TB_TICKET_TYPE_ETC = "2";

    // 套餐类型：1:成人；2:儿童；3:房差；
    public static final String TB_COMBO_TYPE_MAN = "1";
    public static final String TB_COMBO_TYPE_CHILD = "2";
    public static final String TB_COMBO_TYPE_DIFF = "3";

    // 初始化 淘宝产品类型分类
    private Map<Long, Long> cidMap;
    /** 门票（顶级CID）*/
    private static Long TICKET_PARENT_CID = 50454031L;
    /** 线路（顶级CID）*/
    private static Long TRAVEL_PARENT_CID = 50025707L;

    private TaobaoProductSyncService taobaoProductSyncService;
    /** 从表中查出 商品ID */
    private List<Long> itemIdCache;
    /** 本次操作 新增产品ID */
    private List<Long> addItemIds;
    /** 本次操作 删除产品ID */
    private List<Long> delItemIds;

    /** 处理类型 1：门票；2：线路； */
    private String processType;

    private static String PROCESS_TYPE_TICKET = "1";
    private static String PROCESS_TYPE_TRAVEL = "2";
    /** 操作人名称 */
    private String userName;

    public TaobaoSyncHelp(TaobaoProductSyncService taobaoProductSyncService) {
        this.taobaoProductSyncService = taobaoProductSyncService;
    }

    /**
     * 同步淘宝商品
     */
    public void processTaobao() {
        // 初始化 淘宝产品类型
        initCidMap();
        log.info("sync taobao product info start!");
        // 同步 在售的-- 目前产品只同步在售的
        processTaobao(FIND_STATUS_ONSALE);
        processTaobao(FIND_STATUS_INVENTORY);
        log.info("sync taobao product info end!");
        // 销毁 淘宝产品类型
        destroyCidMap();
    }

    /**
     * 同步淘宝在线商品
     */
    public void processTaobaoOnsale() {
        // 初始化 淘宝产品类型
        initCidMap();
        log.info("sync taobao product onsale info start!");
        // 同步 在售的
        processTaobao(FIND_STATUS_ONSALE);
        log.info("sync taobao product onsale info end!");
        // 销毁 淘宝产品类型
        destroyCidMap();
    }

    /**
     * 同步淘宝在库商品
     */
    public void processTaobaoInventory() {
        // 初始化 淘宝产品类型
        initCidMap();
        log.info("sync taobao product inventory info start!");
        // 同步 在售的
        processTaobao(FIND_STATUS_INVENTORY);
        log.info("sync taobao product inventory info end!");
        // 销毁 淘宝产品类型
        destroyCidMap();
    }

    /**
     * 初始化 淘宝产品类型
     */
    private void initCidMap() {
        Map<String, Object> params = new HashMap<String, Object>();
        if (processType != null) {
            if (StringUtils.equals(processType, TB_TYPE_TICKET)) {
                params.put("tbType", TB_TYPE_TICKET);
            } else if (StringUtils.equals(processType, TB_TYPE_TRAVEL)) {
                params.put("tbType", TB_TYPE_TRAVEL);
            }
        }
        itemIdCache = taobaoProductSyncService.getTaobaoProductItemIdList(params);

        // 初始化cId类型Map
        initCid();

        addItemIds = new ArrayList<Long>();
        delItemIds = itemIdCache;
    }

    /**
     * 初始化cId类型Map
     */
    public void initCid() {
        cidMap = new HashMap<Long, Long>();
        addCid(TICKET_PARENT_CID, TICKET_PARENT_CID);
        addCid(TRAVEL_PARENT_CID, TRAVEL_PARENT_CID);
    }

    /**
     * 销毁 淘宝产品类型
     */
    private void destroyCidMap() {
        if (cidMap != null) {
            cidMap.clear();
            cidMap = null;
        }
    }

    /**
     * 添加淘宝产品类型
     * @param parentCid 父类产品类型ID
     * @param cid 产品类型ID
     */
    private void addCid(Long parentCid, Long cid) {
        ItemcatsGetResponse response;
        try {
            response = TOPInterface.findItemcats(parentCid, null);
            if (response.isSuccess()) {
                List<ItemCat> itemCats = response.getItemCats();		// 类目列表
                for (ItemCat itemCat : itemCats) {
                    // 如果不为父目录
                    if (itemCat.getIsParent()) {
                        addCid(itemCat.getCid(), cid);
                    } else {
                        cidMap.put(itemCat.getCid(), cid);
                    }
                }
            } else {
                log.info("add cid failed!parentCid=" + parentCid + ", ErrorMsg:" + response.getSubMsg());
            }
        } catch (ApiException e) {
            log.info("add cid failed!parentCid=" + parentCid, e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 同步接口
     * @param type  onsale：在线的；inventory：在存中；
     */
    private void processTaobao(String type) {
        long total;
        long pageNo = 1L;
        long pageSize = 100L;

        long num;
        // 第一次入库，并获取 总记录数 和获取 记录数
        Map<String, Long> rMap = null;
        try {
            // 处理淘宝接口Request的信息(onsale,inventory)，并返回总页数
            rMap = processRequestInfo(type, pageNo, pageSize, true);
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("add taobaoProductSync error!", e);
        }
        if (rMap == null) {
            return;
        }
        // 总记录数
        total = rMap.get("total");
        // 加载记录数
        num = rMap.get("num");
        if (total > num) {
            // 总页数
            long totalPage = total % pageSize > 0 ? ((total / pageSize) + 1) : (total / pageSize);
            // 循环插入所有数据
            for (int i = 2; i < (totalPage + 1); i++) {
                try {
                    // 处理淘宝接口Request的信息(onsale,inventory)
                    processRequestInfo(type, i, pageSize, false);
                } catch (ApiException e) {
                    e.printStackTrace();
                    log.error("add taobaoProductSync error!");
                }
            }
        }
    }

    /**
     * 处理淘宝接口Request的信息，并返回记录数和总记录数（onsale,inventory）
     * @param type      查询类型
     * @param pageNo    当前页数
     * @param pageSize  每页记录数
     * @param isReturn	是否要返回总数，以及获取的记录数
     * @return  返回总记录数
     * @throws ApiException
     */
    private Map<String, Long> processRequestInfo(String type, long pageNo, long pageSize, boolean isReturn) throws ApiException {
        log.info("type=" + type + ", pageNo=" + pageNo + ", pageSize=" + pageSize);
        List<Item> items = null;
        Map<String, Long> map = null;
        // 查询字段
        String fields = "num_iid,cid";
        // 在售
        if (type.equals(FIND_STATUS_ONSALE)) {
            ItemsOnsaleGetResponse response = TOPInterface.findTaoBaoItemsOnsale(pageNo, pageSize, fields);
            if (response.isSuccess()) {
                items = response.getItems();
                if (isReturn) {
                    map = new HashMap<String, Long>();
                    map.put("total", response.getTotalResults());
                    map.put("num", (long) items.size());
                }
            }

            // 在库
        } else {
            ItemsInventoryGetResponse response = TOPInterface.findTaoBaoItemsInventory(pageNo, pageSize, fields);
            if (response.isSuccess()) {
                items = response.getItems();
                if (isReturn) {
                    map = new HashMap<String, Long>();
                    map.put("total", response.getTotalResults());
                    map.put("num", (long) items.size());
                }
            }
        }
        if (items != null && !items.isEmpty()) {
            // 过滤产品
            filterTaobaoSyncInfo(items);
            // 添加产品
            addTaobaoSyncInfo(items);
        }
        return map;
    }


    /**
     * 更新线路信息
     * @param itemId 淘宝商品ID
     * @return  是否更新成功
     */
    public boolean updateTravelInfo(Long itemId) {
        boolean isSuccess = false;
        TravelItemsGetResponse response;
        try {
            response = TOPInterface.findTaobaoTravelItems(itemId);
            isSuccess = response.isSuccess();
            if (isSuccess && response.getTravelItems() != null) {
                log.info("find travel item info success! itemId=" + itemId);
				
				/*   更新线路       */
                List<TaobaoProductSync> list = taobaoProductSyncService.getTaobaoProductSyncByItemId(itemId);
                try {
                    isSuccess = addSnycTravel(response.getTravelItems(), list.get(0));
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("update travel item info failed! itemId=" + itemId);
                    isSuccess = false;
                }
            } else {
                log.info("find travel item info failed! itemId=" + itemId + ", ErrorMsg:" + response.getSubMsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("update TravelInfo error!itemId=" + itemId, e);
        }
        return isSuccess;
    }

    /**
     * 更新门票信息
     * @param itemId    淘宝门票商品ID
     * @return          是否更新成功
     */
    public boolean updateTickeInfo(Long itemId) {
        String itemIds = String.valueOf(itemId);
        boolean isSuccess = false;
        try {
            TicketItemsGetResponse response = TOPInterface.findTaobaoTicketItems(itemIds);
            isSuccess = response.isSuccess();
            if (isSuccess
                    && response.getTicketItems() != null
                    && !response.getTicketItems().isEmpty()) {
                log.info("find ticket item info success! itemId=" + itemId);
				
				/*   更新门票       */
                List<TaobaoProductSync> list = taobaoProductSyncService.getTaobaoProductSyncByItemId(itemId);
                try {
                    isSuccess = addSnycTicket(response.getTicketItems().get(0), list.get(0));
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info("update ticket item info failed! itemId=" + itemId);
                    isSuccess = false;
                }
            } else {
                log.info("find ticket item info failed! itemId=" + itemId + ", ErrorMsg:" + response.getSubMsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("update TickeInfo error!itemId=" + itemId, e);
        }
        return isSuccess;
    }

    /**
     * 过滤淘宝产品，已有的不做处理
     * 并找到新增的，以及删除的
     * @param items 淘宝商品信息列表
     */
    public void filterTaobaoSyncInfo(List<Item> items) {
        // 过滤淘宝信息，如果ItemId在同步表中有了，则不过滤
        for (int i = 0; i < items.size(); i++) {
            Long numIid = items.get(i).getNumIid();
            if (isSnycExist(numIid)) {
                items.remove(i);
                i--;
            }
            // 去掉获取到的商品ID，剩下的就是，需要删除的商品ID
            delItemIds.remove(numIid);
        }
    }

    /**
     * 添加淘宝同步信息
     * @param items 淘宝产品信息
     */
    public void addTaobaoSyncInfo(List<Item> items) {
        List<Item> ticketList;	// 门票
        List<Item> travelList;	// 线路

        // 添加数据
        ticketList = new ArrayList<Item>();
        travelList = new ArrayList<Item>();
        for (Item item : items) {
            Long type = MapUtils.getLong(cidMap, item.getCid());
            if (type != null) {
                // 处理类型不为空
                if (processType != null) {
                    // 门票
                    if (type.equals(TICKET_PARENT_CID)) {
                        if (StringUtils.equals(PROCESS_TYPE_TICKET, processType)) {
                            ticketList.add(item);
                        }

                        // 线路
                    } else if (type.equals(TRAVEL_PARENT_CID)) {
                        if (StringUtils.equals(PROCESS_TYPE_TRAVEL, processType)) {
                            travelList.add(item);
                        }

                    }
                } else {
                    // 门票
                    if (type.equals(TICKET_PARENT_CID)) {
                        ticketList.add(item);

                        // 线路
                    } else if (type.equals(TRAVEL_PARENT_CID)) {
                        travelList.add(item);
                    }
                }
            }
        }
        // 添加门票
        addSyncTickets(ticketList);
        // 添加线路
        addSnycTravels(travelList);
    }

    /**
     * 添加门票产品（批量）
     * @param items     淘宝商品
     */
    private void addSyncTickets(List<Item> items) {
        if (items.isEmpty()) {
            return;
        }
        int count = 0;
        boolean b = false;
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            count++;
            if (b) {
                sb.append(",");
            } else {
                b = true;
            }
            sb.append(item.getNumIid());
            if (count == 20) {
                addSyncTicket(sb.toString());
                sb = new StringBuilder();
                count = 0;
                b = false;
            }
        }
        if (count > 0) {
            addSyncTicket(sb.toString());
        }
    }

    /**
     * 添加门票产品
     * @param itemIds   淘宝商品
     */
    private void addSyncTicket(String itemIds) {
        if (StringUtils.isBlank(itemIds)) {
            return;
        }
        TicketItemsGetResponse response;
        try {
            response = TOPInterface.findTaobaoTicketItems(itemIds);
            // 返回成功才做处理
            if (response.isSuccess()) {
                List<TicketItem> list = response.getTicketItems();
                for (TicketItem ticketItem : list) {
                    try {
                        addSnycTicket(ticketItem);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("Add ticket error! itemId=" + ticketItem.getItemId(), e);
                    }
                }
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("Add ticket error! itemIds=" + itemIds, e);
        }
    }

    /**
     * 添加门票产品
     * @param ticketItem    门票商品
     */
    private void addSnycTicket(TicketItem ticketItem) throws ApiException {
        addSnycTicket(ticketItem, null);
    }

    /**
     * 添加门票产品
     * @param ticketItem 门票商品
     * @param sync       淘宝同步类
     * @return           是否添加成功
     */
    private boolean addSnycTicket(TicketItem ticketItem, TaobaoProductSync sync) throws ApiException {
        if (ticketItem.getErrMsg() != null) {
            // 获取 门票属性信息 错误
            log.error("Get icketItems info error!itemId=" + ticketItem.getItemId() + ", ErrMsg=" + ticketItem.getErrMsg());
            return false;
        }
        Long itemId = ticketItem.getItemId();
        Long cid = ticketItem.getCatId();

        if (sync == null) {
            sync = new TaobaoProductSync();
        }

        // 淘宝商品ID
        sync.setTbItemId(itemId);
        // 淘宝子目录ID
        sync.setTbCid(cid);
        // 淘宝商品类型，1：门票；2：线路；
        sync.setTbType(TB_TYPE_TICKET);
        // 获取淘宝门票的产品ID
//        sync.setProductId(ticketItem.getProductId());
        // 淘宝商品标题
        sync.setTbTitle(ticketItem.getTitle());
        // 商品状态
        sync.setTbAuctionStatus(ticketItem.getAuctionStatus());
        // 淘宝商品同步时间
        sync.setTbModified(new Date());
        // 是否电子票, 票类型，1：实体票；2：电子票；
        if (ticketItem.getEtc() != null && ticketItem.getEtc().getMerchantId() != null) {
            sync.setTbTicketType(TB_TICKET_TYPE_ETC);
        } else {
            sync.setTbTicketType(TB_TICKET_TYPE_ENTITY);
        }
        // 根据sku获取门票SkusList
        List<TaobaoTicketSku> ticketSkus = getTaobaoTicketSkus(cid, ticketItem.getSkus(), itemId);
        sync.setTicketSkus(ticketSkus);
        // 添加淘宝商品同步
        insertTaobaoProductSync(sync);
        return true;
    }

    /**
     * 获取淘宝门票SKU类
     * @param skus      sku
     * @param itemId    淘宝商品ID
     * @return  List
     */
    public List<TaobaoTicketSku> getTaobaoTicketSkus(Long cid, String skus, Long itemId) throws ApiException {
        List<TaobaoTicketSku> ticketSkus = new ArrayList<TaobaoTicketSku>();
        // 根据sku获取商家编码
        Map<String, List<String>> skuType = sku2OuterId(skus);
        // 获取 淘宝产品规格型号对应的 outerId，（Map的key为pidVid，value为outerIds）
        Set<String> typeSet = skuType.keySet();
        for (String pidVid : typeSet) {
            List<String> outerIds = skuType.get(pidVid);
            for (String outerId : outerIds) {
                Long productId = null;
                Long prodBranchId = null;
                if (outerId != null) {
                    String[] strs = outerId.split(",");
                    try {
                        if (strs.length == 2) {
                            productId = Long.valueOf(strs[0]);
                            prodBranchId = Long.valueOf(strs[1]);
                        }
                    } catch (NumberFormatException e) {
                        log.error("TicketSku get outerId error! numIid=" + itemId + ", pidVid=" + pidVid, e);
                    }
                }
                TaobaoTicketSku itemsSku = new TaobaoTicketSku();
                // 设置  产品规格的属性属性值信息
                itemsSku.setTbPidVid(pidVid);
                // 设置  产品规格名称
                setTicketTbVidName(itemsSku, cid, pidVid);
                // 设置  商家编码
                itemsSku.setTbOuterId(outerId);
                // 设置  产品ID
                itemsSku.setProductId(productId);
                // 设置  产品类型ID
                itemsSku.setProdBranchId(prodBranchId);
                // 添加  淘宝门票SKU类
                ticketSkus.add(itemsSku);
            }
        }
        return ticketSkus;
    }

    /**
     * 设置门票的 规格名称，以及票种类型
     */
    private void setTicketTbVidName(TaobaoTicketSku itemsSku, Long cid, String pidVid) throws ApiException {
        String fields = "name";
        StringBuilder sb = new StringBuilder();
        boolean isETicket = false;
        try {
            pidVid = pidVid.replace("-", ":").replace("_", ";");
            ItempropvaluesGetResponse response = TOPInterface.findItempropvaluesGet(cid, pidVid, fields, 3);
            if (response.isSuccess()) {
                List<PropValue> propValues = response.getPropValues();
                boolean b = true;
                for (PropValue propValue : propValues) {
                    String name = propValue.getName();
                    if (b) {
                        b = false;
                    } else {
                        sb.append(",");
                    }
                    sb.append(name);
                    if (StringUtils.equals("电子票", name)) {
                        isETicket = true;
                    }
                }
            }
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("setTicketTbVidName error! pidVid=" + pidVid, e);
            throw e;
        }
        // 设置名称
        itemsSku.setTbVidName(sb.toString());
        // 设置票种类型
        if (isETicket) {
            itemsSku.setTbTicketType(TB_TICKET_TYPE_ETC);
        } else {
            itemsSku.setTbTicketType(TB_TICKET_TYPE_ENTITY);
        }
    }

    /**
     * 添加线路产品（批量）
     * @param list  淘宝商品信息List
     */
    private void addSnycTravels(List<Item> list) {
        if (list.isEmpty()) {
            return;
        }
        for (Item item : list) {
            Long itemId = item.getNumIid();
            if (itemId == null) {
                continue;
            }
            try {
                // 添加线路产品
                TravelItemsGetResponse response = TOPInterface.findTaobaoTravelItems(itemId);
                if (response.isSuccess() && response.getTravelItems() != null) {
                    addSnycTravel(response.getTravelItems());
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Add travel error! itemId=" + itemId, e);
            }
        }
    }

    /**
     * 添加线路产品（单个）
     * @param travelItems   线路商品
     */
    private void addSnycTravel(TravelItems travelItems) {
        addSnycTravel(travelItems, null);
    }


    /**
     * 添加线路产品（单个）
     * @param travelItems   线路商品
     */
    private boolean addSnycTravel(TravelItems travelItems, TaobaoProductSync sync) {
        Long itemId = travelItems.getItemId();
        Long cid = travelItems.getCid();

        if (sync == null) {
            sync = new TaobaoProductSync();
        }
        // 淘宝商品ID
        sync.setTbItemId(itemId);
        // 淘宝子目录ID
        sync.setTbCid(cid);
        // 淘宝商品类型，1：门票；2：线路；
        sync.setTbType(TB_TYPE_TRAVEL);
        // 淘宝商品标题
        sync.setTbTitle(travelItems.getTitle());
        // 商品状态
        sync.setTbAuctionStatus(travelItems.getApproveStatus());
        // 淘宝商品同步时间
        sync.setTbModified(new Date());
        // 是否电子票, 票类型，1：实体票；2：电子票；
        TravelItemsLocalityLife localityLife = travelItems.getLocalityLife();
        if (localityLife != null && localityLife.getMerchant() != null) {
            sync.setTbTicketType(TB_TICKET_TYPE_ETC);
        } else {
            sync.setTbTicketType(TB_TICKET_TYPE_ENTITY);
        }
        // 获取商家编码
        String outerId = travelItems.getOuterId();
        String[] productIds = null;
        if (outerId != null) {
            try {
                productIds = outerId.split(",|，");
//				sync.setProductId(Long.valueOf(outerId.split(",")[0]));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                log.error("Travel outerId split error! itemId=" + itemId, e);
                return false;
            }
        }
        if (productIds == null || productIds.length == 0) {
            log.error("Travel outerId is null!itemId=" + itemId);
            return false;
        }
        List<TravelItemsCombo> travelItemsCombos = travelItems.getTravelItemsCombos();
        // 判断 产品ID数  是否 和 套餐数 对应
        if (travelItemsCombos.size() == productIds.length) {
            List<TaobaoTravelCombo> travelCombos = new ArrayList<TaobaoTravelCombo>();
            for (int i = 0; i < travelItemsCombos.size(); i++) {
                TravelItemsCombo travelItemsCombo = travelItemsCombos.get(i);
                TravelItemsPropValue combo = travelItemsCombo.getCombo();
                TaobaoTravelCombo travelCombo = new TaobaoTravelCombo();
                travelCombo.setTbPid(combo.getPid());
                travelCombo.setTbVid(combo.getVid());
                travelCombo.setTbComboName(combo.getName());
                // 设置产品Id
                travelCombo.setProductId(Long.parseLong(productIds[i]));
                travelCombos.add(travelCombo);
            }
            // 获取门票明细
            sync.setTravelCombos(travelCombos);
            // 添加淘宝产品同步表
            insertTaobaoProductSync(sync);
        }
        return true;
    }

    /**
     * 根据sku获取商家编码
     * @param skuStr    SKU
     * @return  Map
     */
    private Map<String, List<String>> sku2OuterId(String skuStr) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        if (StringUtils.isEmpty(skuStr)) {
            return map;
        }
        // 获取  产品规格的属性属性值信息
        JSONObject sku = JsonHelpUtil.path2JsonObject(skuStr, "SKU");
        // 获取 产品规格的属性属性值信息 Keys
        String[] keys = JsonHelpUtil.getJsonKeys(sku);
        for (String key : keys) {
            List<String> outerIds = new ArrayList<String>();
            // 获取入园时间
            JSONObject type = JsonHelpUtil.path2JsonObject(sku, key, "effDates");
            String[] jsonKeys = JsonHelpUtil.getJsonKeys(type);
            // 获取入园时间的第一个规则（每个规则上的 商家编码 都是一样的，获取只需获取第一个就可以了）
            for (String typeKey : jsonKeys) {
                // 获取 商家编码
                String outerId = JsonHelpUtil.path2String(type, typeKey, "outerId").trim();
                // 只保存唯一的 outerId
                if (!outerIds.contains(outerId)) {
                    outerIds.add(outerId);
                }
            }
            map.put(key, outerIds);
        }
        return map;
    }

    /**
     * 判断该产品在本地数据库是否存在
     * @param itemId    淘宝商品ID
     * @return     是否存在
     */
    private boolean isSnycExist(Long itemId) {
        if (itemIdCache != null) {
            if (itemIdCache.contains(itemId)) {
                return true;
            }
        } else {
            // 判断同步的产品是否已经在表中
            long count = taobaoProductSyncService.getCountByItemId(itemId);
            if (count > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 插入淘宝同步产品
     * @param sync  同步类
     */
    private void insertTaobaoProductSync(TaobaoProductSync sync) {
        // 添加淘宝同步产品
        taobaoProductSyncService.insertTaobaoProductSync(sync, userName);
        // 添加 新增淘宝产品列表
        if (addItemIds != null) {
            addItemIds.add(sync.getTbItemId());
        }
    }

    /**
     * 获取12周内的日历数据
     * @param list          时间日历
     * @param productId     产品ID
     * @param prodBranchId  产品类别ID
     * @return      价格日历列表
     */
    public static List<ProdTimePrice> getProdTimePrices(List<TimePrice> list, Long productId, Long prodBranchId) {
//		List<TimePrice> list = taobaoProductSyncService.selectProdTimePriceByProductId(productId, prodBranchId, beginTime, endTime);
        List<ProdTimePrice> prodTimePrices = new ArrayList<ProdTimePrice>(list.size());
        for (TimePrice timePrice : list) {
            ProdTimePrice ptp = new ProdTimePrice();
            ptp.setProductId(productId);
            ptp.setProdBranchId(prodBranchId);
            ptp.setSpecDate(timePrice.getSpecDate());
            ptp.setAheadHour(timePrice.getAheadHour());
            ptp.setPrice(timePrice.getPrice());
            ptp.setDayStock(timePrice.getDayStock());

            prodTimePrices.add(ptp);

        }
        return prodTimePrices;
    }

    /**
     * 排序，去除 头尾 价格为null的日期
     * @param prodTimePrices    价格日历列表
     * @return      价格日历列表
     */
    public static List<ProdTimePrice> initProdTimePriceList(List<ProdTimePrice> prodTimePrices) {

        // 排序，查出来的时候已经排序了
//		Collections.sort(prodTimePrices, new Comparator<ProdTimePrice>() {
//			@Override
//			public int compare(ProdTimePrice o1, ProdTimePrice o2) {
//		        //对日期字段进行升序，如果欲降序可采用before方法
//		        if(o1.getSpecDate().after(o2.getSpecDate())) return 1;
//		        return -1;
//			}
//		});

//		for (ProdTimePrice prodTimePrice : prodTimePrices) {
//			System.out.println(DateUtil.formatDate(prodTimePrice.getSpecDate(), "yyyy-MM-dd"));
//		}
//		System.out.println("--------------------------------");


        // 补全日期
        Calendar c = Calendar.getInstance();
        Date tempDate;
        Date startDate;
        List<ProdTimePrice> temp = new ArrayList<ProdTimePrice>();
        for (ProdTimePrice prodTimePrice : prodTimePrices) {
            tempDate = prodTimePrice.getSpecDate();
            startDate = c.getTime();
            boolean b = true;
            while (true) {
                if (isDate(tempDate, startDate)) {
                    break;
                }
                if (tempDate.after(startDate)) {
                    ProdTimePrice tempPtp = new ProdTimePrice();
                    tempPtp.setSpecDate(startDate);

                    temp.add(tempPtp);
                    c.add(Calendar.DATE, 1);
                    startDate = c.getTime();
                } else {
                    b = false;
                    break;
                }
            }
            if (b) {
                temp.add(prodTimePrice);
                c.add(Calendar.DATE, 1);
            }
        }

//		for (ProdTimePrice prodTimePrice : temp) {
//			System.out.println(DateUtil.formatDate(prodTimePrice.getSpecDate(), "yyyy-MM-dd"));
//		}
//		System.out.println("--------------------------------");

        // 去除 头 未的价格为null的日期
        int startNum = 0;
        for (int i = 0; i < temp.size(); i++) {
            ProdTimePrice prodTimePrice = temp.get(i);
            if (prodTimePrice.getPrice() != null) {
                startNum = i;
                break;
            }
        }
        int endNum = temp.size() - 1;
        for (int i = (temp.size() - 1); i >= 0; i--) {
            ProdTimePrice prodTimePrice = temp.get(i);
            if (prodTimePrice.getPrice() != null) {
                endNum = i;
                break;
            }
        }

        List<ProdTimePrice> temp2;
        if (startNum != 0 || endNum != (temp.size() - 1)) {
            temp2 = new ArrayList<ProdTimePrice>();
            for (int i = startNum; i < (endNum + 1); i++) {
                temp2.add(temp.get(i));
            }
        } else {
            temp2 = temp;
        }

//		for (ProdTimePrice prodTimePrice : temp2) {
//			System.out.println(DateUtil.formatDate(prodTimePrice.getSpecDate(), "yyyy-MM-dd"));
//		}
        return temp2;
    }

    private static boolean isDate(Date date1, Date date2) {
        String str1 = DateUtil.formatDate(date1, "yyyy-MM-dd");
        String str2 = DateUtil.formatDate(date2, "yyyy-MM-dd");
        return StringUtils.equals(str1, str2);
    }

    /**
     * 设置处理类型（1：门票；2：线路；）
     * @param processType   处理类型
     */
    public void setProcessType(String processType) {
        this.processType = processType;
    }

    /**
     * 仅处理门票
     */
    public void onlyProcessTicket() {
        setProcessType(PROCESS_TYPE_TICKET);
    }

    /**
     * 仅处理线路
     */
    public void onlyProcessTravel() {
        setProcessType(PROCESS_TYPE_TRAVEL);
    }

    /**
     * 获取新增的淘宝商品ID 列表
     * @return  淘宝商品ID 列表
     */
    public List<Long> getDelItemIds() {
        return delItemIds;
    }

    /**
     * 获取删除的淘宝商品ID 列表
     * @return  淘宝商品ID 列表
     */
    public List<Long> getAddItemIds() {
        return addItemIds;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}