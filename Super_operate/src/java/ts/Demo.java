package ts;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class Demo {

	static Logger logger = Logger.getLogger(Demo.class);

	public static void main(String[] args) {
		bicon();
	}
	public static void bicon(){
		/**
		 * jdbc_2.username=EDM_TEST jdbc_2.password=p3xnm.XqnWeo
		 * jdbc_2.url=jdbc:oracle:thin:@192.168.10.77:1521:lvmamadw
		 */
//		String url = "jdbc:oracle:thin:@192.168.0.16:1521:lvmamadb";
//		String user = "lvmama_super";
//		String password = "lvmama_super";
//		String drvierClass = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@192.168.10.77:1521:lvmamadw";
		String user = "EDM_TEST";
		String password = "p3xnm.XqnWeo";
		String drvierClass = "oracle.jdbc.driver.OracleDriver";
		Connection conn = null;
		try {
			Class.forName(drvierClass);
			Map<String,String> map = new LinkedHashMap<String,String>();
			map.put("数据库ip端口实例", url);
			map.put("用户名", user);
			map.put("密码", password);
			map.put("驱动", drvierClass);
			logger.info(JSON.toJSONString(map,true));
			conn = DriverManager.getConnection(url, user, password);
			if (!conn.isClosed()) {
				logger.info("连接上了BI数据库！");
			} else {
				logger.info("BI数据库连接不上！");
			}
		} catch (Exception e) {
			logger.error("连接BI数据库出错!");
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}

	}

}
