package com.lvmama.clutter.web.place;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.RequestUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.clutter.utils.DistributionParseUtil;
import com.lvmama.clutter.utils.EBKConstant;
import com.lvmama.clutter.utils.EbkUserUtils;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.bee.po.eplace.EbkPerformLog;
import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.bee.po.pass.PassDevice;
import com.lvmama.comm.bee.service.eplace.EbkPerformLogService;
import com.lvmama.comm.bee.service.eplace.EbkUserService;
import com.lvmama.comm.bee.service.ord.OrderPerformService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.ord.OrdOrderPerformResourceVO;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.po.work.WorkOrderType;
import com.lvmama.comm.pet.service.work.PublicWorkOrderService;
import com.lvmama.comm.pet.service.work.WorkOrderTypeService;
import com.lvmama.comm.pet.vo.WorkOrderCreateParam;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.EBK_PERFORM_LOG_STATUS;
import com.lvmama.distribution.util.RequestUtil;

/**
 * E景通通关，基于Web版修改
 * URI as:http://localhost/clutter/supplier/passInfo.do?userId=3481&orderId=1310953&targetId=3199&item=1361242_5,1361243_3,1361244_2
 * @author 张克行
 */
public class EbkPassportAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	private Log log = LogFactory.getLog(EbkPassportAction.class);
	
	private OrderPerformService orderPerformProxy;
	private PassCodeService passCodeService;
	private TopicMessageProducer passportMessageProducer;
	private EbkUserService ebkUserService = null;
	private PublicWorkOrderService publicWorkOrderService;
	private WorkOrderTypeService workOrderTypeService;
	private OrderPerformService orderPerformService;
	private EbkPerformLogService ebkPerformLogService;
	
	//E景通用户ID
	private long userId;
	//刷码设备唯一标识
	private String udid;
	//辅助码
	private String addCode;
	private Long[] quantity ;
	private Long[] orderItemMetaId ;
	//客户端实际履行时间
	private String performTime;
	private Date actualPerformTime = null;
	//客户端的签名
	private String signName;
	private String remark;


	
	@Action("/supplier/passInfo")
	public void passInfo() {
		log.info("POST:"+getRequestMap().toString());
		String uuid = addCode+"-"+UUID.randomUUID().toString();
		
		String passinfo = this.toString();
		log.info(passinfo);
		
		saveRequestParams(uuid,EBK_PERFORM_LOG_STATUS.INSERT,null);
		
		JSONObject json=new JSONObject();
		if(signError()){
			json.put("code", EBKConstant.MSG_LEVEL.SIGN_ERROR.getValue());
			json.put("message", "无效的访问!");
			
			log.info("EBK 通关，签名错误");
			
			sendAjaxResult(uuid,json);
			return;
		}
		
		ResultHandle msg = null ;
		
		boolean returnFlag=true;
		boolean hasException = false;
		Date actualPerformTime = StringUtils.isNotEmpty(performTime)?DateUtil.stringToDate(performTime, "yyyy-MM-dd HH:mm:ss"):null;
		
		if(	  udid==null 
			||addCode==null
			||(quantity==null)!=(orderItemMetaId==null)//份数和子子项其一如果为空，条件成立。
			||((quantity!=null && orderItemMetaId!=null) && (quantity.length!=orderItemMetaId.length))
			||StringUtils.isEmpty(performTime)
			||actualPerformTime==null){
			
			json.put("code", EBKConstant.MSG_LEVEL.PARAM_ERROR.getValue());
			json.put("message", "参数输入不正确");
			
			log.info("EBK 通关失败，"+json.toString());
			
			sendAjaxResult(uuid,json);
			return;
		}
		
		EbkUser ebkUser = EbkUserUtils.availableUser(ebkUserService, userId);
		if (null== ebkUser || !EbkUserUtils.hasBeenBindingToDevice(ebkUserService, ebkUser, udid)) {
			returnFlag=false;
			/**
			 * 
			 */
			json.put("code", EBKConstant.MSG_LEVEL.SUCCESS.getValue());
			json.put("message", "订单通关失败，您没有权限");
			
			log.info("EBK 通关失败，"+json.toString());
			
			sendAjaxResult(uuid,json);
			return;
		}
		try {
			msg = orderPerformProxy.perform(addCode, udid, quantity, orderItemMetaId, ((StringUtils.isEmpty(remark)?"":remark)+this.toString()), userId,actualPerformTime);
			
			log.info("EBK 通关，"+msg.getMsg());
			
			if(StringUtils.isNotEmpty(msg.getMsg())){
				returnFlag=false;
				createWorkOrder();
			}else{
				//TODO:	发JMS消息，告诉可以履行当前订单子子项的其它设备，当前码已经被使用
				String ids = getDeviceIdsByLvmama(addCode.toString(),udid);
				if(StringUtils.isNotEmpty(ids)){
					//码号|device1,device2
					String addtion =addCode+"|"+ids;
					//由于EBK_PUSH并不关心object_id,所以传空
					passportMessageProducer.sendMsg(MessageFactory.newPassportUsedMessage(0L, addtion));
				}
			}
		} catch (Exception e) {
			hasException = true;
			log.info("port/passInfo():passport failed with addtion code ["+addCode+"]!"+e.getMessage());
			json.put("code", EBKConstant.MSG_LEVEL.SUCCESS.getValue());
			json.put("message", "订单通关失败!");
			
		}
		json.put("message", returnFlag==true?"订单通关成功!":msg!=null?msg.getMsg():"订单通关失败!");
		if(!hasException){
			json.put("code", EBKConstant.MSG_LEVEL.SUCCESS.getValue());
		}
		
		json.put("syncTime", DateUtil.getFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		
		log.info("EBK 通关，"+json.toString());

		sendAjaxResult(uuid,json);
	}
	
	public void sendAjaxResult(String uuid,JSONObject json) {
		super.sendAjaxResultByJson(json.toString());
		
		if(json.getString("code").equals(EBKConstant.MSG_LEVEL.SUCCESS.getValue())){
			saveRequestParams(uuid,EBK_PERFORM_LOG_STATUS.PERFORMED,json.toString());
		}else{
			saveRequestParams(uuid,EBK_PERFORM_LOG_STATUS.ERROR,json.toString());
		}
		
	}
	
	private void saveRequestParams(final String uuid,final EBK_PERFORM_LOG_STATUS status,final String memo) {
		if(StringUtils.isEmpty(addCode))
			return;
		
		new Thread(){
			@Override
			public void run() {
				EbkPerformLog ebkPerformLog = new EbkPerformLog();
				ebkPerformLog.setAddCode(addCode);
				ebkPerformLog.setCreateTime(new Date());
				ebkPerformLog.setEbkUserId(userId);
				ebkPerformLog.setOrderItemMetaId(Arrays.toString(orderItemMetaId));
				ebkPerformLog.setUdid(udid);
				ebkPerformLog.setPerformTime( StringUtils.isNotEmpty(performTime)?DateUtil.stringToDate(performTime, "yyyy-MM-dd HH:mm:ss"):null);
				ebkPerformLog.setQuantity(Arrays.toString(quantity));
				ebkPerformLog.setStatus(status.name());
				ebkPerformLog.setUuid(uuid);
				ebkPerformLog.setMemo(memo);
				ebkPerformLogService.insert(ebkPerformLog);
			}
		}.start();
	}
	
	private void createWorkOrder(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("typeCode",Constant.WORK_ORDER_TYPE_AND_SENDGROUP.SJEBKFWDLXSB.getWorkOrderTypeCode());
		List<WorkOrderType> workTypes = workOrderTypeService.queryWorkOrderTypeByParam(map);
		if(workTypes!=null&&workTypes.size()==1){
			Map<String,Object> ordParams = new HashMap<String,Object>();
			ordParams.put("udid", udid);
			ordParams.put("addCode", addCode);
			List<OrdOrderPerformResourceVO> ordOrderPerformResourceVO = orderPerformService.queryOrderPerformByEBK(ordParams);
			Long orderId = ordOrderPerformResourceVO.get(0).getOrderId();
			
			WorkOrderType workType = workTypes.get(0);
			
			WorkOrderCreateParam workOrder = new WorkOrderCreateParam();
			workOrder.setLimitTime(workType.getLimitTime());
			workOrder.setOrderId(orderId);
			workOrder.setProductId(null);
			workOrder.setUrl(null);
			workOrder.setVisitorUserName(null);
			workOrder.setWorkOrderContent("订单号："+ orderId);
			workOrder.setWorkTaskContent("订单号：" + orderId);
			workOrder.setWorkOrderTypeCode(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.SJEBKFWDLXSB.getWorkOrderTypeCode());
			publicWorkOrderService.createWorkOrder(workOrder);
			
		}
	}
	
	private boolean signError() {
		String signKey = getSignKey();
		try {
			String encodedStr = MD5.encode(signKey);
			return !encodedStr.equals(signName);
		} catch (NoSuchAlgorithmException e) {
			log.error("MD5 encode ERROR: "+e.getMessage());
			return false;
		}
	}

	private String getDeviceIdsByLvmama(String addcode,String excludeDevices){
		List<PassDevice> deviceList = passCodeService.getDeviceListByCode(addcode,excludeDevices);
		StringBuilder sb = new StringBuilder();
		if(deviceList!=null && deviceList.size()>0){
			for(PassDevice device : deviceList){
				sb.append(device.getDeviceNo());
				sb.append(",");
			}
			
			String ids = sb.toString();
			ids = ids.substring(0, ids.length()-1);
			return ids;			
		}
		return null;
	}
	public void setItem(String item) {
		if(StringUtils.isNotEmpty(item)){
			String[] items = item.split(",");
			quantity = new Long[items.length];
			orderItemMetaId = new Long[items.length];
			
			for(int i=0;i< items.length;i++){
				Object obj = items[i];
				if(obj!=null){
					String keyValue = obj.toString();
					String[] strLength = keyValue.split("_");
					if(strLength.length!=2){
						this.quantity = null;
						this.orderItemMetaId = null;
						return;
					}else{
						try{
							quantity[i] = Long.valueOf(strLength[1]);
							orderItemMetaId[i] = Long.valueOf(strLength[0]);
						}catch(Exception e){
							this.quantity = null;
							this.orderItemMetaId = null;
							return;
						}
					}
				}
			}
		}
	}

	public void setOrderPerformProxy(OrderPerformService orderPerformProxy) {
		this.orderPerformProxy = orderPerformProxy;
	}

	public void setEbkUserService(EbkUserService ebkUserService) {
		this.ebkUserService = ebkUserService;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setQuantity(Long[] quantity) {
		this.quantity = quantity;
	}

	public void setOrderItemMetaId(Long[] orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}

	public void setPassportMessageProducer(TopicMessageProducer passportMessageProducer) {
		this.passportMessageProducer = passportMessageProducer;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public void setPerformTime(String performTime) {
		this.performTime = performTime;
	}

	public String getSignKey(){
		return DistributionParseUtil.getPropertiesByKey("ebk.key")+userId+udid+addCode;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public PublicWorkOrderService getPublicWorkOrderService() {
		return publicWorkOrderService;
	}

	public void setPublicWorkOrderService(PublicWorkOrderService publicWorkOrderService) {
		this.publicWorkOrderService = publicWorkOrderService;
	}

	public void setWorkOrderTypeService(WorkOrderTypeService workOrderTypeService) {
		this.workOrderTypeService = workOrderTypeService;
	}

	public void setOrderPerformService(OrderPerformService orderPerformService) {
		this.orderPerformService = orderPerformService;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(" userId=");
		builder.append(userId);
		builder.append(", udid=");
		builder.append(udid);
		builder.append(", addCode=");
		builder.append(addCode);
		builder.append(", quantity=");
		builder.append(Arrays.toString(quantity));
		builder.append(", orderItemMetaId=");
		builder.append(Arrays.toString(orderItemMetaId) );
		builder.append(", performTime=");
		builder.append(performTime);
		return builder.toString();
	}

	public EbkPerformLogService getEbkPerformLogService() {
		return ebkPerformLogService;
	}

	public void setEbkPerformLogService(EbkPerformLogService ebkPerformLogService) {
		this.ebkPerformLogService = ebkPerformLogService;
	}
	
}
