/**
 * 
 */
package com.lvmama.back.sweb.invoice;

import com.lvmama.comm.bee.po.ord.OrdInvoice;


/**
 * 判断能否对状态修改.
 * @author yangbin
 *
 */
public interface InvoiceComp {

	/**
	 * 回调的判断操作.
	 * @param ordInvoice
	 * @return
	 */
	boolean hasChangeAble(OrdInvoice ordInvoice);
}
