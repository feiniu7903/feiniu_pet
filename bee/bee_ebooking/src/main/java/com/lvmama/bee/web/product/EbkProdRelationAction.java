package com.lvmama.bee.web.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ebooking.EbkProdRelation;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.ebooking.EbkProdRelationService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;
@Results({ @Result(name = "query", location = "/WEB-INF/pages/ebooking/product/ebkProdRelation.jsp")
})
public class EbkProdRelationAction extends BaseEbkProductAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final static Logger LOG = Logger.getLogger(EbkProdRelationAction.class);
    
    @Autowired 
    private EbkProdRelationService ebkProdRelationService;
    @Autowired 
    private ProdProductService prodProductService;
    @Autowired
    private ProdProductBranchService prodProductBranchService;
    
    private List<EbkProdRelation> relations = new ArrayList<EbkProdRelation>();
    private EbkProdRelation ebkProdRelation;
    
    private String search;
    private String productType;
    @Action(value="/product/relation/query")
    public String query(){
        String result = super.isSupplierEbkProd();
        if(null!=result){
            return result;
        }
        EbkProdRelation ebkProdRelation = new EbkProdRelation();
        ebkProdRelation.setEbkProductId(super.getEbkProdProductId());
        relations = ebkProdRelationService.findListByTerm(ebkProdRelation);
        return "query";
    }
    @Action(value="/product/relation/addRelation")
    public void addRelation(){
        if(!super.isSupplierEbkProductJson()){
            return;
        }
        if(!valid()){
            return;
        }
        ProdProductBranch branch=prodProductBranchService.selectProdProductBranchByPK(ebkProdRelation.getRelateProdBranchId());
        JSONObject json=new JSONObject();
        if(null==branch){
            json.put("success", Boolean.FALSE);
            json.put("message", -3002);
            JSONOutput.writeJSON(getResponse(), json);
            return ;
        }

        ebkProdRelation.setRelateProductId(branch.getProductId());
        ebkProdRelation.setRelateProdBranchName(branch.getBranchName());
        Integer count = ebkProdRelationService.countEbkProdRelationDOByExample(ebkProdRelation);
        if(count>0){
            json.put("success", Boolean.FALSE);
            json.put("message", -3004);
            JSONOutput.writeJSON(getResponse(), json);
            return ;
        }
        try{
            if (Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equals(ebkProdRelation.getRelateProductType())) {
                ebkProdRelation.setSaleNumType(Constant.SALE_NUMBER_TYPE.ANY.name());
            } else {
                ebkProdRelation.setSaleNumType(Constant.SALE_NUMBER_TYPE.OPT.name());
            }
            ebkProdRelationService.insertEbkProdRelationDO(ebkProdRelation);
            comLogService.insert("EBK_PROD_RELATION", super.ebkProdProductId, ebkProdRelation.getRelationId(), super.getSessionUserName(), "addEbkProductRelation", "增加关联销售产品", "增加关联销售产品：关联销售产品编号"+ebkProdRelation.getRelateProductId()+" 销售产品类别编号"+ebkProdRelation.getRelateProdBranchId(), "EBK_PROD_PRODUCT");
            json.put("success", Boolean.TRUE);
        }catch(Exception e){
            LOG.error("add ekb product relation out error:\r\n"+e.getMessage());
            json.put("success", Boolean.FALSE);
            json.put("message", -500);
        }
        JSONOutput.writeJSON(getResponse(), json);
    }
    @Action(value="/product/relation/deleteRelation")
    public void deleteRelation(){
        if(!super.isSupplierEbkProductJson()){
            return;
        }
        if(!valid()){
            return;
        }
        JSONObject json=new JSONObject();
        if(null==ebkProdRelation.getRelationId()){
            json.put("success", Boolean.FALSE);
            json.put("message",-3003);
            JSONOutput.writeJSON(getResponse(), json);
            return;
        }
        try{
            ebkProdRelation = ebkProdRelationService.findEbkProdRelationDOByPrimaryKey(ebkProdRelation.getRelationId());
            ebkProdRelationService.deleteEbkProdRelationDOByPrimaryKey(ebkProdRelation.getRelationId());
            comLogService.insert("EBK_PROD_RELATION", super.ebkProdProductId, ebkProdRelation.getRelationId(), super.getSessionUserName(), "deleteEbkProductRelation", "删除关联销售产品", "删除关联销售产品：关联销售产品编号"+ebkProdRelation.getRelateProductId()+" 销售产品类别编号"+ebkProdRelation.getRelateProdBranchId(), "EBK_PROD_PRODUCT");
            json.put("success", Boolean.TRUE);
        }catch(Exception e){
            LOG.error("delete ekb product relation out error:\r\n"+e.getMessage());
            json.put("success", Boolean.FALSE);
            json.put("message", -500);
        }
        JSONOutput.writeJSON(getResponse(), json);
    }
    
    @Action("/product/relation/searchProductJSON")
    public void searchProduct(){
        Map<String, Object> param = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(search)) {
            param.put("productSearch", search);
            if(NumberUtils.toLong(search)>0){
                param.put("productSearchId", search);
            }
        }
        param.put("productTypeList",new String[]{"OTHER"});
        param.put("subProductTypeList", new String[]{productType});
        param.put("_startRow", "0");
        param.put("_endRow", "10");
        
        List<ProdProduct> list = prodProductService.selectProductByParms(param);
        JSONArray array=new JSONArray();
        if(CollectionUtils.isNotEmpty(list)){
            for(ProdProduct product:list){
                JSONObject obj=new JSONObject();
                obj.put("id", product.getProductId());
                obj.put("text", product.getProductName());
                array.add(obj);
            }
        }
        JSONOutput.writeJSON(getResponse(), array);
    }
    
    @Action("/product/relation/getProdBranchListJSON")
    public void getBranchListJSON(){
        JSONResult result=new JSONResult();
        try{
            List<ProdProductBranch> productBranchList = prodProductBranchService.getProductBranchByProductId(ebkProdRelation.getRelateProductId(),null,"true");
            boolean findFlag=false;
            if(CollectionUtils.isNotEmpty(productBranchList)){
                findFlag=true;
                JSONArray array=new JSONArray();
                for(ProdProductBranch b:productBranchList){
                    JSONObject obj=new JSONObject();
                    obj.put("branchId", b.getProdBranchId());
                    obj.put("branchName", b.getBranchName());
                    array.add(obj);
                }
                result.put("list", array);
            }
            
            result.put("find", findFlag);
        }catch(Exception ex){
            result.raise(ex);
        }
        result.output(getResponse());
    }
    
    private boolean valid(){
        JSONObject json=new JSONObject();
        if(null==ebkProdRelation){
            json.put("success", Boolean.FALSE);
            json.put("message",-3001);
            JSONOutput.writeJSON(getResponse(), json);
            return Boolean.FALSE;
        }
        if(null==ebkProdRelation.getRelateProdBranchId()){
            json.put("success", Boolean.FALSE);
            json.put("message",-3002);
            JSONOutput.writeJSON(getResponse(), json);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    public List<EbkProdRelation> getRelations() {
        return relations;
    }
    public void setRelations(List<EbkProdRelation> relations) {
        this.relations = relations;
    }
    public EbkProdRelation getEbkProdRelation() {
        return ebkProdRelation;
    }
    public void setEbkProdRelation(EbkProdRelation ebkProdRelation) {
        this.ebkProdRelation = ebkProdRelation;
    }
    public String getSearch() {
        return search;
    }
    public void setSearch(String search) {
        this.search = search;
    }
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
}
