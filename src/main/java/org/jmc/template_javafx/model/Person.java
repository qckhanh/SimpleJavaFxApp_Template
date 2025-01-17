package org.jmc.template_javafx.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.jmc.template_javafx.database.Database;

import java.io.Serializable;

public abstract class Person implements Serializable {
    protected int id;
    protected String name;
    protected String address;
    protected String phone;

    transient protected IntegerProperty idProperty = new SimpleIntegerProperty();
    transient protected StringProperty nameProperty = new SimpleStringProperty();
    transient protected StringProperty addressProperty = new SimpleStringProperty();
    transient protected StringProperty phoneProperty = new SimpleStringProperty();

    public Person() {
        int id =  Database.IDGenerator(Person.class);
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        if(addressProperty == null) addressProperty = new SimpleStringProperty();
        addressProperty.set(address);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        if(phoneProperty == null) phoneProperty = new SimpleStringProperty();
        phoneProperty.set(phone);
    }

    public StringProperty phonePropertyProperty() {
        if(phoneProperty == null){
            phoneProperty = new SimpleStringProperty();
            phoneProperty.set(phone);
        }
        return phoneProperty;
    }


    public StringProperty addressPropertyProperty() {
        if(addressProperty == null){
            addressProperty = new SimpleStringProperty();
            addressProperty.set(address);
        }
        return addressProperty;
    }


    public StringProperty namePropertyProperty() {
        if(nameProperty == null){
            nameProperty = new SimpleStringProperty();
            nameProperty.set(name);
        }
        return nameProperty;
    }

    public IntegerProperty idPropertyProperty() {
        if(idProperty == null){
            idProperty = new SimpleIntegerProperty();
            idProperty.set(id);
        }
        return idProperty;
    }
}
