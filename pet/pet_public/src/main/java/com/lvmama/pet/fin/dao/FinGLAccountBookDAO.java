package com.lvmama.pet.fin.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.FinGLAccountBook;

/**
 * 帐套配置数据访问层
 * 
 * @author taiqichao
 * 
 */
@Repository
public class FinGLAccountBookDAO extends BaseIbatisDAO {
	
	/**
	 * 查询所有帐套配置
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FinGLAccountBook> queryAllAccountBook(){
		return super.queryForList("FIN_GL_ACCOUNT_BOOK.queryAllAccountBook");
	}

}
