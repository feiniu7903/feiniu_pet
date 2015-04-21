
package com.lvmama.pet.vo;

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XObject;

@XObject("direct_trade_create_res")
public class AlipayWAPDirectTradeCreateRes {

    /**
     * 获得的创建交易的RequestToken
     */
    @XNode("request_token")
    private String requestToken;

    public String getRequestToken() {
        return requestToken;
    }
}
