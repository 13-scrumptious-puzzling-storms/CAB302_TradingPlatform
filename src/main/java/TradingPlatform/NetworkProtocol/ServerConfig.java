package TradingPlatform.NetworkProtocol;

import java.io.*;

/**
 * Responsible for the backend of the Server GUI.
 * Reads and writes to user specified .props files.
 * Remembers user's last specified .props file (saves
 * locations of specified .props file to disk in ./default_db.txt)
 */
public class ServerConfig {
    private static final int PROP_FILE_LINES = 4;
    private static final String GET_DEFAULT_PROPS_FILE = "./default_db.txt";
    private static final String TEST_PROPS_FILE = "./db_testdb.props";

    public static Boolean defaultPropsExists = false;

    private static File propsFile;
    private static String propsURL;
    private static String propsSchema;
    private static String propsUser;
    private static String propsPass;

    /**
     * Constructor loads the last specified .props file (if there was one).
     * Otherwise it sets all values to "".
     *
     * @throws IOException if it fails to find the last specified .props file
     * or if it fails to read the specified .props file.
     */
    public ServerConfig() throws IOException {
        InitialiseConfig();
    }

    // Getters & Setters

    /**
     * @param path sets the String propsFile to path.
     */
    public static void setPropsFile(String path) { propsFile = new File(path); }

    /**
     * @return returns the File propsFile.
     */
    public static File getPropsFile() { return propsFile; }

    /**
     * @param url sets the String propsURL to url.
     */
    public static void setPropsURL(String url) { propsURL = url; }

    /**
     * @return returns the String propsURL.
     */
    public static String getPropsURL() { return propsURL; }

    /**
     * @param schema sets the String propsSchema to schema.
     */
    public static void setPropsSchema(String schema) { propsSchema = schema; }

    /**
     * @return returns the String propsSchema.
     */
    public static String getPropsSchema() { return propsSchema; }

    /**
     * @param user sets the String propsUser to user.
     */
    public static void setPropsUser(String user) { propsUser = user; }

    /**
     * @return returns the String propsUser.
     */
    public static String getPropsUser() { return propsUser; }

    /**
     * @param pass sets the String propsPass to pass.
     */
    public static void setPropsPass(String pass) { propsPass = pass; }

    /**
     * @return returns the String propsPass.
     */
    public static String getPropsPass() { return propsPass; }

    /**
     * Sets the String propsFile to defaultPropsFile and writes defaultPropsFile to a .txt
     * file to read on next launch.
     * @param defaultPropsFile the file to open on next launch (saving user preference for .props file).
     * @throws IOException if it fails to write the defaultPropsFile to the default .txt location.
     */
    public static void setDefaultPropsFile(String defaultPropsFile) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(GET_DEFAULT_PROPS_FILE));){
            bufferedWriter.write(defaultPropsFile);
            setPropsFile(defaultPropsFile);
        }
    }

    /**
     * Initialises the fields of ServerConfig. Will attempt to read the last specified
     * .props file (if there is one) and load its values. Otherwise, it will attempt to
     * find and read the default .props file (if it exists). If neither of these occur,
     * the fields will be set to "" values.
     * @throws IOException if it fails to find a specified .props file or fails to read
     * an identified .props file.
     */
    private static void InitialiseConfig() throws IOException {
        if (getDefaultPropsFile()) {
            System.out.println("ServerConfig: Successfully found existing props file.");
            defaultPropsExists = true;
            readPropsFile();
        } else {
            defaultPropsExists = false;
            System.out.println("ServerConfig: Props file not found.");
        }
    }

    /**
     * Attempts to identify a .props file path from a default .txt file.
     * Attempts to read the specified .props file from .txt file (if it exists).
     * Otherwise, it sets the fields to "" values.
     * @return returns whether it successfully identified AND read a .props file.
     * @throws IOException if it fails to open or read the .txt file.
     */
    private static Boolean getDefaultPropsFile() throws IOException {
        if (new File(GET_DEFAULT_PROPS_FILE).exists()) {
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(GET_DEFAULT_PROPS_FILE))) {
                String foundPropsFile = bufferedReader.readLine(); // first line in txt should indicate where to find props file
                if (!(new File(foundPropsFile).exists())) {
                    readFailed();
                    return false;
                } else {
                    setPropsFile(foundPropsFile);
                }
                return true;
            }
        } else {
            readFailed();
            return false;
        }
    }

    /**
     * Sets the fields to "" values.
     */
    private static void readFailed() {
        setPropsFile("");
        setPropsURL("");
        setPropsSchema("");
        setPropsUser("");
        setPropsPass("");
    }

    /**
     * Writes the fields to the loaded .props file.
     * @throws IOException if it fails to open or write to the .props file propsFile.
     */
    public static void writePropsFile() throws IOException {
        String propsFileData = "jdbc.url=" + propsURL + "\n" +
                "jdbc.schema=" + propsSchema + "\n" +
                "jdbc.username=" + propsUser + "\n" +
                "jdbc.password=" + propsPass;
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(propsFile))) {
            bufferedWriter.write(propsFileData);
        }
    }

    public static void testMode() {
        setPropsFile(TEST_PROPS_FILE);
        setPropsURL("sqlite:testdb.db");
        setPropsSchema("");
        setPropsUser("");
        setPropsPass("");
        try { writePropsFile(); }
        catch (IOException e) { e.printStackTrace(); }
        DBConnection.setPropsFile(getPropsFile().toString());
        DBConnection.getInstance();
    }

    /**
     * Reads the existing .props file identified and sets the fields
     * to the values within.
     * @throws IOException if it fails to open or read the .props file.
     */
    public static void readPropsFile() throws IOException {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(propsFile))) {
            String[] lines = new String[PROP_FILE_LINES];

            for (int i = 0; i < PROP_FILE_LINES; i++) {
                lines[i] = bufferedReader.readLine();
            }

            String[] splitString;
            String[] parameters = new String[lines.length];
            for (int i = 0; i < lines.length; i++) {
                splitString = lines[i].split("=");
                if (splitString.length == 2) {
                    parameters[i] = splitString[1];
                } else {
                    parameters[i] = "";
                }
            }

            setPropsURL(parameters[0]);
            setPropsSchema(parameters[1]);
            setPropsUser(parameters[2]);
            setPropsPass(parameters[3]);
        }
    }
}
