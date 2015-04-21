package ts;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BIDemo {

	public static void main(String[] args) {
//		String url = "jdbc:oracle:thin:@192.168.0.16:1521:lvmamadb";
//		String user = "lvmama_super";
//		String password = "lvmama_super";
//		String drvierClass = "oracle.jdbc.driver.OracleDriver";
		 String url = "jdbc:oracle:thin:@192.168.10.77:1521:lvmamadw";
		 String user = "EDM_TEST";
		 String password = "p3xnm.XqnWeo";
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
			ps = conn.prepareStatement(getSql());
			log("查询start");
			long startTime = System.currentTimeMillis();
			rs = ps.executeQuery();
			log("查询end" + ",耗时(ms):" + (System.currentTimeMillis() - startTime));
			ResultSetMetaData ms = rs.getMetaData();
			int colCount = ms.getColumnCount();
			List<String> colList = new ArrayList<String>();
			for (int i = 1; i <= colCount; i++) {
				colList.add(ms.getColumnName(i));
			}
			System.out.println(colList);
			while (rs.next()) {
				List<Object> row = new ArrayList<Object>();
				for (int i = 1; i <= colCount; i++) {
					row.add(rs.getObject(i));
				}
				System.out.println(row);
			}
		} catch (Exception e) {
			System.out.println("连接BI数据库出错!");
			e.printStackTrace();
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

	public static String getSql() {
		StringBuffer result = new StringBuffer();
		BufferedReader reader = null;
		try {
			String file = "D:/eclipse4/workspace/Super_operate/src/java/sql.txt";
//			 String file = "/home/bi/sql.txt";
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String s = null;
			while ((s = reader.readLine()) != null) {
				result.append(s);
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
		return result.toString();
	}

	private static SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static void log(String s) {
		System.out.println(format.format(Calendar.getInstance().getTime())
				+ "\t:" + s);
	}
}
