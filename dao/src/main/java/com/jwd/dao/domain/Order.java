package com.jwd.dao.domain;

public class Order {

    private long id;
    private String name;
    private String type;
    private String description;
    private double price;
    private boolean isAvailable;
    private long customerId;
    private String status;

    public Order() {
    }

    public Order(long id, String name, String type, String description, double price, boolean isAvailable, long customerId, String status) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.customerId = customerId;
        this.status = status;
    }

    public Order(String name, String type, String description, double price, boolean isAvailable, long customerId, String status) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.customerId = customerId;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (Double.compare(order.price, price) != 0) return false;
        if (isAvailable != order.isAvailable) return false;
        if (customerId != order.customerId) return false;
        if (name != null ? !name.equals(order.name) : order.name != null) return false;
        if (type != null ? !type.equals(order.type) : order.type != null) return false;
        if (description != null ? !description.equals(order.description) : order.description != null) return false;
        return status != null ? status.equals(order.status) : order.status == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isAvailable ? 1 : 0);
        result = 31 * result + (int) (customerId ^ (customerId >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", customerId=" + customerId +
                ", status='" + status + '\'' +
                '}';
    }
}
