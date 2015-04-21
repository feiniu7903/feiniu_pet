package com.lvmama.operate.util;

import org.apache.log4j.Logger;

public class EdmLogUtil {
	public static final Logger LOG = Logger.getLogger(EdmLogUtil.class);
	/**
	 * 记录用户操作
	 * @param table
	 * @param operator
	 * @param content
	 */
	public static void insert(String table,String operator,Long id){
		LOG.info("EDM 新建信息  table="+table+" creator="+operator+" id="+id);
	}
	public static void update(String table,Long id,String operator,String content){
		LOG.info("EDM 修改信息  table="+table+" 编号="+id+" creator="+operator+"\r\n  修改内容："+content);
	}
	public static void delete(String table,Long id,String operator,String content){
		LOG.info("EDM 删除信息  table="+table+" 编号="+id+" creator="+operator+"\r\n  删除内容："+content);
	}
}
