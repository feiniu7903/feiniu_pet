package com.lvmama.distribution.message;

import com.lvmama.comm.bee.po.distribution.DistributionMessage;
import com.lvmama.comm.bee.po.distribution.DistributorInfo;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.vo.Constant;
import com.lvmama.distribution.service.DistributionCommonService;
import com.lvmama.distribution.service.impl.DistributionPushService.EVENT_TYPE;
import com.lvmama.distribution.service.impl.DistributionPushService.OBJECT_TYPE;
import com.lvmama.distribution.service.impl.DistributionPushService.PUSH_STATUS;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class DistributionPushProcesser implements MessageProcesser{
    private final Log log=LogFactory.getLog(DistributionPushProcesser.class);
    private DistributionCommonService distributionCommonService;
    private ProdProductService prodProductService;
    private ProdProductBranchService prodProductBranchService;

    @Override
    public void process(Message message) {
        if(message.isOrderMessage() && !message.isPaymentSuccessCallMessage() && !message.isOrderCreateMsg() || message.isOrderPerformMsg() || message.isOrderPartpayPayment()){
            Long orderId=message.getObjectId();
            distributionCommonService.pushOrder(orderId);
        }
        //禁止对接方分销,在分销平台中点击“加入黑名单”，禁止对接方销售当前类别
        if(message.isDeleteDistributionProduct()){
            log.info("deleteDistributeProduct:"+message);
            Long branchId=message.getObjectId();
            String idStr=message.getAddition();
            if(StringUtils.isNotBlank(idStr)){
                String[] ids=idStr.split(",");
                for(String distributorIdStr : ids){
                    Long distributorId=Long.valueOf(distributorIdStr);
                    DistributorInfo distributor= distributionCommonService.getDistributorById(distributorId);
                    if(distributor.isPushUpdate()){
                        String distributorChannel = distributor.getDistributorInfoId()+","+ distributor.getDistributorCode()+","+distributor.getDistributorKey();
                        DistributionMessage distributionMessage = new DistributionMessage(branchId, OBJECT_TYPE.product.name(), EVENT_TYPE.offLine.name(), distributorChannel,PUSH_STATUS.unpushed.name() );
                        distributionCommonService.saveOrUpdateMessage(distributionMessage);
                    }
                }
            }
        }

        //产品修改
        if(message.isProductChangeMsg() || message.isProductOnoffMsg() || message.isProductProductChangeMsg() || message.isProductBranchOnoffMsg()||message.isDistributionCashBackUpdate()){
            log.info("productChange:"+message);
            Long productId=message.getObjectId();
            /* 淘宝同步 -- 产品信息修改 */
            addProductChange(message, productId);

            List<DistributorInfo> distributors = distributionCommonService.getDistributorsByProductId(productId);
            for (DistributorInfo distributor : distributors) {
                if(distributor.isPushUpdate()){
                    String distributorChannel = distributor.getDistributorInfoId()+","+ distributor.getDistributorCode()+","+distributor.getDistributorKey();
                    DistributionMessage distributionMessage = new DistributionMessage(productId, OBJECT_TYPE.product.name(), EVENT_TYPE.product.name(), distributorChannel, PUSH_STATUS.unpushed.name());
                    distributionCommonService.saveOrUpdateMessage(distributionMessage);
                }
            }
        }

        //修改采购价格和市场价格
        if(message.isChangeMetaTimePriceMsg() || message.isChangeMarketPriceMsg()){
            log.info("metaPriceChange:"+message);
            Long metaBranchId = message.getObjectId();
            List<ProdProduct> productList =  prodProductService.selectProductByMetaBranchId(metaBranchId);
            for(ProdProduct product : productList){
                Long productId = product.getProductId();
                List<DistributorInfo> distributors = distributionCommonService.getDistributorsByProductId(productId);
                for (DistributorInfo distributor : distributors) {
                    if(distributor.isPushUpdate()){
                        String distributorChannel = distributor.getDistributorInfoId()+","+ distributor.getDistributorCode()+","+distributor.getDistributorKey();
                        DistributionMessage distributionMessage = new DistributionMessage(productId, OBJECT_TYPE.product.name(), EVENT_TYPE.product.name(),distributorChannel, PUSH_STATUS.unpushed.name());
                        if(Constant.DISTRIBUTOR.QUNA.name().equals(distributor.getDistributorCode())){
                            distributionMessage.setEventType(EVENT_TYPE.price.name());
                        }
                        distributionCommonService.saveOrUpdateMessage(distributionMessage);
                    }
                }
                /* 淘宝处理同步 --- 采购价格和市场价格 */
                addTaobaoTimePriceChange(product);
            }
        }

        //销售价格
        if(message.isChangeSellPriceMsg()){
            log.info("productPriceChange:"+message);
            Long prodBranchId = message.getObjectId();
            ProdProduct product = prodProductService.selectProductByProdBranchId(prodBranchId);
            Long productId = product.getProductId();
            List<DistributorInfo> distributors = distributionCommonService.getDistributorsByProductId(productId);
            for (DistributorInfo distributor : distributors) {
                if(distributor.isPushUpdate()){
                    String distributorChannel = distributor.getDistributorInfoId()+","+ distributor.getDistributorCode()+","+distributor.getDistributorKey();
                    DistributionMessage distributionMessage = new DistributionMessage(productId, OBJECT_TYPE.product.name(), EVENT_TYPE.product.name(), distributorChannel,PUSH_STATUS.unpushed.name());
                    if(Constant.DISTRIBUTOR.QUNA.name().equals(distributor.getDistributorCode())){
                        distributionMessage.setEventType(EVENT_TYPE.price.name());
                    }
                    distributionCommonService.saveOrUpdateMessage(distributionMessage);
                }
            }
            /* 淘宝处理同步 --- 销售价格 */
            addTaobaoTimePriceChange(product, prodBranchId);
        }
    }

    /**
     * 添加淘宝产品修改信息
     */
    private void addProductChange(Message message, Long productId) {
        /* 淘宝处理同步 --- 产品信息修改 */
        if (message.isProductChangeMsg()) {
            DistributionMessage distributionMessage = new DistributionMessage(productId, OBJECT_TYPE.tb_product.name(), EVENT_TYPE.tb_product_change.name(), productId.toString(), PUSH_STATUS.unpushed.name());
            distributionCommonService.saveOrUpdateMessage(distributionMessage);
        }
        /* 淘宝处理同步 --- 销售产品上下线 */
        if (message.isProductOnoffMsg()) {
            DistributionMessage distributionMessage = new DistributionMessage(productId, OBJECT_TYPE.tb_product.name(), EVENT_TYPE.tb_product_onoff.name(), productId.toString(), PUSH_STATUS.unpushed.name());
            distributionCommonService.saveOrUpdateMessage(distributionMessage);
        }
        /* 淘宝处理同步 --- 销售产品类别上下线 */
        if (message.isProductBranchOnoffMsg()) {
            DistributionMessage distributionMessage = new DistributionMessage(productId, OBJECT_TYPE.tb_product.name(), EVENT_TYPE.tb_product_branch_onoff.name(), message.getAddition(), PUSH_STATUS.unpushed.name());
            distributionCommonService.saveOrUpdateMessage(distributionMessage);
        }
    }

    /**
     * 添加淘宝产品价格修改信息
     */
    private void addTaobaoTimePriceChange(ProdProduct product) {
        addTaobaoTimePriceChange(product, null);
    }

    /**
     * 添加淘宝产品价格修改信息
     */
    private void addTaobaoTimePriceChange(ProdProduct product, Long prodBranchId) {
        String type = product.getProductType();
        Long productId = product.getProductId();
        // 只对门票和线路做处理
        if (type.equals(Constant.PRODUCT_TYPE.TICKET.name()) || type.equals(Constant.PRODUCT_TYPE.ROUTE.name())) {
            String distributorChannel;
            if (type.equals(Constant.PRODUCT_TYPE.TICKET.name())) {
                distributorChannel = Constant.PRODUCT_TYPE.TICKET.name() + ",";
            } else {
                distributorChannel = Constant.PRODUCT_TYPE.ROUTE.name() + ",";
            }
            if (prodBranchId != null) {
                String dc = distributorChannel + prodBranchId;
                DistributionMessage distributionMessage = new DistributionMessage(productId, OBJECT_TYPE.tb_product.name(), EVENT_TYPE.tb_change_timeprice.name(), dc, PUSH_STATUS.unpushed.name());
                distributionCommonService.saveOrUpdateMessage(distributionMessage);
            } else {
                // 根据产品ID 获取产品类别
                List<ProdProductBranch> list = prodProductBranchService.getProductBranchByProductId(productId);
                for (ProdProductBranch prodProductBranch : list) {
                    prodBranchId = prodProductBranch.getProdBranchId();
                    String dc = distributorChannel + prodBranchId;
                    DistributionMessage distributionMessage = new DistributionMessage(productId, OBJECT_TYPE.tb_product.name(), EVENT_TYPE.tb_change_timeprice.name(), dc, PUSH_STATUS.unpushed.name());
                    distributionCommonService.saveOrUpdateMessage(distributionMessage);
                }
            }
        }
    }

    public void setDistributionCommonService(
            DistributionCommonService distributionCommonService) {
        this.distributionCommonService = distributionCommonService;
    }

    public void setProdProductService(ProdProductService prodProductService) {
        this.prodProductService = prodProductService;
    }

    public void setProdProductBranchService(ProdProductBranchService prodProductBranchService) {
        this.prodProductBranchService = prodProductBranchService;
    }
}
