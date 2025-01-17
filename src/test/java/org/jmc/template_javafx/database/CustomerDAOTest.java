package org.jmc.template_javafx.database;

import org.jmc.template_javafx.model.Customer;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import java.beans.Transient;

import static org.junit.jupiter.api.Assertions.*;
class CustomerDAOTest {

    @Test
    void add() {
        Database.getInstance();

        Customer customer = new Customer();
        assertTrue(new CustomerDAO().add(customer));
    }

    @Test
    void updateInvalidCustomer() {
        Database.getInstance();

        Customer customer = new Customer();
        int id = customer.getId();

        System.out.println("Customer ID: " + id);
        System.out.println("ID is not in the database");

        assertFalse(new CustomerDAO().update(customer));
    }

    @Test
    void updateValidCustomer() {
        Database.getInstance();

        Customer customer = new Customer();
        new CustomerDAO().add(customer);

        int id = customer.getId();
        System.out.println("Customer ID: " + id);
        System.out.println("ID is in the database");

        assertTrue(new CustomerDAO().update(customer));
    }

    @Test
    void delete() {
        Database.getInstance();

        Customer customer = new Customer();
        new CustomerDAO().add(customer);

        assertTrue(new CustomerDAO().delete(customer));
    }

    @Test
    void deleteInvalidCustomer() {
        Database.getInstance();

        Customer customer = new Customer();
        int id = customer.getId();

        System.out.println("Customer ID: " + id);
        System.out.println("ID is not in the database");

        assertFalse(new CustomerDAO().delete(customer));
    }

    @Test
    void get() {
    }

    @Test
    void getAll() {
    }

    @Test
    void search() {
    }
}