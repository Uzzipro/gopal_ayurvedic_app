package com.app.gopalayurvediccenter.Dataclass;

public class OrderDataClass {
    private String phNumber;
    private String address;
    private String payment_done;
    private String time_order_placed;
    private String amount_paid;
    private String payment_mode;
    private CartDataClass c;
    private String orderNumber;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public CartDataClass getC() {
        return c;
    }

    public void setC(CartDataClass c) {
        this.c = c;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getAmount_paid() {
        return amount_paid;
    }

    public void setAmount_paid(String amount_paid) {
        this.amount_paid = amount_paid;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayment_done() {
        return payment_done;
    }

    public void setPayment_done(String payment_done) {
        this.payment_done = payment_done;
    }

    public String getTime_order_placed() {
        return time_order_placed;
    }

    public void setTime_order_placed(String time_order_placed) {
        this.time_order_placed = time_order_placed;
    }
}
