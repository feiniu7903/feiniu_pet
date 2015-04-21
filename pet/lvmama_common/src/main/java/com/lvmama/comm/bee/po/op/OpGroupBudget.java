package com.lvmama.comm.bee.po.op;

import java.io.Serializable;
import java.util.List;

/**
 * 团预算
 * @author zhaojindong
 *
 */
public class OpGroupBudget implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8861433769086563096L;

	private Long budgetId;

    private String travelGroupCode;
    
    private Long travelGroupId;

    private Long bgPersons;

    private Double salePrice;

    private Double bgTotalCosts;

    private Double bgPerCosts;

    private Double bgIncoming;

    private Double bgProfit;

    private Double bgProfitRate;

    private Long actPersons;
    
    private Long actAdult;
    
    private Long actChild;

    private Double actIncoming;

    private Double actAllowance;

    private Double actTotalCosts;

    private Double actPerCosts;

    private Double actProfit;

    private Double actProfitRate;

    private String status;
    
    private List<OpGroupBudgetFixed> fixeds;
    
    private List<OpGroupBudgetProd> prods;

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

    public Long getBgPersons() {
        return bgPersons;
    }

    public void setBgPersons(Long bgPersons) {
        this.bgPersons = bgPersons;
    }

 

    public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getBgTotalCosts() {
		return bgTotalCosts;
	}

	public void setBgTotalCosts(Double bgTotalCosts) {
		this.bgTotalCosts = bgTotalCosts;
	}

	public Double getBgPerCosts() {
		return bgPerCosts;
	}

	public void setBgPerCosts(Double bgPerCosts) {
		this.bgPerCosts = bgPerCosts;
	}

	public Double getBgIncoming() {
		return bgIncoming;
	}

	public void setBgIncoming(Double bgIncoming) {
		this.bgIncoming = bgIncoming;
	}

	public Double getBgProfit() {
		return bgProfit;
	}

	public void setBgProfit(Double bgProfit) {
		this.bgProfit = bgProfit;
	}

	public void setBgProfit(Long bgProfit) {
		if(bgProfit != null){
			this.bgProfit = bgProfit.doubleValue();
		}
    }

    public Double getBgProfitRate() {
        return bgProfitRate;
    }

    public void setBgProfitRate(Double bgProfitRate) {
        this.bgProfitRate = bgProfitRate;
    }

    public Long getActPersons() {
        return actPersons;
    }

    public void setActPersons(Long actPersons) {
        this.actPersons = actPersons;
    }

   

    public Double getActIncoming() {
		return actIncoming;
	}

	public void setActIncoming(Double actIncoming) {
		this.actIncoming = actIncoming;
	}

	public Double getActAllowance() {
		return actAllowance;
	}

	public void setActAllowance(Double actAllowance) {
		this.actAllowance = actAllowance;
	}

	public Double getActTotalCosts() {
		return actTotalCosts;
	}

	public void setActTotalCosts(Double actTotalCosts) {
		this.actTotalCosts = actTotalCosts;
	}

	public Double getActPerCosts() {
		return actPerCosts;
	}

	public void setActPerCosts(Double actPerCosts) {
		this.actPerCosts = actPerCosts;
	}

	public Double getActProfit() {
		return actProfit;
	}

	public void setActProfit(Double actProfit) {
		this.actProfit = actProfit;
	}

	public void setActProfit(Long actProfit) {
		if(actProfit!= null){
			this.actProfit = actProfit.doubleValue();
		}
    }

    public Double getActProfitRate() {
        return actProfitRate;
    }

    public void setActProfitRate(Double actProfitRate) {
        this.actProfitRate = actProfitRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public List<OpGroupBudgetFixed> getFixeds() {
		return fixeds;
	}

	public void setFixeds(List<OpGroupBudgetFixed> fixeds) {
		this.fixeds = fixeds;
	}

	public Long getTravelGroupId() {
		return travelGroupId;
	}

	public void setTravelGroupId(Long travelGroupId) {
		this.travelGroupId = travelGroupId;
	}

	public Long getActAdult() {
		return actAdult;
	}

	public void setActAdult(Long actAdult) {
		this.actAdult = actAdult;
	}

	public Long getActChild() {
		return actChild;
	}

	public void setActChild(Long actChild) {
		this.actChild = actChild;
	}

	public List<OpGroupBudgetProd> getProds() {
		return prods;
	}

	public void setProds(List<OpGroupBudgetProd> prods) {
		this.prods = prods;
	}
	
}