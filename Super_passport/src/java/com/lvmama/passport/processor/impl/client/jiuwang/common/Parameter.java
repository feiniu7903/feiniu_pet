package com.lvmama.passport.processor.impl.client.jiuwang.common;

import java.io.Serializable;

public class Parameter implements Serializable, Comparable<Parameter> {

    private static final long serialVersionUID = 2721340807561333705L;

    private String name;
    private String value;

    public Parameter() {
        super();
    }

    public Parameter(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        //对象不为空
        if (obj == null) {
            return false;
        }
        //hashCode相等
        if (this == obj) {
            return true;
        }

        if (obj instanceof Parameter) {
            Parameter param = (Parameter) obj;
            return this.getName().equals(param.getName())
                    && this.getValue().equals(param.getValue());
        }

        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int compareTo(Parameter arg0) {
        return 0;
    }

}