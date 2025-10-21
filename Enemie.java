public class Enemie extends Entiti {

    public Enemie(int velocity) {
        this.velocity = velocity;
    }
    public void follow(Pair playerPos) {
        this.movingRight = (playerPos.x() > this.absolutePosition.x());
        this.movingLeft = (this.absolutePosition.x() > playerPos.x());
        this.movingUp = (this.absolutePosition.y() > playerPos.y());
        this.movingDown = (playerPos.y() > this.absolutePosition.y());
    }
}
