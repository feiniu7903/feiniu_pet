/**
 * ScenicBean.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.lvmama.passport.processor.impl.client.shandong.model;

public class ScenicBean  implements java.io.Serializable {
    private java.lang.String adaptGroup;

    private java.lang.String address;

    private java.lang.String adviceEqu;

    private java.lang.String adviceLine;

    private java.lang.String attdesM;

    private java.lang.Integer auditState;

    private com.lvmama.passport.processor.impl.client.shandong.model.GeneralState auditStateEnums;

    private java.lang.Integer avgPrice;

    private java.lang.String bestSeason;

    private java.lang.String busInf;

    private java.lang.Integer checkResul;

    private java.lang.String comWebsite;

    private java.util.Calendar createTime;

    private java.lang.String creatorId;

    private java.lang.String defaultPhoto;

    private java.lang.String destName;

    private java.lang.String destinationId;

    private java.lang.Integer disGasStation;

    private java.lang.String exposterPrice;

    private java.lang.String finNetwork;

    private java.lang.Boolean flag;

    private java.lang.String fullName;

    private java.lang.String gasStationName;

    private java.lang.String ifGasstation;

    private java.lang.Integer incitement;

    private java.lang.String introduction;

    private java.lang.String keywords;

    private java.lang.Integer language;

    private java.lang.Double latitude;

    private java.lang.String lineDes;

    private com.lvmama.passport.processor.impl.client.shandong.model.ContactBean[] listContact;

    private com.lvmama.passport.processor.impl.client.shandong.model.PictureBean[] listPicture;

    private com.lvmama.passport.processor.impl.client.shandong.model.ScenicBranchBean[] listScenicBranch;

    private com.lvmama.passport.processor.impl.client.shandong.model.InfoScenicTitleBean[] listScenicTitle;

    private com.lvmama.passport.processor.impl.client.shandong.model.ScenicTypeBean[] listScenicType;

    private java.util.Calendar lockTime;

    private java.lang.String lockerId;

    private java.lang.Double longitude;

    private java.util.Calendar modTime;

    private java.lang.String modifierId;

    private java.lang.String myId;

    private java.lang.String myPrice;

    private java.lang.String nameCn;

    private java.lang.String nameEn;

    private java.lang.Integer noticket;

    private java.lang.String openTime;

    private java.lang.String parkInf;

    private java.lang.Integer parkSpaceNumber;

    private java.lang.Integer permanentAct;

    private java.lang.String picPath;

    private java.lang.String postcode;

    private com.lvmama.passport.processor.impl.client.shandong.model.RecDegree recDegree;

    private com.lvmama.passport.processor.impl.client.shandong.model.RecDegree recDegreeEnum;

    private java.lang.String scenicId;

    private com.lvmama.passport.processor.impl.client.shandong.model.ScenicLevel scenicLevelEnum;

    private com.lvmama.passport.processor.impl.client.shandong.model.ScenicLevel scenicLevelId;

    private java.lang.String scenicLevelPhoto;

    private java.lang.String scenicLogo;

    private java.lang.String selfDrInf;

    private java.lang.String serNote;

    private java.lang.String serviceCenter;

    private java.lang.String shortName;

    private java.lang.String sightFea;

    private java.lang.Integer siteArea;

    private java.lang.String siteCaption;

    private java.lang.String siteMagName;

    private java.lang.String siteProject;

    private java.lang.String siteSlogan;

    private java.lang.String siteSpec;

    private java.lang.String startTime;

    private java.lang.String suppSerFac;

    private java.lang.String tags;

    private java.lang.String ticketDes;

    private java.lang.String ticketNoCaption;

    private java.lang.Integer tourTime;

    private java.lang.String trafficInf;

    private java.lang.String travelTip;

    private java.lang.String type;

    private java.lang.String unitCode;

    private java.lang.Integer unitCodeType;

    private java.lang.String userId;

    private java.lang.String username;

    private java.lang.Integer version;

    private java.lang.Integer viewdegree;

    private java.lang.String websiteM;

    public ScenicBean() {
    }

    public ScenicBean(
           java.lang.String adaptGroup,
           java.lang.String address,
           java.lang.String adviceEqu,
           java.lang.String adviceLine,
           java.lang.String attdesM,
           java.lang.Integer auditState,
           com.lvmama.passport.processor.impl.client.shandong.model.GeneralState auditStateEnums,
           java.lang.Integer avgPrice,
           java.lang.String bestSeason,
           java.lang.String busInf,
           java.lang.Integer checkResul,
           java.lang.String comWebsite,
           java.util.Calendar createTime,
           java.lang.String creatorId,
           java.lang.String defaultPhoto,
           java.lang.String destName,
           java.lang.String destinationId,
           java.lang.Integer disGasStation,
           java.lang.String exposterPrice,
           java.lang.String finNetwork,
           java.lang.Boolean flag,
           java.lang.String fullName,
           java.lang.String gasStationName,
           java.lang.String ifGasstation,
           java.lang.Integer incitement,
           java.lang.String introduction,
           java.lang.String keywords,
           java.lang.Integer language,
           java.lang.Double latitude,
           java.lang.String lineDes,
           com.lvmama.passport.processor.impl.client.shandong.model.ContactBean[] listContact,
           com.lvmama.passport.processor.impl.client.shandong.model.PictureBean[] listPicture,
           com.lvmama.passport.processor.impl.client.shandong.model.ScenicBranchBean[] listScenicBranch,
           com.lvmama.passport.processor.impl.client.shandong.model.InfoScenicTitleBean[] listScenicTitle,
           com.lvmama.passport.processor.impl.client.shandong.model.ScenicTypeBean[] listScenicType,
           java.util.Calendar lockTime,
           java.lang.String lockerId,
           java.lang.Double longitude,
           java.util.Calendar modTime,
           java.lang.String modifierId,
           java.lang.String myId,
           java.lang.String myPrice,
           java.lang.String nameCn,
           java.lang.String nameEn,
           java.lang.Integer noticket,
           java.lang.String openTime,
           java.lang.String parkInf,
           java.lang.Integer parkSpaceNumber,
           java.lang.Integer permanentAct,
           java.lang.String picPath,
           java.lang.String postcode,
           com.lvmama.passport.processor.impl.client.shandong.model.RecDegree recDegree,
           com.lvmama.passport.processor.impl.client.shandong.model.RecDegree recDegreeEnum,
           java.lang.String scenicId,
           com.lvmama.passport.processor.impl.client.shandong.model.ScenicLevel scenicLevelEnum,
           com.lvmama.passport.processor.impl.client.shandong.model.ScenicLevel scenicLevelId,
           java.lang.String scenicLevelPhoto,
           java.lang.String scenicLogo,
           java.lang.String selfDrInf,
           java.lang.String serNote,
           java.lang.String serviceCenter,
           java.lang.String shortName,
           java.lang.String sightFea,
           java.lang.Integer siteArea,
           java.lang.String siteCaption,
           java.lang.String siteMagName,
           java.lang.String siteProject,
           java.lang.String siteSlogan,
           java.lang.String siteSpec,
           java.lang.String startTime,
           java.lang.String suppSerFac,
           java.lang.String tags,
           java.lang.String ticketDes,
           java.lang.String ticketNoCaption,
           java.lang.Integer tourTime,
           java.lang.String trafficInf,
           java.lang.String travelTip,
           java.lang.String type,
           java.lang.String unitCode,
           java.lang.Integer unitCodeType,
           java.lang.String userId,
           java.lang.String username,
           java.lang.Integer version,
           java.lang.Integer viewdegree,
           java.lang.String websiteM) {
           this.adaptGroup = adaptGroup;
           this.address = address;
           this.adviceEqu = adviceEqu;
           this.adviceLine = adviceLine;
           this.attdesM = attdesM;
           this.auditState = auditState;
           this.auditStateEnums = auditStateEnums;
           this.avgPrice = avgPrice;
           this.bestSeason = bestSeason;
           this.busInf = busInf;
           this.checkResul = checkResul;
           this.comWebsite = comWebsite;
           this.createTime = createTime;
           this.creatorId = creatorId;
           this.defaultPhoto = defaultPhoto;
           this.destName = destName;
           this.destinationId = destinationId;
           this.disGasStation = disGasStation;
           this.exposterPrice = exposterPrice;
           this.finNetwork = finNetwork;
           this.flag = flag;
           this.fullName = fullName;
           this.gasStationName = gasStationName;
           this.ifGasstation = ifGasstation;
           this.incitement = incitement;
           this.introduction = introduction;
           this.keywords = keywords;
           this.language = language;
           this.latitude = latitude;
           this.lineDes = lineDes;
           this.listContact = listContact;
           this.listPicture = listPicture;
           this.listScenicBranch = listScenicBranch;
           this.listScenicTitle = listScenicTitle;
           this.listScenicType = listScenicType;
           this.lockTime = lockTime;
           this.lockerId = lockerId;
           this.longitude = longitude;
           this.modTime = modTime;
           this.modifierId = modifierId;
           this.myId = myId;
           this.myPrice = myPrice;
           this.nameCn = nameCn;
           this.nameEn = nameEn;
           this.noticket = noticket;
           this.openTime = openTime;
           this.parkInf = parkInf;
           this.parkSpaceNumber = parkSpaceNumber;
           this.permanentAct = permanentAct;
           this.picPath = picPath;
           this.postcode = postcode;
           this.recDegree = recDegree;
           this.recDegreeEnum = recDegreeEnum;
           this.scenicId = scenicId;
           this.scenicLevelEnum = scenicLevelEnum;
           this.scenicLevelId = scenicLevelId;
           this.scenicLevelPhoto = scenicLevelPhoto;
           this.scenicLogo = scenicLogo;
           this.selfDrInf = selfDrInf;
           this.serNote = serNote;
           this.serviceCenter = serviceCenter;
           this.shortName = shortName;
           this.sightFea = sightFea;
           this.siteArea = siteArea;
           this.siteCaption = siteCaption;
           this.siteMagName = siteMagName;
           this.siteProject = siteProject;
           this.siteSlogan = siteSlogan;
           this.siteSpec = siteSpec;
           this.startTime = startTime;
           this.suppSerFac = suppSerFac;
           this.tags = tags;
           this.ticketDes = ticketDes;
           this.ticketNoCaption = ticketNoCaption;
           this.tourTime = tourTime;
           this.trafficInf = trafficInf;
           this.travelTip = travelTip;
           this.type = type;
           this.unitCode = unitCode;
           this.unitCodeType = unitCodeType;
           this.userId = userId;
           this.username = username;
           this.version = version;
           this.viewdegree = viewdegree;
           this.websiteM = websiteM;
    }


    /**
     * Gets the adaptGroup value for this ScenicBean.
     * 
     * @return adaptGroup
     */
    public java.lang.String getAdaptGroup() {
        return adaptGroup;
    }


    /**
     * Sets the adaptGroup value for this ScenicBean.
     * 
     * @param adaptGroup
     */
    public void setAdaptGroup(java.lang.String adaptGroup) {
        this.adaptGroup = adaptGroup;
    }


    /**
     * Gets the address value for this ScenicBean.
     * 
     * @return address
     */
    public java.lang.String getAddress() {
        return address;
    }


    /**
     * Sets the address value for this ScenicBean.
     * 
     * @param address
     */
    public void setAddress(java.lang.String address) {
        this.address = address;
    }


    /**
     * Gets the adviceEqu value for this ScenicBean.
     * 
     * @return adviceEqu
     */
    public java.lang.String getAdviceEqu() {
        return adviceEqu;
    }


    /**
     * Sets the adviceEqu value for this ScenicBean.
     * 
     * @param adviceEqu
     */
    public void setAdviceEqu(java.lang.String adviceEqu) {
        this.adviceEqu = adviceEqu;
    }


    /**
     * Gets the adviceLine value for this ScenicBean.
     * 
     * @return adviceLine
     */
    public java.lang.String getAdviceLine() {
        return adviceLine;
    }


    /**
     * Sets the adviceLine value for this ScenicBean.
     * 
     * @param adviceLine
     */
    public void setAdviceLine(java.lang.String adviceLine) {
        this.adviceLine = adviceLine;
    }


    /**
     * Gets the attdesM value for this ScenicBean.
     * 
     * @return attdesM
     */
    public java.lang.String getAttdesM() {
        return attdesM;
    }


    /**
     * Sets the attdesM value for this ScenicBean.
     * 
     * @param attdesM
     */
    public void setAttdesM(java.lang.String attdesM) {
        this.attdesM = attdesM;
    }


    /**
     * Gets the auditState value for this ScenicBean.
     * 
     * @return auditState
     */
    public java.lang.Integer getAuditState() {
        return auditState;
    }


    /**
     * Sets the auditState value for this ScenicBean.
     * 
     * @param auditState
     */
    public void setAuditState(java.lang.Integer auditState) {
        this.auditState = auditState;
    }


    /**
     * Gets the auditStateEnums value for this ScenicBean.
     * 
     * @return auditStateEnums
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.GeneralState getAuditStateEnums() {
        return auditStateEnums;
    }


    /**
     * Sets the auditStateEnums value for this ScenicBean.
     * 
     * @param auditStateEnums
     */
    public void setAuditStateEnums(com.lvmama.passport.processor.impl.client.shandong.model.GeneralState auditStateEnums) {
        this.auditStateEnums = auditStateEnums;
    }


    /**
     * Gets the avgPrice value for this ScenicBean.
     * 
     * @return avgPrice
     */
    public java.lang.Integer getAvgPrice() {
        return avgPrice;
    }


    /**
     * Sets the avgPrice value for this ScenicBean.
     * 
     * @param avgPrice
     */
    public void setAvgPrice(java.lang.Integer avgPrice) {
        this.avgPrice = avgPrice;
    }


    /**
     * Gets the bestSeason value for this ScenicBean.
     * 
     * @return bestSeason
     */
    public java.lang.String getBestSeason() {
        return bestSeason;
    }


    /**
     * Sets the bestSeason value for this ScenicBean.
     * 
     * @param bestSeason
     */
    public void setBestSeason(java.lang.String bestSeason) {
        this.bestSeason = bestSeason;
    }


    /**
     * Gets the busInf value for this ScenicBean.
     * 
     * @return busInf
     */
    public java.lang.String getBusInf() {
        return busInf;
    }


    /**
     * Sets the busInf value for this ScenicBean.
     * 
     * @param busInf
     */
    public void setBusInf(java.lang.String busInf) {
        this.busInf = busInf;
    }


    /**
     * Gets the checkResul value for this ScenicBean.
     * 
     * @return checkResul
     */
    public java.lang.Integer getCheckResul() {
        return checkResul;
    }


    /**
     * Sets the checkResul value for this ScenicBean.
     * 
     * @param checkResul
     */
    public void setCheckResul(java.lang.Integer checkResul) {
        this.checkResul = checkResul;
    }


    /**
     * Gets the comWebsite value for this ScenicBean.
     * 
     * @return comWebsite
     */
    public java.lang.String getComWebsite() {
        return comWebsite;
    }


    /**
     * Sets the comWebsite value for this ScenicBean.
     * 
     * @param comWebsite
     */
    public void setComWebsite(java.lang.String comWebsite) {
        this.comWebsite = comWebsite;
    }


    /**
     * Gets the createTime value for this ScenicBean.
     * 
     * @return createTime
     */
    public java.util.Calendar getCreateTime() {
        return createTime;
    }


    /**
     * Sets the createTime value for this ScenicBean.
     * 
     * @param createTime
     */
    public void setCreateTime(java.util.Calendar createTime) {
        this.createTime = createTime;
    }


    /**
     * Gets the creatorId value for this ScenicBean.
     * 
     * @return creatorId
     */
    public java.lang.String getCreatorId() {
        return creatorId;
    }


    /**
     * Sets the creatorId value for this ScenicBean.
     * 
     * @param creatorId
     */
    public void setCreatorId(java.lang.String creatorId) {
        this.creatorId = creatorId;
    }


    /**
     * Gets the defaultPhoto value for this ScenicBean.
     * 
     * @return defaultPhoto
     */
    public java.lang.String getDefaultPhoto() {
        return defaultPhoto;
    }


    /**
     * Sets the defaultPhoto value for this ScenicBean.
     * 
     * @param defaultPhoto
     */
    public void setDefaultPhoto(java.lang.String defaultPhoto) {
        this.defaultPhoto = defaultPhoto;
    }


    /**
     * Gets the destName value for this ScenicBean.
     * 
     * @return destName
     */
    public java.lang.String getDestName() {
        return destName;
    }


    /**
     * Sets the destName value for this ScenicBean.
     * 
     * @param destName
     */
    public void setDestName(java.lang.String destName) {
        this.destName = destName;
    }


    /**
     * Gets the destinationId value for this ScenicBean.
     * 
     * @return destinationId
     */
    public java.lang.String getDestinationId() {
        return destinationId;
    }


    /**
     * Sets the destinationId value for this ScenicBean.
     * 
     * @param destinationId
     */
    public void setDestinationId(java.lang.String destinationId) {
        this.destinationId = destinationId;
    }


    /**
     * Gets the disGasStation value for this ScenicBean.
     * 
     * @return disGasStation
     */
    public java.lang.Integer getDisGasStation() {
        return disGasStation;
    }


    /**
     * Sets the disGasStation value for this ScenicBean.
     * 
     * @param disGasStation
     */
    public void setDisGasStation(java.lang.Integer disGasStation) {
        this.disGasStation = disGasStation;
    }


    /**
     * Gets the exposterPrice value for this ScenicBean.
     * 
     * @return exposterPrice
     */
    public java.lang.String getExposterPrice() {
        return exposterPrice;
    }


    /**
     * Sets the exposterPrice value for this ScenicBean.
     * 
     * @param exposterPrice
     */
    public void setExposterPrice(java.lang.String exposterPrice) {
        this.exposterPrice = exposterPrice;
    }


    /**
     * Gets the finNetwork value for this ScenicBean.
     * 
     * @return finNetwork
     */
    public java.lang.String getFinNetwork() {
        return finNetwork;
    }


    /**
     * Sets the finNetwork value for this ScenicBean.
     * 
     * @param finNetwork
     */
    public void setFinNetwork(java.lang.String finNetwork) {
        this.finNetwork = finNetwork;
    }


    /**
     * Gets the flag value for this ScenicBean.
     * 
     * @return flag
     */
    public java.lang.Boolean getFlag() {
        return flag;
    }


    /**
     * Sets the flag value for this ScenicBean.
     * 
     * @param flag
     */
    public void setFlag(java.lang.Boolean flag) {
        this.flag = flag;
    }


    /**
     * Gets the fullName value for this ScenicBean.
     * 
     * @return fullName
     */
    public java.lang.String getFullName() {
        return fullName;
    }


    /**
     * Sets the fullName value for this ScenicBean.
     * 
     * @param fullName
     */
    public void setFullName(java.lang.String fullName) {
        this.fullName = fullName;
    }


    /**
     * Gets the gasStationName value for this ScenicBean.
     * 
     * @return gasStationName
     */
    public java.lang.String getGasStationName() {
        return gasStationName;
    }


    /**
     * Sets the gasStationName value for this ScenicBean.
     * 
     * @param gasStationName
     */
    public void setGasStationName(java.lang.String gasStationName) {
        this.gasStationName = gasStationName;
    }


    /**
     * Gets the ifGasstation value for this ScenicBean.
     * 
     * @return ifGasstation
     */
    public java.lang.String getIfGasstation() {
        return ifGasstation;
    }


    /**
     * Sets the ifGasstation value for this ScenicBean.
     * 
     * @param ifGasstation
     */
    public void setIfGasstation(java.lang.String ifGasstation) {
        this.ifGasstation = ifGasstation;
    }


    /**
     * Gets the incitement value for this ScenicBean.
     * 
     * @return incitement
     */
    public java.lang.Integer getIncitement() {
        return incitement;
    }


    /**
     * Sets the incitement value for this ScenicBean.
     * 
     * @param incitement
     */
    public void setIncitement(java.lang.Integer incitement) {
        this.incitement = incitement;
    }


    /**
     * Gets the introduction value for this ScenicBean.
     * 
     * @return introduction
     */
    public java.lang.String getIntroduction() {
        return introduction;
    }


    /**
     * Sets the introduction value for this ScenicBean.
     * 
     * @param introduction
     */
    public void setIntroduction(java.lang.String introduction) {
        this.introduction = introduction;
    }


    /**
     * Gets the keywords value for this ScenicBean.
     * 
     * @return keywords
     */
    public java.lang.String getKeywords() {
        return keywords;
    }


    /**
     * Sets the keywords value for this ScenicBean.
     * 
     * @param keywords
     */
    public void setKeywords(java.lang.String keywords) {
        this.keywords = keywords;
    }


    /**
     * Gets the language value for this ScenicBean.
     * 
     * @return language
     */
    public java.lang.Integer getLanguage() {
        return language;
    }


    /**
     * Sets the language value for this ScenicBean.
     * 
     * @param language
     */
    public void setLanguage(java.lang.Integer language) {
        this.language = language;
    }


    /**
     * Gets the latitude value for this ScenicBean.
     * 
     * @return latitude
     */
    public java.lang.Double getLatitude() {
        return latitude;
    }


    /**
     * Sets the latitude value for this ScenicBean.
     * 
     * @param latitude
     */
    public void setLatitude(java.lang.Double latitude) {
        this.latitude = latitude;
    }


    /**
     * Gets the lineDes value for this ScenicBean.
     * 
     * @return lineDes
     */
    public java.lang.String getLineDes() {
        return lineDes;
    }


    /**
     * Sets the lineDes value for this ScenicBean.
     * 
     * @param lineDes
     */
    public void setLineDes(java.lang.String lineDes) {
        this.lineDes = lineDes;
    }


    /**
     * Gets the listContact value for this ScenicBean.
     * 
     * @return listContact
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.ContactBean[] getListContact() {
        return listContact;
    }


    /**
     * Sets the listContact value for this ScenicBean.
     * 
     * @param listContact
     */
    public void setListContact(com.lvmama.passport.processor.impl.client.shandong.model.ContactBean[] listContact) {
        this.listContact = listContact;
    }


    /**
     * Gets the listPicture value for this ScenicBean.
     * 
     * @return listPicture
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.PictureBean[] getListPicture() {
        return listPicture;
    }


    /**
     * Sets the listPicture value for this ScenicBean.
     * 
     * @param listPicture
     */
    public void setListPicture(com.lvmama.passport.processor.impl.client.shandong.model.PictureBean[] listPicture) {
        this.listPicture = listPicture;
    }


    /**
     * Gets the listScenicBranch value for this ScenicBean.
     * 
     * @return listScenicBranch
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.ScenicBranchBean[] getListScenicBranch() {
        return listScenicBranch;
    }


    /**
     * Sets the listScenicBranch value for this ScenicBean.
     * 
     * @param listScenicBranch
     */
    public void setListScenicBranch(com.lvmama.passport.processor.impl.client.shandong.model.ScenicBranchBean[] listScenicBranch) {
        this.listScenicBranch = listScenicBranch;
    }


    /**
     * Gets the listScenicTitle value for this ScenicBean.
     * 
     * @return listScenicTitle
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.InfoScenicTitleBean[] getListScenicTitle() {
        return listScenicTitle;
    }


    /**
     * Sets the listScenicTitle value for this ScenicBean.
     * 
     * @param listScenicTitle
     */
    public void setListScenicTitle(com.lvmama.passport.processor.impl.client.shandong.model.InfoScenicTitleBean[] listScenicTitle) {
        this.listScenicTitle = listScenicTitle;
    }


    /**
     * Gets the listScenicType value for this ScenicBean.
     * 
     * @return listScenicType
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.ScenicTypeBean[] getListScenicType() {
        return listScenicType;
    }


    /**
     * Sets the listScenicType value for this ScenicBean.
     * 
     * @param listScenicType
     */
    public void setListScenicType(com.lvmama.passport.processor.impl.client.shandong.model.ScenicTypeBean[] listScenicType) {
        this.listScenicType = listScenicType;
    }


    /**
     * Gets the lockTime value for this ScenicBean.
     * 
     * @return lockTime
     */
    public java.util.Calendar getLockTime() {
        return lockTime;
    }


    /**
     * Sets the lockTime value for this ScenicBean.
     * 
     * @param lockTime
     */
    public void setLockTime(java.util.Calendar lockTime) {
        this.lockTime = lockTime;
    }


    /**
     * Gets the lockerId value for this ScenicBean.
     * 
     * @return lockerId
     */
    public java.lang.String getLockerId() {
        return lockerId;
    }


    /**
     * Sets the lockerId value for this ScenicBean.
     * 
     * @param lockerId
     */
    public void setLockerId(java.lang.String lockerId) {
        this.lockerId = lockerId;
    }


    /**
     * Gets the longitude value for this ScenicBean.
     * 
     * @return longitude
     */
    public java.lang.Double getLongitude() {
        return longitude;
    }


    /**
     * Sets the longitude value for this ScenicBean.
     * 
     * @param longitude
     */
    public void setLongitude(java.lang.Double longitude) {
        this.longitude = longitude;
    }


    /**
     * Gets the modTime value for this ScenicBean.
     * 
     * @return modTime
     */
    public java.util.Calendar getModTime() {
        return modTime;
    }


    /**
     * Sets the modTime value for this ScenicBean.
     * 
     * @param modTime
     */
    public void setModTime(java.util.Calendar modTime) {
        this.modTime = modTime;
    }


    /**
     * Gets the modifierId value for this ScenicBean.
     * 
     * @return modifierId
     */
    public java.lang.String getModifierId() {
        return modifierId;
    }


    /**
     * Sets the modifierId value for this ScenicBean.
     * 
     * @param modifierId
     */
    public void setModifierId(java.lang.String modifierId) {
        this.modifierId = modifierId;
    }


    /**
     * Gets the myId value for this ScenicBean.
     * 
     * @return myId
     */
    public java.lang.String getMyId() {
        return myId;
    }


    /**
     * Sets the myId value for this ScenicBean.
     * 
     * @param myId
     */
    public void setMyId(java.lang.String myId) {
        this.myId = myId;
    }


    /**
     * Gets the myPrice value for this ScenicBean.
     * 
     * @return myPrice
     */
    public java.lang.String getMyPrice() {
        return myPrice;
    }


    /**
     * Sets the myPrice value for this ScenicBean.
     * 
     * @param myPrice
     */
    public void setMyPrice(java.lang.String myPrice) {
        this.myPrice = myPrice;
    }


    /**
     * Gets the nameCn value for this ScenicBean.
     * 
     * @return nameCn
     */
    public java.lang.String getNameCn() {
        return nameCn;
    }


    /**
     * Sets the nameCn value for this ScenicBean.
     * 
     * @param nameCn
     */
    public void setNameCn(java.lang.String nameCn) {
        this.nameCn = nameCn;
    }


    /**
     * Gets the nameEn value for this ScenicBean.
     * 
     * @return nameEn
     */
    public java.lang.String getNameEn() {
        return nameEn;
    }


    /**
     * Sets the nameEn value for this ScenicBean.
     * 
     * @param nameEn
     */
    public void setNameEn(java.lang.String nameEn) {
        this.nameEn = nameEn;
    }


    /**
     * Gets the noticket value for this ScenicBean.
     * 
     * @return noticket
     */
    public java.lang.Integer getNoticket() {
        return noticket;
    }


    /**
     * Sets the noticket value for this ScenicBean.
     * 
     * @param noticket
     */
    public void setNoticket(java.lang.Integer noticket) {
        this.noticket = noticket;
    }


    /**
     * Gets the openTime value for this ScenicBean.
     * 
     * @return openTime
     */
    public java.lang.String getOpenTime() {
        return openTime;
    }


    /**
     * Sets the openTime value for this ScenicBean.
     * 
     * @param openTime
     */
    public void setOpenTime(java.lang.String openTime) {
        this.openTime = openTime;
    }


    /**
     * Gets the parkInf value for this ScenicBean.
     * 
     * @return parkInf
     */
    public java.lang.String getParkInf() {
        return parkInf;
    }


    /**
     * Sets the parkInf value for this ScenicBean.
     * 
     * @param parkInf
     */
    public void setParkInf(java.lang.String parkInf) {
        this.parkInf = parkInf;
    }


    /**
     * Gets the parkSpaceNumber value for this ScenicBean.
     * 
     * @return parkSpaceNumber
     */
    public java.lang.Integer getParkSpaceNumber() {
        return parkSpaceNumber;
    }


    /**
     * Sets the parkSpaceNumber value for this ScenicBean.
     * 
     * @param parkSpaceNumber
     */
    public void setParkSpaceNumber(java.lang.Integer parkSpaceNumber) {
        this.parkSpaceNumber = parkSpaceNumber;
    }


    /**
     * Gets the permanentAct value for this ScenicBean.
     * 
     * @return permanentAct
     */
    public java.lang.Integer getPermanentAct() {
        return permanentAct;
    }


    /**
     * Sets the permanentAct value for this ScenicBean.
     * 
     * @param permanentAct
     */
    public void setPermanentAct(java.lang.Integer permanentAct) {
        this.permanentAct = permanentAct;
    }


    /**
     * Gets the picPath value for this ScenicBean.
     * 
     * @return picPath
     */
    public java.lang.String getPicPath() {
        return picPath;
    }


    /**
     * Sets the picPath value for this ScenicBean.
     * 
     * @param picPath
     */
    public void setPicPath(java.lang.String picPath) {
        this.picPath = picPath;
    }


    /**
     * Gets the postcode value for this ScenicBean.
     * 
     * @return postcode
     */
    public java.lang.String getPostcode() {
        return postcode;
    }


    /**
     * Sets the postcode value for this ScenicBean.
     * 
     * @param postcode
     */
    public void setPostcode(java.lang.String postcode) {
        this.postcode = postcode;
    }


    /**
     * Gets the recDegree value for this ScenicBean.
     * 
     * @return recDegree
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.RecDegree getRecDegree() {
        return recDegree;
    }


    /**
     * Sets the recDegree value for this ScenicBean.
     * 
     * @param recDegree
     */
    public void setRecDegree(com.lvmama.passport.processor.impl.client.shandong.model.RecDegree recDegree) {
        this.recDegree = recDegree;
    }


    /**
     * Gets the recDegreeEnum value for this ScenicBean.
     * 
     * @return recDegreeEnum
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.RecDegree getRecDegreeEnum() {
        return recDegreeEnum;
    }


    /**
     * Sets the recDegreeEnum value for this ScenicBean.
     * 
     * @param recDegreeEnum
     */
    public void setRecDegreeEnum(com.lvmama.passport.processor.impl.client.shandong.model.RecDegree recDegreeEnum) {
        this.recDegreeEnum = recDegreeEnum;
    }


    /**
     * Gets the scenicId value for this ScenicBean.
     * 
     * @return scenicId
     */
    public java.lang.String getScenicId() {
        return scenicId;
    }


    /**
     * Sets the scenicId value for this ScenicBean.
     * 
     * @param scenicId
     */
    public void setScenicId(java.lang.String scenicId) {
        this.scenicId = scenicId;
    }


    /**
     * Gets the scenicLevelEnum value for this ScenicBean.
     * 
     * @return scenicLevelEnum
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.ScenicLevel getScenicLevelEnum() {
        return scenicLevelEnum;
    }


    /**
     * Sets the scenicLevelEnum value for this ScenicBean.
     * 
     * @param scenicLevelEnum
     */
    public void setScenicLevelEnum(com.lvmama.passport.processor.impl.client.shandong.model.ScenicLevel scenicLevelEnum) {
        this.scenicLevelEnum = scenicLevelEnum;
    }


    /**
     * Gets the scenicLevelId value for this ScenicBean.
     * 
     * @return scenicLevelId
     */
    public com.lvmama.passport.processor.impl.client.shandong.model.ScenicLevel getScenicLevelId() {
        return scenicLevelId;
    }


    /**
     * Sets the scenicLevelId value for this ScenicBean.
     * 
     * @param scenicLevelId
     */
    public void setScenicLevelId(com.lvmama.passport.processor.impl.client.shandong.model.ScenicLevel scenicLevelId) {
        this.scenicLevelId = scenicLevelId;
    }


    /**
     * Gets the scenicLevelPhoto value for this ScenicBean.
     * 
     * @return scenicLevelPhoto
     */
    public java.lang.String getScenicLevelPhoto() {
        return scenicLevelPhoto;
    }


    /**
     * Sets the scenicLevelPhoto value for this ScenicBean.
     * 
     * @param scenicLevelPhoto
     */
    public void setScenicLevelPhoto(java.lang.String scenicLevelPhoto) {
        this.scenicLevelPhoto = scenicLevelPhoto;
    }


    /**
     * Gets the scenicLogo value for this ScenicBean.
     * 
     * @return scenicLogo
     */
    public java.lang.String getScenicLogo() {
        return scenicLogo;
    }


    /**
     * Sets the scenicLogo value for this ScenicBean.
     * 
     * @param scenicLogo
     */
    public void setScenicLogo(java.lang.String scenicLogo) {
        this.scenicLogo = scenicLogo;
    }


    /**
     * Gets the selfDrInf value for this ScenicBean.
     * 
     * @return selfDrInf
     */
    public java.lang.String getSelfDrInf() {
        return selfDrInf;
    }


    /**
     * Sets the selfDrInf value for this ScenicBean.
     * 
     * @param selfDrInf
     */
    public void setSelfDrInf(java.lang.String selfDrInf) {
        this.selfDrInf = selfDrInf;
    }


    /**
     * Gets the serNote value for this ScenicBean.
     * 
     * @return serNote
     */
    public java.lang.String getSerNote() {
        return serNote;
    }


    /**
     * Sets the serNote value for this ScenicBean.
     * 
     * @param serNote
     */
    public void setSerNote(java.lang.String serNote) {
        this.serNote = serNote;
    }


    /**
     * Gets the serviceCenter value for this ScenicBean.
     * 
     * @return serviceCenter
     */
    public java.lang.String getServiceCenter() {
        return serviceCenter;
    }


    /**
     * Sets the serviceCenter value for this ScenicBean.
     * 
     * @param serviceCenter
     */
    public void setServiceCenter(java.lang.String serviceCenter) {
        this.serviceCenter = serviceCenter;
    }


    /**
     * Gets the shortName value for this ScenicBean.
     * 
     * @return shortName
     */
    public java.lang.String getShortName() {
        return shortName;
    }


    /**
     * Sets the shortName value for this ScenicBean.
     * 
     * @param shortName
     */
    public void setShortName(java.lang.String shortName) {
        this.shortName = shortName;
    }


    /**
     * Gets the sightFea value for this ScenicBean.
     * 
     * @return sightFea
     */
    public java.lang.String getSightFea() {
        return sightFea;
    }


    /**
     * Sets the sightFea value for this ScenicBean.
     * 
     * @param sightFea
     */
    public void setSightFea(java.lang.String sightFea) {
        this.sightFea = sightFea;
    }


    /**
     * Gets the siteArea value for this ScenicBean.
     * 
     * @return siteArea
     */
    public java.lang.Integer getSiteArea() {
        return siteArea;
    }


    /**
     * Sets the siteArea value for this ScenicBean.
     * 
     * @param siteArea
     */
    public void setSiteArea(java.lang.Integer siteArea) {
        this.siteArea = siteArea;
    }


    /**
     * Gets the siteCaption value for this ScenicBean.
     * 
     * @return siteCaption
     */
    public java.lang.String getSiteCaption() {
        return siteCaption;
    }


    /**
     * Sets the siteCaption value for this ScenicBean.
     * 
     * @param siteCaption
     */
    public void setSiteCaption(java.lang.String siteCaption) {
        this.siteCaption = siteCaption;
    }


    /**
     * Gets the siteMagName value for this ScenicBean.
     * 
     * @return siteMagName
     */
    public java.lang.String getSiteMagName() {
        return siteMagName;
    }


    /**
     * Sets the siteMagName value for this ScenicBean.
     * 
     * @param siteMagName
     */
    public void setSiteMagName(java.lang.String siteMagName) {
        this.siteMagName = siteMagName;
    }


    /**
     * Gets the siteProject value for this ScenicBean.
     * 
     * @return siteProject
     */
    public java.lang.String getSiteProject() {
        return siteProject;
    }


    /**
     * Sets the siteProject value for this ScenicBean.
     * 
     * @param siteProject
     */
    public void setSiteProject(java.lang.String siteProject) {
        this.siteProject = siteProject;
    }


    /**
     * Gets the siteSlogan value for this ScenicBean.
     * 
     * @return siteSlogan
     */
    public java.lang.String getSiteSlogan() {
        return siteSlogan;
    }


    /**
     * Sets the siteSlogan value for this ScenicBean.
     * 
     * @param siteSlogan
     */
    public void setSiteSlogan(java.lang.String siteSlogan) {
        this.siteSlogan = siteSlogan;
    }


    /**
     * Gets the siteSpec value for this ScenicBean.
     * 
     * @return siteSpec
     */
    public java.lang.String getSiteSpec() {
        return siteSpec;
    }


    /**
     * Sets the siteSpec value for this ScenicBean.
     * 
     * @param siteSpec
     */
    public void setSiteSpec(java.lang.String siteSpec) {
        this.siteSpec = siteSpec;
    }


    /**
     * Gets the startTime value for this ScenicBean.
     * 
     * @return startTime
     */
    public java.lang.String getStartTime() {
        return startTime;
    }


    /**
     * Sets the startTime value for this ScenicBean.
     * 
     * @param startTime
     */
    public void setStartTime(java.lang.String startTime) {
        this.startTime = startTime;
    }


    /**
     * Gets the suppSerFac value for this ScenicBean.
     * 
     * @return suppSerFac
     */
    public java.lang.String getSuppSerFac() {
        return suppSerFac;
    }


    /**
     * Sets the suppSerFac value for this ScenicBean.
     * 
     * @param suppSerFac
     */
    public void setSuppSerFac(java.lang.String suppSerFac) {
        this.suppSerFac = suppSerFac;
    }


    /**
     * Gets the tags value for this ScenicBean.
     * 
     * @return tags
     */
    public java.lang.String getTags() {
        return tags;
    }


    /**
     * Sets the tags value for this ScenicBean.
     * 
     * @param tags
     */
    public void setTags(java.lang.String tags) {
        this.tags = tags;
    }


    /**
     * Gets the ticketDes value for this ScenicBean.
     * 
     * @return ticketDes
     */
    public java.lang.String getTicketDes() {
        return ticketDes;
    }


    /**
     * Sets the ticketDes value for this ScenicBean.
     * 
     * @param ticketDes
     */
    public void setTicketDes(java.lang.String ticketDes) {
        this.ticketDes = ticketDes;
    }


    /**
     * Gets the ticketNoCaption value for this ScenicBean.
     * 
     * @return ticketNoCaption
     */
    public java.lang.String getTicketNoCaption() {
        return ticketNoCaption;
    }


    /**
     * Sets the ticketNoCaption value for this ScenicBean.
     * 
     * @param ticketNoCaption
     */
    public void setTicketNoCaption(java.lang.String ticketNoCaption) {
        this.ticketNoCaption = ticketNoCaption;
    }


    /**
     * Gets the tourTime value for this ScenicBean.
     * 
     * @return tourTime
     */
    public java.lang.Integer getTourTime() {
        return tourTime;
    }


    /**
     * Sets the tourTime value for this ScenicBean.
     * 
     * @param tourTime
     */
    public void setTourTime(java.lang.Integer tourTime) {
        this.tourTime = tourTime;
    }


    /**
     * Gets the trafficInf value for this ScenicBean.
     * 
     * @return trafficInf
     */
    public java.lang.String getTrafficInf() {
        return trafficInf;
    }


    /**
     * Sets the trafficInf value for this ScenicBean.
     * 
     * @param trafficInf
     */
    public void setTrafficInf(java.lang.String trafficInf) {
        this.trafficInf = trafficInf;
    }


    /**
     * Gets the travelTip value for this ScenicBean.
     * 
     * @return travelTip
     */
    public java.lang.String getTravelTip() {
        return travelTip;
    }


    /**
     * Sets the travelTip value for this ScenicBean.
     * 
     * @param travelTip
     */
    public void setTravelTip(java.lang.String travelTip) {
        this.travelTip = travelTip;
    }


    /**
     * Gets the type value for this ScenicBean.
     * 
     * @return type
     */
    public java.lang.String getType() {
        return type;
    }


    /**
     * Sets the type value for this ScenicBean.
     * 
     * @param type
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }


    /**
     * Gets the unitCode value for this ScenicBean.
     * 
     * @return unitCode
     */
    public java.lang.String getUnitCode() {
        return unitCode;
    }


    /**
     * Sets the unitCode value for this ScenicBean.
     * 
     * @param unitCode
     */
    public void setUnitCode(java.lang.String unitCode) {
        this.unitCode = unitCode;
    }


    /**
     * Gets the unitCodeType value for this ScenicBean.
     * 
     * @return unitCodeType
     */
    public java.lang.Integer getUnitCodeType() {
        return unitCodeType;
    }


    /**
     * Sets the unitCodeType value for this ScenicBean.
     * 
     * @param unitCodeType
     */
    public void setUnitCodeType(java.lang.Integer unitCodeType) {
        this.unitCodeType = unitCodeType;
    }


    /**
     * Gets the userId value for this ScenicBean.
     * 
     * @return userId
     */
    public java.lang.String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this ScenicBean.
     * 
     * @param userId
     */
    public void setUserId(java.lang.String userId) {
        this.userId = userId;
    }


    /**
     * Gets the username value for this ScenicBean.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this ScenicBean.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }


    /**
     * Gets the version value for this ScenicBean.
     * 
     * @return version
     */
    public java.lang.Integer getVersion() {
        return version;
    }


    /**
     * Sets the version value for this ScenicBean.
     * 
     * @param version
     */
    public void setVersion(java.lang.Integer version) {
        this.version = version;
    }


    /**
     * Gets the viewdegree value for this ScenicBean.
     * 
     * @return viewdegree
     */
    public java.lang.Integer getViewdegree() {
        return viewdegree;
    }


    /**
     * Sets the viewdegree value for this ScenicBean.
     * 
     * @param viewdegree
     */
    public void setViewdegree(java.lang.Integer viewdegree) {
        this.viewdegree = viewdegree;
    }


    /**
     * Gets the websiteM value for this ScenicBean.
     * 
     * @return websiteM
     */
    public java.lang.String getWebsiteM() {
        return websiteM;
    }


    /**
     * Sets the websiteM value for this ScenicBean.
     * 
     * @param websiteM
     */
    public void setWebsiteM(java.lang.String websiteM) {
        this.websiteM = websiteM;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ScenicBean)) return false;
        ScenicBean other = (ScenicBean) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.adaptGroup==null && other.getAdaptGroup()==null) || 
             (this.adaptGroup!=null &&
              this.adaptGroup.equals(other.getAdaptGroup()))) &&
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            ((this.adviceEqu==null && other.getAdviceEqu()==null) || 
             (this.adviceEqu!=null &&
              this.adviceEqu.equals(other.getAdviceEqu()))) &&
            ((this.adviceLine==null && other.getAdviceLine()==null) || 
             (this.adviceLine!=null &&
              this.adviceLine.equals(other.getAdviceLine()))) &&
            ((this.attdesM==null && other.getAttdesM()==null) || 
             (this.attdesM!=null &&
              this.attdesM.equals(other.getAttdesM()))) &&
            ((this.auditState==null && other.getAuditState()==null) || 
             (this.auditState!=null &&
              this.auditState.equals(other.getAuditState()))) &&
            ((this.auditStateEnums==null && other.getAuditStateEnums()==null) || 
             (this.auditStateEnums!=null &&
              this.auditStateEnums.equals(other.getAuditStateEnums()))) &&
            ((this.avgPrice==null && other.getAvgPrice()==null) || 
             (this.avgPrice!=null &&
              this.avgPrice.equals(other.getAvgPrice()))) &&
            ((this.bestSeason==null && other.getBestSeason()==null) || 
             (this.bestSeason!=null &&
              this.bestSeason.equals(other.getBestSeason()))) &&
            ((this.busInf==null && other.getBusInf()==null) || 
             (this.busInf!=null &&
              this.busInf.equals(other.getBusInf()))) &&
            ((this.checkResul==null && other.getCheckResul()==null) || 
             (this.checkResul!=null &&
              this.checkResul.equals(other.getCheckResul()))) &&
            ((this.comWebsite==null && other.getComWebsite()==null) || 
             (this.comWebsite!=null &&
              this.comWebsite.equals(other.getComWebsite()))) &&
            ((this.createTime==null && other.getCreateTime()==null) || 
             (this.createTime!=null &&
              this.createTime.equals(other.getCreateTime()))) &&
            ((this.creatorId==null && other.getCreatorId()==null) || 
             (this.creatorId!=null &&
              this.creatorId.equals(other.getCreatorId()))) &&
            ((this.defaultPhoto==null && other.getDefaultPhoto()==null) || 
             (this.defaultPhoto!=null &&
              this.defaultPhoto.equals(other.getDefaultPhoto()))) &&
            ((this.destName==null && other.getDestName()==null) || 
             (this.destName!=null &&
              this.destName.equals(other.getDestName()))) &&
            ((this.destinationId==null && other.getDestinationId()==null) || 
             (this.destinationId!=null &&
              this.destinationId.equals(other.getDestinationId()))) &&
            ((this.disGasStation==null && other.getDisGasStation()==null) || 
             (this.disGasStation!=null &&
              this.disGasStation.equals(other.getDisGasStation()))) &&
            ((this.exposterPrice==null && other.getExposterPrice()==null) || 
             (this.exposterPrice!=null &&
              this.exposterPrice.equals(other.getExposterPrice()))) &&
            ((this.finNetwork==null && other.getFinNetwork()==null) || 
             (this.finNetwork!=null &&
              this.finNetwork.equals(other.getFinNetwork()))) &&
            ((this.flag==null && other.getFlag()==null) || 
             (this.flag!=null &&
              this.flag.equals(other.getFlag()))) &&
            ((this.fullName==null && other.getFullName()==null) || 
             (this.fullName!=null &&
              this.fullName.equals(other.getFullName()))) &&
            ((this.gasStationName==null && other.getGasStationName()==null) || 
             (this.gasStationName!=null &&
              this.gasStationName.equals(other.getGasStationName()))) &&
            ((this.ifGasstation==null && other.getIfGasstation()==null) || 
             (this.ifGasstation!=null &&
              this.ifGasstation.equals(other.getIfGasstation()))) &&
            ((this.incitement==null && other.getIncitement()==null) || 
             (this.incitement!=null &&
              this.incitement.equals(other.getIncitement()))) &&
            ((this.introduction==null && other.getIntroduction()==null) || 
             (this.introduction!=null &&
              this.introduction.equals(other.getIntroduction()))) &&
            ((this.keywords==null && other.getKeywords()==null) || 
             (this.keywords!=null &&
              this.keywords.equals(other.getKeywords()))) &&
            ((this.language==null && other.getLanguage()==null) || 
             (this.language!=null &&
              this.language.equals(other.getLanguage()))) &&
            ((this.latitude==null && other.getLatitude()==null) || 
             (this.latitude!=null &&
              this.latitude.equals(other.getLatitude()))) &&
            ((this.lineDes==null && other.getLineDes()==null) || 
             (this.lineDes!=null &&
              this.lineDes.equals(other.getLineDes()))) &&
            ((this.listContact==null && other.getListContact()==null) || 
             (this.listContact!=null &&
              java.util.Arrays.equals(this.listContact, other.getListContact()))) &&
            ((this.listPicture==null && other.getListPicture()==null) || 
             (this.listPicture!=null &&
              java.util.Arrays.equals(this.listPicture, other.getListPicture()))) &&
            ((this.listScenicBranch==null && other.getListScenicBranch()==null) || 
             (this.listScenicBranch!=null &&
              java.util.Arrays.equals(this.listScenicBranch, other.getListScenicBranch()))) &&
            ((this.listScenicTitle==null && other.getListScenicTitle()==null) || 
             (this.listScenicTitle!=null &&
              java.util.Arrays.equals(this.listScenicTitle, other.getListScenicTitle()))) &&
            ((this.listScenicType==null && other.getListScenicType()==null) || 
             (this.listScenicType!=null &&
              java.util.Arrays.equals(this.listScenicType, other.getListScenicType()))) &&
            ((this.lockTime==null && other.getLockTime()==null) || 
             (this.lockTime!=null &&
              this.lockTime.equals(other.getLockTime()))) &&
            ((this.lockerId==null && other.getLockerId()==null) || 
             (this.lockerId!=null &&
              this.lockerId.equals(other.getLockerId()))) &&
            ((this.longitude==null && other.getLongitude()==null) || 
             (this.longitude!=null &&
              this.longitude.equals(other.getLongitude()))) &&
            ((this.modTime==null && other.getModTime()==null) || 
             (this.modTime!=null &&
              this.modTime.equals(other.getModTime()))) &&
            ((this.modifierId==null && other.getModifierId()==null) || 
             (this.modifierId!=null &&
              this.modifierId.equals(other.getModifierId()))) &&
            ((this.myId==null && other.getMyId()==null) || 
             (this.myId!=null &&
              this.myId.equals(other.getMyId()))) &&
            ((this.myPrice==null && other.getMyPrice()==null) || 
             (this.myPrice!=null &&
              this.myPrice.equals(other.getMyPrice()))) &&
            ((this.nameCn==null && other.getNameCn()==null) || 
             (this.nameCn!=null &&
              this.nameCn.equals(other.getNameCn()))) &&
            ((this.nameEn==null && other.getNameEn()==null) || 
             (this.nameEn!=null &&
              this.nameEn.equals(other.getNameEn()))) &&
            ((this.noticket==null && other.getNoticket()==null) || 
             (this.noticket!=null &&
              this.noticket.equals(other.getNoticket()))) &&
            ((this.openTime==null && other.getOpenTime()==null) || 
             (this.openTime!=null &&
              this.openTime.equals(other.getOpenTime()))) &&
            ((this.parkInf==null && other.getParkInf()==null) || 
             (this.parkInf!=null &&
              this.parkInf.equals(other.getParkInf()))) &&
            ((this.parkSpaceNumber==null && other.getParkSpaceNumber()==null) || 
             (this.parkSpaceNumber!=null &&
              this.parkSpaceNumber.equals(other.getParkSpaceNumber()))) &&
            ((this.permanentAct==null && other.getPermanentAct()==null) || 
             (this.permanentAct!=null &&
              this.permanentAct.equals(other.getPermanentAct()))) &&
            ((this.picPath==null && other.getPicPath()==null) || 
             (this.picPath!=null &&
              this.picPath.equals(other.getPicPath()))) &&
            ((this.postcode==null && other.getPostcode()==null) || 
             (this.postcode!=null &&
              this.postcode.equals(other.getPostcode()))) &&
            ((this.recDegree==null && other.getRecDegree()==null) || 
             (this.recDegree!=null &&
              this.recDegree.equals(other.getRecDegree()))) &&
            ((this.recDegreeEnum==null && other.getRecDegreeEnum()==null) || 
             (this.recDegreeEnum!=null &&
              this.recDegreeEnum.equals(other.getRecDegreeEnum()))) &&
            ((this.scenicId==null && other.getScenicId()==null) || 
             (this.scenicId!=null &&
              this.scenicId.equals(other.getScenicId()))) &&
            ((this.scenicLevelEnum==null && other.getScenicLevelEnum()==null) || 
             (this.scenicLevelEnum!=null &&
              this.scenicLevelEnum.equals(other.getScenicLevelEnum()))) &&
            ((this.scenicLevelId==null && other.getScenicLevelId()==null) || 
             (this.scenicLevelId!=null &&
              this.scenicLevelId.equals(other.getScenicLevelId()))) &&
            ((this.scenicLevelPhoto==null && other.getScenicLevelPhoto()==null) || 
             (this.scenicLevelPhoto!=null &&
              this.scenicLevelPhoto.equals(other.getScenicLevelPhoto()))) &&
            ((this.scenicLogo==null && other.getScenicLogo()==null) || 
             (this.scenicLogo!=null &&
              this.scenicLogo.equals(other.getScenicLogo()))) &&
            ((this.selfDrInf==null && other.getSelfDrInf()==null) || 
             (this.selfDrInf!=null &&
              this.selfDrInf.equals(other.getSelfDrInf()))) &&
            ((this.serNote==null && other.getSerNote()==null) || 
             (this.serNote!=null &&
              this.serNote.equals(other.getSerNote()))) &&
            ((this.serviceCenter==null && other.getServiceCenter()==null) || 
             (this.serviceCenter!=null &&
              this.serviceCenter.equals(other.getServiceCenter()))) &&
            ((this.shortName==null && other.getShortName()==null) || 
             (this.shortName!=null &&
              this.shortName.equals(other.getShortName()))) &&
            ((this.sightFea==null && other.getSightFea()==null) || 
             (this.sightFea!=null &&
              this.sightFea.equals(other.getSightFea()))) &&
            ((this.siteArea==null && other.getSiteArea()==null) || 
             (this.siteArea!=null &&
              this.siteArea.equals(other.getSiteArea()))) &&
            ((this.siteCaption==null && other.getSiteCaption()==null) || 
             (this.siteCaption!=null &&
              this.siteCaption.equals(other.getSiteCaption()))) &&
            ((this.siteMagName==null && other.getSiteMagName()==null) || 
             (this.siteMagName!=null &&
              this.siteMagName.equals(other.getSiteMagName()))) &&
            ((this.siteProject==null && other.getSiteProject()==null) || 
             (this.siteProject!=null &&
              this.siteProject.equals(other.getSiteProject()))) &&
            ((this.siteSlogan==null && other.getSiteSlogan()==null) || 
             (this.siteSlogan!=null &&
              this.siteSlogan.equals(other.getSiteSlogan()))) &&
            ((this.siteSpec==null && other.getSiteSpec()==null) || 
             (this.siteSpec!=null &&
              this.siteSpec.equals(other.getSiteSpec()))) &&
            ((this.startTime==null && other.getStartTime()==null) || 
             (this.startTime!=null &&
              this.startTime.equals(other.getStartTime()))) &&
            ((this.suppSerFac==null && other.getSuppSerFac()==null) || 
             (this.suppSerFac!=null &&
              this.suppSerFac.equals(other.getSuppSerFac()))) &&
            ((this.tags==null && other.getTags()==null) || 
             (this.tags!=null &&
              this.tags.equals(other.getTags()))) &&
            ((this.ticketDes==null && other.getTicketDes()==null) || 
             (this.ticketDes!=null &&
              this.ticketDes.equals(other.getTicketDes()))) &&
            ((this.ticketNoCaption==null && other.getTicketNoCaption()==null) || 
             (this.ticketNoCaption!=null &&
              this.ticketNoCaption.equals(other.getTicketNoCaption()))) &&
            ((this.tourTime==null && other.getTourTime()==null) || 
             (this.tourTime!=null &&
              this.tourTime.equals(other.getTourTime()))) &&
            ((this.trafficInf==null && other.getTrafficInf()==null) || 
             (this.trafficInf!=null &&
              this.trafficInf.equals(other.getTrafficInf()))) &&
            ((this.travelTip==null && other.getTravelTip()==null) || 
             (this.travelTip!=null &&
              this.travelTip.equals(other.getTravelTip()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.unitCode==null && other.getUnitCode()==null) || 
             (this.unitCode!=null &&
              this.unitCode.equals(other.getUnitCode()))) &&
            ((this.unitCodeType==null && other.getUnitCodeType()==null) || 
             (this.unitCodeType!=null &&
              this.unitCodeType.equals(other.getUnitCodeType()))) &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId()))) &&
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername()))) &&
            ((this.version==null && other.getVersion()==null) || 
             (this.version!=null &&
              this.version.equals(other.getVersion()))) &&
            ((this.viewdegree==null && other.getViewdegree()==null) || 
             (this.viewdegree!=null &&
              this.viewdegree.equals(other.getViewdegree()))) &&
            ((this.websiteM==null && other.getWebsiteM()==null) || 
             (this.websiteM!=null &&
              this.websiteM.equals(other.getWebsiteM())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAdaptGroup() != null) {
            _hashCode += getAdaptGroup().hashCode();
        }
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getAdviceEqu() != null) {
            _hashCode += getAdviceEqu().hashCode();
        }
        if (getAdviceLine() != null) {
            _hashCode += getAdviceLine().hashCode();
        }
        if (getAttdesM() != null) {
            _hashCode += getAttdesM().hashCode();
        }
        if (getAuditState() != null) {
            _hashCode += getAuditState().hashCode();
        }
        if (getAuditStateEnums() != null) {
            _hashCode += getAuditStateEnums().hashCode();
        }
        if (getAvgPrice() != null) {
            _hashCode += getAvgPrice().hashCode();
        }
        if (getBestSeason() != null) {
            _hashCode += getBestSeason().hashCode();
        }
        if (getBusInf() != null) {
            _hashCode += getBusInf().hashCode();
        }
        if (getCheckResul() != null) {
            _hashCode += getCheckResul().hashCode();
        }
        if (getComWebsite() != null) {
            _hashCode += getComWebsite().hashCode();
        }
        if (getCreateTime() != null) {
            _hashCode += getCreateTime().hashCode();
        }
        if (getCreatorId() != null) {
            _hashCode += getCreatorId().hashCode();
        }
        if (getDefaultPhoto() != null) {
            _hashCode += getDefaultPhoto().hashCode();
        }
        if (getDestName() != null) {
            _hashCode += getDestName().hashCode();
        }
        if (getDestinationId() != null) {
            _hashCode += getDestinationId().hashCode();
        }
        if (getDisGasStation() != null) {
            _hashCode += getDisGasStation().hashCode();
        }
        if (getExposterPrice() != null) {
            _hashCode += getExposterPrice().hashCode();
        }
        if (getFinNetwork() != null) {
            _hashCode += getFinNetwork().hashCode();
        }
        if (getFlag() != null) {
            _hashCode += getFlag().hashCode();
        }
        if (getFullName() != null) {
            _hashCode += getFullName().hashCode();
        }
        if (getGasStationName() != null) {
            _hashCode += getGasStationName().hashCode();
        }
        if (getIfGasstation() != null) {
            _hashCode += getIfGasstation().hashCode();
        }
        if (getIncitement() != null) {
            _hashCode += getIncitement().hashCode();
        }
        if (getIntroduction() != null) {
            _hashCode += getIntroduction().hashCode();
        }
        if (getKeywords() != null) {
            _hashCode += getKeywords().hashCode();
        }
        if (getLanguage() != null) {
            _hashCode += getLanguage().hashCode();
        }
        if (getLatitude() != null) {
            _hashCode += getLatitude().hashCode();
        }
        if (getLineDes() != null) {
            _hashCode += getLineDes().hashCode();
        }
        if (getListContact() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListContact());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListContact(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getListPicture() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListPicture());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListPicture(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getListScenicBranch() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListScenicBranch());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListScenicBranch(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getListScenicTitle() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListScenicTitle());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListScenicTitle(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getListScenicType() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getListScenicType());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getListScenicType(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getLockTime() != null) {
            _hashCode += getLockTime().hashCode();
        }
        if (getLockerId() != null) {
            _hashCode += getLockerId().hashCode();
        }
        if (getLongitude() != null) {
            _hashCode += getLongitude().hashCode();
        }
        if (getModTime() != null) {
            _hashCode += getModTime().hashCode();
        }
        if (getModifierId() != null) {
            _hashCode += getModifierId().hashCode();
        }
        if (getMyId() != null) {
            _hashCode += getMyId().hashCode();
        }
        if (getMyPrice() != null) {
            _hashCode += getMyPrice().hashCode();
        }
        if (getNameCn() != null) {
            _hashCode += getNameCn().hashCode();
        }
        if (getNameEn() != null) {
            _hashCode += getNameEn().hashCode();
        }
        if (getNoticket() != null) {
            _hashCode += getNoticket().hashCode();
        }
        if (getOpenTime() != null) {
            _hashCode += getOpenTime().hashCode();
        }
        if (getParkInf() != null) {
            _hashCode += getParkInf().hashCode();
        }
        if (getParkSpaceNumber() != null) {
            _hashCode += getParkSpaceNumber().hashCode();
        }
        if (getPermanentAct() != null) {
            _hashCode += getPermanentAct().hashCode();
        }
        if (getPicPath() != null) {
            _hashCode += getPicPath().hashCode();
        }
        if (getPostcode() != null) {
            _hashCode += getPostcode().hashCode();
        }
        if (getRecDegree() != null) {
            _hashCode += getRecDegree().hashCode();
        }
        if (getRecDegreeEnum() != null) {
            _hashCode += getRecDegreeEnum().hashCode();
        }
        if (getScenicId() != null) {
            _hashCode += getScenicId().hashCode();
        }
        if (getScenicLevelEnum() != null) {
            _hashCode += getScenicLevelEnum().hashCode();
        }
        if (getScenicLevelId() != null) {
            _hashCode += getScenicLevelId().hashCode();
        }
        if (getScenicLevelPhoto() != null) {
            _hashCode += getScenicLevelPhoto().hashCode();
        }
        if (getScenicLogo() != null) {
            _hashCode += getScenicLogo().hashCode();
        }
        if (getSelfDrInf() != null) {
            _hashCode += getSelfDrInf().hashCode();
        }
        if (getSerNote() != null) {
            _hashCode += getSerNote().hashCode();
        }
        if (getServiceCenter() != null) {
            _hashCode += getServiceCenter().hashCode();
        }
        if (getShortName() != null) {
            _hashCode += getShortName().hashCode();
        }
        if (getSightFea() != null) {
            _hashCode += getSightFea().hashCode();
        }
        if (getSiteArea() != null) {
            _hashCode += getSiteArea().hashCode();
        }
        if (getSiteCaption() != null) {
            _hashCode += getSiteCaption().hashCode();
        }
        if (getSiteMagName() != null) {
            _hashCode += getSiteMagName().hashCode();
        }
        if (getSiteProject() != null) {
            _hashCode += getSiteProject().hashCode();
        }
        if (getSiteSlogan() != null) {
            _hashCode += getSiteSlogan().hashCode();
        }
        if (getSiteSpec() != null) {
            _hashCode += getSiteSpec().hashCode();
        }
        if (getStartTime() != null) {
            _hashCode += getStartTime().hashCode();
        }
        if (getSuppSerFac() != null) {
            _hashCode += getSuppSerFac().hashCode();
        }
        if (getTags() != null) {
            _hashCode += getTags().hashCode();
        }
        if (getTicketDes() != null) {
            _hashCode += getTicketDes().hashCode();
        }
        if (getTicketNoCaption() != null) {
            _hashCode += getTicketNoCaption().hashCode();
        }
        if (getTourTime() != null) {
            _hashCode += getTourTime().hashCode();
        }
        if (getTrafficInf() != null) {
            _hashCode += getTrafficInf().hashCode();
        }
        if (getTravelTip() != null) {
            _hashCode += getTravelTip().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getUnitCode() != null) {
            _hashCode += getUnitCode().hashCode();
        }
        if (getUnitCodeType() != null) {
            _hashCode += getUnitCodeType().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        if (getVersion() != null) {
            _hashCode += getVersion().hashCode();
        }
        if (getViewdegree() != null) {
            _hashCode += getViewdegree().hashCode();
        }
        if (getWebsiteM() != null) {
            _hashCode += getWebsiteM().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ScenicBean.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://entity.xz.com", "ScenicBean"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adaptGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "adaptGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adviceEqu");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "adviceEqu"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adviceLine");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "adviceLine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attdesM");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "attdesM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("auditState");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "auditState"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("auditStateEnums");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "auditStateEnums"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://InfoEnums.xz.com", "GeneralState"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("avgPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "avgPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bestSeason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "bestSeason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("busInf");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "busInf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("checkResul");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "checkResul"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comWebsite");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "comWebsite"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("createTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "createTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creatorId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "creatorId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultPhoto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "defaultPhoto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "destName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "destinationId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disGasStation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "disGasStation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exposterPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "exposterPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finNetwork");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "finNetwork"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "flag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fullName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "fullName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("gasStationName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "gasStationName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ifGasstation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "ifGasstation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("incitement");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "incitement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("introduction");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "introduction"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("keywords");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "keywords"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("language");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "language"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("latitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "latitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lineDes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "lineDes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listContact");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "listContact"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "ContactBean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "ContactBean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listPicture");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "listPicture"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "PictureBean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "PictureBean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listScenicBranch");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "listScenicBranch"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "ScenicBranchBean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "ScenicBranchBean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listScenicTitle");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "listScenicTitle"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://entity.xz.com", "InfoScenicTitleBean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://entity.xz.com", "InfoScenicTitleBean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listScenicType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "listScenicType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "ScenicTypeBean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://scenic.bean.xz.com", "ScenicTypeBean"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lockTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "lockTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lockerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "lockerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("longitude");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "longitude"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "modTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modifierId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "modifierId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("myId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "myId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("myPrice");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "myPrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameCn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "nameCn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameEn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "nameEn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("noticket");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "noticket"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("openTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "openTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parkInf");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "parkInf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parkSpaceNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "parkSpaceNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("permanentAct");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "permanentAct"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("picPath");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "picPath"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postcode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "postcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recDegree");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "recDegree"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://InfoEnums.xz.com", "RecDegree"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("recDegreeEnum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "recDegreeEnum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://InfoEnums.xz.com", "RecDegree"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "scenicId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicLevelEnum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "scenicLevelEnum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://InfoEnums.xz.com", "ScenicLevel"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicLevelId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "scenicLevelId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://InfoEnums.xz.com", "ScenicLevel"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicLevelPhoto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "scenicLevelPhoto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scenicLogo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "scenicLogo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("selfDrInf");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "selfDrInf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serNote");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "serNote"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceCenter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "serviceCenter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("shortName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "shortName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sightFea");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "sightFea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("siteArea");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "siteArea"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("siteCaption");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "siteCaption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("siteMagName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "siteMagName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("siteProject");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "siteProject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("siteSlogan");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "siteSlogan"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("siteSpec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "siteSpec"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "startTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suppSerFac");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "suppSerFac"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tags");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "tags"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ticketDes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "ticketDes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ticketNoCaption");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "ticketNoCaption"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tourTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "tourTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trafficInf");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "trafficInf"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("travelTip");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "travelTip"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "unitCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("unitCodeType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "unitCodeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("version");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "version"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("viewdegree");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "viewdegree"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("websiteM");
        elemField.setXmlName(new javax.xml.namespace.QName("http://entity.xz.com", "websiteM"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
