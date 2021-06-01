package TradingPlatform;
import javax.swing.*;

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

    private static JComboBox itemNameInput;
    private static JTextField quantityInput;
    private static JTextField priceInput;
    private static JLabel itemName;
    private static JLabel quantity;
    private static JLabel price;

    private static JButton confirm;
    private static JButton cancel;
    private static PopupFactory popUp;
    private static JFrame buy;
    private static JPanel panel;
    private static JFrame sell;


    public static void buyPopup() throws IOException, ClassNotFoundException {
        buy = new JFrame("Buy");
        buy.setSize(new Dimension(width/2, height/2));
        popUp = new PopupFactory();

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(width/2, height/2));
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
        priceInput = new JTextField();
        priceInput.setPreferredSize(new Dimension(width/4, 20));

        confirm = new JButton("Confirm Order");
        confirm.addActionListener(GUIOrder::finalButton);

        cancel = new JButton("Cancel");
        cancel.addActionListener(GUIOrder::finalButton);

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
        buyPop = popUp.getPopup(buy, panel, width/4, height/4);

        buyPop.show();
    }
    public static void closePop(){
        buyPop.hide();
        buyPop = popUp.getPopup(buy, panel, 100, 100);
    }

    public static void sellPopup(){

        popUp = new PopupFactory();
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
        sellPop = popUp.getPopup(sell, panel, width/4, height/4);
        sellPop.show();
        sell.setPreferredSize(new Dimension(200, 200));
    }

    public static void finalButton(ActionEvent e){
        var box = e.getActionCommand();
        if(box == "Cancel"){
            buyPop.hide();
        }else{
            //do add new trade order
            buyPop.hide();
        }
    }
}
