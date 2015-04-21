package com.lvmama.pet.web.mark.membershipcard;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zkoss.zul.Filedownload;

import com.lvmama.comm.bee.po.distribution.DistributionTuanDestroyLog;
import com.lvmama.comm.pet.po.mark.MarkMembershipCardCode;
import com.lvmama.comm.pet.service.mark.MarkMembershipCardCodeService;
import com.lvmama.comm.pet.service.mark.MarkMembershipCardService;
import com.lvmama.comm.pet.vo.mark.MarkMembershipCardDetails;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.utils.ZkMessage;
import com.lvmama.pet.utils.ZkMsgCallBack;

/**
 * 查询会员卡
 * @author Brian
 *
 */
public class ListMembershipCardAction extends com.lvmama.pet.web.BaseAction {
	private static final long serialVersionUID = -5367638041381632064L;
	
	private MarkMembershipCardService markMembershipCardService;
	
	private MarkMembershipCardCodeService markMembershipCardCodeService;
	
	private List<MarkMembershipCardDetails> listMembershipCardDetails;
	
	//查询条件
	private Map<String,Object> searchConds = new HashMap<String,Object>();
	
	/**
	 * 根据查询内容查询结果
	 */
	public void loadDataList() {
		searchConds = initialPageInfoByMap(markMembershipCardService.count(searchConds), searchConds);
		if (null != searchConds.get("_startRow")) {
			searchConds.put("skipResults", searchConds.get("_startRow"));
		}
		if (null != searchConds.get("_endRow")) {
			searchConds.put("maxResults", searchConds.get("_endRow"));
		}
		listMembershipCardDetails = markMembershipCardService.query(searchConds);
	}
	
	public void download(Map<String,Object> parameters) {
		final Long cardId =(Long) parameters.get("cardId");
		if (null == cardId) {
			ZkMessage.showWarning("请选择会员卡批次!");
			return;
		}
		try {
			String destFileName = Constant.getTempDir() + "/membershipCardCode.xls";
			
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("cardId", cardId);
			
			List<MarkMembershipCardCode> mmccs = markMembershipCardCodeService.query(param);
			File file = new File(destFileName);
			
			//创建一个EXCEL  
	        Workbook wb = new HSSFWorkbook();
	        int mmccsLen = mmccs.size();
	        int index = 0,sheetIndex=1,maxRow = 20000,rowIndex = 1,colIndex=1;
	        Sheet sheet = null;
	        for (MarkMembershipCardCode mmcc:mmccs) {
	        	index++;
	        	if(index%maxRow==1){
	        		rowIndex=1;
	        		colIndex=1;
	        		sheet = wb.createSheet(index+"-"+((sheetIndex*maxRow>mmccsLen)?mmccsLen:sheetIndex*maxRow));
	        		sheetIndex++;
	        		Row row = sheet.createRow((short)rowIndex++);
	        		Cell cell = row.createCell(colIndex++);
	        		cell.setCellValue("会员卡号");
	        		cell = row.createCell(colIndex++);
	        		cell.setCellValue("数量");
	        	}
	        	colIndex=1;
	        	Row row = sheet.createRow((short)rowIndex++);
	        	Cell cell = row.createCell(colIndex++);
        		cell.setCellValue(mmcc.getCardCode());
        		cell = row.createCell(colIndex++);
        		cell.setCellValue("1");
			}
			FileOutputStream fos = new FileOutputStream(file);
	        wb.write(fos);
			if (file != null && file.exists()) {
				Filedownload.save(file, "application/vnd.ms-excel");
			} else {
				alert("下载失败");
				return;
			}
			alert("下载成功");
		} catch (Exception e) {
			alert(e.getMessage());
		}
	}
	
	/**
	 * 删除会员卡并释放优惠券
	 * @param parameters
	 */
	public void del(Map<String,Object> parameters) {
		final Long cardId =(Long) parameters.get("cardId");
		ZkMessage.showQuestion("您确信需要删除这个批次的会员卡吗?", new ZkMsgCallBack() {
			public void execute() {
				markMembershipCardService.delete(cardId,getSessionUserName());
				loadDataList();
				refreshComponent("refresh");
			}
		}, new ZkMsgCallBack() {
			public void execute() {
			}
		});
	}

	//setter and getter
	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}

	public List<MarkMembershipCardDetails> getListMembershipCardDetails() {
		return listMembershipCardDetails;
	}

	public void setMarkMembershipCardService(
			MarkMembershipCardService markMembershipCardService) {
		this.markMembershipCardService = markMembershipCardService;
	}	
}
