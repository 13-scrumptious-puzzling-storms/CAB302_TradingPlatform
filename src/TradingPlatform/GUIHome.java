package TradingPlatform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;

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
    String TableHeading[] = {"Recent Trades","Price","Quantity"};

    public GUIHome(JPanel HomeTab) throws IOException, ClassNotFoundException {
        homePanel(HomeTab);
    }

    public void homePanel(JPanel panel) throws IOException, ClassNotFoundException {

        panel.setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        JButton buyButton = new JButton("Buy Assets");
        buyButton.setFont(new Font("Verdana", Font.PLAIN, 16));
        buyButton.setPreferredSize(new Dimension(200, 100));
        buyButton.setMinimumSize(new Dimension(50, 50));
        buyButton.setBackground(cust1);
        buyButton.addActionListener(e -> {
            try {
                buyActionListener(e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });

        JButton sellButton = new JButton("Sell Assets");
        sellButton.setFont(new Font("Verdana", Font.PLAIN, 16));
        sellButton.setPreferredSize(new Dimension(200, 100));
        sellButton.setMinimumSize(new Dimension(50, 50));
        sellButton.setBackground(cust1);
        buyButton.addActionListener(this::sellActionListener);

        JScrollPane TradesPaneSell = GUIMain.tablePane(tableCreator(GUIMain.constructTable(TradeManager.getMostRecentAssetTypeTradeDetails(), TableHeading)));
        TradesPaneSell.setPreferredSize(new Dimension(tabWidth, tabHeight));
        TradesPaneSell.setMinimumSize(new Dimension(tabWidth/2, tabHeight));

        position.weighty = 1;
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

    public void buyActionListener(ActionEvent e) throws IOException, ClassNotFoundException {
        String event = e.getActionCommand();
        if(event == "Buy Assets") {
            GUIOrder.buyPopup();
        }
    }
    public void sellActionListener(ActionEvent e){
//        GUIOrder.sellPopup();
    }
}
