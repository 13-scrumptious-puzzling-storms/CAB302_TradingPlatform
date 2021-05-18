package TradingPlatform;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GUItester extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 692675871418401803L;

    public GUItester() {
        super("SPS Trading");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane pane = new JTabbedPane();
        JTabbedPane pane2 = new JTabbedPane();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel2_1 = new JPanel();
        JPanel panel2_2 = new JPanel();
        JPanel panel3 = new JPanel();
        panel2.add(pane2);
        panel1.add(new JLabel("Tab 1"));
        panel3.add(new JButton("Tab 2"));
        pane2.add("assets", panel2_1);
        pane2.add("trades", panel2_2);

        pane.add("Home", panel1);
        pane.add("Organisation Home", panel2);
        pane.add("My Profile", panel3);
        getContentPane().add(pane);

        // Display the window.
        setPreferredSize(new Dimension(500, 200));
        setLocation(new Point(100, 100));
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        new GUItester();
    }
}