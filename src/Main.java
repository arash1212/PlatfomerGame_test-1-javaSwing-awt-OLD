import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Main {
    public static void main(String[] args) {
        final JFXPanel fxPanel = new JFXPanel();
        
        JFrame frame = new JFrame();
        frame.setTitle("بازی 1");
        frame.setResizable(false);
        frame.setSize(500,400);

        GameManager manager=new GameManager();
        frame.add(manager);
        manager.Start();
        frame.addKeyListener(manager);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
