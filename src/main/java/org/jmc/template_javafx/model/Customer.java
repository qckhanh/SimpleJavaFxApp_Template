package org.jmc.template_javafx.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.apache.tools.ant.taskdefs.condition.Or;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Customer extends Person implements Serializable {
    private Set<Order> orders = new HashSet<>();


    transient private ObjectProperty<Set<Order>> setObjectProperty = new SimpleObjectProperty<>();


    public Customer() {
        super();
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        if(setObjectProperty == null) setObjectProperty = new SimpleObjectProperty<>(orders);
        this.orders = orders;
    }

    public ObjectProperty<Set<Order>> ordersPropertyProperty() {
        if(setObjectProperty == null) setObjectProperty = new SimpleObjectProperty<>(orders);
        return setObjectProperty;
    }

    public boolean addOrder(Order order){
        this.orders.add(order);
        if(setObjectProperty == null) setObjectProperty = new SimpleObjectProperty<>();
        setObjectProperty.set(this.orders);
        return true;
    }

    public boolean removeOrder(Order order){
        this.orders.remove(order);
        if(setObjectProperty == null) setObjectProperty = new SimpleObjectProperty<>();
        setObjectProperty.set(this.orders);
        return true;
    }


}
