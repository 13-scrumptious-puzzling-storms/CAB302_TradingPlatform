package TradingPlatform;

import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.IOException;

public class GUIMain extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 692675871418401803L;

    // Colours
    public static final Color cust1 = new Color(38,139,133);
    public static final Color cust2 = new Color(51,61,68);
    public static final Color cust3 = new Color(72,191,146);

    public static Object[] getColours(){
        Object[] colours = new Object[]{cust1, cust2, cust3};
        return colours;
    }
    // Display the window.
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // the screen height
    public static int height = (int)screenSize.getHeight();
    public static int tabHeight = height/2;

    // the screen width
    public static int width = (int)screenSize.getWidth();
    public static int tabWidth = width - (width/3);
    //Font
    public static String FONT = "SansSerif";
    //orgTab
    public JPanel orgTab = new JPanel();

    public String data[][] = {{"Vinod","MCA","Computer"},
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

    public GUIMain() throws IOException, ClassNotFoundException {
        super("SPS Trading");
        JFrame.setDefaultLookAndFeelDecorated(false);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainframe = new JPanel();
        mainframe.setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        JTabbedPane pagePane = new JTabbedPane();
        UIManager.put("TabbedPane.selectedBackground", cust1);



        JPanel homeTab = new JPanel();
        new GUIHome(homeTab);


        new GUIOrgHome(orgTab);

        JPanel profileTab = new JPanel();
        new GUIProfile(profileTab);

        pagePane.add("Home", homeTab);
        pagePane.add("Organisation Home", orgTab);
        pagePane.add("My Profile", profileTab);

        position.weighty = 0;
        position.gridy = 0;
        JLabel title = new JLabel("SPS Trading");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Verdana", Font.BOLD, 50));
        mainframe.add(title , position);
        position.gridy = 1;
        pagePane.setPreferredSize(new Dimension(width-width/10, height - height/4));
        pagePane.setMinimumSize(new Dimension(width/2, height/2));
        mainframe.add(pagePane, position);
        mainframe.setBackground(cust2);

        getContentPane().add(mainframe);
        getContentPane().setBackground(cust2);

        setPreferredSize(new Dimension(width, height));
        setLocation(new Point(0, 0));
        pack();
        setVisible(true);
        orgTab.setAutoscrolls(true);

        pagePane.setBackground(cust1);
        pagePane.setForeground(Color.WHITE);

        homeTab.setBackground(cust2);
        homeTab.setForeground(Color.WHITE);

        orgTab.setBackground(cust2);
        orgTab.setForeground(Color.LIGHT_GRAY);

        profileTab.setBackground(cust2);
        profileTab.setForeground(Color.LIGHT_GRAY);
        setBackground(cust2);

    }



    public static JScrollPane constructTable(String[][] data, String[] headingType){
        DefaultTableModel model = new DefaultTableModel(data, headingType);
        JTable sell_buyTable = new JTable(model);
        JScrollPane tradesScrollTable = new JScrollPane(sell_buyTable);
        tradesScrollTable.setBackground(cust3);
        tradesScrollTable.getVerticalScrollBar().setBackground(cust2);
        tradesScrollTable.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = cust1;
            }
        });
        JTableHeader anHeader = sell_buyTable.getTableHeader();
        anHeader.setBackground(cust1);

        return tradesScrollTable;
    }

}
