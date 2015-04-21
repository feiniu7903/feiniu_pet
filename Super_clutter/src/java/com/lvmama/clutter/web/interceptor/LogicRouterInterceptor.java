package com.lvmama.clutter.web.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.MobileBranchItem;
import com.lvmama.clutter.model.MobilePersonItem;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.vo.Constant;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LogicRouterInterceptor  extends AbstractInterceptor{
	private static final Log LOG = LogFactory
			.getLog(LogicRouterInterceptor.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -1536509126991870050L;
	@Autowired
	private ProdProductBranchService prodProductBranchService;
	


	@Override
	public String intercept(ActionInvocation invocation) {
		String uri = this.getRequest().getRequestURI();
		try {
			if(uri.contains("router")){
				String method = this.getRequest().getParameter("method");
				String lvversion = this.getRequest().getParameter("lvversion");
				// 获取产品列表 ，getProductItems
				if(method!=null&&ClutterConstant.CLIENT_API_METHOD.API_COM_PRODUCT_GETPRODUCTITEMS.getValue().equals(method)){
					Long branchId = Long.valueOf(this.getRequest().getParameter("branchId"));
					if(this.isTrain(branchId)){
						invocation.getInvocationContext().getParameters().put("method",ClutterConstant.CLIENT_API_METHOD.API_COM_PRODUCT_TRAIN_GETPRODUCTITEMS.getValue());
						method = (String) invocation.getInvocationContext().getParameters().get("method");
					}
				// 提交订单. 
				} else if(method!=null&&ClutterConstant.CLIENT_API_METHOD.API_COM_ORDER_COMMITORDER.getValue().equals(method)){
					Map<String,Object> params = invocation.getInvocationContext().getParameters();
					List<MobileBranchItem> branchItemList = this.branchItemParser(this.getRequest().getParameter("branchItem"));
					List<MobilePersonItem> personItemList = this.personItemParser(this.getRequest().getParameter("personItem"));
					if(null != branchItemList && branchItemList.size() > 0) {
						if(this.isTrain(branchItemList)){
							params.put("branchItemList", branchItemList);
							params.put("personItemList", personItemList);
							params.put("method",ClutterConstant.CLIENT_API_METHOD.API_COM_ORDER_TRAIN_COMMITORDER.getValue());
							invocation.getInvocationContext().setParameters(params);
							method = (String) params.get("method");
						}
					}
				} else if(method!=null&&ClutterConstant.CLIENT_API_METHOD.API_COM_ORDER_VALIDATETRAVELLERINFO.getValue().equals(method)){
					Map<String,Object> params = invocation.getInvocationContext().getParameters();
					List<MobileBranchItem> branchItemList = this.branchItemParser(this.getRequest().getParameter("branchItem"));
					if(null != branchItemList && branchItemList.size() > 0) {
						if(this.isTrain(branchItemList)){
							params.put("method",ClutterConstant.CLIENT_API_METHOD.API_COM_ORDER_TRAIN_VALIDATETRAVELLERINFO.getValue());
							invocation.getInvocationContext().setParameters(params);
							method = (String) params.get("method");
						}
					}
				}
				
				String beanName = method.substring(0, method.lastIndexOf("."));	
				String serviceMethod = method.substring(method.lastIndexOf(".")+1);
	            // 版本号
				if(!StringUtils.isEmpty(lvversion)) {
					beanName = beanName+"."+lvversion;
				}
				
				/**
				 * ipad api 生成bean 
				 */
				beanName = beanName.replace(".", "_");
				if(Constant.MOBILE_PLATFORM.IPAD.name().equals(this.getRequest().getParameter("firstChannel"))){
					beanName+="_pad";
				}
				if(Constant.MOBILE_PLATFORM.WP8.name().equals(this.getRequest().getParameter("firstChannel"))){
					beanName+="_wp8";
				}
				
				invocation.getInvocationContext().getParameters().put("beanName",beanName);
				invocation.getInvocationContext().getParameters().put("targetMethod",serviceMethod);
				
				// 修复客户端bugs
				this.repairBugs(invocation);
			}
	
			return invocation.invoke();
		}catch(LogicException e) {
			Map<String,Object> resultMap = resultMapCreator();
			resultMap.put("message", e.getMessage());
			this.sendAjaxResultByJson(resultMap);
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			Map<String,Object> resultMap = resultMapCreator();
			resultMap.put("errorMessage", e.getMessage());
			this.sendAjaxResultByJson(resultMap);
			return null;
		}
	}

	/**
	 * 修复客户端传递参数bugs
	 * @param parameters
	 */
	private void repairBugs(ActionInvocation invocation) {
		try{
			Map<String,Object> parameters = invocation.getInvocationContext().getParameters();
			if(null != parameters 
					&& Constant.MOBILE_PLATFORM.ANDROID.name().equals(this.getRequest().getParameter("firstChannel")) 
					&& "api.com.search.placeSearch".equals(this.getRequest().getParameter("method")) 
					&& "5.0.0".equals(this.getRequest().getParameter("lvversion").toString())) {
				
				String[] longitude = (String[])parameters.get("longitude");
				String[] latitude = (String[])parameters.get("latitude");
				if(null != longitude && longitude.length > 1) {
					parameters.put("longitude", longitude[longitude.length-1]);
				}
				if(null != latitude && latitude.length > 1) {
					parameters.put("latitude", latitude[latitude.length-1]);
				}
			}
			invocation.getInvocationContext().setParameters(parameters);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否是火车票  - 获取产品列表用 
	 * @param branchId
	 * @return
	 */
	public boolean isTrain(Long branchId) {
		ProdProductBranch ppb =  prodProductBranchService.getProductBranchDetailByBranchId(branchId,null, true);
		if(ppb!=null && ppb.getProdProduct()!=null && ppb.getProdProduct().isTrain()){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否是火车票  - 提交订单判断 。
	 * @param branchList  
	 * @return
	 */
	public boolean isTrain(List<MobileBranchItem> branchList) {
		for(int i = 0; i < branchList.size();i++) {
			MobileBranchItem mbi = branchList.get(i);
			ProdProductBranch ppb =  prodProductBranchService.getProductBranchDetailByBranchId(mbi.getBranchId(),null, true);
			if(null != ppb && !ppb.hasAdditional() && ppb.getProdProduct()!=null&&ppb.getProdProduct().isTrain()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 产品类别解析 
	 * @param branchItem
	 * @return
	 */
	private List<MobileBranchItem> branchItemParser(String branchItem){
		List<MobileBranchItem> branchItemList = new ArrayList<MobileBranchItem>();
		if(!StringUtils.isEmpty(branchItem)) {
			String[] branchArray = branchItem.split("_");
			if(branchArray.length > 0) {
				for (String string : branchArray) {
					String[] itemArray  = string.split("-");
					MobileBranchItem mbi = new MobileBranchItem();
					mbi.setBranchId(Long.valueOf(itemArray[0]));
					mbi.setQuantity(Long.valueOf(itemArray[1]));
					branchItemList.add(mbi);
				}
			}
		}
		return branchItemList;
	}
	
	/**
	 * 人员解析 
	 * @param persionItem
	 * @return
	 */
	private List<MobilePersonItem> personItemParser(String persionItem){
		if(StringUtils.isEmpty(persionItem)) {
			throw new LogicException("人员项构建错误");
		}
		String[] personArray = persionItem.split(":");
		List<MobilePersonItem> personItemList = new ArrayList<MobilePersonItem>();
		/**
		 * 解析联系人相关数据
		 */
		for (String string : personArray) {
			String[] itemArray  = string.split("-");
			MobilePersonItem mpi = new MobilePersonItem();
			mpi.setPersonName(itemArray[0]);
			mpi.setPersonMobile(itemArray[1]);
			mpi.setPersonType(itemArray[2]);
			if(mpi.getPersonType().equals(Constant.RECEIVERS_TYPE.ADDRESS.name())){
				/**
				 * 处理有地址的其他情况
				 */
				if(itemArray.length>=4){
					mpi.setProvince(itemArray[3]);
					mpi.setCity(itemArray[4]);
					mpi.setAddress(itemArray[5]);
				}	
			} else {
				if(itemArray.length>=4){
					mpi.setCertNo(itemArray[3]);
					if(itemArray.length==4){
						mpi.setCertType(Constant.CERT_TYPE.ID_CARD.name());
					}
				}
				
				if(itemArray.length>=5){
					mpi.setCertType(itemArray[4]);
				}
				
				if(itemArray.length>=6){
					if(Constant.CERT_TYPE.HUZHAO.name().equals(itemArray[4])){
						if(itemArray.length!=7){
							throw new LogicException("护照必须包含生日和性别");
						} else {
							mpi.setBirthday(itemArray[5]);
							mpi.setGender(itemArray[6]);
						}
					}
				}
			}
			personItemList.add(mpi);
		}

		return personItemList;
	}
	
	public Map<String,Object> resultMapCreator(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code","-1");
		return map;
	}
	
	/**
	 * 发送Ajax请求结果json
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void sendAjaxResultByJson(Map<String,Object> resultMap) {
		this.getResponse().setContentType("application/json;charset=UTF-8");
		this.getResponse().setCharacterEncoding("UTF-8");
		try {
			PrintWriter out = this.getResponse().getWriter();
			JSONObject jsonObj = JSONObject.fromObject(resultMap);
			out.write(jsonObj.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取HttpRequest
	 * 
	 * @return
	 */
	private HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	private HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}
}
