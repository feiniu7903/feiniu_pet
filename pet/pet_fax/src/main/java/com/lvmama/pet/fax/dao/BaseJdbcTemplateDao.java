package com.lvmama.pet.fax.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class BaseJdbcTemplateDao {

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
		
}
