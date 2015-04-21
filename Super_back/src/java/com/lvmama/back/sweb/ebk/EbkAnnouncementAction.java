package com.lvmama.back.sweb.ebk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkAnnouncement;
import com.lvmama.comm.bee.service.ebooking.EbkAnnouncementService;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.EBookingBizType;

/**
 * EBooking公告(平台版).
 */
@Results(value = {
		@Result(name = "ebkAnnouncementList", location = "/WEB-INF/pages/back/ebk/ebkAnnouncementList.jsp"),
		@Result(name = "ebkAnnouncementToAdd", location = "/WEB-INF/pages/back/ebk/ebkAnnouncementToAdd.jsp") 
		})
@Namespace("/announcement")
public class EbkAnnouncementAction extends BackBaseAction {

	private static final long serialVersionUID = 702968048644045717L;

	private EbkAnnouncementService ebkAnnouncementService;
	private EbkAnnouncement ebkAnnouncement = new EbkAnnouncement();
	private Page<EbkAnnouncement> ebkAnnouncementPage = new Page<EbkAnnouncement>();
	private FSClient fsClient;
	// 上传附件的文件对象.
	private File file;
	// 上传附件的文件名.
	private String fileFileName;
	// 附件文件由FSClient返回的ID标识.
	private Long attachment;
	// 附件下载的文件名.
	private String downloadFileName;
	// 附件下载的文件流.
	private InputStream downloadStream;
	// 公告发布状态.
	private String releaseStatus;
	// 公告ID标识.
	private Long announcementId;
	//
	private EBookingBizType[] bizTypeList = Constant.EBookingBizType.values();

	/**
	 * 分页查询所有记录.
	 * 
	 * @return
	 */
	@Action("/ebkAnnouncementList")
	public String announcementList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderByCreateTimeDesc", "true");//创建时间降序排列
		if (StringUtils.isNotEmpty(this.releaseStatus)) {
			params.put("releaseStatus", this.releaseStatus);
		}
		long count = this.ebkAnnouncementService
				.countEbkAnnouncementListByMap(params);
		ebkAnnouncementPage.buildUrl(getRequest());
		ebkAnnouncementPage.setCurrentPage(this.page);
		ebkAnnouncementPage.setTotalResultSize(count);
		if (count > 0) {
			params.put("start", ebkAnnouncementPage.getStartRows());
			params.put("end", ebkAnnouncementPage.getEndRows());
			ebkAnnouncementPage.setItems(this.ebkAnnouncementService
					.findEbkAnnouncementListByMap(params));
		}
		return "ebkAnnouncementList";
	}

	@Action("/ebkAnnouncementToAdd")
	public String preEditAnnouncement() {
		if (announcementId != null) {
			this.ebkAnnouncement = this.ebkAnnouncementService
					.selectByPrimaryKey(this.announcementId);
		}
		return "ebkAnnouncementToAdd";
	}

	@Action(value = "/addorUpdateAnnouncement", interceptorRefs = {
			@InterceptorRef(value = "fileUpload", params = { "maximumSize",
					"10424410" })// 20M
			, @InterceptorRef(value = "defaultStack") })
	public void addOrUpdateAnnouncement() {
		// System.out.println("abcd:" + super.getSessionUser());
		this.ebkAnnouncement.setOperator(super.getSessionUserName());
		if (this.file != null) {
			long attachment = -1L;
			try {
				attachment = this.fsClient.uploadFile(this.fileFileName,
						new FileInputStream(this.file),
						Constant.COM_FS_SERVER_TYPE.EBOOKING.name());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			this.ebkAnnouncement.setAttachment(attachment);
		}
		if (this.ebkAnnouncement.getAnnouncementId() == null) {
			this.ebkAnnouncement.setOperator(super.getSessionUserName());
			this.ebkAnnouncement.setCreateTime(new Date());
			if (this.ebkAnnouncement.getBeginDate() == null) {
				this.ebkAnnouncement.setBeginDate(new Date());
			}
			this.ebkAnnouncementService.insert(this.ebkAnnouncement);
		} else {
			this.ebkAnnouncementService.update(this.ebkAnnouncement);
		}
		this.sendAjaxMsg("SUCCESS");
	}

	@Action(value = "/downloadAnnouncementAttachment", results = { @Result(type = "stream", name = "success", params = {
			"inputName", "downloadStream", "contentDisposition",
			"attachment;filename=${downloadFileName}", "bufferSize", "1024" }) })
	public String downloadAnnouncementAttachment() {
		ComFile comFile = this.fsClient.downloadFile(this.attachment);
		try {
			this.downloadFileName = new String(
					comFile.getFileName().getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.downloadStream = comFile.getInputStream();
		return SUCCESS;
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

	public EbkAnnouncement getEbkAnnouncement() {
		return ebkAnnouncement;
	}

	public void setEbkAnnouncement(EbkAnnouncement ebkAnnouncement) {
		this.ebkAnnouncement = ebkAnnouncement;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
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

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public Long getAnnouncementId() {
		return announcementId;
	}

	public void setAnnouncementId(Long announcementId) {
		this.announcementId = announcementId;
	}

	public EBookingBizType[] getBizTypeList() {
		return bizTypeList;
	}

}
