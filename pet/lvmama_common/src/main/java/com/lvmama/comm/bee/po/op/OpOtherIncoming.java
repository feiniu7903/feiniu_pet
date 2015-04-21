package com.lvmama.comm.bee.po.op;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 团附加收入
 * @author zhaojindong
 *
 */
public class OpOtherIncoming implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7172179479715221064L;

	private Long id;

    private Long budgetId;

    private String travelGroupCode;
    
    private Long costsItemId;

    private String costsItemName;

    private double amount;

    private String remark;

    private String creator;

    private Date createtime;
    
    private String incomingContent;

    public String getIncomingContent() {
		return incomingContent;
	}

	public void setIncomingContent(String incomingContent) {
		this.incomingContent = incomingContent;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

  

	public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public String getTravelGroupCode() {
        return travelGroupCode;
    }

    public void setTravelGroupCode(String travelGroupCode) {
        this.travelGroupCode = travelGroupCode;
    }
 

	public Long getCostsItemId() {
		return costsItemId;
	}

	public void setCostsItemId(Long costsItemId) {
		this.costsItemId = costsItemId;
	}

	public String getCostsItemName() {
		return costsItemName;
	}

	public void setCostsItemName(String costsItemName) {
		this.costsItemName = costsItemName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRemark() {
        return remark;
    }

    public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatetime() {
        return createtime;
    }
    public String getCreatetimeStr() {
    	if(null != createtime){
    		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createtime);
    	}
        return "";
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}