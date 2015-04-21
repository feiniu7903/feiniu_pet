package test;


public class TianmuhuTest {/*
	*//**
	 * 下订单
	 * @return
	 *//*
	// @Test
	public String AppendStr() {
		PWBRequest request = new PWBRequest();
		header h = new header("SendCode", "2012-06-14");
		identityInfo idinfo = new identityInfo("TESTFX", "admin");
		request.setHeader(h);
		request.setIdentityInfo(idinfo);

		order od = new order("330182198804273139", "庄工", "15121008339",
				"021111113", "800.00", "", payMethod.spot.name(), null, null,
				null, null);

		scenicOrder scenicOrder = new scenicOrder("021111113002", "30.00", "2",
				"60.00", "2012-07-21", "20120330001205", "天目湖门票");
		scenicOrder scenicOrder1 = new scenicOrder("021111113003", "30.00", "2",
				"60.00", "2012-07-21", "20120330001205", "天目湖门票1");
		List<scenicOrder> scs = new ArrayList<scenicOrder>();
		scs.add(scenicOrder);
		scs.add(scenicOrder1);
		od.setScenicOrders(scs);
		od.setHotelOrders(null);
		od.setRepastOrders(null);
		od.setFamilyOrders(null);
		
		 * List<hotelOrder> hotelOrders = new ArrayList<hotelOrder>();
		 * hotelOrder hotelOrder = new hotelOrder("021111113002", "170.00", "2",
		 * "340.00", "2012-07-22", "20120330001205", "d砂大酒店");
		 * hotelOrders.add(hotelOrder); List<repastOrder> repastOrders = new
		 * ArrayList<repastOrder>(); repastOrder repastOrder = new
		 * repastOrder("021111113003", "100.00", "2", "100.00", "2012-07-21",
		 * "20120330001205", "d砂大酒店"); repastOrders.add(repastOrder);
		 * List<familyOrder> familyOrders = new ArrayList<familyOrder>();
		 * familyOrder familyOrder = new familyOrder("021111113004", "100.00",
		 * "2", "100.00", "2012-07-21", "20120330001205", "测试票型1");
		 * familyOrders.add(familyOrder);
		 

		order od=new order();
		od.setOrderCode("021111113001");
		orderRequest oq = new orderRequest(od);
		request.setOrderRequest(oq);
		return request.toSendCodeRequestXml();
	}

	*//**
	 * 取消订单
	 * @param xmlResponse
	 *//*
	public String CancelStr() {
		PWBRequest request = new PWBRequest();
		header h = new header("SendCode", "2012-06-14");
		identityInfo idinfo = new identityInfo("TESTFX", "admin");
		request.setHeader(h);
		request.setIdentityInfo(idinfo);
		order od=new order();
		od.setOrderCode("021111113");
		orderRequest oq = new orderRequest(od);
		request.setOrderRequest(oq);
		return request.toSendCodeCancelRequestXml();
	}
	
	*//**
	 * 发图查询
	 * @param xmlResponse
	 *//*
	public String ImgReq() {
		PWBRequest request = new PWBRequest();
		header h = new header("SendCode", "2012-06-14");
		identityInfo idinfo = new identityInfo("TESTFX", "admin");
		request.setHeader(h);
		request.setIdentityInfo(idinfo);
		order od=new order();
		od.setOrderCode("021111113");
		orderRequest oq = new orderRequest(od);
		request.setOrderRequest(oq);
		return request.toSendCodeImgRequestXml();
	}
	
	*//**
	 * 发短信
	 * @param xmlResponse
	 *//*
	public String sendsm() {
		PWBRequest request = new PWBRequest();
		header h = new header("SendCode", "2012-06-14");
		identityInfo idinfo = new identityInfo("TESTFX", "admin");
		request.setHeader(h);
		request.setIdentityInfo(idinfo);
		order od=new order();
		od.setOrderCode("021111113");
		orderRequest oq = new orderRequest(od);
		request.setOrderRequest(oq);
		return request.toSendSMRequestXml();
	}
	*//**
	 * 发彩信
	 * @param xmlResponse
	 *//*
	public String sendmms() {
		PWBRequest request = new PWBRequest();
		header h = new header("SendCode", "2012-06-14");
		identityInfo idinfo = new identityInfo("TESTFX", "admin");
		request.setHeader(h);
		request.setIdentityInfo(idinfo);
		order od=new order();
		od.setOrderCode("021111113");
		orderRequest oq = new orderRequest(od);
		request.setOrderRequest(oq);
		return request.toSendMMSRequestXml();
	}
	
	*//**
	 * 下订单
	 * @return
	 *//*
	@Test
	public void sendCodeReq(){
		String xmlMsg = "";
		*//**下订单*//*
		xmlMsg=AppendStr();
		System.out.println("----------------申请码-------------------------");
		System.out.println(xmlMsg);
		String xmlResponse=TianmuhuClient.request(xmlMsg);
		PWBResponse pwbResponse = ResponseUtil.getPWBResponse(xmlResponse);
		System.out.println("----------------响应报文-------------------------");
		System.out.println(pwbResponse.getTransactionName());
		System.out.println(pwbResponse.getCode());
		System.out.println(pwbResponse.getDescription());
	}
	*//**
	 * 取消订单
	 * @param xmlResponse
	 *//*
	@Test
	public void sendCodeCancelReq() throws NoSuchAlgorithmException {
		String xmlMsg = "";
		*//**取消订单*//*
		xmlMsg=CancelStr();
		System.out.println("----------------申请码-------------------------");
		System.out.println(xmlMsg);
		String singxml = "xmlMsg=" + xmlMsg + "TESTFX";
		String sign = Md5.encode(singxml);
		Map<String, String> xmlreq = new HashMap<String, String>();
		xmlreq.put("xmlMsg", xmlMsg);
		xmlreq.put("sign", sign);
		HttpClient httpclient = new HttpClient();// 创建 HttpClient 的实例
		httpclient.setData(xmlreq);
		httpclient.setUrl("http://boss.piaowubao.com/boss/service/code.htm");
		String xmlResponse = TianmuhuClient.request(xmlMsg);
		PWBResponse pwbResponse = ResponseUtil.getCodeCancelRes(xmlResponse);
		System.out.println("----------------响应报文-------------------------");
		System.out.println(pwbResponse.getTransactionName());
		System.out.println(pwbResponse.getCode());
		System.out.println(pwbResponse.getDescription());
	}
	*//**
	 * 发码图片
	 * @return
	 *//*
	@Test
	public void sendCodeImgReq(){
		String xmlMsg = "";
		
		xmlMsg=ImgReq();
		System.out.println("----------------申请码-------------------------");
		System.out.println(xmlMsg);
		String singxml = "xmlMsg=" + xmlMsg + "TESTFX";
		String sign = Md5.encode(singxml);
		Map<String, String> xmlreq = new HashMap<String, String>();
		xmlreq.put("xmlMsg", xmlMsg);
		xmlreq.put("sign", sign);
		HttpClient httpclient = new HttpClient();// 创建 HttpClient 的实例
		httpclient.setData(xmlreq);
		httpclient.setUrl("http://boss.piaowubao.com/boss/service/code.htm");
		String xmlResponse = TianmuhuClient.request(xmlMsg);
		PWBResponse pwbResponse = ResponseUtil.getCodeImgRes(xmlResponse);
		System.out.println("----------------响应报文------------------");
		System.out.println(pwbResponse.getTransactionName());
		System.out.println(pwbResponse.getCode());
		System.out.println(pwbResponse.getDescription());
		System.out.println(pwbResponse.getImg());
	}
	
	*//**
	 * 发彩信
	 * @return
	 *//*
	@Test
	public void sendMMSReq(){
		String xmlMsg = "";
		
		xmlMsg=sendmms();
		System.out.println("----------------申请码-----------------");
		System.out.println(xmlMsg);
		String xmlResponse = TianmuhuClient.request(xmlMsg);
		System.out.println(xmlResponse);
		PWBResponse pwbResponse = ResponseUtil.getCodeCancelRes(xmlResponse);
		System.out.println("----------------响应报文------------------");
		System.out.println(pwbResponse.getTransactionName());
		System.out.println(pwbResponse.getCode());
		System.out.println(pwbResponse.getDescription());
	}
	*//**
	 * 发彩信
	 * @return
	 *//*
	@Test
	public void sendSMReq(){
		String xmlMsg = "";
		
		xmlMsg=sendsm();
		System.out.println("----------------申请码-----------------");
		System.out.println(xmlMsg);
		String xmlResponse = TianmuhuClient.request(xmlMsg);
		System.out.println(xmlResponse);
		PWBResponse pwbResponse = ResponseUtil.getCodeCancelRes(xmlResponse);
		System.out.println("----------------响应报文------------------");
		System.out.println(pwbResponse.getTransactionName());
		System.out.println(pwbResponse.getCode());
		System.out.println(pwbResponse.getDescription());
		System.out.println(pwbResponse.getImg());
	}
	
	@Test
	public void testClient(){
		String serialNo="";
		TianMuHuClientImpl clientImpl=new TianMuHuClientImpl();
		PassCode passCode=new PassCode();
		passCode.setSerialNo(serialNo);
		Passport passport= clientImpl.apply(passCode);
		System.out.println(passport.getStatus());
		PassPortDetailSQLBuilderImpl p=new PassPortDetailSQLBuilderImpl();
		serialNo=p.getSelect();
		System.out.println(serialNo);
	}
	
*/}
