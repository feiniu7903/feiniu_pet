package ts;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.lvmama.operate.util.FrameUtil;

public class TxtToSql {
	private static Logger logger = Logger.getLogger(TxtToSql.class);

	static Set<String> colSet = new HashSet<String>();

	public static void main(String[] args) throws Exception {
		File dir = new File("C:\\Users\\likun\\Desktop\\sql\\");
		File list[] = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".txt");
			}
		});
		for (File file : list) {
			String filePath = file.getAbsolutePath();
			System.out.println(filePath);
			// System.out.println(JSON.toJSONString(readerTxtFile(filePath),
			// true));
			String sql = convertTxtToSql(filePath);
			File sqlfile = new File(file.getParentFile(), file.getName()
					.substring(0, file.getName().indexOf(".txt")) + ".sql");
			if (!sqlfile.exists()) {
				sqlfile.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(sqlfile)));
			validateSql(sql);
			writer.write(sql);
			writer.flush();
			writer.flush();
		}
		colSet.remove("rn");
		for (String cl : colSet) {
			System.out.println(cl.toUpperCase());
		}
	}

	public static void validateSql(String sql) throws Exception {
		String url = "jdbc:oracle:thin:@192.168.0.16:1521:lvmamadb";
		String user = "lvmama_super";
		String password = "lvmama_super";
		String drvierClass = "oracle.jdbc.driver.OracleDriver";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName(drvierClass);
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("数据库ip端口实例", url);
			map.put("用户名", user);
			map.put("密码", password);
			map.put("驱动", drvierClass);
			System.out.println(map);
			conn = DriverManager.getConnection(url, user, password);
			if (!conn.isClosed()) {
				System.out.println("连接上了BI数据库！");
			} else {
				System.out.println("BI数据库连接不上！");
			}
			ps = conn.prepareStatement(sql);
			logger.info("查询start");
			long startTime = System.currentTimeMillis();
			rs = ps.executeQuery();
			logger.info("查询end" + ",耗时(ms):"
					+ (System.currentTimeMillis() - startTime));
			ResultSetMetaData ms = rs.getMetaData();
			int colCount = ms.getColumnCount();
			List<String> colList = new ArrayList<String>();
			for (int i = 1; i <= colCount; i++) {
				colList.add(ms.getColumnName(i));
			}
			System.out.println(colList);
			if (rs.next()) {
				System.out.println(rs.getObject(1));
				logger.info("sql:" + sql + "\n" + "成功了!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static String convertTxtToSql(String filePath) {
		List<Map<String, String>> result = readerTxtFile(filePath);
		List<String> rows = new ArrayList<String>();
		for (Map<String, String> map : result) {
			rows.add(String.format(
					"select "
							+ FrameUtil.concatIterable(map.keySet(), " %s, ",
									false) + " %s from dual", map.values()
							.toArray()));
		}

		return FrameUtil.concatIterable(rows, " union ", false);

	}

	public static List<Map<String, String>> readerTxtFile(String filePath) {
		List<String> filecons = readerFile(filePath);
		List<Map<String, String>> result = null;
		if (filecons.size() >= 1) {
			result = new ArrayList<Map<String, String>>();
			String[] cols = filecons.get(0).split("##");
			for (String string : cols) {
				colSet.add(string);
			}
			for (int i = 1; i < filecons.size(); i++) {
				Map<String, String> map = new LinkedHashMap<String, String>();
				String[] ts = filecons.get(i).split("##");
				for (int j = 0; j < cols.length; j++) {
					if (!"rn".equalsIgnoreCase(cols[j])) {
						map.put("\'" + ts[j] + "\'", cols[j]);
					}
				}
				result.add(map);
			}
		}
		return result;
	}

	public static List<String> readerFile(String filePath) {
		List<String> filecons = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath)));
			String s = null;
			while ((s = reader.readLine()) != null) {
				filecons.add(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return filecons;
	}
}
