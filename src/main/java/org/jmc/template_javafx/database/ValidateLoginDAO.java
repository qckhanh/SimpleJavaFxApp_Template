package org.jmc.template_javafx.database;

public interface ValidateLoginDAO<T> {

    T validateLogin(String username, String password);
}
