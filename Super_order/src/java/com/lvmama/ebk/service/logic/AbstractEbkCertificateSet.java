package com.lvmama.ebk.service.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.service.ebooking.CertificateService;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;

public class AbstractEbkCertificateSet {
		//新增凭证
		private Map<String, EbkCertificate> newEbkCert = new HashMap<String, EbkCertificate>();
		
		//该凭证需要标识之前的任务为无效
		private Map<String, EbkCertificate> newCancelChangeEbkCert = new HashMap<String, EbkCertificate>();
				
		//修改凭证
		private	List<EbkCertificate> updateEbkCert = new ArrayList<EbkCertificate>();
		/**
		 * 订单合并凭证
		 */
		private	List<EbkCertificate> mergeEbkCert = new ArrayList<EbkCertificate>();
		/**
		 * 客户取消凭证,修改凭证状态
		 */
		private	List<EbkCertificate> cancelEbkCert = new ArrayList<EbkCertificate>();
		
		/**
		 * 凭证子项逻辑删除
		 */
		private List<EbkCertificateItem> cancelEbkCertificateItems = new ArrayList<EbkCertificateItem>();
		/**
		 * 凭证需要逻辑删除
		 */
		private List<EbkCertificate> notValidEbkCert = new ArrayList<EbkCertificate>();
		private Map<Long, SupBCertificateTarget> supBCertificateTargetMap; 
		private String ebkCertificateEvent;
		private String userMemoStatus;
		private String orderItemMetaIdList;
		public void putCancelEbkCert(EbkCertificate ec){
			cancelEbkCert.add(ec);
		}
		public boolean hasNotValidEbkCertItem(){
			return cancelEbkCertificateItems.size()>0;
		}
		public boolean hasNotValidEbkCert(){
			return notValidEbkCert.size()>0;
		}
		public void putNotValidEbkCert(EbkCertificate certificate){
			notValidEbkCert.add(certificate);
		}
		public boolean hasOrderCancel(){
			return ebkCertificateEvent.equalsIgnoreCase(CertificateService.ORDER_CANCEL); 
		}
		public boolean isUpdateAperiodicOrder(){
			return ebkCertificateEvent.equalsIgnoreCase(CertificateService.UPDATE_APERIODIC_ORDER); 
		}
		public boolean hasOrderMemoChange(){
			return ebkCertificateEvent.equalsIgnoreCase(CertificateService.ORDER_MEMO_CHANGE); 
		}
		public boolean hasOrderModifyPerson(){
			return ebkCertificateEvent.equalsIgnoreCase(CertificateService.ORDER_MODIFY_PERSON);
		}
		public boolean hasEbkChangeToFax(){
			return ebkCertificateEvent.equalsIgnoreCase(CertificateService.EBK_CHANGE_TO_FAX);
		}
		
		public void putCancelEbkCertificateItems(EbkCertificateItem certificateItem){
			cancelEbkCertificateItems.add(certificateItem);
		}
		
		/**
		 * 结算价修改
		 * @return
		 */
		public boolean hasOrderModifySettlementPrice(){
			return ebkCertificateEvent.equalsIgnoreCase(CertificateService.ORDER_MODIFY_SETTLEMENT_PRICE);
		}
		/**
		 * 重发凭证
		 * @return
		 */
		public boolean hasRetransmission(){
			return ebkCertificateEvent.equalsIgnoreCase(CertificateService.RETRANSMISSION);
		}
		/**
		 * 创建订单
		 * @return
		 */
		public boolean hasOrderCreate(){
			return ebkCertificateEvent.equalsIgnoreCase(CertificateService.ORDER_CREATE);
		}
		/**
		 * 订单支付
		 * @return
		 */
		public boolean hasOrderPayment(){
			return ebkCertificateEvent.equalsIgnoreCase(CertificateService.ORDER_PAYMENT);
		}
		/**
		 * 订单审核
		 * @return
		 */
		public boolean hasOrderApprove(){
			return ebkCertificateEvent.equalsIgnoreCase(CertificateService.ORDER_APPROVE);
		}
		public String getEbkCertificateEvent() {
			return ebkCertificateEvent;
		}
		public void setEbkCertificateEvent(String ebkCertificateEvent) {
			this.ebkCertificateEvent = ebkCertificateEvent;
		}
		public boolean hasNewEbkCert(){
			return newEbkCert.size()>0;
		}
		public boolean hasNewCancelChangeEbkCert(){
			return !newCancelChangeEbkCert.isEmpty();
		}
		public boolean hasUpdateEbkCert(){
			return updateEbkCert.size()>0;
		}
		public boolean hasMergeEbkCert(){
			return mergeEbkCert.size()>0;
		}
		public boolean hasCancelEbkCert(){
			return cancelEbkCert.size()>0;
		}
		public Map<String, EbkCertificate> getNewEbkCert() {
			return newEbkCert;
		}

		public void setNewEbkCert(Map<String, EbkCertificate> newEbkCert) {
			this.newEbkCert = newEbkCert;
		}

		public List<EbkCertificate> getUpdateEbkCert() {
			return updateEbkCert;
		}
		public void setUpdateEbkCert(List<EbkCertificate> updateEbkCert) {
			this.updateEbkCert = updateEbkCert;
		}
 
		public List<EbkCertificate> getMergeEbkCert() {
			return mergeEbkCert;
		}
		public void setMergeEbkCert(List<EbkCertificate> mergeEbkCert) {
			this.mergeEbkCert = mergeEbkCert;
		}
	 
		public List<EbkCertificateItem> getCancelEbkCertificateItems() {
			return cancelEbkCertificateItems;
		}
		public void setCancelEbkCertificateItems(
				List<EbkCertificateItem> cancelEbkCertificateItems) {
			this.cancelEbkCertificateItems = cancelEbkCertificateItems;
		}
		public List<EbkCertificate> getNotValidEbkCert() {
			return notValidEbkCert;
		}
		public void setNotValidEbkCert(List<EbkCertificate> notValidEbkCert) {
			this.notValidEbkCert = notValidEbkCert;
		}
		public List<EbkCertificate> getCancelEbkCert() {
			return cancelEbkCert;
		}
		public void setCancelEbkCert(List<EbkCertificate> cancelEbkCert) {
			this.cancelEbkCert = cancelEbkCert;
		}
		public Map<Long, SupBCertificateTarget> getSupBCertificateTargetMap() {
			return supBCertificateTargetMap;
		}
		public void setSupBCertificateTargetMap(
				Map<Long, SupBCertificateTarget> supBCertificateTargetMap) {
			this.supBCertificateTargetMap = supBCertificateTargetMap;
		}
		
		public SupBCertificateTarget getSupBCertificateTargetBySupplierId(Long targetId){
			return this.supBCertificateTargetMap.get(targetId);
		}
		public Map<String, EbkCertificate> getNewCancelChangeEbkCert() {
			return newCancelChangeEbkCert;
		}
		public void setNewCancelChangeEbkCert(
				Map<String, EbkCertificate> newCancelChangeEbkCert) {
			this.newCancelChangeEbkCert = newCancelChangeEbkCert;
		}
		public String getUserMemoStatus() {
			return userMemoStatus;
		}
		public void setUserMemoStatus(String userMemoStatus) {
			this.userMemoStatus = userMemoStatus;
		}
		public String getOrderItemMetaIdList() {
			return orderItemMetaIdList;
		}
		public void setOrderItemMetaIdList(String orderItemMetaIdList) {
			this.orderItemMetaIdList = orderItemMetaIdList;
		}
	 
}
