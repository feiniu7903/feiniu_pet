/**
 * 
 */
package com.lvmama.order.service.impl.builder;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * 团状态加入到组合查询
 * @author yangbin
 *
 */
public class TravelGroupBuilder implements IMaterialBuilder,TableName{

	@Override
	public SQlBuilderMaterial buildMaterial(Object obj,
			SQlBuilderMaterial material) {
		List<String> groups=(List<String>)obj;
		if(CollectionUtils.isNotEmpty(groups)){
			StringBuffer sb=new StringBuffer();
			int pos=0;
			sb.append("exists(SELECT 1 FROM OP_TRAVEL_GROUP WHERE OP_TRAVEL_GROUP.TRAVEL_GROUP_CODE = ORD_ORDER.TRAVEL_GROUP_CODE AND OP_TRAVEL_GROUP.TRAVEL_GROUP_STATUS IN(");
			for(String group:groups){
				if(pos++>0){
					sb.append(",");
				}
				sb.append("'");
				sb.append(group);
				sb.append("'");
			}
			sb.append("))");
			material.getConditionSet().add(sb.toString());
		}
		return material;
	}

	@Override
	public SQlBuilderMaterial buildMaterial(Object obj,
			SQlBuilderMaterial material, boolean businessflag) {
		// TODO Auto-generated method stub
		return null;
	}

}
