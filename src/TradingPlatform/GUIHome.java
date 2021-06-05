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

    public User user;

    String TableOneHeading[] = {"Asset Name","Price","Quantity"};
    String TableTwoHeading[] = {"Recent Trades","Price","Quantity"};

    public GUIHome(JPanel HomeTab, User user) throws IOException, ClassNotFoundException {
        homePanel(HomeTab);
        this.user = user;
    }

    public void homePanel(JPanel panel) throws IOException, ClassNotFoundException {

        panel.setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        JButton buyButton = new JButton("Buy Assets");
        buyButton.setFont(new Font("Verdana", Font.PLAIN, 16));
        buyButton.setPreferredSize(new Dimension(200, 100));
        buyButton.setMinimumSize(new Dimension(50, 50));
        buyButton.setBackground(cust1);
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

        JButton sellButton = new JButton("Sell Assets");
        sellButton.setFont(new Font("Verdana", Font.PLAIN, 16));
        sellButton.setPreferredSize(new Dimension(200, 100));
        sellButton.setMinimumSize(new Dimension(50, 50));
        sellButton.setBackground(cust1);
        sellButton.addActionListener(e -> {
            try {
                ActionListener(true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });

        JScrollPane mostRecentTrades = GUIMain.tablePane(tableCreator(GUIMain.constructTable(TradeManager.getMostRecentAssetTypeTradeDetails(), TableOneHeading)));
        mostRecentTrades.setPreferredSize(new Dimension(tabWidth-tabWidth/100, 100));
        mostRecentTrades.setMinimumSize(new Dimension(tabWidth/2, 50));

        JScrollPane allRecentTrades = GUIMain.tablePane(tableCreator(GUIMain.constructTable(TradeManager.getRecentTradeDetails(), TableTwoHeading)));
        allRecentTrades.setPreferredSize(new Dimension(tabWidth-tabWidth/100, tabHeight-100));
        allRecentTrades.setMinimumSize(new Dimension(tabWidth/2, tabHeight));

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

        position.insets = new Insets(20, 0, 0, 0);
        position.anchor = CENTER;
        position.gridx = 0;
        position.gridy = 2;
        position.gridwidth = 3;
        panel.add(mostRecentTrades, position);
        position.insets = new Insets(0, 0, 0, 0);
        position.gridwidth = 3;
        position.gridy = 3;
        panel.add(allRecentTrades, position);

        panel.setBackground(cust1);
    }

    public void ActionListener(Boolean isSell) throws IOException, ClassNotFoundException {
       GUIOrder order = new GUIOrder(user);
       order.popup(isSell);
    }
}
