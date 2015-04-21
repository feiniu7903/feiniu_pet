/**
 * 
 */
package com.lvmama.pet.mark.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.vo.view.MarkCouponUserInfo;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkCouponRelateUser;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.mark.dao.MarkCouponCodeDAO;
import com.lvmama.pet.mark.dao.MarkCouponDAO;
import com.lvmama.pet.mark.dao.MarkCouponRelateUserDAO;


/**
 * @author liuyi
 *
 */
public class MarkCouponServiceImpl implements MarkCouponService {

	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(MarkCouponServiceImpl.class);
	
	
	/**
	 * 优惠码动态生成字符串的长度
	 */
	private final static int COUPON_CODE_FRAGMENT_LENGTH = 10;
	
	@Autowired
	private MarkCouponDAO markCouponDAO;
	@Autowired
	private MarkCouponCodeDAO markCouponCodeDAO;
	@Autowired
	private MarkCouponRelateUserDAO markCouponRelateUserDAO;
	
	//private MarkCouponProductDAO markCouponProductDAO;
	//private MarkCouponUsageDAO markCouponUsageDAO;
	//private CouponLogic couponLogic;
	//private ComLogService comLogService;
	
//	public void saveMarkCoupon(MarkCoupon markCoupon,List<String> codeList){
//		String firstCode = markCoupon.getFirstCode();
//		String type = markCoupon.getCouponType();
//    	markCoupon.setFirstCode(markCoupon.getFirstCode().toUpperCase());//转大写
//		//markCoupon.setOrderMinAmount(markCoupon.getOrderMinAmount()*100);
//		//markCoupon.setAmount(markCoupon.getAmount()*100);//转换成分
//		//markCoupon.setMinAmount(markCoupon.getMinAmount()*100);//转换成分
//		Long id = markCouponDAO.insert(markCoupon);
//		if (!Constant.COUPON_TYPE.B.name().equals(markCoupon.getCouponType())) {//不是b类优惠券 回生产一个默认的code
//		for (String string : codeList) {
//			MarkCouponCode mcc = new MarkCouponCode();
//			mcc.setCouponId(id);
//			mcc.setCouponCode(type.concat(firstCode.concat(string)).toUpperCase());
//			mcc.setUsed("false");
//			markCouponCodeDAO.insert(mcc);
//		}
		//}
//		 markCouponDAO.insertSelective(markCoupon);
//	}
	
//	@Override
//	public List<MarkCoupon> loadAllProductMarkCoupon(Long productId,String subProductType) {
//		//return couponLogic.loadAllProductMarkCoupon(productId,subProductType);
//		return null;
//	}
//	public List<MarkCoupon> loadAllOrderMarkCoupon(List<Long> productIds,List<String> subProductTypes){
//		//return couponLogic.loadAllOrderMarkCoupon(productIds, subProductTypes);
//		return null;
//	}
//	public MarkCouponProduct selectByCouponIdAndProductId(Long couponId, Long productId){
//		//return this.markCouponProductDAO.selectByCouponIdAndProductId(couponId, productId);
//		return null;
//	}
//	
//	public void updateMarkCouponProduct(MarkCouponProduct mcp){
//		//this.markCouponProductDAO.updateByPrimaryKey(mcp);
//		 
//	}
//	
//	public String saveMarkcouponCode(Long couponId){
//		String currentCode="";
//		 MarkCoupon coupon = this.markCouponDAO.selectByPrimaryKey(couponId);
//		 if(coupon != null)
//		 {
//			 MarkCouponCode mcc = new MarkCouponCode();
//				mcc.setCouponId(couponId);
//				mcc.setUsed("false");
//				boolean flag = true;
//				while(flag){//循环生产不重复的code
//					mcc.setCouponCode(coupon.getCouponType().concat(coupon.getFirstCode()).concat(StringUtil.getRandomString(0, 10)));
//					Long scount = this.markCouponCodeDAO.selectCountByCode(mcc.getCouponId(),mcc.getCouponCode());
//					if(scount==0){//不存在重复的就插入
//						markCouponCodeDAO.insert(mcc);
//						currentCode = mcc.getCouponCode();
//						flag = false;
//					}
//					
//				}
//		 }
//		 else
//		 {
//			 LOG.error("can't get coupon "+couponId);
//		 }
//			return currentCode;
//	}
//	
//	public void saveCode(Long id,List<String> codeList){
//		MarkCoupon coupon = this.markCouponDAO.selectByPrimaryKey(id);
//		for (String string : codeList) {
//			MarkCouponCode mcc = new MarkCouponCode();
//			mcc.setCouponId(id);
//			mcc.setCouponCode(coupon.getCouponType().concat(coupon.getFirstCode().concat(string)).toUpperCase());
//			mcc.setUsed("false");
//			Long count = this.markCouponCodeDAO.selectCountByCode(mcc.getCouponId(),mcc.getCouponCode());
//			if(count==0){//不存在重复的就插入
//				markCouponCodeDAO.insert(mcc);
//			} else {//
//				boolean flag = true;
//				while(flag){//循环生产不重复的code
//					mcc.setCouponCode(coupon.getCouponType().concat(coupon.getFirstCode()).concat(StringUtil.getRandomString(0, 10)));
//					Long scount = this.markCouponCodeDAO.selectCountByCode(mcc.getCouponId(),mcc.getCouponCode());
//					if(scount==0){//不存在重复的就插入
//						markCouponCodeDAO.insert(mcc);
//						flag = false;
//					}
//					
//				}
//			}
//		}
//	}
//	
//	
//	public void saveMoreCode(String[] array){
//		 for (int i = 0; i < array.length; i++) {
//			 this.markCouponCodeDAO.insertCodeTemp(array[i]);
//		 }
//	}
//	
//	public Long selectCodeTempCount(){
//		return this.markCouponCodeDAO.selectCodeTempCount();
//	}
//	
//	public void mergeTempCodeData(String couponId){
//		this.markCouponCodeDAO.deleteCodeTempRepeat();
//		this.markCouponCodeDAO.mergeTempData(couponId);
//	}
//	
//	
//	public  List<MarkCouponCode> loadAllCodesByCouponId(Long couponId){
//		return this.markCouponCodeDAO.selectAllCodeByCouponId(couponId);
//	}
//	
//	public void deleteAllCodeTemp(){
//		this.markCouponCodeDAO.deleteAllCodeTemp();
//	}
//	
//	public Long selectCountByCode(Long couponId,String couponCode){
//		return this.markCouponCodeDAO.selectCountByCode(couponId,couponCode);
//	}
//	
//	public	MarkCouponUsage selectProdCouponUsege(Long objectId){
//		Map<String,Object> param = new HashMap<String,Object>();
//		param.put("type", Constant.OBJECT_TYPE.ORD_ORDER_ITEM_PROD.name());
//		param.put("objectId", objectId);
//		//return this.markCouponUsageDAO.selectbyObjectAndType(param);
//		return null;
//	}
//	
//	public MarkCouponUsage selectOrdCouponUsege(Long objectId){
//		Map<String,Object> param = new HashMap<String,Object>();
//		param.put("type", Constant.OBJECT_TYPE.ORD_ORDER.name());
//		param.put("objectId", objectId);
//		//return this.markCouponUsageDAO.selectbyObjectAndType(param);
//		return null;
//	}
//	
//	public void updateCouponCode(MarkCouponCode mkc){
//		this.markCouponCodeDAO.updateByPrimaryKey(mkc);
//	}
//	
//	public MarkCouponCode loadMarkCouponCodeByPk(Long id){
//		return this.markCouponCodeDAO.selectByPrimaryKey(id);
//	}
//	public 	List<MarkCouponUsage> selectCouponUsedByParam(Map<String,Object> param){
//		//return markCouponUsageDAO.selectCouponUsedByParam(param);
//		return null;
//	}
//	
//	public MarkCouponProduct selectMarkCouponProductByPk(Long id){
//		//return this.markCouponProductDAO.selectByPrimaryKey(id);
//		return null;
//	}
//	
//	public List<CouponProduct> selectCouponProduct(Map<String,Object> parameters){
//		//return this.markCouponProductDAO.selectCouponProduct(parameters);
//		return null;
//	}
//	public Long selectCouponProductCount(Map<String,Object> parameters){
//		//return this.markCouponProductDAO.selectCouponProductCount(parameters);
//		return null;
//	}
//	public List<MarkCouponCode> selectByCouponId(Map<String,Object> param){
//		return this.markCouponCodeDAO.selectByCouponId(param);
//	}
//	public Long  selectCountByCouponId(Long couponId){
//		return this.markCouponCodeDAO.selectCountByCouponId(couponId);
//	}
//	
//	public List<MarkCouponCode> selectCouponCodeByCodeAndCouponId(Long couponId,String couponCode){
//		return this.markCouponCodeDAO.selectCouponCodeByCodeAndCouponId(couponId, couponCode);
//	}
//	
//	public void deleteCouponProduct(Long couponProductId){
//		//this.markCouponProductDAO.deleteByPrimaryKey(couponProductId);
//		 
//	}
//	
//	public void saveProductCoupon(Long couponId,List<Long> productId){
//		for (Long pid : productId) {
//			MarkCouponProduct mcp = null;//this.markCouponProductDAO.selectByCouponIdAndProductId(couponId, pid);
//			MarkCoupon mc = this.markCouponDAO.selectByPrimaryKey(couponId);
//			if (mcp == null) {
//			mcp = new MarkCouponProduct();
//			//mcp.setAmount(mc.getAmount());
//			mcp.setCouponId(couponId);
//			mcp.setProductId(pid);
//			//this.markCouponProductDAO.insert(mcp);
//			}
//		}
//	}
//	@Override
//	public void saveSingleProductCoupon(MarkCouponProduct mcp) {
//		//this.markCouponProductDAO.insert(mcp);
//	}
//	
//	@Override
//	public void deleteByCouponIdAndSubProductType(Long couponId,
//			String subProductType) {
//		// this.markCouponProductDAO.deleteByCouponIdAndSubProductType(couponId, subProductType);
//	}
//
//	@Override
//	public String checkProductIdOrSubProductTypeAgainBound(MarkCouponProduct mcp) {
//		//return this.markCouponProductDAO.checkProductIdOrSubProductTypeAgainBound(mcp);
//		return null;
//	}
//
//	public Integer selectCountCouponUsageByParam(Map<String,Object> param){
//		//return this.markCouponUsageDAO.selectCountCouponUsageByParam(param);
//		return null;
//	}
//	
//	public Long countProductByCouponId(Long couponId){
//		//return this.markCouponProductDAO.countProductByCouponId(couponId);
//		return null;
//	}
//	
//	public Long countHasCode(Long couponId){
//		return this.markCouponCodeDAO.countHasCode(couponId);
//	}
//	
//	public List<MarkCouponProduct> selectMarkCouponProduct(Long couponId) {
//		//return this.markCouponProductDAO.select(couponId);
//		return null;
//	}
//
//	public void updateMarkCoupon(MarkCoupon markCoupon) {
//		this.markCouponDAO.updateByPrimaryKey(markCoupon);
//	}
//
	
	@Override
	public MarkCoupon insertMarkCoupon(final MarkCoupon markCoupon) {
		markCouponDAO.insert(markCoupon);
		if (Constant.COUPON_TYPE.A.name().equals(markCoupon.getCouponType())) {//A类优惠券 回生产一个默认的code
			List<String> radomStrList = radomMarkCouponCodeNumbers();
			for (String string : radomStrList) {
				MarkCouponCode mcc = new MarkCouponCode();
				mcc.setCouponId(markCoupon.getCouponId());
				mcc.setCouponCode(markCoupon.getCouponType().concat(markCoupon.getFirstCode().concat(string)).toUpperCase());
				mcc.setUsed("false");
				
				if ("UNFIXED".equals(markCoupon.getValidType())) {
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					mcc.setBeginTime(calendar.getTime());
					calendar.add(Calendar.DAY_OF_YEAR, markCoupon.getTermOfValidity().intValue());
					calendar.set(Calendar.HOUR_OF_DAY, 23);
					calendar.set(Calendar.MINUTE, 59);
					calendar.set(Calendar.SECOND, 59);
					mcc.setEndTime(calendar.getTime());
				}
				markCouponCodeDAO.saveMarkCouponCode(mcc);
			}
		}
		return markCoupon;
	}
	
	@Override
	public MarkCoupon updateMarkCoupon(final MarkCoupon markCoupon) {
		markCouponDAO.updateByPrimaryKey(markCoupon);
		return markCoupon;
	}
	
	@Override
	public MarkCoupon selectMarkCouponByPk(final Long id) {
		return markCouponDAO.selectByPrimaryKey(id);
	}
	
	@Override
	public MarkCoupon selectMarkCouponByCouponCode(final String code, final boolean ignorValid) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		MarkCouponCode markCouponCode = null;
		MarkCoupon markCoupon = null;
		
		param.put("couponCode", code);
		if (!ignorValid) {
			param.put("USED", "true");
		}
		List<MarkCouponCode> codes = markCouponCodeDAO.selectByParam(param);
		if (null == codes || codes.isEmpty()) {
			return null;
		} else {
			markCouponCode = codes.get(0);
		}
		
		markCoupon = markCouponDAO.selectByPrimaryKey(markCouponCode.getCouponId());
		if (!ignorValid && null != markCoupon) { 
			if ((MarkCoupon.FIXED_VALID.equals(markCoupon.getValidType()) && !markCoupon.isOverDue()) 
					|| (MarkCoupon.UNFIXED_VALID.equals(markCoupon.getValidType()) && !markCouponCode.isOverDue())) {
				return markCoupon;
			}
		}
		return markCoupon;
		
	}
	
	@Override
	public List<MarkCoupon> selectMarkCouponByParam(final Map<String, Object> param) {
		/**
		 * 当希望通过优惠券号码找到优惠券的批次时，需要先找到优惠券号码所在的批次号，然后再进行查找优惠券批次的详细信息。
		 * 否则只能使用left join方法查找且用dist去查，过于影响性能
		 */
		if (param.containsKey("couponCode") 
				&& null != param.get("couponCode") 
				&& !param.containsKey("couponId")) {
			List<Long> couponIds = markCouponCodeDAO.selectCouponIdByCouponCode((String) param.get("couponCode"));
			if (null == couponIds || couponIds.isEmpty()) {
				return new ArrayList<MarkCoupon>(0);
			} else {
				param.put("couponIds", couponIds);
			}
		}
		return markCouponDAO.selectByParam(param);
	}
	
	@Override
	public Integer selectMarkCouponRowCount(final Map<String,Object> param){
		if (param.containsKey("couponCode") 
				&& null != param.get("couponCode") 
				&& !param.containsKey("couponId")) {
			List<Long> couponIds = markCouponCodeDAO.selectCouponIdByCouponCode((String) param.get("couponCode"));
			if (null == couponIds || couponIds.isEmpty()) {
				return 0;
			} else {
				param.put("couponIds", couponIds);
			}
		}		
		return markCouponDAO.selectRowCount(param);
	}
	
	@Override
	public MarkCouponCode updateMarkCouponCode(final MarkCouponCode markCouponCode, boolean changeCode) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("couponCode", markCouponCode.getCouponCode());
		if (changeCode && selectMarkCouponCodeRowCount(param) > 0 ) {
			return null;
		} else {
			markCouponCodeDAO.updateByPrimaryKey(markCouponCode);
			return markCouponCode;
		}
	}
	
	@Override
	public MarkCouponCode getMarkCouponCodeByCouponIdAndCode(Long couponId,String couponCode){
		if (null == couponId && null == couponCode) {
			return null;
		}
    	Map<String,Object> param = new HashMap<String, Object>();
    	param.put("couponId", couponId);
    	param.put("couponCode", couponCode);
		List<MarkCouponCode> mccList = this.markCouponCodeDAO.selectByParam(param);
		if (mccList.size()>0) {
			return mccList.get(0);
		}
		return null;
	}
	
	@Override
	public MarkCouponCode selectMarkCouponCodeByPk(final Long id) {
		return markCouponCodeDAO.selectByPrimaryKey(id);
	}
	
	@Override
	public List<MarkCouponCode> selectMarkCouponCodeByParam(final Map<String, Object> param) {
		return markCouponCodeDAO.selectByParam(param);
	}
	
	@Override
	public Integer selectMarkCouponCodeRowCount(final Map<String,Object> param){		
		return markCouponCodeDAO.selectRowCount(param);
	}
	
    @Override
	public MarkCouponCode generateSingleMarkCouponCodeByCouponId(final Long couponId) {
    	MarkCoupon markCoupon = markCouponDAO.selectByPrimaryKey(couponId);
    	if (null == markCoupon || !"true".equals(markCoupon.getValid())) {
    		return new MarkCouponCode();
    	}
    	
		if (Constant.COUPON_TYPE.A.name().equals(markCoupon.getCouponType())) {
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("couponId", couponId);
			params.put("_endRow", 1);
			params.put("_startRow", 0);
			List<MarkCouponCode> markCouponList = this.markCouponCodeDAO.selectByParam(params);
			if(!markCouponList.isEmpty())
			{
				//A类优惠券 已经产生了一个默认的code，无需生成，直接返回
				return markCouponList.get(0);
			}
		}
    	
    	Map<String, Object> param = new HashMap<String, Object>();
    	while (true) {		
			String couponCode = new StringBuilder()
					.append(markCoupon.getCouponType())
					.append(markCoupon.getFirstCode())
					.append(StringUtil.getRandomString(0,
							COUPON_CODE_FRAGMENT_LENGTH)).toString();
			
			param.put("couponCode", couponCode);
			
			if (markCouponCodeDAO.selectRowCount(param) == 0) {
				MarkCouponCode markCouponCode = new MarkCouponCode();
				markCouponCode.setCouponId(couponId);
				markCouponCode.setCouponCode(couponCode);
				markCouponCode.setUsed("false");
				
				if ("UNFIXED".equals(markCoupon.getValidType())) {
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.HOUR_OF_DAY, 0);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					markCouponCode.setBeginTime(calendar.getTime());
					calendar.add(Calendar.DAY_OF_YEAR, markCoupon.getTermOfValidity().intValue());
					calendar.set(Calendar.HOUR_OF_DAY, 23);
					calendar.set(Calendar.MINUTE, 59);
					calendar.set(Calendar.SECOND, 59);
					markCouponCode.setEndTime(calendar.getTime());
				}
				markCouponCodeDAO.saveMarkCouponCode(markCouponCode);
				return markCouponCode;
			}	
    	}    	
    }
	 
     @Override
	 public List<MarkCouponCode> generateMarkCouponCodeByCouponId(final Long couponId, final int number, final String couponGenerateMode) {
    	MarkCoupon markCoupon = markCouponDAO.selectByPrimaryKey(couponId);
     	if (null == markCoupon || !"true".equals(markCoupon.getValid())) {
     		return null;
     	}
     	
     	if(!"force".equals(couponGenerateMode)){
     		//强制就不生成code
    		if (Constant.COUPON_TYPE.A.name().equals(markCoupon.getCouponType())) {
    			Map<String,Object> params=new HashMap<String, Object>();
    			params.put("couponId", couponId);
    			params.put("_endRow", 1);
    			params.put("_startRow", 0);
    			List<MarkCouponCode> markCouponList = this.markCouponCodeDAO.selectByParam(params);
    			if(!markCouponList.isEmpty())
    			{
    				List<MarkCouponCode> aCodeList = new ArrayList<MarkCouponCode>();
    				aCodeList.add(markCouponList.get(0));
    				//A类优惠券 已经自动产生了一个默认的code，无需生成，直接返回
    				return aCodeList;
    			}
    		}
     	}
     	
     	
     	Map<String, Object> param = new HashMap<String, Object>();
     	param.put("couponId", couponId);
     	List<MarkCouponCode> markCouponCodes = markCouponCodeDAO.selectByParam(param);
     
     	/*
     	//已经存在的优惠码
     	Set<String> oldMarkCouponCodeSet = new HashSet<String>();
     	for (MarkCouponCode markCouponCode : markCouponCodes) {
     		oldMarkCouponCodeSet.add(markCouponCode.getCouponCode());
     	}*/
     	
     	//新的优惠码
     	Set<String> newMarkCouponCodeSet = new HashSet<String>(number);
     	int i = 0;
		int begin = 0;
     	while (newMarkCouponCodeSet.size() < number) {
     		 Random random = new Random(System.currentTimeMillis() + (i++));
			 long r = random.nextLong();
			 r = Math.abs(r);
			 String rString = String.valueOf(r);
			 begin = random.nextInt(rString.length() - COUPON_CODE_FRAGMENT_LENGTH);
			 String couponCodeFragment = rString.substring(begin, begin + COUPON_CODE_FRAGMENT_LENGTH);
			 String couponCodeFull = new StringBuilder().
					                 append(markCoupon.getCouponType()).
					                 append(markCoupon.getFirstCode()).
					                 append(couponCodeFragment).toString();
			 param.clear();
		     param.put("couponCode", couponCodeFull);
		     Integer num = markCouponCodeDAO.selectRowCount(param);
		     
			 if (!(num > 0)) {
	     			newMarkCouponCodeSet.add(couponCodeFull);
			 }
     	}
     	
     	Date beginTime = null;
     	Date endTime = null;
     	//效期类型是时间区
     	if ("UNFIXED".equals(markCoupon.getValidType())) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			beginTime = calendar.getTime();
			calendar.add(Calendar.DAY_OF_YEAR, markCoupon.getTermOfValidity().intValue());
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			endTime = calendar.getTime();
		}
     	
     	List<MarkCouponCode> markCouponCodesBatch = new ArrayList<MarkCouponCode>(number);
     	for (String code : newMarkCouponCodeSet) {
     		MarkCouponCode markCouponCode = new MarkCouponCode();
			markCouponCode.setCouponId(couponId);
			markCouponCode.setCouponCode(code);
			markCouponCode.setUsed("false");
			markCouponCode.setBeginTime(beginTime);
			markCouponCode.setEndTime(endTime);
			markCouponCodesBatch.add(markCouponCode);
     	}
     	markCouponCodeDAO.insertMarkCouponCodeBatch(markCouponCodesBatch);
     	markCouponCodes.addAll(markCouponCodesBatch);
    	return markCouponCodes;
     }
     
     
 	@Override
 	public void generateAndBindCouponCodeForUserList(List<UserUser> userList, final Long couponId)
 	{
 		for (UserUser user: userList) {
 			MarkCouponCode couponCode = generateSingleMarkCouponCodeByCouponId(couponId);
 			bindingUserAndCouponCode(user, couponCode.getCouponCode());
 		}
 	}
 	
	@Override
	public Long bindingUserAndCouponCode(final UserUser user, String code) {
		if (StringUtils.isEmpty(code) || null == user.getId()) {
			LOG.warn("coupon code is null or user id is null, can't bind");
			return null;
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("couponCode", code);
		parameters.put("used", "false");
		List<MarkCouponCode> markCouponCodes = markCouponCodeDAO.selectByParam(parameters);

		if (markCouponCodes.size() == 0) {
			LOG.warn("can't find coupon code or coupon code has been used");
			return null;
		}
		
		MarkCouponCode markCouponCode = markCouponCodes.get(0);
		MarkCouponRelateUser markCouponRelateUser = new MarkCouponRelateUser();
		markCouponRelateUser.setUserId(user.getId());
		markCouponRelateUser.setCouponCodeId(markCouponCode.getCouponCodeId());
		return markCouponRelateUserDAO.insert(markCouponRelateUser);
	}
	
	
    /**
	 * 查询指定产品的可用优惠券/优惠活动
	 */
	@Override
	public List<MarkCoupon> selectProductCanUseMarkCoupon(Map<String,Object> map){
		return markCouponDAO.selectProductCanUseMarkCoupon(map);
	}
	
	/**
	 * 查询全场通用的可用优惠券/优惠活动
	 * @param map
	 * @return
	 */
	@Override
	 public List<MarkCoupon> selectAllCanUseMarkCoupon(Map<String,Object> map){
		 return markCouponDAO.selectAllCanUseMarkCoupon(map);
	 }
	 
	 
	 /**
	  * 查询全场通用的可用优惠券/优惠活动以及指定产品的可用优惠券/优惠活动的去重合集
	  * @param map
	  * @return
	  */
	 @Override
	 public List<MarkCoupon> selectAllCanUseAndProductCanUseMarkCoupon(Map<String,Object> map){
		 List<MarkCoupon> allCanUseCouponList = markCouponDAO.selectAllCanUseMarkCoupon(map);
		 List<MarkCoupon> productCanUseCouponList = markCouponDAO.selectProductCanUseMarkCoupon(map);
		 TreeMap<Long, MarkCoupon> couponMap = new TreeMap<Long, MarkCoupon>(new Comparator<Long>() {
             public int compare(Long o1, Long o2) {
                 //如果有空值，直接返回0
                 if (o1 == null || o2 == null)
                     return 0; 
                return o1.compareTo(o2);
          }
		 }
 );
		 //排序去重返回
		 for(int i = 0; i < allCanUseCouponList.size(); i++){
			 couponMap.put(allCanUseCouponList.get(i).getCouponId(), allCanUseCouponList.get(i));
		 }
		 for(int i = 0; i < productCanUseCouponList.size(); i++){
			 couponMap.put(productCanUseCouponList.get(i).getCouponId(), productCanUseCouponList.get(i));
		 }
		 List<MarkCoupon> sortedCouponList = new ArrayList<MarkCoupon>();
		 Iterator<MarkCoupon> sortedCouponIterator = couponMap.values().iterator();
		 while(sortedCouponIterator.hasNext()){
			 sortedCouponList.add(sortedCouponIterator.next());
		 }
		 return sortedCouponList;
	 }
	 
	 
	 
	 
	 
		@Override
		public List<MarkCouponUserInfo> queryMobileUserCouponInfoByUserId(Long userId){
			List<MarkCouponUserInfo> list = new ArrayList<MarkCouponUserInfo>();
			HashMap<String, Object> params = new HashMap<String, Object>();
			if(userId == null)
				return list;
			
			params.put("userId", userId);
			List<MarkCouponCode> codeList = markCouponRelateUserDAO.selectByRelateUserId(params);
			for (MarkCouponCode markCouponCode : codeList) {
				MarkCouponUserInfo dto = new MarkCouponUserInfo();	
				//mru.setCouponCodeId(couponCodeId)
				MarkCoupon coupon = markCouponDAO.selectByPrimaryKey(markCouponCode.getCouponId());
				dto.setMarkCoupon(coupon);
				dto.setMarkCouponCode(markCouponCode);
				list.add(dto);
			}
			return list;
		}
	 
	 
	 
		private List<String> radomMarkCouponCodeNumbers(){
			List<String> radomStrList = new ArrayList<String>();
			for (int i = 0; i < 1; i++) {
				String code = StringUtil.getRandomString(4, 10);
				radomStrList.add(code);
			}
			return radomStrList;
		}
		
		public void changeChannel(){
			
		}

		@Override
		public Integer updateUsedCouponByMarkCoupon(Map<String, Object> map) {
			return markCouponDAO.updateUsedCouponByPK(map);
		}
	
//
//	public void setMarkCouponDAO(MarkCouponDAO markCouponDAO) {
//		this.markCouponDAO = markCouponDAO;
//	}
//
//	
//	public void setMarkCouponCodeDAO(MarkCouponCodeDAO markCouponCodeDAO) {
//		this.markCouponCodeDAO = markCouponCodeDAO;
//	}
//
//	public Long selectCouponIdByCouponCode(String CouponCode) {
//		List<MarkCouponCode> list = new ArrayList<MarkCouponCode>();
//		list = markCouponCodeDAO.selectByCouponCode(CouponCode);
//		if(list.size()!=0){
//			return list.get(0).getCouponId();
//		}
//		return 0l;
//	}
//
//	@Override
//	public List<MarkCoupon> loadAllOrderMarkCoupon(Long productId,String subProductType) {
//		//return couponLogic.loadAllOrderMarkCoupon(productId, subProductType);
//		return null;
//	}
//
//	@Override
//	public ValidateCodeInfo validateCoupon(Long productId, String couponCode,
//			String userId, Long orderPrice, Long orderQuantity,String subProductType) {
//		//return this.couponLogic.validateCoupon(productId, couponCode, userId, orderPrice, orderQuantity, subProductType);
//		return null;
//	}
//
//	@Override
//	public void deleteCouponProductAll(Map<String, Object> parameters) {
//		//this.markCouponProductDAO.deleteCouponProductAll(parameters);
//	}
//
//	@Override
//	public List<MarkChannel> loadChannel() {
//		
//		return null;
//	}
//
//	@Override
//	public Long saveMarkDistChannel(MarkChannel mdc) {
//		
//		return null;
//	}
//
//	@Override
//	public void updateMarkDistChannel(MarkChannel mdc) {
//		
//		
//	}
//
//	@Override
//	public MarkChannel loadMarkDistChannelByPk(Long channelId) {
//		
//		return null;
//	}
//
//	@Override
//	public List<MarkCoupon> loadAllMarkCouponCanBeBindedToMembershipCard(
//			Map<String, Object> parameters) {
//		
//		return null;
//	}	
		
		/**
		 * 判断是否满足打tag的条件,如果满足则直接返回ProdProductTag List,如果不满足则直接返回NULL
		 */
		@Override
		public List<ProdProductTag> checkProductTag(List<Long> productIds) {
			List<ProdProductTag> prodProductTagList=null;
			Map<String,Object> param =new HashMap<String,Object>();
			param.put("productIds", productIds);
			List<MarkCoupon> markCoupons=markCouponDAO.selectValidDate(param);
			if(markCoupons!=null && markCoupons.size()>0){
				prodProductTagList=new ArrayList<ProdProductTag>();
				for(MarkCoupon markCoupon:markCoupons){
					ProdProductTag prodProductTag =new ProdProductTag();
					prodProductTag.setBeginTime(markCoupon.getBeginTime());
					prodProductTag.setEndTime(markCoupon.getEndTime());
					prodProductTag.setProductId(markCoupon.getProductId());
					prodProductTag.setCreator(Constant.PROD_PRODUCT_TAG_CREATOR.SYSTEM.getCode());
					prodProductTagList.add(prodProductTag);
				}
			}
			return prodProductTagList;
		}
		
	public List<MarkCouponCode> queryByUserAndCoupon(final Map<String, Object> param) {
		return this.markCouponCodeDAO.queryByUserAndCoupon(param);
	}
	
}
