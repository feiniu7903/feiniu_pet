package com.lvmama.distribution.sweb;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.bee.po.ckdevice.CkDeviceInfo;
import com.lvmama.comm.bee.po.ckdevice.CkDeviceProduct;
import com.lvmama.comm.bee.service.ckdevice.CkDeviceProductService;
import com.lvmama.comm.bee.service.ckdevice.CkDeviceService;
import com.lvmama.comm.utils.StringUtil;

public class CKProductAction extends BaseAction{

	private CkDeviceProductService deviceProductService;
	private CkDeviceService  deviceService;
	private String volid;
	private String pvolid;
	private String metaProductId;
	private String prductId;
	private String metaBranchId;
	private String branchId;
	private String ckId;
	private String dpId;
	
	
	/** 设备属性 */
	private String deviceCode;
	private String deviceName;
	private String memo;
	private Long start;
	private Long end;
	
	//http://super.lvmama.com/clutter/ck/queryDevice.do?ckId=29&deviceCode=&deviceName=&start=&end=
	@Action("/ck/queryDevice")
	public void queryDevice() throws UnsupportedEncodingException{
		String name = null;
		if(deviceName != null){
			 name = new String(deviceName.getBytes("ISO-8859-1"),"UTF-8");
		}
		Map<String, Object> parameterObject = new HashMap<String, Object>();
		parameterObject.put("ckDeviceCode", deviceCode);
		parameterObject.put("ckDeviceName", name);
		parameterObject.put("start", start !=null ? start : 0);
		parameterObject.put("end", end !=null ? end : 100);
		List<CkDeviceInfo> cd= deviceService.selectDeviceByParams(parameterObject);
		StringBuffer str = new StringBuffer();
		if(cd!=null){
			for(CkDeviceInfo device : cd){
				str.append(device.toString()).append("<br/>");
			}
		}
		sendAjaxMsg(str.toString());
	}
	
	//http://super.lvmama.com/clutter/ck/addDevice.do?deviceCode=&deviceName=&memo=
	@Action("/ck/addDevice")
	public void addDevice() throws UnsupportedEncodingException{
		CkDeviceInfo cd = new CkDeviceInfo();
		cd.setCkDeviceCode(deviceCode);
		String name = new String(deviceName.getBytes("ISO-8859-1"),"UTF-8");
		cd.setCkDeviceName(name);
		cd.setMemo(memo);
		deviceService.addDevice(cd);
		sendAjaxMsg("success");
	}
	//http://super.lvmama.com/clutter/ck/updateDevice.do?ckId=29&deviceCode=&deviceName=&memo=
	@Action("/ck/updateDevice")
	public void updateDevice() throws UnsupportedEncodingException{
		CkDeviceInfo cd = new CkDeviceInfo();
		cd.setCkDeviceInfoId(Long.valueOf(ckId));
		cd.setCkDeviceCode(deviceCode);
		String name = new String(deviceName.getBytes("ISO-8859-1"),"UTF-8");
		cd.setCkDeviceName(name);
		cd.setMemo(memo);
		deviceService.updateDevice(cd);
		sendAjaxMsg("success");
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//http://super.lvmama.com/clutter/ck/add.do?ckId=29&prductId=&branchId=&metaProductId=&metaBranchId=&volid=true&pvolid=true
	@Action("/ck/add")
	public void add() throws Exception {
		CkDeviceProduct cp = new CkDeviceProduct();
		cp.setDeviceInfoId(Long.valueOf(ckId));
		cp.setProductId(Long.valueOf(prductId));
		cp.setProductBranchId(Long.valueOf(branchId));
		cp.setMetaBranchId(Long.valueOf(metaBranchId));
		cp.setMetaProductId(Long.valueOf(metaProductId));
		cp.setPrintVolid(pvolid);
		cp.setVolid(volid);
		deviceProductService.save(cp);
	}
	//http://super.lvmama.com/clutter/ck/update.do?ckId=29&prductId=&branchId=&metaProductId=&metaBranchId=&volid=true&pvolid=true
	@Action("/ck/update")
	public void update() throws Exception {
		List<CkDeviceProduct> ckps =queryDP();
		if(ckps!=null){
			for(CkDeviceProduct dp : ckps){
				if(StringUtil.isNotEmptyString(pvolid)){
					dp.setPrintVolid(pvolid);
				}
				if(StringUtil.isNotEmptyString(volid)){
					dp.setVolid(volid);
				}
				deviceProductService.update(dp);
			}
		}
	}
	//http://super.lvmama.com/clutter/ck/del.do?prductId=&metaProductId=&dpId=
	@Action("/ck/del")
	public void del() throws Exception {
		if(StringUtil.isNotEmptyString(dpId)){
			deviceProductService.del(Long.valueOf(dpId));
			return;
		}
		List<CkDeviceProduct> ckps =queryDP();
		if(ckps!=null){
			for(CkDeviceProduct dp : ckps){
				deviceProductService.del(dp.getDeviceProductId());
			}
		}
		
	}
	//http://super.lvmama.com/clutter/ck/query.do?ckId=29&prductId=&branchId=&metaProductId=&metaBranchId=&volid=true&pvolid=true
	@Action("/ck/query")
	public void query() throws Exception {
		List<CkDeviceProduct> ckps =queryDP();
		StringBuffer str = new StringBuffer();
		if(ckps!=null){
			for(CkDeviceProduct dp : ckps){
				str.append(dp.getString()).append("<br/>");
			}
		}
		sendAjaxMsg(str.toString());
	}
	
	public List<CkDeviceProduct> queryDP(){
		Map<String, Object> params = bulidParm();
		return deviceProductService.query(params);
	}
	private Map<String, Object> bulidParm() {
		Map<String, Object> params = new HashMap<String,Object>();
		if(StringUtil.isNotEmptyString(pvolid)){
			params.put("printVolid", pvolid);
		}
		if(StringUtil.isNotEmptyString(volid)){
			params.put("volid", volid);
		}
		if(StringUtil.isNotEmptyString(metaProductId)){
			params.put("metaProductId", metaProductId);
		}
		if(StringUtil.isNotEmptyString(prductId)){
			params.put("productId", prductId);
		}
		if(StringUtil.isNotEmptyString(metaBranchId)){
			params.put("metaBranchId", metaBranchId);
		}
		if(StringUtil.isNotEmptyString(branchId)){
			params.put("productBranchId", branchId);
		}
		if(StringUtil.isNotEmptyString(ckId)){
			params.put("deviceInfoId", ckId);
		}
		if(StringUtil.isNotEmptyString(dpId)){
			params.put("deviceProductId", dpId);
		}
		return params;
	}
	public String getVolid() {
		return volid;
	}

	public void setVolid(String volid) {
		this.volid = volid;
	}

	public String getPvolid() {
		return pvolid;
	}

	public void setPvolid(String pvolid) {
		this.pvolid = pvolid;
	}

	public String getMetaProductId() {
		return metaProductId;
	}

	public void setMetaProductId(String metaProductId) {
		this.metaProductId = metaProductId;
	}

	public String getPrductId() {
		return prductId;
	}

	public void setPrductId(String prductId) {
		this.prductId = prductId;
	}

	public String getMetaBranchId() {
		return metaBranchId;
	}

	public void setMetaBranchId(String metaBranchId) {
		this.metaBranchId = metaBranchId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getCkId() {
		return ckId;
	}

	public void setCkId(String ckId) {
		this.ckId = ckId;
	}

	public String getDpId() {
		return dpId;
	}

	public void setDpId(String dpId) {
		this.dpId = dpId;
	}

	public void setDeviceProductService(CkDeviceProductService deviceProductService) {
		this.deviceProductService = deviceProductService;
	}

	public CkDeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(CkDeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public CkDeviceProductService getDeviceProductService() {
		return deviceProductService;
	}
	
	
}
