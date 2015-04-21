package com.lvmama.prd.service;

import com.lvmama.comm.bee.po.prod.ProdAvailableBonus;
import com.lvmama.comm.bee.service.prod.ProdAvailableBonusService;
import com.lvmama.prd.dao.ProdAvailableBonusDAO;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-5-4<p/>
 * Time: 上午11:30<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class ProdAvailableBonusServiceImpl implements ProdAvailableBonusService {

    private ProdAvailableBonusDAO prodAvailableBonusDAO;



    public ProdAvailableBonusDAO getProdAvailableBonusDAO() {
        return prodAvailableBonusDAO;
    }

    public void setProdAvailableBonusDAO(ProdAvailableBonusDAO prodAvailableBonusDAO) {
        this.prodAvailableBonusDAO = prodAvailableBonusDAO;
    }

    public ProdAvailableBonus getProdAvailableBonusByMainProductType(String type) {
        return prodAvailableBonusDAO.selectProdAvailableBonusByMainProductType(type);
    }

    public ProdAvailableBonus getProdAvailableBonusBySubProductType(String type) {
        return prodAvailableBonusDAO.selectProdAvailableBonusBySubProductType(type);
    }
}
