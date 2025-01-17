package org.jmc.template_javafx.Helper;


import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import static java.lang.Double.parseDouble;

public class InputValidator {

    public static Color RED = Color.RED;
    public static Color GREEN = Color.GREEN;

    public static void setLabelError(Label label, Color color, String message){
        label.setText(message);
        label.setTextFill(color);
    }

    public static boolean isValidCurrentUsername(String username) {
        if (username == null) return false;
        if (username.isEmpty()) return false;
        if (username.length() < 6) return false;
        return true;
    }
    public static boolean isValidPassword(String s) {
        if (s == null) return false;
        if (s.isEmpty()) return false;
        if (s.length() < 8) return false;
        if (s.length() > 50) return false;
        return true;
    }
    private static boolean isValidEmail(String s) {
        boolean isValid = s.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        return isValid;
    }
    private static boolean isValidPhoneNumber(String s) {
        boolean isValid =  s.matches("^0\\d{8,9}$");
        return isValid;
    }
    public static boolean NoCondition(String s) {
        if (s == null) return false;
        if (s.isEmpty()) return false;
        if (s.length() > 50) return false;
        return true;
    }
    public static boolean isValidDateFormat(LocalDate date) {
        if(date == null){
            return false;
        }
        System.out.println(DateUtils.formatDate(date));

        if(DateUtils.formatDate(date).equals(DateUtils.DEFAULT_DATE)){
            System.out.println("Invalid date format xxxxxxxxxx");
            return false;
        }
        return true;

    }
    public static boolean isValidInteger(String s){
        if(!NoCondition(s)) return false;
        try{
            int i = Integer.parseInt(s);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public static boolean isValidDouble(String s){
        if(!NoCondition(s)) return false;
        try{
            double d = Double.parseDouble(s);
        }
        catch (Exception e){
            return false;
        }
        return true;

    }
    public static boolean isValidOption(String s, int min, int max){
        if(!isValidInteger(s)) return false;
        int option = Integer.parseInt(s);
        boolean isValid = (option >= min && option <= max);
        return isValid;
    }
    public static boolean isValidContact(String s, Label label){
        if(!isValidEmail(s) && !isValidPhoneNumber(s)) {
            setLabelError(label, RED, "Invalid contact format");
            return false;
        }
        return true;

    }
    public static boolean isValidPrice(String s){
        if (!NoCondition(s)) return false;

        for(int i = 0; i < s.length(); i++){
            if(!Character.isDigit(s.charAt(i)) && s.charAt(i) != '.'){
                return false;
            }
        }

        if (s.isEmpty()) {
            return false;
        }

        double price = parseDouble(s);
        boolean isValid =  price > 0;
        return isValid;
    }

    public static boolean isBeforeToday(LocalDate date){
        LocalDate localDate = LocalDate.now();
        boolean isBefore =  date.isBefore(localDate);
        return isBefore;
    }

    public static boolean isAfterToday(LocalDate date){
        LocalDate localDate = LocalDate.now();
        boolean isAfter =  date.isAfter(localDate);
        return isAfter;
    }

    public static boolean isValidType(String s, List<String> types){
        for(String type : types){
            if(type.equalsIgnoreCase(s)) return true;
        }
        return false;
    }

}
