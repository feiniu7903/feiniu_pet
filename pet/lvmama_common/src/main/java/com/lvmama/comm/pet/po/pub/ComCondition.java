package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

public class ComCondition implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5491952165723824401L;

	private Long conditionId;

    private Long objectId;

    private Date createDate;

    private Date beginTime;

    private Date endTime;

    private String content;

    private String frontend = "false";

    private String conditionType;
    
    private String objectType;

    private CodeItem type;
    
    public Long getConditionId() {
        return conditionId;
    }

    public void setConditionId(Long conditionId) {
        this.conditionId = conditionId;
    }

    public Long getnObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrontend() {
		return frontend;
	}

	public void setFrontend(String frontend) {
		this.frontend = frontend;
	}

	public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }
    
    public CodeItem getType() {
    	if (type==null) {
    		CodeItem item = new CodeItem();
    		item.setCode(conditionType);
    		return item;
    	} else {
    		return type;
    	}
    }
    
    public void setType(CodeItem conditionType) {
    	this.type = conditionType;
    	this.conditionType = conditionType.getCode();
    }
    
    public String getZhConditionType() {
		return Constant.CONDITION_TYPE.getCnName(conditionType);
    }
    
	public String getTimeDescription() {
		SimpleDateFormat f=new SimpleDateFormat("MM/dd/yyyy");
		String desc="";
		if (beginTime!=null) {
			desc = desc.concat(f.format(beginTime));
		}
		desc = desc.concat("~");
		if (endTime!=null) {
			desc = desc.concat(f.format(endTime));
		}
		return desc;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public boolean equals(Object obj) {
		if (obj instanceof ComCondition) {
			ComCondition cc = (ComCondition)obj;
			if (conditionId==null) {
				return cc.getConditionId()==null;
			}else{
				return conditionId == cc.getConditionId() || conditionId.equals(cc.getConditionId());
			}
		}else{
			return false;
		}
	}

	@Override
	public int hashCode() {
		if (conditionId!=null)
			return conditionId.hashCode();
		else
			return 0;
	}

	@Override
	public String toString() {
		if (conditionId!=null)
			return "ComCondition_"+conditionId.toString();
		else
			return "ComCondition_null";
	}
}