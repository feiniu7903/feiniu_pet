package com.lvmama.report.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.lvmama.comm.utils.RandomFactory;
import com.lvmama.report.utils.ExcelExportUtil;
/**
 * 
 *  super后台:运营管理-来电统计导出功能.
 *
 */
public class CallCenterQueryExportServlet extends HttpServlet {
	private static final Log log = LogFactory.getLog(CallCenterQueryExportServlet.class);
	 
	private static final long serialVersionUID = 4819911321965277211L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.process(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.process(req, resp);
	}
	
	private void process(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		String whereFragment = this.getWhereFragment(req);
		
		Resource resource = new ClassPathResource("export_callback.sql");
		ExcelExportUtil util = new ExcelExportUtil(RandomFactory.generateMixed(6), whereFragment, resource.getFile().getAbsolutePath());
		File resultFile = util.create();
		fileDownload(resp,resultFile);
	}
	/**
	 * 从request中提取参数,并组装为where子句.
	 * @param request HttpServletRequest.
	 * @return 返回where子句.
	 */
	private String getWhereFragment(HttpServletRequest request) {
		String serachFeedbackBeginDate = request.getParameter("feedbackTime");
		String serachFeedbackEndDate = request.getParameter("feedbackTimeEnd");
		String isCallBack = request.getParameter("isCallBack");
		StringBuilder sb = new StringBuilder();
		sb.append(" WHERE 1 = 1 ");
		if (StringUtils.isNotBlank(serachFeedbackBeginDate) && !"null".equals(serachFeedbackBeginDate)) {
			sb.append(" AND FEEDBACK_TIME >= TO_DATE('").append(serachFeedbackBeginDate.trim()).append("','yyyy-mm-dd hh24:mi:ss') ");
		}
		if (StringUtils.isNotBlank(serachFeedbackEndDate) && !"null".equals(serachFeedbackEndDate)) {
			sb.append(" AND FEEDBACK_TIME <= TO_DATE('").append(serachFeedbackEndDate.trim()).append("','yyyy-mm-dd hh24:mi:ss') ");
		}
		if (StringUtils.isNotBlank(isCallBack) && !"null".equals(isCallBack)) {
			sb.append(" AND CALL_BACK = '").append(isCallBack).append("' ");
		}
		sb.append(";");
		return sb.toString();
		
	}
	/**
	 * 将file文件以附件方式download到客户端.
	 * @param response HttpServletResponse.
	 * @param file 需要download的文件.
	 * @throws IOException
	 */
	private void fileDownload(HttpServletResponse response,File file) throws IOException {
		if (file != null) {
		log.info("export callback to :" + file.getAbsolutePath());
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		ServletOutputStream sos = response.getOutputStream();
		response.setContentType("");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
		response.setContentLength((int)file.length());
		byte[] buffered = new byte[4096];
		int length = 0;
		while ((length = bis.read(buffered)) != -1) {
			sos.write(buffered,0,length);
		}
		bis.close();
		sos.flush();
		sos.close();
		file.delete();
		}
 
	}
}
