package com.lvmama.train.service.ws;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.train.BaseVo;
import com.lvmama.comm.vo.train.ReqVo;
import com.lvmama.comm.vo.train.Rsp;
import com.lvmama.train.service.NotifyMessageService;
import com.lvmama.train.service.NotifyRequest;

/**
 * @author yangbin
 *
 */
public class NotifyMessageServiceImpl implements NotifyMessageService {
	
	private static final Log LOG=LogFactory.getLog(NotifyMessageServiceImpl.class);
	
	@Override
	public Rsp request(ReqVo vo, int iCode) {
		try{
			NotifyRequest request = getRequestInstance(iCode);
			if(request != null){
				Rsp rsp = request.handle(vo);
				if(LOG.isDebugEnabled()){
					LOG.debug("response:"+rsp);
				}
				return rsp;
			}else
				return new Rsp(Constant.HTTP_SERVER_ERROR, 
							Constant.HTTP_SERVER_ERROR_MSG,
							new BaseVo(Constant.REPLY_CODE.OTHERS.getRetCode(), 
									  Constant.REPLY_CODE.OTHERS.getRetMsg()));
		}catch(RuntimeException ex){
			ex.printStackTrace();
			return new Rsp(Constant.HTTP_SERVER_ERROR, 
						Constant.HTTP_SERVER_ERROR_MSG,
						new BaseVo(Constant.REPLY_CODE.OTHERS.getRetCode(), 
								  Constant.REPLY_CODE.OTHERS.getRetMsg()));
		}
	}
	
	
	/**
	 * 根据接口编码获得接口处理类实例
	 * @param iCode
	 * @return
	 */
	private NotifyRequest getRequestInstance(int iCode) {
		Class clazz;
		if(iCode == TRAIN_INTERFACE.TICKET_ISSUED_NOTIFY.getICode())
			clazz = TRAIN_INTERFACE.TICKET_ISSUED_NOTIFY.getIClass();
		else if(iCode == TRAIN_INTERFACE.TICKET_DRAWBACK_NOTIFY.getICode())
			clazz = TRAIN_INTERFACE.TICKET_DRAWBACK_NOTIFY.getIClass();
		else if(iCode == TRAIN_INTERFACE.TICKET_REFUND_NOTIFY.getICode())
			clazz = TRAIN_INTERFACE.TICKET_REFUND_NOTIFY.getIClass();
		else if(iCode == TRAIN_INTERFACE.INTERFACE_UPDATE_NOTIFY.getICode())
			clazz = TRAIN_INTERFACE.INTERFACE_UPDATE_NOTIFY.getIClass();
		else
			clazz = null;
		
		if(clazz == null) throw new RuntimeException("未能找到任何能够处理该通知的处理器!");
		
		try {
			Object instance = clazz.newInstance();
			if(!(instance instanceof NotifyRequest))
				throw new RuntimeException("未能找到正确的通知处理器!");
			return (NotifyRequest)instance;
		} catch (InstantiationException ex) {
			// TODO: handle exception
			throw new RuntimeException(ex.getMessage());
		}catch (IllegalAccessException ex) {
			// TODO: handle exception
			throw new RuntimeException(ex.getMessage());
		}
	}

//	private NotifyRequest getRequest(String serviceName){
//		if(requestMap==null){
//			requestMap = new HashMap<String, Class<? extends NotifyRequest>>();
//			requestMap.put("Train.Notify.Service.LockOrder", LockOrderNotifyRequest.class);
//			requestMap.put("Train.Notify.Service.TicketIssueResult",TicketIssueResultRequest.class);
//			requestMap.put("Train.Notify.Service.ProductUpdate", ProductUpdateRequest.class);
//			requestMap.put("Train.Notify.Service.Refund", OrderRefundRequest.class);
//		}
//		Class<? extends NotifyRequest> clazz = requestMap.get(serviceName);
//		if(clazz==null){
//			return null;
//		}
//		try {
//			return clazz.newInstance();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	
//
//	private NotifyRequest parseRequestHeader(final Document doc){
//		Element header = doc.getRootElement().element("Header");
//		String serviceName = header.elementText("ServiceName");
//		String timeStamp = header.elementText("TimeStamp");
//		Date date = DateUtil.stringToDate(timeStamp.substring(0,timeStamp.lastIndexOf('+')), TIMESTAMP_FORMAT);
//		if(Math.abs(date.getTime()-new Date().getTime())>1000*60*30){
//			//时区相差半小时以上不处理
//		}
//		return getRequest(serviceName);
//	}
//	
//	private final String TIMESTAMP_FORMAT="yyyy-MM-dd'T'HH:mm:ss";
}
