import models.Clothing;
import models.Electronics;
import models.Product;
import models.User;
import utilities.QuickSort;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages and displays the elements of Shopping Page GUI
 *`
 * ````````````````````````````````````````````````````````````````````````````````````````````````````
 * References:
 * <a href="https://stackoverflow.com/questions/7433602/how-to-center-in-jtable-cell-a-value">...</a>
 * ....................................................................................................
 *`
 * - Displays the products in a table
 * - Displays the product info in a text pane
 * - Displays the shopping cart page when the
 *   user clicks the "Shopping Cart" button
 */
public class ShoppingPage extends JFrame implements ActionListener, MouseListener {
    private final ShoppingCart shoppingCart = ShoppingCart.getInstance();
    private final User currentUser = shoppingCart.getCurrentUser();
    JPanel topPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JComboBox<String> categoryBox;
    JButton viewCartBtn, addToCartBtn, sortProductsBtn;
    JTable table;
    JLabel selectProductLbl, headingTxtLbl;
    JScrollPane tableScrollPane;
    JTextPane infoTxtPane;

    /**
     * Creates and displays the Shopping Page GUI
     */
    public ShoppingPage() {
        // Setting the size and position of the panels
        topPanel.setBounds(0, 0, 800, 133);
        topPanel.setBackground(Color.WHITE);
        topPanel.setLayout(null);

        centerPanel.setBounds(0, 133, 800, 200);
        centerPanel.setBackground(Color.WHITE);

        bottomPanel.setBounds(0, 333, 800, 320);
        bottomPanel.setBackground(Color.lightGray);
        bottomPanel.setLayout(null);


        // Adding the select product label into the top panel
        selectProductLbl = new JLabel("Select Product Category");
        selectProductLbl.setBounds(100, 35, 138, 33);
        topPanel.add(selectProductLbl);

        // Adding dropdown menu into the top panel
        categoryBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        categoryBox.setBounds(281, 38, 186, 30);
        categoryBox.addActionListener(this);  // Adding an action listener to category dropdown menu
        topPanel.add(categoryBox);

        // Adding the view cart button into the top panel
        viewCartBtn = new JButton("Shopping Cart");
        viewCartBtn.setBounds(570, 38, 180, 30);
        viewCartBtn.setBackground(new Color(0x191919));
        viewCartBtn.setForeground(new Color(0xFFFFFF));
        viewCartBtn.addMouseListener(this);
        viewCartBtn.setFont(new Font("Inter", Font.BOLD, 12));
        viewCartBtn.setBorder(null);
        viewCartBtn.setFocusable(false);
        viewCartBtn.addMouseListener(this);
        topPanel.add(viewCartBtn);

        // Creating and adding sort button to the top panel
        sortProductsBtn = new JButton("Sort Table");
        sortProductsBtn.setBounds(50, 100, 93, 28);
        sortProductsBtn.setBackground(new Color(0x191919));
        sortProductsBtn.setForeground(new Color(0xFFFFFF));
        sortProductsBtn.setFont(new Font("Inter", Font.BOLD, 12));
        sortProductsBtn.setFocusable(false);
        sortProductsBtn.setBorder(null);
        sortProductsBtn.addMouseListener(this);
        topPanel.add(sortProductsBtn);

        // Adding JTable into the center panel
        String[] columns = {"Product ID", "Name", "Category", "Price(€)", "Info"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);  // Creating a new JTable
        table.setRowHeight(35);  // Setting the row height of the table
        table.addMouseListener(this);


        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String productID = table.getValueAt(row, 0).toString();


                for (Product product : WestminsterShoppingManager.productList) {
                    if (product.getProductID().equals(productID) && product.getNumOfAvailableItems() < 3) {
                        setForeground(Color.RED);
                        break;
                    } else {
                        setForeground(Color.BLACK);
                    }
                }

                return this;
            }
        };


        // Set the custom renderer to all columns
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(700, 163));
        updateTableWithProducts();
        centerPanel.add(tableScrollPane);


        /*
         Adding the product info text area, heading text label
         and the add to cart button into the bottom panel
         */
        headingTxtLbl = new JLabel("Selected Product - Details");
        headingTxtLbl.setBounds(50, 10, 400, 20);
        headingTxtLbl.setForeground(Color.white);
        bottomPanel.add(headingTxtLbl);

        infoTxtPane = new JTextPane();
        infoTxtPane.setBounds(50, 40, 200, 220);
        bottomPanel.add(infoTxtPane);

        addToCartBtn = new JButton("Add to Shopping Cart");
        addToCartBtn.setBounds(300, 216, 200, 44);
        addToCartBtn.setBackground(new Color(0x56FFFF));
        addToCartBtn.setFont(new Font("Inter", Font.PLAIN, 15));
        addToCartBtn.setBorder(null);
        addToCartBtn.setFocusable(false);
        addToCartBtn.addMouseListener(this);
        bottomPanel.add(addToCartBtn);

        ImageIcon imageIcon = new ImageIcon("./src/assets/back1.png");
        JLabel imageContainer = new JLabel();
        imageContainer.setIcon(imageIcon);
        imageContainer.setSize(800, 320);
        bottomPanel.add(imageContainer);

        setTitle("Westminster Shopping Centre");  // Sets the window title as "Westminster Shopping Centre"
        setSize(800, 653);  // Sets the size of the window
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adding the panels to the frame
        add(topPanel);
        add(centerPanel);
        add(bottomPanel);

        setLocationRelativeTo(null);
        setResizable(false);  // Prevents the user from resizing the window
        setVisible(true);  // Shows the window
    }


    /**
     * Updates the table with the products
     * in the product list (from WestminsterShoppingManager class)
     */
    public void updateTableWithProducts() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();  // Fetching the table model

        model.setRowCount(0);  // Clearing the table

        for (Product product : WestminsterShoppingManager.productList) {
            addToTable(model, product);
        };
    }


    /**
     * Adds a product to the table
     * @param model  The table model
     * @param product The product to be added to the table
     */
    public void addToTable(DefaultTableModel model, Product product) {
        Object[] row = new Object[5];  // Object type array since each method returns different data types
        row[0] = product.getProductID();
        row[1] = product.getProductName();
        row[2] = product.getClass().getSimpleName();
        row[3] = product.getPrice();
        row[4] = product instanceof Electronics ?
                ((Electronics) product).getBrand() + ", " + ((Electronics) product).getWarrantyPeriod() + " weeks warranty" :
                ((Clothing) product).getSize() + ", " + ((Clothing) product).getColor();
        model.addRow(row);
    }


    /**
     * Handles the action events of where the user
     *  clicks on the dropdown menu
     *`
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == categoryBox) {  // Dropdown menu action listener (categoryBox)
            String selectedCategory = categoryBox.getSelectedItem().toString();

            if (selectedCategory.equals("All")) {
                updateTableWithProducts();

            } else {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);

                for (Product product : WestminsterShoppingManager.productList) {
                    if (product.getClass().getSimpleName().equals(selectedCategory)) {
                        addToTable(model, product);
                    }
                }
            }
        }
    }


    /**
     * Handles the mouse events of where the user selects a table row
     * or clicks on the "Sort Table" button or "Shopping Cart" button
     * or "Add to Shopping Cart" button
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (table.getSelectedRow() != -1) {
            String productID = table.getValueAt(table.getSelectedRow(), 0).toString();
            // Find the product in the product list
            for (Product product : WestminsterShoppingManager.productList) {
                if (product.getProductID().equals(productID)) {

                    // https://www.programiz.com/java-programming/library/string/replace
                    infoTxtPane.setText("Category: " + product.getClass().getSimpleName() +"\n\n"+
                            product.toString().replace("\n", "\n\n"));
                }
            }
        }
        if (e.getSource() == sortProductsBtn) {
            sortAndPopulateTable();
        }

        if (e.getSource() == viewCartBtn) {
            CartPage.getInstance(true).setVisible(true);
        }

        if (e.getSource() == addToCartBtn) {
            String lines;
            try {
                lines = infoTxtPane.getText(0, infoTxtPane.getDocument().getLength());
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }
            if (!lines.isEmpty()) {
                String productID = lines.split(": ")[2].substring(0, 4);  // Extracting product ID value from infoTxtPane
                System.out.println(productID);

                for (Product product : WestminsterShoppingManager.productList) {
                    if (product.getProductID().equals(productID)) {

                        // For debugging purposes
                        System.out.println("product found and passed");
                        boolean isAdded = shoppingCart.addToCart(product);  // Adding the product to the shopping cart

                        if (!isAdded){
                            showAlert("The stock has run out for this product: " + product.getProductName());

                        } else {
                            infoTxtPane.setText("Category: " + product.getClass().getSimpleName() +"\n\n"+
                                    product.toString().replace("\n", "\n\n"));
                        }

                        CartPage.getInstance(true).updateTableWithProducts();  // Updating the cart table
                        break;
                    }
                }
            }
        }
    }


    /**
     * Sorts the products in the product table
     */
    private void sortAndPopulateTable() {
        List<Product> sortedList = new ArrayList<>(WestminsterShoppingManager.productList);
        sortedList = QuickSort.quickSort(sortedList, 0, sortedList.size() - 1);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);  // Clear the table

        for (Product product : sortedList) {
            addToTable(model, product);
        }
    }


    /**
     * @param message the message to be displayed in the alert box
     */
    public void showAlert(String message) {
        JOptionPane.showMessageDialog(this, message);
    }


    /**
     * Handles the mouse events of where the user hovers over
     * the addToCart button or viewCart button or sortProducts button
     *`
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == addToCartBtn){
            addToCartBtn.setBackground(new Color(0x37CBCB));
            addToCartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        if (e.getSource() == viewCartBtn) {
            viewCartBtn.setBackground(new Color(0x37CBCB));
            viewCartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        if (e.getSource() == sortProductsBtn) {
            sortProductsBtn.setBackground(new Color(0x37CBCB));
            sortProductsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }


    /**
     * Handles the mouse events of where the user's cursor exits
     *  a certain buttons bounds
     *`
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == addToCartBtn){
            addToCartBtn.setBackground(new Color(0x56FFFF));
        }

        if (e.getSource() == viewCartBtn) {
            viewCartBtn.setBackground(new Color(0x191919));
        }

        if (e.getSource() == sortProductsBtn) {
            sortProductsBtn.setBackground(new Color(0x191919));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

}