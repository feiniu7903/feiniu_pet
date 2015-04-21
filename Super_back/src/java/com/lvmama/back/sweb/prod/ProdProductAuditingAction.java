package com.lvmama.back.sweb.prod;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdTraffic;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.service.perm.PermRoleService;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuzhibing
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Results({
        @Result(name = "toProductAuditing", location = "/WEB-INF/pages/back/prod/auditing/product_auditing.jsp"),
        @Result(name = "EDIT_TICKET",       location = "/prod/editTicketProduct.do?productId=${productId}&auditingStatusMark=Y", type = "redirect"),
        @Result(name="EDIT_ROUTE_SELFPACK", location = "/prod/editRouteSelfPackProduct.do?productId=${productId}&auditingStatusMark=Y",type="redirect"),
        @Result(name = "EDIT_ROUTE",        location = "/prod/editRouteProduct.do?productId=${productId}&auditingStatusMark=Y", type = "redirect")
        })
public class ProdProductAuditingAction extends BaseAction {

    private static final long serialVersionUID = 4003366873962908288L;

    private ProdProductService prodProductService;
    private ProdProductBranchService prodProductBranchService;
    private PermRoleService permRoleService;
    private ProdProduct product;
    
    /**
     * 是否可审核
     */
    private Boolean auditAble;

    private Map<String, Object> jsonMap = new HashMap<String, Object>();
    private String productName;
    private Long productId;
    private String productType;
    private String auditingPass;
    protected TopicMessageProducer productMessageProducer;




    @Action("/prod/toProductAuditingShow")
    public String toProductAuditingShow() {
        String page = "PAGE_NOT_FOUND";
        if (productId != null && productId > 0) {
            ProdProduct product = prodProductService.getProdProduct(productId);
            if (product != null) {
                page = "EDIT_".concat(product.getProductType());
                if (product.hasSelfPack()) {//自主打包的产品
                    page += "_SELFPACK";
                }
            }
        }
        return page;
    }

    @Action("/prod/toProductAuditing")
    public String toProductAuditing() {
        product = prodProductService.getProdProduct(productId);

        return "toProductAuditing";
    }

    @Action(value = "/prod/auditing",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String auditing() {
        try {
            product = prodProductService.getProdProduct(productId);
            String auditingStatus = null;
            //QA待审核
            if (Constant.PRODUCT_AUDITING_STATUS.QA_PENDING.getCode().equals(product.getAuditingStatus())) {
                if ("true".equals(auditingPass)) {  //审核通过
                    auditingStatus = Constant.PRODUCT_AUDITING_STATUS.BUSINESS_PENDING.getCode();
                } else if ("false".equals(auditingPass)) {  //审核不通过
                    auditingStatus = Constant.PRODUCT_AUDITING_STATUS.PRODUCTS_SUBMITTED.getCode();
                }
            }
            //商务待审核
            if (Constant.PRODUCT_AUDITING_STATUS.BUSINESS_PENDING.getCode().equals(product.getAuditingStatus())) {
                if ("true".equals(auditingPass)) {  //审核通过
                    auditingStatus = Constant.PRODUCT_AUDITING_STATUS.AUDIT_COMPLETED.getCode();


                    if (!prodProductBranchService.hasDefaultBranch(productId)) {
                        throw new Exception("产品没有设置默认类别，不可以上线");
                    }

                    if (product.isFlight()) {
                        ProdTraffic pt = (ProdTraffic) product;
                        if (pt.getGoFlightId() == null || (pt.hasRound() && pt.getBackFlightId() == null)) {
                            throw new Exception("机票信息的航班信息不正确，不可以上线");
                        }
                    }
                    //检查被打包的采购产品对应的供应商的合同审核状态 add by shihui
                    ResultHandle handle = prodProductService.checkAllMetaSupplierContractStatus(productId);
                    if (handle.isFail()) {
                        throw new Exception(handle.getMsg());
                    }

                } else if ("false".equals(auditingPass)) {  //审核不通过
                    auditingStatus = Constant.PRODUCT_AUDITING_STATUS.BUSINESS_REFUND_SUBMITTED.getCode();
                }
            }
            //培训待确认
            /*
            if (Constant.PRODUCT_AUDITING_STATUS.TRAINING_CONFIRMED.getCode().equals(product.getAuditingStatus())) {
                auditingStatus = Constant.PRODUCT_AUDITING_STATUS.BUSINESS_PENDING.getCode(); //商务待审核
            }*/
            //产品待提交
            if (Constant.PRODUCT_AUDITING_STATUS.PRODUCTS_SUBMITTED.getCode().equals(product.getAuditingStatus())) {
                auditingStatus = Constant.PRODUCT_AUDITING_STATUS.QA_PENDING.getCode(); //QA待审核
            }
            //商务退回，待提交
            if (Constant.PRODUCT_AUDITING_STATUS.BUSINESS_REFUND_SUBMITTED.getCode().equals(product.getAuditingStatus())) {
                auditingStatus = Constant.PRODUCT_AUDITING_STATUS.BUSINESS_PENDING.getCode(); //商务待审核
            }

            if (auditingStatus != null) {
//                product.setAuditingStatus(auditingStatus);
                PermUser permUser = getSessionUser();
//                prodProductService.updateByPrimaryKey(product, permUser.getUserName());
                prodProductService.auditing(product.getProductId(),auditingStatus,permUser.getUserName());

                //审核通过并上线
                if (Constant.PRODUCT_AUDITING_STATUS.AUDIT_COMPLETED.getCode().equals(auditingStatus)) {

                    product.setOnLine("true");

                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("productId", productId);
                    params.put("onLine", product.getOnLine());
                    params.put("productName", product.getProductName());
                    params.put("onLineStr", product.getStrOnLine());
                    prodProductService.markIsSellable(params, getOperatorNameAndCheck());

                    productMessageProducer.sendMsg(MessageFactory.newProductOnOffMessage(productId));
                }
            }

            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }
    
    public ProdProductService getProdProductService() {
        return prodProductService;
    }

    public void setProdProductService(ProdProductService prodProductService) {
        this.prodProductService = prodProductService;
    }

    public PermRoleService getPermRoleService() {
        return permRoleService;
    }

    public void setPermRoleService(PermRoleService permRoleService) {
        this.permRoleService = permRoleService;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String goResult() {
        return null;
    }

    public String goEdit() {
        return null;
    }

    public void save() {

    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public ProdProduct getProduct() {
        return product;
    }

    public void setProduct(ProdProduct product) {
        this.product = product;
    }

    public String getAuditingPass() {
        return auditingPass;
    }

    public void setAuditingPass(String auditingPass) {
        this.auditingPass = auditingPass;
    }

    public Map<String, Object> getJsonMap() {
        return jsonMap;
    }

    public void setJsonMap(Map<String, Object> jsonMap) {
        this.jsonMap = jsonMap;
    }

    public ProdProductBranchService getProdProductBranchService() {
        return prodProductBranchService;
    }

    public void setProdProductBranchService(ProdProductBranchService prodProductBranchService) {
        this.prodProductBranchService = prodProductBranchService;
    }

    public TopicMessageProducer getProductMessageProducer() {
        return productMessageProducer;
    }

    public void setProductMessageProducer(TopicMessageProducer productMessageProducer) {
        this.productMessageProducer = productMessageProducer;
    }

	public Boolean getAuditAble() {
		return auditAble;
	}

	public void setAuditAble(Boolean auditAble) {
		this.auditAble = auditAble;
	}
}
