package TradingPlatform;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class GUIMain extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 692675871418401803L;

    // Colours
    public static final Color cust1 = new Color(38,139,133);
    public static final Color cust2 = new Color(51,61,68);
    public static final Color cust3 = new Color(72,191,146);

    public static Object[] getColours(){
        Object[] colours = new Object[]{cust1, cust2, cust3};
        return colours;
    }
    // Display the window.
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // the screen height
    public static int height = (int)screenSize.getHeight();
    public static int tabHeight = height/2;

    // the screen width
    public static int width = (int)screenSize.getWidth();
    public static int tabWidth = width - (width/3);
    public String BuyHeading[] = {"Buy Orders","Price","Quantity"};
    public String SellHeading[] = {"Sell Orders","Price","Quantity"};
    public String AssetHeading[] = {"Asset ID","Asset Name","Quantity"};

    public String data[][] = {{"Vinod","MCA","Computer"},
            {"Deepak","PGDCA","History"},
            {"Ranjan","M.SC.","Biology"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"},
            {"Radha","BCA","Computer"}};

    public GUIMain() {
        super("SPS Trading");
        JFrame.setDefaultLookAndFeelDecorated(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane pagePane = new JTabbedPane();

        JPanel homeTab = new JPanel();
        new GUIHome(homeTab);

        JPanel orgTab = new JPanel();
        new GUIOrgHome(orgTab);

        JPanel profileTab = new JPanel();
       new GUIProfile(profileTab);

        pagePane.add("Home", homeTab);
        pagePane.add("Organisation Home", orgTab);
        pagePane.add("My Profile", profileTab);
        getContentPane().add(pagePane);


        setPreferredSize(new Dimension(width, height));
        setLocation(new Point(0, 0));
        pack();
        setVisible(true);
        orgTab.setAutoscrolls(true);

        pagePane.setBackground(cust1);
        pagePane.setForeground(Color.WHITE);

        homeTab.setBackground(cust2);
        homeTab.setForeground(Color.WHITE);

        orgTab.setBackground(cust2);
        orgTab.setForeground(Color.LIGHT_GRAY);

        profileTab.setBackground(cust2);
        profileTab.setForeground(Color.LIGHT_GRAY);
        setBackground(cust2);

    }



    public static JScrollPane constructTable(String[][] data, String[] headingType){
        DefaultTableModel model = new DefaultTableModel(data, headingType);
        JTable sell_buyTable = new JTable(model);
        JScrollPane tradesScrollTable = new JScrollPane(sell_buyTable);
        return tradesScrollTable;
    }

}
