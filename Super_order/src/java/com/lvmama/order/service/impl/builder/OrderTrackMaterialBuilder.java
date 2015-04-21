package com.lvmama.order.service.impl.builder;


import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTrackRelate;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * 订单二次处理.
 * @author liwenzhan
 *
 */
public class OrderTrackMaterialBuilder implements IMaterialBuilder, TableName{

	/**
	 * 
	 */
	@Override
	public SQlBuilderMaterial buildMaterial(Object obj,
			SQlBuilderMaterial material) {
		final OrderTrackRelate orderTrack = (OrderTrackRelate) obj;
		
		if (orderTrack.isSearch()) {
			material.getTableSet().add(ORD_ORDER_TRACK);
			material.getConditionSet().add(
			      "ORD_ORDER.ORDER_ID = ORD_ORDER_TRACK.ORDER_ID");

			if (UtilityTool.isValid(orderTrack.getTrackOperator())) {
				material.getConditionSet().add(
						"ORD_ORDER_TRACK.USER_NAME = '"
								+ orderTrack.getTrackOperator() + "'");
			}
			if (UtilityTool.isValid(orderTrack.getTrackCreateTimeStart())) {
				material.getConditionSet().add(
						"ORD_ORDER_TRACK.CREATE_TIME >= TO_DATE('"
								+ UtilityTool.formatDate(orderTrack.getTrackCreateTimeStart())
								+ "','YYYY-MM-DD HH24:MI:SS')");
			}
			if (UtilityTool.isValid(orderTrack.getTrackCreateTimeEnd())) {
				material.getConditionSet().add(
						"ORD_ORDER_TRACK.CREATE_TIME < TO_DATE('"
								+ UtilityTool.formatDate(orderTrack.getTrackCreateTimeEnd())
								+ "','YYYY-MM-DD HH24:MI:SS')");
			}
			if (UtilityTool.isValid(orderTrack.getTrackStatus())) {
				if(orderTrack.getTrackStatus().equals("false")){
					material.getConditionSet().add(
						      "ORD_ORDER_TRACK.TRACK_STATUS ='"+orderTrack.getTrackStatus()+"'");
				}
				if(orderTrack.getTrackStatus().equals("trueOrfalse")){
					material.getConditionSet().add(
						      " (ORD_ORDER_TRACK.TRACK_STATUS ='true'  OR ORD_ORDER_TRACK.TRACK_STATUS ='false') ");
				}
			    if(orderTrack.getTrackStatus().equals("true")){
				material.getConditionSet().add(
					      "(ORD_ORDER_TRACK.TRACK_STATUS ='true' OR ORD_ORDER_TRACK.TRACK_STATUS IS NULL)");
			   }
			}
			if (UtilityTool.isValid(orderTrack.getTrackLogStatus())) {
				material.getTableSet().add(TRACK_LOG);
				material.getConditionSet().add(
				      "ORD_ORDER_TRACK.ORD_TRACK_ID = TRACK_LOG.TRACK_ID");
				material.getConditionSet().add(
			      "TRACK_LOG.TRACK_STATUS = '"+orderTrack.getTrackLogStatus()+"'");
			}
			
		}
		return material;
	}

	@Override
	public SQlBuilderMaterial buildMaterial(Object obj,
			SQlBuilderMaterial material, boolean businessflag) {
		return material;
	}

}
