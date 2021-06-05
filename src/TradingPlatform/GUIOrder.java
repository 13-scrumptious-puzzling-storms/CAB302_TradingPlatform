package TradingPlatform;
import TradingPlatform.JDBCDataSources.JDBCAssetType;
import TradingPlatform.JDBCDataSources.JDBCOrganisationalAsset;
import TradingPlatform.JDBCDataSources.JDBCTradeDataSource;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static TradingPlatform.GUIMain.*;
import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.LINE_END;

public class GUIOrder extends JFrame{

    // popups
    Popup p;
    private static Popup buyPop;
    private static Popup sellPop;
    private User user;

    private JComboBox itemNameInput;
    private JTextField quantityInput;
    private JTextField priceInput;
    private JLabel itemName;
    private JLabel quantity;
    private JLabel price;

    private JLabel order;

    private JButton confirm;
    private JButton cancel;
    private JOptionPane popUp;
    private JPanel buy;
    private JPanel panel;
    private JFrame sell;

    public GUIOrder(User user) {
        this.user = user;
    }

    public GUIOrder(){

    }


    public void buyPopup() throws IOException, ClassNotFoundException {
        UIManager.put("Popup.border", cust1);
        buy = new JPanel();
        buy.setSize(new Dimension(width/2, height/2));
        popUp = new JOptionPane();

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(width/2, 300));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        itemName = new JLabel("Item Name: ");
        quantity = new JLabel("Quantity: ");
        price = new JLabel("Price: ");

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

        confirm = new JButton("Confirm Order");
        confirm.addActionListener(e -> {
            try {
                finalButton(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        cancel = new JButton("Cancel");
        cancel.addActionListener(e -> {
            try {
                finalButton(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        position.weighty = 0;
        position.insets = new Insets(20, 0, 20, 10);
        position.anchor = LINE_END;
        position.gridx = 0;
        position.gridy = 0;
        panel.add(itemName, position);
        position.gridy = 1;
        panel.add(quantity, position);
        position.gridy = 2;
        panel.add(price, position);
        position.insets = new Insets(20, 0, 20, 0);
        position.anchor = CENTER;
        position.gridx = 1;
        position.gridy = 0;
        panel.add(itemNameInput, position);
        position.gridy = 1;
        panel.add(quantityInput, position);
        position.gridy = 2;
        panel.add(priceInput, position);

        position.gridy = 3;
        panel.add(confirm, position);
        position.gridx = 2;
        panel.add(cancel, position);

        buy.setPreferredSize(new Dimension(200, 200));

        int result = JOptionPane.showConfirmDialog(null, panel, "Create buy order", JOptionPane.PLAIN_MESSAGE);
    }

    public void sellPopup(){

        popUp = new JOptionPane();
        sell = new JFrame();
//        JPanel mainPanel = new JPanel();
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(width/2, height/2));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        itemName = new JLabel("Item Name: ");
        quantity = new JLabel("Quantity: ");
        price = new JLabel("Price: ");

        String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };
        itemNameInput = new JComboBox(petStrings);

        quantityInput = new JTextField();
        priceInput = new JTextField();

        position.weighty = 0;
        position.gridx = 0;
        position.gridy = 0;
        panel.add(itemName, position);
        position.gridy = 1;
        panel.add(quantity, position);
        position.gridy = 2;
        panel.add(price, position);
        position.gridx = 1;
        position.gridy = 0;
        panel.add(itemNameInput, position);
        position.gridy = 1;
        panel.add(quantityInput, position);
        position.gridy = 2;
        panel.add(priceInput, position);
        panel.add(new JLabel("THIS IS SELL"));
        //sellPop = popUp.getPopup(sell, panel, width/4, height/4);
        sellPop.show();
        sell.setPreferredSize(new Dimension(200, 200));
    }

    public void finalButton(ActionEvent e) throws IOException {
        var box = e.getActionCommand();
        if(box == "Cancel"){
        }else{
            //do add new trade order
            updateBuyOrder();
        }
    }

    private void updateBuyOrder() throws IOException {

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
            OrganisationalUnit organisationalUnit = user.getOrganisationalUnit();
            int orgID = organisationalUnit.getID();

            Trade order = new Trade(false, buyAsset, quantity, price, orgID);
            order.addTradeOrder(orgID, quantity, false, price);
            JOptionPane.showMessageDialog(panel, "New buy order for " + quantity + " " + asset + " at " + price + " credit(s)!",
                    "Edit Credits", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(panel, "Please enter all details of your order",
                    "Edit Credits", JOptionPane.WARNING_MESSAGE);
        }
    }
}
