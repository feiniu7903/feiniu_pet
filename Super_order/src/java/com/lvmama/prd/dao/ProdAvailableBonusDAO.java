package com.lvmama.prd.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdAvailableBonus;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-5-4<p/>
 * Time: 上午11:37<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class ProdAvailableBonusDAO  extends BaseIbatisDAO {




    public ProdAvailableBonus selectProdAvailableBonusByMainProductType(String type) {
        return (ProdAvailableBonus) super.queryForObject("PROD_AVAILABLE_BONUS.selectProdAvailableBonusByMainProductType", type);
    }

    public ProdAvailableBonus selectProdAvailableBonusBySubProductType(String type) {
        return (ProdAvailableBonus) super.queryForObject("PROD_AVAILABLE_BONUS.selectProdAvailableBonusBySubProductType", type);
    }
}
