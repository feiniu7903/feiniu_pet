package com.lvmama.passport.processor.impl.client.gmedia.model;

import java.util.List;

public class Vouchers {
	private List<Voucher> voucher;
	
	public String toResponseVouchersXml() {
		StringBuilder buf = new StringBuilder();
		for (Voucher voucher : this.voucher) {
			buf.append(voucher.toResponseVoucherXml());
		}
		return buf.toString();
	}
	public List<Voucher> getVoucher() {
		return voucher;
	}

	public void setVoucher(List<Voucher> voucher) {
		this.voucher = voucher;
	}

}
