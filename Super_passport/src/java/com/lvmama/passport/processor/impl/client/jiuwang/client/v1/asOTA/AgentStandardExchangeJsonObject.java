
package com.lvmama.passport.processor.impl.client.jiuwang.client.v1.asOTA;

import org.codehaus.jackson.annotate.JsonIgnore;

public class AgentStandardExchangeJsonObject {

    private String data;
    private String signed;
    private String securityType;
    @JsonIgnore
    private String planString;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSigned() {
        return signed;
    }

    public void setSigned(String signed) {
        this.signed = signed;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getPlanString() {
        return planString;
    }

    public void setPlanString(String planString) {
        this.planString = planString;
    }
}
