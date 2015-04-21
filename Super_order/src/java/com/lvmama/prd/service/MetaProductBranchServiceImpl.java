/**
 * 
 */
package com.lvmama.prd.service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.vo.EbkDayStockDetail;
import com.lvmama.comm.bee.vo.MetaBranchRelateProdBranch;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.MetaProductBranchDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;

/**
 * @author yangbin
 *
 */
public class MetaProductBranchServiceImpl implements MetaProductBranchService{

	private MetaProductBranchDAO metaProductBranchDAO;
	private MetaTimePriceDAO metaTimePriceDAO;
	private ComLogService comLogService;
	private ComMessageService comMessageService;
	
	private MetaProductService metaProductService;
 	private PermUserService permUserService;
	
	@Override
	public MetaProductBranch getMetaBranch(Long pk) {
		return metaProductBranchDAO.selectBrachByPrimaryKey(pk);
	}

	/**
	 * @param metaProductBranchDAO the metaProductBranchDAO to set
	 */
	public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
		this.metaProductBranchDAO = metaProductBranchDAO;
	}

	@Override
	public List<MetaProductBranch> selectBranchListByProductId(
			Long metaProductId) {
		return metaProductBranchDAO.selectBranchListByProductId(metaProductId);
	}
	
	@Override
	public List<MetaProductBranch> selectBranchListByProductId(
			Long metaProductId,String valid) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("metaProductId", metaProductId);
		map.put("valid", "Y");
		return metaProductBranchDAO.selectBranchListByParam(map);
	}

	@Override
	public MetaProductBranch save(MetaProductBranch branch,String operatorName) {
		Assert.notNull(branch);
		String logType;
		String logName;
		String logContent;
		if(branch.getMetaBranchId()==null){
			Long pk=metaProductBranchDAO.insert(branch);
			branch.setMetaBranchId(pk);
			logType=Constant.COM_LOG_META_EVENT.insertMetaBranch.name();
			logName="添加类别信息";
			logContent = getLogContent(branch, "INSERT");
		}else{
			logContent = getLogContent(branch, "UPDATE");
			metaProductBranchDAO.updateByPrimaryKeySelective(branch);
			logType=Constant.COM_LOG_META_EVENT.updateMetaBranch.name();
			logName="更新类别信息";
		}
		if(logContent != null && logContent.trim().length() > 0){
			comLogService.insert("META_PRODUCT_BRANCH", branch.getMetaProductId(), branch.getMetaBranchId(), 
					operatorName, logType, logName, logContent, "META_PRODUCT");
		}
		return branch;
	}
	
	@Override
	public void resetStock() {
		metaTimePriceDAO.resetStock();
	}

	/**
	 * @param comLogService the comLogService to set
	 */
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	/**用于记录日志时，组织日志的内容
	 * @author zhangxin
	 * @param branch 类别对象
	 * @param operateType 操作类型("INSERT":表示添加;   "UPDATE":表示更新;  "DELETE":表示删除)
	 * @return
	 */
	public String getLogContent(MetaProductBranch branch,String operateType){
		StringBuffer sb = new StringBuffer("");
		if(operateType.toUpperCase().equals("INSERT")){
			sb.append(MessageFormat.format("添加了类别名称为{0}的类别信息.", branch.getBranchName()));
		}else if(operateType.toUpperCase().equals("UPDATE")){
			String updateContent = getUpdateLogStr(branch);
			if(updateContent != null && updateContent.trim().length() > 0){
				sb.append(updateContent);
			}
		}else if(operateType.toUpperCase().equals("DELETE")){
			sb.append(MessageFormat.format("删除了类别名称为{0}的类别信息.", branch.getBranchName()));
		}else{}
		return sb.toString();
	}

	/**组织更新类别时的日志内容
	 * @param preUpdateBranch 预更新的类别对象
	 * @return
	 */
	public String getUpdateLogStr(MetaProductBranch preUpdateBranch) {
		StringBuffer strBuf = new StringBuffer("");
		MetaProductBranch oldMetaProductBranch = getMetaBranch(preUpdateBranch.getMetaBranchId());
		
		if (!LogViewUtil.logIsEmptyStr(preUpdateBranch.getBranchType()).equals(LogViewUtil.logIsEmptyStr(oldMetaProductBranch.getBranchType()))) {
			strBuf.append(LogViewUtil.logEditStr("类别类型", oldMetaProductBranch.getBranchType(), preUpdateBranch.getBranchType()));
		}
		if (!LogViewUtil.logIsEmptyStr(preUpdateBranch.getBranchName()).equals(LogViewUtil.logIsEmptyStr(oldMetaProductBranch.getBranchName()))) {
			strBuf.append(LogViewUtil.logEditStr("类别名称", oldMetaProductBranch.getBranchName(), preUpdateBranch.getBranchName()));
		}
		if (!LogViewUtil.logIsEmptyStr(preUpdateBranch.getAdditional()).equals(LogViewUtil.logIsEmptyStr(oldMetaProductBranch.getAdditional()))) {
			strBuf.append(LogViewUtil.logEditStr("是否附加", "true".equals(oldMetaProductBranch.getAdditional()) ? "是" : "否", "true".equals(preUpdateBranch.getAdditional()) ? "是" : "否"));
		}
		if (!LogViewUtil.logIsEmptyStr(preUpdateBranch.getAdultQuantity() + "").equals(LogViewUtil.logIsEmptyStr(oldMetaProductBranch.getAdultQuantity() + ""))
				|| !LogViewUtil.logIsEmptyStr(preUpdateBranch.getChildQuantity() + "").equals(LogViewUtil.logIsEmptyStr(oldMetaProductBranch.getChildQuantity() + ""))) {
			strBuf.append(LogViewUtil.logEditStr("成人、儿童数量", oldMetaProductBranch.getAdultQuantity() + "," + oldMetaProductBranch.getChildQuantity(), preUpdateBranch.getAdultQuantity() + ","
					+ preUpdateBranch.getChildQuantity()));
		}
		if (!LogViewUtil.logIsEmptyStr(preUpdateBranch.getSendFax()).equals(LogViewUtil.logIsEmptyStr(oldMetaProductBranch.getSendFax()))) { 
			strBuf.append(LogViewUtil.logEditStr("是否需要单独创建传真", "true".equals(oldMetaProductBranch.getSendFax()) ? "是" : "否", "true".equals(preUpdateBranch.getSendFax()) ? "是" : "否")); 
		}
		if (!LogViewUtil.logIsEmptyStr(preUpdateBranch.getVirtual()).equals(LogViewUtil.logIsEmptyStr(oldMetaProductBranch.getVirtual()))) { 
			strBuf.append(LogViewUtil.logEditStr("是否虚拟采购产品", "true".equals(oldMetaProductBranch.getVirtual()) ? "是" : "否", "true".equals(preUpdateBranch.getVirtual()) ? "是" : "否")); 
		}
		if (!LogViewUtil.logIsEmptyStr(preUpdateBranch.getDescription()).equals(LogViewUtil.logIsEmptyStr(oldMetaProductBranch.getDescription()))) {
			strBuf.append(LogViewUtil.logEditStr("票种描述", oldMetaProductBranch.getDescription(), preUpdateBranch.getDescription()));
		}
		if (!LogViewUtil.logIsEmptyStr(preUpdateBranch.getTotalDecrease()).equals(LogViewUtil.logIsEmptyStr(oldMetaProductBranch.getTotalDecrease()))) {
			strBuf.append(LogViewUtil.logEditStr("是否使用库存", "true".equals(oldMetaProductBranch.getTotalDecrease()) ? "是" : "否", "true".equals(preUpdateBranch.getTotalDecrease()) ? "是" : "否"));
		}
		
		if (!LogViewUtil.logIsEmptyStr(String.valueOf((preUpdateBranch.getTotalStock() == null)?"":(preUpdateBranch.getTotalStock().longValue())))
				.equals(LogViewUtil.logIsEmptyStr(String.valueOf((oldMetaProductBranch.getTotalStock() == null)?"":(oldMetaProductBranch.getTotalStock().longValue()))))) {
			strBuf.append(LogViewUtil.logEditStr("总库存", 
					String.valueOf((oldMetaProductBranch.getTotalStock() == null)?"":(oldMetaProductBranch.getTotalStock().longValue())), 
					String.valueOf((preUpdateBranch.getTotalStock() == null)?"":(preUpdateBranch.getTotalStock().longValue()))));
		}
		
		if (!LogViewUtil.logIsEmptyStr(preUpdateBranch.getProductIdSupplier()).equals(LogViewUtil.logIsEmptyStr(oldMetaProductBranch.getProductIdSupplier()))) {
			strBuf.append(LogViewUtil.logEditStr("代理产品编号", oldMetaProductBranch.getProductIdSupplier(),preUpdateBranch.getProductIdSupplier()));
		}
		if (!LogViewUtil.logIsEmptyStr(preUpdateBranch.getProductTypeSupplier()).equals(LogViewUtil.logIsEmptyStr(oldMetaProductBranch.getProductTypeSupplier()))) {
			strBuf.append(LogViewUtil.logEditStr("代理产品类型", oldMetaProductBranch.getProductTypeSupplier(),preUpdateBranch.getProductTypeSupplier()));
		}
		return strBuf.toString();
	}

	@Override
	public List<MetaBranchRelateProdBranch> selectProdProductAndProdBranchByMetaBranchId(
			Long metaBranchId) {
		return this.metaProductBranchDAO.selectProdProductAndProdBranchByMetaBranchId(metaBranchId);
	}

	@Override
	public TimePrice getTimePrice(Long metaBranchId, Date specDate) {
		return metaTimePriceDAO.getMetaTimePriceByIdAndDate(metaBranchId, specDate);
	}
	/**
	 * @param metaTimePriceDAO the metaTimePriceDAO to set
	 */
	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}
	
	public Long getEbkMetaBranchCount(Map<String, Object> params){
		return metaProductBranchDAO.getEbkUserMetaBranchCount(params);
	}
	
	public List<MetaProductBranch> getEbkMetaBranch(Map<String, Object> params){
		return metaProductBranchDAO.getEbkUserMetaBranch(params);
	}
	public List<EbkDayStockDetail> getEbkDayStockDetail(Map<String, Object> params){
		return metaProductBranchDAO.getEbkDayStockDetail(params);
	}
	public Long getEbkDayStockDetailPageCount(Map<String, Object> params){
		return metaProductBranchDAO.getEbkDayStockDetailPageCount(params);
	}
	public List<MetaProductBranch> getEbkMetaBranchParam(Map<String, Object> params){
		List<Long> metaBranchIdList = (List<Long>)params.get("metaBranchIds");
		if(metaBranchIdList == null || metaBranchIdList.size() == 0){
			return null;
		}
		return metaProductBranchDAO.getEbkMetaBranchParam(params);
	}
	public List<MetaProductBranch> getEbkMetaBranchByProductId(Long metaProductId){
		return metaProductBranchDAO.getEbkMetaBranchByProductId(metaProductId);
	}

	public List<MetaProductBranch> getMetaBranch(Map<String, Object> params){
		return metaProductBranchDAO.getMetaBranch(params);
	}

	/**
	 * ebooking变价申请后向super后台发送系统消息提示
	 */
	@Override
	public void changeHousepriceSendMessage(Long metaProductId, String content,
			String sender) {
		MetaProduct metaProduct = metaProductService
				.getMetaProduct(metaProductId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("managerId", metaProduct.getManagerId());
		PermUser permU = permUserService.getPermUserByParams(params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("departmentId", permU.getDepartmentId());
		map.put("skipResults", 1L);
		map.put("maxResults", 1024L);
		List<PermUser> permUserList = permUserService.queryPermUserByParam(map);
		for (PermUser permUser : permUserList) {
			this.insertMsg(permUser.getUserName(), content, sender);
 		}
	}
	
	@Override
	public List<MetaProductBranch> selectMetaProductBranchBySupplierType(Long supplierId, String productTypeSupplier, String productIdSupplier) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", supplierId);
		params.put("productTypeSupplier", productTypeSupplier);
		params.put("productIdSupplier", productIdSupplier);
		return metaProductBranchDAO.selectMetaProductBranchBySupplierType(params);
	}

	@Override
	public List<MetaProductBranch> selectMetaProductBranchBySupplierId(Long supplierId) {
		return metaProductBranchDAO.selectMetaProductBranchBySupplierId(supplierId);
	}
	
	@Override
	public List<String> selectSupplierTypeBySupplierId(Long supplierId) {
		return metaProductBranchDAO.selectSupplierTypeBySupplierId(supplierId);
	}
	
	
	private void insertMsg(String receiver, String content, String sender) {
		ComMessage msg = new ComMessage();
		msg.setCreateTime(new Date());
		msg.setContent(content);
		msg.setReceiver(receiver);
		msg.setSender(sender);
		msg.setStatus("CREATE");
		try {
			comMessageService.insertComMessage(msg);
		} catch (Exception ex) {
			System.out.println("消息写入时异常:" + ex);
		}
	}

	
	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	@Override
	public List<MetaProductBranch> getMetaProductBranchByProdBranchId(
			Long prodBranchId) {
		return metaProductBranchDAO.getMetaProductBranchByProdBranchId(prodBranchId);
	}
	
	@Override
	public void deleteMetaProductBranch(Long metaBranchId,String operatorName) {
		MetaProductBranch branch=metaProductBranchDAO.selectBrachByPrimaryKey(metaBranchId);
		if(null!=branch){
			String logContent = getLogContent(branch, "DELETE");
			branch.setValid("N");
			metaProductBranchDAO.updateByPrimaryKeySelective(branch);
			comLogService.insert("META_PRODUCT_BRANCH", branch.getMetaProductId(), branch.getMetaBranchId(), operatorName, Constant.COM_LOG_META_EVENT.deleteMetaBranch.name(), "删除类别信息", logContent, "META_PRODUCT");
		}
	}
	


	@Override
	public Long getProductIdByMetaBranchId(Long metaBranchId) {
		return metaProductBranchDAO.getProductIdByMetaBranchId(metaBranchId);
	}

	@Override
	public ResultHandleT<String> getMetaProductBranchValid(Long metaBranchId,
			String productType ) {
		ResultHandleT<String> handle = new ResultHandleT<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("metaBranchId", metaBranchId);
		map.put("productType", productType);
		List<String> result = metaProductBranchDAO.getMetaProductBranchValid(map);
		if (null != result &&result.size() > 0) {
			handle.setReturnContent(result.get(0));
			return handle;
		}
		return handle;
	}
	
}
