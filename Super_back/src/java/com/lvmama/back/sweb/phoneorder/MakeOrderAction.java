package com.lvmama.back.sweb.phoneorder;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.ord.*;
import com.lvmama.comm.bee.po.prod.*;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.ord.OrderResourceService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.ord.OversoldException;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductRelationService;
import com.lvmama.comm.bee.service.prod.ProdProductRoyaltyService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Coupon;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.bee.vo.ord.Invoice;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.bee.vo.ord.PriceInfo;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.favor.FavorResult;
import com.lvmama.comm.pet.vo.favor.strategy.OrderFavorStrategyForFifthAnniversary;
import com.lvmama.comm.pet.vo.mark.ValidateCodeInfo;
import com.lvmama.comm.utils.*;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.ord.RouteUtil;
import com.lvmama.comm.utils.ord.TimePriceUtil;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderSenderBiz;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author shihui
 * 
 *         后台下单详细页操作
 * */
@ParentPackage("json-default")
@Results({
		@Result(name = "makeorder", location = "/WEB-INF/pages/back/phoneorder/makeorder.jsp"),
		@Result(name = "memo_list", location = "/WEB-INF/pages/back/phoneorder/memo_list.jsp"),
		@Result(name = "check_error", location = "/WEB-INF/pages/back/phoneorder/checkerror.jsp"),
		@Result(name = "invoice_list", location = "/WEB-INF/pages/back/phoneorder/invoice_list.jsp"),
		@Result(name = "address_list", location = "/WEB-INF/pages/back/phoneorder/address_list.jsp"),
		@Result(name = "receivers_list", location = "/WEB-INF/pages/back/phoneorder/receivers_list.jsp"),
		@Result(name = "receivers", location = "/WEB-INF/pages/back/phoneorder/receivers.jsp"),
		@Result(name = "emergency_list", location = "/WEB-INF/pages/back/phoneorder/emergency_list.jsp"),
		@Result(name = "visitor_list", location = "/WEB-INF/pages/back/phoneorder/visitor_list.jsp"),
		@Result(name = "youhui", location = "/WEB-INF/pages/back/phoneorder/youhui.jsp"),
		@Result(name = "order_amount", location = "/WEB-INF/pages/back/phoneorder/order_amount.jsp"),
		@Result(name = "callinfo", location = "/WEB-INF/pages/back/phoneorder/callinfo.jsp"),
		@Result(name = "add_address", location = "/WEB-INF/pages/back/phoneorder/add_address.jsp"),
		@Result(name = "toubao", location = "/WEB-INF/pages/back/phoneorder/toubao.jsp"),
		@Result(name = "add_receiver", location = "/WEB-INF/pages/back/phoneorder/add_receiver.jsp"),
		@Result(name = "add_invoice", location = "/WEB-INF/pages/back/phoneorder/add_invoice.jsp"),
		@Result(name = "user_list", location = "/WEB-INF/pages/back/phoneorder/user_list.jsp"),
		@Result(name = "edit_user", location = "/WEB-INF/pages/back/phoneorder/edit_user.jsp"),
		@Result(name = "businessCoupon", location = "/WEB-INF/pages/back/phoneorder/businessCoupon.jsp") })
public class MakeOrderAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4667419431418441571L;
	
	private final String CART = "CART";

	private Map<String, String> prodBranchItemMap = new HashMap<String, String>();

	private OrdOrderMemo oomemo;

	public CashAccountVO getMoneyAccount() {
		return moneyAccount;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	private OrdInvoice oinvoice;

	private List<OrdOrderMemo> memoList;

	private Map<UsrReceivers, String> visitorList;

	private UsrReceivers emergencyContact;

	private List<ProdAssemblyPoint> prodAssemblyPointList; // 上车地点

	private String id; // 接受关于id操作的参数

	private PlaceCityService placeCityService;

	private List<ComCity> cityList = new ArrayList<ComCity>();

	private UsrReceivers usrReceivers, addressReceiver, newReceiver;

	private IReceiverUserService receiverUserService;

	private List<UsrReceivers> usrReceiversList, addressReceiverList;

	private List<MarkCoupon> partyCouponList;

	private String paramsStr;

	private MarkCouponService markCouponService;

	private boolean showYouHui = true;

	private Long productId;

	private Date visitDate;

	private Date leaveDate;

	private ProdProductRelationService prodProductRelationService;

	private ProdProduct product;

	private ProdProductBranchService prodProductBranchService;

	private BCertificateTargetService bCertificateTargetService;
    private SmsRemoteService smsRemoteService;
	private String resourceConfirm;

	private String needRedail;

	private SettlementTargetService settlementTargetService;

	private String payTarget;

	private String paymentWaitTime;

	private String orderChannel;

	private Long quantity;

	private String itemType;

	private Map<ProdProductBranch, Long> prodBranchItemList = new HashMap<ProdProductBranch, Long>();

	private Map<ProdProductRelation, Long> relationProductItemList = new HashMap<ProdProductRelation, Long>();

	private MetaProductService metaProductService;

	private String code;

	private HashMap<String, String> info;

	private OrderService orderServiceProxy;

	private String needInvoice;

	private String fapiaoReceiverId;

	private String shitipiaoReceiverId;

	private OrdOrder order;

	private String lakalaURL;

	private TopicMessageProducer orderMessageProducer;

	private boolean haveMoblie;

	private boolean havePaymentPassword;

	private CashAccountVO moneyAccount;

	private CashAccountService cashAccountService;

	private boolean canAccountPay;

	private String to;

	private String routeCode;

	private String brithday;

	private boolean testOrder = false;

	private UserUser user;

	private UserUserProxy userUserProxy;

	private UserClient userClient;

	private String mobileNumber;

	private String isTaobao;

	private String userName;

	private List<UserUser> userList;

	private String userId;
	// 将ValidateBusinessCouponInfoList转成JSON对象传到前台
	private JSONArray validateBusinessCouponInfoJSON;

	// private TravelDescriptionService travelDescriptionService;
	private ProdProductService prodProductService;
	// private EContractClient contractClient;
	// private FSClient fsClient;
	private FavorService favorService;
	private WorkOrderSenderBiz workOrderProxy;

	private static Log logger = LogFactory.getLog(MakeOrderAction.class);

	private Long orderId;
	private String message;
	private String ids;
	/**
	 * 订单是否能进行资金转移
	 */
	private boolean canTransfer;

	private ViewPageService viewPageService;

	private ViewPage viewPage;

	private String lastModifyTime;

	private boolean cardTypeRequired = false;

	private ComLogService comLogService;

	private ProdProductRoyaltyService prodProductRoyaltyService;

	private OrderResourceService orderResourceService;
    /**
     * 退款说明
     */
    private String refundSexplanation;
    /**
     * 短信信息
     */
    private  String smsInfo;
	/**
	 * 临时关闭存款账户支付
	 */
	private Boolean tempCloseCashAccountPay=false;

	
	private boolean supplierProduct = false;

    private Map<String, Object> jsonMap = new HashMap<String, Object>();
    private String emergencyContactId;

    public String getEmergencyContactId() {
        return emergencyContactId;
    }

    public void setEmergencyContactId(String emergencyContactId) {
        this.emergencyContactId = emergencyContactId;
    }

    @Action(value = "/stressTest/createOrder",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String create() {
        try {

            toMakeorderPage();
            doOperateUsrReceiversList();
//            checkTravellerInfoOptions();
            doOperateVisitorList();

            id=emergencyContactId;

            doOperateEmergencyContactList();
//            checkOrder();

            doSaveOrder();

            jsonMap.put("status", SUCCESS);
//            jsonMap.put("cart", getSession().getAttribute("CART"));
            jsonMap.put("orderId", order.getOrderId());

        } catch (Exception e) {
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    public Map<String, Object> getJsonMap() {
        return jsonMap;
    }

    public void setJsonMap(Map<String, Object> jsonMap) {
        this.jsonMap = jsonMap;
    }

    /**
	 * 是否EBK订单
	 */
	/**
	 * 取时间价格表传过来的所有的类别和数量
	 * */
	private Map<Long, Long> getBrodBranchItemList() {
		Map<Long, Long> map = new HashMap<Long, Long>();
		Set<String> keySet = prodBranchItemMap.keySet();
		for (String string : keySet) {
			if (string.matches("^branch_[0-9]+$")) {
				Long branchId = Long.valueOf(string.substring(7));
				map.put(branchId, Long.valueOf(prodBranchItemMap.get(string)));
			}
		}
		return map;
	}

	/**
	 * 酒店，计算出入住天数
	 * */
	private int getDays(Date visitDate, Date leaveDate) {
		return (int) ((leaveDate.getTime() - visitDate.getTime()) / 24 / 60 / 60 / 1000);
	}

	/**
	 * 保存搜索条件，生成form
	 * */
	private String createForm(String paramsStr) {
		try {
			paramsStr = URLDecoder.decode(paramsStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringBuffer strBuf = new StringBuffer();
		String action = "/super_back/phoneOrder/index.do";
		if (orderChannel != null) {
			action = "/super_back/ordChannel/index.do";
		}
		strBuf.append("<form  id=\"indexForm\" action=\"" + action + "\" method=\"post\">");
		if (orderChannel != null) {
			strBuf.append("<input type=\"hidden\" name=\"orderChannel\" value=\""
					+ orderChannel + "\"/>");
		}
		String[] strs = paramsStr.split("&");
		String[] map = new String[2];
		for (String str : strs) {
			map = str.split("=");
			if (map.length == 2) {
				strBuf.append("<input type=\"hidden\" name=\"" + map[0]
						+ "\" value=\"" + map[1] + "\"/>");
			} else {
				strBuf.append("<input type=\"hidden\" name=\"" + map[0]
						+ "\" value=\"\"/>");
			}
		}
		strBuf.append("</form>");
		return strBuf.toString();
	}

	/**
	 * 跳转到下单详细页
	 * @throws Exception 
	 */
	@Action(value = "/phoneOrder/makeorder")
	public String toMakeorderPage() {
		long insuranceCount = 0l;
		getSession().removeAttribute(CART);
		Cart cart = new Cart();
		if (orderChannel != null) {
			cart.setOrderChannel(orderChannel);
		}
		cart.setProductId(productId);
		Map<Long, Long> ids = getBrodBranchItemList();
		if (productId != null) {
			product = this.prodProductService.getProdProduct(productId);
			viewPage = viewPageService.getViewPage(productId);
			cart.setProduct(product);
			List<ProdProductBranch> productBranchList = new ArrayList<ProdProductBranch>();
			// 如果是不定期产品
			if (product.IsAperiodic()) {
				Iterator<Long> it = ids.keySet().iterator();
				while (it.hasNext()) {
					ProdProductBranch branch = prodProductBranchService
							.selectProdProductBranchByPK(it.next());
					if (branch.getValidBeginTime() != null
							&& branch.getValidEndTime() != null
							&& !DateUtil.getDayStart(new Date()).after(
									branch.getValidEndTime())) {
						ProdProductBranch prodProductBranch = prodProductBranchService
								.getProductBranchDetailByBranchId(
										branch.getProdBranchId(),
										branch.getValidEndTime(), !testOrder);
						if (prodProductBranch != null) {
							productBranchList.add(prodProductBranch);
						}
					}
				}
			} else {
				cart.setVisitDate(visitDate);
				cart.setLeaveDate(leaveDate);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("productId", productId);
				params.put("visitTime", visitDate);
				params.put("onLine", !testOrder);
				// 酒店只查询出主类别信息,门票和线路查出所有的正常类别
				if (product.isSingleRoom()) {
					ProdProductBranch branch = null;
					// 单房型显示入住到离店的平均价
					List<OrdTimeInfo> timeInfoList = new ArrayList<OrdTimeInfo>();
					Long totalPrice = 0L, totalMarketPrice = 0L;
					// 入住天数
					int days = getDays(visitDate, leaveDate);
					long stock = 0;
					// 计算出每天的价格、库存信息
					for (int i = 0; i < days; i++) {
						Date d = DateUtils.addDays(visitDate, i);
						ProdProductBranch b = prodProductBranchService
								.getProductBranchDetailByBranchId(ids.keySet()
										.iterator().next(), d, !testOrder);
						// 取入住当天的类别信息
						if (i == 0) {
							branch = b;
							stock = b.getStock();
						} else {
							// 取入住期间最小的库存
							if (b.getStock() < stock) {
								stock = b.getStock();
							}
						}
						// 入住期间的销售总价和市场总价
						totalPrice += b.getSellPrice();
						totalMarketPrice += b.getMarketPrice();
						// 保存每天的时间价格信息
						OrdTimeInfo timeInfo = new OrdTimeInfo();
						timeInfo.setProductId(productId);
						timeInfo.setVisitTime(d);
						timeInfo.setMarketPrice(b.getMarketPrice());
						timeInfo.setSellPrice(b.getSellPrice());
						timeInfo.setProductBranchId(b.getProdBranchId());
						timeInfo.setQuantity(ids.get(b.getProdBranchId()));
						timeInfoList.add(timeInfo);
					}
					cart.setSingleSellPriceTotal(totalPrice);
					cart.setSingleMarketPriceTotal(totalMarketPrice);
					cart.setTimeInfoList(timeInfoList);
					// 入住期间最小的库存
					branch.setStock(stock);
					// 入住期间的平均价
					branch.setSellPrice(totalPrice / days);
					branch.setMarketPrice(totalMarketPrice / days);
					productBranchList.add(branch);
				} else if (product.isTraffic()) {
					Long prodBranchId = ids.keySet().iterator().next();
					ProdProductBranch branch = prodProductBranchService
							.getProductBranchDetailByBranchId(prodBranchId,
									visitDate, !testOrder);
					productBranchList = new ArrayList<ProdProductBranch>();
					productBranchList.add(branch);
				} else {
					// 查询出正常类别产品列表，并且将时间价格表页面输入的数量赋进去
					productBranchList = prodProductBranchService
							.getProductBranchDetailByProductId(params);
				}
				// 生成团号
				if (product.isRoute()) {
					boolean flag = RouteUtil.hasTravelGroupProduct(product);
					if (flag) {
						routeCode = RouteUtil.makeTravelGroupCode(product,
								visitDate);
					}
				}
				// 最晚取消时间
				lastModifyTime = getLastCancelHourF();
			}
			// 将产品和数量放到session里
			for (int i = 0; i < productBranchList.size(); i++) {
				ProdProductBranch prodBranch = productBranchList.get(i);
				if (prodBranch.getMarketPrice() == null
						|| prodBranch.getSellPrice() == null
						|| prodBranch.getStock() == 0) {
				} else {
					if (product.isOther()) {
						if (ids.containsKey(prodBranch.getProdBranchId())) {
							prodBranchItemList.put(prodBranch,
									ids.get(prodBranch.getProdBranchId()));
							// 取得保险附加产品初始化数量
							insuranceCount = insuranceCount
									+ (prodBranch.getAdultQuantity() + prodBranch
											.getChildQuantity())
									* ids.get(prodBranch.getProdBranchId());
						} else {
							prodBranchItemList.put(prodBranch, 0L);
						}
					} else {
						if (prodBranch.getAdditional().equals("false")) {
							if (ids.containsKey(prodBranch.getProdBranchId())) {
								prodBranchItemList.put(prodBranch,
										ids.get(prodBranch.getProdBranchId()));
								// 取得保险附加产品初始化数量
								insuranceCount = insuranceCount
										+ (prodBranch.getAdultQuantity() + prodBranch
												.getChildQuantity())
										* ids.get(prodBranch.getProdBranchId());
							} else {
								prodBranchItemList.put(prodBranch, 0L);
							}
						}
					}
				}
			}
			cart.setProdBranchItemList(prodBranchItemList);

			//判断是否是第三方渠道订单
			supplierProduct = checkIsSupplierProduct(productBranchList);
			// add by taiqichao 计算最晚修改或取消时间
			// this.hasEbkOrder = checkIsEbkProduct(productBranchList);
			// 不定期暂不做附加产品
			if (!product.isOther() && !product.IsAperiodic()) {
				// 查出所有附加类别和产品
				List<ProdProductRelation> relationProductList = prodProductRelationService
						.getRelationProductsByProductId(productId, visitDate);
				for (int i = 0; i < relationProductList.size(); i++) {
					ProdProductRelation relation = relationProductList.get(i);
					if (ids.containsKey(relation.getProdBranchId())) {
						relationProductItemList.put(relation,
								ids.get(relation.getProdBranchId()));
					} else {
						// qjh
						if ("保险".equals(relation.getSubProductTypeStr())
								&& "ALL".equals(relation.getSaleNumType())) {
							relationProductItemList.put(relation,
									insuranceCount);
						} else {
							relationProductItemList.put(relation, 0L);
						}
					}
				}
			}
			prodAssemblyPointList = prodProductService.queryAssembly(productId);
			cart.setRelationProductList(relationProductItemList);
			if (this.product.isPaymentToLvmama()) {
				payTarget = Constant.PAYMENT_TARGET.TOLVMAMA.name();
			} else if (this.product.isPaymentToSupplier()) {
				payTarget = Constant.PAYMENT_TARGET.TOSUPPLIER.name();
			} else {
				payTarget = Constant.PAYMENT_TARGET.TOLVMAMA.name();
			}
			cart.setPaymentTarget(payTarget);
		}
		setCart(cart);

		if (paramsStr != null) {
			paramsStr = createForm(paramsStr);
		}

		// 测试下单,取默认的测试专用用户
		if (testOrder) {
			user = userUserProxy.getUsersByMobOrNameOrEmailOrCard("测试专用");
			userId = user.getUserId();
			cart.setUserId(user.getUserId());
			doOperateSelectUsrReceivers();
			if ("true".equals(product.getPhysical())) {
				doOperateAddressList();
			}
		} else {
			// 复制订单,取原订单用户信息,并加载对应数据
			if (orderId != null && orderId != 0l) {
				cart.setOldOrderId(orderId);
				order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
				userName = order.getUserName();
				userId = order.getUserId();
				cart.setUserId(userId);
				doOperateSelectUsrReceivers();
				doOperateUsrReceiversList();
				doOperateEmergencyContactList();
				doOperateVisitorList();
				if ("true".equals(product.getPhysical())) {
					doOperateAddressList();
				}
			} else {
				// 从LVCC过来的用户信息,呼叫中心
				UserUser usr = readUserCookie();
				userId = usr.getUserId();
				if (!StringUtils.isEmpty(userId)) {
					cart.setUserId(userId);
					userName = usr.getUserName();
					doOperateSelectUsrReceivers();
					if ("true".equals(product.getPhysical())) {
						doOperateAddressList();
					}
					clearUserCookie();
				}
			}
		}
		doOperateInvoiceList();
		doOperateMemoList();
		allMarkCoupon();
		countOrderAmount();
		businessCoupon();
		// 五周年活动不适用优惠券
		if (ActivityUtil.getInstance().checkActivityIsValid(
				Constant.ACTIVITY_FIVE_YEAR)) {
			product.setCouponAble("false");
			product.setCouponActivity("false");
		}
		return "makeorder";
	}

	private boolean checkIsSupplierProduct(
			List<ProdProductBranch> productBranchList) {
		for (ProdProductBranch ppb : productBranchList) {
			List<ProdProductBranchItem> items = this.prodProductBranchService
					.selectBranchItemByBranchId(ppb.getProdBranchId());
			for (ProdProductBranchItem item : items) {
				MetaProduct metaProduct = this.metaProductService
						.getMetaProduct(item.getMetaProductId());
				if (StringUtil.isNotEmptyString(metaProduct.getSupplierChannel())) {
					return true;
				}
			}
		}
		return false;
	}

	// 计算最晚修改或取消时间
	private String getLastCancelHourF() {
		Cart cart = getCart();
		if (cart == null) {
			return null;
		}
		Map<ProdProductBranch, Long> branchList = cart.getProdBranchItemList();
		if (branchList == null || branchList.size() < 1) {
			return null;
		}
		Date visitDate = cart.getVisitDate();
		if (visitDate == null) {
			return null;
		}
		List<Date> dates = new ArrayList<Date>();
		for (ProdProductBranch pp : branchList.keySet()) {
			// 不是附加产品并且数量不为空,则判断该类别的最晚取消时间
			if (pp.getAdditional().equals("false") && branchList.get(pp) != 0L) {
				ProdProduct product = cart.getProduct();
				if (product == null) {
					return null;
				}
				// 单房型
				if (product.isHotel() && product.isSingleRoom()) {
					Date leaveDate = cart.getLeaveDate();
					if (leaveDate == null) {
						return null;
					}
					int days = DateUtil.getDaysBetween(visitDate, leaveDate);
					for (int i = 0; i < days; i++) {
						Date date = DateUtil.dsDay_Date(visitDate, i);
						TimePrice timePrice = prodProductBranchService
								.getProdTimePrice(pp.getProdBranchId(), date);
						if (timePrice == null) {
							return null;
						}
						String stragtegy = timePrice.getCancelStrategy();
						if (StringUtil.isEmptyString(stragtegy)) {
							return null;
						}
						if (!timePrice.isAble()) { // 不为可退改
							return null;
						}
						Long cHour = timePrice.getCancelHour();
						if (cHour == null) {
							return null;
						}
						Date mod = DateUtil.DsDay_Minute(visitDate,
								-cHour.intValue());
						dates.add(mod);
					}
				} else {
					TimePrice timePrice = prodProductBranchService
							.getProdTimePrice(pp.getProdBranchId(), visitDate);
					if (timePrice == null) {
						return null;
					}
					String stragtegy = timePrice.getCancelStrategy();
					if (StringUtil.isEmptyString(stragtegy)) {
						return null;
					}
					if (!timePrice.isAble()) { // 不为可退改
						return null;
					}
					Long cHour = timePrice.getCancelHour();
					if (cHour == null) {
						return null;
					}
					Date mod = DateUtil.DsDay_Minute(visitDate,
							-cHour.intValue());
					dates.add(mod);
				}
			}
		}
		return DateUtil.formatDate(Collections.min(dates),
				"yyyy-MM-dd HH:mm:ss");
	}

	private boolean checkIsEbkProduct(List<ProdProductBranch> productBranchList) {
		for (ProdProductBranch ppb : productBranchList) {
			List<ProdProductBranchItem> items = this.prodProductBranchService
					.selectBranchItemByBranchId(ppb.getProdBranchId());
			for (ProdProductBranchItem item : items) {
				MetaProduct metaProduct = this.metaProductService
						.getMetaProduct(item.getMetaProductId());
				if (Constant.PRODUCT_TYPE.HOTEL.name().equals(
						metaProduct.getProductType())) {
					List<SupBCertificateTarget> btargetList = this.bCertificateTargetService
							.selectSuperMetaBCertificateByMetaProductId(item
									.getMetaProductId());
					for (SupBCertificateTarget b : btargetList) {
						if ("true".equals(b.getSupplierFlag())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 清空cookie
	 * */
	private void clearUserCookie() {
		HttpServletResponse res = getResponse();
		Cookie userIdCookie = new Cookie("phone_order_userId", "");
		userIdCookie.setPath("/");
		userIdCookie.setMaxAge(-1);
		res.addCookie(userIdCookie);
		Cookie userNameCookie = new Cookie("phone_order_userName", "");
		userNameCookie.setPath("/");
		userNameCookie.setMaxAge(-1);
		res.addCookie(userNameCookie);
	}

	/**
	 * 从cookie中读取用户信息
	 * */
	private UserUser readUserCookie() {
		UserUser user = new UserUser();
		HttpServletRequest request = getRequest();
		Cookie userIdCookie = ServletUtil.getCookie(request,
				"phone_order_userId");
		if (userIdCookie != null
				&& StringUtils.isNotEmpty(userIdCookie.getValue())) {
			user.setUserId(userIdCookie.getValue());
		}
		Cookie userNameCookie = ServletUtil.getCookie(request,
				"phone_order_userName");
		if (userNameCookie != null
				&& StringUtils.isNotEmpty(userNameCookie.getValue())) {
			try {
				user.setUserName(java.net.URLDecoder.decode(
						userNameCookie.getValue(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	/**
	 * 获取LVCC信息，cookie
	 * */
	@Action(value = "/phoneOrder/doGetLVCC")
	public void doGetLVCC() {
		JSONResult result = new JSONResult();
		Cart cart = getCart();
		if (cart != null) {
			try {
				// 从LVCC过来的用户信息,呼叫中心
				UserUser usr = readUserCookie();
				userId = usr.getUserId();
				if (!StringUtils.isEmpty(userId)) {
					cart.setUserId(userId);
					cart.setVisitorList(null);
					cart.setUsrReceiver(null);
					cart.setEmergencyContact(null);
					userName = usr.getUserName();
					if (!StringUtils.isEmpty(userName)) {
						result.put("userId", userId);
						result.put("userName", userName);
					}
				} else {
					result.put("info", "无法获取LVCC中的用户信息，请手动查询！");
				}
			} catch (Exception ex) {
				result.raise(ex);
			}
		} else {
			result.raise(new Exception("购物车异常!"));
		}
		result.output(getResponse());
	}

	/**
	 * 用户查询
	 * */
	@Action(value = "/phoneOrder/queryUser")
	public String queryUser() {
		HashMap<String, Object> param = new HashMap<String, Object>();
		if (userName != null && !"".equals(userName)) {
			param.put("search", userName);
		}
		param.put("_startRow", "0");
		param.put("_endRow", "10");
		this.userList = userUserProxy.getUsers(param);
		return "user_list";
	}

	/**
	 * 渠道注册
	 * */
	@Action(value = "/phoneOrder/doChannelRegist")
	public void doChannelRegist() {
		JSONResult result = new JSONResult();
		String info = "";
		if (mobileNumber != null) {
			if (com.lvmama.back.utils.StringUtil.isMobileNO(mobileNumber)) {
				if (mobileNumber.charAt(0) == '0') {
					mobileNumber = mobileNumber.substring(1,
							mobileNumber.length());
				}
				try {
					String smsContent = "亲，感谢您惠顾驴妈妈淘宝商城！正在为您处理订单，请耐心等待。注意及时查收短信。客服电话：021-60561630. 祝您旅途愉快。";
					Long userId = userClient.batchRegisterWithChannel(
							mobileNumber, null, null, null, smsContent, null,
							null, isTaobao);
					if (userId != null) {
						info = "手机号:" + mobileNumber + "成功注册并添加至淘宝渠道！";
					} else {
						info = "手机号:" + mobileNumber + "已经是驴妈妈会员！";
					}
				} catch (Exception e) {
					result.raise(e);
				}
			} else {
				info = "警告：会员注册失败,请输入合法的手机号！";
			}
		} else {
			info = "手机号为空！";
		}
		result.put("info", info);
		result.output(getResponse());
	}

	@Action(value = "/phoneOrder/toEditUser")
	public String toEditUser() {
		if (StringUtils.isNotEmpty(userId)) {
			user = userUserProxy.getUserUserByUserNo(userId);
		}
		return "edit_user";
	}

	@Action(value = "/phoneOrder/doEditUser")
	public void doEditUser() {
		// JSONResult result = new JSONResult();
		// if (user != null) {
		// String userId = user.getUserId();
		// String mobile = user.getMobileNumber();
		// boolean flag =
		// userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE, mobile);
		// UserUser u = userUserProxy.getUserUserByUserNo(userId);
		// UsrMobileCodes umcc = userService.selectByMobile(mobile);
		// String sms1 = "您好！欢迎您成为网站认证会员，认证验证码：";
		// String sms2 = "。客服电话：1010-6060。我们将竭诚为您服务！";
		// if (code == null || "".equals(code)) {
		// if (mobile.equals(u.getMobileNumber()) &&
		// !u.getIsMobileChecked().equalsIgnoreCase("Y") || !flag) {
		// if (umc == null) {
		// UUIDGenerator gen = new UUIDGenerator();
		// umc = new UsrMobileCodes();
		// String mobileCode = StringUtil.getRandomString(0, 6);
		// umc.setMcId(gen.generate().toString());
		// umc.setCreatedDate(new Date());
		// umc.setMobileNumber(mobile);
		// umc.setMobileCode(mobileCode);
		// umc.setUserId(userId);
		// userService.insertMobileCodes(umc);
		// try {
		// smsRemoteService.sendSms(sms1 + mobileCode + sms2, mobile);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// } else {
		// String mobileCode = StringUtil.getRandomString(0, 6);
		// umc.setMobileCode(mobileCode);
		// umc.setCreatedDate(new Date());
		// umc.setUserId(userId);
		// userService.updateMobileCodes(umc);
		// try {
		// smsRemoteService.sendSms(sms1 + mobileCode + sms2, mobile);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// result.put("jsonMsg", "mobile_changed");
		// } else {
		// result.put("jsonMsg", "mobile_used");
		// }
		// } else {
		// if (umc == null) {
		// result.put("jsonMsg", "code_error");
		// } else if (!code.equals(umc.getMobileCode())) {
		// result.put("jsonMsg", "code_error");
		// } else {
		// userService.updateMobileNumber(userId, mobile);
		// }
		// }
		// }
		// result.output(getResponse());
	}

	/**
	 * 选中用户
	 * */
	@Action(value = "/phoneOrder/doConfirmUser")
	public void doConfirmUser() {
		JSONResult result = new JSONResult();
		Cart cart = getCart();
		if (cart != null) {
			try {
				if (!StringUtils.isEmpty(userId)) {
					cart.setUserId(userId);
					cart.setVisitorList(null);
					cart.setUsrReceiver(null);
					cart.setEmergencyContact(null);
				}
			} catch (Exception ex) {
				result.raise(ex);
			}
		} else {
			result.raise(new Exception("购物车异常!"));
		}
		result.output(getResponse());
	}

	/**
	 * 备注信息操作
	 * */
	@Action(value = "/phoneOrder/doOperateMemoList")
	public String doOperateMemoList() {
		Cart cart = getCart();
		if (cart != null) {
			memoList = cart.getMemoList();
			if (memoList == null) {
				memoList = new ArrayList<OrdOrderMemo>();
			}
			// 订单重下
			if (orderId != null && orderId != 0l && order != null) {
				memoList = orderServiceProxy.queryMemoByOrderId(orderId);
				for (int i = 0; i < memoList.size(); i++) {
					UUIDGenerator gen = new UUIDGenerator();
					memoList.get(i).setUuid(gen.generate().toString());
				}
			} else {
				if (oomemo != null) {
					// 新增
					UUIDGenerator gen = new UUIDGenerator();
					oomemo.setUuid(gen.generate().toString());
					oomemo.setOperatorName(getOperatorName());
					if (oomemo.hasUserMemo()) {
						oomemo.setStatus("false");
					}
					memoList.add(oomemo);
				} else if (oomemo == null && id != null && !"".equals(id)) {
					// 删除
					for (int i = 0; i < memoList.size(); i++) {
						OrdOrderMemo memo = memoList.get(i);
						if (memo.getUuid().equals(id)) {
							memoList.remove(i);
							break;
						}
					}
				}
			}
			cart.setMemoList(memoList);
			setCart(cart);
		}
		return "memo_list";
	}

	/**
	 * 发票信息操作
	 * */
	@Action(value = "/phoneOrder/doOperateInvoiceList")
	public String doOperateInvoiceList() {
		Cart cart = getCart();
		if (cart != null) {
			if (oinvoice != null) {
				// 新增
				oinvoice.setStatus(Constant.INVOICE_STATUS.UNBILL.name());
				oinvoice.setUserId(getOperatorId());
				cart.setOrdInvoice(oinvoice);
			} else if (oinvoice == null && id != null && !"".equals(id)) {
				// 修改
				if (id.equals("update")) {

				} else {
					// 删除
					cart.setOrdInvoice(null);
					oinvoice = null;
				}
			} else {
				oinvoice = cart.getOrdInvoice();
			}
			setCart(cart);
		}
		return "invoice_list";
	}

	/**
	 * 地址列表操作
	 * */
	@Action(value = "/phoneOrder/doOperateAddressList")
	public String doOperateAddressList() {
		Cart cart = getCart();
		if (cart != null) {
			if (addressReceiver != null) {
				// 新增
				String province = addressReceiver.getProvince();
				String city = addressReceiver.getCity();
				if (StringUtils.isNotEmpty(province)) {// 用编号转名字
					ComProvince cp = placeCityService
							.selectByPrimaryKey(province);
					if (cp != null) {
						addressReceiver.setProvince(cp.getProvinceName());
						ComCity cc = placeCityService
								.selectCityByPrimaryKey(city);
						if (cc != null) {
							addressReceiver.setCity(cc.getCityName());
						}
					}
				}
				// 修改
				if (id != null && !"".equals(id)) {
					UsrReceivers rec = this.receiverUserService
							.getRecieverByPk(id);
					rec.setProvince(this.addressReceiver.getProvince());
					rec.setCity(this.addressReceiver.getCity());
					rec.setAddress(this.addressReceiver.getAddress());
					rec.setReceiverName(this.addressReceiver.getReceiverName());
					rec.setMobileNumber(this.addressReceiver.getMobileNumber());
					rec.setPostCode(this.addressReceiver.getPostCode());
					this.receiverUserService.update(rec);
				} else {
					UUIDGenerator gen = new UUIDGenerator();
					this.addressReceiver.setReceiverId(gen.generate()
							.toString());
					this.addressReceiver.setIsValid("Y");
					this.addressReceiver.setCreatedDate(new Date());
					this.addressReceiver.setUserId(cart.getUserId());
					this.addressReceiver
							.setReceiversType(Constant.ORD_PERSON_TYPE.ADDRESS
									.name());
					this.receiverUserService.insert(addressReceiver);
				}
				// 删除
			} else if (addressReceiver == null && id != null && !"".equals(id)) {
				this.receiverUserService.delete(id);
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", cart.getUserId());
			params.put("receiversType", Constant.RECEIVERS_TYPE.ADDRESS.name());
			this.product = cart.getProduct();
			this.addressReceiverList = receiverUserService
					.loadRecieverByParams(params);
		}
		return "address_list";
	}

	/**
	 * 联系人列表操作
	 * */
	@Action(value = "/phoneOrder/doOperateUsrReceiversList")
	public String doOperateUsrReceiversList() {
		Cart cart = getCart();
		if (cart != null) {
			// 订单重下
			if (orderId != null && orderId != 0l && order != null) {
				OrdPerson op = order.getContact();
				if (op != null) {
					// 前台下单receiverId为空,直接取ord_person数据
					String rid = op.getReceiverId();
					if (StringUtils.isEmpty(rid)) {
						usrReceivers = convertToUsrReceiver(op);
					} else {
						usrReceivers = receiverUserService.getRecieverByPk(rid);
					}
					cart.setUsrReceiver(usrReceivers);
				}
			} else {
				if (id != null && !"".equals(id)) {
					usrReceivers = receiverUserService.getRecieverByPk(id);
					cart.setUsrReceiver(usrReceivers);
				}
			}
			setCart(cart);
		}
		return "receivers_list";
	}

	/**
	 * 紧急联系人列表操作
	 * */
	@Action(value = "/phoneOrder/doOperateEmergencyContactList")
	public String doOperateEmergencyContactList() {
		Cart cart = getCart();
		if (cart != null) {
			// 订单重下
			if (orderId != null && orderId != 0l && order != null) {
				if (order.getEmergencyContact() != null) {
					// 前台下单receiverId为空,直接取ord_person数据
					String rid = order.getEmergencyContact().getReceiverId();
					if (StringUtils.isEmpty(rid)) {
						emergencyContact = convertToUsrReceiver(order
								.getEmergencyContact());
					} else {
						emergencyContact = receiverUserService
								.getRecieverByPk(rid);
					}
					cart.setEmergencyContact(emergencyContact);
				}
			} else {
				if (!StringUtils.isEmpty(id)) {
					if (id.contains("&delete")) {
						this.emergencyContact = null;
						cart.setEmergencyContact(null);
					} else {
						emergencyContact = receiverUserService
								.getRecieverByPk(id);
						cart.setEmergencyContact(emergencyContact);
					}
				}
			}
			setCart(cart);
		}
		return "emergency_list";
	}

	/**
	 * 新增取票人列表操作
	 * */
	@Action(value = "/phoneOrder/doOperateSelectUsrReceivers")
	public String doOperateSelectUsrReceivers() {
		Cart cart = getCart();
		if (cart != null) {
			if (newReceiver != null) {
				if (id != null && !"".equals(id)) {// 修改
					UsrReceivers receiver = receiverUserService
							.getRecieverByPk(id);
					receiver.setReceiverName(newReceiver.getReceiverName());
					receiver.setEmail(newReceiver.getEmail());
					receiver.setMobileNumber(newReceiver.getMobileNumber());
					receiver.setPhone(newReceiver.getPhone());
					receiver.setCardType(newReceiver.getCardType());
					receiver.setCardNum(newReceiver.getCardNum());
					receiver.setBrithday(!"".equals(brithday) ? DateUtil
							.toDate(brithday, "yyyy-MM-dd") : null);
					receiver.setFax(newReceiver.getFax());
					receiver.setFaxContactor(newReceiver.getFaxContactor());
					receiver.setPostCode(newReceiver.getPostCode());
					receiver.setAddress(newReceiver.getAddress());
					receiver.setGender(newReceiver.getGender());
					receiver.setPinyin(newReceiver.getPinyin());
					receiverUserService.update(receiver);
				} else {// 新增
					UUIDGenerator gen = new UUIDGenerator();
					this.newReceiver.setReceiverId(gen.generate().toString());
					this.newReceiver.setUserId(cart.getUserId());
					this.newReceiver.setIsValid("Y");
					this.newReceiver.setCreatedDate(new Date());
					this.newReceiver
							.setReceiversType(Constant.RECEIVERS_TYPE.CONTACT
									.name());
					this.newReceiver
							.setBrithday(!"".equals(brithday) ? DateUtil
									.toDate(brithday, "yyyy-MM-dd") : null);
					this.receiverUserService.insert(newReceiver);
				}
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", cart.getUserId());
			params.put("receiversType", Constant.RECEIVERS_TYPE.CONTACT.name());
			this.usrReceiversList = receiverUserService
					.loadRecieverByParams(params);
		}
		return "receivers";
	}

	/**
	 * 游客列表操作
	 * 
	 * @throws Exception
	 * */
	@Action(value = "/phoneOrder/doOperateVisitorList")
	public String doOperateVisitorList() {
		Cart cart = getCart();
		if (cart != null) {
			visitorList = cart.getVisitorList();
			if (visitorList == null) {
				visitorList = new LinkedHashMap<UsrReceivers, String>();
			}
			// 订单重下
			if (orderId != null && orderId != 0l && order != null) {
				List<OrdPerson> personList = order.getPersonList();
				for (int i = 0; i < personList.size(); i++) {
					OrdPerson p = personList.get(i);
					if (Constant.ORD_PERSON_TYPE.TRAVELLER.name().equals(
							p.getPersonType())) {
						// 前台下单receiverId为空,直接取ord_person数据
						UsrReceivers re = null;
						if (StringUtils.isEmpty(p.getReceiverId())) {
							re = convertToUsrReceiver(p);
						} else {
							re = receiverUserService.getRecieverByPk(p
									.getReceiverId());
						}
						visitorList.put(re, p.getNeedInsure() == null ? "false"
								: p.getNeedInsure());
					}
				}
			} else {
				if (id != null && !id.equals("")) {
					id = id.replace("&amp;", "&");
					// 删除
					if (id.contains("&delete")) {
						for (UsrReceivers receiver : visitorList.keySet()) {
							if (receiver.getReceiverId().equals(
									(id.split("&"))[0])) {
								visitorList.remove(receiver);
								break;
							}
						}

					} else if (id.contains("&update")) {
						String rId = (id.split("&"))[0];
						Map<UsrReceivers, String> tempVisitorList = new LinkedHashMap<UsrReceivers, String>();
						tempVisitorList.putAll(visitorList);
						visitorList.clear();
						for (UsrReceivers receiver : tempVisitorList.keySet()) {
							if (receiver.getReceiverId().equals(rId)) {
								UsrReceivers re = receiverUserService
										.getRecieverByPk(rId);
								visitorList.put(re, tempVisitorList.get(receiver));
							} else {
								visitorList.put(receiver, tempVisitorList.get(receiver));
							}
						}
					} else {// 新增
						boolean flag = false;
						for (UsrReceivers receiver : visitorList.keySet()) {
							if (receiver.getReceiverId().equals((id))) {
								flag = true;
								break;
							}
						}
						// 如果不重复才添加
						if (!flag) {
							UsrReceivers receiver = receiverUserService
									.getRecieverByPk(id);
							visitorList.put(receiver, "false");
						}
					}
				}
			}
			cart.setVisitorList(visitorList);
			setCart(cart);
		}
		return "visitor_list";
	}
	
	/**
	 * 校验游客必填信息
	 * */
	@Action(value = "/phoneOrder/checkTravellerInfoOptions")
	public void checkTravellerInfoOptions() {
		JSONResult result = new JSONResult();
		Cart cart = getCart();
		if (cart != null) {
			try{
				visitorList = cart.getVisitorList();
				//校验必填信息
				ProdProduct product = cart.getProduct();
				if(product != null && id != null) {
					UsrReceivers receiver = receiverUserService.getRecieverByPk(id);
					if(visitorList == null || visitorList.size() == 0) {
						//第一游玩人
						checkOptions(product.getFirstTravellerInfoOptionsList(), receiver, null);
					} else {
						checkOptions(product.getTravellerInfoOptionsList(), receiver, "其他");
					}
				}
			}catch(Exception e) {
				result.raise(e.getMessage());
			}
		}
		result.output(getResponse());
	}
	
	private void checkOptions(List<String> options, UsrReceivers receiver, String msg) throws Exception {
		if(CollectionUtils.isNotEmpty(options) && receiver != null) {
			if(msg == null) {
				msg = "第一";
			}
			if(options.contains("NAME") && StringUtil.isEmptyString(receiver.getReceiverName())) {
				throw new Exception(msg + "游玩人姓名不能为空！");
			}
			if(options.contains("PINYIN") && StringUtil.isEmptyString(receiver.getPinyin())) {
				throw new Exception(msg + "游玩人姓名拼音不能为空！");
			}
			//客服联系我和儿童无证件都不需要做校验
			if(Constant.CERT_TYPE.CUSTOMER_SERVICE_ADVICE.name().equalsIgnoreCase(receiver.getCardType()) || Constant.CERT_TYPE.ERTONG.name().equalsIgnoreCase(receiver.getCardType())) {
			} else {
				if(options.contains("CARD_NUMBER") && StringUtil.isEmptyString(receiver.getCardNum())) {
					throw new Exception(msg + "游玩人证件号码不能为空！");
				}
			}
			if(options.contains("MOBILE") && StringUtil.isEmptyString(receiver.getMobileNumber())) {
				throw new Exception(msg + "游玩人手机号码不能为空！");
			}
		}
	}

	/**
	 * 产品是否可以使用优惠券.
	 */
	@Action(value = "/phoneOrder/allMarkCoupon")
	public String allMarkCoupon() {
		Cart cart = getCart();
		if (cart != null) {
			Map<ProdProductBranch, Long> branchList = cart
					.getProdBranchItemList();
			try {
				List<Long> ids = new ArrayList<Long>();
				// mod by ljp 20120518
				List<String> subProductTypes = new ArrayList<String>();
				if (branchList != null && branchList.size() > 0) {
					for (ProdProductBranch pp : branchList.keySet()) {
						if (pp.getProdProduct().getPayToSupplier()
								.equalsIgnoreCase("true")) {// 支付给供应商
							showYouHui = false;
							break;
						}
						ids.add(pp.getProductId());
						subProductTypes.add(pp.getProdProduct()
								.getSubProductType());
					}
				}
				if (ids.size() > 0) {
					/*
					 * this.partyCouponList = this.couponService
					 * .loadAllOrderMarkCoupon(ids);
					 */
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("productIds", ids);
					map.put("subProductTypes", subProductTypes);
					map.put("withCode", "false");
					this.partyCouponList = this.markCouponService
							.selectAllCanUseAndProductCanUseMarkCoupon(map);

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return "youhui";
	}

	@Action(value = "/phoneOrder/choseCoupon")
	public void choseCoupon() {
		JSONResult result = new JSONResult();
		Cart cart = getCart();
		if (cart != null) {
			try {
				// 此处只构造了计算价格所需要的产品信息，并未构造全部订单信息
				BuyInfo buyInfo = generateBuyInfoProductInfo();
				if (buyInfo == null) {
					throw new Exception("购物车出现异常");
				}
				buyInfo.setMainProductType(cart.getProduct().getProductType());
				buyInfo.setMainSubProductType(cart.getProduct()
						.getSubProductType());
				buyInfo.setUserId(cart.getUserId());

				Coupon validateCoupon = new Coupon();
				validateCoupon.setChecked("true");
				if (StringUtils.isNotBlank(code)) {
					validateCoupon.setCode(code);
				}
				if (StringUtils.isNotBlank(id)) {
					validateCoupon.setCouponId(Long.parseLong(id));
				}
				List<Coupon> couponList = new ArrayList<Coupon>();
				couponList.add(validateCoupon);
				buyInfo.setCouponList(couponList);

				buyInfo.setFavorResult(favorService
						.calculateFavorResultByBuyInfo(buyInfo));
				PriceInfo priceInfo = orderServiceProxy.countPrice(buyInfo);

				ValidateCodeInfo info = new ValidateCodeInfo();
				if (null != priceInfo && null != priceInfo.getInfo()) {
					info = priceInfo.getInfo();
					if (Constant.COUPON_INFO.OK.name().equals(
							priceInfo.getInfo().getKey())) {
						Coupon coupon = new Coupon();
						coupon.setChecked("true");
						coupon.setCouponId(info.getCouponId());
						coupon.setCode(this.code);
						cart.setCoupon(coupon);
						cart.setPaymentChannel(info.getPaymentChannel());
					}
				}
				setCart(cart);
				result.put("info", info.getValue());
			} catch (Exception ex) {
				result.raise(ex);
			}
		} else {
			result.raise(new Exception("购物车异常!"));
		}
		result.output(getResponse());
	}

	/**
	 * 优惠策略计算
	 * */
	@Action(value = "/phoneOrder/businessCoupon")
	public String businessCoupon() {
		Cart cart = getCart();
		if (cart != null) {
			try {
				// 此处只构造了计算价格所需要的产品信息，并未构造全部订单信息
				BuyInfo buyInfo = generateBuyInfoProductInfo();
				if (buyInfo == null) {
					throw new Exception("购物车出现异常");
				}
				buyInfo.setUserId(cart.getUserId());
				List<Coupon> couponList = new ArrayList<Coupon>();
				couponList.add(cart.getCoupon() != null ? cart.getCoupon()
						: new Coupon());
				buyInfo.setCouponList(couponList);
				buyInfo.setMainSubProductType(cart.getProduct()
						.getSubProductType());
				buyInfo.setFavorResult(favorService
						.calculateFavorResultByBuyInfo(buyInfo));
				PriceInfo priceInfo = orderServiceProxy.countPrice(buyInfo);
				// 转JSON
				validateBusinessCouponInfoJSON = JSONArray.fromObject(priceInfo
						.getValidateBusinessCouponInfoList());
			} catch (Exception ex) {
				logger.error(ex, ex);
			}
		}
		return "businessCoupon";
	}

	/**
	 * @deprecated 计算优惠券
	 * */
	// public ValidateCodeInfo checkCoupon(Cart cart, String code) {
	// Long orderPrice = 0L;
	// //Long noadditionorderPrice = 0L;
	// Long orderQuantity = 0L;
	// Long productId = cart.getProductId();
	// Map<ProdProductBranch, Long> branchMap = cart.getProdBranchItemList();
	// // 正常类别总金额
	// for (ProdProductBranch branch : branchMap.keySet()) {
	// Long quantity = branchMap.get(branch);
	// if (quantity != 0L) {
	// // 如果是酒店单房型
	// if
	// (cart.getProduct().getProductType().equals(Constant.PRODUCT_TYPE.HOTEL.name())
	// &&
	// cart.getProduct().getSubProductType().equals(Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name()))
	// {
	// orderPrice += quantity * cart.getSingleSellPriceTotal();
	// } else {
	// orderPrice += quantity * branch.getSellPrice();
	// /**
	// * TASK#443将原先优惠券按金额改为不统计附加产品金额
	// * Date:20120713
	// * Author:caokun
	// */
	// //noadditionorderPrice += quantity * branch.getSellPrice();
	// }
	//
	// List<ProdProductBranchItem> branchItemList =
	// prodProductBranchService.selectBranchItemByBranchId(branch.getProdBranchId());
	// for (ProdProductBranchItem branchItem : branchItemList) {
	// MetaProduct meta =
	// metaProductService.getMetaProduct(branchItem.getMetaProductId());
	// if (!meta.isOtherProductType()) {
	// final MetaProductBranch metaBranch =
	// metaProductBranchService.getMetaBranch(branchItem.getMetaBranchId());
	// orderQuantity += quantity * (branch.getAdultQuantity() +
	// branch.getChildQuantity()) * branchItem.getQuantity()
	// * (metaBranch.getAdultQuantity() + metaBranch.getChildQuantity());
	// }
	// }
	// }
	// }
	// // 关联产品和类别总金额
	// Map<ProdProductRelation, Long> relationMap =
	// cart.getRelationProductList();
	// for (ProdProductRelation relation : relationMap.keySet()) {
	// Long quantity = relationMap.get(relation);
	// if (quantity != 0L) {
	// /**
	// * TASK#443将原先优惠券按金额改为不统计附加产品金额
	// * Date:20120713
	// * Author:caokun
	// */
	// /*
	// if(relation.getBranch().getAdditional()!=null&&(!relation.getBranch().getAdditional().equals("true")&&(!relation.getRelationProduct().getProductType().equals(Constant.PRODUCT_TYPE.OTHER.name()))))
	// {
	// noadditionorderPrice += quantity * relation.getBranch().getSellPrice();
	// }
	// */
	// orderPrice += quantity * relation.getBranch().getSellPrice();
	//
	// List<ProdProductBranchItem> branchItemList =
	// prodProductBranchService.selectBranchItemByBranchId(relation.getProdBranchId());
	// for (ProdProductBranchItem branchItem : branchItemList) {
	// MetaProduct meta =
	// metaProductService.getMetaProduct(branchItem.getMetaProductId());
	// if (!meta.isOtherProductType()) {
	// final MetaProductBranch metaBranch =
	// metaProductBranchService.getMetaBranch(branchItem.getMetaBranchId());
	// orderQuantity += quantity * (relation.getBranch().getAdultQuantity() +
	// relation.getBranch().getChildQuantity()) * branchItem.getQuantity()
	// * (metaBranch.getAdultQuantity() + metaBranch.getChildQuantity());
	// }
	// }
	// }
	// }
	// ValidateCodeInfo info = null;
	// /*
	// * if (this.id == null || "".equals(this.id)) { info =
	// * this.couponService.validateCoupon(productId, null, code == null ?
	// * cart.getCoupon().getCode() : code, cart .getUserId(), orderPrice,
	// * orderQuantity); } else { info =
	// * this.couponService.validateCoupon(productId, Long .valueOf(id), code
	// * == null ? cart.getCoupon().getCode() : code, cart.getUserId(),
	// * orderPrice, orderQuantity); }
	// */
	//
	// // info = this.couponService.validateCoupon(productId, code,
	// // order.getUserId(), order.getOughtPay(), orderQuantity,
	// // cart.getProduct().getSubProductType());
	//
	// //caokun modify
	// //info = this.couponService.validateCoupon(productId, code,
	// cart.getUserId(), orderPrice, noadditionorderPrice, orderQuantity,
	// cart.getProduct().getSubProductType());
	// info = this.couponService.validateCoupon(productId, code,
	// cart.getUserId(), orderPrice, orderQuantity,
	// cart.getProduct().getSubProductType());
	// return info;
	// }

	/**
	 * 计算总金额
	 * */
	@Action(value = "/phoneOrder/countOrderAmount")
	public String countOrderAmount() {
		Cart cart = getCart();
		if (cart != null) {
			// 此处只构造了计算价格所需要的产品信息，并未构造全部订单信息
			BuyInfo buyInfo = generateBuyInfoProductInfo();
			if (buyInfo == null) {
				return "check_error";
			}
			buyInfo.setUserId(cart.getUserId());
			List<Coupon> couponList = new ArrayList<Coupon>();
			couponList.add(cart.getCoupon() != null ? cart.getCoupon()
					: new Coupon());
			buyInfo.setCouponList(couponList);
			buyInfo.setMainSubProductType(cart.getProduct().getSubProductType());
			buyInfo.setFavorResult(favorService
					.calculateFavorResultByBuyInfo(buyInfo));
			PriceInfo priceInfo = orderServiceProxy.countPrice(buyInfo);

			info = new HashMap<String, String>();
			info.put("allSellPrice", String.valueOf(priceInfo.getPrice()));
			info.put("jieshen", String.valueOf(priceInfo.getCoupon()));
			BigDecimal b1 = new BigDecimal(Float.toString(priceInfo.getPrice()));
			BigDecimal b2 = new BigDecimal(Float.toString(priceInfo
					.getOughtPay()));
			float allOrderYouhui = b1.subtract(b2).floatValue();
			info.put("allOrderYouhui", String.valueOf(allOrderYouhui));
			info.put("shifu", String.valueOf(priceInfo.getOughtPay()));
		}
		return "order_amount";
	}

	/**
	 * 当修改产品数量的时候更新购物车数据
	 * */
	@Action(value = "/phoneOrder/doOperateProdBranchItemList")
	public void doOperateProdBranchItemList() {
		JSONResult result = new JSONResult();
		getRequest().getParameterMap();
		Cart cart = getCart();
		if (cart != null) {
			if (StringUtils.isNotBlank(ids)) {
				try {
					ArrayList<Map<String, String>> idList = (ArrayList<Map<String, String>>) JSONUtil
							.deserialize(ids);
					for (Map<String, String> mapId : idList) {
						this.id = mapId.get("id");
						this.itemType = mapId.get("itemType");
						this.quantity = Long.valueOf(mapId.get("quantity"));
						try {
							if (itemType != null && itemType.equals("branch")) {
								Map<ProdProductBranch, Long> map = cart
										.getProdBranchItemList();
								if (MapUtils.isEmpty(map)) {
									throw new Exception("没有选购产品");
								}
								for (ProdProductBranch branch : map.keySet()) {
									if (branch.getProdBranchId().equals(
											Long.parseLong(id))) {
										map.remove(branch);
										map.put(branch, quantity);
										break;
									}
								}
								cart.setProdBranchItemList(map);
								// 单房型，需设置每天时间价格信息
								if (!cart.getProduct().IsAperiodic()
										&& cart.getProduct().isSingleRoom()) {
									List<OrdTimeInfo> timeInfoList = cart
											.getTimeInfoList();
									for (OrdTimeInfo timeInfo : timeInfoList) {
										timeInfo.setQuantity(quantity);
									}
								}
							} else {
								Map<ProdProductRelation, Long> map = cart
										.getRelationProductList();
								if (MapUtils.isEmpty(map)) {
									throw new Exception("没有选购产品");
								}
								if (map != null) {
									for (ProdProductRelation relation : map
											.keySet()) {
										if (relation.getProdBranchId().equals(
												Long.parseLong(id))) {
											map.remove(relation);
											map.put(relation, quantity);
											break;
										}
									}
									cart.setRelationProductList(map);
								}
							}
							setCart(cart);
						} catch (Exception ex) {
							result.raise(ex);
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			result.raise(new Exception("购物车异常!"));
		}
		result.put("lastModifyTime", getLastCancelHourF());
		result.output(getResponse());
	}

	/**
	 * 检查订单生成是否会是必须预授权
	 * 
	 * @param buyInfo
	 * @return
	 */
	private boolean checkNeedPrePayByProductInfo(BuyInfo buyInfo) {
		for (Item item : buyInfo.getItemList()) {
			if (item.getQuantity() > 0) {
				TimePrice tp = prodProductBranchService.getProdTimePrice(
						item.getProductBranchId(), item.getVisitTime());
				if (!TimePriceUtil.checkLastCancel(tp)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 生成buyInfo 产品信息
	 * 
	 * @return
	 */
	private BuyInfo generateBuyInfoProductInfo() {
		Cart cart = this.getCart();
		if (cart == null) {
			return null;
		}
		Map<ProdProductBranch, Long> branchItemList = cart
				.getProdBranchItemList();
		if (branchItemList == null || branchItemList.isEmpty()) {
			return null;
		}
		boolean isAperiodic = cart.getProduct().IsAperiodic();
		BuyInfo buyInfo = new BuyInfo();
		// 将产品列表放入buyInfo
		List<Item> itemList = new ArrayList<Item>();
		boolean defaultFlag = false;
		Date beginTime = null, endTime = null;
		for (ProdProductBranch branch : branchItemList.keySet()) {
			Long quantity = branchItemList.get(branch);
			if (quantity != null && quantity > 0L) {
				Item item = new Item();
				item.setQuantity(Integer.parseInt(quantity.toString()));
				item.setProductId(cart.getProductId());
				item.setProductBranchId(branch.getProdBranchId());
				// 单房型，需设置每天时间价格信息
				if (!isAperiodic && cart.getProduct().isSingleRoom()) {
					item.setTimeInfoList(cart.getTimeInfoList());
					// 单房型数量为天数*用户选择的数量
					item.setQuantity(Integer.parseInt(quantity.toString())
							* cart.getTimeInfoList().size());
				}
				// 不定期产品取最后一天有效期为游玩日期
				if (isAperiodic) {
					Date validBeginTime = branch.getValidBeginTime();
					Date validEndTime = branch.getValidEndTime();
					if (validBeginTime != null
							&& validEndTime != null
							&& !DateUtil.getDayStart(new Date()).after(
									validEndTime)) {
						item.setVisitTime(validEndTime);
						item.setValidBeginTime(validBeginTime);
						item.setValidEndTime(validEndTime);
						item.setInvalidDate(branch.getInvalidDate());
						item.setInvalidDateMemo(branch.getInvalidDateMemo());
						itemList.add(item);

						// 取多类别最大区间有效期
						if (beginTime == null) {
							beginTime = validBeginTime;
						} else {
							beginTime = beginTime.before(validBeginTime) ? beginTime
									: validBeginTime;
						}
						if (endTime == null) {
							endTime = validEndTime;
						} else {
							endTime = endTime.after(validEndTime) ? endTime
									: validEndTime;
						}
					}
				} else {
					item.setVisitTime(cart.getVisitDate());
					itemList.add(item);
				}
				if (branch.hasDefault()) {
					item.setIsDefault("true");
					defaultFlag = true;
				}
			}
		}
		// 在没有默认类别时直接取第一个
		if (!itemList.isEmpty() && !defaultFlag) {
			itemList.get(0).setIsDefault("true");
		}
		Map<ProdProductRelation, Long> relationItemList = cart
				.getRelationProductList();
		for (ProdProductRelation relation : relationItemList.keySet()) {
			Long quantity = relationItemList.get(relation);
			if (quantity != 0L) {
				Item item = new Item();
				item.setQuantity(Integer.parseInt(quantity.toString()));
				item.setVisitTime(cart.getVisitDate());
				item.setProductId(relation.getRelatProductId());
				item.setProductBranchId(relation.getProdBranchId());
				itemList.add(item);
			}
		}
		List<Person> personList = new ArrayList<Person>();
		
		// 取票人
		Person p = convertToPerson(cart.getUsrReceiver(),
				Constant.ORD_PERSON_TYPE.CONTACT.name());
		personList.add(p);
		
		// 游客
		Map<UsrReceivers, String> visitorList = cart.getVisitorList();
		if(visitorList!=null){
			for (UsrReceivers usrReceivers : visitorList.keySet()) {
				p = convertToPerson(usrReceivers,
						Constant.ORD_PERSON_TYPE.TRAVELLER.name());
				String needInsure = visitorList.get(usrReceivers);
				if (needInsure.equals("true")) {
					p.setNeedInsure(needInsure);
				}
				personList.add(p);
			}
		}
		
		
		buyInfo.setPersonList(personList);
		buyInfo.setItemList(itemList);
		buyInfo.setIsAperiodic(cart.getProduct().getIsAperiodic());
		if (beginTime != null && endTime != null) {
			buyInfo.setValidBeginTime(beginTime);
			buyInfo.setValidEndTime(endTime);
		}
		return buyInfo;
	}

	/**
	 * 下单
	 * */
	@Action(value = "/phoneOrder/doSaveOrder")
	public String doSaveOrder() {
		Cart cart = getCart();
		if (cart != null) {
			BuyInfo buyInfo = generateBuyInfoProductInfo();
			if (buyInfo == null) {
				message = "购物车出现异常";
				return "check_error";
			}
			if (buyInfo.getItemList().isEmpty()) {
				message = "产品列表为空";
				return "check_error";
			}
			ResultHandle handle = orderServiceProxy.checkOrderStock(buyInfo);
			if (handle.isFail()) {// 做一次全库存的检查
				message = handle.getMsg();
				return "check_error";
			}
			buyInfo.setMainProductType(cart.getProduct().getProductType());
			buyInfo.setMainSubProductType(cart.getProduct().getSubProductType());
			buyInfo.setWaitPayment(Long.parseLong(paymentWaitTime));
			if (StringUtils.isEmpty(paymentWaitTime)) {
				buyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_DEFAULT
						.getValue());
			}
			buyInfo.setChannel(orderChannel == null ? cart.getOrderChannel()
					: orderChannel);
			buyInfo.setNeedInvoice(needInvoice == null ? "false" : needInvoice);
			buyInfo.setResourceConfirmStatus(resourceConfirm);
			buyInfo.setRedail(needRedail);
			buyInfo.setUserId(cart.getUserId());
			buyInfo.setPaymentTarget(cart.getPaymentTarget());

			// 故宫产品限制支付网关为支付宝 add by taiqichao 2013/06/07
//			if (prodProductRoyaltyService.getRoyaltyProductIds().contains(
//					cart.getProductId())) {
//				cart.setPaymentChannel(Constant.PAYMENT_GATEWAY.ALIPAY.name());
//			}
			buyInfo.setPaymentChannel(cart.getPaymentChannel());
			Map<UsrReceivers, String> visitorList = cart.getVisitorList();
			List<Person> personList = new ArrayList<Person>();
			// 游客
			for (UsrReceivers usrReceivers : visitorList.keySet()) {
				Person p = convertToPerson(usrReceivers,
						Constant.ORD_PERSON_TYPE.TRAVELLER.name());
				String needInsure = visitorList.get(usrReceivers);
				if (needInsure.equals("true")) {
					p.setNeedInsure(needInsure);
				}
				personList.add(p);
			}
			// 取票人
			Person p = convertToPerson(cart.getUsrReceiver(),
					Constant.ORD_PERSON_TYPE.CONTACT.name());
			personList.add(p);
			// 紧急联系人
			if (null != cart.getEmergencyContact()) {
				personList.add(convertToPerson(cart.getEmergencyContact(),
						Constant.ORD_PERSON_TYPE.EMERGENCY_CONTACT.name()));
			}
			// 预订人
			Person booker = new Person();
			UserUser users = userUserProxy
					.getUserUserByUserNo(cart.getUserId());
			booker.setMobile(users.getMobileNumber());
			booker.setName(users.getRealName());
			booker.setPersonType(Constant.ORD_PERSON_TYPE.BOOKER.name());
			if (StringUtils.isEmpty(users.getMobileNumber())) {
				for (Person person : personList) {
					if (Constant.ORD_PERSON_TYPE.CONTACT.name().equals(
							person.getPersonType())) {
						booker.setMobile(person.getMobile());
					}
				}
			}
			personList.add(booker);
			if (cart.getProduct().getPhysical().equals("true")
					&& shitipiaoReceiverId != null) {
				// 实体票地址
				UsrReceivers shitiPiaoReceiver = receiverUserService
						.getRecieverByPk(shitipiaoReceiverId);
				//地址=省 市  地址
				shitiPiaoReceiver.setAddress(shitiPiaoReceiver.getProvince()+" "+shitiPiaoReceiver.getCity()+" "+shitiPiaoReceiver.getAddress());
				personList.add(convertToPerson(shitiPiaoReceiver,
						Constant.ORD_PERSON_TYPE.ADDRESS.name()));
			}
			buyInfo.setPersonList(personList);
			// 发票地址
			if (needInvoice != null && needInvoice.equals("true")) {
				buyInfo.setInvoiceList(convertToInvoice(cart.getOrdInvoice()));
				if (fapiaoReceiverId != null) {
					UsrReceivers faPiaoReceiver = receiverUserService
							.getRecieverByPk(fapiaoReceiverId);
					buyInfo.setInvoiceAddress(convertToPerson(faPiaoReceiver,
							Constant.ORD_PERSON_TYPE.ADDRESS.name()));
				}
			}
			List<Coupon> couponList = new ArrayList<Coupon>();
			couponList.add(cart.getCoupon() != null ? cart.getCoupon()
					: new Coupon());
			buyInfo.setCouponList(couponList);
			buyInfo.setMemoList(cart.getMemoList());
			buyInfo.setFavorResult(favorService
					.calculateFavorResultByBuyInfo(buyInfo));
			if (ActivityUtil.getInstance().checkActivityIsValid(
					Constant.ACTIVITY_FIVE_YEAR)) {
				buyInfo.setCouponList(new ArrayList<Coupon>()); // 5周年不使用优惠券
				FavorResult fr = new FavorResult();
				OrderFavorStrategyForFifthAnniversary ofsf = new OrderFavorStrategyForFifthAnniversary(
						new MarkCoupon(), new MarkCouponCode());
				fr.addOrderFavorStrategy(ofsf);
				buyInfo.setFavorResult(fr);
			}
			
			//超卖异常捕获 modify by taiqichao 20140423
			try {
				if(cart.getOldOrderId() == null){
					order = orderServiceProxy.createOrderWithOperatorId(buyInfo,getOperatorName());
				}else{// 复制订单
					order = orderServiceProxy.createOrderWithOrderId(buyInfo,cart.getOldOrderId(), getOperatorName());
				}
			}catch (OversoldException e) {//超卖异常捕获
				LOG.info("成功防止了超卖");
				message = "库存不足";
				return "check_error";

			}
			
			order = orderServiceProxy.queryOrdOrderByOrderId(order.getOrderId());
			
			
			
			//由于驴妈妈账户被盗严重  对于广州长隆供应商的产品临时关闭存款账户支付功能(485=广州长隆供应商ID)
			List<OrdOrderItemMeta> ordOrderItemMetaList=order.getAllOrdOrderItemMetas();
			if(ordOrderItemMetaList!=null && ordOrderItemMetaList.size()>0){
				for (OrdOrderItemMeta ordOrderItemMeta : ordOrderItemMetaList) {
					if(ordOrderItemMeta.getSupplierId()!=null && 
							(485==ordOrderItemMeta.getSupplierId().longValue()
							||2261==ordOrderItemMeta.getSupplierId().longValue()
							||4462==ordOrderItemMeta.getSupplierId().longValue()
							||4367==ordOrderItemMeta.getSupplierId().longValue()
							||1340==ordOrderItemMeta.getSupplierId().longValue()
							||6134==ordOrderItemMeta.getSupplierId().longValue()
							)){
						tempCloseCashAccountPay=true;
					}
				}
			}
			
			
			
			
			
			
			sendZeroYuan(order);
			getSession().removeAttribute(CART);

			moneyAccount = cashAccountService.queryMoneyAccountByUserNo(order
					.getUserId());

			if (moneyAccount != null) {
				this.haveMoblie = StringUtils.isNotBlank(moneyAccount
						.getMobileNumber());
				this.havePaymentPassword = StringUtils.isNotBlank(moneyAccount
						.getPaymentPassword());
			}

			canAccountPay = orderServiceProxy
					.canAccountPay(order, moneyAccount);
			/**
			 * 订单是否能进行资金转移 分销的订单不支持转移
			 */
			canTransfer = orderServiceProxy.canTransferPayment(order
					.getOrderId())
					&& !order.getChannel().startsWith("DISTRIBUTION");
			/**
			 * 订单行程固化
			 */
			String userName = getOperatorName();
			if (StringUtil.isEmptyString(userName)) {
				userName = order.getUserId();
				if (StringUtil.isEmptyString(userName)) {
					userName = "backend";
				}
			}
			// String travelXml = travelDescriptionService.getTravelDesc(order
			// .getMainProduct().getProductId());
			// if (Constant.PRODUCT_TYPE.ROUTE.name().equals(
			// order.getMainProduct().getProductType())
			// && !StringUtil.isEmptyString(travelXml)) {
			// try {
			// Long fileId = fsClient.uploadFile(order.getOrderId()
			// + "_travel.xml", travelXml.getBytes(), "eContract");
			// travelDescriptionService.initOrderTravel(fileId,
			// order.getOrderId(), userName);
			// } catch (Exception e) {
			// LOG.warn("create order travel is error :\r\n" + e);
			// }
			// }
			/**
			 * 生成电子合同 20120727 szy 在线预售权修改 在生成订单后就生成合同
			 */
			// if (Constant.ECONTRACT_TYPE.NEED_ECONTRACT.name().equals(
			// order.getNeedContract())) {
			// try {
			// contractClient.createEContract(order, userName);
			// orderMessageProducer.sendMsg(MessageFactory
			// .newOrderSendEContract(order.getOrderId()));
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
			// 不定期订单不需要生成工单
			if (!order.IsAperiodic()) {
				// 新增工单
				createWorkOrder(order, buyInfo);
			}
            ViewPage vp = viewPageService.getViewPageByProductId(cart.getProductId());
            if (vp != null) {
                Map<String, Object> constents = vp.getContents();
                //退款说明
                if (null != constents.get("REFUNDSEXPLANATION")) {
                    ViewContent content = (ViewContent) constents.get("REFUNDSEXPLANATION");
                    refundSexplanation = "\r\n退款说明：\r\n"+content.getContent();
                }
            }
            mobileNumber = cart.getUsrReceiver().getMobileNumber();
		}
		return "callinfo";
	}


    @Action(value = "/phoneOrder/sendRefundSexplanation",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String sendRefundSexplanation() {
        try {
            StringBuilder info = new StringBuilder(smsInfo);

            while (info.length()>0){
                String s ;
                 if(info.length()>500){
                     s = info.substring(0,500);
                     info.delete(0,500);
                 }else{
                     s = info.toString();
                     info = new StringBuilder();
                 }

                smsRemoteService.sendSmsContent(s, mobileNumber, 5, null, new Date(), getSessionUser().getUserName());
            }




            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    public void setNeedInvoice(String needInvoice) {
		this.needInvoice = needInvoice;
	}

	/**
	 * 发送0元消息
	 * 
	 * @param ordOrder
	 */
	private void sendZeroYuan(OrdOrder ordOrder) {
		OrdOrder newOrder = orderServiceProxy.queryOrdOrderByOrderId(ordOrder
				.getOrderId());
		if (newOrder.getOughtPay() <= newOrder.getActualPay()) {
			orderMessageProducer.sendMsg(MessageFactory
					.newOrderPay0YuanMessage(newOrder.getOrderId()));
		}
	}

	/**
	 * convert usrReceiver to person
	 * */
	private Person convertToPerson(UsrReceivers receiver, String type) {
		Person p = new Person();
		if (receiver != null) {
			p.setProvince(receiver.getProvince());
			p.setCity(receiver.getCity());
			p.setAddress(receiver.getAddress());
			p.setCertNo(receiver.getCardNum());
			p.setCertType(receiver.getCardType());
			p.setEmail(receiver.getEmail());
			p.setFax(receiver.getFax());
			p.setMobile(receiver.getMobileNumber());
			p.setName(receiver.getReceiverName());
			p.setPersonType(type);
			p.setPostcode(receiver.getPostCode());
			p.setTel(receiver.getPhone());
			p.setFaxTo(receiver.getFaxContactor());
			p.setBrithday(receiver.getBrithday());
			p.setGender(receiver.getGender());
			p.setPinyin(receiver.getPinyin());
			// 前台订单重下,游客receiverId为空,用原personId标识
			if (receiver.getReceiverId().contains("_p")) {
				p.setReceiverId(null);
			} else {
				p.setReceiverId(receiver.getReceiverId());
			}
		} else {
			logger.warn("UsrReceivers 为空" + " type=" + type);
		}
		return p;
	}

	/**
	 * convert person to usrReceiver
	 * */
	private UsrReceivers convertToUsrReceiver(OrdPerson p) {
		UsrReceivers receiver = new UsrReceivers();
		receiver.setProvince(p.getProvince());
		receiver.setCity(p.getCity());
		receiver.setAddress(p.getAddress());
		receiver.setCardNum(p.getCertNo());
		receiver.setCardType(p.getCertType());
		receiver.setEmail(p.getEmail());
		receiver.setFax(p.getFax());
		receiver.setMobileNumber(p.getMobile());
		receiver.setReceiverName(p.getName());
		receiver.setPostCode(p.getPostcode());
		receiver.setPhone(p.getTel());
		receiver.setFaxContactor(p.getFaxTo());
		receiver.setBrithday(p.getBrithday());
		receiver.setReceiverId(p.getPersonId() + "_p");
		receiver.setPinyin(p.getPinyin());
		return receiver;
	}

	/**
	 * convert ordInvoice to invoice list
	 * */
	private List<Invoice> convertToInvoice(OrdInvoice inv) {
		List<Invoice> inList = new ArrayList<Invoice>();
		Invoice invoice = new Invoice();
		invoice.setAmount(inv.getAmount());
		invoice.setTitle(inv.getTitle());
		invoice.setCompany(inv.getCompany());
		invoice.setDeliveryType(inv.getDeliveryType());
		invoice.setDetail(inv.getDetail());
		invoice.setMemo(inv.getMemo());
		inList.add(invoice);
		return inList;
	}

	/**
	 * 下单前检查产品
	 * */
	public String chcekProduct(Date visitDate, Date endDate, Long prodBranchId,
			Long quantity) {
		ProdProductBranch branch = prodProductBranchService
				.getProductBranchDetailByBranchId(prodBranchId, visitDate,
						false);
		if (branch == null) {
			return "该商品" + DateUtil.getFormatDate(visitDate, "yyyy-MM-dd")
					+ "不可售";
		}
		if (quantity > branch.getMaximum()) {
			return "订购数量超过最大可售数";
		}
		if (quantity < branch.getMinimum()) {
			return "订购数量小于最小订购数";
		}
		// 酒店单房型
		if (visitDate != null && endDate != null) {
			if (visitDate.compareTo(endDate) == 0 || visitDate.after(endDate)) {
				return "离店日期必须大于入住日期";
			} else {
				int days = DateUtil.getDaysBetween(visitDate, endDate);
				long day_quantity = quantity / days;
				for (int i = 0; i < days; i++) {
					Date date = DateUtil.dsDay_Date(visitDate, i);
					boolean productSellable = prodProductService
							.isProductSellable(prodBranchId, day_quantity, date);
					if (!productSellable) {
						return DateUtil.getFormatDate(date, "yyyy-MM-dd")
								+ "当前库存不足，请选择其他日期";
					}
				}
			}
			// 其他产品类型
		} else {

			boolean productSellable = prodProductService.isProductSellable(
					prodBranchId, quantity, visitDate);
			if (!productSellable) {
				return DateUtil.getFormatDate(visitDate, "yyyy-MM-dd")
						+ "当前库存不足，请选择其他日期";
			}
		}
		return null;
	}

	/**
	 * 提交订单前校验
	 * */
	@Action(value = "/phoneOrder/checkOrder")
	public void checkOrder() {
		JSONResult result = new JSONResult();
		Cart cart = getCart();
		if (cart != null) {
			try {
				BuyInfo buyInfo = generateBuyInfoProductInfo();
				if (buyInfo == null) {
					throw new Exception("购物车出现异常");
				}
				// 不定期产品只能选购一种类别
				if (buyInfo.IsAperiodic()) {
					if (buyInfo.getValidBeginTime() != null
							&& buyInfo.getValidEndTime() != null) {
						if (DateUtil.getDayStart(new Date()).after(
								buyInfo.getValidEndTime())) {
							throw new Exception("该不定期产品已过期,不可售");
						}
					} else {
						throw new Exception("该不定期产品没有有效期信息");
					}
				}
				if (StringUtils.isEmpty(cart.getUserId())) {
					throw new Exception("下单人为空！");
				}
				if (cart.getUsrReceiver() == null
						|| StringUtils.isEmpty(cart.getUsrReceiver()
								.getReceiverId())) {
					throw new Exception("请选择取票人/入住人！");
				}

				if (MapUtils.isEmpty(cart.getVisitorList())) {
					throw new Exception("请选择游客！");
				}
				
				ProdProduct product = cart.getProduct();
				if (product != null) {
					String emergencyContactMobile = null;
					if (null != cart.getEmergencyContact()) {
						emergencyContactMobile = cart.getEmergencyContact().getMobileNumber();
					}
					int count = 0;
					String visitorName = "";
					//校验游客必填信息和紧急联系人手机号
					for (UsrReceivers u : cart.getVisitorList().keySet()) {
						if (u == null || StringUtils.isEmpty(u.getReceiverId())) {
							throw new Exception("游客信息错误");
						}
						if(count == 0) { //第一游玩人
							checkOptions(product.getFirstTravellerInfoOptionsList(), u, null);
							visitorName = u.getReceiverName();
						} else {//其他游客
							checkOptions(product.getTravellerInfoOptionsList(), u, "其他");
						}
						if (StringUtils.isNotEmpty(emergencyContactMobile) && StringUtils.isNotEmpty(u.getMobileNumber()) && u.getMobileNumber().equals(emergencyContactMobile)) {
							throw new Exception("紧急联系人手机号不能和游玩人相同");
						}
						count++;
					}
					
					String subProductType = product.getSubProductType();

					if (product.isTicket()) {
						if (5521 == product.getProductId().longValue() || 93376 == product.getProductId().longValue()) {
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("productId", product.getProductId());
							params.put("userId", cart.getUserId());
							params.put("contactMobile", cart.getUsrReceiver()
									.getMobileNumber());
							params.put("productLimit", "true");
							params.put("beginTime", DateUtil.formatDate(
									DateUtil.getFirstdayOfMonth(new Date()),
									"yyyy-MM-dd"));
							params.put("endTime", DateUtil.formatDate(
									DateUtil.getLastdayOfMonth(new Date()),
									"yyyy-MM-dd"));
							ResultHandle isExisted = orderServiceProxy
									.checkCreateOrderLimitIsExisted(params);
							if (isExisted.isFail()) {
								throw new Exception(isExisted.getMsg());
							}
						}
						// 故宫同一游玩时间同一证件号只能下一次单
						if (prodProductRoyaltyService.getRoyaltyProductIds()
								.contains(product.getProductId())) {
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("productId", product.getProductId());
							params.put("userId", cart.getUserId());
							params.put("certNo", cart.getUsrReceiver()
									.getCardNum());
							params.put("gugongproductLimit", "true");
							params.put("visitTime", cart.getVisitDate());
							ResultHandle isExisted = orderServiceProxy
									.checkCreateOrderLimitIsExisted(params);
							if (isExisted.isFail()) {
								throw new Exception(isExisted.getMsg());
							}
						}
					} else if (product.isHotel()
							|| Constant.ROUTE_SUB_PRODUCT_TYPE.FREENESS.name()
									.equals(subProductType)) {
						Map<String, Object> params = new HashMap<String, Object>();
						params.put("productId", cart.getProductId());
						params.put("userId", cart.getUserId());
						params.put("visitTime", cart.getVisitDate());
						params.put("subProductType", subProductType);

						List<String> travellerInfoOptionsList = product
								.getTravellerInfoOptionsList();
						params.put("travellerInfoOptions",
								travellerInfoOptionsList);
						// 酒店有必填信息，需填写游客或目的地自由行
						if ((null != travellerInfoOptionsList && !travellerInfoOptionsList
								.isEmpty())
								|| Constant.ROUTE_SUB_PRODUCT_TYPE.FREENESS
										.name().equals(subProductType)) {
						} else {
							visitorName = cart.getUsrReceiver().getReceiverName();// 酒店联系人
						}
						params.put("visitorName", visitorName);
						Date leaveDate = null;
						// 酒店单房型需离店日期
						if (product.isSingleRoom()
								&& cart.getLeaveDate() != null) {
							leaveDate = cart.getLeaveDate();
							params.put("leaveTime", leaveDate);
						}
						ResultHandle isExisted = orderServiceProxy
								.checkCreateOrderLimitIsExisted(params);
						if (isExisted.isFail()) {
							comLogService.insert("ORD_ORDER", null, null,
									getOperatorName(), "REPEATED_ORDER",
									"重复订单游玩人", "后台,产品id：" + cart.getProductId()
											+ ",用户id：" + cart.getUserId()
											+ ",游玩时间：" + cart.getVisitDate()
											+ ",第一游玩人姓名：" + visitorName,
									"ORD_ORDER");
							throw new Exception(
									"此用户已在相同日期内预订过该产品，为避免酒店无法识别传真，请更换游客姓名！");
						}
					}
				}

				if (checkNeedPrePayByProductInfo(buyInfo)) {
					result.put("needPrePay", true);
				}

				// 故宫下单每笔订单只能限定5张
				if (prodProductRoyaltyService.getRoyaltyProductIds().contains(
						product.getProductId())) {
					int count = 0;
					for (BuyInfo.Item item : buyInfo.getItemList()) {
						count = item.getQuantity() + count;
					}
					if (count > 5) {
						throw new Exception("故宫门票一笔订单最多限定5张");
					}
				}

				for (BuyInfo.Item item : buyInfo.getItemList()) {

					if (item.getQuantity() > 0) {
						// 检查产品库存
						String msg = chcekProduct(item.getVisitTime(),
								cart.getLeaveDate(), item.getProductBranchId(),
								(long) item.getQuantity());
						if (null != msg) {
							throw new ResultException("stock", msg);
						}
						// 资源需确认
						if (this.prodProductService.checkResourceNeedConfirm(
								item.getProductBranchId(), cart.getVisitDate())) {
							result.put("resourceConfirm", "true");
						}
						// 是否每单结算
						List<ProdProductBranchItem> itemList = this.prodProductBranchService
								.selectBranchItemByBranchId(item
										.getProductBranchId());
						for (ProdProductBranchItem prodProductItem : itemList) {

							List<SupSettlementTarget> targetList = settlementTargetService
									.getSuperSupSettlementTargetByMetaProductId(prodProductItem
											.getMetaProductId());
							if (targetList != null && targetList.size() > 0) {
								SupSettlementTarget supSettlementTarget = targetList
										.get(0);
								if (supSettlementTarget
										.getSettlementPeriod()
										.equals(com.lvmama.comm.vo.Constant.SETTLEMENT_PERIOD.PERORDER
												.name())) {
									result.put("perOrder", "true");
								}
							}
						}

					}
				}
				ResultHandle handle = orderServiceProxy
						.checkOrderStock(buyInfo);
				if (handle.isFail()) {
					throw new IllegalArgumentException(handle.getMsg());
				}
				result.put("confirmMsg", confirmBeforeSubmit());
			} catch (ResultException ex) {
				result.put(ex.getType(), ex.getMsg());
			} catch (Exception ex) {
				result.raise(ex);
			}
		} else {
			result.raise(new Exception("购物车异常!"));
		}
		result.output(getResponse());
	}

	/**
	 * 保存投保信息
	 * */
	@Action(value = "/phoneOrder/doSaveTouBao")
	public void doSaveTouBao() {
		JSONResult result = new JSONResult();
		// 保存投保的id号
		Cart cart = getCart();
		if (cart != null) {
			Map<UsrReceivers, String> visitorList = cart.getVisitorList();
			try {
				// 记录投保人id
				if (id != null && id.contains("&")) {
					String rId = (id.split("&"))[0];
					// 删除投保信息
					if (id.contains("&delete")) {
						for (UsrReceivers usrReceiver : visitorList.keySet()) {
							if (usrReceiver.getReceiverId().equals(rId)) {
								visitorList.put(usrReceiver, "false");
								break;
							}
						}
						// 添加投保信息
					} else if (id.contains("&save")) {
						for (UsrReceivers usrReceiver : visitorList.keySet()) {
							if (usrReceiver.getReceiverId().equals(rId)) {
								visitorList.put(usrReceiver, "true");
								break;
							}
						}
						// 修改投保信息
					} else {
						UsrReceivers receiver = receiverUserService
								.getRecieverByPk(rId);
						receiver.setCardType(usrReceivers.getCardType());
						receiver.setCardNum(usrReceivers.getCardNum());
						receiver.setBrithday(!"".equals(brithday) ? DateUtil
								.toDate(brithday, "yyyy-MM-dd") : null);
						receiver.setGender(usrReceivers.getGender());
						receiverUserService.update(receiver);

						// 将信息更新到session的游客列表里
						for (UsrReceivers usrReceiver : visitorList.keySet()) {
							if (usrReceiver.getReceiverId().equals(
									receiver.getReceiverId())) {
								visitorList.put(receiver,
										visitorList.get(usrReceiver));
								visitorList.remove(usrReceiver);
								break;
							}
						}
					}
				} else {
					// 将投保信息保存到表里
					if (usrReceivers != null) {
						String receiverId = usrReceivers.getReceiverId();
						if (StringUtils.isNotEmpty(receiverId)) {
							UsrReceivers receiver = receiverUserService
									.getRecieverByPk(receiverId);
							if (receiver != null) {
								receiver.setCardType(usrReceivers.getCardType());
								receiver.setCardNum(usrReceivers.getCardNum());
								receiver.setBrithday(!"".equals(brithday) ? DateUtil
										.toDate(brithday, "yyyy-MM-dd") : null);
								receiver.setGender(usrReceivers.getGender());
								receiverUserService.update(receiver);

								// 将信息更新到session的游客列表里
								for (UsrReceivers usrReceiver : visitorList
										.keySet()) {
									if (usrReceiver.getReceiverId().equals(
											receiver.getReceiverId())) {
										visitorList.put(receiver,
												visitorList.get(usrReceiver));
										visitorList.remove(usrReceiver);
										break;
									}
								}
							} else {
								throw new Exception("取得该联系人信息为空！");
							}
						} else {
							throw new Exception("取得该联系人ID为空！");
						}
					}
				}
				cart.setVisitorList(visitorList);
				setCart(cart);
			} catch (Exception ex) {
				result.raise(ex);
			}
		} else {
			result.raise(new Exception("购物车异常!"));
		}
		result.output(getResponse());
	}

	/**
	 * 订单备注类别信息列表
	 */
	public List<CodeItem> getMemoTypes() {
		return CodeSet.getInstance().getCachedCodeList(
				Constant.CODE_TYPE.ORD_MEMO_TYPE.name());
	}

	/**
	 * 证件类型信息列表
	 */
	public List<CodeItem> getCardTypes() {
		List<CodeItem> list = CodeSet.getInstance().getCachedCodeList(
				Constant.CODE_TYPE.CERT_TYPE.name());
		for (CodeItem item : list) {
			if (item.getCode().equals("OTHER")) {
				list.remove(item);
				break;
			}
		}
		return list;
	}

	/**
	 * 支付等待时间
	 */
	public List<CodeItem> getPayWaitItemList() {
		return CodeSet.getInstance().getCachedCodeList(
				Constant.CODE_TYPE.WAIT_PAYMENT.name());
	}

	/**
	 * 订单来源渠道
	 * */
	public List<CodeItem> getOrderChannelList() {
		return CodeSet.getInstance().getCachedCodeList(
				Constant.CODE_TYPE.CHANNEL.name());
	}

	/**
	 * 生成发票下拉框内容
	 */
	public List<CodeItem> getInvoiceDetails() {
		List<CodeItem> invoiceDetails = new ArrayList<CodeItem>();
		List<CodeItem> list = CodeSet.getInstance().getCachedCodeList(
				Constant.CODE_TYPE.COMPANY_1_CONTENT.name());
		invoiceDetails.addAll(addCompany(list,
				Constant.CODE_TYPE.COMPANY_1_CONTENT));
		list = CodeSet.getInstance().getCachedCodeList(
				Constant.CODE_TYPE.COMPANY_2_CONTENT.name());
		invoiceDetails.addAll(addCompany(list,
				Constant.CODE_TYPE.COMPANY_2_CONTENT));
		list = CodeSet.getInstance().getCachedCodeList(
				Constant.CODE_TYPE.COMPANY_3_CONTENT.name());
		invoiceDetails.addAll(addCompany(list,
				Constant.CODE_TYPE.COMPANY_3_CONTENT));
		return invoiceDetails;
	}

	private List<CodeItem> addCompany(List<CodeItem> list,
			Constant.CODE_TYPE type) {
		if (CollectionUtils.isNotEmpty(list)) {
			for (CodeItem ci : list) {
				if (Constant.CODE_TYPE.COMPANY_1_CONTENT.equals(type)) {
					ci.setName(ci.getName() + "(景域)");
				} else if (Constant.CODE_TYPE.COMPANY_2_CONTENT.equals(type)) {
					ci.setName(ci.getName() + "(驴妈妈)");
				} else if (Constant.CODE_TYPE.COMPANY_3_CONTENT.equals(type)) {
					ci.setName(ci.getName() + "(兴旅)");
				}
			}
		}
		return list;
	}

	public List<ComProvince> getProvinceList() {
		return placeCityService.getProvinceList();
	}

	/**
	 * 让页面联动，取城市信息
	 */
	@Action("/phoneOrder/citys")
	public void getCitys() {
		JSONResult result = new JSONResult();
		List<ComCity> list = placeCityService.getCityListByProvinceId(id);
		if (CollectionUtils.isNotEmpty(list)) {
			JSONArray array = new JSONArray();
			for (ComCity cc : list) {
				JSONObject obj = new JSONObject();
				obj.put("cityId", cc.getCityId());
				obj.put("cityName", cc.getCityName());
				array.add(obj);
			}
			result.put("list", array);
		}
		result.output(getResponse());
	}

	@Action("/phoneOrder/doShowDialog")
	public String doShowDialog() {
		if (id != null && !"".equals(id)) {
			// 修改投保信息、修改联系人信息、修改地址弹出框
			String rId = (id.split("&"))[0];
			this.usrReceivers = receiverUserService.getRecieverByPk(rId);
			if (!id.contains("&")) {// 修改地址弹出框
				String province = this.usrReceivers.getProvince();
				if (province != null) {
					String provinceId = placeCityService.selectByProvinceName(
							province).getProvinceId();
					this.usrReceivers.setProvince(provinceId);
					this.cityList = placeCityService
							.getCityListByProvinceId(provinceId);
					String city = this.usrReceivers.getCity();
					if (city != null) {
						this.usrReceivers.setCity(placeCityService
								.selectByProvinceAndName(provinceId, city)
								.getCityId());
					}
				}
			}
			addressReceiver = usrReceivers;
			newReceiver = usrReceivers;
			if (id.contains("&add")) {
				id = null;
			} else {
				id = rId;
			}
		}
		if (productId != null) {
			product = this.prodProductService.getProdProductById(productId);
			String travellerInfoOptions = product.getTravellerInfoOptions();
			if (StringUtils.isNotEmpty(travellerInfoOptions)) {
				cardTypeRequired = travellerInfoOptions.contains("CARD_NUMBER");
			}
		}
		return to;
	}

	private boolean createWorkOrder(OrdOrder order, BuyInfo buyInfo) {
		List<OrdOrderMemo> memoList = buyInfo.getMemoList();
		String userMemo = "";
		if (memoList != null) {
			for (OrdOrderMemo om : memoList) {
				if (om.hasUserMemo()) {
					userMemo += om.getContent() + "<br/>";
				}
			}
		}
		if (StringUtil.isEmptyString(userMemo)) {
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
			workOrderProxy.sendWorkOrder(order,
					Constant.WORK_ORDER_TYPE_AND_SENDGROUP.YHTSYQSH
							.getWorkOrderTypeCode(),
					"/super_back/ord/showOrderMemoCheck.do?operateType=workOrder&orderId="
							+ order.getOrderId(), Boolean.TRUE, Boolean.TRUE,
					null, null, null, null,null,false);
		} else {
			workOrderProxy.sendWorkOrder(order,
					Constant.WORK_ORDER_TYPE_AND_SENDGROUP.YHTSYQSHHT
							.getWorkOrderTypeCode(),
					"/super_back/ord/showOrderMemoCheck.do?operateType=workOrder&orderId="
							+ order.getOrderId(), Boolean.TRUE, Boolean.FALSE,
					null, null, null, null,null,false);
		}

		return true;
	}

    private String confirmBeforeSubmit() {
        Cart cart = getCart();
        StringBuilder msg = new StringBuilder();
        if (cart != null) {
            try {
                UserUser user = userUserProxy.getUserUserByUserNo(cart.getUserId());
                msg.append(user.getUserName()).append(",您好！您预订的是").append(DateUtil.formatDate(cart.getVisitDate(), "yyyy-MM-dd")).
                        append("的").append(cart.getProduct().getProductName()).append("。\n\n");
                Map<ProdProductBranch, Long> branchList = cart.getProdBranchItemList();
                for (ProdProductBranch pp : branchList.keySet()) {
                    Long quantity = branchList.get(pp);
                    Long adultQ = pp.getAdultQuantity() * quantity;
                    Long childQ = pp.getChildQuantity() * quantity;
                    msg.append(pp.getBranchName()).append(",含").append(adultQ).append("成人,").append(childQ).append( "儿童。");
                }
                msg.append("\n\n出游人：");
                for (UsrReceivers receiver : cart.getVisitorList().keySet()) {
                    msg.append(receiver.getReceiverName() ).append(",");
                }
                if(cart.getProduct().getSubProductType().equals(Constant.SUB_PRODUCT_TYPE.GROUP.getCode()) ||
                        cart.getProduct().getSubProductType().equals(Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.getCode()) ||
                        cart.getProduct().getProductType().equals(Constant.PRODUCT_TYPE.TICKET.getCode())) {
                    msg.append("\n\n具体的退款说明我们会通过短信的方式发送到您的手机上，请您详细查看，一旦您\n支付成功就视您已经同意该产品的退款说明。\n\n");
                }
                ViewPage vp = viewPageService.getViewPageByProductId(cart.getProductId());
                if (vp != null) {
                    Map<String, Object> constents = vp.getContents();
                    //退款说明
                    if (null != constents.get("REFUNDSEXPLANATION")) {
                        ViewContent content = (ViewContent) constents.get("REFUNDSEXPLANATION");
                        msg.append("\n\n退款说明：\n" ).append( content.getContent());
                    }
                }
                String tempMsg = "\n\n您选择购买";
                boolean flag = false;
                Map<ProdProductRelation, Long> relationItemList = cart.getRelationProductList();
                for (ProdProductRelation relation : relationItemList.keySet()) {
                    //保险
                    if (StringUtils.equals(relation.getRelationProduct().getSubProductType(), Constant.SUB_PRODUCT_TYPE.INSURANCE.name())) {
                        Long quantity = relationItemList.get(relation);
                        if (quantity != 0L) {
                            tempMsg += quantity + "份" + relation.getRelationProduct().getProductName() + ",";
                            flag = true;
                        }
                    }
                }
                if (flag) {
                    msg.append(tempMsg);
                } else {
                    msg.append("\n\n为了您的出游安全，建议您购买保险。");
                }
                msg.append("\n\n请问您是否确认下单？ \n感谢您的预订，祝您旅途愉快，再见！");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return msg.toString();
    }

	public void setOomemo(OrdOrderMemo oomemo) {
		this.oomemo = oomemo;
	}

	public OrdOrderMemo getOomemo() {
		return oomemo;
	}

	public List<ProdAssemblyPoint> getProdAssemblyPointList() {
		return prodAssemblyPointList;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public OrdInvoice getOinvoice() {
		return oinvoice;
	}

	public void setOinvoice(OrdInvoice oinvoice) {
		this.oinvoice = oinvoice;
	}

	public List<ComCity> getCityList() {
		if (CollectionUtils.isEmpty(cityList)) {
			ComCity city = new ComCity();
			city.setCityId("");
			city.setCityName("请选择");
			cityList.add(city);
		}
		return cityList;
	}

	public void setUsrReceivers(UsrReceivers usrReceivers) {
		this.usrReceivers = usrReceivers;
	}

	public UsrReceivers getUsrReceivers() {
		return usrReceivers;
	}

	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<MarkCoupon> getPartyCouponList() {
		return partyCouponList;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public boolean isShowYouHui() {
		return showYouHui;
	}

	public void setProdProductRelationService(
			ProdProductRelationService prodProductRelationService) {
		this.prodProductRelationService = prodProductRelationService;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public void setSettlementTargetService(
			SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}

	private Cart getCart() {
		Object obj = getSession().getAttribute(CART);
		return obj != null ? (Cart) obj : new Cart();
	}

	private void setCart(Cart cart) {
		Object obj = getSession().getAttribute(CART);
		if (obj != null) {
			getSession().removeAttribute(CART);
		}
		getSession().setAttribute(CART, cart);
	}

	public List<OrdOrderMemo> getMemoList() {
		return memoList;
	}

	public Map<UsrReceivers, String> getVisitorList() {
		return visitorList;
	}

	public List<UsrReceivers> getUsrReceiversList() {
		return (usrReceiversList == null) ? new ArrayList<UsrReceivers>()
				: usrReceiversList;
	}

	public ProdProduct getProduct() {
		return product;
	}

	public void setResourceConfirm(String resourceConfirm) {
		this.resourceConfirm = resourceConfirm;
	}

	public void setNeedRedail(String needRedail) {
		this.needRedail = needRedail;
	}

	public void setProdBranchItemMap(Map<String, String> prodBranchItemMap) {
		this.prodBranchItemMap = prodBranchItemMap;
	}

	public String getPayTarget() {
		return payTarget;
	}

	public void setPaymentWaitTime(String paymentWaitTime) {
		this.paymentWaitTime = paymentWaitTime;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Map<ProdProductBranch, Long> getProdBranchItemList() {
		return prodBranchItemList;
	}

	public Map<ProdProductRelation, Long> getRelationProductItemList() {
		return relationProductItemList;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public HashMap<String, String> getInfo() {
		return info;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setFapiaoReceiverId(String fapiaoReceiverId) {
		this.fapiaoReceiverId = fapiaoReceiverId;
	}

	public void setShitipiaoReceiverId(String shitipiaoReceiverId) {
		this.shitipiaoReceiverId = shitipiaoReceiverId;
	}

	public Map<String, String> getProdBranchItemMap() {
		return prodBranchItemMap;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Date getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}

	public OrdOrder getOrder() {
		return order;
	}

	public String getLakalaURL() {
		return lakalaURL;
	}

	public boolean isHaveMoblie() {
		return haveMoblie;
	}

	public boolean isCanAccountPay() {
		return canAccountPay;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public String getZhOrderChannel() {
		return Constant.CHANNEL.getCnName(orderChannel);
	}

	public String getRouteCode() {
		return routeCode;
	}

	public String getParamsStr() {
		return paramsStr;
	}

	public void setParamsStr(String paramsStr) {
		this.paramsStr = paramsStr;
	}

	public void setOrderMessageProducer(
			TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public String getBrithday() {
		return brithday;
	}

	public void setBrithday(String brithday) {
		this.brithday = brithday;
	}

	public UsrReceivers getEmergencyContact() {
		return emergencyContact;
	}

	public boolean isTestOrder() {
		return testOrder;
	}

	public void setTestOrder(boolean testOrder) {
		this.testOrder = testOrder;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public UsrReceivers getAddressReceiver() {
		return addressReceiver;
	}

	public void setAddressReceiver(UsrReceivers addressReceiver) {
		this.addressReceiver = addressReceiver;
	}

	public UsrReceivers getNewReceiver() {
		return newReceiver;
	}

	public void setNewReceiver(UsrReceivers newReceiver) {
		this.newReceiver = newReceiver;
	}

	public List<UsrReceivers> getAddressReceiverList() {
		return addressReceiverList;
	}

	/**
	 * @return the user
	 */
	public UserUser getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(UserUser user) {
		this.user = user;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setIsTaobao(String isTaobao) {
		this.isTaobao = isTaobao;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @param emergencyContact
	 *            the emergencyContact to set
	 */
	public void setEmergencyContact(UsrReceivers emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	/**
	 * @param useruserProxy
	 *            the useruserProxy to set
	 */
	public void setUseruserProxy(UserUserProxy useruserProxy) {
		this.userUserProxy = useruserProxy;
	}

	/**
	 * @param userUserProxy
	 *            the userUserProxy to set
	 */
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}

	/**
	 * @return the userList
	 */
	public List<UserUser> getUserList() {
		return userList;
	}

	public String getMessage() {
		return message;
	}

	public boolean getCanTransfer() {
		return canTransfer;
	}

	public ViewPage getViewPage() {
		return viewPage;
	}

	public void setViewPageService(ViewPageService viewPageService) {
		this.viewPageService = viewPageService;
	}

	public boolean isHavePaymentPassword() {
		return havePaymentPassword;
	}

	public void setHavePaymentPassword(boolean havePaymentPassword) {
		this.havePaymentPassword = havePaymentPassword;
	}

	public ProdProductService getProdProductService() {
		return prodProductService;
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}

	public boolean isCardTypeRequired() {
		return cardTypeRequired;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public BCertificateTargetService getbCertificateTargetService() {
		return bCertificateTargetService;
	}

	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}

	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}

	public JSONArray getValidateBusinessCouponInfoJSON() {
		return validateBusinessCouponInfoJSON;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setProdProductRoyaltyService(
			ProdProductRoyaltyService prodProductRoyaltyService) {
		this.prodProductRoyaltyService = prodProductRoyaltyService;
	}

	public OrderResourceService getOrderResourceService() {
		return orderResourceService;
	}

	public void setOrderResourceService(
			OrderResourceService orderResourceService) {
		this.orderResourceService = orderResourceService;
	}

	public WorkOrderSenderBiz getWorkOrderProxy() {
		return workOrderProxy;
	}

	public void setWorkOrderProxy(WorkOrderSenderBiz workOrderProxy) {
		this.workOrderProxy = workOrderProxy;
	}

	public Boolean getTempCloseCashAccountPay() {
		return tempCloseCashAccountPay;
	}

	public void setTempCloseCashAccountPay(Boolean tempCloseCashAccountPay) {
		this.tempCloseCashAccountPay = tempCloseCashAccountPay;
	}

	public boolean getSupplierProduct() {
		return supplierProduct;
	}

	public void setSupplierProduct(boolean supplierProduct) {
		this.supplierProduct = supplierProduct;
	}

    public String getRefundSexplanation() {
        return refundSexplanation;
    }

    public void setRefundSexplanation(String refundSexplanation) {
        this.refundSexplanation = refundSexplanation;
    }

    public SmsRemoteService getSmsRemoteService() {
        return smsRemoteService;
    }

    public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
        this.smsRemoteService = smsRemoteService;
    }

    public String getSmsInfo() {
        return smsInfo;
    }

    public void setSmsInfo(String smsInfo) {
        this.smsInfo = smsInfo;
    }
}
