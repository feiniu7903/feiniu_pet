package com.lvmama.pet.job.quartz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.pub.ComKeJetAds;
import com.lvmama.comm.pet.service.pub.ComKeJetAdsService;
import com.lvmama.comm.utils.KeJieAdsProxy;
import com.lvmama.comm.vo.Constant;

public class KeJetAdsJob implements Runnable {
	@Autowired
	private ComKeJetAdsService comKeJetAdsService;
	private static String[] FILIALE = { "SH", "BJ", "CD", "GZ", "HZ", "NJ",
			"SZ", "SY" };

	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			List<ComKeJetAds> adsList = comKeJetAdsService.queryAll();
			for (ComKeJetAds ads : adsList) {
				if ("Y".equalsIgnoreCase(ads.getExtKey())) {
					for (String filiale : FILIALE) {
						KeJieAdsProxy.getKeJetAdsContent(ads.getAp(),
								ads.getCt(), filiale);
						try {
							Thread.sleep(500L);
						} catch (InterruptedException e) {
						}
					}
				} else {
					KeJieAdsProxy.getKeJetAdsContent(ads.getAp(), ads.getCt(),
							ads.getExtKey());
				}
				try {
					Thread.sleep(500L);
				} catch (InterruptedException e) {
				}
			}

		}
	}

}
