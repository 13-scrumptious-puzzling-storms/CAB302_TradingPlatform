package TradingPlatform.NetworkProtocol;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.regex.Pattern;

public class GUIManager extends JFrame implements ActionListener, Runnable {
    // Screen Sizing
    private static final float SCREEN_RATIO = 2;
    private static final float WINDOW_RATIO = 2;

    // Fonts
    private static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 24);
    private static final Font FONT_HEADER = new Font("SansSerif", 0, 24);
    private static final Font FONT_HEAVY = new Font("SansSerif", Font.BOLD, 16);
    private static final Font FONT_LIGHT = new Font("SansSerif", 0, 16);
    private static final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 12);

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
    private static int tabWidth;
    private static int tabHeight;

    // Dynamic Labels
    private static JLabel statusLabel;
    private static JLabel propsParam;
    private static JLabel schemaParam;
    private static JLabel userParam;
    private static JLabel passParam;

    // Buttons
    private static JButton propsButton;
    private static JButton schemaButton;
    private static JButton userButton;
    private static JButton passButton;
    private static JButton shutdownButton;

    private static ServerConfig serverConfig;

    private static JFrame jframe;
    private static Boolean onStartup;

    // Refer to WIRING FOR GOOD COMMENTS

    @Override
    public void run() {
        try {
            serverConfig = new ServerConfig();
            displayJFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayJFrame() throws IOException {
        // Initialise JFrame
        jframe = new JFrame("SPS Trading SERVER");
        jframe.setDefaultLookAndFeelDecorated(false);
        jframe.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // custom function will shutdown server
        jframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ServerApp.shutdown(); // shutdown server from ServerApp method
            }
        });

        onStartup = !serverConfig.defaultPropsExists;

        // Get and set relative screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        windowWidth = (int)(screenSize.getWidth() / SCREEN_RATIO);
        windowHeight = (int)(screenSize.getHeight() / SCREEN_RATIO);
        tabWidth = (int)(windowWidth / WINDOW_RATIO);
        tabHeight = (int)(windowHeight / WINDOW_RATIO);

        // Set background colour of window
        jframe.getContentPane().setBackground(CELADON_GREEN); //setBackground(cust2);

        // Create console tab
        JPanel consoleTab = new JPanel();
        consoleTab.setBackground(DARK_JUNGLE_GREEN); //consoleTab.setForeground(FLORAL_WHITE);
        consolePanel(consoleTab);
        consoleTab.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        // Create and pane and add the console tab
        UIManager.put("TabbedPane.selected", DARK_JUNGLE_GREEN);
        JTabbedPane pagePane = new JTabbedPane();
        pagePane.add("Console", consoleTab);
        jframe.getContentPane().add(pagePane);
        pagePane.setForeground(WHITE); // Tab text colour //pagePane.setBackground(cust1);

        // Set dimensions and location of window. Display the window
        jframe.setPreferredSize(new Dimension(windowWidth, windowHeight));
        jframe.setLocation(new Point(windowWidth - windowWidth/2, windowHeight - windowHeight/2)); // window position on screen
        jframe.pack();
        jframe.setVisible(true);

        // If ServerConfig failed to load a *.props file
        if (onStartup) {
            UIManager.put("OptionPane.okButtonText", "Select");
            JOptionPane.showMessageDialog(this, "A 'props' file could not be found!\nPlease select a 'props' file ...", "Welcome", JOptionPane.INFORMATION_MESSAGE);
            changeProps();
            update();

        } else {
            testConnection();
        }

        UIManager.put("OptionPane.okButtonText", "Submit");
        UIManager.put("OptionPane.yesButtonText", "Shutdown");
        UIManager.put("OptionPane.noButtonText", "Cancel");
    }

    private void consolePanel(JPanel panel) {
        // Static Labels
        Color staticTextColour = WHITE;
        JLabel serverStatusLabel = newLabel("Server Status", FONT_HEAVY, OCEAN_GREEN);
        JLabel propsLabel = newLabel("Properties Name", FONT_LIGHT, staticTextColour);
        JLabel schemaLabel = newLabel("Database Schema", FONT_LIGHT, staticTextColour);
        JLabel userLabel = newLabel("Database Username", FONT_LIGHT, staticTextColour);
        JLabel passLabel = newLabel("Database Password", FONT_LIGHT, staticTextColour);
        JLabel shutdownLabel = newLabel("Server Control", FONT_HEAVY, OCEAN_GREEN);

        // Create Dynamic Labels - MIGHT ERROR IF CONFIG NOT INITIALISED CORRECTLY
        Color dynamicTextColour = CELADON_GREEN;
        propsParam = newLabel(null, FONT_LIGHT, dynamicTextColour);
        schemaParam = newLabel(null, FONT_LIGHT, dynamicTextColour);
        userParam = newLabel(null, FONT_LIGHT, dynamicTextColour);
        passParam = newLabel(null, FONT_LIGHT, dynamicTextColour);

        statusLabel = newLabel(null, FONT_HEAVY, WHITE);
        statusLabel.setText("UNKNOWN");

        // Set Text of Dynamic Labels
        setDynamicButtons();

        // Buttons
        Color tabColour = DARK_JUNGLE_GREEN;
        propsButton = newButton("Change", FONT_BUTTON, staticTextColour, tabColour);
        schemaButton = newButton("Edit 1", FONT_BUTTON, staticTextColour, tabColour);
        userButton = newButton("Edit 2", FONT_BUTTON, staticTextColour, tabColour);
        passButton = newButton("Edit 3", FONT_BUTTON, staticTextColour, tabColour);
        shutdownButton = newButton("SHUTDOWN", FONT_BUTTON, tabColour, CRIMSON);

        // Panels
        Color panelColour = CHARCOAL;
        JPanel buttonPanel = newPanel(panelColour);
        JPanel emptyPanel01 = newPanel(panelColour);
        JPanel emptyPanel02 = newPanel(panelColour);

        // Button Panel Settings
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setLayout(new GridLayout(0,3, 75, 10));

        // Button Panel Content of Rows
        addToPanel(buttonPanel, new JComponent[] { serverStatusLabel, emptyPanel01, statusLabel });
        addToPanel(buttonPanel, new JComponent[] { propsLabel, propsParam, propsButton });
        addToPanel(buttonPanel, new JComponent[] { schemaLabel, schemaParam, schemaButton });
        addToPanel(buttonPanel, new JComponent[] { userLabel, userParam, userButton });
        addToPanel(buttonPanel, new JComponent[] { passLabel, passParam, passButton });
        addToPanel(buttonPanel, new JComponent[] { shutdownLabel, emptyPanel02, shutdownButton });

        // Add button panel to pane
        panel.add(buttonPanel); //panel.add(buttonPanel, BorderLayout.NORTH);
    }

    private static JLabel newLabel(String text, Font font, Color textColour) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(textColour);
        return label;
    }

    private static void setDynamicButtons() {
        String[] fileSplit = serverConfig.getPropsFile().toString().split(Pattern.quote("\\"));
        String props = fileSplit[fileSplit.length-1];
        String schema = serverConfig.getPropsSchema();
        String user = serverConfig.getPropsUser();
        String pass = serverConfig.getPropsPass();

        String message;
        if (onStartup) {
            message = "Not Found";
        } else {
            message = "Undefined";
        }

        if (props == "" || props.length() < 1) { props = message; }
        if (schema == "" || schema.length() < 1) { schema = message; }
        if (user == "" || user.length() < 1) { user = message; }
        if (pass == "" || pass.length() < 1) { pass = message; }

        propsParam.setText(props);
        schemaParam.setText(schema);
        userParam.setText(user);
        passParam.setText(pass);
    }

    private static void testConnection() {
        DBConnection.setPropsFile(serverConfig.getPropsFile().toString());
        DBConnection.getInstance();
        if (DBConnection.getIsConnected()) {
            onStartup = false;
            statusLabel.setForeground(OCEAN_GREEN);
            statusLabel.setText("CONNECTED");
        } else {
            statusLabel.setForeground(CRIMSON);
            statusLabel.setText("DISCONNECTED");
        }
    }

    private JButton newButton(String text, Font font, Color textColour, Color btnColour) {
        JButton button = new JButton(text);
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
    
    private static void addToPanel(JPanel panel, JComponent[] components) {
        for (int i = 0; i < components.length; i++) {
            panel.add(components[i]);
        }
    }

    private Boolean changeProps() throws IOException {
        JFileChooser fileChooser = new JFileChooser("./");
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            serverConfig.setDefaultPropsFile(fileChooser.getSelectedFile().toString());
            serverConfig.readPropsFile();
            return true;
        } else {
            if (onStartup) {
                ServerApp.shutdown();
            }
            return false;
        }
    }

    private void update() {
        try { serverConfig.writePropsFile(); }
        catch (IOException ioException) { ioException.printStackTrace(); }
        testConnection();
        setDynamicButtons();
        jframe.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource(); // Get the event source
        if (src instanceof JButton) {
            JButton btn = ((JButton) src);
            Boolean modified = false;
            if (btn == propsButton) {
                try { modified = changeProps(); }
                catch (IOException ioException) { ioException.printStackTrace(); }
            } else if (btn == schemaButton) {
                String input = JOptionPane.showInputDialog(this, "New Schema", "Edit Schema", JOptionPane.PLAIN_MESSAGE);
                if (input != null) {
                    modified = true;
                    serverConfig.setPropsSchema(input);
                }
            } else if (btn == userButton) {
                String input = JOptionPane.showInputDialog(this, "New Username", "Edit Username", JOptionPane.PLAIN_MESSAGE);
                if (input != null) {
                    modified = true;
                    serverConfig.setPropsUser(input);
                }
            } else if (btn == passButton) {
                String input = JOptionPane.showInputDialog(this, "New Password", "Edit Password", JOptionPane.PLAIN_MESSAGE);
                if (input != null) {
                    modified = true;
                    serverConfig.setPropsPass(input);
                }
            } else if (btn == shutdownButton) {
                int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to shutdown the server?", "Shutdown", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    ServerApp.shutdown();
                }
            }

            if (modified) {
                update();
            }
        }
    }
}
