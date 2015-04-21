package com.lvmama.pet.web.mark.membershipcard;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;

import com.lvmama.comm.pet.po.mark.MarkMembershipCardCode;
import com.lvmama.comm.pet.service.mark.MarkMembershipCardCodeService;
import com.lvmama.comm.pet.service.mark.MarkMembershipCardService;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.utils.ZkMessage;

/**
 * 创建会员卡
 * @author Brian
 *
 */
public class AddMembershipCardAction extends com.lvmama.pet.web.BaseAction {
	private static final long serialVersionUID = -7700776744524993132L;
	private static final Pattern cardPrefixNumberPattern = Pattern.compile("^\\d{4}$");
	
	private MarkMembershipCardCodeService markMembershipCardCodeService;
	private MarkMembershipCardService markMembershipCardService;

	//批次号
	private String cardPrefixNumber;
	//数量
	private int number;
	private Set<String> cardCodeSet;
	
	/**
	 * 生成会员卡
	 */
	public void generate() {
		if (StringUtils.isEmpty(cardPrefixNumber) || !cardPrefixNumberPattern.matcher(cardPrefixNumber).matches()) {
			alert("请输入合法的批次代码(4位数字)");
			return;
		}
		if (number <= 0) {
			alert("请输入合法的数量");
			return;
		}
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("cardPrefixNumber", cardPrefixNumber);
		if (markMembershipCardService.count(parameters) > 0) {
			alert("批次" + cardPrefixNumber + "已存在，不能重复生成会员卡。");
			return;
		}
		
		cardCodeSet = new HashSet<String>(number);
		while (cardCodeSet.size() < number) {
			cardCodeSet.add(cardPrefixNumber + StringUtil.getRandomString(0,5));
		}
		markMembershipCardCodeService.insertByBatch(cardPrefixNumber,new Long(number), cardCodeSet,getSessionUserName());
		((Button) this.getComponent().getFellow("btn_download")).setDisabled(false);
		super.refreshParent("refresh");
		ZkMessage.showInfo("会员卡号已经生成，请下载!");
	}
	
	/**
	 * 下载会员卡
	 */
	public void download() {
		if (null == cardCodeSet || cardCodeSet.size() == 0) {
			alert("无会员卡信息需要导出!");
			return;
		}
		try {
			String destFileName = Constant.getTempDir() + "/membershipCardCode.xls";
			File file = new File(destFileName);
			//创建一个EXCEL  
	        Workbook wb = new HSSFWorkbook();
	        int mmccsLen = cardCodeSet.size();
	        int index = 0,sheetIndex=1,maxRow = 20000,rowIndex = 1,colIndex=1;
	        Sheet sheet = null;
	        for (String code:cardCodeSet) {
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
        		cell.setCellValue(code);
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
	

	//setter and getter
	public String getCardPrefixNumber() {
		return cardPrefixNumber;
	}

	public void setCardPrefixNumber(String cardPrefixNumber) {
		this.cardPrefixNumber = cardPrefixNumber;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public void setMarkMembershipCardCodeService(
			MarkMembershipCardCodeService markMembershipCardCodeService) {
		this.markMembershipCardCodeService = markMembershipCardCodeService;
	}

	public void setMarkMembershipCardService(
			MarkMembershipCardService markMembershipCardService) {
		this.markMembershipCardService = markMembershipCardService;
	}

	public Set<String> getCardCodeSet() {
		return cardCodeSet;
	}	
}
