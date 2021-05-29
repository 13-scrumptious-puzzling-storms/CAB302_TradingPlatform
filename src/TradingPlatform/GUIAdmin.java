package TradingPlatform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static TradingPlatform.GUIMain.*;

public class GUIAdmin {

    private JPanel pnlProfile;

    private JTextField txtUserUsername;
    private JTextField txtUserPassword;
    private JComboBox cboxUserOrgUnitName;
    private JComboBox cboxUserAccountType;

    private JButton btnAddUser;
    private JButton btnSaveNewUser;

    private final adminActionListener listener;

    private final User admin;
    private final String[] AccountTypes = new String[]{AccountType.MEMBER.toString(), AccountType.ADMINISTRATOR.toString()};
    private ArrayList<OrganisationalUnit> OrganisationUnits;

    public GUIAdmin(JPanel ProfileTab, ITAdministrator admin){
        profilePanel(ProfileTab);
        this.admin = admin;
        listener = new adminActionListener();
    }

    /**
     * Makes a JPanel consisting of the user's information, and text fields to change
     * their password in a grid bag layout.
     */
    private void profilePanel(JPanel ProfilePanel){
        pnlProfile = ProfilePanel;
        pnlProfile.setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        position.gridy = 0;
        position.anchor = GridBagConstraints.CENTER;
        position.insets = new Insets(0, 0, 50, 0);
        var pnlAddUser = makeAddUserPanel();
        pnlAddUser.setPreferredSize(new Dimension(width / 2, 150));
        pnlProfile.add(pnlAddUser, position);

        position.gridy = 1;
        position.anchor = GridBagConstraints.CENTER;
        position.insets = new Insets(0, 0, 0, 0);
        var pnlButtons = makeAddUserPanel();
        pnlButtons.setPreferredSize(new Dimension(width / 2, 100));
        pnlProfile.add(pnlButtons, position);

    }

    private JPanel makeAddUserPanel(){
        // pnlAddUser will contain 3 sub-sections: The Panel title, the editable fields, and the add / save buttons
        JPanel pnlAddUser = new JPanel();
        pnlAddUser.setLayout(new GridBagLayout());
        pnlAddUser.setBackground(DARK_JUNGLE_GREEN);
        GridBagConstraints position = new GridBagConstraints();

        JLabel lblAddUser = new JLabel("Create New User");
        lblAddUser.setForeground(Color.WHITE);

        position.gridy = 0;
        position.insets = new Insets(0, 0, 20, 0);
        pnlAddUser.add(lblAddUser, position);

        position.gridy = 1;
        position.insets = new Insets(0, 0, 0, 0);
        pnlAddUser.add(makeAddUserFieldsPanel(), position);

        position.gridy = 2;
        pnlAddUser.add(makeAddUserButtonsPanel(), position);

        setAddUserComponentsEditable(false);

        return pnlAddUser;
    }

    private JPanel makeAddUserFieldsPanel() {
        // All the text edit fields / comboboxes will be added to pnlAddUserFields
        JPanel pnlAddUserFields = new JPanel();
        GroupLayout layout = new GroupLayout(pnlAddUserFields);
        pnlAddUserFields.setLayout(layout);

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        JLabel lblUsername = new JLabel("Username");
        JLabel lblPassword = new JLabel("Password");
        JLabel lblAccountType = new JLabel("Account Type");
        JLabel lblOrgUnitName = new JLabel("Organisation Unit");

        lblUsername.setForeground(Color.white);
        lblPassword.setForeground(Color.white);
        lblAccountType.setForeground(Color.white);
        lblOrgUnitName.setForeground(Color.white);

        txtUserUsername = new JTextField(20);
        txtUserPassword = new JTextField(20);
        cboxUserAccountType = new JComboBox(AccountTypes);
        cboxUserOrgUnitName = new JComboBox(new String[]{"Org1", "Org2"});

        cboxUserAccountType.addActionListener(listener);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(lblUsername)
                .addComponent(lblPassword).addComponent(lblAccountType).addComponent(lblOrgUnitName));
        hGroup.addGroup(layout.createParallelGroup().addComponent(txtUserUsername)
                .addComponent(txtUserPassword).addComponent(cboxUserAccountType).addComponent(cboxUserOrgUnitName)
                );
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        // The sequential group contains five parallel groups that align
        // the contents along the baseline. The first parallel group contains
        // the first label and edit field, and the second parallel group contains
        // the second label and edit field etc. By using a sequential group
        // the labels and text fields are positioned vertically after one another.
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblUsername).addComponent(txtUserUsername));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblPassword).addComponent(txtUserPassword));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblAccountType).addComponent(cboxUserAccountType));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblOrgUnitName).addComponent(cboxUserOrgUnitName));
        layout.setVerticalGroup(vGroup);

        return pnlAddUserFields;
    }

    private void setAddUserComponentsEditable(boolean editable){
        txtUserUsername.setEditable(editable);
        cboxUserOrgUnitName.setEditable(editable);
        cboxUserAccountType.setEditable(editable);

        txtUserUsername.setEnabled(editable);
        cboxUserOrgUnitName.setEnabled(editable);
        cboxUserAccountType.setEnabled(editable);
        btnSaveNewUser.setEnabled(editable);
        btnAddUser.setEnabled(editable == false); // btnAddUser enables the fields, so it needs to be the opposite

        // If editable, set the background to a lighter colour, else dark colour
        if (editable){
            txtUserUsername.setBackground(Color.WHITE);
            txtUserPassword.setBackground(Color.WHITE);
            cboxUserOrgUnitName.setBackground(Color.WHITE);
            cboxUserAccountType.setBackground(Color.WHITE);
        }
        else {
            txtUserUsername.setBackground(UIManager.getColor("TextField.Background"));
            txtUserPassword.setBackground(UIManager.getColor("TextField.Background"));
            cboxUserOrgUnitName.setBackground(UIManager.getColor("TextField.Background"));
            cboxUserAccountType.setBackground(UIManager.getColor("TextField.Background"));
        }
    }

    /**
     * Adds the ChangePassword and SavePassword buttons to a panel
     * @return a new panel containing Change Password and Save Password buttons.
     */
    private JPanel makeAddUserButtonsPanel(){
        JPanel pnlButton = new JPanel();
        pnlButton.setLayout(new GridBagLayout());
        pnlButton.setBackground(DARK_JUNGLE_GREEN);
        GridBagConstraints position = new GridBagConstraints();

        btnAddUser = new JButton("Create User");
        btnAddUser.setPreferredSize(new Dimension(150, 50));
        btnAddUser.setBackground(cust1);
        btnSaveNewUser = new JButton("Save New User");
        btnSaveNewUser.setPreferredSize(new Dimension(150, 50));
        btnSaveNewUser.setBackground(cust1);

        btnAddUser.addActionListener(listener);
        btnSaveNewUser.addActionListener(listener);

        position.gridx = 0;
        position.anchor = GridBagConstraints.LINE_START;
        position.insets = new Insets(0, 0, 0, width/2 - 400);
        pnlButton.add(btnAddUser, position);

        position.gridx = 1;
        position.anchor = GridBagConstraints.LINE_END;
        position.insets = new Insets(0, 0, 0, 0);
        pnlButton.add(btnSaveNewUser, position);

        return pnlButton;
    }



    private class adminActionListener implements ActionListener {

        /**
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            // Get event source
            Object src = e.getSource();
            if (src instanceof JButton) {
                JButton source = (JButton) e.getSource();
                if (source == btnAddUser) {
                    //changePasswordPressed();
                } else if (source == btnSaveNewUser) {
                    //savePressed();
                }
            } else if (src instanceof JComboBox){
                JComboBox source = (JComboBox) e.getSource();
                if (source == cboxUserAccountType){
                    // Do Something
                }
            }
        }


    }
}
