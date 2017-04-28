package com.fiuady.android.compustorevv10.db;

/**
 * Created by Aarón Madera on 28/04/2017.
 */

public class SimProductsNeeded {
    private int orderId;
    private int productId;
    private int qtyNeeded;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQtyNeeded() {
        return qtyNeeded;
    }

    public void setQtyNeeded(int qtyNeeded) {
        this.qtyNeeded = qtyNeeded;
    }


    public SimProductsNeeded(int orderId, int productId, int qtyNeeded) {
        this.orderId = orderId;
        this.productId = productId;
        this.qtyNeeded = qtyNeeded;
    }
}
