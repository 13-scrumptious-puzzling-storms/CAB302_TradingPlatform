package TradingPlatform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static TradingPlatform.GUIMain.*;

public class GUIAdmin {

    private JPanel pnlAdmin;

    private JTextField txtUserUsername;
    private JTextField txtUserPassword;
    private JComboBox<String> cboxUserOrgUnitName;
    private JComboBox<String> cboxUserAccountType;
    private JButton btnAddUser;
    private JButton btnSaveNewUser;

    private JTextField txtAssetName;
    private JButton btnAddAsset;
    private JButton btnSaveNewAsset;

    private final adminActionListener listener = new adminActionListener();

    private final ITAdministrator admin;
    private final String[] AccountTypes = new String[]{AccountType.MEMBER.toString(), AccountType.ADMINISTRATOR.toString()};
    private final ArrayList<OrganisationalUnit> OrganisationUnits = ITAdministrator.GetAllOrgUnits();

    private boolean addUserIsMember = true;

    public GUIAdmin(JPanel AdminTab, ITAdministrator admin){

//        // NEED TO GET ALL THE ORG UNITS
//        OrganisationUnits.add(admin.getOrganisationalUnit());
//        OrganisationUnits.add(new OrganisationalUnit("Dummy Org1", 0));
//        OrganisationUnits.add(new OrganisationalUnit("Dummy Org2", 0));

        this.admin = admin;

        // Do this last (after variables have been initialised)
        adminPanel(AdminTab);
    }

    /**
     * Makes a JPanel consisting of the user's information, and text fields to change
     * their password in a grid bag layout.
     */
    private void adminPanel(JPanel AdminPanel){
        pnlAdmin = AdminPanel;
        pnlAdmin.setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        position.gridy = 0;
        position.anchor = GridBagConstraints.CENTER;
        position.insets = new Insets(0, 0, 50, 0);
        var pnlAddUser = makeAddUserPanel();
//        pnlAddUser.setPreferredSize(new Dimension(width, 150));
        pnlAdmin.add(pnlAddUser, position);

        position.gridy = 1;
        position.anchor = GridBagConstraints.CENTER;
        position.insets = new Insets(0, 0, 0, 0);
        var pnlAddAsset = makeAddAssetPanel();
//        pnlAddAsset.setPreferredSize(new Dimension(width / 2, 100));
        pnlAdmin.add(pnlAddAsset, position);

    }

    //region Add User
    /**
     * Creates the whole Add User panel, including the label of the panel, the edit fields, and the buttons
     * @return an Add User panel
     */
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
        pnlAddUser.add(makeAddUserFieldsPanel(), position);

        position.gridy = 2;
        position.insets = new Insets(0, 0, 0, 0);
        pnlAddUser.add(makeAddUserButtonsPanel(), position);

        setAddUserComponentsEditable(false);

        return pnlAddUser;
    }

    /**
     * Adds the text and combobox components used to create a user to a panel.
     * @return a panel containing labeled text components and combobox components used to create a user.
     */
    private JPanel makeAddUserFieldsPanel() {
        // All the text edit fields / comboboxes will be added to pnlAddUserFields
        JPanel pnlAddUserFields = new JPanel();
        GroupLayout layout = new GroupLayout(pnlAddUserFields);
        pnlAddUserFields.setLayout(layout);
        pnlAddUserFields.setBackground(DARK_JUNGLE_GREEN);

        pnlAddUserFields.setPreferredSize(new Dimension(width / 2, 120));
        pnlAddUserFields.setMinimumSize(new Dimension(700, 120));

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
        cboxUserAccountType = new JComboBox<>(AccountTypes);
        cboxUserOrgUnitName = new JComboBox<>(getOrgUnitNames());
        cboxUserAccountType.setEditable(false);
        cboxUserOrgUnitName.setEditable(false);

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

    /**
     * Adds the Add User and Save New User buttons to a panel
     * @return a panel containing Add User and Save New User buttons.
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

    /**
     * Sets whether or not the add user fields are editable.
     */
    private void setAddUserComponentsEditable(boolean editable){
        txtUserUsername.setEditable(editable);
        txtUserPassword.setEditable(editable);

        txtUserUsername.setEnabled(editable);
        txtUserPassword.setEnabled(editable);
        cboxUserAccountType.setEnabled(editable);
        cboxUserOrgUnitName.setEnabled(editable && addUserIsMember); // Auto set the org unit if the user is an new Admin
        btnSaveNewUser.setEnabled(editable);
        btnAddUser.setEnabled(editable == false); // btnAddUser enables the fields, so it needs to be the opposite

        // If editable, set the background to a lighter colour, else dark colour
        if (editable){
            txtUserUsername.setBackground(Color.WHITE);
            txtUserPassword.setBackground(Color.WHITE);
        }
        else {
            txtUserUsername.setBackground(UIManager.getColor("TextField.Background"));
            txtUserPassword.setBackground(UIManager.getColor("TextField.Background"));

        }
    }

    //endregion

    //region Add Asset type
    /**
     * Creates the whole Add asset type panel, including the label of the panel, the edit fields, and the buttons
     * @return an Add Asset panel
     */
    private JPanel makeAddAssetPanel() {
        // pnlAddUser will contain 3 sub-sections: The Panel title, the editable fields, and the add / save buttons
        JPanel pnlAddAsset = new JPanel();
        pnlAddAsset.setLayout(new GridBagLayout());
        pnlAddAsset.setBackground(DARK_JUNGLE_GREEN);
        GridBagConstraints position = new GridBagConstraints();

        JLabel lblAddUser = new JLabel("Add new Asset Type");
        lblAddUser.setForeground(Color.WHITE);

        position.gridy = 0;
        position.insets = new Insets(0, 0, 20, 0);
        pnlAddAsset.add(lblAddUser, position);

        position.gridy = 1;
        pnlAddAsset.add(makeAddAssetFieldsPanel(), position);

        position.gridy = 2;
        position.insets = new Insets(0, 0, 0, 0);
        pnlAddAsset.add(makeAddAssetButtonsPanel(), position);

        setAddAssetComponentsEditable(false);

        return pnlAddAsset;
    }

    /**
     * Adds the text and combobox components used to create a new asset to a panel.
     * @return a panel containing labeled text components and combobox components used to create an asset type.
     */
    private JPanel makeAddAssetFieldsPanel() {
        // All the text edit fields / comboboxes will be added to pnlAddAssetFields
        JPanel pnlAddAssetFields = new JPanel();
        GroupLayout layout = new GroupLayout(pnlAddAssetFields);
        pnlAddAssetFields.setLayout(layout);
        pnlAddAssetFields.setBackground(DARK_JUNGLE_GREEN);

        pnlAddAssetFields.setPreferredSize(new Dimension(width / 2, 25));
        pnlAddAssetFields.setMinimumSize(new Dimension(700, 25));

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        JLabel lblUsername = new JLabel("Asset Name");

        lblUsername.setForeground(Color.white);

        txtAssetName = new JTextField(20);

        cboxUserAccountType.addActionListener(listener);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(lblUsername));
        hGroup.addGroup(layout.createParallelGroup().addComponent(txtAssetName));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblUsername).addComponent(txtAssetName));
        layout.setVerticalGroup(vGroup);

        return pnlAddAssetFields;
    }

    /**
     * Adds the Add Asset and Save New Asset buttons to a panel
     * @return a panel containing Add Asset and Save New Asset buttons.
     */
    private JPanel makeAddAssetButtonsPanel(){
        JPanel pnlButton = new JPanel();
        pnlButton.setLayout(new GridBagLayout());
        pnlButton.setBackground(DARK_JUNGLE_GREEN);
        GridBagConstraints position = new GridBagConstraints();

        btnAddAsset = new JButton("Create New Asset");
        btnAddAsset.setPreferredSize(new Dimension(150, 50));
        btnAddAsset.setBackground(cust1);
        btnSaveNewAsset = new JButton("Save New Asset");
        btnSaveNewAsset.setPreferredSize(new Dimension(150, 50));
        btnSaveNewAsset.setBackground(cust1);

        btnAddAsset.addActionListener(listener);
        btnSaveNewAsset.addActionListener(listener);

        position.gridx = 0;
        position.anchor = GridBagConstraints.LINE_START;
        position.insets = new Insets(0, 0, 0, width/2 - 400);
        pnlButton.add(btnAddAsset, position);

        position.gridx = 1;
        position.anchor = GridBagConstraints.LINE_END;
        position.insets = new Insets(0, 0, 0, 0);
        pnlButton.add(btnSaveNewAsset, position);

        return pnlButton;
    }

    /**
     * Sets whether or not the add asset fields are editable.
     */
    private void setAddAssetComponentsEditable(boolean editable) {
        txtAssetName.setEditable(editable);

        txtAssetName.setEnabled(editable);
        btnSaveNewAsset.setEnabled(editable);
        btnAddAsset.setEnabled(editable == false);

        // If editable, set the background to a lighter colour, else dark colour
        if (editable)
            txtAssetName.setBackground(Color.WHITE);
        else
            txtAssetName.setBackground(UIManager.getColor("TextField.Background"));
    }

    //endregion

    /**
     * @return a String[] of the organisation unit names
     */
    private String[] getOrgUnitNames(){
        String[] orgNames = new String[OrganisationUnits.size()];
        for (int i = 0; i < OrganisationUnits.size(); i++) {
            orgNames[i] = OrganisationUnits.get(i).organisationName;
        }
        return orgNames;
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
                    setAddUserComponentsEditable(true);
                } else if (source == btnSaveNewUser) {
                    //saveUserPressed();
                    setAddUserComponentsEditable(false);
                } else if (source == btnAddAsset) {
                    setAddAssetComponentsEditable(true);
                } else if (source == btnSaveNewAsset) {
                    saveAssetPressed();
                }
            } else if (src instanceof JComboBox){
                JComboBox source = (JComboBox) e.getSource();
                if (source == cboxUserAccountType){
                    String selectedAccountType = (String)source.getSelectedItem();
                    if (AccountType.ADMINISTRATOR.toString().equals(selectedAccountType)) {
                        addUserIsMember = false;
                        cboxUserOrgUnitName.setSelectedItem(admin.getOrganisationalUnit().organisationName);
                    }
                    else
                        addUserIsMember = true;
                    setAddUserComponentsEditable(true);
                }
            }
        }

        /**
         * When the save user button is pressed, check that all fields are filled.
         * If they are, create a new Member or Admin depending on the account type
         * selected.
         */
        private void saveUserPressed() {
            if (txtUserUsername.getText() != null && !txtUserUsername.getText().equals("")
                    && txtUserPassword.getText() != null && !txtUserPassword.getText().equals("")) {
                // Get the text fields (username and password)
                String username = txtUserUsername.getText();
                String hashedPassword = SHA256.hashPassword(txtUserPassword.getText());

                // If member, add new member, else add admin
                if (AccountType.MEMBER.toString().equals(cboxUserAccountType.getSelectedItem())){
                    // Get the org unit
                    OrganisationalUnit unit = OrganisationUnits.get(cboxUserOrgUnitName.getSelectedIndex());
                    // Create the new member
                    admin.CreateNewMember(txtUserUsername.getText(), SHA256.hashPassword(txtUserPassword.getText()), unit);
                } else if (AccountType.ADMINISTRATOR.toString().equals(cboxUserAccountType.getSelectedItem())){
                    admin.CreateNewITAdmin(username, hashedPassword);
                }

                JOptionPane.showMessageDialog(pnlAdmin, username + " has been added to " + cboxUserOrgUnitName.getSelectedItem(),
                        "Change Password", JOptionPane.INFORMATION_MESSAGE);
                txtUserPassword.setText("");
                txtUserUsername.setText("");
                setAddUserComponentsEditable(false);
            }
            else {
                JOptionPane.showMessageDialog(pnlAdmin, "Please enter enter the user's username and password.",
                        "Change Password", JOptionPane.WARNING_MESSAGE);
            }
        }

        /**
         * When the save asset button is pressed, check that all fields are filled.
         * If they are, create a new asset type.
         */
        private void saveAssetPressed() {
            if (txtAssetName.getText() != null && !txtAssetName.getText().equals("")) {
                String assetName = txtAssetName.getText();
                admin.CreateNewAssetType(assetName);

                JOptionPane.showMessageDialog(pnlAdmin, assetName + " has been added as a new Asset Type!",
                        "New Asset Type", JOptionPane.INFORMATION_MESSAGE);
                txtAssetName.setText("");
                setAddAssetComponentsEditable(false);
            }
            else{
                JOptionPane.showMessageDialog(pnlAdmin, "Please enter name of the new asset.",
                        "Create Asset", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
