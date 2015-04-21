package com.lvmama.comm.bee.service.prod;

import com.lvmama.comm.bee.po.prod.ProdAvailableBonus;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-5-4<p/>
 * Time: 上午11:29<p/>
 * Email:kouhongyu@163.com<p/>
 */
public interface ProdAvailableBonusService {

    public ProdAvailableBonus getProdAvailableBonusByMainProductType(String type);
    public ProdAvailableBonus getProdAvailableBonusBySubProductType(String type);
}
