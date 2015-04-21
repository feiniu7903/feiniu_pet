/**
 *
 */
package com.lvmama.back.sweb.prod;

import com.lvmama.comm.bee.po.prod.ProdTicket;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.vo.Constant;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.util.List;

/**
 * @author yangbin
 */
@Results({
        @Result(name = "input", location = "/WEB-INF/pages/back/prod/base/ticket_product.jsp"),
        @Result(name = "auditingShow", location = "/WEB-INF/pages/back/prod/auditing/ticket_product_auditing_show.jsp")
})
public class ProdTicketAction extends ProdProductAction<ProdTicket> {

    /**
     *
     */
    private static final long serialVersionUID = -8894575841059978201L;

    @Override
    @Action("/prod/toAddTicketProduct")
    public String goResult() {
        //product=new ProdTicket();
        return goAfter();
    }

    @Override
    @Action(value = "/prod/editTicketProduct")
    public String goEdit() {
        if (!doBefore()) {
            return PRODUCT_EXCEPTION_PAGE;
        }
        initEditProduct();
        return goAfter();
    }

    @Override
    @Action("/prod/saveTicketProduct")
    public void save() {
        // TODO Auto-generated method stub
        saveProduct();
    }

    public ProdTicketAction() {
        super(Constant.PRODUCT_TYPE.TICKET);
        addFields("physical");
    }

    public List<CodeItem> getSubProductTypeList() {
        return ProductUtil.getTicketSubTypeList();
    }


    public List<CodeItem> getRegionNamesList() {
        return ProductUtil.getregionNamesMPJD();
    }


}
