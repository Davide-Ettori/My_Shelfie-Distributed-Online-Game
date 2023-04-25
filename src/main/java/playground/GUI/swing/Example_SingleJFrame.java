package playground.GUI.swing;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Example_SingleJFrame {

    JFrame mainFrame = new JFrame("Double CheeseBurger");
    JPanel externalPanel = new JPanel(new GridBagLayout());
    JLabel boardLabel, libraryLabel;

    public static void main(String[] args){ new Example_SingleJFrame();}

    public Example_SingleJFrame(){

        while(true){
            FirstDecoration();

            try{
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {System.out.println("OiOiOiOi qualcosa è andato storto!");}

            SecondDecoration();

            try{
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {System.out.println("OiOiOiOi qualcosa è andato storto!");}
        }

    }
    public void FirstDecoration(){

        externalPanel.removeAll();

        final ImageIcon boardImage = new ImageIcon(new ImageIcon("assets/boards/livingroom.png").getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH));
        boardLabel = new JLabel(boardImage);
        boardLabel.setLayout(new GridBagLayout());

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        externalPanel.add(boardLabel, gbc2);

        mainFrame.add(externalPanel);
        mainFrame.setSize(800, 800);
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }
    public void SecondDecoration(){

        externalPanel.removeAll();

        final ImageIcon libraryImage = new ImageIcon(new ImageIcon("assets/boards/bookshelf_orth.png").getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH));
        libraryLabel = new JLabel(libraryImage);
        libraryLabel.setLayout(new GridBagLayout());

        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        externalPanel.add(libraryLabel, gbc3);

        mainFrame.setVisible(true);
    }

}
