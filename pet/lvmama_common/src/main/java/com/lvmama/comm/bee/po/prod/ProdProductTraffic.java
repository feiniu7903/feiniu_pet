package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;
import java.util.Date;

public class ProdProductTraffic implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1998829487291057521L;

	private Long prodTrafficId;

    private Date flightTime;

    private Date fromTime;

    private Date toTime;

    private String fromAirport;

    private Date toAirport;

    private String airlineName;

    private String flightNumber;

    private String flightModel;

    private String description;

    private Long airportTax;

    private Long bunkerSurcharge;

    private Long productId;

    public Long getProdTrafficId() {
        return prodTrafficId;
    }

    public void setProdTrafficId(Long prodTrafficId) {
        this.prodTrafficId = prodTrafficId;
    }

    public Date getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Date flightTime) {
        this.flightTime = flightTime;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport == null ? null : fromAirport.trim();
    }

    public Date getToAirport() {
        return toAirport;
    }

    public void setToAirport(Date toAirport) {
        this.toAirport = toAirport;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName == null ? null : airlineName.trim();
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber == null ? null : flightNumber.trim();
    }

    public String getFlightModel() {
        return flightModel;
    }

    public void setFlightModel(String flightModel) {
        this.flightModel = flightModel == null ? null : flightModel.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Long getAirportTax() {
        return airportTax;
    }

    public void setAirportTax(Long airportTax) {
        this.airportTax = airportTax;
    }

    public Long getBunkerSurcharge() {
        return bunkerSurcharge;
    }

    public void setBunkerSurcharge(Long bunkerSurcharge) {
        this.bunkerSurcharge = bunkerSurcharge;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}