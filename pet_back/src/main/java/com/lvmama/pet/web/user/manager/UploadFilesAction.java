package com.lvmama.pet.web.user.manager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.zkoss.zul.Filedownload;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.pub.ComAffix;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComAffixService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.utils.ZkMessage;
import com.lvmama.pet.web.BaseAction;

/**
 * 上传附件ACTION
 * 
 * @author shihui
 * 
 */
public class UploadFilesAction extends BaseAction {

	private static final long serialVersionUID = 1189703885908270065L;

	private static final Logger LOG = Logger.getLogger(UploadFilesAction.class);
	/**
	 * 文件系统服务.
	 */
	private FSClient fsClient = (FSClient) SpringBeanProxy.getBean("fsClient");
	/**
	 * 日志接口
	 */
	private ComLogService comLogService;
	/**
	 * 文件地址
	 */
	private String furl;
	/**
	 * 文件名称
	 */
	private String fileName;

	private ComAffix file = new ComAffix();

	private List<ComAffix> fileList = new ArrayList<ComAffix>();

	private Long uuId;

	private ComAffixService comAffixService;

	private final String OBJECT_TYPE = "USER_USER";

	private String fileType;

	@Override
	public void doBefore() throws Exception {
		initFileList();
	}

	public void upload() throws Exception {
		if (StringUtil.isEmptyString(furl)) {
			ZkMessage.showError("请上传文件!");
			return;
		}
		if(StringUtil.isEmptyString(file.getMemo())) {
			ZkMessage.showError("文件描述不能为空!");
			return;
		}
		if(StringUtil.isEmptyString(fileType)) {
			ZkMessage.showError("请选择附件类型!");
			return;
		}
		try {
			final String operatorName = super.getSessionUserName();
			Long fileId = fsClient.uploadFile(new File(furl), "COM_AFFIX");
			if (StringUtil.isEmptyString(file.getName())) {
				file.setName(fileName);
			}
			file.setUserId(operatorName);
			file.setObjectId(uuId);
			file.setObjectType(OBJECT_TYPE);
			file.setFileType(fileType);
			file.setFileId(fileId);
			comAffixService.addAffix(file, operatorName);

			final ComLog log = new ComLog();
			log.setObjectType("USER_USER");
			log.setObjectId(uuId);
			log.setOperatorName(operatorName);
			log.setLogType(Constant.COM_LOG_USER_MANAGER.UPLOAD_FILE.name());
			log.setLogName("上传附件");
			log.setContent("附件类型：" + Constant.COM_LOG_USER_MANAGER.getCnName(fileType) + ",文件描述：" + file.getMemo());
			comLogService.addComLog(log);
			initFileList();
			alert("上传文件成功");
		} catch (Exception e) {
			LOG.warn("上传件失败:\r\n" + e.getMessage());
			alert("上传文件失败");
		}
	}

	public void downFile(final Long fileId) throws Exception {
		try {
			ComFile comFile = fsClient.downloadFile(fileId);
			Filedownload.save(comFile.getInputStream(),
					"application/octet-stream", comFile.getFileName());
			alert("下载成功");
		} catch (Exception e) {
			alert(e.getMessage());
		}
	}

	private void initFileList() {
		if (null != uuId) {
			file = new ComAffix();
			file.setObjectId(uuId);
			file.setObjectType(OBJECT_TYPE);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("objectId", uuId);
			map.put("objectType", OBJECT_TYPE);
			fileList = comAffixService.selectListByParam(map);
			furl = "";
		}
	}

	/** */
	/**
	 * 文件转化为字节数组
	 * 
	 * @Author Sean.guo
	 * @EditTime 2007-8-13 上午11:45:28
	 */
	public static byte[] getBytesFromFile(File f) {
		if (f == null) {
			return null;
		}
		FileInputStream stream = null;
		ByteArrayOutputStream out = null;
		try {
			stream = new FileInputStream(f);
			out = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			for (int n; (n = stream.read(b)) != -1;) {
				out.write(b, 0, n);
			}
			stream.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(stream);
		}
		return null;
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ComAffix getFile() {
		return file;
	}

	public void setFile(ComAffix file) {
		this.file = file;
	}

	public void setComAffixService(ComAffixService comAffixService) {
		this.comAffixService = comAffixService;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getUuId() {
		return uuId;
	}

	public void setUuId(Long uuId) {
		this.uuId = uuId;
	}

	public List<ComAffix> getFileList() {
		return fileList;
	}

}
