package com.lvmama.back.sweb.util.marketing;
/**
 * JobManagementServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

public class JobManagementServiceStub extends org.apache.axis.client.Stub implements JobManagementServiceSoap_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[9];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("writeListFiles");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "report"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportRef"), ReportRef.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "reportParams"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportParams"), ReportParams.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "segmentPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "treeNodePath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "TreeNodePath"), TreeNodePath.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "segmentationOptions"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SegmentationOptions"), SegmentationOptions.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "filesystem"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "timeout"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "JobInfo"));
        oper.setReturnClass(JobInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "jobInfo"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getJobInfo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "jobID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "JobInfo"));
        oper.setReturnClass(JobInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "jobInfo"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("cancelJob");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "jobID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "integer"), java.math.BigInteger.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "JobInfo"));
        oper.setReturnClass(JobInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "jobInfo"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getCounts");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "segmentPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "treePath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "segmentationOptions"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SegmentationOptions"), SegmentationOptions.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "JobInfo"));
        oper.setReturnClass(JobInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "jobInfo"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getPromptedColumns");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "segmentPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "treePath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "PromptedColumnInfo"));
        oper.setReturnClass(java.lang.String[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ColumnInfo"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "Columns"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("purgeCache");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "segmentPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "treePath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ignoreCacheRef"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), java.lang.Boolean.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "JobInfo"));
        oper.setReturnClass(JobInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "jobInfo"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("prepareCache");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "segmentPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "treePath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "refresh"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), java.lang.Boolean.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "JobInfo"));
        oper.setReturnClass(JobInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "jobInfo"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("saveResultSet");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "segmentPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "treeNodePath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "TreeNodePath"), TreeNodePath.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "savedSegmentPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "segmentationOptions"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SegmentationOptions"), SegmentationOptions.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SRCustomLabel"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "appendStaticSegment"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), java.lang.Boolean.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "JobInfo"));
        oper.setReturnClass(JobInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "jobInfo"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("deleteResultSet");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "targetLevel"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "GUIDs"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "arrayOfGUIDs"), java.lang.String[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "GUID"));
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "segmentPath"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sessionID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "JobInfo"));
        oper.setReturnClass(JobInfo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "jobInfo"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

    }

    public JobManagementServiceStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public JobManagementServiceStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public JobManagementServiceStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        addBindings0();
        addBindings1();
        addBindings2();
    }

    private void addBindings0() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">addReportToPage");
            cachedSerQNames.add(qName);
            cls = AddReportToPage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">addReportToPageResult");
            cachedSerQNames.add(qName);
            cls = AddReportToPageResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">applyReportDefaults");
            cachedSerQNames.add(qName);
            cls = ApplyReportDefaults.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">applyReportDefaultsResult");
            cachedSerQNames.add(qName);
            cls = ApplyReportDefaultsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">applyReportParams");
            cachedSerQNames.add(qName);
            cls = ApplyReportParams.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">applyReportParamsResult");
            cachedSerQNames.add(qName);
            cls = ApplyReportParamsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">cancelJob");
            cachedSerQNames.add(qName);
            cls = CancelJob.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">cancelJobResult");
            cachedSerQNames.add(qName);
            cls = CancelJobResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">cancelQuery");
            cachedSerQNames.add(qName);
            cls = CancelQuery.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">cancelQueryResult");
            cachedSerQNames.add(qName);
            cls = CancelQueryResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">clearQueryCache");
            cachedSerQNames.add(qName);
            cls = ClearQueryCache.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">clearQueryCacheResult");
            cachedSerQNames.add(qName);
            cls = ClearQueryCacheResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">copyItem");
            cachedSerQNames.add(qName);
            cls = CopyItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">copyItem2");
            cachedSerQNames.add(qName);
            cls = CopyItem2.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">copyItem2Result");
            cachedSerQNames.add(qName);
            cls = CopyItem2Result.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">copyItemResult");
            cachedSerQNames.add(qName);
            cls = CopyItemResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">createFolder");
            cachedSerQNames.add(qName);
            cls = CreateFolder.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">createFolderResult");
            cachedSerQNames.add(qName);
            cls = CreateFolderResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">createLink");
            cachedSerQNames.add(qName);
            cls = CreateLink.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">createLinkResult");
            cachedSerQNames.add(qName);
            cls = CreateLinkResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">deleteIBot");
            cachedSerQNames.add(qName);
            cls = DeleteIBot.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">deleteIBotResult");
            cachedSerQNames.add(qName);
            cls = DeleteIBotResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">deleteItem");
            cachedSerQNames.add(qName);
            cls = DeleteItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">deleteItemResult");
            cachedSerQNames.add(qName);
            cls = DeleteItemResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">deleteResultSet");
            cachedSerQNames.add(qName);
            cls = DeleteResultSet.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">deleteResultSetResult");
            cachedSerQNames.add(qName);
            cls = DeleteResultSetResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">describeColumn");
            cachedSerQNames.add(qName);
            cls = DescribeColumn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">describeColumnResult");
            cachedSerQNames.add(qName);
            cls = DescribeColumnResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">describeSubjectArea");
            cachedSerQNames.add(qName);
            cls = DescribeSubjectArea.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">describeSubjectAreaResult");
            cachedSerQNames.add(qName);
            cls = DescribeSubjectAreaResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">describeTable");
            cachedSerQNames.add(qName);
            cls = DescribeTable.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">describeTableResult");
            cachedSerQNames.add(qName);
            cls = DescribeTableResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">endPage");
            cachedSerQNames.add(qName);
            cls = EndPage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">endPageResult");
            cachedSerQNames.add(qName);
            cls = EndPageResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">evaluateCondition");
            cachedSerQNames.add(qName);
            cls = EvaluateCondition.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">evaluateConditionResult");
            cachedSerQNames.add(qName);
            cls = EvaluateConditionResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">evaluateInlineCondition");
            cachedSerQNames.add(qName);
            cls = EvaluateInlineCondition.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">evaluateInlineConditionResult");
            cachedSerQNames.add(qName);
            cls = EvaluateInlineConditionResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">executeIBotNow");
            cachedSerQNames.add(qName);
            cls = ExecuteIBotNow.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">executeIBotNowResult");
            cachedSerQNames.add(qName);
            cls = ExecuteIBotNowResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">executeSQLQuery");
            cachedSerQNames.add(qName);
            cls = ExecuteSQLQuery.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">executeSQLQueryResult");
            cachedSerQNames.add(qName);
            cls = ExecuteSQLQueryResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">executeXMLQuery");
            cachedSerQNames.add(qName);
            cls = ExecuteXMLQuery.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">executeXMLQueryResult");
            cachedSerQNames.add(qName);
            cls = ExecuteXMLQueryResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">export");
            cachedSerQNames.add(qName);
            cls = Export.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">exportResult");
            cachedSerQNames.add(qName);
            cls = ExportResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">fetchNext");
            cachedSerQNames.add(qName);
            cls = FetchNext.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">fetchNextResult");
            cachedSerQNames.add(qName);
            cls = FetchNextResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">forgetAccounts");
            cachedSerQNames.add(qName);
            cls = ForgetAccounts.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">forgetAccountsResult");
            cachedSerQNames.add(qName);
            cls = ForgetAccountsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">generateReportSQL");
            cachedSerQNames.add(qName);
            cls = GenerateReportSQL.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">generateReportSQLResult");
            cachedSerQNames.add(qName);
            cls = GenerateReportSQLResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getAccounts");
            cachedSerQNames.add(qName);
            cls = GetAccounts.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getAccountsResult");
            cachedSerQNames.add(qName);
            cls = Account[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "Account");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "accountDetails");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getAccountTenantID");
            cachedSerQNames.add(qName);
            cls = GetAccountTenantID.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getAccountTenantIDResult");
            cachedSerQNames.add(qName);
            cls = GetAccountTenantIDResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getBIPSVariables");
            cachedSerQNames.add(qName);
            cls = GetBIPSVariables.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getBIPSVariablesResult");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "return");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getCommonBodyHtml");
            cachedSerQNames.add(qName);
            cls = GetCommonBodyHtml.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getCommonBodyHtmlResult");
            cachedSerQNames.add(qName);
            cls = GetCommonBodyHtmlResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getConditionCustomizableReportElements");
            cachedSerQNames.add(qName);
            cls = GetConditionCustomizableReportElements.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getConditionCustomizableReportElementsResult");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "customizableElement");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getCounts");
            cachedSerQNames.add(qName);
            cls = GetCounts.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getCountsResult");
            cachedSerQNames.add(qName);
            cls = GetCountsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getCurUser");
            cachedSerQNames.add(qName);
            cls = GetCurUser.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getCurUserResult");
            cachedSerQNames.add(qName);
            cls = GetCurUserResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getGlobalPrivilegeACL");
            cachedSerQNames.add(qName);
            cls = GetGlobalPrivilegeACL.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getGlobalPrivilegeACLResult");
            cachedSerQNames.add(qName);
            cls = GetGlobalPrivilegeACLResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getGlobalPrivileges");
            cachedSerQNames.add(qName);
            cls = GetGlobalPrivileges.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getGlobalPrivilegesResult");
            cachedSerQNames.add(qName);
            cls = Privilege[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "Privilege");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sawPrivileges");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getGroups");
            cachedSerQNames.add(qName);
            cls = GetGroups.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getGroupsResult");
            cachedSerQNames.add(qName);
            cls = Account[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "Account");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "account");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getHeadersHtml");
            cachedSerQNames.add(qName);
            cls = GetHeadersHtml.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getHeadersHtmlResult");
            cachedSerQNames.add(qName);
            cls = GetHeadersHtmlResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getHtmlForPageWithOneReport");
            cachedSerQNames.add(qName);
            cls = GetHtmlForPageWithOneReport.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getHtmlForPageWithOneReportResult");
            cachedSerQNames.add(qName);
            cls = GetHtmlForPageWithOneReportResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getHtmlForReport");
            cachedSerQNames.add(qName);
            cls = GetHtmlForReport.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getHtmlForReportResult");
            cachedSerQNames.add(qName);
            cls = GetHtmlForReportResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getItemInfo");
            cachedSerQNames.add(qName);
            cls = GetItemInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getItemInfoResult");
            cachedSerQNames.add(qName);
            cls = GetItemInfoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getJobInfo");
            cachedSerQNames.add(qName);
            cls = GetJobInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getJobInfoResult");
            cachedSerQNames.add(qName);
            cls = GetJobInfoResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getMembers");
            cachedSerQNames.add(qName);
            cls = GetMembers.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getMembersResult");
            cachedSerQNames.add(qName);
            cls = Account[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "Account");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "account");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getPermissions");
            cachedSerQNames.add(qName);
            cls = GetPermissions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getPermissionsResult");
            cachedSerQNames.add(qName);
            cls = int[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "return");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getPrivilegesStatus");
            cachedSerQNames.add(qName);
            cls = GetPrivilegesStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getPrivilegesStatusResult");
            cachedSerQNames.add(qName);
            cls = boolean[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "return");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getPromptedColumns");
            cachedSerQNames.add(qName);
            cls = GetPromptedColumns.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getPromptedColumnsResult");
            cachedSerQNames.add(qName);
            cls = GetPromptedColumnsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getPromptedFilters");
            cachedSerQNames.add(qName);
            cls = GetPromptedFilters.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getPromptedFiltersResult");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "promptedFilter");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getSessionEnvironment");
            cachedSerQNames.add(qName);
            cls = GetSessionEnvironment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getSessionEnvironmentResult");
            cachedSerQNames.add(qName);
            cls = GetSessionEnvironmentResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getSessionVariables");
            cachedSerQNames.add(qName);
            cls = GetSessionVariables.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getSessionVariablesResult");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "return");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getSubItems");
            cachedSerQNames.add(qName);
            cls = GetSubItems.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getSubItemsResult");
            cachedSerQNames.add(qName);
            cls = ItemInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ItemInfo");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "itemInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getSubjectAreas");
            cachedSerQNames.add(qName);
            cls = GetSubjectAreas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">getSubjectAreasResult");
            cachedSerQNames.add(qName);
            cls = SASubjectArea[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SASubjectArea");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "subjectArea");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    }
    private void addBindings1() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">impersonate");
            cachedSerQNames.add(qName);
            cls = Impersonate.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">impersonateex");
            cachedSerQNames.add(qName);
            cls = Impersonateex.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">impersonateexResult");
            cachedSerQNames.add(qName);
            cls = ImpersonateexResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">impersonateResult");
            cachedSerQNames.add(qName);
            cls = ImpersonateResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">import");
            cachedSerQNames.add(qName);
            cls = _import.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">importResult");
            cachedSerQNames.add(qName);
            cls = ImportError[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ImportError");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "error");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">isMember");
            cachedSerQNames.add(qName);
            cls = IsMember.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">isMemberResult");
            cachedSerQNames.add(qName);
            cls = IsMemberResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">JobInfo>detailedInfo");
            cachedSerQNames.add(qName);
            cls = JobInfoDetailedInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">JobStats>jobState");
            cachedSerQNames.add(qName);
            cls = JobStatsJobState.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">joinGroups");
            cachedSerQNames.add(qName);
            cls = JoinGroups.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">joinGroupsResult");
            cachedSerQNames.add(qName);
            cls = JoinGroupsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">keepAlive");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "sessionID");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">keepAliveResult");
            cachedSerQNames.add(qName);
            cls = KeepAliveResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">leaveGroups");
            cachedSerQNames.add(qName);
            cls = LeaveGroups.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">leaveGroupsResult");
            cachedSerQNames.add(qName);
            cls = LeaveGroupsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">logoff");
            cachedSerQNames.add(qName);
            cls = Logoff.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">logoffResult");
            cachedSerQNames.add(qName);
            cls = LogoffResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">logon");
            cachedSerQNames.add(qName);
            cls = Logon.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">logonex");
            cachedSerQNames.add(qName);
            cls = Logonex.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">logonexResult");
            cachedSerQNames.add(qName);
            cls = LogonexResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">logonResult");
            cachedSerQNames.add(qName);
            cls = LogonResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">maintenanceMode");
            cachedSerQNames.add(qName);
            cls = MaintenanceMode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">maintenanceModeResult");
            cachedSerQNames.add(qName);
            cls = MaintenanceModeResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">markForReplication");
            cachedSerQNames.add(qName);
            cls = MarkForReplication.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">markForReplicationResult");
            cachedSerQNames.add(qName);
            cls = MarkForReplicationResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">moveIBot");
            cachedSerQNames.add(qName);
            cls = MoveIBot.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">moveIBotResult");
            cachedSerQNames.add(qName);
            cls = MoveIBotResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">moveItem");
            cachedSerQNames.add(qName);
            cls = MoveItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">moveItemResult");
            cachedSerQNames.add(qName);
            cls = MoveItemResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">pasteItem2");
            cachedSerQNames.add(qName);
            cls = PasteItem2.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">pasteItem2Result");
            cachedSerQNames.add(qName);
            cls = PasteItem2Result.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">prepareCache");
            cachedSerQNames.add(qName);
            cls = PrepareCache.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">prepareCacheResult");
            cachedSerQNames.add(qName);
            cls = PrepareCacheResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">purgeCache");
            cachedSerQNames.add(qName);
            cls = PurgeCache.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">purgeCacheResult");
            cachedSerQNames.add(qName);
            cls = PurgeCacheResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">purgeLog");
            cachedSerQNames.add(qName);
            cls = PurgeLog.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">purgeLogResult");
            cachedSerQNames.add(qName);
            cls = PurgeLogResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">readObjects");
            cachedSerQNames.add(qName);
            cls = ReadObjects.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">readObjectsResult");
            cachedSerQNames.add(qName);
            cls = CatalogObject[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "CatalogObject");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "catalogObject");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">removeFolder");
            cachedSerQNames.add(qName);
            cls = RemoveFolder.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">removeFolderResult");
            cachedSerQNames.add(qName);
            cls = RemoveFolderResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">renameAccounts");
            cachedSerQNames.add(qName);
            cls = RenameAccounts.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">renameAccountsResult");
            cachedSerQNames.add(qName);
            cls = RenameAccountsResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">saveResultSet");
            cachedSerQNames.add(qName);
            cls = SaveResultSet.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">saveResultSetResult");
            cachedSerQNames.add(qName);
            cls = SaveResultSetResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">sendMessage");
            cachedSerQNames.add(qName);
            cls = SendMessage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">sendMessageResult");
            cachedSerQNames.add(qName);
            cls = SendMessageResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">setBridge");
            cachedSerQNames.add(qName);
            cls = SetBridge.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">setBridgeResult");
            cachedSerQNames.add(qName);
            cls = SetBridgeResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">setItemAttributes");
            cachedSerQNames.add(qName);
            cls = SetItemAttributes.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">setItemAttributesResult");
            cachedSerQNames.add(qName);
            cls = SetItemAttributesResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">setItemProperty");
            cachedSerQNames.add(qName);
            cls = SetItemProperty.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">setItemPropertyResult");
            cachedSerQNames.add(qName);
            cls = SetItemPropertyResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">setOwnership");
            cachedSerQNames.add(qName);
            cls = SetOwnership.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">setOwnershipResult");
            cachedSerQNames.add(qName);
            cls = SetOwnershipResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">startPage");
            cachedSerQNames.add(qName);
            cls = StartPage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">startPageResult");
            cachedSerQNames.add(qName);
            cls = StartPageResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">subscribe");
            cachedSerQNames.add(qName);
            cls = Subscribe.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">subscribeResult");
            cachedSerQNames.add(qName);
            cls = SubscribeResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">unsubscribe");
            cachedSerQNames.add(qName);
            cls = Unsubscribe.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">unsubscribeResult");
            cachedSerQNames.add(qName);
            cls = UnsubscribeResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">updateCatalogItemACL");
            cachedSerQNames.add(qName);
            cls = UpdateCatalogItemACL.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">updateCatalogItemACLResult");
            cachedSerQNames.add(qName);
            cls = UpdateCatalogItemACLResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">updateGlobalPrivilegeACL");
            cachedSerQNames.add(qName);
            cls = UpdateGlobalPrivilegeACL.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">updateGlobalPrivilegeACLResult");
            cachedSerQNames.add(qName);
            cls = UpdateGlobalPrivilegeACLResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">upgradeXML");
            cachedSerQNames.add(qName);
            cls = UpgradeXML.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">upgradeXMLResult");
            cachedSerQNames.add(qName);
            cls = UpgradeXMLResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">voidType");
            cachedSerQNames.add(qName);
            cls = VoidType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">writeIBot");
            cachedSerQNames.add(qName);
            cls = WriteIBot.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">writeIBotResult");
            cachedSerQNames.add(qName);
            cls = WriteIBotResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">writeListFiles");
            cachedSerQNames.add(qName);
            cls = WriteListFiles.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">writeListFilesResult");
            cachedSerQNames.add(qName);
            cls = WriteListFilesResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">writeObjects");
            cachedSerQNames.add(qName);
            cls = WriteObjects.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", ">writeObjectsResult");
            cachedSerQNames.add(qName);
            cls = ErrorInfo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ErrorInfo");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "errorInfo");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "AccessControlToken");
            cachedSerQNames.add(qName);
            cls = AccessControlToken.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "Account");
            cachedSerQNames.add(qName);
            cls = Account.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ACL");
            cachedSerQNames.add(qName);
            cls = ACL.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "AggregationRule");
            cachedSerQNames.add(qName);
            cls = AggregationRule.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "arrayOfGUIDs");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "GUID");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "AuthResult");
            cachedSerQNames.add(qName);
            cls = AuthResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "CatalogItemsFilter");
            cachedSerQNames.add(qName);
            cls = CatalogItemsFilter.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "CatalogObject");
            cachedSerQNames.add(qName);
            cls = CatalogObject.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ErrorDetailsLevel");
            cachedSerQNames.add(qName);
            cls = ErrorDetailsLevel.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ErrorInfo");
            cachedSerQNames.add(qName);
            cls = ErrorInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ExportFlags");
            cachedSerQNames.add(qName);
            cls = ExportFlags.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "FileInfo");
            cachedSerQNames.add(qName);
            cls = FileInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "GetSubItemsFilter");
            cachedSerQNames.add(qName);
            cls = GetSubItemsFilter.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "GetSubItemsParams");
            cachedSerQNames.add(qName);
            cls = GetSubItemsParams.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ImportError");
            cachedSerQNames.add(qName);
            cls = ImportError.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ImportFlags");
            cachedSerQNames.add(qName);
            cls = ImportFlags.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ItemInfo");
            cachedSerQNames.add(qName);
            cls = ItemInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ItemInfoType");
            cachedSerQNames.add(qName);
            cls = ItemInfoType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "JobInfo");
            cachedSerQNames.add(qName);
            cls = JobInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "JobStats");
            cachedSerQNames.add(qName);
            cls = JobStats.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "LogonParameter");
            cachedSerQNames.add(qName);
            cls = LogonParameter.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "NameValueArrayPair");
            cachedSerQNames.add(qName);
            cls = NameValueArrayPair.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "NameValuePair");
            cachedSerQNames.add(qName);
            cls = NameValuePair.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "OverrideType");
            cachedSerQNames.add(qName);
            cls = OverrideType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "PathMap");
            cachedSerQNames.add(qName);
            cls = PathMapEntry[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "PathMapEntry");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "pathMapEntries");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    }
    private void addBindings2() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "PathMapEntry");
            cachedSerQNames.add(qName);
            cls = PathMapEntry.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "Privilege");
            cachedSerQNames.add(qName);
            cls = Privilege.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "PromptedColumnInfo");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "Columns");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "QueryResults");
            cachedSerQNames.add(qName);
            cls = QueryResults.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReadObjectsReturnOptions");
            cachedSerQNames.add(qName);
            cls = ReadObjectsReturnOptions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportHTMLLinksMode");
            cachedSerQNames.add(qName);
            cls = ReportHTMLLinksMode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportHTMLOptions");
            cachedSerQNames.add(qName);
            cls = ReportHTMLOptions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportParams");
            cachedSerQNames.add(qName);
            cls = ReportParams.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "ReportRef");
            cachedSerQNames.add(qName);
            cls = ReportRef.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SAColumn");
            cachedSerQNames.add(qName);
            cls = SAColumn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SADataType");
            cachedSerQNames.add(qName);
            cls = SADataType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SASubjectArea");
            cachedSerQNames.add(qName);
            cls = SASubjectArea.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SASubjectAreaDetails");
            cachedSerQNames.add(qName);
            cls = SASubjectAreaDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SATable");
            cachedSerQNames.add(qName);
            cls = SATable.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SATableDetails");
            cachedSerQNames.add(qName);
            cls = SATableDetails.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SAWException");
            cachedSerQNames.add(qName);
            cls = SAWException.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SAWLocale");
            cachedSerQNames.add(qName);
            cls = SAWLocale.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SAWSessionParameters");
            cachedSerQNames.add(qName);
            cls = SAWSessionParameters.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SegmentationOptions");
            cachedSerQNames.add(qName);
            cls = SegmentationOptions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "SessionEnvironment");
            cachedSerQNames.add(qName);
            cls = SessionEnvironment.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "StartPageParams");
            cachedSerQNames.add(qName);
            cls = StartPageParams.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "TemplateInfo");
            cachedSerQNames.add(qName);
            cls = TemplateInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "TemplateInfoInstance");
            cachedSerQNames.add(qName);
            cls = TemplateInfoInstance.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "TreeNodePath");
            cachedSerQNames.add(qName);
            cls = TreeNodePath.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "UpdateACLMode");
            cachedSerQNames.add(qName);
            cls = UpdateACLMode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "UpdateACLParams");
            cachedSerQNames.add(qName);
            cls = UpdateACLParams.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "UpdateCatalogItemACLParams");
            cachedSerQNames.add(qName);
            cls = UpdateCatalogItemACLParams.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "Variable");
            cachedSerQNames.add(qName);
            cls = Variable.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "XMLQueryExecutionOptions");
            cachedSerQNames.add(qName);
            cls = XMLQueryExecutionOptions.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "XMLQueryOutputFormat");
            cachedSerQNames.add(qName);
            cls = XMLQueryOutputFormat.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public JobInfo writeListFiles(ReportRef report, ReportParams reportParams, java.lang.String segmentPath, TreeNodePath treeNodePath, SegmentationOptions segmentationOptions, java.lang.String filesystem, java.math.BigInteger timeout, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#writeListFiles");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "writeListFiles"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {report, reportParams, segmentPath, treeNodePath, segmentationOptions, filesystem, timeout, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (JobInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (JobInfo) org.apache.axis.utils.JavaUtils.convert(_resp, JobInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public JobInfo getJobInfo(java.math.BigInteger jobID, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#getJobInfo");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "getJobInfo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {jobID, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (JobInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (JobInfo) org.apache.axis.utils.JavaUtils.convert(_resp, JobInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public JobInfo cancelJob(java.math.BigInteger jobID, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#cancelJob");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "cancelJob"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {jobID, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (JobInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (JobInfo) org.apache.axis.utils.JavaUtils.convert(_resp, JobInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public JobInfo getCounts(java.lang.String segmentPath, java.lang.String treePath, SegmentationOptions segmentationOptions, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#getCounts");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "getCounts"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {segmentPath, treePath, segmentationOptions, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (JobInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (JobInfo) org.apache.axis.utils.JavaUtils.convert(_resp, JobInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String[] getPromptedColumns(java.lang.String segmentPath, java.lang.String treePath, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#getPromptedColumns");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "getPromptedColumns"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {segmentPath, treePath, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String[]) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public JobInfo purgeCache(java.lang.String segmentPath, java.lang.String treePath, java.lang.Boolean ignoreCacheRef, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#purgeCache");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "purgeCache"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {segmentPath, treePath, ignoreCacheRef, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (JobInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (JobInfo) org.apache.axis.utils.JavaUtils.convert(_resp, JobInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public JobInfo prepareCache(java.lang.String segmentPath, java.lang.String treePath, java.lang.Boolean refresh, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#prepareCache");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "prepareCache"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {segmentPath, treePath, refresh, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (JobInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (JobInfo) org.apache.axis.utils.JavaUtils.convert(_resp, JobInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public JobInfo saveResultSet(java.lang.String segmentPath, TreeNodePath treeNodePath, java.lang.String savedSegmentPath, SegmentationOptions segmentationOptions, java.lang.String SRCustomLabel, java.lang.Boolean appendStaticSegment, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#saveResultSet");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "saveResultSet"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {segmentPath, treeNodePath, savedSegmentPath, segmentationOptions, SRCustomLabel, appendStaticSegment, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (JobInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (JobInfo) org.apache.axis.utils.JavaUtils.convert(_resp, JobInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public JobInfo deleteResultSet(java.lang.String targetLevel, java.lang.String[] GUIDs, java.lang.String segmentPath, java.lang.String sessionID) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("#deleteResultSet");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("urn://oracle.bi.webservices/v7", "deleteResultSet"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {targetLevel, GUIDs, segmentPath, sessionID});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (JobInfo) _resp;
            } catch (java.lang.Exception _exception) {
                return (JobInfo) org.apache.axis.utils.JavaUtils.convert(_resp, JobInfo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
