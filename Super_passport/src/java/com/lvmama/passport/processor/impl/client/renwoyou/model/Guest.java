package com.lvmama.passport.processor.impl.client.renwoyou.model;

public class Guest {
		private String guestName;//客人姓名，当为实名制景点时，必填；
		private String certType;//证件类型，身份证01 , 军官证02 , 护照03 , 户口簿04 , 回乡证05 , 其他06，默认身份证；
		private String certNo;//证件号，当为实名制景点时，必填；
		private String gender;//性别，“MALE”：男，“FEMALE”：女，非必填，预留；
		private String birthday;//生日yyyy-MM-dd格式，非必填，预留；
		private String isChild;//是否为小孩，“1”：小孩，“0”：非小孩，非必填，预留。
		public String getGuestName() {
			return guestName;
		}
		public void setGuestName(String guestName) {
			this.guestName = guestName;
		}
		public String getCertType() {
			return certType;
		}
		public void setCertType(String certType) {
			this.certType = certType;
		}
		public String getCertNo() {
			return certNo;
		}
		public void setCertNo(String certNo) {
			this.certNo = certNo;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public String getBirthday() {
			return birthday;
		}
		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
		public String getIsChild() {
			return isChild;
		}
		public void setIsChild(String isChild) {
			this.isChild = isChild;
		}
}
