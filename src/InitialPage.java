import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Manages and displays the Initial Page GUI to choose the role (Admin or User)
 * Spawns the CLI if the user chooses Admin
 * Spawns the Login Page if the user chooses User
 *`
 * ``````````````````````````````````````````````````````````````````````````````````````````````````````````````````````````
 * References:
 * <a href="https://docs.oracle.com/javase/8/docs/api/java/awt/Window.html#setLocationRelativeTo-java.awt.Component-">...</a>
 * ..........................................................................................................................
 */
public class InitialPage extends JFrame implements MouseListener {
    JButton adminBtn, userBtn;
    Panel leftPanel;
    JLabel txtLabel, labelHead;

    /**
     * Constructor to initialize the Initial Page GUI
     */
    public InitialPage() {
        ImageIcon imageIcon = new ImageIcon("./src/assets/trimPic1.png");
        JLabel label = new JLabel();
        label.setIcon(imageIcon);
        label.setSize(502, 719);

        // Setting up the Head label which displays Welcome!
        labelHead = new JLabel();
        labelHead.setBounds(150, 120, 502, 266);
        labelHead.setText("Welcome!");
        labelHead.setFont(new Font("Inter", Font.BOLD, 32));
        labelHead.setForeground(new Color(0x68F0F0
        ));

        // Setting up the text label
        txtLabel = new JLabel();
        txtLabel.setText("Westminster Shopping Management System");
        txtLabel.setBounds(50, 180, 502, 266);
        txtLabel.setFont(new Font("Inter", Font.PLAIN, 20));
        txtLabel.setForeground(new Color(0xD9D9D9));

        // Setting up the Admin button setup
        adminBtn = new JButton("Admin (CLI)");
        adminBtn.setBounds(608, 200, 298, 91);
        adminBtn.setFont(new Font("Inter", Font.PLAIN, 32));
        adminBtn.addMouseListener(this);
        adminBtn.setBackground(new Color(0x333333));
        adminBtn.setForeground(new Color(0xD9D9D9));
        adminBtn.setFocusable(false);
        adminBtn.setBorder(null);

        // Setting up the User button setup
        userBtn = new JButton("User (GUI)");
        userBtn.setBounds(608, 400, 298, 91);
        userBtn.setFont(new Font("Inter", Font.PLAIN, 32));
        userBtn.addMouseListener(this);
        userBtn.setBackground(new Color(0x333333));
        userBtn.setForeground(new Color(0xD9D9D9));
        userBtn.setFocusable(false);
        userBtn.setBorder(null);

        // Setting up the Left panel
        leftPanel = new Panel();
        leftPanel.setBounds(0, 0, 502, 719);
        leftPanel.add(txtLabel);
        leftPanel.add(labelHead);
        leftPanel.add(label);
        leftPanel.setLayout(null);

        setTitle("Westminster Shopping Management System");
        setSize(1000, 719);
        setLayout(null);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adding components to the JFrame
        add(adminBtn);
        add(userBtn);
        add(leftPanel);

        setLocationRelativeTo(null);  // Center the JFrame in the screen
        setResizable(false);
        setVisible(true);
    }


    /**
     * Invoked when the user clicks on admin or user button to select role
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == userBtn){
            new LoginPage();
            dispose();
        }

        if (e.getSource() == adminBtn){
            dispose();  // exit the role selection window
            Main.isActivate(true);  // start the CLI if user click on admin

        }
    }


    /**
     * Invoked when the user press on admin or user button to select role
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == adminBtn) {
            adminBtn.setBackground(new Color(104, 240, 240));
            adminBtn.setForeground(new Color(0x1F1F1F));
            adminBtn.setBorder(BorderFactory.createLineBorder(new Color(0x1F1F1F), 2));
        }

        if (e.getSource() == userBtn) {
            userBtn.setBackground(new Color(104, 240, 240));
            userBtn.setForeground(new Color(0x1F1F1F));
            userBtn.setBorder(BorderFactory.createLineBorder(new Color(0x1F1F1F), 2));
        }


    }


    /**
     * Invoked when the user hovers over the admin or user button to select role.
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == adminBtn ) {
            adminBtn.setBackground(new Color(0x1F1F1F));
            adminBtn.setForeground(new Color(0xD9D9D9));
            adminBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        if (e.getSource() == userBtn ) {
            userBtn.setBackground(new Color(0x1F1F1F));
            userBtn.setForeground(new Color(0xD9D9D9));
            userBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }


    /**
     * Invoked when the user hovers out of the admin or user button to select role.
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == adminBtn ) {
            adminBtn.setBackground(new Color(0x333333));
            adminBtn.setForeground(new Color(0xD9D9D9));
        }

        if (e.getSource() == userBtn ) {
            userBtn.setBackground(new Color(0x333333));
            userBtn.setForeground(new Color(0xD9D9D9));
        }

    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }

}
