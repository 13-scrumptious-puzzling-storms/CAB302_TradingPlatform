package TradingPlatform;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static TradingPlatform.GUIMain.*;
import static TradingPlatform.GUIOrgHome.*;
import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.LINE_END;

/**
 * Constructs the Buy/Sell Trade Order popup as a JOptionPane.
 * From this, the user is able to create a new trade order for their organisational unit.
 */
public class GUIOrder extends JFrame{

    private User user;
    private OrganisationalUnit organisationalUnit;

    private JComboBox itemNameInput;
    private JTextField quantityInput;
    private JTextField priceInput;
    private JLabel itemName;
    private JLabel quantity;
    private JLabel price;

    private JLabel order;

    private JOptionPane popUp;
    private JPanel buy;
    private JPanel panel;

    private volatile JTable BuyOrderTable;
    private volatile JTable SellOrderTable;

    String AssetBuyHeading[] = {"Buy Price", "Quantity"};
    String AssetSellHeading[] = {"Sell Price", "Quantity"};

    /**
     * GUIOrder constructor, setting the user and organisational unit
     * @param user The current user
     */
    public GUIOrder(User user) {
        this.user = user;
        organisationalUnit = user.getOrganisationalUnit();
    }

    /**
     * The main method for calling the buy/sell popup. Other classes will call this when they need a buy/sell popup.
     * @param isSell A Boolean determining if the order is a Sell (true) or Buy (false)
     */
    public void popup(Boolean isSell)  {
        buy = new JPanel();
        buy.setSize(new Dimension(width/2, height/2));
        popUp = new JOptionPane();

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(width - width/3, 300));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        if(isSell){
            order = new JLabel("CREATE NEW SELL ORDER");
        }
        else{
            order = new JLabel("CREATE NEW BUY ORDER");
        }

        itemName = new JLabel("Item Name: ");
        quantity = new JLabel("Quantity: ");
        price = new JLabel("Price (per unit): ");

        order.setFont(new Font("Verdana", Font.BOLD, 30));

        String[] data = AssetType.getAllAssetNames();
        itemNameInput = new JComboBox(data);
        itemNameInput.setPreferredSize(new Dimension(width/4, 20));
        quantityInput = new JTextField();
        quantityInput.setPreferredSize(new Dimension(width/4, 20));
        quantityInput.setEditable(true);
        quantityInput.setEnabled(true);
        priceInput = new JTextField();
        priceInput.setPreferredSize(new Dimension(width/4, 20));
        priceInput.setEditable(true);
        priceInput.setEnabled(true);

        position.weightx = 1;
        position.weighty = 1;
        position.gridx = 1;
        position.gridy = 0;
        position.gridwidth = 3;
        panel.add(order, position);
        position.gridwidth = 1;
        position.insets = new Insets(20, 0, 20, 10);
        position.anchor = LINE_END;
        position.gridx = 1;
        position.gridy = 1;
        panel.add(itemName, position);
        position.gridy = 2;
        panel.add(quantity, position);
        position.gridy = 3;
        panel.add(price, position);
        position.insets = new Insets(20, 0, 20, 0);
        position.anchor = CENTER;
        position.gridx = 2;
        position.gridy = 1;
        panel.add(itemNameInput, position);
        position.gridy = 2;
        panel.add(quantityInput, position);
        position.gridy = 3;
        panel.add(priceInput, position);

        var assetName = itemNameInput.getSelectedItem();
        AssetType asset = new AssetType(assetName.toString());
        int assetId = asset.getAssetId();
        itemNameInput.addActionListener(e -> RefreshContent());

        BuyOrderTable = tableCreator(GUIMain.constructTable(TradeManager.getCurrentBuyOrdersPriceAndQuantityForAsset(assetId), AssetBuyHeading));
        JScrollPane RecentBuy = GUIMain.tablePane(BuyOrderTable);
        RecentBuy.setPreferredSize(new Dimension(tabWidth/6, 100));
        RecentBuy.setMinimumSize(new Dimension(tabWidth/10, 50));

        SellOrderTable = tableCreator(GUIMain.constructTable(TradeManager.getCurrentSellOrdersPriceAndQuantityForAsset(assetId), AssetSellHeading));
        JScrollPane RecentSell = GUIMain.tablePane(SellOrderTable);
        RecentSell.setPreferredSize(new Dimension(tabWidth/6, 100));
        RecentSell.setMinimumSize(new Dimension(tabWidth/10, 50));

        position.insets = new Insets(0, 0, 0, 0);
        position.gridx = 3;
        position.gridy = 1;
        position.gridheight = 2;
        panel.add(RecentBuy, position);
        position.gridy = 3;
        panel.add(RecentSell, position);


        buy.setPreferredSize(new Dimension(200, 200));

        if (JOptionPane.showInternalConfirmDialog(null, panel, "" , JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
            updateOrder(isSell);
        }

    }

    /**
     * Refreshes the data in the buy and sell tables on the popup.
     */
    private void RefreshContent(){
        try {
            var assetName = itemNameInput.getSelectedItem();
            AssetType asset = new AssetType(assetName.toString());
            int assetId = asset.getAssetId();
            var dataModel1 = GUIMain.constructTable(TradeManager.getCurrentBuyOrdersPriceAndQuantityForAsset(assetId), AssetBuyHeading);
            BuyOrderTable.setModel(dataModel1);
            var dataModel2 = GUIMain.constructTable(TradeManager.getCurrentSellOrdersPriceAndQuantityForAsset(assetId), AssetSellHeading);
            SellOrderTable.setModel(dataModel2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * UpdateOrder handles the trade order. It makes sure all values are valid and calls warning messages if it is not.
     * It then calls the functions to add the new buy or sell trade to the database.
     * @param isSell A Boolean determining if the order is a Sell (true) or Buy (false)
     */
    private void updateOrder(Boolean isSell){

        if (quantityInput.getText() != null && !quantityInput.getText().equals("")) {
            int quantity;
            try {
                quantity = Integer.parseInt(quantityInput.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(panel, quantityInput.getText() + " is not a valid amount. " +
                                "Please type in a whole number.",
                        "Edit Quantity", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int price;
            try {
                price = Integer.parseInt(priceInput.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(panel, priceInput.getText() + " is not a valid credit amount. " +
                                "Please type in a whole number.",
                        "Edit Credits", JOptionPane.ERROR_MESSAGE);
                return;
            }
            var asset = itemNameInput.getSelectedItem();


            AssetType buyAsset = new AssetType(asset.toString());
            int orgID = organisationalUnit.getID();
            int orgAssetId = OrganisationAsset.getOrganisationAssetID(organisationalUnit, buyAsset);


            Trade order = new Trade(isSell, buyAsset, quantity, price, orgID);

            if(isSell){
                int currentQuantity = OrganisationAsset.getQuantity(orgAssetId);
                if( currentQuantity >= quantity){
                    OrganisationAsset.updateOrganisationalUnitAssetQuantity(orgAssetId, currentQuantity - quantity);
                    order.addTradeOrder(orgAssetId, quantity, isSell, price);
                    sellTableModel = GUIOrgHome.constructSellTableModel();
                    sellTable.setModel(sellTableModel);
                    assetTableModel = GUIOrgHome.constructAssetTableModel();
                    assetTable.setModel(assetTableModel);
                    JOptionPane.showMessageDialog(panel, "New SELL order for " + quantity + " " + asset + " at " + price + " credit(s)!",
                            "Order Complete", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    if(currentQuantity == -1){
                        currentQuantity = 0;
                    }
                    JOptionPane.showMessageDialog(panel, "Insufficient quantity!\nOrganisational Unit : " + organisationalUnit.getName() + " has " + currentQuantity + " " + asset + "s.\nYou attempted to sell " + quantity + " unit(s)!",
                            "Edit Quantity", JOptionPane.WARNING_MESSAGE);
                }
            }else{
                int currentCredits = organisationalUnit.organisationCredit;
                if(currentCredits >= price*quantity){
                    organisationalUnit.UpdateOrganisationalUnitCredits(orgID, currentCredits - price*quantity);
                    if(orgAssetId < 0){
                        orgAssetId = OrganisationAsset.addOrganisationAsset(orgID, buyAsset.getAssetId(), 0);
                    }
                    order.addTradeOrder(orgAssetId, quantity, isSell, price);
                    buyTableModel = GUIOrgHome.constructBuyTableModel();
                    buyTable.setModel(buyTableModel);
                    GUIOrgHome.LabelCredits.setText("Credits: " + String.valueOf(currentCredits - price*quantity));
                    JOptionPane.showMessageDialog(panel, "New BUY order for " + quantity + " " + asset + " at " + price + " credit(s)!",
                            "Order Complete", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    JOptionPane.showMessageDialog(panel, "Insufficient funds!\nOrganisational Unit : " + organisationalUnit.getName() + " has " + currentCredits + " credits.\nYou attempted to use " + price*quantity + " credit(s)!",
                            "Edit Credits", JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Please enter all details of your order",
                    "Edit Details", JOptionPane.WARNING_MESSAGE);
        }
    }
}
