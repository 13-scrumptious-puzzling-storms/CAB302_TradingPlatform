package TradingPlatform;
import javax.swing.*;

import java.awt.*;

import static TradingPlatform.GUIMain.*;

public class GUIOrder extends JFrame{

    // popups
    Popup p;
    private static Popup buyPop;
    Popup SellPop;

    private static JComboBox itemNameInput;
    private static JTextField quantityInput;
    private static JTextField priceInput;
    private static JLabel itemName;
    private static JLabel quantity;
    private static JLabel price;

    public static void buyPopup(){
        PopupFactory popUp = new PopupFactory();
        JFrame buy = new JFrame();
//        JPanel mainPanel = new JPanel();
        JPanel panel = new JPanel();
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


        buyPop = popUp.getPopup(buy, panel, width/4, height/4);
        buyPop.show();
        buy.setPreferredSize(new Dimension(200, 200));
    }
    public void sellPopup(){

    }
}
