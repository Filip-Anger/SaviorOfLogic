import java.awt.Color;
import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class GamePanel extends JPanel {
    private Timer timer;
    private Player player;
    private int[][] tileMap;

    public GamePanel() {
        this.player = new Player();
        this.timer = new Timer(16, e -> {
            player.update();
            repaint();
        });
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke("W"), "moveUp");
        inputMap.put(KeyStroke.getKeyStroke("S"), "moveDown");
        inputMap.put(KeyStroke.getKeyStroke("A"), "moveLeft");
        inputMap.put(KeyStroke.getKeyStroke("D"), "moveRight");

        ActionMap actionMap = getActionMap();
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                this.player.setMovingUp(true);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clean background
        
        this.player.draw(g);
    }

    private void updateGameLogic() {

    }
}
