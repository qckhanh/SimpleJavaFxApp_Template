package org.jmc.template_javafx.database;

import java.util.*;
public abstract class DAOInterface<T>{

    public abstract boolean add(T t);
    public abstract boolean update(T t);
    public abstract boolean delete(T t);
    public abstract T get(int id);
    public abstract List<T> getAll();
    public abstract List<T> search(String keyword);


    public static String refineString(String s){
        return s.toLowerCase().replace(" ", "");
    }

}
