package com.lvmama.fenxiao.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.vo.ord.OrderChannelInfo;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.service.ord.OrdOrderChannelService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.fenxiao.service.tuangou.BaiduTuangouService;

public class BaiduTuangouProcessor implements MessageProcesser {
	private final Log log = LogFactory.getLog(BaiduTuangouProcessor.class);
	private BaiduTuangouService baiduTuangouService;
	private OrdOrderChannelService ordOrderChannelService;
	@Override
	public void process(Message message) {
		if (message.isProductCreateMsg() || message.isProductChangeMsg() || message.isProductOnoffMsg() || message.isProductProductChangeMsg()) {
			Long productId = message.getObjectId();
			if (baiduTuangouService.isTuangouProduct(productId)) {
				baiduTuangouService.saveBaiduTuangouProduct(productId);
			}
		}
		if (message.isPaymentSuccessCallMessage() || message.isOrderPaymentMsg()) {
			Long orderId = message.getObjectId();
			OrderChannelInfo orderChannel = ordOrderChannelService.queryByOrderIdAndChannel(orderId, Constant.BAIDU_TUANGOU_CHANNEL);
			if (orderChannel != null) {
				baiduTuangouService.saveOrder(orderId, orderChannel.getArg1(), orderChannel.getArg2());
			}
		}
	}

	public void setBaiduTuangouService(BaiduTuangouService baiduTuangouService) {
		this.baiduTuangouService = baiduTuangouService;
	}

	public void setOrdOrderChannelService(OrdOrderChannelService ordOrderChannelService) {
		this.ordOrderChannelService = ordOrderChannelService;
	}

}
