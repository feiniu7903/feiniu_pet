package com.lvmama.back.utils;

import java.util.Iterator;
import java.util.List;

import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.vo.Constant;

public class ListboxCodeset extends Listbox{

	private static final long serialVersionUID = 1L;
	private String codeset;

	public String getCodeset() {
		return codeset;
	}

	public void setCodeset(String codeset) {
		
		if(codeset.equalsIgnoreCase("refundType")||codeset.equalsIgnoreCase("REFUND_TYPE")){
			Listitem listitem1 = new Listitem();
			listitem1.setLabel(Constant.REFUND_TYPE.ORDER_REFUNDED.getCnName());
			listitem1.setValue(Constant.REFUND_TYPE.ORDER_REFUNDED.name());
			this.appendChild(listitem1);
			
			Listitem listitem2 = new Listitem();
			listitem2.setLabel(Constant.REFUND_TYPE.COMPENSATION.getCnName());
			listitem2.setValue(Constant.REFUND_TYPE.COMPENSATION.name());
			this.appendChild(listitem2);
		}else if(codeset.equalsIgnoreCase("refundStatus")){
			Listitem listitem1 = new Listitem();
			listitem1.setLabel("已申请");
			listitem1.setValue("REFUND_APPLY");
			this.appendChild(listitem1);
			
			Listitem listitem2 = new Listitem();
			listitem2.setLabel("已确认");
			listitem2.setValue("APPLY_CONFIRM");
			listitem2.setSelected(true);
			this.appendChild(listitem2);
			
			Listitem listitem3 = new Listitem();
			listitem3.setLabel("未审核");
			listitem3.setValue("UNVERIFIED");
			this.appendChild(listitem3);
			
			Listitem listitem4 = new Listitem();
			listitem4.setLabel("退款单审核通过");
			listitem4.setValue("REFUND_VERIFIED");
			this.appendChild(listitem4);
			
			Listitem listitem5 = new Listitem();
			listitem5.setLabel("打款审核不通过");
			listitem5.setValue("REJECTED");
			this.appendChild(listitem5);
		}else if(codeset.equalsIgnoreCase("sysCode")){
			Listitem listitem1 = new Listitem();
			listitem1.setLabel("vst系统");
			listitem1.setValue("VST");
			this.appendChild(listitem1);
			
			Listitem listitem2 = new Listitem();
			listitem2.setLabel("super系统");
			listitem2.setValue("SUPER");
			listitem2.setSelected(true);
			this.appendChild(listitem2);
		}else if(codeset.equalsIgnoreCase("bizType")){
			Listitem listitem1 = new Listitem();
			listitem1.setLabel("老系统订单支付");
			listitem1.setValue("SUPER_ORDER");
			listitem1.setSelected(true);
			this.appendChild(listitem1);
			
			Listitem listitem2 = new Listitem();
			listitem2.setLabel("自由行订单支付");
			listitem2.setValue("BEE_ORDER");
			this.appendChild(listitem2);

			Listitem listitem3 = new Listitem();
			listitem3.setLabel("代售订单支付");
			listitem3.setValue("ANT_ORDER");
			this.appendChild(listitem3);

			Listitem listitem4 = new Listitem();
			listitem4.setLabel("TRANSHOTEL订单支付");
			listitem4.setValue("TRANSHOTEL_ORDER");
			this.appendChild(listitem4);

			Listitem listitem5 = new Listitem();
			listitem5.setLabel("VST订单支付");
			listitem5.setValue("VST_ORDER");
			this.appendChild(listitem5);

			Listitem listitem6 = new Listitem();
			listitem6.setLabel("现金帐户充值");
			listitem6.setValue("CASH_ACCOUNT");
			this.appendChild(listitem6);

			Listitem listitem7 = new Listitem();
			listitem7.setLabel("合并支付");
			listitem7.setValue("MERGE_PAY");
			this.appendChild(listitem7);
		}else{
			this.codeset = codeset;
			Listitem listitem = new Listitem();
			listitem.setLabel("-- 请选择  --");
			listitem.setValue("");
			listitem.setSelected(true);
			this.appendChild(listitem);
			
			List<CodeItem> list=CodeSet.getInstance().getCodeList(codeset);
			for (Iterator<CodeItem> iter = list.iterator(); iter.hasNext();) {
				CodeItem item =  iter.next();
				listitem = new Listitem();
				listitem.setLabel(item.getName());
				listitem.setValue(item.getCode());
				this.appendChild(listitem);
			}
		}
	}
	
	public String getValue() {
		return this.getSelectedCount()==1 ? this.getSelectedItem().getValue() + "" : "";
	}
}
