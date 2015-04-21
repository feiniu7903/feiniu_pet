package com.lvmama.comm.vo;

import java.util.ResourceBundle;

public class PassportConstant {

	private static ResourceBundle resourceBundle;
	private static PassportConstant instance;

	public final static String defaultWidth = "950px";

	public final static Long defaultPaymentWait = 60l;

	public final static String SESSION_USER = "SESSION_USER";
	public final static String SESSION_EPLACE_SUPPLIER = "SESSION_EPLACE_SUPPLIER";

	public final static String MEMU_EPLACE = "EPLACE";// 平台用户管理
	public final static String MEMU_EPLACE_LVMAMMA = "LVMAMA";// 平台用户管理-二级菜单驴妈妈控制端.
	public final static String MEMU_EPLACE_SUPPLIER = "SUPPLIER";// 平台用户管理-二级菜单景区控制端.
	public final static String MEMU_SYSTEM = "SYSTEM";// 系统管理
	public final static String MEMU_PASSPORT = "PASSPORT";// 通关用户管理

	public static enum PASSCODE_STATUS {
		/** 临时失败 */
		TEMP_FAILED,
		/** 失败状态 */
		FAILED,
		/** 成功状态 */
		SUCCESS;
	}

	public static enum PASSCODE_HANDL_STATUS {
		/**
		 * 未处理的请求状态
		 */
		NOHANDL,
		/**
		 * 已经处理的请求状态
		 */
		HANDL;
	}

	public static enum PASSCODE_TYPE {
		/**
		 * 申请码
		 */
		APPLAYCODE,
		/**
		 * 废码
		 */
		DESTROYCODE,
		/**
		 * 更新内容
		 */
		UPDATECONTENT,
		/**
		 * 回收码
		 */
		USEEDCODE,
		/**
		 * 修改人数
		 */
		UPDATEPERSON,
		/**
		 * 重申码
		 */
		REAPPLAY,
		/**
		 * 更新订单联系人
		 */
		UPDATECONTACT,
		/**
		 * 重发短信
		 */
		RESEND,
		/**
		 * 其它类型
		 */
		OTHER;
	}

	public static enum PASSCODE_APPLY_STATUS {
		/** 已经申请码状态 */
		APPLIED,
		/** 申请码成功状态 */
		SUCCESS,
		/** 申请失败 */
		FAILED;
	}
	
	public static enum PASSCODE_DESTROY_STATUS {
		/**废码失败*/
		UNDESTROYED,
		/** 废码成功*/
		DESTROYED;
	}

	public static enum PASSCODE_ERROR {
		/** 申请码 */
		APPLY,
		/** 废码失败 */
		DESTROY,
		/** 重发短信失败*/
		RESEND,
		/** 申请失败 */
		USED;
	}

	public static enum PASSCODE_USE_STATUS {
		/** 未被使用 */
		UNUSED,
		/** 已使用 */
		USED,
		/** 部分使用 */
		PART_USED,
		/** 被废弃 */
		DESTROYED;
	}
	public static enum PASSCODE_DEVICE_TYPE {
		/** 新设备 */
		NEW_DEVICE,
		/** 老设备 */
		OLD_DEVICE
	}
	public static enum PASS_PROVIDER_TYPE {
		/**恐龙园 */
		DINOSAURTOWN,
		/**长隆*/
		CHIMELONG,
		/**永利国旅*/
		YONGLIGUOLV,
		/**华强方特*/
		FANGTE,
		/**瘦西湖*/
		SHOUXIHU,
		/**e游*/
		YIYOU,
		/**灵山大佛*/
		LINGSHAN,
		;
	}
	
	public static enum PASSCODE_SMS_SENDER {
		/**由驴妈妈发送短信*/
		LVMAMA,
		/**由合作伙伴发送短信*/
		PARTNER
	}
	
	public void init() {
		resourceBundle = ResourceBundle.getBundle("const");
	}

	public synchronized static PassportConstant getInstance() {
		if (instance == null) {
			instance = new PassportConstant();
			instance.init();
		}
		return instance;
	}

	public String getBackendUrl() {
		return resourceBundle.getString("backendUrl");
	}

	public Long getGmediaId() {
		return Long.parseLong(resourceBundle.getString("gmedia_provider"));
	}

	public Long getDinosaurtownId() {
		return Long.parseLong(resourceBundle.getString("dinosaurtown"));
	}

	public Long getNewlandId() {
		return Long.parseLong(resourceBundle.getString("newland_provider"));
	}

	public Long getYanchengId() {
		return Long.parseLong(resourceBundle.getString("yancheng_provider"));
	}

	public static String getTempDir() {
		return System.getProperty("java.io.tmpdir");
	}
}
