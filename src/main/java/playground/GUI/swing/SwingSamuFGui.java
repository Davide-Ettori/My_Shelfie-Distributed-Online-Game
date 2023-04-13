package playground.GUI.swing;

import javax.swing.*;
import java.awt.*;

public class SwingSamuFGui {
    public static void main(String[] args) {
        //PrimoJframe();
        BorderLayoutExample();
    }


private static void PrimoJframe() {

    //set the lookAndFeel (optional)
    try {
        UIManager.setLookAndFeel(
                UIManager.getCrossPlatformLookAndFeelClassName());//metal -> cross platform, looks the same in all platforms
        UIManager.getSystemLookAndFeelClassName();// set the default of the platform -> this by default
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (InstantiationException e) {
        e.printStackTrace();
    } catch (IllegalAccessException e) {
        e.printStackTrace();
    } catch (UnsupportedLookAndFeelException e) {
        e.printStackTrace();
    }

    JFrame window = new JFrame();

    //set the size of the window
    window.setSize(500, 600);

    //set the window visibility to true
    window.setVisible(true);

    //set the behaviour when the window is closed
    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    //set the window in the middle of the screen, if not specified, the window placed on the high sx corner
    window.setLocationRelativeTo(null);
    }

    /*
     * A Java swing FlowLayout example
     */


    /**
     * creates a swing window with the Border layout
     */
    private static void BorderLayoutExample() {

            //set the frame look and feel: in windows it will not cover the task bar, in mac noting will change
            JFrame.setDefaultLookAndFeelDecorated(true);

            //set title
            JFrame frame = new JFrame("Title");

            //set behaviour on close
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 600);

            // Define new buttons with different regions
            JButton jb1 = new JButton("NORTH");
            JButton jb2 = new JButton("SOUTH");
            JButton jb3 = new JButton("WEST");
            JButton jb4 = new JButton("EAST");
            JButton jb5 = new JButton("CENTER");

            // Define the panel to hold the buttons
            JPanel panel = new JPanel();

            //set orientation ( this is the default and won't change anything with NORD, SOUTH, WEST AND EST.
            panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

            //set the layout
            panel.setLayout(new BorderLayout());

            //set the layout with gaps between elements
            //panel.setLayout(new BorderLayout(10,20));

            //add the buttons
            panel.add(jb1, BorderLayout.NORTH);
            panel.add(jb2, BorderLayout.SOUTH);
            panel.add(jb3, BorderLayout.WEST);
            panel.add(jb4, BorderLayout.EAST);
            panel.add(jb5, BorderLayout.CENTER);

            //add panel to the frame
            frame.add(panel);

            //frame.setLocationRelativeTo(null); centre the window

            //Sizes the frame so that all its contents are at or above their preferred sizes.
            frame.pack();

            // Set the window to be visible as the default to be false
            frame.setVisible(true);
        }


}
