package com.lvmama.pet.sweb.visa.approval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.visa.VisaApplicationDocument;
import com.lvmama.comm.pet.po.visa.VisaApproval;
import com.lvmama.comm.pet.po.visa.VisaApprovalDetails;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.visa.VisaApplicationDocumentService;
import com.lvmama.comm.pet.service.visa.VisaApprovalService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.WebUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.visa.GroupVisaInformation;
import com.lvmama.comm.vo.visa.VisaApprovalDetailsVO;
import com.lvmama.comm.work.builder.WorkOrderSenderBiz;

/**
 * 签证
 * @author nixianjun 2013-6-4
 *
 */
@Results({
		@Result(name = "list", location = "/WEB-INF/pages/back/visa/approval/list.jsp"),
		@Result(name = "confirm", location = "/WEB-INF/pages/back/visa/approval/confirm.jsp"),
		@Result(name = "vit", location = "/WEB-INF/pages/back/visa/approval/vit.jsp"),
		@Result(name = "showVit", location = "/WEB-INF/pages/back/visa/approval/showVit.jsp"),
		@Result(name = "send", location = "/WEB-INF/pages/back/visa/approval/send.jsp"),
		@Result(name = "face", location = "/WEB-INF/pages/back/visa/approval/face.jsp"),
		@Result(name = "batchFace", location = "/WEB-INF/pages/back/visa/approval/batchFace.jsp"),
		@Result(name = "batchSend", location = "/WEB-INF/pages/back/visa/approval/batchSend.jsp"),
		@Result(name = "add", location = "/WEB-INF/pages/back/visa/approval/add.jsp"),
		@Result(name = "get", location = "/WEB-INF/pages/back/visa/approval/get.jsp"),
		@Result(name = "batchGet", location = "/WEB-INF/pages/back/visa/approval/batchGet.jsp"),
		@Result(name = "memo", location = "/WEB-INF/pages/back/visa/approval/memo.jsp"),
		@Result(name = "error", location = "/WEB-INF/pages/back/visa/approval/error.jsp") })
public class VisaApprovalAction extends BackBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 2446818948874300597L;
	/**
	 * 签证审核资料远程服务
	 */
	private VisaApprovalService visaApprovalService;
	/**
	 * 签证材料远程服务
	 */
	private VisaApplicationDocumentService visaApplicationDocumentService;
	/**
	 * 日志远程服务
	 */
	private ComLogService comLogService;
	/**
	 * 签证材料列表
	 */
	private List<VisaApplicationDocument> visaApplicationDocuments;
	/**
	 * 审核明细列表
	 */
	private List<VisaApprovalDetails> visaApprovalDetailsList;
	private List<ComLog> logs;
	private List<ComLog> facelogs;
	private VisaApproval visaApproval;
	/**
	 * 签证审核资料数组
	 */
	private List<VisaApproval> visaApprovalList;
	private VisaApprovalDetails visaApprovalDetails;
	private Long searchVisaApprovalId;
	private Long searchOrderId;
	private String searchVisaStatus;
	private String searchTravelGroupCode;
	private String searchStartVisitDate;
	private String searchEndVisitDate;
	private String searchName;
	private String searchTele;
	private Long searchPersonId;
	private String batchVisaApprovalId;
	private Long pid;
	private String content;
	//订单状态
	private String searchOrderStatus;
	//区域
	private String searchRegionName;

	private WorkOrderSenderBiz workOrderProxy;

	private OrderService orderServiceProxy;
	private OrdOrder ordOrder;

	//
 	private String travelGroupCode;
	private String visaApprovalIds;
	private String orderIds;
	/**
	 * 签证审核记录列表页
	 * 
	 * @return
	 */
	@Action("/visa/approval/index")
	public String index() {
		Map<String, Object> param = initParam();

		pagination = initPage();

		param.put("_startRow", pagination.getStartRows() - 1);
		param.put("_endRow", pagination.getEndRows());

		pagination.setTotalResultSize(visaApprovalService.count(param));
		pagination.setItems(visaApprovalService.query(param));
		pagination.setUrl(WebUtils.getUrl(this.getRequest()));

		return "list";
	}
	/**
	 * 签证详细资料导出
	 * @author nixianjun 2013-6-4
	 * @return String
	 */
	@Action("/visa/approval/exportXLSForVisaList")
	public void exportXLSForVisaList(){
		Map<String, Object> param=new HashMap<String, Object>();
 
		if(StringUtils.isNotBlank(travelGroupCode)){
			param.put("travelGroupCode",travelGroupCode);
		}
		if(null!=getVisaApprovalIds()){
			param.put("visaApprovalIds", getVisaApprovalIds());
		}
		 
 		List<VisaApproval> visaList = visaApprovalService.query(param);

		GroupVisaInformation groupVisaInformation =new GroupVisaInformation();
		//设定团信息
		groupVisaInformation.setNowDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
 		if(visaList.get(0)!=null){
		groupVisaInformation.setCountry(visaList.get(0).getCountry());
		groupVisaInformation.setCity(visaList.get(0).getCity());
		groupVisaInformation.setVisaType(visaList.get(0).getVisaType());
		groupVisaInformation.setProductName(visaList.get(0).getProductName());
		groupVisaInformation.setTravelGroupCode(visaList.get(0).getTravelGroupCode());
		groupVisaInformation.setVisitTime(DateUtil.formatDate(visaList.get(0).getVisitTime(), "yyyy-MM-dd"));
		}
 		
		//设定表数据
		if(visaList!=null){
			List<VisaApprovalDetailsVO> employeeList=new ArrayList<VisaApprovalDetailsVO>();
			List<VisaApprovalDetailsVO> retireList=new ArrayList<VisaApprovalDetailsVO>();
			List<VisaApprovalDetailsVO> studentList=new ArrayList<VisaApprovalDetailsVO>();
			List<VisaApprovalDetailsVO> preschoolsList=new ArrayList<VisaApprovalDetailsVO>();
			List<VisaApprovalDetailsVO> freelanceList=new ArrayList<VisaApprovalDetailsVO>();
			List<VisaApprovalDetailsVO> allList=new ArrayList<VisaApprovalDetailsVO>();//所有人群//VISA_FOR_ALL
				for(VisaApproval v:visaList){
					VisaApprovalDetailsVO  subVisadetailsList=visaApprovalService.queryVerticalDetailsByApprovalId(v.getVisaApprovalId());
					if(v.getOccupation()==null||subVisadetailsList==null){
						continue;
					}else if(v.getOccupation().equals(Constant.VISA_OCCUPATION.VISA_FOR_EMPLOYEE.getCode())){
						employeeList.add(subVisadetailsList);
					}else if(v.getOccupation().equals(Constant.VISA_OCCUPATION.VISA_FOR_RETIRE.getCode())){
						retireList.add(subVisadetailsList);
					}else if(v.getOccupation().equals(Constant.VISA_OCCUPATION.VISA_FOR_STUDENT.getCode())){
						studentList.add(subVisadetailsList);
					}else if(v.getOccupation().equals(Constant.VISA_OCCUPATION.VISA_FOR_PRESCHOOLS.getCode())){
						preschoolsList.add(subVisadetailsList);
					}else if(v.getOccupation().equals(Constant.VISA_OCCUPATION.VISA_FOR_FREELANCE.getCode())){
						freelanceList.add(subVisadetailsList);
					}if(v.getOccupation().equals(Constant.VISA_OCCUPATION.VISA_FOR_ALL.getCode())){
						allList.add(subVisadetailsList);
					}             
			   }
		     //设定各个表数据
 			 groupVisaInformation.setEmployeeList(employeeList);
 			 groupVisaInformation.setRetireList(retireList);
 			 groupVisaInformation.setStudentList(studentList);
 			 groupVisaInformation.setPreschoolsList(preschoolsList);
 			 groupVisaInformation.setFreelanceList(freelanceList);
 			 groupVisaInformation.setAllList(allList);
 			
 			//设定表头
 			 if(employeeList!=null&&(!employeeList.isEmpty())&&employeeList.get(0)!=null&&employeeList.get(0).getTitle()!=null){
 				groupVisaInformation.setEmployeeTableHeads(employeeList.get(0).getTitle());
 			 }
 			 if(retireList!=null&&(!retireList.isEmpty())&&retireList.get(0)!=null&&retireList.get(0).getTitle()!=null){
 				groupVisaInformation.setRetireTableHeads(retireList.get(0).getTitle());
 			 }
 			 if(studentList!=null&&(!studentList.isEmpty())&&studentList.get(0)!=null&&studentList.get(0).getTitle()!=null){
 				groupVisaInformation.setStudentTableHeads(studentList.get(0).getTitle());
 			 }
 			 if(preschoolsList!=null&&(!preschoolsList.isEmpty())&&preschoolsList.get(0)!=null&&preschoolsList.get(0).getTitle()!=null){
 				groupVisaInformation.setPreschoolsTableHeads(preschoolsList.get(0).getTitle());
 			 }
 			 if(freelanceList!=null&&(!freelanceList.isEmpty())&&freelanceList.get(0)!=null&&freelanceList.get(0).getTitle()!=null){
 				groupVisaInformation.setFreelanceTableHeads(freelanceList.get(0).getTitle());
 			 }
 			 if(allList!=null&&(!allList.isEmpty())&&allList.get(0)!=null&&allList.get(0).getTitle()!=null){
 				groupVisaInformation.setAllTableHeads(allList.get(0).getTitle());
 			 }
 			 
 	   }
		//设定输出文件名
		String outPutName="visaInformationTransferTable_";
		if(StringUtils.isNotBlank(travelGroupCode)&&getOrderIds().equals("")){
			outPutName=outPutName+travelGroupCode;
		} 
		if(!getOrderIds().equals("")){
			outPutName=outPutName+getOrderIds();
		}
		outPutName=outPutName+".xls";
		//输出文件
 		getVisaInfomationXls(groupVisaInformation,outPutName);
	}
	private void getVisaInfomationXls(GroupVisaInformation groupVisaInformation,String outPutFile){
		String inputFilePath = "/WEB-INF/resources/template/visaInformationTransferTable.xls";
 		Map<String,Object> beans = new HashMap<String,Object>();
		beans.put("groupInfor", groupVisaInformation);
 		super.exportXLS(beans, inputFilePath, outPutFile);
	}

	/**
	 * 签证适用人群选择页
	 * 
	 * @return
	 */
	@Action("/visa/approval/confirmMaterial")
	public String confirmMaterial() {
		visaApproval = visaApprovalService.queryByPk(searchVisaApprovalId);
		if (null != visaApproval) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("country", visaApproval.getCountry());
			param.put("visaType", visaApproval.getVisaType());
			param.put("city", visaApproval.getCity());
			visaApplicationDocuments = visaApplicationDocumentService
					.query(param);

			VisaApplicationDocument document = new VisaApplicationDocument();
			document.setCountry(visaApproval.getCountry());
			document.setVisaType(visaApproval.getVisaType());
			document.setCity(visaApproval.getCity());
			document.setOccupation(Constant.VISA_OCCUPATION.VISA_FOR_SELF
					.getCode());
			visaApplicationDocuments.add(document);
		}
		return "confirm";
	}

	/**
	 * 保存签证适用人群选择(材料确认)
	 * 
	 * @return
	 */
	@Action("/visa/approval/saveConfirmMaterial")
	public void saveConfirmMaterial() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != this.visaApproval
				&& null != visaApproval.getVisaApprovalId()
				&& StringUtils.isNotBlank(visaApproval.getOccupation())) {
			visaApprovalService.updateOccupation(
					visaApproval.getVisaApprovalId(),
					visaApproval.getOccupation(), getSessionUserNameAndCheck());
			json.put("success", true);
			json.put("message", "");

			visaApproval = visaApprovalService.queryByPk(visaApproval
					.getVisaApprovalId());
			sendWorkOrderTo();
		}
		getResponse().getWriter().print(json.toString());
	}

	/**
	 *  发“签证材料提醒(客服)”工单
	 *  
	 *  @author zhushuying
	 */
	private void sendWorkOrderTo() {
		Long orderId = visaApproval.getOrderId();
		ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if((Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equalsIgnoreCase(ordOrder.getOrderType())
				||Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equalsIgnoreCase(ordOrder.getOrderType())
				||Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equalsIgnoreCase(ordOrder.getOrderType())
				||Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equalsIgnoreCase(ordOrder.getOrderType()))){
			workOrderProxy.sendWorkOrder(ordOrder,
					Constant.WORK_ORDER_TYPE_AND_SENDGROUP.QZCLTX.getWorkOrderTypeCode(),
					null, Boolean.TRUE,Boolean.FALSE, null, 
					this.getSessionUser().getUserName(), null,null,null,false);
		}
	}

	/**
	 * 更新签证明细审核状态
	 * 
	 * @throws IOException
	 */
	@Action("/visa/approval/updateApprovalDetailsStatus")
	public void updateApprovalDetailsStatus() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != this.visaApprovalDetails
				&& null != visaApprovalDetails.getDetailsId()
				&& StringUtils.isNotBlank(visaApprovalDetails
						.getApprovalStatus())) {
			visaApprovalService.updateDetailsApprovalStatus(
					visaApprovalDetails.getDetailsId(),
					visaApprovalDetails.getApprovalStatus(),
					getSessionUserNameAndCheck());
			json.put("success", true);
			json.put("message", "");
		}
		getResponse().getWriter().print(json.toString());
	}

	/**
	 * 显示签证明细页
	 * 
	 * @return
	 */
	@Action("/visa/approval/vitMaterial")
	public String vitMaterial() {
		visaApproval = visaApprovalService.queryByPk(searchVisaApprovalId);
		visaApprovalDetailsList = visaApprovalService
				.queryDetailsByApprovalId(searchVisaApprovalId);
		return "vit";
	}

	/**
	 * 显示签证明细页(readonly)
	 * 
	 * @return
	 */
	@Action("/visa/approval/showVitMaterial")
	public String showVitMaterial() {
		if (null != searchOrderId && null != searchPersonId) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("orderId", searchOrderId);
			param.put("personId", searchPersonId);
			List<VisaApproval> approvals = visaApprovalService.query(param);
			if (null != approvals && !approvals.isEmpty()) {
				visaApproval = visaApprovalService.queryByPk(approvals.get(0)
						.getVisaApprovalId());
				visaApprovalDetailsList = visaApprovalService
						.queryDetailsByApprovalId(approvals.get(0)
								.getVisaApprovalId());
				logs = comLogService.queryByObjectId(
						"VISA_APPROVAL_SENDLOG_TARGET", approvals.get(0)
								.getVisaApprovalId());
				facelogs = comLogService.queryByObjectId(
						"VISA_APPROVAL_FACELOG_TARGET", approvals.get(0)
								.getVisaApprovalId());
				return "showVit";
			} else {
				return ERROR;
			}
		}
		return ERROR;
	}

	/**
	 * 添加签证明细备注
	 * 
	 * @return
	 */
	@Action("/visa/approval/addMemo")
	public String addMemo() {
		visaApprovalDetails = visaApprovalService
				.queryDetailsByPK(visaApprovalDetails.getDetailsId());
		return "memo";
	}

	/**
	 * 保存签证明细备注
	 * 
	 * @throws IOException
	 */
	@Action("/visa/approval/saveMemo")
	public void saveMemo() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != visaApprovalDetails.getDetailsId()
				&& StringUtils.isNotBlank(visaApprovalDetails.getMemo())) {
			visaApprovalService
					.addDetailsMemo(visaApprovalDetails.getDetailsId(),
							visaApprovalDetails.getMemo(),
							getSessionUserNameAndCheck());
			json.put("success", true);
		}
		getResponse().getWriter().print(json.toString());
	}

	/**
	 * 保存保证金形式
	 * 
	 * @throws IOException
	 */
	@Action("/visa/approval/saveDeposit")
	public void saveDeposit() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != visaApproval.getVisaApprovalId()) {
			visaApprovalService.updateDeposit(visaApproval.getVisaApprovalId(),
					visaApproval.getDepositType(), visaApproval.getBank(),
					visaApproval.getAmount(), getSessionUserNameAndCheck());
			json.put("success", true);
		}
		getResponse().getWriter().print(json.toString());
	}

	/**
	 * 更新签证记录审核状态(材料审核)
	 * 
	 * @throws IOException
	 */
	@Action("/visa/approval/updateApprovalStatus")
	public void updateApprovalStatus() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != visaApproval.getVisaApprovalId()
				&& null != visaApproval.getVisaStatus()) {
			visaApprovalService.updateApprovalStatus(
					visaApproval.getVisaApprovalId(),
					visaApproval.getVisaStatus(), getSessionUserNameAndCheck());
			json.put("success", true);

			visaApproval = visaApprovalService.queryByPk(visaApproval
					.getVisaApprovalId());
			//材料审核(审核通过/审核不通过)，发系统工单
			if(Constant.VISA_STATUS.PASS_APPROVAL.getCode().equals(visaApproval.getVisaStatus())
					|| Constant.VISA_STATUS.UNPASS_APPROVAL.getCode().equals(visaApproval.getVisaStatus())){
				sendWorkOrderTo();
			}
		}
		getResponse().getWriter().print(json.toString());
	}

	/**
	 * 批量更新签证记录审核状态
	 * 
	 * @throws IOException
	 */
	@Action("/visa/approval/batchUpdateApprovalStatus")
	public void batchUpdateApprovalStatus() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != batchVisaApprovalId
				&& StringUtils.isNotBlank(batchVisaApprovalId)
				&& null != visaApproval.getVisaStatus()) {
			String[] visaApprovalIds = batchVisaApprovalId.split(",");
			for (String visaApprovalId : visaApprovalIds) {
				visaApprovalService.updateApprovalStatus(
						Long.parseLong(visaApprovalId),
						visaApproval.getVisaStatus(),
						getSessionUserNameAndCheck());
			}
			json.put("success", true);
		}

		getResponse().getWriter().print(json.toString());
	}

	/**
	 * 显示快递
	 * 
	 * @return
	 */
	@Action("/visa/approval/sendMaterial")
	public String sendMaterial() {
		visaApproval = visaApprovalService.queryByPk(searchVisaApprovalId);
		logs = comLogService.queryByObjectId("VISA_APPROVAL_SENDLOG_TARGET",
				this.searchVisaApprovalId);
		return "send";
	}

	@Action(value = "/visa/approval/batchSendMaterial")
	public String batchSendMaterial() {
		return "batchSend";
	}

	@Action(value = "/visa/approval/saveSendLog")
	public void saveSendLog() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != visaApproval && null != visaApproval.getVisaApprovalId()
				&& StringUtils.isNotBlank(content)) {
			visaApprovalService.addSendLog(visaApproval.getVisaApprovalId(),
					content, pid, this.getSessionUserNameAndCheck());
			json.put("success", true);
		}
		getResponse().getWriter().print(json.toString());
	}

	/**
	 * 显示面签/面销通知书
	 * 
	 * @return
	 */
	@Action("/visa/approval/faceVisa")
	public String faceVisa() {
		visaApproval = visaApprovalService.queryByPk(searchVisaApprovalId);
		logs = comLogService.queryByObjectId("VISA_APPROVAL_FACELOG_TARGET",
				this.searchVisaApprovalId);
		return "face";
	}
	public List<VisaApproval> getVisaApprovalList() {
		return visaApprovalList;
	}
	@Action(value = "/visa/approval/batchFaceVisa")
	public String batchFaceVisa() {
		return "batchFace";
	}

	/**
	 * 保存面签/面销通知书(上传面签/面销通知书)
	 * 
	 * @throws IOException
	 */
	@Action(value = "/visa/approval/saveFaceLog")
	public void saveFaceLog() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != visaApproval && null != visaApproval.getVisaApprovalId()
				&& StringUtils.isNotBlank(content)) {
			visaApprovalService.addFaceLog(visaApproval.getVisaApprovalId(),
					content, pid, this.getSessionUserNameAndCheck());
			json.put("success", true);

			visaApproval = visaApprovalService.queryByPk(visaApproval
					.getVisaApprovalId());
			sendWorkOrderTo();
		}
		getResponse().getWriter().print(json.toString());
	}

	@Action(value = "/visa/approval/batchSaveSendLog")
	public void batchSaveSendLog() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != batchVisaApprovalId
				&& StringUtils.isNotBlank(batchVisaApprovalId)) {
			String[] visaApprovalIds = batchVisaApprovalId.split(",");
			for (String visaApprovalId : visaApprovalIds) {
				visaApprovalService.addSendLog(Long.parseLong(visaApprovalId),
						content, pid, this.getSessionUserNameAndCheck());
			}
			json.put("success", true);
		}
		getResponse().getWriter().print(json.toString());
	}

	@Action(value = "/visa/approval/batchSaveFaceLog")
	public void batchSaveFaceLog() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != batchVisaApprovalId
				&& StringUtils.isNotBlank(batchVisaApprovalId)) {
			String[] visaApprovalIds = batchVisaApprovalId.split(",");
			for (String visaApprovalId : visaApprovalIds) {
				visaApprovalService.addFaceLog(Long.parseLong(visaApprovalId),
						content, pid, this.getSessionUserNameAndCheck());
			}
			json.put("success", true);
		}
		getResponse().getWriter().print(json.toString());
	}

	/**
	 * 增补材料
	 * 
	 * @return
	 */
	@Action("/visa/approval/addMaterial")
	public String addMaterial() {
		return "add";
	}

	/**
	 * 显示获签状态
	 * 
	 * @return
	 */
	@Action("/visa/approval/getVisa")
	public String getVisa() {
		visaApproval = visaApprovalService.queryByPk(searchVisaApprovalId);
		return "get";
	}

	/**
	 * 显示获签状态
	 * 
	 * @return
	 */
	@Action("/visa/approval/batchGetVisa")
	public String batchGetVisa() {
		return "batchGet";
	}

	/**
	 * 显示增补签证材料(增补材料)
	 * @throws IOException
	 */
	@Action("/visa/approval/saveAddMaterial")
	public void saveAddMaterial() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != visaApprovalDetails.getVisaApprovalId()
				&& StringUtils.isNotBlank(visaApprovalDetails.getContent())) {
			visaApprovalService.insertVisaApprovalDetails(
					visaApprovalDetails.getVisaApprovalId(),
					visaApprovalDetails.getContent(),
					getSessionUserNameAndCheck());
			json.put("success", true);
			visaApproval = visaApprovalService.queryByPk(visaApprovalDetails
					.getVisaApprovalId());
			sendWorkOrderTo();
		}
		getResponse().getWriter().print(json.toString());
	}

	/**
	 * 退还材料
	 * 
	 * @return
	 */
	@Action("/visa/approval/returnMaterial")
	public void returnMaterial() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != visaApproval.getVisaApprovalId()) {
			visaApprovalService.returnMaterial(
					visaApproval.getVisaApprovalId(),
					getSessionUserNameAndCheck());
			json.put("success", true);

			visaApproval = visaApprovalService.queryByPk(visaApproval
					.getVisaApprovalId());
			sendWorkOrderTo();
		}
		getResponse().getWriter().print(json.toString());
	}

	/**
	 * 批量退还材料
	 * 
	 * @return
	 */
	@Action("/visa/approval/batchReturnMaterial")
	public void batchReturnMaterial() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != batchVisaApprovalId
				&& StringUtils.isNotBlank(batchVisaApprovalId)) {
			String[] visaApprovalIds = batchVisaApprovalId.split(",");
			for (String visaApprovalId : visaApprovalIds) {
				visaApprovalService.returnMaterial(
						Long.parseLong(visaApprovalId),
						getSessionUserNameAndCheck());
			}
			json.put("success", true);
		}
		getResponse().getWriter().print(json.toString());
	}

	/**
	 * 退还保证金
	 * 
	 * @return
	 */
	@Action("/visa/approval/returnDeposit")
	public void returnDeposit() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != visaApproval.getVisaApprovalId()) {
			visaApprovalService.returnDeposit(visaApproval.getVisaApprovalId(),
					getSessionUserNameAndCheck());
			json.put("success", true);

			visaApproval = visaApprovalService.queryByPk(visaApproval
					.getVisaApprovalId());
			sendWorkOrderTo();
		}
		getResponse().getWriter().print(json.toString());
	}

	/**
	 * 批量退还保证金
	 * 
	 * @return
	 */
	@Action("/visa/approval/batchReturnDeposit")
	public void batchReturnDeposit() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		if (null != batchVisaApprovalId
				&& StringUtils.isNotBlank(batchVisaApprovalId)) {
			String[] visaApprovalIds = batchVisaApprovalId.split(",");
			for (String visaApprovalId : visaApprovalIds) {
				visaApprovalService.returnDeposit(
						Long.parseLong(visaApprovalId),
						getSessionUserNameAndCheck());
			}
			json.put("success", true);
		}
		getResponse().getWriter().print(json.toString());
	}

	/**
	 * 初始化查询参数
	 * 
	 * @return
	 */
	private Map<String, Object> initParam() {
		Map<String, Object> param = new HashMap<String, Object>();
		if (null != searchOrderId) {
			param.put("orderId", searchOrderId);
		}
		if (StringUtils.isNotBlank(searchTravelGroupCode)) {
			param.put("travelGroupCode", searchTravelGroupCode);
		}
		if (StringUtils.isNotBlank(searchVisaStatus)) {
			param.put("visaStatus", searchVisaStatus);
		}
		if (StringUtils.isNotBlank(searchStartVisitDate)) {
			param.put("startVisitDate",
					DateUtil.toDate(searchStartVisitDate, "yyyy-MM-dd"));
		}
		if (StringUtils.isNotBlank(searchEndVisitDate)) {
			param.put("endVisitDate", DateUtil.getDayEnd(DateUtil.toDate(
					searchEndVisitDate, "yyyy-MM-dd")));
		}
		if (StringUtils.isNotBlank(searchName)) {
			param.put("name", searchName);
		}
		if (StringUtils.isNotBlank(searchTele)) {
			param.put("tele", searchTele);
		}
		if(StringUtils.isNotBlank(searchOrderStatus)){
			param.put("orderStatus", searchOrderStatus);
		}
		if(StringUtils.isNotBlank(searchRegionName)){
			param.put("regionName", searchRegionName);
		}
		return param;
	}

	public Long getSearchOrderId() {
		return searchOrderId;
	}

	public void setSearchOrderId(Long searchOrderId) {
		this.searchOrderId = searchOrderId;
	}

	public String getSearchTravelGroupCode() {
		return searchTravelGroupCode;
	}

	public void setSearchTravelGroupCode(String searchTravelGroupCode) {
		this.searchTravelGroupCode = searchTravelGroupCode;
	}

	public String getSearchStartVisitDate() {
		return searchStartVisitDate;
	}

	public void setSearchStartVisitDate(String searchStartVisitDate) {
		this.searchStartVisitDate = searchStartVisitDate;
	}

	public String getSearchEndVisitDate() {
		return searchEndVisitDate;
	}

	public void setSearchEndVisitDate(String searchEndVisitDate) {
		this.searchEndVisitDate = searchEndVisitDate;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchTele() {
		return searchTele;
	}

	public void setSearchTele(String searchTele) {
		this.searchTele = searchTele;
	}

	public void setVisaApprovalService(VisaApprovalService visaApprovalService) {
		this.visaApprovalService = visaApprovalService;
	}

	public Long getSearchVisaApprovalId() {
		return searchVisaApprovalId;
	}

	public void setSearchVisaApprovalId(Long searchVisaApprovalId) {
		this.searchVisaApprovalId = searchVisaApprovalId;
	}

	public VisaApproval getVisaApproval() {
		return visaApproval;
	}

	public void setVisaApproval(VisaApproval visaApproval) {
		this.visaApproval = visaApproval;
	}

	public List<VisaApplicationDocument> getVisaApplicationDocuments() {
		return visaApplicationDocuments;
	}

	public void setVisaApplicationDocumentService(
			VisaApplicationDocumentService visaApplicationDocumentService) {
		this.visaApplicationDocumentService = visaApplicationDocumentService;
	}

	public List<VisaApprovalDetails> getVisaApprovalDetailsList() {
		return visaApprovalDetailsList;
	}

	public VisaApprovalDetails getVisaApprovalDetails() {
		return visaApprovalDetails;
	}

	public void setVisaApprovalDetails(VisaApprovalDetails visaApprovalDetails) {
		this.visaApprovalDetails = visaApprovalDetails;
	}

	public String getBatchVisaApprovalId() {
		return batchVisaApprovalId;
	}

	public void setBatchVisaApprovalId(String batchVisaApprovalId) {
		this.batchVisaApprovalId = batchVisaApprovalId;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public List<ComLog> getLogs() {
		return logs;
	}

	public String getSearchVisaStatus() {
		return searchVisaStatus;
	}

	public void setSearchVisaStatus(String searchVisaStatus) {
		this.searchVisaStatus = searchVisaStatus;
	}

	public Long getSearchPersonId() {
		return searchPersonId;
	}

	public void setSearchPersonId(Long searchPersonId) {
		this.searchPersonId = searchPersonId;
	}

	public List<ComLog> getFacelogs() {
		return facelogs;
	}
	public String getTravelGroupCode() {
		return travelGroupCode;
	}
	public void setTravelGroupCode(String travelGroupCode) {
		this.travelGroupCode = travelGroupCode;
	}
	public String[] getVisaApprovalIds() {
		if(StringUtils.isNotBlank(visaApprovalIds)){
			String[] strList= visaApprovalIds.split(",");
			return strList;
		}else {
			return null;
		}
	}
	public void setVisaApprovalIds(String visaApprovalIds) {
		this.visaApprovalIds = visaApprovalIds;
	}
	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}
	/**
	 * 获取订单号格式”id_id_id“
	 * @return
	 * @author:nixianjun 2013-6-14
	 */
	public String getOrderIds() {
		if(StringUtils.isNotBlank(orderIds)){
			List<String> orderIdList=new ArrayList<String>();
			String[]  strList= orderIds.split(",");
			 StringBuffer  destStr=new StringBuffer("");
			for(String str:strList){
			   if(!orderIdList.contains(str)){
				   orderIdList.add(str);
			   }
			}
			 int i=0;
			 for(String str:orderIdList){
				 if(i==orderIdList.size()-1){
					 destStr.append(str);
				 }else{
					 destStr.append(str+"_");
				 }
				 i++;
			 }
			return destStr.toString();
		}else {
			return "";
		}
	}

	public String getSearchOrderStatus() {
		return searchOrderStatus;
	}
	public void setSearchOrderStatus(String searchOrderStatus) {
		this.searchOrderStatus = searchOrderStatus;
	}
	public String getSearchRegionName() {
		return searchRegionName;
	}
	public void setSearchRegionName(String searchRegionName) {
		this.searchRegionName = searchRegionName;
	}
	public WorkOrderSenderBiz getWorkOrderProxy() {
		return workOrderProxy;
	}
	public void setWorkOrderProxy(WorkOrderSenderBiz workOrderProxy) {
		this.workOrderProxy = workOrderProxy;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public OrdOrder getOrdOrder() {
		return ordOrder;
	}

	public void setOrdOrder(OrdOrder ordOrder) {
		this.ordOrder = ordOrder;
	}

}
