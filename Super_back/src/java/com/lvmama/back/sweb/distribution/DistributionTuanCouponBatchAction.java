package com.lvmama.back.sweb.distribution;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.UserAnonymousException;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.distribution.DistributionTuanCoupon;
import com.lvmama.comm.bee.po.distribution.DistributionTuanCouponBatch;
import com.lvmama.comm.bee.po.distribution.DistributorTuanInfo;
import com.lvmama.comm.bee.po.ord.OrdOrderBatch;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.distribution.DistributionTuanCouponBatchService;
import com.lvmama.comm.bee.service.distribution.DistributionTuanCouponService;
import com.lvmama.comm.bee.service.distribution.DistributionTuanService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.RandomFactory;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name="add",location="/WEB-INF/pages/back/distribution/batchAbandonOrder/batchCreateTuanCode.jsp"),
	@Result(name="edit",location="/WEB-INF/pages/back/distribution/batchAbandonOrder/editTuanCode.jsp"),
	@Result(name="index",location="/WEB-INF/pages/back/distribution/batchAbandonOrder/distributionTuanBatchList.jsp")
})
public class DistributionTuanCouponBatchAction extends BackBaseAction {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 320827020019422622L;
	
	private static final Log log = LogFactory.getLog(DistributionTuanCouponBatchAction.class);
	private UserUserProxy userUserProxy;
	private ComLogService comLogService;
	private PageService pageService;
	private ProdProductBranchService prodProductBranchService;
	private DistributionTuanService distributionTuanService;
	private DistributionTuanCouponBatchService distributionTuanCouponBatchService;
	private DistributionTuanCouponService distributionTuanCouponService;
	private Page<OrdOrderBatch> batchList = new Page<OrdOrderBatch>();
	private Long distributionBatchId;
	private Long productId;
	private String productName;
	private Long branchId;
	private Long count;//生成订单数量
	private String startTime;
	private String endTime; //最晚时间
	private Long distributionId;//分销商
	private String operatorName;
	private String token;
	private String booker="银联旅游卡分销专用";//银联旅游卡分销专用
	private List<DistributorTuanInfo> lists=  new ArrayList<DistributorTuanInfo>();
	private Page<DistributionTuanCouponBatch> distPage = new Page<DistributionTuanCouponBatch>();
	
	@Action("/distribution/distributionTuanBatchList")
	public String index(){
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(productName)){
			params.put("productName", productName.trim());
		}
		if(StringUtils.isNotBlank(operatorName)){
			params.put("operatorName", operatorName.trim());
		}
		if(StringUtils.isNotBlank(startTime)){
			params.put("startTime",DateUtil.getDateByStr(startTime, "yyyy-MM-dd"));
		}
		if(StringUtils.isNotBlank(endTime)){
			params.put("endTime",DateUtil.dsDay_Date(DateUtil.getDateByStr(endTime, "yyyy-MM-dd"),1));
		}
		if(productId!=null){
			params.put("productId", productId);
		}
		if(branchId!=null){
			params.put("branchId",branchId);
		}
		if(distributionId!=null && distributionId !=0){
			params.put("distributionId",distributionId);
		}	
		lists = distributionTuanService.selectByDistributorChannelType("DIST_YUYUE");
		distPage.setTotalResultSize(distributionTuanCouponBatchService.queryCount(params));
		distPage.buildUrl(getRequest());
		distPage.setCurrentPage(super.page);
		params.put("start", distPage.getStartRows());
		params.put("end", distPage.getEndRows());
		if(distPage.getTotalResultSize()>0){
			distPage.setItems(distributionTuanCouponBatchService.queryList(params));
		}
		return "index";
	}
	
	
	@Action("/distribution/tuanBatchCreate")
	public String create(){
		token = ""+ new Random().nextInt(1000);
		getSession().setAttribute("token", token);
		lists = distributionTuanService.selectByDistributorChannelType("DIST_YUYUE");
		return "add";
	}
	
	@Action("/distribution/edit")
	public String edit(){
		token = ""+ new Random().nextInt(1000);
		getSession().setAttribute("token", token);
		DistributionTuanCouponBatch dtcb = distributionTuanCouponBatchService.find(distributionBatchId);
		if(dtcb==null){
			return index();
		}
		endTime =DateUtil.formatDate(dtcb.getValidEndTime(), "yyyy-MM-dd");
		return "edit";
	}
	
	@Action("/distribution/saveTuanBatch")
	public void submit(){
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("result", "true");
		try{
			if(!checkToken(jsonObj)){
				return ;
			}
			if(distributionBatchId!=null)
			{
				update();
				jsonObj.put("message", "更新成功");
				sendAjaxResultByJson(jsonObj.toString());
				return;
			}
	
			if(!isCanCreate(jsonObj)){
				return ;
			}
			
			UserUser user = userUserProxy.getUsersByIdentity(booker,UserUserProxy.USER_IDENTITY_TYPE.USER_NAME);
			if(user==null){
				sendAjaxResultByJson(jsonObj,"下单人不存在,生成券码未执行");
				return;
			}
			getSession().setAttribute("token",null);
			String operatorName=getOperatorNameAndCheck();
			DistributionTuanCouponBatch dtcb = new DistributionTuanCouponBatch();
			dtcb.setBranchId(branchId);
			dtcb.setDistributorTuanInfoId(distributionId);
			dtcb.setOrdBatchCount(count);
			dtcb.setOrderBatchCreatetime(new Date());
			dtcb.setProductId(productId);
			dtcb.setOrderBatchCreator(user.getId());
			dtcb.setOperatorName(operatorName);
			dtcb.setValidEndTime(DateUtil.getDateByStr(endTime, "yyyy-MM-dd"));
			Long batchId = distributionTuanCouponBatchService.insert(dtcb);
			jsonObj.put("message", "批次创建成功");
			sendAjaxResultByJson(jsonObj.toString());
			insertLog("创建批量券码",batchId);
			createBatchOrderCoupon(batchId);
			
		}catch(Exception e){
			log.error("createBatch Exception:" ,e);
			jsonObj.put("message", "操作异常");
			sendAjaxResultByJson(jsonObj.toString());
		}
	}


	private boolean isCanCreate(JSONObject jsonObj) {

		
		Date date = prodProductBranchService.selectNearBranchTimePriceByBranchId(branchId);
		if(date==null){
			sendAjaxResultByJson(jsonObj,"类别不存在或不存在可售的时间价格表");
			return false;
		}
		ProdProductBranch prodBranch = prodProductBranchService.getProductBranchDetailByBranchId(branchId, date, true);
		if(prodBranch==null){
			sendAjaxResultByJson(jsonObj,"类别不存在或不存在可售的时间价格表");
			return false;
		}
		if("true".equalsIgnoreCase(prodBranch.getProdProduct().getIsAperiodic())){
			sendAjaxResultByJson(jsonObj,"任务失败，当前只支持非期票产品");
			return false;
		}
		if(prodBranch.getProdProduct().isPaymentToSupplier()){
			sendAjaxResultByJson(jsonObj,"任务失败，支付对象非驴妈妈产品");
			return false;
		}
		if(!(Constant.PRODUCT_TYPE.TICKET.getCode().equalsIgnoreCase(prodBranch.getProdProduct().getProductType()) || Constant.PRODUCT_TYPE.ROUTE.getCode().equalsIgnoreCase(prodBranch.getProdProduct().getProductType()))){
			sendAjaxResultByJson(jsonObj,"任务失败，产品必须是门票或线路");
			return false;
		}
		if(Constant.PRODUCT_TYPE.ROUTE.getCode().equalsIgnoreCase(prodBranch.getProdProduct().getProductType())){
			if(!Constant.SUB_PRODUCT_TYPE.FREENESS.getCode().equalsIgnoreCase(prodBranch.getProdProduct().getSubProductType())){
				sendAjaxResultByJson(jsonObj,"任务失败，线路产品必须是目的地自由行产品");
				return false;
			}
			if(prodBranch.getProdProduct().isEContract()){
				sendAjaxResultByJson(jsonObj,"任务失败,自由行线路产品必须是无需签约的产品");
				return false;
			}
		}
		
		productId = prodBranch.getProductId();
		List<TimePrice> timePrices = prodProductBranchService.selectProdTimePriceByProdBranchId(branchId, date, DateUtil.getDateByStr(endTime, "yyyy-MM-dd"));
		if(timePrices == null){
			sendAjaxResultByJson(jsonObj,"任务失败,时间价格表查询为Null");
			return false;
		}
		TimePrice timePrice = timePrices.get(0);
		for(TimePrice tp:timePrices){
			if(tp.getPrice()!=timePrice.getPrice()){
				sendAjaxResultByJson(jsonObj,"任务失败,在有效期内价格不统一 时间-价格:" + tp.getSpecDate() +"-"+tp.getPrice() + "  " + timePrice.getSpecDate()+"-"+timePrice.getPrice());
				return false;
			}
			boolean resourceConfirm = pageService.isResourceConfirm(prodBranch.getProdBranchId(),timePrice.getSpecDate());
			if(resourceConfirm){
				sendAjaxResultByJson(jsonObj,"任务失败,产品需要资源审核");
				return false;
			}
		}
		return true;
	}

	@Action("/distribution/downloadTuanCode")
	public void downloadPasscode(){
		if(distributionBatchId == null)
			return ;
		
		List<DistributionTuanCouponBatch> tuancodeList = distributionTuanCouponBatchService.queryTuanCodeByBatchId(distributionBatchId);
		String template = "/WEB-INF/resources/template/batchTuanCodeTemplate.xls";
		output(tuancodeList, template,"tuancodeList");
		
		insertLog("券码下载",distributionBatchId);
		
	}


	private void insertLog(String content,Long batchId) {
		PermUser user = getSessionUser();
		ComLog comlog = new ComLog();
		comlog.setContent(content);
		comlog.setContentType("VARCHAR");
		comlog.setCreateTime(new Date());
		comlog.setLogName("");
		comlog.setLogType(Constant.COM_LOG_ORDER_EVENT.tuanCodeDownPASSCODE.name());
		comlog.setObjectId(batchId);
		comlog.setObjectType(Constant.COM_LOG_OBJECT_TYPE.ORDER_DISTRIBUTION_TUAN_COUPON.getCode());
		comlog.setOperatorName(user.getUserName());
		comLogService.addComLog(comlog);
	}
	
	private void createBatchOrderCoupon(Long batchId) {
		DistributionTuanCouponBatch dtcb = distributionTuanCouponBatchService.find(batchId);
		if(dtcb==null){
			return;
		}
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("end", dtcb.getOrdBatchCount());
			map.put("start", 1);
			map.put("status", Constant.DISTRIBUTION_TUAN_COUPON_STATUS.INITSTATUS.name());
			List<DistributionTuanCoupon> list = distributionTuanCouponService.queryCanUseCodeInfo(map);
			if(list ==null){
				list = createNewCode(batchId,dtcb.getOrdBatchCount());
			}
			if(list.size()<dtcb.getOrdBatchCount()){
				list.addAll( createNewCode(batchId,dtcb.getOrdBatchCount()));
			}
			for(DistributionTuanCoupon db:list){
				if(db.getDistributionCouponId()!=null){
					db.setBatchId(batchId);
					db.setStatus(Constant.DISTRIBUTION_TUAN_COUPON_STATUS.NORMAL.name());
					distributionTuanCouponService.update(db);
				}else{
					distributionTuanCouponService.insert(db);
				}
			}
			map.put("end",5000);
			list = distributionTuanCouponService.queryCanUseCodeInfo(map);
			if(list.size()<1500){
				initGroupCouponCode();
			}
		}catch(Exception e){
			log.error("创建团分销批次码出错:" + e);
		}
		
	}

	/**
	 * 每次创建完批次都要再初始化一批券码
	 */
	private void initGroupCouponCode() {
		
		List<String> codes = new ArrayList<String>();
		List<String> oldCodes = distributionTuanCouponService.queryAllCode();
		if(oldCodes==null){
			oldCodes = new ArrayList<String>();
		}
		for(int i=0;i<5000;){
			int topNum=new Random().nextInt(9)+1;
			String code = topNum + RandomFactory.generate(8);
			if(!codes.contains(code) && !oldCodes.contains(code)){
				codes.add(code);
				i++;
			 }
		}
		for(String code:codes){
			DistributionTuanCoupon dbtc = new DistributionTuanCoupon();
			dbtc.setDistributionCouponCode(code);
			dbtc.setStatus(Constant.DISTRIBUTION_TUAN_COUPON_STATUS.INITSTATUS.name());
			distributionTuanCouponService.insert(dbtc);
		}
	}


	private List<DistributionTuanCoupon> createNewCode(Long batchId,
			Long ordBatchCount) {
		List<DistributionTuanCoupon> dbtcs = new ArrayList<DistributionTuanCoupon>();
		List<String> codes = new ArrayList<String>();
		List<String> oldCodes = distributionTuanCouponService.queryAllCode();
		if(oldCodes==null){
			oldCodes = new ArrayList<String>();
		}
		for(int i=0;i<ordBatchCount;){
			int topNum=new Random().nextInt(9)+1;
			String code = topNum + RandomFactory.generate(8);
			if(!codes.contains(code) && !oldCodes.contains(code)){
				codes.add(code);
				i++;
			 }
		}
		for(String code:codes){
			DistributionTuanCoupon dbtc = new DistributionTuanCoupon();
			dbtc.setDistributionCouponCode(code);
			dbtc.setBatchId(batchId);
			dbtc.setStatus(Constant.DISTRIBUTION_TUAN_COUPON_STATUS.NORMAL.name());
			dbtcs.add(dbtc);
		}
		return dbtcs;
	}


	private void update() {
		DistributionTuanCouponBatch dtcb = distributionTuanCouponBatchService.find(distributionBatchId);
		if(dtcb==null){
			return;
		}
		insertLog("修改了券码最晚预约时间:" + DateUtil.formatDate(dtcb.getValidEndTime(), "yyyy-MM-dd") + " 修改为 " +endTime,distributionBatchId);
		dtcb.setValidEndTime(DateUtil.getDateByStr(endTime, "yyyy-MM-dd"));
		distributionTuanCouponBatchService.update(dtcb);
	}


	private synchronized boolean checkToken(JSONObject jsonObj) {
		if(getSession().getAttribute("token")==null){
			jsonObj.put("result", "false");
			jsonObj.put("message", "请求不合法");
			sendAjaxResultByJson(jsonObj.toString());
			return false;
		}
		
		String token2 = (String)getSession().getAttribute("token");
		if(!token2.equals(token)){
			jsonObj.put("result", "false");
			jsonObj.put("message", "请求不合法");
			sendAjaxResultByJson(jsonObj.toString());
			return false;
		}
		
		return true;
	}
	private List<String> getRandomCode(int n){
		List<String> codes = new ArrayList<String>();
		List<String> oldCodes = new ArrayList<String>();
		 for(int i=0;i<n;){
			 int topNum=new Random().nextInt(9)+1;
			 String code = topNum + RandomFactory.generate(8);
			 if(!codes.contains(code) && !oldCodes.contains(code)){
				 codes.add(code);
				 i++;
			 }
		 }
		return codes;
	}
	private void sendAjaxResultByJson(JSONObject jsonObj,String content) {
		jsonObj.put("result", "false");
		jsonObj.put("message", content);
		sendAjaxResultByJson(jsonObj.toString());
	}
	private void output(List list, String template,String key) {
		FileInputStream fin = null;
		OutputStream os = null;
		try {
			File templateResource = ResourceUtil.getResourceFile(template);
			Map beans = new HashMap();
			beans.put(key, list);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			beans.put("dateFormat", dateFormat);
			XLSTransformer transformer = new XLSTransformer();
			File destFileName = new File(Constant.getTempDir() + "/excel" + new Date().getTime() + ".xls");
			transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName.getAbsolutePath());
			getResponse().setContentType("application/vnd.ms-excel");
			getResponse().setHeader("Content-Disposition", "attachment; filename=" + destFileName.getName());
			os = getResponse().getOutputStream();
			fin = new FileInputStream(destFileName);
			IOUtils.copy(fin, os);
			os.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fin);
			IOUtils.closeQuietly(os);
		}
	}
	
	public static void main(String arg[]){
		/*DistributionTuanCouponBatchAction t = new DistributionTuanCouponBatchAction();
		List<String> cd = t.getRandomCode(100);
		System.out.println("" + cd.size());
		System.out.println("cd =  " + cd.toString());*/
		for(int i = 0 ;i <100;i++){
			int topNum=new Random().nextInt(9)+1;
			String code = topNum + RandomFactory.generate(8);
			System.out.println(code);
		}
	}

	protected String getOperatorNameAndCheck(){
		if(isLogined()){
			return getSessionUser().getUserName();
		}
		throw new UserAnonymousException();
	}

	public Page<OrdOrderBatch> getBatchList() {
		return batchList;
	}


	public void setBatchList(Page<OrdOrderBatch> batchList) {
		this.batchList = batchList;
	}


	public Long getBranchId() {
		return branchId;
	}


	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}


	public Long getCount() {
		return count;
	}


	public void setCount(Long count) {
		this.count = count;
	}


	


	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	public Long getDistributionId() {
		return distributionId;
	}


	public void setDistributionId(Long distributionId) {
		this.distributionId = distributionId;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}


	public DistributionTuanService getDistributionTuanService() {
		return distributionTuanService;
	}

	public void setDistributionTuanService(
			DistributionTuanService distributionTuanService) {
		this.distributionTuanService = distributionTuanService;
	}

	
	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}


	public Long getDistributionBatchId() {
		return distributionBatchId;
	}

	public void setDistributionBatchId(Long distributionBatchId) {
		this.distributionBatchId = distributionBatchId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setDistributionTuanCouponBatchService(
			DistributionTuanCouponBatchService distributionTuanCouponBatchService) {
		this.distributionTuanCouponBatchService = distributionTuanCouponBatchService;
	}

	public void setDistributionTuanCouponService(
			DistributionTuanCouponService distributionTuanCouponService) {
		this.distributionTuanCouponService = distributionTuanCouponService;
	}

	
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}


	public List<DistributorTuanInfo> getLists() {
		return lists;
	}

	public void setLists(List<DistributorTuanInfo> lists) {
		this.lists = lists;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Page<DistributionTuanCouponBatch> getDistPage() {
		return distPage;
	}

	public void setDistPage(Page<DistributionTuanCouponBatch> distPage) {
		this.distPage = distPage;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	
}
