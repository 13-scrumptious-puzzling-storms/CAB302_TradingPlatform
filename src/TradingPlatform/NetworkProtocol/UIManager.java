package TradingPlatform.NetworkProtocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIManager extends JFrame implements ActionListener, Runnable {
    private static final int FONT_SIZE = 24;

    private static int width;
    private static int height;

    // GUI Panels
    private JPanel row1;
    private JPanel row2;
    private JPanel row3;
    private JPanel row4;
    private JPanel row5;
    private JPanel row6;

    // GUI Buttons
    private JButton btnChange;
    private JButton btnEditSchema;
    private JButton btnEditUser;
    private JButton btnEditPass;
    private JButton btnShutdown;

    // Text headings and fields
    private JTextArea headStatus;
    private JTextArea status;

    private JTextArea headFileName;
    private JTextArea fileName;

    private JTextArea headSchema;
    private JTextArea schema;

    private JTextArea headUser;
    private JTextArea username;

    private JTextArea headPass;
    private JTextArea password;

    private JTextArea headServerControl;
    private JTextArea serverControl;

    public UIManager(String title) throws HeadlessException {
        super(title);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get event source
        Object src = e.getSource();
        if (src instanceof JButton) {
            JButton btn = ((JButton) src);
            if (btn == btnChange) {
                // Change file for props
            } else if (btn == btnEditSchema) {
                // change name of schema
            } else if (btn == btnEditUser) {
                // change username
            } else if (btn == btnEditPass) {
                // change password
            } else if (btn == btnShutdown) {
                // shutdown server
            } else {
                JOptionPane.showMessageDialog(this, "Something unexpected has occurred!", "Trading Platform Server: Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @Override
    public void run() {
        // Get size of screen
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        width = (int)size.getWidth();
        height = (int)size.getHeight();

        // Create GUI
        createGUI();
        //layoutButtonPanel();
    }

    private void createGUI() {
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        row1 = createPanel(Color.white);
        row1 = createPanel(Color.GREEN);
        row1 = createPanel(Color.white);
        row1 = createPanel(Color.GREEN);
        row1 = createPanel(Color.white);
        row1 = createPanel(Color.GREEN);

        btnChange = createButton("Change");
        btnEditSchema = createButton("Edit");
        btnEditUser = createButton("Edit");
        btnEditPass = createButton("Edit");
        btnShutdown = createButton("Shut down");

        headStatus = createDisplay();
        status = createDisplay();
        headFileName = createDisplay();
        fileName = createDisplay();
        headSchema = createDisplay();
        schema = createDisplay();
        headUser = createDisplay();
        username = createDisplay();
        headPass = createDisplay();
        password = createDisplay();
        headServerControl = createDisplay();
        serverControl = createDisplay();

        row1.setLayout(new BorderLayout());
        row1.add(headStatus, BorderLayout.WEST);
        row1.add(status, BorderLayout.EAST);

        // Repaint the GUI now that we've updated the elements
        repaint();

        // Show the GUI to the user
        setVisible(true);
    }

    private JPanel createPanel(Color c) {
        // Create a JPanel object and store it in a local var
        JPanel panel = new JPanel();

        // Set the background colour to that passed in c
        panel.setBackground(c);

        // Return the JPanel object
        return panel;
    }

    private JButton createButton(String str) {
        // Create a JButton object and store it in a local var
        JButton button = new JButton();

        // Set the button text to that passed in str
        button.setText(str);

        // Add the frame as an actionListener
        button.addActionListener(this);

        // Return the JButton
        return button;
    }

    private JTextArea createDisplay() {
        JTextArea display = new JTextArea();
        display.setEditable(false);
        display.setLineWrap(true);
        display.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        display.setBorder(BorderFactory.createEtchedBorder());
        return display;
    }

    public static void main(String[] args)
    {
        //JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new UIManager("BorderLayout"));
    }
}
