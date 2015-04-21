package com.lvmama.comm.bee.po.op;

import java.io.Serializable;

/**
 * 成本项内容
 * @author zhaojindong
 *
 */
public class OpCostsItem implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2221270926586530839L;

	private Long id;

    private String name;

    private String itemOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(String itemOrder) {
        this.itemOrder = itemOrder;
    }
}