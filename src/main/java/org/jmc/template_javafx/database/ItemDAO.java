package org.jmc.template_javafx.database;

import org.jmc.template_javafx.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO extends DAOInterface<Item> {

    @Override
    public boolean add(Item item) {
        return Database.getInstance().getItems().add(item);
    }

    @Override
    public boolean update(Item item) {
        int index = Database.getInstance().getItems().indexOf(item);
        if(index == -1) return false;
        Database.getInstance().getItems().set(index, item);
        return true;
    }

    @Override
    public boolean delete(Item item) {
        return Database.getInstance().getItems().remove(item);
    }

    @Override
    public Item get(int id) {
        List<Item> list = Database.getInstance().getItems();
        for(Item current: list){
            if(current.getId() == id) return current;
        }
        return null;
    }

    @Override
    public List<Item> getAll() {
        return Database.getInstance().getItems();
    }

    @Override
    public List<Item> search(String keyword) {
        keyword = refineString(keyword);

        List<Item> list = Database.getInstance().getItems();
        List<Item> results = new ArrayList<>();
        for(Item current: list){
            String name2 = refineString(current.getName());
            String price = refineString(String.valueOf(current.getPrice()));

            if(name2.contains(keyword) || price.contains(keyword)){
                results.add(current);
            }
        }

        return results;
    }
}
