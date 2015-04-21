package com.lvmama.back.sweb.groupadvice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderRoute;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.GroupAdviceNoteService;
import com.lvmama.comm.bee.service.SmsService;
import com.lvmama.comm.bee.service.ord.IGroupAdviceNoteService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.view.ViewPageJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.client.EContractClient;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.pub.ComAffix;
import com.lvmama.comm.pet.service.pub.ComAffixService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderFinishedBiz;

/**
 * 
 * 出团通知书上传下载类.<br/>
 * 主要功能:<br/>
 * 1.出团通知书在线模板、word模板、 word文件的上传功能,<br/>
 * 2.向在线模板、word模板中填充订单、产品、行程信息,<br/>
 * 3.将word文档转换为pdf文档,<br/>
 * 4.文档上传之后通知游客(邮件、短信),通知客服(系统消息),<br/>
 * 5.文档的下载功能,提供列表下载、最近上传文档下载,<br/>
 * 
 * @author nixianjun
 */
@ParentPackage("json-default")
@SuppressWarnings("serial")
@Results({
		@Result(name = "error", location = "/WEB-INF/pages/back/groupadvice/file_upload_error.jsp"),
		@Result(name = "form", location = "/WEB-INF/pages/back/groupadvice/file_upload.jsp"),
		@Result(name = "success", location = "/groupadvice/upload.do?objectId=${objectId}&objectType=${objectType}", type = "redirect"),
		@Result(name = "detailEcontract", location = "/WEB-INF/pages/back/groupadvice/editContract.jsp"),
		@Result(name = "formCT", location = "/WEB-INF/pages/back/groupadvice/file_upload_ct.jsp"),
		@Result(name = "SUCCESSCT", location = "/WEB-INF/pages/back/groupadvice/file_upload_success.jsp"),
		@Result(name = "formDCT", location = "/WEB-INF/pages/back/groupadvice/file_download_dct.jsp"),
		@Result(name = "SUCCESSDCT", location = "/WEB-INF/pages/back/groupadvice/file_dwload_success.jsp"),
		@Result(name = "batchUseSysTpl", location = "/WEB-INF/pages/back/groupadvice/batch_tpl_preview.jsp"),
		@Result(name = "batchUseUploadTpl", location = "/WEB-INF/pages/back/groupadvice/batch_upload_doc.jsp"),
		@Result(name = "batchUploadFile", location = "/WEB-INF/pages/back/groupadvice/batch_upload_file.jsp") })
public class UploadOrDownloadAction extends BaseAction {
	/**
	 * 日志.
	 */
	private static Log logger = LogFactory.getLog(UploadOrDownloadAction.class);
	private OrderService orderServiceProxy;
	private ComAffixService comAffixService;
	private Long objectId;
	private String objectType;
	private File file;
	private String fileContentType;
	private String fileFileName;
	/**
	 * 附件类
	 */
	private ComAffix affix = new ComAffix();
	private String fileId;// 文件存放的id
	private String fileName;
	/**
	 * 文件系统服务.
	 */
	private FSClient fsClient;

	private TopicMessageProducer orderMessageProducer;
	private SmsService smsService;

	private ComMessageService comMessageService;
	private GroupAdviceNoteService groupAdviceNoteService;
	private ComLogService comLogService;
	private WorkOrderFinishedBiz workOrderFinishedProxy;

	private String content;
	private String contentFirstFix;
	private String contentEndFix;

	private ViewPageJourneyService viewPageJourneyService = (ViewPageJourneyService) SpringBeanProxy
			.getBean("viewPageJourneyService");
	private ProdProductService prodProductService = (ProdProductService) SpringBeanProxy
			.getBean("prodProductService");
	private ViewPageService viewPageService = (ViewPageService) SpringBeanProxy
			.getBean("viewPageService");
	private EContractClient contractClient;
	
	private IGroupAdviceNoteService groupAdviceNoteServiceProxy;

	private String jsonMsg;
	private String orderId;// 订单id
	private String groupWordStatus;// 出团通知书状态

	private String objectIds;// 批量处理订单id，分号隔开
	private String batchAffixName;// 文件名
	private String batchAffixMemo;// 文件描述

	/**
	 * 上传出团通知书,使用出团通知书在线模版.
	 */
	@Action("/groupadvice/uploadOnlineHtmlTemplate")
	public String uploadGroupNoticeFileByOnlineHtmlTemplate() {
		this.editOnlineTemplate();
		return "detailEcontract";
	}

	/**
	 * 重新上传出团通知书,使用出团通知书在线模版.
	 */
	@Action("/groupadvice/ReUploadOnlineHtmlTemplate")
	public String ReUploadGroupNoticeFileByOnlineHtmlTemplate() {
		this.editOnlineTemplate();
		return "detailEcontract";

	}

	// 使用出团通知书模版(页面上显示在线模板.)
	private void editOnlineTemplate() {
		String filePath = this
				.createGroupAdviceNote(GroupAdviceNote.HTML_TEMPLATE);
		try {
			this.content = org.springframework.util.FileCopyUtils
					.copyToString(new BufferedReader(new InputStreamReader(
							new FileInputStream(filePath), "UTF-8")));
		} catch (FileNotFoundException e) {
			logger.info(e);
		} catch (IOException e) {
			logger.info(e);
		}

		String[] array = content.split("<body>");
		if (array.length > 1) {
			contentFirstFix = array[0] + "<body>";
			String[] endArray = array[1].split("</body>");
			if (endArray.length > 1) {
				contentEndFix = "</body>" + endArray[1];
			}
		}
		this.affix.setObjectId(this.objectId);
		this.affix.setObjectType(this.objectType);

		File htmlFilePath = new File(filePath);
		if (htmlFilePath.exists()) {
			htmlFilePath.delete();
		}
	}

	/**
	 * (重新上传)文件上传后进行的处理(出团通知书).<br/>
	 * 此方法对应保存在线模板.<br/>
	 * 包括:<br/>
	 * 1.文件信息保存到数据库.<br/>
	 * 2.通知游客(邮件、短信),客服(系统消息).<br/>
	 * 3.更改出团通知书状态.<br/>
	 * 
	 * @throws Exception
	 * 
	 */
	@Action("/groupadvicenote/saveGroupAdviceNote")
	public void saveGroupAdviceNote() {
		content = content.replaceAll("&nbsp(;)?", " ").replaceAll(
				"<br(\\s*)/>", "<br/>");
		String[] array = content.split("</style>");
		this.content = GroupAdviceNoteUtils.convert(this.content);
		content = contentFirstFix + array[1] + contentEndFix;
		String pdfFileName = GroupAdviceNoteUtils.createFileName(
				this.affix.getObjectId(), GroupAdviceNoteUtils.PDF_FILE_SUFFIX);
		GroupAdviceNoteUtils.createPdfDocument(this.content, pdfFileName);
		File pdfFile = new File(pdfFileName);
		String affixName = GroupAdviceNoteUtils.createAffixName(
				this.affix.getObjectId(), GroupAdviceNoteUtils.PDF_FILE_SUFFIX);
		try {
			Long fileId = fsClient.uploadFile(pdfFile, "COM_AFFIX");
			if (fileId != null && pdfFile.exists()) {
				pdfFile.delete();
			}

			// 文件添加记录
			objectId = affix.getObjectId();
			objectType = affix.getObjectType();
			affix.setUserId(this.getOperatorName());
			affix.setName(affixName);
			affix.setFileId(fileId);// 设定文件路径id
			affix.setCreateTime(new Date());
			affix.setFileType(ComAffix.GROUP_ADVICE_NOTE_FILE_TYPE);// 文件类型：出团通知书

			if (affix.getMemo() == null || "".equals(affix.getMemo().trim())) {
				affix.setMemo("ONLINE_TEMPLATE");
			}
			comAffixService.addAffixForGroupAdvice(affix, getOperatorName());// 插入文件记录

			// 发送通知和邮件
			sendGroupAdviceNote(affix.getObjectId());
		} catch (Exception ex) {
			ex.printStackTrace();
			addFieldError("name", "保存错误");
			this.sendAjaxResult("false");
		}
		this.sendAjaxResult("true");
	}

	/**
	 * 上传出团通知书,上传文件.
	 */
	@Action("/groupadvice/uploadDirectly")
	public String uploadGroupNoticeFileDirectly() {
		if (getRequest().getMethod().equalsIgnoreCase("POST")) {
			return doUpload();
		} else {
			return entry();
		}
	}

	/**
	 * 重新上传出团通知书,上传文件.
	 */
	@Action("/groupadvice/ReUploadFileDirectly")
	public String ReUploadGroupNoticeFileDirectly() {
		if (getRequest().getMethod().equalsIgnoreCase("POST")) {
			return doReUploadByFileDirectly();
		} else {
			return entry();
		}
	}

	/**
	 * 上传出团通知书,上传模版文件.
	 */
	@Action("/groupadvice/uploadFileTemplate")
	public String uploadGroupNoticeFileByFileTemplate() {
		if (getRequest().getMethod().equalsIgnoreCase("POST")) {
			return doUploadByFileTemplate();
		} else {
			return entry();
		}
	}

	/**
	 * 文件上传后进行的处理(出团通知书).<br/>
	 * 此方法对应直接上传Wrod模板文档,需要向模板文档中填充订单、产品、行程信息.<br/>
	 * 包括:<br/>
	 * 1.文件信息保存到数据库.<br/>
	 * 2.通知游客(邮件、短信),客服(系统消息).<br/>
	 * 3.更改出团通知书状态.<br/>
	 */
	@SuppressWarnings("deprecation")
	private String doUploadByFileTemplate() {
		String name = fileFileName;
		int pos = name.lastIndexOf(".");
		if (pos == -1) {
			addFieldError("name", "文件类型错误");
			return ERROR;
		}
		String[] names = fileFileName.split("\\.");
		if (names[1].equalsIgnoreCase("doc")) {
			addFieldError("name", "不支持2003 word!!!");
			return ERROR;
		}

		String docxTemplate = this
				.createGroupAdviceNote(GroupAdviceNote.DOCX_TEMPLATE);
		File docxInputFile = new File(docxTemplate);
		try {
			Long fileId = fsClient.uploadFile(docxInputFile, "COM_AFFIX");
			if (fileId != null) {
				if (docxInputFile.exists()) {
					docxInputFile.delete();
				}
			}

			String affixName = this.getAffixName();

			objectId = affix.getObjectId();
			objectType = affix.getObjectType();
			affix.setUserId(this.getOperatorName());
			affix.setName(affixName);
			affix.setFileId(fileId);// 设定文件路径id
			affix.setCreateTime(new Date());
			affix.setFileType(ComAffix.GROUP_ADVICE_NOTE_FILE_TYPE);// 文件类型：出团通知书
			this.comAffixService.addAffixForGroupAdvice(affix,
					this.getOperatorName());

			// 发送通知和邮件
			sendGroupAdviceNote(affix.getObjectId());

		} catch (Exception ex) {
			ex.printStackTrace();
			addFieldError("name", "保存错误");
			return entry();
		}
		return "SUCCESSCT";
	}

	/**
	 * 显示上传文件开始页面
	 */
	private String entry() {
		if (objectId == null || StringUtils.isEmpty(objectType))
			return ERROR;

		affix.setObjectId(objectId);
		affix.setObjectType(objectType);

		return "formCT";
	}

	/**
	 * 文件上传后进行的处理(出团通知书).<br/>
	 * 此方法对应直接上传完整的Wrod文档.<br/>
	 * 包括:<br/>
	 * 1.文件信息保存到数据库.<br/>
	 * 2.通知游客(邮件、短信),客服(系统消息).<br/>
	 * 3.更改出团通知书状态.<br/>
	 */
	@SuppressWarnings("deprecation")
	private String doUpload() {
		String name = fileFileName;
		int pos = name.lastIndexOf(".");
		if (pos == -1) {
			addFieldError("name", "文件类型错误");
			return ERROR;
		}
		try {
			// 上传文件调用
			Long fileId = fsClient.uploadFile(this.file, "COM_AFFIX");
			String affixName = this.getAffixName();

			// 文件添加记录
			objectId = affix.getObjectId();
			objectType = affix.getObjectType();
			affix.setUserId(this.getOperatorName());
			affix.setName(affixName);
			affix.setFileId(fileId);// 设定文件路径id
			affix.setCreateTime(new Date());
			affix.setFileType(ComAffix.GROUP_ADVICE_NOTE_FILE_TYPE);// 文件类型：出团通知书
			comAffixService.addAffixForGroupAdvice(affix, getOperatorName());// 插入文件记录
			// 发送通知和邮件
			sendGroupAdviceNote(affix.getObjectId());
		} catch (Exception ex) {
			ex.printStackTrace();
			addFieldError("name", "保存错误");
			return entry();
		}

		return "SUCCESSCT";
	}

	/**
	 * (重新上传)文件上传后进行的处理(出团通知书).<br/>
	 * 此方法对应直接上传完整的Wrod文档.<br/>
	 * 包括:<br/>
	 * 1.文件信息保存到数据库.<br/>
	 * 2.通知游客(邮件、短信),客服(系统消息).<br/>
	 * 3.更改出团通知书状态.<br/>
	 */
	@SuppressWarnings("deprecation")
	private String doReUploadByFileDirectly() {
		String name = fileFileName;

		int pos = name.lastIndexOf(".");
		if (pos == -1) {
			addFieldError("name", "文件类型错误");
			return ERROR;
		}
		try {
			Long fileId = fsClient.uploadFile(this.file, "COM_AFFIX");
			String affixName = this.getAffixName();

			// 文件添加记录
			objectId = affix.getObjectId();
			objectType = affix.getObjectType();
			affix.setUserId(this.getOperatorName());
			affix.setName(affixName);
			affix.setFileId(fileId);// 设定文件路径id
			affix.setCreateTime(new Date());
			affix.setFileType(ComAffix.GROUP_ADVICE_NOTE_FILE_TYPE);// 文件类型：出团通知书
			comAffixService.addAffixForGroupAdvice(affix, getOperatorName());// 插入文件记录

			// 发送通知和邮件
			sendGroupAdviceNote(affix.getObjectId());
		} catch (Exception ex) {
			ex.printStackTrace();
			addFieldError("name", "保存错误");
			return entry();
		}

		return "SUCCESSCT";
	}

	/**
	 * 显示下载出团通知书列表.
	 * 
	 * @author nixianjun
	 * @CreateDate 2012-7-16
	 */
	@Action("/groupadvice/dwload")
	public String dwLoadGroupAdviceForList() {

		if (objectId == null || StringUtils.isEmpty(objectType))
			return ERROR;

		affix.setObjectId(objectId);
		affix.setObjectType(objectType);
		affix.setFileType(ComAffix.GROUP_ADVICE_NOTE_FILE_TYPE);// 文件类型：出团通知书

		initPagination();// 初始化分页
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));// 提交actonUrl

		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("objectId", objectId);
		parameter.put("objectType", objectType);
		parameter.put("fileType", affix.getFileType());
		long count = comAffixService.selectCountByParam(parameter);
		pagination.setTotalRecords(count);
		logger.info("super 操作人：" + getOperatorName() + " 操作内容：下载出团通知书    列表："
				+ getPagination().toString());
		if (count > 0) {
			parameter.put("skipResult", pagination.getFirstRow() - 1);
			parameter.put("maxResult", pagination.getLastRow());
			pagination.setRecords(comAffixService
					.selectListForTimeDescByParam(parameter));
		}
		logger.info("super 操作人：" + getOperatorName() + " 操作内容：下载出团通知书    别表："
				+ getPagination().toString());
		return "formDCT";
	}

	/**
	 * 下载出团通知书(最新的一个),提供给游客在"我的订单"下载.
	 */
	@Action("/groupadvice/dwloadLatestGroupAdvice")
	public void dwloadLatestGroupAdvice() {

		affix.setObjectId(objectId);
		affix.setObjectType(objectType);
		affix.setFileType(ComAffix.GROUP_ADVICE_NOTE_FILE_TYPE);// 文件类型：出团通知书
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("objectId", affix.getObjectId());
		parameter.put("objectType", affix.getObjectType());
		parameter.put("fileType", affix.getFileType());

		long count = comAffixService.selectCountByParam(parameter);
		// pagination.setTotalRecords(count);
		ComAffix com = this.affix;
		if (count > 0) {
			com = comAffixService.selectLatestRecordByParam(parameter);
			// 下载文件
			downLoad(com.getFileId(), com.getName());
		}

	}

	/**
	 * 根据文件标识下载指定文件.
	 */
	@Action("/groupadvice/download")
	public void downLoad() {

		if (fileId == null || fileId.equals("")) {
			logger.info("操作人：" + getOperatorName() + " 文件id为空！");
		} else {
			OutputStream os = null;
			try {
				getResponse().setHeader("Content-Disposition",
						"attachment; filename=" + fileName);
				os = getResponse().getOutputStream();
				ComFile resultFile = fsClient
						.downloadFile(Long.valueOf(fileId));
				IOUtils.copy(resultFile.getInputStream(), os);
				os.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				IOUtils.closeQuietly(os);
			}
		}
	}

	/**
	 * 文件下载
	 * 
	 * @param fileId
	 *            文件对应id
	 * @param fileName
	 *            文件名称
	 */
	private void downLoad(Long fileIdStr, String fileNameStr) {

		if (fileId == null || fileId.equals("")) {
			logger.info("操作人：" + getOperatorName() + " 文件id为空！");
		} else {
			OutputStream os = null;
			try {
				getResponse().setHeader("Content-Disposition",
						"attachment; filename=" + fileNameStr);
				os = getResponse().getOutputStream();
				ComFile resultFile = fsClient.downloadFile(fileIdStr);
				IOUtils.copy(resultFile.getInputStream(), os);
				os.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				IOUtils.closeQuietly(os);
			}
		}

	}

	/**
	 * 提供给游客邮件附件中的出团通知书名字.
	 * 
	 * @return
	 */
	private String getAffixName() {
		String fileSuffix = "";

		if (this.fileFileName.endsWith(GroupAdviceNoteUtils.PDF_FILE_SUFFIX)) {
			fileSuffix = GroupAdviceNoteUtils.PDF_FILE_SUFFIX;
		} else if (this.fileFileName
				.endsWith(GroupAdviceNoteUtils.DOCX_FILE_SUFFIX)) {
			fileSuffix = GroupAdviceNoteUtils.DOCX_FILE_SUFFIX;
		} else if (this.fileFileName
				.endsWith(GroupAdviceNoteUtils.DOC_FILE_SUFFIX)) {
			fileSuffix = GroupAdviceNoteUtils.DOC_FILE_SUFFIX;
		} else {
			throw new RuntimeException("不支持的文件格式名:" + this.fileFileName);
		}
		String affixName = GroupAdviceNoteUtils.createAffixName(
				this.affix.getObjectId(), fileSuffix);
		return affixName;
	}

	// 获取订单中的信息填充到相应的html,docx模板中去.
	private String createGroupAdviceNote(String type) {
		GroupAdviceNote groupAdviceNote = GroupAdviceNote.getInstance(type);
		if (GroupAdviceNote.DOCX_TEMPLATE.equals(type)) {
			String templatePath = this.file.getAbsolutePath();
			groupAdviceNote.setTemplatePath(templatePath);
		}
		Long orderId = (this.objectId == null ? this.getAffix().getObjectId()
				: this.objectId);
		OrdOrder ordOrder = this.orderServiceProxy
				.queryOrdOrderByOrderId(orderId);
		groupAdviceNote.setOrdOrder(ordOrder);
		// List<ViewJourney> list =
		// this.viewPageJourneyService.getViewJourneysByProductId(ordOrder.getMainProduct().getProductId());
		// groupAdviceNote.setViewJourneyList(list);
		ProdProduct p = this.prodProductService.getProdProductById(ordOrder
				.getMainProduct().getProductId());
		ProdRoute prodProduct = this.prodProductService
				.getProdRouteById(ordOrder.getMainProduct().getProductId());
		prodProduct.setSubProductType(p.getSubProductType());
		groupAdviceNote.setProdProduct(prodProduct);
		// ViewPage v = this.viewPageService.getViewPage(p.getProductId());
		initViewPageAndJourney(groupAdviceNote, orderId);
		groupAdviceNote.createFile();
		return groupAdviceNote.getFileName();
	}

	// 发送出团通知书邮件、短信、提醒
	private void sendGroupAdviceNote(Long orderId) {
		ResultHandleT<Boolean> handle = groupAdviceNoteServiceProxy
				.sendGroupAdviceNote(orderId, getOperatorNameAndCheck());
		if (handle.isSuccess() && handle.getReturnContent()) {
			if (null != getSession().getAttribute("workTaskId")) {
				String paramId = getSession().getAttribute("workTaskId")
						.toString();
				workOrderFinishedProxy.finishWorkOrder(Long.valueOf(paramId),
						"", getSessionUser().getUserName());
				getSession().removeAttribute("workTaskId");
			}
		}
	}

	/**
	 * 更改出团通知书状态为通知状态（已发送已通知，修改已通知）
	 */
	@Action(value = "/groupadvicenote/updateNoticeStatus", results = @Result(type = "json", name = "updateNoticeStatus", params = {
			"includeProperties", "jsonMsg.*" }))
	public String updateNoticeStatus() {
		try {
			// 依据订单ID更改订单附加团信息.
			OrdOrderRoute orderRoute = orderServiceProxy
					.queryOrdOrderRouteByOrderId(Long.valueOf(this.orderId));
			if (orderRoute != null && orderRoute.getGroupWordStatus() != null) {
				if (orderRoute.getGroupWordStatus().equals(
						Constant.GROUP_ADVICE_STATE.SENT_NO_NOTICE.name())) {
					orderRoute
							.setGroupWordStatus(Constant.GROUP_ADVICE_STATE.SENT_NOTICE
									.name());
					orderServiceProxy.updateOrderRoute(orderRoute);
					this.jsonMsg = "ok";
				} else if (orderRoute.getGroupWordStatus().equals(
						Constant.GROUP_ADVICE_STATE.MODIFY_NO_NOTICE.name())) {
					orderRoute
							.setGroupWordStatus(Constant.GROUP_ADVICE_STATE.MODIFY_NOTICE
									.name());
					orderServiceProxy.updateOrderRoute(orderRoute);
					this.jsonMsg = "ok";
				}
				// add by zhushuying "已通知用户出团通知书"操作完成,结束工单
				// mod by zhangwengang 手工结束工单
				/*if (null != getSession().getAttribute("workTaskId")) {
					String paramId = getSession().getAttribute("workTaskId")
							.toString();
					creatorComplete = workOrderFinishedProxy.finishWorkOrder(
							Long.parseLong(paramId), getSessionUser()
									.getUserName());
					getSession().removeAttribute("workTaskId");
					this.jsonMsg = creatorComplete;
				}*/
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.jsonMsg = "error";
		}
		return "updateNoticeStatus";
	}

	// -----------------------------add by
	// taiqichao-------------------------------------start//
	/**
	 * 获取批量处理订单id
	 * 
	 * @return
	 */
	private List<Long> getOrderIds() {
		List<Long> ids = new ArrayList<Long>();
		if (StringUtils.isNotBlank(this.objectIds)) {
			String[] sids = objectIds.split(";");
			if (null != sids && sids.length > 0) {
				for (String id : sids) {
					ids.add(Long.valueOf(id));
				}
			}
		}
		return ids;
	}

	/**
	 * 返回ajax结果
	 * 
	 * @param result
	 * @param message
	 */
	private void outResultMsg(String result, String message) {
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		obj.put("message", message);
		super.sendAjaxResult(obj.toString());
	}

	/**
	 * 判断一组订单是否为同一个产品子类型
	 * 
	 * @param orderIds
	 *            订单id集合
	 * @return
	 */
	private boolean isTheSameSubProductType(List<Long> orderIds) {
		boolean result = true;
		if (orderIds.size() == 1) {
			return result;
		}
		Map<String, Integer> subMap = new HashMap<String, Integer>();
		for (Long orderId : orderIds) {
			OrdOrder ordOrder = this.orderServiceProxy
					.queryOrdOrderByOrderId(orderId);
			if (null != ordOrder) {
				ProdProduct p = this.prodProductService
						.getProdProductById(ordOrder.getMainProduct()
								.getProductId());
				subMap.put(p.getSubProductType(), 0);
				if (subMap.keySet().size() > 1) {
					result = false;
					break;
				}
			}
		}
		return result;
	}

	private String getAffixName(Long objectId, String fileFileName) {
		String fileSuffix = "";
		if (fileFileName.endsWith(GroupAdviceNoteUtils.PDF_FILE_SUFFIX)) {
			fileSuffix = GroupAdviceNoteUtils.PDF_FILE_SUFFIX;
		} else if (fileFileName.endsWith(GroupAdviceNoteUtils.DOCX_FILE_SUFFIX)) {
			fileSuffix = GroupAdviceNoteUtils.DOCX_FILE_SUFFIX;
		} else if (fileFileName.endsWith(GroupAdviceNoteUtils.DOC_FILE_SUFFIX)) {
			fileSuffix = GroupAdviceNoteUtils.DOC_FILE_SUFFIX;
		} else {
			throw new RuntimeException("不支持的文件格式名:" + fileFileName);
		}
		String affixName = GroupAdviceNoteUtils.createAffixName(objectId,
				fileSuffix);
		return affixName;
	}

	/**
	 * 转到批量使用系统html模板
	 * 
	 * @return
	 */
	@Action("/groupadvice/toBatchUseSysTpl")
	public String toBatchUseSysTpl() {
		// 校验
		if (getOrderIds().isEmpty()) {
			getRequest().setAttribute("msg", "请检查参数是否齐全!");
			return "batchUseSysTpl";
		}
		// 判断一组订单是否为同一个产品子类型
		if (!isTheSameSubProductType(getOrderIds())) {
			getRequest().setAttribute("msg", "您选择的订单不属于同一个产品子类!");
			return "batchUseSysTpl";
		}
		// 取第一个订单,产品子类
		OrdOrder ordOrder = this.orderServiceProxy
				.queryOrdOrderByOrderId(getOrderIds().get(0));
		if (null == ordOrder) {
			getRequest().setAttribute("msg", "您选择的订单不存在!");
			return "batchUseSysTpl";
		}
		ProdProduct p = this.prodProductService.getProdProductById(ordOrder
				.getMainProduct().getProductId());
		HtmlTemplateGroupAdviceNote htmlTemplateGroupAdviceNote = new HtmlTemplateGroupAdviceNote();
		try {
			this.content = htmlTemplateGroupAdviceNote.getTplContent(p
					.getSubProductType());
		} catch (Exception e) {
			getRequest().setAttribute("msg", e.getMessage());
			return "batchUseSysTpl";
		}
		String[] array = content.split("<body>");
		if (array.length > 1) {
			contentFirstFix = array[0] + "<body>";
			String[] endArray = array[1].split("</body>");
			if (endArray.length > 1) {
				contentEndFix = "</body>" + endArray[1];
			}
		}
		return "batchUseSysTpl";
	}

	/**
	 * 处理用户在线编辑后的模板，批量生成出团通知书
	 */
	@Action("/groupadvice/doBatchUseSysTpl")
	public void doBatchUseSysTpl() {
		// 校验
		if (getOrderIds().isEmpty() || StringUtils.isBlank(content)) {
			outResultMsg("false", "请检查参数是否齐全!");
			return;
		}
		content = content.replaceAll("&nbsp(;)?", " ").replaceAll(
				"<br(\\s*)/>", "<br/>");
		String[] array = content.split("</style>");
		content = GroupAdviceNoteUtils.convert(this.content);
		content = contentFirstFix + array[1] + contentEndFix;
		int successCount = 0;
		String errorOrderIds = "";
		HtmlTemplateGroupAdviceNote groupAdviceNote = new HtmlTemplateGroupAdviceNote();
		for (Long orderId : this.getOrderIds()) {
			try {
				OrdOrder ordOrder = this.orderServiceProxy
						.queryOrdOrderByOrderId(orderId);
				groupAdviceNote.setOrdOrder(ordOrder);
				// List<ViewJourney> list =
				// this.viewPageJourneyService.getViewJourneysByProductId(ordOrder.getMainProduct().getProductId());
				// groupAdviceNote.setViewJourneyList(list);
				ProdProduct p = this.prodProductService
						.getProdProductById(ordOrder.getMainProduct()
								.getProductId());
				ProdRoute prodProduct = this.prodProductService
						.getProdRouteById(ordOrder.getMainProduct()
								.getProductId());
				prodProduct.setSubProductType(p.getSubProductType());
				groupAdviceNote.setProdProduct(prodProduct);
				// ViewPage v =
				// this.viewPageService.getViewPage(p.getProductId());
				// groupAdviceNote.setViewPage(v);
				initViewPageAndJourney(groupAdviceNote, orderId);
				// 渲染数据
				content = groupAdviceNote.initContentFromData(content);
				String pdfFileName = GroupAdviceNoteUtils.createFileName(
						orderId, GroupAdviceNoteUtils.PDF_FILE_SUFFIX);
				GroupAdviceNoteUtils.createPdfDocument(content, pdfFileName);
				File pdfFile = new File(pdfFileName);

				Long fileId = fsClient.uploadFile(pdfFile, "COM_AFFIX");
				if (fileId != null && pdfFile.exists()) {
					pdfFile.delete();
				}
				// 文件添加记录
				ComAffix affix = new ComAffix();
				affix.setObjectId(orderId);
				affix.setObjectType("ORD_ORDER");
				affix.setUserId(this.getOperatorName());
				affix.setName(GroupAdviceNoteUtils.createAffixName(orderId,
						GroupAdviceNoteUtils.PDF_FILE_SUFFIX));
				affix.setFileId(fileId);// 设定文件路径id
				affix.setCreateTime(new Date());
				affix.setFileType(ComAffix.GROUP_ADVICE_NOTE_FILE_TYPE);// 文件类型：出团通知书
				affix.setMemo("ONLINE_TEMPLATE");
				comAffixService
						.addAffixForGroupAdvice(affix, getOperatorName());// 插入文件记录

				// 更新出团通知状态为已上传待发送状态
				groupAdviceNoteService.updateOrderGroupWordStatus(orderId,
						Constant.GROUP_ADVICE_STATE.UPLOADED_NOT_SENT.name());

				successCount++;
			} catch (Exception ex) {
				logger.error("订单号:" + orderId + ",通过系统html模板生成出团通知书书失败:" + ex);
				errorOrderIds += orderId + ";";
			}
		}
		String error = "";
		if (StringUtils.isNotBlank(errorOrderIds)) {
			error = ",失败订单号:" + errorOrderIds;
		}
		outResultMsg("true", "成功生成" + successCount + "个出团通知书 " + error);
	}

	/**
	 * 转到批量使用上传docx模板
	 * 
	 * @return
	 */
	@Action("/groupadvice/toBatchUseUploadTpl")
	public String toBatchUseUploadTpl() {
		return "batchUseUploadTpl";
	}

	/**
	 * 处理批量使用上传docx模板
	 */
	@Action("/groupadvice/doBatchUseUploadTpl")
	public void doBatchUseUploadTpl() {
		// 校验
		if (getOrderIds().isEmpty()) {
			outResultMsg("false", "请检查参数是否齐全!");
			return;
		}
		// 校验文件
		if (file == null || file.length() == 0) {
			outResultMsg("false", "请上传模板文件。");
			return;
		}
		String ext = FilenameUtils.getExtension(fileFileName);
		if (StringUtils.isBlank(ext)) {
			outResultMsg("false", "未知的文件类型。");
			return;
		} else {
			ext = ext.toLowerCase();
		}
		if ("doc".equals(ext.toLowerCase())) {
			outResultMsg("false", "模板文件不支持Office 2003 Word文档。");
			return;
		}
		if (!"docx".equals(ext.toLowerCase())) {
			outResultMsg("false", "不支持的文件类型，请上传docx格式文档。");
			return;
		}

		int successCount = 0;
		String errorOrderIds = "";
		GroupAdviceNote groupAdviceNote = GroupAdviceNote
				.getInstance(GroupAdviceNote.DOCX_TEMPLATE);
		String templatePath = this.file.getAbsolutePath();
		groupAdviceNote.setTemplatePath(templatePath);
		for (Long orderId : this.getOrderIds()) {
			try {
				OrdOrder ordOrder = this.orderServiceProxy
						.queryOrdOrderByOrderId(orderId);
				groupAdviceNote.setOrdOrder(ordOrder);
				// List<ViewJourney> list =
				// this.viewPageJourneyService.getViewJourneysByProductId(ordOrder.getMainProduct().getProductId());
				// groupAdviceNote.setViewJourneyList(list);
				ProdProduct p = this.prodProductService
						.getProdProductById(ordOrder.getMainProduct()
								.getProductId());
				ProdRoute prodProduct = this.prodProductService
						.getProdRouteById(ordOrder.getMainProduct()
								.getProductId());
				prodProduct.setSubProductType(p.getSubProductType());
				groupAdviceNote.setProdProduct(prodProduct);
				// ViewPage v =
				// this.viewPageService.getViewPage(p.getProductId());
				// groupAdviceNote.setViewPage(v);
				initViewPageAndJourney(groupAdviceNote, orderId);
				groupAdviceNote.createFile();
				String docxTemplate = groupAdviceNote.getFileName();
				File docxInputFile = new File(docxTemplate);

				Long fileId = fsClient.uploadFile(docxInputFile, "COM_AFFIX");
				if (fileId != null) {
					if (docxInputFile.exists()) {
						docxInputFile.delete();
					}
				}
				ComAffix affix = new ComAffix();
				affix.setObjectId(orderId);
				affix.setObjectType("ORD_ORDER");
				affix.setUserId(this.getOperatorName());
				affix.setName(this.getAffixName(orderId, fileFileName));
				affix.setMemo(this.getBatchAffixMemo());
				affix.setFileId(fileId);
				affix.setCreateTime(new Date());
				affix.setFileType(ComAffix.GROUP_ADVICE_NOTE_FILE_TYPE);// 文件类型：出团通知书
				if (StringUtils.isBlank(affix.getMemo())) {// 用户没有输入文件描述
					affix.setMemo("ONLINE_TEMPLATE");
				}
				comAffixService.addAffixForGroupAdvice(affix,
						this.getOperatorName());

				// 更新出团通知状态为已上传待发送状态
				groupAdviceNoteService.updateOrderGroupWordStatus(orderId,
						Constant.GROUP_ADVICE_STATE.UPLOADED_NOT_SENT.name());
				successCount++;
			} catch (Exception ex) {
				logger.error("订单号:" + orderId + ",通过用户上传docx模板生成出团通知书书失败:" + ex);
				errorOrderIds += orderId + ";";
			}
		}
		String error = "";
		if (StringUtils.isNotBlank(errorOrderIds)) {
			error = ",失败订单号:" + errorOrderIds;
		}
		outResultMsg("true", "成功生成" + successCount + "个出团通知书 " + error);
	}

	/**
	 * 转到批量上传文件
	 * 
	 * @return
	 */
	@Action("/groupadvice/toBatchUploadFile")
	public String toBatchUploadFile() {
		return "batchUploadFile";
	}

	/**
	 * 处理批量为出团通知上传文件
	 */
	@Action("/groupadvice/doBatchUploadFile")
	public void doBatchUploadFile() {
		// 校验
		if (getOrderIds().isEmpty()) {
			outResultMsg("false", "请检查参数是否齐全!");
			return;
		}
		// 校验文件
		if (file == null || file.length() == 0) {
			outResultMsg("false", "请上传文件。");
			return;
		}
		String ext = FilenameUtils.getExtension(fileFileName);
		if (StringUtils.isBlank(ext)) {
			outResultMsg("false", "未知的文件类型。");
			return;
		} else {
			ext = ext.toLowerCase();
		}
		if (!"docx".equals(ext) && !"doc".equals(ext) && !"pdf".equals(ext)) {
			outResultMsg("false", "请上传doc,docx,pdf格式文档。");
			return;
		}

		int successCount = 0;
		String errorOrderIds = "";
		for (Long orderId : this.getOrderIds()) {
			try {
				// 上传文件调用
				Long fileId = fsClient.uploadFile(this.file, "COM_AFFIX");
				// 文件添加记录
				ComAffix affix = new ComAffix();
				affix.setObjectId(orderId);
				affix.setObjectType("ORD_ORDER");
				affix.setUserId(this.getOperatorName());
				affix.setMemo(this.getBatchAffixMemo());
				affix.setFileId(fileId);
				affix.setCreateTime(new Date());
				affix.setFileType(ComAffix.GROUP_ADVICE_NOTE_FILE_TYPE);// 文件类型：出团通知书
				affix.setName(this.getAffixName(orderId, fileFileName));
				comAffixService
						.addAffixForGroupAdvice(affix, getOperatorName());// 插入文件记录
				// 更新出团通知状态为已上传待发送状态
				groupAdviceNoteService.updateOrderGroupWordStatus(orderId,
						Constant.GROUP_ADVICE_STATE.UPLOADED_NOT_SENT.name());
				successCount++;
			} catch (Exception ex) {
				logger.error("订单号:" + orderId + ",上传出团通知附件失败:" + ex);
				errorOrderIds += orderId + ";";
			}
		}
		String error = "";
		if (StringUtils.isNotBlank(errorOrderIds)) {
			error = ",失败订单号:" + errorOrderIds;
		}
		outResultMsg("true", "成功为" + successCount + "个订单上传出团通知附件 " + error);
	}

	/**
	 * 判断是否可以发送出团通知
	 * 
	 * @param orderId
	 *            订单id
	 * @return
	 */
	private boolean isCanSend(Long orderId) {
		boolean send = false;
		OrdOrderRoute orderRoute = groupAdviceNoteService
				.getOrderRouteByOrderId(orderId);
		String status = orderRoute.getGroupWordStatus();
		if (Constant.GROUP_ADVICE_STATE.NEEDSEND.name().equals(status)
				|| Constant.GROUP_ADVICE_STATE.UPLOADED_NOT_SENT.name().equals(
						status)
				|| Constant.GROUP_ADVICE_STATE.MODIFY_NOTICE.name().equals(
						status)
				|| Constant.GROUP_ADVICE_STATE.SENT_NO_NOTICE.name().equals(
						status)
				|| Constant.GROUP_ADVICE_STATE.MODIFY_NO_NOTICE.name().equals(
						status)) {
			send = true;
		}
		return send;
	}

	/**
	 * 处理批量发送出团通知
	 */
	@Action("/groupadvice/doBatchSendGroupAdviceNote")
	public void doBatchSendGroupAdviceNote() {
		// 校验
		if (getOrderIds().isEmpty()) {
			outResultMsg("false", "请检查参数是否齐全!");
			return;
		}
		int successCount = 0;
		String errorOrderIds = "";
		for (Long orderId : this.getOrderIds()) {
			if (isCanSend(orderId)) {// sendGroupAdviceNote方法中有个return,希望可以采用异常方式处理
				this.sendGroupAdviceNote(orderId);
				successCount++;
			} else {
				errorOrderIds += orderId + ";";
			}
		}
		String error = "";
		if (StringUtils.isNotBlank(errorOrderIds)) {
			error = ",失败订单号:" + errorOrderIds;
		}
		outResultMsg("true", "成功发送出团通知" + successCount + "个 " + error);
	}

	/**
	 * 重发出团通知
	 */
	@Action("/groupadvice/doReSendGroupAdviceNote")
	public void doReSendGroupAdviceNote() {
		// 校验
		if (StringUtils.isEmpty(orderId)) {
			outResultMsg("false", "订单号为空!");
			return;
		}
		OrdOrderRoute orderRoute = groupAdviceNoteService
				.getOrderRouteByOrderId(Long.parseLong(orderId));
		String status = orderRoute.getGroupWordStatus();
		if (Constant.GROUP_ADVICE_STATE.MODIFY_NOTICE.name().equals(status)
				|| Constant.GROUP_ADVICE_STATE.SENT_NO_NOTICE.name().equals(
						status)
				|| Constant.GROUP_ADVICE_STATE.MODIFY_NO_NOTICE.name().equals(
						status)
				|| Constant.GROUP_ADVICE_STATE.SENT_NOTICE.name()
						.equals(status)) {
			this.sendGroupAdviceNote(Long.parseLong(orderId));
		} else {
			outResultMsg("false", "不符合重发条件!");
			return;
		}
		outResultMsg("true", "成功发送出团通知");
	}

	private void initViewPageAndJourney(final GroupAdviceNote groupAdviceNote,
			final Long orderId) {
		Map<String, Object> map = contractClient.loadRouteTravelObject(orderId);
		if (null == map) {
			return;
		}
		List<ViewJourney> list = (List<ViewJourney>) map.get("viewJourneys");
		ViewPage v = (ViewPage) map.get("viewPage");
		groupAdviceNote.setViewJourneyList(list);
		groupAdviceNote.setViewPage(v);
	}
	
	public String getOperatorName() {
		String operatorName = this.getRequest().getParameter("operatorName");
		if (StringUtils.isBlank(operatorName)) {
			operatorName = super.getOperatorName();
		}
		return operatorName;
	}

	// -----------------------------add by
	// taiqichao-------------------------------------end//

	public String getContent() {
		return content;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the groupWordStatus
	 */
	public String getGroupWordStatus() {
		return groupWordStatus;
	}

	/**
	 * @param groupWordStatus
	 *            the groupWordStatus to set
	 */
	public void setGroupWordStatus(String groupWordStatus) {
		this.groupWordStatus = groupWordStatus;
	}

	/**
	 * @return the jsonMsg
	 */
	public String getJsonMsg() {
		return jsonMsg;
	}

	/**
	 * @param jsonMsg
	 *            the jsonMsg to set
	 */
	public void setJsonMsg(String jsonMsg) {
		this.jsonMsg = jsonMsg;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public FSClient getFsClient() {
		return fsClient;
	}

	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}

	public ComAffixService getComAffixService() {
		return comAffixService;
	}

	public void setComAffixService(ComAffixService comAffixService) {
		this.comAffixService = comAffixService;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public ComAffix getAffix() {
		return affix;
	}

	public void setAffix(ComAffix affix) {
		this.affix = affix;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public TopicMessageProducer getOrderMessageProducer() {
		return orderMessageProducer;
	}

	public void setOrderMessageProducer(
			TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public ComMessageService getComMessageService() {
		return comMessageService;
	}

	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}

	public GroupAdviceNoteService getGroupAdviceNoteService() {
		return groupAdviceNoteService;
	}

	public void setGroupAdviceNoteService(
			GroupAdviceNoteService groupAdviceNoteService) {
		this.groupAdviceNoteService = groupAdviceNoteService;
	}

	public String getContentFirstFix() {
		return contentFirstFix;
	}

	public void setContentFirstFix(String contentFirstFix) {
		this.contentFirstFix = contentFirstFix;
	}

	public String getContentEndFix() {
		return contentEndFix;
	}

	public void setContentEndFix(String contentEndFix) {
		this.contentEndFix = contentEndFix;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public String getObjectIds() {
		return objectIds;
	}

	public void setObjectIds(String objectIds) {
		this.objectIds = objectIds;
	}

	public String getBatchAffixName() {
		return batchAffixName;
	}

	public void setBatchAffixName(String batchAffixName) {
		this.batchAffixName = batchAffixName;
	}

	public String getBatchAffixMemo() {
		return batchAffixMemo;
	}

	public void setBatchAffixMemo(String batchAffixMemo) {
		this.batchAffixMemo = batchAffixMemo;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public EContractClient getContractClient() {
		return contractClient;
	}

	public void setContractClient(EContractClient contractClient) {
		this.contractClient = contractClient;
	}

	public WorkOrderFinishedBiz getWorkOrderFinishedProxy() {
		return workOrderFinishedProxy;
	}

	public void setWorkOrderFinishedProxy(
			WorkOrderFinishedBiz workOrderFinishedProxy) {
		this.workOrderFinishedProxy = workOrderFinishedProxy;
	}

	public void setGroupAdviceNoteServiceProxy(
			IGroupAdviceNoteService groupAdviceNoteServiceProxy) {
		this.groupAdviceNoteServiceProxy = groupAdviceNoteServiceProxy;
	}

}
