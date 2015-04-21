package com.lvmama.distribution.job;

import java.util.List;

import com.kayak.telpay.mpi.util.StringUtils;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.tmall.TaobaoProductSyncService;
import com.lvmama.distribution.service.impl.DistributionPushService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.distribution.DistributionMessage;
import com.lvmama.comm.vo.Constant;
import com.lvmama.distribution.service.DistributionCommonService;
import com.lvmama.distribution.service.impl.DistributionPushService.EVENT_TYPE;
import com.lvmama.distribution.service.impl.DistributionPushService.PUSH_STATUS;

public class DistributionPushJob {
    private final Log log = LogFactory.getLog(this.getClass());
    private DistributionCommonService distributionCommonService;
    private TaobaoProductSyncService taobaoProductSyncService;

    public void run(){
        if (Constant.getInstance().isJobRunnable()) {
            long startTime =System.currentTimeMillis();
            log.info("DistributionPush start");
            try {
                DistributionMessage distributionMessage = new DistributionMessage();
                distributionMessage.setStatus(PUSH_STATUS.unpushed.name());
                List<DistributionMessage> distributionMessages = distributionCommonService.queryMessageByMsgParams(distributionMessage);
                for (DistributionMessage message : distributionMessages) {
                    try {
                        if(EVENT_TYPE.offLine.name().equals( message.getEventType())){
                            distributionCommonService.pushOffLine(message);
                        }else{
                            distributionCommonService.pushProduct(message);
                        }
                    } catch (Exception e) {
                        log.error("DistributionPush Exception: messageId=" + message.getMessageId(), e);
                    }
                    /* 淘宝同步产品 */
                    try {
                        processTaobaoSync(message);
                    } catch (Exception e) {
                        log.error("DistributionPush processTaobaoSync Exception: messageId=" + message.getMessageId(), e);
                    } finally {
                        // 更新状态 为 已推送状态
                        message.setStatus(PUSH_STATUS.pushed.name());
                        distributionCommonService.saveOrUpdateMessage(message);
                    }
                }
            } catch (Exception e) {
                log.error("DistributionPush Exception:",e);
            }
            log.info("DistributionPush end useTime:"+((System.currentTimeMillis()-startTime)/1000));
        }
    }

    /**
     * 处理淘宝同步
     */
    private void processTaobaoSync(DistributionMessage message) {
        String objectType = message.getObjectType();
        // 判断是否为淘宝同步产品
        if (StringUtils.equals(objectType, DistributionPushService.OBJECT_TYPE.tb_product.name())) {
            String eventType = message.getEventType();
            // 产品修改
            if (StringUtils.equals(eventType, EVENT_TYPE.tb_product_change.name())) {
                taobaoProductSyncService.updateTaobaoTravelDuration(message.getObjectId());

                // 产品下线
            } else if (StringUtils.equals(eventType, EVENT_TYPE.tb_product_onoff.name())) {
                taobaoProductSyncService.updateTaobaoProdAuctionStatus(message.getObjectId());

                // 产品类别下线
            } else if (StringUtils.equals(eventType, EVENT_TYPE.tb_product_branch_onoff.name())) {
                Long prodBranchId = Long.valueOf(message.getDistributorChannel());
                taobaoProductSyncService.updateTaobaoProdBranchAuctionStatus(message.getObjectId(), prodBranchId);

                // 价格修改
            } else if (StringUtils.equals(eventType, EVENT_TYPE.tb_change_timeprice.name())) {
                String type;
                Long productId;
                Long prodBranchId;

                String dc = message.getDistributorChannel();
                String[] strs = dc.split(",");
                type = strs[0];

                productId = message.getObjectId();
                prodBranchId = Long.valueOf(strs[1]);
                // 门票
                if (type.equals(Constant.PRODUCT_TYPE.TICKET.name())) {
                    try {
                        taobaoProductSyncService.updateTaobaoTicketSkuEffDates(productId, prodBranchId);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("updateTaobaoTicketSkuEffDates error!productId=" + productId + ", prodBranchId=" + prodBranchId, e);
                    }

                    // 线路
                } if (type.equals(Constant.PRODUCT_TYPE.ROUTE.name())) {
                    try {
                        taobaoProductSyncService.updateTaobaoTravelComboCalendar(productId, prodBranchId);
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.error("updateTaobaoTravelComboCalendar error!productId=" + productId + ", prodBranchId=" + prodBranchId, e);
                    }
                }
            }
        }
    }

    public void setDistributionCommonService(
            DistributionCommonService distributionCommonService) {
        this.distributionCommonService = distributionCommonService;
    }

    public void setTaobaoProductSyncService(TaobaoProductSyncService taobaoProductSyncService) {
        this.taobaoProductSyncService = taobaoProductSyncService;
    }
}
