package com.trivera.domain;

public class Order {

    private int id;
    private int orderNumber;
    private String description;
    private String owner;

    public Order(int id, int orderNumber, String description, String owner) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.description = description;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber=" + orderNumber +
                ", description='" + description + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
