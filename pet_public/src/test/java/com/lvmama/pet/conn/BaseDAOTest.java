package com.lvmama.pet.conn;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

public class BaseDAOTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	protected DataSource dataSource;
	
	protected Connection conn;
	
	@Before
	public void initConnection() throws Exception {
		conn = DataSourceUtils.getConnection(dataSource);
	}
	
	@After
	public void relaseConnection() {
		DataSourceUtils.releaseConnection(conn, dataSource);
	}

}
