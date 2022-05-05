package com.jwd.dao.domain;

import static java.util.Objects.nonNull;

public class Product {

    private long id;
    private String name;
    private String type;
    private String description;
    private double price;
    private boolean isAvailable;
    private String image;

    public Product() {
    }

    public Product(long id, String name, String type, String description, double price, boolean isAvailable, String image) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.image = image;
    }

    public Product(String name, String type, String description, double price, boolean isAvailable, String image) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.image = image;
    }

    public Product(Product product) {
        if (nonNull(product)) {
            this.id = product.getId();
            this.name = product.getName();
            this.type = product.getType();
            this.description = product.getDescription();
            this.price = product.getPrice();
            this.isAvailable = product.getIsAvailable();
            this.image = product.getImage();
        } else {
            this.id = -1L;
        }
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

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean available) {
        isAvailable = available;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (Double.compare(product.price, price) != 0) return false;
        if (isAvailable != product.isAvailable) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (type != null ? !type.equals(product.type) : product.type != null) return false;
        if (description != null ? !description.equals(product.description) : product.description != null) return false;
        return image != null ? image.equals(product.image) : product.image == null;
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
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", image='" + image + '\'' +
                '}';
    }
}
