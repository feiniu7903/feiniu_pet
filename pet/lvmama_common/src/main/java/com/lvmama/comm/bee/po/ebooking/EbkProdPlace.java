package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;

/**
 * @since 2013-09-24
 */
public class EbkProdPlace implements Serializable {

    private static final long serialVersionUID = 138001285198311211L;

    /**
     * column EBK_PROD_PLACE.PRODUCT_PLACE_ID
     */
    private Long productPlaceId;

    /**
     * column EBK_PROD_PLACE.PRODUCT_ID
     */
    private Long productId;

    /**
     * column EBK_PROD_PLACE.PLACE_ID
     */
    private Long placeId;

    private String placeName;
    public EbkProdPlace() {
        super();
    }

    public EbkProdPlace(Long productPlaceId, Long productId, Long placeId) {
        this.productPlaceId = productPlaceId;
        this.productId = productId;
        this.placeId = placeId;
    }

    /**
     * getter for Column EBK_PROD_PLACE.PRODUCT_PLACE_ID
     */
    public Long getProductPlaceId() {
        return productPlaceId;
    }

    /**
     * setter for Column EBK_PROD_PLACE.PRODUCT_PLACE_ID
     * @param productPlaceId
     */
    public void setProductPlaceId(Long productPlaceId) {
        this.productPlaceId = productPlaceId;
    }

    /**
     * getter for Column EBK_PROD_PLACE.PRODUCT_ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * setter for Column EBK_PROD_PLACE.PRODUCT_ID
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * getter for Column EBK_PROD_PLACE.PLACE_ID
     */
    public Long getPlaceId() {
        return placeId;
    }

    /**
     * setter for Column EBK_PROD_PLACE.PLACE_ID
     * @param placeId
     */
    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

}