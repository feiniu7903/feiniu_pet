package com.lvmama.elong.model;

import javax.xml.bind.annotation.XmlEnumValue;

import com.itextpdf.text.log.SysoLogger;

public enum EnumOrderStatusDescription {
    A("已确认"),
    B("用户未入住"),
    //B("NO SHOW"),
    B1("有预定未查到"),
    B2("待查"),
    B3("暂不确定"),
    C("已结帐"),
    D("已删除"),
    //D("删除"),
    E("已取消"),
    //E("取消"),
    F("已入住"),
    G("变价"),
    H("变更"),
    I("大单"),
    J("仅酒店已确认"),
    M("恶意"),
    N("已提交"),
    //N("新单"),
    O("满房"),
    P("暂无价格"),
    R("预付"),
    S("特殊"),
    T("计划中"),
    U("特殊满房"),
    V("已审"),
    W("虚拟"),
    Z("删除,另换酒店");
    private final String value;

    EnumOrderStatusDescription(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumOrderStatusDescription fromValue(String v) {
        for (EnumOrderStatusDescription c: EnumOrderStatusDescription.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
    
    public static void main(String[] args) {
		System.out.println(EnumOrderStatusDescription.A.toString() instanceof String);
		System.out.println(EnumOrderStatusDescription.valueOf("A").value());
	}
}
