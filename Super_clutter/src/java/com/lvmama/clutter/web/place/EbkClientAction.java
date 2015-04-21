package com.lvmama.clutter.web.place;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.clutter.utils.EBKConstant;
import com.lvmama.clutter.utils.EbkUserUtils;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.bee.po.pass.PassProvider;
import com.lvmama.comm.bee.service.eplace.EbkUserService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sup.SupplierService;


/**
 * app access point: http://localhost/clutter/supplier/login.do?userName=zhangkexing&password=111111&udid=33221
 * @author zhangkexing
 *
 */
public class EbkClientAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 日志输出
	 */
	public final Log log = LogFactory.getLog(getClass());

	private EbkUserService ebkUserService = null;
	private SupplierService supplierService;
	private PassCodeService passCodeService;
	
	private String userName;
	private String password;
	//手机唯一标识
	private String udid;
	
	
	@Action("/supplier/login")
	public void login() {
		try {
			EbkUser ebkUser = EbkUserUtils.availableUser(ebkUserService, userName, password);
			List<PassProvider> providerList = getPassProvider(udid);
			if (null != ebkUser	
					&& EbkUserUtils.hasBeenBindingToDevice(ebkUserService, ebkUser, udid) 
					&& providerList !=null
					&& providerList.size()==1) {
				log.info("E景通 供应商登录成功 username:".concat(userName));
				SupSupplier supplier = supplierService.getSupplier(ebkUser.getSupplierId());
				sendAjaxResult(successMsg(ebkUser, supplier.getSupplierName(),providerList.get(0)));
			} else {
				log.info("E景通 供应商登录失败  username:".concat(userName));
				sendAjaxResult(failureMsg(EBKConstant.MSG_LEVEL.LOGIC_ERROR.getValue(),null));
			}
		} catch (Exception ex) {
			sendAjaxResult(failureMsg(EBKConstant.MSG_LEVEL.EXCEPTION.getValue(),ex.getMessage()));
		}
	}

	private List<PassProvider> getPassProvider( String udid ){
		return passCodeService.selectByDeviceNo(udid);
	} 

	private String successMsg(EbkUser user, String Supplier,PassProvider passProvider) {
		JSONObject jobj = new JSONObject();
		JSONObject data = new JSONObject();

		data.accumulate("userId", user.getUserId())
				.accumulate("userName", user.getUserName())
				.accumulate("supplyName", Supplier)
				.accumulate("canPrint", user.getCanPrint())
				.accumulate("providerId", passProvider.getProviderId())
				.accumulate("providerName", passProvider.getName());

		jobj.put("code", 0);
		jobj.put("message", "登录成功");
		jobj.put("data", data);

		return jobj.toString();
	}

	/**
	 * 返回JSON失败信息
	 * @param msgLevel 1：逻辑异常， -1：程序异常
	 * @param msg
	 * @return
	 */
	private String failureMsg(String msgLevel, String msg) {

		JSONObject jobj = new JSONObject();
		JSONObject data = new JSONObject();

		data.accumulate("userId", "")
				.accumulate("userName", "")
				.accumulate("supplyName", "");

		jobj.put("code", msgLevel);
		jobj.put("message", StringUtils.isEmpty(msg) ? "登录失败! 请确认用户名/密码是否正确或此用户已经绑定该设备." : msg);
		jobj.put("data", data);

		return jobj.toString();
	}

	
	
	
	public EbkUserService getEbkUserService() {
		return ebkUserService;
	}

	public void setEbkUserService(EbkUserService ebkUserService) {
		this.ebkUserService = ebkUserService;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public String getUdid() {
		return udid;
	}



	public void setUdid(String udid) {
		this.udid = udid;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}


}
