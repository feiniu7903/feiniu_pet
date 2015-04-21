package com.lvmama.ord.logic;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.IdentityCardUtil;
import com.lvmama.comm.vo.Constant;
@Deprecated
public class BJOrderContractLogic implements OrderContractLogic {

	public void continueData(OrdOrder order, ProdProduct product,
			Map<String, Object> result) {
		//1.取得订单游玩人
		List<OrdPerson> ordPersonList=order.getPersonList();
		int amount=1;
		StringBuffer sb=new StringBuffer("");
	    for(int i=0;i<ordPersonList.size();i++){
	    	OrdPerson ordPerson= ordPersonList.get(i);
	    	String personType = ordPerson.getPersonType();
	    	if(personType != null && personType.equals("TRAVELLER")){
	    		sb.append("<tr>");
	    		result.put("touristName_"+amount, ordPerson.getName());										/**旅客姓名*/
	    		sb.append("<td>"+ordPerson.getName()+"</td>");
	    		if(null!=ordPerson.getCertNo()&&null!=ordPerson.getCertType()&&Constant.CERTIFICATE_TYPE.ID_CARD.name().equals(ordPerson.getCertType())){
	    			String sex=Constant.E_CONTRACT_DEFAULT_FILL;
	    			sex=IdentityCardUtil.getSex(ordPerson.getCertNo());
	    			sex=sex.replace("M","男").replace("F", "女");
	    			if(sex=="X"){
						sex=Constant.E_CONTRACT_DEFAULT_FILL;
					}
	    			sb.append("<td>"+(null!=sex?sex:Constant.E_CONTRACT_DEFAULT_FILL)+"</td>");
	    			sb.append("<td>"+getYear(ordPerson.getCertNo())+"</td>");												/**旅客年龄*/
	    		}else{
	    			sb.append("<td>"+Constant.E_CONTRACT_DEFAULT_FILL+"</td>");													/**旅客性别*/
	    			sb.append("<td>"+Constant.E_CONTRACT_DEFAULT_FILL+"</td>");													/**旅客年龄*/
	    		}
	    		sb.append("<td>"+Constant.E_CONTRACT_DEFAULT_FILL+"</td>");														/**旅客健康状况*/
    			sb.append("<td>"+Constant.E_CONTRACT_DEFAULT_FILL+"</td>");														/**备注*/
	    		amount++;
	    		sb.append("</tr>");
	    	}
	    }
	    result.put("alltouristInfo", sb.toString());
	    Object traveller=result.get("traveller");
		if(null!=traveller && amount>2){
			result.put("traveller", traveller+"(等共"+(amount-1)+"人)");
		}
		//团号：计调中订单所对应的团号
		result.put("groupNo", order.getTravelGroupCode());
	}
	private String getYear(String certNo){
		  if(null==certNo)
			  return Constant.E_CONTRACT_DEFAULT_FILL;
		  java.util.Date birthDay=null;
		  birthDay= IdentityCardUtil.getDate(certNo);
		  if(null!=birthDay){
			  Integer age=DateUtil.getAge(birthDay);
			  return age.toString();
		  }else{
			  return Constant.E_CONTRACT_DEFAULT_FILL;
		  }
	}
	@Override
	public String getContractNoPreffic() {
		return "bj";
	}

}
