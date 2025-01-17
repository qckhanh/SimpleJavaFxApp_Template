package org.jmc.template_javafx.model;

import javafx.beans.property.*;
import org.jmc.template_javafx.database.Database;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String name;
    private double price;

    transient private IntegerProperty idProperty = new SimpleIntegerProperty();
    transient private DoubleProperty priceProperty = new SimpleDoubleProperty();
    transient private StringProperty nameProperty = new SimpleStringProperty();


    public Item() {
        int id = Database.IDGenerator(Item.class);
        setId(id);
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
        if(idProperty == null) idProperty = new SimpleIntegerProperty();
        idProperty.set(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if(nameProperty == null) nameProperty = new SimpleStringProperty();
        nameProperty.set(name);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        if(priceProperty == null) priceProperty = new SimpleDoubleProperty();
        priceProperty.set(price);
    }

    public int getIdProperty() {
        return idProperty.get();
    }

    public IntegerProperty idPropertyProperty() {
        if(idProperty == null) idProperty = new SimpleIntegerProperty(id);
        return idProperty;
    }

    public DoubleProperty pricePropertyProperty() {
        if(priceProperty == null) priceProperty = new SimpleDoubleProperty(price);
        return priceProperty;
    }

    public StringProperty namePropertyProperty() {
        if(nameProperty == null) nameProperty = new SimpleStringProperty(name);
        return nameProperty;
    }

}
