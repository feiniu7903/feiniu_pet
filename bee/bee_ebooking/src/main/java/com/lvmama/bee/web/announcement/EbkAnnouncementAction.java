package com.lvmama.bee.web.announcement;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkAnnouncement;
import com.lvmama.comm.bee.service.ebooking.EbkAnnouncementService;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;


/**
 * EBooking公告(商户版).
 */
@Results(value={
		@Result(name="ebkAnnouncementList",location="/WEB-INF/pages/announcement/ebkAnnouncementList.jsp"),
		@Result(name="show_announcement_detail",location="/WEB-INF/pages/announcement/ebkAnnouncementDetail.jsp")
})
@Namespace("/announcement")
public class EbkAnnouncementAction extends EbkBaseAction {

	private static final long serialVersionUID = 702968048644045717L;
	private static final Logger logger = Logger.getLogger(EbkAnnouncementAction.class);
	private EbkAnnouncementService ebkAnnouncementService;
	
	private Page<EbkAnnouncement> ebkAnnouncementPage = new Page<EbkAnnouncement>();
	//
	private FSClient fsClient;
	//附件文件由FSClient返回的ID标识.
	private Long attachment;
	//附件下载的文件名.
	private String downloadFileName;
	//附件下载的文件流.
	private InputStream downloadStream;
	//附件下载的文件类型.
	private String contentType;

	/**
	 * 分页查询所有记录.
	 * @return
	 */
	@Action("/ebkAnnouncementList")
	public String announcementList() {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("beginDate", new Date());
		ebkAnnouncementPage.setTotalResultSize(this.ebkAnnouncementService.countEbkAnnouncementListByMap(params));
		ebkAnnouncementPage.buildUrl(getRequest());
		ebkAnnouncementPage.setCurrentPage(this.page);
		params.put("start", ebkAnnouncementPage.getStartRows());
		params.put("end", ebkAnnouncementPage.getEndRows());
		params.put("orderByBeginDateDesc", "true");
		List<EbkAnnouncement> items = this.ebkAnnouncementService.findEbkAnnouncementListByMap(params);
		this.initAttachmentName(items);
		ebkAnnouncementPage.setItems(items);
		return "ebkAnnouncementList";
	}
	
	//初始化文件名:文件名+文件大小(KB).
	private void initAttachmentName(List<EbkAnnouncement> items) {
		if (items == null || items.isEmpty()) {
			return;
		}
		
		for (EbkAnnouncement e : items) {
			if (e.isHaveAttachment()) {
				ComFile comFile = this.fsClient.downloadFile(e.getAttachment());
				StringBuilder sb = new StringBuilder("");
				if (comFile != null) {
					sb.append(comFile.getFileName());
					double size = comFile.getFileData().length;
					sb.append("(").append(String.format("%.2f", size / 1024)).append("KB)");
				}
				e.setAttachmentName(sb.toString());
			}
		}
	}
	
	@Action(value="/downloadAnnouncementAttachment",results={@Result(type="stream",name="success",params={"contentType","${contentType}","inputName","downloadStream","contentDisposition","attachment;filename=${downloadFileName}","bufferSize","1024"})})
	public String downloadAnnouncementAttachment() {
		//用户未登陆，无法下载非All类型的公告附件；已登录，只能下载其所在业务模块和All类型的公告附件；
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("attachment", attachment);
		map.put("start", 0);
		map.put("end", 1);
		List<EbkAnnouncement> announcements = ebkAnnouncementService.findEbkAnnouncementListByMap(map);
		if(announcements == null || announcements.size() == 0){
			return "error_404";
		}
		ComFile comFile = this.fsClient.downloadFile(this.attachment);
		
		this.downloadFileName =  this.getDownloadFileName(comFile.getFileName());
		this.contentType = this.getContentType(comFile.getFileName());
		this.downloadStream = comFile.getInputStream();
		
		return SUCCESS;
	}
	/**
	 * 查看公告
	 * @return
	 */
	@Action("show_announcement_detail")
	public String showAnnouncementDetail(){
		EbkAnnouncement announcement = ebkAnnouncementService.selectByPrimaryKey(Long.parseLong(getRequestParameter("id")));
		if(null == announcement){
			return "error_404";
		}
		List<EbkAnnouncement> list = new ArrayList<EbkAnnouncement>();
		list.add(announcement);
		initAttachmentName(list);
		setRequestAttribute("announcement", announcement);
		return "show_announcement_detail";
	}
	
	//获取下载文件的文件名.
	private String getDownloadFileName(String fileName) {
		String result = null;
		try {
		String Agent = super.getRequest().getHeader("User-Agent"); 
        if (null != Agent) { 
            Agent = Agent.toLowerCase(); 
            if (Agent.indexOf("firefox") != -1) { 
            	result = new String(fileName.getBytes(),"iso8859-1"); 
            } else if (Agent.indexOf("msie") != -1) { 
            	result = java.net.URLEncoder.encode(fileName,"UTF-8"); 
            } else { 
            	result = java.net.URLEncoder.encode(fileName,"UTF-8"); 
            } 
        }
		} catch (UnsupportedEncodingException e) {
			logger.info(e.getMessage(),e);
		}
		return result;
	}
	
	//获取下载文件的文件类型.
	private String getContentType(String fileName) {
		if (StringUtil.isEmptyString(fileName)) {
			return null;
		}
		Map<String,String> mimeTypeMap = new HashMap<String,String>();
		 mimeTypeMap.put("ai", "application/postscript");
		 mimeTypeMap.put("eps", "application/postscript");
		 mimeTypeMap.put("exe", "application/octet-stream");
		 mimeTypeMap.put("doc", "application/vnd.ms-word");
		 mimeTypeMap.put("xls", "application/vnd.ms-excel");
		 mimeTypeMap.put("ppt", "application/vnd.ms-powerpoint");
		 mimeTypeMap.put("pps", "application/vnd.ms-powerpoint");
		 mimeTypeMap.put("pdf", "application/pdf");
		 mimeTypeMap.put("xml", "application/xml");
		 mimeTypeMap.put("odt", "application/vnd.oasis.opendocument.text");
		 mimeTypeMap.put("swf", "application/x-shockwave-flash");
		 mimeTypeMap.put("gz", "application/x-gzip");
		 mimeTypeMap.put("tgz", "application/x-gzip");
		 mimeTypeMap.put("bz", "application/x-bzip2");
		 mimeTypeMap.put("bz2", "application/x-bzip2");
		 mimeTypeMap.put("tbz", "application/x-bzip2");
		 mimeTypeMap.put("zip", "application/zip");
		 mimeTypeMap.put("rar", "application/x-rar");
		 mimeTypeMap.put("tar", "application/x-tar");
		 mimeTypeMap.put("7z", "application/x-7z-compressed");
		 mimeTypeMap.put("txt", "text/plain");
		 mimeTypeMap.put("php", "text/x-php");
		 mimeTypeMap.put("html", "text/html");
		 mimeTypeMap.put("htm", "text/html");
		 mimeTypeMap.put("js", "text/javascript");
		 mimeTypeMap.put("css", "text/css");
		 mimeTypeMap.put("rtf", "text/rtf");
		 mimeTypeMap.put("rtfd", "text/rtfd");
		 mimeTypeMap.put("py", "text/x-python");
		 mimeTypeMap.put("java", "text/x-java-source");
		 mimeTypeMap.put("rb", "text/x-ruby");
		 mimeTypeMap.put("sh", "text/x-shellscript");
		 mimeTypeMap.put("pl", "text/x-perl");
		 mimeTypeMap.put("sql", "text/x-sql");
		 mimeTypeMap.put("bmp", "image/x-ms-bmp");
		 mimeTypeMap.put("jpg", "image/jpeg");
		 mimeTypeMap.put("jpeg", "image/jpeg");
		 mimeTypeMap.put("gif", "image/gif");
		 mimeTypeMap.put("png", "image/png");
		 mimeTypeMap.put("tif", "image/tiff");
		 mimeTypeMap.put("tiff", "image/tiff");
		 mimeTypeMap.put("tga", "image/x-targa");
		 mimeTypeMap.put("psd", "image/vnd.adobe.photoshop");
		 mimeTypeMap.put("mp3", "audio/mpeg");
		 mimeTypeMap.put("mid", "audio/midi");
		 mimeTypeMap.put("ogg", "audio/ogg");
		 mimeTypeMap.put("mp4a", "audio/mp4");
		 mimeTypeMap.put("wav", "audio/wav");
		 mimeTypeMap.put("wma", "audio/x-ms-wma");
		 mimeTypeMap.put("avi", "video/x-msvideo");
		 mimeTypeMap.put("dv", "video/x-dv");
		 mimeTypeMap.put("mp4", "video/mp4");
		 mimeTypeMap.put("mpeg", "video/mpeg");
		 mimeTypeMap.put("mpg", "video/mpeg");
		 mimeTypeMap.put("mov", "video/quicktime");
		 mimeTypeMap.put("wm", "video/x-ms-wmv");
		 mimeTypeMap.put("flv", "video/x-flv");
		 mimeTypeMap.put("mkv", "video/x-matroska");
		 
		 String result = null;
		 String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		 result = mimeTypeMap.get(fileNameSuffix);
		 return result;
	}
	
	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public InputStream getDownloadStream() {
		return downloadStream;
	}

	public void setDownloadStream(InputStream downloadStream) {
		this.downloadStream = downloadStream;
	}

	public void setEbkAnnouncementService(
			EbkAnnouncementService ebkAnnouncementService) {
		this.ebkAnnouncementService = ebkAnnouncementService;
	}
	
	public Page<EbkAnnouncement> getEbkAnnouncementPage() {
		return ebkAnnouncementPage;
	}

	public void setEbkAnnouncementPage(Page<EbkAnnouncement> ebkAnnouncementPage) {
		this.ebkAnnouncementPage = ebkAnnouncementPage;
	}

	public FSClient getFsClient() {
		return fsClient;
	}

	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}

	public Long getAttachment() {
		return attachment;
	}

	public void setAttachment(Long attachment) {
		this.attachment = attachment;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
