package com.lvmama.pet.job.quartz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.user.Annhongbao;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 519大促 红包把剩余的奖金红包放到下一天；
 * 
 * @author nixianjun
 * 
 */
public class DacunUpdateHongbaoJob implements Runnable {
	private final Log log = LogFactory.getLog(getClass());
	private UserUserProxy userUserProxy;

	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			try {
				log.info("DacunUpdateHongbaoJob lunched.");

				Date now = DateUtil.getTodayDate();
				Date begin = DateUtil.stringToDate("2014-5-9", "yyyy-MM-dd");
				Date end = DateUtil.stringToDate("2014-5-19", "yyyy-MM-dd");
				if (now.getTime() < begin.getTime()
						|| now.getTime() > end.getTime()) {
					return;
				}
 				Map map = new HashMap();
				map.put("senddate", DateUtil.getTodayDate());
				List<Annhongbao>  list=userUserProxy.queryAnnhongbaoByParam(map);
				if(null!=list&&list.size()>0){
					Annhongbao annjieguo=list.get(0);
					if (annjieguo.getTwoShengyu() != 0L
							|| annjieguo.getFiveShengyu() != 0L
							|| annjieguo.getFifteenShengyu() != 0L
							|| annjieguo.getFiftyShengyu() != 0L) {
 						Map map2 = new HashMap();
						map2.put("senddate",  DateUtil.getDateAfterDays(new Date(),1));
						List<Annhongbao>  list2=userUserProxy.queryAnnhongbaoByParam(map2);
						if(null!=list2&&list2.size()>0){
							//next day 剩余增加前一天金额
							Annhongbao nextann=list2.get(0);
							nextann.setTwoShengyu(nextann.getTwoShengyu()+annjieguo.getTwoShengyu());
							nextann.setFiveShengyu(nextann.getFiveShengyu()+annjieguo.getFiveShengyu());
							nextann.setFifteenShengyu(nextann.getFifteenShengyu()+annjieguo.getFifteenShengyu());
							nextann.setFiftyShengyu(nextann.getFiftyShengyu()+annjieguo.getFiftyShengyu());
							nextann.setUpdateDate(new Date());
							//前一天剩余金额设置为0；
							annjieguo.setTwoShengyu(0L);
							annjieguo.setFiveShengyu(0L);
							annjieguo.setFifteenShengyu(0L);
							annjieguo.setFiftyShengyu(0L);
							annjieguo.setUpdateDate(new Date());
							this.userUserProxy.updateReWriteShengyuJinE(nextann,annjieguo);
						}
						
					}
				}
				log.info("DacunUpdateHongbaoJob finished.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the userUserProxy
	 */
	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}

	/**
	 * @param userUserProxy the userUserProxy to set
	 */
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	
}
