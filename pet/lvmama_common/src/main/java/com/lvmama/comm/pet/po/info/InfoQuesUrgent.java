package com.lvmama.comm.pet.po.info;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InfoQuesUrgent implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 483398422662973011L;

	private Long id;

    private String tittle;

    private String userName;

    private String content;

    private Date createTime;
    
    private Date beginTime;
    
    private Date endTime;
    
    private String beginTimeStr;
    
    private String endTimeStr;

	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH");
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle == null ? null : tittle.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

	public String getBeginTimeStr() {
		if(this.beginTime!=null){
			beginTimeStr = sf.format(this.beginTime);
		} 
		return beginTimeStr;
	}

	public void setBeginTimeStr(String beginTimeStr) throws ParseException {
		if (beginTimeStr!=null&&!"".equals(beginTimeStr)) {
			this.beginTime = sf.parse(beginTimeStr);
		}
		
	}

	public String getEndTimeStr() {
		if(this.endTime!=null){
			endTimeStr = sf.format(this.endTime);
		}
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) throws ParseException {
		if(endTimeStr!=null&&!"".equals(endTimeStr)){
			this.endTime = sf.parse(endTimeStr);
		}
		
	}
	

	public String getCreateTimeStr(){
		if(this.createTime!=null){
			return sf1.format(this.createTime);
		}
		return "";
	}
	
	/*public String getEndTimeStr() {
		
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) throws ParseException {
		this.endTime = sf.parse(endTimeStr);
	}

	public String getBeginTimeStr() {
		return beginTimeStr;
	}

	public void setBeginTimeStr(String beginTimeStr) throws ParseException {
		this.beginTime = sf.parse(beginTimeStr);
	}*/
}