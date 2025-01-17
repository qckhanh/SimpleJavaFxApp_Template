package org.jmc.template_javafx.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;



public class Session {
    private static Session session;
    private ObjectProperty<Person> currentUser;

    private Session(){
        currentUser = new SimpleObjectProperty<>(null);
    }

    public static Session getInstance(){
        if(session == null){
            session = new Session();
        }
        return session;
    }

    public Person getCurrentUser(){
        return currentUser.get();
    }

    public ObjectProperty<Person> currentUserProperty(){
        return currentUser;
    }

    public void setCurrentUser(Person currentUser){
        this.currentUser.set(currentUser);
    }


}
