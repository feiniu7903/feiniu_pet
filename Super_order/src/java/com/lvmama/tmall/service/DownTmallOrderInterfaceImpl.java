package com.lvmama.tmall.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.tmall.OrdTmallMap;
import com.lvmama.comm.bee.po.tmall.TmallMemo;
import com.lvmama.comm.bee.po.tmall.TmallPerson;
import com.lvmama.comm.bee.service.DownTmallOrderInterface;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.tmall.OrdTmallMapService;
import com.lvmama.comm.bee.service.tmall.TOPInterface;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Coupon;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.ActivityUtil;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.logic.ProductTimePriceLogic;
import com.taobao.api.response.TradeFullinfoGetResponse;

public class DownTmallOrderInterfaceImpl implements DownTmallOrderInterface {

	private static final Log log = LogFactory.getLog(DownTmallOrderInterfaceImpl.class);
	private OrderService orderServiceProxy;
	private ProdProductDAO prodProductDAO;
	private ProdProductBranchDAO prodProductBranchDAO;
	private OrdTmallMapService ordTmallMapService;
	protected CashAccountService cashAccountService;
	private UserClient userClient;
	private UserUserProxy userUserProxy;
	private ProductTimePriceLogic productTimePriceLogic;
	private TopicMessageProducer resourceMessageProducer;
	private PayPaymentService payPaymentService;
	private MarkCouponService markCouponService;
	private FavorService favorService;

	@Override
	public void backDownOrder(String  tmallOrderNo, TradeFullinfoGetResponse response) throws Exception {
		// 是否注册获取用户id
		String userId = getUserId(response.getTrade().getReceiverMobile(),tmallOrderNo);
		if (userId == null) {
			return;
		}

		BuyInfo buyer = null;
		Person person = null;
		List<Person> personList = null;
		Item item = null;
		List<Item> items = null;
		OrdTimeInfo ordTimeInfo = null;
		List<OrdTimeInfo> ordTimeInfos = null;

		List<com.taobao.api.domain.Order> ords = response.getTrade().getOrders();
		TmallMemo memo = null;
		int paymentTradeNoSufix = 0;
		//默认旗子为蓝色，成功
		long returnFlag = 4L;
		StringBuffer callbackMemo = new StringBuffer();
		for (com.taobao.api.domain.Order ord : ords) {
			log.info("process taobao orderId:"+tmallOrderNo+",sub order numId:"+ord.getNumIid());
			paymentTradeNoSufix++;
			Long productId = null;
			Long branchId = null;
			String pro_categ =null;
			pro_categ=ord.getOuterSkuId();
			if(pro_categ==null){
				pro_categ=ord.getOuterIid();
			}
			if(pro_categ==null){
				pro_categ=ord.getTicketOuterId();
			}
			
			if (pro_categ != null && pro_categ.length() > 0) {
				try{
					if (pro_categ.indexOf(",") != -1) {
						String arrs[] = pro_categ.split(",");
						productId = Long.valueOf(arrs[0]);
						branchId = Long.valueOf(arrs[1]);
					} else {
						productId = Long.valueOf(pro_categ);
						branchId = Long.valueOf(pro_categ);
					}
				}catch(NumberFormatException e){
					OrdTmallMap orderNum = new OrdTmallMap();
					orderNum.setTmallOrderNo(tmallOrderNo);
					orderNum.setStatus("failure");
					orderNum.setTmallMemo(response.getTrade().getSellerMemo());
					orderNum.setFailedReason("产品ID或类别ID不是数字");
					//ordTmallMapService.updateByTmallOrderNoSelective(orderNum);
					ordTmallMapService.updateByOrdSelective(orderNum);
					TOPInterface.updateMemo(Long.valueOf(tmallOrderNo), "", 2L);// 调淘宝接口修改旗帜颜色
					log.error(response.getTrade().getTid() + " UserMemo Error!!>> "
							+ response.getTrade().getSellerMemo());
					returnFlag = 2L;
					continue;
				}
			}else{
				OrdTmallMap orderNum = new OrdTmallMap();
				orderNum.setTmallOrderNo(tmallOrderNo);
				orderNum.setStatus("failure");
				orderNum.setTmallMemo(response.getTrade().getSellerMemo());
				orderNum.setFailedReason("淘宝接口获取不到产品id和类别id");
				ordTmallMapService.updateByOrdSelective(orderNum);
				TOPInterface.updateMemo(Long.valueOf(tmallOrderNo), "", 2L);// 调淘宝接口修改旗帜颜色
				return;
			}
			
			// 检测此笔订单是否已经下单
			String orderStatus = getOrderStatus(tmallOrderNo, productId, branchId);
			if (!"create".equalsIgnoreCase(orderStatus)){
				continue;
			}
			if("failure".equalsIgnoreCase(orderStatus)||"unpay".equalsIgnoreCase(orderStatus)){
				returnFlag = 2L;
			}

			// 拆分每个子订单寻找关联备注信息
			memo = checkUserMemo(tmallOrderNo,productId,String.valueOf(branchId), response);
			if (memo == null) {
				returnFlag = 2L;
				continue;
			}

			long payment = PriceUtil.convertToFen(new BigDecimal(ord.getPayment()));
			Date payTime = response.getTrade().getPayTime();
			Date tourDate = memo.getTourDate();// 游玩日期
			String seller = memo.getSeller();// 淘宝客服
			// 产品信息校验
			ProdProduct proProduct = checkProductInfo(productId, branchId,ord.getNum(),
					Float.valueOf(ord.getPrice()), tourDate,tmallOrderNo);
			if (proProduct == null) {
				returnFlag = 2L;
				continue;
			}
			buyer = new BuyInfo();
			personList = new ArrayList<Person>();
			item = new Item();
			items = new ArrayList<BuyInfo.Item>();
			ordTimeInfo = new OrdTimeInfo();
			ordTimeInfos = new ArrayList<BuyInfo.OrdTimeInfo>();

			buyer.setIsAperiodic(proProduct.getIsAperiodic());
			//不定期产品取最后一天有效期
			if(proProduct.IsAperiodic()) {
				ProdProductBranch branch = prodProductBranchDAO.selectByPrimaryKey(branchId);
				tourDate = branch.getValidEndTime();
				item.setValidBeginTime(branch.getValidBeginTime());
				item.setValidEndTime(branch.getValidEndTime());
				buyer.setValidBeginTime(branch.getValidBeginTime());
				buyer.setValidEndTime(branch.getValidEndTime());
			}
			// 实例化item
			item.setVisitTime(tourDate);
			item.setProductId(Long.valueOf(productId));
			item.setProductBranchId(Long.valueOf(branchId));
			ordTimeInfo.setProductId(Long.valueOf(productId));
			ordTimeInfo.setProductBranchId(Long.valueOf(branchId));
			ordTimeInfo.setMarketPrice(proProduct.getMarketPrice());
			ordTimeInfo.setSellPrice(proProduct.getSellPrice());
			ordTimeInfo.setVisitTime(tourDate);
			item.setQuantity(ord.getNum().intValue());
			ordTimeInfo.setQuantity(ord.getNum());
			ordTimeInfos.add(ordTimeInfo);
			item.setTimeInfoList(ordTimeInfos);
			item.setIsDefault("true");
			items.add(item);

			// 实例化person
			List<TmallPerson> persons = memo.getPersons();
			boolean isContact = true;

			for (TmallPerson ps : persons) {
				person = new Person();
				if (ps.getName() != null && ps.getName().length() > 0) {
					person.setName(ps.getName());
				}
				if (ps.getMobile() != null && ps.getMobile().length() > 0) {
					person.setMobile(ps.getMobile().trim());
				}
				if (ps.getIdentity() != null && ps.getIdentity().length() > 0) {
					person.setCertNo(ps.getIdentity().trim());
					person.setCertType(Constant.CERTIFICATE_TYPE.ID_CARD.name());
				}
				person.setProvince(response.getTrade().getReceiverState());
				person.setCity(response.getTrade().getReceiverCity());
				person.setAddress(response.getTrade().getReceiverAddress());
				person.setPostcode(response.getTrade().getReceiverZip());

				if(isContact){
					person.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
					isContact = false;
				}else{
					person.setPersonType(Constant.ORD_PERSON_TYPE.TRAVELLER.name());
				}
				personList.add(person);
			}
			if(proProduct.getPayToLvmama().equalsIgnoreCase("true")&&proProduct.getPayToSupplier().equalsIgnoreCase("false")){
			   buyer.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
			}
			if(proProduct.getPayToLvmama().equalsIgnoreCase("false")&&proProduct.getPayToSupplier().equalsIgnoreCase("true")){
			   buyer.setPaymentTarget(Constant.PAYMENT_TARGET.TOSUPPLIER.name());
			}
			//buyer.setUserMemo("淘宝用户订单");
			buyer.setChannel(Constant.CHANNEL.TAOBAL.name());
			buyer.setUserId(userId);
			buyer.setPersonList(personList);
			buyer.setItemList(items);
			
			/****加入优惠券功能begin*****/
			/********如果此笔交易使用了优惠券，那么优惠券的金额会加在第一个子订单上******/
			if(paymentTradeNoSufix==1&&response.getTrade().getPromotionDetails()!=null&&response.getTrade().getPromotionDetails().size()>0){
				if(response.getTrade().getPromotionDetails().get(0).getDiscountFee()!=null&&Float.valueOf(response.getTrade().getPromotionDetails().get(0).getDiscountFee())>0){
					//response.getTrade().getPromotionDetails().get(0).getDiscountFee()
					/**如果购物车多笔订单，则第一笔订单的支付金额减去优惠券的金额***/
					if(response.getTrade().getOrders().size()>1){
						payment = PriceUtil.convertToFen(new BigDecimal(ord.getPayment()))-PriceUtil.convertToFen(new BigDecimal(response.getTrade().getPromotionDetails().get(0).getDiscountFee()));
					}
					buyer.setCouponList(getCouponListByMoney(PriceUtil.convertToFen(new BigDecimal(response.getTrade().getPromotionDetails().get(0).getDiscountFee()))));
					buyer.setFavorResult(favorService.calculateFavorResultByBuyInfo(buyer));
				}
			}
			/****加入优惠券功能end*****/
			boolean flag=checkIsCanDownOrder(buyer,response);
			if(flag==false){
				returnFlag=2L;
				continue;
			}
			
			// 后台下单
			OrdOrder lvorder = remoteBackDownOrder(buyer, tmallOrderNo, response, productId, branchId,seller);
			if (lvorder != null) {
				callbackMemo.append("\r\n驴妈妈订单号:").append(lvorder.getOrderId());
				if(lvorder.getPaymentTarget().equals("TOSUPPLIER")){
					returnFlag = 2L;
					continue;
				}
				boolean paymentSuccess = remotePayment(lvorder, response, payment, payTime, paymentTradeNoSufix, productId, branchId);
				if (!paymentSuccess){
					returnFlag = 2L;
				}
			}else{
				returnFlag = 2L;
			}
		}
		
		// 调淘宝接口更改旗帜颜色,并将驴妈妈订单号加上
		TOPInterface.updateMemo(Long.valueOf(tmallOrderNo), response.getTrade()
				.getSellerMemo() + "  " + callbackMemo.toString(), returnFlag);


	}
	
	/***
	 * 电子凭证订单下单接口
	 */
	@Override
	public void downEticketOrder(Map<String, String> data,TradeFullinfoGetResponse response) throws Exception {
		// 是否注册获取用户id
		String userId = getUserId(data.get("mobile"),data.get("order_id"));
		if (userId == null) {
			return;
		}
		List<com.taobao.api.domain.Order> ords = response.getTrade().getOrders();
		OrdOrder lvorder=null;
		List<OrdOrder> orders=new ArrayList<OrdOrder>();
		int paymentTradeNoSufix = 0;
		for (com.taobao.api.domain.Order ord : ords) {
			paymentTradeNoSufix++;
			Long productId = null;
			Long branchId = null;
//			String proBranch =null;
//			proBranch=ord.getOuterSkuId();
//			if(proBranch==null){
//				proBranch=ord.getOuterIid();
//			}
//			if(proBranch==null){
//				proBranch=data.get("outer_iid");
//			} 
			String proBranch=data.get("sub_outer_iid");
			if (proBranch != null && proBranch.length() > 0) {
				if (proBranch.indexOf(",") != -1) {
					String arrs[] = proBranch.split(",");
					productId = Long.valueOf(arrs[0]);
					branchId = Long.valueOf(arrs[1]);
				}
		}

			long payment = PriceUtil.convertToFen(new BigDecimal(ord.getPayment()));
			Date payTime = response.getTrade().getPayTime();
			Date tourDate = DateUtil.stringToDate(data.get("valid_start"), "yyyy-MM-dd"); //游玩日期为有效期开始时间
			// 产品信息校验
			ProdProduct proProduct = checkProductInfo(productId, branchId,ord.getNum(),Float.valueOf(ord.getPrice()), tourDate,data.get("order_id"));
			if (proProduct == null) {
				continue;
			}
			
			log.info("product check pass:"+productId+","+branchId);
			
			BuyInfo buyer = new BuyInfo();
			List<Person> personList = new ArrayList<Person>();
			Item item = new Item();
			List<Item> items = new ArrayList<BuyInfo.Item>();
			OrdTimeInfo ordTimeInfo = new OrdTimeInfo();
			List<OrdTimeInfo> ordTimeInfos = new ArrayList<BuyInfo.OrdTimeInfo>();
			
			buyer.setIsAperiodic(proProduct.getIsAperiodic());
			//不定期产品取最后一天有效期
			if(proProduct.IsAperiodic()) {
				ProdProductBranch branch = prodProductBranchDAO.selectByPrimaryKey(branchId);
				tourDate = branch.getValidEndTime();
				item.setValidBeginTime(branch.getValidBeginTime());
				item.setValidEndTime(branch.getValidEndTime());
				buyer.setValidBeginTime(branch.getValidBeginTime());
				buyer.setValidEndTime(branch.getValidEndTime());
			}

			// 实例化item
			item.setVisitTime(tourDate);
			item.setProductId(Long.valueOf(productId));
			item.setProductBranchId(Long.valueOf(branchId));
			ordTimeInfo.setProductId(Long.valueOf(productId));
			ordTimeInfo.setProductBranchId(Long.valueOf(branchId));
			ordTimeInfo.setMarketPrice(proProduct.getMarketPrice());
			ordTimeInfo.setSellPrice(proProduct.getSellPrice());
			ordTimeInfo.setVisitTime(tourDate);
			item.setQuantity(ord.getNum().intValue());
			ordTimeInfo.setQuantity(ord.getNum());
			ordTimeInfos.add(ordTimeInfo);
			item.setTimeInfoList(ordTimeInfos);
			items.add(item);

			// 实例化person

			Person person = new Person();
			person.setName(response.getTrade().getBuyerNick());
			person.setMobile(data.get("mobile"));
			person.setProvince(response.getTrade().getReceiverState());
			person.setCity(response.getTrade().getReceiverCity());
			person.setAddress(response.getTrade().getReceiverAddress());
			person.setPostcode(response.getTrade().getReceiverZip());
			person.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
			String idCard = data.get("id_card");
			if( StringUtil.isNotEmptyString(idCard) ){
				person.setCertType(Constant.CERT_TYPE.ID_CARD.getCode());
				person.setCertNo(idCard);
			}
		    personList.add(person);
		    
			if(proProduct.getPayToLvmama().equalsIgnoreCase("true")&&proProduct.getPayToSupplier().equalsIgnoreCase("false")){
			   buyer.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
			}
			if(proProduct.getPayToLvmama().equalsIgnoreCase("false")&&proProduct.getPayToSupplier().equalsIgnoreCase("true")){
			   buyer.setPaymentTarget(Constant.PAYMENT_TARGET.TOSUPPLIER.name());
			}
			//buyer.setUserMemo("淘宝用户订单");
			buyer.setChannel(Constant.CHANNEL.TAOBAL.name());
			buyer.setUserId(userId);
			buyer.setPersonList(personList);
			buyer.setItemList(items);
			
			boolean flag=checkIsCanDownOrder(buyer,response);
			if(flag==false){
				continue;
			}
			
			// 后台下单
			log.info("begin call orderService to down order !!!");
			
			lvorder = remoteBackDownOrder(buyer, data.get("order_id"), response, productId, branchId,"syetem");
			if (lvorder != null) {
				orders.add(lvorder);
				if(lvorder.getPaymentTarget().equals("TOSUPPLIER")){
					continue;
				}
				
				log.info("begin call paymentService to pay order :" +lvorder.getOrderId());
				
			 remotePayment(lvorder, response, payment, payTime, paymentTradeNoSufix, productId, branchId);
			}
		}
	  }
	

	// 根据手机号检测用户是否渠道注册
	public String getUserId(String mobileNumber,String tmallOrderNo) {
		//String mobileNumber = order.getBuyerMobile();
		String userId = null;
		Long id = 0L;
		UserUser userUser = null;
			if (mobileNumber != null&&StringUtil.validMobileNumber(mobileNumber)) {
				if (mobileNumber.charAt(0) == '0') {
					mobileNumber = mobileNumber.substring(1, mobileNumber.length());
				}
				try {
					String smsContent = "亲，感谢您惠顾驴妈妈淘宝商城！正在为您处理订单，请耐心等待。注意及时查收短信。客服电话：021-60561630. 祝您旅途愉快。";
					id = this.userClient.batchRegisterWithChannel(mobileNumber, null, null, null,
							smsContent, null, null, "010936");
					if (id == null) {
						userUser = userUserProxy.getUsersByMobOrNameOrEmailOrCard(mobileNumber);
						userId = userUser.getUserId();
					} else {
						userUser = userUserProxy.getUserUserByPk(id);
						userId = userUser.getUserId();
					}
				} catch (Exception e) {
					log.error(mobileNumber + "getUserId", e);
					OrdTmallMap ord = new OrdTmallMap();
					ord.setTmallOrderNo(tmallOrderNo);
					ord.setStatus("failure");
					ord.setFailedReason("注册用户异常");
					ordTmallMapService.updateByTmallOrderNoSelective(ord);
					TOPInterface.updateMemo(Long.valueOf(tmallOrderNo), "", 2L);// 调淘宝接口修改旗帜颜色
					return null;
				}
			}else{
				log.error("is not mobile number error:"+mobileNumber);
				OrdTmallMap ord = new OrdTmallMap();
				ord.setTmallOrderNo(tmallOrderNo);
				ord.setStatus("failure");
				ord.setFailedReason("淘宝用户联系方式不是正确的手机号码!");
				ordTmallMapService.updateByTmallOrderNoSelective(ord);
				TOPInterface.updateMemo(Long.valueOf(tmallOrderNo), "", 2L);// 调淘宝接口修改旗帜颜色
				return null;
			}
		return userId;
	}

	// 备注信息校验 如返回为null,调用程序终止代码执行
	public TmallMemo checkUserMemo(String tmallOrderNo,Long productId, String branchId,
			TradeFullinfoGetResponse response) {
		// 将备注信息封装为map对象
		TmallMemo memo = null;
		Map<String, TmallMemo> map = TmallMemo.processMemo(response.getTrade().getSellerMemo());
		if (map != null) {
			memo = (TmallMemo) map.get(branchId);
		}

		String failedReason = "";
		if (map == null){
			failedReason = "没有备注信息或备注信息格式不正确";
		}else if (memo == null){
			failedReason = "备注类别ID和淘宝类别ID不同，或者备注信息不正确";
		}else if(memo.getTourDate() == null ){
			ProdProduct product = prodProductDAO.selectByPrimaryKey(productId);
			//非不定期产品才做游玩日期校验
			if(!product.IsAperiodic()) {
				failedReason = "游玩日期不正确";
			}
		}else if(memo.getSeller() == null){
			failedReason = "淘宝客服不正确";
		}
		boolean isMobileNo=true;
		boolean isIdentiNo=true;
		if(memo!=null&&"".equals(failedReason)){
			for(TmallPerson pes:memo.getPersons()){
				boolean isMobile=StringUtil.validMobileNumber(pes.getMobile().trim());
				if(isMobile==false){
					isMobileNo=false;
				}
				if(pes.getIdentity()!=null){
					boolean	isIdentity=IDCardValidate(pes.getIdentity().trim());
					if(isIdentity==false){
						isIdentiNo=false;
					}
					}
			}
			
			if(isMobileNo==false&&isIdentiNo==true){
				failedReason="手机号码不正确";
			}
			if(isMobileNo==true&&isIdentiNo==false){
				failedReason="身份证号码不正确";
			}
			if(isMobileNo==false&&isIdentiNo==false){
				failedReason="手机号码和身份证号码不正确";
			}
		}
		
		
		if (!"".equals(failedReason)) {
			OrdTmallMap ord = new OrdTmallMap();
			ord.setTmallOrderNo(tmallOrderNo);
			ord.setProductId(productId);
			ord.setCategoryId(Long.valueOf(branchId));
			ord.setStatus("failure");
			ord.setTmallMemo(response.getTrade().getSellerMemo());
			ord.setFailedReason(failedReason);
			try {
				ordTmallMapService.updateByOrdSelective(ord);
			} catch (Exception e) {
				e.printStackTrace();
				OrdTmallMap ordmap = new OrdTmallMap();
				ordmap.setTmallOrderNo(tmallOrderNo);
				ordmap.setProductId(productId);
				ordmap.setCategoryId(Long.valueOf(branchId));
				ordmap.setStatus("failure");
				ordmap.setFailedReason("插入备注失败信息异常");
				ordTmallMapService.updateByOrdSelective(ordmap);
			}
			//TOPInterface.updateMemo(Long.valueOf(order.getTmallOrderNo()), "", 2L);// 调淘宝接口修改旗帜颜色
			log.error(response.getTrade().getTid() + " UserMemo Error!!>> "
					+ response.getTrade().getSellerMemo());
			return null;
		}

		return memo;

	}

	// 产品信息校验
	public ProdProduct checkProductInfo(Long productId, Long branchId, Long quantity, Float price, Date tourDate,
			String tid) {
		ProdProduct prodProduct = null;
		ProdProductBranch prodProductBranch=null;
//		String productType=null;
		Long maxNum;
		String gtMaxNum="false";
		try {
			prodProduct = prodProductDAO.selectByPrimaryKey(productId);
			prodProductBranch=prodProductBranchDAO.selectByPrimaryKey(branchId);
			//不定期订单,取类别的最后一天有效期做游玩日期
			if(prodProduct != null && prodProductBranch != null && prodProduct.IsAperiodic()) {
				Date validEndTime = prodProductBranch.getValidEndTime();
				if(prodProductBranch.getValidBeginTime() != null && validEndTime != null 
						&& !DateUtil.getDayStart(new Date()).after(validEndTime)) {
					tourDate = validEndTime;
				} else {
					tourDate = null;
				}
			}
			String failedReason = "";
			TimePrice timePrice = null;
			if(prodProduct.IsAperiodic() && tourDate == null) {
				failedReason = "该期票产品不可售";
			} else {
				timePrice = productTimePriceLogic.calcProdTimePrice(Long.valueOf(branchId),
					tourDate);
			}
			float lvTimePrice = 0;
			if (timePrice != null) {
				lvTimePrice = PriceUtil.convertToYuan(timePrice.getPrice());
			}
			//五周年庆 时间价格不校验\
			String key="LVMAMA_PROMOTION_519";
			if(MemcachedUtil.getInstance().get(key)==null){
				if (timePrice == null || prodProduct == null||prodProductBranch == null) {
					failedReason = "产品时间价格表不存在";
				}else if(!Float.valueOf(price).equals(lvTimePrice)){
					failedReason = "淘宝价格与驴妈妈价格不相同";
				}	
			}
//			if(prodProduct!=null){
//				productType=prodProduct.getProductType();
//			}
			if(prodProductBranch!=null){
				maxNum=prodProductBranch.getMaximum();
				if(maxNum<quantity){
					gtMaxNum="true";
				}
			}
			if (!"".equals(failedReason)){
				OrdTmallMap order = new OrdTmallMap();
				order.setTmallOrderNo(tid);
				order.setProductId(productId);
//				order.setProductType(productType);
				order.setGtMaxNum(gtMaxNum);
				order.setCategoryId(branchId);
				order.setStatus("failure");
				order.setFailedReason(failedReason);
				ordTmallMapService.updateByOrdSelective(order);
			//	TOPInterface.updateMemo(Long.valueOf(tid), "", 2L);
				return null;
			}else{
				OrdTmallMap order = new OrdTmallMap();
				order.setTmallOrderNo(tid);
				order.setGtMaxNum(gtMaxNum);
				order.setProductId(productId);
//				order.setProductType(productType);
				order.setCategoryId(branchId);
				ordTmallMapService.updateByOrdSelective(order);
			}
		} catch (Exception e) {
			log.error(this.getClass(), e);
			OrdTmallMap order = new OrdTmallMap();
			order.setTmallOrderNo(tid);
			order.setProductId(productId);
//			order.setProductType(productType);
			order.setGtMaxNum(gtMaxNum);
			order.setCategoryId(branchId);
			order.setStatus("failure");
			order.setFailedReason("产品信息校验不通过");
			ordTmallMapService.updateByOrdSelective(order);
//			TOPInterface.updateMemo(Long.valueOf(tid), "", 2L);
			return null;
		}
		return prodProduct;
	}

	// 远程调用订单服务
	public OrdOrder remoteBackDownOrder(BuyInfo buyer, String tmallOrderNo,
			TradeFullinfoGetResponse response, Long productId, Long branchId, String seller) {
		OrdTmallMap orderTmallMap = new OrdTmallMap();
		OrdOrder lvorder = null;
		// 开始调用后台下单接口进行后台下单
		try {
			lvorder = orderServiceProxy.createOrderWithOperatorId(buyer, "system");
			// 根据后台下单结果 调淘宝备忘更改接口 更改旗帜颜色
			if (lvorder != null) {
				Long id = lvorder.getOrderId();

				OrdOrderMemo orderMemo = new OrdOrderMemo();
				orderMemo.setOrderId(id);
				orderMemo.setType("淘宝订单备注");
				orderMemo.setContent("淘宝商城   " + response.getTrade().getBuyerNick());
				orderMemo.setOperatorName(seller);
				orderMemo.setCreateTime(new Date());
				orderServiceProxy.saveMemo(orderMemo, seller);
				boolean isCertificate=ordTmallMapService.selectCertificateType(id);
				if(isCertificate==true){
					orderTmallMap.setCertificate("true");
				}else{
					orderTmallMap.setCertificate("false");
				}
				orderTmallMap.setLvOrderId(id);
				orderTmallMap.setResourceConfirmStatus(lvorder.getResourceConfirmStatus());
				if(lvorder.getPaymentTarget().equals("TOSUPPLIER")){
					orderTmallMap.setStatus("unpay");
				}else{
					orderTmallMap.setStatus("success");
				}
				orderTmallMap.setTmallMemo(response.getTrade().getSellerMemo());
				orderTmallMap.setSeller(seller);
				orderTmallMap.setTmallOrderNo(tmallOrderNo);
				orderTmallMap.setProductId(productId);
				orderTmallMap.setCategoryId(branchId);
				orderTmallMap.setOperatorName("system");
				orderTmallMap.setProcessTime(new Date());
				ordTmallMapService.updateByOrdSelective(orderTmallMap);
			}
		} catch (Exception e) {
			log.error(tmallOrderNo + ":" + productId + ":" + branchId
					+ "  remoteBackDownOrder Error!!!", e);
			orderTmallMap.setStatus("failure");
			orderTmallMap.setTmallMemo(response.getTrade().getSellerMemo());
			orderTmallMap.setSeller(seller);
			orderTmallMap.setFailedReason("调用下单接口失败");
			orderTmallMap.setTmallOrderNo(tmallOrderNo);
			ordTmallMapService.updateByTmallOrderNoSelective(orderTmallMap);
//			TOPInterface.updateMemo(Long.valueOf(tmallOrderNo), "", 2L);
			return null;
		}

		return lvorder;
	}

	// 远程支付接口调用
	public boolean remotePayment(OrdOrder lvorder, TradeFullinfoGetResponse response, long payment,
			Date payTime, int loop, Long productId, Long branchId) {
		boolean flag = true;
		try {
			PayPayment payPayment = new PayPayment();
			payPayment.setBizType(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.getCode());
			payPayment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
			payPayment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
			payPayment.setObjectId(lvorder.getOrderId());
			payPayment.setSerial(payPayment.geneSerialNo());
			payPayment.setPaymentGateway(Constant.PAYMENT_GATEWAY.ALIPAY_OFFLINE.name());
			payPayment.setAmount(payment);
			payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			payPayment.setCallbackTime(payTime);
			payPayment.setCreateTime(new Date());
			payPayment.setGatewayTradeNo(response.getTrade().getAlipayNo());
			payPayment.setRefundSerial(response.getTrade().getAlipayNo());
			if (response.getTrade().getOrders().size() > 1) {
				payPayment.setPaymentTradeNo(String.valueOf(response.getTrade().getTid()) + "_" + loop);
			} else {
				//ordPayment.setGatewayTradeNo(response.getTrade().getAlipayNo());
				payPayment.setPaymentTradeNo(String.valueOf(response.getTrade().getTid()));
			}
			Long paymentId = payPaymentService.savePayment(payPayment);
			resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
		} catch (Exception e) {
			log.error(this.getClass(), e);
			OrdTmallMap orderTmallMap = new OrdTmallMap();
			orderTmallMap.setStatus("unpay");
			orderTmallMap.setProductId(productId);
			orderTmallMap.setCategoryId(branchId);
			orderTmallMap.setFailedReason("支付失败");
			orderTmallMap.setTmallOrderNo(String.valueOf(response.getTrade().getTid()));
			ordTmallMapService.updateByTmallOrderNoSelective(orderTmallMap);
			flag = false;
		}
		return flag;

	}

	// 下单之前根据淘宝id,产品id，类别id进行查询此笔订单状态 success 不下单
	public String getOrderStatus(String tid, Long productId, Long branchId) {
		try {
			OrdTmallMap orderTmallMap = ordTmallMapService.getOrderByUK(tid, productId,
					branchId);
			return orderTmallMap.getStatus();
		} catch (Exception e) {
			log.error("checkOrderStatus(" + tid + productId + branchId + ") Exception", e);
			return null;
		}
	}
	
	//buyinfo对象下单前的检查
	public boolean checkIsCanDownOrder(BuyInfo buyinfo,TradeFullinfoGetResponse response){
		boolean flag=true;
		ResultHandle result=orderServiceProxy.checkOrderStock(buyinfo); 
		if(result.isFail()){
			OrdTmallMap orderTmallMap = new OrdTmallMap();
			orderTmallMap.setStatus("failure");
			orderTmallMap.setProductId(buyinfo.getItemList().get(0).getProductId());
			orderTmallMap.setCategoryId(buyinfo.getItemList().get(0).getProductBranchId());
			orderTmallMap.setFailedReason("BuyInfo检查失败："+result.getMsg());
			orderTmallMap.setTmallOrderNo(String.valueOf(response.getTrade().getTid()));
			ordTmallMapService.updateByTmallOrderNoSelective(orderTmallMap);
		}
		return flag;
	}
	
	//根据淘宝优惠金额查找优惠批次id
	//淘宝优惠金额生成的批次号码需要在此加入,单位为分，如300L表示3元
	public List<Coupon> getCouponListByMoney(Long moneyNum){
		HashMap<Long, Long> couponidMap=new HashMap<Long, Long>();
		couponidMap.put(300L, 3384L);
		couponidMap.put(500L, 3385L);
		couponidMap.put(1000L,3386L);
		couponidMap.put(2000L,3387L);
		couponidMap.put(5000L, 4397L);
		couponidMap.put(10000L, 4398L);
		couponidMap.put(20000L, 4441L);
		MarkCouponCode markCouponCode=markCouponService.generateSingleMarkCouponCodeByCouponId(couponidMap.get(moneyNum));
		List<Coupon> couponList=new ArrayList<Coupon>();
		Coupon coupon=new Coupon();
		coupon.setCouponId(markCouponCode.getCouponId());
		coupon.setCode(markCouponCode.getCouponCode());
		coupon.setChecked("true");
		couponList.add(coupon);
		return couponList;
	}

    //身份证号码的简单验证
    public boolean IDCardValidate(String IDStr){
    	boolean result=true;
        String Ai = "";  
        // ================ 号码的长度 15位或18位 ================  
        if (IDStr.length() != 15 && IDStr.length() != 18) {  
        	result=false; 
            return result;  
        }  
        // ================ 数字 除最后以为都为数字 ================  
        if (IDStr.length() == 18) {  
            Ai = IDStr.substring(0, 17);  
        } else if (IDStr.length() == 15) {  
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);  
        }  
        if (isNumeric(Ai) == false) {  
        	result=false; 
            return result;  
        }
		return result; 
    }
    
    /** 
     * 功能：判断字符串是否为数字 
     * @param str 
     * @return 
     */ 
    private static boolean isNumeric(String str) {  
        Pattern pattern = Pattern.compile("[0-9]*");  
        Matcher isNum = pattern.matcher(str);  
        if (isNum.matches()) {  
            return true;  
        } else {  
            return false;  
        }  
    }

	public void setOrdTmallMapService(OrdTmallMapService ordTmallMapService) {
		this.ordTmallMapService = ordTmallMapService;
	}

	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}

	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setResourceMessageProducer(TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}
	

	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}

}
