package com.lvmama.pet.mark.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mark.MarkMembershipCardCode;

public class MarkMembershipCardCodeDAO extends BaseIbatisDAO {
	
	/**
	 * 查询
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MarkMembershipCardCode> query(final Map<String,Object> parameters) {
		return super.queryForListNoMax("MARK_MEMBERSHIP_CARD_CODE.query", parameters);
	}
	
	/**
	 * 计数
	 * @param parameters
	 * @return
	 */
	public Long count(final Map<String,Object> parameters) {
		return (Long) super.queryForObject("MARK_MEMBERSHIP_CARD_CODE.count", parameters);
	}
	
	/**
	 * 批量插入
	 * @param codes
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void insertByBatch(final Set<MarkMembershipCardCode> codes) {
		super.execute(new SqlMapClientCallback() {
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                executor.startBatch();
                for (MarkMembershipCardCode code : codes) { 
                    executor.insert("MARK_MEMBERSHIP_CARD_CODE.insert", code);
                }
                executor.executeBatch();
				return null;
            }
         });
	}
	
	/**
	 * 删除
	 * @param parameters
	 */
	public void delete(final Map<String,Object> parameters) {
		super.delete("MARK_MEMBERSHIP_CARD_CODE.delete", parameters);
	}
	
	/**
	 * 使用会员卡
	 * @param code 会员卡号
	 */
	public void use(final String code) {
		if (null == code) {
			return;
		}
		super.update("MARK_MEMBERSHIP_CARD_CODE.use", code);
	}
}
