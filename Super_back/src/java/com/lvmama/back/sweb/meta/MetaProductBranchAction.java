
package com.lvmama.back.sweb.meta;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.back.utils.StringUtil;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaProductTraffic;
import com.lvmama.comm.bee.po.prod.LineStationStation;
import com.lvmama.comm.bee.po.prod.ProdTraffic;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.bee.vo.MetaBranchRelateProdBranch;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.utils.CopyUtil;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

/**
 * 采购产品类别
 * @author yangbin
 *
 */
@Results({
	@Result(name = "input", location = "/WEB-INF/pages/back/meta/add_meta_product_branch.jsp"),
	@Result(name = "toProdProductAndBranch", location = "/WEB-INF/pages/back/meta/relate_prod_branch.jsp")
	})
public class MetaProductBranchAction extends MetaProductFormAction{

	public MetaProductBranchAction() {
		super("branch");
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4740418351515408577L;
	private List<MetaProductBranch> branchList;
	private Long metaBranchId;
//	private String valid;
	private MetaProductBranchService metaProductBranchService;
	private ProdTrainService prodTrainService;
	private MetaProductBranch branch=new MetaProductBranch();
	
	private List<MetaBranchRelateProdBranch> relativeProdProductAndBranchList;
	
	public List<MetaBranchRelateProdBranch> getRelativeProdProductAndBranchList() {
		return relativeProdProductAndBranchList;
	}
	public void setRelativeProdProductAndBranchList(
			List<MetaBranchRelateProdBranch> relativeProdProductAndBranchList) {
		this.relativeProdProductAndBranchList = relativeProdProductAndBranchList;
	}
	
	//@Action("/meta/addMetaProduct")
	public String addMetaProduct(){
		return "addMetaProduct";
	}
	
	public String addMetaProductBranch(){
		return "addMetaProductBranch";
	}

	@Override
	@Action("/meta/saveBranch")
	public void save() {
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(branch.getMetaProductId(),"采购产品信息不可以为空");
			if (StringUtil.hasIllegalCharacter(branch.getBranchName())){
				throw new IllegalArgumentException("类别名称不可包含'<','>','&'");
			}
			if(branch.getAdultQuantity()==null||branch.getAdultQuantity()<0){
				throw new IllegalArgumentException("成人数不可为空");				
			}
			if(branch.getChildQuantity()==null||branch.getChildQuantity()<0){
				throw new IllegalArgumentException("儿童数不可为空");				
			}
			result.put("hasNew", branch.getMetaBranchId()==null);
			if(branch.getMetaBranchId()!=null){
				MetaProductBranch mpbEntity=metaProductBranchService.getMetaBranch(branch.getMetaBranchId());
				if(mpbEntity==null){
					throw new Exception("修改的采购类别不存在");
				}
				
				branch=CopyUtil.copy(mpbEntity, branch, getRequest().getParameterNames(), "branch.", new CopyUtil.PropCallback() {					
					@Override
					public boolean allowField(String field) {
						return !"metaProductId".equals(field);//不更新采购产品的ID.
					}
				});
			}
			branch=metaProductBranchService.save(branch,getOperatorNameAndCheck());
			result.put("branch", conver());
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 转换
	 * @return
	 */
	private JSONObject conver(){
		return JSONObject.fromObject(branch);
	}

	@Override
	@Action("/meta/toEditBranch")
	public String toEdit() {
		doBefore();
		branchList=metaProductBranchService.selectBranchListByProductId(metaProductId);
		return goAfter();
	}
	
	@Action("/meta/getBranchJSON")
	public void getBranchDetail(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(metaBranchId,"类别不存在");
			branch=metaProductBranchService.getMetaBranch(metaBranchId);
			Assert.notNull(branch,"类别不存在");
			
			result.put("branch", conver());
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 现在不做该功能
	 * 修改类别状态
	 */
	//@Action("/meta/changeBranchValid")
	public void changeBranchValid(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(metaBranchId,"类别信息不存在");
			branch=metaProductBranchService.getMetaBranch(metaBranchId);
			Assert.notNull(branch,"类别不存在");
			
			branch.changeValid();
			if(!branch.hasValid()){//如果为关闭时判断是否有打包
				
			}
			metaProductBranchService.save(branch,getOperatorNameAndCheck());
			result.put("valid", branch.getValid());
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 采购类别查看关联的销售类别时调用
	 * @return
	 */
	@Action("/meta/getProdProductAndBranch")
	public String getProdProductAndBranch(){
		relativeProdProductAndBranchList = metaProductBranchService.selectProdProductAndProdBranchByMetaBranchId(metaBranchId);
		return "toProdProductAndBranch";
	}

	/**
	 * @return the branchList
	 */
	public List<MetaProductBranch> getBranchList() {
		return branchList;
	}
	
	public List<CodeItem> getBranchCodeSetList(){	
		if(metaProduct.isTrain()){
			return ProductUtil.getTrainBranchTypeList(true);
		}
		return CodeSet.getInstance().getCodeListAndBlank(ProductUtil.getBranchSetByType(metaProductType));
	}
	
	public List<CodeItem> getBranch_1CodeSetList(){		
		if(metaProduct.isTrain()){
			MetaProductTraffic traffic = (MetaProductTraffic)metaProductService.getMetaProduct(metaProduct.getMetaProductId(), Constant.PRODUCT_TYPE.TRAFFIC.name());
			List<LineStationStation> list= prodTrainService.selectStationStationByLineInfo(traffic.getLineInfoId());
			List<CodeItem> result = new ArrayList<CodeItem>();
			result.add(new CodeItem("","请选择"));
			for(LineStationStation lss:list){
				LineStationStation lss2=prodTrainService.getStationStationDetailById(lss.getStationStationId());
				result.add(new CodeItem(String.valueOf(lss2.getStationStationId()),lss2.getDepartureStation().getStationName()+"-"+lss2.getArrivalStation().getStationName()));
			}
			return result;
		}else{
			return CodeSet.getInstance().getCodeListAndBlank(ProductUtil.getBranchSetByType(metaProductType) + "_1");
		}
	}

	/**
	 * @param metaProductBranchService the metaProductBranchService to set
	 */
	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	
	/**
	 * @return the branch
	 */
	public MetaProductBranch getBranch() {
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(MetaProductBranch branch) {
		this.branch = branch;
	}

	/**
	 * @param metaBranchId the metaBranchId to set
	 */
	public void setMetaBranchId(Long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}
	public void setProdTrainService(ProdTrainService prodTrainService) {
		this.prodTrainService = prodTrainService;
	}
	
	
}
