package playground.GUI.swing;

import javax.swing.*;
import java.awt.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class GUI{
    private int count = 0;
    private JFrame frame;
    private JLabel label;
    private JPanel panel;
    private JTextField text;
    private final int maxPoints = 8;

    public static void main(String[] args) {
        new GUI();
    } // inizializzo la GUI
    public GUI(){
        frame = new JFrame(); // creo la finestra

        JButton buttonUp = new JButton("Aggiungi"); // bottone per aggiungere punti, con relativa callback
        buttonUp.addActionListener((e) ->{
            if(count == maxPoints){
                showMessageDialog(null, "Punti massimi raggiunti (" + maxPoints + ")");
                return;
            }
            label.setText("Numero di punti: " + ++count);
        });

        JButton buttonDown = new JButton("Togli"); // bottone per togliere punti, con relativa callback
        buttonDown.addActionListener((e) ->{
            if(count == 0)
                return;
            label.setText("Numero di punti: " + --count);
        });

        JButton buttonReset = new JButton("Resetta"); // bottone per resettare punti, con relativa callback
        buttonReset.addActionListener((e) ->{
            count = -1;
            label.setText("Numero di punti: " + ++count);
        });

        JButton buttonPrint = new JButton("Stampa"); // bottone per stampare punti, con relativa callback
        buttonPrint.addActionListener((e) ->{
            showMessageDialog(null, "\nBravo " + text.getText() + ", hai fatto " + count + " punti!");
        });

        JButton buttonDoge = new JButton(new ImageIcon("src/main/java/playground/GUI/swing/doge.png"));
        buttonDoge.addActionListener(e -> showMessageDialog(null, "Bau Bau!")); // onClick event
        buttonDoge.setPreferredSize(new Dimension(50, 200));
        buttonDoge.setBorderPainted(false); // ecco come aggiungere un'immagine cliccabile

        label = new JLabel("Numero di punti: " + count); // casella di testo

        text = new JTextField(20); // textbox input dall'utente
        text .setBounds(100, 20, 165, 25);
        text.setText("Inserisci il tuo nome");

        panel = new JPanel(); // creo un pannello, dandogli i parametri dimensionali
        panel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        panel.setLayout(new GridLayout(0,3)); // griglia con o righe e 3 colonne
        // da qui in poi aggiungo tutti gli elementi che ho creato al pannello
        // inizio
        panel.add(text);
        panel.add(new JPanel()); // elemento placeholder per impaginare bene
        panel.add(buttonPrint);
        panel.add(buttonUp);
        panel.add(new JPanel());
        panel.add(buttonDown);
        panel.add(new JPanel());
        panel.add(buttonReset);
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(label);
        panel.add(buttonDoge);
        // fine

        frame.add(panel, BorderLayout.CENTER); // aggiungo il pannello alla finestra
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GUI di Prova");
        frame.pack(); // preparo la finestra
        frame.setVisible(true); // mostro il tutto a schermo, GUI
    }
}