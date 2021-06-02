package TradingPlatform;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static TradingPlatform.GUIMain.*;


public class GUIOrgHome{
//    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        ClientApp.launchProgram(2);
//    }

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

        //Retrieve trades buy table for organisational unit
        String[][] tradesBuy = TradeManager.getBuyOrders(organisationalUnitID);
        int buySize = tradesBuy.length;
        String[] tradeIDBuy = new String[buySize]; //array that stores organisationAssetID's for buy orders
        String[][] buyData = new String[buySize][]; //array that stores data to be displayed in buyTrades table
        try {
            for (int i = 0; i < buySize; i++) {
                if(tradesBuy[i]!= null) {
                    tradeIDBuy[i] = tradesBuy[0][i];
                    String[] buy = new String[3]; //temporary array
                    buy[0] = tradesBuy[i][1];
                    buy[1] = tradesBuy[i][2];
                    buy[2] = tradesBuy[i][3];
                    buyData[i] = buy;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        JTable sellTable = GUIMain.constructTable(buyData,SellHeading);
        JScrollPane TradesPaneSell = GUIMain.tablePane(sellTable);

        //Retrieve trades sell table for organisational unit
        String[][] tradesSell = TradeManager.getSellOrders(organisationalUnitID);
        int sellSize = tradesSell.length;
        String[] tradeIDSell = new String[sellSize]; //array that stores organisationAssetID's for sell orders
        String[][] sellData = new String[sellSize][]; //array that stores data to be displayed in sellTrades table
        try {
            for (int i = 0; i < sellSize; i++) {
                if (tradesSell[i]!= null) {
                    tradeIDSell[i] = tradesSell[0][i];
                    String[] sell = new String[3]; //temporary array
                    sell[0] = tradesSell[i][1];
                    sell[1] = tradesSell[i][2];
                    sell[2] = tradesSell[i][3];
                    sellData[i] = sell;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        JTable buyTable = GUIMain.constructTable(sellData, BuyHeading);
        JScrollPane TradesPaneBuy = GUIMain.tablePane(buyTable);

        //Set up Trades tables in Trades tab
        JSplitPane tablesPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TradesPaneSell, TradesPaneBuy);
        tablesPane.setDividerLocation(tabWidth/2);
        tablesPane.setResizeWeight(0.5);

        //JPanel AssetsPanel = new JPanel();
        String[][] OrgAssets = OrganisationAsset.getOrganisationalUnitAssetTable(organisationalUnitID);
        int size = OrgAssets.length;
        String[] OrgAssetID = new String[size]; //array that stores OrganisationAssetID's for given array
        String[][] AssetItemQuantity = new String[size][]; //array that stores AssetItem and AssetQuantity
        for(int i = 0; i<size; i++){
            OrgAssetID[i] = OrgAssets[i][0];
            String[] assets = new String[2]; //temporary array
            assets[0] = OrgAssets[i][1];
            assets[1] = OrgAssets[i][2];
            AssetItemQuantity[i] = assets;
        }
        JTable assetTable = GUIMain.constructTable(AssetItemQuantity, AssetHeading);
        JScrollPane Assets = GUIMain.tablePane(assetTable);

        //create buttons
        JButton removeBuyOrderButton = removeBuyOrderButton(panel2, position, buyTable, tradeIDBuy);
        JButton removeSellOrderButton = removeSellOrderButton(panel2, position, sellTable, tradeIDSell);
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
                    removeBuyOrderButton.setVisible(true);
                    removeSellOrderButton.setVisible(true);
                    buyButton.setVisible(false);
                    sellButton.setVisible(false);
                }
                else if (selectedTab == 1) {
                    removeBuyOrderButton.setVisible(false);
                    removeSellOrderButton.setVisible(false);
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

    private JButton removeBuyOrderButton(JPanel panel2, GridBagConstraints position, JTable table, String[] tradeIDBuy){
        //Create Remove Buy/Sell Button
        position.insets = new Insets(0, 0, 20, 0);
        position.gridx = 3;
        position.gridy = 1;
        position.gridwidth = 1;
        position.anchor = GridBagConstraints.CENTER;
        JButton removeButton = new JButton("Cancel Buy Order");
        removeButton.setBackground(cust1);
        panel2.add(removeButton, position);
        removeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int selectedRow = table.getSelectedRow();
                if(selectedRow == -1){
                    JOptionPane.showMessageDialog(null, "Please select one row from the 'Buy Orders' table to cancel order.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "selected row is: " + String.valueOf(selectedRow) + "with tradeID" + String.valueOf(tradeIDBuy[selectedRow]));
                }
            }
        });
        return removeButton;
    }

    private JButton removeSellOrderButton(JPanel panel2, GridBagConstraints position, JTable table, String[] tradeIDSell){
        //Create Remove Buy/Sell Button
        position.insets = new Insets(0, 0, 20, 0);
        position.gridx = 2;
        position.gridy = 1;
        position.gridwidth = 1;
        position.anchor = GridBagConstraints.LINE_END;
        JButton removeButton = new JButton("Cancel Sell Order");
        removeButton.setBackground(cust1);
        panel2.add(removeButton, position);
        removeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int selectedRow = table.getSelectedRow();
                if(selectedRow == -1){
                    JOptionPane.showMessageDialog(null, "Please select one row from the 'Sell Orders' table to cancel order.");
                }
                else{
                    JOptionPane.showMessageDialog(null, "selected row is: " + String.valueOf(selectedRow) + "with tradeID" + String.valueOf(tradeIDSell[selectedRow]));

                }
            }
        });
        return removeButton;
    }


    private JButton buyAssetButton(JPanel panel2, GridBagConstraints position){
        //Create Buy Asset Button
        position.gridwidth = 1;
        position.gridx = 2;
        position.gridy = 1;
        position.anchor = GridBagConstraints.LINE_END;
        JButton buyButton = new JButton("Buy Assets");
        buyButton.setBackground(cust1);
        panel2.add(buyButton, position);
        buyButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // display buy popup
                System.out.println("Just pressed the buy button");
                try {
                    GUIOrder.buyPopup();
                } catch (Exception m) {
                    m.printStackTrace();
                }
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
