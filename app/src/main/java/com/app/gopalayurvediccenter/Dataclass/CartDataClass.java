package com.app.gopalayurvediccenter.Dataclass;

public class CartDataClass {
    private String productKey;
    private String productQuantity;
    private String productTotalPrice;
    private productsClass pd;
    private String nodeKey;

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public productsClass getPd() {
        return pd;
    }

    public void setPd(productsClass pd) {
        this.pd = pd;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(String productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }
}
