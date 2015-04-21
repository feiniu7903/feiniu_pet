package com.lvmama.passport.web.pass;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.lvmama.ZkBaseAction;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.pass.OrderAutoPerformService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
public class FixedPassCodeStatusAction extends ZkBaseAction {
	private static final long serialVersionUID = 8033341535916095589L;
	private PassCodeService passCodeService;
	private OrderAutoPerformService orderAutoPerformService;
	
	/**
	 * 修复二维码通关自动履行数据
	 */
	public void doFixedDate(){
		Map<String, Object> queryOption = new HashMap<String, Object>();
		queryOption.put("status", "SUCCESS");
		queryOption.put("passportStatus","UNUSED");
		queryOption.put("isAutoPerform","true");
		queryOption.put("isInvalideTime","true");
		Integer totalRowCount=passCodeService.selectPassCodeRowCount(queryOption);
		List<PassCode> passCodeList=null;
		Integer rowCount = 800;
		Integer pageCount = getTotalPages(totalRowCount, rowCount);
		for(int curPage =1;curPage<=pageCount;curPage++){
			int startRow = (curPage - 1) * rowCount + 1;
			int endRow = curPage * rowCount;
			queryOption.put("_startRow",startRow);
			queryOption.put("_endRow",endRow);
			passCodeList = passCodeService.selectPassCodeByParams(queryOption);
			orderAutoPerformService.autoPerform(passCodeList);
		}
	}
	
	public int getTotalPages(int totalResultSize, int pageSize) {
		if (totalResultSize % pageSize > 0)
			return totalResultSize / pageSize + 1;
		else
			return totalResultSize / pageSize;
	}
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
	
	public void setOrderAutoPerformService(
			OrderAutoPerformService orderAutoPerformService) {
		this.orderAutoPerformService = orderAutoPerformService;
	}
}
