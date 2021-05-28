package TradingPlatform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import static TradingPlatform.GUIMain.*;

public class GUIProfile {

    private JTextField txtUsername;
    private JTextField txtOrgUnitName;
    private JTextField txtAccountType;
    private JTextField txtCurrentPassword;
    private JTextField txtNewPassword;

    private JButton btnChangePassword;
    private JButton btnSave;

    public GUIProfile(JPanel ProfileTab){
        profilePanel(ProfileTab);
    }

    /**
     * Makes a JPanel consisting of the user's information, and text fields to change
     * their password in a box layout with horizontal alignment and puts a 20 pixel
     * gap between the components and the left and right edges of the panel.
     */
    private void profilePanel(JPanel pnlProfile){
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



//        pnlProfile.setLayout(new BoxLayout(pnlProfile, BoxLayout.Y_AXIS));
//        pnlProfile.add(Box.createVerticalStrut(20));
//        pnlProfile.add(makeUserDetailsPanel());
//        pnlProfile.add(Box.createVerticalStrut(20));
//        pnlProfile.add(makeButtonsPanel());
//        pnlProfile.add(Box.createVerticalStrut(20));
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
        pnlUserDetails.setBackground(cust2);

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        // Turn on automatically creating gaps between components that touch
        // the edge of the container and the container.
        layout.setAutoCreateContainerGaps(true);

        JLabel lblUsername = new JLabel("Username");
        JLabel lblOrgUnitName = new JLabel("Organisation Unit");
        JLabel lblAccountType = new JLabel("Account Type");
        JLabel lblCurrentPassword = new JLabel("Current Password");
        JLabel lblNewPassword = new JLabel("New Password");

        txtUsername = new JTextField(20);
        txtOrgUnitName = new JTextField(20);
        txtAccountType = new JTextField(20);
        txtCurrentPassword = new JTextField(20);
        txtNewPassword = new JTextField(20);

        lblUsername.setForeground(Color.white);
        lblOrgUnitName.setForeground(Color.white);
        lblAccountType.setForeground(Color.white);
        lblCurrentPassword.setForeground(Color.white);
        lblNewPassword.setForeground(Color.white);

        txtUsername.setEditable(false);
        txtOrgUnitName.setEditable(false);
        txtAccountType.setEditable(false);
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

    /**
     * Adds the ChangePassword and SavePassword buttons to a panel
     * @return
     */
    private JPanel makeButtonsPanel(){
        JPanel pnlButton = new JPanel();
        pnlButton.setLayout(new GridBagLayout());
        pnlButton.setBackground(cust2);
        GridBagConstraints position = new GridBagConstraints();

        btnChangePassword = new JButton("Change Password");
        btnChangePassword.setPreferredSize(new Dimension(200, 100));
        btnChangePassword.setBackground(cust1);
        btnSave = new JButton("Save");
        btnSave.setPreferredSize(new Dimension(200, 100));
        btnSave.setBackground(cust1);

        btnSave.setEnabled(false);

        position.gridx = 0;
        position.anchor = GridBagConstraints.LINE_START;
        position.insets = new Insets(0, 0, 0, width/2 - 400);
        pnlButton.add(btnChangePassword, position);

        position.gridx = 1;
        position.anchor = GridBagConstraints.LINE_END;
        position.insets = new Insets(0, 0, 0, 0);
        pnlButton.add(btnSave, position);

//        pnlButton.add(Box.createHorizontalStrut(50));
//        pnlButton.add(btnChangePassword);
//        pnlButton.add(Box.createHorizontalStrut(50));
//        pnlButton.add(btnSave);
//        pnlButton.add(Box.createHorizontalStrut(50));
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
}
