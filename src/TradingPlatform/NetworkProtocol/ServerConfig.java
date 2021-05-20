package TradingPlatform.NetworkProtocol;

import java.io.*;

public class ServerConfig {
    private static final int PROP_FILE_LINES = 4;
    private static final File DEFAULT_PROPS_FILE = new File("defaultPropsFile.txt");

    private static Boolean defaultPropsExists;

    private static File propsFile;
    private static String propsURL;
    private static String propsSchema;
    private static String propsUser;
    private static String propsPass;

    // get rid of throwa IOException when you remove writePropsFIle
    public static void main(String[] args) throws IOException {
        if (getDefaultPropsFile()) {
            readPropsFile(); // maybe make this a bool function with a tru catch?
        }

        //setPropsFile("test");
        //propsURL = "testypoo";
        //propsSchema = "yiggy:sdfdsf";
        //propsUser = "ur gay";
        //propsPass = "jdbc:nahhhhh";

        //writePropsFile();
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
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DEFAULT_PROPS_FILE));){
            bufferedWriter.write(defaultPropsFile);
        }
    }

    public static Boolean getDefaultPropsFile() throws IOException {
        if (DEFAULT_PROPS_FILE.exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DEFAULT_PROPS_FILE));){
                setPropsFile(bufferedReader.readLine());
            }
            defaultPropsExists = true;
            return true;
        }
        defaultPropsExists = false;
        return false;
    }

    public static void writePropsFile() throws IOException {
        String propsFileData = "jdbc.url=" + propsURL + "\n" +
                "jdbc.schema=" + propsSchema + "\n" +
                "jdbc.username=" + propsUser + "\n" +
                "jdbc.password=" + propsPass;
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(propsFile));) {
            bufferedWriter.write(propsFileData);
        }
    }

    public static void readPropsFile() throws IOException {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(propsFile));) {
            String[] lines = new String[PROP_FILE_LINES];

            for (int i = 0; i < PROP_FILE_LINES; i++) {
                lines[i] = bufferedReader.readLine();
            }

            setPropsURL(lines[0].split("=")[1]);
            setPropsSchema(lines[1].split("=")[1]);
            setPropsUser(lines[2].split("=")[1]);
            setPropsPass(lines[3].split("=")[1]);
        }
    }
}
