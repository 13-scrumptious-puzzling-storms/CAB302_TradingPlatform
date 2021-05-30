package TradingPlatform;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static TradingPlatform.GUIMain.*;

public class GUIAdmin {

    private JPanel pnlAdmin;

    private JTextField txtAddUserUsername;
    private JTextField txtAddUserPassword;
    private JComboBox<String> cboxUserOrgUnitName;
    private JComboBox<String> cboxUserAccountType;
    private JButton btnAddUser;
    private JButton btnSaveNewUser;

    private JTextField txtAssetName;
    private JButton btnAddAsset;
    private JButton btnSaveNewAsset;

    private JTextField txtUpdatePasswordUsername;
    private JTextField txtUpdatePasswordPassword;
    private JButton btnUpdatePassword;

    private JTextField txtCreateOrgUnitName;
    private JButton btnCreateOrgUnit;
    private JButton btnSaveNewOrgUnit;

    private JComboBox<String> cboxCreditsOrgUnit;
    private JTextField txtCredits;
    private JButton btnUpdateCredits;

    private final adminActionListener listener = new adminActionListener();

    private final ITAdministrator admin;
    private final String[] AccountTypes = new String[]{AccountType.MEMBER.toString(), AccountType.ADMINISTRATOR.toString()};
    private final ArrayList<OrganisationalUnit> OrganisationUnits = ITAdministrator.GetAllOrgUnits();

    private boolean addUserIsMember = true;

    private static final int PANEL_MIN_WIDTH = 400;
    private static final int PANEL_PREFERRED_WIDTH = width / 4;
    private static final int BUTTON_PREFERRED_WIDTH = 150;
    private static final int BUTTON_PREFERRED_HEIGHT = 50;
    private static final int BUTTON_MIN_HEIGHT = 30;

    public GUIAdmin(JPanel AdminTab, ITAdministrator admin){

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

        position.insets = new Insets(0, 0, 25, 50);
        position.gridy = 0;
        position.gridx = 0;
        position.anchor = GridBagConstraints.CENTER;
        var pnlAddUser = makeAddUserPanel();
        pnlAdmin.add(pnlAddUser, position);

        position.gridy = 1;
        position.anchor = GridBagConstraints.CENTER;
        var pnlChangePassword = makeChangePasswordPanel();
        pnlAdmin.add(pnlChangePassword, position);

        position.insets = new Insets(0, 0, 0, 50);
        position.gridy = 2;
        position.anchor = GridBagConstraints.CENTER;
        var pnlCreateOrgUnit = makeCreateOrgUnitPanel();
        pnlAdmin.add(pnlCreateOrgUnit, position);

        position.gridx = 1;
        position.insets = new Insets(0, 0, 25, 0);
//        position.gridy = 0;
//        position.anchor = GridBagConstraints.CENTER;
//        var pnlCreateOrgUnit2 = makeCreateOrgUnitPanel();
//        pnlAdmin.add(pnlCreateOrgUnit2, position);

        position.gridy = 1;
        position.anchor = GridBagConstraints.CENTER;
        var pnlEditOrgUnitCredits = makeEditOrgUnitCreditsPanel();
        pnlAdmin.add(pnlEditOrgUnitCredits, position);

        position.gridy = 2;
        position.anchor = GridBagConstraints.CENTER;
        position.insets = new Insets(0, 0, 0, 0);
        var pnlAddAsset = makeAddAssetPanel();
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
        pnlAddUser.setBorder(CreatePanelBorder("Create New User"));

        position.gridy = 1;
        position.insets = new Insets(0, 0, 20, 0);
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

        pnlAddUserFields.setPreferredSize(new Dimension(PANEL_PREFERRED_WIDTH, 120));
        pnlAddUserFields.setMinimumSize(new Dimension(PANEL_MIN_WIDTH, 120));

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

        txtAddUserUsername = new JTextField(20);
        txtAddUserPassword = new JTextField(20);
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
        hGroup.addGroup(layout.createParallelGroup().addComponent(txtAddUserUsername)
                .addComponent(txtAddUserPassword).addComponent(cboxUserAccountType).addComponent(cboxUserOrgUnitName)
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
                .addComponent(lblUsername).addComponent(txtAddUserUsername));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblPassword).addComponent(txtAddUserPassword));
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
        pnlButton.setPreferredSize(new Dimension(PANEL_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
        pnlButton.setMinimumSize(new Dimension(PANEL_MIN_WIDTH, BUTTON_MIN_HEIGHT));
        GridBagConstraints position = new GridBagConstraints();

        btnAddUser = new JButton("Create User");
        btnAddUser.setPreferredSize(new Dimension(BUTTON_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
        btnAddUser.setBackground(cust1);
        btnSaveNewUser = new JButton("Save New User");
        btnSaveNewUser.setPreferredSize(new Dimension(BUTTON_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
        btnSaveNewUser.setBackground(cust1);

        btnAddUser.addActionListener(listener);
        btnSaveNewUser.addActionListener(listener);

        position.gridx = 0;
        position.weightx = 0;
        position.anchor = GridBagConstraints.LINE_END;
        pnlButton.add(btnAddUser, position);

        position.gridx = 2;
        position.anchor = GridBagConstraints.LINE_START;
        pnlButton.add(btnSaveNewUser, position);

        // add blank component to fill the space between the buttons
        position.gridx = 1;
        position.anchor = GridBagConstraints.CENTER;
        position.weightx = 100.0;
        position.gridwidth = GridBagConstraints.RELATIVE;
        pnlButton.add(Box.createGlue(), position);

        return pnlButton;
    }

    /**
     * Sets whether or not the add user fields are editable.
     */
    private void setAddUserComponentsEditable(boolean editable){
        txtAddUserUsername.setEditable(editable);
        txtAddUserPassword.setEditable(editable);

        txtAddUserUsername.setEnabled(editable);
        txtAddUserPassword.setEnabled(editable);
        cboxUserAccountType.setEnabled(editable);
        cboxUserOrgUnitName.setEnabled(editable && addUserIsMember); // Auto set the org unit if the user is an new Admin
        btnSaveNewUser.setEnabled(editable);
        btnAddUser.setEnabled(editable == false); // btnAddUser enables the fields, so it needs to be the opposite

        // If editable, set the background to a lighter colour, else dark colour
        if (editable){
            txtAddUserUsername.setBackground(Color.WHITE);
            txtAddUserPassword.setBackground(Color.WHITE);
        }
        else {
            txtAddUserUsername.setBackground(UIManager.getColor("TextField.Background"));
            txtAddUserPassword.setBackground(UIManager.getColor("TextField.Background"));

        }
    }

    //endregion

    //region Update User's Password

    /**
     * Creates the whole Add User panel, including the label of the panel, the edit fields, and the buttons
     * @return an Add User panel
     */
    private JPanel makeChangePasswordPanel(){
        // pnlChangePassword will contain 3 sub-sections: The Panel title, the editable fields, and the add / save buttons
        JPanel pnlChangePassword = new JPanel();
        pnlChangePassword.setLayout(new GridBagLayout());
        pnlChangePassword.setBackground(DARK_JUNGLE_GREEN);
        GridBagConstraints position = new GridBagConstraints();
        pnlChangePassword.setBorder(CreatePanelBorder("Change a user's password"));

        position.gridy = 1;
        position.insets = new Insets(0, 0, 20, 0);
        pnlChangePassword.add(makeUpdatePasswordFieldsPanel(), position);

        position.gridy = 2;
        position.insets = new Insets(0, 0, 0, 0);
        pnlChangePassword.add(makeUpdatePasswordButtonsPanel(), position);

        return pnlChangePassword;
    }

    /**
     * Adds the text components used to update a user's password to a panel.
     * @return a panel containing labeled text components used to update a user's password.
     */
    private JPanel makeUpdatePasswordFieldsPanel() {
        // All the text edit fields will be added to pnlUpdatePasswordFields
        JPanel pnlUpdatePasswordFields = new JPanel();
        GroupLayout layout = new GroupLayout(pnlUpdatePasswordFields);
        pnlUpdatePasswordFields.setLayout(layout);
        pnlUpdatePasswordFields.setBackground(DARK_JUNGLE_GREEN);

        pnlUpdatePasswordFields.setPreferredSize(new Dimension(PANEL_PREFERRED_WIDTH, 50));
        pnlUpdatePasswordFields.setMinimumSize(new Dimension(PANEL_MIN_WIDTH, 50));

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        JLabel lblUsername = new JLabel("User's username");
        JLabel lblPassword = new JLabel("New password");

        lblUsername.setForeground(Color.white);
        lblPassword.setForeground(Color.white);

        txtUpdatePasswordUsername = new JTextField(20);
        txtUpdatePasswordPassword = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(lblUsername).addComponent(lblPassword));
        hGroup.addGroup(layout.createParallelGroup().addComponent(txtUpdatePasswordUsername)
                .addComponent(txtUpdatePasswordPassword));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        // The sequential group contains two parallel groups that align
        // the contents along the baseline. The first parallel group contains
        // the first label and edit field, and the second parallel group contains
        // the second label and edit field etc. By using a sequential group
        // the labels and text fields are positioned vertically after one another.
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblUsername).addComponent(txtUpdatePasswordUsername));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblPassword).addComponent(txtUpdatePasswordPassword));
        layout.setVerticalGroup(vGroup);

        return pnlUpdatePasswordFields;
    }

    /**
     * Adds the change password button to a panel
     * @return a panel containing the change password button.
     */
    private JPanel makeUpdatePasswordButtonsPanel(){
        JPanel pnlButton = new JPanel();
        pnlButton.setLayout(new GridBagLayout());
        pnlButton.setBackground(DARK_JUNGLE_GREEN);
        pnlButton.setPreferredSize(new Dimension(PANEL_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
        pnlButton.setMinimumSize(new Dimension(PANEL_MIN_WIDTH, BUTTON_MIN_HEIGHT));
        GridBagConstraints position = new GridBagConstraints();

        btnUpdatePassword = new JButton("Change Password");
        btnUpdatePassword.setPreferredSize(new Dimension(BUTTON_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
        btnUpdatePassword.setBackground(cust1);

        btnUpdatePassword.addActionListener(listener);

        position.gridx = 1;
        position.anchor = GridBagConstraints.LINE_END;
        pnlButton.add(btnUpdatePassword, position);

        // add blank component to fill the space between the buttons
        position.gridx = 1;
        position.anchor = GridBagConstraints.CENTER;
        position.weightx = 100.0;
        position.gridwidth = GridBagConstraints.RELATIVE;
        pnlButton.add(Box.createGlue(), position);

        return pnlButton;
    }

    //endregion

    //region Add Asset type

    /**
     * Creates the whole Add asset type panel, including the label of the panel, the edit fields, and the buttons
     * @return an Add Asset panel
     */
    private JPanel makeAddAssetPanel() {
        // pnlAddAsset will contain 3 sub-sections: The Panel title, the editable fields, and the add / save buttons
        JPanel pnlAddAsset = new JPanel();
        pnlAddAsset.setLayout(new GridBagLayout());
        pnlAddAsset.setBackground(DARK_JUNGLE_GREEN);
        pnlAddAsset.setBorder(CreatePanelBorder("Add new Asset Type"));
        GridBagConstraints position = new GridBagConstraints();

        position.gridy = 1;
        position.insets = new Insets(0, 0, 20, 0);
        pnlAddAsset.add(makeAddAssetFieldsPanel(), position);

        position.gridy = 2;
        position.insets = new Insets(0, 0, 0, 0);
        pnlAddAsset.add(makeAddAssetButtonsPanel(), position);

        setAddAssetComponentsEditable(false);

        return pnlAddAsset;
    }

    /**
     * Adds the text components used to create a new asset to a panel.
     * @return a panel containing labeled text components used to create an asset type.
     */
    private JPanel makeAddAssetFieldsPanel() {
        // All the text edit fields will be added to pnlAddAssetFields
        JPanel pnlAddAssetFields = new JPanel();
        GroupLayout layout = new GroupLayout(pnlAddAssetFields);
        pnlAddAssetFields.setLayout(layout);
        pnlAddAssetFields.setBackground(DARK_JUNGLE_GREEN);

        pnlAddAssetFields.setPreferredSize(new Dimension(PANEL_PREFERRED_WIDTH, 25));
        pnlAddAssetFields.setMinimumSize(new Dimension(PANEL_MIN_WIDTH, 25));

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        JLabel lblAssetName = new JLabel("Asset Name");
        lblAssetName.setForeground(Color.white);

        txtAssetName = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(lblAssetName));
        hGroup.addGroup(layout.createParallelGroup().addComponent(txtAssetName));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblAssetName).addComponent(txtAssetName));
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
        pnlButton.setPreferredSize(new Dimension(PANEL_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
        pnlButton.setMinimumSize(new Dimension(PANEL_MIN_WIDTH, BUTTON_MIN_HEIGHT));
        GridBagConstraints position = new GridBagConstraints();

        btnAddAsset = new JButton("Create New Asset");
        btnAddAsset.setPreferredSize(new Dimension(BUTTON_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
        btnAddAsset.setBackground(cust1);
        btnSaveNewAsset = new JButton("Save New Asset");
        btnSaveNewAsset.setPreferredSize(new Dimension(BUTTON_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
        btnSaveNewAsset.setBackground(cust1);

        btnAddAsset.addActionListener(listener);
        btnSaveNewAsset.addActionListener(listener);

        position.gridx = 0;
        position.anchor = GridBagConstraints.LINE_START;
        pnlButton.add(btnAddAsset, position);

        position.gridx = 1;
        position.anchor = GridBagConstraints.LINE_END;
        pnlButton.add(btnSaveNewAsset, position);

        // add blank component to fill the space between the buttons
        position.gridx = 1;
        position.anchor = GridBagConstraints.CENTER;
        position.weightx = 100.0;
        position.gridwidth = GridBagConstraints.RELATIVE;
        pnlButton.add(Box.createGlue(), position);

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

    //region Create new Organisational Unit

    /**
     * Creates the whole create Organisational Unit panel, including the label of the panel, the edit fields, and the buttons
     * @return an Add Asset panel
     */
    private JPanel makeCreateOrgUnitPanel() {
        // pnlCreateOrgUnit will contain 3 sub-sections: The Panel title, the editable fields, and the add / save buttons
        JPanel pnlCreateOrgUnit = new JPanel();
        pnlCreateOrgUnit.setLayout(new GridBagLayout());
        pnlCreateOrgUnit.setBackground(DARK_JUNGLE_GREEN);
        pnlCreateOrgUnit.setBorder(CreatePanelBorder("Add New Organisational Unit"));
        GridBagConstraints position = new GridBagConstraints();

        position.gridy = 1;
        position.insets = new Insets(0, 0, 20, 0);
        pnlCreateOrgUnit.add(makeCreateOrgUnitFieldsPanel(), position);

        position.gridy = 2;
        position.insets = new Insets(0, 0, 0, 0);
        pnlCreateOrgUnit.add(makeCreateOrgUnitButtonsPanel(), position);

        setCreateOrgUnitComponentsEditable(false);

        return pnlCreateOrgUnit;
    }

    /**
     * Adds the text components used to create new Organisational Unit to a panel.
     * @return a panel containing labeled text components used to create new Organisational Unit.
     */
    private JPanel makeCreateOrgUnitFieldsPanel() {
        // All the text edit fields will be added to pnlCreateOrgUnitFields
        JPanel pnlCreateOrgUnitFields = new JPanel();
        GroupLayout layout = new GroupLayout(pnlCreateOrgUnitFields);
        pnlCreateOrgUnitFields.setLayout(layout);
        pnlCreateOrgUnitFields.setBackground(DARK_JUNGLE_GREEN);

        pnlCreateOrgUnitFields.setPreferredSize(new Dimension(PANEL_PREFERRED_WIDTH, 25));
        pnlCreateOrgUnitFields.setMinimumSize(new Dimension(PANEL_MIN_WIDTH, 25));

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        JLabel lblOrgName = new JLabel("Organisational Unit Name");

        lblOrgName.setForeground(Color.white);

        txtCreateOrgUnitName = new JTextField(20);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(lblOrgName));
        hGroup.addGroup(layout.createParallelGroup().addComponent(txtCreateOrgUnitName));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblOrgName).addComponent(txtCreateOrgUnitName));
        layout.setVerticalGroup(vGroup);

        return pnlCreateOrgUnitFields;
    }

    /**
     * Adds the Create Org Unit and Save New Org Unit buttons to a panel
     * @return a panel containing Create Org Unit and Save New Org buttons.
     */
    private JPanel makeCreateOrgUnitButtonsPanel(){
        JPanel pnlButton = new JPanel();
        pnlButton.setLayout(new GridBagLayout());
        pnlButton.setBackground(DARK_JUNGLE_GREEN);
        pnlButton.setPreferredSize(new Dimension(PANEL_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
        pnlButton.setMinimumSize(new Dimension(PANEL_MIN_WIDTH, BUTTON_MIN_HEIGHT));
        GridBagConstraints position = new GridBagConstraints();

        btnCreateOrgUnit = new JButton("Create New Org Unit");
        btnCreateOrgUnit.setPreferredSize(new Dimension(BUTTON_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
        btnCreateOrgUnit.setBackground(cust1);
        btnSaveNewOrgUnit = new JButton("Save New Org Unit");
        btnSaveNewOrgUnit.setPreferredSize(new Dimension(BUTTON_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
        btnSaveNewOrgUnit.setBackground(cust1);

        btnCreateOrgUnit.addActionListener(listener);
        btnSaveNewOrgUnit.addActionListener(listener);

        position.gridx = 0;
        position.anchor = GridBagConstraints.LINE_START;
        pnlButton.add(btnCreateOrgUnit, position);

        position.gridx = 1;
        position.anchor = GridBagConstraints.LINE_END;
        pnlButton.add(btnSaveNewOrgUnit, position);

        // add blank component to fill the space between the buttons
        position.gridx = 1;
        position.anchor = GridBagConstraints.CENTER;
        position.weightx = 100.0;
        position.gridwidth = GridBagConstraints.RELATIVE;
        pnlButton.add(Box.createGlue(), position);

        return pnlButton;
    }

    /**
     * Sets whether or not the Create Org Unit fields are editable.
     */
    private void setCreateOrgUnitComponentsEditable(boolean editable) {
        txtCreateOrgUnitName.setEditable(editable);

        txtCreateOrgUnitName.setEnabled(editable);
        btnSaveNewOrgUnit.setEnabled(editable);
        btnCreateOrgUnit.setEnabled(editable == false);

        // If editable, set the background to a lighter colour, else dark colour
        if (editable)
            txtCreateOrgUnitName.setBackground(Color.WHITE);
        else
            txtCreateOrgUnitName.setBackground(UIManager.getColor("TextField.Background"));
    }

    //endregion

    //region Edit Organisational Unit's Credits

    /**
     * Creates the whole edit Org Unit credits panel, including the label of the panel, the edit fields, and the buttons
     * @return an edit Org Unit credits panel
     */
    private JPanel makeEditOrgUnitCreditsPanel() {
        // pnlEditCredits will contain 3 sub-sections: The Panel title, the editable fields, and the save button
        JPanel pnlEditCredits = new JPanel();
        pnlEditCredits.setLayout(new GridBagLayout());
        pnlEditCredits.setBackground(DARK_JUNGLE_GREEN);
        pnlEditCredits.setBorder(CreatePanelBorder("Manage Organisational Unit's Credits"));
        GridBagConstraints position = new GridBagConstraints();

        position.gridy = 1;
        position.insets = new Insets(0, 0, 20, 0);
        pnlEditCredits.add(makeEditOrgUnitCreditsFieldsPanel(), position);

        position.gridy = 2;
        position.insets = new Insets(0, 0, 0, 0);
        pnlEditCredits.add(makeEditOrgUnitCreditsButtonsPanel(), position);

        return pnlEditCredits;
    }

    /**
     * Adds the text components used to edit Org Unit credits to a panel.
     * @return a panel containing labeled text components used to edit Org Unit credits.
     */
    private JPanel makeEditOrgUnitCreditsFieldsPanel() {
        // All the text edit fields will be added to pnlCreateOrgUnitFields
        JPanel pnlCreateOrgUnitFields = new JPanel();
        GroupLayout layout = new GroupLayout(pnlCreateOrgUnitFields);
        pnlCreateOrgUnitFields.setLayout(layout);
        pnlCreateOrgUnitFields.setBackground(DARK_JUNGLE_GREEN);
        pnlCreateOrgUnitFields.setPreferredSize(new Dimension(PANEL_PREFERRED_WIDTH, 50));
        pnlCreateOrgUnitFields.setMinimumSize(new Dimension(PANEL_MIN_WIDTH, 50));

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        JLabel lblOrgName = new JLabel("Organisational Unit");
        JLabel lblCredits = new JLabel("Credits");

        lblOrgName.setForeground(Color.white);
        lblCredits.setForeground(Color.white);

        cboxCreditsOrgUnit = new JComboBox<>(AccountTypes);
        cboxCreditsOrgUnit = new JComboBox<>(getOrgUnitNames());
        cboxCreditsOrgUnit.setEditable(false);
        txtCredits = new JTextField(20);

        cboxCreditsOrgUnit.addActionListener(listener);
        cboxCreditsOrgUnit.setSelectedIndex(0);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(lblOrgName).addComponent(lblCredits));
        hGroup.addGroup(layout.createParallelGroup().addComponent(cboxCreditsOrgUnit).addComponent(txtCredits));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblOrgName).addComponent(cboxCreditsOrgUnit));
        vGroup.addGroup((layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblCredits).addComponent(txtCredits)));
        layout.setVerticalGroup(vGroup);

        return pnlCreateOrgUnitFields;
    }

    /**
     * Adds the edit Org Unit credits button to a panel
     * @return a panel containing edit Org Unit credits button.
     */
    private JPanel makeEditOrgUnitCreditsButtonsPanel(){
        JPanel pnlButton = new JPanel();
        pnlButton.setLayout(new GridBagLayout());
        pnlButton.setBackground(DARK_JUNGLE_GREEN);
        pnlButton.setPreferredSize(new Dimension(PANEL_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
        pnlButton.setMinimumSize(new Dimension(PANEL_MIN_WIDTH, BUTTON_MIN_HEIGHT));
        GridBagConstraints position = new GridBagConstraints();

        btnUpdateCredits = new JButton("Update Credits");
        btnUpdateCredits.setPreferredSize(new Dimension(BUTTON_PREFERRED_WIDTH, BUTTON_PREFERRED_HEIGHT));
        btnUpdateCredits.setBackground(cust1);

        btnUpdateCredits.addActionListener(listener);

        position.gridx = 1;
        position.anchor = GridBagConstraints.LINE_END;
        pnlButton.add(btnUpdateCredits, position);

        // add blank component to fill the space between the buttons
        position.gridx = 1;
        position.anchor = GridBagConstraints.CENTER;
        position.weightx = 100.0;
        position.gridwidth = GridBagConstraints.RELATIVE;
        pnlButton.add(Box.createGlue(), position);

        return pnlButton;
    }

    //endregion

    //region Helper methods

    /**
     * Updates the available fields for the org unit combo boxes.
     */
    private void updateOrgUnitComboBoxes(){
        int userOrgIndex = cboxUserOrgUnitName.getSelectedIndex();
        int creditOrgIndex = cboxCreditsOrgUnit.getSelectedIndex();

        var orgUnitNamesModel = new DefaultComboBoxModel<>(getOrgUnitNames());
        cboxUserOrgUnitName.setModel(orgUnitNamesModel);
        cboxUserOrgUnitName.setSelectedIndex(userOrgIndex);
        cboxCreditsOrgUnit.setModel(orgUnitNamesModel);
        cboxCreditsOrgUnit.setSelectedIndex(creditOrgIndex);
    }

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

    // Creates a titled border with a white centered title
    private CompoundBorder CreatePanelBorder(String titleText){
        TitledBorder title = BorderFactory.createTitledBorder(titleText);
        title.setTitleJustification(TitledBorder.CENTER);
        title.setTitleColor(Color.WHITE);
        // Also add some padding
        return BorderFactory.createCompoundBorder(title, new EmptyBorder(10, 10, 10, 10));
    }

    //endregion

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
                    saveUserPressed();
                } else if (source == btnAddAsset) {
                    setAddAssetComponentsEditable(true);
                } else if (source == btnSaveNewAsset) {
                    saveAssetPressed();
                } else if (source == btnUpdatePassword) {
                    changePasswordPressed();
                } else if (source == btnCreateOrgUnit) {
                    setCreateOrgUnitComponentsEditable(true);
                } else if (source == btnSaveNewOrgUnit) {
                    saveOrgUnitPressed();
                } else if (source == btnUpdateCredits) {
                    updateCreditsPressed();
                }
            } else if (src instanceof JComboBox){
                JComboBox source = (JComboBox) e.getSource();
                if (source == cboxUserAccountType){
                    String selectedAccountType = (String)source.getSelectedItem();
                    if (AccountType.ADMINISTRATOR.toString().equals(selectedAccountType)) {
                        addUserIsMember = false;
                        cboxUserOrgUnitName.setSelectedItem(admin.getOrganisationalUnit().getName());
                    }
                    else
                        addUserIsMember = true;
                    setAddUserComponentsEditable(true);
                } else if (source == cboxCreditsOrgUnit){
                    // Set the credits text to the org unit's current credits
                    var orgUnit = OrganisationUnits.get(cboxCreditsOrgUnit.getSelectedIndex());
                    txtCredits.setText(Integer.toString(orgUnit.getCredits()));
                }
            }
        }

        /**
         * When the save user button is pressed, check that all fields are filled.
         * If they are, create a new Member or Admin depending on the account type
         * selected.
         */
        private void saveUserPressed() {
            if (txtAddUserUsername.getText() != null && !txtAddUserUsername.getText().equals("")
                    && txtAddUserPassword.getText() != null && !txtAddUserPassword.getText().equals("")
                    && cboxUserOrgUnitName.getSelectedIndex() >= 0) {
                // Get the text fields (username and password)
                String username = txtAddUserUsername.getText();
                String hashedPassword = SHA256.hashPassword(txtAddUserPassword.getText());

                // If member, add new member, else add admin
                boolean success = false;
                if (AccountType.MEMBER.toString().equals(cboxUserAccountType.getSelectedItem())){
                    // Get the org unit
                    OrganisationalUnit unit = OrganisationUnits.get(cboxUserOrgUnitName.getSelectedIndex());
                    // Create the new member
                    success = admin.CreateNewMember(txtAddUserUsername.getText(), SHA256.hashPassword(txtAddUserPassword.getText()), unit);
                } else if (AccountType.ADMINISTRATOR.toString().equals(cboxUserAccountType.getSelectedItem())){
                    success = admin.CreateNewITAdmin(username, hashedPassword);
                }

                // If adding user was successful, display message and clear the fields.
                if (success) {
                    JOptionPane.showMessageDialog(pnlAdmin, username + " has been added to " + cboxUserOrgUnitName.getSelectedItem(),
                            "Add user", JOptionPane.INFORMATION_MESSAGE);
                    txtAddUserPassword.setText("");
                    txtAddUserUsername.setText("");
                    setAddUserComponentsEditable(false);
                } else { // Adding user was not successful, show error message.
                    JOptionPane.showMessageDialog(pnlAdmin, username + " could not be added. Please try a different username.",
                            "Add user", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(pnlAdmin, "Please enter enter the user's username, password, and Organisational Unit.",
                        "Add user", JOptionPane.WARNING_MESSAGE);
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
            } else {
                JOptionPane.showMessageDialog(pnlAdmin, "Please enter name of the new asset.",
                        "Create Asset", JOptionPane.WARNING_MESSAGE);
            }
        }

        /**
         * Checks that the username and password field is filled, then attempts to change
         * the user's password. Displays a message saying whether the password was
         * successfully changed or not.
         */
        private void changePasswordPressed() {
            if (txtUpdatePasswordUsername.getText() != null && !txtUpdatePasswordUsername.getText().equals("")
                    && txtUpdatePasswordPassword.getText() != null && !txtUpdatePasswordPassword.getText().equals("")) {

                // Get the text fields (username and password)
                String username = txtUpdatePasswordUsername.getText();
                String hashedPassword = SHA256.hashPassword(txtUpdatePasswordPassword.getText());

                boolean success = admin.ChangeUserPassword(username, hashedPassword);
                if (success){
                    JOptionPane.showMessageDialog(pnlAdmin, username + "'s password has been updated.",
                            "Change password", JOptionPane.INFORMATION_MESSAGE);
                    txtUpdatePasswordUsername.setText("");
                    txtUpdatePasswordPassword.setText("");
                } else {
                    JOptionPane.showMessageDialog(pnlAdmin, username + "'s password could not be changed. " +
                                    "Please check for the correct username.",
                            "Change password", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(pnlAdmin, "Please enter enter the user's username and new password.",
                        "Change password", JOptionPane.WARNING_MESSAGE);
            }
        }

        /**
         * When the save new Org Unit button is pressed, check that all fields are filled.
         * If they are, create a new org unit type.
         */
        private void saveOrgUnitPressed() {
            if (txtCreateOrgUnitName.getText() != null && !txtCreateOrgUnitName.getText().equals("")) {
                String orgUnitName = txtCreateOrgUnitName.getText();
                int OrgUnitId = admin.CreateOrganisationalUnit(orgUnitName);

                if (OrgUnitId != -1) {
                    JOptionPane.showMessageDialog(pnlAdmin, orgUnitName + " has been added as a new Organisational Unit!",
                            "Create Organisational Unit", JOptionPane.INFORMATION_MESSAGE);
                    txtCreateOrgUnitName.setText("");
                    setCreateOrgUnitComponentsEditable(false);

                    // Add the organisational unit to the combo box list
                    OrganisationUnits.add(new OrganisationalUnit(OrgUnitId));
                    updateOrgUnitComboBoxes();
                } else {
                    JOptionPane.showMessageDialog(pnlAdmin, orgUnitName + " could not be added. " +
                                    "Please check that the organisation unit does not already exist.",
                            "Create Organisational", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(pnlAdmin, "Please enter name of the new organisational unit.",
                        "Create Organisational Unit", JOptionPane.WARNING_MESSAGE);
            }
        }

        /**
         * When the save update Credits button is pressed, check that all fields are filled,
         * and that the credits text is an int. If they are, create a new org unit type.
         */
        private void updateCreditsPressed() {
            if (txtCredits.getText() != null && !txtCredits.getText().equals("")) {
                int credits;
                try {
                    credits = Integer.parseInt(txtCredits.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(pnlAdmin, txtCredits.getText() + " is not a valid credit amount. " +
                                    "Please type in a whole number.",
                            "Edit Credits", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                OrganisationalUnit orgUnit = OrganisationUnits.get(cboxCreditsOrgUnit.getSelectedIndex());
                admin.EditOrganisationalUnitCredits(orgUnit, credits);

                JOptionPane.showMessageDialog(pnlAdmin, orgUnit.getName() + " now has " + credits + " credits!",
                        "Edit Credits", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(pnlAdmin, "Please enter the number of credits the the organisational unit will be given.",
                        "Edit Credits", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
