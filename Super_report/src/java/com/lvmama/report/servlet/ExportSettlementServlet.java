package com.lvmama.report.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.lvmama.comm.utils.RandomFactory;
import com.lvmama.report.utils.ExcelExportUtil;

public class ExportSettlementServlet extends HttpServlet {
	
	private static final Log log = LogFactory.getLog(ExportSettlementServlet.class);
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String targ1 = req.getParameter("settlementId");
		String targ2 = req.getParameter("subSettlementId");
		String whereFragment="";
		if (targ1!=null) {
			whereFragment = "WHERE ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID = ORD_SUB_SETTLEMENT_ITEM.ORDER_ITEM_META_ID AND ORD_SUB_SETTLEMENT_ITEM.SUB_SETTLEMENT_ID = ORD_SUB_SETTLEMENT.SUB_SETTLEMENT_ID AND ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_ID AND ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER' AND ORD_PERSON.PERSON_TYPE = 'CONTACT' AND ORD_PERSON.OBJECT_ID = ORD_ORDER_ITEM_META.ORDER_ID AND ORD_SUB_SETTLEMENT.SETTLEMENT_ID = "
				+ Long.valueOf(targ1) + ";";
		}
		if (targ2!=null) {
			whereFragment = "WHERE ORD_SUB_SETTLEMENT_ITEM.SUB_SETTLEMENT_ID = " + Long.valueOf(targ2)
				+ " AND ORD_SUB_SETTLEMENT_ITEM.ORDER_ITEM_META_ID =  ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID  AND ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID =  ORD_ORDER_ITEM_META.ORDER_ITEM_ID  AND ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'   AND ORD_PERSON.PERSON_TYPE = 'CONTACT'  AND ORD_PERSON.OBJECT_ID = ORD_ORDER_ITEM_META.ORDER_ID ;";
		}
		Resource resource = new ClassPathResource("export_settlement.sql");
		ExcelExportUtil util = new ExcelExportUtil(RandomFactory.generateMixed(6), whereFragment, resource.getFile().getAbsolutePath());
		File resultFile = util.create();
		if (resultFile!=null) {
			log.info("export settlement to :" + resultFile.getAbsolutePath());
			res.setContentType("");
			res.addHeader("Content-Disposition", "attachment; filename=\"" + resultFile.getName() + "\"");
			res.setContentLength((int)resultFile.length());
			
            FileInputStream inStream = new FileInputStream(resultFile);
            byte[] buf = new byte[4096];
            /*创建输出流*/
            ServletOutputStream servletOS = res.getOutputStream();
            int readLength;
            while (((readLength = inStream.read(buf)) != -1)) {
                servletOS.write(buf, 0, readLength);
            }
            inStream.close();
            servletOS.flush();
            servletOS.close();
			resultFile.delete();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
