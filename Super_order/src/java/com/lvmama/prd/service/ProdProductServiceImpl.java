package com.lvmama.prd.service;

import com.lvmama.com.dao.ComCodesetDAO;
import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.com.dao.ComPlaceDAO;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaProductTraffic;
import com.lvmama.comm.bee.po.prod.*;
import com.lvmama.comm.bee.service.prod.ProdProductModelPropertyService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.view.ViewMultiJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.bee.vo.ProdRouteDate;
import com.lvmama.comm.bee.vo.view.ViewProdProductJourney;
import com.lvmama.comm.bee.vo.view.ViewProdProductJourneyDetail;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.po.prod.ProdProductChannel;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.pet.service.sensitiveW.SensitiveWordService;
import com.lvmama.comm.pet.service.sup.SupContractService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.homePage.PindaoPageUtils;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.TimeInfo;
import com.lvmama.comm.vo.ViewBuyInfo;
import com.lvmama.order.logic.BonusReturnLogic;
import com.lvmama.prd.dao.*;
import com.lvmama.prd.logic.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProdProductServiceImpl implements ProdProductService {
    private ProdProductDAO prodProductDAO;
    private ProdProductItemDAO prodProductItemDAO;
    private ProdProductPlaceDAO prodProductPlaceDAO;
    private ComCodesetDAO comCodesetDAO;
    private ProductTimePriceLogic productTimePriceLogic;
    private MetaProductBranchDAO metaProductBranchDAO;
    private ProdTimePriceDAO prodTimePriceDAO;
    private ProductGroupDAO productGroupDAO;
    private ComLogDAO comLogDAO;
    private ProdEContractDAO prodEContractDAO;
    private ProductResourceConfirmLogic productResourceConfirmLogic;
    private ProductSellableLogic productSellableLogic;
    private ComMessageService comMessageService;
    private ProdProductBranchDAO prodProductBranchDAO;
    private ComPlaceDAO comPlaceDAO;
    private ViewPageDAO viewPageDAO;
    private MetaProductDAO metaProductDAO;
    private ProdProductModelPropertyService prodProductModelPropertyService;
    private final String DATE_FORMAT = "yyyy-MM-dd";
    private ProdProductBranchLogic prodProductBranchLogic;
    private ProdProductBranchItemDAO prodProductBranchItemDAO;
    private ProdProductRelationDAO prodProductRelationDAO;
    private ProdJourneyLogic prodJourneyLogic;
    private SupplierService supplierService;
    private SupContractService supContractService;
    private ViewTravelTipsDAO viewTravelTipsDAO;
    private BonusReturnLogic bonusReturnLogic;

    public void setProdProductModelPropertyService(ProdProductModelPropertyService prodProductModelPropertyService) {
        this.prodProductModelPropertyService = prodProductModelPropertyService;
    }

    @SuppressWarnings("unused")
    private TopicMessageProducer productMessageProducer;

    public void setProductMessageProducer(TopicMessageProducer productMessageProducer) {
        this.productMessageProducer = productMessageProducer;
    }

    public ComMessageService getComMessageService() {
        return comMessageService;
    }

    public void setComMessageService(ComMessageService comMessageService) {
        this.comMessageService = comMessageService;
    }

    public void setProdProductPlaceDAO(ProdProductPlaceDAO prodProductPlaceDAO) {
        this.prodProductPlaceDAO = prodProductPlaceDAO;
    }

    public List<ProdProductItem> getProductItems(Long productId) {
        return prodProductItemDAO.selectProductItems(productId);
    }

    public ProdProduct getProdProductById(Long productId, String type) {
        return prodProductDAO.selectProductDetailByPrimaryKey(productId);
    }

    public ProdProduct getProdProductById(Long productId) {
        return prodProductDAO.selectByPrimaryKey(productId);
    }

    public void setProdProductBranchItemDAO(
            ProdProductBranchItemDAO prodProductBranchItemDAO) {
        this.prodProductBranchItemDAO = prodProductBranchItemDAO;
    }

    public void setProdProductRelationDAO(
            ProdProductRelationDAO prodProductRelationDAO) {
        this.prodProductRelationDAO = prodProductRelationDAO;
    }

    public ProdProduct getProdProductPlaceById(Long productId) {
        ProdProduct p = prodProductDAO.selectProductDetailByPrimaryKey(productId);
        if (p != null) {
            p.setToPlace(comPlaceDAO.getToDestByProductId(productId));
        }
        return p;
    }


    public Page<Long> selectAllProductId(long pageSize, long page) {
        Page<Long> pp = Page.page(pageSize, page);
        pp.setTotalResultSize(prodProductDAO.selectAllProductIdCount());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("skipResults", pp.getStartRows());
        params.put("maxResults", pp.getEndRows());
        pp.setItems(prodProductDAO.selectAllProductId(params));
        return pp;
    }

    public List<Long> getAllGroupProdIds(Long startRow, Long endRow) {
        return prodProductDAO.getAllGroupProdIds(startRow, endRow);
    }

    public Long getGroupProdIdCount() {
        return prodProductDAO.getGroupProdIdCount();
    }

    public void updatePriceByProductId(Long productId) {
        productTimePriceLogic.updatePriceByProductId(productId);
    }

    public String findOldChanelStr(List chanelList) {
        StringBuffer oldStrBuf = new StringBuffer();
        if (chanelList.size() != 0) {
            for (int i = 0; i < chanelList.size(); i++) {
                ProdProductChannel prodChannel = (ProdProductChannel) chanelList.get(i);
                oldStrBuf.append(prodChannel.getChannelName() + " ");
            }
        }
        return oldStrBuf.toString();
    }

    /**
     * 产品选定的标的信息是否被修改
     * @param oldList
     * @param newList
     * @return
     */
    private boolean isEditProdChannel(List oldList, List<CodeItem> newList) {
        boolean isEdit = false;
        int count = 0;
        for (CodeItem channel : newList) {
            if (channel.isChecked()) {
                count++;
            }
        }
        if (oldList.size() != count) {
            isEdit = true;
            return isEdit;
        }

        for (CodeItem channel : newList) {
            if (channel.isChecked()) {
                ProdProductChannel prodChannel = new ProdProductChannel();
                prodChannel.setProductChannel(channel.getCode());
                if (this.findIsExistChanels(prodChannel, oldList)) {
                } else {
                    isEdit = true;
                }
            }
        }
        return isEdit;
    }

    /**
     * 只需要判断两边的数量是否相等，并且当中的值是否一样
     *
     * @param oldList
     * @param newList 操作的channel
     * @return
     */
    private boolean isEditProdChannel(List oldList, String[] newList) {
        boolean isEdit = false;
        if ((ArrayUtils.isEmpty(newList) && CollectionUtils.isNotEmpty(oldList))
                || (!ArrayUtils.isEmpty(newList) && CollectionUtils
                .isEmpty(oldList)))
            return true;
        if (oldList.size() != newList.length) {
            return true;
        }
        for (Iterator<ProdProductChannel> it = oldList.iterator(); it.hasNext(); ) {
            ProdProductChannel ppc = it.next();
            if (!ArrayUtils.contains(newList, ppc.getProductChannel())) {
                isEdit = true;
                break;
            }
        }
        return isEdit;
    }

    public boolean findIsExistChanels(ProdProductChannel prodChannel, List oldList) {
        boolean isExist = false;
        for (int j = 0; j < oldList.size(); j++) {
            ProdProductChannel oldOrodChannel = (ProdProductChannel) oldList.get(j);
            if (!prodChannel.getProductChannel().equals(oldOrodChannel.getProductChannel())) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    /**
     * 产品选定的标的信息是否被修改
     * @param oldList
     * @param newList
     * @return
     */
    private boolean isEditProductPlace(List oldList, List newList) {
        boolean isEdit = false;
        if (oldList.size() != newList.size()) {
            isEdit = true;
            return isEdit;
        }
        Iterator itr2 = newList.iterator();
        while (itr2.hasNext()) {
            ProdProductPlace prodProductPlace = (ProdProductPlace) itr2.next();
            if (this.findIsExistsOldProductPlace(prodProductPlace, oldList)) {
            } else {
                isEdit = true;
            }
        }
        return isEdit;
    }

    public boolean findIsExistsOldProductPlace(ProdProductPlace prodProductPlace, List oldList) {
        boolean isExist = false;
        for (int j = 0; j < oldList.size(); j++) {
            ProdProductPlace oldPlace = (ProdProductPlace) oldList.get(j);
            if (prodProductPlace.getPlaceId().equals(oldPlace.getPlaceId())) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    public boolean isProdProductPlaceChanged(ProdProduct product, List<ProdProductPlace> prodProductPlaceList) {
        List<ProdProductPlace> placeList = prodProductPlaceDAO.selectByProductId(product.getProductId());
        return this.isEditProductPlace(placeList, prodProductPlaceList);
    }

    @Override
    public void updateProdProduct(ProdProduct product, String[] channels, String operatorName, String isClearRoute) {
        String str = this.generLogStr(product, operatorName);

        prodProductDAO.updateByPrimaryKey(product);
        if (isClearRoute != null && !"".equals(isClearRoute) && "Y".equals(isClearRoute)) {
            prodProductModelPropertyService.clearProdProductModelPropertyByProductId(product.getProductId());
        }
        //对以前没有组织ID的销售产品添加组织ID
        ProdProduct temp = prodProductDAO.selectByPrimaryKey(product.getProductId());
        if (temp.getOrgId() == null) {
            HashMap params = new HashMap();
            params.put("orgId", product.getOrgId());
            params.put("productId", product.getProductId());
            this.prodProductDAO.updateOrgId(params);
        }

        //更新渠道
        List chanelsList = prodProductDAO.getProductChannelByProductId(product.getProductId());
        String oldStrBuf = this.findOldChanelStr(chanelsList);
        StringBuffer newStrBuf = new StringBuffer();
        boolean isEdit = false;
        if (ArrayUtils.isEmpty(channels)) {
            //prodProductDAO.deleteChannelByProductId(product.getProductId());//如果渠道为空时清空之前的渠道信息
            isEdit = true;
        } else {
            isEdit = this.isEditProdChannel(chanelsList, channels);
            if (isEdit) {
                prodProductDAO.deleteChannelByProductId(product.getProductId());//如果渠道不一置时删除之前的再重新添加
                for (String channel : channels) {
                    ProdProductChannel prodChannel = new ProdProductChannel();
                    prodChannel.setProductId(product.getProductId());
                    prodChannel.setProductChannel(channel);
                    prodProductDAO.insertChannel(prodChannel);
                    newStrBuf.append(prodChannel.getChannelName() + " ");
                }
            }
        }
        if (isEdit) {
            //记录渠道日志.
            if ("".equals(oldStrBuf) && !"".equals(newStrBuf.toString()) || !"".equals(oldStrBuf) && "".equals(newStrBuf.toString()) || !"".equals(oldStrBuf) && !"".equals(newStrBuf.toString())) {
                str += LogViewUtil.logEditStr("渠道", oldStrBuf.toString(), newStrBuf.toString());
            }
        }


        //记录日志.
        if (StringUtils.isNotEmpty(str)) {
            comLogDAO.insert("PROD_PRODUCT", null, product.getProductId(), operatorName,
                    Constant.COM_LOG_ORDER_EVENT.updateProdProduct.name(),
                    "编辑销售产品", str, null);
        }
    }

    public String generLogStr(ProdProduct newProduct, String operatorName) {
        StringBuffer strBuf = new StringBuffer();
        ProdProduct oldPorudct = this.getProdProductById(newProduct.getProductId());
        if (!LogViewUtil.logIsEmptyStr(newProduct.getProductName()).equals(LogViewUtil.logIsEmptyStr(oldPorudct.getProductName()))) {
            strBuf.append(LogViewUtil.logEditStr("对象名称", oldPorudct.getProductName(), newProduct.getProductName()));
        }
        if (!LogViewUtil.logIsEmptyStr(newProduct.getBizcode()).equals(LogViewUtil.logIsEmptyStr(oldPorudct.getBizcode()))) {
            strBuf.append(LogViewUtil.logEditStr("产品编号", oldPorudct.getBizcode(), newProduct.getBizcode()));
        }
        if (!LogViewUtil.logIsEmptyStr(newProduct.getAdditional()).equals(LogViewUtil.logIsEmptyStr(oldPorudct.getAdditional()))) {
            strBuf.append(LogViewUtil.logEditStr("仅能捆绑销售", "true".equals(oldPorudct.getAdditional()) ? "是" : "否", "true".equals(newProduct.getAdditional()) ? "是" : "否"));
        }
        if (!LogViewUtil.logIsEmptyStr(newProduct.getFilialeName()).equals(LogViewUtil.logIsEmptyStr(oldPorudct.getFilialeName()))) {
            strBuf.append(LogViewUtil.logEditStr("所属公司", oldPorudct.getFilialeName(), newProduct.getFilialeName()));
        }
        if (!LogViewUtil.logIsEmptyStr(newProduct.getManagerId() + "").equals(LogViewUtil.logIsEmptyStr(oldPorudct.getManagerId() + ""))) {
            strBuf.append(LogViewUtil.logEditStr("产品经理ID", oldPorudct.getManagerId() + "", newProduct.getManagerId() + ""));
        }
        if (!LogViewUtil.logIsEmptyStr(newProduct.getTravellerInfoOptions()).equals(LogViewUtil.logIsEmptyStr(oldPorudct.getTravellerInfoOptions()))) {
            strBuf.append(LogViewUtil.logEditStr("游客必填信息", oldPorudct.getTravellerInfoOptions(), newProduct.getTravellerInfoOptions()));
        }

        if (!LogViewUtil.logIsEmptyStr(newProduct.getShowSaleDays() + "").equals(LogViewUtil.logIsEmptyStr(oldPorudct.getShowSaleDays() + ""))) {
            strBuf.append(LogViewUtil.logEditStr("可显示的销售时间价格表天数", oldPorudct.getShowSaleDays() + "", newProduct.getShowSaleDays() + ""));
        }
        if (!LogViewUtil.logIsEmptyStr(oldPorudct.getOnlineTime() + "").equals(LogViewUtil.logIsEmptyStr(newProduct.getOnlineTime() + ""))) {
            strBuf.append(LogViewUtil.logEditStr(
                    "上线时间",
                    oldPorudct.getOnlineTime() == null ? "" : DateFormatUtils.format(oldPorudct.getOnlineTime(), DATE_FORMAT),
                    newProduct.getOnlineTime() == null ? "" : DateFormatUtils.format(newProduct.getOnlineTime(), DATE_FORMAT)));
        }
        if (!LogViewUtil.logIsEmptyStr(oldPorudct.getOfflineTime() + "").equals(LogViewUtil.logIsEmptyStr(newProduct.getOfflineTime() + ""))) {
            strBuf.append(LogViewUtil.logEditStr("下线时间",
                    oldPorudct.getOfflineTime() == null ? "" : DateFormatUtils.format(oldPorudct.getOfflineTime(), DATE_FORMAT),
                    newProduct.getOfflineTime() == null ? "" : DateFormatUtils.format(newProduct.getOfflineTime(), DATE_FORMAT)));
        }

        if (!LogViewUtil.logIsEmptyStr(newProduct.getCanPayByBonus()).equals(LogViewUtil.logIsEmptyStr(oldPorudct.getCanPayByBonus()))) {
            strBuf.append(LogViewUtil.logEditStr("是否支持使用奖金账户支付", "Y".equals(oldPorudct.getCanPayByBonus()) ? "是" : "否", "Y".equals(newProduct.getCanPayByBonus()) ? "是" : "否"));
        }

        if (!LogViewUtil.logIsEmptyStr(newProduct.getSendSms()).equals(LogViewUtil.logIsEmptyStr(oldPorudct.getSendSms()))) {
            strBuf.append(LogViewUtil.logEditStr("是否发送短信", "true".equals(oldPorudct.getSendSms()) ? "是" : "否", "true".equals(newProduct.getSendSms()) ? "是" : "否"));
        }

        if (!LogViewUtil.logIsEmptyStr(newProduct.getSmsContent()).equals(LogViewUtil.logIsEmptyStr(oldPorudct.getSmsContent()))) {
            strBuf.append(LogViewUtil.logEditStr("短信内容", oldPorudct.getSmsContent(), newProduct.getSmsContent()));
        }
        if (!LogViewUtil.logIsEmptyStr(oldPorudct.getPayDeposit() + "").equals(LogViewUtil.logIsEmptyStr(newProduct.getPayDeposit() + ""))) {
            long oldPayDeposit = 0;
            long newPayDeposit = 0;

            //保证与页面上看到的值是一样的,页面上显示的是元，存储的是分
            if (oldPorudct.getPayDeposit() >= 0) {
                oldPayDeposit = oldPorudct.getPayDeposit() / 100;
            }
            if (newProduct.getPayDeposit() >= 0) {
                newPayDeposit = newProduct.getPayDeposit() / 100;
            }
            strBuf.append(LogViewUtil.logEditStr("定金", oldPayDeposit + "", newPayDeposit + ""));
        }

        if ((newProduct.getGroupMin() != null && !newProduct.getGroupMin().equals(oldPorudct.getGroupMin())) || (oldPorudct.getGroupMin() != null && !oldPorudct.getGroupMin().equals(newProduct.getGroupMin()))) {
            strBuf.append(LogViewUtil.logEditStr("团购最小成团人数", oldPorudct.getGroupMin() + "", newProduct.getGroupMin() + ""));
        }

        //线路//其他//门票//酒店
        if (newProduct instanceof ProdRoute) {
            ProdRoute oldProdRoute = this.prodProductDAO.getProdRouteById(oldPorudct.getProductId());
            ProdRoute newProdRoute = (ProdRoute) newProduct;

            if (!LogViewUtil.logIsEmptyStr(newProduct.getSubProductType()).equals(LogViewUtil.logIsEmptyStr(oldPorudct.getSubProductType()))) {
                strBuf.append(LogViewUtil.logEditStr("线路产品信息-线路类型", oldPorudct.getZhSubProductType(), newProduct.getZhSubProductType()));
            }

            if (!LogViewUtil.logIsEmptyStr(newProdRoute.getGroupType()).equals(LogViewUtil.logIsEmptyStr(oldProdRoute.getGroupType()))) {
                strBuf.append(LogViewUtil.logEditStr("组团方式", oldProdRoute.getGroupType(), newProdRoute.getGroupType()));
            }
            if (!LogViewUtil.logIsEmptyStr(newProdRoute.getInitialNum() + "").equals(LogViewUtil.logIsEmptyStr(oldProdRoute.getInitialNum() + ""))) {
                strBuf.append(LogViewUtil.logEditStr("最少成团人数", oldProdRoute.getDays() + "", newProdRoute.getInitialNum() + ""));
            }
            if (!LogViewUtil.logIsEmptyStr(newProdRoute.getDays() + "").equals(LogViewUtil.logIsEmptyStr(oldProdRoute.getDays() + ""))) {
                strBuf.append(LogViewUtil.logEditStr("行程天数", oldProdRoute.getDays() + "", newProdRoute.getDays() + ""));
            }

            if (!LogViewUtil.logIsEmptyStr(newProdRoute.getTravelGroupCode()).equals(LogViewUtil.logIsEmptyStr(oldProdRoute.getTravelGroupCode()))) {
                strBuf.append(LogViewUtil.logEditStr("团号前辍", oldProdRoute.getTravelGroupCode(), newProdRoute.getTravelGroupCode()));
            }
            if (!LogViewUtil.logIsEmptyStr(newProdRoute.getVisaType()).equals(LogViewUtil.logIsEmptyStr(oldProdRoute.getVisaType()))) {
                strBuf.append(LogViewUtil.logEditStr("送签类型", oldProdRoute.getVisaType(), newProdRoute.getVisaType()));
            }
            if (!LogViewUtil.logIsEmptyStr(newProdRoute.getCountry()).equals(LogViewUtil.logIsEmptyStr(oldProdRoute.getCountry()))) {
                strBuf.append(LogViewUtil.logEditStr("国家", oldProdRoute.getCountry(), newProdRoute.getCountry()));
            }
            if (!LogViewUtil.logIsEmptyStr(newProdRoute.getCity()).equals(LogViewUtil.logIsEmptyStr(oldProdRoute.getCity()))) {
                strBuf.append(LogViewUtil.logEditStr("送签城市", oldProdRoute.getCity(), newProdRoute.getCity()));
            }
        } else if (newProduct instanceof ProdOther) {
            ProdOther newProdOther = (ProdOther) newProduct;
            ProdOther oldProdOther = this.prodProductDAO.getProdOtherById(oldPorudct.getProductId());
            if (!LogViewUtil.logIsEmptyStr(newProdOther.getSubProductType()).equals(LogViewUtil.logIsEmptyStr(oldPorudct.getSubProductType()))) {
                strBuf.append(LogViewUtil.logEditStr("特征描述-产品子类型", oldPorudct.getZhSubProductType(), newProdOther.getZhSubProductType()));
            }
            if (Constant.SUB_PRODUCT_TYPE.VISA.name().equals(newProduct.getSubProductType())) {
                if (!LogViewUtil.logIsEmptyStr(newProdOther.getVisaValidTime()).equals(LogViewUtil.logIsEmptyStr(oldProdOther.getVisaValidTime()))) {
                    strBuf.append(LogViewUtil.logEditStr("签证有效期", oldProdOther.getVisaValidTime(), newProdOther.getVisaValidTime()));
                }
                if (!LogViewUtil.logIsEmptyStr(newProdOther.getVisaType()).equals(LogViewUtil.logIsEmptyStr(oldProdOther.getVisaType()))) {
                    strBuf.append(LogViewUtil.logEditStr("送签类型", oldProdOther.getVisaType(), newProdOther.getVisaType()));
                }
                if (!LogViewUtil.logIsEmptyStr(newProdOther.getVisaMaterialAheadDay() + "").equals(LogViewUtil.logIsEmptyStr(oldProdOther.getVisaMaterialAheadDay() + ""))) {
                    strBuf.append(LogViewUtil.logEditStr("材料截止收取提前", oldProdOther.getVisaMaterialAheadDay() + "", newProdOther.getVisaMaterialAheadDay() + ""));
                }
                if (!LogViewUtil.logIsEmptyStr(newProdOther.getVisaSelfSign()).equals(LogViewUtil.logIsEmptyStr(oldProdOther.getVisaSelfSign()))) {
                    strBuf.append(LogViewUtil.logEditStr("是否自备签", oldProdOther.getVisaSelfSign(), newProdOther.getVisaSelfSign()));
                }
                if (!LogViewUtil.logIsEmptyStr(newProdOther.getCountry()).equals(LogViewUtil.logIsEmptyStr(oldProdOther.getCountry()))) {
                    strBuf.append(LogViewUtil.logEditStr("国家", oldProdOther.getCountry(), newProdOther.getCountry()));
                }
                if (!LogViewUtil.logIsEmptyStr(newProdOther.getCity()).equals(LogViewUtil.logIsEmptyStr(oldProdOther.getCity()))) {
                    strBuf.append(LogViewUtil.logEditStr("送签城市", oldProdOther.getCity(), newProdOther.getCity()));
                }
            }
        } else if (newProduct instanceof ProdTicket) {
            if (!LogViewUtil.logIsEmptyStr(newProduct.getPhysical()).equals(LogViewUtil.logIsEmptyStr(oldPorudct.getPhysical()))) {
                strBuf.append(LogViewUtil.logEditStr("实体票", "true".equals(oldPorudct.getPhysical()) ? "是" : "否", "true".equals(newProduct.getPhysical()) ? "是" : "否"));
            }
            if (!LogViewUtil.logIsEmptyStr(newProduct.getSubProductType()).equals(LogViewUtil.logIsEmptyStr(oldPorudct.getSubProductType()))) {
                strBuf.append(LogViewUtil.logEditStr("特征描述-门票类型", oldPorudct.getZhSubProductType(), newProduct.getZhSubProductType()));
            }
        } else if (newProduct instanceof ProdHotel) {
            StringBuffer strHotelBuf = new StringBuffer();
            ProdHotel oldProdHotel = this.getProdHotelById(oldPorudct.getProductId());
            ProdHotel newProdHotel = (ProdHotel) newProduct;

            if (!LogViewUtil.logIsEmptyStr(newProdHotel.getDays() + "").equals(LogViewUtil.logIsEmptyStr(oldProdHotel.getDays() + ""))) {
                strHotelBuf.append(LogViewUtil.logEditStr("入住天数", oldProdHotel.getDays() + "", newProdHotel.getDays() + ""));
            }

            //记录日志.
            if (!"".equals(strHotelBuf)) {
                strBuf.append(strHotelBuf.toString());
            }
        } else if (newProduct instanceof ProdTraffic) {
            if (!LogViewUtil.logIsEmptyStr(newProduct.getSubProductType()).equals(LogViewUtil.logIsEmptyStr(oldPorudct.getSubProductType()))) {
                strBuf.append(LogViewUtil.logEditStr("大交通产品信息-产品类型", oldPorudct.getZhSubProductType(), newProduct.getZhSubProductType()));
            }
        }
        return strBuf.toString();
    }


    /**
     * 添加产品信息，新版本当中添加，现在的标地信息分开添加
     *
     * @param product
     * @param channels
     * @param operatorName
     */
    public ProdProduct addProductChannel(ProdProduct product, String[] channels, String operatorName) {
        prodProductDAO.insert(product);

        for (String c : channels) {
            ProdProductChannel prodChannel = new ProdProductChannel();
            prodChannel.setProductId(product.getProductId());
            prodChannel.setProductChannel(c);
            prodProductDAO.insertChannel(prodChannel);
        }
        //线路产品自动绑定保险
        if(product.getProductType().equals(Constant.PRODUCT_TYPE.ROUTE.getCode())){
            ProdRoute prodRoute = prodProductDAO.getProdRouteById(product.getProductId());
            Map<String,Object> param = new HashMap<String, Object>();

            param.put("productType",Constant.PRODUCT_TYPE.OTHER.getCode());
            param.put("subProductType",Constant.SUB_PRODUCT_TYPE.INSURANCE.getCode());
            param.put("applicableTravelDays",prodRoute.getDays());
            param.put("applicableSubProductType",product.getSubProductType());

            //境外
            if(product.getSubProductType().equals(Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.getCode()) ||
                    product.getSubProductType().equals(Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.getCode())){

                param.put("isForegin","Y");
                param.put("regionName",product.getRegionName());

            }else{//境内
                param.put("isForegin","N");
            }
            //支付目标不同的保险不能绑定
            param.put("payToLvmama", product.getPayToLvmama());
            param.put("onLine", "true");

            param.put("_startRow", "0");
            param.put("_endRow", "1");

            List<ProdProduct> productList = prodProductDAO.selectProductByParms(param);

           if(productList!=null && productList.size()>0){

              ProdProduct insuranceProd = productList.get(0);

               List<ProdProductBranch> productBranchList = prodProductBranchDAO.getProductBranchByProductId(insuranceProd.getProductId(),null,"true",null);

               for (ProdProductBranch branch : productBranchList) {

                   ProdProductRelation relation = new ProdProductRelation();
                   relation.setProductId(product.getProductId());
                   relation.setRelatProductId(branch.getProductId());
                   relation.setProdBranchId(branch.getProdBranchId());
                   relation.setSaleNumType(Constant.SALE_NUMBER_TYPE.ANY.name());

                   prodProductRelationDAO.insert(relation);
               }

           }
        }

        comLogDAO.insert("PROD_PRODUCT", null, product.getProductId(), operatorName,
                Constant.COM_LOG_ORDER_EVENT.insertProdProduct.name(),
                "创建销售产品", MessageFormat.format("创建名称为[ {0} ]的销售产品", product.getProductName()), null);
        return product;
    }

    public List<CodeItem> getChannelByProductId(Long productId) {
        List<CodeItem> list = comCodesetDAO.getCodeset(Constant.CODE_TYPE.CHANNEL.name());
        if (productId != null && productId != 0) {
            List<String> selected = prodProductDAO.selectChannelByProductId(productId);
            for (int i = 0; i < list.size(); i++) {
                CodeItem item = list.get(i);
                if (selected.contains(item.getCode())) {
                    item.setChecked("true");
                }
            }
        }
        return list;
    }

    public List<ProdProductChannel> selectChannelByProductId(Long productId) {
        return prodProductDAO.getProductChannelByProductId(productId);
    }

    public List<ProdProduct> getAdditionalProdProductByProductId(Long productId) {
        ProdProduct p = new ProdProduct();
        p.setProductId(productId);
        p.setAdditional("true");
        return prodProductDAO.getProdProductByParamByRelation(p);
    }

    public List<ProdProduct> selectSuggestInfoByPlacesName(String name) {
        return this.prodProductDAO.selectSuggestInfoByPlacesName(name);
    }

    public List<ProdProductChannel> getProductChannelByProductId(Long productId) {
        return prodProductDAO.getProductChannelByProductId(productId);
    }

    public void saveTimePrice(TimePrice bean, Long productId, String operatorName) {
        productTimePriceLogic.saveTimePrice(bean, productId, operatorName);
    }

    public void insertTimePrice(TimePrice prodTimePrice, TimePrice metaTimePrice) {
        productTimePriceLogic.insertTimePrice(prodTimePrice, metaTimePrice);
    }

    public void updateTimePrice(TimePrice prodTimePrice, TimePrice metaTimePrice) {
        productTimePriceLogic.updateTimePrice(prodTimePrice, metaTimePrice);
    }

    @Override
    public void updateTimePriceNoMultiJourney(TimePrice prodTimePrice,
                                              TimePrice metaTimePrice) {
        productTimePriceLogic.updateTimePriceNoMultiJourneyFk(prodTimePrice, metaTimePrice);
    }

    public void updateDynamicTimePrice(TimePrice prodTimePrice, TimePrice metaTimePrice) {
        productTimePriceLogic.updateDynamicTimePrice(prodTimePrice, metaTimePrice);
    }


    @Override
    public void saveLastCancelHour(TimePrice bean, Long productId,
                                   String operatorName) {
        productTimePriceLogic.saveTimePriceHour(bean, productId, operatorName);
    }

    @Override
    public List<Date> saveSelfPackTimePrice(TimePrice bean, Long productId, String operatorName) {
        return productTimePriceLogic.saveSelfPackTimePrice(bean, productId, operatorName);
    }

    public List<TimePrice> selectProdTimePriceByProductId(Long productId, Long prodBranchId, Long beginTime, Long endTime) {
        return productTimePriceLogic.selectProdTimePriceByProductId(productId, prodBranchId, beginTime, endTime);
    }

    public Integer selectProdTimePriceCountByProductId(Long productId) {

        return prodTimePriceDAO.selectProdTimePriceCountByProductId(productId);
    }

    @Override
    public Date selectMaxTimePriceByProductId(Long productId) {
        return prodTimePriceDAO.selectMaxTimePriceByProductId(productId);
    }

    public CalendarModel selectProdTimePriceByProdBranchId(Long productId, Long prodBranchId,
                                                           Long beginTime, Long endTime, boolean selfPack) {

        List<TimePrice> timePriceList = null;
        if (selfPack) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("productId", productId);
            param.put("prodBranchId", prodBranchId);
            param.put("beginDate", new Date(beginTime));
            param.put("endDate", new Date(endTime));
            timePriceList = prodTimePriceDAO.selectProdTimePriceByParams(param);
        } else {
            timePriceList = productTimePriceLogic.selectProdTimePriceByProductId(productId, prodBranchId, beginTime, endTime);
        }
        CalendarModel calendarModel = new CalendarModel();
        calendarModel.setTimePrice(timePriceList, new Date(beginTime));
        return calendarModel;
    }

    public List<ProdProduct> selectProductByParms(Map map) {
        return prodProductDAO.selectProductByParms(map);
    }

    public List<ProdProduct> selectRouteProductByParam(Map map) {
        return prodProductDAO.getRouteListByParam(map);
    }

    public void setComCodesetDAO(ComCodesetDAO comCodesetDAO) {
        this.comCodesetDAO = comCodesetDAO;
    }

    public void setProdProductDAO(ProdProductDAO prodProductDAO) {
        this.prodProductDAO = prodProductDAO;
    }

    public void setProdProductItemDAO(ProdProductItemDAO prodProductItemDAO) {
        this.prodProductItemDAO = prodProductItemDAO;
    }

    public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
        this.productTimePriceLogic = productTimePriceLogic;
    }

    /**
     * 复制产品信息
     *
     * @param srcProductId 源产品标识
     * @param operatorName 操作人
     * @return
     * @throws Exception
     */
    public Long copyProduct(final Long srcProductId, final String operatorName) throws Exception {
        ProdProduct po = prodProductDAO.selectProductDetailByPrimaryKey(srcProductId);
        if (po != null && po.getManagerId() == null)
            throw new Exception("该产品的产品经理为空，不能复制.");
        else if (po != null) {
            try {
            	po.setProductId(null);
                po.setProductName(po.getProductName() + "(复制)");
                po.setAuditingStatus(Constant.PRODUCT_AUDITING_STATUS.PRODUCTS_SUBMITTED.getCode());
                po.setOnLine("false");
                prodProductDAO.insert(po);
                comLogDAO.insert("PROD_PRODUCT", null, po.getProductId(), operatorName,
                        Constant.COM_LOG_ORDER_EVENT.insertProdProduct.name(),
                        "复制销售产品", LogViewUtil.logNewStr(operatorName) + "(原产品" + srcProductId + "复制)", null);

                comMessageService.addSystemComMessage(Constant.EVENT_TYPE.ADD_PRODUCT.name(), "新增销售产品:" + po.getProductName(), Constant.SYSTEM_USER);

                List<ProdProductChannel> list = prodProductDAO.getProductChannelByProductId(srcProductId);
                for (ProdProductChannel prodProductChannel : list) {
                    ProdProductChannel channel = new ProdProductChannel();
                    channel.setProductId(po.getProductId());
                    channel.setProductChannel(prodProductChannel.getProductChannel());
                    prodProductDAO.insertChannel(channel);
                }

                List<ProdProductPlace> placeList = prodProductPlaceDAO.selectByProductId(srcProductId);
                for (ProdProductPlace prodProductPlace : placeList) {
                    ProdProductPlace productPlace = new ProdProductPlace();
                    productPlace.setProductId(po.getProductId());
                    productPlace.setPlaceId(prodProductPlace.getPlaceId());
                    productPlace.setFrom(prodProductPlace.getFrom());
                    productPlace.setTo(prodProductPlace.getTo());
                    prodProductPlaceDAO.insert(productPlace);
                }
//				if(po.isRoute()){
//					copyViewJourney(po, srcProductId);
//				}
                copyViewContent(po, srcProductId);

                //复制电子合同
                if (po.isEContract()) {
                    ProdEContract prodEContract = getProdEContractByProductId(srcProductId);
                    prodEContract.setProductId(po.getProductId());
                    prodEContractDAO.insert(prodEContract);
                }

                po.setIsForegin(po.getIsForegin());
                po.setRegionName(po.getRegionName());


            } catch (Exception e) {
                e.printStackTrace();
            }
            return po.getProductId();
        } else
            throw new Exception("产品不存在.");
    }

    private void copyViewContent(ProdProduct product, Long beforeProductId) {
        ViewPage viewPage = viewPageDAO.selectByPrimaryKey(beforeProductId);
        if (viewPage != null) {
            ViewPage newViewPage = new ViewPage();
            newViewPage.setProductId(product.getProductId());
            viewPageDAO.insert(newViewPage);

            List<ViewContent> viewContentList = viewPage.getContentList();
            for (ViewContent viewContent : viewContentList) {
                viewContent.setContentId(null);
                viewContent.setPageId(product.getProductId());
                viewPageDAO.insertViewContent(viewContent);
            }
        }
    }

    public Long getProductNearMarketPrice(Long productId) {
        return prodProductItemDAO.getProductNearMarketPrice(productId);
    }

    public void updateByPrimaryKey(ProdProduct pp, String operatorName) {
        prodProductDAO.updateByPrimaryKey(pp);
    }

    public void deleteProduct(Map params, String operatorName) {
        prodProductDAO.markIsValid(params);
        comLogDAO.insert("PROD_PRODUCT", null, (Long) params.get("productId"), operatorName,
                Constant.COM_LOG_ORDER_EVENT.delteProdProduct.name(),
                "删除销售产品", LogViewUtil.logDeleteStr(operatorName), null);
    }

    public ProdHotel selectProdHotelByPrimaryKey(Long id) {
        return this.prodProductDAO.selectProdHotelByPrimaryKey(id);
    }

    public ProdRoute selectProdRouteByPrimaryKey(Long id) {
        return this.prodProductDAO.selectProdRouteByPrimaryKey(id);
    }

    public ProdTicket selectProdTicketByPrimaryKey(Long id) {
        return this.prodProductDAO.selectProdTicketByPrimaryKey(id);
    }


    public boolean checkResourceNeedConfirm(Long prodBranchId, Date specDate) {
        //资源需确认
        boolean resourceConfirm = false;
        if (specDate != null) {
            List<MetaProductBranch> list = metaProductBranchDAO.getMetaProductBranchByProdBranchId(prodBranchId);
            for (MetaProductBranch metaBranch : list) {
                resourceConfirm = productResourceConfirmLogic.isResourceConfirm(metaBranch, specDate);
                if (resourceConfirm) {//如果已经遇到需要资源确认就跳出
                    break;
                }
            }
        }
        return resourceConfirm;
    }


    public void setProductResourceConfirmLogic(
            ProductResourceConfirmLogic productResourceConfirmLogic) {
        this.productResourceConfirmLogic = productResourceConfirmLogic;
    }

    public Long selectBizCodeByProductId(Long productId) {
        return prodProductDAO.selectBizCodeByProductId(productId);
    }

    public ProdProduct getProdProduct(Long ProdProductId) {
        return prodProductDAO.selectProductDetailByPrimaryKey(ProdProductId);
    }

    public Integer selectRowCount(Map searchConds) {
        return prodProductDAO.selectRowCount(searchConds);
    }

    public List<ProdProduct> selectBizCode(Map param) {
        return prodProductDAO.selectBizCode(param);
    }

    public ProductGroupDAO getProductGroupDAO() {
        return productGroupDAO;
    }

    public void setProductGroupDAO(ProductGroupDAO productGroupDAO) {
        this.productGroupDAO = productGroupDAO;
    }

    public List<ProductGroup> getProductGroupList() {
        return productGroupDAO.selectAll();
    }

    public void packToGroup(Long productId, List<Long> relatProductIds, Long groupId, String operatorName) {
        //该功能现在不使用
//		String prodName="";
//		for(Long relatProductId : relatProductIds) {
//			Map<String, Long> params = new HashMap<String, Long>();
//			params.put("relatProductId", relatProductId);
//			params.put("productId", productId);
//			ProdRelation prodRelation = prodRelationDAO.selectByRelatProductIdAndProductId(params);
//			prodRelation.setProGroupId(groupId);
//			ProdProduct prodProduct=this.prodProductDAO.selectProductDetailByPrimaryKey(prodRelation.getRelatProductId());
//			prodName+=prodProduct.getProductName()+" ";
//			prodRelationDAO.updateProdRelation(prodRelation);
//		}
//		ProductGroup ProductGroup=this.productGroupDAO.selectByPrimaryKey(groupId);
//		comLogDAO.insert("PRODUCT_GROUP",productId,ProductGroup.getGroupId(),operatorName,
//				Constant.COM_LOG_ORDER_EVENT.updateProdRelation.name(),
//				"关联的销售产品",LogViewUtil.logEditStr("请选择组别", prodName+"打包到【"+ProductGroup.getGroupName()+"】"), "PROD_PRODUCT");
    }

    public boolean isProductSellable(Long prodBranchId, Long quantity,
                                     Date visitTime) {
        return this.productSellableLogic.isProductSellable(prodBranchId, quantity, visitTime);
    }

    public ProdTimePriceDAO getProdTimePriceDAO() {
        return prodTimePriceDAO;
    }

    public void setProdTimePriceDAO(ProdTimePriceDAO prodTimePriceDAO) {
        this.prodTimePriceDAO = prodTimePriceDAO;
    }

    public void updateTimeById(Long productId, Date onlineTime, Date offlineTime, String operatorName) {
        prodProductDAO.updateTimeById(productId, onlineTime, offlineTime);
        comLogDAO.insert("PROD_PRODUCT", null, productId, operatorName,
                Constant.COM_LOG_ORDER_EVENT.updateProdProduct.name(),
                "编辑销售产品", LogViewUtil.logEditStr("上下线时间", DateUtil.getFormatDate(onlineTime, "yyyy-MM-dd") + "," + DateUtil.getFormatDate(offlineTime, "yyyy-MM-dd")), null);
    }

    @Override
    public void updateProdRecommendWord(ProdProduct prodProduct) {
        ProdProduct product = getProdProductById(prodProduct.getProductId());
        Assert.notNull(product, "产品不存在");
        this.prodProductDAO.updateProdRecommendWord(prodProduct);
    }

    public void updatePlacePrice() {

    }

    public ProdHotel getProdHotelById(Long productId) {
        return prodProductDAO.getProdHotelById(productId);
    }

    public void setProdEContractDAO(ProdEContractDAO prodEContractDAO) {
        this.prodEContractDAO = prodEContractDAO;
    }

    public Long saveAssembly(ProdAssemblyPoint ap, String operatorName) {

        Long id = this.prodProductDAO.insertAssembly(ap);

        /**20120307 zx add log*/
        comLogDAO.insert("PROD_ASSEMBLY_POINT", ap.getProductId(), id, operatorName,
                Constant.COM_LOG_PRODUCT_EVENT.addAssemblyPoint.name(),
                "添加上车地点", MessageFormat.format("添加名称为[ {0} ]的上车地点", ap.getAssemblyPoint()), "PROD_PRODUCT");
        /***/

        return id;
    }

    public List<ProdAssemblyPoint> queryAssembly(Long productId) {
        return this.prodProductDAO.selectAssembly(productId);
    }

    public void delAssembly(Long assemblyPointId, String operatorName) {

        ProdAssemblyPoint prodAssemblyPoint = this.prodProductDAO.selectAssemblyById(assemblyPointId);
        if (prodAssemblyPoint != null) {
            this.prodProductDAO.delAssembly(assemblyPointId);

            /**20120307 zx add log*/
            comLogDAO.insert("PROD_ASSEMBLY_POINT", prodAssemblyPoint.getProductId(), assemblyPointId, operatorName,
                    Constant.COM_LOG_PRODUCT_EVENT.deleteAssemblyPoint.name(),
                    "删除上车地点", MessageFormat.format("删除名称为[ {0} ]的上车地点", prodAssemblyPoint.getAssemblyPoint()), "PROD_PRODUCT");
            /***/
        }
    }

    public void saveEContract(final ProdEContract prodEContract) {
    	ProdEContract pc = prodEContractDAO.selectByProductId(prodEContract.getProductId());
    	if (null != pc) {
    		if (null == pc.geteContractId()) {
                prodEContractDAO.insert(prodEContract);
            } else {
            	if (!(StringUtils.trimToNull(prodEContract.getEContractTemplate()) == null)) {
            		prodEContractDAO.updateByProductId(prodEContract);
            	}
            }
    	} else {
    		prodEContractDAO.insert(prodEContract);
    	}
    }

    public ProdEContract getProdEContractByProductId(Long productId) {
        return prodEContractDAO.selectByProductId(productId);
    }

    public void setProductSellableLogic(ProductSellableLogic productSellableLogic) {
        this.productSellableLogic = productSellableLogic;
    }

    public void markIsSellable(Map params, String operatorName) {
        this.prodProductDAO.markIsSellable(params);

        //进行下线操作的门票和线路，审核状态改为“商务待审核”
        Long productId = (Long)params.get("productId");
        String onLine = (String)params.get("onLine");
        if (onLine != null && productId != null) {
            ProdProduct product = prodProductDAO.selectByPrimaryKey(productId);
            if (product != null && "TICKET,ROUTE".contains(product.getProductType())) {
                if (onLine.equals("false")) {
                    prodProductDAO.updateAuditingStatus(product.getProductId(), Constant.PRODUCT_AUDITING_STATUS.BUSINESS_REFUND_SUBMITTED.getCode());
                } else if (onLine.equals("true")) {
                    prodProductDAO.updateAuditingStatus(product.getProductId(), Constant.PRODUCT_AUDITING_STATUS.AUDIT_COMPLETED.getCode());
                }
            }
        }

        comLogDAO.insert("PROD_PRODUCT", null, (Long) params.get("productId"), operatorName, Constant.COM_LOG_ORDER_EVENT.updateProdProduct.name(),
                "更新销售产品", MessageFormat.format("更新名称为[{0}]的销售产品状态为[{1}]", params.get("productName"), params.get("onLineStr")), null);
    }

    public List<ProdProduct> selectManager(Map params) {
        return this.prodProductDAO.selectManager(params);
    }

    public Integer selectManagerCount(Map params) {
        return this.prodProductDAO.selectManagerCount(params);
    }

    public void updateManager(HashMap params) {
        // mod by zhangwengang 2013/4/1 批量修改销售采购经理时部门更新 start
        prodProductDAO.updateManager(params);
        // mod by zhangwengang 2013/4/1 批量修改销售采购经理时部门更新 end
    }

    public void updateOrgIds(Map<String, Object> params) {
        prodProductDAO.updateOrgIds(params);
    }

    @Override
    public TimePrice getProductPrice(Long product, Date date) {
        ProdProductBranch branch = prodProductBranchDAO.getProductDefaultBranchByProductId(product);
        if (branch == null) {
            return null;
        }
        TimePrice tp = productTimePriceLogic.calcProdTimePrice(branch.getProdBranchId(), date);
        return tp;
    }

    @Deprecated
    public List<ProdProductItem> selectProductByMetaId(Long productId) {
        return this.prodProductItemDAO.selectProductByMetaId(productId);
    }

    /**
     * @author shihui
     * 产品列表查询
     */
    @Override
    public List<ProdProduct> selectProductListByParams(Map<String, Object> params) {
        String type = (String) params.get("productType");
        if (Constant.PRODUCT_TYPE.TICKET.name().equals(type)) {
            return prodProductDAO.selectTicketProductListByParams(params);
        } else if (Constant.PRODUCT_TYPE.HOTEL.name().equals(type)) {
            return prodProductDAO.selectHotelProductListByParams(params);
        } else if (Constant.PRODUCT_TYPE.TRAFFIC.name().equals(type)) {
            return prodProductDAO.selectTrainProductListByParams(params);
        } else {
            return prodProductDAO.selectOtherProductListByParams(params);
        }
    }

    /**
     * @author shihui
     * 类别列表查询
     */
    @Override
    public List<ProdProductBranch> selectProductBranchListByParams(Map<String, Object> params) {
        String type = (String) params.get("productType");
        if (Constant.PRODUCT_TYPE.TICKET.name().equals(type)) {
            return prodProductDAO.selectTicketBranchListByParams(params);
        } else if (Constant.PRODUCT_TYPE.HOTEL.name().equals(type)) {
            return prodProductDAO.selectHotelBranchListByParams(params);
        } else {
            return prodProductDAO.selectOtherBranchListByParams(params);
        }
    }

    /**
     * @author shihui
     * 酒店类别列表查询
     */
    private List<ProdProductBranch> selectHotelBranchListByParams(Map<String, Object> params) {
        return prodProductDAO.selectHotelBranchListByParams(params);
    }

    @Override
    public List<ProdRouteDate> selectRouteProductListByParams(Map<String, Object> params) {
        return prodProductDAO.selectRouteProductListByParams(params);
    }

    /**
     * @param prodProductBranchDAO the prodProductBranchDAO to set
     */
    public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
        this.prodProductBranchDAO = prodProductBranchDAO;
    }

    public void setComPlaceDAO(ComPlaceDAO comPlaceDAO) {
        this.comPlaceDAO = comPlaceDAO;
    }

    public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
        this.metaProductBranchDAO = metaProductBranchDAO;
    }

    @Override
    public void updateRouteOther(ProdRoute route, String operatorName) {
        prodProductDAO.updateRoute(route);
    }

    @Override
    public Long selectCountProductListByParams(Map<String, Object> params) {
        return prodProductDAO.selectCountProductListByParams(params);
    }

    @Override
    public Long selectCountRouteListByParams(Map<String, Object> params) {
        return prodProductDAO.selectCountRouteListByParams(params);
    }

    @Override
    public List<Long> selectRouteProductIdsByParams(Map<String, Object> params) {
        return prodProductDAO.selectRouteProductIdsByParams(params);
    }

    public ProdRoute getProdRouteById(Long productId) {
        ProdRoute prodRoute = prodProductDAO.getProdRouteById(productId);
        return prodRoute;
    }


    public void setViewPageDAO(ViewPageDAO viewPageDAO) {
        this.viewPageDAO = viewPageDAO;
    }

    @Override
    public Place getFromDestByProductId(Long productId) {
        return comPlaceDAO.getFromDestByProductId(productId);
    }

    @Override
    public boolean isSellProductByChannel(Long productId, String channel) {
        List<String> channelId = prodProductDAO.selectChannelByProductId(productId);
        for (String string : channelId) {
            if (string.equalsIgnoreCase(channel)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Date getProductsLastCancelTime(List<Long> branchIdList, Date visitTime) {
        Date lastCancelTime = null;
        Date tempTime = null;
        for (Long branchId : branchIdList) {
            TimePrice timePrice = productTimePriceLogic.calcProdTimePrice(branchId, visitTime);
            if (timePrice == null || !timePrice.isAble()) {
                return null;
            }
            if (timePrice.getCancelHour() != null) {
                tempTime = DateUtils.addMinutes(timePrice.getSpecDate(), -timePrice.getCancelHour()
                        .intValue());
                if (tempTime != null) {
                    if (lastCancelTime == null) {
                        lastCancelTime = tempTime;
                    } else if (lastCancelTime.after(tempTime)) {
                        lastCancelTime = tempTime;
                    }
                }
            }
        }
        return lastCancelTime;
    }

    @Override
    public List<ProdAssemblyPoint> getAssemblyPoints(Long productId) {
        return prodProductDAO.selectAssembly(productId);
    }

    @Override
    public List<ProdProductBranch> getProductBranchDetailByProductId(
            Long productId, Long removeBranchId, Date visitTime, String additional, boolean checkOnline) {
        List<ProdProductBranch> list = getProductBranchByProductId(productId, additional);
        List<ProdProductBranch> res = new ArrayList<ProdProductBranch>();
        ProdProduct product = prodProductDAO.selectProductDetailByPrimaryKey(productId);
        if (product == null) {
            return null;
        }

        if (CollectionUtils.isNotEmpty(list)) {
            for (ProdProductBranch branch : list) {
                if (removeBranchId != null && branch.getProdBranchId().equals(removeBranchId)) {
                    continue;
                }
                branch.setProdProduct(product);
                ProdProductBranch successBranch = null;
                //不定期产品取该类别有效期结束日期做校验
                if (product.IsAperiodic()) {
                    if (branch.getValidBeginTime() != null && branch.getValidEndTime() != null) {
                        if (!DateUtil.getDayStart(new Date()).after(branch.getValidEndTime())) {
                            visitTime = branch.getValidEndTime();
                            successBranch = prodProductBranchLogic.fill(branch, visitTime, false, checkOnline);
                        }
                    }
                } else {
                    successBranch = prodProductBranchLogic.fill(branch, visitTime, false, checkOnline);
                }

                if (successBranch != null) {
                    res.add(successBranch);
                }
            }
        }
        return res;
    }

    @Override
    public List<ProdProductBranch> getProductBranchByProductId(Long productId, String additional) {
        return prodProductBranchDAO.getProductBranchByProductId(productId, additional);
    }

    public void setProdProductBranchLogic(
            ProdProductBranchLogic prodProductBranchLogic) {
        this.prodProductBranchLogic = prodProductBranchLogic;
    }

    @Override

    public List<ProdProductRelation> getRelatProduct(Long productId, Date visitTime) {
        List<ProdProductRelation> list = new ArrayList<ProdProductRelation>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", productId);
        List<ProdProductRelation> relatProductlist = prodProductRelationDAO.selectProdRelationByParam(map);
        for (ProdProductRelation relat : relatProductlist) {
            if (prodProductBranchItemDAO.countProductItem(relat.getProdBranchId()) > 0) {// 如果产品进行了打包才加入到有效的相关产品列表中
                ProdProductBranch branch = prodProductBranchDAO.selectByPrimaryKey(relat.getProdBranchId());
                branch = prodProductBranchLogic.fill(branch, visitTime);
                if (branch != null) {
                    relat.setRelationProduct(branch.getProdProduct());
                    relat.setBranch(branch);
                    list.add(relat);
                }
            }
        }
        return list;
    }

    @Override
    public ProdProductBranch getProdBranchDetailByProdBranchId(
            Long prodBranchId, Date visitTime, boolean checkOnline) {
        ProdProductBranch branch = prodProductBranchDAO.selectByPrimaryKey(prodBranchId);
        if (branch == null) {
            return null;
        }
        return prodProductBranchLogic.fill(branch, visitTime, true, checkOnline);
    }

    @Override
    public ProdProductBranch getProductDefaultBranchByProductId(Long productId) {
        ProdProductBranch prodBranch = prodProductBranchDAO.getProductDefaultBranchByProductId(productId);
        if (null != prodBranch) {
            ProdProduct pp = prodProductDAO.selectByPrimaryKey(productId);
            prodBranch.setProdProduct(pp);
        }
        return prodBranch;
    }

    @Override
    public boolean isSellable(Long prodBranchId, Long quantity, Date visitTime) {
        return productSellableLogic.isProductSellable(prodBranchId, quantity, visitTime);
    }

    public boolean checkJourneyRequird(ViewBuyInfo buyInfo) {
        ViewProdProductJourneyDetail detail = prodJourneyLogic.getProductJourneyFromProductId(buyInfo.getProductId(), buyInfo.getVisitDate(), (long) buyInfo.getAdult(), (long) buyInfo.getChild(), true, buyInfo.getPackId());
        if (!detail.isSuccess()) {//如果行程读取不出来直接跳出
            return false;
        }
        Map<Long, TimeInfo> tiMap = buyInfo.getOrdItemProdMap();
        Collection<TimeInfo> timeInfoList = tiMap.values();
        FindJourneyProduct fjp = new FindJourneyProduct();

        for (ViewProdProductJourney vppj : detail.getProductJourneyList()) {
            Map<String, List<ProdJourneyProduct>> map = vppj.getProdJourneyGroupMap();
            for (String type : map.keySet()) {
                List<ProdJourneyProduct> list = map.get(type);
                if (CollectionUtils.isEmpty(list)) {
                    continue;
                }
                //判断是否已经查询过本类的商品,如果已经查过对组查处理必选时就可以直接跳过
                boolean find_flag = false;
                //行程产品不为空的情况如果组选项必选时
                for (ProdJourneyProduct pjp : list) {
                    if (pjp.hasRequire()) {//如果产品必选
                        fjp.setProdJourneyId(pjp.getJourneyProductId());
                        if (CollectionUtils.find(timeInfoList, fjp) == null) {
                            return false;
                        }
                        find_flag = true;
                    }
                }

                if (vppj.isPolicy(type) && !find_flag) {
                    for (ProdJourneyProduct pjp : list) {
                        fjp.setProdJourneyId(pjp.getJourneyProductId());

                        //查找到了产品就跳出
                        if (CollectionUtils.find(timeInfoList, fjp) != null) {
                            find_flag = true;
                            break;
                        }

                    }
                    //如果还是没有找到数据就算是行程不正常的
                    if (!find_flag) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * 根据销售产品ID获取最近的可售的时间价格表
     *
     * @param productId
     * @return
     */
    public TimePrice selectLowestPriceByProductId(Long productId) {
        return productTimePriceLogic.selectLowestPriceByProductId(productId);
    }

    class FindJourneyProduct implements Predicate {

        private Long prodJourneyId;

        @Override
        public boolean evaluate(Object arg0) {
            TimeInfo ti = (TimeInfo) arg0;
            return ti.getJourneyProductId() != null && ti.getJourneyProductId().equals(prodJourneyId);
        }

        /**
         * @param prodJourneyId the prodJourneyId to set
         */
        public void setProdJourneyId(Long prodJourneyId) {
            this.prodJourneyId = prodJourneyId;
        }
    }

    public void setComLogDAO(ComLogDAO comLogDAO) {
        this.comLogDAO = comLogDAO;
    }

    @Override
    public TimePrice calcProdTimePrice(Long prodBranchId, Date specDate) {
        return productTimePriceLogic.calcProdTimePrice(prodBranchId, specDate);
    }

    @Override
    public TimePrice calcProdTimePrice(Long prodBranchId, Date specDate, boolean isAnniversary) {
    	// add by gaoxin for 519
		String key = "DIST_TEMP_TIMEPRICE_519";
		Object ob = MemcachedUtil.getInstance().get(key);
		boolean isActive = false;
		if(ob != null){
			isActive= true;
		}
        return productTimePriceLogic.distCalcProdTimePrice(prodBranchId, specDate,isActive);
    }
    @Override
    public TimePrice buildTimePriceByPriceAndBranchId(Long prodBranchId, Long sellPrice){
    	return productTimePriceLogic.buildTimePriceByPriceAndBranchId(prodBranchId, sellPrice);
    }
    /**
     * 通过productName like %productName% 出productId结果集
     *
     * @param productName productName
     * @return productId List<Long>
     * @author ZHANG Nan
     */
    @Override
    public List<Long> selectProductIdsByLikeProductName(String productName) {
        if (StringUtils.isNotBlank(productName)) {
            return prodProductDAO.selectProductIdsByLikeProductName(productName);
        }
        return new ArrayList<Long>();
    }

    @Override
    public List<TimePrice> getMainProdTimePrice(Long productId, Long branchId) {
        List<TimePrice> timePriceList = null;
        ProdProduct product = prodProductDAO.selectByPrimaryKey(productId);
        if (product != null) {
            if (branchId == null) {
                ProdProductBranch prodBranch = prodProductBranchDAO.selectDefaultBranchByProductId(productId);
                if (prodBranch != null) {
                    timePriceList = productTimePriceLogic.getTimePriceList(productId, prodBranch.getProdBranchId(), product.getShowSaleDays());
                }
            } else {
                timePriceList = productTimePriceLogic.getTimePriceList(productId, branchId, product.getShowSaleDays());
            }
        }
        if (timePriceList == null) {
            return Collections.emptyList();
        }
        return timePriceList;
    }

    @Override
    public List<CalendarModel> selectSaleTimePriceByProductId(Long productId) {
        CalendarUtilV2 c = new CalendarUtilV2();
        return c.selectSaleTimePriceByProductId(productId);
    }

    @Override
    public List<CalendarModel> selectSaleTimePrice(Long prodBranchId) {
        CalendarUtilV2 c = new CalendarUtilV2();
        return c.selectSaleTimePrice(prodBranchId);
    }

    @Override
    public void updateTraffic(Long productId, Long metaProductId) {
        ProdProduct product = prodProductDAO.selectProductDetailByPrimaryKey(productId);
        MetaProduct mp = metaProductDAO.getMetaProduct(metaProductId, Constant.PRODUCT_TYPE.TRAFFIC.name());
        if (product == null || mp == null) {
            return;
        }

        if (product.isFlight()) {
            long len = selectProdProductBranchItemCount(productId);
            if (len == 1) {
                ProdTraffic pt = (ProdTraffic) product;
                MetaProductTraffic mpt = (MetaProductTraffic) mp;
                pt.setGoFlightId(mpt.getGoFlight());
                pt.setBackFlightId(mpt.getBackFlight());
                pt.setDays(mpt.getDays());
                pt.setDirection(mpt.getDirection());
                prodProductDAO.updateByPrimaryKey(pt);
            }
        }
    }


    public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
        this.metaProductDAO = metaProductDAO;
    }


    public void setProdJourneyLogic(ProdJourneyLogic prodJourneyLogic) {
        this.prodJourneyLogic = prodJourneyLogic;
    }


    @Override
    public Integer checkTimePriceByProductId(Long productId, String isSelftSign) {
        return prodProductDAO.checkTimePriceByProductId(productId, isSelftSign);
    }


    @Override
    public long selectProdProductBranchItemCount(Long productId) {
        return prodProductBranchItemDAO.countProductItemByProductId(productId);
    }

    @Override
    public List<ProdProduct> selectProductByMetaBranchId(Long metaBranchId) {
        return prodProductDAO.selectProductByMetaBranchId(metaBranchId);
    }

    @Override
    public ProdProduct selectProductByProdBranchId(Long prodBranchId) {
        return prodProductDAO.selectProductByProdBranchId(prodBranchId);
    }

    @Override
    public TimePrice getTimePriceByProdId(Long productId, Long prodBranchId, Date date) {
        return prodTimePriceDAO.getProdTimePrice(productId, prodBranchId, date);
    }

    @Override
    public ProdProductBranch getPhoneProdBranchDetailByProdBranchId(
            Long prodBranchId, Date visitTime, boolean checkOnline) {
        ProdProductBranch branch = prodProductBranchDAO.selectByPrimaryKey(prodBranchId);
        if (branch == null) {
            return null;
        }
        return prodProductBranchLogic.fill(branch, visitTime, true, checkOnline, false, true);
    }

    /**
     * 不定期产品时间价格表校验
     */
    public ResultHandle aperiodicTimePriceValidation(TimePrice bean, Long prodBranchId) {
        ResultHandle handle = new ResultHandle();
        //取最晚的时间价格
        TimePrice endTimePrice = prodTimePriceDAO.getMinOrMaxTimePriceByMetaBranchId(prodBranchId, true);
        if (endTimePrice != null) {
            //最早的时间价格
            TimePrice minTimePrice = prodTimePriceDAO.getMinOrMaxTimePriceByMetaBranchId(prodBranchId, false);
            Date minSpecDate = DateUtil.getDayStart(minTimePrice.getSpecDate());
            Date maxSpecDate = DateUtil.getDayStart(endTimePrice.getSpecDate());
            Date currentDate = DateUtil.getDayStart(new Date());
            Date beanBeginDate = DateUtil.getDayStart(bean.getBeginDate());
            Date beanEndDate = DateUtil.getDayStart(bean.getEndDate());
            //当前时间在已存在的时间价格结束期前
            if (!currentDate.after(maxSpecDate)) {
                if (!beanBeginDate.after(minSpecDate) && !beanEndDate.before(maxSpecDate)) {
                } else {
                    if (!bean.isClosed()) {
                        if (bean.getPrice() != endTimePrice.getPrice()) {
                            handle.setMsg("已存在的时间价格区间还未过期,本次修改的驴妈妈价必须跟已存在的时间价格区间的驴妈妈价一致");
                            return handle;
                        }
                    }
                }
            }
        }
        return handle;
    }


    @Override
    public Long selectCountQiPiaoProductListByParams(Map<String, Object> params) {
        return prodProductDAO.selectCountQiPiaoProductListByParams(params);
    }


    @Override
    public List<ProdProduct> selectQiPiaoProductListByParams(
            Map<String, Object> params) {
        return prodProductDAO.selectQiPiaoProductListByParams(params);
    }


    @Override
    public List<ProdProductBranch> selectQiPiaoBranchListByParams(
            Map<String, Object> params) {
        return prodProductDAO.selectQiPiaoBranchListByParams(params);
    }

    @Override
    public ResultHandle checkAllMetaSupplierContractStatus(Long productId) {
        ResultHandle handle = new ResultHandle();
        List<MetaProduct> metaList = metaProductDAO.getMetaProductsByProductId(productId);
        for (MetaProduct metaProduct : metaList) {
            Long supplierId = metaProduct.getSupplierId();
            SupSupplier sup = supplierService.getSupplier(supplierId);
            Date d = DateUtil.toDate("2013-06-20", DATE_FORMAT);
            //2013-06-20之前的不校验
            if (sup.getCreateTime().after(d)) {
                Long contractId = metaProduct.getContractId();
                if (contractId == null) {
                    handle.setMsg("该销售产品所绑定的采购产品id[" + metaProduct.getMetaProductId() + "]对应的供应商合同为空");
                    return handle;
                }
                SupContract contract = supContractService.getContract(contractId);
                if (contract == null) {
                    handle.setMsg("该销售产品所绑定的采购产品id[" + metaProduct.getMetaProductId() + "]对应的供应商合同为空");
                    return handle;
                }
                String audit = contract.getContractAudit();
                if (StringUtils.isEmpty(audit)) {
                    handle.setMsg("该销售产品所绑定的采购产品id[" + metaProduct.getMetaProductId() + "]对应的供应商合同未审核");
                    return handle;
                }
                if (!Constant.CONTRACT_AUDIT.PASS.name().equals(contract.getContractAudit())) {
                    handle.setMsg("该销售产品所绑定的采购产品id[" + metaProduct.getMetaProductId() + "]对应的供应商合同未审核");
                    return handle;
                }
            }
        }
        return handle;
    }

    @Override
    public List<TimePrice> selectProdTimePriceByParams(Map<String, Object> param) {
        return productTimePriceLogic.selectProdTimePriceByParams(param);
    }

    @Override
    public void updateTimePriceByViewMultijourney(TimePrice timeprice) {
        productTimePriceLogic.updateTimePriceByViewMultijourney(timeprice);
    }

    public void setSupplierService(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public void setSupContractService(SupContractService supContractService) {
        this.supContractService = supContractService;
    }

    public void setViewTravelTipsDAO(ViewTravelTipsDAO viewTravelTipsDAO) {
        this.viewTravelTipsDAO = viewTravelTipsDAO;
    }

    public void updatePaymentTarget(Map<String, Object> param) {
        this.prodProductDAO.updatePaymentTarget(param);
    }

    @Override
    public ProdTraffic getTrainProduct(String fullName) {
        // TODO Auto-generated method stub
        return prodProductDAO.getTrainProduct(fullName);
    }

    @Override
    public List<ViewTravelTips> selectViewTravelTipsByProductId(Long productId) {
        return viewTravelTipsDAO.selectByProductId(productId);
    }

    public void auditing(Long productId, String auditingStatus, String operatorName) {
        ProdProduct product = prodProductDAO.selectByPrimaryKey(productId);
        String oldAuditingStatus = product.getAuditingStatus();
        prodProductDAO.updateAuditingStatus(productId, auditingStatus);


        //日志
        comLogDAO.insert("PROD_PRODUCT", null, product.getProductId(), operatorName,
                Constant.COM_LOG_PRODUCT_EVENT.changeAuditingStatus.name(),
                "销售产品审核",
                LogViewUtil.logNewStr(operatorName) +
                        "(审核状态由“" +
                        Constant.PRODUCT_AUDITING_STATUS.getCnName(oldAuditingStatus) +
                        "”变为“" + Constant.PRODUCT_AUDITING_STATUS.getCnName(auditingStatus) +
                        "”)",
                null);

    }

    /**
     * 记录每个产品信息是否有敏感词
     *
     * */
	private void updateHasSensitiveWordByProductId(Long productId,
			boolean hasSensitiveWord) {
		String str = "N";
		if(hasSensitiveWord) {
			str = "Y";
		}
		prodProductDAO.updateHasSensitiveWordByProductId(productId, str);
	}

	@Override
	public void updateRefundByProductIds(Map<String, Object> params) {
		prodProductDAO.updateRefundByProductIds(params);
	}
	
	private SensitiveWordService sensitiveWordService;
	private ViewMultiJourneyService viewMultiJourneyService;
	private ViewPageJourneyService viewPageJourneyService;
	private ViewJourneyTipDAO viewJourneyTipDAO;
	private ViewPageService viewPageService;

	public void setViewPageService(ViewPageService viewPageService) {
		this.viewPageService = viewPageService;
	}

	public void setViewJourneyTipDAO(ViewJourneyTipDAO viewJourneyTipDAO) {
		this.viewJourneyTipDAO = viewJourneyTipDAO;
	}

	public void setSensitiveWordService(SensitiveWordService sensitiveWordService) {
		this.sensitiveWordService = sensitiveWordService;
	}
	
	public void setViewMultiJourneyService(
			ViewMultiJourneyService viewMultiJourneyService) {
		this.viewMultiJourneyService = viewMultiJourneyService;
	}

	public void setViewPageJourneyService(
			ViewPageJourneyService viewPageJourneyService) {
		this.viewPageJourneyService = viewPageJourneyService;
	}

	@Override
	public boolean checkAndUpdateIsHasSensitiveWords(Long productId) {
		if(productId != null) {
			ProdProduct product = prodProductDAO.selectProductDetailByPrimaryKey(productId);
			boolean flag = validateSW_1(product);//为true则为有敏感词
			updateHasSensitiveWordByProductId(productId, flag);
			return flag;
		}
		return false;
	}


	@Override
	public List<ProdProduct> selectProdToPlaceProduct(String productChannel,long placeId, int maxCount,long productId,String incodeProdId) {
	    return prodProductDAO.selectProdToPlaceProduct(productChannel, placeId, maxCount,productId,incodeProdId);
	}

	//校验产品各部分信息是否包含敏感词
    private boolean validateSW_1(ProdProduct product) {
    	if(product != null) {
	    	Long productId = product.getProductId();
	    	Pattern p_html = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
			//产品名称
	    	if(sensitiveWordService.checkSensitiveWords(product.getProductName())) {
	    		return true;
	    	}
			//所有类别名称和描述信息
			List<ProdProductBranch> branchList = prodProductBranchDAO.getProductBranchByProductId(productId, null,null,null);
			if(!branchList.isEmpty()) {
				for (int i = 0; i < branchList.size(); i++) {
					ProdProductBranch branch = branchList.get(i);
					if(sensitiveWordService.checkSensitiveWords(branch.getBranchName())) {
			    		return true;
			    	}
					if(StringUtils.isNotEmpty(branch.getDescription())) {
						Matcher m_html = p_html.matcher(branch.getDescription());
						String desc = m_html.replaceAll("");
						if(sensitiveWordService.checkSensitiveWords(desc)) {
				    		return true;
				    	}
					}
				}
			}
			//产品描述信息
			ViewPage viewPage= viewPageDAO.selectByPrimaryKey(productId);
			if(viewPage != null) {
				List<ViewContent> vcList = viewPage.getContentList();
				if(!vcList.isEmpty()) {
					for (int i = 0; i < vcList.size(); i++) {
						if(StringUtils.isNotEmpty(vcList.get(i).getContent())) {
					    	Matcher m_html = p_html.matcher(vcList.get(i).getContent());
					        String content = m_html.replaceAll("");
							if(sensitiveWordService.checkSensitiveWords(content)) {
					    		return true;
					    	}
						}
					}
				}
			}
			if(product.isRoute()) {
				//分别单行程和多行程(只有线路有多行程和单行程,其他类型产品均为单行程)
				ProdRoute pr = (ProdRoute)product;
				boolean hasMultiJourney = pr.hasMultiJourney();
				
				//多行程时:多行程标题和每个行程信息
				if(hasMultiJourney) {
					List<ViewMultiJourney> vmjList = viewMultiJourneyService.getAllMultiJourneyDetailByProductId(productId);
					if(!vmjList.isEmpty()) {
						for (int i = 0; i < vmjList.size(); i++) {
							ViewMultiJourney vmj = vmjList.get(i);
							if(sensitiveWordService.checkSensitiveWords(vmj.getJourneyName())) {
					    		return true;
					    	}
							if(sensitiveWordService.checkSensitiveWords(vmj.getContent())) {
					    		return true;
					    	}
							if(validateVJ(vmj.getViewJourneyList())) {
								return true;
							}
							
							//多行程费用包含
							ViewContent ccViewContent = viewPageService.getViewContentByMultiJourneyId(vmj.getMultiJourneyId(), Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name());
							if(ccViewContent != null) {
								if(sensitiveWordService.checkSensitiveWords(ccViewContent.getContent())) {
						    		return true;
						    	}
							}
							
							//费用不包含
							ViewContent nccViewContent = viewPageService.getViewContentByMultiJourneyId(vmj.getMultiJourneyId(), Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name());
							if(nccViewContent != null) {
								if(sensitiveWordService.checkSensitiveWords(nccViewContent.getContent())) {
						    		return true;
						    	}
							}
						}
					}
				} else {//单行程时:每个行程信息
					List<ViewJourney> vjList = viewPageJourneyService.getViewJourneysByProductId(productId);
					if(validateVJ(vjList)) {
						return true;
					}
				}
				
				//上车地点
				List<ProdAssemblyPoint> assemblyList = prodProductDAO.selectAssembly(productId);
				if(!assemblyList.isEmpty()) {
					for (int i = 0; i < assemblyList.size(); i++) {
						if(sensitiveWordService.checkSensitiveWords(assemblyList.get(i).getAssemblyPoint())) {
				    		return true;
				    	}
					}
				}
			}
			
			//一句话推荐
			/*if(sensitiveWordService.checkSensitiveWords(product.getRecommendInfoFirst())) {
	    		return true;
	    	}
			if(sensitiveWordService.checkSensitiveWords(product.getRecommendInfoSecond())) {
	    		return true;
	    	}
			if(sensitiveWordService.checkSensitiveWords(product.getRecommendInfoThird())) {
	    		return true;
	    	}*/
		}
		return false;
    }
    
    //校验每个行程的标题、用餐、住宿、内容
    private boolean validateVJ(List<ViewJourney> vjList) {
    	if(!vjList.isEmpty()) {
	    	for (int j = 0; j < vjList.size(); j++) {
				ViewJourney vj = vjList.get(j);
				if(sensitiveWordService.checkSensitiveWords(vj.getTitle())) {
		    		return true;
		    	}
				if(sensitiveWordService.checkSensitiveWords(vj.getContent())) {
		    		return true;
		    	}
				if(sensitiveWordService.checkSensitiveWords(vj.getDinner())) {
		    		return true;
		    	}
				if(sensitiveWordService.checkSensitiveWords(vj.getHotel())) {
		    		return true;
		    	}
				
				//校验每个行程小贴士
				List<ViewJourneyTips> journeyTipList = viewJourneyTipDAO.getViewJourneyTipsByJourneyId(vj.getJourneyId());
				if(!journeyTipList.isEmpty()) {
					for (int i = 0; i < journeyTipList.size(); i++) {
						ViewJourneyTips vjt = journeyTipList.get(i);
						if(sensitiveWordService.checkSensitiveWords(vjt.getTipTitle())) {
				    		return true;
				    	}
						if(sensitiveWordService.checkSensitiveWords(vjt.getTipContent())) {
				    		return true;
				    	}
					}
				}
			}
    	}
    	return false;
    }

	@Override
	public void markProductSensitive(Long productId, String hasSensitiveWord) {
		if(productId != null) {
    		//记录敏感词
			if(StringUtils.isNotEmpty(hasSensitiveWord) && "Y".equals(hasSensitiveWord)) {
				updateHasSensitiveWordByProductId(productId, true);
			} else {
				checkAndUpdateIsHasSensitiveWords(productId);
			}
		}
	}

  @Override
  public List<ProdProduct> queryHotSeqByProdTypeAndPlaceId(String prodPlaceIds, long orderCreateTime, String productType,String subProductType,String regionName, long endRow) {
      return prodProductDAO.queryHotSeqByProdTypeAndPlaceId(prodPlaceIds, orderCreateTime, productType, subProductType,regionName , endRow);
  }

	@Override
	public void insertProdHotSell(ProdHotSellSeq phss) {
	    prodProductDAO.insertProdHotSell(phss);
	    
	}
	@Override
	public void deleteProdHotSell() {
	    prodProductDAO.deleteProdHotSell();
	    
	}
	@Override
	public Map<String,List<ProdHotSellSeq>> queryProdHotSell(String channel,String baseChannel) {
	    Map<String,List<ProdHotSellSeq>> hotMap = new HashMap<String, List<ProdHotSellSeq>>();
	    List<ProdHotSellSeq> hotList ;
	    hotList = prodProductDAO.queryProdHotSell(channel+PindaoPageUtils.HOT_TYPE._ZZY.getCode(),baseChannel);
	    if(null != hotList && hotList.size()>0){
	        hotMap.put(channel+PindaoPageUtils.HOT_TYPE._ZZY.getCode(),hotList) ;
	    }
	    hotList = prodProductDAO.queryProdHotSell(channel+PindaoPageUtils.HOT_TYPE._GTY.getCode(),baseChannel);
        if(null != hotList && hotList.size()>0){
            hotMap.put(channel+PindaoPageUtils.HOT_TYPE._GTY.getCode(),hotList) ;
        }
        hotList = prodProductDAO.queryProdHotSell(channel+PindaoPageUtils.HOT_TYPE._JDL.getCode(),baseChannel);
        if(null != hotList && hotList.size()>0){
            hotMap.put(channel+PindaoPageUtils.HOT_TYPE._JDL.getCode(),hotList) ;
        }
        hotList = prodProductDAO.queryProdHotSell(channel+PindaoPageUtils.HOT_TYPE._MPL.getCode(),baseChannel);
        if(null != hotList && hotList.size()>0){
            hotMap.put(channel+PindaoPageUtils.HOT_TYPE._MPL.getCode(),hotList) ;
        }
        hotList = prodProductDAO.queryProdHotSell(channel+PindaoPageUtils.HOT_TYPE._ZYX.getCode(),baseChannel);
        if(null != hotList && hotList.size()>0){
            hotMap.put(channel+PindaoPageUtils.HOT_TYPE._ZYX.getCode(),hotList) ;
        }
        hotList = prodProductDAO.queryProdHotSell(channel+PindaoPageUtils.HOT_TYPE._CJY.getCode(),baseChannel);
        if(null != hotList && hotList.size()>0){
            hotMap.put(channel+PindaoPageUtils.HOT_TYPE._CJY.getCode(),hotList) ;
        }
	    return hotMap; 
	}

	@Override
	public long getProductBonusReturnAmount(Long productId) {
		ProdProduct product=prodProductDAO.selectByPrimaryKey(productId);
		return bonusReturnLogic.getProductBonusReturnAmount(product);
	}
	

	@Override
	public long getProductBonusReturnAmount(ProdProduct prodProduct) {
		return bonusReturnLogic.getProductBonusReturnAmount(prodProduct);
	}

	public void setBonusReturnLogic(BonusReturnLogic bonusReturnLogic) {
		this.bonusReturnLogic = bonusReturnLogic;
	}

	
}
