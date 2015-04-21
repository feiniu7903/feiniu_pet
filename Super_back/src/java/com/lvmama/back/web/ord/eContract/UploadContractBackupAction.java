package com.lvmama.back.web.ord.eContract;

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

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdEcontractBackUpFile;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;
import com.opensymphony.oscache.util.StringUtil;

public class UploadContractBackupAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2140900532122835808L;
	
	private static final Logger LOG = Logger.getLogger(UploadContractBackupAction.class);
 
	private OrdEContractService ordEContractService;
	/**
	 * 文件系统服务.
	 */
	private FSClient fsClient = (FSClient)SpringBeanProxy.getBean("fsClient");
	/**
	 * 日志接口
	 */
	private ComLogService comLogService;
	/**
	 * 电子合同编码
	 */
	private Long orderId;
	
	/**
	 * 文件地址
	 */
	private String furl;
	/**
	 * 文件名称
	 */
	private String fileName;
	
	private OrdEcontractBackUpFile backupFile = new OrdEcontractBackUpFile();
	
	private List<OrdEcontractBackUpFile> fileList = new ArrayList<OrdEcontractBackUpFile>();

	public void doBefore() throws Exception{
		initFileList();
	}
	
	public void upload() throws Exception{
		if (StringUtil.isEmpty(furl)) {
			ZkMessage.showError("请上传文件!");
			return;
		}
		try {
			Long fileId = fsClient.uploadFile(new File(furl), "eContract");
			if(StringUtil.isEmpty(backupFile.getFileName())){
				backupFile.setFileName(fileName);
			}
			backupFile.setFileContent("合同备份".getBytes());
			backupFile.setFileId(fileId);
			backupFile.setCreateUser(getOperatorName());
			ordEContractService.insertEcontractBackUpFile(backupFile);
			comLogService.insert(
					"ORD_ECONTRACT_BACKUP_FILE",
					orderId,
					orderId,
					getOperatorName(),
					Constant.COM_LOG_CONTRACT_EVENT.uploadContractBackupFile.name(),
					"上传合同备份文件  ",
					getOperatorName()+"上传合同文件 "+backupFile.getFileName(),"ORD_ECONTRACT");
			initFileList();
			alert("上传文件成功");
		}catch(Exception e){
			LOG.warn("上传合同备份文件失败:\r\n"+e.getMessage());
			alert("上传合同备份文件失败");
		}
	}
	public void deleteFile(final Long fileId,final String fileName) throws Exception{
		final String  operatorName=super.getOperatorName();
		ZkMessage.showQuestion("您确定要删除此备份文件吗?", new ZkMsgCallBack() {
			public void execute() {
				OrdEcontractBackUpFile file = new OrdEcontractBackUpFile();
				file.setFileId(fileId);
				file.setIsValid("N");
				file.setUpdateUser(operatorName);
				try{
					ordEContractService.updateEcontractBackUpFile(file);
					comLogService.insert(
							"ORD_ECONTRACT_BACKUP_FILE",
							orderId,
							orderId,
							getOperatorName(),
							Constant.COM_LOG_CONTRACT_EVENT.deleteContractBackupFile.name(),
							"删除合同备份文件",
							getOperatorName()+"删除合同备份文件 "+fileName,"ORD_ECONTRACT");
					initFileList();
					alert("删除合同备份文件成功");
				}catch(Exception e){
					LOG.warn("删除合同备份文件失败:\r\n"+e.getMessage());
					alert("删除合同备份文件失败");
				}
			}
		}, new ZkMsgCallBack() {
			public void execute() {
			}
		});
	}
	
	public void downFile(final Long fileId) throws Exception{
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("fileId", fileId);
		List<OrdEcontractBackUpFile> fileList = ordEContractService.queryEcontractBackUpFile(parameters);
		if(null == fileList || (null != fileList && fileList.size() == 0)){
			alert("无此文件");
		}else{
			try {
				OrdEcontractBackUpFile file = fileList.get(0);
				ComFile comFile = fsClient.downloadFile(file.getFileId());
				Filedownload.save(comFile.getInputStream(), "application/octet-stream", comFile.getFileName());
				alert("下载成功");
			} catch (Exception e) {
				alert(e.getMessage());
			}		
		}
	}
	private void initFileList(){
		if(null != orderId){
			backupFile = new OrdEcontractBackUpFile();
			backupFile.setOrderId(orderId);
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("orderId", orderId);
			parameters.put("isValid", "Y");
			fileList = ordEContractService.queryEcontractBackUpFile(parameters);
			furl="";
		}
	}
	  /** *//**
     * 文件转化为字节数组
     * @Author Sean.guo
     * @EditTime 2007-8-13 上午11:45:28
     */
    public static byte[] getBytesFromFile(File f){
        if (f == null){
            return null;
        }
        FileInputStream stream =null;
        ByteArrayOutputStream out =null;
        try{
            stream = new FileInputStream(f);
            out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            for (int n;(n = stream.read(b)) != -1;) {
            	out.write(b, 0, n);
            }
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e){
        } finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(stream);
		}
        return null;
    }

	public OrdEcontractBackUpFile getBackupFile() {
		return backupFile;
	}

	public void setBackupFile(OrdEcontractBackUpFile backupFile) {
		this.backupFile = backupFile;
	}

	public List<OrdEcontractBackUpFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<OrdEcontractBackUpFile> fileList) {
		this.fileList = fileList;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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

	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}
	
}
