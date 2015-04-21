package com.lvmama.pet.sweb.lvmamacard;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;


import com.lvmama.comm.pet.po.department.Department;
import com.lvmama.comm.pet.po.lvmamacard.LvmamaStoredCard;
import com.lvmama.comm.pet.po.lvmamacard.StoredCardOut;
import com.lvmama.comm.pet.po.lvmamacard.StoredCardOutDetails;
import com.lvmama.comm.pet.service.department.DepartmentService;
import com.lvmama.comm.pet.service.lvmamacard.LvmamacardService;
import com.lvmama.comm.utils.FileUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.lvmamacard.LvmamaCardUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PageElementModel;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 储值卡出库
 * 
 * @author yifan
 * 
 */
@Results({
		@Result(name = "success", location = "/WEB-INF/pages/back/lvmamacard/outStorageCardPre.jsp"),
		@Result(name = "outStorageQuery", location = "/WEB-INF/pages/back/lvmamacard/outStorageCardQuery.jsp"),
		@Result(name = "outStoragePrint", location = "/WEB-INF/pages/back/lvmamacard/outStorageCardPrint.jsp"),
		@Result(name = "outStorageUpdPre", location = "/WEB-INF/pages/back/lvmamacard/outStorageCardUpd.jsp"),
		@Result(name = "outStorageDiv", location = "/WEB-INF/pages/back/lvmamacard/outStorageCardDiv.jsp"),
		@Result(name = "outStorageAddPre", location = "/WEB-INF/pages/back/lvmamacard/outStorageCardAdd.jsp") })
public class OutStorageCardAction extends CardBaseAction implements ModelDriven<StoredCardOut>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2947638498767996350L;
	private LvmamacardService lvmamacardService;
	private DepartmentService departmentService;
	private StoredCardOut storedCardOut = new StoredCardOut();
	private StoredCardOut storedOutSum;
	private List<PageElementModel> amountModel;
	private List<StoredCardOut> outList;
	private List<StoredCardOutDetails> detailsList;
	private Map<String, Object> param;
	private Map<String, Object> cardStatus;
	private List<Department> departments;
	private String cardNo;
	private String json;
	private LvmamaStoredCard storedCard;

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	/**
	 * 储值卡出库初始页
	 */
	@Action("/outStorage/outStoragePre")
	public String execute() throws Exception {
		this.setMapParam();
		this.initPreTagsResPage();
		this.storedOutSum = this.lvmamacardService.queryOutStoregeSum(param);
		outList = this.lvmamacardService.queryByParamForOutStorege(param);
		this.setDetailsData(this.outList);
		return SUCCESS;
	}
	
	private void setDetailsData(List<StoredCardOut> outList){
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < outList.size(); i++) {
			map.clear();
			map.put("outCode", outList.get(i).getOutCode());
			outList.get(i).setDetails(this.lvmamacardService.queryByParamForOutStoregeDetails(map));
		}
	}
	
	/**
	 * 储值卡出库新增初始页
	 * 
	 * @return
	 */
	@Action("/outStorage/outStorageAddPre")
	public String addPreOutStorage() {
		this.amountModel = Constant.CARD_AMOUNT.getList();
		this.departments = this.departmentService.queryByParamDepartment(null);
		json = JSONArray.fromObject(this.departments).toString();
		return "outStorageAddPre";
	}
	
	/**
	 * 储值卡出库修改初始页
	 * @return
	 */
	@Action("/outStorage/outStorageUpdPre")
	public String updPreOutStorage() {
		this.amountModel = Constant.CARD_AMOUNT.getList();
		param = new HashMap<String, Object>();
		param.put("outCode", this.storedCardOut.getOutCode());
		this.storedCardOut = this.lvmamacardService.queryByParamForOutStorege(param).get(0);
		this.detailsList = this.lvmamacardService.queryByParamForOutStoregeDetails(param);
		this.departments = this.departmentService.queryByParamDepartment(null);
		json = JSONArray.fromObject(this.departments).toString();
		return "outStorageUpdPre";
	}
	
	/**
	 * 储值卡出库查看
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action("/outStorage/outStorageQuery")
	public String queryOutStorage() {
		param = new HashMap<String, Object>();
		param.put("outCode", this.storedCardOut.getOutCode());
		this.storedCardOut = this.lvmamacardService.queryByParamForOutStorege(param).get(0);
		this.detailsList = this.lvmamacardService.queryByParamForOutStoregeDetails(param);
		if(cardNo != null)
			param.put("cardNo", this.cardNo);
		String _status = "";
		if(this.storedCard !=null && this.storedCard.getStatus()!=null&&!this.storedCard.getStatus().equals("")){
			param.put("status", this.storedCard.getStatus());
			_status = this.storedCard.getStatus();
		}
		pagination = initPage();
		pagination.setCurrentPage(pagination.getCurrentPage());
		pagination.setTotalResultSize(lvmamacardService
				.countByParamForLvmamaStoredCard(param));
		pagination.setPageSize(10L);
		if (pagination.getTotalResultSize() > 0) {
			param.put("start", pagination.getStartRows());
			param.put("end", pagination.getEndRows());
			pagination.setAllItems(lvmamacardService.queryByParamForLvmamaStoredCard(param));
		}
		pagination.buildUrl(getRequest());
		this.departments = this.departmentService.queryByParamDepartment(null);
		json = JSONArray.fromObject(this.departments).toString();
		cardStatus = new HashMap<String, Object>();
		this.storedCard = this.lvmamacardService.queryOutStoredCardStatusByOutCode(param).get(0);
		this.storedCard.setStatus(_status);
		return "outStorageQuery";
	}
	
	/**
	 * 出库打印页
	 * @return
	 */
	@Action("/outStorage/outStoragePrint")
	public String printOutStorage() {
		param = new HashMap<String, Object>();
		param.put("outCode", this.storedCardOut.getOutCode());
		this.storedCardOut = this.lvmamacardService.queryByParamForOutStorege(param).get(0);
		this.detailsList = this.lvmamacardService.queryByParamForOutStoregeDetails(param);
		this.departments = this.departmentService.queryByParamDepartment(null);
		json = JSONArray.fromObject(this.departments).toString();
		return "outStoragePrint";
	}
	
	/**
	 * 储值卡确认层
	 * @return
	 */
	@Action("/outStorage/showDiv")
	public String showDivOutStatorage() {
		return "outStorageDiv";
	}
	
	/**
	 * 储值卡确认
	 * @return
	 * @throws Exception
	 */
	@Action("/outStorage/outStorageConfirm")
	public String confirmOutStatorage() throws Exception{
		param = new HashMap<String, Object>();
		param.put("outCode", this.storedCardOut.getOutCode());
		detailsList = this.lvmamacardService.queryByParamForOutStoregeDetails(param);
		Long _count;
		for (int i = 0; i < detailsList.size(); i++) {
			param.clear();
			param.put("amount", detailsList.get(i).getOutDetailsAmount()*100);
			param.put("status","INITIALIZATION");
			param.put("stockStatus","INTO_STOCK");
			param.put("type", 1);
			_count = this.lvmamacardService.countByParamForLvmamaStoredCard(param);
			if(_count < detailsList.get(i).getOutDetailsCount()){
				this.outputToClient(detailsList.get(i).getOutDetailsAmount()+"面值缺少"+(detailsList.get(i).getOutDetailsCount()-_count)+"张");
				return null;
			}
		}
		Date date = new Date();
		
		List<LvmamaStoredCard> list = new ArrayList<LvmamaStoredCard>();
		for (int i = 0; i < detailsList.size(); i++) {
			param.clear();
			param.put("amount", detailsList.get(i).getOutDetailsAmount()*100);
			param.put("count", detailsList.get(i).getOutDetailsCount());
			list = this.lvmamacardService.queryOutStoredBeginNoAndEndNo(param);
			detailsList.get(i).setCardNoBegin(list.get(0).getCardNo());
			if(detailsList.get(i).getOutDetailsCount()==1){
				detailsList.get(i).setCardNoEnd(list.get(0).getCardNo());
			}else{
				detailsList.get(i).setCardNoEnd(list.get(1).getCardNo());
			}
			this.lvmamacardService.updateOutStoregeDetails(detailsList.get(i));
			param.put("status","NOTUSED");
			param.put("stockStatus","OUT_STOCK");
			param.put("outCode", this.storedCardOut.getOutCode());
			this.lvmamacardService.updateOutStoredCard(param);
		}
		storedCardOut.setOutDate(date);
		this.lvmamacardService.updateStoredCardOutForOutStorege(storedCardOut);
		String _outCode = this.storedCardOut.getOutCode();
		String msg = this.storedCardOut.getOutStatus()==1?"出库未付款":"出库已付款";
		Integer object_id= this.lvmamacardService.queryByParamForOutStorege(param).get(0).getOutId();
		super.comLogService.insert(LvmamaCardUtils.STORED_CARD_OUT, null, object_id.longValue(), super.getSessionUser().getUserName(), Constant.COM_LOG_OBJECT_TYPE.OUTSTOREDCARD.getCode(), "出库单确认", "出库单确认"+_outCode+"为:"+msg, null);
		this.outputToClient("true");
		return null;
	}
	
	/**
	 * 出库单付款
	 * @return
	 * @throws Exception
	 */
	@Action("/outStorage/outStoragePay")
	public String payOutStatorage() throws Exception{
		String _outCode = this.storedCardOut.getOutCode();
		this.lvmamacardService.updateStoredCardOutForOutStorege(storedCardOut);
		param  = new HashMap<String, Object>();
		param.put("outCode",_outCode);
		Integer object_id= this.lvmamacardService.queryByParamForOutStorege(param).get(0).getOutId();
		super.comLogService.insert(LvmamaCardUtils.STORED_CARD_OUT, null, object_id.longValue(), super.getSessionUser().getUserName(), Constant.COM_LOG_OBJECT_TYPE.OUTSTOREDCARD.getCode(), "出库单付款", "出库单付款"+_outCode, null);
		this.outputToClient("true");
		return null;
	}
	
	/**
	 * 出库单删除
	 * @return
	 * @throws Exception
	 */
	@Action("/outStorage/outStorageDelete")
	public String deleteOutStatorage() throws Exception{
		String _outCode = this.storedCardOut.getOutCode();
		this.lvmamacardService.deleteOutStorege(storedCardOut);
		this.lvmamacardService.deleteOutStoregeDetails(this.storedCardOut.getOutCode());	
		super.comLogService.insert(LvmamaCardUtils.STORED_CARD_OUT, null, null, super.getSessionUser().getUserName(), Constant.COM_LOG_OBJECT_TYPE.OUTSTOREDCARD.getCode(), "出库单删除", "出库单删除"+_outCode, null);
		this.outputToClient("true");
		return null; 
	}
	
	/**
	 * 储值卡出库新增
	 * 
	 * @return
	 */
	@Action("/outStorage/outStorageAdd")
	public String addOutStorage() throws Exception{
		String _outCode = this.generalOutCode();
		this.storedCardOut.setOutCode(_outCode);
		this.lvmamacardService.insertStoredCardOutForOutStorege(storedCardOut);
		detailsList = this.storedCardOut.getDetails();
		for (int i = 0; i < detailsList.size(); i++) {
			detailsList.get(i).setOutCode(_outCode);
		}
		this.lvmamacardService.insertOutStoregeDetails(detailsList);
		param = new HashMap<String, Object>();
		param.put("outCode", _outCode);
		Integer object_id= this.lvmamacardService.queryByParamForOutStorege(param).get(0).getOutId();
		super.comLogService.insert(LvmamaCardUtils.STORED_CARD_OUT, null, object_id.longValue(), super.getSessionUser().getUserName(), Constant.COM_LOG_OBJECT_TYPE.OUTSTOREDCARD.getCode(), "出库单新增", "出库单新增"+_outCode, null);
		this.outputToClient("true");
		return null;
	}
	
	/**
	 * 储值卡出库修改
	 * @return
	 * @throws Exception
	 */
	@Action("/outStorage/outStorageUpd")
	public String updOutStorage() throws Exception{
		String _outCode = this.storedCardOut.getOutCode();
		this.lvmamacardService.updateStoredCardOutForOutStorege(storedCardOut);
		this.lvmamacardService.deleteOutStoregeDetails(this.storedCardOut.getOutCode());
		detailsList = this.storedCardOut.getDetails();
		for (int i = 0; i < detailsList.size(); i++) {
			detailsList.get(i).setOutCode(this.storedCardOut.getOutCode());
		}
		this.lvmamacardService.insertOutStoregeDetails(detailsList);
		param = new HashMap<String, Object>();
		param.put("outCode",_outCode);
		Integer object_id= this.lvmamacardService.queryByParamForOutStorege(param).get(0).getOutId();
		super.comLogService.insert(LvmamaCardUtils.STORED_CARD_OUT, null, object_id.longValue(), super.getSessionUser().getUserName(), Constant.COM_LOG_OBJECT_TYPE.OUTSTOREDCARD.getCode(), "出库单修改", "出库单修改"+_outCode, null);
		this.outputToClient("true");
		return null;
	}
	
	@Action("/outStorage/outexcel")
	public String outexcel(){
		this.param = new HashMap<String, Object>();
		param.put("outStatus", this.storedCardOut.getOutStatus());
		List<StoredCardOut> list = this.lvmamacardService.queryOutStoregeExcel(param);
		String excelTemplate = LvmamaCardUtils.OUTSTORAGECARDTEMP;
		if (null != list&&list.size()>=0) {
			 //删除缓存文件
			 deteleTmepFile();
			 Map<String, Object> beans = new HashMap<String, Object>();
			 beans.put("list",list);
			 super.writeExcelByjXls(beans,excelTemplate,ResourceUtil.getResourceFile(LvmamaCardUtils.OUTSTORAGECARDTEMP1).getAbsolutePath());
			//输出文件
			 String outfile= ResourceUtil.getResourceFile(LvmamaCardUtils.OUTSTORAGECARDTEMP1).getAbsolutePath();
			 super.writeAttachment(outfile, "lvmamacard");
		}
		return null;
	}
	
	private void deteleTmepFile(){
		try {
			FileUtil.deleteDirectory(ResourceUtil.getResourceFile(LvmamaCardUtils.LVMAMACARDINEXCELFILETEMPDIR).getAbsolutePath());
		} catch (Exception e) {
			this.log.info("instorageCardAction 出库导出，缓存文件删除，出现异常！");
		}
	}
	
	/**
	 * 生成出库单号
	 * @return
	 */
	private synchronized String generalOutCode() {
	    String code= lvmamacardService.getOutCodeForOutStorege();
 		DecimalFormat decimalformat=new DecimalFormat("00000000");
		  if(code!=null){
			 code="S"+decimalformat.format(Integer.valueOf(code.substring(1, code.length()))+1);
		 }else {
			 code="S"+"00000001" ;
		 }
  	   return code;
	}
	
	private void initPreTagsResPage(){
        pagination=initPage();
        pagination.setPageSize(10);
        pagination.setTotalResultSize(this.lvmamacardService.countByParamForOutStorege(param));
        if(pagination.getTotalResultSize()>0){
            param.put("startRows", pagination.getStartRows());
            param.put("endRows", pagination.getEndRows());
        }
        pagination.buildUrl(getRequest());
    }
	
	private void setMapParam(){
		param = new HashMap<String, Object>();
		if(storedCardOut != null){
			param.put("outCode", this.storedCardOut.getOutCode());
			param.put("salePerson", this.storedCardOut.getSalePerson());
			if(storedCardOut.getOutDate() != null){
				param.put("outDate", this.storedCardOut.getOutDate());
			}
			
			param.put("outStatus", this.storedCardOut.getOutStatus());
			param.put("cardNo", this.cardNo);
		}
	}
	
	public List<PageElementModel> getAmountModel() {
		return amountModel;
	}

	public void setAmountModel(List<PageElementModel> amountModel) {
		this.amountModel = amountModel;
	}

	public LvmamacardService getLvmamacardService() {
		return lvmamacardService;
	}

	public void setLvmamacardService(LvmamacardService lvmamacardService) {
		this.lvmamacardService = lvmamacardService;
	}

	public StoredCardOut getStoredCardOut() {
		return storedCardOut;
	}

	public void setStoredCardOut(StoredCardOut storedCardOut) {
		this.storedCardOut = storedCardOut;
	}

	public List<StoredCardOut> getOutList() {
		return outList;
	}

	public void setOutList(List<StoredCardOut> outList) {
		this.outList = outList;
	}

	public List<StoredCardOutDetails> getDetailsList() {
		return detailsList;
	}

	public void setDetailsList(List<StoredCardOutDetails> detailsList) {
		this.detailsList = detailsList;
	}

	@Override
	public StoredCardOut getModel() {
		return this.storedCardOut;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public LvmamaStoredCard getStoredCard() {
		return storedCard;
	}

	public void setStoredCard(LvmamaStoredCard storedCard) {
		this.storedCard = storedCard;
	}


	public Map<String, Object> getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(Map<String, Object> cardStatus) {
		this.cardStatus = cardStatus;
	}

	public StoredCardOut getStoredOutSum() {
		return storedOutSum;
	}

	public void setStoredOutSum(StoredCardOut storedOutSum) {
		this.storedOutSum = storedOutSum;
	}
	
}
