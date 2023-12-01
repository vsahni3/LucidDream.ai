package view;

import javax.swing.*;
import java.awt.*;

/**

 A panel containing a label and a text field.*/
class LabelTextPanel extends JPanel {
    LabelTextPanel(JLabel label, JTextField textField) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(label);
        this.add(Box.createRigidArea(new Dimension(30, 0)));
        this.add(textField);

        textField.setPreferredSize(new Dimension(200, 30));
        label.setFont(new Font("SansSerif", Font.PLAIN, 20));

        this.setAlignmentX(Component.RIGHT_ALIGNMENT); // Consistent alignment


        // Set this panel's maximum height to the preferred height to avoid vertical expansion
        this.setMinimumSize(new Dimension(this.getPreferredSize().width, this.getPreferredSize().height));
        this.setMaximumSize(new Dimension(this.getPreferredSize().width, this.getPreferredSize().height));

    }
}
