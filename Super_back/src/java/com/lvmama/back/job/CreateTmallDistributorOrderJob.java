package com.lvmama.back.job;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.tmall.OrdTmallDistributorMap;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.tmall.OrdTmallDistributorMapService;
import com.lvmama.comm.bee.service.tmall.TOPInterface;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.taobao.api.domain.PurchaseOrder;
import com.taobao.api.domain.SubPurchaseOrder;

/***
 * 调用淘宝分销批量查询接口,将可搬单的订单插入ord_tmall_distributor_map表中
 * 
 * @author YuanXueBo
 *
 */
public class CreateTmallDistributorOrderJob {
	
	private static final Log log = LogFactory.getLog(CreateTmallDistributorOrderJob.class);
	private OrdTmallDistributorMapService ordTmallDistributorMapService;
	private ProdProductService prodProductService;
	
	public void run() {
		if (Constant.getInstance().isJobRunnable() && Constant.getInstance().isSyncTmallDistributorOrder()) {
			log.info("CreateTmallDistributorOrderJob begin run.");
			Date dsDate=DateUtil.dsDay_Date(new Date(), -3);
			boolean flag=true;
			Long pageNum=0L;
			List<PurchaseOrder> purchaseOrderList=null;
			while(flag && pageNum<100){
				pageNum++;
				try{
					purchaseOrderList=TOPInterface.getPurchaseOrderList(dsDate, new Date(),pageNum, "WAIT_BUYER_CONFIRM_GOODS",null);
					if(purchaseOrderList!=null && purchaseOrderList.size()>0){
						creatTmallDistributorOrd(purchaseOrderList);
					}else{
						flag=false;
					}
				}catch(Exception e){
					log.error("CreateTmallDistributorOrderJob Error==>"+e);	
				}
			}
			log.info("CreateTmallDistributorOrderJob end run.");
		}
	}
	public  void creatTmallDistributorOrd(List<PurchaseOrder> purchaseOrderList) throws Exception{
		if(purchaseOrderList!=null && purchaseOrderList.size()>0){
			String pro_id=null;
			String categ_id=null;
			ProdProduct prodProduct=null;
			for(PurchaseOrder purchaseOrder:purchaseOrderList){
				//供应商备注旗帜 1:红色 2:黄色 3:绿色 4:蓝色 5:粉红色
				if(purchaseOrder!=null && purchaseOrder.getSupplierFlag().compareTo(3L)==0){
					//防止相同淘宝订单号重复拉取数据
					 if(!ordTmallDistributorMapService.getOrdTmallDistributorMapCount(purchaseOrder.getFenxiaoId())){
						 continue;
					 }
					 List<SubPurchaseOrder> subPurchaseOrderList=purchaseOrder.getSubPurchaseOrders();
					 if(subPurchaseOrderList!=null && subPurchaseOrderList.size()>0){
						 OrdTmallDistributorMap ordTmallDistributorMap = null;
						 for(SubPurchaseOrder subPurchaseOrder:subPurchaseOrderList){
							 ordTmallDistributorMap =new OrdTmallDistributorMap();
							 ordTmallDistributorMap.setFenXiaoId(purchaseOrder.getFenxiaoId());
							 ordTmallDistributorMap.setDistributorNo(purchaseOrder.getId());
							 ordTmallDistributorMap.setTcOrderId(purchaseOrder.getTcOrderId());
							 ordTmallDistributorMap.setDistributorUserName(purchaseOrder.getDistributorUsername());
							 ordTmallDistributorMap.setBuyerMobile(purchaseOrder.getReceiver().getMobilePhone());
							 ordTmallDistributorMap.setAlipayNo(purchaseOrder.getAlipayNo());
							 ordTmallDistributorMap.setPayTime(purchaseOrder.getPayTime());
							 ordTmallDistributorMap.setTitle(subPurchaseOrder.getTitle());
							 ordTmallDistributorMap.setNum(subPurchaseOrder.getNum().intValue());
							 ordTmallDistributorMap.setPrice(new Float(subPurchaseOrder.getPrice()));
							 ordTmallDistributorMap.setTmallMemo(purchaseOrder.getSupplierMemo());

							 //获取产品ID和类别ID
							 String pro_categ="";
							 if(subPurchaseOrder.getItemOuterId()!=null && !"".equals(subPurchaseOrder.getItemOuterId())){
								 pro_categ=subPurchaseOrder.getItemOuterId();
								 if(pro_categ!=null&&pro_categ.length()>0){
									 if(pro_categ.indexOf(",")!=-1){
										 String arrs[]=pro_categ.split(",");
										 pro_id=arrs[0].trim();
										 categ_id=arrs[1].trim();
									 }else{
										 pro_id=pro_categ.trim();
										 categ_id=pro_categ.trim();
									 }															 
								 }
							 }else{
								 pro_categ=subPurchaseOrder.getSkuOuterId();
								 if(pro_categ!=null&&pro_categ.length()>0){
									 if(pro_categ.indexOf(",")!=-1){
										 String arrs[]=pro_categ.split(",");
										 pro_id=arrs[0].trim();
										 categ_id=arrs[1].trim();
									 }else{
										 pro_id=pro_categ.trim();
										 categ_id=pro_categ.trim();
									 }															 
								 }
							 }
							
							 if(pro_id==null||categ_id==null){
								 ordTmallDistributorMap.setStatus("failure"); 
								 ordTmallDistributorMap.setFailedReason("缺少产品id或类别id");
							 }else{
								 prodProduct = prodProductService.getProdProduct(Long.valueOf(pro_id));
								 if(prodProduct==null){
									 ordTmallDistributorMap.setStatus("failure"); 
									 ordTmallDistributorMap.setFailedReason("错误的产品ID");
								 }else{
									 ordTmallDistributorMap.setProductType(prodProduct.getProductType());
									 ordTmallDistributorMap.setStatus("create");
								 }
								 ordTmallDistributorMap.setProductId(Long.valueOf(pro_id));
								 ordTmallDistributorMap.setCategoryId(Long.valueOf(categ_id));
								
							 }
							 ordTmallDistributorMap.setSystemOrder("true");
							 ordTmallDistributorMap.setCreateTime(new Date());
							 ordTmallDistributorMapService.insert(ordTmallDistributorMap);
						 }
					 }
				}
			}
		}
		
	}

	public void setOrdTmallDistributorMapService(
			OrdTmallDistributorMapService ordTmallDistributorMapService) {
		this.ordTmallDistributorMapService = ordTmallDistributorMapService;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	

}
