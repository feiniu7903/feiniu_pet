package com.lvmama.vst.service;

import java.beans.PropertyEditor;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.commons.beanutils.BeanComparator;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimeRange;
import com.lvmama.comm.bee.po.prod.TimeRange.TimeRangePropertEditor;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vst.service.PetProdGoodsService;
import com.lvmama.comm.vst.service.VstProdUpdateService;
import com.lvmama.comm.vst.vo.VstProdGoodsTimePriceVo;

/**
 * 类别（商品）及库存修改（不保证事务）
 * @author taiqichao
 *
 */
public class PetGoodsServiceProxy implements PetProdGoodsService {
	
	private TopicMessageProducer productMessageProducer;
	
	private ProdProductService prodProductService;
	
	private ProdProductBranchService prodProductBranchService;
	
	private MetaProductBranchService metaProductBranchService;
	
	private VstProdUpdateService vstProdUpdateService;
	
	private MetaProductService metaProductService;

	
	private ResultHandle  updateGoodsLineStatus(Long branchId,boolean onLine) {
		ProdProductBranch prodProductBranch=prodProductBranchService.selectProdProductBranchByPK(branchId);
		if(null==prodProductBranch){
			return new ResultHandle("Unkown product branch id:"+branchId);
		}
		ProdProduct prodProduct=prodProductService.getProdProductById(prodProductBranch.getProductId());
		if(null==prodProduct){
			return new ResultHandle("Unkown prod product id:"+prodProductBranch.getProductId());
		}
		//更新
		vstProdUpdateService.updateProductLineStatus(prodProduct, prodProductBranch, onLine);
		//发送修改产品的消息
		productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(prodProduct.getProductId()));
		return new ResultHandle();
	}
	
	@Override
	public ResultHandle updateGoodsOnlineById(Long branchId) {
		return updateGoodsLineStatus(branchId,true);
	}

	@Override
	public ResultHandle updateGoodsOfflineById(Long branchId) {
		return updateGoodsLineStatus(branchId,false);
	}

	@Override
	public ResultHandle saveGoodsTimePrice(Long metaBranchId,List<VstProdGoodsTimePriceVo> timePrice) {
		if(null==timePrice||timePrice.size()==0){
			return new ResultHandle("Time price list is null,or size is 0.");
		}
		MetaProductBranch metaProductBranch=metaProductBranchService.getMetaBranch(metaBranchId);
		if(null==metaProductBranch){
			return new ResultHandle("Could not found the meta product branch,use branch id:"+metaBranchId);
		}
		//保存时间价格表
		vstProdUpdateService.saveTimePrice(metaProductBranch,timePrice);
		//发消息
		TimeRange tr=getTimeRange(timePrice);
		if(null!=tr){
			PropertyEditor editor = new TimeRangePropertEditor();
			editor.setValue(tr);	
			productMessageProducer.sendMsg(MessageFactory.newProductMetaPriceMessage(metaProductBranch.getMetaBranchId(),editor.getAsText()));
		}
		
		return new ResultHandle();
	}
	
	@SuppressWarnings("unchecked")
	private TimeRange getTimeRange(List<VstProdGoodsTimePriceVo> timePrice){
		Collections.sort(timePrice, new BeanComparator("specDate"));
		Date startDate=timePrice.get(0).getSpecDate();
		Date endDate=timePrice.get(timePrice.size()-1).getSpecDate();
		return new TimeRange(startDate,endDate);
	}

	@Override
	public ResultHandle updateStockByOrder(Long orderId, Long metaBranchId,Long stock, Date start, Date end) {
		MetaProductBranch metaProductBranch=metaProductBranchService.getMetaBranch(metaBranchId);
		if(null==metaProductBranch){
			return new ResultHandle("Could not found the meta product branch,use branch id:"+metaBranchId);
		}
		//更新库存
		vstProdUpdateService.updateStock(metaProductBranch, stock, start, end);
		//发消息
		TimeRange tr=new TimeRange(start, end);
		PropertyEditor editor = new TimeRangePropertEditor();
		editor.setValue(tr);
		productMessageProducer.sendMsg(MessageFactory.newProductMetaPriceMessage(metaProductBranch.getMetaBranchId(),editor.getAsText()));
		
		return new ResultHandle();
	}

	@Override
	public ResultHandle updateProdBranchPrice(Long metaBranchId, Date start,
			Date end) {
		ResultHandle handle = new ResultHandle();
		if(metaBranchId==null || start==null || end==null){
			handle.setMsg("mateBranchId or start or end is null,Can't null");
			return handle;
		}
		// 现在的消息直接向类别发送.
		TimeRange range=new TimeRange(start,end);
		PropertyEditor editor = new TimeRangePropertEditor();
		editor.setValue(range);			
		productMessageProducer.sendMsg(MessageFactory.newProductMetaPriceMessage(metaBranchId,editor.getAsText()));
		
		return handle;
	}
	@Override
	public ResultHandleT<String> getMetaProductBranchValid(Long metaProductId, String type) {
		return metaProductBranchService.getMetaProductBranchValid(metaProductId,type);
	}
	

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setProductMessageProducer(
			TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public void setVstProdUpdateService(VstProdUpdateService vstProdUpdateService) {
		this.vstProdUpdateService = vstProdUpdateService;
	}


}
