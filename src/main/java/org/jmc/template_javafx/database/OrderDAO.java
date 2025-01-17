package org.jmc.template_javafx.database;

import org.apache.tools.ant.taskdefs.condition.Or;
import org.jmc.template_javafx.Helper.DateUtils;
import org.jmc.template_javafx.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DAOInterface<Order>{
    @Override
    public boolean add(Order order) {
        return Database.getInstance().getOrders().add(order);
    }

    @Override
    public boolean update(Order order) {
        int index = -1;
        List<Order> orders = Database.getInstance().getOrders();
        for(int i = 0; i < orders.size(); i++){
            if(orders.get(i).getId() == order.getId()){
                index = i;
                break;
            }
        }
        if(index == -1) return false;
        orders.set(index, order);
        return true;
    }

    @Override
    public boolean delete(Order order) {
        return Database.getInstance().getOrders().remove(order);
    }

    @Override
    public Order get(int id) {
        List<Order> orders = Database.getInstance().getOrders();
        for(Order order: orders){
            if(order.getId() == id) return order;
        }
        return null;
    }

    @Override
    public List<Order> getAll() {
        return Database.getInstance().getOrders();
    }

    @Override
    public List<Order> search(String keyword) {
        keyword = refineString(keyword);
        List<Order> orders = Database.getInstance().getOrders();
        List<Order> list = new ArrayList<>();
        for(Order order: orders){
            String name2 = refineString(order.getId() + "");
            String date = DateUtils.formatDate(order.getCreatedDate());
            String date2 = refineString(date);
            String item2 = refineString(order.getItem().getName());
            if(name2.contains(keyword) || date2.contains(keyword) || item2.contains(keyword)){
                list.add(order);
            }
        }
        return list;
    }
}
