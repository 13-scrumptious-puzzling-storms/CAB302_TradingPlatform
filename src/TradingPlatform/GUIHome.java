package TradingPlatform;

import javax.swing.*;
import java.awt.*;

import static TradingPlatform.GUIMain.cust1;

public class GUIHome{

    Object[] colours = GUIMain.getColours();
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
    String BuyHeading[] = {"Buy Orders","Price","Quantity"};

    public GUIHome(JPanel HomeTab){
        homePanel(HomeTab);
        GUIMain.constructTable(data, BuyHeading);
        HomeTab.setBackground(Color.red);
    }

    public void homePanel(JPanel panel){
        JButton buyButton = new JButton("Buy Assets");
        JButton sellButton = new JButton("Sell Assets");
        JPanel buttonPanel = new JPanel();
        JPanel emptyPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,8, 10, 10));
        buttonPanel.add(emptyPanel);
        buttonPanel.add(emptyPanel);
        buttonPanel.add(buyButton);
        buttonPanel.add(emptyPanel);
        buttonPanel.add(sellButton);
        buttonPanel.add(emptyPanel);
        buttonPanel.add(emptyPanel);

        JScrollPane homePanel = new JScrollPane();
        JScrollPane TradesPaneSell = GUIMain.constructTable(data, BuyHeading);
        homePanel.setPreferredSize(new Dimension(100, 100));
        panel.add(new JLabel("Tab 1"));
        panel.setLayout(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(TradesPaneSell, BorderLayout.CENTER);
        panel.setBackground(Color.blue);
        buyButton.setBackground(cust1);

    }
}
