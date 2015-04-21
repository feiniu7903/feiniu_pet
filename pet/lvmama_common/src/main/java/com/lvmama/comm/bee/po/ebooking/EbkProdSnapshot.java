package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;

/**
 * @since 2013-09-24
 */
public class EbkProdSnapshot implements Serializable {

    private static final long serialVersionUID = 138001285521749378L;

    /**
     * column EBK_PROD_SNAPSHOT.PROD_SNAPSHOT_ID
     */
    private Long prodSnapshotId;

    /**
     * column EBK_PROD_SNAPSHOT.PRODUCT_ID
     */
    private Long productId;

    /**
     * column EBK_PROD_SNAPSHOT.CONTENT
     */
    private String content;

    public EbkProdSnapshot() {
        super();
    }

    public EbkProdSnapshot(Long prodSnapshotId, Long productId, String content) {
        this.prodSnapshotId = prodSnapshotId;
        this.productId = productId;
        this.content = content;
    }

    /**
     * getter for Column EBK_PROD_SNAPSHOT.PROD_SNAPSHOT_ID
     */
    public Long getProdSnapshotId() {
        return prodSnapshotId;
    }

    /**
     * setter for Column EBK_PROD_SNAPSHOT.PROD_SNAPSHOT_ID
     * @param prodSnapshotId
     */
    public void setProdSnapshotId(Long prodSnapshotId) {
        this.prodSnapshotId = prodSnapshotId;
    }

    /**
     * getter for Column EBK_PROD_SNAPSHOT.PRODUCT_ID
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * setter for Column EBK_PROD_SNAPSHOT.PRODUCT_ID
     * @param productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * getter for Column EBK_PROD_SNAPSHOT.CONTENT
     */
    public String getContent() {
        return content;
    }

    /**
     * setter for Column EBK_PROD_SNAPSHOT.CONTENT
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

}