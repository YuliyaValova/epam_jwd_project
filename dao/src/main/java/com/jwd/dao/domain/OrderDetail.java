package com.jwd.dao.domain;

import java.util.Objects;

public class OrderDetail {
    private long orderId;
    private long orderDetailId;
    private int productAmount;
    private double itemPrice;
    private long productId;
    private String productName;
    private String productDetail;

    public OrderDetail() {
    }

    public OrderDetail(long orderId, long orderDetailId, int productAmount, double itemPrice, long productId, String productName, String productDetail) {
        this.orderId = orderId;
        this.orderDetailId = orderDetailId;
        this.productAmount = productAmount;
        this.itemPrice = itemPrice;
        this.productId = productId;
        this.productName = productName;
        this.productDetail = productDetail;
    }

    public OrderDetail(long orderDetailId, int productAmount, double itemPrice, long productId, String productName, String productDetail) {
        this.orderDetailId = orderDetailId;
        this.productAmount = productAmount;
        this.itemPrice = itemPrice;
        this.productId = productId;
        this.productName = productName;
        this.productDetail = productDetail;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return orderId == that.orderId && orderDetailId == that.orderDetailId && productAmount == that.productAmount && Double.compare(that.itemPrice, itemPrice) == 0 && productId == that.productId && Objects.equals(productName, that.productName) && Objects.equals(productDetail, that.productDetail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderDetailId, productAmount, itemPrice, productId, productName, productDetail);
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId=" + orderId +
                ", orderDetailId=" + orderDetailId +
                ", productAmount=" + productAmount +
                ", itemPrice=" + itemPrice +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDetail='" + productDetail + '\'' +
                '}';
    }
}
