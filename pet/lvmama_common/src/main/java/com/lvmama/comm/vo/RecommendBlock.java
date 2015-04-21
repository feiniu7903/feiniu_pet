package com.lvmama.comm.vo;

import java.io.Serializable;


/**
 * 该类为自动生成
 * @author yuzhibing
 * @since  2010-03-20 17:52:46
 * Company lvmama
 * Descripion ${table.comments}
 */
public class RecommendBlock implements Serializable{

        
    /**
	 * 
	 */
	private static final long serialVersionUID = 9117064423191876596L;


	/**  */
	private long id;

        
    /**  */
	private String placeId;

        
    /**  */
	private String modeType;

        
    /**  */
	private String remark;

        
    /**  */
	private String blockId;

        
    /**  */
	private long blockNumber;

        
    /**  */
	private long level_;

	private String station;

	/**
     * @return 
     */
	public Long getId() {
    	return this.id;
    }
    /**
     * @param _id 
     */
	public void setId(Long _id) {
    	this.id = _id;
    }


    /**
     * @return 
     */
	public String getPlaceId() {
    	return this.placeId;
    }
    /**
     * @param _placeId 
     */
	public void setPlaceId(String _placeId) {
    	this.placeId = _placeId;
    }


    /**
     * @return 
     */
	public String getModeType() {
    	return this.modeType;
    }
    /**
     * @param _modeType 
     */
	public void setModeType(String _modeType) {
    	this.modeType = _modeType;
    }


    /**
     * @return 
     */
	public String getRemark() {
    	return this.remark;
    }
    /**
     * @param _remark 
     */
	public void setRemark(String _remark) {
    	this.remark = _remark;
    }


    /**
     * @return 
     */
	public String getBlockId() {
    	return this.blockId;
    }
    /**
     * @param _blockId 
     */
	public void setBlockId(String _blockId) {
    	this.blockId = _blockId;
    }


    /**
     * @return 
     */
	public Long getBlockNumber() {
    	return this.blockNumber;
    }
    /**
     * @param _blockNumber 
     */
	public void setBlockNumber(Long _blockNumber) {
    	this.blockNumber = _blockNumber;
    }


    /**
     * @return 
     */
	public Long getLevel_() {
    	return this.level_;
    }
    /**
     * @param _level_ 
     */
	public void setLevel_(Long _level_) {
    	this.level_ = _level_;
    }

    public String getStation() {
		return station;
	}
    
	public void setStation(String station) {
		this.station = station;
	}
}