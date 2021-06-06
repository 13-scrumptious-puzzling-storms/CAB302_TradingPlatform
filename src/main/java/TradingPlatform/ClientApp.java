package TradingPlatform;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Responsible for the Client application and its core processes.
 */
public class ClientApp {

    public static NetworkManager networkManager;
    private static GUILogin guiLogin;
    private static GUIMain guiMain;

    public static Boolean loggedIn;

    /**
     * Starts Client main method.
     * @param args
     */
    public static void main(String[] args) {
        StartClient();
    }

    /**
     * Starts the Client by:
     * Starting the NetworkManager to send Client's
     * requests and receive replies from Server.
     * Starting the Login GUI (GUILogin) for user authentication.
     */
    private static void StartClient() {
        loggedIn = false;

        // Initialise the client-side network protocol
        networkManager = new NetworkManager();
        Thread networkThread = new Thread(networkManager);
        networkThread.start();

        // Initialise the login GUI and get user.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                guiLogin = new GUILogin();
            }
        });
    }

    /**
     * Launches the main Client application after successful
     * user authentication. Main Client application will be
     * loaded using the privileges of the user.
     * @param userID the authenticated user.
     */
    public static void launchProgram(int userID) {
        loggedIn = true;
        User user = new User(userID);
        guiLogin.terminate();
        try { guiMain = new GUIMain(user); }
        catch (Exception ex) { ex.printStackTrace(); }
    }

    /**
     * A public method allowing any GUI class to display a custom
     * error (errorMessage) to the user using a JOptionPane.
     * @param errorMessage the errorMessage to display.
     */
    public static void displayError(String errorMessage) {
        Component parentComponent;
        if (loggedIn) {
            parentComponent = guiLogin;
        } else {
            parentComponent = guiMain;
        }
        JOptionPane.showConfirmDialog(parentComponent, errorMessage, "Warning", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE);
    }
}
