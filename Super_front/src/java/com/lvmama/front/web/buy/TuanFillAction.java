package com.lvmama.front.web.buy;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.distribution.DistributionTuanCoupon;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.ord.DistributionOrderService;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.vo.UsrReceivers;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.bee.vo.ord.BuyInfo.Item;
import com.lvmama.comm.bee.vo.ord.BuyInfo.OrdTimeInfo;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.pet.service.prod.ProductHeadQueryService;
import com.lvmama.comm.pet.service.user.IReceiverUserService;
import com.lvmama.comm.utils.ActivityUtil;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ProdCProduct;
import com.lvmama.comm.vo.TimeInfo;
import com.lvmama.comm.vo.ViewBuyInfo;
import com.lvmama.front.web.BaseAction;

@Results( {
	@Result(name = "home", location = "http://www.lvmama.com", type = "redirect"),
	@Result(name = "tuanFill", location = "/WEB-INF/pages/tuangou/writeInfo.ftl", type = "freemarker"),
	@Result(name = "success", location = "/WEB-INF/pages/tuangou/tuan_success.ftl", type = "freemarker"),
	@Result(name = "fail", location = "/WEB-INF/pages/tuangou/tuan_fail.ftl", type = "freemarker")
})
/**
 * 团购预约、生成下单页
 * 
 * @author zenglei
 *
 */
public class TuanFillAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6406177835257324546L;
	private ViewBuyInfo buyInfo;
	private ProdProductBranch mainProdBranch;
	private List<ProdProductBranch> relatedProductList;
	
	private String verifycode;
	private String batchId;
	private List<DistributionTuanCoupon> tuanCouponLists = new ArrayList<DistributionTuanCoupon>();
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(OrderFillAction.class);
	
	private ProductHeadQueryService productServiceProxy;
	private PageService pageService;
	private List<String> firstTravellerInfoOptions; //第一游玩人的必填信息
	private List<String> travellerInfoOptions;  //游玩人的必填信息
	private List<String> contactInfoOptions;  //取票人的必填信息
	
	/** 取票人/联系人 */
	protected UsrReceivers contact = new UsrReceivers();
	/** 游客 */
	protected List<UsrReceivers> travellerList = new ArrayList<UsrReceivers>();
	/** 紧急联系人 **/
	protected UsrReceivers emergencyContact;
	/** 订票人 */
	protected UsrReceivers booker;
	
	private IReceiverUserService receiverUserService;
	private DistributionOrderService distributionOrderServiceProxy; 
	
	private String vs;
	/**
	 * 
	 * @return
	 */
	@Action("/buy/distributionFill")
	public String initFillOrderData() {
		if (null == buyInfo||buyInfo.getProdBranchId()==null) {
			LOG.error("提交的buyInfo数据信息不全!跳转回主站.");
			return "home";
		}
		ProdProductBranch prodProductBranch = pageService.getProdBranchByProdBranchId(buyInfo.getProdBranchId());
		if(prodProductBranch == null) {
			LOG.error("类别不存在");
			return "home";
		}
		boolean isAperiodic = prodProductBranch.getProdProduct().IsAperiodic();
		//不定期产品用最后一天有效期做校验
		if(isAperiodic) {
			mainProdBranch=productServiceProxy.getProdBranchDetailByProdBranchId(buyInfo.getProdBranchId(),prodProductBranch.getValidEndTime());
		} else {
			//现在传入的为产品类别ID.为保证前台参数传的简单性,自己计算对应的产品ID.
			mainProdBranch=productServiceProxy.getProdBranchDetailByProdBranchId(buyInfo.getProdBranchId(),buyInfo.getVisitDate());
		}
		if(mainProdBranch==null){
			LOG.error("类别不存在");
			return "home";
		}
		ProdProduct prodProduct = mainProdBranch.getProdProduct();
		if(!prodProduct.isSellable()){
			LOG.error("当前产品"+buyInfo.getProductId()+" 已经过了上下线时间"); 
			return "home";
		} else if(!prodProduct.isOnLine()) {
			LOG.error("当前产品"+buyInfo.getProductId()+"未上线online="+prodProduct.getOnLine());
			return "home";
		} else if(!pageService.checkDateCanSale(buyInfo.getProductId(),buyInfo.getVisitDate())) { 
			LOG.error("当前产品"+buyInfo.getProductId()+"游玩时间"+DateUtil.getDateTime("yyyy-MM-dd", buyInfo.getVisitDate())+"有时间限制"); 
			return "home";
		}
		// 初始化游玩人必填信息
		Map<String, Object> data = pageService.getProdCProductInfo(prodProductBranch.getProductId(), false);
		ProdCProduct prodCProduct = (ProdCProduct) data.get("prodCProduct");
		//初始化游玩人信息
		initOptionInfo(prodCProduct.getProdProduct());
		buyInfo.setProductId(mainProdBranch.getProductId());
		
		vs = getRequestValidateMd5();
		return "tuanFill";
	}
	
	/**
	 * 校验团购券码、提交订单
	 * 
	 * @return
	 */
    @Action("/product/submitDistributionOrder")
	public String createChannelTicketOrder(){
    	String validateString = getRequestValidateMd5();
    	mainProdBranch = pageService.getProdBranchByProdBranchId(buyInfo.getProdBranchId());
		if(mainProdBranch == null) {
			LOG.error("类别不存在");
			return "home";
		}
    	//校验数据完整性
    	if(StringUtil.isNotEmptyString(validateString) && StringUtil.isNotEmptyString(this.vs) && validateString.equals(this.vs)){
	    	BuyInfo createOrderBuyInfo = new BuyInfo();
	    	buyInfo.getProdBranchId();
			createOrderBuyInfo.setMainProductType(buyInfo.getProductType());
			createOrderBuyInfo.setMainSubProductType(buyInfo.getSubProductType());
			//createOrderBuyInfo.setUserId(this.getBookerUserId());
			createOrderBuyInfo.setChannel(buyInfo.getChannel());
			createOrderBuyInfo.setWaitPayment(Constant.WAIT_PAYMENT.PW_DEFAULT.getValue());
			
			List<Item> list = this.getItem();	
			createOrderBuyInfo.setPersonList(this.getPerson());
			createOrderBuyInfo.setItemList(list);
			
			List<String> tuanCoupons = new ArrayList<String>();
			for (int i = 0; i < tuanCouponLists.size(); i++) {
				tuanCoupons.add(tuanCouponLists.get(i).getDistributionCouponCode());
			}
			OrdOrder flag = distributionOrderServiceProxy.createOrderByCouponCode(createOrderBuyInfo, tuanCoupons);
			if(flag != null){
				return "success";
			}else{
				return "fail";
			}
    	}else{
    		LOG.error("=======表单前后数据不致");
    		return "fail";
    	}
	}
    
    
    /**
  	 * 初始化游玩人和联系人的必填信息
  	 * @param product 购买主产品
  	 */
    private void initOptionInfo(ProdProduct product) {
		//联系人的手机和姓名必填
		contactInfoOptions = new ArrayList<String>();
		contactInfoOptions.add(Constant.TRAVELLER_INFO_OPTIONS.NAME.getCode());
		contactInfoOptions.add(Constant.TRAVELLER_INFO_OPTIONS.MOBILE.getCode());
		
		travellerInfoOptions = product.getTravellerInfoOptionsList();
		//System.out.println(travellerInfoOptions);
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
	}
	
  	/**
	 * 获取游客填写的联系信息
	 * 
	 * @return
	 */
	public List<Person> getPerson() {
		List<Person> personList = new ArrayList<Person>();

		// 联系人
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(this.getContact()
				.getMobileNumber())) {
			personList.add(this.setPerson(this.getContact(),
					Constant.ORD_PERSON_TYPE.CONTACT.name()));
		}
		// 游客信息
		List<UsrReceivers> travellerList=getUsrReceivers();
		for (int i = 0; i < travellerList.size(); i++) {
			UsrReceivers usrReceivers = travellerList.get(i);
			if (usrReceivers != null) {
				Person person = this.setPerson(usrReceivers,Constant.ORD_PERSON_TYPE.TRAVELLER.name());
				personList.add(person);	
			}
		}
		//紧急联系人
		if (!StringUtils.isEmpty(getRequest().getParameter("emergencyContact.mobileNumber")) && !StringUtils.isEmpty(getRequest().getParameter("emergencyContact.receiverName"))) {
			emergencyContact = new UsrReceivers();
			emergencyContact.setReceiverName(getRequest().getParameter("emergencyContact.receiverName"));
			emergencyContact.setMobileNumber(getRequest().getParameter("emergencyContact.mobileNumber"));
			personList.add(setPerson(emergencyContact, Constant.ORD_PERSON_TYPE.EMERGENCY_CONTACT.name()));
		} 
		// 预订人信息
		/*if (isLogin()) {
			UserUser users = this.getUser();
			this.booker = new UsrReceivers();
			this.booker.setUserId(users.getUserId());
			this.booker.setReceiverName(users.getRealName());
			this.booker.setEmail(users.getEmail());
			if (org.apache.commons.lang3.StringUtils.isNotEmpty(users
					.getMobileNumber())) {
				this.booker.setMobileNumber(users.getMobileNumber());
			} else {
				this.booker.setMobileNumber(this.getContact().getMobileNumber());
			}
			personList.add(this.setPerson(this.booker,
					Constant.ORD_PERSON_TYPE.BOOKER.name()));
		} else {
			if (org.apache.commons.lang3.StringUtils.isNotEmpty(this.getContact()
					.getMobileNumber())) {
				personList.add(this.setPerson(this.getContact(),
						Constant.ORD_PERSON_TYPE.BOOKER.name()));
			}
		}*/
		return personList;
	}
  	
  	private Item getHotelSigelRoomItem(){
		Item item = new Item();
		List<OrdTimeInfo> ordTimeInfoList = new ArrayList<OrdTimeInfo>();
		for (TimeInfo timeInfo : buyInfo.getTimeInfo()) {			
			OrdTimeInfo ordTimeInfo = new OrdTimeInfo();
			ProdProductBranch branch=pageService.getProdBranchByProdBranchId(timeInfo.getProductId());
			
			ordTimeInfo.setProductBranchId(branch.getProdBranchId());
			ordTimeInfo.setProductId(branch.getProductId());
			item.setProductBranchId(branch.getProdBranchId());
			item.setProductId(branch.getProductId());
			ordTimeInfo.setQuantity(timeInfo.getQuantity());
			try{
			ordTimeInfo.setVisitTime(timeInfo.getVisitDate());
			}catch(Exception ex){
				StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
			}
			ordTimeInfoList.add(ordTimeInfo);
		}
		item.setTimeInfoList(ordTimeInfoList);
		item.setQuantity(getHotelRoomQuantity(buyInfo.getTimeInfo()));
		item.setVisitTime(getSigelRoomFistVisitDate(buyInfo.getTimeInfo()));
		return item;
	}
  	
  	/**
	 * 排序获得酒店最近入住日期
	 * @param timeInfoList
	 * @return
	 */
	private Date getSigelRoomFistVisitDate(List<TimeInfo> timeInfoList){
		List<Date> dateList = new ArrayList<Date>();
		for (TimeInfo timeInfo : timeInfoList) {
			try {
				dateList.add(timeInfo.getVisitDate());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		java.util.Collections.sort(dateList);
		return dateList.get(0);
		
	}
	
	/**
	 * 获得酒店房型所有房间数量
	 * @param timeInfoList
	 * @return
	 */
	private int getHotelRoomQuantity(List<TimeInfo> timeInfoList){
		int quantity =0;
		for (TimeInfo timeInfo : timeInfoList) {
			quantity+=timeInfo.getQuantity();
		}
		return quantity;
	}
  	
  	protected List<Item> getItem() {
		List<Item> itemList = new ArrayList<Item>();
		if(!buyInfo.hasSelfPack()){
			if(buyInfo.getProductId() != null) {
				ProdProduct product = pageService.getProdProductByProductId(buyInfo.getProductId());
				if(product != null) {
					buyInfo.setIsAperiodic(product.getIsAperiodic());
				}
			} else if(buyInfo.getProdBranchId() != null) {
				ProdProductBranch branch = pageService.getProdBranchByProdBranchId(buyInfo.getProdBranchId());
				if(branch != null) {
					buyInfo.setIsAperiodic(branch.getProdProduct().getIsAperiodic());
				}
			}
			Date beginTime = null, endTime = null;
			Map<Long, Long> ordOrderItemProds = buyInfo.getOrdItemProdList();
			for (Iterator<Long> iterator = ordOrderItemProds.keySet().iterator(); iterator
					.hasNext();) {
				Long prodBranchId = (Long) iterator.next();
				Long quantity = ordOrderItemProds.get(prodBranchId);
				if(quantity != null && quantity > 0){
					Item item = new Item();
					ProdProductBranch branch=pageService.getProdBranchByProdBranchId(prodBranchId);
					item.setProductId(branch.getProductId());
					item.setProductBranchId(branch.getProdBranchId());
					item.setQuantity(Integer.parseInt(ordOrderItemProds.get(prodBranchId)
							+ ""));
					item.setFaxMemo(null);
					item.setProductType(branch.getProdProduct().getProductType());
					item.setSubProductType(branch.getProdProduct().getSubProductType());
					//不定期取有效期最后日期为游玩日期
					if(branch.getProdProduct().IsAperiodic()) {
						Date validBeginTime = branch.getValidBeginTime();
						Date validEndTime = branch.getValidEndTime();
						if(validBeginTime != null && validEndTime != null && !DateUtil.getDayStart(new Date()).after(validEndTime)) {
							item.setVisitTime(validEndTime);
							item.setValidBeginTime(validBeginTime);
							item.setValidEndTime(validEndTime);
							item.setInvalidDate(branch.getInvalidDate());
							item.setInvalidDateMemo(branch.getInvalidDateMemo());
							itemList.add(item);
							
							//取多类别最大区间有效期
							if(beginTime == null) {
								beginTime = validBeginTime;
							} else {
								beginTime = beginTime.before(validBeginTime) ? beginTime : validBeginTime;
							}
							if(endTime == null) {
								endTime = validEndTime;
							} else {
								endTime = endTime.after(validEndTime) ? endTime : validEndTime;
							}
						}
					} else {
						item.setVisitTime(buyInfo.getVisitDate());
						itemList.add(item);
					}
				}		
			}
			/**
			 * 设置房型日期信息(非不定期)
			 */
			if(Constant.PRODUCT_TYPE.HOTEL.name().equals(buyInfo.getProductType()) && Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(buyInfo.getSubProductType()) && !buyInfo.IsAperiodic())	{
				itemList.add(this.getHotelSigelRoomItem());
			}
			
			/**
			 * 设置订单的主产品.
			 */
			Item item=(Item)CollectionUtils.find(itemList, new Predicate() {
				
				public boolean evaluate(Object arg0) {
					Item item = (Item)arg0;
					return buyInfo.getProdBranchId().equals(item.getProductBranchId());
				}
			});
			if(!itemList.isEmpty()) {
				if(item==null){		
					if(itemList.size()==1){
						item=itemList.get(0);
					}else{
						for(BuyInfo.Item it:itemList){
							if(!Constant.PRODUCT_TYPE.OTHER.name().equals(it.getProductType())){
								item=it;
								break;
							}
						}
						if(item==null){
							item=itemList.get(0);
						}
					}
				}
				item.setIsDefault("true");
			}
			if(beginTime != null && endTime != null) {
				buyInfo.setValidBeginTime(beginTime);
				buyInfo.setValidEndTime(endTime);
			}
		}else{
			Map<Long,TimeInfo> map=buyInfo.getOrdItemProdMap();
			for(Long key:map.keySet()){
				TimeInfo ti=map.get(key);
				Item item=new Item();
				item.setProductBranchId(ti.getProdBranchId());
				item.setJourneyProductId(ti.getJourneyProductId());
				ProdProductBranch branch=pageService.getProdBranchByProdBranchId(item.getProductBranchId());
				item.setProductId(branch.getProductId());
				try{
					item.setVisitTime(ti.getVisitDate());
				}catch(ParseException ex){
					
				}
				if(ti.hasHotel()){
					int quantity=0;
					List<OrdTimeInfo> ordTimeInfoList = new ArrayList<OrdTimeInfo>();
					try{
						int days=(int)ti.getDays();
						for(int i=0;i<days;i++){						
							Date vt=DateUtils.addDays(ti.getVisitDate(), i);
							OrdTimeInfo oti=new OrdTimeInfo();
							oti.setQuantity(ti.getQuantity());
							oti.setVisitTime(vt);
							oti.setProductBranchId(item.getProductBranchId());
							oti.setProductId(item.getProductId());
							ordTimeInfoList.add(oti);
							quantity+=oti.getQuantity();							
						}
					}catch(ParseException ex){
						
					}
					item.setTimeInfoList(ordTimeInfoList);
					item.setQuantity(quantity);
					
				}else{
					item.setQuantity(ti.getQuantity().intValue());
				}
				item.setFaxMemo(null);
				itemList.add(item);
			}	
			if(buyInfo.hasSelfPack()){
				//添加当前下单的主对象
				Item item=new Item();
				item.setQuantity(buyInfo.getTotalQuantity());
				item.setFaxMemo(null);
				item.setIsDefault("true");
				item.setProductBranchId(buyInfo.getProdBranchId());
				ProdProductBranch branch=pageService.getProdBranchByProdBranchId(item.getProductBranchId());
				item.setProductId(branch.getProductId());
				item.setVisitTime(buyInfo.getVisitDate());	
				itemList.add(item);
			}
		}
		
		return itemList;
	}
  	
  	public UsrReceivers getContact(){
		List<UsrReceivers> receiversList = this.receiverUserService.loadUserReceiversByUserId(getUserId());
		return replaceHiddenMobile(this.contact,receiversList);
	}
  	
  	public List<UsrReceivers> getUsrReceivers(){
		List<UsrReceivers> receiversList = this.receiverUserService.loadUserReceiversByUserId(getUserId());
		for (UsrReceivers usrReceivers : this.travellerList) {
			replaceHiddenMobile(usrReceivers,receiversList);
		}
		return travellerList;
	}
  	
  	/**
	 * 构造person
	 * 
	 * @param usrReceivers
	 * @param personType
	 * @return
	 */
	public Person setPerson(UsrReceivers usrReceivers, String personType) {
		Person person = new Person();

		person.setName(usrReceivers.getReceiverName());
		if(ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR)){
			String requestIp = InternetProtocol.getRemoteAddr(getRequest());
            if(ActivityUtil.isCompanyInnerIp(requestIp)) {
            	if (null != person.getName() && person.getName().startsWith("amp;")) {
            		person.setName(person.getName().substring(4));
            	} else {
            		person.setName("测试");
            	}
            }
        }
		person.setMobile(usrReceivers.getMobileNumber());
		person.setPersonType(personType);
		person.setReceiverId(usrReceivers.getReceiverId());
		person.setCertNo(usrReceivers.getCardNum());
		person.setCertType(usrReceivers.getCardType());
		person.setEmail(usrReceivers.getEmail());
		person.setGender(usrReceivers.getGender());
		person.setPinyin(usrReceivers.getPinyin());
		person.setBrithday(usrReceivers.getBrithday());
		return person;
	}
	
	/**
	 * 替换***
	 * @param usrReceivers 目标
	 * @param receiversList 数据源
	 * @return
	 */
	private UsrReceivers replaceHiddenMobile(UsrReceivers usrReceivers,List<UsrReceivers> receiversList){
		if(null==usrReceivers){
			return null;
		}
		if(StringUtils.isNotEmpty(usrReceivers.getMobileNumber())){
			Pattern pattern = Pattern.compile("(1[0-9]{2,2}[\\*]{4,4}[0-9]{4,4})");
			if(StringUtils.isNotBlank(usrReceivers.getReceiverId())){//选择的常用联系人
				Matcher matcher = pattern.matcher(usrReceivers.getMobileNumber());
				if (matcher.matches()) {//134****8362,替换掉****
					for(UsrReceivers receiver:receiversList){
						if(receiver.getReceiverId().equals(usrReceivers.getReceiverId())){
							usrReceivers.setMobileNumber(receiver.getMobileNumber());
							break;
						}
					}
				}
			}
		}
		return usrReceivers;
	}
	/**
	 * 将参数进行加密、
	 * @return
	 */
	private String getRequestValidateMd5(){
    	// 产品ID、类别ID、验证码、券码、游玩时间、购买数量、渠道方、批次ID
    	try {
			String needValidate = "productId_prodBranchId_verifycode_distributionCouponCode_visitTime_buyNum_channel_batchId";
			// MD5校验
			Enumeration parameterNames = getRequest().getParameterNames();
			String md5String = "";
			List<String> needValidateList = new ArrayList<String>();
			//循环所有参数
			while (parameterNames.hasMoreElements()) {
				String object = (String) parameterNames.nextElement();
				String _parameterName = "";
				//有两种类型的参数:  buyInfo.***   buyInfo.***.xxx
				//需要取出***  看是否需要对该参数做校验
				if(object.indexOf(".") != -1){
					_parameterName = object.substring(object.indexOf(".")+1,object.indexOf(".")!=object.lastIndexOf(".")?object.lastIndexOf("."):object.length());
				}else{
					_parameterName = object;
				}
				if(needValidate.indexOf(_parameterName) != -1){
					needValidateList.add(getRequest().getParameter(object));
				}
			}
			//将所需要校验的值、进行排序、加密
			Collections.sort(needValidateList);
			md5String = MD5.encode(needValidateList.toString());
			return md5String;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	return "";
    }
	
	public ViewBuyInfo getBuyInfo() {
		return buyInfo;
	}

	public void setBuyInfo(ViewBuyInfo buyInfo) {
		this.buyInfo = buyInfo;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public List<DistributionTuanCoupon> getTuanCouponLists() {
		return tuanCouponLists;
	}

	public void setTuanCouponLists(List<DistributionTuanCoupon> tuanCouponLists) {
		this.tuanCouponLists = tuanCouponLists;
	}

	public String getVerifycode() {
		return verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}

	public ProdProductBranch getMainProdBranch() {
		return mainProdBranch;
	}

	public void setMainProdBranch(ProdProductBranch mainProdBranch) {
		this.mainProdBranch = mainProdBranch;
	}

	public void setProductServiceProxy(ProductHeadQueryService productServiceProxy) {
		this.productServiceProxy = productServiceProxy;
	}

	public List<ProdProductBranch> getRelatedProductList() {
		return relatedProductList;
	}

	public void setRelatedProductList(List<ProdProductBranch> relatedProductList) {
		this.relatedProductList = relatedProductList;
	}

	public List<String> getFirstTravellerInfoOptions() {
		return firstTravellerInfoOptions;
	}

	public void setFirstTravellerInfoOptions(List<String> firstTravellerInfoOptions) {
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

	public void setReceiverUserService(IReceiverUserService receiverUserService) {
		this.receiverUserService = receiverUserService;
	}

	public void setContact(UsrReceivers contact) {
		this.contact = contact;
	}

	

	public String getVs() {
		return vs;
	}

	public void setVs(String vs) {
		this.vs = vs;
	}

	public List<UsrReceivers> getTravellerList() {
		return travellerList;
	}

	public void setTravellerList(List<UsrReceivers> travellerList) {
		this.travellerList = travellerList;
	}

	public UsrReceivers getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(UsrReceivers emergencyContact) {
		this.emergencyContact = emergencyContact;
	}

	public void setDistributionOrderServiceProxy(
			DistributionOrderService distributionOrderServiceProxy) {
		this.distributionOrderServiceProxy = distributionOrderServiceProxy;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	
}
