package com.lvmama.comm.bee.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.prod.BounsReturnScale;

/**
 * 
 * @author zuoxiaoshuai
 *
 */
public interface BounsReturnScaleService {

    public List<BounsReturnScale> getAll();
    
    public BounsReturnScale getBonusScaleByType(Map<String, String> param);
}
