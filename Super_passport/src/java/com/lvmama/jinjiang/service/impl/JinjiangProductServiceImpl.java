package com.lvmama.jinjiang.service.impl;

import com.lvmama.comm.bee.po.duijie.SupplierProd;
import com.lvmama.comm.bee.po.duijie.SupplierViewContent;
import com.lvmama.comm.bee.po.duijie.SupplierViewJourney;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaProductRoute;
import com.lvmama.comm.bee.po.meta.MetaTravelCode;
import com.lvmama.comm.bee.po.prod.*;
import com.lvmama.comm.bee.po.prod.TimeRange.TimeRangePropertEditor;
import com.lvmama.comm.bee.service.duijie.SupplierProdService;
import com.lvmama.comm.bee.service.duijie.SupplierViewContentService;
import com.lvmama.comm.bee.service.duijie.SupplierViewJourneyService;
import com.lvmama.comm.bee.service.meta.MetaTravelCodeService;
import com.lvmama.comm.bee.service.op.IOpTravelGroupService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductRelationService;
import com.lvmama.comm.bee.service.view.ViewMultiJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.po.sup.MetaBCertificate;
import com.lvmama.comm.pet.po.sup.MetaPerform;
import com.lvmama.comm.pet.po.sup.MetaSettlement;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.hotel.service.BaseHotelProductService;
import com.lvmama.jinjiang.JinjiangClient;
import com.lvmama.jinjiang.model.*;
import com.lvmama.jinjiang.model.request.GetGroupRequest;
import com.lvmama.jinjiang.model.request.GetLineRequest;
import com.lvmama.jinjiang.model.request.GetVisasRequest;
import com.lvmama.jinjiang.model.request.LineCodesRequest;
import com.lvmama.jinjiang.model.response.GetGroupResposne;
import com.lvmama.jinjiang.model.response.GetLineResposne;
import com.lvmama.jinjiang.model.response.GetVisasResposne;
import com.lvmama.jinjiang.model.response.LineCodesResponse;
import com.lvmama.jinjiang.service.JinjiangProductService;
import com.lvmama.jinjiang.util.VisaUtil;
import com.lvmama.jinjiang.vo.product.GroupJourney;
import com.lvmama.jinjiang.vo.product.ProductInfo;
import com.lvmama.jinjiang.vo.product.ProductPrice;
import com.lvmama.passport.utils.WebServiceConstant;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import java.beans.PropertyEditor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 锦江之星产品接口
 * @version 2013-9-3
 */
public class JinjiangProductServiceImpl extends BaseHotelProductService implements JinjiangProductService{
    private static final Log log = LogFactory.getLog(JinjiangProductServiceImpl.class);


    private static final Long CANCELHOUR = 1440L;//最晚修改或者取消小时分钟数 一天

    private static final Integer SHOW_DAY = 180;

    private static final Long RATE = 120L; //加价比例

    private JinjiangClient jinjiangClient;

    private MetaTravelCodeService metaTravelCodeService;
    private PermUserService permUserService;

    private BCertificateTargetService bCertificateTargetService;
    private PerformTargetService performTargetService;
    private SettlementTargetService settlementTargetService;
    private ViewPageService viewPageService;
    private ComPictureService comPictureService;
    private ViewPageJourneyService viewPageJourneyService;
    private ProdProductPlaceService prodProductPlaceService;
    private PlaceService placeService;
    private ProdProductRelationService prodProductRelationService;
    private SupplierViewContentService supplierViewContentService;
    private SupplierViewJourneyService supplierViewJourneyService;
    private SupplierProdService supplierProdService;
    private ViewMultiJourneyService viewMultiJourneyService;

    private TopicMessageProducer productMessageProducer;


    private List<SupplierProd> viewContentList;


    private IOpTravelGroupService opTravelGroupService;


    /**
     * 根据前台action的查询条件获得结果集
     * @return
     */
    public Page<SupplierProd> selectProductInfoByCondition(Long currentPage,Long pageSize,Map<String,Object> paramMap){
        Long supplierId = Long.valueOf(WebServiceConstant.getProperties("jinjiang.supplierId"));
        Page<SupplierProd> page = new Page<SupplierProd>();
        String destinationCity = (String)paramMap.get("destination");
        String supplierProductType = (String)paramMap.get("routeType");
        String supplierProdName = (String)paramMap.get("keyword");
        int pageNo = paramMap.get("pageNo") == null ? 1:Integer.parseInt(paramMap.get("pageNo").toString());
        Map<String,Object> map = new HashMap<String,Object>();
        if(!StringUtil.isEmptyString(destinationCity)){
            map.put("destinationCity", destinationCity);
        }
        if(!StringUtil.isEmptyString(supplierProdName)){
            map.put("supplierProdName", supplierProdName);
        }
        if(!StringUtil.isEmptyString(supplierProductType)){
            map.put("supplierProductType", supplierProductType);
        }
        map.put("supplierId", supplierId);
        map.put("supplierChannel", Constant.SUPPLIER_CHANNEL.JINJIANG.name());
        map.put("start",paramMap.get("start"));
        map.put("end",paramMap.get("end"));
        long resultSize = supplierProdService.selectBySupplierProdCount(map);
        if(resultSize > 0){
            List<SupplierProd>  resultList = supplierProdService.selectSupplierProd(map);
            page.setItems(resultList);
        }

        //分页查询
        page.setTotalResultSize(resultSize);
        page.setCurrentPage(currentPage);
        return page;
    }

    /**
     * 把对接的产品保存到临时表中，SupplierProduct,SupplierViewContent,SupplierViewJourney
     */
    @Override
    public void saveTempStockProduct(){
        Date updateTimeEnd = new Date();
        Date updateTimeStart = getSycTime(JinjiangClient.keyLine);
        putSycTime(JinjiangClient.keyLine,updateTimeEnd);
        List<String> lineCodes = null;
        try {
            lineCodes = getLineCodes(updateTimeStart, updateTimeEnd);
        } catch (Exception e) {
            log.error("JinjiangProductServiceImpl saveTempStockProduct getLineCodes error! Exception: ", e);
        }
        if(lineCodes != null){
            for(String lineCode : lineCodes){
                // 将单个线路产品，保存到临时表中
                saveTempStockProduct(lineCode, updateTimeStart, updateTimeEnd);
            }
        }
    }

    /**
     * 将单个线路产品，保存到临时表中
     * @param lineCode          线路产品Code
     * @param updateTimeStart   更新开始时间
     * @param updateTimeEnd     更新结束时间
     */
    @Override
    public void saveTempStockProduct(String lineCode, Date updateTimeStart, Date updateTimeEnd) {
        ProductInfo info = buildProductInfo(lineCode, updateTimeStart, updateTimeEnd);
        if (info != null) {
            SupplierProd prod = new SupplierProd();
            prod.setDestinationCity(info.getDestinationCity());
            prod.setSupplierId(Long.parseLong(WebServiceConstant.getProperties("jinjiang.supplierId")));
            prod.setSupplierProdId(info.getSupplierProdId());
            prod.setSupplierProdName(info.getSupplierProdName());
            prod.setSupplierChannel(Constant.SUPPLIER_CHANNEL.JINJIANG.name());
            prod.setSupplierProductType(Constant.SUPPLIER_PRODUCT_TYPE.TEAM.name());

            //查询临时供应商产品表是否已经存在
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("supplierId", Long.parseLong(WebServiceConstant.getProperties("jinjiang.supplierId")));
            map.put("supplierProdId", info.getSupplierProdId());
            map.put("supplierChannel", Constant.SUPPLIER_CHANNEL.JINJIANG.name());
            Long supplierProdCount = supplierProdService.selectBySupplierProdCount(map);
            if(supplierProdCount == 0){
                //添加供应商产品及明细
                Long id = supplierProdService.insert(prod);
                if(info.getJourneys() != null){
                    for (ViewJourney journey : info.getJourneys()) {
                        SupplierViewJourney supplierViewJourney = new SupplierViewJourney();
                        supplierViewJourney.setProductId(id);
                        supplierViewJourney.setContent(journey.getContent());
                        supplierViewJourney.setDinner(journey.getDinner());
                        supplierViewJourney.setHotel(journey.getHotel());
                        supplierViewJourney.setSeq(journey.getSeq());
                        supplierViewJourney.setTitle(journey.getTitle());
                        supplierViewJourney.setTraffic(journey.getTraffic());
                        supplierViewJourneyService.insert(supplierViewJourney);
                    }
                }

                if(info.getContents() != null){
                    for (ViewContent content : info.getContents()) {
                        SupplierViewContent supplierViewContent = new SupplierViewContent();
                        supplierViewContent.setProductId(id);
                        supplierViewContent.setContentType(content.getContentType());
                        supplierViewContent.setContent(content.getContent());
                        supplierViewContentService.insert(supplierViewContent);
                    }
                }
            } else{
                //修改供应商产品及明细
                try {
                    SupplierProd supplierProd = supplierProdService.selectSupplierProd(map).get(0);
                    Long supplierProdId = supplierProd.getId();
                    Integer valid = supplierProd.getValid();
                    PropertyUtils.copyProperties(supplierProd, prod);
                    supplierProd.setId(supplierProdId);
                    supplierProd.setValid(valid);
                    supplierProdService.update(supplierProd);

                    if(info.getJourneys() != null){
                        for(ViewJourney journey : info.getJourneys()){
                            Map<String,Object> viewJourneyMap = new HashMap<String,Object>();
                            viewJourneyMap.put("productId", supplierProdId);
                            viewJourneyMap.put("seq", journey.getSeq());
                            List<SupplierViewJourney> supplierViewJourneyList = supplierViewJourneyService.selectSupplierViewJourneyByCondition(viewJourneyMap);
                            SupplierViewJourney viewJourney = (supplierViewJourneyList != null && !supplierViewJourneyList.isEmpty())?supplierViewJourneyList.get(0):null;
                            if(viewJourney != null){
                                viewJourney.setContent(journey.getContent());
                                viewJourney.setDinner(journey.getDinner());
                                viewJourney.setHotel(journey.getHotel());
                                viewJourney.setTitle(journey.getTitle());
                                viewJourney.setTraffic(journey.getTraffic());
                                supplierViewJourneyService.update(viewJourney);
                            }else{
                                SupplierViewJourney supplierViewJourney = new SupplierViewJourney();
                                supplierViewJourney.setProductId(supplierProdId);
                                supplierViewJourney.setContent(journey.getContent());
                                supplierViewJourney.setDinner(journey.getDinner());
                                supplierViewJourney.setHotel(journey.getHotel());
                                supplierViewJourney.setSeq(journey.getSeq());
                                supplierViewJourney.setTitle(journey.getTitle());
                                supplierViewJourney.setTraffic(journey.getTraffic());
                                supplierViewJourneyService.insert(supplierViewJourney);
                            }
                        }
                    }

                    if(info.getContents() != null){
                        for(ViewContent content : info.getContents()){
                            Map<String,Object> viewContentMap = new HashMap<String,Object>();
                            viewContentMap.put("productId",supplierProdId);
                            viewContentMap.put("contentType", content.getContentType());
                            List<SupplierViewContent> supplierViewContentList = supplierViewContentService.selectSupplierViewContentByCondition(viewContentMap);
                            SupplierViewContent viewContent = (supplierViewContentList!=null && !supplierViewContentList.isEmpty())? supplierViewContentList.get(0):null;
                            if(viewContent != null){
                                viewContent.setContent(content.getContent());
                                supplierViewContentService.update(viewContent);
                            }else{
                                SupplierViewContent supplierViewContent = new SupplierViewContent();
                                supplierViewContent.setProductId(supplierProdId);
                                supplierViewContent.setContentType(content.getContentType());
                                supplierViewContent.setContent(content.getContent());
                                supplierViewContentService.insert(supplierViewContent);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("JinjiangProductServiceImpl saveTempStockProduct Exception: ", e);
                }
            }
            map.put("valid", 1);
            Long validStock = supplierProdService.selectBySupplierProdCount(map);
            if(validStock == 1){
                // 更新入库的产品基本信息
                try {
                    updateProductStocked(lineCode,updateTimeStart,updateTimeEnd);
                } catch (Exception e) {
                    log.error("JinjiangProductServiceImpl saveTempStockProduct updateProductStocked Exception: ", e);
                }
            }
        }
    }


    /**
     * 实时查询时间价格
     */
    @Override
    public List<ProductPrice> realTimeGetGroup(String groupCode) throws Exception {
        return buildRealTimeGroup(groupCode);
    }


    @Override
    public List<Visa> getVisasByVisaCode(String visaCode) throws Exception {

        return null;
    }

    /** 入库指定产品*/
    @Override
    public boolean saveProductUnStocked(String lineCode) throws Exception {
        boolean flag = true;
        Long supplierId = Long.valueOf(WebServiceConstant.getProperties("jinjiang.supplierId"));
        String userName = WebServiceConstant.getProperties("jinjiang.supplier.username");
        PermUser user = permUserService.getPermUserByUserName(userName);
        log.info("============>> jinjiang.supplier.username===" + userName);
        log.info("============>> user.getUserId()===" + user.getUserId());
        List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId, null, lineCode);
        Date updateTimeEnd = new Date();
        Date updateTimeStart= DateUtils.addYears(updateTimeEnd, -1);
        ProductInfo productInfo = buildProductInfo(lineCode ,updateTimeStart ,updateTimeEnd);
        if (productInfo != null) {
            productInfo =initProductInfo(productInfo, metaProductBranchs);
            Long metaProductId = null;
            Long metaBranchId = null;
            MetaProduct metaProduct = saveMetaProductInfo(productInfo, metaProductId, user);
            ProdProduct prodProduct = saveProdProductInfo(productInfo, metaBranchId, user);
            Long prodProductId = prodProduct.getProductId();
            updateSupplierProd(productInfo,prodProductId);
            if(!productInfo.isValid()){
                addMetaTarget(metaProduct.getMetaProductId());
                List<MetaProductBranch> metaBranch = saveMetaBranch(metaProduct, productInfo);
                List<ProdProductBranch> prodBranch = saveProdBranch(prodProduct);
                packMeta(prodBranch, metaBranch, productInfo);
                saveProductPlace(prodProductId, productInfo);
            }

            saveViewContent(prodProductId,productInfo);
			/*try {
				saveViewJourney(prodProductId,productInfo);
			} catch (Exception e) {
			 log.error("savePic Exception:",e);
			}*/

            try {
                updateProductTimePrice(lineCode, null, null);
            } catch (Exception e) {
                log.error("updateProductTimePrice Exception", e);
            }
            // 发送修改销售产品的消息
            productMessageProducer.sendMsg(MessageFactory.newProductCreateMessage(prodProductId));
            // 发送place变更消息
            productMessageProducer.sendMsg(MessageFactory.newProductPlaceUpdateMessage(prodProductId));
        }else{
            flag = false;
        }
        return flag;
    }

    @Override
    public void updateProductStocked(String lineCode, Date updateTimeStart, Date updateTimeEnd) throws Exception {
        Long supplierId = Long.valueOf(WebServiceConstant.getProperties("jinjiang.supplierId"));
        List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId, null, lineCode);
        String userName = WebServiceConstant.getProperties("jinjiang.supplier.username");
        PermUser user = permUserService.getPermUserByUserName(userName);
        ProductInfo productInfo = buildProductInfo(lineCode ,updateTimeStart ,updateTimeEnd);
        productInfo.setValid(true);

        try {
            if(metaProductBranchs != null){
                for(MetaProductBranch metaProductBranch : metaProductBranchs){
                    // 只更新下线状态 linkai 2014-06-05
                    if (!productInfo.isOnline()) {
                        onOffline(metaProductBranch, productInfo.isOnline());
                    }
                }
            }
        } catch (Exception e) {
            log.error("onOffLine Product Exception:" ,e );
        }

        Long metaProductId = null;
        Long metaBranchId = null;
        if(metaProductBranchs != null && !metaProductBranchs.isEmpty()){
            metaProductId = metaProductBranchs.get(0).getMetaProductId();
            metaBranchId = metaProductBranchs.get(0).getMetaBranchId();
        }
        saveMetaProductInfo(productInfo ,metaProductId, user);
        ProdProduct prodProduct = saveProdProductInfo(productInfo,metaBranchId ,user);
        Long prodProductId = prodProduct.getProductId();
        saveViewContent(prodProductId,productInfo);
        try {
            saveViewJourney(prodProductId,productInfo);
        } catch (Exception e) {
            log.error("savePic Exception:",e);
        }
        // 发送修改销售产品的消息
        productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(prodProductId));
        // 发送place变更消息
        productMessageProducer.sendMsg(MessageFactory.newProductPlaceUpdateMessage(prodProductId));
    }

    @Override
    public void updateProductTimePrice(String lineCode, Date updateTimeStart, Date updateTimeEnd) throws Exception {
        Long supplierId = Long.valueOf(WebServiceConstant.getProperties("jinjiang.supplierId"));
        List<ProductPrice> productPriceList = buildPrices(lineCode, updateTimeStart, updateTimeEnd);
        List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId, null, lineCode);
        Long metaBranchId = null;

        for (MetaProductBranch metaProductBranch : metaProductBranchs) {
            syncProductTimePrice(metaProductBranch, productPriceList);
            if(metaBranchId == null){
                metaBranchId = metaProductBranch.getMetaBranchId();
            }
        }

        Long productId = metaProductBranchService.getProductIdByMetaBranchId(metaBranchId);
        buildProductInfoGroupJourney(lineCode, updateTimeStart, updateTimeEnd,productId);
        //saveViewMultiJourney(productId, productInfo,updateTimeStart,updateTimeEnd);

    }

    /**
     * 更新所有产品的时间价格库存
     */
    @Override
    public void updateAllProductTimePrices(Date updateTimeStart, Date updateTimeEnd) throws Exception {
        if(updateTimeStart == null) updateTimeStart = getSycTime(JinjiangClient.keyPrice);
        if(updateTimeEnd == null) updateTimeEnd = new Date();
        putSycTime(JinjiangClient.keyPrice,updateTimeEnd);
        List<String> lineCodes = getLineCodes(updateTimeStart, updateTimeEnd);
        if(lineCodes != null){
            for (String lineCode : lineCodes) {
                updateProductTimePrice(lineCode, updateTimeStart, updateTimeEnd);
            }
        }

    }


    /**
     * 上下线指定的产品
     */
    @Override
    public void onOfflineProduct(ProductInfo productInfo) throws Exception {
        Long supplierId = Long.valueOf(WebServiceConstant.getProperties("jinjiang.supplierId"));
        boolean isHotelOnline = productInfo.isOnline();
        List<MetaProductBranch> metaProductBranchs = metaProductBranchService.selectMetaProductBranchBySupplierType(supplierId,null , productInfo.getSupplierProdId());
        for (MetaProductBranch metaProductBranch : metaProductBranchs) {
            onOffline(metaProductBranch, isHotelOnline);
        }
    }

    @Override
    public Date getSycTime(String key) {
        return jinjiangClient.getSycTime(key);
    }

    @Override
    public void putSycTime(String key, Date syncTime) {
        jinjiangClient.putSycTime(key, syncTime);
    }

    @Override
    public void syncRealTimePrice(String groupCode,Long metaProductId) throws Exception{
        List<ProductPrice> productPrices = realTimeGetGroup(groupCode);
        if(metaProductId != null){
            List<MetaProductBranch> metaBranchs=  metaProductBranchService.selectBranchListByProductId(metaProductId);
            for (MetaProductBranch metaBranch : metaBranchs) {
                syncProductTimePrice(metaBranch, productPrices);
            }
        }
    }


    private void syncProductTimePrice(MetaProductBranch metaProductBranch ,List<ProductPrice> productPriceList)throws Exception{
        List<ProdProductBranch> prodProductBranchs = prodProductBranchService.getProductBranchByMetaProdBranchId(metaProductBranch.getMetaBranchId());
        if (productPriceList != null) {
            for (ProductPrice productPrice : productPriceList) {
                if(productPrice.getPriceDate() != null){
                    if (metaProductBranch.getProductTypeSupplier().equals(productPrice.getSupplierBranchId())) {
                        TimePrice metaTimePrice = saveMetaProductBranchTimePrice(metaProductBranch, productPrice);
                        if(!"true".equals(metaProductBranch.getVirtual())){
                            saveMetaTravelCode(metaProductBranch, productPrice);
                            for (ProdProductBranch prodProductBranch : prodProductBranchs) {
                                saveProductTimePrice(prodProductBranch, productPrice, metaTimePrice);
                            }
                        }
                    }
                }
            }
            for (ProdProductBranch prodProductBranch : prodProductBranchs) {
                prodProductBranchService.updatePriceByBranchId(prodProductBranch.getProdBranchId());
                if(prodProductBranch.hasDefault()){
                    Long productId = prodProductBranch.getProductId();
                    opTravelGroupService.createTravelGroupByProductId(productId);
                }
            }
        }
        Date updateTimeStart = new Date();
        Date updateTimeEnd = DateUtils.addMonths(updateTimeStart, 3);
        TimeRange range=new TimeRange(updateTimeStart,updateTimeEnd);
        PropertyEditor editor = new TimeRangePropertEditor();
        editor.setValue(range);
        productMessageProducer.sendMsg(MessageFactory.newProductMetaPriceMessage(metaProductBranch.getMetaBranchId(),editor.getAsText()));
    }

    /**
     * 修改供应商产品属性
     * @param productInfo
     * @param lvmamaProdId
     */
    private void updateSupplierProd(ProductInfo productInfo,Long lvmamaProdId){
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("supplierProdId",productInfo.getSupplierProdId());
        params.put("supplierChannel", Constant.SUPPLIER_CHANNEL.JINJIANG.name());
        List<SupplierProd> supplierList = supplierProdService.selectSupplierProd(params);
        SupplierProd supplierProd = supplierList != null ? supplierList.get(0): null;
        if(supplierProd != null && supplierProd.getId() != null){
            supplierProd.setLvmamaProdId(lvmamaProdId);
            supplierProd.setValid(1);
            supplierProdService.update(supplierProd);
        }
    }


    /**
     * 初始化更新采购时间价格表
     * @param metaProductBranch
     * @param productPrice
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    private TimePrice saveMetaProductBranchTimePrice(MetaProductBranch metaProductBranch, ProductPrice productPrice) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        TimePrice timePrice = metaProductService.getMetaTimePriceByIdAndDate(metaProductBranch.getMetaBranchId(), productPrice.getPriceDate());
        TimePrice bean = new TimePrice();
        Long aheadHour = Long.valueOf(productPrice.getAheadHour());
        Long settlementPrice = productPrice.getSettlementPrice();
        Long stock = productPrice.getDayStock();
        Long marketPrice = productPrice.getIndividualPrice();
        if (timePrice == null) {
            bean.setProductId(metaProductBranch.getMetaProductId());
            bean.setMetaBranchId(metaProductBranch.getMetaBranchId());
            bean.setSpecDate(productPrice.getPriceDate());
            bean.setSettlementPrice(settlementPrice);
            bean.setMarketPrice(marketPrice);
            bean.setOverSale("false");
            bean.setTotalDayStock(0L);
            bean.setBreakfastCount(0L);
            bean.setAheadHour(aheadHour);
            bean.setCancelHour(CANCELHOUR);
            bean.setDayStock(stock);
            bean.setResourceConfirm(String.valueOf(true));
            if (settlementPrice <= 0 && stock == 0) {
                return null;
            }
            metaProductService.insertTimePrice(bean);
        } else {
            PropertyUtils.copyProperties(bean, timePrice);
            if (stock != 0) {
                bean.setSettlementPrice(settlementPrice);
                bean.setMarketPrice(marketPrice);
                bean.setAheadHour(aheadHour);
            }
            bean.setDayStock(stock);
            metaProductService.updateTimePrice(bean, timePrice);
        }
        return bean;
    }


    /**
     * 初始化更新团
     * @param metaProductBranch
     * @param productPrice
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    private void saveMetaTravelCode(MetaProductBranch metaProductBranch, ProductPrice productPrice) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        if (StringUtils.isNotBlank(productPrice.getTeamNo())) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("specDate", DateUtil.getDayStart(productPrice.getPriceDate()));
            params.put("supplierChannel", Constant.SUPPLIER_CHANNEL.JINJIANG.name());
            params.put("travelCodeId",productPrice.getTeamNo());
            params.put("productBranch", productPrice.getSupplierBranchId());
            List<MetaTravelCode> metaTravelCodeList = metaTravelCodeService.selectByCondition(params);
            MetaTravelCode metaTravelCode = metaTravelCodeList.isEmpty() ? null : metaTravelCodeList.get(0);
            MetaTravelCode bean = new MetaTravelCode();
            if (metaTravelCode == null) {
                bean.setSpecDate(DateUtil.getDayStart(productPrice.getPriceDate()));
                bean.setSupplierProductId(metaProductBranch.getProductIdSupplier());
                bean.setTravelCodeId(productPrice.getTeamNo());
                bean.setTravelCode(productPrice.getTeamName());
                bean.setProductBranch(productPrice.getSupplierBranchId());
                bean.setSupplierChannel(Constant.SUPPLIER_CHANNEL.JINJIANG.name());
                metaTravelCodeService.insert(bean);
            } else {
                PropertyUtils.copyProperties(bean, metaTravelCode);
                bean.setTravelCode(productPrice.getTeamName());
                bean.setTravelCodeId(productPrice.getTeamNo());
                metaTravelCodeService.updateByPrimaryKeySelective(bean);
            }
        }
    }

    /**
     * 初始化更新销售时间价格表
     * @param prodProductBranch
     * @param productPrice
     * @param metaTimePrice
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    private void saveProductTimePrice(ProdProductBranch prodProductBranch, ProductPrice productPrice, TimePrice metaTimePrice) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Long productId = prodProductBranch.getProductId();
        Long prodBranchId = prodProductBranch.getProdBranchId();
        Long aheadHour = Long.valueOf(productPrice.getAheadHour());
        if(productPrice.getPriceDate() == null){
            return ;
        }
        TimePrice timePrice = prodProductService.getTimePriceByProdId(productId, prodBranchId, productPrice.getPriceDate());
        TimePrice bean = new TimePrice();
        if (timePrice == null ) {
            bean.setProductId(productId);
            bean.setProdBranchId(prodBranchId);
            bean.setSpecDate(productPrice.getPriceDate());
            bean.setCancelHour(CANCELHOUR);
            bean.setAheadHour(aheadHour);
            bean.setPriceType(Constant.PRICE_TYPE.FIXED_PRICE.name());
            bean.setPrice(productPrice.getSalePrice());
            prodProductService.insertTimePrice(bean, metaTimePrice);
        } else {
            PropertyUtils.copyProperties(bean, timePrice);
            bean.setCancelHour(CANCELHOUR);
            bean.setAheadHour(aheadHour);
            bean.setPrice(productPrice.getSalePrice());
            prodProductService.updateTimePriceNoMultiJourney(bean, metaTimePrice);
        }
    }


    /**
     * 创建采购产品
     * @param productInfo
     * @param user
     * @return
     */
    private MetaProduct saveMetaProductInfo(ProductInfo productInfo ,Long metaProductId,PermUser user){
        Long supplierId = Long.valueOf(WebServiceConstant.getProperties("jinjiang.supplierId"));
        String contractId =WebServiceConstant.getProperties("jinjiang.supplier.contractid");
        String subProductType =  productInfo.isForeign() ? Constant.ROUTE_SUB_PRODUCT_TYPE.GROUP_FOREIGN.name() :Constant.ROUTE_SUB_PRODUCT_TYPE.GROUP.name();
        MetaProduct metaProduct = new MetaProductRoute();
        if(metaProductId != null){
            metaProduct =metaProductService.getMetaProduct(metaProductId);
        }
        metaProduct.setBizCode(productInfo.getSupplierProdId());
        metaProduct.setCurrencyType(Constant.FIN_CURRENCY.CNY.name());
        metaProduct.setIsResourceSendFax("false");
        metaProduct.setPayToLvmama("true");
        metaProduct.setPayToSupplier("false");
        metaProduct.setProductType(Constant.PRODUCT_TYPE.ROUTE.name());
        metaProduct.setSupplierId(supplierId);
        metaProduct.setSubProductType(subProductType);
        metaProduct.setManagerId(user.getUserId());
        metaProduct.setOrgId(user.getDepartmentId());
        metaProduct.setValidDays(1L);//有效天数
        metaProduct.setContractId(Long.valueOf(contractId));
        metaProduct.setValid("Y");
        metaProduct.setSupplierChannel(Constant.SUPPLIER_CHANNEL.JINJIANG.name());
        if(!productInfo.isValid()){
            metaProduct.setProductName(productInfo.getSupplierProdName());
            metaProductId = metaProductService.addMetaProduct(metaProduct, "system");
            metaProduct.setMetaProductId(metaProductId);
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("metaProductId", metaProductId);
            map.put("valid", "Y");
            metaProductService.changeMetaProductValid(map, "SYSTEM");
        }else{
            metaProductService.updateMetaProduct(metaProduct, "SYSTEM");
        }
        return metaProduct;
    }


    /**
     * 产品绑定履行对象，结算对象，凭证对象
     * @param metaProductId
     */
    private void addMetaTarget(final Long metaProductId){
        String settlmentTargetId = WebServiceConstant.getProperties("jinjiang.supplier.settletargetid");
        String performTargetId = WebServiceConstant.getProperties("jinjiang.supplier.performtargetid");
        String bcertificateTargetId = WebServiceConstant.getProperties("jinjiang.supplier.bcertificatetargetid");

        MetaBCertificate bcertificate = new MetaBCertificate();
        bcertificate.setTargetId(Long.valueOf(bcertificateTargetId));
        bcertificate.setMetaProductId(metaProductId);
        bCertificateTargetService.insertSuperMetaBCertificate(bcertificate, "SYSTEM");

        MetaSettlement settlement = new MetaSettlement();
        settlement.setMetaProductId(metaProductId);
        settlement.setTargetId(Long.valueOf(settlmentTargetId));
        settlementTargetService.addMetaRelation(settlement, "SYSTEM");

        MetaPerform perform = new MetaPerform();
        perform.setMetaProductId(metaProductId);
        perform.setTargetId(Long.valueOf(performTargetId));
        performTargetService.addMetaRelation(perform, "SYSTEM");
    }

    /**
     * 创建销售产品
     * @param productInfo
     * @param user
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private ProdProduct saveProdProductInfo(ProductInfo productInfo,Long metaBranchId,PermUser user) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        ProdRoute product =new ProdRoute();
        String[] channels = new String[]{Constant.CHANNEL.BACKEND.name(),Constant.CHANNEL.FRONTEND.name(),Constant.CHANNEL.CLIENT.name()};
        String isForeign = productInfo.isForeign() ? "Y" :"N";
        String subProductType =  productInfo.isForeign() ? Constant.ROUTE_SUB_PRODUCT_TYPE.GROUP_FOREIGN.name() :Constant.ROUTE_SUB_PRODUCT_TYPE.GROUP_LONG.name();
        if(!productInfo.isValid() || metaBranchId == null){
            Date now = new Date();
            product.setOnLine("false");
            product.setProductType(Constant.PRODUCT_TYPE.ROUTE.name());
            product.setBizcode(productInfo.getSupplierProdId());
            product.setDays(productInfo.getDayCount());
            product.setFilialeName(Constant.FILIALE_NAME.SH_FILIALE.name());
            product.setAdditional("false");
            product.setCreateTime(now);
            product.setOnlineTime(productInfo.getOnlineTime());
            product.setOfflineTime(productInfo.getOfflineTime());
            product.setCanPayByBonus("N");
            product.setCouponAble("false");
            product.setValid("Y");
            product.setCouponActivity("false");
            product.setIsRefundable("N");
            product.setManagerId(user.getUserId());
            product.setOrgId(user.getDepartmentId());
            product.setTravellerInfoOptions("NAME,CARD_NUMBER,F_NAME,F_CARD_NUMBER,F_MOBILE");
            product.setWrapPage("false");
            product.setValid("Y");
            product.setIsForegin(isForeign);
            product.setIsAperiodic("false");
            product.setInitialNum(Long.valueOf(productInfo.getGroupMin()));
            product.setShowSaleDays(SHOW_DAY);//显示几天价格
            product.setGroupType(Constant.GROUP_TYPE.AGENCY.name());
            product.setSubProductType(subProductType);//产品类型
            product.seteContract(Constant.ECONTRACT_TYPE.NEED_ECONTRACT.name());
            product.setPrePaymentAble("N");
            if(productInfo.isForeign()){
                product.setCountry(VisaUtil.queryVisaCountry(productInfo.getVisaCountry()));
                product.setVisaType(Constant.VISA_TYPE.GROUP_LEISURE_TORUS_VISA.name());
            }
            String supplierProdName = productInfo.getSupplierProdName(); //供应商产品名称
            product.setProductName(supplierProdName);
            product.setIsMultiJourney("Y");
            ProdProduct prodProduct = prodProductService.addProductChannel(product, channels,"SYSTEM");
            Long productId = prodProduct.getProductId();
            Map<String,Object> param = new HashMap<String,Object>();
            param.put("productId", productId);
            param.put("payToLvmama", "true");
            param.put("payToSupplier", "false");
            this.prodProductService.updatePaymentTarget(param);

            ProdEContract eContract = new ProdEContract();
            eContract.setProductId(productId);
            eContract.setEContractTemplate(Constant.ECONTRACT_TEMPLATE.GROUP_ECONTRACT.name());
            eContract.setTravelFormalities(Constant.TRAVEL_FORMALITIES.OTHERS.name());
            eContract.setGroupType(Constant.GROUP_TYPE.AGENCY.name());
            eContract.setGuideService(Constant.GUIDE_SERVICE.LOCAL_GUIDE.name());
            prodProductService.saveEContract(eContract);

            return prodProduct;
        }else{
            List<ProdProduct> products = prodProductService.selectProductByMetaBranchId(metaBranchId);
            ProdProduct oldProduct = products.get(0);
            PropertyUtils.copyProperties(product, oldProduct);
            product.setDays(productInfo.getDayCount());
            product.setInitialNum(Long.valueOf(productInfo.getGroupMin()));
            product.setOnlineTime(productInfo.getOnlineTime());
            product.setOfflineTime(productInfo.getOfflineTime());
            product.setIsForegin(isForeign);
            product.setSubProductType(subProductType);//产品类型
            product.setCountry(VisaUtil.queryVisaCountry(productInfo.getVisaCountry()));
            product.setGroupType(Constant.GROUP_TYPE.AGENCY.name());
            product.seteContract(Constant.ECONTRACT_TYPE.NEED_ECONTRACT.name());
            product.setVisaType(Constant.VISA_TYPE.GROUP_LEISURE_TORUS_VISA.name());
            String supplierProdName = productInfo.getSupplierProdName(); //供应商产品名称
            product.setProductName(supplierProdName);
            product.setIsMultiJourney("Y");
            prodProductService.updateProdProduct(product, channels, "SYSTEM", null);
            return product;
        }
    }
    /**
     * 打包采购
     * @param prodBranchs
     * @param metaBranchs
     * @param productInfo
     */
    private void packMeta(List<ProdProductBranch> prodBranchs,List<MetaProductBranch> metaBranchs,ProductInfo productInfo){
        ProdProductBranchItem virtualItem = new ProdProductBranchItem();
        for(MetaProductBranch metaBranch : metaBranchs){
            if(Constant.ROUTE_BRANCH.VIRTUAL.name().equals(metaBranch.getProductTypeSupplier())){
                virtualItem.setCreateTime(new Date());
                virtualItem.setMetaProductId(metaBranch.getMetaProductId());
                virtualItem.setMetaBranchId(metaBranch.getMetaBranchId());
                virtualItem.setQuantity(1L);
                break;
            }
        }
        for(ProdProductBranch prodBranch : prodBranchs){
            for(MetaProductBranch metaBranch : metaBranchs){
                if(prodBranch.getBranchName().equals(metaBranch.getBranchName())){
                    Long prodBranchId = prodBranch.getProdBranchId();
                    ProdProductBranchItem item = new ProdProductBranchItem();
                    item.setCreateTime(new Date());
                    item.setMetaProductId(metaBranch.getMetaProductId());
                    item.setMetaBranchId(metaBranch.getMetaBranchId());
                    item.setProdBranchId(prodBranchId);
                    item.setQuantity(1L);
                    prodProductBranchService.addItem(item,prodBranch,"SYSTEM");
                    if(Constant.ROUTE_BRANCH.FANGCHA.name().equals(metaBranch.getBranchType())){
                        continue;
                    }
                    virtualItem.setProdBranchId(prodBranchId);
                    prodProductBranchService.addItem(virtualItem,prodBranch,"SYSTEM");
                }
            }
        }
    }

    /**
     * 创建采购类别
     * @param product
     * @param productInfo
     * @return
     */
    private List<MetaProductBranch> saveMetaBranch(MetaProduct product,ProductInfo productInfo){
        List<MetaProductBranch> metaBranchList = new ArrayList<MetaProductBranch>();
        MetaProductBranch mpb = new MetaProductBranch();
        mpb.setAdditional("false");
        mpb.setCreateTime(new Date());
        mpb.setMetaProductId(product.getMetaProductId());
        mpb.setSendFax("false");
        mpb.setTotalDecrease("false");
        mpb.setValid("Y");
        mpb.setVirtual("false");
        mpb.setCheckStockHandle("JINJIANG");

        mpb.setBranchName(Constant.ROUTE_BRANCH.CHILD.getCnName());
        mpb.setBranchType(Constant.ROUTE_BRANCH.CHILD.name());
        mpb.setAdultQuantity(0L);
        mpb.setChildQuantity(1L);
        mpb.setProductIdSupplier(productInfo.getSupplierProdId());
        mpb.setProductTypeSupplier(Constant.ROUTE_BRANCH.CHILD.name());
        MetaProductBranch metaChild = metaProductBranchService.save(mpb, "SYSTEM");
        metaBranchList.add(metaChild);


        mpb.setBranchName(Constant.ROUTE_BRANCH.ADULT.getCnName());
        mpb.setBranchType(Constant.ROUTE_BRANCH.ADULT.name());
        mpb.setAdultQuantity(1L);
        mpb.setChildQuantity(0L);
        mpb.setProductIdSupplier(productInfo.getSupplierProdId());
        mpb.setProductTypeSupplier(Constant.ROUTE_BRANCH.ADULT.name());
        MetaProductBranch metaAdult = metaProductBranchService.save(mpb, "SYSTEM");
        metaBranchList.add(metaAdult);

        mpb.setBranchName(Constant.ROUTE_BRANCH.VIRTUAL.getCnName());
        mpb.setBranchType(Constant.ROUTE_BRANCH.VIRTUAL.name());
        mpb.setProductIdSupplier(productInfo.getSupplierProdId());
        mpb.setProductTypeSupplier(Constant.ROUTE_BRANCH.VIRTUAL.name());
        mpb.setVirtual("true");
        MetaProductBranch virtualBranch = metaProductBranchService.save(mpb, "SYSTEM");
        metaBranchList.add(virtualBranch);

        mpb.setVirtual("false");
        mpb.setBranchName(Constant.ROUTE_BRANCH.FANGCHA.getCnName());
        mpb.setBranchType(Constant.ROUTE_BRANCH.FANGCHA.name());
        mpb.setAdditional("true");
        mpb.setAdultQuantity(1L);
        mpb.setChildQuantity(0L);
        mpb.setProductIdSupplier(productInfo.getSupplierProdId());
        mpb.setProductTypeSupplier(Constant.ROUTE_BRANCH.FANGCHA.name());
        MetaProductBranch metaRoomDiffer = metaProductBranchService.save(mpb, "SYSTEM");
        metaBranchList.add(metaRoomDiffer);

        return metaBranchList;
    }
    /**
     * 创建销售类别
     * @param product
     * @return
     */
    private List<ProdProductBranch> saveProdBranch(ProdProduct product) {
        List<ProdProductBranch> prodBranchList = new ArrayList<ProdProductBranch>();

        ProdProductBranch ppb = new ProdProductBranch();
        ppb.setAdditional("false");
        ppb.setCreateTime(new Date());
        ppb.setProductId(product.getProductId());
        ppb.setPriceUnit("人");
        ppb.setValid("Y");
        ppb.setVisible("true");
        ppb.setMaximum(10L);

        ppb.setBranchName(Constant.ROUTE_BRANCH.CHILD.getCnName());
        ppb.setBranchType(Constant.ROUTE_BRANCH.CHILD.name());
        ppb.setAdultQuantity(0L);
        ppb.setChildQuantity(1L);
        ppb.setDefaultBranch("false");
        ppb.setMinimum(0L);
        ResultHandleT<ProdProductBranch> childBranch = prodProductBranchService.saveBranch(ppb, "SYSTEM");
        prodBranchList.add(childBranch.getReturnContent());


        ppb.setBranchName(Constant.ROUTE_BRANCH.ADULT.getCnName());
        ppb.setBranchType(Constant.ROUTE_BRANCH.ADULT.name());
        ppb.setAdultQuantity(1L);
        ppb.setChildQuantity(0L);
        ppb.setDefaultBranch("true");
        ppb.setMinimum(1L);
        ResultHandleT<ProdProductBranch> adultBranch = prodProductBranchService.saveBranch(ppb, "SYSTEM");
        prodBranchList.add(adultBranch.getReturnContent());

        ppb.setBranchName(Constant.ROUTE_BRANCH.FANGCHA.getCnName());
        ppb.setAdditional("true");
        ppb.setBranchType(Constant.ROUTE_BRANCH.FANGCHA.name());
        ppb.setAdultQuantity(1L);
        ppb.setChildQuantity(0L);
        ppb.setDefaultBranch("false");
        ppb.setMinimum(0L);
        ResultHandleT<ProdProductBranch> roomdifferBranch = prodProductBranchService.saveBranch(ppb, "SYSTEM");
        prodProductRelationService.addRelation(product.getProductId(), roomdifferBranch.getReturnContent(), "SYSTEM");
        prodBranchList.add(roomdifferBranch.getReturnContent());

        for(ProdProductBranch productBranch : prodBranchList){
            productBranch.setOnline("true");
            prodProductBranchService.updateByPrimaryKeySelective(productBranch);
        }

        return prodBranchList;
    }

    private ProductInfo initProductInfo(ProductInfo info,List<MetaProductBranch> metaProductBranchs){
        if(metaProductBranchs != null){
            for(MetaProductBranch metaBranch : metaProductBranchs){
                String supProdId = metaBranch.getProductIdSupplier();
                if(info.getSupplierProdId().equals(supProdId)){
                    info.setValid(true);
                    ProdProductBranch item =  prodProductBranchService.getProductBranchByMetaProdBranchId(metaBranch.getMetaBranchId()).get(0);
                    info.setLvmamaProdId(item.getProductId());
                    break;
                }
            }
        }

        return info;
    }


    /** 保存销售产品描述信息*/
    private void saveViewContent(Long productId,ProductInfo productInfo){
        String userName = WebServiceConstant.getProperties("jinjiang.supplier.username");
        List<ViewContent> contentList=productInfo.getContents();
        ViewPage viewPage=new ViewPage();
        viewPage.setProductId(productId);
        if(contentList != null && !contentList.isEmpty()){
            for(ViewContent content : contentList){
                content.setPageId(productId);
            }
            if(!productInfo.isValid()){
                viewPageService.addViewPage(viewPage);
                viewPage.setContentList(contentList);
                viewPageService.saveViewContent(viewPage,userName);
            } else {
                //等产品经理确定那些产品描述信息不更新
				/*List<ViewContent> contentRemove = new ArrayList<ViewContent>();
				for (ViewContent content : contentList) {
					if (Constant.VIEW_CONTENT_TYPE.FEATURES.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.INTERIOR.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.SHOPPINGEXPLAIN.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.SERVICEGUARANTEE.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.TRAFFICINFO.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.RECOMMENDPROJECT.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.ORDERTOKNOWN.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.PLAYPOINTOUT.name().equals(content.getContentType())
							|| Constant.VIEW_CONTENT_TYPE.ACITONTOKNOW.name().equals(content.getContentType())
							) {
						contentRemove.add(content);
					}
				}
				contentList.removeAll(contentRemove);*/
                viewPage.setContentList(contentList);
                viewPageService.saveViewContent(viewPage, userName);
            }
        }
    }
    private Map<String, GroupJourney> getGroupJourneyMapRemote(List<Group> groups,String lineCode){
        StringBuilder originalText;
        Map<String, GroupJourney> groupJourneyMap = new HashMap<String, GroupJourney>();
        for(Group group:groups){
            originalText = new StringBuilder();
            List<ViewJourney> viewJourneyList = new ArrayList<ViewJourney>();
            for(Journey journey:group.getJourneys()){
                originalText.append(journey.getDayNumber());
                originalText.append(journey.getSubject());
                originalText.append(journey.getTourDescription());
                originalText.append(journey.getRepastDescription());
                originalText.append(journey.getAccomodationDescription());
                originalText.append(group.getPriceInclude());//费用包含
                originalText.append(group.getPriceExclude());//费用不包含

                ViewJourney viewJourney = new ViewJourney();
                viewJourney.setSeq(Long.parseLong(journey.getDayNumber().toString()));
                viewJourney.setTitle(journey.getSubject());
                viewJourney.setContent(journey.getTourDescription());
                viewJourney.setDinner(journey.getRepastDescription());
                viewJourney.setHotel(journey.getAccomodationDescription());
                viewJourneyList.add(viewJourney);

            }
            try {
                String key = MD5.encode(originalText.toString());
                Set<Date> specDates = null;
                GroupJourney groupJourney =null;
                if(groupJourneyMap.get(key)==null){
                    specDates = new HashSet<Date>();
                    specDates.add(DateUtil.getDayStart(group.getDepartDate()));
                    groupJourney = new GroupJourney();
                    groupJourney.setKey(key);
                    groupJourney.setPriceInclude(group.getPriceInclude());
                    groupJourney.setPriceExclude(group.getPriceExclude());
                    groupJourney.setDepartDates(specDates);
                    groupJourney.setViewJourneyList(viewJourneyList);
                    groupJourneyMap.put(key, groupJourney);
                }else{
                    groupJourney = groupJourneyMap.get(key);
                    specDates = groupJourney.getDepartDates();
                    specDates.add(DateUtil.getDayStart(group.getDepartDate()));
                    groupJourney.setDepartDates(specDates);
                    groupJourneyMap.put(key, groupJourney);
                }
            } catch (NoSuchAlgorithmException e) {
                log.error("JinjiangProductServiceImpl getGroupJourneyMapRemote NoSuchAlgorithmException", e);
            }
        }
        return groupJourneyMap;
    }
    private Map<String, GroupJourney> getGroupJourneyMapLocal(Long productId){

        List<ViewMultiJourney> viewMultiJourneys= viewMultiJourneyService.getAllMultiJourneyDetailByProductId(productId);
        Map<String, GroupJourney> groupJourneyMap = new HashMap<String, GroupJourney>();
        //本地行程key
        for (ViewMultiJourney viewMultiJourney : viewMultiJourneys) {
            String key = null;
            Set<Date> specDates = null;
            GroupJourney groupJourney =null;
            //查询销售时间价格列表
            Map<String,Object> mapTimePrice = new HashMap<String,Object>();
            mapTimePrice.put("productId", productId);
            mapTimePrice.put("multiJourneyId", viewMultiJourney.getMultiJourneyId());
            List<TimePrice> prodTimePriceList = prodProductService.selectProdTimePriceByParams(mapTimePrice);
            StringBuilder originalText = new StringBuilder();
            List<ViewJourney> journeyList = viewPageJourneyService.getViewJourneyByMultiJourneyId(viewMultiJourney.getMultiJourneyId());
            //费用包含
            ViewContent ccViewContent = viewPageService.getViewContentByMultiJourneyId(viewMultiJourney.getMultiJourneyId(), Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name());
            //费用不包含
            ViewContent nccViewContent = viewPageService.getViewContentByMultiJourneyId(viewMultiJourney.getMultiJourneyId(), Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name());
            for(ViewJourney viewJourney : journeyList ){
                originalText.append(viewJourney.getSeq());
                originalText.append(viewJourney.getTitle());
                originalText.append(viewJourney.getContent());
                originalText.append(viewJourney.getDinner());
                originalText.append(viewJourney.getHotel());
                if(ccViewContent!=null){
                    originalText.append(ccViewContent.getContent());
                }
                if(nccViewContent!=null){
                    originalText.append(nccViewContent.getContent());
                }
            }
            try {
                key = MD5.encode(originalText.toString());
            } catch (NoSuchAlgorithmException e) {
                log.error("JinjiangProductServiceImpl getGroupJourneyMapLocal NoSuchAlgorithmException", e);
            }
            specDates = new HashSet<Date>();
            for (TimePrice timePrice : prodTimePriceList) {
                specDates.add(timePrice.getSpecDate());
            }
            groupJourney = new GroupJourney();
            groupJourney.setKey(key);
            groupJourney.setViewJourneyList(journeyList);
            groupJourney.setDepartDates(specDates);
            groupJourneyMap.put(key, groupJourney);

        }
        return groupJourneyMap;
    }
    /**
     * 保存多行程
     * @param productId
     * @param productInfo
     * @param groups
     * @param lineCode
     */
    private void saveViewMultiJourney(Long productId,ProductInfo productInfo,List<Group> groups,String lineCode){
        String userName = WebServiceConstant.getProperties("jinjiang.supplier.username");
        Map<String, GroupJourney> groupJourneyMapLocal= this.getGroupJourneyMapLocal(productId);
        Map<String, GroupJourney> groupJourneyMapRemote= this.getGroupJourneyMapRemote(groups,lineCode);
        Iterator<Entry<String, GroupJourney>> itRemote = groupJourneyMapRemote.entrySet().iterator();
        while (itRemote.hasNext()) {
            Map.Entry<String, GroupJourney> entry = (Map.Entry<String, GroupJourney>) itRemote.next();
            //保存
            if(groupJourneyMapLocal.get(entry.getKey())==null){
                GroupJourney groupJourney = groupJourneyMapRemote.get(entry.getKey());
                //多行程
                String journeyName = "行程_"+entry.getKey().substring(0,2);
                journeyName+=entry.getKey().substring(entry.getKey().length()/2-1,entry.getKey().length()/2+1);
                journeyName+=entry.getKey().substring(entry.getKey().length()-2);
                Map<String,Object> params = new HashMap<String,Object>();
                params.put("journeyName", journeyName);
                params.put("productId", productId);
                List<ViewMultiJourney>  viewMultiJourneys = viewMultiJourneyService.queryMultiJourneyByParams(params);
                ViewMultiJourney viewMultiJourney = null;
                Long multiJourneyId = null;
                List<ViewJourney> viewJourneys=new ArrayList<ViewJourney>();
                //多次同时调用入库时，已经存在相同多行程  不再插入
                if(viewMultiJourneys.size()>0){
                    viewMultiJourney =viewMultiJourneys.get(0);
                    multiJourneyId = viewMultiJourney.getMultiJourneyId();
                    viewJourneys = viewPageJourneyService.getViewJourneyByMultiJourneyId(multiJourneyId);
                }else{
                    viewMultiJourney = new ViewMultiJourney();
                    viewMultiJourney.setJourneyName(journeyName);
                    viewMultiJourney.setCreateTime(new Date());
                    viewMultiJourney.setContent("行程");
                    viewMultiJourney.setDays(new Long(groupJourney.getViewJourneyList().size()));
                    viewMultiJourney.setProductId(productId);
                    viewMultiJourney.setNights(new Long(groupJourney.getViewJourneyList().size()-1));
                    viewMultiJourney.setValid("Y");
                    multiJourneyId = viewMultiJourneyService.insert(viewMultiJourney, userName);
                    viewMultiJourney.setMultiJourneyId(multiJourneyId);
                }
                if(viewJourneys.size()==0){

                    ViewPage viewPage= viewPageService.getViewPageByProductId(productId);
                    if(viewPage==null){
                        viewPage = new ViewPage();
                        viewPage.setProductId(productId);
                        viewPageService.addViewPage(viewPage);
                    }
                    //多行程详细信息
                    for (ViewJourney viewJourney : groupJourney.getViewJourneyList()) {
                        viewJourney.setMultiJourneyId(multiJourneyId);
                        viewJourney.setProductId(productId);
                        viewJourney.setPageId(productId);
                        viewPageJourneyService.insertViewJourney(viewJourney, userName);
                    }
                    //费用说明
                    //费用包含
                    ViewContent ccViewContent = new ViewContent();
                    ccViewContent.setContentType(Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name());
                    ccViewContent.setMultiJourneyId(multiJourneyId);
                    ccViewContent.setPageId(productId);
                    ccViewContent.setContent(groupJourney.getPriceInclude());
                    viewPageService.insertViewContent(ccViewContent);
                    //费用不包含
                    ViewContent nccViewContent = new ViewContent();
                    nccViewContent.setContentType(Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name());
                    nccViewContent.setMultiJourneyId(multiJourneyId);
                    nccViewContent.setPageId(productId);
                    nccViewContent.setContent(groupJourney.getPriceExclude());
                    viewPageService.insertViewContent(nccViewContent);
                }
                //关联时间价格表
                Map<String,Object> mapTimePriceRemote = new HashMap<String,Object>();
                mapTimePriceRemote.put("productId", productId);
                mapTimePriceRemote.put("specDates", groupJourney.getDepartDates().toArray());
                List<TimePrice> prodTimePrice = prodProductService.selectProdTimePriceByParams(mapTimePriceRemote);
                for (TimePrice timePrice : prodTimePrice) {
                    timePrice.setMultiJourneyId(multiJourneyId);
                    prodProductService.updateTimePriceByViewMultijourney(timePrice);
                }
                //更新成有效
                viewMultiJourney.setValid("Y");
                viewMultiJourneyService.update(viewMultiJourney, userName);
            }
            //更新
            else{
                GroupJourney groupJourney = groupJourneyMapLocal.get(entry.getKey());
                Long multiJourneyId = groupJourney.getViewJourneyList().get(0).getMultiJourneyId();
                Map<String,Object> mapTimePriceRemote = new HashMap<String,Object>();
                mapTimePriceRemote.put("productId", productId);
                mapTimePriceRemote.put("specDates", groupJourney.getDepartDates().toArray());
                mapTimePriceRemote.put("multiJourneyId", groupJourney.getViewJourneyList().get(0).getMultiJourneyId());
                List<TimePrice> prodTimePrice = null;
                if(groupJourney.getDepartDates().size()>0){
                    //老行程数据 --当前日期之后(包含当前时间)更新为空
                    prodTimePrice = prodProductService.selectProdTimePriceByParams(mapTimePriceRemote);
                    for (TimePrice timePrice : prodTimePrice) {
                        if(!timePrice.getSpecDate().before(new Date())){
                            timePrice.setMultiJourneyId(null);
                            prodProductService.updateTimePriceByViewMultijourney(timePrice);
                        }
                    }
                }
                //新行程数据
                groupJourney = groupJourneyMapRemote.get(entry.getKey());
                mapTimePriceRemote.clear();
                mapTimePriceRemote.put("productId", productId);
                mapTimePriceRemote.put("specDates", groupJourney.getDepartDates().toArray());
                prodTimePrice = prodProductService.selectProdTimePriceByParams(mapTimePriceRemote);
                for (TimePrice timePrice : prodTimePrice) {
                    timePrice.setMultiJourneyId(multiJourneyId);
                    prodProductService.updateTimePriceByViewMultijourney(timePrice);
                }
                ViewMultiJourney  viewMultiJourney =  viewMultiJourneyService.selectByPrimaryKey(multiJourneyId);
                viewMultiJourney.setValid("Y");
                viewMultiJourneyService.update(viewMultiJourney, userName);
            }
        }

    }

    /** 产品的行程展示
     * @throws Exception
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClientProtocolException */
    private void saveViewJourney(Long productId,ProductInfo productInfo) throws ClientProtocolException, FileNotFoundException, IOException, Exception{
        String userName = WebServiceConstant.getProperties("jinjiang.supplier.username");
        if(!productInfo.isValid()){
            for(ViewJourney journey:productInfo.getJourneys()){
                journey.setPageId(productId);
                journey.setProductId(productId);
                viewPageJourneyService.insertViewJourney(journey,userName);
            }
        }else{
            List<ViewJourney> journeyList=viewPageJourneyService.getViewJourneysByProductId(productId);
            Map<Long, Long> map = new HashMap<Long, Long>();
            for(ViewJourney journey:journeyList){
                map.put(journey.getSeq(), journey.getJourneyId());
            }
            for(ViewJourney journey:productInfo.getJourneys()){
                journey.setPageId(productId);
                journey.setProductId(productId);
                journey.setJourneyId(map.get(journey.getSeq()));
                viewPageJourneyService.updateViewJourney(journey,userName);
            }
        }

    }
    /**
     * 更新目的地出发的
     * @param productId
     * @param productInfo
     */
    private void saveProductPlace(Long productId , ProductInfo productInfo){
        String from = productInfo.getDepartCity();
        String toStr = productInfo.getDestinationCity();
        if (from != null || toStr != null) {
            Place placeFrom = placeService.getPlaceByName(from, "Y");
            Long fromPlaceId = placeFrom.getPlaceId();
            List<String> toList =StringUtil.split(toStr, ",");
            for (String to : toList) {
                Place placeTo = placeService.getPlaceByName(to, "Y");
                Long toPlaceId = placeTo.getPlaceId();
                if (fromPlaceId != null || toPlaceId != null) {
                    prodProductPlaceService.insertOrUpdateTrafficPlace(productId, fromPlaceId, toPlaceId);
                }
            }
        }
    }

    /**
     * 查询所有的线路Code
     * @param updateTimeStart
     * @param updateTimeEnd
     * @return
     * @throws Exception
     */
    private List<String> getLineCodes(Date updateTimeStart, Date updateTimeEnd) throws Exception {
        if(updateTimeEnd == null) updateTimeEnd = new Date();
        if(updateTimeStart == null) updateTimeStart= DateUtils.addMonths(updateTimeEnd, -1);
        LineCodesRequest lineCodeRequest = new LineCodesRequest(updateTimeStart, updateTimeEnd);
        LineCodesResponse lineCodeesponse = jinjiangClient.execute(lineCodeRequest);
        if(lineCodeesponse.isSuccess()){
            return lineCodeesponse.getLineCodes();
        }
        return null;
    }

    /**
     * 根据线路Id查询相关团行程
     * @param lineCode
     * @param updateTimeStart
     * @param updateTimeEnd
     * @return
     */
    private ProductInfo buildProductInfoGroupJourney(String lineCode,Date updateTimeStart,Date updateTimeEnd,Long productId){
        if(updateTimeEnd == null) updateTimeEnd = new Date();
        if(updateTimeStart == null) updateTimeStart= DateUtils.addMonths(updateTimeEnd, -1);
        ProductInfo productInfo = new ProductInfo();
        Line line = null;
        try {
            line = getLineByCode(lineCode, updateTimeStart, updateTimeEnd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(line == null) return null;


        List<Group> groups = new ArrayList<Group>();
        groups = line.getGroups();
        Integer groupMin = 100;
        for(Group group : groups){
            if(group.getRequireNum() < groupMin){
                groupMin = group.getRequireNum();
            }
        }
        int dayCount = line.getDays();
        String departCity = line.getDepartPlace();
        String destinationCity = line.getDestination();
        boolean isForeign = !"DOMESTIC".equals(line.getBusinessCategory());
        productInfo.setForeign(isForeign);
        productInfo.setSupplierProdId(lineCode);
        productInfo.setSupplierProdName(line.getName());
        productInfo.setDayCount(Long.valueOf(dayCount));
        productInfo.setDepartCity(departCity);
        productInfo.setDestinationCity(destinationCity);
        productInfo.setGroupMin(groupMin+"");
        boolean isOnline = "ON".equals(line.getLineStatus()) ? true : false;
        productInfo.setOnline(isOnline);
        Date beginDate = line.getBeginDate().after(new Date()) ? (line.getBeginDate() == null ? new Date() : line.getBeginDate()) : new Date();
        productInfo.setOnlineTime(beginDate);
        productInfo.setOfflineTime(line.getEndDate());
        //保存多行程
        if(productId!=null){
            this.saveViewMultiJourney(productId, productInfo,groups,lineCode);
        }
        return productInfo;
    }

    /**
     * 根据线路Id查询产品
     * @param lineCode
     * @param updateTimeStart
     * @param updateTimeEnd
     * @return
     */
    private ProductInfo buildProductInfo(String lineCode, Date updateTimeStart, Date updateTimeEnd){
        if(updateTimeEnd == null) updateTimeEnd = new Date();
        if(updateTimeStart == null) updateTimeStart= DateUtils.addMonths(updateTimeEnd, -1);
        ProductInfo productInfo = new ProductInfo();
        Line line = null;
        try {
            line = getLineByCode(lineCode, updateTimeStart, updateTimeEnd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(line == null) return null;

        List<Group> groups = new ArrayList<Group>();
        groups = line.getGroups();
        Integer groupMin = 100;
        if(groups != null){
            for(Group group : groups){
                if(group.getRequireNum() < groupMin){
                    groupMin = group.getRequireNum();
                }
            }
        }
        int dayCount = line.getDays();
        String departCity = line.getDepartPlace();
        String destinationCity = line.getDestination();
        boolean isForeign = !"DOMESTIC".equals(line.getBusinessCategory());
        productInfo.setForeign(isForeign);
        productInfo.setSupplierProdId(lineCode);
        productInfo.setSupplierProdName(line.getName());
        productInfo.setDayCount(Long.valueOf(dayCount));
        productInfo.setDepartCity(departCity);
        productInfo.setDestinationCity(destinationCity);
        productInfo.setGroupMin(groupMin+"");
        boolean isOnline = "ON".equals(line.getLineStatus()) ? true : false;
        productInfo.setOnline(isOnline);
        Date beginDate = line.getBeginDate().after(new Date()) ? (line.getBeginDate() == null ? new Date() : line.getBeginDate()) : new Date();
        productInfo.setOnlineTime(beginDate);
        productInfo.setOfflineTime(line.getEndDate());

        Group group = (groups != null && !groups.isEmpty())?groups.get(0): null;
        if(group != null){
            List<ViewJourney> viewJourneyList = new ArrayList<ViewJourney>();
            group.getJourneys();
            for(Journey journey:group.getJourneys()){
                ViewJourney viewJourney = new ViewJourney();
                viewJourney.setSeq(Long.parseLong(journey.getDayNumber().toString()));
                viewJourney.setTitle(journey.getSubject());
                viewJourney.setContent(journey.getTourDescription());
                viewJourney.setDinner(journey.getRepastDescription());
                viewJourney.setHotel(journey.getAccomodationDescription());
                viewJourneyList.add(viewJourney);
            }
            productInfo.setJourneys(viewJourneyList);

            List<ViewContent> viewContentList = new ArrayList<ViewContent>();

            ViewContent viewcostcontain = new ViewContent();
            viewcostcontain.setContentType(Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name());
            viewcostcontain.setContent(group.getPriceInclude());
            viewContentList.add(viewcostcontain);

            ViewContent viewnocostcontain = new ViewContent();
            viewnocostcontain.setContentType(Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name());
            viewnocostcontain.setContent(group.getPriceExclude());
            viewContentList.add(viewnocostcontain);

            ViewContent viewtrafficeinfo = new ViewContent();
            viewtrafficeinfo.setContent(line.getLineTraffic());
            viewtrafficeinfo.setContentType(Constant.VIEW_CONTENT_TYPE.TRAFFICINFO.name());
            viewContentList.add(viewtrafficeinfo);

            LineFeature lineFeature = group.getLineFeature();
            if(lineFeature != null){
                /** 产品特色 */
                String features = "";
                for(String item : lineFeature.getItem()){
                    features += item + ",";
                }
                features += (lineFeature.getDescription() != null ? lineFeature.getDescription() : "") +"<br/>" + (group.getSaleRemark() == null ? "" : group.getSaleRemark());
                ViewContent viewFeatures = new ViewContent();
                viewFeatures.setContent(features);
                viewFeatures.setContentType(Constant.VIEW_CONTENT_TYPE.FEATURES.name());
                viewContentList.add(viewFeatures);
            }
            ViewContent viewordertoknown = new ViewContent();
            viewordertoknown.setContent(group.getRemark());
            viewordertoknown.setContentType(Constant.VIEW_CONTENT_TYPE.ORDERTOKNOWN.name());
            viewContentList.add(viewordertoknown);



            ViewContent viewVisa = new ViewContent();

            List<Visa> visaList = null;
            String visaCode = group.getVisaCode();
            if(StringUtils.isNotEmpty(visaCode)){
                visaList = getVisasByCode(visaCode);
            }
            StringBuilder viewVisaContent = new StringBuilder();
            if(visaList!=null){
                String visaCountry = null;
                for(Visa visa :visaList){
                    viewVisaContent.append("签证代码: ");
                    viewVisaContent.append(visa.getCode());
                    viewVisaContent.append("&nbsp;目的地:"+visa.getDestination());
                    viewVisaContent.append("&nbsp;送签地:"+visa.getSendPlace());
                    viewVisaContent.append("&nbsp;游客类型:"+visa.getGuestType());
                    viewVisaContent.append("&nbsp;材料名称:"+visa.getMaterialName());
                    viewVisaContent.append("&nbsp;需要份数:"+visa.getNeedNum()+"<br/>");
                    viewVisaContent.append("&nbsp;材料说明:"+visa.getMaterialRemark());
                    viewVisaContent.append("&nbsp;签证类型："+visa.getVisaType());
                    viewVisaContent.append("&nbsp;担保金："+visa.getBondAmount()+"<br/>");
                    if(visa.getDestination() !=null && visaCountry == null){
                        visaCountry = visa.getDestination();
                    }
                }

                viewVisa.setContent(viewVisaContent.toString());
                viewVisa.setContentType(Constant.VIEW_CONTENT_TYPE.VISA.name());
                viewContentList.add(viewVisa);
                productInfo.setVisaCountry(visaCountry);
            }
            productInfo.setContents(viewContentList);
        }
        return productInfo;
    }

    /**
     * 根据线路Id查询产品价格
     * @param lineCode
     * @param updateTimeStart
     * @param updateTimeEnd
     * @return
     */
    private List<ProductPrice> buildPrices(String lineCode, Date updateTimeStart, Date updateTimeEnd){
        if(updateTimeEnd == null) updateTimeEnd = new Date();
        if(updateTimeStart == null) updateTimeStart= DateUtils.addMonths(updateTimeEnd, -1);
        Line line = null;
        try {
            line = getLineByCode(lineCode, updateTimeStart, updateTimeEnd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(line == null) return new ArrayList<ProductPrice>();
        List<Group> groups = line.getGroups();
        List<ProductPrice> productPrices = new ArrayList<ProductPrice>();
        if(groups == null){
            return productPrices;
        }
        for(Group group : groups){
            Date priceDate = group.getDepartDate();
            Integer virualDayStock = group.getLimitNum();//group.getPlanNum() - group.getPaidInNum() - group.getReserveNum();
            virualDayStock= "ON".equals(group.getGroupStatus()) ? (virualDayStock == null ? 0 : virualDayStock) : 0;
            List<GroupPrice> priceList = group.getPrices();
            String teamNo = group.getGroupCode();
            Integer aheadHour = group.getAdvanceCloseDSO();
            aheadHour = (aheadHour != null && aheadHour !=0) ? aheadHour * 1440 : 7*1440;
            Long dayStock = (virualDayStock == 0) ? 0L : -1L;
            buildGroupPrice(lineCode, productPrices,priceDate,dayStock,priceList,teamNo,aheadHour );
            Long vituralPrice = 0L;
            /** 虚拟库存 */
            ProductPrice vitualPrice = new ProductPrice(lineCode, Constant.ROUTE_BRANCH.VIRTUAL.name(), priceDate, vituralPrice,
                    vituralPrice,vituralPrice, Long.valueOf(virualDayStock), teamNo, null,aheadHour);
            productPrices.add(vitualPrice);
        }
        return productPrices;
    }

    /**
     * 实时查询团价格
     * @param groupCode 团编码
     * @return  锦江线路价格
     */
    private List<ProductPrice> buildRealTimeGroup(String groupCode) {
        SimpleGroup group = getGroupByCode(groupCode);
        if (group == null) return new ArrayList<ProductPrice>();
        List<ProductPrice> productPrices = new ArrayList<ProductPrice>();
        Date priceDate = group.getDepartDate();
        Integer dayStock = group.getLimitNum();//group.getPlanNum() - group.getPaidInNum() - group.getReserveNum();
        dayStock= "ON".equals(group.getGroupStatus()) ? (dayStock == null ? 0 : dayStock) : 0;
        List<GroupPrice> priceList = group.getPrices();
        String teamNo = group.getGroupCode();
        Integer aheadHour = group.getAdvanceCloseDSO();
        aheadHour = (aheadHour != null && aheadHour !=0) ? aheadHour * 1440 : 7*1440;
        productPrices = buildGroupPrice(null , productPrices , priceDate ,Long.valueOf( dayStock) , priceList , teamNo ,aheadHour);
        return productPrices;
    }

    private List<ProductPrice> buildGroupPrice(String lineCode, List<ProductPrice> productPrices ,Date priceDate,Long dayStock,List<GroupPrice> priceList,String teamNo ,Integer aheadHour) {

        ProductPrice adultPrice  = null;
        ProductPrice childPrice  = null;
        List<String> categorys = new ArrayList<String>();
        for(GroupPrice price : priceList){
            if("团费".equals(price.getType())){
                categorys.add(price.getCategory());
            }
        }
        boolean adultActivated = false;
        boolean childActivated = false;
        if(categorys.contains("成人价")){
            for(GroupPrice priceAdult : priceList){
                if("成人价".equals(priceAdult.getCategory()) && "团费".equals(priceAdult.getType())){
                    adultActivated = priceAdult.getActivated();
                }
            }
        }
        if(categorys.contains("儿童价")){
            for(GroupPrice priceChild : priceList){
                if("儿童价".equals(priceChild.getCategory()) && "团费".equals(priceChild.getType())){
                    childActivated = priceChild.getActivated();
                }
            }
        }
        for(GroupPrice price : priceList){
            Long groupStock = price.getActivated() ? dayStock : 0L;
            String priceCode = price.getCode();
            Long salePrice = price.getSalePrice().longValue() * 100;
            Long settlementPrice = price.getSettlementPrice().longValue() * 100;
            Long marketPrice = price.getSalePrice().longValue() * RATE ;
            String category = price.getCategory();
            String type = price.getType();
            if("基本价".equals(category) && "团费".equals(type)){
                if(!categorys.contains("成人价") || (categorys.contains("成人价") && !adultActivated)){
                    /** 成人价 */
                    adultPrice = new ProductPrice(lineCode, Constant.ROUTE_BRANCH.ADULT.name(), priceDate,
                            settlementPrice , marketPrice , salePrice, groupStock, teamNo, priceCode,aheadHour);
                    productPrices.add(adultPrice);
                }

                if(!categorys.contains("儿童价") || (categorys.contains("儿童价") && !childActivated)){
                    /** 儿童价 */
                    childPrice = new ProductPrice(lineCode, Constant.ROUTE_BRANCH.CHILD.name(), priceDate,
                            settlementPrice , marketPrice , salePrice, groupStock, teamNo, priceCode,aheadHour);
                    productPrices.add(childPrice);
                }
            }else{
                if("成人价".equals(category) && "团费".equals(type) && adultActivated){
                    /** 成人价 */
                    adultPrice = new ProductPrice(lineCode, Constant.ROUTE_BRANCH.ADULT.name(), priceDate,
                            settlementPrice , marketPrice , salePrice, groupStock, teamNo, priceCode,aheadHour);
                    productPrices.add(adultPrice);
                }else if("儿童价".equals(category) && "团费".equals(type) && childActivated){
                    /** 儿童价 */
                    childPrice = new ProductPrice(lineCode, Constant.ROUTE_BRANCH.CHILD.name(), priceDate,
                            settlementPrice , marketPrice , salePrice, groupStock, teamNo, priceCode,aheadHour);
                    productPrices.add(childPrice);
                }
            }

            if("单房差".equals(category) && "团费".equals(type)){
                /** 房差价 */
                ProductPrice roomDifferPrice = new ProductPrice(lineCode, Constant.ROUTE_BRANCH.FANGCHA.name(), priceDate,
                        settlementPrice , marketPrice , salePrice, groupStock, teamNo, priceCode,aheadHour);
                productPrices.add(roomDifferPrice);
            }
        }
        return productPrices;
    }

    /**
     * 查询所有线路代码
     * @param lineCode
     * @param updateTimeStart
     * @param updateTimeEnd
     * @return
     * @throws Exception
     */
    private Line getLineByCode(String lineCode, Date updateTimeStart, Date updateTimeEnd) throws Exception {
        GetLineRequest lineRequest = new GetLineRequest(lineCode, updateTimeStart, updateTimeEnd);
        GetLineResposne lineResponse = jinjiangClient.execute(lineRequest);
        if(lineResponse.isSuccess()){
            return lineResponse.getLine();
        }
        return null;
    }

    /**
     * 查询签证信息
     * @param visaCode
     * @return
     */
    private List<Visa> getVisasByCode(String visaCode){
        GetVisasRequest visaRequest = new GetVisasRequest(visaCode);
        GetVisasResposne visaResponse = jinjiangClient.execute(visaRequest);
        if(visaResponse.isSuccess()){
            return visaResponse.getVisas();
        }
        return null;


    }


    private SimpleGroup getGroupByCode(String groupCode){
        GetGroupRequest groupRequest = new GetGroupRequest(groupCode);
        GetGroupResposne groupResponse = jinjiangClient.execute(groupRequest);
        if(groupResponse.isSuccess()){
            return groupResponse.getSimpleGroup();
        }
        return null;
    }

    private String replaceHtml(String tips) {
        Pattern patternStr;
        Matcher matcherStr;
        try {
            String regEx_Str = "<[^>]+>"; // 定义HTML标签的正则表达式
            patternStr = Pattern.compile(regEx_Str, Pattern.CASE_INSENSITIVE);
            matcherStr = patternStr.matcher(tips);
            tips = matcherStr.replaceAll(""); // 过滤html标签
        } catch (Exception e) {
            log.error("过滤html标签出错 ", e);
        }
        Pattern p_html;
        Matcher m_html;
        String regEx_html = "<[^>]+>";
        p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        m_html = p_html.matcher(tips);
        tips = m_html.replaceAll("");
        return tips;
    }


    public void setMetaTravelCodeService(MetaTravelCodeService metaTravelCodeService) {
        this.metaTravelCodeService = metaTravelCodeService;
    }

    public void setPerformTargetService(PerformTargetService performTargetService) {
        this.performTargetService = performTargetService;
    }

    public void setbCertificateTargetService(BCertificateTargetService bCertificateTargetService) {
        this.bCertificateTargetService = bCertificateTargetService;
    }

    public void setSettlementTargetService(SettlementTargetService settlementTargetService) {
        this.settlementTargetService = settlementTargetService;
    }

    public void setPermUserService(PermUserService permUserService) {
        this.permUserService = permUserService;
    }

    public void setViewPageService(ViewPageService viewPageService) {
        this.viewPageService = viewPageService;
    }


    public void setPlaceService(PlaceService placeService) {
        this.placeService = placeService;
    }

    public void setComPictureService(ComPictureService comPictureService) {
        this.comPictureService = comPictureService;
    }

    public void setViewPageJourneyService(
            ViewPageJourneyService viewPageJourneyService) {
        this.viewPageJourneyService = viewPageJourneyService;
    }

    public void setProdProductPlaceService(ProdProductPlaceService prodProductPlaceService) {
        this.prodProductPlaceService = prodProductPlaceService;
    }

    public void setProdProductRelationService(ProdProductRelationService prodProductRelationService) {
        this.prodProductRelationService = prodProductRelationService;
    }

    public void setProductMessageProducer(TopicMessageProducer productMessageProducer) {
        this.productMessageProducer = productMessageProducer;
    }

    public void setSupplierViewContentService(
            SupplierViewContentService supplierViewContentService) {
        this.supplierViewContentService = supplierViewContentService;
    }

    public void setSupplierViewJourneyService(
            SupplierViewJourneyService supplierViewJourneyService) {
        this.supplierViewJourneyService = supplierViewJourneyService;
    }

    public void setSupplierProdService(SupplierProdService supplierProdService) {
        this.supplierProdService = supplierProdService;
    }

    public void setJinjiangClient(JinjiangClient jinjiangClient) {
        this.jinjiangClient = jinjiangClient;
    }

    public void setViewMultiJourneyService(
            ViewMultiJourneyService viewMultiJourneyService) {
        this.viewMultiJourneyService = viewMultiJourneyService;
    }

    public void setOpTravelGroupService(IOpTravelGroupService opTravelGroupService) {
        this.opTravelGroupService = opTravelGroupService;
    }
}
