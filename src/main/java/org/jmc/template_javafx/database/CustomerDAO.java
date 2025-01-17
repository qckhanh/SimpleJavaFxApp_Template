package org.jmc.template_javafx.database;

import javafx.css.CssMetaData;
import org.jmc.template_javafx.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends DAOInterface<Customer> {
    @Override
    public boolean add(Customer customer) {
        Database.getInstance().getCustomers().add(customer);
        return true;
    }

    @Override
    public boolean update(Customer customer) {
        int index = -1;
        List<Customer> customers = Database.getInstance().getCustomers();
        for(int i = 0; i < customers.size(); i++){
            if(customers.get(i).getId() == customer.getId()){
                index = i;
                break;
            }
        }

        if(index == -1) return false;
        customers.set(index, customer);
        return true;
    }

    @Override
    public boolean delete(Customer customer) {
        return Database.getInstance().getCustomers().remove(customer);
    }

    @Override
    public Customer get(int id) {
        List<Customer> customers = Database.getInstance().getCustomers();
        for(Customer customer: customers){
            if(customer.getId() == id) return customer;
        }
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return Database.getInstance().getCustomers();
    }

    @Override
    public List<Customer> search(String keyword) {
        List<Customer> customers = Database.getInstance().getCustomers();
        List<Customer> results = new ArrayList<>();
        keyword = refineString(keyword);
        for(Customer c: customers){
            String phoneNumber2 = refineString(c.getPhone());
            String name2 = refineString(c.getName());

            if(phoneNumber2.contains(keyword) || name2.contains(keyword)){
                results.add(c);
            }
        }

        return results;
    }
}
