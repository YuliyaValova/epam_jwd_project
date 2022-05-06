package com.jwd.dao.domain;

import java.util.ArrayList;
import java.util.Objects;

public class Order {

    private long orderId;
    private String date;
    private String status;
    private double totalPrice;
    private long customerId;
    private ArrayList<OrderDetail> details;
    private String comment;

    public Order() {
    }

    public Order(long orderId, String date, String status, double totalPrice, long customerId, ArrayList<OrderDetail> details, String comment) {
        this.orderId = orderId;
        this.date = date;
        this.status = status;
        this.totalPrice = totalPrice;
        this.customerId = customerId;
        this.details = details;
        this.comment = comment;
    }

    public Order(String date, String status, double totalPrice, long customerId, ArrayList<OrderDetail> details, String comment) {
        this.date = date;
        this.status = status;
        this.totalPrice = totalPrice;
        this.customerId = customerId;
        this.details = details;
        this.comment = comment;
    }

    public ArrayList<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<OrderDetail> details) {
        this.details = details;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && Double.compare(order.totalPrice, totalPrice) == 0 && customerId == order.customerId && Objects.equals(date, order.date) && Objects.equals(status, order.status) && Objects.equals(details, order.details) && Objects.equals(comment, order.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, date, status, totalPrice, customerId, details, comment);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                ", totalPrice=" + totalPrice +
                ", customerId=" + customerId +
                ", details=" + details +
                ", comment='" + comment + '\'' +
                '}';
    }
}
