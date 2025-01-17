package org.jmc.template_javafx.model;

import javafx.beans.property.*;
import org.jmc.template_javafx.database.Database;

import java.io.Serializable;
import java.time.LocalDate;

public class Order implements Serializable {

    int id;
    private Customer customer;
    private Delivery delivery;
    private double totalPrice;
    private LocalDate createdDate;
    private Item item;

    public Order() {
        int id = Database.IDGenerator(Order.class);
        setId(id);
    }

    transient private IntegerProperty idProperty = new SimpleIntegerProperty();
    transient private ObjectProperty<Delivery> deliveryProperty = new SimpleObjectProperty<>();
    transient private ObjectProperty<Item> itemProperty = new SimpleObjectProperty<>();
    transient private ObjectProperty<LocalDate> createdDateProperty = new SimpleObjectProperty<>();
    transient private ObjectProperty<Customer> customerProperty = new SimpleObjectProperty<>();
    transient private DoubleProperty totalPriceProperty = new SimpleDoubleProperty();

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
        if(idProperty == null) idProperty = new SimpleIntegerProperty(this.id);
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        if(deliveryProperty == null) deliveryProperty = new SimpleObjectProperty<>();
        deliveryProperty.set(this.delivery);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        if (totalPriceProperty == null) totalPriceProperty = new SimpleDoubleProperty();
        totalPriceProperty.set(this.totalPrice);

    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        if(createdDateProperty == null) createdDateProperty = new SimpleObjectProperty<>();
        createdDateProperty.set(this.createdDate);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        if(itemProperty == null) itemProperty = new SimpleObjectProperty<>();
        itemProperty.set(this.item);
    }

    public int getIdProperty() {
        return idProperty.get();
    }

    public IntegerProperty idPropertyProperty() {
        if(idProperty == null) idProperty = new SimpleIntegerProperty(this.id);
        return idProperty;
    }

    public ObjectProperty<Delivery> deliveryPropertyProperty() {
        if(deliveryProperty == null) deliveryProperty = new SimpleObjectProperty<>(this.delivery);
        return deliveryProperty;
    }


    public ObjectProperty<Item> itemPropertyProperty() {
        if(itemProperty == null) itemProperty = new SimpleObjectProperty<>(this.item);
        return itemProperty;
    }

    public ObjectProperty<LocalDate> createdDatePropertyProperty() {
        if(createdDateProperty == null) createdDateProperty = new SimpleObjectProperty<>(this.createdDate);
        return createdDateProperty;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        if(customerProperty == null) customerProperty = new SimpleObjectProperty<>();
        customerProperty.set(this.customer);
    }
}
