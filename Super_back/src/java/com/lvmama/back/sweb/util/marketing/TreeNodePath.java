package com.lvmama.back.sweb.util.marketing;
/**
 * TreeNodePath.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class TreeNodePath  implements java.io.Serializable {
    private java.lang.String treePath;

    private java.lang.String treeNode;

    public TreeNodePath() {
    }

    public TreeNodePath(
           java.lang.String treePath,
           java.lang.String treeNode) {
           this.treePath = treePath;
           this.treeNode = treeNode;
    }


    /**
     * Gets the treePath value for this TreeNodePath.
     * 
     * @return treePath
     */
    public java.lang.String getTreePath() {
        return treePath;
    }


    /**
     * Sets the treePath value for this TreeNodePath.
     * 
     * @param treePath
     */
    public void setTreePath(java.lang.String treePath) {
        this.treePath = treePath;
    }


    /**
     * Gets the treeNode value for this TreeNodePath.
     * 
     * @return treeNode
     */
    public java.lang.String getTreeNode() {
        return treeNode;
    }


    /**
     * Sets the treeNode value for this TreeNodePath.
     * 
     * @param treeNode
     */
    public void setTreeNode(java.lang.String treeNode) {
        this.treeNode = treeNode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TreeNodePath)) return false;
        TreeNodePath other = (TreeNodePath) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.treePath==null && other.getTreePath()==null) || 
             (this.treePath!=null &&
              this.treePath.equals(other.getTreePath()))) &&
            ((this.treeNode==null && other.getTreeNode()==null) || 
             (this.treeNode!=null &&
              this.treeNode.equals(other.getTreeNode())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getTreePath() != null) {
            _hashCode += getTreePath().hashCode();
        }
        if (getTreeNode() != null) {
            _hashCode += getTreeNode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TreeNodePath.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "TreeNodePath"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("treePath");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "treePath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("treeNode");
        elemField.setXmlName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "treeNode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
