package com.lvmama.eplace.web.lvmama;

import com.lvmama.ZkBaseAction;

/**
 * 
 * @author luoyinqi
 *
 */

public class ListShowMemoAction extends ZkBaseAction {

	private String faxMemo;
	
	public void doBefore() throws Exception {
		if(faxMemo==null||"".equals(faxMemo)){
			faxMemo="备注为空";
		}
	}

	public String getFaxMemo() {
		return faxMemo;
	}

	public void setFaxMemo(String faxMemo) {
		this.faxMemo = faxMemo;
	}
}
