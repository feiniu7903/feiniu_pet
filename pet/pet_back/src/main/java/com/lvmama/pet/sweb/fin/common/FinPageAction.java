package com.lvmama.pet.sweb.fin.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import com.lvmama.comm.utils.StringUtil;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 提供分页查询的基础ACTION
 * 
 * @author yanggan
 * @version 结算重构 12/01/2012
 * 
 */
public abstract class FinPageAction extends BackBaseAction {
	private Logger log = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	protected long currentPage;
	protected long pageSize;
	protected String order;
	protected String orderBy;

	protected String businessName;
	/**
	 * 初始化Request中的查询参数
	 * 
	 * @return 包含查询参数的Map key为参数名/value为参数值
	 */
	public abstract Map<String, Object> initRequestParameter();

	/**
	 * 初始化分页查询参数
	 * 
	 * @return 实际传递给SqlMap的参数Map(包含分页信息与Request中的查询参数)
	 */
	public Map<String, Object> initSearchParameter() {
		Map<String, Object> searchParameter = initRequestParameter();
		searchParameter.put("currentPage", currentPage);
		searchParameter.put("pageSize", pageSize);
		searchParameter.put("order", order);
		searchParameter.put("orderby", orderBy);
		searchParameter.put("businessName", getBusinessName());
		return searchParameter;
	}

	/**
	 * 把HttpServletRequest中的参数转换为Map，转换后的参数值类型为String，request中的参数名称与Map中的Key一致
	 * 
	 * @param map
	 *            目标Map
	 * @param key
	 *            Map中的key
	 * @param request
	 *            HttpServletRequest
	 */
	protected void extractRequestParam(Map<String, Object> map, String key, HttpServletRequest request) {
		this.extractRequestParam(map, key, key, String.class, request);
	}

	/**
	 * 把HttpServletRequest中的参数转换为Map，转换后的参数值类型为String，request中的参数名称与Map中的Key一致
	 * 
	 * @param map
	 *            目标Map
	 * @param key
	 *            Map中的key
	 * @param cls
	 *            参数值的类型
	 * @param request
	 *            HttpServletRequest
	 */
	protected void extractRequestParam(Map<String, Object> map, String key, Class<?> cls, HttpServletRequest request) {
		this.extractRequestParam(map, key, key, cls, request);
	}

	/**
	 * 把HttpServletRequest中的参数转换为Map
	 * 
	 * @param map
	 *            目标Map
	 * @param key
	 *            Map中的key
	 * @param request
	 *            HttpServletRequest
	 * @param paramName
	 *            request中的参数名
	 * @param cls
	 *            参数值的类型
	 */
	protected void extractRequestParam(Map<String, Object> map, String key, String paramName, Class<?> cls, HttpServletRequest request) {
		String[] vals = request.getParameterValues(paramName);
		if (vals == null || vals.length == 0) {
			log.debug(paramName + "'s value is null");
			return;
		}
		if (vals.length == 1) {
			if (StringUtil.isEmptyString(vals[0])) {
				log.debug(paramName + "'s value is an empty string");
				return;
			}
			Object o_val = null;
			String val = vals[0].trim();
			if (Date.class.getName().equals(cls.getName())) {
				o_val = DateUtil.stringToDate(val, "yyyy-MM-dd");
			} else if (Long.class.getName().equals(cls.getName())) {
				o_val = Long.parseLong(val);
			} else if (Integer.class.getName().equals(cls.getName())) {
				o_val = Integer.parseInt(val);
			} else if (Float.class.getName().equals(cls.getName())) {
				o_val = Float.parseFloat(val);
			} else if (String.class.getName().equals(cls.getName())) {
				o_val = val;
			} else if (List.class.getName().equals(cls.getName())) {
				List<Object> list = new ArrayList<Object>();
				list.add(val);
				o_val = list;
			} else {
				throw new RuntimeException("Does not support " + cls + ",please add it by yourself!!");
			}
			map.put(key, o_val);
		} else if (vals.length > 1) {
			List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < vals.length; i++) {
				if ("".equals(vals[i])) {
					log.debug(paramName + "[" + i + "]'s value is an empty string");
					continue;
				}
				Object o_val = null;
				String val = vals[i].trim();
				o_val = val;
				list.add(o_val);
			}
			map.put(key, list);
		}

	}

	public void exportXLS(Map<String, Object> map, String path,String fileName){
		try {
			File templateResource = ResourceUtil.getResourceFile(path);
			String templateFileName = templateResource.getAbsolutePath();
			String destFileName = Constant.getTempDir() + "/" + fileName ;
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(templateFileName, map, destFileName);
			HttpServletResponse response = getResponse();
			if(fileName.indexOf(".xlsx") != -1	){
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			}else{
				response.setContentType("application/vnd.ms-excel");
			}
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);   
			File file = new File(destFileName);
			InputStream inputStream=new FileInputStream(destFileName);  
			if (file != null && file.exists()) {
				OutputStream os=response.getOutputStream();  
	            byte[] b=new byte[1024];  
	            int length;  
	            while((length=inputStream.read(b))>0){  
	                os.write(b,0,length);  
	            }  
	            inputStream.close();  
			} else {
				IOUtils.closeQuietly(inputStream);
				throw new RuntimeException("下载失败");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
}
