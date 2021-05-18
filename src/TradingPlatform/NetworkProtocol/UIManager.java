package TradingPlatform.NetworkProtocol;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIManager extends JFrame implements ActionListener, Runnable {
    private static final int FONT_SIZE = 24;

    private static int width;
    private static int height;

    // Colours
    private static final Color cust1 = new Color(38,139,133);
    private static final Color cust2 = new Color(51,61,68);
    private static final Color cust3 = new Color(72,191,146);

    // GUI Panels
    private JPanel pnlDisplay;
    private JPanel pnlTwo;
    private JPanel pnlThree;
    private JPanel pnlFour;
    private JPanel pnlBtn;

    // GUI Buttons
    private JButton btnMount;
    private JButton btnUnmount;
    private JButton btnFind;
    private JButton btnSwitch;

    // Text headings and fields
    private JTextArea header;
    private JTextArea row1;
    private JTextArea row2;
    private JTextArea areDisplay;

    public UIManager(String title) throws HeadlessException {
        super(title);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get event source
        Object src = e.getSource();
        if (src instanceof JButton) {
            JButton btn = ((JButton) src);
            if (btn == btnSwitch) {
                JOptionPane.showMessageDialog(this, "idiots");
            } else if (btn == btnFind) {
                JOptionPane.showMessageDialog(this, "silly willy");
            } else {
                areDisplay.setText(btn.getText().trim());
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
        layoutButtonPanel();
    }

    private void createGUI() {
        setSize(width / 2, height / 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SPS Trading Server");
        setLayout(new BorderLayout());

        pnlDisplay = createPanel(Color.WHITE);
        pnlTwo = createPanel(Color.RED);
        pnlThree = createPanel(cust2);
        pnlFour = createPanel(cust2); // Setting colour here does't do anythin since this panel gtes filled with text. that text background will be what ditctaes panel backhround
        pnlBtn = createPanel(Color.PINK);

        btnMount = createButton("Mount");
        btnUnmount = createButton("Unmount");
        btnFind = createButton("Find");
        btnSwitch = createButton("Switch");

        areDisplay = createDisplay(24);
        header = createDisplay(24);
        row1 = createDisplay(16);
        row2 = createDisplay(16);

        pnlDisplay.setLayout(new BorderLayout());
        pnlDisplay.add(areDisplay, BorderLayout.CENTER);
        pnlDisplay.add(pnlFour, BorderLayout.NORTH);

        pnlFour.setLayout(new BorderLayout());
        pnlFour.add(header, BorderLayout.CENTER);
        pnlFour.add(pnlTwo, BorderLayout.SOUTH);

        pnlTwo.setLayout(new BorderLayout());
        pnlTwo.add(row1, BorderLayout.CENTER);
        pnlTwo.add(pnlThree, BorderLayout.SOUTH);

        pnlThree.setLayout(new BorderLayout());
        pnlThree.add(row2, BorderLayout.CENTER);

        // Add panels to frame
        getContentPane().add(pnlDisplay,BorderLayout.CENTER);
        getContentPane().add(pnlBtn,BorderLayout.SOUTH);

        header.setForeground(cust1);
        header.setBackground(cust2);
        header.setText(" SPS Trading (Server)");

        row1.setForeground(Color.lightGray);
        row1.setBackground(cust2);
        row1.setText(" Props File: '/sdfsdfd/sdf.db'");

        row2.setForeground(cust3);
        row2.setBackground(cust2);
        row2.setText(" SHUTDOWN");

        row1.setBorder(null);
        row2.setBorder(null);
        header.setBorder(null);

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

    private JTextArea createDisplay(int font_size) {
        JTextArea display = new JTextArea();
        display.setEditable(false);
        display.setLineWrap(true);
        display.setFont(new Font("Arial", Font.BOLD, font_size));
        display.setBorder(BorderFactory.createEtchedBorder());
        return display;
    }

    private void layoutButtonPanel() {
        GridBagLayout layout = new GridBagLayout();
        pnlBtn.setLayout(layout);
        // Main pooper code

        // Add components to the grid
        GridBagConstraints constraints = new GridBagConstraints();

        // Defaults
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 100;
        constraints.weighty = 100;

        addToPanel(pnlBtn, btnMount, constraints, 0, 0, 2, 1);
        addToPanel(pnlBtn, btnUnmount, constraints, 3, 0, 2, 1);
        addToPanel(pnlBtn, btnFind, constraints, 0, 2, 2, 1);
        addToPanel(pnlBtn, btnSwitch, constraints, 3, 2, 2, 1);
    }

    private void addToPanel(JPanel jp, Component c, GridBagConstraints constraints, int x, int y, int w, int h) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        jp.add(c, constraints);
    }

    public static void main(String[] args)
    {
        //JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new UIManager("BorderLayout"));
    }
}
