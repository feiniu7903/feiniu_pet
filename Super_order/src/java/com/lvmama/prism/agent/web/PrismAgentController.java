package com.lvmama.prism.agent.web;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvmama.ckdevice.dao.DeviceInfoDAO;

@Controller
@RequestMapping("/prism")
public class PrismAgentController {

	@Autowired
	private DeviceInfoDAO dao;

	@RequestMapping(value = "/super/agent", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
	public String getJSON(
			Model model,
			@RequestParam(value = "max", required = false) String max,
			@RequestParam(value = "count", required = false) String count,
			@RequestParam(value = "detail", required = false) String detail,
			@RequestParam(value = "detailSplit", required = false) String detailSplit) {

		int returnStatus = 0;
		String maxResult = "";
		int countResult = 0;
		String detailResult = "";

		if (detailSplit == null)
			detailSplit = ",";

		Connection conn = null;
		try {
			Statement stm = null;
			ResultSet rs = null;
			String sql = null;
			
			conn = dao.getDataSource().getConnection();

			// max
			if (max != null) {
				sql = max;
				if (!sql.trim().toLowerCase().startsWith("select"))
					throw new Exception("Can only query the DB for max");

				stm = conn.createStatement();
				rs = stm.executeQuery(sql);

				if (rs.next()) {
					maxResult = rs.getString(1);
				}

				stm.close();
				stm = null;
			}

			// count
			if (count != null) {
				sql = count.replaceAll("_MAX_RESULT_", maxResult);
				if (!sql.trim().toLowerCase().startsWith("select"))
					throw new Exception("Can only query the DB for detail");

				stm = conn.createStatement();
				rs = stm.executeQuery(sql);

				if (rs.next()) {
					countResult = rs.getInt(1);
				}

				stm.close();
				stm = null;
			}

			// detail
			if (detail != null) {
				sql = detail.replaceAll("_MAX_RESULT_", maxResult);
				if (!sql.trim().toLowerCase().startsWith("select"))
					throw new Exception("Can only query the DB for detail");

				stm = conn.createStatement();
				rs = stm.executeQuery(sql);

				StringBuffer bf = new StringBuffer();
				ResultSetMetaData rsmd = rs.getMetaData();
				int colCount = rsmd.getColumnCount();
				int detailCount = 0;
				while (rs.next() && ++detailCount <= 999) {
					for (int i = 1; i <= colCount; i++) {
						bf.append(rs.getObject(i)).append(detailSplit);
					}
				}

				stm.close();
				stm = null;

				detailResult = bf.toString();
			}

		} catch (Exception e) {
			returnStatus = 1;
			detailResult = e.getMessage();
		} finally {
			if (conn != null)
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		model.addAttribute("returnStatus", returnStatus);
		model.addAttribute("max", maxResult);
		model.addAttribute("count", countResult);
		model.addAttribute("detail", detailResult);

		return "jsonfeed";
	}
}
