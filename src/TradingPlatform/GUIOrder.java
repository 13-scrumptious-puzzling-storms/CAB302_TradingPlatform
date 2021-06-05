package TradingPlatform;
import TradingPlatform.JDBCDataSources.JDBCAssetType;
import TradingPlatform.JDBCDataSources.JDBCOrganisationalAsset;
import TradingPlatform.JDBCDataSources.JDBCTradeDataSource;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static TradingPlatform.GUIMain.*;
import static TradingPlatform.GUIOrgHome.*;
import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.LINE_END;

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

    public GUIOrder(User user) {
        this.user = user;
        organisationalUnit = user.getOrganisationalUnit();
    }

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

    public void popup(Boolean isSell) throws IOException, ClassNotFoundException {
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

    private void updateOrder(Boolean isSell) throws IOException, ClassNotFoundException {

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
//                    new GUIOrgHome(panel, user).constructSellTableModel();
//                    new GUIOrgHome(panel, user).constructAssetTableModel();
                    sellTableModel = GUIOrgHome.constructSellTableModel();
                    sellTable.setModel(sellTableModel);
                    assetTableModel = GUIOrgHome.constructAssetTableModel();
                    assetTable.setModel(assetTableModel);
                    JOptionPane.showMessageDialog(panel, "New SELL order for " + quantity + " " + asset + " at " + price + " credit(s)!",
                            "Edit Credits", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    if(currentQuantity == -1){
                        currentQuantity = 0;
                    }
                    JOptionPane.showMessageDialog(panel, "Insufficient quantity!\nOrganisational Unit : " + organisationalUnit.getName() + " has " + currentQuantity + " " + asset + "s.\nYou attempted to sell " + quantity + " unit(s)!",
                            "Edit Credits", JOptionPane.WARNING_MESSAGE);
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
//                    //new GUIOrgHome(panel, user).creditsLabel();
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
