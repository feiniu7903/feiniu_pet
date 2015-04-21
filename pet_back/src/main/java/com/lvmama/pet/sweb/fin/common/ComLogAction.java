package com.lvmama.pet.sweb.fin.common;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;

/**
 * 系统日志
 * 
 * @author yanggan
 * 
 */
@Results(value={
		@Result(name="log",location="/WEB-INF/pages/back/fin/common/log.ftl")
	})
@Namespace(value="/fin/log")
public class ComLogAction extends BackBaseAction {

	private static final long serialVersionUID = 1L;

	private ComLogService comLogServiceRemote;

	private Long id;
	private String type;
	private List<ComLog> logs;
	
	/**
	 * 查询LOG
	 * 
	 * @param type
	 *            类型
	 * @param id
	 *            值
	 * @param model
	 * @return
	 */
	@Action("search")
	public String search() {
		logs = comLogServiceRemote.queryByObjectId(type, id);
		return "log";
	}

	public List<ComLog> getLogs() {
		return logs;
	}

	
	public void setComLogServiceRemote(ComLogService comLogServiceRemote) {
		this.comLogServiceRemote = comLogServiceRemote;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setType(String type) {
		this.type = type;
	}

}
