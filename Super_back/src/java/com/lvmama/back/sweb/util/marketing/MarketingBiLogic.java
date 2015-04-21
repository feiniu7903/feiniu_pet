package com.lvmama.back.sweb.util.marketing;

import com.lvmama.comm.vo.Constant;

import java.util.Properties;

/**
 * 通过webservice从bi获取营销数据
 *
 * @author shihui
 */
public class MarketingBiLogic {

    private static String sessionId;
    private Properties properties;

    private static void initSession() throws Exception {
        SAWSessionServiceLocator sl = new SAWSessionServiceLocator();

        sl.setSAWSessionServiceSoapEndpointAddress(Constant.getInstance().getProperty("DATA_MODEL_SAWSessionServiceSoapEndpointAddress"));
        sessionId = sl.getSAWSessionServiceSoap().logon(Constant.getInstance().getProperty("DATA_MODEL_username"),
                Constant.getInstance().getProperty("DATA_MODEL_password"));
    }

    /**
     * 从bi获取数据模型列表
     */
    public static ItemInfo[] getMarkModelDatas() throws Exception {
        initSession();

        WebCatalogServiceLocator wl = new WebCatalogServiceLocator();
        wl.setWebCatalogServiceSoapEndpointAddress(Constant.getInstance().getProperty("DATA_MODEL_WebCatalogServiceSoapEndpointAddress"));
        ItemInfo[] itemArray = wl.getWebCatalogServiceSoap().getSubItems(
                "/shared/Marketing", "*", true, null, sessionId);
        return itemArray;
    }

    /**
     * 获取数据前需先运行该job,保存到数据库才能获取数据
     *
     * @param segmentPath 数据模型项的路径
     * @param lableName   自定义标签名称
     */
    public static JobInfo saveResultSet(String segmentPath, String lableName) throws Exception {
        initSession();

        JobManagementServiceLocator jl = new JobManagementServiceLocator();
        jl.setJobManagementServiceSoapEndpointAddress(Constant.getInstance().getProperty("DATA_MODEL_JobManagementServiceSoapEndpointAddress"));
        return jl.getJobManagementServiceSoap().saveResultSet(segmentPath, null, null,
                null, lableName, false, sessionId);
    }
}
