package com.lvmama.jinjiang.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.duijie.SupplierProd;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.jinjiang.model.Visa;
import com.lvmama.jinjiang.vo.product.ProductInfo;
import com.lvmama.jinjiang.vo.product.ProductPrice;
/**
 * 锦江之星产品接口
 * @version 2013-9-3
 */
public interface JinjiangProductService {

    /**
     * 根据前台action的查询条件获得结果集
     * @return
     */
    public Page<SupplierProd> selectProductInfoByCondition(Long currentPage,Long pageSize,Map<String,Object> paramMap);

    /**
     * 把对接的产品保存到临时表中，SupplierProduct,SupplierViewContent,SupplierViewJourney
     */
    public void saveTempStockProduct();

    /**
     * 将单个线路产品，保存到临时表中
     * @param lineCode          线路产品Code
     * @param updateTimeStart   更新开始时间
     * @param updateTimeEnd     更新结束时间
     */
    public void saveTempStockProduct(String lineCode, Date updateTimeStart, Date updateTimeEnd);

    /**获取 memcache缓存的同步时间*/
    public Date getSycTime(String key);

    /** 放入入库产品后的时间*/
    public void putSycTime(String key , Date syncTime);

    /**
     * 根据团代码查询一天的时间价格
     * @param groupCode
     * @return
     */
    public List<ProductPrice> realTimeGetGroup(String groupCode) throws Exception;

    /**
     * 根据签证代码查询签证信息
     * @param visaCode
     * @return
     */
    public List<Visa> getVisasByVisaCode(String visaCode) throws Exception;

    /**
     * 入库指定产品
     * @param lineCode
     * @throws Exception
     */
    public boolean saveProductUnStocked(String lineCode) throws Exception;

    /**
     * 更新指定产品
     * @param lineCode
     * @throws Exception
     */
    public void updateProductStocked(String lineCode, Date updateTimeStart, Date updateTimeEnd) throws Exception;

    /**
     * 上下线指定的产品
     * @param productInfo
     * @throws Exception
     */
    public void onOfflineProduct(ProductInfo productInfo) throws Exception;

    /**
     * 更新所有产品的时间价格库存
     * @param updateTimeStart
     * @param updateTimeEnd
     * @throws Exception
     */
    public void updateAllProductTimePrices(Date updateTimeStart,Date updateTimeEnd) throws Exception;

    /**
     * 更新指定产品的时间价格库存
     * @param lineCode
     * @param updateTimeStart
     * @param updateTimeEnd
     * @throws Exception
     */
    public void updateProductTimePrice(String lineCode,Date updateTimeStart,Date updateTimeEnd) throws Exception;
    /**
     * 实时更新价格
     * @param groupCode团Id
     * @param metaProductId采购产品Id
     * @throws Exception
     */
    void syncRealTimePrice(String groupCode, Long metaProductId) throws Exception;

}
