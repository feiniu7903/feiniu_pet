package com.lvmama.front.web.buy;

import com.lvmama.comm.bee.po.prod.ProdAvailableBonus;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdAvailableBonusService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.bee.vo.ord.PriceInfo;
import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.ord.TimePriceUtil;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.util.*;
@ParentPackage("json-default")
@SuppressWarnings("unused")
public class AjaxOrderInfoAction extends CreateOrderAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1620146521612976355L;
	private Log logger = LogFactory.getLog(AjaxOrderInfoAction.class);
	protected OrderService orderServiceProxy;
	PriceInfo priceInfo=new PriceInfo();
	private ProdProductService prodProductService;
	private ComLogService comLogService;
	private FavorService favorService;

    protected CashAccountVO moneyAccount;
    transient CashAccountService cashAccountService;
    private ProdAvailableBonusService prodAvailableBonusService;

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	@Action(value="/buy/ajaxPriceInfo")
	public void ajaxPriceInfo(){
		JSONResult result=new JSONResult(getResponse());		
		try {
			BuyInfo buyInfo=super.getOrderInfo();
			net.sf.json.JSONObject obj=new JSONObject();
			if(buyInfo.getItemList().isEmpty()) {
				obj.put("success", false);
				obj.put("msg", "未选购产品");
			} else {
				buyInfo.setFavorResult(favorService.calculateFavorResultByBuyInfo(buyInfo));
				priceInfo=orderServiceProxy.countPrice(buyInfo);

				if(!priceInfo.isSuccess()){
					obj.put("success", priceInfo.isSuccess());
					obj.put("msg", priceInfo.getMsg());
				}else{				
					obj=net.sf.json.JSONObject.fromObject(priceInfo);
				}

                moneyAccount = cashAccountService.queryMoneyAccountByUserNo(this.getUserId());
                
                //可使用奖金金额
                float availableCash=0;

                //校验是否可以使用奖金支付
                boolean canUseBonusPay=cashAccountService.canUseBonusPay(moneyAccount.getUserId());
                
                if(canUseBonusPay){
                	//先用新奖金
                    availableCash = moneyAccount.getNewBonusBalanceYuan() > priceInfo.getPrice() ? priceInfo.getPrice() : moneyAccount.getNewBonusBalanceYuan();
                    //如果新奖金不够用，再用按规则用老奖金
                    if (availableCash < priceInfo.getPrice()) {
                        //先找产品子类
                        ProdAvailableBonus prodAvailableBonus = prodAvailableBonusService.getProdAvailableBonusBySubProductType(buyInfo.getMainSubProductType());
                        //子类找不到，再找产品大类
                        if (prodAvailableBonus == null) {
                            prodAvailableBonus = prodAvailableBonusService.getProdAvailableBonusByMainProductType(buyInfo.getMainProductType());
                        }
                        if (prodAvailableBonus != null) {
                            availableCash += (prodAvailableBonus.getAmount() > moneyAccount.getBonusBalanceYuan() ? moneyAccount.getBonusBalanceYuan() : prodAvailableBonus.getAmount());
                        }
                        availableCash = availableCash > priceInfo.getPrice() ? priceInfo.getPrice() : availableCash;
                    }
                }else{
                    LOG.info("不可以使用奖金账户支付订单,order id:"+orderId+",user id:"+moneyAccount.getUserId());
                }
                obj.put("availableCash",(int)availableCash);
			}
			result.put("priceInfo", obj);
		} catch (IndexOutOfBoundsException e){
			result.raise("没有选中产品");
		} catch (Exception e) {
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
		}
		result.output();
	}
	@Action(value="/buy/ajaxCheckSock")
	public void ajaxCheckSock(){
		logger.info("Check stock begin..");
		JSONResult result=new JSONResult(getResponse());
		try{
			BuyInfo buyInfo = super.getOrderInfo();
			logger.info(buyInfo.getItemList().size());
			if(buyInfo.getItemList().isEmpty()) {
				throw new Exception("未选购产品");
			}
			if(Constant.PRODUCT_TYPE.TRAFFIC.name().equals(buyInfo.getMainProductType())&&Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(buyInfo.getMainSubProductType())){
				logger.info(this.buyInfo.getLocalCheck());
				buyInfo.setLocalCheck(this.buyInfo.getLocalCheck());
				if(TimePriceUtil.hasTrainSoldout()){
					throw new IllegalArgumentException("不在可售时间范围");
				}
			}
			//不定期校验有效期
			if(buyInfo.IsAperiodic()) {
				if(buyInfo.getValidBeginTime() != null && buyInfo.getValidEndTime() != null) {
					if(DateUtil.getDayStart(new Date()).after(buyInfo.getValidEndTime())) {
						throw new Exception("当前商品不可售");
					}
				} else {
					throw new Exception("当前商品不可售");
				}
			}
			ResultHandle handle=orderServiceProxy.checkOrderStock(buyInfo);
			if(handle.isFail()){
				result.raise(handle.getMsg());
			}
			if(buyInfo.getItemList()!=null && buyInfo.getItemList().size()>0){
				//故宫门票每笔订单只能限定5张
				if(prodProductRoyaltyService.getRoyaltyProductIds().contains(buyInfo.getItemList().get(0).getProductId())){
					int count=0;
					for(BuyInfo.Item item:buyInfo.getItemList()){
						count=item.getQuantity()+count;
					}
					if(count>5){
						result.raise("故宫门票一笔订单最多限购5张");
					}
				}
			}
		} catch (IndexOutOfBoundsException e) {
			result.raise("未选购产品");
			e.printStackTrace();
		} catch (Exception ex) {
			result.raise(ex);
			StackOverFlowUtil.printErrorStack(this.getRequest(), getResponse(), ex);
			ex.printStackTrace();
		}
		result.output();
	}
	
	
	
	@Action(value="/buy/ajaxCheckVisitorIsExisted")
	public void ajaxCheckVisitorIsExisted(){
		JSONResult result=new JSONResult(getResponse());
		try{
			//超级自由行
			if(buyInfo.hasSelfPack()) {
			} else if(5521==buyInfo.getProductId().longValue() || 93376 == buyInfo.getProductId().longValue()){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("productId", buyInfo.getProductId());
				params.put("userId", this.getUserId());
				params.put("contactMobile", this.getContact().getMobileNumber());
				params.put("productLimit", "true");
				params.put("beginTime", DateUtil.formatDate(DateUtil.getFirstdayOfMonth(new Date()), "yyyy-MM-dd"));
				params.put("endTime", DateUtil.formatDate(DateUtil.getLastdayOfMonth(new Date()), "yyyy-MM-dd"));
				ResultHandle isExisted = orderServiceProxy.checkCreateOrderLimitIsExisted(params);
				if(isExisted.isFail()) {
					result.raise(isExisted.getMsg());
				}
			}else if(prodProductRoyaltyService.getRoyaltyProductIds().contains(buyInfo.getProductId())){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("productId",buyInfo.getProductId());
				params.put("userId",this.getUserId());
				params.put("certNo",contact.getCardNum());
				params.put("gugongproductLimit", "true");
				params.put("visitTime",buyInfo.getVisitDate());
				ResultHandle isExisted = orderServiceProxy.checkCreateOrderLimitIsExisted(params);
				if(isExisted.isFail()) {
					result.raise(isExisted.getMsg());
				}
			}else {
				String userId = getUserId();
				ProdProduct product = this.prodProductService.getProdProduct(buyInfo.getProductId());
				if(product.isTraffic()&&Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(product.getSubProductType())){
					List<Person> personList = getPerson();
					List<Person> list = new ArrayList<Person>();
					for(Person person:personList){
						if(Constant.ORD_PERSON_TYPE.TRAVELLER.name().equalsIgnoreCase(person.getPersonType())){
							list.add(person);
						}
					}
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("productId", buyInfo.getProductId());
					param.put("personList", list);
					param.put("visitTime", buyInfo.getVisitDate());
					ResultHandle handle = orderServiceProxy.checkTrainOrderLimit(param);
					if(handle.isFail()){
						result.raise(handle.getMsg());
					}
				}else{
					String subProductType = buyInfo.getSubProductType();
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("productId", buyInfo.getProductId());
					params.put("userId", userId);
					params.put("visitTime", buyInfo.getVisitDate());
					params.put("subProductType", subProductType);
					List<String> travellerInfoOptions = product.getTravellerInfoOptionsList();
					if (null != travellerInfoOptions && !travellerInfoOptions.isEmpty()) {
						params.put("travellerInfoOptions", travellerInfoOptions);
					}
					
					String receiverName = contact.getReceiverName();
					System.out.println("test:" + (null != travellerInfoOptions && !travellerInfoOptions.isEmpty()));
					//酒店有必填信息，需填写游客或目的地自由行
					if((null != travellerInfoOptions && !travellerInfoOptions.isEmpty()) || Constant.ROUTE_SUB_PRODUCT_TYPE.FREENESS.name().equals(subProductType)) {
						receiverName = getUsrReceivers().get(1).getReceiverName();
					}
					params.put("visitorName", receiverName);
					Date leaveDate = null;
					//酒店单房型需离店日期
					if(Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(subProductType) && buyInfo.getLeaveTime() != null) {
						leaveDate = DateUtil.toDate(buyInfo.getLeaveTime(), "yyyy-MM-dd");
						params.put("leaveTime", leaveDate);
					}
					ResultHandle isExisted = orderServiceProxy.checkCreateOrderLimitIsExisted(params);
					if(isExisted.isFail()) {
						comLogService.insert("ORD_ORDER", null, null, getUserId(), "REPEATED_ORDER", "重复订单游玩人", "前台,产品id：" + buyInfo.getProductId() + ",用户id：" + userId + ",游玩时间：" + buyInfo.getVisitDate() + ",第一游玩人姓名：" + receiverName, "ORD_ORDER");
						result.raise(isExisted.getMsg());
					}
				}
			}
		}catch(Exception ex){
			result.raise(ex);
			StackOverFlowUtil.printErrorStack(this.getRequest(), getResponse(), ex);
		}
		result.output();
	}


	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	public void setFavorService(FavorService favorService) {
		this.favorService = favorService;
	}

    public CashAccountVO getMoneyAccount() {
        return moneyAccount;
    }

    public void setMoneyAccount(CashAccountVO moneyAccount) {
        this.moneyAccount = moneyAccount;
    }

    public CashAccountService getCashAccountService() {
        return cashAccountService;
    }

    public void setCashAccountService(CashAccountService cashAccountService) {
        this.cashAccountService = cashAccountService;
    }

    public ProdAvailableBonusService getProdAvailableBonusService() {
        return prodAvailableBonusService;
    }

    public void setProdAvailableBonusService(ProdAvailableBonusService prodAvailableBonusService) {
        this.prodAvailableBonusService = prodAvailableBonusService;
    }
}
