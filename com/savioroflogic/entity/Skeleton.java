package com.savioroflogic.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.savioroflogic.util.Pair;

/**Demo of enemie, extends entiti, collisions work the same input is 
 * based on follow method - tries to go straight towards player
 */
public class Skeleton extends Entiti {

    Image sprite;
    
    public Skeleton(int velocity, Pair absolutePosition) {
        this.setAbsotulePosition(absolutePosition);
        this.velocity = velocity;
        try {
            this.sprite = ImageIO.read(new File("Static\\Player\\whiteSkull.png"));  
            this.sprite = this.sprite.getScaledInstance(com.savioroflogic.core.Game.TILE_SIZE * 2, com.savioroflogic.core.Game.TILE_SIZE * 2, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void follow(Entiti target) {
        this.movingRight = (target.getMiddle().x() > this.getMiddle().x());
        this.movingLeft = (this.getMiddle().x() > target.getMiddle().x());
        this.movingUp = (this.getMiddle().y() > target.getMiddle().y());
        this.movingDown = (target.getMiddle().y() > this.getMiddle().y());
    }

    @Override
    public void draw(Graphics g, Pair offset) {
        g.drawImage(this.sprite, this.absolutePosition.x() - offset.x(), this.absolutePosition.y() - offset.y(), null);
    }
}
