package com.lvmama.passport.callback.ws.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.service.pass.PassPortCodeService;
import com.lvmama.comm.bee.vo.Passport;
import com.lvmama.passport.callback.ws.IYongliguolvService;
import com.lvmama.passport.processor.UsedCodeProcessor;
/**
 * 永利国旅回调驴妈妈接口实现类
 * @author lipengcheng
 * @date 2011-11-3
 */
public class YongliguolvService implements IYongliguolvService {

	private static final Log log = LogFactory.getLog(YongliguolvService.class);
	private PassCodeService passCodeService;
	private UsedCodeProcessor usedCodeProcessor;
	private OrderService orderServiceProxy;

	public static final int CANCELLED = 2;
	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;

	@Override
	public int doAfterPerformance(String orderId, int childQuantity, int adultQuantity) {
		log.info("Yongliguolv has notified us to update performance ralated status");
		int flag = FAILURE;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("serialNo", orderId.trim());
		PassCode passCode = passCodeService.getPassCodeByParams(data);
		if (passCode == null) {
			flag = CANCELLED;
		} else {
			List<PassPortCode> passPortCodeList = passCodeService.queryProviderByCode(passCode.getCodeId());
			PassPortCode passPortCode = passPortCodeList.get(0);
			if (passPortCode != null) {
				// 履行对象
				Long targetId = passPortCode.getTargetId();
				Passport passport = new Passport();
				passport.setSerialno(passCode.getSerialNo());
				passport.setPortId(targetId);
				passport.setOutPortId(targetId.toString());
				passport.setDeviceId("Yongliguolv");
				// 如果永利国旅未提供儿童数和成人数,则从我们自己系统里取儿童数和成人数
				if (childQuantity == 0 && adultQuantity == 0) {
					OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrdOrderItemMetaId(passCode.getObjectId());
					if (ordOrder.getAllOrdOrderItemMetas() != null) {
						for (OrdOrderItemMeta ordOrderItemMeta : ordOrder.getAllOrdOrderItemMetas()) {
							if (passCode.getObjectId().equals(ordOrderItemMeta.getOrderItemMetaId())) {
								childQuantity = (int) ordOrderItemMeta.getTotalChildQuantity();
								adultQuantity = (int) ordOrderItemMeta.getTotalAdultQuantity();
								break;
							}
						}
					}
				}
				passport.setChild(String.valueOf(childQuantity));
				passport.setAdult(String.valueOf(adultQuantity));

				// 更新履行状态
				if (usedCodeProcessor.update(passport).equals("SUCCESS")) {
					flag = SUCCESS;
				}
			}
		}
		return flag;
	}


	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setUsedCodeProcessor(UsedCodeProcessor usedCodeProcessor) {
		this.usedCodeProcessor = usedCodeProcessor;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
 
}
