package models;

import java.io.Serializable;

/**
 * Models.User class
 * This class is used to create user objects
 *
 * @version 1.0
 * @since 2024-01-11
 */
public class User implements Serializable {
    private final String userName;  // final because username cannot be changed
    private final String password;  // final because password cannot be changed
    private boolean hasPurchaseHistory = false;

    /**
     * @param userName username of the user
     * @param password password of the user
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    /**
     * @return Password of the user
     */
    public String getPassword() {
        return password;
    }


    /**
     * @return Username of the user
     */
    public String getUsername() {
        return userName;
    }


    /**
     * @return boolean value of whether the user
     * has purchase history (If true then user
     * has purchase history, else false)
     */
    public boolean hasPurchaseHistory() {
        return hasPurchaseHistory;
    }


    /**
     * @param hasPurchaseHistory sets the boolean value of
     *                           whether the user has purchased before
     */
    public void setHasPurchaseHistory(boolean hasPurchaseHistory) {
        this.hasPurchaseHistory = hasPurchaseHistory;
    }
}