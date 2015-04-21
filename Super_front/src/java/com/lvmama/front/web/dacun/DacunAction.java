package com.lvmama.front.web.dacun;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.client.RecommendInfoClient;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.po.user.Annwinnerslist;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.homePage.PlaceUtils;
import com.lvmama.comm.utils.homePage.TwoDimensionCode;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ProdCProduct;
import com.lvmama.front.web.seckill.SeckillMemcachedUtil;

@Results({ @Result(name = "input", location = "/dacun/index.html") })
public class DacunAction extends BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -6392921717994038866L;

	private RecommendInfoClient recommendInfoClient;
	private RecommendInfoService recommendInfoService;
	private PageService pageService;
	private ComPictureService comPictureService;
	private ProdProductService prodProductService ;

	private Map<String, List<RecommendInfo>> map;
	private SeckillMemcachedUtil seckillMemcachedUtil;

	private Long blockId=21460L; // 主块ID
	private String station="LP"; // 站点
	private UserUserProxy userUserProxy;
	private Long reducePrice;//秒杀价格
	
	
	/**
	 * 获得秒杀价
	 * @param list
	 * @return
	 */
	private List<RecommendInfo> getRecommList(List<RecommendInfo> list){
		long mprice =0L;
		for (RecommendInfo recommendInfo : list) {
			Object obj = SeckillMemcachedUtil.getSeckillMemcachedUtil().getSeckillRuleByProductId(Long.valueOf(recommendInfo.getRecommObjectId()));
			if(obj != null){
				ProdProduct p = prodProductService.getProdProductById(Long.valueOf(recommendInfo.getRecommObjectId()));
				if(p!=null){
				    ProdSeckillRule psr =((ProdSeckillRule) obj);
				    reducePrice = psr.getReducePrice();
				    mprice = Long.valueOf(p.getSellPrice())-reducePrice;
				    recommendInfo.setMemberPrice(String.valueOf(mprice));
				}
			}
		}
		return list;	
	}
	@Action(value = "/dacun/index")
	public String index() {
		WebApplicationContext wac=WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		recommendInfoClient=(RecommendInfoClient) wac.getBean("recommendInfoClient");
		recommendInfoService = (RecommendInfoService) wac.getBean("recommendInfoService");
		pageService = (PageService) wac.getBean("pageService");
		comPictureService = (ComPictureService) wac.getBean("comPictureService");
		
		map= recommendInfoClient.getRecommendProductByBlockIdAndStation(blockId, station);
		//app1元秒杀
		List<RecommendInfo> _8yms = map.get("LP_time_eight_oclock");
		List<RecommendInfo> _10yms = map.get("LP_time_ten_oclock");
		List<RecommendInfo> _12yms = map.get("LP_time_twelve_oclock");
		List<RecommendInfo> _14yms = map.get("LP_time_fourteen_oclock");
		List<RecommendInfo> _16yms = map.get("LP_time_sixteen_oclock");
		List<RecommendInfo> _18yms = map.get("LP_time_eighteen_oclock");
		List<RecommendInfo> _20yms = map.get("LP_time_twenty_oclock");
		
		excuteQRDataFile(_8yms);   getBigPicture(_8yms);
		
		excuteQRDataFile(_10yms);  getBigPicture(_10yms);
		excuteQRDataFile(_12yms);  getBigPicture(_12yms);
		excuteQRDataFile(_14yms);  getBigPicture(_14yms);
		excuteQRDataFile(_16yms);  getBigPicture(_16yms);
		excuteQRDataFile(_18yms);  getBigPicture(_18yms);
		excuteQRDataFile(_20yms);  getBigPicture(_20yms);
		
		//1折起限时抢购 
		List<RecommendInfo> _8_place = map.get("LP_8_place");             getBigPicture(_8_place);      getRecommList(_8_place);
	List<RecommendInfo> _8_freetour = map.get("LP_8_freetour");      	  getBigPicture(_8_freetour);	getRecommList(_8_freetour);
		List<RecommendInfo> _8_home = map.get("LP_8_home");               getBigPicture(_8_home);		getRecommList(_8_home);
		List<RecommendInfo> _8_abroad = map.get("LP_8_abroad");           getBigPicture(_8_abroad);		getRecommList(_8_abroad);
		List<RecommendInfo> _8_hotel = map.get("LP_8_hotel");             getBigPicture(_8_hotel);		getRecommList(_8_hotel);
		
		List<RecommendInfo> _10_place = map.get("LP_10_place");           getBigPicture(_10_place);		getRecommList(_10_place);
		List<RecommendInfo> _10_freetour = map.get("LP_10_freetour");     getBigPicture(_10_freetour);	getRecommList(_10_freetour);
		List<RecommendInfo> _10_home = map.get("LP_10_home");             getBigPicture(_10_home);		getRecommList(_10_home);
		List<RecommendInfo> _10_abroad = map.get("LP_10_abroad");         getBigPicture(_10_abroad);	getRecommList(_10_abroad);
		List<RecommendInfo> _10_hotel = map.get("LP_10_hotel");           getBigPicture(_10_hotel);		getRecommList(_10_hotel);
		
	    List<RecommendInfo> _12_place = map.get("LP_12_place");           getBigPicture(_12_place);		getRecommList(_12_place);
        List<RecommendInfo> _12_freetour = map.get("LP_12_freetour");     getBigPicture(_12_freetour);  getRecommList(_12_freetour);
        List<RecommendInfo> _12_home = map.get("LP_12_home");             getBigPicture(_12_home);		getRecommList(_12_home);
        List<RecommendInfo> _12_abroad = map.get("LP_12_abroad");         getBigPicture(_12_abroad);	getRecommList(_12_abroad);
        List<RecommendInfo> _12_hotel = map.get("LP_12_hotel");           getBigPicture(_12_hotel);		getRecommList(_12_hotel);
	
        List<RecommendInfo> _14_place = map.get("LP_14_place");           getBigPicture(_14_place);		getRecommList(_14_place);
        List<RecommendInfo> _14_freetour = map.get("LP_14_freetour");     getBigPicture(_14_freetour);	getRecommList(_14_freetour);
        List<RecommendInfo> _14_home = map.get("LP_14_home");             getBigPicture(_14_home);		getRecommList(_14_home);
        List<RecommendInfo> _14_abroad = map.get("LP_14_abroad");         getBigPicture(_14_abroad);	getRecommList(_14_abroad);
        List<RecommendInfo> _14_hotel = map.get("LP_14_hotel");           getBigPicture(_14_hotel);		getRecommList(_14_hotel);
		
        List<RecommendInfo> _16_place = map.get("LP_16_place");           getBigPicture(_16_place);		getRecommList(_16_place);
        List<RecommendInfo> _16_freetour = map.get("LP_16_freetour");     getBigPicture(_16_freetour);	getRecommList(_16_freetour);
        List<RecommendInfo> _16_home = map.get("LP_16_home");             getBigPicture(_16_home);		getRecommList(_16_home);
        List<RecommendInfo> _16_abroad = map.get("LP_16_abroad");         getBigPicture(_16_abroad);	getRecommList(_16_abroad);
        List<RecommendInfo> _16_hotel = map.get("LP_16_hotel");           getBigPicture(_16_hotel);		getRecommList(_16_hotel);
		
        List<RecommendInfo> _18_place = map.get("LP_18_place");           getBigPicture(_18_place);		getRecommList(_18_place);
        List<RecommendInfo> _18_freetour = map.get("LP_18_freetour");     getBigPicture(_18_freetour);	getRecommList(_18_freetour);
        List<RecommendInfo> _18_home = map.get("LP_18_home");             getBigPicture(_18_home);		getRecommList(_18_home);
        List<RecommendInfo> _18_abroad = map.get("LP_18_abroad");         getBigPicture(_18_abroad);	getRecommList(_18_abroad);
        List<RecommendInfo> _18_hotel = map.get("LP_18_hotel");           getBigPicture(_18_hotel);		getRecommList(_18_hotel);
        
        //低价预售
		List<RecommendInfo> _dijiayushou = map.get("LP_dijiayushou");     getBigPicture(_dijiayushou);
		//早定早惠
		List<RecommendInfo> _zaodingzaohui = map.get("LP_zaodingzaohui"); getBigPicture(_zaodingzaohui);
		
		
		CreateHTML chtml = new CreateHTML();
 		chtml.geneHtmlFile("index.ftl", map, this.getServletContext()
				.getRealPath("/dacun/"), "index.html", this
				.getServletContext().getRealPath("/WEB-INF/templates"));
		return "input";
	}
	
	/**
	 * 查询获奖名单
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "/dacun/getwinnerslist")
	public void getwinnerslist() throws IOException{
		String key = "ANNLISTS_DACU";
		List<Annwinnerslist> annlists = (List<Annwinnerslist>) MemcachedUtil.getInstance().get(key);
		if(annlists == null){
			annlists = this.userUserProxy.queryWinnerslistBylimit(20);
			MemcachedUtil.getInstance().set(key,MemcachedUtil.ONE_HOUR,annlists);
		}
		if(annlists!=null)
			printRtn(annlists);
	}

	/**
	 * 二维码生成调用，动态存取目的地ID
	 */
	public void excuteQRDataFile(List<RecommendInfo> recommList) {
		if (null != recommList) {
			for (RecommendInfo rci : recommList) {
				if (null != rci.getRecommObjectId()) {
						ProdSeckillRule psr = (ProdSeckillRule) SeckillMemcachedUtil
								.getSeckillMemcachedUtil()
								.getSeckillRuleByProductId(
										Long.valueOf(rci.getRecommObjectId()));
						if (psr != null) {
							String dirPath = ResourceUtil
									.getResourceFileName("/productQr");
							File dir = new File(dirPath);
							if (!dir.exists()) {
								dir.mkdirs();
							}
							String imgPath = ResourceUtil
									.getResourceFileName("/productQr/"
											+ rci.getRecommObjectId() + "."
											+ PlaceUtils.QR_IMGTYPE);
							File imgFile = new File(imgPath);
							if (!imgFile.exists()) {
								TwoDimensionCode.encoderQRCode(
										"http://m.lvmama.com/clutter/seckill/"
												+ rci.getRecommObjectId() + "/"
												+ psr.getBranchId()
												+ "/?channel=QR", imgPath,
										PlaceUtils.QR_IMGTYPE,
										PlaceUtils.QR_SIZE);
							}
							if (null == rci.getBakWord10()) {
							    rci.setBakWord1("");
		                        rci.setBakWord2("");
								rci.setBakWord3("http://www.lvmama.com/productQr/"
										+ rci.getRecommObjectId() + ".png");
								rci.setBakWord10("seckillProduct");
								recommendInfoService.updateRecommendInfo(rci);
							}
						}
					
				}
			}
		}
	}
	
	
	@Action(value = "/519dacun/index")
	public String frontIndex() {
		return "input";
	}
	
	public void getBigPicture(List<RecommendInfo> topList){
	    if (topList != null && topList.size() > 0) {
            for (RecommendInfo rec : topList) {
                if (rec != null && rec.getRecommObjectId() != null) {
                    List<ComPicture>  comPic = getComPictureService().getPictureByPageId(Long.valueOf(rec.getRecommObjectId()));
                    if(null != comPic && comPic.size()>0){
                        rec.setImgUrl("http://pic.lvmama.com/pics/"+comPic.get(0).getPictureUrl());
                        //recommendInfoService.updateRecommendInfo(rec);
                    }
                }
            }
        }
	}
	
	/**
	 * 输出返回码
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param object
	 *            Ajax返回的对象
	 * @throws IOException
	 *             IOException
	 */
	protected void printRtn(final Object object) throws IOException  {
		String json = null;
		getResponse().setContentType("text/json; charset=utf-8");
		if (null == object) {
			return;
		} else {
			if (object instanceof java.util.Collection) {
				json = JSONArray.fromObject(object).toString();
			} else {
				json = JSONObject.fromObject(object).toString();
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("返回对象:" + json);
			}
		}
		if (getRequest().getParameter("jsoncallback") == null) {
			getResponse().getWriter().print(json);
		} else {
			getResponse().getWriter().print(
					getRequest().getParameter("jsoncallback") + "(" + json
							+ ")");
		}
	}

	public Map<String, List<RecommendInfo>> getMap() {
		return map;
	}

    public RecommendInfoClient getRecommendInfoClient() {
        return recommendInfoClient;
    }

    public RecommendInfoService getRecommendInfoService() {
        return recommendInfoService;
    }

    public PageService getPageService() {
        return pageService;
    }

	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

    public ComPictureService getComPictureService() {
        return comPictureService;
    }

	public SeckillMemcachedUtil getSeckillMemcachedUtil() {
		return seckillMemcachedUtil;
	}

	public void setSeckillMemcachedUtil(SeckillMemcachedUtil seckillMemcachedUtil) {
		this.seckillMemcachedUtil = seckillMemcachedUtil;
	}
    public ProdProductService getProdProductService() {
        return prodProductService;
    }
    public void setProdProductService(ProdProductService prodProductService) {
        this.prodProductService = prodProductService;
    }
    
    
	
}
