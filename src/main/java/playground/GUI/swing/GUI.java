package playground.GUI.swing;

import javax.swing.*;
import java.awt.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class GUI{
    private int count = 0;
    private JFrame frame;
    private JLabel label;
    private JPanel panel;
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

        label = new JLabel("Numero di punti: " + count);

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        panel.setLayout(new GridLayout(0,3));
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