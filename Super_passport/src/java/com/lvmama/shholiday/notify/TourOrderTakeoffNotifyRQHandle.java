/**
 * 
 */
package com.lvmama.shholiday.notify;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.dom4j.Element;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderSHHoliday;
import com.lvmama.comm.bee.service.ord.IGroupAdviceNoteService;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.pub.ComAffix;
import com.lvmama.comm.pet.service.pub.ComAffixService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.HttpsUtil.HttpResponseWrapper;
import com.lvmama.comm.utils.pic.UploadCtrl;

/**
 * 出团通知书通知处理
 * @author yangbin
 *
 */
public class TourOrderTakeoffNotifyRQHandle extends AbstractShholidayOrderNotify{

	
	private FSClient fsClient;
	private ComAffixService comAffixService;
	private IGroupAdviceNoteService groupAdviceNoteServiceProxy;
	private OrdOrder order;
	private TakeoffBook book;
	
	
	public TourOrderTakeoffNotifyRQHandle() {
		super("OrderNotifyRQ", "OTA_TourOrderTakeoffNotifyRS");
		fsClient = SpringBeanProxy.getBean(FSClient.class,"fsClient");
		comAffixService = SpringBeanProxy.getBean(ComAffixService.class,"comAffixService");
		groupAdviceNoteServiceProxy = SpringBeanProxy.getBean(IGroupAdviceNoteService.class,"groupAdviceNoteServiceProxy");
	}

	@Override
	protected void handleOther(Element body) {
		Element takeoffBookElement = body.element("TakeoffBook");
		book =getTakeoffBook(takeoffBookElement);
		if(book!=null && book.hasOK()){
			OrdOrderSHHoliday sh = getEntity();
			if(sh==null){
				setError("60051", "订单不存在",null);
				return;
			}
			
			order = orderServiceProxy.queryOrdOrderByOrderId(sh.getObjectId());
			if(order==null){
				setError("60051","订单不存在",null);
				return;
			}
			
			try {
				String affixName = this.getAffixName();
				Long fileId =  uploadPicture(book.getDownloadUrl(),affixName);	
				
				ComAffix affix = new ComAffix();
				affix.setObjectId(order.getOrderId());
				affix.setObjectType("ORD_ORDER");
				affix.setUserId("System");
				affix.setName(affixName);
				affix.setFileId(fileId);// 设定文件路径id
				affix.setCreateTime(new Date());
				affix.setMemo("出团通知书");
				affix.setFileType(ComAffix.GROUP_ADVICE_NOTE_FILE_TYPE);// 文件类型：出团通知书
				comAffixService.addAffixForGroupAdvice(affix, "System");// 插入文件记录
				groupAdviceNoteServiceProxy.sendGroupAdviceNote(order.getOrderId(), "System");
				
				addParam("orderPackageNo", sh.getContent());
			} catch (MalformedURLException e) {
				setError(NOTIFY_ERROR_CODE.Err80001);
				
			} catch (ClientProtocolException e) {
				setError(NOTIFY_ERROR_CODE.Err80001);
			} catch (IOException e) {
				setError(NOTIFY_ERROR_CODE.Err80001);
			}catch (Exception e){
				
			}
		}
	}
	
	private Long uploadPicture(String downUrl,String affixName) throws ClientProtocolException, IOException, FileNotFoundException, Exception {
		HttpResponseWrapper response = HttpsUtil.requestGetResponse(downUrl);
		InputStream in = response.getResponseStream();
		File file = File.createTempFile("shholiday", ".pdf");
		FileOutputStream fos =null;
		try{
			fos= new FileOutputStream(file);
			IOUtils.copy(in, fos);
			response.close();
			fos.close();
			Long fileId = fsClient.uploadFile(file, "COM_AFFIX");
			return fileId;
		}finally{
			IOUtils.closeQuietly(fos);
		}
	}
	
	private TakeoffBook getTakeoffBook(Element takeoffBookElement) {
		TakeoffBook offBook = new TakeoffBook();
		if(takeoffBookElement!=null){
			offBook.setStatus(takeoffBookElement.attributeValue("Status"));
			offBook.setDownloadUrl(takeoffBookElement.elementText("DownloadUrl"));
		}
		return offBook;
	}

	/**
	 * 提供给游客邮件附件中的出团通知书名字.
	 * 
	 * @return
	 */
	private String getAffixName() {
		StringBuilder sb = new StringBuilder();
		sb.append(order.getOrderId());
		sb.append("_GROUP_ADVICE_NOTE_");
		sb.append(DateUtil.getFormatDate(new java.util.Date(), "yyyyMMddHHmmss"));
		sb.append(".pdf");
		return sb.toString();
	}
	
	

	static class TakeoffBook implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = -929830543164365958L;
		
		
		private String status;
		private String downloadUrl;
		public String getStatus() {
			return status;
		}
		public String getDownloadUrl() {
			return downloadUrl;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public void setDownloadUrl(String downloadUrl) {
			this.downloadUrl = downloadUrl;
			if(StringUtils.isNotEmpty(downloadUrl)){
				this.downloadUrl = downloadUrl.replaceAll("&amp;", "&").replace("previewTeamMessage", "downloadTeamMessage");
			}
		}
	
		public boolean hasOK(){
			return "ok".equalsIgnoreCase(status);
		}
	}
	
}
