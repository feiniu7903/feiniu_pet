package com.lvmama.back.web.utils.insurance.pinan;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BasePersonnelInfo {
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
	
	protected String personnelName;
	protected String sexCode;
	protected String certificateType;
	protected String certificateNo;
	protected Date birthday;
	
	public BasePersonnelInfo(String name, String sex, String cType, String cNo, Date birthday) {
		this.personnelName = name;
		this.sexCode = sex;
		this.certificateType = cType;
		this.certificateNo = cNo;
		this.birthday = birthday;
	}
}
