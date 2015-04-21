package com.lvmama.finance;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.finance.base.FinanceContext;
import com.lvmama.finance.common.ibatis.dao.ComLogDAO;
import com.lvmama.finance.common.ibatis.po.ComLog;

/**
 * 基础Service
 * 
 * @author yanggan
 * 
 */
public class BaseService {
	private final int MAX_LIST_SIZE = 1000;
	@Autowired
	protected ComLogDAO comLogDAO;

	/**
	 * 生成LOG
	 * 
	 * @param log
	 *            日志对象
	 */
	protected void log(ComLog log) {
		HttpSession session = FinanceContext.getSession();
		PermUser user = (PermUser) session.getAttribute(com.lvmama.comm.vo.Constant.SESSION_BACK_USER);
		log.setUserid(user.getUserId() + "");
		log.setOperatorName(user.getUserName());
		comLogDAO.insertLog(log);
	}

	/**
	 * 生成LOG
	 * 
	 * @param objectId
	 *            操作的值（可以理解为表的主键）
	 * @param objectType
	 *            操作的类型(可以理解为表名）
	 * @param logType
	 *            日志类型（需要自己定义）
	 * @param logName
	 *            日志名称（需要自己定义）
	 * @param parentId
	 *            上级操作的值（对应上级的objectId）
	 * @param parentType
	 *            上级操作的类型（对应上级的objectType）
	 */
	protected void log(Long objectId, String objectType, String logType, String logName, Long parentId, String parentType, String content) {
		ComLog log = new ComLog();
		log.setObjectId(objectId);
		log.setObjectType(objectType);
		log.setLogType(logType);
		log.setLogName(logName);
		log.setParentId(parentId);
		log.setParentType(parentType);
		log.setContent(content);
		this.log(log);
	}

	/**
	 * 生成LOG
	 * 
	 * @param objectId
	 *            操作的值（可以理解为表的主键）
	 * @param objectType
	 *            操作的类型(可以理解为表名）
	 * @param logType
	 *            日志类型（需要自己定义）
	 * @param logName
	 *            日志名称（需要自己定义）
	 * @param parentId
	 *            上级操作的值（对应上级的objectId）
	 * @param parentType
	 *            上级操作的类型（对应上级的objectType）
	 */
	protected void log(Long objectId, String objectType, String logType, String logName, String content) {
		this.log(objectId, objectType, logType, logName, null, null, content);
	}
	
	//长列表转换为List嵌套结构，以支持sql大数据量处理
	protected List<List<Long>> list2Array(List<Long> list){
		if(list == null || list.size() == 0){
			return null;
		}
		List<List<Long>> arr = new ArrayList<List<Long>>();
		int n = list.size() / MAX_LIST_SIZE;
		if(list.size() % MAX_LIST_SIZE > 0){
			n = n + 1;
		}
		for(int i=0;i<n;i++){
			int end = (i + 1) * MAX_LIST_SIZE;
			if(end > list.size()){
				end = list.size();
			}
			arr.add(list.subList(i * MAX_LIST_SIZE, end)); 
		}
		return arr;
	}
}
