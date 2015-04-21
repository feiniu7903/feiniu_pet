package com.lvmama.back.sweb.ord;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.service.fax.FaxService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderFinishedBiz;
import com.lvmama.comm.work.builder.WorkOrderSenderBiz;

@ParentPackage("json-default")
@Results({ @Result(name = "memo", location = "/WEB-INF/pages/back/ord/ord_memo.jsp") })
/**
 * 订单备注操作类
 * 
 * @author shihui
 */
public class OrdMemoAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4531837914262307282L;
	private WorkOrderFinishedBiz workOrderFinishedProxy;
	private MetaProductService metaProductService;

	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;
	/**
	 * 
	 */
	private FaxService faxServiceProxy;
	/**
	 * 订单备注
	 */
	private List<OrdOrderMemo> orderMemos;
	/**
	 * 订单备注类型
	 */
	private List<CodeItem> memoTypes;
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 备注对象
	 */
	private OrdOrderMemo orderMemo;
	/**
	 * 当前操作人
	 */
	private String currentUsr;

	private WorkOrderSenderBiz workOrderProxy;

	/**
	 * 加载订单列表
	 */
	@Action("/ord/loadMemos")
	public String loadMemos() {
		try {
			this.orderMemos = orderServiceProxy.queryMemoByOrderId(new Long(
					orderId));
			this.memoTypes = CodeSet.getInstance().getCachedCodeList(
					Constant.CODE_TYPE.ORD_MEMO_TYPE.name());
			this.currentUsr = getOperatorName();
			if (orderMemo != null) {
				orderMemo = orderServiceProxy.selectMemo(orderMemo.getMemoId());
			}
			// this.hasEbkOrder = this.hasEbkOrderMate(orderId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "memo";
	}

	/**
	 * 新增或修改订单备注信息
	 */
	@Action("/ord/saveOrUpdateMemo")
	public void saveOrUpdateMemo() {
		try {
			boolean flag = true;
			orderMemo.setOperatorName(getOperatorName());

			// 新增
			if (orderMemo.getMemoId() == null) {
				orderMemo.setCreateTime(new Date());
				if (orderMemo.hasUserMemo()) {
					orderMemo.setStatus("false");
				}
				OrdOrderMemo m = orderServiceProxy.saveMemo(orderMemo,
						getOperatorName());
				flag = m != null ? true : false;
				if (orderMemo.hasUserMemo()) {
					OrdOrder order = orderServiceProxy
							.queryOrdOrderByOrderId(orderMemo.getOrderId());
					// if(!"true".equals(order.getResourceConfirm()) ||
					// order.isApproveResourceAmple()) {
					// 新增工单
					faxServiceProxy.updateUserMemoStatus(
							orderMemo.getOrderId(), "false");
					createWorkOrder(m, order);
					// }
				}
			} else {// 修改
				flag = orderServiceProxy.updateMemo(orderMemo,
						getOperatorName());
			}

			// add by zhangwengang 2013/06/03 支付后航班处理（长线计调）自动完成任务 start
			if (null != getSession().getAttribute("workTaskId")) {
				String paramId = getSession().getAttribute("workTaskId")
						.toString();
				workOrderFinishedProxy.finishWorkOrder(Long.valueOf(paramId),
						"", getSessionUser().getUserName());
				getSession().removeAttribute("workTaskId");
			}
			// add by zhangwengang 2013/06/03 支付后航班处理（长线计调）自动完成任务 end

			returnMessage(flag);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}

	/**
	 * 删除备注
	 */
	@Action("/ord/deleteMemo")
	public void deleteMemo() {
		try {
			boolean flag = this.orderServiceProxy.deleteMemo(
					orderMemo.getMemoId(), getOperatorName());
			returnMessage(flag);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}

	/**
	 * 返回操作成功信息
	 */
	private void returnMessage(boolean flag) {
		try {
			if (flag) {
				this.getResponse().getWriter().write("{result:true}");
			} else {
				this.getResponse().getWriter().write("{result:false}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean createWorkOrder(OrdOrderMemo memo, OrdOrder order) {

		if (StringUtil.isEmptyString(memo.getContent())) {
			return false;
		}

		OrdOrderItemProd ordOrderItemProd = order.getMainProduct();
		if (Constant.PRODUCT_TYPE.ROUTE.getCode().equals(
				ordOrderItemProd.getProductType())
				&& (Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.getCode()
						.equals(ordOrderItemProd.getSubProductType())
						|| Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.getCode()
								.equals(ordOrderItemProd.getSubProductType()))) {
			// 用户特殊要求工单（计调）
			workOrderProxy.sendWorkOrder(
					order,
					Constant.WORK_ORDER_TYPE_AND_SENDGROUP.YHTSYQSH
							.getWorkOrderTypeCode(),
					"/super_back/ord/showOrderMemoCheck.do?operateType=workOrder&orderId="
							+ order.getOrderId() + "&orderMemoId="
							+ memo.getMemoId(), Boolean.TRUE, Boolean.TRUE,
					null, null, null, null,null,false);
		} else {
			workOrderProxy.sendWorkOrder(
					order,
					Constant.WORK_ORDER_TYPE_AND_SENDGROUP.YHTSYQSHHT
							.getWorkOrderTypeCode(),
					"/super_back/ord/showOrderMemoCheck.do?operateType=workOrder&orderId="
							+ order.getOrderId() + "&orderMemoId="
							+ memo.getMemoId(), Boolean.TRUE, Boolean.FALSE,
					null, null, null, null,null,false);
		}

		return true;
	}

	public List<OrdOrderMemo> getOrderMemos() {
		return orderMemos;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public Long getOrderId() {
		return orderId;
	}

	public OrdOrderMemo getOrderMemo() {
		return orderMemo;
	}

	public void setOrderMemo(OrdOrderMemo orderMemo) {
		this.orderMemo = orderMemo;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public List<CodeItem> getMemoTypes() {
		return memoTypes;
	}

	public String getCurrentUsr() {
		return currentUsr;
	}

	public void setFaxServiceProxy(FaxService faxServiceProxy) {
		this.faxServiceProxy = faxServiceProxy;
	}

	public WorkOrderFinishedBiz getWorkOrderFinishedProxy() {
		return workOrderFinishedProxy;
	}

	public void setWorkOrderFinishedProxy(
			WorkOrderFinishedBiz workOrderFinishedProxy) {
		this.workOrderFinishedProxy = workOrderFinishedProxy;
	}

	public MetaProductService getMetaProductService() {
		return metaProductService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public WorkOrderSenderBiz getWorkOrderProxy() {
		return workOrderProxy;
	}

	public void setWorkOrderProxy(WorkOrderSenderBiz workOrderProxy) {
		this.workOrderProxy = workOrderProxy;
	}

}
