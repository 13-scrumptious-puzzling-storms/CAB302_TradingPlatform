package TradingPlatform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class GUILogin extends JFrame implements ActionListener, FocusListener, Runnable {
    // Screen Sizing
    private static final float WIDTH_RATIO = 4.5f;
    private static final int HEIGHT_RATIO = 2;
    private static final float WINDOW_RATIO = 2;

    // Constant Strings
    private static final String usernameText = " Username";
    private static final String passwordText = " Password";

    // Fonts
    private static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 24);
    private static final Font FONT_HEADER = new Font("SansSerif", 0, 24);
    private static final Font FONT_HEAVY = new Font("SansSerif", Font.BOLD, 16);
    private static final Font FONT_LIGHT = new Font("SansSerif", 0, 16);
    private static final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 12);
    private static final Font FONT_FIELD = new Font("SansSerif", 0, 12);

    // Colours
    private static final Color CELADON_GREEN = new Color(38,139,133);
    private static final Color OCEAN_GREEN = new Color(72,191,146);
    private static final Color CRIMSON = new Color(214, 40, 57);
    private static final Color CHARCOAL = new Color(51,61,68);
    private static final Color DARK_JUNGLE_GREEN = new Color(13, 27, 30);
    private static final Color WHITE = Color.WHITE;

    // Screen Sizes
    private static int windowWidth;
    private static int windowHeight;

    // Text Fields
    private static JTextField usernameField;
    private static JTextField passwordField;

    // Buttons
    private static JButton loginButton;

    public static JFrame jframe;
    private static NetworkManager networkManager = ClientApp.networkManager;

    // Refer to WIRING FOR GOOD COMMENTS
    @Override
    public void run() {
        try { displayJFrame(); }
        catch (IOException e) { e.printStackTrace(); }
    }

    private void displayJFrame() throws IOException {
        // Initialise JFrame
        jframe = new JFrame("SPS Trading Platform");
        jframe.setDefaultLookAndFeelDecorated(false);
        jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Get and set relative screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        float screenWidth = (float)screenSize.getWidth();
        float screenHeight = (float)screenSize.getHeight();
        windowWidth = (int)(screenWidth / WIDTH_RATIO);
        windowHeight = (int)(screenHeight / HEIGHT_RATIO);

        // Set background colour of window
        jframe.getContentPane().setBackground(CELADON_GREEN);

        // Create console tab
        JPanel loginTab = new JPanel();
        loginTab.setBackground(DARK_JUNGLE_GREEN);
        loginPanel(loginTab);
        loginTab.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        // Create and pane and add the console tab
        UIManager.put("TabbedPane.selected", DARK_JUNGLE_GREEN);
        JTabbedPane pagePane = new JTabbedPane();
        pagePane.add("Login", loginTab);
        jframe.getContentPane().add(pagePane);
        pagePane.setForeground(WHITE); // Tab text colour //pagePane.setBackground(cust1);

        // Set dimensions and location of window. Display the window
        jframe.setPreferredSize(new Dimension(windowWidth, windowHeight));
        jframe.setLocation(new Point(Math.round(screenWidth / 2) - windowWidth/2,  Math.round(screenHeight / 2) - windowHeight / 2)); // window position on screen
        jframe.pack();
        jframe.setVisible(true);
    }

    private void loginPanel(JPanel panel) {
        // Labels
        JLabel headerLabel = newLabel("SPS Trading", FONT_HEADER, OCEAN_GREEN);
        JLabel loginLabel = newLabel("User Login", FONT_HEAVY, WHITE);

        // Text Fields
        usernameField = newTextField(usernameText, FONT_FIELD, CELADON_GREEN, DARK_JUNGLE_GREEN);
        passwordField = newTextField(passwordText, FONT_FIELD, CELADON_GREEN, DARK_JUNGLE_GREEN);

        // Buttons
        loginButton = newButton("LOGIN", FONT_BUTTON, WHITE, OCEAN_GREEN);

        // Panels
        JPanel buttonPanel = newPanel(CHARCOAL);

        // Panel Settings
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(35, 40, 35, 40));
        buttonPanel.setLayout(new GridLayout(0,1, 75, 10));

        // Add content to panel
        buttonPanel.add(headerLabel);
        buttonPanel.add(loginLabel);
        buttonPanel.add(usernameField);
        buttonPanel.add(passwordField);
        buttonPanel.add(loginButton);

        // Panel to main panel
        panel.add(buttonPanel); //panel.add(buttonPanel, BorderLayout.NORTH);
    }

    private static JLabel newLabel(String text, Font font, Color textColour) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(textColour);
        return label;
    }

    private JTextField newTextField(String initialText, Font font, Color textColour, Color fieldColour) {
        JTextField textField = new JTextField(initialText);
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setFont(font);
        textField.setForeground(textColour);
        textField.setBackground(fieldColour);
        textField.addFocusListener(this);
        return textField;
    }

    private JButton newButton(String text, Font font, Color textColour, Color btnColour) {
        JButton button = new JButton(text);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFont(font);
        button.setForeground(textColour);
        button.setBackground(btnColour);
        button.addActionListener(this);
        return button;
    }

    private static JPanel newPanel(Color bg) {
        JPanel panel = new JPanel();
        panel.setBackground(bg);
        return panel;
    }

    private void attemptLogin() throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        String username = usernameField.getText();
        String hashedPass = SHA256.hashPassword(passwordField.getText());
        Request response = networkManager.GetResponse("JDBCUserDataSource", "getUserId", new String[] { username, hashedPass });

        int userID = Integer.parseInt(response.getArguments()[0]);
        if (userID != -1) {
            ClientApp.launchProgram(userID);
        } else {
            JOptionPane.showConfirmDialog(this, "Invalid username or password.", "Access Denied", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource(); // Get the event source
        if (src instanceof JButton) {
            JButton btn = ((JButton) src);
            if (btn == loginButton) {
                try { attemptLogin(); }
                catch (Exception ex) { ex.printStackTrace(); }
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        Object src = e.getSource();
        if (src instanceof JTextField) {
            JTextField txt = ((JTextField) src);
            if (txt == usernameField && usernameField.getText().equals(usernameText)) {
                usernameField.setText("");
            } else if (txt == passwordField && passwordField.getText().equals(passwordText)) {
                passwordField.setText("");
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        Object src = e.getSource();
        if (src instanceof JTextField) {
            JTextField txt = ((JTextField) src);
            if (txt == usernameField && usernameField.getText().equals("")) {
                usernameField.setText(usernameText);
            } else if (txt == passwordField && passwordField.getText().equals("")) {
                passwordField.setText(passwordText);
            }
        }
    }
}
