package com.lvmama.order.aop;

import static com.lvmama.comm.utils.UtilityTool.isNotNull;
import static com.lvmama.comm.utils.UtilityTool.isValid;

import java.util.List;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import com.lvmama.comm.bee.po.meta.MetaProductTicket;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Coupon;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.pet.vo.favor.FavorResult;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductBranchItemDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;

/**
 * 在生成订单之前对所传参数BuyInfo进行一些校验.
 * 
 * @author sunruyi
 * @see static com.lvmama.UtilityTool.isNotNull;
 * @see static com.lvmama.UtilityTool.isValid;
 * @see java.util.List;
 * @see org.aopalliance.intercept.MethodInterceptor;
 * @see org.aopalliance.intercept.MethodInvocation;
 * @see org.apache.log4j.Logger;
 * @see com.lvmama.ord.service.po.BuyInfo;
 * @see com.lvmama.ord.service.po.BuyInfo.Item;
 * @see com.lvmama.ord.service.po.Person
 * @see com.lvmama.prd.dao.ProdProductDAO;
 * @see com.lvmama.prd.dao.ProdTimePriceDAO;
 * @see com.lvmama.prd.po.ProdProduct;
 * @see com.lvmama.prd.po.TimePrice;
 * 
 */
public class ValidityOrderInfoAround implements MethodInterceptor {

	/**
	 * 日志对象.
	 */
	private static final Logger LOG = Logger
			.getLogger(ValidityOrderInfoAround.class);

	/**
	 * 产品DAO.
	 */
	private ProdProductDAO prodProductDAO;
	
	/**
	 * 类别DAO.
	 */
	private ProdProductBranchDAO prodProductBranchDAO;
	/**
	 * 时间价格DAO.
	 */
	private ProdTimePriceDAO prodTimePriceDAO;


	private ProdProductBranchItemDAO prodProductBranchItemDAO;
	

	/**
	 * invoke.<br>
	 * Implement this method to perform extra treatments before and after the
	 * invocation. Polite implementations would certainly like to invoke
	 * Joinpoint.proceed().
	 * 
	 * @param invocation
	 *            the method invocation joinpoint
	 * @return the result of the call to Joinpoint.proceed(), might be
	 *         intercepted by the interceptor.
	 * @throws java.lang.Throwable
	 *             - if the interceptors or the target-object throws an
	 *             exception.
	 */
	@Override
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		// 当调用的方法为createOrder时执行
		if (invocation.getMethod().getName().equalsIgnoreCase("createOrder")) {
			final BuyInfo buyInfo = (BuyInfo) invocation.getArguments()[0];
			// 校验UserID
			this.checkUserId(buyInfo.getUserId());
			// 校验PersonList
			this.checkPersonList(buyInfo.getPersonList());
			// 校验ItemList
			this.checkItemList(buyInfo);
			// 校验Coupon
			this.checkCoupon(buyInfo);
		}
		return invocation.proceed();
	}
	

	/**
	 * 校验userID.
	 * 
	 * @param userID
	 *            userID
	 */
	private void checkUserId(final String userID) {
		if (!isValid(userID)) {
			this.error("createOrder fail: buyInfo's userId is null");
		}
	}

	/**
	 * 校验personList.
	 * 
	 * @param personList
	 *            personList
	 */
	private void checkPersonList(final List<Person> personList) {
		if (!isNotNull(personList)) {
			this.error("createOrder fail: buyInfo's Person List is null");
		}
	}

	/**
	 * 校验ItemList.
	 * 
	 * @param itemList
	 *            itemList
	 */
	private void checkItemList(BuyInfo buyInfo) {
		final List<Item> itemList = buyInfo.getItemList();
		if (!isNotNull(itemList)) {
			this.error("createOrder fail: buyInfo's Item List is null");
		}		
		for (Item item : itemList) {
			// 只校验数量大于0的数据，等于0的不会被处理，不需要校验
			if (item.getQuantity() > 0) {
				this.checkProduct(item);
				if(buyInfo.isTodayOrder()){
					List<ProdProductBranchItem> list = prodProductBranchItemDAO.selectBranchItemByProdBranchId(item.getProductBranchId());
//					if(list.size()>1){
//						this.error("createOrder fail: today order item List greater 1 product count,productBranchId:"+item.getProductBranchId());
//					}
					for(ProdProductBranchItem ppbi:list){
						MetaProductTicket mp = (MetaProductTicket)metaProductDAO.getMetaProduct(ppbi.getMetaProductId(), Constant.PRODUCT_TYPE.TICKET.name());
						if(mp==null||!mp.hasTodayOrderAble()){
							this.error("createOrder fail,product null or todayOrderAble false");
						}
					}
				}
			}
		}
	}
	MetaProductDAO metaProductDAO;

	/**
	 * 校验Product.
	 * 
	 * @param item
	 *            item
	 */
	private void checkProduct(final Item item) {
		ProdProduct product = prodProductDAO.selectByPrimaryKey(item
				.getProductId());
		if (product == null) {
			this.error("createOrderItem fail: ProdProduct is null by ProductId : "
					+ item.getProductId());
		} else {
			ProdProductBranch branch=prodProductBranchDAO.selectByPrimaryKey(item.getProductBranchId());
			if(branch==null||!branch.getProductId().equals(product.getProductId())){
				this.error("createOrderItem fail: ProdProductBranch is null by ProdBranchId : "
						+ branch.getProductId()+",productId:"+product.getProductId());
			}
			this.checkTimePrice(item, product,branch);
		}
	}

	

	/**
	 * 校验TimePrice.
	 * 
	 * @param item
	 *            item
	 * @param product
	 *            product
	 */
	private void checkTimePrice(final Item item, final ProdProduct product,final ProdProductBranch branch) {
		TimePrice timePrice = this.prodTimePriceDAO.getProdTimePrice(product.getProductId(), branch.getProdBranchId(), item.getVisitTime());
		if (timePrice == null) {
			this.error("createOrderItem fail: ProdProduct's TimePrice is null by ProductId: "
					+ product.getProductId()
					+ " and VisitTime: "
					+ item.getVisitTime());
		}
	}

	/**
	 * 校验Coupon.
	 * 
	 * @param coupon
	 *            优惠券
	 */
	private void checkCoupon(final BuyInfo buyInfo){
		Coupon coupon = buyInfo.getCheckedCoupon();
		FavorResult favorResult = buyInfo.getFavorResult();
		if(coupon != null && favorResult != null && favorResult.getValidateCodeInfo() != null && favorResult.getValidateCodeInfo().getCouponId() != null 
				&& !favorResult.getValidateCodeInfo().getCouponId().equals(coupon.getCouponId())){
			this.error("createOrder fail: buyInfo's Coupon does not exist by CouponCode:"
					+ coupon.getCode()
					+ " CouponId:"
					+ coupon.getCouponId());
		}
	}
		
	/**
	 * 报错提示.
	 * 
	 * @param message
	 *            message
	 */
	private void error(final String message) {
		LOG.info(message);
		throw new RuntimeException(message);
	}

	/**
	 * 注入ProdProductDAO实例.
	 * 
	 * @param prodProductDAO
	 */
	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	/**
	 * 注入ProdTimePriceDAO实例.
	 * 
	 * @param prodTimePriceDAO
	 */
	public void setProdTimePriceDAO(ProdTimePriceDAO prodTimePriceDAO) {
		this.prodTimePriceDAO = prodTimePriceDAO;
	}

	/**
	 * @param prodProductBranchDAO the prodProductBranchDAO to set
	 */
	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}
	
	public void setProdProductBranchItemDAO(
			ProdProductBranchItemDAO prodProductBranchItemDAO) {
		this.prodProductBranchItemDAO = prodProductBranchItemDAO;
	}

	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}
}
