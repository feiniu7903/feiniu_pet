package com.lvmama.back.remote.impl;

import com.lvmama.back.remote.PriceUpdateJobService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.pub.TaskResult;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.spring.SpringBeanProxy;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-5-15<p/>
 * Time: 下午8:16<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class PriceUpdateJobServiceImpl implements PriceUpdateJobService, Serializable {


    private static final long serialVersionUID = -3729763830375909158L;
    protected final Log log = LogFactory.getLog(PriceUpdateJobServiceImpl.class);
    private ProdProductService prodProductService;
    private ProdProductBranchService prodProductBranchService;

    public TaskResult execute(Long logId, String parameter) throws Exception {
        TaskResult result = new TaskResult();

        prodProductService = (ProdProductService) SpringBeanProxy.getBean("prodProductService");
        prodProductBranchService = (ProdProductBranchService) SpringBeanProxy.getBean("prodProductBranchService");

        long begin = System.currentTimeMillis();
        updateAllProdBranch();//首先更新类别的价格
        updateAllProduct();
        log.info("Update product sell_price and market_price. spent :" + (System.currentTimeMillis() - begin));

        result.setStatus(1);//完成

        return result;
    }

    private void updateAllProduct() {
        int page = 1;
        while (true) {
            Page<Long> pp = prodProductService.selectAllProductId(pageSize, page);
            if (CollectionUtils.isEmpty(pp.getItems())) {
                break;
            }
            for (Long productId : pp.getItems()) {
                try {
                    prodProductService.updatePriceByProductId(productId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pp.hasNext()) {
                page++;
            } else {
                break;
            }
        }
    }

    final long pageSize = 800;

    //更新所有的类别销售价
    private void updateAllProdBranch() {
        long page = 1;
        while (true) {
            Page<Long> pp = prodProductBranchService.selectAllBranchId(pageSize, page);
            if (CollectionUtils.isEmpty(pp.getItems())) {
                break;
            }
            for (Long prodBranchId : pp.getItems()) {
                try {
                    //新的销售价=销售价-最优惠的早定早惠
                    prodProductBranchService.updatePriceByBranchId(prodBranchId);
                } catch (Exception ex) {
                    log.error(ex, ex);
                    log.info("invalid number prodBranchId:" + prodBranchId);
                }
            }

            if (pp.hasNext()) {
                page++;
            } else {
                break;
            }
        }
    }
}
