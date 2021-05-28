package TradingPlatform.NetworkProtocol;

import java.io.*;

public class ServerConfig {
    private static final int PROP_FILE_LINES = 4;
    private static final String GET_DEFAULT_PROPS_FILE = "./default_db.txt";
    private static final String DEFAULT_PROPS_FILE = "./db.props";

    public static Boolean defaultPropsExists = false;

    private static File propsFile;
    private static String propsURL;
    private static String propsSchema;
    private static String propsUser;
    private static String propsPass;

    // get rid of throws IOException when you remove writePropsFIle
    public ServerConfig() throws IOException {
        InitialiseConfig();
    }

    // Getters & Setters
    public static void setPropsFile(String path) { propsFile = new File(path); }
    public static File getPropsFile() { return propsFile; }

    public static void setPropsURL(String url) { propsURL = url; }
    public static String getPropsURL() { return propsURL; }

    public static void setPropsSchema(String schema) { propsSchema = schema; }
    public static String getPropsSchema() { return propsSchema; }

    public static void setPropsUser(String user) { propsUser = user; }
    public static String getPropsUser() { return propsUser; }

    public static void setPropsPass(String pass) { propsPass = pass; }
    public static String getPropsPass() { return propsPass; }

    public static void setDefaultPropsFile(String defaultPropsFile) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(GET_DEFAULT_PROPS_FILE));){
            bufferedWriter.write(defaultPropsFile);
            setPropsFile(defaultPropsFile);
        }
    }

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

    private static void readFailed() {
        setPropsFile("");
        setPropsURL("");
        setPropsSchema("");
        setPropsUser("");
        setPropsPass("");
    }

    /**private static void readFailed() {
        setPropsFile("");
        setPropsURL("");
        setPropsSchema("");
        setPropsUser("");
        setPropsPass("");
    }**/

    public static void writePropsFile() throws IOException {
        String propsFileData = "jdbc.url=" + propsURL + "\n" +
                "jdbc.schema=" + propsSchema + "\n" +
                "jdbc.username=" + propsUser + "\n" +
                "jdbc.password=" + propsPass;
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(propsFile))) {
            bufferedWriter.write(propsFileData);
        }
    }

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
