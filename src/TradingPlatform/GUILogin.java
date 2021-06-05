package TradingPlatform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Responsible for the frontend and backend of the Client's Login.
 */
public class GUILogin extends JFrame implements ActionListener, FocusListener {
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

    // Dynamic Strings
    private static String ipAddressText;
    private static String portText;

    // Text Fields
    private static JTextField usernameField;
    private static JTextField passwordField;
    private static JTextField ipAddressParam;
    private static JTextField portParam;

    // Dynamic Variables
    private static String ipAddress;
    private static int port;

    // Buttons
    private static JButton loginButton;
    private static JButton applyButton;

    public static JFrame jframe;
    private static NetworkManager networkManager = ClientApp.networkManager;

    /**
     * Constructor invokes the initial GUI method.
     */
    public GUILogin() {
        displayJFrame();
    }

    /**
     * Builds the entire graphical user interface for the Login.
     * Initialises JFrame, panels, buttons, labels etc.
     */
    private void displayJFrame() {
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

        // Create settings tab
        JPanel configTab = new JPanel();
        configTab.setBackground(DARK_JUNGLE_GREEN);
        configPanel(configTab);
        configTab.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        // Create the pane and add the console and settings tab
        UIManager.put("TabbedPane.selected", DARK_JUNGLE_GREEN);
        UIManager.put("TabbedPane.unselected", DARK_JUNGLE_GREEN);
        JTabbedPane pagePane = new JTabbedPane();
        pagePane.add("Login", loginTab);
        pagePane.add("Config", configTab);
        pagePane.addFocusListener(this);
        jframe.getContentPane().add(pagePane);
        pagePane.setForeground(WHITE);
        pagePane.setBackground(OCEAN_GREEN);

        // Prevent auto-focus on first component.


        // Set dimensions and location of window. Display the window
        jframe.setPreferredSize(new Dimension(windowWidth, windowHeight));
        jframe.setLocation(new Point(Math.round(screenWidth / 2) - windowWidth/2,  Math.round(screenHeight / 2) - windowHeight / 2)); // window position on screen
        jframe.pack();
        jframe.getContentPane().requestFocusInWindow();
        jframe.setVisible(true);


    }

    /**
     * Creates the login panel.
     * Initialises labels, textFields and buttons.
     * Adds the constructed login panel to provided JPanel panel.
     * @param panel The parent panel which has the Login panel added to it.
     */
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

    private void configPanel(JPanel panel) {
        // Labels
        JLabel headerLabel = newLabel("SPS Trading", FONT_HEADER, OCEAN_GREEN);
        JLabel configLabel = newLabel("Server Address", FONT_HEAVY, WHITE);

        // Dynamic Labels
        ipAddressParam = newTextField(null, FONT_FIELD, CELADON_GREEN, DARK_JUNGLE_GREEN);
        portParam = newTextField(null, FONT_FIELD, CELADON_GREEN, DARK_JUNGLE_GREEN);
        setDynamicFields();

        // Buttons
        applyButton = newButton("SAVE", FONT_BUTTON, WHITE, OCEAN_GREEN);

        // Panels
        JPanel buttonPanel = newPanel(CHARCOAL);

        // Panel Settings
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(35, 40, 35, 40));
        buttonPanel.setLayout(new GridLayout(0,1, 75, 10));

        // Add content to panel
        buttonPanel.add(headerLabel);
        buttonPanel.add(configLabel);
        buttonPanel.add(ipAddressParam);
        buttonPanel.add(portParam);
        buttonPanel.add(applyButton);

        // Panel to main panel
        panel.add(buttonPanel);
    }

    /**
     * A convenience method to create a new label.
     *
     * @param text The text of the new label.
     * @param font The font of the new label.
     * @param textColour The text colour of the new label.
     * @return Returns the newly created label.
     */
    private static JLabel newLabel(String text, Font font, Color textColour) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(textColour);
        return label;
    }

    /**
     * A convenience method to create a new text field.
     * Adds a focus listener to the field to detect mouse clicks.
     *
     * @param initialText The initial text of the text field.
     * @param font The font of the text field.
     * @param textColour The colour of the text of the text field.
     * @param fieldColour The background colour of the text field.
     * @return Returns the newly created text field.
     */
    private JTextField newTextField(String initialText, Font font, Color textColour, Color fieldColour) {
        JTextField textField = new JTextField(initialText);
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setFont(font);
        textField.setForeground(textColour);
        textField.setBackground(fieldColour);
        textField.addFocusListener(this);
        return textField;
    }

    /**
     * A convenience method to create a new button.
     * Adds an action listner to detect when button is pressed.
     *
     * @param text The text of the button.
     * @param font The font of the button.
     * @param textColour The text colour of the button.
     * @param btnColour The colour of the button.
     * @return Returns the newly create button.
     */
    private JButton newButton(String text, Font font, Color textColour, Color btnColour) {
        JButton button = new JButton(text);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFont(font);
        button.setForeground(textColour);
        button.setBackground(btnColour);
        button.addActionListener(this);
        return button;
    }

    /**
     * A convenience method to crate a new panel.
     *
     * @param bg The background colour of the panel.
     * @return Returns the newly created panel.
     */
    private static JPanel newPanel(Color bg) {
        JPanel panel = new JPanel();
        panel.setBackground(bg);
        return panel;
    }

    private static void setDynamicFields() {
        ClientConfig.ReadServerAddress();
        ipAddress = ClientConfig.GetIPAddress();
        port = ClientConfig.GetPort();

        ipAddressParam.setText(" " + ipAddress);
        portParam.setText(" " + port);
    }

    /**
     * Attempts to login the user into the main application.
     * Uses username and password from the text fields and
     * sends these to the server. If valid login, the user
     * will be logged in.
     *
     * @throws IOException if Client fails to connect to the Server.
     * @throws ClassNotFoundException if Server's response is not a valid Request object.
     */
    private void attemptLogin() throws IOException, ClassNotFoundException {
        NetworkManager.ipAddress = ipAddress;
        NetworkManager.port = port;
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

    private void SetServerAddress() {
        ipAddress = ipAddressParam.getText().replace(" ", "");
        port = Integer.parseInt(portParam.getText().replace(" ", ""));
        ClientConfig.SetIPAddress(ipAddress);
        ClientConfig.SetPort(port);
        ClientConfig.WriteServerAddress();
        JOptionPane.showConfirmDialog(this, "Server Address updated!", "Success", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Detects when the Login button is pressed.
     *
     * @param e Action event e.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource(); // Get the event source
        if (src instanceof JButton) {
            JButton btn = ((JButton) src);
            if (btn == loginButton) {
                try { attemptLogin(); }
                catch (Exception ex) { ex.printStackTrace(); }
            } else if (btn == applyButton) {
                SetServerAddress();
            }
        }
    }

    /**
     * Detects when the text fields are clicked, i.e.,
     * when they become the 'focus'.
     *
     * @param e Focus event e.
     */
    @Override
    public void focusGained(FocusEvent e) {
        Object src = e.getSource();
        if (src instanceof JTabbedPane) {
            jframe.getContentPane().requestFocusInWindow();
        }
        if (src instanceof JTextField) {
            JTextField txt = ((JTextField) src);
            if (txt == usernameField && usernameField.getText().equals(usernameText)) {
                usernameField.setText("");
            } else if (txt == passwordField && passwordField.getText().equals(passwordText)) {
                passwordField.setText("");
            } else if (txt == portParam && portParam.getText().equals(" " + port)) {
                portParam.setText("");
            } else if (txt == ipAddressParam && ipAddressParam.getText().equals(" " + ipAddress)) {
                ipAddressParam.setText("");
            }
        }
    }

    /**
     * Detects when something other than the currently selected
     * text field is selected, i.e., when the text fields lose
     * 'focus'.
     * @param e The focus event e.
     */
    @Override
    public void focusLost(FocusEvent e) {
        Object src = e.getSource();
        if (src instanceof JTextField) {
            JTextField txt = ((JTextField) src);
            if (txt == usernameField && usernameField.getText().equals("")) {
                usernameField.setText(usernameText);
            } else if (txt == passwordField && passwordField.getText().equals("")) {
                passwordField.setText(passwordText);
            } else if (txt == ipAddressParam && ipAddressParam.getText().equals("")) {
                ipAddressParam.setText(" " + ipAddress);
            } else if (txt == portParam && portParam.getText().equals("")) {
                portParam.setText(" " + port);
            }
        }
    }

    /**
     * Method to terminate the Login GUI.
     */
    public static void terminate() {
        jframe.setVisible(false);
        jframe.dispose();
    }
}
