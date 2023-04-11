package playground.GUI.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
public class Example_GUI {

    public static void main(String[] args){new Example_GUI();}

    public Example_GUI(){

        Toolkit tk = Toolkit.getDefaultToolkit();//alternative method for full-screen window
        Dimension screenSize = tk.getScreenSize();

        JFrame mainFrame = new JFrame();
        //mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); //set FullScreen on every display resolution
        mainFrame.setSize(screenSize.width, screenSize.height);//alternative way
        mainFrame.setResizable(false);                          //alternative way
        mainFrame.setVisible(true);//set the mainFrame visibility to true
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//set the behaviour when the mainFrame is closed
        mainFrame.setTitle("Titolo di Prova - My Shitty");

        //Creation:
        //External
        JPanel externalPanel = new JPanel(new GridLayout(3, 1));
        //Internals: first level of abstraction
        JPanel internalPanelBlue = new JPanel(new GridLayout(1, 3));
        JPanel internalPanelGreen = new JPanel(new GridLayout(1, 3));
        JPanel internalPanelCyan = new JPanel(new GridLayout(1, 6));
        //Internals: second level of abstraction
        //blue
        JPanel gameBoardPanel = new JPanel(new GridLayout(2, 1)); //the chairman is just a card in the matrix
        JPanel myLibraryPanel = new JPanel(new GridLayout(3, 1));
        JPanel chatPanel = new JPanel(new GridLayout(2, 1));
        //green
        JPanel player1Panel = new JPanel(new GridLayout(2, 1));
        JPanel player2Panel = new JPanel(new GridLayout(2, 1));
        JPanel player3Panel = new JPanel(new GridLayout(2, 1));
        //cyan
        JPanel POPanel = new JPanel(new GridLayout(2, 1));
        JPanel CO1Panel = new JPanel(new GridLayout(2, 1));
        JPanel CO2Panel = new JPanel(new GridLayout(2, 1));
        JPanel pointsCO1Panel = new JPanel();
        JPanel pointsCO2Panel = new JPanel();
        JPanel fullLibraryPanel = new JPanel();
        //Internals: third level of abstraction
        //...in development

        //Addition: (Hierarchy of panels inside panels)
        //first level
        externalPanel.add(internalPanelBlue);
        externalPanel.add(internalPanelGreen);
        externalPanel.add(internalPanelCyan);
        //second level
        //blue
        internalPanelBlue.add(gameBoardPanel);
        internalPanelBlue.add(myLibraryPanel);
        internalPanelBlue.add(chatPanel);
        //green
        internalPanelGreen.add(player1Panel);
        internalPanelGreen.add(player2Panel);
        internalPanelGreen.add(player3Panel);
        //cyan
        internalPanelCyan.add(POPanel);
        internalPanelCyan.add(CO1Panel);
        internalPanelCyan.add(pointsCO1Panel);
        internalPanelCyan.add(CO2Panel);
        internalPanelCyan.add(pointsCO2Panel);
        internalPanelCyan.add(fullLibraryPanel);
        //third level
        //...in development

        JButton testButton = new JButton("PROVA");
        chatPanel.add(testButton);

        DisplayImageTest1(gameBoardPanel, "src/main/java/playground/GUI/swing/doge.png");

    }

    private void DisplayImageTest1(JPanel jp, String src) { //doesn't show image
        JLabel jl=new JLabel(new ImageIcon(src));
        jp.add(jl);
    }

    private void DisplayImageTest2(JPanel jp, String src) { //problem with read is Null
        try {
            BufferedImage img = ImageIO.read(getClass().getResource(src));
            JLabel label = new JLabel(new ImageIcon(img));
            //JLabel label = new JLabel(new ImageIcon(img.getScaledInstance(-1, 50, Image.SCALE_SMOOTH)));//in theory is a scalable image with the manual resize of the window
            jp.add(label);
        } catch (IOException e) {}
    }

}
