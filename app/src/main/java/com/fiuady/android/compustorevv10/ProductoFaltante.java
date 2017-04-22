package com.fiuady.android.compustorevv10;

/**
 * Created by Diego Hernandez on 20/04/2017.
 */

public class ProductoFaltante {
    private int AssemblyId;
    private int ProductId;
    private int qty;

    public ProductoFaltante(int assemblyId, int productId, int qty) {
        AssemblyId = assemblyId;
        ProductId = productId;
        this.qty = qty;
    }

    public int getAssemblyId() {
        return AssemblyId;
    }

    public void setAssemblyId(int assemblyId) {
        AssemblyId = assemblyId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
