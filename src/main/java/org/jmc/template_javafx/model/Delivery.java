package org.jmc.template_javafx.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.apache.tools.ant.taskdefs.condition.Or;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Delivery extends Person implements Serializable {
    private Set<Order> orders = new HashSet<>();

    transient private ObjectProperty<Set<Order>> ordersProperty = new SimpleObjectProperty<>();


    public Delivery() {
        super();
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        if(ordersProperty == null) ordersProperty = new SimpleObjectProperty<>(orders);
        this.orders = orders;
    }

    public ObjectProperty<Set<Order>> ordersPropertyProperty() {
        if(ordersProperty == null) ordersProperty = new SimpleObjectProperty<>(orders);
        return ordersProperty;
    }

    public boolean addOrder(Order order){
        this.orders.add(order);
        if(ordersProperty == null) ordersProperty = new SimpleObjectProperty<>();
        ordersProperty.set(this.orders);
        return true;
    }

    public boolean removeOrder(Order order){
        this.orders.remove(order);
        if(ordersProperty == null) ordersProperty = new SimpleObjectProperty<>();
        ordersProperty.set(this.orders);
        return true;
    }
}
