import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * LoginPage class is responsible for managing and displaying the Login Page GUI
 * Contains all GUI swing elements and interactive functionality.
 *
 */
public class LoginPage extends JFrame implements MouseListener {
    JLabel usernameLabel, passwordLabel;
    JButton loginBtn, RegisterBtn, backBtn;
    JTextField usernameField;
    JPasswordField passwordField;

    /**
     * LoginPage constructor to initialize the
     * GUI elements and display the Login Page
     */
    public LoginPage(){

        // Username label setup
        usernameLabel = new JLabel("Username: ");
        usernameLabel.setForeground(new Color(0xEBEBEB));
        usernameLabel.setBounds(80, 60, 110, 18);
        usernameLabel.setFont(new Font("Inter", Font.PLAIN, 15));

        passwordLabel = new JLabel("Password: ");
        passwordLabel.setForeground(new Color(0xEBEBEB));
        passwordLabel.setBounds(80, 120, 110, 18);
        passwordLabel.setFont(new Font("Inter", Font.PLAIN, 15));

        // Username field setup
        usernameField = new JTextField();
        usernameField.setBounds(160, 58, 241, 37);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 118, 241, 37);

        // Login button setup
        loginBtn = new JButton("Login");
        loginBtn.setBounds(291, 170, 110, 30);
        loginBtn.setFont(new Font("Inter", Font.PLAIN, 15));
        loginBtn.setBackground(new Color(0x85FEFE));
        loginBtn.addMouseListener(this);
        loginBtn.setFocusable(false);
        loginBtn.setBorder(null);

        // Register button setup
        RegisterBtn = new JButton("Register");
        RegisterBtn.setBounds(291, 215, 110, 30);
        RegisterBtn.setFont(new Font("Inter", Font.PLAIN, 15));
        RegisterBtn.setBackground(new Color(0xD9D9D9));
        RegisterBtn.addMouseListener(this);
        RegisterBtn.setFocusable(false);
        RegisterBtn.setBorder(null);

        // Back button setup
        backBtn = new JButton();
        backBtn.setText("Back");
        backBtn.setFocusable(false);
        backBtn.setBounds(9, 11, 50, 25);
        backBtn.setFont(new Font("Inter", Font.BOLD, 12));
        backBtn.setBackground(new Color(0X828282));
        backBtn.setBorder(null);
        backBtn.addMouseListener(this);

        // Adding all the elements to the JFrame
        setTitle("Westminster Shopping Management System");
        setSize(482, 300);
        getContentPane().setBackground(new Color(0x171717));
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(usernameLabel);
        add(passwordLabel);
        add(usernameField);
        add(passwordField);
        add(loginBtn);
        add(RegisterBtn);
        add(backBtn);

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }


    /**
     * This method is invoked when the mouse button
     * has been clicked (pressed and released) on a component.
     *`
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == RegisterBtn) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password to register");

            } else if (WestminsterShoppingManager.isUserExist(username)) {
                JOptionPane.showMessageDialog(this, "A username with that name already exists.");

            } else {
                User newUser = new User(username, password);
                WestminsterShoppingManager.users.add(newUser);
                JOptionPane.showMessageDialog(this, "User successfully registered. Login to continue.");
            }
        }

        if (e.getSource() == loginBtn){
            if (usernameField.getText().isEmpty() || new String(passwordField.getPassword()).isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password to login");

            } else {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check whether the given credentials are existing in the users list
                for (User user : WestminsterShoppingManager.users) {

                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                        ShoppingCart shoppingCart = ShoppingCart.getInstance();
                        shoppingCart.setCurrentUser(user);
                        JOptionPane.showMessageDialog(this, "LogIn success!");
                        new ShoppingPage();
                        Main.isAutoSaveEnabled = true;
                        dispose();
                        return;
                    }

                }
                JOptionPane.showMessageDialog(this, "Please register first!");
            }
        }

        if (e.getSource() == backBtn) {
            new InitialPage();
            dispose();
        }
    }


    /**
     * This method is invoked when the mouse enters a component.
     *`
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == loginBtn) {
            loginBtn.setBackground(new Color(0x1FCDCD));
            loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        if (e.getSource() == RegisterBtn) {
            RegisterBtn.setBackground(new Color(0x999999));
            RegisterBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        if (e.getSource() == backBtn) {
            backBtn.setBackground(new Color(0xA4A4A4));
            backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }


    /**
     * This method is invoked when the mouse exits a component.
     *`
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == loginBtn) {
            loginBtn.setBackground(new Color(0x68F0F0));
            loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        if (e.getSource() == RegisterBtn) {
            RegisterBtn.setBackground(new Color(0xD9D9D9));
            RegisterBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        if (e.getSource() == backBtn) {
            backBtn.setBackground(new Color(0X828282));
            backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}