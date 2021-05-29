package TradingPlatform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static TradingPlatform.GUIMain.*;

public class GUIProfile {

    private JPanel pnlProfile;

    private JTextField txtUsername;
    private JTextField txtOrgUnitName;
    private JTextField txtAccountType;
    private JTextField txtCurrentPassword;
    private JTextField txtNewPassword;

    private JButton btnChangePassword;
    private JButton btnSave;

    private User user;


    public GUIProfile(JPanel ProfileTab, User user){
        profilePanel(ProfileTab);
        this.user = user;
        display(user);
    }

    /**
     * Makes a JPanel consisting of the user's information, text fields to change
     * their password, as well as buttons to change and save their password in a grid bag layout.
     */
    private void profilePanel(JPanel ProfilePanel){
        pnlProfile = ProfilePanel;
        pnlProfile.setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        position.gridy = 0;
        position.anchor = GridBagConstraints.CENTER;
        position.insets = new Insets(0, 0, 50, 0);
        var pnlDetails = makeUserDetailsPanel();
        pnlDetails.setPreferredSize(new Dimension(width / 2, 150));
        pnlProfile.add(pnlDetails, position);

        position.gridy = 1;
        position.anchor = GridBagConstraints.CENTER;
        position.insets = new Insets(0, 0, 0, 0);
        var pnlButtons = makeButtonsPanel();
        pnlButtons.setPreferredSize(new Dimension(width / 2, 100));
        pnlProfile.add(pnlButtons, position);

    }


    /**
     * Makes a JPanel containing labels and text fields for each of the pieces of
     * data for the user. The labels and fields are laid out using a GroupLayout,
     * with the labels vertically grouped, the fields vertically grouped and each
     * label/group pair horizontally grouped.
     *
     * @return a panel containing the user's detail fields
     */
    private JPanel makeUserDetailsPanel() {
        JPanel pnlUserDetails = new JPanel();
        GroupLayout layout = new GroupLayout(pnlUserDetails);
        pnlUserDetails.setLayout(layout);
        pnlUserDetails.setBackground(DARK_JUNGLE_GREEN);

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        // Turn on automatically creating gaps between components that touch
        // the edge of the container and the container.
//        layout.setAutoCreateContainerGaps(true);

        JLabel lblUsername = new JLabel("Username");
        JLabel lblOrgUnitName = new JLabel("Organisation Unit");
        JLabel lblAccountType = new JLabel("Account Type");
        JLabel lblCurrentPassword = new JLabel("Current Password");
        JLabel lblNewPassword = new JLabel("New Password");

        lblUsername.setForeground(Color.white);
        lblOrgUnitName.setForeground(Color.white);
        lblAccountType.setForeground(Color.white);
        lblCurrentPassword.setForeground(Color.white);
        lblNewPassword.setForeground(Color.white);

        txtUsername = new JTextField(20);
        txtOrgUnitName = new JTextField(20);
        txtAccountType = new JTextField(20);
        txtCurrentPassword = new JTextField(20);
        txtNewPassword = new JTextField(20);

        setTextFieldsAppearance();

        setPasswordFieldsEditable(false);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(lblUsername)
                .addComponent(lblOrgUnitName).addComponent(lblAccountType).addComponent(
                        lblCurrentPassword).addComponent(lblNewPassword));
        hGroup.addGroup(layout.createParallelGroup().addComponent(txtUsername)
                .addComponent(txtOrgUnitName).addComponent(txtAccountType).addComponent(txtCurrentPassword)
                .addComponent(txtNewPassword));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        // The sequential group contains five parallel groups that align
        // the contents along the baseline. The first parallel group contains
        // the first label and text field, and the second parallel group contains
        // the second label and text field etc. By using a sequential group
        // the labels and text fields are positioned vertically after one another.
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblUsername).addComponent(txtUsername));

        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblOrgUnitName).addComponent(txtOrgUnitName));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblAccountType).addComponent(txtAccountType));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblCurrentPassword).addComponent(txtCurrentPassword));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblNewPassword).addComponent(txtNewPassword));
        layout.setVerticalGroup(vGroup);

        return pnlUserDetails;
    }

    private void setTextFieldsAppearance(){
        txtUsername.setBackground(UIManager.getColor("TextField.Background"));
        txtOrgUnitName.setBackground(UIManager.getColor("TextField.Background"));
        txtAccountType.setBackground(UIManager.getColor("TextField.Background"));

        txtUsername.setMinimumSize(new Dimension(500, 20));
        txtOrgUnitName.setMinimumSize(new Dimension(500, 20));
        txtAccountType.setMinimumSize(new Dimension(500, 20));
        txtCurrentPassword.setMinimumSize(new Dimension(500, 20));
        txtNewPassword.setMinimumSize(new Dimension(500, 20));

        txtUsername.setForeground(Color.WHITE);
        txtOrgUnitName.setForeground(Color.WHITE);
        txtAccountType.setForeground(Color.WHITE);

        txtUsername.setEditable(false);
        txtOrgUnitName.setEditable(false);
        txtAccountType.setEditable(false);

        txtUsername.setEnabled(false);
        txtOrgUnitName.setEnabled(false);
        txtAccountType.setEnabled(false);
    }

    /**
     * Adds the ChangePassword and SavePassword buttons to a panel
     * @return a new panel containing Change Password and Save Password buttons.
     */
    private JPanel makeButtonsPanel(){
        JPanel pnlButton = new JPanel();
        pnlButton.setLayout(new GridBagLayout());
        pnlButton.setBackground(DARK_JUNGLE_GREEN);
        GridBagConstraints position = new GridBagConstraints();

        btnChangePassword = new JButton("Change Password");
        btnChangePassword.setPreferredSize(new Dimension(200, 100));
        btnChangePassword.setBackground(cust1);
        btnSave = new JButton("Save");
        btnSave.setPreferredSize(new Dimension(200, 100));
        btnSave.setBackground(cust1);

        btnSave.setEnabled(false);
        addButtonListeners(new ButtonListener());

        position.gridx = 0;
        position.anchor = GridBagConstraints.LINE_START;
        position.insets = new Insets(0, 0, 0, width/2 - 400);
        pnlButton.add(btnChangePassword, position);

        position.gridx = 1;
        position.anchor = GridBagConstraints.LINE_END;
        position.insets = new Insets(0, 0, 0, 0);
        pnlButton.add(btnSave, position);

        return pnlButton;
    }

    /**
     * Adds a listener to the new, save and delete buttons
     */
    private void addButtonListeners(ActionListener listener) {
        btnChangePassword.addActionListener(listener);
        btnSave.addActionListener(listener);
    }

    /**
     * Sets whether or not the password fields are editable.
     */
    private void setPasswordFieldsEditable(boolean editable) {
        txtCurrentPassword.setEditable(editable);
        txtNewPassword.setEditable(editable);
        if (editable){
            txtCurrentPassword.setBackground(Color.WHITE);
            txtNewPassword.setBackground(Color.WHITE);
        }
        else {
            txtCurrentPassword.setBackground(UIManager.getColor("TextField.Background"));
            txtNewPassword.setBackground(UIManager.getColor("TextField.Background"));
        }
    }

    /**
     * Sets the text in the password fields to the empty string.
     */
    private void clearPasswordFields() {
        txtCurrentPassword.setText("");
        txtNewPassword.setText("");
    }

    /**
     * Displays the details of a User.
     * @param user the User to display.
     */
    private void display(User user) {
        if (user != null) {
            txtUsername.setText(user.getUsername());
            txtOrgUnitName.setText(user.getOrganisationalUnit().organisationName);
            txtAccountType.setText(user.getAccountType().name());
        }
    }

    private class ButtonListener implements ActionListener {

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            if (source == btnChangePassword) {
                changePasswordPressed();
            } else if (source == btnSave) {
                savePressed();
            }
        }

        /**
         * When the change password button is pressed, enable the password text fields
         * and the save button.
         */
        private void changePasswordPressed() {
            setPasswordFieldsEditable(true);
            btnSave.setEnabled(true);
            btnChangePassword.setEnabled(false);
        }

        /**
         * When the save button is pressed, check that both password fields contains
         * something. If they do, attempt to verify the user's current password, and if
         * verified change their password. If successfully updated, change the fields back
         * to not editable, make the save button inactive and clear the password fields.
         */
        private void savePressed() {
            if (txtCurrentPassword.getText() != null && !txtCurrentPassword.getText().equals("")
                && txtNewPassword.getText() != null && !txtNewPassword.getText().equals("")) {
                if (user.ChangePassword(txtCurrentPassword.getText(), txtNewPassword.getText())){
                    JOptionPane.showMessageDialog(pnlProfile, "Your password has been changed!",
                            "Change Password", JOptionPane.INFORMATION_MESSAGE);
                    setPasswordFieldsEditable(false);
                    btnSave.setEnabled(false);
                    btnChangePassword.setEnabled(true);
                    clearPasswordFields();
                }
                else {
                    JOptionPane.showMessageDialog(pnlProfile, "Invalid current password! Please try again.",
                            "Change Password", JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                JOptionPane.showMessageDialog(pnlProfile, "Please enter your current and new passwords.",
                        "Change Password", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
