package com.lvmama.back.sweb.complaint;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.ord.NcComplaint;
import com.lvmama.comm.bee.po.ord.NcComplaintDuty;
import com.lvmama.comm.bee.po.ord.NcComplaintDutyDetails;
import com.lvmama.comm.bee.po.ord.NcComplaintRemind;
import com.lvmama.comm.bee.po.ord.NcComplaintResult;
import com.lvmama.comm.bee.po.ord.NcComplaintRole;
import com.lvmama.comm.bee.po.ord.NcComplaintTracking;
import com.lvmama.comm.bee.po.ord.NcComplaintType;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.complaint.NcComplaintRoleService;
import com.lvmama.comm.bee.service.complaint.NcComplaintService;
import com.lvmama.comm.bee.service.complaint.NcComplaintTypeService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.client.EmailClient;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.po.perm.PermOrganization;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComAffix;
import com.lvmama.comm.pet.po.relation.NcComplaintRelation;
import com.lvmama.comm.pet.po.sms.SmsContent;
import com.lvmama.comm.pet.po.sms.SmsContentLog;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.email.EmailService;
import com.lvmama.comm.pet.service.perm.PermOrganizationService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComAffixService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.relation.NcComplaintRelationService;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.COMPLAINT_SYS_CODE;
import com.lvmama.comm.vst.service.VstOrdOrderService;
import com.lvmama.comm.vst.service.VstProdProductService;
import com.lvmama.comm.vst.service.VstSuppSupplierService;
import com.lvmama.comm.vst.vo.VstOrdOrderItem;
import com.lvmama.comm.vst.vo.VstOrdOrderVo;
import com.lvmama.comm.vst.vo.VstProdGoodsVo;
import com.lvmama.comm.vst.vo.VstProdProductVo;
import com.lvmama.comm.vst.vo.VstSuppSupplierVo;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-10-29<p/>
 * Time: 上午11:29<p/>
 * Email:kouhongyu@163.com<p/>
 */

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Results({
        @Result(name = "toComplaintEdit", location = "/WEB-INF/pages/back/complaint/complaint_edit.jsp"),
        @Result(name = "toComplaintView", location = "/WEB-INF/pages/back/complaint/complaint_view.jsp"),
        @Result(name = "toComplaintList", location = "/WEB-INF/pages/back/complaint/complaint_list.jsp"),
        @Result(name = "showTrackingUploadDialog", location = "/WEB-INF/pages/back/complaint/tracking_upload_affix.jsp"),
        @Result(name = "showConfirmationUploadDialog", location = "/WEB-INF/pages/back/complaint/confirmation_upload_dialog.jsp"),
        @Result(name = "showComplaintRemindDialog", location = "/WEB-INF/pages/back/complaint/remind_dialog.jsp"),
        @Result(name = "showTransferDialog", location = "/WEB-INF/pages/back/complaint/transfer_dialog.jsp"),
        @Result(name = "showUpgradeDialog", location = "/WEB-INF/pages/back/complaint/upgrade_dialog.jsp"),
        @Result(name = "showSelectDepartmentDialog", location = "/WEB-INF/pages/back/complaint/select_department_dialog.jsp"),
        @Result(name = "showSelectStaffDialog", location = "/WEB-INF/pages/back/complaint/staff_dialog.jsp"),
        @Result(name = "showOrderSupplierDialog", location = "/WEB-INF/pages/back/complaint/order_supplier_dialog.jsp"),
        @Result(name = "showUpdateOrderIdDialog", location = "/WEB-INF/pages/back/complaint/update_order_id_dialog.jsp"),
        @Result(name = "showEditRelatedComplaintDialog", location = "/WEB-INF/pages/back/complaint/edit_related_complaint_dialog.jsp"),
        @Result(name = "showEditRelatedOrderDialog", location = "/WEB-INF/pages/back/complaint/edit_related_order_dialog.jsp"),
        @Result(name = "searchStaff", location = "/WEB-INF/pages/back/complaint/show_select_staff_dialog.jsp"),
        @Result(name = "addComplaint", location = "/WEB-INF/pages/back/complaint/addComplaint.jsp"),
        @Result(name = "showEmailDialog", location = "/WEB-INF/pages/back/complaint/email_dialog.jsp"),
        @Result(name = "toSendEmail", location = "/WEB-INF/pages/back/complaint/email_iframe.jsp"),
        @Result(name = "showMessageDialog", location = "/WEB-INF/pages/back/complaint/message_dialog.jsp"),
        @Result(name = "searchContent", location = "/WEB-INF/pages/back/complaint/email_search_content.jsp")
})
public class ComplaintEditAction extends BaseAction {

    private Map<String, Object> jsonMap = new HashMap<String, Object>();

    private VstOrdOrderService vstOrdOrderService;
    private VstProdProductService vstProdProductService;
    private VstSuppSupplierService vstSuppSupplierService;
    private NcComplaintService ncComplaintService;
    private NcComplaintRoleService ncComplaintRoleService;
    private NcComplaintTypeService ncComplaintTypeService;
    private OrderService orderServiceProxy;
    private PermUserService permUserService;
    private ComAffixService comAffixService;
    private OrdRefundMentService ordRefundMentService;
    private SupplierService supplierService;
    private ProdProductService prodProductService;
    private ComLogService comLogService;
    private SmsRemoteService smsRemoteService;
    private EmailService emailRemoteService;
    private EmailClient emailClient;
    private FSClient fsClient;
    private NcComplaintRelationService ncComplaintRelationService;
    private UserUserProxy userUserProxy;
    private PermOrganizationService permOrganizationService;

    private OrdOrder orderDetail;

    private NcComplaint ncComplaint;
    private NcComplaintTracking ncComplaintTracking;
    private OrdOrder ordOrder;
    private ProdProduct prodProduct;
    private PermUser permUser;
    private ComAffix comAffix;
    private NcComplaintRemind ncComplaintRemind;
    private NcComplaintDuty ncComplaintDuty;
    private NcComplaintDuty ncComplaintDutyReparation;
    private Page<PermUser> permUserPage = new Page<PermUser>();
    private NcComplaintResult ncComplaintResult;

    private String permId;
    private String complaintId;
    private String orderId;
    private String complaintDateStart; //投诉日期
    private String complaintDateEnd;
    private String startProcessTimeStart; //处理日期
    private String startProcessTimeEnd;
    private String closeTimeStart; //结案日期
    private String closeTimeEnd;
    private String currentProcessPeople; //投诉处理人
    private String entryPeople; //录入人
    private String completionTimeStart; //完成日期
    private String completionTimeEnd;
    private String complaintName; //投诉会员名
//    private String userName;
    private String contact; //投诉联系人
    private String contactMobile; //联系人电话
    private String productId;
    private String productName;
    private String productType;
    private String source; //投诉来源
    private Long complaintType; //投诉类型Id
    private String complaintTypeCnName; //投诉类型名称（显示用）
    private String upgrade; //是否升级
    private String urgent; //是否紧急 
    private String belongsCenter; //所属中心Id
    private String belongsCenterCnName; //所属中心名称（显示用）
    private String treatmentType; //处理结果
    private String replyAging; //回复时效
    private String sysCode;//业务编码
    private List<String> processStatus; //处理状态
    private String gender;
    private String identity;
    private String relatedOrder;
    private String email;
    private String detailsComplaint;
    private String realName;
    private Long page = 1L;
    private Boolean view = false;
    private String selectUserType;
    private String transferComplaintUserNames;
    private String complaintIds;
    private boolean flag=false; //判断是否是从投诉列表中进入新增投诉页面

    private List<NcComplaintRole> ncComplaintRoleList;
    private List<NcComplaintType> ncComplaintTypeList;
    private List<NcComplaintTracking> ncComplaintTrackingList;
    private List<NcComplaintRemind> ncComplaintRemindList;
    private List<ComAffix> confirmationAffixList;
    private List<OrdRefundment> ordRefundmentList;
    private List<NcComplaintDutyDetails> ncComplaintDutyDetailsList;
    private List<NcComplaintDutyDetails> ncComplaintDutyDetailsReparationList;
    private List<OrdOrderItemMeta> ordOrderItemMetaList;
    private List<Long> relatedComplaints;
    private List<Long> relatedOrders;
    private List<SmsContent> contentsList;
    private List<SmsContentLog> contentLogsList;
    private List<EmailContent> emailList;

    private String mobilePhone;
    private String processMessage;

    private String visitorEmail;
    private String title;
    private String contentEmail;
    private Long emailId;
    private String complaintDutyType;

    @Action("/order/complaint/toComplaintEdit")
    public String toComplaintEdit() {

        if (ncComplaint == null) {
            ncComplaint = new NcComplaint();
            ncComplaint.setComplaintId(9L);
        }

        ncComplaint = ncComplaintService.getNcComplaintByComplaintId(ncComplaint.getComplaintId());
        //通过权限验证后，记录开始处理时间,修改处理状态为处理中
        if(ncComplaint.getStartProcessTime()==null && !view){
        	ncComplaint.setStartProcessTime(new Date());
        	ncComplaint.setProcessStatus(Constant.NC_COMPLAINT_PROCESSING_STATUS.PROCESSING.name());
        	ncComplaintService.updateNcComplaint(ncComplaint);
        }

        ncComplaintTypeList = ncComplaintTypeService.getAllTypeByPage(new HashMap<String, Object>());
        if(ncComplaint.getComplaintType() != null){
            for(NcComplaintType type : ncComplaintTypeList){
                if(type.getTypeId().equals(ncComplaint.getComplaintType())){
                    complaintTypeCnName  = type.getTypeName();        
                }
            }
        }

/*
        Map<String,Object> roleMapParams = new HashMap<String, Object>();
        roleMapParams.put("isShow", "YES");
        ncComplaintRoleList = ncComplaintRoleService.getAllRoleByPage(roleMapParams);
        if(ncComplaint.getBelongsCenter() != null){
            for(NcComplaintRole role : ncComplaintRoleList){
                if(role.getRoleId().equals(ncComplaint.getBelongsCenter())){
                    belongsCenterCnName  = role.getDepartment();
                }
            }
        }
*/

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("complaintId", ncComplaint.getComplaintId());

        ncComplaintTrackingList = ncComplaintService.getNcComplaintTrackingList(params);
        ncComplaintTrackingList = changeAffix(ncComplaintTrackingList);

        if (ncComplaint.getOrderId() != null) {
        	//根据业务系统查询订单
        	VstOrdOrderVo vstOrdOrder = null;
        	if (Constant.COMPLAINT_SYS_CODE.VST.name().equals(ncComplaint.getSysCode())) {
        		vstOrdOrder = vstOrdOrderService.getVstOrdOrderVo(ncComplaint.getOrderId());
        		if (vstOrdOrder != null) {
        			ordOrder = new OrdOrder();
        			List<OrdOrderItemProd> prods = new ArrayList<OrdOrderItemProd>();
        			if (vstOrdOrder.getVstOrdOrderItems() != null) {
        				for (VstOrdOrderItem item : vstOrdOrder.getVstOrdOrderItems()) {
        					OrdOrderItemProd prod = new OrdOrderItemProd();
        					prod.setProductId(item.getProductId());
        					prod.setProdBranchId(item.getBranchId());
        					prod.setProductName(item.getProductName());
        					prods.add(prod);
        				}
        				ordOrder.setOrdOrderItemProds(prods);
        			}
        			ordOrder.setCreateTime(vstOrdOrder.getCreateTime());
        			ordOrder.setVisitTime(vstOrdOrder.getVisitTime());
        		}
        	} else {
        		ordOrder = orderServiceProxy.queryOrdOrderByOrderId(ncComplaint.getOrderId());
        	}
        	
            if (ordOrder != null) {
            	//根据业务系统查询产品经理
            	Long managerId = null;
            	if (Constant.COMPLAINT_SYS_CODE.VST.name().equals(ncComplaint.getSysCode())) {
            		//通过VST订单获取产品经理ID
            		managerId = getVstManagerId(vstOrdOrder);
            	} else {
            		prodProduct = ncComplaintService.getProdProductByOrderId(ordOrder.getOrderId());
            		managerId = prodProduct.getManagerId();
            	}
            	permUser = permUserService.getPermUserByUserId(managerId);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("orderId", ncComplaint.getOrderId());
                map.put("saleServiceId", ncComplaint.getComplaintId());
                map.put("sysCode", ncComplaint.getSysCode());
                ordRefundmentList = ordRefundMentService.findOrdRefundByParam(map, 0, 100);

                for(OrdRefundment ordRefundment :ordRefundmentList){
                    PermUser user = permUserService.getPermUserByUserName(ordRefundment.getOperatorName());
                    if(user!=null){
                        ordRefundment.setOperatorName(user.getRealName());
                    }
                }

            }
        }

        ncComplaintRemindList = ncComplaintService.getNcComplaintRemindList(params);

        Map<String, Object> confirmationParams = new HashMap<String, Object>();
        confirmationParams.put("objectType", "NC_CONFIRMATION");
        confirmationParams.put("objectId", ncComplaint.getComplaintId());

        confirmationAffixList = comAffixService.selectListByParam(confirmationParams);

        Map<String, Object> dutyParams = new HashMap<String, Object>();
        dutyParams.put("complaintId",ncComplaint.getComplaintId());
        dutyParams.put("type",Constant.NC_COMPLAINT_DUTY_TYPE.DUTY);

        ncComplaintDuty = ncComplaintService.getNcComplaintDuty(dutyParams);
        if (ncComplaintDuty != null) {
            ncComplaintDutyDetailsList = ncComplaintService.getNcComplaintDutyDetailsList(ncComplaintDuty.getDutyId());
        }

        dutyParams.put("type",Constant.NC_COMPLAINT_DUTY_TYPE.REPARATION);
        ncComplaintDutyReparation= ncComplaintService.getNcComplaintDuty(dutyParams);
        if (ncComplaintDutyReparation != null) {
            ncComplaintDutyDetailsReparationList = ncComplaintService.getNcComplaintDutyDetailsList(ncComplaintDutyReparation.getDutyId());
        }

        if (ncComplaint.getRelatedComplaint() != null && StringUtils.isNotBlank(ncComplaint.getRelatedComplaint().trim())) {
            relatedComplaints = new ArrayList<Long>();

            String[] rcs = ncComplaint.getRelatedComplaint().trim().split(",");
            for (String rc : rcs) {
                if (StringUtils.isNotBlank(rc.trim())) {
                    relatedComplaints.add(Long.parseLong(rc.trim()));
                }
            }
        }
        if (ncComplaint.getRelatedOrder() != null && StringUtils.isNotBlank(ncComplaint.getRelatedOrder().trim())) {
            relatedOrders = new ArrayList<Long>();

            String[] ros = ncComplaint.getRelatedOrder().trim().split(",");
            for (String ro : ros) {
                if (StringUtils.isNotBlank(ro.trim())) {
                    relatedOrders.add(Long.parseLong(ro.trim()));
                }
            }
        }
        ncComplaintResult = ncComplaintService.getComplaintResultByComplaintId(ncComplaint.getComplaintId());

        if(view){
            return "toComplaintView";
        }else{
            if(ncComplaint.getProcessStatus().equals(Constant.NC_COMPLAINT_PROCESSING_STATUS.COMPLETE.getCode()) ||
                    ncComplaint.getProcessStatus().equals(Constant.NC_COMPLAINT_PROCESSING_STATUS.CLOSE.getCode())){
                return "toComplaintView";
            }else{
                return "toComplaintEdit";
            }
        }
    }

    private Long getVstManagerId(VstOrdOrderVo vstOrdOrder) {
    	if (vstOrdOrder.getMainOrderItem() == null) {
			throw new RuntimeException("订单主项为空");
		}
    	VstProdGoodsVo vstProdGoods = vstProdProductService.getVstProdGoodsVo(vstOrdOrder.getMainOrderItem().getSuppGoodsId());
		return vstProdGoods.getManagerId();
	}

	private List<NcComplaintTracking> changeAffix(List<NcComplaintTracking> ncComplaintTrackingList) {
        for (NcComplaintTracking tracking : ncComplaintTrackingList) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("objectId", tracking.getTrackingId());
            param.put("objectType", "NC_TRACKING");

            List<ComAffix> affixList = comAffixService.selectListByParam(param);
            if (affixList != null && affixList.size() > 0) {
                ComAffix affix = affixList.get(0);
                affix.setFileType(Constant.NC_COMPLAINT_TRACKING_FILE_TYPE.getCnName(affix.getFileType()));
                tracking.setComAffix(affix);
            }
        }
        return ncComplaintTrackingList;
    }

    @Action("/order/complaint/showOrderSupplierDialog")
    public String showOrderSupplierDialog() {
    	//如果是业务系统是VST则调用VST服务查询供应商
    	if (Constant.COMPLAINT_SYS_CODE.VST.name().equals(this.sysCode)) {
    		
    		VstOrdOrderVo ordVo = vstOrdOrderService.getVstOrdOrderVo(Long.valueOf(orderId));
    		Set<Long> queriedSuppliers = new HashSet<Long>();
    		for (VstOrdOrderItem item : ordVo.getVstOrdOrderItems()) {
    			Long supplierId = item.getSupplierId();
    			if (supplierId != null && !queriedSuppliers.contains(supplierId)) {
    				if (ordOrderItemMetaList == null) {
    					ordOrderItemMetaList = new ArrayList<OrdOrderItemMeta>();
    				}
    				queriedSuppliers.add(supplierId);
    				VstSuppSupplierVo supplier = vstSuppSupplierService.findVstSuppSupplierById(supplierId);
    				OrdOrderItemMeta orderItemMeta = new OrdOrderItemMeta();
        			orderItemMeta.setSupplierName(supplier.getSupplierName());
        			orderItemMeta.setSupplierId(supplier.getSupplierId());
        			ordOrderItemMetaList.add(orderItemMeta);
    			}
    		}
    	} else {
    		
    		ordOrder = orderServiceProxy.queryOrdOrderByOrderId(Long.valueOf(orderId));
    		ordOrderItemMetaList = ordOrder.getAllOrdOrderItemMetas();
    		
    		for (OrdOrderItemMeta orderItemMeta : ordOrderItemMetaList) {
    			
    			SupSupplier supSupplier = supplierService.getSupplier(orderItemMeta.getSupplierId());
    			orderItemMeta.setSupplierName(supSupplier.getSupplierName());
    			orderItemMeta.setProductType(Constant.PRODUCT_TYPE.getCnName(orderItemMeta.getProductType()));
    		}
    	}


        return "showOrderSupplierDialog";
    }
    
    

    @Action("/order/complaint/showUpgradeDialog")
    public String showUpgradeDialog() {

        return "showUpgradeDialog";
    }

    @Action("/order/complaint/showSelectDepartmentDialog")
    public String showSelectDepartmentDialog() {

//        ncComplaintRoleList = ncComplaintRoleService.getAllRoleByPage(new HashMap<String, Object>());

        return "showSelectDepartmentDialog";
    }

    @Action("/order/complaint/showSelectStaffDialog")
    public String showSelectStaffDialog() {

        return "showSelectStaffDialog";
    }

    @Action("/order/complaint/showUpdateOrderIdDialog")
    public String showUpdateOrderIdDialog() {

        ncComplaint = ncComplaintService.getNcComplaintByComplaintId(ncComplaint.getComplaintId());

        return "showUpdateOrderIdDialog";
    }

    @Action("/order/complaint/searchStaff")
    public String searchStaff() {
        Map<String, Object> params = new HashMap<String, Object>();
        if (!StringUtil.isEmptyString(complaintName)) {
            params.put("userName", complaintName);
        }
        if (!StringUtil.isEmptyString(realName)) {
            params.put("realName", realName);
        }
        params.put("valid", "Y");
        //取得数据总数量
//        permUserPage.setTotalResultSize(permUserService.selectUsersCountByParams(params));
        //初始化分页信息
        permUserPage.buildUrl(getRequest());
        permUserPage.setCurrentPage(page);
        params.put("skipResults", 0);
        params.put("maxResults", 5);

//        params.put("skipResults", permUserPage.getStartRows() - 1);
//        params.put("maxResults", permUserPage.getEndRows());
        permUserPage.setItems(permUserService.selectUsersByParams(params));


        return "searchStaff";
    }

    @Action("/order/complaint/showTransferDialog")
    public String showTransferDialog() {

        return "showTransferDialog";
    }

    @Action("/order/complaint/showTrackingUploadDialog")
    public String showTrackingUploadDialog() {

        return "showTrackingUploadDialog";
    }

    @Action("/order/complaint/showConfirmationUploadDialog")
    public String showConfirmationUploadDialog() {

        return "showConfirmationUploadDialog";
    }

    @Action("/order/complaint/toSendEmail")
    public String toSendEmail() {

        return "toSendEmail";
    }


    @Action("/order/complaint/showEditRelatedComplaintDialog")
    public String showEditRelatedComplaintDialog() {
        ncComplaint = ncComplaintService.getNcComplaintByComplaintId(ncComplaint.getComplaintId());
        return "showEditRelatedComplaintDialog";
    }
@Action("/order/complaint/showEditRelatedOrderDialog")
    public String showEditRelatedOrderDialog() {
        ncComplaint = ncComplaintService.getNcComplaintByComplaintId(ncComplaint.getComplaintId());
        return "showEditRelatedOrderDialog";
    }

    @Action("/order/complaint/showComplaintRemindDialog")
    public String showComplaintRemindDialog() {


        if (ncComplaintRemind != null && ncComplaintRemind.getRemindId() != null) {
            ncComplaintRemind = ncComplaintService.getNcComplaintRemindByRemindId(ncComplaintRemind.getRemindId());
        }

        return "showComplaintRemindDialog";
    }

    @Action(value = "/order/complaint/saveComplaintDuty",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String saveComplaintDuty() {
        try {

            if (ncComplaintDuty == null) {
                throw new Exception("责任认定信息错误");
            }

            ncComplaintDutyDetailsList = trimNcComplaintDutyDetailsList(ncComplaintDutyDetailsList);

            Map<String,Integer> bmMap = new HashMap<String, Integer>();
            Map<String,Integer> zbMap = new HashMap<String, Integer>();
            Map<String,Integer> ygMap = new HashMap<String, Integer>();
            Map<String,Integer> gysMap = new HashMap<String, Integer>();

            for(NcComplaintDutyDetails details:ncComplaintDutyDetailsList){

                if (details.getDutyMain().equals("部门")) {
                    bmMap.put(details.getMainName(), bmMap.get(details.getMainName()) == null ? 1 : bmMap.get(details.getMainName()) + 1);
                } else if (details.getDutyMain().equals("公司总部")) {
                    zbMap.put(details.getMainName(), zbMap.get(details.getMainName()) == null ? 1 : zbMap.get(details.getMainName()) + 1);
                } else if (details.getDutyMain().equals("员工")) {
                    ygMap.put(details.getMainName(), ygMap.get(details.getMainName()) == null ? 1 : ygMap.get(details.getMainName()) + 1);
                } else if (details.getDutyMain().equals("供应商")) {
                    gysMap.put(details.getMainName(), gysMap.get(details.getMainName()) == null ? 1 : gysMap.get(details.getMainName()) + 1);
                }

            }

            checkDetails(bmMap,"部门");
            checkDetails(zbMap,"公司总部");
            checkDetails(ygMap,"员工");
            checkDetails(gysMap,"供应商");


            ncComplaintService.saveNcComplaintDuty(ncComplaintDuty, ncComplaintDutyDetailsList);
            StringBuilder sb = new StringBuilder();
            if (ncComplaintDuty.getType().equals(Constant.NC_COMPLAINT_DUTY_TYPE.DUTY.getCode())) {

                sb.append("问题类型:").append(Constant.NC_COMPLAINT_DUTY_DEFECT_CATEGORY.getCnName(ncComplaintDuty.getDefectCategory())).append("。");

                for (NcComplaintDutyDetails details : ncComplaintDutyDetailsList) {
                    sb.append(details.getDutyMain()).append(":").append(details.getMainName()).append("。");
                }

                addComLog("SAVE_COMPLAINT_DUTY", "录入责任认定", sb.toString(), getSessionUser().getRealName());
            } else {
                sb.append("赔付总金额:").append(ncComplaintDuty.getTotalAmount()==null?"0.0":ncComplaintDuty.getTotalAmount()).append("。");

                for (NcComplaintDutyDetails details : ncComplaintDutyDetailsList) {
                    sb.append(details.getDutyMain()).append(":").append(details.getMainName()).append(",");
                    sb.append("赔偿金额:").append(details.getAmount()).append("。");
                }

                addComLog("SAVE_COMPLAINT_DUTY_REPARATION", "录入补偿认定", sb.toString(), getSessionUser().getRealName());
            }

            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    private void checkDetails(Map<String, Integer> map, String dutyMain) throws Exception {
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String mainName = iterator.next();
            Integer num = map.get(mainName);
            if (num > 1) {
                throw new Exception(dutyMain + "“" + mainName + "”不能重复");
            }
        }
    }

    private List<NcComplaintDutyDetails> trimNcComplaintDutyDetailsList(List<NcComplaintDutyDetails> ncComplaintDutyDetailsList) {
        List<NcComplaintDutyDetails> list = new ArrayList<NcComplaintDutyDetails>();
        if (ncComplaintDutyDetailsList != null) {
            for (NcComplaintDutyDetails details : ncComplaintDutyDetailsList) {
                if (details != null) {
                    list.add(details);
                }
            }
        }
        return list;
    }
    @Action(value = "/order/complaint/saveComplaintResult",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String saveComplaintResult() {
        try {

            if (ncComplaintResult == null) {
                throw new Exception("处理结果为空");
            }

            if(ncComplaintResult.getTreatmentType().equals(Constant.NC_COMPLAINT_CASH_COMPLAINT.COMMUNICATION.getCode()) ||
                    ncComplaintResult.getTreatmentType().equals(Constant.NC_COMPLAINT_CASH_COMPLAINT.APOLOGY.getCode())){
                ncComplaintResult.setCashCompensation(" ");
            }


            if (ncComplaintResult.getResultId() == null) {
                ncComplaintService.createNcComplaintResult(ncComplaintResult);
            } else {
                ncComplaintService.updateNcComplaintResult(ncComplaintResult);
            }

            StringBuilder sb = new StringBuilder();
            sb.append("积分:").append(StringUtils.isBlank(ncComplaintResult.getIntegralCompensation())?"无":ncComplaintResult.getIntegralCompensation());
            sb.append(",礼品:").append(StringUtils.isBlank(ncComplaintResult.getGifiCompendation())?"无":ncComplaintResult.getGifiCompendation());
            sb.append(",现金:").append(StringUtils.isBlank(ncComplaintResult.getCashCompensation())?"无":ncComplaintResult.getCashCompensation());

            addComLog("SAVE_COMPLAINT_RESULT", "录入处理结果", sb.toString(),getSessionUser().getRealName());


             ncComplaintResult = ncComplaintService.getComplaintResultByComplaintId(ncComplaintResult.getComplaintId());
            jsonMap.put("resultId", ncComplaintResult.getResultId());
            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/order/complaint/updateOrderId",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String updateOrderId() {
        try {
            Long orderid = ncComplaint.getOrderId();
            ncComplaint = ncComplaintService.getNcComplaintByComplaintId(ncComplaint.getComplaintId());

            if(orderid==null){
                ncComplaint.setOrderId(null);
                ncComplaint.setProductNumber(null);
            }else{
            	//根据业务系统查询订单
            	VstOrdOrderVo vstOrdOrder = null;
            	if (Constant.COMPLAINT_SYS_CODE.VST.name().equals(ncComplaint.getSysCode())) {
            		vstOrdOrder = vstOrdOrderService.getVstOrdOrderVo(orderid);
            		ordOrder = new OrdOrder();//用于判断
            	} else {
            		ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderid);
            	}

                if (ordOrder == null) {
                    throw new Exception("找不到订单");
                }

                ncComplaint.setOrderId(orderid);
                
                Long managerId = null;
                if (Constant.COMPLAINT_SYS_CODE.VST.name().equals(ncComplaint.getSysCode())) {
                	//如果来自VST取main order item
                	ncComplaint.setProductNumber(vstOrdOrder.getMainOrderItem().getSuppGoodsId());
                	//通过VST订单获取产品经理ID
                	managerId = getVstManagerId(vstOrdOrder);
                } else {
                	ProdProduct product = ncComplaintService.getProdProductByOrderId(orderid);
                	ncComplaint.setProductNumber(product.getProductId());
                	managerId = product.getManagerId();
                }
                
                permUser = permUserService.getPermUserByUserId(managerId);
                String depName = permUser.getDepartmentName();
                //同时修改当前处理人
                permUser = DistributionComplaint(permUser.getDepartmentId());
                if (permUser == null) {
                	throw new Exception("“"+depName+"”下没有找到用户");
                }
                if(permUser==null){
                	throw new Exception();
                }
                ncComplaint.setCurrentProcessPeople(permUser.getUserName());
            }
            ncComplaintService.updateNcComplaintAll(ncComplaint);

            addComLog("UPDATE_ORDER_ID", "更新投诉单", "修改了订单号",getSessionUser().getRealName());

            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

	@Action(value = "/order/complaint/updateRelatedComplaint",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String updateRelatedComplaint() {
        try {

            String relatedComplaint = ncComplaint.getRelatedComplaint();
            String[] rcs;

            if(StringUtils.isBlank(relatedComplaint)){
                relatedComplaint = null;
                rcs = new String[0];
            }else {


                 rcs = relatedComplaint.split(",");

                Map<String, Integer> map = new HashMap<String, Integer>();


                for (String rc : rcs) {
                    Integer num = map.get(rc);
                    if (num == null) {
                        map.put(rc, 1);
                    } else {
                        map.put(rc, num + 1);
                    }
                }

                Iterator iterator = map.keySet().iterator();

                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    Integer num = map.get(key);
                    if (num > 1) {
                        throw new Exception("投诉单号“" + key + "”重复");
                    }
                }

                for (String rc : rcs) {

                    try {
                        Long comid = Long.parseLong(rc);
                        NcComplaint nc = ncComplaintService.getNcComplaintByComplaintId(comid);
                        if (nc == null) {
                            throw new Exception("投诉单“" + rc + "”不存在");
                        }
                    } catch (NumberFormatException e) {
                        throw new Exception("投诉单号“" + rc + "”格式错误");
                    } catch (Exception e) {
                        throw e;
                    }
                }
            }
            ncComplaintService.updateRelatedComplaint(ncComplaint.getComplaintId(),relatedComplaint);

            addComLog("UPDATE_RELATED_COMPLAINT", "更新投诉单", "修改了关联投诉单",getSessionUser().getRealName());

            jsonMap.put("complaintIds", rcs);
            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/order/complaint/updateRelatedOrder",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String updateRelatedOrder() {
        try {

        	
            relatedOrder = ncComplaint.getRelatedOrder();
            String[] ros;

            if(StringUtils.isBlank(relatedOrder)){
                relatedOrder = null;
                ros = new String[0];
            }else {
                 ros = relatedOrder.split(",");

                Map<String, Integer> map = new HashMap<String, Integer>();


                for (String ro : ros) {
                    Integer num = map.get(ro);
                    if (num == null) {
                        map.put(ro, 1);
                    } else {
                        map.put(ro, num + 1);
                    }
                }

                Iterator iterator = map.keySet().iterator();

                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    Integer num = map.get(key);
                    if (num > 1) {
                        throw new Exception("订单号“" + key + "”重复");
                    }
                }

                NcComplaint dbncComplaint = ncComplaintService.getNcComplaintByComplaintId(ncComplaint.getComplaintId());
                for (String ro : ros) {
                    try {
                        Long orderId = Long.parseLong(ro);
                        OrdOrder order = this.getOrdOrder(dbncComplaint.getSysCode(), orderId);
                        if (order == null) {
                            throw new Exception("投诉单“" + ro + "”不存在");
                        }
                    } catch (NumberFormatException e) {
                        throw new Exception("投诉单“" + ro + "”格式错误");
                    } catch (Exception e) {
                        throw e;
                    }
                }
            }
            ncComplaint = ncComplaintService.getNcComplaintByComplaintId(ncComplaint.getComplaintId());
            ncComplaint.setRelatedOrder(relatedOrder);
            ncComplaintService.updateNcComplaintAll(ncComplaint);

            addComLog("UPDATE_RELATED_ORDER", "更新投诉单", "修改了关联订单",getSessionUser().getRealName());

            jsonMap.put("relatedOrders", ros);
            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }


    @Action(value = "/order/complaint/transferComplaint",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String transferComplaint() {
        try {

            if (transferComplaintUserNames == null) {
                throw new Exception("请填写客服编号");
            }

            String[] names = transferComplaintUserNames.split(",");

            if (names == null || names.length == 0) {
                throw new Exception("请填写客服编号");
            }

            String targetUserName = null;

            List<String> onlineUserList = new ArrayList<String>();

            for (String username : names) {
                PermUser targetPermUser = permUserService.getPermUserByUserName(username);
                if ("ONLINE".equals(targetPermUser.getWorkStatus())) {
                    onlineUserList.add(targetPermUser.getUserName());
                }
            }

            Random random = new Random();

            if(onlineUserList.size()>0){
                int index = random.nextInt(onlineUserList.size());
                targetUserName = onlineUserList.get(index);
            }else{
                int index = random.nextInt(names.length);
                targetUserName = names[index];
            }

            String[] cids = complaintIds.split(",");
            List<NcComplaint> tempNcComplaintList = new ArrayList<NcComplaint>();
            for (String cid : cids) {
                NcComplaint tNcComplaint = ncComplaintService.getNcComplaintByComplaintId(Long.valueOf(cid));
                tempNcComplaintList.add(tNcComplaint);
            }

            ncComplaintService.transferComplaint(complaintIds, targetUserName);

            for (NcComplaint nc : tempNcComplaintList) {
                String oldPermUserRealName = nc.getCurrentProcessPeople();
                PermUser pPermUser = permUserService.getPermUserByUserName(nc.getCurrentProcessPeople());
                if (pPermUser != null) {
                    oldPermUserRealName = pPermUser.getRealName();
                }
                pPermUser = permUserService.getPermUserByUserName(targetUserName);
                if (pPermUser != null) {
                    targetUserName = pPermUser.getRealName();
                }
                comLogService.insert("NC_COMPLAINT",
                        null,
                        nc.getComplaintId(),
                        getSessionUser().getRealName(),
                        "TRANSFER_COMPLAINT",
                        "录入投诉处理信息",
                        oldPermUserRealName + "转移客服至" + targetUserName,
                        null);
            }


            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/order/complaint/selectComplaintRemind",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String selectComplaintRemind() {
        try {

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("complaintId", ncComplaint.getComplaintId());
            ncComplaintRemindList = ncComplaintService.getNcComplaintRemindList(params);

            jsonMap.put("ncComplaintRemindList", ncComplaintRemindList);
            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/order/complaint/deleteComplaintRemind",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String deleteComplaintRemind() {
        try {

            if (ncComplaintRemind == null) {
                throw new Exception("自我提醒不能为空");
            }

            ncComplaintService.deleteNcComplaintRemind(ncComplaintRemind.getRemindId());

            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/order/complaint/deleteComplaintConfirmation",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String deleteComplaintConfirmation() {
        try {

            if (comAffix == null) {
                throw new Exception("附件信息错误");
            }

            comAffixService.removeAffix(comAffix, getSessionUser().getUserName());

            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/order/complaint/saveComplaintTrackingAffix",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String saveComplaintTrackingAffix() {
        try {

            Long trackingId = ncComplaintService.getNcComplaintTrackingSequence();

            comAffix.setCreateTime(new Date());
            comAffix.setObjectId(trackingId);
            comAffix.setObjectType("NC_TRACKING");
            comAffix.setUserId(String.valueOf(getSessionUser().getUserId()));

            comAffixService.addAffixForGroupAdvice(comAffix, String.valueOf(getSessionUser().getUserId()));

            jsonMap.put("fileTypeName", Constant.NC_COMPLAINT_TRACKING_FILE_TYPE.getCnName(comAffix.getFileType()));
            jsonMap.put("fileId", comAffix.getFileId());
            jsonMap.put("trackingId", trackingId);
            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/order/complaint/saveComplaintConfirmationAffix",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String saveComplaintConfirmationAffix() {
        try {

            comAffix.setCreateTime(new Date());
            comAffix.setObjectType("NC_CONFIRMATION");
            comAffix.setUserId(String.valueOf(getSessionUser().getUserId()));

            comAffixService.addAffixForGroupAdvice(comAffix, String.valueOf(getSessionUser().getUserId()));

            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/order/complaint/updateComplaint",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String updateComplaint() {
        try {

            NcComplaint dbNcComplaint = ncComplaintService.getNcComplaintByComplaintId(ncComplaint.getComplaintId());

            dbNcComplaint.setGender(ncComplaint.getGender());
            dbNcComplaint.setRepeatedComplaint(ncComplaint.getRepeatedComplaint());
            dbNcComplaint.setContact(ncComplaint.getContact());
            dbNcComplaint.setContactMobile(ncComplaint.getContactMobile());
            dbNcComplaint.setIdentity(ncComplaint.getIdentity());
            dbNcComplaint.setReplyAging(ncComplaint.getReplyAging());
            dbNcComplaint.setUrgent(ncComplaint.getUrgent());
            dbNcComplaint.setUpgrade(ncComplaint.getUpgrade());
            dbNcComplaint.setSource(ncComplaint.getSource());
            dbNcComplaint.setDetailsComplaint(ncComplaint.getDetailsComplaint());

            if(ncComplaint.getBelongsCenter() ==null){
                throw new Exception("请选择“所属中心”");
            }
            dbNcComplaint.setBelongsCenter(ncComplaint.getBelongsCenter());


            if(ncComplaint.getComplaintType()==null){
                throw new Exception("请选择“投诉类型”");
            }

            dbNcComplaint.setComplaintType(ncComplaint.getComplaintType());

            ncComplaintService.updateNcComplaintAll(dbNcComplaint);

            StringBuffer logSb = new StringBuffer();
            //多次投诉，投诉联系人，联系电话，投诉人身份，回复时效，是否紧急，是否升级，投诉来源，订单号。
            logSb.append("多次投诉:").append(dbNcComplaint.getRepeatedComplaint().equals("YES") ? "是" : "否").append(",");
            logSb.append("投诉联系人:").append(dbNcComplaint.getContact()).append(",");
            logSb.append("联系电话:").append(dbNcComplaint.getContactMobile()).append(",");
            logSb.append("投诉人身份:").append(Constant.NC_COMPLAINT_IDENTITY.getCnName(dbNcComplaint.getIdentity())).append(",");
            logSb.append("回复时效:").append(Constant.NC_COMPLAINT_REPLY_AGING.getCnName(dbNcComplaint.getReplyAging())).append(",");
            logSb.append("是否紧急:").append(dbNcComplaint.getUrgent().equals("YES") ? "是" : "否").append(",");
            logSb.append("是否升级:").append(Constant.NC_COMPLAINT_UPGRADE.getCnName(dbNcComplaint.getUpgrade())).append(",");
            logSb.append("投诉来源:").append(Constant.NC_COMPLAINT_SOURCE.getCnName(dbNcComplaint.getSource())).append(",");
            logSb.append("订单号:").append(dbNcComplaint.getOrderId() == null ? "无" : dbNcComplaint.getOrderId()).append("。");


            addComLog("UPDATE_COMPLAINT", "更新投诉单", logSb.toString(), getSessionUser().getRealName());

            jsonMap.put("ncComplaint", dbNcComplaint);
            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/order/complaint/activationComplaint",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String activationComplaint() {
        try {
            ncComplaint = ncComplaintService.getNcComplaintByComplaintId(ncComplaint.getComplaintId());

            ncComplaint.setProcessStatus(Constant.NC_COMPLAINT_PROCESSING_STATUS.PROCESSING.getCode());

            ncComplaintService.updateNcComplaint(ncComplaint);

            addComLog("ACTIVATION_COMPLAINT", "重新激活投诉单", "重新激活投诉单", getSessionUser().getRealName());

            jsonMap.put("ncComplaint", ncComplaint);
            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/order/complaint/saveComplaintTracking",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String saveComplaintTracking() {
        try {

            permUser = getSessionUser();
            ncComplaintTracking.setComplaintId(ncComplaint.getComplaintId());
            ncComplaintTracking.setOperator(permUser.getRealName());
            ncComplaintTracking.setOperationTime(new Date());

            if (ncComplaintTracking.getTrackingId() == null) {
                Long trackingId = ncComplaintService.getNcComplaintTrackingSequence();
                ncComplaintTracking.setTrackingId(trackingId);
            }

            ncComplaintService.createNcComplaintTracking(ncComplaintTracking, ncComplaint);


            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/order/complaint/completeComplaint",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String completeComplaint() {
        try {

            ncComplaint.setCompletionTime(new Date());
            ncComplaint.setProcessStatus(Constant.NC_COMPLAINT_PROCESSING_STATUS.COMPLETE.getCode());

            ncComplaintService.updateNcComplaint(ncComplaint);

            addComLog("COMPLETE_COMPLAINT", "投诉状态变更", "投诉处理完成",getSessionUser().getRealName());

            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/order/complaint/closeComplaint",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String closeComplaint() {
        try {
            ncComplaint = ncComplaintService.getNcComplaintByComplaintId(ncComplaint.getComplaintId());

            ncComplaint.setProcessStatus(Constant.NC_COMPLAINT_PROCESSING_STATUS.CLOSE.getCode());

            ncComplaintService.updateNcComplaint(ncComplaint);

            addComLog("CLOSE_COMPLAINT", "关闭投诉单", "关闭投诉单。",getSessionUser().getRealName());

            jsonMap.put("ncComplaint", ncComplaint);
            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    private void addComLog(String logType, String logName, String content,String operatorName) {
        comLogService.insert("NC_COMPLAINT",
                null,
                ncComplaint.getComplaintId(),
                operatorName,
                logType,
                logName,
                content,
                null);
    }

    @Action(value = "/order/complaint/saveComplaintRemind",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String saveComplaintRemind() {
        try {
            if (ncComplaintRemind != null) {
                ncComplaintRemind.setOperator(getSessionUser().getUserName());

                if (ncComplaintRemind.getRemindId() == null) {
                    ncComplaintService.createNcComplaintRemind(ncComplaintRemind);
                } else {
                    ncComplaintService.updateNcComplaintRemind(ncComplaintRemind);
                }
            }

            jsonMap.put("ncComplaintRemind", ncComplaintRemind);
            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/order/complaint/selectComplaintTracking",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String selectComplaintTracking() {
        try {

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("complaintId", ncComplaint.getComplaintId());

            if (!StringUtils.isBlank(ncComplaintTracking.getCategory())) {
                params.put("category", ncComplaintTracking.getCategory());
            }

            ncComplaintTrackingList = ncComplaintService.getNcComplaintTrackingList(params);
            ncComplaintTrackingList = changeAffix(ncComplaintTrackingList);
            changeComplaintTrackingType(ncComplaintTrackingList);

            jsonMap.put("ncComplaintTrackingList", ncComplaintTrackingList);
            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    @Action(value = "/order/complaint/selectComplaintConfirmationAffixList",
            results = @Result(type = "json", name = "success", params = {"includeProperties", "jsonMap.*"})
    )
    public String selectComplaintConfirmationAffixList() {
        try {

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("objectType", "NC_CONFIRMATION");
            params.put("objectId", ncComplaint.getComplaintId());

            confirmationAffixList = comAffixService.selectListByParam(params);

            jsonMap.put("confirmationAffixList", confirmationAffixList);
            jsonMap.put("status", SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            jsonMap.put("status", e.getMessage());
        }
        return SUCCESS;
    }

    private void changeComplaintTrackingType(List<NcComplaintTracking> ncComplaintTrackingList) {
        if (ncComplaintTrackingList != null) {
            for (NcComplaintTracking tracking : ncComplaintTrackingList) {
                tracking.setCategory(Constant.NC_COMPLAINT_TRACKING_TYPE.getCnName(tracking.getCategory()));
            }
        }

    }

    @Action("/order/complaint/toComplaintList")
    public String toComplaintList() {
        selectRoleAndType();
        permUser = getSessionUser();
        currentProcessPeople = getSessionUser().getUserName();
        processStatus=new ArrayList<String>();
        processStatus.add(Constant.NC_COMPLAINT_PROCESSING_STATUS.UNTREATED.name());
        processStatus.add(Constant.NC_COMPLAINT_PROCESSING_STATUS.PROCESSING.name());
        processStatus.add(Constant.NC_COMPLAINT_PROCESSING_STATUS.SUSPENDED.name());
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("currentProcessPeople", currentProcessPeople);
        params.put("processStatus", processStatus);
        toResult(params);
        params.put("page", pagination.getPage());
        params.put("perPageRecord", pagination.getPerPageRecord());
        getSession().setAttribute("PROD_GO_UPSTEP_URL", params);
        return "toComplaintList";
    }

    @Action("/order/complaint/queryComplaintList")
    public String queryComplaintList() {
        permUser = getSessionUser();
        Map<String, Object> searchConds = initParam();
        String success = toResult(searchConds);
        selectRoleAndType();

        searchConds.put("page", pagination.getPage());
        searchConds.put("perPageRecord", pagination.getPerPageRecord());
        getSession().setAttribute("PROD_GO_UPSTEP_URL", searchConds);
        return success;
    }

    /**
     * 查询角色中心和投诉类型
     */
    private void selectRoleAndType() {
        Map<String, Object> params = new HashMap<String, Object>();
		params.put("isShow", "YES");
        ncComplaintRoleList = ncComplaintRoleService.getAllRoleByPage(params);
        ncComplaintTypeList = ncComplaintTypeService.getAllTypeByPage(params);
    }

    private String toResult(Map<String, Object> searchConds) {
        //查询总数量
        Long totalRowCount = ncComplaintService.queryComplaintCount(searchConds);
        pagination = super.initPagination();
        pagination.setTotalRecords(totalRowCount);
        searchConds.put("_startRow", pagination.getFirstRow());
        searchConds.put("_endRow", pagination.getLastRow());
        //查询列表信息
        List<NcComplaint> complaintList = ncComplaintService.queryComplaintByParams(searchConds);
        List<NcComplaint> cList = new ArrayList<NcComplaint>();
        for (NcComplaint ncComplaint : complaintList) {
        	if(Constant.COMPLAINT_SYS_CODE.SUPER.getCode().equals(ncComplaint.getSysCode())){
        		  ProdProduct product = ncComplaintService.getProdProductByOrderId(ncComplaint.getOrderId());
                  ncComplaint.setProduct(product);
        	}else if(Constant.COMPLAINT_SYS_CODE.VST.getCode().equals(ncComplaint.getSysCode())){
//        		ProdProduct product =new ProdProduct();
//        		VstProdProductVo productVo =  vstProdProductService.findProdProductListById(ncComplaint.getProductNumber());
//        		if(null != productVo){
//        			product.setProductId(ncComplaint.getProductNumber());
//        			product.setProductName(productVo.getProductName());
//        		}
//                ncComplaint.setProduct(product);
        	}
            NcComplaintResult complaintResult = ncComplaintService.getComplaintResultByComplaintId(ncComplaint.getComplaintId());
            ncComplaint.setComplaintResult(complaintResult);
            List<NcComplaintRemind> remindList = ncComplaintService.getNcComplaintRemindByComplaintId(ncComplaint.getComplaintId());
            if (remindList.size() > 0) {
                NcComplaintRemind remind = remindList.get(0);
                ncComplaint.setComplaintRemind(remind);
            }
            cList.add(ncComplaint);
        }

        Map<String, Object> searchCondsPage = initParamPage();
        searchCondsPage.put("_startRow", pagination.getFirstRow());
        searchCondsPage.put("_endRow", pagination.getLastRow());


        pagination.setRecords(cList);
        pagination.setActionUrl(WebUtils.getUrl(
                "/order/complaint/queryComplaintList.do", true, searchCondsPage));
        return "toComplaintList";
    }

    //判断查询条件
    private Map<String, Object> initParam() {
        Map<String, Object> searchConds = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(this.complaintId)) {
            if (NumberUtils.toLong(complaintId) > 0) {
                searchConds.put("complaintId", complaintId);
            }
        }
        if (StringUtils.isNotEmpty(this.orderId)) {
            if (NumberUtils.toLong(orderId) > 0) {
                searchConds.put("orderId", orderId);
            }
        }

        if (StringUtils.isNotEmpty(this.productId)) {
            if (NumberUtils.toLong(productId) > 0) {
                searchConds.put("productId", productId);
            }
        }
        if (StringUtils.isNotEmpty(this.complaintDateStart)) {
            searchConds.put("complaintDateStart", DateUtil.stringToDate(complaintDateStart
                    + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }
        if (StringUtils.isNotEmpty(this.complaintDateEnd)) {
            searchConds.put("complaintDateEnd", DateUtil.stringToDate(complaintDateEnd
                    + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }

        if (StringUtils.isNotEmpty(this.startProcessTimeStart)) {
            searchConds.put("startProcessTimeStart", DateUtil.stringToDate(startProcessTimeStart
                    + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }
        if (StringUtils.isNotEmpty(this.startProcessTimeEnd)) {
            searchConds.put("startProcessTimeEnd", DateUtil.stringToDate(startProcessTimeEnd
                    + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }
        if (StringUtils.isNotEmpty(this.closeTimeStart)) {
            searchConds.put("closeTimeStart", DateUtil.stringToDate(closeTimeStart
                    + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }
        if (StringUtils.isNotEmpty(this.closeTimeEnd)) {
            searchConds.put("closeTimeEnd", DateUtil.stringToDate(closeTimeEnd
                    + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }

        if (StringUtils.isNotEmpty(currentProcessPeople)) {
            searchConds.put("currentProcessPeople", currentProcessPeople);
        }
        if (StringUtils.isNotEmpty(this.entryPeople)) {
            searchConds.put("entryPeople", entryPeople);
        }
        if (StringUtils.isNotEmpty(this.completionTimeStart)) {
            searchConds.put("completionTimeStart", DateUtil.stringToDate(completionTimeStart
                    + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }
        if (StringUtils.isNotEmpty(this.completionTimeEnd)) {
            searchConds.put("completionTimeEnd", DateUtil.stringToDate(completionTimeEnd
                    + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }
        if (StringUtils.isNotEmpty(this.complaintName)) {
            searchConds.put("userName", complaintName);
        }
        if (StringUtils.isNotEmpty(this.contact)) {
            searchConds.put("contact", contact);
        }
        if (StringUtils.isNotEmpty(contactMobile)) {
            if (NumberUtils.toLong(contactMobile) > 0) {
                searchConds.put("contactMobile", contactMobile);
            }
        }
        if (StringUtils.isNotEmpty(this.source)) {
            searchConds.put("source", source);
        }
        if (complaintType != null && complaintType > 0) {
            searchConds.put("complaintType", complaintType);
        }
        if (StringUtils.isNotEmpty(this.upgrade)) {
            searchConds.put("upgrade", upgrade);
        }
        if (StringUtils.isNotEmpty(this.urgent)) {
            searchConds.put("urgent", urgent);
        }
        if (StringUtils.isNotBlank(belongsCenter)) {
            searchConds.put("belongsCenter", belongsCenter);
        }
        if (StringUtils.isNotEmpty(replyAging)) {
            searchConds.put("replyAging", replyAging);
        }
        if (processStatus != null) {
            searchConds.put("processStatus", processStatus);

            if (processStatus.size() == 1) {

                String ps = processStatus.get(0);

                if (ps.contains("[")) {

                    String[] pss = ps.replace("[", "").replace("]", "").replace(" ", "").split(",");
                    List<String> psList = new ArrayList<String>();
                    for (String s : pss) {
                        String cnName = Constant.NC_COMPLAINT_PROCESSING_STATUS.getCnName(s);
                        if (!s.equals(cnName)) {
                            psList.add(s);
                        }
                    }

                    processStatus = psList;
                    searchConds.put("processStatus", processStatus);
                }
            }

        }
        if (StringUtils.isNotEmpty(productType)) {
            searchConds.put("productType", productType);
        }
        if (StringUtils.isNotEmpty(treatmentType)) {
            searchConds.put("treatmentType", treatmentType);
        }
        if (StringUtils.isNotEmpty(sysCode)) {
        	searchConds.put("sysCode", sysCode);
        }
        return searchConds;
    }
    private Map<String, Object> initParamPage() {
        Map<String, Object> searchConds = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(this.complaintId)) {
            if (NumberUtils.toLong(complaintId) > 0) {
                searchConds.put("complaintId", complaintId);
            }
        }
        if (StringUtils.isNotEmpty(this.orderId)) {
            if (NumberUtils.toLong(orderId) > 0) {
                searchConds.put("orderId", orderId);
            }
        }

        if (StringUtils.isNotEmpty(this.productId)) {
            if (NumberUtils.toLong(productId) > 0) {
                searchConds.put("productId", productId);
            }
        }
        if (StringUtils.isNotEmpty(this.complaintDateStart)) {
            searchConds.put("complaintDateStart", this.complaintDateStart);
        }
        if (StringUtils.isNotEmpty(this.complaintDateEnd)) {
            searchConds.put("complaintDateEnd", this.complaintDateEnd);
        }

        if (StringUtils.isNotEmpty(this.startProcessTimeStart)) {
            searchConds.put("startProcessTimeStart", this.startProcessTimeStart);
        }
        if (StringUtils.isNotEmpty(this.startProcessTimeEnd)) {
            searchConds.put("startProcessTimeEnd", this.startProcessTimeEnd);
        }
        if (StringUtils.isNotEmpty(this.closeTimeStart)) {
            searchConds.put("closeTimeStart", this.closeTimeStart);
        }
        if (StringUtils.isNotEmpty(this.closeTimeEnd)) {
            searchConds.put("closeTimeEnd", this.closeTimeEnd);
        }

        if (StringUtils.isNotEmpty(currentProcessPeople)) {
            searchConds.put("currentProcessPeople", currentProcessPeople);
        }
        if (StringUtils.isNotEmpty(this.entryPeople)) {
            searchConds.put("entryPeople", entryPeople);
        }
        if (StringUtils.isNotEmpty(this.completionTimeStart)) {
            searchConds.put("completionTimeStart", this.completionTimeStart);
        }
        if (StringUtils.isNotEmpty(this.completionTimeEnd)) {
            searchConds.put("completionTimeEnd", this.completionTimeEnd);
        }
        if (StringUtils.isNotEmpty(this.complaintName)) {
            searchConds.put("userName", complaintName);
        }
        if (StringUtils.isNotEmpty(this.contact)) {
            searchConds.put("contact", contact);
        }
        if (StringUtils.isNotEmpty(contactMobile)) {
            if (NumberUtils.toLong(contactMobile) > 0) {
                searchConds.put("contactMobile", contactMobile);
            }
        }
        if (StringUtils.isNotEmpty(this.source)) {
            searchConds.put("source", source);
        }
        if (complaintType != null && complaintType > 0) {
            searchConds.put("complaintType", complaintType);
        }
        if (StringUtils.isNotEmpty(this.upgrade)) {
            searchConds.put("upgrade", upgrade);
        }
        if (StringUtils.isNotEmpty(this.urgent)) {
            searchConds.put("urgent", urgent);
        }
        if (StringUtils.isNotBlank(belongsCenter)) {
            searchConds.put("belongsCenter", belongsCenter);
        }
        if (StringUtils.isNotEmpty(replyAging)) {
            searchConds.put("replyAging", replyAging);
        }
        if (processStatus != null) {
            searchConds.put("processStatus", processStatus);

            if (processStatus.size() == 1) {

                String ps = processStatus.get(0);

                if (ps.contains("[")) {

                    String[] pss = ps.replace("[", "").replace("]", "").replace(" ", "").split(",");
                    List<String> psList = new ArrayList<String>();
                    for (String s : pss) {
                        String cnName = Constant.NC_COMPLAINT_PROCESSING_STATUS.getCnName(s);
                        if (!s.equals(cnName)) {
                            psList.add(s);
                        }
                    }

                    processStatus = psList;
                    searchConds.put("processStatus", processStatus);
                }
            }

        }
        if (StringUtils.isNotEmpty(productType)) {
            searchConds.put("productType", productType);
        }
        if (StringUtils.isNotEmpty(treatmentType)) {
            searchConds.put("treatmentType", treatmentType);
        }
        if (StringUtils.isNotEmpty(sysCode)) {
        	searchConds.put("sysCode", sysCode);
        }
        return searchConds;
    }

    @Action("/order/complaint/addComplaint")
    public String addComplaint() {
        selectRoleAndType();
        if(StringUtils.isBlank(sysCode)){
        	sysCode=COMPLAINT_SYS_CODE.SUPER.getCode();
        }
        if(StringUtils.isNotEmpty(orderId)){
        	 this.orderDetail=getOrdOrder(sysCode,Long.valueOf(orderId));
        }
        getRequest().setAttribute("sysCode", sysCode);
        return "addComplaint";
    }
    
    /**
     * 获取订单详情
     * @param sysCode 系统代码
     * @param orderId 订单id
     * @return
     */
    private OrdOrder getOrdOrder(String sysCode,Long orderId){
    	if(StringUtils.isBlank(sysCode)){
        	sysCode=COMPLAINT_SYS_CODE.SUPER.getCode();
        }
    	OrdOrder orderDetail=null;
    	 if(sysCode.equals(COMPLAINT_SYS_CODE.SUPER.getCode())){//老系统
         	if (orderId != null) {
         		orderDetail = orderServiceProxy.queryOrdOrderByOrderId(orderId);
             }
         }else if(sysCode.equals(COMPLAINT_SYS_CODE.VST.getCode())){//vst系统
         	 if(orderId != null){
              	VstOrdOrderVo vstOrdOrder=vstOrdOrderService.getVstOrdOrderVo(orderId);
              	if(null!=vstOrdOrder){
              		orderDetail =new OrdOrder();
              		UserUser userUser=this.userUserProxy.getUserUserByUserNo(vstOrdOrder.getUserId());
              		if(null!=userUser){
              			orderDetail.setRealName(userUser.getRealName());
              			orderDetail.setGender(userUser.getGender());
              			orderDetail.setUserName(userUser.getUserName());
              			orderDetail.setMobileNumber(userUser.getMobileNumber());
              		}
              		orderDetail.setVisitTime(vstOrdOrder.getVisitTime());
          			orderDetail.setOrderId(vstOrdOrder.getOrderId());
          			orderDetail.setCreateTime(vstOrdOrder.getCreateTime());
          			orderDetail.setUserId(vstOrdOrder.getUserId());
          			orderDetail.setOrderStatus(vstOrdOrder.getOrderStatus());
          			orderDetail.setPaymentStatus(vstOrdOrder.getPaymentStatus());
          			orderDetail.setPaymentTime(vstOrdOrder.getPaymentTime());
          			orderDetail.setOrderPay(vstOrdOrder.getOughtAmount());
          			orderDetail.setActualPay(vstOrdOrder.getActualAmount());
          			orderDetail.setOughtPay(vstOrdOrder.getOughtAmount());
              	}
         	 }
         }
    	 return orderDetail;
    }
    
    
    
    
    @Action("/order/complaint/saveComplaint")
    public void saveComplaint() {
        NcComplaint nc = new NcComplaint();
        nc.setGender(gender);
        nc.setContact(contact);
        nc.setContactMobile(contactMobile);
        nc.setIdentity(identity);
        nc.setSource(source);
        if(StringUtils.isBlank(sysCode)){
        	sysCode=COMPLAINT_SYS_CODE.SUPER.getCode();
        }
        nc.setSysCode(sysCode);
        nc.setReplyAging(replyAging);
        nc.setUrgent(urgent);
        try{
	        if (StringUtils.isNotEmpty(orderId)) {
	        	//判断该订单是否存在
	            if (NumberUtils.toLong(orderId) > 0) {
		            OrdOrder order =this.getOrdOrder(sysCode, Long.valueOf(orderId));
		            if (order != null) {
		                nc.setOrderId(Long.valueOf(orderId));
		                if(COMPLAINT_SYS_CODE.SUPER.getCode().equals(sysCode)){//老系统
		                	 if (order.getMainProduct() == null) {
		                		 sendAjaxMsg("订单主产品为空");
		                         return;
		                	 }
		                	 ProdProduct prodProduct = prodProductService.getProdProduct(order.getMainProduct().getProductId());
				             nc.setProductNumber(prodProduct.getProductId());
				             permUser = permUserService.getPermUserByUserId(prodProduct.getManagerId());
		                }else{//VST
		                	 VstOrdOrderVo vstOrdOrder=vstOrdOrderService.getVstOrdOrderVo(Long.valueOf(orderId));
		                	 if (vstOrdOrder.getMainOrderItem() == null) {
		             			 sendAjaxMsg("订单主项为空");
		                         return;
		             		 }
		                	 nc.setProductNumber(vstOrdOrder.getMainOrderItem().getProductId());
		                	 Long managerId=getVstManagerId(vstOrdOrder);
		                	 permUser = permUserService.getPermUserByUserId(managerId);
		                	 if(null==permUser){
		                		 sendAjaxMsg("没有找到产品经理");
		                         return;
		                	 }
		                }
                        permUser = DistributionComplaint(permUser.getDepartmentId());
                        if (permUser == null) {
	                		 sendAjaxMsg("没有找到当前处理人,请至新投诉角色配置中配置");
	                         return;
                        }
                        String depName = permUser.getDepartmentName();
                        if (permUser == null) {
                            sendAjaxMsg("“"+depName+"”下没有找到用户");
                            return;
                        }
                        nc.setCurrentProcessPeople(permUser.getUserName());
                    }else{
		            	sendAjaxMsg(orderId+" 订单不存在");
		            	return;
		            }
	            }else{
	            	sendAjaxMsg(orderId+" 订单不存在");
	            	return;
	            }
	        }else{
                permUser = DistributionComplaint(55L); //呼叫中心
                if (permUser == null) {
                    sendAjaxMsg("“呼叫中心”下没有找到用户");
                    return;
                }
                nc.setCurrentProcessPeople(permUser.getUserName());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        if(StringUtils.isNotEmpty(complaintName)){
        	//验证会员是否存在
        	UserUser user=userUserProxy.getUsersByMobOrNameOrEmailOrCard(complaintName);
        	if(user==null){
        		sendAjaxMsg("投诉会员名不存在");
            	return;
        	}
        }
        nc.setUserName(complaintName);
        try{
	        if (StringUtils.isNotEmpty(relatedOrder)) {
	        	List<String> orderList = new ArrayList<String>();
	            StringTokenizer st = new StringTokenizer(relatedOrder, ",");
	            while (st.hasMoreTokens()) {
	            	orderList.add(st.nextToken());
	            }
	            if(!isEqualOrder(orderList)){
	      			sendAjaxMsg("不能添加相同的订单");
	      			return;
	      		}
	        	for (String orderRelated : orderList) {
	        		if (NumberUtils.toLong(orderRelated) > 0) {
		        		//判断订单是否存在
		                OrdOrder order = getOrdOrder(sysCode, Long.valueOf(orderRelated));
		                if (order == null) {
		                	sendAjaxMsg(orderRelated+" 订单不存在");
		                	return;
		                }
	        		}else{
	        			sendAjaxMsg(orderRelated+" 订单不存在");
	                	return;
	        		}
				}
	        }
	    }catch(Exception e) {
	        e.printStackTrace();
	    }
        nc.setRelatedOrder(relatedOrder);
        nc.setEmail(email);
        nc.setBelongsCenter(belongsCenter);
        nc.setDetailsComplaint(detailsComplaint);
        nc.setEntryPeople(getOperatorName()); // 录入人，当前用户名
        nc.setProcessStatus(Constant.NC_COMPLAINT_PROCESSING_STATUS.UNTREATED.name());
        nc.setRepeatedComplaint("NO");
        nc.setCreateTime(new Date());
        nc.setComplaintDate(new Date());
        
        Long res = ncComplaintService.createNcComplaint(nc);
        //更新订单售后服务状态
        orderServiceProxy.updateNeedSaleService("true", Long.valueOf(orderId), this.getOperatorName());
        if (res > 0) {
        	Map<String, Object> params=new HashMap<String, Object>();
        	//查询投诉列表，获取最新的一条
        	List<NcComplaint> complaint=ncComplaintService.queryComplaintByParams(params);
        	comLogService.insert("NC_COMPLAINT",null,complaint.get(0).getComplaintId(),
                    getSessionUser().getRealName(),"ADD_COMPLAINT","创建投诉单",
                    "创建投诉单",null);
            sendAjaxMsg("SUCCESS");
        } else {
            sendAjaxMsg("FAILED");
        }
    }

    //将获取的字符串转换成list集合
    private List<String> ChangeList(String name){
    	List<String> list = new ArrayList<String>();
        if (StringUtils.isNotBlank(name)) {
            StringTokenizer st = new StringTokenizer(name, ",");
            while (st.hasMoreTokens()) {
                list.add(st.nextToken());
            }
        }
        return list;
    }
    //判断添加的关联订单号是否存在相同的
  	@SuppressWarnings({ "unchecked", "rawtypes" })
  	private boolean isEqualOrder(List<String> list) {
  		HashSet set=new HashSet();
  		for(String i:list){
  		    set.add(i);
  		}
  		if(!(set.size()==list.size())){
      		return false;
  		}
  		return true;
  	}

    /**
     * 分配投诉信息
     */
    private PermUser DistributionComplaint(Long departmentId) {

        PermOrganization permOrganization = permOrganizationService.getOrganizationByOrgId(departmentId);
        while (permOrganization.getPermLevel() > 1) {//获取顶级部门
            permOrganization = permOrganizationService.getOrganizationByOrgId(permOrganization.getParentOrgId());
        }

        PermUser pu = null;
        //根据所属中心id获取所属中心的人员
        NcComplaintRole role = ncComplaintRoleService.selectRoleByOrgId(permOrganization.getOrgId());
        List<String> personlist = ChangeList(role.getPersons());
        List<PermUser> permUsersList = new ArrayList<PermUser>();//所有人员
        for (String name : personlist) {
            PermUser pUser = permUserService.getPermUserByUserName(name);
            if (pUser == null) {
                break;
            }
            permUsersList.add(pUser);//将查询出的用户放到list集合中
        }
        List<PermUser> permList = new ArrayList<PermUser>(); //存储在线人员信息
        if (CollectionUtils.isNotEmpty(permUsersList)) {
            Iterator<PermUser> it = permUsersList.iterator();
            while (it.hasNext()) {
                PermUser user = it.next();
                if ("ONLINE".equalsIgnoreCase(user.getWorkStatus())) {
                    permList.add(user);
                }
            }
        }
        if (CollectionUtils.isEmpty(permList)) {
            permList.addAll(permUsersList);
        }
        if (CollectionUtils.isNotEmpty(permList)) {
            Random random = new Random();
            int index = random.nextInt(permList.size());
            pu = permList.get(index);
        }
        return pu;
    }

    //发送邮件页面
    @Action("/order/complaint/showEmailDialog")
    public String showEmailDialog() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("complaintId", complaintId);
        emailList = emailRemoteService.queryByParamList(param);
        return "showEmailDialog";
    }

    //查看邮件内容
    @Action("/order/complaint/searchContent")
    public String searchContent() {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("contentId", emailId);
        emailList = emailRemoteService.queryByParamList(param);
        for (EmailContent emailContent : emailList) {
            ComFile file = fsClient.downloadFile(emailContent.getContentFileId());
            try {
                contentEmail = new String(file.getFileData(), "UTF-8");
                if(StringUtils.isNotEmpty(contentEmail)){
	                //将字符串后面的<br />去掉
	                contentEmail = contentEmail.substring(0, contentEmail.length() - 6);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "searchContent";
    }

    /**
     * 发送邮件
     */
    @Action("/order/complaint/saveEmail")
    public void saveEmail() {
        EmailContent email = new EmailContent();
        email.setContentText(contentEmail);
        email.setFromName(getSessionUser().getUserName());
        email.setSubject(title);
        email.setFromAddress("services@lvmama.com");
        email.setToAddress(visitorEmail);
        email.setCreateTime(new Date());
        Long contentId = emailClient.sendEmailDirect(email);
        if (contentId > 0) {
            try {
                NcComplaintRelation complaintRelation = new NcComplaintRelation();
                complaintRelation.setComplaintId(Long.valueOf(complaintId));
                complaintRelation.setEmailId(contentId);
                ncComplaintRelationService.addRelation(complaintRelation);
            } catch (Exception e) {
                LOG.error("添加关系信息错误：\r\n 投诉号：" + complaintId + " >>" + e);
            }
            sendAjaxMsg("success");
        } else {
            sendAjaxMsg("fail");
        }
    }
	
	@Action("/order/complaint/showMessageDialog")
	public String showMessageDialog() {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("complaintId", complaintId);
		contentsList=smsRemoteService.queryByParam(param);
		param.put("searchAll", true);
		contentLogsList=smsRemoteService.querySendedSms(param);
		return "showMessageDialog";
	}
	//发送短信
	@Action("/order/complaint/saveMessage")
	public void saveMessage() {
		SmsContent content=null;
		try{
			//发送短信
			if(StringUtils.isNotEmpty(mobilePhone)){
				List<String> mobileList = ChangeList(mobilePhone);
				for (String mobile : mobileList) {
					content=smsRemoteService.sendSmsContent(processMessage, mobile, 5, null, new Date(), getSessionUser().getUserName());
					try{
					//添加关系信息
						NcComplaintRelation complaintRelation=new NcComplaintRelation();
						complaintRelation.setComplaintId(Long.valueOf(complaintId));
						complaintRelation.setSmsId(Long.valueOf(content.getId()));
						ncComplaintRelationService.addRelation(complaintRelation);
					}catch(Exception e){
						LOG.error("添加关系信息错误：\r\n 投诉号："+complaintId+" >>"+e);
					}
				}
				sendAjaxMsg("success");
			}else{
				sendAjaxMsg("FAILED");
			}
		}catch(Exception e){
			LOG.error("短信发送失败：\r\n 手机号："+mobilePhone+" >>"+e);
		}
	}

    public NcComplaintService getNcComplaintService() {
        return ncComplaintService;
    }

    public void setNcComplaintService(NcComplaintService ncComplaintService) {
        this.ncComplaintService = ncComplaintService;
    }

    public NcComplaint getNcComplaint() {
        return ncComplaint;
    }

    public void setNcComplaint(NcComplaint ncComplaint) {
        this.ncComplaint = ncComplaint;
    }

    public Map<String, Object> getJsonMap() {
        return jsonMap;
    }

    public void setJsonMap(Map<String, Object> jsonMap) {
        this.jsonMap = jsonMap;
    }

    public NcComplaintRoleService getNcComplaintRoleService() {
        return ncComplaintRoleService;
    }

    public void setNcComplaintRoleService(NcComplaintRoleService ncComplaintRoleService) {
        this.ncComplaintRoleService = ncComplaintRoleService;
    }

    public NcComplaintTypeService getNcComplaintTypeService() {
        return ncComplaintTypeService;
    }

    public void setNcComplaintTypeService(NcComplaintTypeService ncComplaintTypeService) {
        this.ncComplaintTypeService = ncComplaintTypeService;
    }

    public List<NcComplaintRole> getNcComplaintRoleList() {
        return ncComplaintRoleList;
    }

    public void setNcComplaintRoleList(List<NcComplaintRole> ncComplaintRoleList) {
        this.ncComplaintRoleList = ncComplaintRoleList;
    }

    public List<NcComplaintType> getNcComplaintTypeList() {
        return ncComplaintTypeList;
    }

    public void setNcComplaintTypeList(List<NcComplaintType> ncComplaintTypeList) {
        this.ncComplaintTypeList = ncComplaintTypeList;
    }

    public List<NcComplaintTracking> getNcComplaintTrackingList() {
        return ncComplaintTrackingList;
    }

    public void setNcComplaintTrackingList(List<NcComplaintTracking> ncComplaintTrackingList) {
        this.ncComplaintTrackingList = ncComplaintTrackingList;
    }

    public String getComplaintDateStart() {
        return complaintDateStart;
    }

    public void setComplaintDateStart(String complaintDateStart) {
        this.complaintDateStart = complaintDateStart;
    }

    public String getComplaintDateEnd() {
        return complaintDateEnd;
    }

    public void setComplaintDateEnd(String complaintDateEnd) {
        this.complaintDateEnd = complaintDateEnd;
    }

    public String getCloseTimeStart() {
        return closeTimeStart;
    }

    public void setCloseTimeStart(String closeTimeStart) {
        this.closeTimeStart = closeTimeStart;
    }

    public String getCloseTimeEnd() {
        return closeTimeEnd;
    }

    public void setCloseTimeEnd(String closeTimeEnd) {
        this.closeTimeEnd = closeTimeEnd;
    }

    public String getCurrentProcessPeople() {
        return currentProcessPeople;
    }

    public void setCurrentProcessPeople(String currentProcessPeople) {
        this.currentProcessPeople = currentProcessPeople;
    }

    public String getEntryPeople() {
        return entryPeople;
    }

    public void setEntryPeople(String entryPeople) {
        this.entryPeople = entryPeople;
    }

    public String getCompletionTimeStart() {
        return completionTimeStart;
    }

    public void setCompletionTimeStart(String completionTimeStart) {
        this.completionTimeStart = completionTimeStart;
    }

    public String getCompletionTimeEnd() {
        return completionTimeEnd;
    }

    public void setCompletionTimeEnd(String completionTimeEnd) {
        this.completionTimeEnd = completionTimeEnd;
    }

    public String getComplaintName() {
		return complaintName;
	}

	public void setComplaintName(String complaintName) {
		this.complaintName = complaintName;
	}

	public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getBelongsCenter() {
        return belongsCenter;
    }

    public void setBelongsCenter(String belongsCenter) {
        this.belongsCenter = belongsCenter;
    }

    public String getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(String treatmentType) {
        this.treatmentType = treatmentType;
    }

    public String getReplyAging() {
        return replyAging;
    }

    public void setReplyAging(String replyAging) {
        this.replyAging = replyAging;
    }

    public List<String> getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(List<String> processStatus) {
        this.processStatus = processStatus;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(Long complaintType) {
        this.complaintType = complaintType;
    }

    public OrderService getOrderServiceProxy() {
        return orderServiceProxy;
    }

    public void setOrderServiceProxy(OrderService orderServiceProxy) {
        this.orderServiceProxy = orderServiceProxy;
    }

    public OrdOrder getOrdOrder() {
        return ordOrder;
    }

    public void setOrdOrder(OrdOrder ordOrder) {
        this.ordOrder = ordOrder;
    }

    public ProdProduct getProdProduct() {
        return prodProduct;
    }

    public void setProdProduct(ProdProduct prodProduct) {
        this.prodProduct = prodProduct;
    }

    public PermUserService getPermUserService() {
        return permUserService;
    }

    public void setPermUserService(PermUserService permUserService) {
        this.permUserService = permUserService;
    }

    public PermUser getPermUser() {
        return permUser;
    }

    public void setPermUser(PermUser permUser) {
        this.permUser = permUser;
    }

    public NcComplaintTracking getNcComplaintTracking() {
        return ncComplaintTracking;
    }

    public void setNcComplaintTracking(NcComplaintTracking ncComplaintTracking) {
        this.ncComplaintTracking = ncComplaintTracking;
    }

    public ComAffix getComAffix() {
        return comAffix;
    }

    public void setComAffix(ComAffix comAffix) {
        this.comAffix = comAffix;
    }

    public ComAffixService getComAffixService() {
        return comAffixService;
    }

    public void setComAffixService(ComAffixService comAffixService) {
        this.comAffixService = comAffixService;
    }

    public String getPermId() {
        return permId;
    }

    public void setPermId(String permId) {
        this.permId = permId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getRelatedOrder() {
        return relatedOrder;
    }

    public void setRelatedOrder(String relatedOrder) {
        this.relatedOrder = relatedOrder;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetailsComplaint() {
        return detailsComplaint;
    }

    public void setDetailsComplaint(String detailsComplaint) {
        this.detailsComplaint = detailsComplaint;
    }

    public NcComplaintRemind getNcComplaintRemind() {
        return ncComplaintRemind;
    }

    public void setNcComplaintRemind(NcComplaintRemind ncComplaintRemind) {
        this.ncComplaintRemind = ncComplaintRemind;
    }

    public List<NcComplaintRemind> getNcComplaintRemindList() {
        return ncComplaintRemindList;
    }

    public void setNcComplaintRemindList(List<NcComplaintRemind> ncComplaintRemindList) {
        this.ncComplaintRemindList = ncComplaintRemindList;
    }

    public List<ComAffix> getConfirmationAffixList() {
        return confirmationAffixList;
    }

    public void setConfirmationAffixList(List<ComAffix> confirmationAffixList) {
        this.confirmationAffixList = confirmationAffixList;
    }

    public OrdRefundMentService getOrdRefundMentService() {
        return ordRefundMentService;
    }

    public void setOrdRefundMentService(OrdRefundMentService ordRefundMentService) {
        this.ordRefundMentService = ordRefundMentService;
    }

    public List<OrdRefundment> getOrdRefundmentList() {
        return ordRefundmentList;
    }

    public void setOrdRefundmentList(List<OrdRefundment> ordRefundmentList) {
        this.ordRefundmentList = ordRefundmentList;
    }

    public String getStartProcessTimeStart() {
        return startProcessTimeStart;
    }

    public void setStartProcessTimeStart(String startProcessTimeStart) {
        this.startProcessTimeStart = startProcessTimeStart;
    }

    public String getStartProcessTimeEnd() {
        return startProcessTimeEnd;
    }

    public void setStartProcessTimeEnd(String startProcessTimeEnd) {
        this.startProcessTimeEnd = startProcessTimeEnd;
    }

    public NcComplaintDuty getNcComplaintDuty() {
        return ncComplaintDuty;
    }

    public void setNcComplaintDuty(NcComplaintDuty ncComplaintDuty) {
        this.ncComplaintDuty = ncComplaintDuty;
    }

    public List<NcComplaintDutyDetails> getNcComplaintDutyDetailsList() {
        return ncComplaintDutyDetailsList;
    }

    public void setNcComplaintDutyDetailsList(List<NcComplaintDutyDetails> ncComplaintDutyDetailsList) {
        this.ncComplaintDutyDetailsList = ncComplaintDutyDetailsList;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Page<PermUser> getPermUserPage() {
        return permUserPage;
    }

    public void setPermUserPage(Page<PermUser> permUserPage) {
        this.permUserPage = permUserPage;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public List<OrdOrderItemMeta> getOrdOrderItemMetaList() {
        return ordOrderItemMetaList;
    }

    public void setOrdOrderItemMetaList(List<OrdOrderItemMeta> ordOrderItemMetaList) {
        this.ordOrderItemMetaList = ordOrderItemMetaList;
    }

    public SupplierService getSupplierService() {
        return supplierService;
    }

    public void setSupplierService(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public List<Long> getRelatedComplaints() {
        return relatedComplaints;
    }

    public void setRelatedComplaints(List<Long> relatedComplaints) {
        this.relatedComplaints = relatedComplaints;
    }

    public NcComplaintResult getNcComplaintResult() {
        return ncComplaintResult;
    }

    public void setNcComplaintResult(NcComplaintResult ncComplaintResult) {
        this.ncComplaintResult = ncComplaintResult;
    }

    public ProdProductService getProdProductService() {
        return prodProductService;
    }

    public void setProdProductService(ProdProductService prodProductService) {
        this.prodProductService = prodProductService;
    }

    public Boolean getView() {
        return view;
    }

    public void setView(Boolean view) {
        this.view = view;
    }

    public OrdOrder getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrdOrder orderDetail) {
        this.orderDetail = orderDetail;
    }

    public ComLogService getComLogService() {
        return comLogService;
    }

    public void setComLogService(ComLogService comLogService) {
        this.comLogService = comLogService;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getSelectUserType() {
        return selectUserType;
    }

    public void setSelectUserType(String selectUserType) {
        this.selectUserType = selectUserType;
    }

    public String getTransferComplaintUserNames() {
        return transferComplaintUserNames;
    }

    public void setTransferComplaintUserNames(String transferComplaintUserNames) {
        this.transferComplaintUserNames = transferComplaintUserNames;
    }

    public String getComplaintIds() {
        return complaintIds;
    }

    public void setComplaintIds(String complaintIds) {
        this.complaintIds = complaintIds;
    }

    public SmsRemoteService getSmsRemoteService() {
        return smsRemoteService;
    }

    public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
        this.smsRemoteService = smsRemoteService;
    }

    public List<SmsContentLog> getContentLogsList() {
        return contentLogsList;
    }

    public void setContentLogsList(List<SmsContentLog> contentLogsList) {
        this.contentLogsList = contentLogsList;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getProcessMessage() {
        return processMessage;
    }

    public void setProcessMessage(String processMessage) {
        this.processMessage = processMessage;
    }

    public String getVisitorEmail() {
        return visitorEmail;
    }

    public void setVisitorEmail(String visitorEmail) {
        this.visitorEmail = visitorEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FSClient getFsClient() {
        return fsClient;
    }

    public void setFsClient(FSClient fsClient) {
        this.fsClient = fsClient;
    }

    public String getContentEmail() {
        return contentEmail;
    }

    public void setContentEmail(String contentEmail) {
        this.contentEmail = contentEmail;
    }

    public EmailService getEmailRemoteService() {
        return emailRemoteService;
    }

    public void setEmailRemoteService(EmailService emailRemoteService) {
        this.emailRemoteService = emailRemoteService;
    }

    public EmailClient getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(EmailClient emailClient) {
        this.emailClient = emailClient;
    }

    public List<EmailContent> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<EmailContent> emailList) {
        this.emailList = emailList;
    }

    public NcComplaintRelationService getNcComplaintRelationService() {
        return ncComplaintRelationService;
    }

    public void setNcComplaintRelationService(
            NcComplaintRelationService ncComplaintRelationService) {
        this.ncComplaintRelationService = ncComplaintRelationService;
    }

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public List<SmsContent> getContentsList() {
        return contentsList;
    }

    public void setContentsList(List<SmsContent> contentsList) {
        this.contentsList = contentsList;
    }

	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

    public String getComplaintTypeCnName() {
        return complaintTypeCnName;
    }

    public void setComplaintTypeCnName(String complaintTypeCnName) {
        this.complaintTypeCnName = complaintTypeCnName;
    }

    public String getBelongsCenterCnName() {
        return belongsCenterCnName;
    }

    public void setBelongsCenterCnName(String belongsCenterCnName) {
        this.belongsCenterCnName = belongsCenterCnName;
    }

    public String getComplaintDutyType() {
        return complaintDutyType;
    }

    public void setComplaintDutyType(String complaintDutyType) {
        this.complaintDutyType = complaintDutyType;
    }

    public NcComplaintDuty getNcComplaintDutyReparation() {
        return ncComplaintDutyReparation;
    }

    public void setNcComplaintDutyReparation(NcComplaintDuty ncComplaintDutyReparation) {
        this.ncComplaintDutyReparation = ncComplaintDutyReparation;
    }

    public List<NcComplaintDutyDetails> getNcComplaintDutyDetailsReparationList() {
        return ncComplaintDutyDetailsReparationList;
    }

    public void setNcComplaintDutyDetailsReparationList(List<NcComplaintDutyDetails> ncComplaintDutyDetailsReparationList) {
        this.ncComplaintDutyDetailsReparationList = ncComplaintDutyDetailsReparationList;
    }

    public PermOrganizationService getPermOrganizationService() {
        return permOrganizationService;
    }

    public void setPermOrganizationService(PermOrganizationService permOrganizationService) {
        this.permOrganizationService = permOrganizationService;
    }

    public void setVstOrdOrderService(VstOrdOrderService vstOrdOrderService) {
		this.vstOrdOrderService = vstOrdOrderService;
	}

	public void setVstProdProductService(VstProdProductService vstProdProductService) {
		this.vstProdProductService = vstProdProductService;
	}

	public void setVstSuppSupplierService(
			VstSuppSupplierService vstSuppSupplierService) {
		this.vstSuppSupplierService = vstSuppSupplierService;
	}

	public List<Long> getRelatedOrders() {
        return relatedOrders;
    }

    public void setRelatedOrders(List<Long> relatedOrders) {
        this.relatedOrders = relatedOrders;
    }

	public String getSysCode() {
		return sysCode;
	}

	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

	
}
