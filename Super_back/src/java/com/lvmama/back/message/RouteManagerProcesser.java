package com.lvmama.back.message;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.vo.Constant;

/**
 * 国内长线资源无需审核产品下及支付监听器,通知相应产品经理
 * 
 * @author shihui
 */
public class RouteManagerProcesser implements MessageProcesser {

	/**
	 * 日志对象
	 */
	private static final Log LOG = LogFactory
			.getLog(RouteManagerProcesser.class);

	/**
	 * 订单访问接口
	 */
	private OrderService orderServiceProxy;

	/**
	 * 消息接口
	 */
	private ComMessageService comMessageService;

	/**
	 * 产品接口
	 * */
	private ProdProductService prodProductService;

	/**
	 * 用户接口
	 * */
	private PermUserService permUserService;

	/**
	 * 消息处理.
	 * 
	 * @param message
	 * @author shihui
	 */
	@Override
	public void process(final Message message) {
		if (hasJMSMessageType(message)) {
			OrdOrder order = getOrder(message.getObjectId());
			// 长途跟团游//长途自由行
			if (Constant.ORDER_TYPE.FREENESS_LONG.name().equals(
					order.getOrderType())
					|| Constant.ORDER_TYPE.GROUP_LONG.name().equals(
							order.getOrderType())) {
				// 资源不需要确认
				if (!order.isNeedResourceConfirm()) {
					Long productId = order.getMainProduct().getProductId();
					String content = "";
					if (message.isOrderCreateMsg()) {
						content = "产品ID" + productId + "已下单，订单号"
								+ order.getOrderId();
					} else if (message.isOrderPartpayPayment()) {
						content = "订单" + order.getOrderId() + "部分付款成功";
					} else if (message.isOrderPaymentMsg()) {
						content = "订单" + order.getOrderId() + "付款成功";
					}
					Long managerId = prodProductService.getProdProduct(
							productId).getManagerId();
					Long departmentId = permUserService.getPermUserByUserId(managerId).getDepartmentId();
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("departmentId", departmentId);
					params.put("valid", "Y");
					
					params.put("maxResults", permUserService.queryPermUserByParamCount(params));
					params.put("skipResults", 0);
					List<PermUser> userList = permUserService.queryPermUserByParam(params);
					for (PermUser user : userList) {
						insertMsg(user.getUserName(), content);
					}
				}
			}
		}
	}

	/**
	 * 判断需要处理的消息类型.
	 * 
	 * @param message
	 * @return
	 */
	private boolean hasJMSMessageType(Message message) {
		return (message.isOrderCreateMsg() || message.isOrderPartpayPayment() || message
				.isOrderPaymentMsg());
	}

	/**
	 * 写入消息
	 * 
	 * @param receiver
	 * @param content
	 * @param date
	 */
	private void insertMsg(String receiver, String content) {
		ComMessage msg = new ComMessage();
		msg.setCreateTime(new Date());

		msg.setContent(content);
		msg.setReceiver(receiver);
		msg.setSender(Constant.SYSTEM_USER);
		msg.setStatus("CREATE");

		try {
			comMessageService.insertComMessage(msg);
		} catch (Exception ex) {
			LOG.warn("消息写入时异常", ex);
		}
	}

	/**
	 * 获取订单
	 * 
	 * @param orderId
	 *            订单号
	 * @return 订单实体
	 */
	private OrdOrder getOrder(final Long orderId) {
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		return order;
	}

	public void setOrderServiceProxy(final OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

}
