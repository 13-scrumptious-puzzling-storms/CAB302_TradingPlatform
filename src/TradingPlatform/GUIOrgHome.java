package TradingPlatform;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static TradingPlatform.GUIMain.tabHeight;
import static TradingPlatform.GUIMain.tabWidth;
import static TradingPlatform.GUIMain.cust1;
import static TradingPlatform.GUIMain.cust2;
import static TradingPlatform.GUIMain.cust3;
import static TradingPlatform.GUIMain.FONT;


public class GUIOrgHome{
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClientApp.launchProgram(2);
    }

    public String BuyHeading[] = {"Buy Orders","Quantity", "Price",};
    public String SellHeading[] = {"Sell Orders","Quantity","Price"};
    public String AssetHeading[] = {"Asset Item","Quantity"};

    private User user;
    OrganisationalUnit organisationalUnit;
    int organisationalUnitID;
    int credits;
    String orgName;


    public GUIOrgHome(JPanel OrgHomeTab, User user) throws IOException, ClassNotFoundException {
        this.user = user;
        this.organisationalUnit = user.getOrganisationalUnit();
        this.organisationalUnitID = organisationalUnit.getID();
        this.orgName = organisationalUnit.getName(organisationalUnitID);
        this.credits = organisationalUnit.getCredits(organisationalUnitID);
        orgHomePanel(OrgHomeTab);
    }

    public void orgHomePanel(JPanel panel2) throws IOException, ClassNotFoundException {

        JTabbedPane tradesAssets = new JTabbedPane();
        tradesAssets.setBackground(cust3);
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        //Retrieve trades buy and sell tables for organisational unit
        JTable sellTable = GUIMain.constructTable(TradeManager.getSellOrders(organisationalUnitID),SellHeading);
        JScrollPane TradesPaneSell = GUIMain.tablePane(sellTable);
        JTable buyTable = GUIMain.constructTable(TradeManager.getBuyOrders(organisationalUnitID), BuyHeading);
        JScrollPane TradesPaneBuy = GUIMain.tablePane(buyTable);

        //Set up Trades tables in Trades tab
        JSplitPane tablesPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TradesPaneSell, TradesPaneBuy);
        tablesPane.setDividerLocation(tabWidth/2);
        tablesPane.setResizeWeight(0.5);

        //JPanel AssetsPanel = new JPanel();
        JScrollPane Assets = GUIMain.tablePane(GUIMain.constructTable(OrganisationAsset.getOrganisationalUnitAssetTable(organisationalUnitID), AssetHeading));

        JButton removeButton = removeButton(panel2, position);
        JButton buyButton = buyAssetButton(panel2, position);
        JButton sellButton = sellAssetButton(panel2, position);
        creditsLabel(panel2, position);

        //Organisation name label
        String name = "Organisation: " + orgName;
        JLabel orgName = new JLabel(name);
        orgName.setForeground(Color.white);
        orgName.setFont(new Font(FONT, Font.PLAIN, 18));
        position.gridx = 1;
        position.gridy = 2;
        position.anchor = GridBagConstraints.LINE_START;
        panel2.add(orgName, position);

        //setup of tradeAssets JTabbed pane
        position.insets = new Insets(40, 0, 20, 0);
        position.gridwidth = 3;
        position.gridx = 1;
        position.gridy = 4;
        panel2.add(tradesAssets, position);
        tradesAssets.add("Trades", tablesPane);
        tradesAssets.add("Assets", Assets);
        tradesAssets.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
                int selectedTab = tabbedPane.getSelectedIndex();
                if (selectedTab == 0) {
                    removeButton.setVisible(true);
                    buyButton.setVisible(false);
                    sellButton.setVisible(false);
                }
                else if (selectedTab == 1) {
                    removeButton.setVisible(false);
                    buyButton.setVisible(true);
                    sellButton.setVisible(true);
                }
            }
        });
        tradesAssets.setPreferredSize(new Dimension(tabWidth, tabHeight));
        tradesAssets.setMinimumSize(new Dimension(tabWidth/2, tabHeight/2));
        JScrollPane pageScroll = new JScrollPane();
        pageScroll.add(panel2);
    }

    private JButton removeButton(JPanel panel2, GridBagConstraints position){
        //Create Remove Buy/Sell Button
        position.insets = new Insets(0, 0, 20, 0);
        position.gridx = 1;
        position.gridy = 1;
        position.gridwidth = 3;
        position.anchor = GridBagConstraints.CENTER;
        JButton removeButton = new JButton("Cancel Buy/Sell Order");
        removeButton.setBackground(cust1);
        panel2.add(removeButton, position);
        removeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // display buy popup
                System.out.println("Just pressed the remove button");
            }
        });
        return removeButton;
    }

    private JButton buyAssetButton(JPanel panel2, GridBagConstraints position){
        //Create Buy Asset Button
        position.gridwidth = 1;
        position.gridx = 2;
        position.gridy = 1;
        position.anchor = GridBagConstraints.LINE_START;
        JButton buyButton = new JButton("Buy Assets");
        buyButton.setBackground(cust1);
        panel2.add(buyButton, position);
        buyButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // display buy popup
                System.out.println("Just pressed the buy button");
            }
        });
        buyButton.setVisible(false);
        return buyButton;
    }

    private JButton sellAssetButton(JPanel panel2, GridBagConstraints position){
        //Create Sell Asset Button
        position.gridx = 3;
        position.gridy = 1;
        position.anchor = GridBagConstraints.CENTER;
        JButton sellButton = new JButton("Sell Assets");
        sellButton.setBackground(cust1);
        panel2.add(sellButton, position);
        sellButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // display sell popup
                System.out.println("Just pressed the sell button");
            }
        });
        sellButton.setVisible(false);
        return sellButton;
    }

    private void creditsLabel(JPanel panel2, GridBagConstraints position){
        //Credits Label
        String creditsLabel = "Credits: " + String.valueOf(credits);
        JLabel credits = new JLabel(creditsLabel);
        credits.setForeground(Color.white);
        credits.setFont(new Font(FONT, Font.PLAIN, 18));
        position.gridx = 3;
        position.gridy = 2;
        position.anchor = GridBagConstraints.LINE_END;
        panel2.add(credits, position);
    }

}
