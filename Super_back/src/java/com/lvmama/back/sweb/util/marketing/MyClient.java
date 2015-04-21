package com.lvmama.back.sweb.util.marketing;


public class MyClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SAWSessionServiceLocator sl = new SAWSessionServiceLocator();
		String sessionId;
		try {
			sessionId = sl.getSAWSessionServiceSoap().logon("marketer", "123456");
			WebCatalogServiceLocator wl = new WebCatalogServiceLocator();
			ItemInfo[] itemArray = wl.getWebCatalogServiceSoap().getSubItems("/shared/Marketing", "*", true, null, sessionId);
			System.out.println(itemArray);
			//SegmentationOptions options = new SegmentationOptions(); 
			//JobManagementServiceLocator jl = new JobManagementServiceLocator();
			//jl.getJobManagementServiceSoap().getCounts(segmentPath, treePath, segmentationOptions, sessionId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
