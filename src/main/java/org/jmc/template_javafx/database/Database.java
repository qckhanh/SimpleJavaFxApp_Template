package org.jmc.template_javafx.database;

import org.apache.tools.ant.taskdefs.condition.Or;
import org.jmc.template_javafx.model.*;


import java.io.*;
import java.util.*;

public class Database{
    private final String PATH = "src/main/resources/org/jmc/template_javafx/Data/";

    // All entities

    private List<Customer> customers;
    private List<Delivery> deliveries;
    private List<Item> items;
    private List<Order> orders;
    //add new list of model when you create a new model

    //all id
    private static Map<Integer, Boolean> PERSON_ID;
    private static Map<Integer, Boolean> ORDER_ID;
    private static Map<Integer, Boolean> ITEM_ID;
    //add new map of id when you create a new that not extends Person

    private static  Database db;
    private Database() {
        customers = new ArrayList<>();
        deliveries = new ArrayList<>();
        items = new ArrayList<>();
        orders = new ArrayList<>();
        //add new list of model when you create a new model


        PERSON_ID = new HashMap<>();
        ORDER_ID = new HashMap<>();
        ITEM_ID = new HashMap<>();

        loadData();
    }
    public static Database getInstance(){
        return (db == null) ? db = new Database() : db;
    }
    public void loadData(){
        try{
            File file = new File(PATH + "USER_DATA.ser");
            File file2 = new File(PATH + "USER_ID.ser");

            if(file.length() == 0 || file2.length() == 0){
//                UserInterfaceManager.errorMessage("Data loaded failed");
                return;
            }
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));


            customers = (List<Customer>) ois.readObject();
            deliveries = (List<Delivery>) ois.readObject();
            items = (List<Item>) ois.readObject();
            orders = (List<Order>) ois.readObject();
            //add new list of model when you create a new model

            ois.close();

            ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream(file2));
            PERSON_ID = (Map<Integer, Boolean>) ois2.readObject();
            ORDER_ID = (Map<Integer, Boolean>) ois2.readObject();
            ITEM_ID = (Map<Integer, Boolean>) ois2.readObject();
            //add new map of id when you create a new that not extends Person

            ois2.close();
        }
        catch (Exception e){
//            UserInterfaceManager.errorMessage("Data loaded incorrectly");
        }
    }
    public void saveData() {
        try{
            File file = new File(PATH + "USER_DATA.ser");
            ObjectOutputStream data = new ObjectOutputStream(new FileOutputStream(file));

            data.writeObject(customers);
            data.writeObject(deliveries);
            data.writeObject(items);
            data.writeObject(orders);
            //add new list of model when you create a new model


            data.flush();
            data.close();

            File file2 = new File(PATH + "USER_ID.ser");
            ObjectOutputStream ID = new ObjectOutputStream(new FileOutputStream(file2));

            ID.writeObject(PERSON_ID);
            ID.writeObject(ORDER_ID);
            ID.writeObject(ITEM_ID);
            //add new map of id when you create a new that not extends Person


            ID.flush();
            ID.close();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
    public static int IDGenerator(Class<?> c){
//        int ID = Math.abs(new Random().nextInt() % 1000);
        int ID;
        while(true){
            ID = Math.abs(new Random().nextInt() % 1000);

            if(c.equals(Person.class)){
                if(!PERSON_ID.containsValue(ID)){
                    PERSON_ID.put(ID, true);
                    break;
                }
            }
            else if (c.equals(Order.class)){
                if(!ORDER_ID.containsValue(ID)){
                    ORDER_ID.put(ID, true);
                    break;
                }
            }
            else if (c.equals(Item.class)){
                if(!ITEM_ID.containsValue(ID)){
                    ITEM_ID.put(ID, true);
                    break;
                }
            }
            else return -1;
        }
        return ID;
    }



    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Item> getItems() {
        return items;
    }
}
