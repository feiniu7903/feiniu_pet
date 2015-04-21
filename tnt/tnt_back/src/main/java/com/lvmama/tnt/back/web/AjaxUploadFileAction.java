package com.lvmama.tnt.back.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.json.JSONResult;

/**
 * 附件处理Action
 * 
 */
@Controller
@RequestMapping("/pet/ajax")
public class AjaxUploadFileAction {

	@Autowired
	private FSClient vstFSClient;

	/**
	 * 上传文件使用
	 */
	@RequestMapping(value = "/file/upload")
	public void upload(
			@RequestParam(value = "file", required = false) MultipartFile file,
			String serverType, HttpServletRequest request,
			HttpServletResponse response) {

		String fileName = file.getOriginalFilename();
		JSONResult result = new JSONResult();
		try {
			if (StringUtils.isEmpty(serverType)) {
				throw new IllegalArgumentException("服务类型不存在");
			}
			if (file.getSize() > 18874368L) {
				throw new IllegalArgumentException("文件不可以大于18M");
			}
			Long pid = vstFSClient.uploadFile(fileName, file.getBytes(),
					serverType);
			if (pid == 0) {
				throw new IllegalArgumentException("上传文件失败");
			}
			result.put("file", pid);
			result.put("fileName", fileName);

		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(response);
	}

	/**
	 * 上传图片使用
	 * 
	 */
	@RequestMapping(value = "/image/upload")
	public void updateImage(
			@RequestParam(value = "file", required = false) MultipartFile file,
			String serverType, HttpServletRequest request,
			HttpServletResponse response) {

		String fileName = file.getOriginalFilename();
		JSONResult result = new JSONResult(response);
		try {
			if (file.getSize() > 10424410L) {
				throw new IllegalArgumentException("文件不可以大于1M");
			}
			Long fileId = vstFSClient.uploadFile(fileName, file.getBytes(),
					serverType);
			result.put("fileId", fileId);
			result.put("fileName", fileName);

		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(response);
	}

	/**
	 * 上传传真回传件使用
	 */
	@RequestMapping(value = "/FaxReceive/upload")
	public void uploadFaxReceiveFile(
			@RequestParam(value = "file", required = false) MultipartFile file,
			String serverType, HttpServletRequest request,
			HttpServletResponse response) {

		String fileName = file.getOriginalFilename();
		JSONResult result = new JSONResult(response);
		try {
			Long fileId = vstFSClient.uploadFile(fileName, file.getBytes(),
					serverType);
			result.put("fileId", fileId);
			result.put("fullName", fileName);
		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(response);
	}

	@RequestMapping(value = "/file/downLoad")
	public void downLoad(Long fileId, HttpServletRequest request,
			HttpServletResponse response) {
		OutputStream os = null;
		try {
			ComFile resultFile = vstFSClient.downloadFile(fileId);
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ java.net.URLEncoder.encode(
									resultFile.getFileName(), "UTF-8"));
			os = response.getOutputStream();
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

	/* * * * * * * 上传文件记录操作人 (后台登陆完成后放开使用)* * * * * * * * * * */
	protected String getSessionUserNameAndCheck(HttpServletRequest request,
			HttpServletResponse response) {
		PermUser pu = getSessionUser(request, response);
		if (pu == null) {
			throw new IllegalArgumentException("您没有登录或离开太久");
		}
		return pu.getUserName();
	}

	protected PermUser getSessionUser(HttpServletRequest request,
			HttpServletResponse response) {
		return (PermUser) ServletUtil.getSession(request, response,
				"SESSION_BACK_USER");
	}

}