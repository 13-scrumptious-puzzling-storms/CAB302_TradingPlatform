package TradingPlatform;
import javax.swing.*;

import java.awt.*;

import static TradingPlatform.GUIMain.*;

public class GUIOrder extends JFrame{

    // popup
    Popup p;

    // constructor
    GUIOrder(JFrame frame)
    {
        // create a label
        JLabel l = new JLabel("This is a popup");

        PopupFactory pf = new PopupFactory();

        // create a panel
        JPanel p2 = new JPanel();

        // set Background of panel
        p2.setBackground(Color.red);

        p2.add(l);

        // create a popup
        p = pf.getPopup(frame, p2, 180, 100);

    }

    public void buyPopup() {
        PopupFactory popUp = new PopupFactory();
        JFrame buy = new JFrame();
    }
    public void sellPopup(){

    }
}
