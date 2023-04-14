package playground.GUI.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;
public class Example_GridBagLayout {

    public static void main(String[] args){new Example_GridBagLayout();}

    public Example_GridBagLayout(){

        JFrame mainFrame = new JFrame("PROVA");
        JPanel externalPanel = new JPanel(new GridBagLayout());

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();

        //IMPOSTA L'IMMAGINE DI SFONDO: la board
        final ImageIcon boardImage = new ImageIcon(new ImageIcon("assets/boards/livingroom.png").getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH));
        JLabel boardLabel = new JLabel(boardImage);
        boardLabel.setLayout(new GridBagLayout());
        /*
        //IMPOSTA L'IMMAGINE DI SFONDO: la library
        final ImageIcon libraryImage = new ImageIcon(new ImageIcon("assets/boards/bookshelf_orth.png").getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH));
        JLabel libraryLabel = new JLabel(libraryImage);
        libraryLabel.setLayout(new GridBagLayout());

        //Tutorial di youtube:
        //Componenti:
        JLabel NameLabel = new JLabel("Nome: ");
        JTextField NameText = new JTextField(20);
        JButton NameButton = new JButton("Inserimento");

        //Layout:
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; //posizione x nella grid
        gbc.gridy = 0; //posizione y nella grid

        gbc.weightx = 0.01;//valore compreso tra 0 e 1, imposta il "peso", quindi quanto spazio occupano i componenti (1 = componente spazioso, ma non di per sè grosso)
        gbc.weighty = 0.01;

        gbc.insets = new Insets(0,0,0,5); //sono i margini di spazio intorno al componente

        boardLabel.add(NameLabel, gbc); //aggiungo la Label al Panel di tipo GridBagLayout, e le passo i constraint

        gbc.gridx = 1; //NB: ricorda che ogni volta che riusi il gbc, devi dargli nuovi valori, altrimenti lui usa i vecchi
        gbc.gridy = 0;

        gbc.weightx = 0.01;
        gbc.weighty = 0.01;

        gbc.insets = new Insets(0,0,0,0);

        boardLabel.add(NameText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;

        gbc.weightx = 1;
        gbc.weighty = 1;

        gbc.gridwidth = 2; //imposto quante righe della tabella occupa il componente
        gbc.gridheight = 1; //uguale ma per le colonne

        gbc.anchor = GridBagConstraints.PAGE_START; //dice dove posizionare il componente (di default è CENTER) all'interno del suo spazio che occupa mediante il suo weight

        gbc.fill = GridBagConstraints.VERTICAL; //riempie tutto lo spazio verticale disponibile grazie al suo peso

        gbc.ipadx = 50; //aggiunge del padding dentro al componente
        gbc.ipady = 50;

        libraryLabel.add(NameButton, gbc);
        /FINE TUTORIAL YOUTUBE

         */

        //Aggiungo la library e la board al Pannello gridBagLayout che li contiene
        /*
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        externalPanel.add(boardLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
       externalPanel.add(libraryLabel, gbc);


         */

        //sistemo il frame esterno che contiene il panel
        mainFrame.add(boardLabel);
        mainFrame.setSize(screenSize.width, screenSize.height);
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
