package TradingPlatform;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import static TradingPlatform.GUIMain.*;

/**
 * Constructs components to creates organisationalHome tab of the GUI.
 * Allows users to buy and sell assets, view orders made by their organisation
 * and cancel orders made by their organisational unit.
 */
public class GUIOrgHome{


    public static String BuyHeading[] = {"Buy Orders","Quantity","Remaining Quantity", "Price",};
    public static String[] SellHeading = {"Sell Orders","Quantity","Remaining Quantity", "Price"};
    public static String[] AssetHeading = {"Asset Item","Quantity"};

    //User information
    private static User user;
    OrganisationalUnit organisationalUnit;
    static int organisationalUnitID;
    int credits;
    String orgName;

    //Pane information
    JTabbedPane tradesAssets;
    JSplitPane tablesPane;

    //buy table variables
    public static DefaultTableModel buyTableModel;
    JScrollPane TradesPaneBuy;
    public static JTable buyTable;
    static String[] tradeIDBuy;
    static String[][] buyData;

    //sell table variables
    public static DefaultTableModel sellTableModel;
    JScrollPane TradesPaneSell;
    public static JTable sellTable;
    static String[] tradeIDSell;
    static String[][] sellData;

    //Credits label
    public static JLabel LabelCredits;

    //asset table variables
    public static DefaultTableModel assetTableModel;
    public static JTable assetTable;
    JScrollPane Assets;
    static String[] OrgAssetID;
    static String[][] AssetItemQuantity;


    /**
     * GUIOrg home constructor, adding the organisational home tab to pane
     * @param OrgHomeTab Tab which to add organisational home contents
     * @param user User that belongs to the organisational unit
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public GUIOrgHome(JPanel OrgHomeTab, User user) throws IOException, ClassNotFoundException {
        this.user = user;
        this.organisationalUnit = user.getOrganisationalUnit();
        this.organisationalUnitID = organisationalUnit.getID();
        this.orgName = organisationalUnit.getName(organisationalUnitID);
        this.credits = organisationalUnit.getCredits(organisationalUnitID);
        orgHomePanel(OrgHomeTab);
    }

    /**
     * Main method for adding components to Tab (buttons, tables, labels)
     * @param panel2 panel to add all orgHome components to
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void orgHomePanel(JPanel panel2) throws IOException, ClassNotFoundException {

        tradesAssets = new JTabbedPane();
        tradesAssets.setBackground(cust3);
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        //Setup buy order table
        buyTableModel = constructBuyTableModel();
        buyTable = tableCreator(buyTableModel);
        TradesPaneBuy = GUIMain.tablePane(buyTable);

        //Setup sell order table
        sellTableModel = constructSellTableModel();
        sellTable = tableCreator(sellTableModel);
        TradesPaneSell = tablePane(sellTable);

        //Set up Trades tables in Trades tab
        tablesPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, TradesPaneSell, TradesPaneBuy);
        tablesPane.setDividerLocation(tabWidth/2);
        tablesPane.setResizeWeight(0.5);

        //Setup asset table
        assetTableModel = constructAssetTableModel();
        assetTable = tableCreator(assetTableModel);
        Assets = GUIMain.tablePane(assetTable);

        //create buttons
        JButton removeBuyOrderButton = removeBuyOrderButton(panel2, position, buyTable);
        JButton removeSellOrderButton = removeSellOrderButton(panel2, position, sellTable);
        JButton buyButton = buyAssetButton(panel2, position);
        JButton sellButton = sellAssetButton(panel2, position);
        creditsLabel(panel2, position);

        //Organisation name label
        String name = "Organisation: " + orgName;
        JLabel orgName = new JLabel(name);
        orgName.setForeground(Color.white);
        orgName.setFont(new Font(FONT, Font.PLAIN, 18));
        position.insets = new Insets(0, 0, 0, 0);
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

    /**
     * Button which removes selected buy order from table. Subsequently updates
     * organisational unit credits
     * @param panel2 panel to add all orgHome components to
     * @param position position variable to set
     * @param table buy order table
     * @return
     */
    private JButton removeBuyOrderButton(JPanel panel2, GridBagConstraints position, JTable table){
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
                    int result = JOptionPane.showConfirmDialog(null,"Cancel trade buy order of "+buyData[selectedRow][1]+" "+buyData[selectedRow][0]+"?", "Cancel trade",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if(result == JOptionPane.YES_OPTION){
                        try {
                            TradeManager.setCancel(Integer.valueOf(tradeIDBuy[selectedRow])); //cancel order

                            //restore credits (add remaining quantity*price)
                            int newCredits = credits + Integer.valueOf(buyData[selectedRow][2])*Integer.valueOf(buyData[selectedRow][3]);
                            OrganisationalUnit.UpdateOrganisationalUnitCredits(organisationalUnitID, newCredits);

                            //update buy order table
                            buyTableModel = constructBuyTableModel();
                            buyTable.setModel(buyTableModel);

                            //update credits label
                            LabelCredits.setText("Credits: " + Integer.valueOf(newCredits));

                        } catch (Exception m) {
                            m.printStackTrace();
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "Trade cancellation unsuccessful");
                    }
                }
            }
        });
        return removeButton;
    }

    /**
     * Button which removes selected row from sell order table. Subsequently
     * updates the asset table and asset quantity
     * @param panel2 panel to add orgHome components to
     * @param position position variable to set
     * @param table sell order table
     * @return button which allows user to cancel sell order of organisational unit
     */
    private JButton removeSellOrderButton(JPanel panel2, GridBagConstraints position, JTable table){
        //Create Remove Buy/Sell Button
        position.insets = new Insets(0, 80, 20, 0);
        position.gridx = 2;
        position.gridy = 1;
        position.gridwidth = 1;
        position.anchor = GridBagConstraints.LINE_START;
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
                    int result = JOptionPane.showConfirmDialog(null,"Cancel trade sell order of "+sellData[selectedRow][1]+" "+sellData[selectedRow][0]+"?", "Cancel trade",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if(result == JOptionPane.YES_OPTION){
                        System.out.print("Trade cancelled");
                        try {
                            int tradeId = Integer.valueOf(tradeIDSell[selectedRow]);
                            TradeManager.setCancel(tradeId); //cancel order

                            // Get the org asset id from the trade id
                            int orgAssetId = TradeManager.getOrganisationAssetId(tradeId);

                            //Calculate new Asset Quantity
                            //get current asset quantity for organisationAssetID
                            int oldQuantity = OrganisationAsset.getQuantity(orgAssetId);
                            int newQuantity = oldQuantity + Integer.valueOf(sellData[selectedRow][2]); //add remaining quantity

                            //restore the remaining quantity of the asset
                            OrganisationAsset.updateOrganisationalUnitAssetQuantity(orgAssetId, newQuantity);

                            //Update Sell Order Table
                            sellTableModel = constructSellTableModel();
                            sellTable.setModel(sellTableModel);

                            //update assets table
                            assetTableModel = constructAssetTableModel();
                            assetTable.setModel(assetTableModel);

                        } catch (Exception m) {
                            m.printStackTrace();
                        }

                    }else {
                        JOptionPane.showMessageDialog(null, "Trade cancellation unsuccessful");
                    }
                }
            }
        });
        return removeButton;
    }

    /**
     * Buy asset button calls popup box that allows user to place a
     * buy order on behalf of their organisational unit
     * @param panel2 panel to add orgHome components to
     * @param position position variable to set
     * @return buy button which allows user to buy assets
     */
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
                try {
                    GUIOrder order = new GUIOrder(user);
                    order.popup(false);

                } catch (Exception m) {
                    m.printStackTrace();
                }
            }
        });
        buyButton.setVisible(false);
        return buyButton;
    }

    /**
     * Sell asset button calls popup box that allows user to place sell order
     * on behalf of their organisational unit
     * @param panel2 panel to add orgHome components to
     * @param position position variable to set
     * @return sell button which allows user to sell assets
     */
    private JButton sellAssetButton(JPanel panel2, GridBagConstraints position){
        //Create Sell Asset Button
        position.gridx = 3;
        position.gridy = 1;
        position.anchor = GridBagConstraints.CENTER;
        JButton sellButton = new JButton("Sell Assets");
        sellButton.setBackground(cust1);
        panel2.add(sellButton, position);
        sellButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // display sell popup
                try {
                    GUIOrder order = new GUIOrder(user);
                    order.popup(true);
                } catch (Exception m) {
                    m.printStackTrace();
                }
            }
        });
        sellButton.setVisible(false);
        return sellButton;
    }

    /**
     * Displays number of credits owned by the organisational unit the user
     * belong to
     * @param panel2 panel to add orgHome components to
     * @param position position variable to set
     */
    public void creditsLabel(JPanel panel2, GridBagConstraints position){
        //Credits Label
        String creditsLabel = "Credits: " + String.valueOf(credits);
        LabelCredits = new JLabel(creditsLabel);
        LabelCredits.setForeground(Color.white);
        LabelCredits.setFont(new Font(FONT, Font.PLAIN, 18));
        position.gridx = 3;
        position.gridy = 2;
        position.anchor = GridBagConstraints.LINE_END;
        panel2.add(LabelCredits, position);
    }

    /**
     * constructs buy order table model using information retrieved from
     * the database
     * @return buy order table model
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static DefaultTableModel constructBuyTableModel() throws IOException, ClassNotFoundException {
        //Retrieve trades buy table for organisational unit
        String[][] tradesBuy = TradeManager.getBuyOrders(organisationalUnitID);
        int buySize = tradesBuy.length;
        tradeIDBuy = new String[buySize]; //array that stores organisationAssetID's for buy orders
        buyData = new String[buySize][]; //array that stores data to be displayed in buyTrades table
        try {
            for (int i = 0; i < buySize; i++) {
                if(tradesBuy[i]!= null) {
                    tradeIDBuy[i] = tradesBuy[i][0];
                    String[] buy = new String[4]; //temporary array
                    buy[0] = tradesBuy[i][1];
                    buy[1] = tradesBuy[i][2];
                    buy[2] = tradesBuy[i][3];
                    buy[3] = tradesBuy[i][4];
                    buyData[i] = buy;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return GUIMain.constructTable(buyData,BuyHeading);
    }

    /**
     * constructs sell order table model using information retrieved from
     * the database
     * @return sell order table model
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static DefaultTableModel constructSellTableModel() throws IOException, ClassNotFoundException {
        //Retrieve trades sell table for organisational unit
        String[][] tradesSell = TradeManager.getSellOrders(organisationalUnitID);
        int sellSize = tradesSell.length;
        tradeIDSell = new String[sellSize]; //array that stores organisationAssetID's for sell orders
        sellData = new String[sellSize][]; //array that stores data to be displayed in sellTrades table
        try {
            for (int i = 0; i < sellSize; i++) {
                if (tradesSell[i]!= null) {
                    tradeIDSell[i] = tradesSell[i][0];
                    String[] sell = new String[4]; //temporary array
                    sell[0] = tradesSell[i][1];
                    sell[1] = tradesSell[i][2];
                    sell[2] = tradesSell[i][3];
                    sell[3] = tradesSell[i][4];

                    sellData[i] = sell;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return GUIMain.constructTable(sellData, SellHeading);
    }

    /**
     * constructs asset table model using information retrieved from
     * the database
     * @return asset table model of assets belonging to the organisational unit
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static DefaultTableModel constructAssetTableModel() throws IOException, ClassNotFoundException {
        //JPanel AssetsPanel = new JPanel();
        String[][] OrgAssets = OrganisationAsset.getOrganisationalUnitAssetTable(organisationalUnitID);
        int size = OrgAssets.length;
        OrgAssetID = new String[size]; //array that stores OrganisationAssetID's for given array
        AssetItemQuantity = new String[size][]; //array that stores AssetItem and AssetQuantity
        for(int i = 0; i<size; i++){
            OrgAssetID[i] = OrgAssets[i][0];
            String[] assets = new String[2]; //temporary array
            assets[0] = OrgAssets[i][1];
            assets[1] = OrgAssets[i][2];
            AssetItemQuantity[i] = assets;
        }
        return GUIMain.constructTable(AssetItemQuantity, AssetHeading);
    }

}
