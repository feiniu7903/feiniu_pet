package com.lvmama.ord.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.ord.dao.OrdOrderRouteTravelDAO;

public class OrdOrderRouteTravelDAOTest {

	@Autowired
	OrdOrderRouteTravelDAO ordOrderRouteTravelDAO;
	@Autowired
	FSClient fsClient;
	@Test
	public void testInsert() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.16:1521:lvmamadb", "lvmama_super", "lvmama_super");
		boolean isCycle = true;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement ps = null;
		while(isCycle){
			statement = connection.createStatement();
			resultSet=statement.executeQuery("SELECT ORDER_ID,ROUTE_TRAVEL FROM ORD_ORDER_ROUTE_TRAVEL T WHERE T.FILE_ID IS NULL AND ROWNUM<=100");
			isCycle=resultSet.first();
			while(resultSet.next()){
				Long orderId = resultSet.getLong("ORDER_ID");
				byte[] routeTravel = resultSet.getBytes("ROUTE_TRAVEL");
				Long fileId=fsClient.uploadFile(orderId+"_travel.xml", routeTravel, "eContract");
				ps =connection.prepareStatement("UPDATE ORD_ORDER_ROUTE_TRAVEL T SET T.FILE_ID=? WHERE T.ORDER_ID=?");
				ps.setLong(1, fileId);
				ps.setLong(2, orderId);
				ps.execute();
			}
		}
		if(null!=resultSet && !resultSet.isClosed()){
			resultSet.close();
		}
		if(null!=statement && !statement.isClosed()){
			statement.close();
		}
		if(null!=ps && !ps.isClosed()){
			ps.close();
		}
		if(null!=connection && !connection.isClosed()){
			connection.close();
		}
	}

	@Test
	public void testQuery() {
		fail("Not yet implemented");
	}

}
