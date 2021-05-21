package TradingPlatform;

import java.awt.*;

import javax.swing.*;

public class GUItester extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 692675871418401803L;

    // Colours
    private static final Color cust1 = new Color(38,139,133);
    private static final Color cust2 = new Color(51,61,68);
    private static final Color cust3 = new Color(72,191,146);

    public GUItester() {
        super("SPS Trading");
        JFrame.setDefaultLookAndFeelDecorated(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane pane = new JTabbedPane();

        JPanel panel1 = new JPanel();
        homePanel(panel1);

        JPanel panel2 = new JPanel();
        orgHomePanel(panel2);

        JPanel panel3 = new JPanel();
        profilePanel(panel3);

        pane.add("Home", panel1);
        pane.add("Organisation Home", panel2);
        pane.add("My Profile", panel3);
        getContentPane().add(pane);

        // Display the window.
        setPreferredSize(new Dimension(500, 400));
        setLocation(new Point(0, 0));
        pack();
        setVisible(true);

        pane.setBackground(cust1);
        pane.setForeground(Color.WHITE);

        panel1.setBackground(cust2);
        panel1.setForeground(Color.WHITE);

        panel2.setBackground(cust2);
        panel2.setForeground(Color.LIGHT_GRAY);

        panel3.setBackground(cust2);
        panel3.setForeground(Color.LIGHT_GRAY);
        setBackground(cust2);


    }

    public void homePanel(JPanel panel){
        panel.add(new JLabel("Tab 1"));
    }

    public void orgHomePanel(JPanel panel2){
        JTabbedPane pane2 = new JTabbedPane();
        JPanel panel2_1 = new JPanel();
        orgTrades(panel2_1);

        JPanel panel2_2 = new JPanel();
        orgAssets(panel2_2);

        panel2.add(pane2);
        pane2.add("assets", panel2_1);
        pane2.add("trades", panel2_2);
        pane2.setPreferredSize(new Dimension(300, 100));
    }

    public void orgTrades(JPanel panel){

    }

    public void orgAssets(JPanel panel){

    }

    public void profilePanel(JPanel panel3){
        panel3.add(new JButton("Tab 2"));
    }



}