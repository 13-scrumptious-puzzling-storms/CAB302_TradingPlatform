package TradingPlatform;

import javax.swing.*;
import java.awt.*;

import static TradingPlatform.GUIMain.*;
import static java.awt.GridBagConstraints.*;

public class GUIHome extends JFrame{

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
    String BuyHeading[] = {"Recent Trades","Price","Quantity"};

    public GUIHome(JPanel HomeTab){
        homePanel(HomeTab);
    }

    public void homePanel(JPanel panel){

        panel.setLayout(new GridBagLayout());

        GridBagConstraints position = new GridBagConstraints();

        JLabel title = new JLabel("SPS Trading");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Verdana", Font.BOLD, 50));

        JButton buyButton = new JButton("Buy Assets");
        buyButton.setFont(new Font("Verdana", Font.PLAIN, 16));
        buyButton.setPreferredSize(new Dimension(200, 100));
        buyButton.setMinimumSize(new Dimension(50, 50));
        JButton sellButton = new JButton("Sell Assets");
        sellButton.setFont(new Font("Verdana", Font.PLAIN, 16));
        sellButton.setPreferredSize(new Dimension(200, 100));
        sellButton.setMinimumSize(new Dimension(50, 50));

        JScrollPane TradesPaneSell = GUIMain.constructTable(data, BuyHeading);
        TradesPaneSell.setPreferredSize(new Dimension(tabWidth, tabHeight));
        TradesPaneSell.setMinimumSize(new Dimension(tabWidth/2, tabHeight));

        position.weighty = 1;
        position.gridx = 0;
        position.gridy = 0;
        position.gridwidth = 3;
        position.anchor = CENTER;
        panel.add(title, position);

        position.gridx = 0;
        position.gridy = 1;
        position.insets = new Insets(0, 200, 0, 0);
        position.anchor = LINE_START;
        panel.add(buyButton, position);

        position.insets = new Insets(0, 0, 0, 200);
        position.gridx = 2;
        position.anchor = LINE_END;
        panel.add(sellButton, position);

        position.insets = new Insets(50, 0, 0, 0);
        position.anchor = CENTER;
        position.gridx = 0;
        position.gridy = 2;
        position.gridwidth = 3;
        panel.add(TradesPaneSell, position);

        panel.setBackground(cust1);
    }
}
