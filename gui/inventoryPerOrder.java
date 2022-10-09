import java.awt.*;
import javax.swing.*;
import javax.xml.catalog.CatalogFeatures.Feature;
import java.awt.event.*;

/* Array Index 
 * 0 - Rice
 * 1 - Chicken
 * 2 - Sp Meatballs
 * 3 - Cucumbers
 * 4 - Cauliflower
 * 5 - Tomato
 * 6 - Spinach
 * 7 - Kale
 * 8 - Cabbage
 * 9 - Romaine
 * 10 - Banana Peppers
 * 11 - Feta Cheese
 * 12 - Harissa
 * 13 - Tzatziki Sauce
 * 14 - Balsamic Vinegar
 */

public class inventoryPerOrder implements ActionListener {
    private void makeFrameFullSize(JFrame aFrame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        aFrame.setSize(screenSize.width, screenSize.height);
    }

    // Frame Declaraiton
    JFrame f = new JFrame("Inventory GUI");

    // Button Declaration
    JButton riceBtn = new JButton("Rice");
    JButton chickenBtn = new JButton("Chicken");
    JButton meatballsBtn = new JButton("Sp Meatballs");
    JButton cucumbersBtn = new JButton("Cucumbers");
    JButton cauliflowerBtn = new JButton("Cauliflower");
    JButton tomatoBtn = new JButton("Tomato");
    JButton spinachBtn = new JButton("Spinach");
    JButton kaleBtn = new JButton("Kale");
    JButton cabbageBtn = new JButton("Cabbage");
    JButton romaineBtn = new JButton("Romaine");
    JButton peppersBtn = new JButton("Banana Peppers");
    JButton cheeseBtn = new JButton("Feta Cheese");
    JButton harissaBtn = new JButton("Harissa");
    JButton sauceBtn = new JButton("Tzatziki Sauce");
    JButton vinegarBtn = new JButton("Balsamic Vinegar");
    JButton backBtn = new JButton("GO BACK");
    JButton submitBtn = new JButton("SUBMIT");

    JButton btnArr[] = { riceBtn, chickenBtn, meatballsBtn, cucumbersBtn, cauliflowerBtn,
            tomatoBtn, spinachBtn, kaleBtn, cabbageBtn, romaineBtn, peppersBtn, cheeseBtn,
            harissaBtn, sauceBtn, vinegarBtn, backBtn, submitBtn };

    // Label Declaration
    JLabel riceLabel = new JLabel("Rice");
    JLabel chickenLabel = new JLabel("Chicken");
    JLabel meatballsLabel = new JLabel("Sp Meatballs");
    JLabel cucumbersLabel = new JLabel("Cucumbers");
    JLabel cauliflowerLabel = new JLabel("Cauliflower");
    JLabel tomatoLabel = new JLabel("Tomato");
    JLabel spinachLabel = new JLabel("Spinach");
    JLabel kaleLabel = new JLabel("Kale");
    JLabel cabbageLabel = new JLabel("Cabbage");
    JLabel romaineLabel = new JLabel("Romaine");
    JLabel peppersLabel = new JLabel("Banana Peppers");
    JLabel cheesesLabel = new JLabel("Feta Cheese");
    JLabel harissaLabel = new JLabel("Harissa");
    JLabel sauceLabel = new JLabel("Tzatziki Sauce");
    JLabel vinegarLabel = new JLabel("Balsamic Vinegar");

    JLabel labelArr[] = { riceLabel, chickenLabel, meatballsLabel, cucumbersLabel, cauliflowerLabel,
            tomatoLabel, spinachLabel, kaleLabel, cabbageLabel, romaineLabel, peppersLabel,
            cheesesLabel, harissaLabel, sauceLabel, vinegarLabel };

    // Input Declaration
    JTextField riceInput = new JTextField("1");
    JTextField chickenInput = new JTextField("1");
    JTextField meatballsInput = new JTextField("1");
    JTextField cucumbersInput = new JTextField("1");
    JTextField cauliflowerInput = new JTextField("1");
    JTextField tomatoInput = new JTextField("1");
    JTextField spinachInput = new JTextField("1");
    JTextField kaleInput = new JTextField("1");
    JTextField cabbageInput = new JTextField("1");
    JTextField romaineInput = new JTextField("1");
    JTextField peppersInput = new JTextField("1");
    JTextField cheesesInput = new JTextField("1");
    JTextField harissaInput = new JTextField("1");
    JTextField sauceInput = new JTextField("1");
    JTextField vinegarInput = new JTextField("1");

    JTextField inputArr[] = { riceInput, chickenInput, meatballsInput, cucumbersInput, cauliflowerInput,
            tomatoInput, spinachInput, kaleInput, cabbageInput, romaineInput, peppersInput,
            cheesesInput, harissaInput, sauceInput, vinegarInput };

    // Global Vars
    int userId = 0;
    JLabel title = new JLabel("Welcome User " + userId);
    int height = 0;
    /* Store quantity of each inventory item */
    int quantityArray[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

    inventoryPerOrder() {
        // Frame setting
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        f.setSize(screenSize.width, screenSize.height);
        f.setBackground(Color.gray);
        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
        title.setBounds(30, 5, 200, 60);

        // Add to the frame and addActionLisener
        for (int i = 0; i < 15; ++i) {
            f.add(btnArr[i]);
            f.add(labelArr[i]);
            f.add(inputArr[i]);
            btnArr[i].addActionListener(this);
        }
        f.add(title);
        f.add(backBtn);
        f.add(submitBtn);
        backBtn.addActionListener(this);
        submitBtn.addActionListener(this);

        // Position of each btn
        riceBtn.setBounds(50, 60, 120, 80);

        chickenBtn.setBounds(50, 160, 120, 80);
        meatballsBtn.setBounds(190, 160, 120, 80);

        cucumbersBtn.setBounds(50, 270, 120, 80);
        cauliflowerBtn.setBounds(190, 270, 120, 80);
        tomatoBtn.setBounds(330, 270, 120, 80);
        spinachBtn.setBounds(470, 270, 120, 80);

        kaleBtn.setBounds(50, 360, 120, 80);
        cabbageBtn.setBounds(190, 360, 120, 80);
        romaineBtn.setBounds(330, 360, 120, 80);
        peppersBtn.setBounds(470, 360, 120, 80);
        cheeseBtn.setBounds(50, 450, 120, 80);

        harissaBtn.setBounds(50, 550, 120, 80);
        sauceBtn.setBounds(190, 550, 120, 80);
        vinegarBtn.setBounds(330, 550, 120, 80);

        backBtn.setBounds(10, 850, 300, 60);
        submitBtn.setBounds(1000, 800, 300, 80);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == riceBtn) {
            riceLabel.setBounds(1000, 40 + height, 100, 30);
            inputArr[0].setBounds(1200, 40 + height, 100, 30);
            riceBtn.setEnabled(false);
        } else if (e.getSource() == chickenBtn) {
            chickenLabel.setBounds(1000, 40 + height, 100, 30);
            inputArr[1].setBounds(1200, 40 + height, 100, 30);
            chickenBtn.setEnabled(false);
        } else if (e.getSource() == meatballsBtn) {
            meatballsLabel.setBounds(1000, 40 + height, 100, 30);
            inputArr[2].setBounds(1200, 40 + height, 100, 30);
            meatballsBtn.setEnabled(false);
        } else if (e.getSource() == cucumbersBtn) {
            cucumbersLabel.setBounds(1000, 40 + height, 100, 30);
            inputArr[3].setBounds(1200, 40 + height, 100, 30);
            cucumbersBtn.setEnabled(false);
        } else if (e.getSource() == cauliflowerBtn) {
            cauliflowerLabel.setBounds(1000, 40 + height, 100, 30);
            inputArr[4].setBounds(1200, 40 + height, 100, 30);
            cauliflowerBtn.setEnabled(false);
        } else if (e.getSource() == tomatoBtn) {
            tomatoLabel.setBounds(1000, 40 + height, 100, 30);
            inputArr[5].setBounds(1200, 40 + height, 100, 30);
            tomatoBtn.setEnabled(false);
        } else if (e.getSource() == spinachBtn) {
            spinachLabel.setBounds(1000, 40 + height, 100, 30);
            inputArr[6].setBounds(1200, 40 + height, 100, 30);
            spinachBtn.setEnabled(false);
        } else if (e.getSource() == kaleBtn) {
            kaleLabel.setBounds(1000, 40 + height, 100, 30);
            inputArr[7].setBounds(1200, 40 + height, 100, 30);
            kaleBtn.setEnabled(false);
        } else if (e.getSource() == cabbageBtn) {
            cabbageLabel.setBounds(1000, 40 + height, 100, 30);
            inputArr[8].setBounds(1200, 40 + height, 100, 30);
            cabbageBtn.setEnabled(false);
        } else if (e.getSource() == romaineBtn) {
            romaineLabel.setBounds(1000, 40 + height, 100, 30);
            inputArr[9].setBounds(1200, 40 + height, 100, 30);
            romaineBtn.setEnabled(false);
        } else if (e.getSource() == peppersBtn) {
            peppersLabel.setBounds(1000, 40 + height, 100, 30);
            inputArr[10].setBounds(1200, 40 + height, 100, 30);
            peppersBtn.setEnabled(false);
        } else if (e.getSource() == cheeseBtn) {
            cheesesLabel.setBounds(1000, 40 + height, 100, 30);
            inputArr[11].setBounds(1200, 40 + height, 100, 30);
            cheeseBtn.setEnabled(false);
        } else if (e.getSource() == harissaBtn) {
            harissaLabel.setBounds(1000, 40 + height, 100, 30);
            inputArr[12].setBounds(1200, 40 + height, 100, 30);
            harissaBtn.setEnabled(false);
        } else if (e.getSource() == sauceBtn) {
            sauceLabel.setBounds(1000, 40 + height, 100, 30);
            inputArr[13].setBounds(1200, 40 + height, 100, 30);
            sauceBtn.setEnabled(false);
        } else if (e.getSource() == vinegarBtn) {
            vinegarLabel.setBounds(1000, 40 + height, 100, 30);
            inputArr[14].setBounds(1200, 40 + height, 100, 30);
            vinegarBtn.setEnabled(false);
        } else if (e.getSource() == submitBtn) {
            for (int i = 0; i < 15; ++i) {
                if (inputArr[i].getText() != "") {
                    quantityArray[i] = Integer.parseInt(inputArr[i].getText());
                }
            }
        } else if (e.getSource() == backBtn) {
            // TODO: Implement
        }

        height += 50;
    }

    public static void main(String[] args) {
        new inventoryPerOrder();
    }

}
