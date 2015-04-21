package com.lvmama.order.sms;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.passport.dao.PassCodeDAO;

public class DiemPaySuccSMMSCreator implements MultiSmsCreator {
	private OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
	private PassCodeDAO passCodeDAO = (PassCodeDAO)SpringBeanProxy.getBean("passCodeDAO");
	private OrdOrder order;
	private PassCode passCode;
	private String code;
	private String visitDate;
	private String mobile;
	private String provider;
	
	public DiemPaySuccSMMSCreator(Long codeId, String mobile) {
		this.mobile = mobile;
		passCode = passCodeDAO.getPassCodeByCodeId(codeId);
		order = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
		visitDate = DateUtil.getFormatDate(order.getVisitTime(),"yyyy-MM-dd");
		
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("codeId", passCode.getCodeId());
		params.put("_startRow",0);
		params.put("_endRow",1000);
		
		List<PassCode> passCodes = passCodeDAO.selectByParams(params);
		if(passCodes!=null && passCodes.size()==1){
			provider = passCodes.get(0).getProviderName(); 
		}
		
		if("BASE64".equalsIgnoreCase(passCode.getCode())){
			code=passCode.getAddCode();
		}else{
			code=passCode.getCode();
		}
	}

	class X extends DiemPaySuccSmsCreator implements SingleSmsCreator{

		public X(Long codeId, String mobile) {
			super(codeId, mobile);
		}

		@Override
		Map<String, Object> getContentData() {
			Map<String, Object> contentData = super.getContentData();
			contentData.put("code", null);
			contentData.put("codeImage", null);
			return contentData;
		}
	}
	
	@Override
	public List<ComSms> createSmsList() {
		List<ComSms> smsList = new ArrayList<ComSms>();
		ComSms sms = new ComSms();
		sms.setMobile(mobile);
		sms.setObjectId(passCode.getOrderId());
		String content="订单号："+passCode.getOrderId()+",游玩日期："+visitDate+",辅助码："+code;
		sms.setContent(content);
		
		//2013-09-06变更，LVMAMA QR申码由  二维码短信变为彩信
		//变更后LVMAMA QC申码时，因为是合并申码，并且非合并凭证发送，所以在   此   之前已经发送了
		//一条彩信，因此，此处排除LVMAMA QR再次发送彩信，而转为普通短信。
		if(provider.equals("LVMAMA QR")){
			sms.setMms("false");
			ComSms comSms = new X(passCode.getCodeId(),this.mobile).createSingleSms();
			if(comSms!=null){
				content = new X(passCode.getCodeId(),this.mobile).createSingleSms().getContent();
				sms.setContent(content);
			}
		}else{
			if(passCode.getCodeImage()!=null){
				sms.setMms("true");
				sms.setCodeImage(passCode.getCodeImage());
			}else{
				sms.setMms("false");
			}
		}
		smsList.add(sms);
		return smsList;
	}
}
