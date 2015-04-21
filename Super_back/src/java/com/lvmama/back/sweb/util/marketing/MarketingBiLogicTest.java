package com.lvmama.back.sweb.util.marketing;

public class MarketingBiLogicTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//ItemInfo[] array = MarketingBiLogic.getMarkModelDatas();
		JobInfo jobInfo = MarketingBiLogic.saveResultSet("/shared/Marketing/Segmentation_01", "test");
		System.out.println(jobInfo.getJobStats().getJobFinishedTime());
	}

}
