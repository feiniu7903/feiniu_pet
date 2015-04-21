package com.lvmama.clutter.web.place;

import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.json.JSONArray;

import com.lvmama.clutter.utils.DistributionParseUtil;
import com.lvmama.clutter.utils.EBKConstant;
import com.lvmama.clutter.utils.EbkUserUtils;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.service.eplace.EbkUserService;
import com.lvmama.comm.bee.service.ord.OrderPerformService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.ord.OrdOrderPerformResourceVO;
import com.lvmama.comm.pet.po.ebkpush.ModelUtils;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.vo.Constant;

/**
 * E 景通获取订单表单，返回数据结构如下<br/>
 * 
 * <pre>
 * {
 *   code:...,
 *   message:...,
 *   datas:[
 *     {
 *        baseInfo:{},
 *        metas:[],
 *        persons:[]
 *     },
 *     ...
 *   ]
 * }
 * </pre>
 * 
 * URL
 * http://localhost/clutter/supplier/orders.do?userId=3541&udid=zhangkexing&addCode=62239128
 * 
 * @since 2013-03-04
 * @author 张克行
 */
public final class EbkOrdersAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5330843567682238750L;

	private OrderPerformService orderPerformProxy;
	private EbkUserService ebkUserService = null;
	private PassCodeService passCodeService;
	
	//E景通用户ID
	private Long userId;
	//辅助码
	private String addCode;
	//刷码设备唯一标识
	private String udid;
	//客户端的签名
	private String signName;
	private List<String> includeForBuildJson = new ArrayList<String>();

	public EbkOrdersAction() {
		super();
		includeForBuildJson.add("getClientVisitTime");
		includeForBuildJson.add("getContactName");
		includeForBuildJson.add("getMobileNumber");
		includeForBuildJson.add("getOrderId");
		includeForBuildJson.add("getOrderStatus");
		includeForBuildJson.add("getOrderViewStatus");
		includeForBuildJson.add("getZhOrderViewStatus");
		includeForBuildJson.add("getPayTo");
		
		
		includeForBuildJson.add("getAddCodeStatus");
		includeForBuildJson.add("getZhAddCodeStatus");
		includeForBuildJson.add("getValidTime");
		includeForBuildJson.add("getInvalidTime");
		includeForBuildJson.add("getAddCode");
		includeForBuildJson.add("getInvalidDate");
		includeForBuildJson.add("getInvalidDateMemo");

		includeForBuildJson.add("getOrderItemMetaId");
		includeForBuildJson.add("getProductName");
		includeForBuildJson.add("getTotalAdultQuantity");
		includeForBuildJson.add("getTotalChildQuantity");
		includeForBuildJson.add("getTotalQuantity");
		includeForBuildJson.add("getSellPrice");
		includeForBuildJson.add("getQuantity");		

		includeForBuildJson.add("getPersonId");
		includeForBuildJson.add("getPersonType");
		includeForBuildJson.add("getName");
		includeForBuildJson.add("getMobile");
		includeForBuildJson.add("getTel");
		includeForBuildJson.add("getCertNo");
		includeForBuildJson.add("getZhCertType");
		includeForBuildJson.add("getCertType");
	}

	@Action("/supplier/orders")
	public void getOrders() {
		if(signError()){
			sendAjaxResult(failureMsg(EBKConstant.MSG_LEVEL.SIGN_ERROR.getValue(),"无效的访问!"));
			return;
		}
		
		if (userId==null || StringUtils.isEmpty(udid) || addCode==null ) {
			sendAjaxResult(failureMsg(EBKConstant.MSG_LEVEL.PARAM_ERROR.getValue(),"参数输入不正确!"));
			return;
		}
		
		EbkUser ebkUser = EbkUserUtils.availableUser(ebkUserService, userId);
		//当前E景通用户是否绑定到当前设备？
		if (null!= ebkUser && EbkUserUtils.hasBeenBindingToDevice(ebkUserService, ebkUser, udid)) {

			
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("udid", udid);
			param.put("addCode", addCode);
			
			//二维码不存在！
			boolean addCodeExisting = addCodeExisting();
			if(!addCodeExisting){
				sendAjaxResult(failureMsg(EBKConstant.MSG_LEVEL.LOGIC_ERROR.getValue(),"二维码不存在！请检查输入是否正确"));
				return;
			}
			//二维码不能在此景区使用
			boolean addCodeInTarget = addCodeIsInTarget();
			if(!addCodeInTarget){
				sendAjaxResult(failureMsg(EBKConstant.MSG_LEVEL.LOGIC_ERROR.getValue(),"二维码不能在此景区使用！"));
				return;
			}
			

			List<OrdOrderPerformResourceVO> performResources = orderPerformProxy.queryOrderPerformByEBK(param);
			
			if(addCode!=null && StringUtils.isNotEmpty(addCode) && performResources!=null && performResources.size()==1){
				OrdOrderPerformResourceVO vo = performResources.get(0);

				//该订单已经取消
				if(vo.getOrderStatus().equals( Constant.ORDER_STATUS.CANCEL)){
					sendAjaxResult(failureMsg(EBKConstant.MSG_LEVEL.LOGIC_ERROR.getValue(),"该订单已经取消"));
				}
				//该订单已经履行
				else if(vo.getOrderStatus().equals( Constant.ORDER_STATUS.FINISHED)){
					sendAjaxResult(failureMsg(EBKConstant.MSG_LEVEL.LOGIC_ERROR.getValue(),"该订单已经履行"));
				}
				//该订单已经过游玩时间
				else if(DateUtil.stringToDate(vo.getInvalidTime(), "yyyy-MM-dd HH:mm:ss").compareTo(new Date()) == -1){
					sendAjaxResult(failureMsg(EBKConstant.MSG_LEVEL.LOGIC_ERROR.getValue(),"该订单已经过游玩时间",vo.getInvalidTime()));
				} else if(!vo.validateInvalidDate()){
					sendAjaxResult(failureMsg(EBKConstant.MSG_LEVEL.LOGIC_ERROR.getValue(),"该订单今日不可游玩"));
				}else{
					sendAjaxResult(buildingResultAsJson(performResources));
				}
			}else{
				if(performResources == null || performResources.size()==0){
					sendAjaxResult(failureMsg(EBKConstant.MSG_LEVEL.LOGIC_ERROR.getValue(),null));
				}else{
					sendAjaxResult(buildingResultAsJson(performResources));
				}
			}
			
		} else {
			sendAjaxResult(failureMsg(EBKConstant.MSG_LEVEL.LOGIC_ERROR.getValue(),null));
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

	private boolean addCodeIsInTarget() {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("udid", udid);
		param.put("addCode", addCode);
		param.put("userId", userId);
		Object o = passCodeService.addCodeIsInTarget(param);
		return o!=null;
	}

	private boolean addCodeExisting() {
		return passCodeService.addCodeExisting(addCode);
	}

	/**
	 * 将订单列表转换为JSON
	 * 
	 * @param orderList
	 * @return
	 */
	private String buildingResultAsJson(List<OrdOrderPerformResourceVO> performResources) {
		JSONObject jobj = new JSONObject();
		jobj.put("code", 0);
		jobj.put("message", "查询成功");
		List<Object> objs = ModelUtils.buildSendDatas(performResources);
		jobj.put("datas", objs);
		return jobj.toString();
	}

	/**
	 * 将POJO转换为JSON
	 * 
	 * @param jsonObject
	 * @param instance
	 * @return
	 */
	private JSONObject buildObjectAsJson(JSONObject jsonObject, Object instance) {
		Method[] ms = instance.getClass().getMethods();
		for (int i = 0; i < ms.length; i++) {
			Method method = ms[i];
			String methodName = method.getName();

			if (methodName.startsWith("get") && includeForBuildJson.contains(methodName)) {

				Class<?> returnType = method.getReturnType();
				Package pkg = returnType.getPackage();
				String packageName = pkg != null ? pkg.getName() : "";

				if (packageName.equals("java.lang") || returnType.isPrimitive()) {
					String key = methodName.substring(3);
					try {
						Object returnValue = method.invoke(instance);
						// 注：如果returnValue==null,应把其转换为空字符串，否则JSON
						// toString时，会过滤掉null值的列。
						jsonObject.put(key, returnValue == null ? "" : returnValue);
					} catch (Exception e) {
						jsonObject.put(key, null);
					}
				}
			}
		}
		return jsonObject;
	}

	private String failureMsg(String msgLevel, String msg, String... addtion) {

		JSONObject jobj = new JSONObject();
		JSONObject data = new JSONObject();

		jobj.put("code", msgLevel);
		jobj.put("message", StringUtils.isEmpty(msg) ? "查询失败" : msg);
		jobj.put("data", data);
		if(addtion.length>0){
			jobj.put("addtion", addtion[0]);
		}

		return jobj.toString();
	}

	public void setOrderPerformProxy(OrderPerformService orderPerformProxy) {
		this.orderPerformProxy = orderPerformProxy;
	}

	public EbkUserService getEbkUserService() {
		return ebkUserService;
	}

	public void setEbkUserService(EbkUserService ebkUserService) {
		this.ebkUserService = ebkUserService;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public String getSignKey(){
		return DistributionParseUtil.getPropertiesByKey("ebk.key")+userId+udid+addCode;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

}