package com.lvmama.passport.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pass.PassEvent;


/**
 * @author shihui
 */
@SuppressWarnings("unchecked")
public class PassEventDAO extends BaseIbatisDAO {
	private static final Log log = LogFactory.getLog(PassEventDAO.class);
	
	/**
	 * 按PK查询
	 * 
	 * @param ID
	 */
	public PassEvent selectByEventId(Long eventId) {
		return (PassEvent)super.queryForObject(
				"EVENT.Event_selectByEventId", eventId);
	}
	
	
	/**
	 * 按条件查询
	 * 
	 * @param 查询参数
	 */
	public List<PassEvent> selectByParams(Map<String, Object> params) {
		return super.queryForList(
				"EVENT.EVENT_selectByParams", params);
	}

	/**
	 * 新增
	 * 
	 * @param Event对象
	 */
	public void addEvent(PassEvent event) {
		super
				.insert("EVENT.Event_insertSelective", event);
	}

	/**
	 * 修改
	 */
	public void updateEvent(PassEvent event) {
		super
				.update("EVENT.Event_updateByPrimaryKeySelective", event);
	}
	
	
	/**
	 * 修改事件状态
	 */
	public void updateEventStauts(PassEvent event) {
		super
				.update("EVENT.updateBatchStauts", event);
	}
	
	/**
	 * 修改事件状态
	 */
	public void updateEventStautsByEventId(PassEvent event) {
		super
				.update("EVENT.updateStautsByEventId", event);
	}
	/**
	 * 批量修改
	 * @param passCodes
	 * @return
	 */
		public int updateBatch(final List<PassEvent> events) {
			int count=0;
			try {
				if (events != null) {
					count=(Integer)super.execute(new SqlMapClientCallback() {
						public Object doInSqlMapClient(SqlMapExecutor executor)
								throws SQLException {
							executor.startBatch();
							for (PassEvent event : events) {
								executor.update("EVENT.updateBatchStauts", event);
							}
							int flag=executor.executeBatch();
							return flag;
						}
					});
				}
			} catch (Exception e) {
				log.error("批量更新异常", e);
			}
			return count;
		}
}
