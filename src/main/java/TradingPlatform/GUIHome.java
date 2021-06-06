package TradingPlatform;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static TradingPlatform.GUIMain.*;
import static java.awt.GridBagConstraints.*;

/**
 * Constructs components to create the GUIHome page. The user can see the most recent reconciled trade orders,
 * and create new Buy/Sell orders.
 */
public class GUIHome extends JFrame{

    public User user;

    private volatile JTable mostRecentTradesTable;
    private volatile JTable allRecentTradesTable;

    String TableOneHeading[] = {"Asset Name","Price","Quantity"};
    String TableTwoHeading[] = {"Recent Trades","Price","Quantity"};

    /**
     * GUIHome constructor, adds the home panel to the home tab panel
     * @param HomeTab A JPanel which to add Home contents in
     * @param user A User that belongs to the organisational unit
     */
    public GUIHome(JPanel HomeTab, User user)  {
        homePanel(HomeTab);
        this.user = user;

        // create a thread to update the page content every 5 seconds or so
        var exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleWithFixedDelay(this::RefreshContent, 5, 5, TimeUnit.SECONDS);
    }

    /**
     * Constructs components and adds them into the JPanel for the Home tab.
     * @param panel A JPanel which to add Home contents in
     */
    public void homePanel(JPanel panel)  {

        panel.setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        JButton buyButton = new JButton("Buy Assets");
        buyButton.setFont(new Font("Verdana", Font.PLAIN, 16));
        buyButton.setPreferredSize(new Dimension(200, 100));
        buyButton.setMinimumSize(new Dimension(50, 50));
        buyButton.setBackground(cust1);
        buyButton.addActionListener(e -> {
            ActionListener(false);
        });

        JButton sellButton = new JButton("Sell Assets");
        sellButton.setFont(new Font("Verdana", Font.PLAIN, 16));
        sellButton.setPreferredSize(new Dimension(200, 100));
        sellButton.setMinimumSize(new Dimension(50, 50));
        sellButton.setBackground(cust1);
        sellButton.addActionListener(e -> {
            ActionListener(true);
        });

        mostRecentTradesTable = tableCreator(GUIMain.constructTable(TradeManager.getMostRecentAssetTypeTradeDetails(), TableOneHeading));
        var mostRecentTrades = GUIMain.tablePane(mostRecentTradesTable);
        mostRecentTrades.setPreferredSize(new Dimension(tabWidth-tabWidth/100, 100));
        mostRecentTrades.setMinimumSize(new Dimension(tabWidth/2, 50));

        allRecentTradesTable = tableCreator(GUIMain.constructTable(TradeManager.getRecentTradeDetails(), TableTwoHeading));
        var allRecentTrades = GUIMain.tablePane(allRecentTradesTable);
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

    /**
     * Periodically refreshes table models for the most recent reconciled trades for each asset,
     * and all recent reconciled trades.
     */
    private void RefreshContent(){
        try {
            var dataModel1 = GUIMain.constructTable(TradeManager.getMostRecentAssetTypeTradeDetails(), TableOneHeading);
            mostRecentTradesTable.setModel(dataModel1);
            var dataModel2 = GUIMain.constructTable(TradeManager.getRecentTradeDetails(), TableTwoHeading);
            allRecentTradesTable.setModel(dataModel2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls the GUIOrder buy or sell order depending on if isSell is true or false
     * @param isSell A Boolean determining if the order is a Sell (true) or Buy (false)
     */
    public void ActionListener(Boolean isSell) {
       GUIOrder order = new GUIOrder(user);
       order.popup(isSell);
    }
}
