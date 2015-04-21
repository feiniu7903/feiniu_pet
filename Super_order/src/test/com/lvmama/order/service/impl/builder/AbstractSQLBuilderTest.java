package com.lvmama.order.service.impl.builder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.order.po.SQlBuilderMaterial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-order-beans.xml" })
public class AbstractSQLBuilderTest {
	@Test
	public final void testGetSelect() {
		SQLBuilderImpl sqlBuilderImpl = new SQLBuilderImpl();
		Assert.assertNotNull(sqlBuilderImpl.getSelect());
	}

	@Test
	public final void testGetLog() {
		SQLBuilderImpl sqlBuilderImpl = new SQLBuilderImpl();
		Assert.assertNotNull(sqlBuilderImpl.getLog());
	}

	@Test
	public final void testBuildSQLSelectStatement() {
		SQLBuilderImpl sqlBuilderImpl = new SQLBuilderImpl();
		sqlBuilderImpl.buildSQLSelectStatement(new SQlBuilderMaterial());
	}

	@Test
	public final void testBuildSQLFromStatement() {
		SQLBuilderImpl sqlBuilderImpl = new SQLBuilderImpl();
		SQlBuilderMaterial material = new SQlBuilderMaterial();
		material.getTableSet().add("");
		sqlBuilderImpl.buildSQLFromStatement(material);
	}

	@Test
	public final void testBuildSQLWhereStatement() {
		SQLBuilderImpl sqlBuilderImpl = new SQLBuilderImpl();
		sqlBuilderImpl.buildSQLWhereStatement(new SQlBuilderMaterial());
	}

	@Test
	public final void testBuildSQLOrderByStatement() {
		SQLBuilderImpl sqlBuilderImpl = new SQLBuilderImpl();
		sqlBuilderImpl.buildSQLOrderByStatement(new SQlBuilderMaterial());
	}

	@Test
	public final void testBuildSQLPageIndexStatement() {
		SQLBuilderImpl sqlBuilderImpl = new SQLBuilderImpl();
		sqlBuilderImpl.buildSQLPageIndexStatement(new SQlBuilderMaterial());
	}

	@Test
	public final void testBuildCompleteSQLStatement() {
		SQLBuilderImpl sqlBuilderImpl = new SQLBuilderImpl();
		Assert.assertNotNull(sqlBuilderImpl.buildCompleteSQLStatement());
	}

	@Test
	public final void testBuildOrderItemMetaIdInStatement() {
		SQLBuilderImpl sqlBuilderImpl = new SQLBuilderImpl();
		Assert.assertNotNull(sqlBuilderImpl.buildOrderItemMetaIdInStatement());
	}
}
