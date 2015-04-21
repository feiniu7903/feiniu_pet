package com.lvmama.clutter.web.order;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.clutter.exception.ActivityLogicException;
import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.JianYiOrderDTO;
import com.lvmama.clutter.model.JianYiProduct;
import com.lvmama.clutter.model.MobileBranch;
import com.lvmama.clutter.model.MobileBranchItem;
import com.lvmama.clutter.model.MobileCouponPoint;
import com.lvmama.clutter.model.MobileOrder;
import com.lvmama.clutter.model.MobileOrderItem;
import com.lvmama.clutter.model.MobilePayment;
import com.lvmama.clutter.model.MobilePersonItem;
import com.lvmama.clutter.model.MobileProductRoute;
import com.lvmama.clutter.model.MobileReceiver;
import com.lvmama.clutter.model.MobileTimePrice;
import com.lvmama.clutter.model.MobileUser;
import com.lvmama.clutter.model.MobileUserCoupon;
import com.lvmama.clutter.model.OrderDataItem;
import com.lvmama.clutter.service.IClientHotelService;
import com.lvmama.clutter.service.IClientOrderService;
import com.lvmama.clutter.service.IClientOtherService;
import com.lvmama.clutter.service.IClientProductService;
import com.lvmama.clutter.service.IClientUserService;
import com.lvmama.clutter.utils.BaiduActivityUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.DateUtil;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.po.work.WorkGroup;
import com.lvmama.comm.pet.po.work.WorkGroupUser;
import com.lvmama.comm.pet.po.work.WorkOrder;
import com.lvmama.comm.pet.po.work.WorkOrderType;
import com.lvmama.comm.pet.po.work.WorkTask;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.work.WorkGroupService;
import com.lvmama.comm.pet.service.work.WorkOrderService;
import com.lvmama.comm.pet.service.work.WorkOrderTypeService;
import com.lvmama.comm.pet.service.work.WorkTaskService;
import com.lvmama.comm.utils.ClientConstants;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ProdCProduct;

@ParentPackage("clutterCreateOrderInterceptorPackage")
@ResultPath("/clutterCreateOrderInterceptor")
@Results({
		@Result(name = "my_order", location = "/WEB-INF/pages/order/order_myorder.html", type = "freemarker"),
		@Result(name = "my_jianyi_order", location = "/WEB-INF/pages/order/order_jianyiorder.html", type = "freemarker"),
		@Result(name = "my_jianyi_order_detail", location = "/WEB-INF/pages/order/order_jianyidetail.html", type = "freemarker"),
		@Result(name = "order_list_ajax", location = "/WEB-INF/pages/order/order_list_ajax.html", type = "freemarker"),
		@Result(name = "order_fill", location = "/WEB-INF/pages/order/order_fill.html", type = "freemarker"),
		@Result(name = "order_fill_freeness", location = "/WEB-INF/pages/order/order_fill_freeness.html", type = "freemarker"),
		@Result(name = "order_fill_easy", location = "/WEB-INF/pages/order/order_fill_easy.html", type = "freemarker"),
		@Result(name = "order_succ_v3", location = "/WEB-INF/pages/order/order_succ_v3.html", type = "freemarker"),
		@Result(name = "order_success", location = "/WEB-INF/pages/order/order_success.html", type = "freemarker"),
		@Result(name = "order_detail", location = "/WEB-INF/pages/order/order_detail.html", type = "freemarker"),
		@Result(name = "order_pay_callback", location = "/WEB-INF/pages/order/order_pay_success.html", type = "freemarker"),
		@Result(name = "order_pay_callback_ajax", location = "/WEB-INF/pages/order/order_pay_success_ajax.html", type = "freemarker"),
		@Result(name = "order_fill_traveler", location = "/WEB-INF/pages/order/order_fill_traveler_V5.html", type = "freemarker"),
		@Result(name = "hotel_guarantee_nocards", location = "/WEB-INF/pages/hotel/hotel_guarantee_nocards.html", type = "freemarker"),
		@Result(name = "hotel_guarantee_yescards", location = "/WEB-INF/pages/hotel/hotel_guarantee_yescards.html", type = "freemarker"),
		@Result(name = "hotel_order_fill", location = "/WEB-INF/pages/hotel/hotel_order_fill.html", type = "freemarker"),
		@Result(name = "hotel_order_success", location = "/WEB-INF/pages/hotel/hotel_order_success.html", type = "freemarker"),
		@Result(name = "hotel_order_detail", location = "/WEB-INF/pages/hotel/hotel_order_detail.html", type = "freemarker"),
		@Result(name = "hotel_order_list", location = "/WEB-INF/pages/hotel/hotel_order_list.html", type = "freemarker"),
		@Result(name = "hotel_order_list_ajax", location = "/WEB-INF/pages/hotel/hotel_order_list_ajax.html", type = "freemarker"),
		@Result(name = "mylv", location = "/WEB-INF/pages/user/mylv.html", type = "freemarker"),
		@Result(name = "error", location = "/WEB-INF/error.html", type = "freemarker") })
@Namespace("/mobile/order")
public class OrderAction extends BaseAction {
	private static final long serialVersionUID = -6164959037367208915L;

	/**
	 * 产品服务
	 */
	IClientProductService mobileProductService;

	/**
	 * 产品服务
	 */
	protected ProdProductService prodProductService;

	/**
	 * 订单服务
	 */
	protected IClientOrderService mobileOrderService;

	/**
	 * User服务
	 */
	protected IClientUserService mobileUserService;

	/**
	 * 工单服务
	 */
	protected WorkOrderService workOrderService;

	protected WorkTaskService workTaskService;

	protected WorkGroupService workGroupService;

	/**
	 * 工单类型服务
	 */
	protected WorkOrderTypeService workOrderTypeService;

	protected ProductHeadQueryService productServiceProxy;

	protected IReceiverUserService receiverUserService;

	protected IClientOtherService mobileOtherService;
	
	/**
	 * 子产品服务
	 */
	private ProdProductBranchService prodProductBranchService;

	/**
	 * 
	 */
	protected PageService pageService;

	protected List<MobileBranchItem> orderItemList; // 购买的产品列表
	protected List<OrderDataItem> orderDataList;// 简易预订，用户购买产品数量价格列表
	protected Long workOrderId;// 工单编号
	protected String couponCode = ""; // 优惠券号码
	protected boolean needIdCard;// 是否需要身份证号
	protected String idCard; // 身份证
	protected String mobile;// 手机号
	protected String userName;// 取票人姓名
	protected String orderId;// 订单id
	protected String personType;// 人员类型 ORD_PERSON_TYPE CONTACT("联系人")
								// BOOKER("预订人"),RECIPIENT("发票收件人")
								// CONTACT("联系人")EMERGENCY_CONTACT("紧急联系人")
	protected String visitTime; // 游玩时间
	protected boolean canOrderToday;// 是否今日定.
	protected int page = 1;// 页数.

	protected String productId;// 产品id
	protected String branchId; // 类别id
	protected String productName;// 产品名称
	protected String subProductType;// 产品子类型
	protected String firstChannel = Constant.CHANNEL.TOUCH.name(); // 一级渠道
	protected String secondChannel = "LVMM"; // 二级渠道

	protected String msg;// 提示信息
	protected boolean ajax;// 是否异步查询

	protected String payStatus;// 支付状态
	protected Long location = 1L;// 地址URL（订单返回用）

	protected MobileCouponPoint mobileCouponPoint;// 积分兑换优惠券实体.

	/**
	 * 目的地自由行
	 */
	protected List<UsrReceivers> receiversList; // 用户联系人列表
	protected List<String> firstTravellerInfoOptions; // 第一游玩人的必填信息
	protected List<String> travellerInfoOptions; // 游玩人的必填信息
	protected List<String> contactInfoOptions; // 联系人的必填信息
	protected Long baoxianSelect = 0L; // 保险 数量

	protected String productType;// 产品类别
	protected Long totalQuantity = 0l; // 成人 和 儿童的数量(双人行是2)
	protected String agreement; // 驴行协议
	protected String totalPrice = "0";// 总价格
	protected int baoxianAmount = 0;// 购买保险的数量
	protected String personListItems = " "; // 线路人员列表

	protected MobilePersonItem mpi; // 实体票收件人

	protected String physical;// 是否实体票 true是

	protected String userNo;// 用户NO匿名下单用

	protected String econtractEmail;//出团通知书邮箱

	/**
	 * 酒店接口服务
	 */
	protected IClientHotelService clientHotelService;
	
	@Autowired
	protected UserUserProxy userUserProxy;

	private String arrivalDate;// 酒店入住日期

	private String departureDate;// 酒店离开日期

	private String hotelId;// 房间ID

	private String roomTypeId;// 房间类型ID

	private String ratePlanId;// 收费ID

	private String numberOfRooms;// 房间数

	private String hotelOrderSign;// 酒店取消参数Sign

	private String hotelOrderTime;// 酒店取消参数tmme

	private String lvsessionid;//

	private String cancelHotelOrderType;// 酒店取消订单位置/1详情/0列表

	private String pageIndex;// 分页参数

	private String pageSize;// 分页参数

	private String hotelName;// 酒店名称
	
	private String hotelTypeName;// 酒店类型名称
	
	private String guaranteePrice;//担保金额
	
	/** 入住客人数 **/
	private String numberOfCustomers;
	/** 客户端ip **/
	private String customerIPAddress;
	/** 房间保留至选项 如：20:00 **/
	private String option;

	/** 联系人姓名 **/
	private String contactName;
	/** 联系人手机号码 **/
	private String contactMobile;
	/** 入住人姓名，多个半角逗号","隔开 **/
	private String customerNames;
	/** 用户国籍 **/
	private String customerType;

	/**
	 * 我的订单.
	 * 
	 * @return
	 */
	@Action("myorder")
	public String myorder() {
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			param.put("page", page);
			param.put("userNo", getUser().getUserNo());
			Map<String, Object> resultMap = mobileUserService
					.getOrderList(param);
			if (null != resultMap) {
				getRequest().setAttribute("orderList", resultMap.get("datas"));
				getRequest().setAttribute("isLastPage",
						resultMap.get("isLastPage"));
			}
			getRequest().setAttribute("prefixPic",
					Constant.getInstance().getPrefixPic());

			if (ajax) {
				return "order_list_ajax";
			}

		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
			return "error";
		}
		if (ajax) {
			return "order_list_ajax";
		}
		return "my_order";
	}

	/**
	 * 我的简易预订单
	 * 
	 * @return
	 */
	@Action("my_jianyi_order")
	public String myJianYiOrder() {
		String userNo = getUser().getUserNo();
		// 个人简易预订订单列表
		List<JianYiOrderDTO> jianYiOrderList = new ArrayList<JianYiOrderDTO>();

		// 根据USERNO查询工单对应的工单列表
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("createUserName", "WAP" + userNo);

		List<WorkOrder> workOrderList = workOrderService
				.queryWorkOrderByParam(params);
		// 对查出的工单列表进行数据转换，转换成个人中心预订单显示
		if (workOrderList != null && workOrderList.size() > 0) {
			for (WorkOrder w : workOrderList) {
				jianYiOrderList.add(this.dataTransf(w));
			}
		}
		getRequest().setAttribute("jianYiOrderList", jianYiOrderList);
		return "my_jianyi_order";
	}

	/**
	 * 简易预订单详情页
	 * 
	 * @return
	 */
	@Action("my_jianyi_order_detail")
	public String myJianYiOrderDetail() {
		JianYiOrderDTO jianYiOrderDTO = new JianYiOrderDTO();
		WorkOrder workOrder = workOrderService.getWorkOrderById(workOrderId);

		if (workOrder != null) {
			jianYiOrderDTO = this.dataTransf(workOrder);
		}

		getRequest().setAttribute("jianYiOrder", jianYiOrderDTO);
		return "my_jianyi_order_detail";
	}

	/**
	 * 简易预订数据转换--我的意向单用
	 * 
	 * @return
	 */
	public JianYiOrderDTO dataTransf(WorkOrder workOrder) {

		// String str =
		// "联系人：wq,手机号码：18616023394,产品名称：【独家自由行】无锡灵山元一温泉，住无锡凯莱大饭店高级间2人1晚【独家赠送欢迎水果一份】,产品列表：双床套餐<101139>￥595x1,大床套餐<101140>￥556x2,游玩日期：2013-11-27";
		String str = workOrder.getContent();
		String[] datas = str.split(",");
		JianYiOrderDTO jianYiOrderDTO = new JianYiOrderDTO();

		String userName = datas[0].split("：")[1];// 联系人姓名
		String userMobile = datas[1].split("：")[1];// 联系人手机
		String visitTime = datas[datas.length - 1].split("：")[1];// 游玩时间
		String productName = "";// 产品名称
		String branchList = "";// 产品列表
		String productId = "";// 产品ID

		// 产品名称
		Pattern p = Pattern.compile("产品名称：(.*),产品ID：");
		Matcher m = p.matcher(str);
		// 产品ID
		Pattern p2 = Pattern.compile("产品ID：(.*),产品列表：");
		Matcher m2 = p2.matcher(str);
		// 产品列表（短名，价格，数量）
		Pattern p1 = Pattern.compile("产品列表：(.*),游玩日期：");
		Matcher m1 = p1.matcher(str);

		boolean bool = m.find();
		boolean bool1 = m1.find();
		boolean bool2 = m2.find();
		while (bool) {
			productName = m.group(1);
			break;
		}
		while (bool1) {
			branchList = m1.group(1);
			break;
		}
		while (bool2) {
			productId = m2.group(1);
			break;
		}
		// 产品列表
		String[] branchs = branchList.split(",");
		List<JianYiProduct> jianYiProductList = new ArrayList<JianYiProduct>();
		for (String brach : branchs) {
			String[] brachOne = brach.split("<");
			String[] brachTwo = brach.split(">");
			JianYiProduct jianYiProduct = new JianYiProduct();
			String shortName = brachOne[0];
			String priceAndNum = brachTwo[1];

			jianYiProduct.setShortName(shortName);
			jianYiProduct.setPriceAndNum(priceAndNum);

			jianYiProductList.add(jianYiProduct);
		}
		jianYiOrderDTO.setUserName(userName);
		jianYiOrderDTO.setUserMobile(userMobile);
		jianYiOrderDTO.setVisitTime(visitTime);
		jianYiOrderDTO.setProductName(productName);
		jianYiOrderDTO.setJianYiProductList(jianYiProductList);
		jianYiOrderDTO.setCreateTime(this.getDate(workOrder.getCreateTime()));
		jianYiOrderDTO.setWorkOrderId(workOrder.getWorkOrderId());
		jianYiOrderDTO.setOrderJYStatus(Constant.WORK_ORDER_STATUS.UNCOMPLETED
				.getCnName(workOrder.getStatus()));
		jianYiOrderDTO.setProductId(productId);
		jianYiOrderDTO.setOrderJYImg(this.getProductSmallImage(productId));

		return jianYiOrderDTO;
	}

	/**
	 * 查询自由行产品详情(这里只用小图片)
	 * 
	 * @return
	 */
	public String getProductSmallImage(String productId) {
		Map<String, Object> param = new HashMap<String, Object>();
		String smallImage = null;
		MobileProductRoute mpr = null;
		try {
			param.put("productId", Long.valueOf(productId));
			UserUser u = getUser();
			if (null != u) {
				param.put("userNo", u.getUserNo());
				param.put("userId", u.getId());
			}
			mpr = mobileProductService.getRouteDetail(param);
			if (null != mpr && !StringUtils.isEmpty(mpr.getProductName())) {
				mpr.setProductName(ClientUtils.filterQuotationMarks(mpr
						.getProductName()));
			}
			// 设置图片前缀
			this.setImagePrefix();
			if (mpr != null && !"".equals(mpr.getSmallImage())) {
				smallImage = mpr.getSmallImage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smallImage;
	}

	/**
	 * DATE转String
	 * 
	 * @param date
	 * @return
	 */
	public String getDate(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = df.format(date);
		return strDate;
	}

	/**
	 * 填写订单.
	 * 
	 * @return
	 */
	@Action("order_fill")
	public String fillOrder() {
		// 门票
		String returnStr = "order_fill";
		// 返回地址次数SESSION读写
		this.urlBackCount();
		try {
			//519活动支持下单
			//http://m.lvmama.com/clutter/ticketorder/order_fill.htm?branchId=3467
			if(StringUtil.isEmptyString(productId)){
				ProdProductBranch ppb = prodProductBranchService.getProductBranchDetailByBranchId(Long.valueOf(branchId), null, true);
				if(ppb!=null){
					productId=ppb.getProductId().toString();
				}
			}
			Map<String, Object> param = new HashMap<String, Object>();
			// 获取brancId
			param.put("branchId", branchId);
			String udid = ServletUtil.getCookieValue(getRequest(), "mb_udid");
			if (null == udid || udid.isEmpty()) {
				udid = UUID.randomUUID().toString();
				ServletUtil
						.addCookie(getResponse(), "mb_udid", udid, 30, false);// 默认保存30天
			}
			param.put("udid", udid);
			param.put("todayOrder", canOrderToday);
			if (getUser() != null) {
				param.put("userNo", getUser().getUserNo());

				// 初始化默认联系人信息
				userName = getUser().getUserName();
				mobile = getUser().getMobileNumber();

				// 我的可用优惠券
				point2CouponInfo();
			}
			if (!StringUtils.isEmpty(visitTime)) {
				param.put("visitTime", visitTime); // 预定 日期
			}

			// 获取Proudct
			ProdProduct product = prodProductService.getProdProductById(Long
					.valueOf(productId));

			// 时间价格表
			param.put("productId", productId);
			List<MobileTimePrice> timePrice = mobileProductService
					.timePrice(param);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("datas", timePrice);
			getRequest()
					.setAttribute("timePriceJson", JSONObject.fromObject(m));

			// 非今天明天后天 ，显示星期
			Map<String, Object> map = mobileProductService
					.getProductItems(param);
			if (null != map && null != map.get("timeHolder")) {
				if (String.valueOf(map.get("timeHolder")).indexOf("20") != -1) {
					map.put("timeHolder", map.get("weekOfDay")); // 星期
				}
			}

			getRequest().setAttribute("productItems", map);
			getRequest().setAttribute("product", product);
			getRequest().setAttribute("productName", product.getProductName());

			// 下单区分(自由行，简易预订，门票)
			if (null != product) {
				if (Constant.PRODUCT_TYPE.ROUTE.getCode().equals(
						product.getProductType())) {
					// 目的地自由行预定/短途跟团+自助巴士班+长途+出境
					if (Constant.SUB_PRODUCT_TYPE.FREENESS.getCode().equals(
							product.getSubProductType())
							|| Constant.SUB_PRODUCT_TYPE.GROUP.getCode().equals(
									product.getSubProductType())
							|| Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.getCode().equals(
									product.getSubProductType())
							|| Constant.SUB_PRODUCT_TYPE.GROUP_LONG.getCode().equals(
									product.getSubProductType())
							|| Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.getCode().equals(
									product.getSubProductType())
							|| Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.getCode().equals(
									product.getSubProductType())
							|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.getCode().equals(
							product.getSubProductType())) {
						
						returnStr = "order_fill_freeness";
						// 判断是否有附加产品
						getRequest().setAttribute("hasAddtional",
								hasAddtionalProduct(map));
					}
				} else if (Constant.PRODUCT_TYPE.TICKET.getCode().equals(
						product.getProductType())) {
					// 判断是否有快递费附加产品 - 实体票专用
					boolean b = hasExpressAddtionalProduct(map);
					getRequest().setAttribute("hasExpressAddtional", b);
					getRequest().setAttribute("physical", map.get("physical"));// 是否实体票
					getRequest().setAttribute("noAddQt",
							getNoAdditionalProductQt(map));// 非附加产品数量
					Map<String, Object> treeMap = mobileOtherService
							.getProvinceTree(param);
					if (null != treeMap && null != treeMap.get("tree")) {
						getRequest().setAttribute("provinceTree",
								treeMap.get("tree"));// 是否实体票
						getRequest().setAttribute("provinceTreeJson",
								JSONArray.fromObject(treeMap.get("tree")));// 是否实体票
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
		}

		// 门票预定
		return returnStr;
	}

	/**
	 * 返回地址SESSION读写
	 */
	public void urlBackCount() {
		if (this.getRequest().getSession().getAttribute("location") != null
				&& !"".equals(this.getRequest().getSession()
						.getAttribute("location").toString())) {
			location = Long.valueOf(this.getRequest().getSession()
					.getAttribute("location").toString());
		}
	}

	/**
	 * 删除SESSION属性
	 */
	@Action("del_sion")
	public void del_session() {
		if (this.getRequest().getSession().getAttribute("location") != null
				&& !"".equals(this.getRequest().getSession()
						.getAttribute("location"))) {
			this.getRequest().getSession().removeAttribute("location");
		}
	}

	/**
	 * 判断是否有附加产品
	 * 
	 * @param m
	 * @return
	 */
	public boolean hasAddtionalProduct(Map<String, Object> m) {
		try {
			if (null != m && null != m.get("datas")) {
				List<MobileBranch> branchList = (List<MobileBranch>) m
						.get("datas");
				if (!branchList.isEmpty()) {
					for (MobileBranch mb : branchList) {
						if (mb.isAdditional()) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 判断是否有快递费的附加产品
	 * 
	 * @param m
	 * @return
	 */
	public boolean hasExpressAddtionalProduct(Map<String, Object> m) {
		try {
			if (null != m && null != m.get("datas")) {
				List<MobileBranch> branchList = (List<MobileBranch>) m
						.get("datas");
				if (!branchList.isEmpty()) {
					for (MobileBranch mb : branchList) {
						if (mb.isAdditional()
								&& Constant.SUB_PRODUCT_TYPE.EXPRESS.getCode()
										.equals(mb.getSubProductType())) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 获取非附加产品的数量
	 * 
	 * @return
	 */
	public int getNoAdditionalProductQt(Map<String, Object> m) {
		int count = 0;
		try {
			if (null != m && null != m.get("datas")) {
				List<MobileBranch> branchList = (List<MobileBranch>) m
						.get("datas");
				if (!branchList.isEmpty()) {
					for (MobileBranch mb : branchList) {
						if (!mb.isAdditional()) {
							count++;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 订单提交成功.
	 * 
	 * @return
	 */
	@Action(value = "order_success", interceptorRefs = {
			@InterceptorRef(value = "clutterCreateOrderInterceptor"),
			@InterceptorRef(value = "defaultStack") })
	public String orderSuccess() {
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			param.put("orderId", orderId);
			if (getUser() != null) {
				param.put("userNo", getUser().getUserNo());
				param.put("userId", getUser().getId());
			} else {
				param.put("userNo", userNo);// 门票匿名下单用
				UserUser u=userUserProxy.getUserUserByUserNo(userNo);
				param.put("userId", u.getId());
			}
			MobileOrder order = mobileUserService.getOrder(param);
			if (null != order) {
				// 获取支付倒计时描述.
				Map<String, Object> m = mobileOrderService
						.queryOrderWaitPayTimeSecond(param);
				if (null != m) {
					getRequest().setAttribute("surplusSeconds",
							m.get("surplusSeconds"));
				}
				getRequest().setAttribute("order", order);
				//判断财付通支付
				String tenpayWapUrl=null;
				if(order.getPaymentChannels()!=null && order.getPaymentChannels().size()>0){
					for(MobilePayment o : order.getPaymentChannels()){
						if("TENPAY_WAP".equals(o.getPaymentCode())){
							tenpayWapUrl=o.getPaymentUrl();
						}
					}
				}
				getRequest().setAttribute("tenpayWapUrl", tenpayWapUrl);
				getRequest().setAttribute("needResourceTicketCanToPay",
						ClientUtils.isNeedResourceTicketCanToPay(order));
				return "order_success";
			} else {
				getRequest().setAttribute("msg", "未找到订单，订单号：" + orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
		}
		return "error";
	}

	/**
	 * 取消订单.
	 * 
	 * @return
	 */
	@Action("order_cancel")
	public void orderCancel() {
		String json = "{\"code\":1,\"orderId\":\"\",\"msg\":\"\"}";
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			param.put("orderId", orderId);
			param.put("userNo", getUser().getUserNo());
			Map<String, Object> m = mobileOrderService.cancellOrder4Wap(param);
			getRequest().setAttribute("operatorStatu", m.get("operatorStatu"));
			json = "{\"code\":1,\"operatorStatu\":\"" + m.get("operatorStatu")
					+ "\",\"msg\":\"\"}";
		} catch (Exception e) {
			e.printStackTrace();
			json = "{\"code\":-1,\"operatorStatu\":false,\"msg\":\"\"}";
		}
		super.sendAjaxResult(json);
	}

	/**
	 * 订单详情.
	 * 
	 * @return
	 */
	@Action("order_detail")
	public String orderDetail() {
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			param.put("orderId", orderId);
			param.put("userNo", getUser().getUserNo());
			MobileOrder order = mobileUserService.getOrder(param);
			if (null != order) {
				//百度的订单处理
				if(order.getPaymentChannel()!=null && Constant.MARK_PAYMENT_CHANNEL.BAIDU_PAY.name().equals(order.getPaymentChannel())){
					List<MobileOrderItem> orderItem=order.getOrderItem();
					for(MobileOrderItem o : orderItem){
						o.setPrice(BaiduActivityUtils.getProductPrice(String.valueOf(order.getProductId()),o.getPrice()));
					}
				}
				getRequest().setAttribute("order", order);
				getRequest().setAttribute("hasTraveller",
						isHasTraveller(order.getListPerson()));
				return "order_detail";
			} 
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
		}
		//找不到订单返回我的页面
		this.mylv();
		return "mylv";
	}
	public void mylv() {
		this.setImagePrefix();
		getRequest().setAttribute("nssoUrl", ClutterConstant.getNssoUrl());
		getRequest().setAttribute("needCmtAmout", 0);
		try {
			// 如果登陆
			if (null != getUser()) {
				Map<String, Object> parmas = new HashMap<String, Object>();
				parmas.put("userNo", getUser().getUserNo());
				Map<String, Object> resultMap = mobileUserService
						.queryCmtWaitForOrder(parmas);
				if (null != resultMap && null != resultMap.get("count")) {
					getRequest().setAttribute("needCmtAmout",
							resultMap.get("count"));
				}
				Map<String, String> pointParmas = new HashMap<String, String>();
				pointParmas.put("userNo", getUser().getUserNo());
				MobileUser user = mobileUserService.getUser(pointParmas);
				Long userPoint = 0L;
				if (user != null && user.getPoint() > 0L) {
					userPoint = user.getPoint();
				}
				getRequest().setAttribute("userPoint", userPoint);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 是否有游玩人
	 * 
	 * @param listPerson
	 * @return
	 */
	private boolean isHasTraveller(List<MobilePersonItem> listPerson) {
		if (null == listPerson || listPerson.size() < 1) {
			return false;
		} else {
			for (MobilePersonItem mp : listPerson) {
				if (null != mp
						&& Constant.ORD_PERSON_TYPE.TRAVELLER.getCode().equals(
								mp.getPersonType())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 支付返回界面.
	 * 
	 * @return
	 */
	@Action("order_pay_callback")
	public String payCallBack() {
		if (ajax) {
			Map<String, Object> param = new HashMap<String, Object>();
			try {
				param.put("orderId", orderId);
				if (getUser() != null) {
					param.put("userNo", getUser().getUserNo());
				}
				MobileOrder order = mobileUserService.getOrder(param);
				if (null != order) {
					getRequest().setAttribute("order", order);
				} else {
					getRequest().setAttribute("msg", "未找到订单，订单号：" + orderId);
				}
			} catch (Exception e) {
				e.printStackTrace();
				getRequest().setAttribute("msg", e.getMessage());
			}
		}
		if (ajax) {
			return "order_pay_callback_ajax";
		} else {
			return "order_pay_callback";
		}
	}

	/**
	 * 提交订单
	 */
	@Action("order_submit")
	public void orderSubmit() {
		String json = "{\"code\":1,\"orderId\":\"\",\"msg\":\"\"}";
		Map<String, Object> param = new HashMap<String, Object>();
		if (null != getUser()) {
			try {
				// 校验表单信息.
				if (Constant.PRODUCT_TYPE.ROUTE.getCode().equals(productType)) {

				} else {
					validateParams(param);
				}
				// 初始化表单信息.
				initSubmitParams(param);
				Map<String, Object> m = mobileOrderService.commitOrder(param);
				getRequest().setAttribute("orderId", m.get("orderId"));
				json = "{\"code\":1,\"orderId\":\""
						+ String.valueOf(m.get("orderId")) + "\",\"userNo\":\""
						+ getUser().getUserNo() + "\",\"msg\":\"\"}";
			} catch (Exception e) {
				e.printStackTrace();
				msg = e.getMessage();
				json = "{\"code\":-1,\"orderId\":\"\",\"msg\":\"" + msg + "\"}";
			}
		} else {
			json = "{\"code\":-1,\"orderId\":\"\",\"msg\":\"未登陆！\"}";
		}

		super.sendAjaxResult(json);
	}

	/**
	 * 简易预订提交订单
	 */
	@Action("order_submit_jianyi")
	public void orderSubmitJianYi() {
		String json = "{\"code\":1,\"orderId\":\"\",\"msg\":\"\"}";
		Map<String, Object> param = new HashMap<String, Object>();
		Long workOrderId = null;
		if (null != getUser()) {
			try {
				// 提交数据整理和验证
				validateParams(param);
				// 简易预订用户填写信息
				String content = initSubmitContent();

				// 创建工单
				WorkOrder workOrder = new WorkOrder();
				// 根据TYPECODE查询workOrderTypeId
				WorkOrderType workOrderType = getWorkOrderType(subProductType);
				if (workOrderType != null) {
					workOrder.setWorkOrderTypeId(workOrderType
							.getWorkOrderTypeId());
					workOrder.setContent(content);// 用户提交内容
					Date date = new Date();
					workOrder.setCreateTime(date);
					workOrder.setCreateUserName("WAP" + getUser().getUserNo());// 工单创建人简易预订（WAP+userId）
					workOrder.setLimitTime(this.limitTime(date));// 工单处理时限120分钟
					workOrder.setProductId(Long.valueOf(productId));
					workOrder.setStatus(Constant.WORK_ORDER_STATUS.UNCOMPLETED
							.getCode());// 工单状态默认”未处理“
					workOrder.setMobileNumber(this.mobile);
					workOrder.setUserName(this.userName);

					// 数据插入工单系统
					workOrderId = workOrderService.insert(workOrder);

					// 任务记录表插入数据
					WorkTask workTask = new WorkTask();
					// line 628--646将生成工单分给固定组
					Map<String, Object> params = new HashMap<String, Object>();
					if (subProductType != null && !"".equals(subProductType)) {
						// 长途
						if (subProductType.endsWith("LONG")) {
							params.put("groupName", "长线线路单组");
						}// 出境
						else if (subProductType.endsWith("FOREIGN")) {
							params.put("groupName", "出境线路单组");
						}
					}
					List<WorkGroup> groupList = workGroupService
							.queryWorkGroupByParam(params);
					WorkGroup workGroup = new WorkGroup();
					if (groupList != null && groupList.size() > 0) {
						workGroup = groupList.get(0);
					}
					WorkGroupUser receiveGroupUser = workOrderService
							.getFitUser(null, workGroup.getWorkGroupId(), null,
									null, null, null,
									workOrderType.getTypeCode());

					workTask.setContent("WAP简易预订单");
					workTask.setCreateTime(new Date());
					workTask.setStatus(Constant.WORK_TASK_STATUS.UNCOMPLETED
							.getCode());
					workTask.setWorkOrderId(workOrderId);
					workTask.setReceiver(receiveGroupUser.getWorkGroupUserId());
					Long workTaskId = workTaskService.insert(workTask);
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg = e.getMessage();
				json = "{\"code\":-1,\"orderId\":\"\",\"msg\":\"" + msg + "\"}";
			}
		} else {
			json = "{\"code\":-1,\"orderId\":\"\",\"msg\":\"未登陆！\"}";
		}

		// return "order_succ_v3";
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("workOrderId", workOrderId);
		this.sendAjaxResult(jsonObj.toString());
	}

	// 工单处理时限
	public Long limitTime(Date date) {
		Long limitTime = 0L;

		// 日期
		int day = date.getDate();

		// 小时
		int hour = date.getHours();

		// 分钟
		int minute = date.getMinutes();

		// 时间处理
		if ((hour >= 11 && hour < 19) || (hour == 19 && minute <= 30)) {// 11点到19.30
																		// 处理时间为120分钟
			limitTime = 120L;
		} else {
			if ((hour > 19 && hour <= 23) || (hour == 19 && minute >= 30)) {// 19.30到24点--
				limitTime = (long) ((35 - hour) * 60 - minute);
			} else {// 0-11点
				limitTime = (long) ((11 - hour) * 60 - minute);
			}
		}
		return limitTime;
	}

	// 判断是否显示处理时间文案
	public String limitTimeText(Date date) {
		String limitTimText = null;

		// 小时
		int hour = date.getHours();

		// 分钟
		int minute = date.getMinutes();

		// 时间处理
		if ((hour >= 9 && hour < 19) || (hour == 19 && minute <= 30)) {// 11点到19.30
																		// 处理时间为120分钟
			limitTimText = "false";
		} else {
			limitTimText = "true";
		}
		return limitTimText;
	}

	/**
	 * 简易预订单成功页面
	 * 
	 * @return
	 */
	@Action("order_succ_v3")
	public String orderSuccV3() {
		JianYiOrderDTO jianYiOrderDTO = new JianYiOrderDTO();
		String limitTimeText = null;
		WorkOrder workOrder = workOrderService.getWorkOrderById(workOrderId);

		if (workOrder != null) {
			jianYiOrderDTO = this.dataTransf(workOrder);
			limitTimeText = this.limitTimeText(workOrder.getCreateTime());
		}

		getRequest().setAttribute("jianYiOrder", jianYiOrderDTO);
		getRequest().setAttribute("limitTimeText", limitTimeText);
		return "order_succ_v3";
	}

	/**
	 * 验证订单
	 */
	@Action("validateAjaxPrice")
	public void validateAjaxPrice() {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		;
		try {
			param.put("validateCouponCode",
					getRequestParameter("validateCoupon"));
			initSubmitParams(param);
			if (!StringUtils.isEmpty(param.get("branchItem").toString())
					&& !StringUtils.isEmpty(param.get("personItem").toString())) {
				resultMap = mobileOrderService.validateCoupon(param);
			}
			this.putResultMessageForWap(resultMap, null);
		} catch (Exception e) {
			if (e instanceof LogicException) {
				String msg = e.getMessage();
				this.putResultMessageForWap(resultMap, msg);
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.sendAjaxResult(JSONObject.fromObject(resultMap).toString());
		}

	}

	/**
	 * 添加游玩人，联系人和紧急联系人页面
	 * 
	 * @return
	 */
	@Action("order_fill_traveler")
	public String orderFillTraveler() {
		try {
			Long t_branchId = Long.valueOf(branchId);
			Long t_productId = Long.valueOf(productId);
			Date t_visitDate = DateUtil.parseDate(visitTime, "yyyy-MM-dd");

			ProdProductBranch prodProductBranch = pageService
					.getProdBranchByProdBranchId(t_branchId);
			if (prodProductBranch == null) {
				LOG.error("类别不存在");
				return "error";
			}
			Map<String, Object> data = pageService.getProdCProductInfo(
					t_productId, false);
			if (data.size() == 0) {
				return "error";
			}
			List<ProdProductBranch> branchList = (List<ProdProductBranch>) data
					.get("prodProductBranchList");
			if (branchList.isEmpty()) {
				return "error";
			}
			ProdProductBranch mainProdBranch;
			// 非不定期产品才需校验主类别
			if (!prodProductBranch.getProdProduct().IsAperiodic()) {
				// 现在传入的为产品类别ID.为保证前台参数传的简单性,自己计算对应的产品ID.
				mainProdBranch = productServiceProxy
						.getProdBranchDetailByProdBranchId(t_branchId,
								t_visitDate);
				if (mainProdBranch == null) {
					LOG.error("类别不存在");
					return "error";
				}
			} else {// 不定期取第一个可售类别为主类别
				mainProdBranch = (ProdProductBranch) data.get("prodBranch");
			}

			// receiversList =
			// receiverUserService.loadUserReceiversByUserId(this.getUser().getUserNo());
			// // 联系人列表

			ProdProduct prodProduct = ((ProdCProduct) data.get("prodCProduct"))
					.getProdProduct();
			if (null == prodProduct) {
				return "errror";
			}

			// 游玩人信息
			initOptionInfo(prodProduct);

			long fillTravellerNum = 0L;
			if (null != baoxianSelect || baoxianSelect > 0) { // 如果选择保险的时候就按产品信息读取需要填写的游玩人数
				if (mainProdBranch.getProdProduct().hasSelfPack()) {// 超级自由行 大人
																	// + 小孩
					// fillTravellerNum = buyInfo.getTotalQuantity();
					fillTravellerNum = totalQuantity;
				} else {
					// 含有必填的数据(且不是酒店)
					// 酒店提交的数据结构不支持目前关于有玩人数的计算
					// 主产品的人数
					// if (buyInfo.getBuyNum().containsKey("product_" +
					// mainProdBranch.getProdBranchId())) {
					Long banchQuantity = getQuantityByBranchId(mainProdBranch
							.getProdBranchId());
					if (banchQuantity > 0) {
						fillTravellerNum = (mainProdBranch.getAdultQuantity() + mainProdBranch
								.getChildQuantity()) * banchQuantity;
					}
					// 关联产品
					List<ProdProductBranch> relatedProductList = this.productServiceProxy
							.getProdBranchList(mainProdBranch.getProductId(),
									mainProdBranch.getProdBranchId(),
									t_visitDate);
					for (ProdProductBranch vpp : relatedProductList) {
						Long vppBranchQuantity = getQuantityByBranchId(vpp
								.getProdBranchId());
						if (!vpp.hasAdditional() && vppBranchQuantity > 0) {
							fillTravellerNum += (vpp.getAdultQuantity() + vpp
									.getChildQuantity()) * vppBranchQuantity;
						}
					}

				}
			}

			fillTravellerNum = fillTravellerNum == 0 ? 1 : fillTravellerNum;

			
			
			//游玩人数量和必填字段
			List<Map<String, Object>> travellerOptions = new ArrayList<Map<String, Object>>();

			/**
			 * 处理游玩人选项。
			 */
			for (int i = 0; i < fillTravellerNum; i++) {
				if (i == 0
						&& prodProduct.getFirstTravellerInfoOptionsList() != null) {
					Map<String, Object> firstTravellerOption = new HashMap<String, Object>();
					for (String option : prodProduct
							.getFirstTravellerInfoOptionsList()) {
						firstTravellerOption.put(option, option);
					}
					//线路--如果有保险
					if("ROUTE".equals(prodProduct.getProductType()) && (null != baoxianSelect && baoxianSelect > 0)){
						firstTravellerOption.put("NAME","NAME");
						firstTravellerOption.put("MOBILE","MOBILE");
						firstTravellerOption.put("CARD_NUMBER", "CARD_NUMBER");
					}
					travellerOptions.add(firstTravellerOption);
				} else if (prodProduct.getTravellerInfoOptionsList() != null) {
					Map<String, Object> otherTravellerOption = new HashMap<String, Object>();
					for (String option : prodProduct
							.getTravellerInfoOptionsList()) {
						otherTravellerOption.put(option, option);
					}
					//线路--如果有保险
					if("ROUTE".equals(prodProduct.getProductType()) && (null != baoxianSelect && baoxianSelect > 0)){
						otherTravellerOption.put("NAME","NAME");
						otherTravellerOption.put("MOBILE","MOBILE");
						otherTravellerOption.put("CARD_NUMBER", "CARD_NUMBER");
					}
					travellerOptions.add(otherTravellerOption);
				} 
//				else if ("ROUTE".equals(prodProduct.getProductType()) && (null != baoxianSelect && baoxianSelect > 0)) {
//					Map<String, Object> travellerOption = new HashMap<String, Object>();
//					travellerOption.put("NAME", "NAME");
//					travellerOption.put("CARD_NUMBER", "CARD_NUMBER");
//					travellerOptions.add(travellerOption);
//				}
			
			}

			// prodAssemblyPointList =
			// this.productServiceProxy.getAssemblyPoints(t_productId); 上车地点
			// getUser();

			// this.initReceiversList(); 订单邮寄地址
			// 游玩人数量
			getRequest().setAttribute("fillTravellerNum", fillTravellerNum);
			
			// 需要游玩人的信息列表
			getRequest().setAttribute("travellerOptions", travellerOptions);

			// 是否需要紧急联系人
			getRequest().setAttribute("emergencyContact",
					isNeedEmergencyContact(mainProdBranch));

			// 获取我的游玩人列表
			getMyContactList();

			if (!StringUtils.isEmpty(userName)) {
				String paramsTrans = new String(
						userName.getBytes("ISO-8859-1"), "UTF-8");
				userName = java.net.URLDecoder.decode(paramsTrans, "UTF-8");
			}

			// 是否实体票
			if (hasPhysical()) {
				decodeExpMpi(mpi);
			}
			// 门票匿名下单到填写游玩人需要登录，因为有乱码所以将填写订单页面的中文存到SESSION，这里读取SESSION
			if ("TICKET".equals(prodProduct.getProductType())) {
				this.mpiGetSession();
			}
			return "order_fill_traveler";
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("添加游玩人，联系人和紧急联系人页面异常");

			return "error";
		}
	}

	/**
	 * 填写游玩人将填写订单信息存入SESSION
	 */
	public void mpiGetSession() {
		// 订单联系人SESSION读取
		if (this.getRequest().getSession().getAttribute("orderUserName") != null
				&& !"".equals(this.getRequest().getSession()
						.getAttribute("orderUserName").toString())) {

			userName = this.getRequest().getSession()
					.getAttribute("orderUserName").toString();
			// 赋完值后删除SESSION
			this.getRequest().getSession().removeAttribute("orderUserName");
		}
		if (this.getRequest().getSession().getAttribute("mpi") != null
				&& !"".equals(this.getRequest().getSession()
						.getAttribute("mpi").toString())) {
			mpi = (MobilePersonItem) this.getRequest().getSession()
					.getAttribute("mpi");
			// 赋完值后删除SESSION
			this.getRequest().getSession().removeAttribute("mpi");
		}
	}

	/**
	 * 填写游玩人将填写订单信息存入SESSION
	 */
	public void mpiSetSession() {
		if (userName != null && !"".equals(userName)) {
			this.getRequest().getSession()
					.setAttribute("orderUserName", userName);
		}
		if (mpi != null) {
			this.getRequest().getSession().setAttribute("mpi", mpi);
		}
	}

	/**
	 * 是否实体票
	 * 
	 * @return true；
	 */
	public boolean hasPhysical() {
		if (!StringUtils.isEmpty(physical) && "true".equals(physical)) {
			return true;
		}
		return false;
	}

	/**
	 * 实体票收件人中文编码
	 * 
	 * @param mp
	 */
	public void decodeExpMpi(MobilePersonItem mp) {
		if (null != mp) {
			try {
				if (!StringUtils.isEmpty(mp.getPersonName())) {
					String name = new String(mp.getPersonName().getBytes(
							"ISO-8859-1"), "UTF-8");
					mp.setPersonName(java.net.URLDecoder.decode(name, "UTF-8"));
				}

				if (!StringUtils.isEmpty(mp.getAddress())) {
					String address = new String(mp.getAddress().getBytes(
							"ISO-8859-1"), "UTF-8");
					address = address.replace("-", "_").replace(":", "");
					mp.setAddress(java.net.URLDecoder.decode(address, "UTF-8"));
				}

				if (!StringUtils.isEmpty(mp.getProvince())) {
					String name = new String(mp.getProvince().getBytes(
							"ISO-8859-1"), "UTF-8");
					mp.setProvince(java.net.URLDecoder.decode(name, "UTF-8"));
				}

				if (!StringUtils.isEmpty(mp.getCity())) {
					String name = new String(mp.getCity()
							.getBytes("ISO-8859-1"), "UTF-8");
					mp.setCity(java.net.URLDecoder.decode(name, "UTF-8"));
				}

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 直接提交表单 不需要填写游玩人
	 */
	@Action("order_validate_traveler")
	public void redirectFreenessForm() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		;
		try {
			Long t_branchId = Long.valueOf(branchId);
			Long t_productId = Long.valueOf(productId);
			Date t_visitDate = DateUtil.parseDate(visitTime, "yyyy-MM-dd");

			ProdProductBranch prodProductBranch = pageService
					.getProdBranchByProdBranchId(t_branchId);
			if (prodProductBranch == null) {
				LOG.error("类别不存在");
				this.putResultMessageForWap(resultMap, "类别不存在");
			}
			Map<String, Object> data = pageService.getProdCProductInfo(
					t_productId, false);
			if (data.size() == 0) {
				this.putResultMessageForWap(resultMap, "产品不存在");

			}
			List<ProdProductBranch> branchList = (List<ProdProductBranch>) data
					.get("prodProductBranchList");
			if (branchList.isEmpty()) {
				this.putResultMessageForWap(resultMap, "类别不存在");
			}
			ProdProductBranch mainProdBranch;
			// 非不定期产品才需校验主类别
			if (!prodProductBranch.getProdProduct().IsAperiodic()) {
				// 现在传入的为产品类别ID.为保证前台参数传的简单性,自己计算对应的产品ID.
				mainProdBranch = productServiceProxy
						.getProdBranchDetailByProdBranchId(t_branchId,
								t_visitDate);
				if (mainProdBranch == null) {
					LOG.error("类别不存在");
					this.putResultMessageForWap(resultMap, "类别不存在");
				}
			} else {// 不定期取第一个可售类别为主类别
				mainProdBranch = (ProdProductBranch) data.get("prodBranch");
			}

			ProdProduct prodProduct = ((ProdCProduct) data.get("prodCProduct"))
					.getProdProduct();
			if (null == prodProduct) {
				this.putResultMessageForWap(resultMap, "类别不存在");
			}
			// 游玩人信息
			initOptionInfo(prodProduct);
			// 是否直接提交订单
			boolean b = isRedirectSubmitFreenessForm(mainProdBranch);
			if (b) {
				this.putResultMessageForWap(resultMap, null);
			} else {
				// 填写游玩人将填写订单信息存入SESSION--门票专用
				if ("TICKET".equals(prodProduct.getProductType())) {
					this.mpiSetSession();
				}
			}
		} catch (Exception e) {
			this.putResultMessageForWap(resultMap, "服务器异常");
		} finally {
			this.sendAjaxResult(JSONObject.fromObject(resultMap).toString());
		}
	}

	/**
	 * 线路是否不填写游玩人而直接提交表单
	 * 
	 * @param mainProdBranch
	 * @return
	 */
	public boolean isRedirectSubmitFreenessForm(ProdProductBranch mainProdBranch) {
		if (!isNeedEmergencyContact(mainProdBranch)
				&& travellerInfoOptions.size() < 1) {
			return true;
		}
		return false;
	}

	/**
	 * 游玩人列表
	 */
	public void getMyContactList() {
		Map<String, Object> param = new HashMap<String, Object>();
		List<MobileReceiver> contactList = null;
		if (getUser() != null) {
			param.put("userNo", getUser().getUserNo());
			contactList = mobileUserService.getContact(param);
		}
		getRequest().setAttribute("contactList", contactList);
	}

	/**
	 * 是否需要紧急联系人
	 * 
	 * @param mainProdBranch
	 * @return
	 */
	private boolean isNeedEmergencyContact(ProdProductBranch mainProdBranch) {
		if (null == mainProdBranch || null == mainProdBranch.getProdProduct()) {
			return false;
		}
		ProdProduct pd = mainProdBranch.getProdProduct();
		if (!(pd.isTicket() || pd.isHotel() || pd.isTrain() || (pd.isRoute() && "FREENESS"
				.equals(pd.getSubProductType())))) {
			return true;
		}

		return false;
	}

	/**
	 * 更加branchId获取branch的数量
	 * 
	 * @param branchItem
	 * @return
	 */
	private Long getQuantityByBranchId(Long branchId) {
		Long quantity = 0l;
		if (null != orderItemList && orderItemList.size() > 0) {
			for (int i = 0; i < orderItemList.size(); i++) {
				MobileBranchItem mb = orderItemList.get(i);
				if ((mb.getBranchId() + "").equals(branchId + "")) {
					return mb.getQuantity();
				}
			}
		}
		return quantity;
	}

	/**
	 * 初始化游玩人和联系人的必填信息
	 * 
	 * @param product
	 *            购买主产品
	 */
	private void initOptionInfo(ProdProduct product) {
		// 联系人的手机和姓名必填
		contactInfoOptions = new ArrayList<String>();
		contactInfoOptions.add(Constant.TRAVELLER_INFO_OPTIONS.NAME.getCode());
		contactInfoOptions
				.add(Constant.TRAVELLER_INFO_OPTIONS.MOBILE.getCode());

		travellerInfoOptions = product.getTravellerInfoOptionsList();
		firstTravellerInfoOptions = product.getFirstTravellerInfoOptionsList();

		if (null == travellerInfoOptions) {
			travellerInfoOptions = new ArrayList<String>(0);
		}
		if (null == firstTravellerInfoOptions) {
			firstTravellerInfoOptions = new ArrayList<String>(0);
		}

		if (null != product.getContactInfoOptionsList()) {
			contactInfoOptions.addAll(product.getContactInfoOptionsList());
		}

		// 保险产品强制填写投保人信息
		if (null != baoxianSelect && baoxianSelect > 0) {
			if (!firstTravellerInfoOptions.contains("NAME")) {
				firstTravellerInfoOptions.add("NAME");
			}
			if (!firstTravellerInfoOptions.contains("CARD_NUMBER")) {
				firstTravellerInfoOptions.add("CARD_NUMBER");
			}
			if (!firstTravellerInfoOptions.contains("MOBILE")) {
				firstTravellerInfoOptions.add("MOBILE");
			}
			if (!travellerInfoOptions.contains("NAME")) {
				travellerInfoOptions.add("NAME");
			}
			if (!travellerInfoOptions.contains("CARD_NUMBER")) {
				travellerInfoOptions.add("CARD_NUMBER");
			}
			if (!travellerInfoOptions.contains("MOBILE")) {
				travellerInfoOptions.add("MOBILE");
			}
		}
		// else {
		// // 如果第一游玩人和游玩人都不选
		// if (firstTravellerInfoOptions.size() < 1
		// && travellerInfoOptions.size() > 0) {
		// for (String s : travellerInfoOptions) {
		// firstTravellerInfoOptions.add(s);
		// }
		// }
		// }
	}

	/**
	 * 积分兑换优惠券.
	 */
	public void point2CouponInfo() {
		try {
			// 可用优惠券
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", getUser().getId());
			params.put("state", "NOT_USED");
			List<MobileUserCoupon> unusedList = mobileUserService
					.getCoupon(params);
			getRequest().setAttribute("unusedList", unusedList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化订单信息.
	 * 
	 * @return
	 */
	public Map<String, Object> initSubmitParams(Map<String, Object> param) {
		String udid = ServletUtil.getCookieValue(getRequest(), "mb_udid");
		if (null == udid || udid.isEmpty()) {
			udid = UUID.randomUUID().toString();
			ServletUtil.addCookie(getResponse(), "mb_udid", udid, 30, false);// 默认保存30天
		}
		param.put("udid", ServletUtil.getCookieValue(getRequest(), "mb_udid"));
		param.put("lvsessionid",
				ServletUtil.getLvSessionId(getRequest(), getResponse()));
		if (getUser() != null) {
			param.put("userNo", getUser().getUserNo());
		}
		param.put("branchItem", initBranchItems()); // 类别
		if (Constant.PRODUCT_TYPE.ROUTE.getCode().equals(productType)) {
			param.put("personItem", personListItems); // 游玩人信息
		} else if (Constant.PRODUCT_TYPE.TICKET.getCode().equals(productType)) {// 景区
																				// 如果不是直接提交
			param.put("personItem", personListItems); // 游玩人信息
		} else {
			param.put("personItem", initPersonItems()); // 如果门票不需要游玩人信息
		}
		if (!StringUtil.isEmptyString(econtractEmail)){
			param.put("econtractEmail", this.econtractEmail);//出团通知书邮件
		}
		param.put("visitTime", this.visitTime); // 游玩时间
		param.put("couponCode", this.couponCode);// 优惠券
		param.put("todayOrder", this.canOrderToday);// 是否今日定
		param.put("firstChannel", this.firstChannel);// 一级渠道
		param.put("lvversion", "4.0.0");
		param.put("secondChannel", this.secondChannel);// 二级渠道
		return param;
	}

	/**
	 * 简易预数据组装
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String initSubmitContent() throws UnsupportedEncodingException {
		StringBuffer content = new StringBuffer();
		content.append("联系人：" + this.userName + ",");
		content.append("手机号码：" + this.mobile + ",");
		content.append("产品名称：" + this.productName + ",");
		content.append("产品ID：" + this.productId + ",");
		content.append("产品列表：" + probuceListDatas() + ",");
		content.append("游玩日期：" + this.visitTime);
		return content.toString();
	}

	/**
	 * 根据TYPECODE查询WorkOrderType
	 * 
	 * @param subProductType
	 * @return
	 */
	public WorkOrderType getWorkOrderType(String subProductType) {
		WorkOrderType workOrderType = null;
		Map<String, Object> map = new HashMap<String, Object>();
		if (subProductType != null && !"".equals(subProductType)) {
			// 长途
			if (subProductType.endsWith("LONG")) {
				map.put("typeCode", "cxxl");
			}// 出境
			else if (subProductType.endsWith("FOREIGN")) {
				map.put("typeCode", "cjxl");
			}
		}
		List<WorkOrderType> WorkOrderTypeList = workOrderTypeService
				.queryWorkOrderTypeByParam(map);
		if (WorkOrderTypeList != null && WorkOrderTypeList.size() > 0) {
			workOrderType = WorkOrderTypeList.get(0);
		}
		return workOrderType;
	}

	/**
	 * 简易预订产品列表组装
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String probuceListDatas() throws UnsupportedEncodingException {
		// 数据整理
		this.tidyData();
		StringBuffer sb = new StringBuffer("");
		if (null != orderDataList && orderDataList.size() > 0) {
			for (int i = 0; i < orderDataList.size(); i++) {
				OrderDataItem mb = orderDataList.get(i);
				if (mb.getBranchNum() > 0) {
					if (StringUtils.isEmpty(sb)) {
						sb.append(mb.getShortName())
								.append("<" + mb.getBranchId() + ">" + "￥"
										+ mb.getSellPriceYuan() + "x")
								.append(mb.getBranchNum());
					} else {
						sb.append(",")
								.append(mb.getShortName())
								.append("<" + mb.getBranchId() + ">" + "￥"
										+ mb.getSellPriceYuan() + "x")
								.append(mb.getBranchNum());
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 数据整理
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public void tidyData() throws UnsupportedEncodingException {
		// 简易预定产品数量价格列表
		if (null != orderDataList && orderDataList.size() > 0) {
			List<OrderDataItem> t_list = new ArrayList<OrderDataItem>();
			for (int i = 0; i < orderDataList.size(); i++) {
				OrderDataItem roi = orderDataList.get(i);
				if (null != roi && null != roi.getBranchNum()
						&& roi.getBranchNum() > 0) {
					t_list.add(roi);
				}
			}
			orderDataList = t_list;
		}
	}

	/**
	 * 初始化类别信息.
	 * 
	 * @return
	 */
	public String initBranchItems() {
		StringBuffer sb = new StringBuffer("");
		if (null != orderItemList && orderItemList.size() > 0) {
			for (int i = 0; i < orderItemList.size(); i++) {
				MobileBranchItem mb = orderItemList.get(i);
				if (mb.getQuantity() > 0) {
					if (StringUtils.isEmpty(sb)) {
						sb.append(mb.getBranchId()).append("-")
								.append(mb.getQuantity());
					} else {
						sb.append("_").append(mb.getBranchId()).append("-")
								.append(mb.getQuantity());
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 初始化人员信息. 格式为 名字:手机:类型:身份证 其中类型： ORD_PERSON_TYPE
	 * 
	 * @return
	 */
	public String initPersonItems() {
		StringBuffer sb = new StringBuffer("");
		sb.append(StringUtil.filterOutHTMLTags(this.userName)).append("-")
				.append(this.mobile).append("-").append(this.personType);
		if (!StringUtils.isEmpty(this.idCard)) {
			sb.append("-").append(this.idCard);
		}
		// 是实体票联系人
		if (hasPhysical() && null != this.mpi
				&& !StringUtils.isEmpty(mpi.getPersonName())) {
			if (!StringUtils.isEmpty(sb)) {
				sb.append(":");
			}
			sb.append(StringUtil.filterOutHTMLTags(mpi.getPersonName()))
					.append("-").append(mpi.getPersonMobile()).append("-")
					.append(mpi.getPersonType()).append("-")
					.append(mpi.getProvince()).append("-")
					.append(mpi.getCity()).append("-")
					.append(StringUtil.filterOutHTMLTags(mpi.getAddress()));
		}

		return sb.toString();
	}

	/**
	 * 表单校验
	 * 
	 * @throws Exception
	 */
	public void validateParams(Map<String, Object> param) throws Exception {
		Map<String, String> m = ClientConstants.getErrorInfo();
		// 取票人姓名
		if (StringUtil.isEmptyString(userName)/*
											 *  * &&*
											 * !StringUtil.validUserName(userName
											 * * )
											 */) {
			throw new Exception(m.get("invalidUserName"));
		}
		// 取票人手机
		if (StringUtil.isEmptyString(mobile)) {
			throw new Exception(m.get("invalidMobile"));
		}

		// 身份证号 。则判断，如果填写身份证号。
		if (needIdCard) {
			if (StringUtil.isEmptyString(idCard)
					|| !StringUtil.isIdCard(idCard)) {
				throw new Exception(m.get("invalidIdCard"));
			}
		}

		// 票数
		if (null != orderItemList && orderItemList.size() > 0) {
			List<MobileBranchItem> t_list = new ArrayList<MobileBranchItem>();
			for (int i = 0; i < orderItemList.size(); i++) {
				MobileBranchItem roi = orderItemList.get(i);
				if (null != roi && null != roi.getQuantity()
						&& roi.getQuantity() > 0) {
					t_list.add(roi);
				}
			}
			if (t_list.size() < 1) {
				throw new Exception(m.get("invalidQuantity"));
			}
			orderItemList = t_list;
		} else {
			throw new Exception(m.get("invalidQuantity"));
		}
		//判断是否是百度活动票1半价2立减0其他
		String spottickType=BaiduActivityUtils.ticketType(productId);
		//不可预订直接返回
		if(!"0".equals(spottickType)){
			throw new ActivityLogicException("非特价票");
		}
	}

	/**
	 * 酒店预订
	 * 
	 * @return
	 */
	@Action("hotel_order_fill")
	public String hotelOrderFill() {
		Map<String, Object> param=new HashMap<String, Object>();
		try {
			//this.paramsverify();//参数验证
			//this.sessionGetDel();
			
			this.paramsAssemble(param);//参数组装
			param.put("userNo",getUser().getUserNo());
			Map<String, Object> resultMap=clientHotelService.orderFill(param);
			if(resultMap!=null){
				JSONObject mobileHotelRule=(JSONObject) resultMap.get("mobileHotelRule");
				getRequest().setAttribute("mobileHotelRule", mobileHotelRule);
			}
			customerIPAddress=this.getRequest().getRemoteAddr();
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
			return "error";
		}
		
		return "hotel_order_fill";
		
	}
//	/**
//	 * 酒店名称和类型名称SESSION
//	 */
//	public void sessionGetDel(){
//		if(this.getRequest().getSession().getAttribute("hotelNameSession")!=null && !"".equals(this.getRequest().getSession().getAttribute("hotelNameSession"))){
//			hotelName=this.getRequest().getSession().getAttribute("hotelNameSession").toString();
//			this.getRequest().getSession().removeAttribute("hotelNameSession");
//		}
//		if(this.getRequest().getSession().getAttribute("hotelTypeNameSession")!=null && !"".equals(this.getRequest().getSession().getAttribute("hotelNameSession"))){
//			hotelTypeName=this.getRequest().getSession().getAttribute("hotelTypeNameSession").toString();
//			this.getRequest().getSession().removeAttribute("hotelTypeNameSession");
//		}
//	}

	/**
	 * 酒店预订页--改变预订房间数量
	 */
	@Action("hotel_change_number")
	public void changeNumberRooms() {
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			// this.paramsverify();//参数验证
			this.paramsAssemble(param);// 参数组装
			Map<String, Object> resultMap = clientHotelService.orderFill(param);
			JSONObject mobileHotelRule = new JSONObject();
			if (resultMap != null) {
				mobileHotelRule = (JSONObject) resultMap.get("mobileHotelRule");
				getRequest().setAttribute("mobileHotelRule", mobileHotelRule);
			}
			this.sendAjaxResult(mobileHotelRule.toString());
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
		}

	}

	/**
	 * 酒店预订--需要担保
	 * @throws UnsupportedEncodingException 
	 */
	@Action("guarantee_order")
	public String guaranteeOrder() throws UnsupportedEncodingException {
			String returnStr=null;
			Map<String, Object> resultMap = super.resultMapCreator();
			Map<String, Object> param = new HashMap<String, Object>();
			Map<String, Object> result = null;
			UserUser user = this.getUser();
			if (null != user) {
				param.put("userNo", user.getUserNo());
				try {
					result = clientHotelService.getUserCreditCards(param);
				} catch (Exception e) {
					String msg = e.getMessage();
					if (e instanceof LogicException) {
						super.putLogicExceptionMessage(resultMap, msg);

					} else {
						this.putExceptionMessage(resultMap, msg);
					}
					e.printStackTrace();
				}
			}else{
				this.putErrorMessage(resultMap, "用户尚未登录");
				return "error";
			}
			if ("-1".equals(resultMap.get("code"))) {
				resultMap.put("message", "获取信用卡信息失败");
				return "error";
			}
			/**
			 * 直接从数据库取用户信息，避免缓存未刷新 数据部一致的问题。
			 */
			user = userUserProxy.getUserUserByUserNo(user.getUserNo());
			resultMap.put("saveCreditCard", "Y".equals(user.getSaveCreditCard())?true:false);
			
			
			//填写订单页面参数传递编码
			String hanzi1 = new String(
					option.getBytes("ISO-8859-1"), "UTF-8");
			option = java.net.URLDecoder.decode(hanzi1, "UTF-8");
			String hanzi2 = new String(
					contactName.getBytes("ISO-8859-1"), "UTF-8");
			contactName = java.net.URLDecoder.decode(hanzi2, "UTF-8");
			String hanzi3 = new String(
					customerNames.getBytes("ISO-8859-1"), "UTF-8");
			customerNames = java.net.URLDecoder.decode(hanzi3, "UTF-8");
			//信用卡有效期
			this.getYearAndMohth();
			if(result!=null){
				JSONArray userCreditCards=(JSONArray) result.get("userCreditCards");
				if(userCreditCards!=null && userCreditCards.size()>0){
					for(Object o : userCreditCards){
						JSONObject ob=(JSONObject) o;
						ob.put("creditCardNoShort",  ob.get("creditCardNo").toString().substring(0, 4)+"-"+ob.get("creditCardNo").toString().substring(4, 8));
					}
					getRequest().setAttribute("userCreditCards", userCreditCards);
					getRequest().setAttribute("saveCreditCard", resultMap.get("saveCreditCard"));
					returnStr= "hotel_guarantee_yescards";
				}else{
					returnStr="hotel_guarantee_nocards";
				}
			}
		
		
		return  returnStr ;//"hotel_guarantee_nocards";// hotel_guarantee_yescards
	}
	/**
	 * 得到信用卡有效日期
	 */
	public void getYearAndMohth(){
		List<Integer> yearList = new ArrayList<Integer>();
		List<Integer> monthList = new ArrayList<Integer>();
		int year = 2014;
		for (int i = 0; i < 100; i++) {
			yearList.add(year);
			year++;
		}
		int month = 1;
		for (int i = 0; i < 12; i++) {
			monthList.add(month);
			month++;
		}
		Date now=new Date();
		getRequest().setAttribute("nowMonth", now.getMonth()+1);
		getRequest().setAttribute("nowYear", now.getYear());
		getRequest().setAttribute("yearList", yearList);
		getRequest().setAttribute("monthList", monthList);
	}
	// /**
	// * 酒店预订--提交订单
	// */
	// @Action("hotel_order_submit")
	// public void hotelOrderSubmit(){
	// Map<String, Object> param=new HashMap<String, Object>();
	// try {
	// //this.paramsverify();//参数验证
	// // this.paramsAssemble(param);//参数组装
	// // Map<String, Object> resultMap=clientHotelService.orderFill(param);
	// // JSONObject mobileHotelRule=new JSONObject();
	// // if(resultMap!=null){
	// // mobileHotelRule=(JSONObject) resultMap.get("mobileHotelRule");
	// // getRequest().setAttribute("mobileHotelRule", mobileHotelRule);
	// // }
	// String jsonStr =HttpsUtil.proxyRequestGet("/hotel/create.do",
	// InternetProtocol.getRemoteAddr(getRequest()));
	// this.sendAjaxResult(jsonStr);
	// } catch (Exception e) {
	// e.printStackTrace();
	// getRequest().setAttribute("msg", e.getMessage());
	// }
	// }
	/**
	 * 酒店预订--成功页面
	 */
	@Action("hotel_order_success")
	public String hotelOrderSuccess() {
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			param.put("orderId", orderId);
			Map<String, Object> resultMap = clientHotelService
					.orderDetail(param);
			if (resultMap != null) {
				JSONObject orderDetailResult = (JSONObject) resultMap
						.get("orderDetailResult");
				getRequest().setAttribute("orderDetailResult",
						orderDetailResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
			return "error";
		}

		return "hotel_order_success";
	}

	/**
	 * 酒店列表
	 * 
	 * @return
	 */
	@Action("hotel_order_list")
	public String hotelOrderList() {
		// http://192.168.0.94/clutter/router/rest.do?method=api.com.hotel.orderList&pageIndex=1&pageSize=5&contactMobile=13312121212
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			param.put("pageIndex", pageIndex);
			param.put("pageSize", pageSize);
			param.put("userNo", getUser().getUserId());
			param.put("userId",getUser().getUserId());

			Map<String, Object> resultMap = clientHotelService.orderList(param);
			List<JSONObject> jsonOrderDetailResults = new ArrayList<JSONObject>();
			if (resultMap != null) {
				jsonOrderDetailResults = (List<JSONObject>) resultMap
						.get("orderDetailResults");
				Date now=new Date();//今天
				Date tomorrow=DateUtil.getDateAddDay(now,1);//明天
				arrivalDate=getDateToString(now);
				departureDate=getDateToString(tomorrow);
				if(ajax){
					if(jsonOrderDetailResults!=null && jsonOrderDetailResults.size()>0){
						for(JSONObject o : jsonOrderDetailResults){
							o.put("orderId", o.get("orderId").toString());
						}
					}
				}
				getRequest().setAttribute("hotelOrderList",
						jsonOrderDetailResults);
				getRequest().setAttribute("hasNext", resultMap.get("hasNext"));
				getRequest().setAttribute("arrivalDate", arrivalDate);
				getRequest().setAttribute("departureDate", departureDate);
			}
			if(ajax){
				return "hotel_order_list_ajax";
			}
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
			return "error";
		}
		return "hotel_order_list";
	}
	/**
	 * 日期转换String
	 * @param dateString yyyy-MM-dd
	 * @param days  int
	 * @return
	 */
	public String getDateToString(Date date){
		String newDateString=null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//Date date1= dateFormat.parse(dateString);
		
		//Date date2=DateUtil.getDateAddDay(date,days);
		newDateString=dateFormat.format(date);
		return newDateString;
	}
	/**
	 * 酒店订单详情
	 * 
	 * @return
	 */
	@Action("hotel_order_detail")
	public String hotelOrderDetail() {
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			param.put("orderId", orderId);
			Map<String, Object> resultMap = clientHotelService
					.orderDetail(param);
			if (resultMap != null) {
				JSONObject orderDetailResult = (JSONObject) resultMap
						.get("orderDetailResult");
				getRequest().setAttribute("orderDetailResult",
						orderDetailResult);
			}
			Date now=new Date();//今天
			Date tomorrow=DateUtil.getDateAddDay(now,1);//明天
			arrivalDate=getDateToString(now);
			departureDate=getDateToString(tomorrow);
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
			return "error";
		}

		return "hotel_order_detail";
	}

	/**
	 * 取消酒店订单
	 * 
	 * @return
	 */
	@Action("hotel_order_cancel")
	public void hotelOrderCancel() {
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			// 参数验证
			if (orderId == null || "".equals(orderId)) {
				throw new Exception("订单ID不可为空");
			}
			if (hotelOrderSign == null || "".equals(hotelOrderSign)) {
				throw new Exception("加密参数不用可为空");
			}
			param.put("orderId", orderId);
			param.put("sign", hotelOrderSign);
			param.put("time", hotelOrderTime);
			param.put("lvsessionid", lvsessionid);

			Map<String, Object> resultMap = clientHotelService
					.orderCancel(param);
			JSONObject mobileHotelRule = new JSONObject();
			if (resultMap != null) {
				int resultCode = (Integer) resultMap.get("resultCode");// 为0取消成功
				mobileHotelRule.put("resultCode", resultCode);
				mobileHotelRule.put("orderId", orderId);
				mobileHotelRule.put("cancelHotelOrderType",
						cancelHotelOrderType);
			}
			this.sendAjaxResult(mobileHotelRule.toString());
		} catch (Exception e) {
			e.printStackTrace();
			getRequest().setAttribute("msg", e.getMessage());
		}

	}

	/**
	 * 酒店填写订单页参数验证
	 * 
	 * @throws Exception
	 */
	public void paramsverify() throws Exception {
		Map<String, String> m = ClientConstants.getErrorInfo();
		// param.put("arrivalDate",arrivalDate );
		// param.put("departureDate", departureDate);
		// param.put("hotelId", hotelId);
		// param.put("roomTypeId", roomTypeId);
		// param.put("ratePlanId", ratePlanId);
		// param.put("numberOfRooms", numberOfRooms);

		if (hotelId == null || "".equals(hotelId)) {
			throw new Exception(m.get("invalidUserName"));
		}

		// 取票人姓名
		if (StringUtil.isEmptyString(userName)) {
			throw new Exception(m.get("invalidUserName"));
		}
		// 取票人手机
		if (StringUtil.isEmptyString(mobile)) {
			throw new Exception(m.get("invalidMobile"));
		}

	}

	/**
	 * 参数组装
	 * 
	 * @param param
	 * @return
	 */
	public Map<String, Object> paramsAssemble(Map<String, Object> param) {
		param.put("arrivalDate", arrivalDate);
		param.put("departureDate", departureDate);
		param.put("hotelId", hotelId);
		param.put("roomTypeId", roomTypeId);
		param.put("ratePlanId", ratePlanId);
		param.put("numberOfRooms", numberOfRooms);

		return param;
	}

	public void setMobileUserService(IClientUserService mobileUserService) {
		this.mobileUserService = mobileUserService;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public List<MobileBranchItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<MobileBranchItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getFirstChannel() {
		return firstChannel;
	}

	public void setFirstChannel(String firstChannel) {
		this.firstChannel = firstChannel;
	}

	public String getSecondChannel() {
		return secondChannel;
	}

	public void setSecondChannel(String secondChannel) {
		this.secondChannel = secondChannel;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isNeedIdCard() {
		return needIdCard;
	}

	public void setNeedIdCard(boolean needIdCard) {
		this.needIdCard = needIdCard;
	}

	public boolean isCanOrderToday() {
		return canOrderToday;
	}

	public void setCanOrderToday(boolean canOrderToday) {
		this.canOrderToday = canOrderToday;
	}

	public boolean isAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public void setMobileProductService(
			IClientProductService mobileProductService) {
		this.mobileProductService = mobileProductService;
	}

	public void setMobileOrderService(IClientOrderService mobileOrderService) {
		this.mobileOrderService = mobileOrderService;
	}

	public MobileCouponPoint getMobileCouponPoint() {
		return mobileCouponPoint;
	}

	public void setMobileCouponPoint(MobileCouponPoint mobileCouponPoint) {
		this.mobileCouponPoint = mobileCouponPoint;
	}

	public void setProductServiceProxy(
			ProductHeadQueryService productServiceProxy) {
		this.productServiceProxy = productServiceProxy;
	}

	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}

	public List<UsrReceivers> getReceiversList() {
		return receiversList;
	}

	public void setReceiversList(List<UsrReceivers> receiversList) {
		this.receiversList = receiversList;
	}

	public List<String> getFirstTravellerInfoOptions() {
		return firstTravellerInfoOptions;
	}

	public void setFirstTravellerInfoOptions(
			List<String> firstTravellerInfoOptions) {
		this.firstTravellerInfoOptions = firstTravellerInfoOptions;
	}

	public List<String> getTravellerInfoOptions() {
		return travellerInfoOptions;
	}

	public void setTravellerInfoOptions(List<String> travellerInfoOptions) {
		this.travellerInfoOptions = travellerInfoOptions;
	}

	public List<String> getContactInfoOptions() {
		return contactInfoOptions;
	}

	public void setContactInfoOptions(List<String> contactInfoOptions) {
		this.contactInfoOptions = contactInfoOptions;
	}

	public Long getBaoxianSelect() {
		return baoxianSelect;
	}

	public void setBaoxianSelect(Long baoxianSelect) {
		this.baoxianSelect = baoxianSelect;
	}

	public Long getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getBaoxianAmount() {
		return baoxianAmount;
	}

	public void setBaoxianAmount(int baoxianAmount) {
		this.baoxianAmount = baoxianAmount;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getPersonListItems() {
		return personListItems;
	}

	public void setPersonListItems(String personListItems) {
		this.personListItems = personListItems;
	}

	public void setMobileOtherService(IClientOtherService mobileOtherService) {
		this.mobileOtherService = mobileOtherService;
	}

	public MobilePersonItem getMpi() {
		return mpi;
	}

	public void setMpi(MobilePersonItem mpi) {
		this.mpi = mpi;
	}

	public String getPhysical() {
		return physical;
	}

	public void setPhysical(String physical) {
		this.physical = physical;
	}

	public Long getLocation() {
		return location;
	}

	public void setLocation(Long location) {
		this.location = location;
	}

	public WorkOrderService getWorkOrderService() {
		return workOrderService;
	}

	public void setWorkOrderService(WorkOrderService workOrderService) {
		this.workOrderService = workOrderService;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public WorkOrderTypeService getWorkOrderTypeService() {
		return workOrderTypeService;
	}

	public void setWorkOrderTypeService(
			WorkOrderTypeService workOrderTypeService) {
		this.workOrderTypeService = workOrderTypeService;
	}

	public List<OrderDataItem> getOrderDataList() {
		return orderDataList;
	}

	public void setOrderDataList(List<OrderDataItem> orderDataList) {
		this.orderDataList = orderDataList;
	}

	public WorkTaskService getWorkTaskService() {
		return workTaskService;
	}

	public void setWorkTaskService(WorkTaskService workTaskService) {
		this.workTaskService = workTaskService;
	}

	public WorkGroupService getWorkGroupService() {
		return workGroupService;
	}

	public void setWorkGroupService(WorkGroupService workGroupService) {
		this.workGroupService = workGroupService;
	}

	public Long getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public IClientHotelService getClientHotelService() {
		return clientHotelService;
	}

	public void setClientHotelService(IClientHotelService clientHotelService) {
		this.clientHotelService = clientHotelService;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(String ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public String getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(String numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public String getHotelOrderSign() {
		return hotelOrderSign;
	}

	public void setHotelOrderSign(String hotelOrderSign) {
		this.hotelOrderSign = hotelOrderSign;
	}

	public String getHotelOrderTime() {
		return hotelOrderTime;
	}

	public void setHotelOrderTime(String hotelOrderTime) {
		this.hotelOrderTime = hotelOrderTime;
	}

	public String getLvsessionid() {
		return lvsessionid;
	}

	public void setLvsessionid(String lvsessionid) {
		this.lvsessionid = lvsessionid;
	}

	public String getCancelHotelOrderType() {
		return cancelHotelOrderType;
	}

	public void setCancelHotelOrderType(String cancelHotelOrderType) {
		this.cancelHotelOrderType = cancelHotelOrderType;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getHotelTypeName() {
		return hotelTypeName;
	}

	public void setHotelTypeName(String hotelTypeName) {
		this.hotelTypeName = hotelTypeName;
	}

	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String getNumberOfCustomers() {
		return numberOfCustomers;
	}

	public void setNumberOfCustomers(String numberOfCustomers) {
		this.numberOfCustomers = numberOfCustomers;
	}

	public String getCustomerIPAddress() {
		return customerIPAddress;
	}

	public void setCustomerIPAddress(String customerIPAddress) {
		this.customerIPAddress = customerIPAddress;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getCustomerNames() {
		return customerNames;
	}

	public void setCustomerNames(String customerNames) {
		this.customerNames = customerNames;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getGuaranteePrice() {
		return guaranteePrice;
	}

	public void setGuaranteePrice(String guaranteePrice) {
		this.guaranteePrice = guaranteePrice;
	}
	public String getEcontractEmail() {
		return econtractEmail;
	}

	public void setEcontractEmail(String econtractEmail) {
		this.econtractEmail = econtractEmail;
	}

	public ProdProductBranchService getProdProductBranchService() {
		return prodProductBranchService;
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}
	
}
