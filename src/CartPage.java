import models.Clothing;
import models.Electronics;
import models.Product;
import models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * CartPage class is responsible for displaying and updating the CartPage GUI
 *.
 * ``````````````````````````````````````````````````````````````````````````````````````````````````````````````````
 * References:
 * <a href="https://medium.com/@thecodebean/singleton-design-pattern-implementation-in-java-1fba4ecc959f">...</a>
 * <a href="https://stackoverflow.com/questions/12838978/how-to-surround-java-swing-components-with-border">...</a>
 * <a href="https://stackoverflow.com/questions/7433602/how-to-center-in-jtable-cell-a-value">...</a>
 * ..................................................................................................................
 *
 */
public class CartPage extends JFrame implements MouseListener {
    private final ShoppingCart shoppingCart = ShoppingCart.getInstance();
    private final User currentUser = shoppingCart.getCurrentUser();
    private static CartPage instance;
    JPanel topPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JTable table;
    JScrollPane tableScrollPane;
    JLabel infoLbl1, infoLbl2, infoLbl3, infoLbl4;
    JLabel totalLbl, disc10Lbl, disc20Lbl, finalLbl;
    JButton checkoutBtn;

    /**
     * Creates an instance of the CartPage only if
     * an instance does not exist, otherwise return
     * the existing one
     *
     * @return the instance of CartPage
     */
    public static CartPage getInstance(boolean isUserLoggedIn) {
        if (instance == null) {
            if (isUserLoggedIn) {
                instance = new CartPage();
            }
        }
        return instance;
    }


    /**
     * Private constructor to prevent any other class from
     *  instantiating the CartPage
     *`
     *  This ensures that the same instance of the CartPage
     *   is used throughout the program via singleton design pattern
     */
    private CartPage() {
        shoppingCart.setCartPage(this);
        // Setting sizes and positioning of the JPanels
        topPanel.setBounds(0,0,800, 327);
        topPanel.setLayout(null);

        bottomPanel.setBounds(50, 341, 446, 150);
        bottomPanel.setBackground(new Color(0xE1E1E1));
        bottomPanel.setLayout(null);

        buttonPanel.setBounds(500, 341, 250, 150);
        buttonPanel.setBackground(new Color(0x191919));
        buttonPanel.setLayout(null);

        // Setting up the JTable
        String[] columns = {"Product", "Quantity", "Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);

        // Centering the text in the table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Setting the column width
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(50, 50, 700, 250);


        // Setting up the JLabels for the bottom panel
        infoLbl1 = new JLabel("Total: ");
        infoLbl1.setFont(new Font("Inter", Font.PLAIN, 15));
        infoLbl1.setBounds(260, 10, 100, 20);

        infoLbl2 = new JLabel("10% Discount: ");
        infoLbl2.setFont(new Font("Inter", Font.PLAIN, 15));
        infoLbl2.setBounds(202, 40, 120, 20);

        infoLbl3 = new JLabel("20% Discount: ");
        infoLbl3.setFont(new Font("Inter", Font.PLAIN, 15));
        infoLbl3.setBounds(202, 70, 120, 20);

        infoLbl4 = new JLabel("Final Total: ");
        infoLbl4.setBounds(220, 100, 100, 20);
        infoLbl4.setFont(new Font("Inter", Font.BOLD, 15));

        totalLbl = new JLabel();
        totalLbl.setBounds(350, 10, 100, 20);

        disc10Lbl = new JLabel();
        disc10Lbl.setBounds(350, 40, 100, 20);

        disc20Lbl = new JLabel();
        disc20Lbl.setBounds(350, 70, 100, 20);

        finalLbl = new JLabel();
        finalLbl.setBounds(350, 100, 100, 20);

        // Setting up the checkout button
        checkoutBtn = new JButton();
        checkoutBtn.setText("Checkout");
        checkoutBtn.setBounds(0, 110, 142, 44);
        checkoutBtn.setFont(new Font("Inter", Font.PLAIN, 15));
        checkoutBtn.setBackground(new Color(0x56FFFF));
        checkoutBtn.setBorder(null);
        checkoutBtn.setFocusable(false);
        checkoutBtn.addMouseListener(this);


        // Adding the table to the top panel
        topPanel.add(tableScrollPane);

        // Adding the buttons to the bottom panel
        bottomPanel.add(infoLbl1);
        bottomPanel.add(infoLbl2);
        bottomPanel.add(infoLbl3);
        bottomPanel.add(infoLbl4);

        bottomPanel.add(totalLbl);
        bottomPanel.add(disc10Lbl);
        bottomPanel.add(disc20Lbl);
        bottomPanel.add(finalLbl);

        // Adding the button to the button panel
        buttonPanel.add(checkoutBtn);

        setTitle("Shopping cart");
        setSize(800, 558);
        getContentPane().setBackground(new Color(0x191919));
        setLayout(null);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);  // Closes up the cart page window without terminating the program

        // Adding the panels to the frame
        add(topPanel);
        add(bottomPanel);
        add(buttonPanel);

        setLocationRelativeTo(null);  // Centers the window
        setResizable(false);  // Prevents the user from resizing the window
        setVisible(true);

    }


    /**
     * Updates and populates the Jtable with the products in the cart
     */
    public void updateTableWithProducts() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();  // Fetching the table model

        model.setRowCount(0);  // Clearing the table

        for (Product product : shoppingCart.getCartProducts()) {
            addToCartTable(model, product);
        }

    }


    /**
     * Updates the price labels in the bottom panel
     * with the total, discounts and final total of the cart
     */
    public void updatePriceLabels() {
        double total = calculateTotal();
        double[] discount = shoppingCart.calculateDiscount(currentUser);
        double finalTotal = total - (total * (discount[0]+discount[1]) );

        totalLbl.setText(String.format("%.2f", total));
        disc10Lbl.setText("- " + String.format("%.2f", total * discount[0]));
        disc20Lbl.setText("- " + String.format("%.2f", total * discount[1]));
        finalLbl.setText(String.format("%.2f", finalTotal));
    }


    /**
     * Calculates the total price of the cart
     * @return the total price of the cart
     */
    private double calculateTotal() {
        double total = 0;

        for (int i = 0; i < table.getRowCount(); i++) {
            total += (Integer) table.getValueAt(i, 1) * (Double) table.getValueAt(i, 2);
        }
        return total;
    }


    /**
     * Adds a product to the cart table
     * @param model the table model
     * @param product the product to be added to the table
     */
    public void addToCartTable(DefaultTableModel model, Product product) {
        int productRow = findInTable(model, product);

        if (productRow != -1) {
            // If product is already in the table, increment the quantity
            int currentQuantity = (Integer) model.getValueAt(productRow, 1);
            model.setValueAt(currentQuantity + 1, productRow, 1);

        } else {
            String type = product instanceof Electronics ?
                    " " + ((Electronics) product).getBrand() + ", " + ((Electronics) product).getWarrantyPeriod() + " weeks warranty":
                    " " + ((Clothing) product).getSize() + ", " + ((Clothing) product).getColor();

            Object[] row = new Object[3];  // Object type array since each method returns different data types
            row[0] = product.getProductID() + "\n"
                    + product.getProductName() + String.format(type);

            row[1] = 1;

            row[2] = product.getPrice();

            model.addRow(row);
        }
        updatePriceLabels();
    }


    /**
     * Finds a product in the table and returns its row index
     *  if found or -1 if not found
     * @param model the table model
     * @param product the product to be found
     * @return the row index of the product in the table
     */
    private int findInTable(DefaultTableModel model, Product product) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().startsWith(product.getProductID())) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Invoked when the mouse button has been clicked
     *  (pressed and released) on a component.
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == checkoutBtn) {
            // Get the table model
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Your cart is empty");  // Display an error message if the cart is empty
                return;
            }

            // Iterate over the rows of the table
            for (int i = 0; i < model.getRowCount(); i++) {
                // Get the product ID and quantity from the table
                String productID = model.getValueAt(i, 0).toString().split("\n")[0];
                int quantity = (Integer) model.getValueAt(i, 1);

                // Find the corresponding product in the productList
                Product product = WestminsterShoppingManager.findById(productID);  // Returns null if not found

                if (product.getNumOfAvailableItems() >= 0) {  // Check if the product is still available

                    // Update the numOfAvailableItems attribute of the product3
                    System.out.println(product.getNumOfAvailableItems());

                    // update the total available space in the system
                    WestminsterShoppingManager.totalNumOfProducts -= quantity;

                    JOptionPane.showMessageDialog(this, "Checkout successful");  // Display a success message
                }
            }

            // Clear all products in the cart
            shoppingCart.getCartProducts().clear();
            totalLbl.setText("");
            disc10Lbl.setText("");
            disc20Lbl.setText("");
            finalLbl.setText("");
            currentUser.setHasPurchaseHistory(true);

            // Update the table
            updateTableWithProducts();
        }
    }

    /**
     * Invoked when the mouse enters a component.
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == checkoutBtn){
            checkoutBtn.setBackground(new Color(0x37CBCB));
        }
    }

    /**
     * Invoked when the mouse exits a component.
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == checkoutBtn){
            checkoutBtn.setBackground(new Color(0x56FFFF));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }

}
