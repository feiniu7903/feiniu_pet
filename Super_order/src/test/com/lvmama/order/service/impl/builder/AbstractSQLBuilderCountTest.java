package com.lvmama.order.service.impl.builder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.order.po.SQlBuilderMaterial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class AbstractSQLBuilderCountTest {
	@Test
	public final void testGetSelect() {
		SQLBuilderCountImpl sqlBuilderCountImpl = new SQLBuilderCountImpl();
		Assert.assertNotNull(sqlBuilderCountImpl.getSelect());
	}

	@Test
	public final void testGetLog() {
		SQLBuilderCountImpl sqlBuilderCountImpl = new SQLBuilderCountImpl();
		Assert.assertNotNull(sqlBuilderCountImpl.getLog());
	}

	@Test
	public final void testBuildSQLSelectStatement() {
		SQLBuilderCountImpl sqlBuilderCountImpl = new SQLBuilderCountImpl();
		sqlBuilderCountImpl.buildSQLSelectStatement(new SQlBuilderMaterial());
	}

	@Test
	public final void testBuildSQLFromStatement() {
		SQLBuilderCountImpl sqlBuilderCountImpl = new SQLBuilderCountImpl();
		SQlBuilderMaterial material = new SQlBuilderMaterial();
		material.getTableSet().add("");
		sqlBuilderCountImpl.buildSQLFromStatement(material);
	}

	@Test
	public final void testBuildSQLWhereStatement() {
		SQLBuilderCountImpl sqlBuilderCountImpl = new SQLBuilderCountImpl();
		sqlBuilderCountImpl.buildSQLWhereStatement(new SQlBuilderMaterial());
	}

	@Test
	public final void testBuildSQLOrderByStatement() {
		SQLBuilderCountImpl sqlBuilderCountImpl = new SQLBuilderCountImpl();
		sqlBuilderCountImpl.buildSQLOrderByStatement(new SQlBuilderMaterial());
	}

	@Test
	public final void testBuildSQLPageIndexStatement() {
		SQLBuilderCountImpl sqlBuilderCountImpl = new SQLBuilderCountImpl();
		sqlBuilderCountImpl
				.buildSQLPageIndexStatement(new SQlBuilderMaterial());
	}

	@Test
	public final void testBuildCompleteSQLStatement() {
		SQLBuilderCountImpl sqlBuilderCountImpl = new SQLBuilderCountImpl();
		Assert.assertNotNull(sqlBuilderCountImpl.buildCompleteSQLStatement());
	}

	@Test
	public final void testBuildOrderItemMetaIdInStatement() {
		SQLBuilderCountImpl sqlBuilderCountImpl = new SQLBuilderCountImpl();
		sqlBuilderCountImpl.buildOrderItemMetaIdInStatement();
	}
}
