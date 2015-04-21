package com.lvmama.back.web.utils.insurance.alianz;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.AESCode;

public class AlianzPolicyRecord {
	private String seq = "0001";
	private String productId;
	private String effDate;
	private String isBaby = "N";
	private String phFn; // 姓
	private String phLn; // 名
	private String phGender; // 性别
	private String phBirth;
	private String phCountry = "CN";
	private String phIdtype = "9";
	private String phId;
	private String phAdd1 = "31";
	private String phAdd2 = "310114";
	private String phAdd3 = "金沙江路3131号创意园区3号楼";
	private String phPost = "201824";
	private String phMobile;
	private String phEmail = "admin@lvmama.com";
	private String rel = "SLF";
	private String insOne = "Y";
	private String reserved1 = "驴妈妈";
	private String Signature;

	public AlianzPolicyRecord(final String productId, final String effDate,
			final String phFn, final String phLn, final String phGender,
			final String phId, final String phMobile) throws Exception {
		if (StringUtils.isEmpty(productId) || StringUtils.isEmpty(effDate)
				|| StringUtils.isEmpty(phFn) || StringUtils.isEmpty(phLn)
				|| StringUtils.isEmpty(phGender) || StringUtils.isEmpty(phId)
				|| StringUtils.isEmpty(phMobile)) {
			throw new IllegalArgumentException("所输入的参数有不合法的Null值或空值");
		}
		if (!phGender.equalsIgnoreCase("F") && !phGender.equalsIgnoreCase("M")) {
			throw new IllegalArgumentException("投保人性别只能为M/F");
		}
		this.productId = productId;
		this.effDate = effDate;
		this.phFn = phFn;
		this.phLn = phLn;
		this.phGender = phGender.toUpperCase();
		this.phId = phId;
		this.phMobile = phMobile;
		calBirthday();
		this.Signature = AESCode.encrypt("goodluck",this.productId + this.effDate + this.phId + this.phId).replaceAll("\\s", "");
	}

	private void calBirthday() {
		if (phId.length() == 15) {
			this.phBirth = "19" + phId.substring(6, 12);
		} else {
			if (phId.length() == 18) {
				this.phBirth = phId.substring(6, 14);
			}
		}
	}

}
