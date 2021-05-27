package TradingPlatform;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class GUItester extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 692675871418401803L;

    // Colours
    private static final Color cust1 = new Color(38,139,133);
    private static final Color cust2 = new Color(51,61,68);
    private static final Color cust3 = new Color(72,191,146);
    // Display the window.
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // the screen height
    private static int height = (int)screenSize.getHeight();
    private static int tabHeight = height/2;

    // the screen width
    private static int width = (int)screenSize.getWidth();
    private static int tabWidth = width - (width/3);
    String BuyHeading[] = {"Buy Orders","Price","Quantity"};
    String SellHeading[] = {"Sell Orders","Price","Quantity"};
    String AssetHeading[] = {"Asset ID","Asset Name","Quantity"};

    String data[][] = {{"Vinod","MCA","Computer"},
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

    public GUItester() {
        super("SPS Trading");
        JFrame.setDefaultLookAndFeelDecorated(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane pagePane = new JTabbedPane();

        JPanel homeTab = new JPanel();
        homePanel(homeTab);

        JPanel orgTab = new JPanel();
        orgHomePanel(orgTab);

        JPanel profileTab = new JPanel();
        profilePanel(profileTab);

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

    public void homePanel(JPanel panel){
        JButton buyButton = new JButton("Buy Assets");
        JButton sellButton = new JButton("Sell Assets");
        JPanel buttonPanel = new JPanel();
        JPanel emptyPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,8, 10, 10));
        buttonPanel.add(emptyPanel);
        buttonPanel.add(emptyPanel);
        buttonPanel.add(buyButton);
        buttonPanel.add(emptyPanel);
        buttonPanel.add(sellButton);
        buttonPanel.add(emptyPanel);
        buttonPanel.add(emptyPanel);

        JScrollPane homePanel = new JScrollPane();
        JScrollPane TradesPaneSell = constructTable(data, SellHeading);
        homePanel.setPreferredSize(new Dimension(tabWidth, tabHeight));
        panel.add(new JLabel("Tab 1"));
        panel.setLayout(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(TradesPaneSell, BorderLayout.CENTER);

    }

    public void orgHomePanel(JPanel panel2){
        JTabbedPane tradesAssets = new JTabbedPane();

        //Retrieve trades buy and sell tables for organisational unit
        JScrollPane TradesPaneSell = constructTable(data,SellHeading );
        JScrollPane TradesPaneBuy = constructTable(data, BuyHeading);

        //Set up Trades tables in Trades tab
        JSplitPane tablesPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TradesPaneSell, TradesPaneBuy);
        tablesPane.setDividerLocation(tabWidth/2);
        tablesPane.setResizeWeight(0.5);

        //JPanel AssetsPanel = new JPanel();
        JScrollPane Assets = constructTable(data, AssetHeading);

        panel2.setLayout(new GridBagLayout());
        panel2.add(tradesAssets);
        tradesAssets.add("Trades", tablesPane);
        tradesAssets.add("Assets", Assets);
        tradesAssets.setPreferredSize(new Dimension(tabWidth, tabHeight));
        panel2.add(tradesAssets);
    }

    public JScrollPane constructTable(String[][] data, String[] headingType){
        DefaultTableModel model = new DefaultTableModel(data, headingType);
        JTable sell_buyTable = new JTable(model);
        JScrollPane tradesScrollTable = new JScrollPane(sell_buyTable);
        return tradesScrollTable;
    }


    public void profilePanel(JPanel panel3){
        panel3.add(new JButton("Tab 2"));
    }

}