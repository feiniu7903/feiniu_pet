package com.lvmama.pet.fax.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.pet.fax.dao.TrafaxStatusDao;
import com.lvmama.pet.fax.utils.Constant;

/**
 * Servlet implementation class TrafaxReceiveServlet
 */
public class TrafaxReceiveServlet extends HttpServlet {
	private static final Log log = LogFactory.getLog(TrafaxReceiveServlet.class);
	private static final long serialVersionUID = 1L;
	private TrafaxStatusDao trafaxStatusDao = (TrafaxStatusDao)ServiceContext.getBean("trafaxStatusDao"); 
 
	File tiff;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		log.info("test ="+Constant.hasTest());
		String action = request.getParameter("action");
		log.info("action = "+action);
		if(Constant.hasTest()){
			//执行传真回传处理
			if("receive".equals(action)){
				this.exeReceive(request);
			}else if("faxStatus".equals(action)){
				//修改传真状态
				String barcode = request.getParameter("barcode");
				String faxstatus = request.getParameter("faxstatus");
				String errorMsg = request.getParameter("errorMsg");
				trafaxStatusDao.updateFaxStatus(faxstatus, barcode, errorMsg);
				request.setAttribute("msg", "success");
			}
			
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/fax/trafaxReceive.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 执行传真回传处理
	 * @param request
	 */
	private void exeReceive(HttpServletRequest request){
		String FaxID = null;
		String barCode = null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(4096);
			factory.setRepository(new File(Constant.getRecvDirectory()));
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(4194304);

			String fieldName = "TEMP";
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> i = items.iterator();
			FileItem fi = null;
			while (i.hasNext()) {
				fi = i.next();
				fieldName = fi.getFieldName();
				if (!fi.isFormField()) {
					FaxID = fi.getName();
					if(FaxID.lastIndexOf("/")>-1){
						FaxID = FaxID.substring(FaxID.lastIndexOf("/")+1, FaxID.length());
					}else if(FaxID.lastIndexOf("\\")>-1){
						FaxID = FaxID.substring(FaxID.lastIndexOf("\\")+1, FaxID.length());
					}
					
					File savefile = new File(Constant.getRecvDirectory()+"/"+FaxID);
					fi.write(savefile);
				} else {
					if (fieldName.equals("barCode")) {
						barCode = fi.getString();
					} else {
						log.info(fieldName + "=" + fi.getString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!"".equals(FaxID) && FaxID != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sid", this.getSid());
			map.put("faxcallerId", "test110");
			map.put("faxId", FaxID);
			map.put("barCode", barCode);
			
			trafaxStatusDao.insertTdbiFaxIn(map);
	 
			request.setAttribute("msg", "success");
		}
	}

	public Long getSid(){
		return new Date().getTime();
	}
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrafaxReceiveServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public File getTiff() {
		return tiff;
	}

	public void setTiff(File tiff) {
		this.tiff = tiff;
	}

}
