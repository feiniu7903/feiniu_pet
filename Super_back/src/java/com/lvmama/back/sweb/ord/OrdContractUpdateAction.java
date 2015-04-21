package com.lvmama.back.sweb.ord;
/**
 * 当订单信息被更新之后，重新生成合同
 * @author shangzhengyuan
 * @create_date 2012-07-26
 * @version 1.0
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdEcontractSignLog;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.client.EContractClient;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.utils.XmlObjectUtil;
import com.lvmama.comm.utils.econtract.EcontractUtil;
import com.lvmama.comm.vo.Constant;
@ParentPackage("json-default")
public class OrdContractUpdateAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7712494219431722467L;
	
	private static final Logger LOG = Logger.getLogger(OrdContractUpdateAction.class);
	/**
	 * 订单编号
	 */
	private Long orderId;
	
	/**
	 * 订单远程服务接口
	 */
	private OrderService orderServiceProxy;
	/**
	 * 订单合同保存接口
	 */
	private OrdEContractService ordEContractService;
	/**
	 * 日志接口
	 */
	private TopicMessageProducer orderMessageProducer;
	private ProdProductService prodProductService;
	private EContractClient contractClient;
	/**
	 * 返回信息
	 */
	private String result;
	
	private FSClient fsClient;
	
	@Action(value = "/ajax/updateContract", results = @Result(type = "json", name = "updateContract", params = {"orderId","productId"}))
	public String updateContract() throws Exception{
		OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (null == order) {
			LOG.error("订单为空，无法重新生成电子合同");
			result = "订单为空";
			return "updateContract";
		}
		if (!order.isCanceled() && order.isNeedEContract()) {
			String operater = getOperatorName();
			OrdEContract ordEContract = ordEContractService.queryByOrderId(orderId); 
			if (null == ordEContract) { 
			LOG.error("无法成功找到电子合同记录或电子合同，操作失败!"); 
			result = "无法成功找到电子合同记录"; 
			return "updateContract"; 
			} 
			ComFile comFile = fsClient.downloadFile(ordEContract.getContentFileId()); 
			Map<String,Object> contractData_t = (Map<String,Object>)XmlObjectUtil.xml2Bean(new String(comFile.getFileData(),"UTF-8"), java.util.HashMap.class); 
			String agree3 = (String) contractData_t.get("agree3"); 
			String agree4 = (String) contractData_t.get("agree4"); 
			String agree5 = (String) contractData_t.get("agree5"); 
			String agree6 = (String) contractData_t.get("agree6"); 
			boolean isUpdated = contractClient.updateEContract(order, "同意".equals(agree3), "同意".equals(agree4), "同意".equals(agree5), "同意".equals(agree6), operater);
			if(order.isEContractConfirmed()&& !order.isCanceled() && isUpdated){
				OrdEcontractSignLog signLog = new OrdEcontractSignLog();
				String contractNo =EcontractUtil.initContractNo(null!=ordEContract?ordEContract.getEcontractNo():null, order.getOrderId(),order.getVisitTime());
				signLog.setEcontractNo(contractNo);
				signLog.setSignMode(Constant.ECONTRACT_SIGN_TYPE.ONLINE_SIGN.name());
				signLog.setSignUser(order.getUserId()); //默认为下单人签约
				signLog.setSignMode(Constant.ECONTRACT_SIGN_TYPE.ONLINE_SIGN.name());
				Map parameters = new HashMap();
				parameters.put("econtractNo", ordEContract.getEcontractNo());
				List<OrdEcontractSignLog> list =ordEContractService.queryEcontractSignLog(parameters);
				if(null!=list && list.size() > 0){
					signLog = list.get(0);
					signLog.setEcontractNo(contractNo);
				}
				ordEContractService.insertEcontractSignLog(signLog);
				orderMessageProducer.sendMsg(MessageFactory.newOrderSendRefreshEContract(orderId));
			}
			result = "更新合同成功";
		}else{
			result = "订单已废或不需要签约";
		}
		return "updateContract";
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setContractClient(EContractClient contractClient) {
		this.contractClient = contractClient;
	}

	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}

}
