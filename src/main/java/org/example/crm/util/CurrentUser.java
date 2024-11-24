package org.example.crm.util;
public class CurrentUser {
    private static String loggedInAdmin;
    public static String getLoggedInAdmin() {
        return loggedInAdmin;
    }
    public static void setLoggedInAdmin(String username) {
        loggedInAdmin = username;
    }
}