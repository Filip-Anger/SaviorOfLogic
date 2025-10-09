import java.awt.Color;
import java.awt.Graphics;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
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
    private InputMap inputMap;
    private ActionMap actionMap;
    public static final int TILE_SIZE = 16;


    public GamePanel() {
        // Player
        this.player = new Player();

        // Update timer
        this.timer = new Timer(16, e -> {
            player.update();
            repaint();
        });

        // Input map
        this.inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        this.inputMap.put(KeyStroke.getKeyStroke("W"), "moveUp");
        this.inputMap.put(KeyStroke.getKeyStroke("S"), "moveDown");
        this.inputMap.put(KeyStroke.getKeyStroke("A"), "moveLeft");
        this.inputMap.put(KeyStroke.getKeyStroke("D"), "moveRight");

        // Input handle
        this.actionMap = this.getActionMap();
        this.actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setMovingUp(true);
            }
        });




        this.timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clean background

        TileMap.draw(g); // HELPER class to make it organized

        g.drawImage(this.tilePath, 100, 100, null);
        this.player.draw(g);
    }

    private void updateGameLogic() {

    }
}
