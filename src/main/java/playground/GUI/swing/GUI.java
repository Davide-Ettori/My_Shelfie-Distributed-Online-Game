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
    }
    public GUI(){
        frame = new JFrame();

        JButton buttonUp = new JButton("Aggiungi");
        buttonUp.addActionListener((e) ->{
            if(count == maxPoints){
                showMessageDialog(null, "Punti massimi raggiunti (" + maxPoints + ")");
                return;
            }
            label.setText("Numero di punti: " + ++count);
        });

        JButton buttonDown = new JButton("Togli");
        buttonDown.addActionListener((e) ->{
            if(count == 0)
                return;
            label.setText("Numero di punti: " + --count);
        });

        JButton buttonReset = new JButton("Resetta");
        buttonReset.addActionListener((e) ->{
            count = -1;
            label.setText("Numero di punti: " + ++count);
        });

        JButton buttonPrint = new JButton("Stampa");
        buttonPrint.addActionListener((e) ->{
            showMessageDialog(null, "\nBravo " + text.getText() + ", hai fatto " + count + " punti!");
        });

        label = new JLabel("Numero di punti: " + count);

        text = new JTextField(20);
        text .setBounds(100, 20, 165, 25);
        text.setText("Inserisci il tuo nome");

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        panel.setLayout(new GridLayout(0,3));
        panel.add(text);
        panel.add(new JPanel());
        panel.add(buttonPrint);
        panel.add(buttonUp);
        panel.add(new JPanel());
        panel.add(buttonDown);
        panel.add(new JPanel());
        panel.add(buttonReset);
        panel.add(new JPanel());
        panel.add(new JPanel());
        panel.add(label);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GUI di Prova");
        frame.pack();
        frame.setVisible(true);
    }
}