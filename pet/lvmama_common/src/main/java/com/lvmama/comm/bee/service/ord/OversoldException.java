package com.lvmama.comm.bee.service.ord;

/**
 * 商品库存超卖异常
 * 
 * @author taiqichao
 * 
 */
public class OversoldException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OversoldException(String msg) {
		super(msg);
	}

	public OversoldException(String msg, Throwable t) {
		super(msg, t);
	}
}
