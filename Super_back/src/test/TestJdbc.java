import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class TestJdbc {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url,user="lvmama_super",password="lvmama_super";
		url="jdbc:oracle:thin:@192.168.0.22:1521:lvmamadb";
		Connection con=DriverManager.getConnection(url,user,password);
		PreparedStatement ps=con.prepareStatement("select * from temp");
		ResultSet rs=ps.executeQuery();	
		rs.next();
		System.out.println(rs.getObject(1).getClass().getName());

	}

}
