/**
 * 驴妈妈首页
 */
package com.lvmama.front.web.home;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.ProdHotSellSeq;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.utils.HttpRequestDeviceUtils;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.homePage.PindaoPageUtils;
import com.lvmama.comm.vo.Constant;


@Results({
	@Result(name = "success", location = "/WEB-INF/pages/www/home/lvmamaHome.ftl", type = "freemarker"),
 	@Result(name = "wap", location = "http://m.lvmama.com", type = "redirect")
})
public class LvmamaHomeAction extends ChannelBaseHomeAction {
	private static final long serialVersionUID = -1814572599244208664L;
	private Long commonBlockId = PindaoPageUtils.HOME_COMMONBLOCKID2;
	private String channelPage =PindaoPageUtils.HOME_CHANNELPAGE2;
	private String containerCode = PindaoPageUtils.HOME_CONTAINERCODE2;
	private ProdProductService prodProductService;
	private Map<String,List<ProdHotSellSeq>> prodHotSellList;
	
	
	@Action("/homePage/lvmamaHome")
	public String  execute() {
		if (null == this.getCookieValue("wap_to_lvmama")&&HttpRequestDeviceUtils.isMobileDevice(getRequest())) {
			return "wap";
		}
		this.initExcute(Constant.CHANNEL_ID.CH_INDEX.name());
		
		if (null != fromPlaceId && fromPlaceId.longValue() != DEFAULT_FROM_PLACE_ID.longValue()) {
			//如果出发地已经是上海，则不需要使用默认出发地当做备用字段
			putRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId, DEFAULT_FROM_PLACE_ID);
		} else {
			putRecommentInfoResult(channelPage, commonBlockId, containerCode, this.fromPlaceId, null);
		}
		prodHotSellList = queryProdHotSellSeqByFromPlaceCode(fromPlaceCode);
		if(null == prodHotSellList){
		    prodHotSellList = queryProdHotSellSeqByFromPlaceCode("SH");
		}
 		return "success";
	}
	
	public Map<String,List<ProdHotSellSeq>> queryProdHotSellSeqByFromPlaceCode(String fromPlaceCode){
	    String key = "LVMAMAHOMEACTION_QUERYPRODHOTSELLSEQBYFROMPLACECODE_"+fromPlaceCode;
	    Map<String,List<ProdHotSellSeq>> placeHotSellList =(Map<String,List<ProdHotSellSeq>>) MemcachedUtil.getInstance().get(key);
        if (placeHotSellList == null) {
            LOG.info("MemCache beginning:" + key);
    	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
            String nowTimeByChannel = formatter.format(new Date());
            placeHotSellList = prodProductService.queryProdHotSell(fromPlaceCode, nowTimeByChannel);
            if(placeHotSellList.size()==0){
                //如果最近数据还没有跑出来 即读老数据
                Calendar calendar = Calendar.getInstance();//日历对象
                calendar.setTime(new Date());//设置当前日期
                calendar.add(Calendar.MONTH, -1);//月份减一
                String oldTimeByChannel = formatter.format(calendar.getTime());//得到上个月的日期
                placeHotSellList = prodProductService.queryProdHotSell(fromPlaceCode, oldTimeByChannel);
            }
            
            MemcachedUtil.getInstance().set(key, MemcachedUtil.TWO_HOUR,placeHotSellList);
            if (null == MemcachedUtil.getInstance().get(key)) {
                LOG.info("SAVE MemCache Failure:" + key);
            }
        }
        return placeHotSellList;
	}
	
	public String getChannelPage() {
		return channelPage;
	}	
	public Map<String, List<RecommendInfo>> getRecommendInfoMainList(){
		return (Map<String, List<RecommendInfo>>) map.get("recommendInfoMainList");
	}

    public ProdProductService getProdProductService() {
        return prodProductService;
    }

    public void setProdProductService(ProdProductService prodProductService) {
        this.prodProductService = prodProductService;
    }

    public Map<String,List<ProdHotSellSeq>> getProdHotSellList() {
        return prodHotSellList;
    }

    public void setProdHotSellList(Map<String,List<ProdHotSellSeq>> prodHotSellList) {
        this.prodHotSellList = prodHotSellList;
    }
	
}
