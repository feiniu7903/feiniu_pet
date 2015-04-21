package com.lvmama.front.web.seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.vo.CalendarModel;
import com.lvmama.comm.pet.po.businessCoupon.ProdSeckillRule;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ViewBuyInfo;
import com.lvmama.front.web.BaseAction;

/**
 * 
 * @author zenglei
 *
 */
@Results({
	
})
public class SeckillFillAction extends BaseAction{
	
	
	private ViewBuyInfo buyInfo;
	//秒杀规则
    private ProdSeckillRule prodSeckillRule;
    
    private PageService pageService;
    
    private String verifycode;
    
    private static final long serialVersionUID = 8314244587458799940L;
    
    private static String seckillVerifycodeMode = Constant.getInstance().getProperty("seckill.verifycode.mode");
	/**
	 * 秒杀
	 * 
	 */
	@Action("/fill/seckill")
	public String execute(){
		
		JSONResult jsonObject=new JSONResult(getResponse());
		boolean flag = false;
			//验证码
			if(StringUtils.isNotEmpty(seckillVerifycodeMode) && !seckillVerifycodeMode.trim().equals("1")){
				ServletUtil servletUtil = new ServletUtil();
				String chineseCode = (String)servletUtil.getSession(ServletActionContext.getRequest(), ServletActionContext.getResponse(), "chineseCode");
				if(!StringUtils.equalsIgnoreCase(chineseCode,StringUtil.toUTF8(verifycode))){
					jsonObject.put("flag", false);
					jsonObject.put("msg", "验证码输入错误");
					jsonObject.output();
					return null;
				}	
			}
			//读取秒杀类别的购买数量
			Map<Long, Long> branchMap = buyInfo.getOrdItemProdList();
			Long branchNum = 0L;
			if(branchMap != null){
				branchNum = branchMap.get(buyInfo.getSeckillBranchId());
			}
			if(branchNum == 0L){
				jsonObject.put("flag",false);
				jsonObject.put("msg","未选择秒杀商品!");
				jsonObject.output();
				return null;
			}
			/**秒杀是否开始
			 * 当前时间在 秒杀开始之后 并且 在秒杀结束之前
			*/
			prodSeckillRule = SeckillMemcachedUtil.getSeckillMemcachedUtil().getSeckillRule(buyInfo.getSeckillBranchId());
			if(prodSeckillRule == null){
				jsonObject.put("flag",false);
				jsonObject.put("msg","无效的秒杀产品");
				jsonObject.output();
				return null;
			}else{
				Date nowDate = new Date();
				if(nowDate.after(prodSeckillRule.getStartTime()) && nowDate.before(prodSeckillRule.getEndTime()) ){
					flag = true;
				}else{
					if(nowDate.before(prodSeckillRule.getStartTime())){
						jsonObject.put("flag",false);
						jsonObject.put("msg","秒杀暂未开始");
					}else{
						jsonObject.put("flag",false);
						jsonObject.put("msg","秒杀己结束");
					}
					jsonObject.output();
					return null;
				}
			}
			//等待人数校验
			Long waitPeople = SeckillMemcachedUtil.getSeckillMemcachedUtil().getWaitPeopleByMemcached(buyInfo.getSeckillBranchId(), true, branchNum);
			//return 商品己告罄
			if(waitPeople >= 1){
				flag = true;
			}else{
				jsonObject.put("flag",false);
				jsonObject.put("msg","商品己告罄");
				jsonObject.output();
				return null;
			}
			if(flag){
				
				ProdProductBranch prodProductBranch = pageService.getProdBranchByProdBranchId(buyInfo.getSeckillBranchId());
				if(prodProductBranch == null) {
					LOG.error("类别不存在");
					return "input";
				}

				//是否是不定期产品
				if(prodProductBranch.getProdProduct().IsAperiodic()){
					jsonObject.put("flag",true);
					jsonObject.put("msg","");
				}else{
					//时间价格表
					List<CalendarModel> cmList = SeckillMemcachedUtil.getSeckillMemcachedUtil().getCalendarModel(buyInfo.getProductId(), buyInfo.getSeckillBranchId());
					
					String jsonTimePrice = SeckillMemcachedUtil.getSeckillMemcachedUtil().getJSONTimePrice(cmList, prodProductBranch.getProdProduct());
					jsonObject.put("flag",true);
					jsonObject.put("msg",jsonTimePrice);
					jsonObject.output();
				}
			}
		
		return null;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public ViewBuyInfo getBuyInfo() {
		return buyInfo;
	}

	public void setBuyInfo(ViewBuyInfo buyInfo) {
		this.buyInfo = buyInfo;
	}

	public String getVerifycode() {
		return verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}
}











