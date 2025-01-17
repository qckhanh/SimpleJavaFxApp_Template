package org.jmc.template_javafx.database;

import javafx.scene.chart.PieChart;
import org.jmc.template_javafx.model.Customer;
import org.jmc.template_javafx.model.Delivery;

import java.util.ArrayList;
import java.util.List;

public class DeliveryManDAO extends DAOInterface<Delivery> {


    @Override
    public boolean add(Delivery delivery) {
        return Database.getInstance().getDeliveries().add(delivery);
    }

    @Override
    public boolean update(Delivery delivery) {
        int index = Database.getInstance().getDeliveries().indexOf(delivery);
        if(index == -1) return false;
        Database.getInstance().getDeliveries().set(index, delivery);
        return true;
    }

    @Override
    public boolean delete(Delivery delivery) {
        return Database.getInstance().getDeliveries().remove(delivery);
    }

    @Override
    public Delivery get(int id) {
        List<Delivery> list = Database.getInstance().getDeliveries();
        for(Delivery current: list){
            if(current.getId() == id) return current;
        }
        return null;
    }

    @Override
    public List<Delivery> getAll() {
        return Database.getInstance().getDeliveries();
    }

    @Override
    public List<Delivery> search(String keyword) {
        List<Delivery> list = Database.getInstance().getDeliveries();
        List<Delivery> results = new ArrayList<>();
        keyword = refineString(keyword);
        for(Delivery c: list){
            String phoneNumber2 = refineString(c.getPhone());
            String name2 = refineString(c.getName());

            if(phoneNumber2.contains(keyword) || name2.contains(keyword)){
                results.add(c);
            }
        }

        return results;
    }
}
