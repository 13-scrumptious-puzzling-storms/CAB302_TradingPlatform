package TradingPlatform;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static TradingPlatform.GUIMain.tabHeight;
import static TradingPlatform.GUIMain.tabWidth;
import static TradingPlatform.GUIMain.cust1;
import static TradingPlatform.GUIMain.cust2;
import static TradingPlatform.GUIMain.cust3;


public class GUIOrgHome{

    public String BuyHeading[] = {"Buy Orders","Price","Quantity"};
    public String SellHeading[] = {"Sell Orders","Price","Quantity"};
    public String AssetHeading[] = {"Asset Item","Quantity", "Buy", "Sell"};

    //Temp data stuff
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

    int credits = 2342;
    String orgName = "Organisational Unit Name";


    public GUIOrgHome(JPanel OrgHomeTab){
        orgHomePanel(OrgHomeTab);
    }

    public void orgHomePanel(JPanel panel2){
        JTabbedPane tradesAssets = new JTabbedPane();
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        //Retrieve trades buy and sell tables for organisational unit
        JScrollPane TradesPaneSell = GUIMain.constructTable(data,SellHeading );
        JScrollPane TradesPaneBuy = GUIMain.constructTable(data, BuyHeading);


        //Set up Trades tables in Trades tab
        JSplitPane tablesPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TradesPaneSell, TradesPaneBuy);
        tablesPane.setDividerLocation(tabWidth/2);
        tablesPane.setResizeWeight(0.5);

        //JPanel AssetsPanel = new JPanel();
        JScrollPane Assets = GUIMain.constructTable(data, AssetHeading);

        //Create Remove Buy/Sell
        //Insets
        position.insets = new Insets(40, 0, 20, 0);
        position.gridx = 1;
        position.gridy = 0;
        position.gridwidth = 3;
        position.anchor = GridBagConstraints.CENTER;
        JButton button = new JButton("Remove Buy/Sell Order");
        button.setBackground(cust1);
        panel2.add(button, position);



        //Credits Label
        String creditsLabel = "Credits: " + String.valueOf(credits);
        JLabel credits = new JLabel(creditsLabel);
        credits.setForeground(Color.white);
        credits.setFont(new Font("Verdana", Font.PLAIN, 18));
        position.gridx = 3;
        position.gridy = 2;
        position.anchor = GridBagConstraints.LINE_END;
        panel2.add(credits, position);

        //Organisation name label
        String name = "Organisation: " + orgName;
        JLabel orgName = new JLabel(name);
        orgName.setForeground(Color.white);
        orgName.setFont(new Font("Verdana", Font.PLAIN, 18));
        position.gridx = 1;
        position.gridy = 2;
        position.anchor = GridBagConstraints.LINE_START;
        panel2.add(orgName, position);


        //Insets
        position.insets = new Insets(40, 0, 20, 0);

        position.gridwidth = 3;
        position.gridx = 1;
        position.gridy = 4;
        panel2.add(tradesAssets, position);
        tradesAssets.add("Trades", tablesPane);
        tradesAssets.add("Assets", Assets);
        tradesAssets.setPreferredSize(new Dimension(tabWidth, tabHeight));
        tradesAssets.setMinimumSize(new Dimension(tabWidth/2, tabHeight/2));
        JScrollPane pageScroll = new JScrollPane();
        pageScroll.add(panel2);
    }

}
