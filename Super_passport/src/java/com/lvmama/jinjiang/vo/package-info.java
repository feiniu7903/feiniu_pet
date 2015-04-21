package com.lvmama.jinjiang.vo;

/**业务类型*/
enum BUSINESSCATEGROY{
	DOMESTIC("国内"),
	OUTBOUND("出境"),
	INBOUND("入境");
	private String cnName;
	BUSINESSCATEGROY(String name){
		this.cnName=name;
	}
	public String getCode(){
		return this.name();
	}
	public String getCnName(){
		return this.cnName;
	}
	public static String getCnName(String code){
		for(BUSINESSCATEGROY item : BUSINESSCATEGROY.values()){
			if(item.getCode().equals(code))
			{
				return item.getCnName();
			}
		}
		return code;
	}
	public String toString(){
		return this.name();
	}
}

/**线路状态*/
enum LINESTATUS{
	ON("上架"),
	AUDITING("上架审核"),
	OFF("下架");
	private String cnName;
	LINESTATUS(String name){
		this.cnName=name;
	}
	public String getCode(){
		return this.name();
	}
	public String getCnName(){
		return this.cnName;
	}
	public static String getCnName(String code){
		for(LINESTATUS item : LINESTATUS.values()){
			if(item.getCode().equals(code))
			{
				return item.getCnName();
			}
		}
		return code;
	}
	public String toString(){
		return this.name();
	}
}