package com.lvmama.front.web.myspace;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import sun.misc.BASE64Encoder;

import com.lvmama.comm.bee.po.insurance.InsPolicyInfo;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.po.ord.OrdUserOrder;
import com.lvmama.comm.bee.service.insurance.PolicyInfoService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.pub.ComAffix;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.pub.ComAffixService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.ord.DaZhongPolicyUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

/**
 * 我的驴妈妈中的“我的订单”
 * 
 * @author ranlongfei 2012-8-27
 * @version
 */
@Results({
		@Result(name = "myspaceOrder", location = "/WEB-INF/pages/myspace/sub/myspaceOrder.ftl", type = "freemarker"),
		@Result(name = "myspaceOrderDetail", location = "/WEB-INF/pages/myspace/sub/order/myspaceOrderDetail.ftl", type = "freemarker") })
public class MyspaceOrderAction extends MySpaceBaseOrderAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 806578690957424990L;

	private static final int DA_ZONG_SUPPLIERID = 6054;

	private static final int PIN_AN_SUPPLIERID = 21;

	/**
	 * 日志控制台
	 */
	private static final Log LOG = LogFactory.getLog(MyspaceOrderAction.class);

	private PolicyInfoService policyInfoService;
	/**
	 * 短信发送服务器.
	 */
	private TopicMessageProducer orderMessageProducer;

	private OrdOrder order;

	private Page<OrdUserOrder> pageConfig;
	private List<InsPolicyInfo> policyList = new ArrayList<InsPolicyInfo>();

	private long currentPage = 1;
	private long pageSize = 10;

	private Long orderId;

	private String itemCode;
	private String reason;
	private Long objectId;
	private String objectType;
	private ComAffixService comAffixService;
	private CmtCommentService cmtCommentService;

	/**
	 * 文件系统服务.
	 */
	private FSClient fsClient;
	
	
	
	
	
	/**
	 * 订单列表查询
	 * 
	 * @author: ranlongfei 2012-8-27 下午1:46:56
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Action("/myspace/order")
	public String myspaceOrder() throws Exception {
		
		//--------------------------
		List<OrdUserOrder> beeOrderList = new ArrayList<OrdUserOrder>();
		List<OrdUserOrder> vstOrderList = new ArrayList<OrdUserOrder>();
		
		//查询用户订单表总数
		Long totalRecords = getUserOrderTotalCount();
		this.pageConfig = Page.page(totalRecords, pageSize, currentPage);
		//查询当前页面用户订单
		List<OrdUserOrder> userOrderList = queryUserOrderList(pageConfig);
		//将用户订单按bee和vst系统分离
		separateBeaAndVstOrder2List(userOrderList, beeOrderList, vstOrderList);
		//查找、填充bee系统订单
		fillBeeOrderByOrdUserOrder(beeOrderList);
		//查找、填充vst系统订单
		fillVstOrderByOrdUserOrder(vstOrderList);
		
		List<OrdOrder> orderList = getBeeOrdOrderListFromUserOrderList(beeOrderList);
		
		if (orderList != null && orderList != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			for (OrdOrder order : orderList) {
				LOG.info("最近订单：id"+order.getOrderId()+" status:"+order.getOrderStatus()+" flag:"+!order.getOrderStatus().equals(
						Constant.ORDER_STATUS.CANCEL.getCode())+"  点评flag:"+(order.getMainProduct().getProductType()
						.equals("TICKET") && "TOSUPPLIER".equals(order.getPaymentTarget())
						&& (!order.getOrderStatus().equals( Constant.ORDER_STATUS.CANCEL.getCode())))
						+"  游玩时间："+DateUtil.inAdvance(order.getVisitTime(),
								new Date()));
				parameters.put("orderId", order.getOrderId());
				List<InsPolicyInfo> list = policyInfoService.query(parameters);
				if (null != list && !list.isEmpty() && list.size() > 0) {
					for (InsPolicyInfo policy : list) {
						if ((DA_ZONG_SUPPLIERID == policy.getSupplierId() || PIN_AN_SUPPLIERID == policy.getSupplierId())
								&& Constant.POLICY_STATUS.REQUESTED.getCode()
										.equals(policy.getPolicyStatus())) {
							policyList.add(policy);
						}
					}
				}
				// 添加该订单是否可以点评需求
				parameters.put("isHide", "displayall");
				List<CommonCmtCommentVO> cmtCommentList = cmtCommentService
						.getCmtCommentList(parameters);
				if (cmtCommentList == null || cmtCommentList.size() == 0) {
					if (order.getMainProduct().getProductType().equals("ROUTE")
							|| order.getMainProduct().getProductType()
									.equals("HOTEL")
							|| order.getMainProduct().getProductType()
									.equals("TICKET")) {
						if (!order.getOrderStatus().equals(
								Constant.ORDER_STATUS.CANCEL.getCode())
								&& order.getPaymentStatus().equals(
										Constant.PAYMENT_STATUS.PAYED.getCode())) {
							if (order.getVisitTime() != null
									&& DateUtil.inAdvance(order.getVisitTime(),
											new Date())) {
								order.setIscanComment(true);
							}
						}
					}
					if (order.getMainProduct().getProductType()
							.equals("TICKET")
							&& "TOSUPPLIER".equals(order.getPaymentTarget())
							&& (!order.getOrderStatus().equals(
									Constant.ORDER_STATUS.CANCEL.getCode()))) {
						if (order.getVisitTime() != null
								&& DateUtil.inAdvance(order.getVisitTime(),
										new Date())) {
							order.setIscanComment(true);
						}
					}
				}
			}
			for (OrdOrder order : orderList) {
				reSetCanCreateInvoicByOrderPay(order);
			}
		}
		
		this.pageConfig.setItems(userOrderList);

		if (pageConfig.getItems().size() > 0) {
			this.pageConfig.setUrl("/myspace/order.do?currentPage=");
		}
		return "myspaceOrder";
	}

	private void reSetCanCreateInvoicByOrderPay(OrdOrder ordOrder) {
		if (StringUtils.equals("true", ordOrder.getCanCreatInvoice())) {
			long amount = orderServiceProxy
					.unableInvoiceAmountByOrderId(ordOrder.getOrderId());
			if (ordOrder.getActualPay() - amount < 1) {
				ordOrder.setCanCreatInvoice("false");
			}
		}
	}

	/**
	 * 订单详情页面
	 * 
	 * @author: ranlongfei 2012-8-27 上午11:28:26
	 * @return
	 */
	@Action("/myspace/order_detail")
	public String myspaceOrderDetail() {
		order = this.orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (order == null || getUserId() == null
				|| !getUserId().equalsIgnoreCase(order.getUserId())) {
			return ERROR;
		}
		return "myspaceOrderDetail";
	}

	/**
	 * 取消订单
	 * 
	 * @author: ranlongfei 2012-8-27 下午1:52:43
	 * @return
	 */
	@Action("/myspace/ordercancel")
	public void myspaceOrderCancel() {
		// 订单取消动作与前台判断逻辑保持一致
		if (this.orderId != null) {
			OrdUserOrder userOrder = ordUserOrderService.queryOrdUserOrderByPrimaryKey(orderId);
			//System.out.println("xxxxxxx:"+userOrder.getUserId()+"    "+getUser().getId()+"    "+(userOrder.getUserId()!=getUser().getId()));
			if(userOrder==null || !userOrder.getUserId().equals(getUser().getId())){
				sendAjaxMsg("{\"supplierChannel\":\"false\",\"status\":\"false\"}");
				return;
			}
			if (OrdUserOrder.BIZ_TYPE.BIZ_BEE.name().equals(userOrder.getBizType())) {
				Long beeOrderId = userOrder.getOrderId();
				OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(beeOrderId);
				if (ordOrder != null) {
					if (ordOrder.getOrderStatus().equals(
							Constant.ORDER_STATUS.NORMAL.name())
							&& ordOrder.getPaymentStatus().equals(
									Constant.PAYMENT_STATUS.UNPAY.name())) {
						//上航订单取消发送消息给对方 由对方触发取消
						if(ordOrder.getIsShHolidayOrder()){
							orderMessageProducer.sendMsg(MessageFactory.newSupplierOrderCancelMessage(beeOrderId));
							sendAjaxMsg("{\"supplierChannel\":\"true\",\"status\":\"true\"}");
							return;
						}else{
							if (null == reason) {
								this.orderServiceProxy.cancelOrder(beeOrderId, itemCode, super.getUserId());
							} else {
								OrdOrderMemo memo = new OrdOrderMemo();
								memo.setUuid(ordOrder.getUserId());
								memo.setOperatorName(ordOrder.getUserName());
								memo.setContent(decode(reason));
								memo.setOrderId(ordOrder.getOrderId());
								orderServiceProxy.saveMemo(memo, ordOrder.getUserId());
								this.orderServiceProxy.cancelOrder(beeOrderId, itemCode,
										super.getUserId());
							}
						}
					}
				}
			} else if (OrdUserOrder.BIZ_TYPE.BIZ_VST.name().equals(userOrder.getBizType())) {
				Long vstOrderId = userOrder.getOrderId();
				Map<String, Object> vstOrderMap = vstOrdOrderService.queryVstOrdorderByOrderId(vstOrderId);
				if (vstOrderMap != null) {
					Boolean canCancel = (Boolean) vstOrderMap.get("canCancel");
					if (canCancel != null && canCancel == Boolean.TRUE) {
						vstOrdOrderService.cancelOrder(vstOrderId, itemCode, decode(reason), super.getUserId(), "");
					}
					Boolean isSupplierOrder = (Boolean) vstOrderMap.get("isSupplierOrder");
					if (isSupplierOrder != null && isSupplierOrder == Boolean.TRUE) {
						sendAjaxMsg("{\"supplierChannel\":\"true\",\"status\":\"true\"}");
						return;
					}
				}
			}
		}
		sendAjaxMsg("{\"supplierChannel\":\"false\",\"status\":\"true\"}");
	}
	public static void main(String[] args) {
		Long x=10L;
		Long b=10L;
		System.out.println(x==b);
	}

	/**
	 * 发送短信通知
	 * 
	 * @author: ranlongfei 2012-8-27 下午1:47:39
	 */
	@Action("/myspace/resendMsg")
	public void myspaceResendMsg() {
		String orderHeadId = this.getRequest().getParameter("orderHeadId");
		String mobileNumber = this.getRequest().getParameter("mobileNumber");
		if (orderHeadId != null) {
			orderMessageProducer.sendMsg(MessageFactory.newCertSmsSendMessage(
					Long.valueOf(orderHeadId), mobileNumber));
		}
	}

	/**
	 * 下载出团通知书
	 * 
	 * @author: ranlongfei 2012-9-13 下午3:56:47
	 */
	@Action("/groupAdviceNoteDownload/order")
	public String groupAdviceNoteDownloadOrder() {
		// / 附件类
		ComAffix affix = new ComAffix();
		affix.setObjectId(objectId);
		affix.setObjectType(objectType);
		affix.setFileType("GROUP_ADVICE_NOTE");// 文件类型：出团通知书
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("objectId", affix.getObjectId());
		parameter.put("objectType", affix.getObjectType());
		parameter.put("fileType", affix.getFileType());

		long count = comAffixService.selectCountByParam(parameter);
		if (count > 0) {
			ComAffix com = comAffixService.selectLatestRecordByParam(parameter);
			downLoad(com.getFileId(), com.getName());
			return null;
		}
		return ERROR;
	}
	
	@Action(value = "/myspace/order/downloadpolicy1")
	public String execute1() {
		//InsPolicyInfo policy = policyInfoService.queryByPK(objectId);
		InsPolicyInfo policy = new InsPolicyInfo();
		policy.setPolicyNo("10203131900108877267");
		policy.setValidateCode("VoahKNmzlqEAwcVHTU");

			String type = "HTML";
			byte[] pdfStream = null;

			try {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String curTime = sdf.format(Calendar.getInstance().getTime());
				String umCode = "99290000";
				String policyNo = policy.getPolicyNo();
				String validateCode = policy.getValidateCode();
				
				HttpClient httpClient = HttpsUtil.createHttpClient();
				List<BasicNameValuePair> paramList = new ArrayList<BasicNameValuePair>();

				paramList.add(new BasicNameValuePair("umCode", umCode));
				paramList.add(new BasicNameValuePair("policyNo", policyNo));
				paramList.add(new BasicNameValuePair("validateCode", validateCode));
				paramList.add(new BasicNameValuePair("curTime", curTime));
				paramList.add(new BasicNameValuePair("isSeperated", ""));
				paramList.add(new BasicNameValuePair("cipherText", signData(
						umCode + policyNo + validateCode + "" + curTime,
						Constant.getInstance().getProperty("PINAN_POLICY_CERT"),
						"paicw1234", "1")));
				paramList.add(new BasicNameValuePair("TransID", null));
				paramList.add(new BasicNameValuePair("requestId", null));

				HttpPost httpPost = new HttpPost(
						"https://epcis-ptp.pingan.com.cn/epcis.ptp.partner.getAhsEPolicyPDFWithCert.do");
				StringEntity entity;
				entity = new UrlEncodedFormEntity(paramList, "gbk");
				httpPost.setEntity(entity);

				HttpResponse httpResponse = httpClient.execute(httpPost);// 发送请求到平安
				HttpEntity httpEntity = httpResponse.getEntity();// 获取返回内容
				Header[] headers = httpResponse.getHeaders("Content-type");// 获取返回类型

				for (int i = 0; i < headers.length; i++) {
					String typeValue = headers[i].getValue();
					if (typeValue != null
							&& typeValue.toUpperCase().indexOf("PDF") >= 0)// 如果是pdf类型，下载后的文件是pdf类型
					{
						type = "PDF";
						break;
					}

				}

				if (httpEntity != null) {
					pdfStream = EntityUtils.toByteArray(httpEntity);// 获取返回内容
					if (type.equals("PDF")) {
						getResponse().setHeader(
								"Content-Disposition",
								"attachment; filename="
										+ java.net.URLEncoder.encode(
												policy.getPolicyNo() + ".pdf",
												"UTF-8"));
					} else {
						getResponse().setHeader(
								"Content-Disposition",
								"attachment; filename="
										+ java.net.URLEncoder.encode(
												policy.getPolicyNo() + ".html",
												"UTF-8"));
					}

					OutputStream os = this.getResponse().getOutputStream();// 输出内容到文件
					os.write(pdfStream);
					os.close();
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		

		return ERROR;
	}	

	@Action(value = "/myspace/order/downloadpolicy")
	public String execute() {
		if (!isLogin()) {
			return LOGIN;
		}
		if (null == orderId || null == objectId) {
			return ERROR;
		}
		InsPolicyInfo policy = policyInfoService.queryByPK(objectId);

		// 大众保险
		if (null != policy
				&& DA_ZONG_SUPPLIERID == policy.getSupplierId()
				&& Constant.POLICY_STATUS.REQUESTED.getCode().equals(
						policy.getPolicyStatus())
				&& getUser().getUserNo().equals(policy.getOrderUserId())) {
			HttpClient httpClient = HttpsUtil.createHttpClient();
			ArrayList<String> cookies = DaZhongPolicyUtils
					.createSession(httpClient);
			byte[] policydata = DaZhongPolicyUtils.downloadPolicy(httpClient,
					cookies, policy.getPolicyNo());
			DaZhongPolicyUtils.deleteSession(httpClient, cookies);
			OutputStream os = null;
			try {
				getResponse()
						.setHeader(
								"Content-Disposition",
								"attachment; filename="
										+ java.net.URLEncoder.encode(
												policy.getPolicyNo() + ".pdf",
												"UTF-8"));
				os = getResponse().getOutputStream();
				if (policydata != null) {
					os.write(policydata);
				}
				os.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				IOUtils.closeQuietly(os);
			}
			return null;
		}

		if (null != policy
				&& PIN_AN_SUPPLIERID == policy.getSupplierId()
				&& Constant.POLICY_STATUS.REQUESTED.getCode().equals(
						policy.getPolicyStatus())
				&& StringUtils.isNotEmpty(policy.getValidateCode())
				&& StringUtils.isNotEmpty(policy.getPolicyNo())
				&& getUser().getUserNo().equals(policy.getOrderUserId())) {
			String type = "HTML";
			byte[] pdfStream = null;

			try {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String curTime = sdf.format(Calendar.getInstance().getTime());
				String umCode = "99290000";
				String policyNo = policy.getPolicyNo();
				String validateCode = policy.getValidateCode();
				
				HttpClient httpClient = HttpsUtil.createHttpClient();
				List<BasicNameValuePair> paramList = new ArrayList<BasicNameValuePair>();

				paramList.add(new BasicNameValuePair("umCode", umCode));
				paramList.add(new BasicNameValuePair("policyNo", policyNo));
				paramList.add(new BasicNameValuePair("validateCode", validateCode));
				paramList.add(new BasicNameValuePair("curTime", curTime));
				paramList.add(new BasicNameValuePair("isSeperated", ""));
				paramList.add(new BasicNameValuePair("cipherText", signData(
						umCode + policyNo + validateCode + "" + curTime,
						Constant.getInstance().getProperty("PINAN_POLICY_CERT"),
						"paicw1234", "1")));
				paramList.add(new BasicNameValuePair("TransID", null));
				paramList.add(new BasicNameValuePair("requestId", null));

				HttpPost httpPost = new HttpPost(
						"https://epcis-ptp.pingan.com.cn/epcis.ptp.partner.getAhsEPolicyPDFWithCert.do");
				StringEntity entity;
				entity = new UrlEncodedFormEntity(paramList, "gbk");
				httpPost.setEntity(entity);

				HttpResponse httpResponse = httpClient.execute(httpPost);// 发送请求到平安
				HttpEntity httpEntity = httpResponse.getEntity();// 获取返回内容
				Header[] headers = httpResponse.getHeaders("Content-type");// 获取返回类型

				for (int i = 0; i < headers.length; i++) {
					String typeValue = headers[i].getValue();
					if (typeValue != null
							&& typeValue.toUpperCase().indexOf("PDF") >= 0)// 如果是pdf类型，下载后的文件是pdf类型
					{
						type = "PDF";
						break;
					}

				}

				if (httpEntity != null) {
					pdfStream = EntityUtils.toByteArray(httpEntity);// 获取返回内容
					if (type.equals("PDF")) {
						getResponse().setHeader(
								"Content-Disposition",
								"attachment; filename="
										+ java.net.URLEncoder.encode(
												policy.getPolicyNo() + ".pdf",
												"UTF-8"));
					} else {
						getResponse().setHeader(
								"Content-Disposition",
								"attachment; filename="
										+ java.net.URLEncoder.encode(
												policy.getPolicyNo() + ".html",
												"UTF-8"));
					}

					OutputStream os = this.getResponse().getOutputStream();// 输出内容到文件
					os.write(pdfStream);
					os.close();
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return ERROR;
	}

	/**
	 * 文件下载
	 * 
	 * @param fileId
	 *            文件对应id
	 * @param fileName
	 *            文件名称
	 * @author nixianjun
	 * @CreateDate 2012-7-16
	 */
	private void downLoad(Long fileIdStr, String fileNameStr) {

		OutputStream os = null;
		try {
			getResponse().setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ java.net.URLEncoder.encode(fileNameStr, "UTF-8"));
			os = getResponse().getOutputStream();
			ComFile resultFile = fsClient.downloadFile(fileIdStr);
			byte[] data = resultFile.getFileData();
			if (resultFile != null && data != null) {
				os.write(data);
			}
			os.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(os);
		}

	}

	/**
	 * 将页面get过来的参数的编码转为utf-8
	 * 
	 * @author: ranlongfei 2012-8-27 下午1:50:27
	 * @param reason
	 * @return
	 */
	private String decode(String reason) {
		try {
			if (StringUtils.isNotEmpty(reason)) {
				return new String(reason.getBytes("iso-8859-1"), "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 签名算法
	 * 
	 * @author HXS
	 * @date 2012-7-2
	 * @todo TODO
	 * @param data
	 *            需要签名的内容
	 * @param keyStoreFileName
	 *            含私钥的文件
	 * @param keyStorePassword
	 *            含私钥文件的密码
	 * @param keyStoreAlias
	 *            别名
	 * @return
	 */
	private static String signData(String data, String keyStoreFileName,
			String keyStorePassword, String keyStoreAlias) {
		KeyStore keyStore;
		byte[] signRstByte = null;
		String signValue = "";
		String keystoreType = "";
		try {
			if (keyStoreFileName.toUpperCase().indexOf("PFX") >= 0)// 判断证书文件的格式
			{
				keystoreType = "PKCS12";
			} else {
				keystoreType = "JKS";
			}
			keyStore = KeyStore.getInstance(keystoreType);// 获取JKS证书实例
			FileInputStream in = new FileInputStream(keyStoreFileName);// 获取证书文件流
			char[] pwdChar = keyStorePassword.toCharArray();// 证书密码
			keyStore.load(in, pwdChar);// 加载证书到keystore中
			PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyStoreAlias,
					pwdChar);// 从证书中获取私钥
			Signature sign = Signature.getInstance("SHA1WithRSA");// SHA1WithRSA签名算法
			sign.initSign(privateKey);// 设置私钥
			sign.update(data.getBytes());// 设置明文
			signRstByte = sign.sign();// 加密
			BASE64Encoder encoder = new BASE64Encoder();
			signValue = encoder.encodeBuffer(signRstByte);// BASE64编码
			// System.out.println("签名并编码后的内容signValue=="+signValue);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return signValue;

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

	public OrdOrder getOrder() {
		return order;
	}

	public void setOrder(OrdOrder order) {
		this.order = order;
	}

	public Page<OrdUserOrder> getPageConfig() {
		return pageConfig;
	}

	public void setPageConfig(Page<OrdUserOrder> pageConfig) {
		this.pageConfig = pageConfig;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public void setComAffixService(ComAffixService comAffixService) {
		this.comAffixService = comAffixService;
	}

	public FSClient getFsClient() {
		return fsClient;
	}

	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}

	public void setPolicyInfoService(PolicyInfoService policyInfoService) {
		this.policyInfoService = policyInfoService;
	}

	public List<InsPolicyInfo> getPolicyList() {
		return policyList;
	}

	public void setPolicyList(List<InsPolicyInfo> policyList) {
		this.policyList = policyList;
	}

	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}
	
	


	/**
	 * 根据用户ID获取订单总数
	 * 
	 * @return
	 */
	private Long getUserOrderTotalCount() {
		Long count = 0L;
		if (getUserId() != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", getUser().getId());
			count = ordUserOrderService.getTotalCount(params);
		}
		
		return count;
	}
	
	
}
