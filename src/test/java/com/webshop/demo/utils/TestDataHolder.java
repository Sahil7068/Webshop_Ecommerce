package com.webshop.demo.utils;

/**
 * Utility class to hold test data between test executions
 */
public class TestDataHolder {
    private static String userEmail;
    private static String userPassword;
    private static String firstName;
    private static String lastName;

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String email) {
        userEmail = email;
    }

    public static String getUserPassword() {
        return userPassword;
    }

    public static void setUserPassword(String password) {
        userPassword = password;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String name) {
        firstName = name;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String name) {
        lastName = name;
    }

    public static void clearData() {
        userEmail = null;
        userPassword = null;
        firstName = null;
        lastName = null;
    }
}
