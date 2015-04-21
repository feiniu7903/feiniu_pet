package com.lvmama.bee.web.eplace;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.OrdEplaceOrderQuantity;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.eplace.EbkUserTargetService;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PerformDetailRelate;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.sup.MetaPerform;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ExcelUtils;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * @author Huangl
 * 
 */
@Results( {
	@Result(name = "query", location = "/WEB-INF/pages/eplace/passport/allPassportList.jsp"),
	@Result(name = "operatorLog", location = "/WEB-INF/pages/eplace/passport/operatorLog.jsp"),
	@Result(name = "tongji", location = "/WEB-INF/pages/eplace/passport/tongJi.jsp")
	}
)
public class QueryPassPortAction extends EbkBaseAction {
	private static final long serialVersionUID = 5123568398598027070L;
	private List<CodeItem> statusList = new ArrayList<CodeItem>();
	private List<CodeItem> orderStatusList = new ArrayList<CodeItem>();
	private List<MetaProductBranch> ebkMetaBranchList;
	private List<MetaProduct> ebkProductList;
	private MetaProductBranchService metaProductBranchService;
	private MetaProductService	metaProductService;
//	private EbkUserMetaBranchService ebkUserMetaBranchService;
	private PerformTargetService performTargetService;
	private EbkUserTargetService ebkUserTargetService;
	private Page<PerformDetail> performDetailPage;
	
	//统计
	private List<OrdEplaceOrderQuantity> eplaceOrderQuantity;
	private Page<Object[]> tongjiPage;
	private List<PerformDetail> fulfillList;
	private List<ComLog> comloglist;
	private PassCodeService passCodeService;
	private OrderService orderServiceProxy;
	private CompositeQuery compositeQuery;
	private PerformDetailRelate performDetailRelate;
	private ComLogService comLogService;
	
	private String createTimeStart;
	private String createTimeEnd;
	private String playTimeStart;
	private String playTimeEnd;
	private String orderId;
	private String status;
	private String moblieNumber;
	private Long productId;
	private Long branchId;
	private String travellerName;
	private String orderStatus;
	private String paymentTarget;
	private String passport;
	private Long orderItemMetaId;
	@Action("/eplace/allPassportList")
	public String query(){
		initQuery();
			performDetailPage=new Page<PerformDetail>();
			compositeQuery = new CompositeQuery();
			performDetailRelate = new PerformDetailRelate();
			/* 根据履行状态查询 */
			if (Constant.ORDER_PERFORM_STATUS.PERFORMED.toString().equals(status)) {
				performDetailRelate.setPerformStatus(Constant.ORDER_PERFORM_STATUS.PERFORMED);
			} else if (Constant.ORDER_PERFORM_STATUS.UNPERFORMED.toString().equals(status)) {
				performDetailRelate.setPerformStatus(Constant.ORDER_PERFORM_STATUS.UNPERFORMED);
			}
			/* 根据时间查询 */
			if(StringUtils.isNotBlank(createTimeStart) && StringUtils.isNotBlank(createTimeEnd)){
				compositeQuery.getOrderTimeRange().setCreateTimeStart(DateUtil.getDayStart(DateUtil.toDate(createTimeStart, "yyyy-MM-dd")));
				compositeQuery.getOrderTimeRange().setCreateTimeEnd(DateUtil.getDayEnd(DateUtil.toDate(createTimeEnd, "yyyy-MM-dd")));
			}
			/* 根据时间查询 */
			if(StringUtils.isNotBlank(playTimeStart) && StringUtils.isNotBlank(playTimeEnd)){
				performDetailRelate.setVisitTimeStart(DateUtil.getDayStart(DateUtil.toDate(playTimeStart, "yyyy-MM-dd")));
				performDetailRelate.setVisitTimeEnd(DateUtil.getDayEnd(DateUtil.toDate(playTimeEnd, "yyyy-MM-dd")));
			}
			/* 根据订单状态查询 */
			if (StringUtils.isNotBlank(orderStatus)) {
				performDetailRelate.setOrderStatus(orderStatus);
			}
			/* 根据供手机号码查询 */
			if (StringUtils.isNotBlank(moblieNumber)) {
				performDetailRelate.setContactMobile(moblieNumber);
			}
			/* 根据订单编号查询 */
			if (StringUtils.isNotBlank(orderId)) {
				performDetailRelate.setOrderId(Long.parseLong(orderId.trim()));
			}
			/* 根据供姓名查询 */
			if (StringUtils.isNotBlank(travellerName)) {
				performDetailRelate.setContactName(travellerName.trim());
			}
			if(StringUtils.isNotBlank(paymentTarget)){
				performDetailRelate.setPaymentTarget(paymentTarget);
			}
			//按照辅助码查询
			if(StringUtils.isNotBlank(passport)){
				List<PassCode> passCodes=passCodeService.getPassCodeByAddCode(passport);
				if(passCodes!=null && passCodes.size()>0){
					PassCode passCode=passCodes.get(0);
					if(Constant.OBJECT_TYPE.ORD_ORDER_ITEM_META.name().equalsIgnoreCase(passCode.getObjectType())){
						performDetailRelate.setOrderItemMetaId(passCode.getObjectId());
					}else{
						//按照辅助码查询时要判断订单号是否传入
						if(StringUtils.isBlank(orderId) || orderId.trim().equals(passCode.getObjectId().toString())){
							performDetailRelate.setOrderId(passCode.getObjectId());
						}else{
							performDetailPage.setTotalResultSize(0);
							performDetailPage.setItems(null);
							return "query";
						}
					}
				}else{
					performDetailPage.setTotalResultSize(0);
					performDetailPage.setItems(null);
					return "query";
				}
			}
			//用户产品权限限制
			List<Long> branchIds=new ArrayList<Long>();
			if(productId!=null){
				if(branchId!=null){
					branchIds.add(branchId);
				}else{
					List<MetaProductBranch> lst=metaProductBranchService.getEbkMetaBranchByProductId(productId);
					if(lst!=null){
						for(MetaProductBranch item:lst){
							branchIds.add(item.getMetaBranchId());
						}
					}
				}
			}else{
				branchIds = (List<Long>)getSession(Constant.Session_EBOOKING_USER_META_BRANCH_LIST);
			}
			performDetailRelate.setBranchIds(branchIds);
			compositeQuery.setPerformDetailRelate(performDetailRelate);
			performDetailPage.buildUrl(getRequest());
			performDetailPage.setCurrentPage(super.page);
			//判断用户是否有产品权限
			if(CollectionUtils.isNotEmpty(branchIds))
			{
				performDetailPage.setTotalResultSize(orderServiceProxy.queryPerformDetailForEplaceCount(compositeQuery));
				PageIndex pageIndex =new PageIndex();
				pageIndex.setBeginIndex((int) performDetailPage.getStartRows());
				pageIndex.setEndIndex((int)performDetailPage.getEndRows());
				compositeQuery.setPageIndex(pageIndex);
				fulfillList = orderServiceProxy.queryPerformDetailForEplacePageList(compositeQuery);
			}else{
				performDetailPage.setTotalResultSize(0);
				fulfillList=new ArrayList<PerformDetail>();
			}
			performDetailPage.setItems(fulfillList);
			setRequestAttribute("PerformDetailPage", performDetailPage);
		return "query";
	}
	
	@Action("/eplace/tongJi")
	public String tongJi(){
		this.tongJiQuery();
		return "tongji";
	}
	@Action("/eplace/tongJiQuery")
	public String tongJiQuery(){
		initQuery();
		tongjiPage=new Page<Object[]>();
		//判断用户是否有产品权限
		if(CollectionUtils.isEmpty((List<Long>)getSession(Constant.Session_EBOOKING_USER_META_BRANCH_LIST))){
			tongjiPage.setTotalResultSize(0);
			return "tongji";
		}
		compositeQuery = createTongjiCompositeQuery(playTimeStart,playTimeEnd,this.paymentTarget,this.productId,this.branchId);
		tongjiPage.setTotalResultSize(orderServiceProxy.queryPerformDetailForEplaceTongjiCount(compositeQuery));
		tongjiPage.buildUrl(getRequest());
		tongjiPage.setCurrentPage(super.page);
		eplaceOrderQuantity = countKindsOfTicketQuantity(compositeQuery,true);
		setRequestAttribute("eplaceOrderTotalQuantity", eplaceOrderQuantity.get(0));
		PageIndex pageIndex =new PageIndex();
		pageIndex.setBeginIndex((int) tongjiPage.getStartRows());
		pageIndex.setEndIndex((int)tongjiPage.getEndRows());
		compositeQuery.setPageIndex(pageIndex);
		eplaceOrderQuantity = countKindsOfTicketQuantity(compositeQuery,false);
		return "tongji";
	}
	@Action("/eplace/doExcel")
	public void doExcel() throws Exception {
		boolean export=true;
		try {
			compositeQuery = new CompositeQuery();
			performDetailRelate = new PerformDetailRelate();
			/* 根据履行状态查询 */
			if (Constant.ORDER_PERFORM_STATUS.PERFORMED.toString().equals(status)) {
				performDetailRelate.setPerformStatus(Constant.ORDER_PERFORM_STATUS.PERFORMED);
			} else if (Constant.ORDER_PERFORM_STATUS.UNPERFORMED.toString().equals(status)) {
				performDetailRelate.setPerformStatus(Constant.ORDER_PERFORM_STATUS.UNPERFORMED);
			}
			/* 根据时间查询 */
			if(StringUtils.isNotBlank(createTimeStart) && StringUtils.isNotBlank(createTimeEnd)){
				compositeQuery.getOrderTimeRange().setCreateTimeStart(DateUtil.getDayStart(DateUtil.toDate(createTimeStart, "yyyy-MM-dd")));
				compositeQuery.getOrderTimeRange().setCreateTimeEnd(DateUtil.getDayEnd(DateUtil.toDate(createTimeEnd, "yyyy-MM-dd")));
			}
			/* 根据时间查询 */
			if(StringUtils.isNotBlank(playTimeStart) && StringUtils.isNotBlank(playTimeEnd)){
				performDetailRelate.setVisitTimeStart(DateUtil.getDayStart(DateUtil.toDate(playTimeStart, "yyyy-MM-dd")));
				performDetailRelate.setVisitTimeEnd(DateUtil.getDayEnd(DateUtil.toDate(playTimeEnd, "yyyy-MM-dd")));
			}
			/* 根据订单状态查询 */
			if (StringUtils.isNotBlank(orderStatus)) {
				performDetailRelate.setOrderStatus(orderStatus);
			}
			/* 根据供手机号码查询 */
			if (StringUtils.isNotBlank(moblieNumber)) {
				performDetailRelate.setContactMobile(moblieNumber);
			}
			/* 根据订单编号查询 */
			if (StringUtils.isNotBlank(orderId) ) {
				performDetailRelate.setOrderId(Long.parseLong(orderId.trim()));
			}
			/* 根据供姓名查询 */
			if (StringUtils.isNotBlank(travellerName)) {
				performDetailRelate.setContactName(travellerName.trim());
			}
			if(StringUtils.isNotBlank(paymentTarget)){
				performDetailRelate.setPaymentTarget(paymentTarget);
			}
			//按照辅助码查询
			if(StringUtils.isNotBlank(passport)){
				List<PassCode> passCodes=passCodeService.getPassCodeByAddCode(passport);
				if(passCodes!=null && passCodes.size()>0){
					PassCode passCode=passCodes.get(0);
					if(Constant.OBJECT_TYPE.ORD_ORDER_ITEM_META.name().equalsIgnoreCase(passCode.getObjectType())){
						performDetailRelate.setOrderItemMetaId(passCode.getObjectId());
					}else{
						//按照辅助码查询时要判断订单号是否传入
						if(StringUtils.isBlank(orderId) || orderId.trim().equals(passCode.getObjectId().toString())){
							performDetailRelate.setOrderId(passCode.getObjectId());
						}else{
							export=false;
						}
					}
				}else{
					export=false;
				}
			}
			//用户产品权限限制
			List<Long> branchIds=new ArrayList<Long>();
			if(productId!=null){
				if(branchId!=null){
					branchIds.add(branchId);
				}else{
					List<MetaProductBranch> lst=metaProductBranchService.getEbkMetaBranchByProductId(productId);
					if(lst!=null){
						for(MetaProductBranch item:lst){
							branchIds.add(item.getMetaBranchId());
						}
					}
				}
			}else{
				branchIds = (List<Long>)getSession(Constant.Session_EBOOKING_USER_META_BRANCH_LIST);
			}
			performDetailRelate.setBranchIds(branchIds.size()>0?branchIds:null);
			compositeQuery.setPerformDetailRelate(performDetailRelate);
			if(export && CollectionUtils.isNotEmpty(branchIds)){
				fulfillList = orderServiceProxy.queryPerformDetailForEplaceList(compositeQuery);
				/*String fileName = ExcelUtils.writeXlsFile(fulfillList, "/WEB-INF/resources/template/eplace_passport_order.xls");
				this.writeAttachment(fileName, "eplace"+DateFormatUtils.format(new Date(), "yyMMddHHmm"));*/
				// 导出csv
				writeCsvFile(fulfillList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void writeCsvFile(List<PerformDetail> list){
		try{
			String header = "订单号,订单子号,订单状态,游玩时间,取票人姓名,取票人手机号,支付方式,产品名称,销售单价,结算单价,结算总价,订购票数,实际取票数,游玩状态,预计游玩人数,成人,儿童,实际游玩人数,第一位游客身份证\r\n";
			HttpServletResponse response = getResponse();
			response.setHeader("Content-Disposition", "attachment; filename=downloadfile.csv");
			response.setContentType("application/vnd.ms-excel");
			OutputStreamWriter ow = new OutputStreamWriter(response.getOutputStream(),"UTF-16LE");
			header= header.replaceAll(",","\t");
			ow.write(0xFEFF);
			ow.write(header);
			for (int i = 0; i < list.size(); i++) {
				PerformDetail pd = list.get(i);
				String content = pd.toString() + "\r\n";
				ow.write(content);
				ow.flush();
			}
			ow.flush();
			ow.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@Action("/eplace/getPassPortLog")
	public String getPassPortLog(){
		if(orderItemMetaId!=null){
			compositeQuery = new CompositeQuery();
			performDetailRelate = new PerformDetailRelate();
			performDetailRelate.setOrderItemMetaId(orderItemMetaId);
			compositeQuery.setPerformDetailRelate(performDetailRelate);
			fulfillList = orderServiceProxy.queryPerformDetailForEplacePageList(compositeQuery);
			//E景通通关日志
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("objectId", orderItemMetaId);
			params.put("objectType", Constant.OBJECT_TYPE.ORD_ORDER_ITEM_META.name());
			params.put("logType", Constant.EBookingBizType.EPLACE.name());
			params.put("maxResults", 100);
			params.put("skipResults", 0);
			comloglist=comLogService.queryByMap(params);
			//机器通关日志
			List<MetaPerform> metaPerformLst=performTargetService.getMetaPerformByMetaProductId(fulfillList.get(0).getMetaProductId());
			List<PassCode> passCodeLst=passCodeService.getPassCodeByOrderIdAndTargetIdList(fulfillList.get(0).getOrderId(), metaPerformLst.get(0).getTargetId());
			for(PassCode passCode:passCodeLst){
				params.put("objectId", passCode.getCodeId());
				params.put("objectType", Constant.COM_LOG_OBJECT_TYPE.PASS_CODE.name());
				params.put("logType", Constant.COM_LOG_ORDER_EVENT.systemApprovePass.name());
				params.put("maxResults", 100);
				params.put("skipResults", 0);
				List<ComLog> logList=comLogService.queryByMap(params);
				for(ComLog comlog:logList){
					comlog.setContent("设备刷码通关");
					if(comloglist==null)comloglist=new ArrayList<ComLog>();
					comloglist.add(comlog);
				}
			}
		}
		return "operatorLog";
	}
	
	@Action("/eplace/getPassPortLogJson")
	public void getPassPortLogJson(){
		JSONArray array=new JSONArray();
		if(orderItemMetaId!=null){
			compositeQuery = new CompositeQuery();
			performDetailRelate = new PerformDetailRelate();
			performDetailRelate.setOrderItemMetaId(orderItemMetaId);
			compositeQuery.setPerformDetailRelate(performDetailRelate);
			fulfillList = orderServiceProxy.queryPerformDetailForEplaceList(compositeQuery);
			
			if(fulfillList.get(0).getPerformMemo()!=null){
				Map<String,Object> params=new HashMap<String,Object>();
				params.put("objectId", orderItemMetaId);
				params.put("objectType", Constant.OBJECT_TYPE.ORD_ORDER_ITEM_META.name());
				params.put("logType", Constant.EBookingBizType.EPLACE.name());
				params.put("maxResults", 1000);
				params.put("skipResults", 0);
				comloglist=comLogService.queryByMap(params);
				
				for(ComLog item:comloglist){
					JSONObject obj=JSONObject.fromObject(item);
					obj.put("createDate", DateFormatUtils.format(item.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
					obj.put("performMemo", fulfillList.get(0).getPerformMemo());
					array.add(obj);
				}
				
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	
	@Action("/eplace/getUserLogJson")
	public void getUserLogJson(){
		JSONArray array=new JSONArray();
		if(orderItemMetaId!=null){
			compositeQuery = new CompositeQuery();
			performDetailRelate = new PerformDetailRelate();
			performDetailRelate.setOrderItemMetaId(orderItemMetaId);
			compositeQuery.setPerformDetailRelate(performDetailRelate);
			fulfillList = orderServiceProxy.queryPerformDetailForEplaceList(compositeQuery);
			
			if(fulfillList.get(0).getUserMemo()!=null){
				JSONObject obj=new JSONObject();
				obj.put("userMemo",fulfillList.get(0).getUserMemo());
				obj.put("contactName",fulfillList.get(0).getContactName());
				obj.put("orderCreateTime", DateFormatUtils.format(fulfillList.get(0).getOrderCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				array.add(obj);
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	
	@Action("/eplace/getEbkMetaBranchByProductId")
	public void getEbkMetaBranchByProductId(){
		ebkMetaBranchList=metaProductBranchService.getEbkMetaBranchByProductId(productId);
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(ebkMetaBranchList)){
			for(MetaProductBranch emb:ebkMetaBranchList){
				JSONObject obj=new JSONObject();
				obj.put("id", emb.getMetaBranchId());
				obj.put("text", emb.getBranchName());
				array.add(obj);
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	private List<String> getTongjiPageList(CompositeQuery compositeQuery,int begin,int end ){
		PageIndex pageIndex =new PageIndex();
		pageIndex.setBeginIndex(begin);
		pageIndex.setEndIndex(end);
		compositeQuery.setPageIndex(pageIndex);
		
		return orderServiceProxy.queryPerformDetailForEplaceTongjiPageList(compositeQuery);
	}
	private CompositeQuery createTongjiCompositeQuery(String startDate,String endDate,String target,Long productId,Long branchId){
		compositeQuery = new CompositeQuery();
		performDetailRelate = new PerformDetailRelate();
		/* 根据时间查询 */
		if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
			Date timeStart=DateUtil.getDayStart(DateUtil.toDate(startDate, "yyyy-MM-dd"));
			Date timeEnd=DateUtil.getDayEnd(DateUtil.toDate(endDate, "yyyy-MM-dd"));
			performDetailRelate.setVisitTimeStart(timeStart);
			performDetailRelate.setVisitTimeEnd(timeEnd);
		}
		if(StringUtils.isNotBlank(target)){
			performDetailRelate.setPaymentTarget(target);
		}
		//用户产品权限限制
		List<Long> branchIds=new ArrayList<Long>();
		if(productId!=null){
			if(branchId!=null){
				branchIds.add(branchId);
			}else{
				List<MetaProductBranch> lst=metaProductBranchService.getEbkMetaBranchByProductId(productId);
				if(lst!=null){
					for(MetaProductBranch item:lst){
						branchIds.add(item.getMetaBranchId());
					}
				}
			}
		}else{
			branchIds = (List<Long>)getSession(Constant.Session_EBOOKING_USER_META_BRANCH_LIST);
		}
		
		performDetailRelate.setBranchIds(branchIds);
		compositeQuery.setPerformDetailRelate(performDetailRelate);
		
		return compositeQuery;
	}
	/**
	 * 统计订单结果中的各种票数的总数
	 * @param compositeQuery
	 * @return
	 */
	private List<OrdEplaceOrderQuantity>countKindsOfTicketQuantity(CompositeQuery compositeQuery,boolean isTotal){
		List<OrdEplaceOrderQuantity> eplaceOrderQuantity;
		eplaceOrderQuantity = orderServiceProxy.queryEbkOrderForEplaceTotalQuantity(compositeQuery,isTotal);
		return eplaceOrderQuantity;		
	}
	/**
	 * 统计结果中的各种票数、各种人数
	 * @param compositeQuery
	 * @return
	 */
	private Long[] calcTongjiNum(CompositeQuery compositeQuery){
		fulfillList = orderServiceProxy.queryPerformDetailForEplaceList(compositeQuery);
		//预订票数，已取票数，待取票数，预计人数，已玩人数，未玩人数
		Long[] arr={0l,0l,0l,0l,0l,0l};
		for(PerformDetail item:fulfillList){
			arr[0]=arr[0]+item.getQuantity();
			arr[3]=arr[3]+item.getAdultQuantity()+item.getChildQuantity();
			if(item.isNotPass()){
				arr[2]=arr[2]+item.getQuantity();
				arr[5]=arr[5]+item.getAdultQuantity()+item.getChildQuantity();
			}else{
				if(Constant.PAYMENT_TARGET.TOLVMAMA.name().equalsIgnoreCase(item.getPaymentTarget())){
					arr[1]=arr[1]+item.getQuantity();
				}else{
					//景区支付
					arr[1]=arr[1]+item.getRealQuantity();
				}
				arr[4]=arr[4]+item.getRealAdultQuantity()+item.getRealChildQuantity();
			}		
		}
		return arr;
	}
	
	
	private boolean checkCondition(){
		if(StringUtils.isNotBlank(playTimeStart) && StringUtils.isNotBlank(playTimeEnd)){
			return true;
		}
		return false;
	}
	private void initQuery(){
		statusList.add(new CodeItem("", "全部"));
		statusList.add(new CodeItem(Constant.ORDER_PERFORM_STATUS.PERFORMED.toString(), "已游玩"));
		statusList.add(new CodeItem(Constant.ORDER_PERFORM_STATUS.UNPERFORMED.toString(), "未游玩"));
		
		orderStatusList.add(new CodeItem(Constant.ORDER_STATUS.NORMAL.name(), "正常"));
		orderStatusList.add(new CodeItem(Constant.ORDER_STATUS.CANCEL.name(), "取消"));
		orderStatusList.add(new CodeItem(Constant.ORDER_STATUS.FINISHED.name(), "完成"));
		
		if(StringUtils.isBlank(playTimeStart) && StringUtils.isBlank(playTimeEnd)&&
			StringUtils.isBlank(orderId)&&StringUtils.isBlank(moblieNumber)
			&&StringUtils.isBlank(travellerName)&&StringUtils.isBlank(passport)
			&&productId==null&&(StringUtils.isBlank(createTimeEnd) || StringUtils.isBlank(createTimeStart))){
			Date today=new Date();
			playTimeStart=DateFormatUtils.format(today, "yyyy-MM-dd");
			playTimeEnd=DateFormatUtils.format(today, "yyyy-MM-dd");			
		}
		Map<String,Object> userMetaMap=new HashMap<String,Object>();
		if("true".equalsIgnoreCase(getSessionUser().getIsAdmin())){
			userMetaMap.put("userId", this.getSessionUser().getUserId());
		}else{
			userMetaMap.put("userId", this.getSessionUser().getParentUserId());
		}
		ebkProductList = metaProductService.getEbkUserMetaProductsByParam(userMetaMap);

		if(productId!=null){
			ebkMetaBranchList=metaProductBranchService.getEbkMetaBranchByProductId(productId);
		}
	}
	public void chkListChecked(boolean isChecked){
		List<PerformDetail> chkList=new ArrayList<PerformDetail>();
		for (int i = 0; i < this.fulfillList.size(); i++) {
			PerformDetail performDetail=(PerformDetail)this.fulfillList.get(i);
			if(!performDetail.isDisabledPass()){
				performDetail.setChecked(isChecked);
			}
			chkList.add(performDetail);
		}
		this.fulfillList=chkList;
	}
	public List<CodeItem> getStatusList() {
		return statusList;
	}

	public List<PerformDetail> getFulfillList() {
		return fulfillList;
	}

	public List<MetaProductBranch> getEbkMetaBranchList() {
		return ebkMetaBranchList;
	}
	public void setEbkMetaBranchList(List<MetaProductBranch> ebkMetaBranchList) {
		this.ebkMetaBranchList = ebkMetaBranchList;
	}
	
	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

//	public void setEbkUserMetaBranchService(
//			EbkUserMetaBranchService ebkUserMetaBranchService) {
//		this.ebkUserMetaBranchService = ebkUserMetaBranchService;
//	}
	public List<MetaProduct> getEbkProductList() {
		return ebkProductList;
	}
	public void setEbkProductList(List<MetaProduct> ebkProductList) {
		this.ebkProductList = ebkProductList;
	}
	public List<OrdEplaceOrderQuantity> getEplaceOrderQuantity() {
		return eplaceOrderQuantity;
	}
	public void setEplaceOrderQuantity(
			List<OrdEplaceOrderQuantity> eplaceOrderQuantity) {
		this.eplaceOrderQuantity = eplaceOrderQuantity;
	}

	public List<CodeItem> getOrderStatusList() {
		return orderStatusList;
	}
	public void setOrderStatusList(List<CodeItem> orderStatusList) {
		this.orderStatusList = orderStatusList;
	}

	public Page<PerformDetail> getPerformDetailPage() {
		return performDetailPage;
	}

	public void setPerformDetailPage(Page<PerformDetail> performDetailPage) {
		this.performDetailPage = performDetailPage;
	}

	public String getPlayTimeStart() {
		return playTimeStart;
	}

	public void setPlayTimeStart(String playTimeStart) {
		this.playTimeStart = playTimeStart;
	}

	public String getPlayTimeEnd() {
		return playTimeEnd;
	}

	public void setPlayTimeEnd(String playTimeEnd) {
		this.playTimeEnd = playTimeEnd;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMoblieNumber() {
		return moblieNumber;
	}

	public void setMoblieNumber(String moblieNumber) {
		this.moblieNumber = moblieNumber;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getTravellerName() {
		return travellerName;
	}

	public void setTravellerName(String travellerName) {
		this.travellerName = travellerName;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getPaymentTarget() {
		return paymentTarget;
	}

	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public Page<Object[]> getTongjiPage() {
		return tongjiPage;
	}

	public List<ComLog> getComloglist() {
		return comloglist;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}

	public EbkUserTargetService getEbkUserTargetService() {
		return ebkUserTargetService;
	}

	public void setEbkUserTargetService(EbkUserTargetService ebkUserTargetService) {
		this.ebkUserTargetService = ebkUserTargetService;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

}
