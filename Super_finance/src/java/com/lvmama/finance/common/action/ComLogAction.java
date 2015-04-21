package com.lvmama.finance.common.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lvmama.finance.base.BaseAction;
import com.lvmama.finance.common.ibatis.po.ComLog;
import com.lvmama.finance.common.service.ComLogService;

/**
 * 系统日志
 * 
 * @author yanggan
 * 
 */
@Controller
@RequestMapping("/log")
public class ComLogAction extends BaseAction {

	@Autowired
	private ComLogService comLogService;

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
	@RequestMapping("/search/{type}/{id}")
	public String search(@PathVariable("type") String type, @PathVariable("id") Long id, Model model) {
		List<ComLog> logs = comLogService.searchLog(id, type);
		model.addAttribute("logs", logs);
		return "common/log";
	}

}
